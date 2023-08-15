package com.roomraccoon.test.automation.framework.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import static com.roomraccoon.test.automation.framework.constants.BrowserTypes.CHROME;
import static com.roomraccoon.test.automation.framework.constants.BrowserTypes.FIREFOX;
import static com.roomraccoon.test.automation.framework.constants.Environments.QA;
import static com.roomraccoon.test.automation.framework.constants.PlatformTypes.WEB;

public class TestBase {

    // Properties object to store configuration
    public static final Properties PROPERTIES = new Properties();

    // Logger for logging messages
    private static final Logger LOG = LoggerFactory.getLogger(TestBase.class);

    // WebDriver instance shared across tests
    public static WebDriver driver;

    // Environment variable
    public static String ENVIRONMENT;

    // Timeout values for Selenium
    private static final int implicitWaitTimeout = 20;
    private static final int scriptTimeout = 30;
    private static final int pageLoadTimeout = 60;

    // This method sets up environment properties for all tests in the suite
    @BeforeSuite(alwaysRun = true)
    @Parameters({"environment"})
    public void setupGlobal(@Optional(QA) String environment) {
        LOG.info("Running against environment: " + environment);
        PROPERTIES.setProperty("environment", environment);
        TestBase.ENVIRONMENT = environment;
    }

    // This method sets up the WebDriver instance based on the platform and browser specified
    @Parameters({"platformType", "browserName"})
    @BeforeClass(alwaysRun = true)
    public void setupDriver(@Optional(WEB) String platformType, @Optional(CHROME) String browserName) throws Exception {
        LOG.info("platformType: " + platformType);
        LOG.info("browserName: " + browserName);

        // Check if platform type is WEB
        if (platformType.toUpperCase().equals(WEB)) {
            switch (browserName.toUpperCase()) {
                case CHROME:
                    // Set up Chrome WebDriver
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("start-maximized");
                    chromeOptions.addArguments("disable-infobars");
                    chromeOptions.addArguments("--disable-extensions");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver(chromeOptions);
                    break;
                case FIREFOX:
                    // Set up Firefox WebDriver
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver(firefoxOptions);
                    break;
                default:
                    throw new Exception("Unknown browser - " + browserName + "\n it either does not exist or needs to be set up.");
            }

            // Set timeouts and maximize window
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTimeout));
            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(scriptTimeout));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
            driver.manage().window().maximize();
            LOG.info("Completed setting up Selenium Driver for browser: " + browserName);
        } else {
            LOG.info("API only tests");
        }
    }

    // This method closes the WebDriver instance after the test class
    @Parameters({"platformType"})
    @AfterClass(alwaysRun = true)
    public void tearDownDriver() throws IOException {
        if (driver != null) driver.quit();
        LOG.info("Selenium Driver closed");
    }
}