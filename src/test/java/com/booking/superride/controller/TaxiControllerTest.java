package com.booking.superride.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

import com.booking.superride.common.AbstractIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

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
                .post("/taxi/register")
                .then()
                .statusCode(HttpStatus.OK.value())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body("taxiId", greaterThan(0))
                .body("taxiNumber", is("TN 59 DG 5555"));
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
                .post("/taxi/register")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .body("detail", equalTo("Invalid request content."))
                .body("type", equalTo("about:blank"))
                .body("title", equalTo("Bad Request"))
                .body("status", equalTo(400))
                .body("instance", equalTo("/taxi/register"));
        ;
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
                .post("/taxi/bulk/register")
                .then()
                .statusCode(HttpStatus.OK.value())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(".", isA(List.class))
                .body(".", hasSize(2));
    }
}
