package com.booking.superride.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import com.booking.superride.common.AbstractIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.HttpStatus;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaxiControllerTest extends AbstractIntegrationTest {

    @BeforeAll
    void setUp() {
        RestAssured.port = localServerPort;
    }

    @Test
    void testSaveTaxiDetailsWithSingleTaxi() {
        given().contentType(ContentType.JSON)
                .body(
                        """
                        {
                          "make": "Honda",
                          "taxiNumber": "TN 59 DG 5555",
                          "driverName": "Guna",
                          "currentLocation": "A"
                        }
                        """)
                .when()
                .post("/taxi/add-taxi")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("taxiId", greaterThan(0))
                .body("taxiNumber", notNullValue());
    }

    @Test
    void testSaveTaxiDetailsWithInvalidTaxiNumber() {
        given().contentType(ContentType.JSON)
                .body(
                        """
                        {
                          "make": "Honda",
                          "taxiNumber": null,
                          "driverName": "Arul",
                          "currentLocation": "A"
                        }
                        """)
                .when()
                .post("/taxi/add-taxi")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void testSaveTaxiDetailsWithMultipleTaxis() {
        given().contentType(ContentType.JSON)
                .body(
                        """
                        [
                            {
                                "make": "Tata",
                                "taxiNumber": "TN 11 SF 2927",
                                "driverName": "Mohan",
                                "currentLocation": "A"
                            },
                            {
                                "make": "Maruti Suzuki",
                                "taxiNumber": "TN 12 JK 1718",
                                "driverName": "Guru",
                                "currentLocation": "A"
                            }
                        ]
                        """)
                .when()
                .post("/taxi/bulk/add-taxi")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(".", isA(List.class))
                .body(".", hasSize(2));
    }
}
