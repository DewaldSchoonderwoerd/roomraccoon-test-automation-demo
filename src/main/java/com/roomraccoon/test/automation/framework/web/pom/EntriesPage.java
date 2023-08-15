package com.roomraccoon.test.automation.framework.web.pom;

import com.roomraccoon.test.automation.framework.web.WebPageHelper;
import com.roomraccoon.test.automation.framework.web.WebPropertyUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.List;

import static com.roomraccoon.test.automation.framework.base.TestBase.ENVIRONMENT;

public class EntriesPage extends WebPageHelper {

    // Logger for logging messages
    private static final Logger LOG = LoggerFactory.getLogger(EntriesPage.class);

    // WebElement representing the "Add" button
    @FindBy(xpath = "//a[contains(text(),'Add')]")
    private WebElement addButton;

    // WebElement representing the container for rows of entries
    @FindBy(xpath = "//body/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]")
    private WebElement rows;

    // WebElement representing the entries list container
    @FindBy(xpath = "//body/div[1]/div[2]")
    private WebElement entriesList;

    // Constructor to initialize the page using WebDriver and PageFactory
    public EntriesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Method to navigate to the specified URL
    public void goTo() {
        String baseUrl = WebPropertyUtils.getWebUrl("roomraccoon", ENVIRONMENT);
        driver.navigate().to(baseUrl + "/home");
        LOG.info("Navigating to URL: " + baseUrl);
    }

    // Method to open the "Add New Entry" modal
    public void openAddNewEntryModal() {
        clickOn(addButton);
    }

    // Method to open the details modal for a specific entry with an action
    public void openEntryDetailsModal(String entryName, String action) {
        waitForTheElementToAppear(entriesList);
        boolean found = false;
        String parentElementXpath = "//body/div[1]/div[2]/div[1]/div[1]/div[1]";
        String rowElementXpath = ".//div[@class='row my-3']";
        String columnElementXpath = ".//div[@class='col-12 col-md-4 mb-1']";

        // Find the parent element that contains the rows
        WebElement parentElement = driver.findElement(By.xpath(parentElementXpath));

        // Find all row elements under the parent element
        List<WebElement> rowElements = parentElement.findElements(By.xpath(rowElementXpath));

        // Iterate through the row elements to find the desired entry and perform the action
        for (WebElement row : rowElements) {
            List<WebElement> rowColumns = row.findElements(By.xpath(columnElementXpath));

            for (WebElement rowColumn : rowColumns) {
                String columnText = rowColumn.getText().replace("· ", "");
                if (columnText.equals(entryName)) {
                    WebElement actionButton = row.findElement(By.xpath(".//*[.='" + action.toLowerCase() + "']"));
                    waitAndCheckIfElementClickable(actionButton);
                    clickOn(actionButton);
                    found = true;
                    break;
                }
            }
            if (found)
                break;
        }
    }

    // Method to validate that an entry with the specified title does not exist
    public void validateEntryDoesNotExist(String entryTitle) {
        String parentElementXpath = "//body/div[1]/div[2]/div[1]/div[1]/div[1]";
        String rowElementXpath = ".//div[@class='row my-3']";
        String columnElementXpath = ".//div[@class='col-12 col-md-4 mb-1']";

        // Find the parent element that contains the rows
        WebElement parentElement = driver.findElement(By.xpath(parentElementXpath));

        // Find all row elements under the parent element
        List<WebElement> rowElements = parentElement.findElements(By.xpath(rowElementXpath));

        // Iterate through the row elements to check if the entry exists
        for (WebElement row : rowElements) {
            List<WebElement> rowColumns = row.findElements(By.xpath(columnElementXpath));

            for (WebElement rowColumn : rowColumns) {
                String columnText = rowColumn.getText().replace("· ", "");
                if (columnText.equals(entryTitle)) {
                    Assert.fail("Entry: " + entryTitle + " was found, expected it to be deleted");
                }
            }
        }
    }

    // Method to validate that a task with the specified title is marked as complete
    public void validateTaskIsComplete(String entryTitle) {
        WebElement webElement = getElementByXpath("//del[contains(text(),'" + entryTitle + "')]");
        Assert.assertTrue(webElement.isDisplayed());
    }
}
