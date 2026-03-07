package com.api.base;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class TestBase {

    protected static Properties properties;
    protected static String token;
    protected RequestSpecification requestSpec;
    protected ResponseSpecification responseSpec;

    @BeforeClass
    public void setup() throws IOException {

        // Load configuration from properties file
        properties = new Properties();
        FileInputStream fis = new FileInputStream(
            "src/test/resources/config.properties");
        properties.load(fis);

        // Set Base URI from config file
        RestAssured.baseURI = properties.getProperty("baseURI");

        // Build reusable Request Specification
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        // Build reusable Response Specification
        responseSpec = new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();

        // Generate authentication token
        generateToken();
    }

    /**
     * Generates auth token for PUT, PATCH, DELETE operations
     * Restful Booker requires token-based authentication
     */
    protected void generateToken() {
        String requestBody = "{"
                + "\"username\": \"" + properties.getProperty("username") + "\","
                + "\"password\": \"" + properties.getProperty("password") + "\""
                + "}";

        token = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        System.out.println("Auth Token Generated: " + token);
    }

    @AfterClass
    public void teardown() {
        // Reset RestAssured to defaults
        RestAssured.reset();
        System.out.println("Test cleanup completed.");
    }
}