package core.Wrappers;

import core.Utils.Constant;
import core.Utils.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;


public class ElementWrapper {
    private static Logger log = LogManager.getLogger(ElementWrapper.class);
    private final int _eleTimeout = Constant.WAIT_TIMEOUT;// in second
    private By _by;
    private String _xpath;
    private WebDriver webDriver = DriverWrapper.getInstance().getDriver();
    private Actions action = new Actions(webDriver);

    public ElementWrapper(By by) {
        this._by = by;
    }

    public ElementWrapper(String locator) {
        this._xpath = locator;
        this._by = By.xpath(this._xpath);
    }

    public ElementWrapper getDynamicLocator(Object... parameter) {
        this._by = By.xpath(String.format(this._xpath, parameter));
        return this;
    }

    public ElementWrapper getDynamicLocatorWithSpace(Object... parameter) {
        this._by = By.xpath(String.format(this._xpath, parameter).replace(" ", " "));
        return this;
    }

    public ElementWrapper(String locator, String parameter1, String parameter2) {
        this._xpath = locator;
        this._by = By.xpath(String.format(locator, parameter1, parameter2));
    }

    public WebElement getElement() {
        return webDriver.findElement(this._by);
    }

    public String getElementXpath() {
        return this._xpath;
    }

    public List<WebElement> getElementList() {
        try {
            return webDriver.findElements(this._by);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<String> getAllTexts() {
        try {
            List<WebElement> elements = this.getElementList();
            return elements.stream().map(x -> x.getAttribute("innerText").trim())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void waitForDisplay() {
        this.waitForDisplay(this._eleTimeout);
    }

    public void waitForDisplay(long timeOut) {
        try {
            WebElement firstResult = new WebDriverWait(this.webDriver, Duration.ofSeconds(timeOut))
                    .until(ExpectedConditions.visibilityOfElementLocated(this._by));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean waitForNotPresence(long timeOut) {
        try {
            boolean firstResult = new WebDriverWait(this.webDriver, Duration.ofSeconds(timeOut))
                    .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(this._xpath)));
            return firstResult;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void waitForPresence() {
        this.waitForPresence(this._eleTimeout);
    }

    public void waitForPresence(long timeOut) {
        try {
            WebElement firstResult = new WebDriverWait(this.webDriver, Duration.ofSeconds(timeOut))
                    .until(ExpectedConditions.presenceOfElementLocated(this._by));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void waitForClickable() {
        this.waitForClickable(this._eleTimeout);
    }

    public void waitForClickable(long timeOut) {
        try {
            WebElement firstResult = new WebDriverWait(this.webDriver, Duration.ofSeconds(timeOut))
                    .until(ExpectedConditions.elementToBeClickable(this._by));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void waitUntilDisappear() {
        this.waitUntilDisappear(this._eleTimeout);
    }

    public void waitUntilDisappear(long timeOut) {
        try {
            boolean firstResult = new WebDriverWait(this.webDriver, Duration.ofSeconds(timeOut))
                    .until(ExpectedConditions.invisibilityOf(this.getElement()));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean isClickable() {
        return this.isClickable(4);
    }


    public boolean isClickable(long timeOut) {
        try {
            WebElement firstResult = new WebDriverWait(this.webDriver, Duration.ofSeconds(timeOut))
                    .until(ExpectedConditions.elementToBeClickable(this._by));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void waitForStaleness() {
        this.waitForStaleness(this._eleTimeout);
    }

    public void waitForStaleness(long timeOut) {
        try {
            boolean firstResult = new WebDriverWait(this.webDriver, Duration.ofSeconds(timeOut))
                    .until(ExpectedConditions.stalenessOf(this.getElement()));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void waitForSelected() {
        this.waitForSelected(this._eleTimeout);
    }

    public void waitForSelected(long timeOut) {
        try {
            boolean firstResult = new WebDriverWait(this.webDriver, Duration.ofSeconds(timeOut))
                    .until(ExpectedConditions.elementToBeSelected(this.getElement()));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void rightClick() {
        try {
            this.waitForClickable();
            action.contextClick(this.getElement()).perform();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void doubleClick() {
        try {
            this.waitForClickable();
            action.doubleClick(this.getElement()).perform();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void check() {
        try {
            if (!this.isSelected())
                this.click();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void uncheck() {
        try {
            if (this.isSelected())
                this.click();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void click() {
        this.waitForClickable();
        this.click(5);
    }

    public void click(int maxTries) {
        log.debug(String.format("Click %s: %s", this._xpath, maxTries));
        boolean notClicked = true;
        int count = 0;
        while (notClicked && count <= maxTries) {
            try {
                this.waitForClickable();
                action.click(this.getElement()).perform();
                notClicked = false;
            } catch (Exception e) {
                ++count;
                if (count > maxTries) {
                    log.error(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void type(String value) {
        this.type(value, true);
    }

    public void type(String value, boolean isWait) {
        try {
            if (!isWait) {
                Thread.sleep(2000);
            } else {
                this.waitForDisplay();
            }
            this.getElement().sendKeys(value);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void moveTo() {
        try {
            this.waitForDisplay();
            action.moveToElement(this.getElement()).perform();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void moveTo(Point point) {
        try {
            this.waitForDisplay();
            action.moveToElement(this.getElement(), point.getX(), point.getY()).perform();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public void moveToAndClick() {
        try {
            this.moveTo();
            this.click();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void pressButton(Keys button) {
        try {
            this.waitForClickable();
            this.moveTo();
            this.getElement().sendKeys(button);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public void clear() {
        try {
            this.waitForClickable();
            this.getElement().clear();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void enter(String value) {
        try {
            this.clear();
            this.type(value);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean isEnabled() {
        return this.isEnabled(1);
    }

    public boolean isEnabled(long timeOut) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.startClock();
        try {
            while (stopWatch.getTimeLeftInSecond(timeOut) > 0) {
                if (this.getElement().isEnabled()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public boolean isPresent() {
        return this.isPresent(1);
    }

    public boolean isPresent(long timeOut) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.startClock();
        try {
            while (stopWatch.getTimeLeftInSecond(timeOut) > 0) {
                if (this.getElementList().size() > 0) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean isDisplayed() {
        return this.isDisplayed(1);
    }

    public boolean isDisplayed(long timeOut) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.startClock();
        try {
            while (stopWatch.getTimeLeftInSecond(timeOut) > 0) {
                if (this.getElementList().size() > 0) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean isSelected() {
        return this.isSelected(1);
    }

    public boolean isSelected(long timeOut) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.startClock();
        try {
            this.waitForClickable();
            while (stopWatch.getTimeLeftInSecond(timeOut) > 0) {
                if (this.getElement().isSelected()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getAttribute(String attributeName) {
        return this.getAttribute(attributeName, this._eleTimeout);
    }

    public String getAttribute(String attributeName, long timeOut) {
        try {
            this.waitForDisplay(timeOut);
            return this.getElement().getAttribute(attributeName);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getCssValue(String cssName) {
        return this.getCssValue(cssName, this._eleTimeout);
    }

    public String getCssValue(String cssName, long timeOut) {
        try {
            this.waitForPresence(timeOut);
            return this.getElement().getCssValue(cssName);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void waitUntilEnabled() {
        this.waitUntilEnabled(this._eleTimeout);
    }

    public void waitUntilEnabled(long timeOut) {
        try {
            this.waitForPresence(timeOut);
            this.waitForClickable(timeOut);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getText() {
        return this.getText(this._eleTimeout);
    }

    public String getText(long timeOut) {
        try {
            this.waitForPresence(timeOut);
            return this.getElement().getText().trim();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getValue() {
        return this.getValue(this._eleTimeout, true, 3);
    }

    public String getSelectedOption() {
        Select select = new Select(this.getElement());
        WebElement option = select.getFirstSelectedOption();
        String defaultItem = option.getText();
        return defaultItem;
    }

    public String getValue(long timeOut) {
        return this.getValue(timeOut, true, 3);
    }

    public String getValue(long timeOut, boolean isWait) {
        return this.getValue(timeOut, isWait, 3);
    }

    public String getValue(long timeOut, boolean isWait, int retry) {
        try {
            if (isWait) {
                this.waitForPresence(timeOut);
            }
            while (retry > 0) {
                String value = this.getElement().getAttribute("value").trim();
                if (value != null) {
                    return value;
                }
                --retry;
            }
            return null;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getInnerText() {
        return this.getInnerText(this._eleTimeout);
    }

    public String getInnerText(long timeOut) {
        try {
            this.waitForPresence(timeOut);
            return this.getElement().getAttribute("innerText").trim();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void waitUntilPropertyChange(String property) {
        this.waitUntilPropertyChange(property, this._eleTimeout);
    }

    public void waitUntilPropertyChange(String property, long timeOut) {
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.startClock();

            String previousValue = this.getAttribute(property);
            String currentValue = previousValue;

            while (stopWatch.getTimeLeftInSecond(timeOut) > 0 && (previousValue.equals(currentValue))) {
                currentValue = this.getAttribute(property);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void waitUntilPropertyNotChange(String property) {
        this.waitUntilPropertyNotChange(property, this._eleTimeout);
    }

    public void waitUntilPropertyNotChange(String property, long timeOut) {
        try {
            String previousValue = "previousValue";
            String currentValue = this.getAttribute(property);

            StopWatch stopWatch = new StopWatch();
            stopWatch.startClock();

            while (stopWatch.getTimeLeftInSecond(timeOut) > 0 && (!previousValue.equals(currentValue))) {
                previousValue = currentValue;
                currentValue = this.getAttribute(property);
                Thread.sleep(500);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void waitUntilCssValueNotChange(String property) {
        this.waitUntilCssValueNotChange(property, this._eleTimeout);
    }

    public void waitUntilCssValueNotChange(String cssName, long timeOut) {
        try {
            String previousValue = "previousValue";
            String currentValue = this.getCssValue(cssName);

            StopWatch stopWatch = new StopWatch();
            stopWatch.startClock();

            while (stopWatch.getTimeLeftInSecond(timeOut) > 0 && (!previousValue.equals(currentValue))) {
                previousValue = currentValue;
                currentValue = this.getCssValue(cssName);
                Thread.sleep(500);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void waitForControlStable() {
        this.waitForControlStable(this._eleTimeout);
    }

    public void waitForControlStable(long timeOut) {
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.startClock();

            this.waitUntilCssValueNotChange("top", stopWatch.getTimeLeftInSecond(timeOut));
            this.waitUntilCssValueNotChange("height", stopWatch.getTimeLeftInSecond(timeOut));
            this.waitUntilCssValueNotChange("left", stopWatch.getTimeLeftInSecond(timeOut));
            this.waitUntilCssValueNotChange("width", stopWatch.getTimeLeftInSecond(timeOut));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void scrollTo() {
        try {
            DriverWrapper.getInstance().jsExecution("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'nearest'});", this.getElement());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void scrollToElement() {
        try {
            DriverWrapper.getInstance().jsExecution(String.format("let element= document.evaluate(\"%s\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue ; element.scrollIntoView({behavior: 'smooth', block: 'center', inline: 'nearest'})", this.getElementXpath()));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void clickElement() {
        try {
            DriverWrapper.getInstance().jsExecution(String.format("let element= document.evaluate(\"%s\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue ; element.click({behavior: 'smooth', block: 'center', inline: 'nearest'})", this.getElementXpath()));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void scrollToAndClick() {
        boolean stale = true;
        int retry = 3;
        while (stale && retry > 0) {
            try {
                this.scrollTo();
                this.waitForPresence();
                this.click();
                stale = false;
            } catch (StaleElementReferenceException e) {
                stale = true;
                --retry;
                if (retry <= 0) {
                    log.error(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public List<WebElement> getChildrenElementList() {
        try {
            return this.getElement().findElements(By.xpath("./*"));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<String> getChildrenText() {
        try {
            List<WebElement> children = this.getChildrenElementList();
            return children.stream().map(element -> element.getAttribute("innerText").trim())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Dimension getSize() {
        try {
            this.waitForDisplay();
            return this.getElement().getSize();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Point getLocation() {
        try {
            this.waitForDisplay();
            return this.getElement().getLocation();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getTagName() {
        try {
            this.waitForPresence();
            return this.getElement().getTagName();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public void selectDropdown(String item) {
        try {
            this.click();
            ElementWrapper itemList = new ElementWrapper("//div[contains(@class, 'dropdown-items')]");
            if (!itemList.isDisplayed()) {
                this.click();
            }
            itemList.waitForDisplay();
            ElementWrapper itemLabel = new ElementWrapper(String.format("//li[contains(@class, 'dropdown-item')]//*[(self::div or self::span or self::a) and normalize-space(.)='%s']", item));
            itemLabel.click();
            itemList.waitUntilDisappear();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void selectOption(String text) {
        this.selectOption(text, false);
    }

    public void selectOption(String text, boolean bool) {
        try {
            Select select = new Select(this.getElement());
            if (bool) {
                select.selectByVisibleText(text);
            } else {
                select.selectByValue(text);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void selectOption(int index) {
        try {
            Select select = new Select(this.getElement());
            select.selectByIndex(index);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void takeElementScreenshot(String fileWithPath) {
        try {
            this.waitForDisplay();
            File source = this.getElement().getScreenshotAs(OutputType.FILE);
            File destination = new File(fileWithPath);
            FileHandler.copy(source, destination);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}


