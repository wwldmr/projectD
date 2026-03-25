package com.projectD.multisimbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration.class
})
public class MultiSimBoxApplication {
    public static void main(String[] args) {
        SpringApplication.run(MultiSimBoxApplication.class, args);
    }
}
