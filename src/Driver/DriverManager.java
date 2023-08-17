package Driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {
    WebDriver driver;
    ChromeOptions options;

    public DriverManager() {
        options = new ChromeOptions();
    }

    public DriverManager(String chromedriverPath, String debuggerAddress) {
        System.setProperty("webdriver.chrome.driver", chromedriverPath);
        options = new ChromeOptions();
        options.setExperimentalOption("debuggerAddress", debuggerAddress);
        driver = new ChromeDriver(options);
    }

    public void setDebuggerAddress(String debuggerAddress) {
        options.setExperimentalOption("debuggerAddress", debuggerAddress);
    }

    public void startDriver() {
        driver = new ChromeDriver(options);
    }

    public void closeDriver() {
        driver.quit();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void goTo(String url) {
        driver.get(url);
    }

    public void click(WebElement element) {
        element.click();
    }
}
