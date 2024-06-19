package com.booking.superride.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import com.booking.superride.common.ContainersConfig;
import com.booking.superride.domain.CustomerDetailsDTO;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import(ContainersConfig.class)
public class CustomerControllerTest {
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
