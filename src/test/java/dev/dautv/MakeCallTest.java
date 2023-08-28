package dev.dautv;

import dev.dautv.utils.AppUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MakeCallTest {
    private RemoteWebDriver driver;
    private String callTo = "chrome2";

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        System.setProperty("webdriver.chrome.driver", AppUtils.getDriver());

        Map<String, Object> prefs = new HashMap<>();
        //add key and value to map as follows to switch off browser notification
        //Pass the argument 1 to allow and 2 to block
        prefs.put("profile.default_content_setting_values.notifications", 1);
        prefs.put("profile.default_content_setting_values.media_stream_mic", 1);
        prefs.put("profile.default_content_setting_values.media_stream_camera", 1);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--autoplay-policy=no-user-gesture-required");
        options.addArguments("--use-fake-device-for-media-stream");
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @Test
    public void makeVideoCallTest() throws InterruptedException {
        driver.get("https://video-call.stringeextest.com/login");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edUsername")));
        element.sendKeys("dautv");
        driver.findElement(By.id("edPassword")).sendKeys("123456");
        driver.findElement(By.id("btnLogin")).click();
        element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toNumberBtn")));
        element.clear();
        element.sendKeys(callTo);
        // 5s for authentication
        Thread.sleep(3000);
        driver.findElement(By.id("call2Btn")).click();

        boolean exist = false;
        try {
            exist = wait.until((ExpectedCondition<Boolean>) driver -> {
                WebElement containerElement = driver.findElement(By.id("local_videos"));
                List<WebElement> videoElements = containerElement.findElements(By.tagName("video"));
                return !videoElements.isEmpty();
            });
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        Thread.sleep(2000);
        System.out.println("exist: " + exist);
        if (exist) {
            driver.findElement(By.id("call2HangupBtn")).click();
            assert true;
            return;
        }

        assert false;

    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
