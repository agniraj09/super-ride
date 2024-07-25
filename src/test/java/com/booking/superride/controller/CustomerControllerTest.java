package com.booking.superride.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
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

    @BeforeAll
    void setUp() {
        RestAssured.port = localServerPort;
    }

    @Test
    void shouldGetAllCustomers() {

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
}
