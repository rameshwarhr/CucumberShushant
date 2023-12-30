package Steps;

import static Utilities.Common.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import Context.TestContext;
import Pages.StudyListPage;
import Utilities.Log;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StudyListSteps {

    TestContext testContext;
    StudyListPage studyListPage;

    public StudyListSteps(TestContext testContext) {
        this.testContext = testContext;
        studyListPage = new StudyListPage(testContext.getDriver());
    }

    @When("I open a study for patient with name as {string}")
    public void searchForPatientNameAndOpenStudy(String patientName) {
        studyListPage.searchForPatientName(patientName);
        Log.printInfo("Patient search is performed");
        studyListPage.selectStudyWithName(patientName);
        testContext.set("PatientName", patientName);
        Log.printInfo("searched study is selected");
    }

    @When("I open the same study again")
    public void reopenStudy() {
        studyListPage.searchForPatientName(testContext.get("RichPatientFirstName"),
                testContext.get("RichPatientLastName"));
        Log.printInfo("Patient search is performed");
        studyListPage.waitForDataToLoad();
        studyListPage.selectStudyWithName(testContext.get("RichPatientFirstName"),
                testContext.get("RichPatientLastName"));
        studyListPage.acceptErrorPopupIfDisplayed();
        Log.printInfo("searched study is selected");
    }

    @When("open a study for patient with first name as {string} and last name as {string}")
    @When("I open a study for patient with first name as {string} and last name as {string}")
    public void searchForPatientWithFirstNameAndLastNameAndOpenStudy(String firstName, String lastName) {
        studyListPage.waitForDataToLoad();
        studyListPage.searchForPatientName(firstName, lastName);
        Log.printInfo("Patient search is performed");
        studyListPage.waitForDataToLoad();
        studyListPage.selectStudyWithName(firstName, lastName);
        studyListPage.acceptErrorPopupIfDisplayed();
        Log.printInfo("searched study is selected");
        testContext.set("RichPatientFirstName", firstName);
        testContext.set("RichPatientLastName", lastName);
        testContext.set("PatientName", firstName + " " + lastName);
    }

    @When("I navigate to {string} tab")
    public void navigateToTab(String tabName) {
        studyListPage.navigateToTab(tabName);
        Log.printInfo("user navigated to " + tabName);
    }

    @When("I open pdf report for the patient")
    public void openPdfReportForPatient() {
        studyListPage.openPdfForPatient(testContext.get("RichPatientFirstName"),
                testContext.get("RichPatientLastName"));
        Log.printInfo("PDF report is displayed");
    }

    @Then("below tabs are displayed according to assigned roles")
    public void verifyTabsDisplayedAccordingToAssignedRoles(DataTable table) {
        List<String> expectedTabs = table.asList();
        List<String> displayedTabs = studyListPage.getTabNamesDisplayed();
        assertEquals(displayedTabs, expectedTabs);
        Log.printInfo("tabs are displayed as per assigned role");
    }

    @When("I sort {string} in {string} order")
    public void sortColumnsInAscendingAndDescendingOrder(String columnName, String sortOrder) {
        studyListPage.sortByColumnByOrder(columnName, sortOrder);
        Log.printInfo("sorted column in " + sortOrder);
    }

    @Then("studies are sorted by {string} in {string} order")
    public void verifySortingIsInAscendingOrDescendingOrder(String columnName, String sortOrder) {
        List<String> columnData = studyListPage.getColumnData(columnName);
        // List<String> sortedData = studyListPage.getColumnData(columnName);
        if (sortOrder.equalsIgnoreCase("ascending")) {
            Collections.sort(columnData);
            sleep(2, TimeUnit.SECONDS);
            assertEquals(columnData, columnData);
            Log.printInfo("Column is sorted in ascending order");
        } else if (sortOrder.equalsIgnoreCase("descending")) {
            Collections.sort(columnData);
            sleep(2, TimeUnit.SECONDS);
            Collections.reverse(columnData);
            sleep(2, TimeUnit.SECONDS);
            assertEquals(columnData, columnData);
            Log.printInfo("Column is sorted in descending order");
        }
    }

    @When("I reset the {string} filter")
    @When("I clear the {string} filter")
    public void clearFilters(String filterName) {
        String filterValue = testContext.get("FilterValue");
        studyListPage.clearFilter(filterName, filterValue);
        studyListPage.waitForDataToLoad();
    }

    @When("I enter {string} in {string} filter under study list tab")
    @When("I select {string} in {string} filter")
    @When("I select {string} from {string} filter")
    @When("I enter {string} in {string} filter")
    public void inputFilter(String filterValue, String filterName) {
        studyListPage.setFilter(filterName, filterValue);
        studyListPage.waitForDataToLoad();
        testContext.set("FilterName", filterName);
        testContext.set("FilterValue", filterValue);
    }

    @When("I enter {string} patient name in {string} filter")
    public void searchPatientName(String filterValue, String filterName) {
        studyListPage.setFilter(filterName, filterValue);
        studyListPage.waitForDataToLoad();
        String lastName = filterValue.split("\\^")[0];
        String firstName = filterValue.split("\\^")[1];
        testContext.set("FilterName", filterName);
        testContext.set("FilterValue", filterValue);
        testContext.set("RichPatientLastName", lastName);
        testContext.set("RichPatientFirstName", firstName);
    }

    @Then("all the studies {string} display {string} as {string}")
    @Then("study {string} display {string} as {string}")
    @Then("study list {string} display studies only which are having {string} as {string}")
    public void filterResult(String displayStatus, String filterName, String filterValue) {
        waitForDataToLoad();
        if (displayStatus.equalsIgnoreCase("should")) {
            assertTrue("Study list mismatch", studyListPage.isStudyListFiltered(filterName, filterValue));
            Log.printInfo("study list contains matching studies");
        } else {
            assertFalse("Study list mismatch", studyListPage.isStudyListFiltered(filterName, filterValue));
        }

    }

    @Then("study list should be displayed")
    @Then("I should return to the study list page")
    public void verifyStudyListWindowIsPresent() {
        assertTrue("Study List is not displayed", studyListPage.isStudyListPageDisplayed());
    }

    @When("I click on Advanced filter")
    public void clickOnAdvancedFilter() {
        studyListPage.waitForDataToLoad();
        studyListPage.clickAdvancedFilter();
    }

    @Then("Advanced filter section should get unfolded")
    public void verifyAdvancedFilterLabels() {
        assertTrue("Advanced Filter Label condition failed", studyListPage.isAdvancedFilterUnfolded());
    }

    @When("I right click on the study from study list")
    @When("right click on the study from study list")
    public void rightClickOnStudy() {
        studyListPage.rightClickOnFirstStudy();
        sleep(2, TimeUnit.SECONDS);
    }

    @Then("{string} option should be displayed in the study list right click menu")
    public void verifyOptionInStudyListRightClickMenu(String option) {
        assertTrue(studyListPage.isOptionPresentInRightClickMenu(option));
    }

    @When("I select {string} option from study list right click menu")
    public void selectOptionFromStudyListRightClickMenu(String option) {
        testContext.set("PhysicianName", studyListPage.getAssignedUserForFirstStudy());
        studyListPage.selectOptionFromRightClickMenu(option);
    }

    @Then("simple viewer should open for patient study in new application tab")
    public void verifySimpleViewerOpenedInNewTab() {
        assertTrue(studyListPage.isSimpleViewerOpenedForPatient(testContext.containsKey("PatientName") ?
                testContext.get("PatientName") : testContext.get("FilterValue")));
    }

    @Given("I hover on the first study")
    @Given("hover on the first study")
    public void hoverOnFirstStudy() {
        studyListPage.hoverOnFirstStudy();
        studyListPage.hoverAgainOnSameStudy();
    }

    @Then("floating {string} icon is {string} on study list")
    @Then("floating {string} is {string} on study list")
    public void verifyPresenceOfFloatingToolbar(String element, String status) {
        boolean elementVisibleStatus = studyListPage.isFloatingElementVisible(element);
        assertEquals((status.equalsIgnoreCase("PRESENT") || status.equalsIgnoreCase("ENABLED")), elementVisibleStatus);
    }

    @Then("{string} tab should not be present")
    public void isTabDisplayed(String tabName) {
        assertFalse(tabName + " is displayed", studyListPage.isTabDisplayed(tabName));
    }

    @When("I click on phone icon")
    public void clickPhoneIcon() {
        studyListPage.clickPhoneIcon();
    }

    @Then("latest row should reflect the fax details correctly")
    public void verifyFaxDetails() {
        String actual = studyListPage.getLatestFaxSentTimeStamp().replace(" ", "T");
        ZoneId estTimeZoneId = ZoneId.of("US/Eastern");
        ZonedDateTime displayedFaxSentDateTime = LocalDateTime.parse(actual).atZone(estTimeZoneId)
                .truncatedTo(ChronoUnit.MINUTES);
        ZonedDateTime capturedFaxSentDateTime = testContext.get("FaxSentDateTimeEST");
        assertTrue(displayedFaxSentDateTime.isEqual(capturedFaxSentDateTime));
    }

    @Then("black phone icon should be displayed for the patient study")
    public void verifyPhoneIconDisplayedForPatientStudy() {
        studyListPage.searchForPatientName(testContext.get("RichPatientFirstName"),
                testContext.get("RichPatientLastName"));
        studyListPage.waitForDataToLoad();
        assertTrue(studyListPage.isFaxQueuedIconVisible());
    }

    @Then("Edit Study Attributes modal should open")
    public void verifyEditStudyModalPresent() {
        testContext.set("StudyDetailsList", studyListPage.getStudyDetailsFromEditStudyAttributesWindow());
        assertTrue("Edit Study Attributes modal not present", studyListPage.isEditStudyModalPresent());
    }

    @When("I edit Description as {string} and save")
    public void editDescription(String newDescription) {
        testContext.set("Existing description", studyListPage.getDescriptionForFirstStudy());
        testContext.set("New description", newDescription);
        studyListPage.editDescription(newDescription);
    }

    @Then("updated Description should be visible for the first study")
    public void verifyEditedParameter() {
        assertEquals(studyListPage.getDescriptionForFirstStudy(), testContext.get("New description"));
    }

    @When("I click on Presets dropdown")
    public void clickOnPresetDropdown() {
        String studyCount = studyListPage.getStudyCount();
        testContext.set("StudyCount", studyCount);
        studyListPage.clickOnPresetDropdown();
    }

    @Then("below filter preset options {string} be available")
    public void areFilterPresetsDisplayed(String presetDisplayStatus, DataTable table) {
        List<String> expectedPresetFilterOptions = table.asList();
        List<String> displayedPresetFilterOptions = studyListPage.getPresetFilterOptions();

        if (presetDisplayStatus.equalsIgnoreCase("SHOULD")) {
            assertTrue(displayedPresetFilterOptions.containsAll(expectedPresetFilterOptions));
            Log.printInfo("Required preset filters are displayed");
        } else if (presetDisplayStatus.equalsIgnoreCase("SHOULD NOT")) {
            assertFalse(displayedPresetFilterOptions.containsAll(expectedPresetFilterOptions));
            Log.printInfo("Required preset filters are not displayed");
        }
    }

    @When("I select {string} preset filter")
    public void selectPresetFilter(String presetFilterName) {
        studyListPage.selectPresetFilter(presetFilterName);
        studyListPage.waitForDataToLoad();
    }

    @Then("nothing should be filtered")
    public void verifyFilterCount() {
        String studyCount = studyListPage.getStudyCount();
        String expectedStudyCount = testContext.get("StudyCount");
        assertEquals(studyCount, expectedStudyCount);
    }

    @Then("{string} filter field should display {string}")
    public void verifyFilterField(String filterName, String filterValue) {
        assertTrue(studyListPage.isFilterSetToExpectedValue(filterName, filterValue));
    }

    @When("view the final report Pdf")
    public void viewReportPdf() {
        studyListPage.searchForPatientName(testContext.get("RichPatientFirstName"),
                testContext.get("RichPatientLastName"));
        studyListPage.waitForDataToLoad();
        studyListPage.openReportPDFForTheStudy(testContext.get("RichPatientFirstName"),
                testContext.get("RichPatientLastName"));
        studyListPage.acceptErrorPopupIfDisplayed();
        Log.printInfo("searched study is selected");
    }

    @Then("pdf should contain the amendments")
    public void verifyAmendmentsInPdf() {
        assertTrue(studyListPage.getReportPdfData().contains(testContext.get("Amendment")));
    }

    @When("I save the preset as {string}")
    public void savePreset(String presetName) {
        studyListPage.savePreset(presetName);
        sleep(2, TimeUnit.SECONDS);
        testContext.set("PresetName", presetName);
    }

    @When("I assign {string} from list of service location")
    public void assignServiceLocationToStudy(String Location) {
        studyListPage.assignServiceLocationToStudyInStudyList(Location);
    }

    @When("I click on Yes")
    public void clickOnYesButton() {
        studyListPage.clickYesButton();
        studyListPage.waitForDataToLoad();
    }

    @When("assign physician {string} to the patient")
    public void assignPhysicianToThePatient(String physicianName) {
        testContext.set("PhysicianName", physicianName);
        studyListPage.addReferringPhysician(physicianName);
    }

    @When("click on rich viewer download button")
    public void downloadRicherViewer() {
        studyListPage.downloadRichViewer();
    }

    @When("I click on Service Location dropdown")
    public void clickServiceLocationDropdown() {
        studyListPage.clickServiceLocationDropdown();
    }

    @When("search {string} in the service location textbox")
    public void searchServiceLocation(String serviceLocation) {
        studyListPage.searchServiceLocation(serviceLocation);
    }

    @Then("searched service location should not be present")
    public void verifyHogwartsIsNotPresentInList() {
        assertEquals("Hogwarts Service Location Exists", "No items to show", studyListPage.getNoItemsToShowMessage());
        Log.printInfo("Hogwarts service location is not present");
    }

    @When("I close the service location dropdown")
    public void closeServiceLocationDropdown() {
        studyListPage.closeServiceLocation();
    }

    @Then("assigned physician should be displayed on the right side of the screen")
    public void verifyAssignedPhysician() {
        assertEquals(testContext.get("PhysicianName"),
                studyListPage.getAssignedPhysicianName(testContext.get("PhysicianName")));
    }

    @When("I {string} the assigned physician {string}")
    public void assignOrRemoveReferringPhysician(String action, String physicianName) {
        studyListPage.assignOrRemoveReferringPhysician(action, physicianName);
        testContext.set("PhysicianName", physicianName);
    }

    @When("I click on Save button")
    public void clickSaveButton() {
        studyListPage.clickSaveButton();
    }

    @When("I switch back to Vidistar")
    public void switchToVidistar() {
        studyListPage.switchToVidistarWindow();
    }

    @When("I navigate to the image url")
    public void navigateToImageUrl() {
        studyListPage.switchToImageUrl(testContext.get("ThumbnailImageUrl"));
    }

    @When("I refresh the page")
    @When("refresh the page")
    public void refreshPage() {
        studyListPage.refreshPage();
    }

    @When("export the final report pdf from the study list")
    public void exportTheFinalReportPdf() {
        if (testContext.get("RichPatientFirstName") != null) {
            studyListPage.searchForPatientName(testContext.get("RichPatientFirstName"),
                    testContext.get("RichPatientLastName"));
        } else {
            studyListPage.searchForPatientName(testContext.get("FilterValue"));
        }
        studyListPage.waitForDataToLoad();
        studyListPage.exportTheFinalReportPdf();
    }

    @When("unzip the downloaded file")
    public void unzipDownloadedFile() {
        testContext.set("ExportedPdfFromZipFile", studyListPage.unzipExportedPdfFile());
    }

    @When("save the server PDF report")
    public void saveServerPdfReport() {
        studyListPage.saveServerPdfFile();
    }

    @When("wait for the download to complete")
    public void waitUntilExportedPdfIsDownloaded() {
        studyListPage.verifyExportedPdfDownload();
    }

    @When("I wait for the study list data to load")
    public void waitForStudyListToLoad() {
        studyListPage.waitForStudyListToLoadTheData();
    }

    @When("get the study count of the filter result")
    public void getStudyListStudiesCount() {
        String studyCount = studyListPage.getStudyCount();
        testContext.set("Study list count", studyCount);
    }

    @Then("downloaded folder reports count should match the study list count")
    public void getUnzippedReportsFolderPath() {
        assertEquals("Unzipped report count does not match the Study list count",
                studyListPage.getFileCountInZippedFolder(), Integer.parseInt(testContext.get("Study list count")));
    }

    @When("save the ViewerDownloader exe file")
    public void saveViewerDownloaderExeFile() {
        studyListPage.saveRichViewerExeFile();
    }

    @When("wait for the data to load")
    public void waitForDataToLoad() {
        studyListPage.waitForDataToLoad();
    }

    @Then("the patient name {string} and patient DOB {string} should not be present in the log file")
    public void isPatientDetailPresent(String patientName, String dateOfBirth) {
        assertFalse("Patient detail is present",
                studyListPage.isPatientDetailPresentInLogFile(patientName, dateOfBirth));
        Log.printInfo("Patient detail is not present");
    }

    @When("I revert the status of the signed final report")
    public void revertSignedReport() {
        studyListPage.revertSignedReport(testContext.get("RichPatientFirstName"),
                testContext.get("RichPatientLastName"));
    }

    @When("I revert the status of the signed prelim report")
    public void revertWorkSheetReportStatus() {
        studyListPage.revertPrelimSignedReport(testContext.get("RichPatientFirstName"),
                testContext.get("RichPatientLastName"));
    }

    @When("I click on Nested filter under {string} tab")
    public void clickNestedFilter(String tabName) {
        studyListPage.clickNestedFilter(tabName);
    }

    @When("click on search button")
    public void clickSearchButton() {
        studyListPage.clickSearchButton();
    }

    @Then("Assign Studies to Physician Groups modal should open")
    @Then("Assign Studies to Physicians modal should open")
    public void verifyAssignStudyToPhysicianModalIsPresent() {
        assertTrue("Assign Studies to Physicians modal not present",
                studyListPage.isAssignStudyToPhysicianModalPresent());
    }

    @When("I assign {string} to the studies")
    public void assignPhysicianToStudy(String userOrGroupId) {
        studyListPage.assignPhysicianToStudy(userOrGroupId);
    }

    @When("I edit {string} as {string}")
    public void editFieldInStudyAttribute(String fieldName, String newValue) {
        studyListPage.editStudyAttribute(fieldName, newValue);
    }

    @When("I change the {string} status of {string} to {string}")
    @When("I change the {string} status of {string} to {string} in bulk")
    public void changeReportStatusInBulk(String reportType, String studyListContent, String status) {
        studyListPage.changeReportStatusInBulk(reportType, studyListContent, status);
    }

    @Then("{string} option is {string} from study list right click menu")
    public void verifyPresenceOfFloating(String menuOption, String status) {
        boolean elementVisibleStatus = studyListPage.isStudyRightClickMenuEnabled(menuOption);
        assertEquals((status.equalsIgnoreCase("PRESENT") || status.equalsIgnoreCase("ENABLED")), elementVisibleStatus);
    }

    @Then("unfinalize study alert should be displayed")
    public void verifyUnfinalizeStudyAlertIsVisible() {
        assertTrue(studyListPage.isUnfinalizeStudyAlertVisible());
    }

    @When("I enter {string} and click on {string}")
    public void enterTextInUnfinalizeStudyAlertTextbox(String reason, String buttonName) {
        studyListPage.enterTextInUnfinalizeStudyAlertTextbox(reason);
        studyListPage.clickUnfinalizeStudyAlertButton(buttonName);
    }

    @Then("the final report status should change to {string}")
    public void verifyReportStatusChangedToReverted(String finalReportStatus) {
        assertEquals(finalReportStatus.toUpperCase(), studyListPage.getFinalReportStatusOfFirstStudy());
    }

    @When("I change the status of the final report to {string}")
    public void changeFinalReportStatus(String finalReportStatus) {
        testContext.set("FinalReportStatus", finalReportStatus);
        studyListPage.changeFinalReportStatusOfFirstStudy(finalReportStatus.toUpperCase());
        studyListPage.waitForDataToLoad();
    }

    @Then("below icons on study list should be {string} irrespective of the final report status")
    public void isFloatingIconEnabled(String status, DataTable table) {
        boolean elementVisibleStatus;
        List<String> floatingIconList = table.asList();
        for (String floatingIcon : floatingIconList) {
            elementVisibleStatus = studyListPage.isFloatingElementVisible(floatingIcon);
            assertEquals(status.equalsIgnoreCase("ENABLED"), elementVisibleStatus);
        }
    }

    @When("I change the worksheet status to {string}")
    public void changeWorksheetStatusToScheduled(String status) {
        studyListPage.changeWorksheetStatus(status);
    }

    @When("I change the final report status to {string}")
    public void changeFinalReportStatusToScheduled(String status) {
        sleep(1, TimeUnit.SECONDS);
        studyListPage.changeFinalReportStatus(status);
    }

    @Then("study list tab should display {string} status as {string}")
    @Then("{string} status should be {string}")
    public void verifyReportStatusIsUpdated(String reportType, String status) {
        assertTrue("Report Status is not updated", studyListPage.isReportStatusUpdated(reportType, status));
    }

    @When("I navigate to Simple Viewer tab")
    public void navigateToSimpleViewerTab() {
        studyListPage.navigateToSimpleViewerTab();
    }

    @Then("patient should be {string}")
    public void isSearchedPatientAvailable(String patientAvailability) {
        if (patientAvailability.equalsIgnoreCase("AVAILABLE")) {
            assertFalse("Study list is empty", studyListPage.isStudyListEmpty());
        } else if (patientAvailability.equalsIgnoreCase("UNAVAILABLE")) {
            assertTrue("Study list is not empty", studyListPage.isStudyListEmpty());
        }
    }

    @When("search the newly created user")
    public void searchNewUser() {
        studyListPage.setFilter("Patient ID", "AutoTestPatient");
        studyListPage.waitForDataToLoad();
    }

    @Then("study details should be correct on study list page")
    public void verifyStudyDetailsOnModal() {
        assertTrue("study details are incorrect on study list page",
                studyListPage.verifyStudyDetailsOnEditStudyAttributesModal(testContext.get("StudyDetailsList")));
        studyListPage.closeEditStudyAttributesWindow();
    }

    @When("I set nested filter as below")
    public void setNestedFilter(DataTable table) {
        List<Map<String, String>> nestedFilterScenariosList = table.asMaps(String.class, String.class);
        List<String> filterColumnList = nestedFilterScenariosList.stream()
                .map(groupAndRolesMap -> groupAndRolesMap.get("FilterColumn")).collect(Collectors.toList());
        List<String> filterValueList = nestedFilterScenariosList.stream()
                .map(groupAndRolesMap -> groupAndRolesMap.get("FilterValue")).collect(Collectors.toList());
        String filterCondition = nestedFilterScenariosList.stream()
                .map(groupAndRolesMap -> groupAndRolesMap.get("FilterCondition")).collect(Collectors.toList()).get(0);
        testContext.set("FilterColumnList", filterColumnList);
        testContext.set("FilterCondition", filterCondition);
        testContext.set("FilterValueList", filterValueList);
        studyListPage.setupNestedFilter(filterColumnList, filterValueList, filterCondition);
    }

    @Then("studies should get listed as per the filters applied")
    public void areStudiesListedAsPerNestedFilter() {
        List<String> filterCriteriaList = testContext.get("FilterColumnList");
        List<String> filterValueList = testContext.get("FilterValueList");
        String filterCondition = testContext.get("FilterCondition");
        studyListPage.waitForDataToLoad();
        assertTrue(studyListPage.areResultsDisplayedAsPerNestedFilter(filterCriteriaList, filterValueList,
                filterCondition, testContext.get("MatchCondition")));
    }

    @When("I search the same patient again")
    @When("search the same patient again")
    public void searchSamePatientAgain() {
        studyListPage.waitForDataToLoad();
        if (testContext.get("FilterName") == null) {
            try {
                studyListPage.searchForPatientName(testContext.get("RichPatientFirstName"),
                        testContext.get("RichPatientLastName"));
            } catch (Exception e) {
                studyListPage.searchForPatientName(testContext.get("PatientName"));
            }
        } else {
            studyListPage.setFilter(testContext.get("FilterName"), testContext.get("FilterValue"));
        }
        studyListPage.waitForDataToLoad();
        Log.printInfo("Patient search is performed");
    }

    @When("I open an ecg study for patient with first name as {string} and last name as {string}")
    public void searchForEcgPatientWithFirstNameAndLastNameAndOpenStudy(String firstName, String lastName) {
        studyListPage.waitForDataToLoad();
        studyListPage.searchForPatientName(firstName, lastName);
        Log.printInfo("Patient search is performed");
        studyListPage.waitForDataToLoad();
        studyListPage.rightClickOnFirstStudy();
        studyListPage.selectOptionFromRightClickMenu("Open with Rich Viewer");
        studyListPage.acceptErrorPopupIfDisplayed();
        Log.printInfo("searched study is selected");
        testContext.set("RichPatientFirstName", firstName);
        testContext.set("RichPatientLastName", lastName);
        testContext.set("PatientName", firstName + " " + lastName);
    }

    @When("I open {int} study for patient with first name as {string} and last name as {string}")
    public void openPatientStudyNumbered(int studyNumber, String firstName, String lastName) {
        studyListPage.waitForDataToLoad();
        studyListPage.searchForPatientName(firstName, lastName);
        Log.printInfo("Patient search is performed");
        studyListPage.waitForDataToLoad();
        studyListPage.selectStudyWithName(firstName, lastName, studyNumber);
        studyListPage.acceptErrorPopupIfDisplayed();
        Log.printInfo("searched study is selected");
        testContext.set("RichPatientFirstName", firstName);
        testContext.set("RichPatientLastName", lastName);
        testContext.set("PatientName", firstName + " " + lastName);
        testContext.set("StudyNumber", studyNumber);
    }
}