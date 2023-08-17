package General;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GeneralManager {
    private final WebDriver driver;

    public GeneralManager(WebDriver driver) {
        this.driver = driver;
    }

    public void goTo(String url) {
        driver.get(url);
    }

    public void click(WebElement element) {
        element.click();
    }
}
