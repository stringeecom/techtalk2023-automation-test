package dev.dautv;

import dev.dautv.utils.AppUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class AuthenticationTest {

    private RemoteWebDriver driver;

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        System.setProperty("webdriver.chrome.driver", AppUtils.getDriver());
        driver = new ChromeDriver();
    }

    @Test
    public void loginTest() throws InterruptedException {
        driver.get("https://video-call.stringeextest.com/login");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edUsername")));
        element.sendKeys("dautv");
        driver.findElement(By.id("edPassword")).sendKeys("123456");
        driver.findElement(By.id("btnLogin")).click();
        element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("call2Btn")));
        assert element != null;
    }

    @Test
    public void registerTest() throws InterruptedException {

        driver.get("https://video-call.stringeextest.com/login");
        driver.findElement(By.id("btnRegister")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edRUserName")));

        String username = RandomStringUtils.random(10, true, true);
        element.sendKeys(username);

        driver.findElement(By.id("edRPassword")).sendKeys("123456");
        driver.findElement(By.id("edRRePassword")).sendKeys("123456");
        driver.findElement(By.cssSelector(".btn.btn-success")).click();

        WebElement btnConfirm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn.btn-primary.bootstrap4-dialog-button.btn-small")));
        btnConfirm.click();

        Thread.sleep(1000);
        wait = new WebDriverWait(driver, 2);
        boolean elementExists = false;
        try {
            elementExists = wait.until((ExpectedCondition<Boolean>) driver -> {
                try {
                    driver.findElement(By.cssSelector(".bootstrap-dialog-title"));
                    return true;
                } catch (NoSuchElementException e) {
                    return false;
                }
            });
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        assert !elementExists;

    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}