package com.roomraccoon.test.automation.framework.web.update;

import com.github.javafaker.Faker;
import com.roomraccoon.test.automation.framework.base.TestBase;
import com.roomraccoon.test.automation.framework.web.pom.EntriesPage;
import com.roomraccoon.test.automation.framework.web.pom.modal.AddEntryModal;
import com.roomraccoon.test.automation.framework.web.pom.modal.EditEntryModal;
import com.roomraccoon.test.automation.framework.web.pom.modal.EntryDetailModal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;

import static com.roomraccoon.test.automation.framework.constants.TestGroups.REGRESSION;
import static com.roomraccoon.test.automation.framework.constants.TestGroups.SMOKE;

public class EditEntryTest extends TestBase {

    // Logger for logging messages
    private static final Logger LOG = LoggerFactory.getLogger(EditEntryTest.class);

    // Instance of EntriesPage to interact with the entries page
    private EntriesPage entriesPage;

    // Instance of AddEntryModal to interact with the add entry modal
    private AddEntryModal addEntryModal;

    // Instance of EntryDetailModal to interact with the entry detail modal
    private EntryDetailModal entryDetailModal;

    // Instance of EditEntryModal to interact with the edit entry modal
    private EditEntryModal editEntryModal;

    // Setup method to initialize the test
    @BeforeClass(alwaysRun = true)
    private void setup() {
        entriesPage = new EntriesPage(driver);
        addEntryModal = new AddEntryModal(driver);
        entryDetailModal = new EntryDetailModal(driver);
        editEntryModal = new EditEntryModal(driver);
    }

    // Test case to edit an entry and validate the updated details
    @Test(groups = {SMOKE, REGRESSION})
    private void editEntryTest() {
        // Generate initial and updated entry titles and descriptions using Faker and Random
        String initialEntryTitle = Faker.instance().idNumber().toString() + new Random().nextInt(6) + 1;
        String updatedEntryTitle = Faker.instance().idNumber().toString() + new Random().nextInt(6) + 1;
        String initialEntryDescription = Faker.instance().idNumber().toString() + new Random().nextInt(6) + 1;
        String updatedEntryDescription = Faker.instance().idNumber().toString() + new Random().nextInt(6) + 1;

        // Navigate to the entries page
        entriesPage.goTo();

        // Open the "Add New Entry" modal and add an initial entry
        entriesPage.openAddNewEntryModal();
        addEntryModal.addNewEntry(initialEntryTitle, initialEntryDescription);

        // Open the entry details modal and validate its initial details
        entriesPage.openEntryDetailsModal(initialEntryTitle, "view");
        entryDetailModal.validateEntryDetails(initialEntryTitle, initialEntryDescription);
        entryDetailModal.closeModal();

        // Open the entry details modal and edit the entry
        entriesPage.openEntryDetailsModal(initialEntryTitle, "edit");
        editEntryModal.updateEntry(initialEntryTitle, updatedEntryTitle, updatedEntryDescription);

        // Open the entry details modal for the updated entry and validate its details
        entriesPage.openEntryDetailsModal(updatedEntryTitle, "view");
        entryDetailModal.validateEntryDetails(updatedEntryTitle, updatedEntryDescription);
    }
}
