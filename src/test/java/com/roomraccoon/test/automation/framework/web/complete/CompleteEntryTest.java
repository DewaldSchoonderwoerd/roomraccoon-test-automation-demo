package com.roomraccoon.test.automation.framework.web.complete;

import com.github.javafaker.Faker;
import com.roomraccoon.test.automation.framework.base.TestBase;
import com.roomraccoon.test.automation.framework.web.pom.EntriesPage;
import com.roomraccoon.test.automation.framework.web.pom.modal.AddEntryModal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;

import static com.roomraccoon.test.automation.framework.constants.TestGroups.REGRESSION;
import static com.roomraccoon.test.automation.framework.constants.TestGroups.SMOKE;

public class CompleteEntryTest extends TestBase {

    // Logger for logging messages
    private static final Logger LOG = LoggerFactory.getLogger(CompleteEntryTest.class);

    // Instance of EntriesPage to interact with the entries page
    private EntriesPage entriesPage;

    // Instance of AddEntryModal to interact with the add entry modal
    private AddEntryModal addEntryModal;

    // Setup method to initialize the test
    @BeforeClass(alwaysRun = true)
    private void setup() {
        entriesPage = new EntriesPage(driver);
        addEntryModal = new AddEntryModal(driver);
    }

    // Test case to complete an entry
    @Test(groups = {SMOKE, REGRESSION})
    private void completeEntryTest() {
        // Generate random entry title and description using Faker and Random
        String entryTitle = Faker.instance().idNumber().toString() + new Random().nextInt(6) + 1;
        String entryDescription = Faker.instance().idNumber().toString() + new Random().nextInt(6) + 1;

        // Navigate to the entries page
        entriesPage.goTo();

        // Open the "Add New Entry" modal and add a new entry
        entriesPage.openAddNewEntryModal();
        addEntryModal.addNewEntry(entryTitle, entryDescription);

        // Open the entry details modal and mark the entry as complete
        entriesPage.openEntryDetailsModal(entryTitle, "complete");

        // Validate that the task is marked as complete
        entriesPage.validateTaskIsComplete(entryTitle);
    }
}