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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class RideControllerTest extends AbstractIntegrationTest {

    @Autowired
    TaxiRepository taxiRepository;

    @Autowired
    RideRepository rideRepository;

    @Autowired
    CustomerRepository customerRepository;

    private CustomerDetails customer;

    @BeforeAll
    void setup() {
        RestAssured.port = localServerPort;
        initializeTestData();
    }

    @BeforeEach
    void prepareDataSetup() {
        // Delete all data
        deleteTaxiAndRideData();
    }

    private void initializeTestData() {
        this.customer = customerRepository.save(
                new CustomerDetails().setCustomerName("Agniraj").setMobileNumber("9898787898"));
    }

    @Test
    void testBookRide() {
        // Insert a new taxi with active status
        var taxi = taxiRepository.save(new TaxiDetails()
                .setMake("Tata")
                .setDriverName("Guna")
                .setTaxiNumber("TN 44 BH 3245")
                .setCurrentLocation('A')
                .setStatus("Active"));

        // Book a ride - ride should be booked
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
    void testBookRideWithNoAvailableTaxi() {
        var pickupTime = LocalDateTime.now();

        // Insert a new taxi with active status
        taxiRepository.save(new TaxiDetails()
                .setMake("Tata")
                .setDriverName("Guna")
                .setTaxiNumber("TN 44 BH 3245")
                .setCurrentLocation('A')
                .setStatus("Active"));

        // Book a ride - ride will be booked
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
                                .replace("#pickUpTime#", pickupTime.toString()))
                .when()
                .post("/ride/book");

        // Try to book a ride again - ride will not be booked as taxi would be on-ride already
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
                                .replace("#pickUpTime#", pickupTime.toString()))
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
    void testBookRideWithNoActiveTaxi() {
        // Insert a taxi with inactive status
        taxiRepository.save(new TaxiDetails()
                .setMake("Tata")
                .setDriverName("Guna")
                .setTaxiNumber("TN 44 BH 3245")
                .setCurrentLocation('A')
                .setStatus("Inactive"));

        // Try to book a ride now
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

    private void deleteTaxiAndRideData() {
        rideRepository.deleteAll();
        taxiRepository.deleteAll();
    }
}
