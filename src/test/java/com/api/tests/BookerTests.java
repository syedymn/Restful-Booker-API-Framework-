package com.api.tests;

import com.api.base.TestBase;
import com.api.pojo.Booking;
import com.api.pojo.BookingDates;
import io.restassured.response.Response;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeMethod;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * BookerTests — Comprehensive API test suite covering
 * full CRUD operations, authentication, negative testing,
 * and data-driven scenarios for the Restful Booker API.
 */
public class BookerTests extends TestBase {

    private int bookingId;

    // ============================================
    // DELAY BETWEEN TESTS (Prevents Rate Limiting)
    // ============================================

    @BeforeMethod
    public void waitBetweenTests() throws InterruptedException {
        Thread.sleep(1500); // 1.5 second delay between tests
    }

    // ============================================
    // RETRY HELPER METHOD
    // ============================================

    private Response sendPostWithRetry(Object body) {
        Response response = null;
        int maxRetries = 3;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                response = given()
                        .spec(requestSpec)
                        .body(body)
                    .when()
                        .post("/booking")
                    .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract().response();
                return response; // Success — return immediately
            } catch (AssertionError e) {
                System.out.println("POST attempt " + attempt 
                    + " failed. Status not 200. Retrying...");
                if (attempt == maxRetries) throw e;
                try { 
                    Thread.sleep(3000); // Wait 3 seconds before retry
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return response;
    }

    // ============================================
    // TEST DATA PROVIDER
    // ============================================

    @DataProvider(name = "bookingData")
    public Object[][] bookingTestData() {
        return new Object[][] {
            {"John", "Smith", 200, true,
             "2024-06-01", "2024-06-10", "Breakfast"},
            {"Jane", "Doe", 350, false,
             "2024-07-15", "2024-07-20", "Lunch"},
            {"Mike", "Johnson", 500, true,
             "2024-08-01", "2024-08-05", "Dinner"}
        };
    }

    // ============================================
    // POST — CREATE BOOKING
    // ============================================

    @Test(priority = 1)
    @Description("Verify new booking can be created via POST")
    @Severity(SeverityLevel.CRITICAL)
    public void createBookingTest() {

        // Arrange
        BookingDates dates = new BookingDates.Builder()
                .checkin("2024-03-01")
                .checkout("2024-03-10")
                .build();

        Booking bookingPayload = new Booking.Builder()
                .firstname("John")
                .lastname("Doe")
                .totalprice(150)
                .depositpaid(true)
                .bookingdates(dates)
                .additionalneeds("Breakfast")
                .build();

        // Act — Send POST with retry logic
        Response response = sendPostWithRetry(bookingPayload);

        // Verify response body
        Assert.assertEquals(
            response.jsonPath().getString("booking.firstname"), 
            "John");
        Assert.assertEquals(
            response.jsonPath().getString("booking.lastname"), 
            "Doe");

        // Extract booking ID
        bookingId = response.jsonPath().getInt("bookingid");
        System.out.println("Created Booking ID: " + bookingId);

        Assert.assertTrue(bookingId > 0,
            "Booking ID should be a positive number");
    }

    // ============================================
    // GET — READ BOOKING BY ID
    // ============================================

    @Test(priority = 2, dependsOnMethods = "createBookingTest")
    @Description("Verify booking can be retrieved by ID via GET")
    @Severity(SeverityLevel.CRITICAL)
    public void getBookingByIdTest() {

        Response response = given()
                .spec(requestSpec)
                .pathParam("id", bookingId)
            .when()
                .get("/booking/{id}")
            .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().response();

        // Deserialize response to POJO
        Booking actualBooking = response.as(Booking.class);

        // Verify all fields
        Assert.assertEquals(actualBooking.getFirstname(), "John");
        Assert.assertEquals(actualBooking.getLastname(), "Doe");
        Assert.assertEquals(actualBooking.getTotalprice(), 150);
        Assert.assertTrue(actualBooking.isDepositpaid());
        Assert.assertEquals(
            actualBooking.getBookingdates().getCheckin(),
            "2024-03-01");
        Assert.assertEquals(
            actualBooking.getBookingdates().getCheckout(),
            "2024-03-10");
        Assert.assertEquals(
            actualBooking.getAdditionalneeds(), "Breakfast");

        System.out.println("Retrieved: " + actualBooking);
    }

    // ============================================
    // GET ALL — LIST ALL BOOKINGS
    // ============================================

    @Test(priority = 3)
    @Description("Verify all bookings can be retrieved via GET")
    @Severity(SeverityLevel.NORMAL)
    public void getAllBookingsTest() {

        given()
                .spec(requestSpec)
            .when()
                .get("/booking")
            .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].bookingid", notNullValue());
    }

    // ============================================
    // PUT — FULL UPDATE (Requires Auth Token)
    // ============================================

    @Test(priority = 4, dependsOnMethods = "createBookingTest")
    @Description("Verify booking can be fully updated via PUT")
    @Severity(SeverityLevel.CRITICAL)
    public void updateBookingTest() {

        BookingDates updatedDates = new BookingDates.Builder()
                .checkin("2024-05-01")
                .checkout("2024-05-15")
                .build();

        Booking updatedBooking = new Booking.Builder()
                .firstname("Jane")
                .lastname("Smith")
                .totalprice(300)
                .depositpaid(false)
                .bookingdates(updatedDates)
                .additionalneeds("Lunch")
                .build();

        // PUT request WITH auth token
        Response response = given()
                .spec(requestSpec)
                .header("Cookie", "token=" + token)
                .pathParam("id", bookingId)
                .body(updatedBooking)
            .when()
                .put("/booking/{id}")
            .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().response();

        // Deserialize and verify
        Booking actualBooking = response.as(Booking.class);
        Assert.assertEquals(actualBooking.getFirstname(), "Jane");
        Assert.assertEquals(actualBooking.getLastname(), "Smith");
        Assert.assertEquals(actualBooking.getTotalprice(), 300);
        Assert.assertFalse(actualBooking.isDepositpaid());

        System.out.println("Updated: " + actualBooking);
    }

    // ============================================
    // PATCH — PARTIAL UPDATE (Requires Auth Token)
    // ============================================

    @Test(priority = 5, dependsOnMethods = "updateBookingTest")
    @Description("Verify booking can be partially updated via PATCH")
    @Severity(SeverityLevel.NORMAL)
    public void partialUpdateBookingTest() {

        String partialPayload = "{"
                + "\"firstname\": \"Updated\","
                + "\"totalprice\": 999"
                + "}";

        Response response = given()
                .spec(requestSpec)
                .header("Cookie", "token=" + token)
                .pathParam("id", bookingId)
                .body(partialPayload)
            .when()
                .patch("/booking/{id}")
            .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("firstname", equalTo("Updated"))
                .body("totalprice", equalTo(999))
                .extract().response();

        System.out.println("Partially Updated:");
        response.prettyPrint();
    }

    // ============================================
    // DELETE — DELETE BOOKING (Requires Auth Token)
    // ============================================

    @Test(priority = 6, dependsOnMethods = "createBookingTest")
    @Description("Verify booking can be deleted via DELETE")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteBookingTest() {

        given()
                .spec(requestSpec)
                .header("Cookie", "token=" + token)
                .pathParam("id", bookingId)
            .when()
                .delete("/booking/{id}")
            .then()
                .statusCode(201);

        System.out.println("Deleted Booking ID: " + bookingId);
    }

    // ============================================
    // VERIFY DELETE — Confirm Deletion
    // ============================================

    @Test(priority = 7, dependsOnMethods = "deleteBookingTest")
    @Description("Verify deleted booking returns 404")
    @Severity(SeverityLevel.NORMAL)
    public void verifyBookingDeletedTest() {

        given()
                .spec(requestSpec)
                .pathParam("id", bookingId)
            .when()
                .get("/booking/{id}")
            .then()
                .statusCode(404);

        System.out.println("Confirmed: Booking "
            + bookingId + " no longer exists.");
    }

    // ============================================
    // NEGATIVE TEST — Invalid Booking ID
    // ============================================

    @Test(priority = 8)
    @Description("Verify invalid booking ID returns 404")
    @Severity(SeverityLevel.NORMAL)
    public void getInvalidBookingTest() {

        given()
                .spec(requestSpec)
                .pathParam("id", 999999999)
            .when()
                .get("/booking/{id}")
            .then()
                .statusCode(404);
    }

    // ============================================
    // DATA-DRIVEN TEST — Multiple Bookings
    // ============================================

    @Test(priority = 9, dataProvider = "bookingData")
    @Description("Data-driven: Create bookings with multiple data sets")
    @Severity(SeverityLevel.NORMAL)
    public void createBookingDataDrivenTest(
            String firstname, String lastname,
            int totalprice, boolean depositpaid,
            String checkin, String checkout,
            String additionalneeds) {

        BookingDates dates = new BookingDates.Builder()
                .checkin(checkin)
                .checkout(checkout)
                .build();

        Booking booking = new Booking.Builder()
                .firstname(firstname)
                .lastname(lastname)
                .totalprice(totalprice)
                .depositpaid(depositpaid)
                .bookingdates(dates)
                .additionalneeds(additionalneeds)
                .build();

        // Use retry helper for POST requests
        Response response = sendPostWithRetry(booking);

        // Verify response
        Assert.assertEquals(
            response.jsonPath().getString("booking.firstname"),
            firstname);
        Assert.assertEquals(
            response.jsonPath().getString("booking.lastname"),
            lastname);
        Assert.assertEquals(
            response.jsonPath().getInt("booking.totalprice"),
            totalprice);
    }
}