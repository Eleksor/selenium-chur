package selenidePom;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import selenidePom.WebFormPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class HomePage extends BasePage {

    protected static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";

    @Step("Open homepage")
    public void open() {
        Selenide.open(BASE_URL, HomePage.class);
    }

    @Step("Open Web form page")
    public WebFormPage openWebFormPage() {
        $(By.linkText("Web form")).click();
        return page(WebFormPage.class);
    }
}
