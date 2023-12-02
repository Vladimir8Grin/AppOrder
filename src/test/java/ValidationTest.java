import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidationTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {

        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void afterEach() {
        driver.quit();
        driver = null;
    }

    @Test
    public void nameFieldIsEmpty() {
        //driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Владимир Груненко");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79532672323");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim());
    }

    @Test
    public void phoneFieldIsEmpty() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Владимир Груненко");
        //driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79532672323");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }

    @Test
    public void checkBoxIsNotPressed() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Владимир Груненко");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79532672323");
        //driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertTrue(driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid")).isDisplayed());
    }

    @Test
    public void invalidValueInTheNameField() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Vladimir");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79532672323");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim());
    }

    @Test
    public void doubleSurnameTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Михаил Иванов-Петров");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79532672323");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualText = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actualText);
    }

    @Test
    public void invalidValueInThePhoneField() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Владимир Груненко");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("Владимир Vladimir");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }

    @Test
    public void invalidValueInThePhoneField_12_Symbol() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Владимир Груненко");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("123456789123");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }

    @Test
    public void invalidValueInThePhoneField_10_Symbol() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Владимир Груненко");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("1234567891");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }

    @Test
    public void invalidValueInThePhoneFieldWithoutPlus() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Владимир Груненко");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("89999999999");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }
}

