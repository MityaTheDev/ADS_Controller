package ADS;

import General.Utils;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;

public class AdsController {
    private String chromedriverPath = "";
    private String debuggerAddress = "";
    WebDriver driver;

    public AdsController() {

    }

    public void startChromedriver(JSONObject json) {
        System.setProperty("webdriver.chrome.driver", json.getJSONObject("data").get("webdriver").toString()); // Set the webdriver returned by the API
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("debuggerAddress", json.getJSONObject("data").getJSONObject("ws").get("selenium")); // Set the chrome debugging interface returned by the API

        driver = new ChromeDriver(options);
    }

    public String startSession(String id, boolean headless) {
        Utils.sleep(1000);

        JSONObject json;

        int i = headless ? 1 : 0;
        try {
            json = JsonReader.readJsonFromUrl("http://local.adspower.net:50325/api/v1/browser/start?user_id=" + id + "&headless=" + i);

            chromedriverPath = json.getJSONObject("data").get("webdriver").toString();
            debuggerAddress = json.getJSONObject("data").getJSONObject("ws").get("selenium").toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Exception caught while starting session: " + e.getMessage();
        }
        return json.get("msg").toString();
    }

    public String stopSession(String id) {
        Utils.sleep(1000);

        JSONObject json;
        try {
            json = JsonReader.readJsonFromUrl("http://local.adspower.net:50325/api/v1/browser/stop?user_id=" + id);
        } catch (IOException e) {
            e.printStackTrace();
            return "Exception caught while stopping session: " + e.getMessage();
        }
        return json.getString("msg");
    }

    public JSONObject checkSessionStatus(String id) {
        Utils.sleep(1000);

        try {
            return JsonReader.readJsonFromUrl("http://local.adspower.net:50325/api/v1/browser/active?user_id=" + id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public String getChromedriverPath() {
        return chromedriverPath;
    }

    public String getDebuggerAddress() {
        return debuggerAddress;
    }
}
