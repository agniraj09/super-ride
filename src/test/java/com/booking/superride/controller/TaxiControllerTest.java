package com.booking.superride.controller;

import com.booking.superride.common.ContainersConfig;
import com.booking.superride.domain.AddTaxiRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(ContainersConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TaxiControllerTest {

    @Test
    void testSaveTaxiDetailsWithSingleTaxi() {
        var request = new AddTaxiRequest("Tata", "TN 59 BH 8191", "Arul", 'A');
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/taxi/add-taxi")
                .then()
                .statusCode(200)
                .body("taxiId", Matchers.greaterThan(0))
                .body("taxiNumber", Matchers.equalTo(request.taxiNumber()));
    }
}
