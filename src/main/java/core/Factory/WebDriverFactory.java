package core.Factory;

import core.Utils.Constant;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

public class WebDriverFactory {
    private static WebDriver webDriver;
    private static String browser;

    public static WebDriver getWebDriver(String browserName) {
        return getWebDriver(browserName, false);

    }

    public static WebDriver getWebDriver(String browserName, boolean remote) {
        if (remote) {
            switch (browserName.toLowerCase()) {
                case "chrome":
                    browser = "chrome";
                    return getRemoteChromeDriver();
                case "firefox":
                    browser = "firefox";
                    return getRemoteFirefoxDriver();
                case "edge":
                    browser = "edge";
                    return getRemoteEdgeDriver();
                default:
                    throw new IllegalArgumentException("Match case not found for browser: "
                            + browserName);
            }
        } else {
            switch (browserName.toLowerCase()) {
                case "chrome":
                    browser = "chrome";
                    return getChromeDriver();
                case "firefox":
                    browser = "firefox";
                    return getFirefoxDriver();
                case "edge":
                    browser = "edge";
                    return getEdgeDriver();
                default:
                    throw new IllegalArgumentException("Match case not found for browser: "
                            + browserName);
            }
        }
    }

    public static String getDriverBrowser() {
        return browser;
    }

    private static WebDriver getChromeDriver() {
        WebDriverManager.chromedriver().setup();
        LoggingPreferences preferences = new LoggingPreferences();
        preferences.enable(LogType.PERFORMANCE, Level.ALL);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        options.setCapability(CapabilityType.LOGGING_PREFS, preferences);
        options.setCapability("goog:loggingPrefs", preferences);
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        WebDriverManager.chromedriver().clearResolutionCache().setup();
        webDriver = new ChromeDriver(options);
        return webDriver;
    }

    private static WebDriver getFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        webDriver = new FirefoxDriver();
        return webDriver;
    }

    private static WebDriver getEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        webDriver = new EdgeDriver();
        return webDriver;
    }

    private static WebDriver getRemoteChromeDriver() {
        try {
            LoggingPreferences preferences = new LoggingPreferences();
            preferences.enable(LogType.PERFORMANCE, Level.ALL);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--window-size=1920,1080");
            options.setCapability(CapabilityType.LOGGING_PREFS, preferences);
            options.setCapability("goog:loggingPrefs", preferences);
            options.addArguments("--disable-extensions");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-gpu");
            options.setCapability("browserName", "Chrome");
            DesiredCapabilities caps = new DesiredCapabilities(options);
            caps.setBrowserName("chrome");
            webDriver = new RemoteWebDriver(new URL(Constant.HUB_URL), caps);
            return webDriver;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static WebDriver getRemoteFirefoxDriver() {
        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setBrowserName("firefox");
            webDriver = new RemoteWebDriver(new URL(Constant.HUB_URL), caps);
            return webDriver;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static WebDriver getRemoteEdgeDriver() {
        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setBrowserName("edge");
            webDriver = new RemoteWebDriver(new URL(Constant.HUB_URL), caps);
            return webDriver;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
