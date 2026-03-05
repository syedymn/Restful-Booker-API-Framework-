# 🔧 Restful Booker API Automation Framework

![Java](https://img.shields.io/badge/Java-17-orange)
![RestAssured](https://img.shields.io/badge/RestAssured-5.5.0-green)
![TestNG](https://img.shields.io/badge/TestNG-7.10.0-blue)
![Maven](https://img.shields.io/badge/Maven-Build-red)
![CI/CD](https://img.shields.io/badge/GitHub_Actions-CI/CD-black)

## 📖 Overview

A **production-ready API test automation framework** 
built with **RestAssured** and **Java 17** to validate the [Restful Booker API](https://restful-booker.herokuapp.com/apidoc/index.html). 
This framework demonstrates enterprise-grade practices including **POJO-based serialization/deserialization**, 
**token-based authentication**, **data-driven testing**, 
and **CI/CD integration**.

Designed to showcase a scalable architecture that can 
be adapted for any RESTful API testing needs.

---

## 🚀 Tech Stack

| Tool / Technology | Purpose |
|---|---|
| **Java 17** | Programming language |
| **RestAssured 5.5.0** | API automation library |
| **TestNG 7.10.0** | Test runner and assertions |
| **Jackson Databind** | JSON ↔ POJO serialization/deserialization |
| **Allure Reports** | Test execution reporting |
| **ExtentReports 5** | HTML test reporting |
| **Maven** | Build and dependency management |
| **GitHub Actions** | CI/CD pipeline |
| **SLF4J** | Logging framework |
| **JavaFaker** | Dynamic test data generation |

---

## 🏗️ Framework Architecture

```text
API-Automation-Framework/
│
├── src/
│   ├── main/java/com/api/
│   │   ├── base/
│   │   │   └── TestBase.java            # Setup, auth token, 
│   │   │                                  request/response specs
│   │   ├── endpoints/
│   │   │   └── BookingEndpoints.java    # API endpoint methods
│   │   │
│   │   ├── pojo/
│   │   │   ├── Booking.java            # Booking POJO with 
│   │   │   │                             Builder pattern
│   │   │   └── BookingDates.java       # Nested POJO for dates
│   │   │
│   │   └── utilities/
│   │       └── ConfigReader.java       # Properties file reader
│   │
│   ├── main/resources/
│   │   ├── config.properties           # Environment configuration
│   │   └── schemas/
│   │       └── booking-schema.json     # JSON Schema for validation
│   │
│   └── test/java/com/api/tests/
│       └── BookerTests.java            # CRUD + negative + 
│                                         data-driven tests
│
├── pom.xml                             # Maven dependencies
├── testng.xml                          # TestNG suite configuration
├── .gitignore                          # Git ignore rules
├── .github/workflows/ci.yml           # GitHub Actions CI/CD
└── README.md                           # Project documentation
✅ Key Features
Feature	Description
Full CRUD Testing	POST, GET, PUT, PATCH, DELETE operations
POJO Serialization	Nested Java objects (Booking → BookingDates) with Jackson
Builder Pattern	Clean, readable test data construction
Token Authentication	Auto-generates auth token for secured endpoints
Data-Driven Testing	TestNG @DataProvider for multiple test data sets
Negative Testing	Invalid ID, missing fields, unauthorized access
Request/Response Specs	Reusable RestAssured specifications
JSON Schema Validation	Validates API response structure
Allure Reporting	Detailed test reports with @Description and @Severity
Config-Driven	All URLs and credentials externalized in config.properties
CI/CD Pipeline	GitHub Actions auto-runs tests on push/PR
Logging	SLF4J integration for request/response logging
🔗 API Endpoints Covered
Method	Endpoint	Description	Auth Required
POST	/booking	Create a new booking	No
GET	/booking/{id}	Get booking by ID	No
GET	/booking	Get all booking IDs	No
PUT	/booking/{id}	Update entire booking	✅ Yes (Token)
PATCH	/booking/{id}	Partial update booking	✅ Yes (Token)
DELETE	/booking/{id}	Delete a booking	✅ Yes (Token)
POST	/auth	Generate auth token	No
📄 API Documentation: Restful Booker API Docs

📋 Test Cases
#	Test Name	Type	Priority
1	Create Booking (POST)	Positive	Critical
2	Get Booking by ID (GET)	Positive	Critical
3	Get All Bookings (GET)	Positive	Normal
4	Update Booking (PUT)	Positive	Critical
5	Partial Update (PATCH)	Positive	Normal
6	Delete Booking (DELETE)	Positive	Critical
7	Verify Deleted Booking	Positive	Normal
8	Invalid Booking ID (GET)	Negative	Normal
9	Data-Driven Create (POST)	Data-Driven	Normal
⚙️ Prerequisites
Before running the tests, ensure you have:

Java 17 or higher → Download
Maven 3.8+ → Download
Git → Download
IDE (IntelliJ IDEA or Eclipse recommended)
Verify installations:

Bash

java -version
mvn -version
git --version
🚀 How to Run Tests
Option 1: Run via Maven (Recommended)
Bash

# Clone the repository
git clone https://github.com/maikehasman/Restful-Booker-API-Framework.git

# Navigate to project directory
cd Restful-Booker-API-Framework

# Run all tests
mvn clean test

# Run specific test class
mvn test -Dtest=BookerTests

# Run with specific TestNG suite
mvn test -DsuiteXmlFile=testng.xml
Option 2: Run via IDE
text

1. Open project in IntelliJ IDEA / Eclipse
2. Right-click on testng.xml
3. Select "Run"
Option 3: Run via TestNG
Bash

# Run specific test method
mvn test -Dtest=BookerTests#createBookingTest
📊 Test Reports
Allure Reports
Bash

# Generate Allure report after test run
mvn allure:serve
ExtentReports
After test execution, HTML report is generated at:

text

reports/ExtentReport.html
🔧 Configuration
All configurations are managed via config.properties:

properties

# API Configuration
baseURI=https://restful-booker.herokuapp.com

# Authentication
username=admin
password=password123

# Environment
environment=QA
🔄 CI/CD Pipeline
This framework includes a GitHub Actions workflow
that automatically:

✅ Triggers on every push and pull request
✅ Sets up Java 17 environment
✅ Runs all tests via Maven
✅ Generates and publishes test reports
🛠️ Design Patterns Used
Pattern	Implementation
Builder Pattern	POJO classes (Booking, BookingDates)
Singleton	ConfigReader for properties management
Template Method	TestBase provides shared setup/teardown
Data-Driven	TestNG @DataProvider for parameterized tests


👤 Author
Syed Muttaquee
QA Automation Engineer | SDET

🔗 LinkedIn
💻 GitHub
📧 yameen.muttaquee@gmail.com
