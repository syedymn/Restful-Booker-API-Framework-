# Restful Booker API Automation Framework

A scalable, data-driven API Automation framework built to test the Restful-Booker booking system. Designed using the **RestAssured** library with **Jackson** for serialization.

## 🚀 Tech Stack
*   **Language:** Java 17
*   **Library:** RestAssured 5.3
*   **Runner:** TestNG
*   **Data Binding:** Jackson Databind (POJO Mapping)
*   **Reporting:** ExtentReports 5
*   **Build Tool:** Maven

## 🏗 Key Features
*   **POJO Serialization:** Uses complex nested Java Objects (`BookingPOJO`, `BookingDates`) to construct JSON payloads dynamically.
*   **Chaining:** Tests pass data (e.g., Booking IDs) from one test to another using static variables.
*   **BaseURI Management:** Centralized configuration in `TestBase.java`.
*   **CRUD Operations:** Covers Create (POST), Read (GET), Update (PUT), and Delete (DELETE) workflows.

## 📂 Project Structure
```text
src/test/java
   ├── com.api.base    # Base URL Setup
   ├── com.api.tests   # Test Logic (CRUD)
   └── com.api.pojo    # Java Data Models (Serialization)
