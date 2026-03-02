package com.api.base;

import org.testng.annotations.BeforeClass;
import io.restassured.RestAssured;

public class TestBase {
    
    @BeforeClass
    public void setup() {
        // This tells RestAssured where to send requests
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.basePath = "/booking";
    }
}
