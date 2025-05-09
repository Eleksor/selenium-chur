package selenidePom;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class WebFormPage extends BasePage {
    private static final String WEB_FORM_URL = "web-form.html";

    @Step("Get subpage url")
    public String getWebFormExpectedUrl() {
        return WEB_FORM_URL;
    }

    @Step("Get readonly input")
    public SelenideElement getReadonlyInput() {
        return $(By.name("my-readonly"));
    }
}
