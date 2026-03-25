package com.projectD.fakedata.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fake-data")
public class FakeDataProperties {
    private int usersCount = 50;
    private int simCardsCount = 200;
    private String locale = "ru";

    public int getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public int getSimCardsCount() {
        return simCardsCount;
    }

    public void setSimCardsCount(int simCardsCount) {
        this.simCardsCount = simCardsCount;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
