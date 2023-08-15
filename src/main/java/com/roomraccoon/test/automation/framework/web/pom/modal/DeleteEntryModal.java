package com.roomraccoon.test.automation.framework.web.pom.modal;

import com.roomraccoon.test.automation.framework.web.WebPageHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class DeleteEntryModal extends WebPageHelper {

    // WebElement representing the title value in the delete modal
    @FindBy(xpath = "//div[contains(text(),'Deleting: ')]")
    private WebElement titleValue;

    // WebElement representing the "Yes" button in the delete modal
    @FindBy(xpath = "//button[contains(text(),'Yes')]")
    private WebElement yesButton;

    // WebElement representing the "Cancel" button in the delete modal
    @FindBy(xpath = "//a[contains(text(),'Cancel')]")
    private WebElement cancelButton;

    // WebElement representing the delete modal container
    @FindBy(xpath = "//div[@id='myModal']")
    private WebElement modal;

    // Constructor to initialize the delete modal using WebDriver and PageFactory
    public DeleteEntryModal(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Method to delete an entry using the delete modal
    public void deleteEntry(String title) {
        waitForTheElementToAppear(modal);
        Assert.assertTrue(titleValue.getText().contains(title),
                "Delete Modal does not contain the correct title: " + title);

        // Click the "Yes" button to confirm deletion
        clickOn(yesButton);
        waitTillElementInvisible("//div[@id='myModal']");
    }
}
