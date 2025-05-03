package pom;

import org.openqa.selenium.WebDriver;

public class NavigationPage extends BasePage {
    private static final String NAVIGATION_PAGE_URL = "navigation1.html";

    public NavigationPage(WebDriver driver) {
        super(driver);
    }

    public String getUrl() {
        return NAVIGATION_PAGE_URL;
    }
}
