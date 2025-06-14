package config;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestConfig {
    String env;
    String username;
    String password;
    Properties properties;

    public TestConfig() {
        env = System.getProperty("env", "default");
        properties = getPropertiesByEnv(env);
    }

    public String getBaseUrl() {
        String uiBaseUrl = properties.getProperty("uiBaseUrl");
        uiBaseUrl = System.getProperty("uiBaseUrl", uiBaseUrl);
        assertNotNull(uiBaseUrl, String.format("BaseUrl is not found in %s.properties", env));
        System.out.println("Base URL: " + uiBaseUrl);
        return uiBaseUrl;
    }

    public String getUsername() {
        return getFieldByName("username");
    }

    public String getPassword() {
        return getFieldByName("password");
    }

    public String getFieldByName(String fieldName) {
        String field = properties.getProperty(fieldName);
        if (field == null || field.isEmpty()) {
            field = System.getProperty(fieldName, field);
        }
        assertNotNull(field, String.format("%s is not in %s.properties and not set by system properties", fieldName, env));
        //System.out.printf("%s: %s%n", fieldName, field);
        return field;
    }

    private Properties getPropertiesByEnv(String env) {
        Properties testProperties = new Properties();
        try {
            testProperties.load(getClass().getClassLoader().getResourceAsStream(env + ".properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw  new RuntimeException(String.format("Cannot open %s.properties", env));
        }
        return testProperties;
    }
}
