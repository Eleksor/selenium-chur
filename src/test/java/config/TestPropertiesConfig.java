package config;

import constants.Constants;
import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:${env}.properties",
        "classpath:default.properties"
})
public interface TestPropertiesConfig extends Config {
    @Key("uiBaseUrl")
    @DefaultValue(Constants.BASE_URL)
    String getUiBaseUrl();

    @Key("apiBaseUrl")
    String getApiBaseUrl();

    @Key("username")
    String getUsername();

    @Key("password")
    String getPassword();

    @Key("selenium.remote.url")
    String getSeleniumRemoteUrl();
}
