package com.api.tests;

import com.api.base.TestBase;
import com.api.pojo.Booking;
import com.api.pojo.BookingDates;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class BookerTests extends TestBase {

    
    public static int bookingId; 

    @Test(priority = 1)
    public void createBookingTest() {
        System.out.println("--- Step 1: Create Booking ---");

     
        BookingDates dates = new BookingDates("2024-03-01", "2024-03-10");
        Booking bookingPayload = new Booking("Maike", "Hasman", 150, true, dates, "Dinner");

       
        Response response = given()
            .contentType(ContentType.JSON)
            .body(bookingPayload)
        .when()
            .post();

        response.prettyPrint();

        // 3. Validation
        Assert.assertEquals(response.getStatusCode(), 200);
        
        // 4. Extract ID for the next test
        bookingId = response.jsonPath().getInt("bookingid");
        System.out.println("Generated Booking ID: " + bookingId);
        
        Assert.assertTrue(bookingId > 0, "Booking ID should be generated!");
    }

    @Test(priority = 2, dependsOnMethods = "createBookingTest")
    public void getBookingTest() {
        System.out.println("--- Step 2: Get Booking by ID ---");

        Response response = given()
            .pathParam("id", bookingId) 
        .when()
            .get("/{id}"); // https://restful-booker.herokuapp.com/booking/{id}

        response.prettyPrint();

        Assert.assertEquals(response.getStatusCode(), 200);
        
        // Verify the data matches what we created
        String actualName = response.jsonPath().getString("firstname");
        Assert.assertEquals(actualName, "Maike");
    }
}
