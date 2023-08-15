package com.roomraccoon.test.automation.framework.web.delete;

import com.github.javafaker.Faker;
import com.roomraccoon.test.automation.framework.base.TestBase;
import com.roomraccoon.test.automation.framework.web.pom.EntriesPage;
import com.roomraccoon.test.automation.framework.web.pom.modal.AddEntryModal;
import com.roomraccoon.test.automation.framework.web.pom.modal.DeleteEntryModal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.roomraccoon.test.automation.framework.constants.TestGroups.REGRESSION;
import static com.roomraccoon.test.automation.framework.constants.TestGroups.SMOKE;

public class DeleteEntryTest extends TestBase {

    // Logger for logging messages
    private static final Logger LOG = LoggerFactory.getLogger(DeleteEntryTest.class);

    // Instance of EntriesPage to interact with the entries page
    private EntriesPage entriesPage;

    // Instance of AddEntryModal to interact with the add entry modal
    private AddEntryModal addEntryModal;

    // Instance of DeleteEntryModal to interact with the delete entry modal
    private DeleteEntryModal deleteEntryModal;

    // Setup method to initialize the test
    @BeforeClass(alwaysRun = true)
    private void setup() {
        entriesPage = new EntriesPage(driver);
        addEntryModal = new AddEntryModal(driver);
        deleteEntryModal = new DeleteEntryModal(driver);
    }

    // Test case to delete an entry and validate its non-existence
    @Test(groups = {SMOKE, REGRESSION})
    private void deleteEntryTest() {
        // Generate unique entry title and description using Faker
        String entryTitle = Faker.instance().idNumber().toString();
        String entryDescription = Faker.instance().idNumber().toString();

        // Navigate to the entries page
        entriesPage.goTo();

        // Open the "Add New Entry" modal and add a new entry
        entriesPage.openAddNewEntryModal();
        addEntryModal.addNewEntry(entryTitle, entryDescription);

        // Open the entry details modal and delete the entry
        entriesPage.openEntryDetailsModal(entryTitle, "delete");
        deleteEntryModal.deleteEntry(entryTitle);

        // Validate that the entry no longer exists
        entriesPage.validateEntryDoesNotExist(entryTitle);
    }
}
