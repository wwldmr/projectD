package com.projectD.fakedata;

import com.projectD.fakedata.config.FakeDataProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FakeDataProperties.class)
public class FakeDataApplication {
    public static void main(String[] args) {
        SpringApplication.run(FakeDataApplication.class, args);
    }
}
