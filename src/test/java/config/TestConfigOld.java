package config;

import static constants.Constants.BASE_URL;

public class TestConfigOld {

    public String getBaseUrl() {
        return System.getProperty("baseUrl", BASE_URL);
    }
}
