import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
public class TestConfirmAlert {
    private static final String URL = "https://the-internet.herokuapp.com/javascript_alerts";
    private static final String TITLE = "The Internet";
    private static final By BUTTON_OPEN_ALERT = By.cssSelector("button[onclick= 'jsConfirm()']");
    private static final By RESULT_MESSAGE = By.cssSelector("p#result");
    private WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    public void openBrowser() {
        driver = new ChromeDriver();
        driver.get(URL);
        wait = new WebDriverWait(driver, 30);
    }

    @AfterEach
    public void closeBrowser() {
        driver.quit();
        driver = null;
    }

    @Test
    public void LoginSuccessful() {
        wait.until(titleIs(TITLE));
        driver.findElement(BUTTON_OPEN_ALERT).click();
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
            WebElement resultMessage = driver.findElement(RESULT_MESSAGE);
            assertEquals("You clicked: Ok", resultMessage.getText());
        } catch (NoAlertPresentException e) {
            e.printStackTrace();
        }
    }
}