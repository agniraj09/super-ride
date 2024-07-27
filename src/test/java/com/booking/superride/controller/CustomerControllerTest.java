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
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerControllerTest extends AbstractIntegrationTest {

    @BeforeAll
    void setUp() {
        RestAssured.port = localServerPort;
    }

    @Test
    @Order(1)
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
                .post("/customer/register")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .body("detail", hasItems("customerName cannot be blank"))
                .body("type", equalTo("about:blank"))
                .body("title", equalTo("Bad Request"))
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()))
                .body("instance", equalTo("/customer/register"));
    }

    @Test
    @Order(2)
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
                .post("/customer/register")
                .then()
                .statusCode(HttpStatus.OK.value())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body("customerName", is("Surya"))
                .body("customerId", greaterThan(0))
                .body("mobileNumber", hasLength(10));
    }

    @Test
    @Order(3)
    void testCustomerRegistrationForDuplicateCustomer() {

        given().contentType(ContentType.JSON)
                .body(
                        """
                        {
                            "customerName": "Surya",
                            "mobileNumber" : "9797979777"
                        }
                        """)
                .when()
                .post("/customer/register")
                .then()
                .statusCode(HttpStatus.CONFLICT.value())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .body("detail", equalTo("Customer already exists with following mobile number 9797979777"))
                .body("type", equalTo("about:blank"))
                .body("title", equalTo("Duplicate data"))
                .body("status", equalTo(HttpStatus.CONFLICT.value()))
                .body("instance", equalTo("/customer/register"));
    }
}
