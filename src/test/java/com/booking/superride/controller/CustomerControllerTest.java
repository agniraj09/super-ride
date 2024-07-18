package com.booking.superride.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import com.booking.superride.common.AbstractIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.HttpStatus;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
                            "customerName": "Surya"
                        }
                        """)
                .when()
                .post("/customer/register")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("customerName", notNullValue())
                .body("customerId", greaterThan(0));
    }
}
