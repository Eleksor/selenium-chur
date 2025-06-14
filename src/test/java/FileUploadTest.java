import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class FileUploadTest extends BaseTest {
    @Test
    void uploadFileTest() throws InterruptedException {
        String fileName = "webdrivermanager.png";
        String filePath = "src/main/resources/" + fileName;

        // Получаем URL ресурса
        URL url = FileUploadTest.class.getClassLoader().getResource(fileName);

        String absolutePath = null;
        if (url != null) {
            // Получаем абсолютный путь к файлу
            absolutePath = new File(url.getPath()).getAbsolutePath();
            System.out.println("Абсолютный путь к файлу: " + absolutePath);
        } else {
            System.out.println("Ресурс не найден.");
        }

        // Открываем страницу с формой загрузки файла
        getDriver().get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        // Находим элемент <input type="file"> по его атрибуту name
        WebElement fileInput = getDriver().findElement(By.xpath("//input[@name='my-file']"));

        // Загружаем файл, указывая абсолютный путь к файлу
        fileInput.sendKeys(new File(filePath).getAbsolutePath());

        // Далее можно продолжить взаимодействие с элементами на странице или выполнять другие действия
        Thread.sleep(5000);
        WebElement submit = getDriver().findElement(By.xpath("//button[text()='Submit']"));
        submit.click();
        Thread.sleep(5000);

        assertThat(getDriver().getCurrentUrl()).contains(fileName);
    }
}
