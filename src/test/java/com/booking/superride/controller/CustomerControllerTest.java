package com.booking.superride.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import com.booking.superride.common.AbstractIntegrationTest;
import com.booking.superride.domain.CustomerDetailsDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerControllerTest extends AbstractIntegrationTest {

    @BeforeAll
    void setUp() {
        RestAssured.port = localServerPort;
    }

    @Test
    void shouldGetAllCustomers() {

        given().contentType(ContentType.JSON)
                .body(new CustomerDetailsDTO("Agniraj"))
                .when()
                .post("/customer/add-customer")
                .then()
                .statusCode(200)
                .body("customerName", equalTo("Agniraj"))
                .body("customerId", greaterThan(0));
    }
}
