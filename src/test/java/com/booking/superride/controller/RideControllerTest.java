package com.booking.superride.controller;

import static com.booking.superride.constants.AppConstants.ALL_TAXIS_ARE_IN_TRIP_ERROR;
import static com.booking.superride.constants.AppConstants.NO_ACTIVE_TAXI_FOUND_ERROR;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import com.booking.superride.common.ContainersConfig;
import com.booking.superride.domain.RideBookingRequest;
import com.booking.superride.domain.RideDetailsResponse;
import com.booking.superride.entity.CustomerDetails;
import com.booking.superride.entity.TaxiDetails;
import com.booking.superride.repository.CustomerRepository;
import com.booking.superride.repository.RideRepository;
import com.booking.superride.repository.TaxiRepository;
import io.restassured.http.ContentType;
import java.time.LocalDateTime;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(ContainersConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RideControllerTest {

    private TaxiDetails taxi;
    private CustomerDetails customer;

    @Autowired
    TaxiRepository taxiRepository;

    @Autowired
    RideRepository rideRepository;

    @Autowired
    CustomerRepository customerRepository;

    @BeforeAll
    void setup() {
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
        var request = new RideBookingRequest(customer.getCustomerId(), 'A', 'B', LocalDateTime.now());
        var response = given().contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/ride/book-ride")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(RideDetailsResponse.class);
        assertTrue(response.rideDetails().rideId() > 0);
        assertEquals(response.rideDetails().taxiId(), taxi.getTaxiId());
        assertEquals(response.rideDetails().customerId(), customer.getCustomerId());
    }

    @Test
    @Order(2)
    void testBookRideWithNoAvailableTaxi() {
        var request = new RideBookingRequest(customer.getCustomerId(), 'A', 'B', LocalDateTime.now());
        given().contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/ride/book-ride")
                .then()
                .statusCode(404)
                .body("detail", equalTo(ALL_TAXIS_ARE_IN_TRIP_ERROR));
    }

    @Test
    @Order(3)
    void testBookRideWithNoActiveTaxi() {
        // Change taxi status to inactive first
        taxiRepository.save(taxi.setStatus("Inactive"));

        var request = new RideBookingRequest(customer.getCustomerId(), 'A', 'B', LocalDateTime.now());
        given().contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/ride/book-ride")
                .then()
                .statusCode(404)
                .body("detail", equalTo(NO_ACTIVE_TAXI_FOUND_ERROR));
    }

    private void deleteAllData() {
        rideRepository.deleteAll();
        customerRepository.deleteAll();
        taxiRepository.deleteAll();
    }

    @AfterAll
    void cleanUp() {
        deleteAllData();
    }
}
