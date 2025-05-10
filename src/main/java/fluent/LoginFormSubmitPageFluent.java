package fluent;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginFormSubmitPageFluent extends BasePageFluent {
    public LoginFormSubmitPageFluent(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "success")
    public WebElement successAlert;

    public String getSuccessMessage() {
        return successAlert.getText().trim();
    }
}
