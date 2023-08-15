package com.roomraccoon.test.automation.framework.web;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class WebPageHelper {

    // Logger for logging messages
    private static final Logger LOG = LoggerFactory.getLogger(WebPageHelper.class);

    // Constants for element location strategies
    public static final String ID = "ID";
    public static final String XPATH = "xpath";
    public static final String TEXT = "text";
    public static final String CONTAINS = "contains";
    public static final String STARTS_WITH = "starts-with";

    // WebDriver and WebDriverWait for interacting with web elements
    protected WebDriver driver;
    protected WebDriverWait wait;

    // Default wait timeout and sleep interval
    protected static final int DRIVER_DEFAULT_WAIT_TIMEOUT = 20;
    protected static final int SLEEP_IN_BETWEEN_POLLS = 500;

    // Constructor to initialize the driver and wait
    public WebPageHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_DEFAULT_WAIT_TIMEOUT), Duration.ofMillis(SLEEP_IN_BETWEEN_POLLS));
    }

    // Method to send keys to a web element after ensuring visibility
    public void sendKeys(WebElement element, String value) {
        new WebDriverWait(driver, Duration.ofSeconds(15)).
                until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(value);
    }

    // Method to click on a web element after ensuring it's clickable
    public void clickOn(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(30)).
                until(ExpectedConditions.elementToBeClickable(element));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    // Method to wait for a web element to appear
    public void waitForTheElementToAppear(WebElement el) {
        try {
            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, Duration.ofSeconds(30)).
                    pollingEvery(Duration.ofMillis(500));
            wait.until(ExpectedConditions.visibilityOf(el));
        } catch (Exception e) {
            LOG.info("failed to load the web element");
        }
    }

    // Method to wait for a web element to be clickable
    public void waitAndCheckIfElementClickable(WebElement el) {
        try {
            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, Duration.ofSeconds(30)).
                    pollingEvery(Duration.ofMillis(500));
            wait.until(ExpectedConditions.visibilityOf(el));
            wait.until(ExpectedConditions.elementToBeClickable(el));
            LOG.info("Element is clickable");
        } catch (Exception e) {
            LOG.info("failed to check web element clickable");
        }
    }

    // Method to wait for a web element to become invisible
    public void waitTillElementInvisible(String xpath) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
        } catch (Exception e) {
            LOG.info("failed to check web element invisibility");
        }
    }

    // Method to find a web element by a specified location strategy and value
    protected WebElement findElementWhenReady(By by) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return driver.findElement(by);
        } catch (Exception e) {
            LOG.error("Exception on : " + by);
        }
        return null;
    }

    // Method to get a web element using a specific location strategy and value
    public WebElement getElementByType(String type, String value) {
        try {
            return findElementWhenReady(getByTypeAndValue(type, value));
        } catch (Exception e) {
            return null;
        }
    }

    // Method to get a web element using an XPath expression
    public WebElement getElementByXpath(String value) {
        return getElementByType("XPATH", value);
    }

    // Method to construct a By object based on the specified location strategy and value
    public By getByTypeAndValue(String type, String value) {
        String inputType = type.toLowerCase();
        By by = By.id(value);

        if (inputType.equalsIgnoreCase(ID)) {
            by = By.id(value);
            LOG.info("finding element by id => " + value);

        } else if (inputType.equalsIgnoreCase(XPATH)) {
            by = By.xpath(value);
            LOG.info("finding element by xpath => " + value);

        } else if (inputType.equalsIgnoreCase(TEXT)) {
            by = By.xpath("//*[.='" + value + "']");
            LOG.info("finding element by text => " + value);

        } else if (inputType.equalsIgnoreCase(CONTAINS)) {
            by = By.xpath("//*[contains(.,'" + value + "')]");
            LOG.info("finding element by contains => " + value);
        } else if (inputType.equalsIgnoreCase(STARTS_WITH)) {
            by = By.xpath("//*[starts-with(text(),'" + value + "')]");
            LOG.info("finding element by starts-with => " + value);
        }
        return by;
    }
}