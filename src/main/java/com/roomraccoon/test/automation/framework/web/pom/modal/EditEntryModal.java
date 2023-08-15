package com.roomraccoon.test.automation.framework.web.pom.modal;

import com.roomraccoon.test.automation.framework.web.WebPageHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class EditEntryModal extends WebPageHelper {

    // WebElement representing the title field in the edit modal
    @FindBy(xpath = "//input[@id='staticEmail']")
    private WebElement titleField;

    // WebElement representing the description field in the edit modal
    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement descriptionField;

    // WebElement representing the edit modal container
    @FindBy(xpath = "//div[@id='myModal']")
    private WebElement modal;

    // WebElement representing the title value in the edit modal
    @FindBy(xpath = "//div[contains(text(),'Editing: ')]")
    private WebElement titleValue;

    // WebElement representing the "Submit" button in the edit modal
    @FindBy(xpath = "//button[contains(text(),'Submit')]")
    private WebElement submitButton;

    // Constructor to initialize the edit modal using WebDriver and PageFactory
    public EditEntryModal(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Method to update an entry's details in the edit modal
    public void updateEntry(String initialTitle, String title, String description) {
        waitForTheElementToAppear(modal);
        Assert.assertTrue(titleValue.getText().contains(initialTitle),
                "Edit Modal does not contain the correct title: " + title);

        // Enter the new title and description values
        sendKeys(titleField, title);
        sendKeys(descriptionField, description);

        // Click the "Submit" button and wait for the modal to become invisible
        clickOn(submitButton);
        waitTillElementInvisible("//div[@id='myModal']");
    }
}
