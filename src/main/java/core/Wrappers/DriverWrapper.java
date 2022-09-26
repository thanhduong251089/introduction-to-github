package core.Wrappers;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class DriverWrapper {
    private static Logger log = LogManager.getLogger(DriverWrapper.class);

    private DriverWrapper() {
    }

    private static DriverWrapper instance = new DriverWrapper();

    public static DriverWrapper getInstance() {
        return instance;
    }

    ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    public WebDriver getDriver() {
        return driver.get();
    }

    public void setDriver(WebDriver driverParm) {
        driver.set(driverParm);
    }


    public void closeBrowser() {
        driver.get().quit();
        driver.remove();
    }


    private long pageTimeout = 240;
    private long elementTimeout = 40;
    private String previousWindow;
    private List<String> windows = new ArrayList<>();
    private String firstWindow;

    public void addWindows(String window) {
        if (windows.isEmpty()) {
            windows.add(window);
        }
        if (!windows.isEmpty() && !windows.contains(window)) {
            windows.add(window);
        }
    }

    public void setFirstWindow() {
        firstWindow = this.getWindowHandle();
    }

    public void switchToOtherWindow() {
        this.switchToOtherWindow(firstWindow);
    }

    public void switchToOtherWindow(String currentWindowHandle) {
        try {
            String otherWindow = this.getAllWindowHandles().stream()
                    .filter(windowHandle -> !windowHandle.equals(currentWindowHandle))
                    .collect(Collectors.toList()).get(0);
            this.switchToWindow(otherWindow);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getTitle() {
        return getDriver().getTitle();
    }

    public void handleAlert(boolean accept) {
        try {
            if (accept) {
                switchToAlert().accept();
            } else {
                switchToAlert().dismiss();
            }
        } catch (NoAlertPresentException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean isAlertPresent() {
        try {
            getDriver().switchTo().alert();
            return true;
        } catch (NoAlertPresentException Ex) {
            return false;
        }
    }

    public String getTextPopup() {
        try {
            return this.switchToAlert().getText();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void sendKeysPopup(String text) {
        getDriver().switchTo().alert().sendKeys(text);
    }

    public Object jsExecution(String script, Object... objects) {
        try {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
            return jsExecutor.executeScript(script, objects);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public void SleepInSecond(long second) {
        try {
            Thread.sleep(second * 1000);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void switchToFrame(String attribute) {
        try {
            getDriver().switchTo().frame(attribute);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public void switchToFrame(WebElement element) {
        try {
            getDriver().switchTo().frame(element);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void switchToDefault() {
        try {
            getDriver().switchTo().defaultContent();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public void switchToWindow(String name) {
        try {
            previousWindow = getDriver().getWindowHandle();
            getDriver().switchTo().window(name);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void switchToFirstWindow() {
        try {
            this.switchToWindow(firstWindow);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void switchToLastWindow() {
        try {
            if (windows.isEmpty()) {
                addWindows(getWindowHandle());
            }
            switchToWindow(windows.get(windows.size() - 1));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void switchToNewWindow() {
        try {
            previousWindow = getDriver().getWindowHandle();
            addWindows(previousWindow);
            getDriver().switchTo().newWindow(WindowType.WINDOW);
            addWindows(getDriver().getWindowHandle());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void switchToNewTab() {
        try {
            getDriver().switchTo().newWindow(WindowType.TAB);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public Set<String> getAllWindowHandles() {
        try {
            return getDriver().getWindowHandles();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public int getTotalWindowHandles() {
        try {
            return getDriver().getWindowHandles().size();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void switchToPreviousWindow() {
        try {
            this.switchToWindow(previousWindow);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getWindowHandle() {
        try {
            return getDriver().getWindowHandle();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void reload() {
        reload(false);
    }

    public void reload(boolean forcedReload) {
        try {
            jsExecution(String.format("location.reload(%s);", forcedReload));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public WebDriver.Navigation navigate() {
        try {
            return getDriver().navigate();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void refresh() {
        try {
            this.navigate().refresh();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void back() {
        try {
            this.navigate().back();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void forward() {
        try {
            this.navigate().forward();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void navigateTo(String url) {
        try {
            this.navigate().to(url);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void navigateTo(URL url) {
        try {
            this.navigate().to(url);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void waitForAlertPresent() {
        waitForAlertPresent(elementTimeout);
    }

    public void waitForAlertPresent(long timeOut) {
        try {
            Alert wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOut))
                    .until(ExpectedConditions.alertIsPresent());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Alert switchToAlert() {
        return switchToAlert(elementTimeout);
    }

    public Alert switchToAlert(long timeOut) {
        try {
            waitForAlertPresent(timeOut);
            Alert alert = getDriver().switchTo().alert();
            return alert;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void maximize() {
        try {
            getDriver().manage().window().maximize();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void get(String url) {
        try {
            getDriver().get(url);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void quit() {
        try {
            getDriver().quit();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            getDriver().close();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public void scrollToTop() {
        try {
            jsExecution("window.scrollTo(0, 0);");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void divertBrowserLog() {
        try {
            jsExecution(" window.ProLog = '';");
            jsExecution("console.log = function (){ for(let i = 0 ; i< arguments.length;i++){ window.ProLog += arguments[i];}};");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public String getBrowserLog() {
        try {
            return jsExecution("return window.ProLog;").toString();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Dimension getSize() {
        try {
            return getDriver().manage().window().getSize();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public void zoom(int zoomPercent) {
        try {
            jsExecution(String.format("document.body.style.zoom = %s", zoomPercent));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void takeScreenshot(String destination) {
        try {
            TakesScreenshot scrShot = ((TakesScreenshot) getDriver());
            File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
            File DestFile = new File(destination);
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public byte[] takeScreenshot() {
        try {
            TakesScreenshot scrShot = ((TakesScreenshot) getDriver());
            byte[] SrcFile = scrShot.getScreenshotAs(OutputType.BYTES);
            return SrcFile;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}


