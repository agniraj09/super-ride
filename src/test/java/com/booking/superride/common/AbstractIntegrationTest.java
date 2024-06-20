package com.booking.superride.common;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.booking.superride.TestSuperRideApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = TestSuperRideApplication.class)
public class AbstractIntegrationTest {

    @LocalServerPort
    protected int localServerPort;
}
