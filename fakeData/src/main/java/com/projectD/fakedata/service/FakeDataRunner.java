package com.projectD.fakedata.service;

import com.projectD.fakedata.config.FakeDataProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FakeDataRunner implements CommandLineRunner {
    private final FakeDataSeeder fakeDataSeeder;
    private final FakeDataProperties properties;

    public FakeDataRunner(FakeDataSeeder fakeDataSeeder, FakeDataProperties properties) {
        this.fakeDataSeeder = fakeDataSeeder;
        this.properties = properties;
    }

    @Override
    public void run(String... args) {
        fakeDataSeeder.seed();
        System.out.printf(
                "Fake data generated: users=%d, simCards=%d%n",
                properties.getUsersCount(),
                properties.getSimCardsCount()
        );
    }
}
