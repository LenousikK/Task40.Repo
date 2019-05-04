import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class LoginToTutBy {
    private static final String URL = "https://www.tut.by/";
    private static final String LOGIN_TITLE = "Белорусский портал TUT.BY. Новости Беларуси и мира";
    private static final By LOGIN_MENU_ITEM = By.xpath("//a[@class = 'enter']");
    private static final By INPUT_LOGIN_USERNAME = By.xpath("//input[@name = 'login']");
    private static final String LOGIN_USERNAME = "seleniumtests@tut.by";
    private static final By INPUT_LOGIN_PASSWORD = By.xpath("//input[@name = 'password']");
    private static final String LOGIN_PASSWORD = "123456789zxcvbn";
    private static final By BUTTON_LOGIN_SUBMIT = By.xpath("//input[@class = 'button auth__enter']");
    private static final By LABEL_LOGGED_USER_NAME = By.xpath("//span[@class = 'uname']");
    private WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    public void openBrowser() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(URL);
        wait = new WebDriverWait(driver, 30);
    }

    @AfterEach
    public void closeBrowser() {
        driver.quit();
        driver = null;
    }

    @Test
    public void loginSuccessful() throws InterruptedException {
        wait.until(titleIs(LOGIN_TITLE));
        //Thread.sleep is unconditional and also explicit type of water
        Thread.sleep(2000);
        driver.findElement(LOGIN_MENU_ITEM).click();
        driver.findElement(INPUT_LOGIN_USERNAME).sendKeys(LOGIN_USERNAME);
        driver.findElement(INPUT_LOGIN_PASSWORD).sendKeys(LOGIN_PASSWORD);
        driver.findElement(BUTTON_LOGIN_SUBMIT).click();
        Wait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);
        fluentWait.until(ExpectedConditions.presenceOfElementLocated(LABEL_LOGGED_USER_NAME));
        assertEquals("Selenium Test", driver.findElement(LABEL_LOGGED_USER_NAME).getText());
    }
}