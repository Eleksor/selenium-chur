package chapters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import base.BaseTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ChaptersTest extends BaseTest {

    static int parametrizedCount = 1;

    @BeforeEach
    void setup2() {
        getDriver().get(baseUrl);
    }

    @Test
    void chapter3Test() {
        String chapterContent = "//div[@class='card-body']/h5[contains(@class, 'card-title') and  " +
                "text() = 'Chapter 3. WebDriver Fundamentals']/../a";
        Map<String, String> elementUrlPair = new HashMap<>();
        List<WebElement> list = getDriver().findElements(By.xpath(chapterContent));

        for (WebElement webElement : list) {
            elementUrlPair.put(webElement.getDomAttribute("href"), webElement.getText());
        }

        for (Map.Entry<String, String> entry : elementUrlPair.entrySet()) {
            getDriver().findElement(By.xpath("//a[text() = '" + entry.getValue() + "']")).click();

            assertEquals(baseUrl + entry.getKey(), getDriver().getCurrentUrl());
            if (entry.getValue().equals("Navigation")) {
                assertEquals(entry.getValue() + " example",
                        getDriver().findElement(By.xpath("//h1[@class='display-6']")).getText());
            } else if (entry.getValue().equals("Draw in canvas")) {
                assertEquals("Drawing in canvas", getDriver().findElement(By.xpath("//h1[@class='display-6']")).getText());
            } else {
                assertEquals(entry.getValue(), getDriver().findElement(By.xpath("//h1[@class='display-6']")).getText());
            }
            getDriver().navigate().back();
        }
    }

    @ParameterizedTest
    @CsvSource(value = {
            "long-page.html,Long page,This is a long page",
            "infinite-scroll.html,Infinite scroll,Infinite scroll",
            "shadow-dom.html,Shadow DOM,Shadow DOM",
            "cookies.html,Cookies,Cookies",
            "frames.html,Frames,Frames",
            "iframes.html,IFrames,IFrame",
            "dialog-boxes.html,Dialog boxes,Dialog boxes",
            "web-storage.html,Web storage,Web storage"},
            ignoreLeadingAndTrailingWhitespace = false)
    void chapter4Test(String urlButt, String nameButt, String nameHead) {
        String nextBtnLocator = "//div[@class='card-body']/h5[contains(@class, 'card-title') " +
                "and text() = 'Chapter 4. Browser-Agnostic Features']/../a[" + parametrizedCount + "]";
        parametrizedCount++;

        WebElement currentElement = getDriver().findElement(By.xpath(nextBtnLocator));
        String actualBtnName = currentElement.getText();

        getDriver().findElement(By.xpath(nextBtnLocator)).click();
        String actualUrl = getDriver().getCurrentUrl();
        String headerLocator = "//h1[@class='display-6']";

        try {
            String actualHead = getDriver().findElement(By.xpath(headerLocator)).getText();
            assertEquals(nameHead, actualHead);
        } catch (NoSuchElementException e) {
            getDriver().switchTo().frame(getDriver().findElement(By.name("frame-header")));
            String actualHead = getDriver().findElement(By.xpath(headerLocator)).getText();
            assertEquals(nameHead, actualHead);
        }

        assertEquals(baseUrl + urlButt, actualUrl);
        assertEquals(nameButt, actualBtnName);
    }

    @Test
    void longPageTest() {
        String nextBtnLocator = "//div[@class='card-body']/h5[contains(@class, 'card-title') " +
                "and text() = 'Chapter 4. Browser-Agnostic Features']/../a[1]";
        getDriver().findElement(By.xpath(nextBtnLocator)).click();
        this.action
                .sendKeys(Keys.SPACE)
                .sendKeys(Keys.SPACE)
                .sendKeys(Keys.SPACE)
                .perform();

        this.wait2.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//footer")));

        assertTrue(getDriver().findElement(By.xpath("//footer")).isDisplayed());
    }

    @Test
    void infinitePageTest() {
        String nextBtnLocator = "//div[@class='card-body']/h5[contains(@class, 'card-title') " +
                "and text() = 'Chapter 4. Browser-Agnostic Features']/../a[2]";
        getDriver().findElement(By.xpath(nextBtnLocator)).click();
        action
                .sendKeys(Keys.SPACE)
                .sendKeys(Keys.SPACE)
                .sendKeys(Keys.SPACE)
                .perform();

        //waitFluent.until(ExpectedConditions.presenceOfElementLocated(By.className("text-muted")));

        assertThrows(ElementClickInterceptedException.class, () -> getDriver().findElement(By.className("text-muted")).click());
    }

    @Test
    void shadowDOMPageTest() {
        String nextBtnLocator = "//div[@class='card-body']/h5[contains(@class, 'card-title') " +
                "and text() = 'Chapter 4. Browser-Agnostic Features']/../a[3]";
        getDriver().findElement(By.xpath(nextBtnLocator)).click();

        assertThrows(NoSuchElementException.class, () -> getDriver().findElement(By.cssSelector("p")));

        WebElement shadowContent = getDriver().findElement(By.id("content"));
        SearchContext shadowRoot = shadowContent.getShadowRoot();
        WebElement textElementFromShadow = shadowRoot.findElement(By.cssSelector("p"));

        assertEquals("Hello Shadow DOM", textElementFromShadow.getText());
    }

    @Test
    void defaultCookiePageTest() {
        String expectedCookiesList = "username=John Doe\ndate=10/07/2018";
        String nextBtnLocator = "//div[@class='card-body']/h5[contains(@class, 'card-title') " +
                "and text() = 'Chapter 4. Browser-Agnostic Features']/../a[4]";
        getDriver().findElement(By.xpath(nextBtnLocator)).click();
        getDriver().findElement(By.id("refresh-cookies")).click();
        String cookiesList = getDriver().findElement(By.id("cookies-list")).getText();

        assertEquals(expectedCookiesList, cookiesList);
    }

    @Test
    void addedCookiePageTest() {
        String expectedCookiesList = "username=John Doe\ndate=10/07/2018\npsina=sutulaya";
        String nextBtnLocator = "//div[@class='card-body']/h5[contains(@class, 'card-title') " +
                "and text() = 'Chapter 4. Browser-Agnostic Features']/../a[4]";

        getDriver().findElement(By.xpath(nextBtnLocator)).click();
        getDriver().manage().addCookie(new Cookie("psina", "sutulaya"));
        getDriver().findElement(By.id("refresh-cookies")).click();
        String cookiesList = getDriver().findElement(By.id("cookies-list")).getText();

        assertEquals(expectedCookiesList, cookiesList);
    }

    @Test
    void deleteCookiePageTest() {
        String expectedCookiesList = "";
        String nextBtnLocator = "//div[@class='card-body']/h5[contains(@class, 'card-title') " +
                "and text() = 'Chapter 4. Browser-Agnostic Features']/../a[4]";

        getDriver().findElement(By.xpath(nextBtnLocator)).click();
        getDriver().manage().deleteAllCookies();
        getDriver().findElement(By.id("refresh-cookies")).click();
        String cookiesList = getDriver().findElement(By.id("cookies-list")).getText();

        assertEquals(expectedCookiesList, cookiesList);
    }

    @Test
    void framePageTest() {
        String expectedParagraph = "Nibh netus aliquet nam mattis vestibulum interdum euismod suspendisse rutrum ullamcorper venenatis, vehicula curabitur viverra habitasse leo vulputate eget malesuada ad tincidunt, donec posuere bibendum varius sollicitudin aenean accumsan lacinia consequat mi. Litora vel turpis luctus nec quisque hendrerit morbi maecenas, netus conubia feugiat orci fringilla suspendisse iaculis erat, semper posuere integer et senectus justo curabitur. Fringilla ut taciti mollis morbi blandit dictumst odio ultrices, parturient feugiat vel porttitor convallis eros pellentesque pretium risus, varius accumsan litora habitasse cursus pharetra commodo.";
        String nextBtnLocator = "//div[@class='card-body']/h5[contains(@class, 'card-title') " +
                "and text() = 'Chapter 4. Browser-Agnostic Features']/../a[5]";

        getDriver().findElement(By.xpath(nextBtnLocator)).click();
        getDriver().switchTo().frame("frame-body");
        String actualParagraph = getDriver().findElement(By.xpath("//p[2]")).getText();

        assertEquals(expectedParagraph, actualParagraph);
    }

    @Test
    void frameNegativePageTest() {
        String nextBtnLocator = "//div[@class='card-body']/h5[contains(@class, 'card-title') " +
                "and text() = 'Chapter 4. Browser-Agnostic Features']/../a[5]";

        getDriver().findElement(By.xpath(nextBtnLocator)).click();

        assertThrows(NoSuchElementException.class, () -> getDriver().findElement(By.xpath("//p[2]")));
    }

    @Test
    void iFramePageTest() {
        String expectedParagraph = "Varius bibendum volutpat porttitor habitant quis quam vehicula cras, " +
                "facilisi natoque ornare viverra vestibulum aliquet aliquam. Libero class porttitor hac iaculis " +
                "mauris ligula mattis turpis, tincidunt leo vivamus velit massa praesent ante, torquent eleifend " +
                "ullamcorper scelerisque nec dictum imperdiet. Tincidunt purus nostra felis fusce varius at " +
                "pellentesque, sociosqu accumsan phasellus interdum posuere eros, pharetra velit diam quisque " +
                "porttitor scelerisque.";
        String nextBtnLocator = "//div[@class='card-body']/h5[contains(@class, 'card-title') " +
                "and text() = 'Chapter 4. Browser-Agnostic Features']/../a[6]";

        getDriver().findElement(By.xpath(nextBtnLocator)).click();
        getDriver().switchTo().frame("my-iframe");
        String actualParagraph = getDriver().findElement(By.xpath("//p[3]")).getText();

        assertEquals(expectedParagraph, actualParagraph);
    }

    @Test
    void alertPageTest() {
        String expectedTypedText = "A corn is a queen of fields!";
        String nextBtnLocator = "//div[@class='card-body']/h5[contains(@class, 'card-title') " +
                "and text() = 'Chapter 4. Browser-Agnostic Features']/../a[7]";

        getDriver().findElement(By.xpath(nextBtnLocator)).click();
        getDriver().findElement(By.id("my-alert")).click();
        getDriver().switchTo().alert().accept();

        getDriver().findElement(By.id("my-confirm")).click();
        getDriver().switchTo().alert().accept();
        getDriver().findElement(By.id("my-confirm")).click();
        getDriver().switchTo().alert().dismiss();

        getDriver().findElement(By.id("my-prompt")).click();
        getDriver().switchTo().alert().sendKeys(expectedTypedText);
        getDriver().switchTo().alert().accept();
        String actualDisplayedText = getDriver().findElement(By.id("prompt-text")).getText();

        getDriver().findElement(By.id("my-modal")).click();
        getDriver().findElement(By.xpath("//button[@class='btn btn-primary model-button']")).click();

        assertEquals("You typed: " + expectedTypedText, actualDisplayedText);
        assertEquals("You chose: Save changes", getDriver().findElement(By.id("modal-text")).getText());
    }

}
