package com.roomraccoon.test.automation.framework.web.pom.modal;

import com.roomraccoon.test.automation.framework.web.WebPageHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddEntryModal extends WebPageHelper {

    // Constructor to initialize the add entry modal using WebDriver and PageFactory
    public AddEntryModal(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // WebElement representing the title field in the add entry modal
    @FindBy(xpath = "//input[@id='staticEmail']")
    private WebElement titleField;

    // WebElement representing the description field in the add entry modal
    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement descriptionField;

    // WebElement representing the "Submit" button in the add entry modal
    @FindBy(xpath = "//body/div[@id='myModal']/div[1]/div[1]/div[2]/form[1]/div[3]/div[1]/div[1]/div[1]/input[1]")
    private WebElement submitButton;

    // WebElement representing the "Cancel" button in the add entry modal
    @FindBy(xpath = "//a[contains(text(),'Cancel')]")
    private WebElement cancelButton;

    // WebElement representing the add entry modal container
    @FindBy(xpath = "//div[@id='myModal']")
    private WebElement modal;

    // Method to add a new entry using the add entry modal
    public void addNewEntry(String title, String description) {
        waitForTheElementToAppear(modal);

        // Enter title and description values
        sendKeys(titleField, title);
        sendKeys(descriptionField, description);

        // Click the "Submit" button and wait for the modal to become invisible
        clickOn(submitButton);
        waitTillElementInvisible("//div[@id='myModal']");
    }
}
