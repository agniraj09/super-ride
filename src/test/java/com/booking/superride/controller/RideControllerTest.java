package com.booking.superride.controller;

import static com.booking.superride.constants.AppConstants.ALL_TAXIS_ARE_IN_TRIP_ERROR;
import static com.booking.superride.constants.AppConstants.NO_ACTIVE_TAXI_FOUND_ERROR;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.booking.superride.common.AbstractIntegrationTest;
import com.booking.superride.domain.RideDetailsResponse;
import com.booking.superride.entity.CustomerDetails;
import com.booking.superride.entity.TaxiDetails;
import com.booking.superride.repository.CustomerRepository;
import com.booking.superride.repository.RideRepository;
import com.booking.superride.repository.TaxiRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RideControllerTest extends AbstractIntegrationTest {

    @Autowired
    TaxiRepository taxiRepository;

    @Autowired
    RideRepository rideRepository;

    @Autowired
    CustomerRepository customerRepository;

    private TaxiDetails taxi;
    private CustomerDetails customer;

    @BeforeAll
    void setup() {
        RestAssured.port = localServerPort;
        deleteAllData();
        this.customer = customerRepository.save(new CustomerDetails().setCustomerName("Agniraj"));
        this.taxi = taxiRepository.save(new TaxiDetails()
                .setMake("Tata")
                .setDriverName("Arul")
                .setTaxiNumber("TN 59 BH 8191")
                .setCurrentLocation('A')
                .setStatus("Active"));
    }

    @Test
    @Order(1)
    void testBookRide() {
        var response = given().contentType(ContentType.JSON)
                .body(
                        """
                        {
                          "customerId": #customerId#,
                          "pickupPoint": "A",
                          "dropPoint": "B",
                          "pickupTime": "#pickUpTime#"
                        }
                        """
                                .replace("#customerId#", String.valueOf(customer.getCustomerId()))
                                .replace("#pickUpTime#", LocalDateTime.now().toString()))
                .when()
                .post("/ride/book")
                .then()
                .statusCode(HttpStatus.OK.value())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .as(RideDetailsResponse.class);
        assertThat(response.rideDetails().rideId()).isGreaterThan(0);
        assertThat(response.rideDetails().taxiId()).isEqualTo(taxi.getTaxiId());
        assertThat(response.rideDetails().customerId()).isEqualTo(customer.getCustomerId());
    }

    @Test
    @Order(2)
    void testBookRideWithNoAvailableTaxi() {
        given().contentType(ContentType.JSON)
                .body(
                        """
                        {
                          "customerId": #customerId#,
                          "pickupPoint": "A",
                          "dropPoint": "B",
                          "pickupTime": "#pickUpTime#"
                        }
                        """
                                .replace("#customerId#", String.valueOf(customer.getCustomerId()))
                                .replace("#pickUpTime#", LocalDateTime.now().toString()))
                .when()
                .post("/ride/book")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .body("detail", equalTo(ALL_TAXIS_ARE_IN_TRIP_ERROR))
                .body("type", equalTo("about:blank"))
                .body("title", equalTo("Taxi not available"))
                .body("status", equalTo(404))
                .body("instance", equalTo("/ride/book"));
    }

    @Test
    @Order(3)
    void testBookRideWithNoActiveTaxi() {
        // Change taxi status to inactive first
        taxiRepository.save(taxi.setStatus("Inactive"));

        given().contentType(ContentType.JSON)
                .body(
                        """
                        {
                          "customerId": #customerId#,
                          "pickupPoint": "A",
                          "dropPoint": "B",
                          "pickupTime": "#pickUpTime#"
                        }
                        """
                                .replace("#customerId#", String.valueOf(customer.getCustomerId()))
                                .replace("#pickUpTime#", LocalDateTime.now().toString()))
                .when()
                .post("/ride/book")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .body("detail", equalTo(NO_ACTIVE_TAXI_FOUND_ERROR))
                .body("type", equalTo("about:blank"))
                .body("title", equalTo("Taxi not available"))
                .body("status", equalTo(404))
                .body("instance", equalTo("/ride/book"));
    }

    private void deleteAllData() {
        rideRepository.deleteAll();
        customerRepository.deleteAll();
        taxiRepository.deleteAll();
    }
}
