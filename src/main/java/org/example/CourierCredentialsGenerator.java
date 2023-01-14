package org.example;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierCredentialsGenerator {
    public static CourierCredentials getRandom() {
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);
        return new CourierCredentials(login, password);
    }
}
