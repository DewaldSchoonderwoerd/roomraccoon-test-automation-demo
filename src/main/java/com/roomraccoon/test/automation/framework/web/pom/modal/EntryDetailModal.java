package com.roomraccoon.test.automation.framework.web.pom.modal;

import com.roomraccoon.test.automation.framework.web.WebPageHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class EntryDetailModal extends WebPageHelper {

    // WebElement representing the title value in the modal
    @FindBy(xpath = "//body/div[@id='myModal']/div[1]/div[1]/div[2]/div[1]/div[1]")
    private WebElement titleValue;

    // WebElement representing the description value in the modal
    @FindBy(xpath = "//body/div[@id='myModal']/div[1]/div[1]/div[2]/div[2]/div[1]")
    private WebElement descriptionValue;

    // WebElement representing the "Close" button in the modal
    @FindBy(xpath = "//a[contains(text(),'Close')]")
    private WebElement closeButton;

    // WebElement representing the modal container
    @FindBy(xpath = "//div[@id='myModal']")
    private WebElement modal;

    // Constructor to initialize the modal using WebDriver and PageFactory
    public EntryDetailModal(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Method to validate entry details in the modal
    public void validateEntryDetails(String title, String description) {
        waitForTheElementToAppear(modal);
        Assert.assertEquals(titleValue.getText(), title, "Title is incorrect");
        Assert.assertEquals(descriptionValue.getText(), description, "Description is incorrect");
    }

    // Method to close the modal and wait for it to become invisible
    public void closeModal() {
        clickOn(closeButton);
        waitTillElementInvisible("//div[@id='myModal']");
    }
}
