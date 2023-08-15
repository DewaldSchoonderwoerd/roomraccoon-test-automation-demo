package com.roomraccoon.test.automation.framework.web.create;

import com.github.javafaker.Faker;
import com.roomraccoon.test.automation.framework.base.TestBase;
import com.roomraccoon.test.automation.framework.web.pom.EntriesPage;
import com.roomraccoon.test.automation.framework.web.pom.modal.AddEntryModal;
import com.roomraccoon.test.automation.framework.web.pom.modal.EntryDetailModal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;

import static com.roomraccoon.test.automation.framework.constants.TestGroups.REGRESSION;
import static com.roomraccoon.test.automation.framework.constants.TestGroups.SMOKE;

public class CreateNewEntryTest extends TestBase {

    // Logger for logging messages
    private static final Logger LOG = LoggerFactory.getLogger(CreateNewEntryTest.class);

    // Instance of EntriesPage to interact with the entries page
    private EntriesPage entriesPage;

    // Instance of AddEntryModal to interact with the add entry modal
    private AddEntryModal addEntryModal;

    // Instance of EntryDetailModal to interact with the entry detail modal
    private EntryDetailModal entryDetailModal;

    // Setup method to initialize the test
    @BeforeClass(alwaysRun = true)
    private void setup() {
        entriesPage = new EntriesPage(driver);
        addEntryModal = new AddEntryModal(driver);
        entryDetailModal = new EntryDetailModal(driver);
    }

    // Test case to create a new entry and validate its details
    @Test(groups = {SMOKE, REGRESSION})
    private void createNewEntryTest() {
        // Generate random entry title and description using Faker and Random
        String entryTitle = Faker.instance().idNumber().toString() + new Random().nextInt(6) + 1;
        String entryDescription = Faker.instance().idNumber().toString() + new Random().nextInt(6) + 1;

        // Navigate to the entries page
        entriesPage.goTo();

        // Open the "Add New Entry" modal and add a new entry
        entriesPage.openAddNewEntryModal();
        addEntryModal.addNewEntry(entryTitle, entryDescription);

        // Open the entry details modal and validate its details
        entriesPage.openEntryDetailsModal(entryTitle, "view");
        entryDetailModal.validateEntryDetails(entryTitle, entryDescription);
    }
}