import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class TestIFrame {
    private static final String URL = "https://the-internet.herokuapp.com/iframe";
    private static final String TITLE = "The Internet";
    private static final By ELEMENT_IFRAME = By.cssSelector("iframe[id = 'mce_0_ifr']");
    private static final By TEXT_AREA = By.cssSelector("body#tinymce");
    private static final String TEXT_TO_ENTER_HELLO = "Hello ";
    private static final By BUTTON_B = By.cssSelector("div#mceu_3 > button");
    private static final String TEXT_TO_ENTER_WORLD = "world";
    private static final String TEXT_TO_ENTER_POINT = "!";
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
    public void textIsEnteredToIFrame() {
        wait.until(titleIs(TITLE));
        WebElement iframe = driver.findElement(ELEMENT_IFRAME);
        driver.switchTo().frame(iframe);
        driver.findElement(TEXT_AREA).clear();
        driver.findElement(TEXT_AREA).sendKeys(TEXT_TO_ENTER_HELLO);
        driver.switchTo().defaultContent();
        driver.findElement(BUTTON_B).click();
        driver.switchTo().frame(driver.findElement(ELEMENT_IFRAME));
        driver.findElement(TEXT_AREA).sendKeys(TEXT_TO_ENTER_WORLD);
        driver.switchTo().defaultContent();
        driver.findElement(BUTTON_B).click();
        driver.switchTo().frame(driver.findElement(ELEMENT_IFRAME));
        driver.findElement(TEXT_AREA).sendKeys(TEXT_TO_ENTER_POINT);
        String enteredText = driver.findElement(TEXT_AREA).getText();
        String filteredEnteredText = "";
        for (char character : enteredText.toCharArray()) {
            if (!((String.format("\\u%04x", (int) character)).equals("\\ufeff"))) {
                filteredEnteredText = filteredEnteredText + character;
            }
        }
        assertEquals("Hello world!", filteredEnteredText);
    }
}