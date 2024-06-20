package com.booking.superride.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import com.booking.superride.common.ContainersConfig;
import com.booking.superride.domain.AddTaxiRequest;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(ContainersConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TaxiControllerTest {

    @Test
    void testSaveTaxiDetailsWithSingleTaxi() {
        var request = new AddTaxiRequest("Tata", "TN 59 BH 8191", "Arul", 'A');
        given().contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/taxi/add-taxi")
                .then()
                .statusCode(200)
                .body("taxiId", greaterThan(0))
                .body("taxiNumber", equalTo(request.taxiNumber()));
    }

    @Test
    void testSaveTaxiDetailsWithInvalidTaxiNumber() {
        var request = new AddTaxiRequest("Tata", null, "Arul", 'A');
        given().contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/taxi/add-taxi")
                .then()
                .statusCode(400);
    }

    @Test
    void testSaveTaxiDetailsWithMultipleTaxis() {
        var request = List.of(
                new AddTaxiRequest("Tata", "TN 59 BH 8191", "Arul", 'A'),
                new AddTaxiRequest("Maruti Suzuki", "TN 12 JK 1718", "Guru", 'A'));
        given().contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/taxi/bulk/add-taxi")
                .then()
                .statusCode(200)
                .body(".", isA(List.class))
                .body(".", hasSize(2));
    }
}
