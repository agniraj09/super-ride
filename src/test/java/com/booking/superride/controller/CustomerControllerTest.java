package com.booking.superride.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasLength;
import static org.hamcrest.Matchers.is;

import com.booking.superride.common.AbstractIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class CustomerControllerTest extends AbstractIntegrationTest {

    private static final String CUSTOMER_REGISTER_URI = "/customer/register";

    @BeforeAll
    void setUp() {
        RestAssured.port = localServerPort;
    }

    @Test
    void testCustomerRegistrationWithInvalidName() {

        given().contentType(ContentType.JSON)
                .body(
                        """
                        {
                            "customerName": "",
                            "mobileNumber" : "9797979777"
                        }
                        """)
                .when()
                .post(CUSTOMER_REGISTER_URI)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .body("detail", hasItems("customerName cannot be blank"))
                .body("type", equalTo("about:blank"))
                .body("title", equalTo("Bad Request"))
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()))
                .body("instance", equalTo(CUSTOMER_REGISTER_URI));
    }

    @Test
    void testCustomerRegistration() {

        given().contentType(ContentType.JSON)
                .body(
                        """
                        {
                            "customerName": "Surya",
                            "mobileNumber" : "9797979777"
                        }
                        """)
                .when()
                .post(CUSTOMER_REGISTER_URI)
                .then()
                .statusCode(HttpStatus.OK.value())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body("customerName", is("Surya"))
                .body("customerId", greaterThan(0))
                .body("mobileNumber", hasLength(10));
    }

    @Test
    void testCustomerRegistrationForDuplicateCustomer() {

        String requestBody =
                """
                        {
                            "customerName": "Anbu",
                            "mobileNumber" : "9876543210"
                        }
                        """;
        // Register customer details
        given().contentType(ContentType.JSON).body(requestBody).when().post(CUSTOMER_REGISTER_URI);

        // Register same customer details again to test idempotency validation
        given().contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(CUSTOMER_REGISTER_URI)
                .then()
                .statusCode(HttpStatus.CONFLICT.value())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .body("detail", equalTo("Customer already exists with following mobile number 9876543210"))
                .body("type", equalTo("about:blank"))
                .body("title", equalTo("Duplicate data"))
                .body("status", equalTo(HttpStatus.CONFLICT.value()))
                .body("instance", equalTo(CUSTOMER_REGISTER_URI));
    }
}
