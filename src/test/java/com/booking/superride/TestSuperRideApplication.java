package com.booking.superride;

import com.booking.superride.common.ContainersConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration(proxyBeanMethods = false)
@Import(ContainersConfig.class)
public class TestSuperRideApplication {

    public static void main(String[] args) {
        SpringApplication.from(SuperRideApplication::main)
                .with(TestSuperRideApplication.class)
                .run(args);
    }
}
