package Steps;

import Context.TestContext;
import Pages.StudyTreePage;
import TestData.TestData;
import Utilities.Log;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static Utilities.Common.sleep;
import static org.junit.Assert.*;

public class StudyTreeSteps {

    TestContext testContext;
    StudyTreePage studyTreePage;

    public StudyTreeSteps(TestContext testContext) {
        this.testContext = testContext;
        studyTreePage = new StudyTreePage(testContext.getDriver());
    }

    @When("click Merge Patients option")
    public void selectMergePatientOption() {
        studyTreePage.selectMergePatientOption();
        Log.printInfo("Merge patient option is selected");
        studyTreePage.waitForDataToLoad();
    }

    @Then("all the studies of that patient should be burned to media")
    @Then("child patient study should be merged with master patient")
    public void verifyMergedStudyOfMasterPatient() {
        int initialFirstPatientStudyCount = testContext.get("firstPatientInitialStudyCount");
        assertEquals(initialFirstPatientStudyCount, studyTreePage.firstPatientStudyCount());
        Log.printInfo("study count after merging the patients is correct");
    }

    @Then("merged study should not be present in the child patient")
    public void verifyMergedStudyRemovedFromChildPatient() {
        int secondPatientInitialStudyCount = testContext.get("secondPatientInitialStudyCount");
        assertEquals("Post merge study from child patient is present", secondPatientInitialStudyCount,
                studyTreePage.secondPatientStudyCount());
        Log.printInfo("Post merge study from child patient is not present");
    }

    @When("I click on new study option")
    public void clickOnNewStudyOption() {
        studyTreePage.clickAddNewStudy();
        Log.printInfo("Clicked on new study");
    }

    @Then("Add new study modal should be displayed")
    public void verifyAddStudyModalDisplayed() {
        assertTrue(studyTreePage.isAddStudyModalDisplayed());
        Log.printInfo("Add new study modal is displayed");
    }

    @When("I enter study details")
    public void enterStudyDetails(DataTable table) {
        List<Map<String, String>> studyDetails = table.asMaps(String.class, String.class);
        studyTreePage.enterStudyDetails(studyDetails);
        Log.printInfo("Study details are entered in respective fields");
        testContext.set("StudyDateTime", studyTreePage.getStudyCreationDateTime());
    }

    @When("click on save button")
    public void clickSaveButton() {
        studyTreePage.saveStudy();
        studyTreePage.acceptWarningAtStudyCreation();
        Log.printInfo("Save button is clicked");
    }

    @Then("new study should be created")
    public void verifyStudyIsCreated() {
        String studyCreationDateTime = testContext.get("StudyDateTime");
        assertTrue(studyTreePage.isStudyCreated(studyCreationDateTime));
        Log.printInfo("New study is created");
    }

    @When("I open created study in rich viewer")
    public void openCreatedStudy() {
        String studyCreationDateTime = testContext.get("StudyDateTime");
        studyTreePage.selectStudyByStudyDateTime(studyCreationDateTime);
        Log.printInfo("Selected study by study date time");
        studyTreePage.openStudyInRichViewer(studyCreationDateTime);
        Log.printInfo("Opened the created study in rich viewer");
    }

    @When("hover on the first patient")
    public void hoverOnFirstPatient() {
        sleep(2, TimeUnit.SECONDS);
        studyTreePage.hoverOnFirstPatientOnStudyTree();
    }

    @Given("hover on the first series")
    public void hoverOnFirstSeries() {
        sleep(2, TimeUnit.SECONDS);
        studyTreePage.hoverOnFirstSeriesOnStudyTree();
    }

    @When("I expand studies for first patient")
    @When("I expand first patient")
    public void expandFirstPatient() {
        studyTreePage.expandFirstPatientOnStudyTree();
        testContext.set("firstPatientInitialStudyCount", studyTreePage.firstPatientStudyCount());
    }

    @When("I expand study attributes")
    public void expandFirstStudy() {
        studyTreePage.expandFirstStudyOfFirstPatientOnStudyTree();
        studyTreePage.waitForDataToLoad();
    }

    @Then("edit floating {string} is {string} for series attributes")
    @Then("floating {string} is {string} for study attributes")
    public void verifyIfFloatingElementVisible(String element, String status) {
        boolean elementVisibleStatus = studyTreePage.isFloatingElementVisible(element);
        assertEquals((status.equalsIgnoreCase("PRESENT") || status.equalsIgnoreCase("ENABLED")), elementVisibleStatus);
    }

    @When("hover on the first study in the study tree tab")
    public void hoverOnFirstStudyOnStudyTree() {
        studyTreePage.hoverOnFirstStudyOnStudyTree();
    }

    @Then("edit floating {string} is {string} for patient list")
    public void verifyPresenceOfFloatingToolbar(String element, String status) {
        boolean elementVisibleStatus = studyTreePage.isFloatingElementVisible(element);
        assertEquals(status.equalsIgnoreCase("PRESENT"), elementVisibleStatus);
    }

    @Given("I add a new patient")
    public void addNewPatient() {
        String testPatientId = TestData.patientDetails.TestPatientId + LocalDateTime.now().toString();
        studyTreePage.addNewPatient(testPatientId, TestData.patientDetails.TestPatientFirstName,
                TestData.patientDetails.TestPatientLastName);
        testContext.set("RichPatientFirstName", TestData.patientDetails.TestPatientFirstName);
        testContext.set("RichPatientLastName", TestData.patientDetails.TestPatientLastName);
        testContext.set("PatientId", testPatientId);
    }

    @When("I edit Patient Id as {string} and save")
    public void editPatientId(String newPatientId) {
        testContext.set("existingId", studyTreePage.getPatientIdForFirstPatient());
        testContext.set("newPatientId", newPatientId);
        studyTreePage.editPatientId(newPatientId);
    }

    @Then("updated Patient Id should be visible for the patient")
    public void verifyEditedParameter() {
        assertEquals(studyTreePage.getPatientIdForFirstPatient(), testContext.get("newPatientId"));
    }

    @When("I edit Modality as {string} and save")
    public void editModality(String newModality) {
        testContext.set("existingModality", studyTreePage.getSeriesModality(1));
        testContext.set("newModality", newModality);
        studyTreePage.editModality(newModality);
    }

    @Then("updated Modality should be visible for the first series")
    public void verifyEditedSeriesParameter() {
        assertTrue(studyTreePage.getSeriesModality(1).equals(testContext.get("newModality"))||
                studyTreePage.getSeriesModality(7).equals(testContext.get("newModality")));
    }

    @When("click on Edit Series icon")
    public void clickEditSeriesIcon() {
        studyTreePage.clickEditSeriesIcon();
    }

    @When("I select {string} option from study tree right click menu")
    public void selectOptionFromStudyTreeRightClickMenu(String option) {
        studyTreePage.selectOptionFromRightClickMenu(option);
    }

    @When("right click on the patient from study tree")
    public void rightClickOnPatient() {
        studyTreePage.rightClickOnFirstPatient();
    }

    @Then("below columns are displayed for Patient table")
    public void verifyPatientColumnNamesDisplayed(DataTable table) {
        List<String> expectedColumnNames = table.asList();
        assertEquals("Column Names are not displayed as expected", studyTreePage.getPatientColumnNamesDisplayed(), expectedColumnNames);
    }

    @When("I expand patient {string} to view its studies")
    public void expandStudy(String patientName) {
        studyTreePage.expandStudiesForPatient(patientName);
    }

    @Then("below columns are displayed for Study table")
    public void verifyStudyColumnNamesDisplayed(DataTable table) {
        List<String> expectedColumnNames = table.asList();
        assertEquals("Column Names are not displayed as expected", studyTreePage.getStudyColumnNamesDisplayed(), expectedColumnNames);
    }

    @When("I expand the first study")
    public void expandStudy() {
        studyTreePage.expandStudyByIndex(0);
    }

    @Then("below columns are displayed for Series table")
    public void verifySeriesColumnNamesDisplayed(DataTable table) {
        List<String> expectedColumnNames = table.asList();
        assertEquals("Column Names are not displayed as expected", studyTreePage.getSeriesColumnNamesDisplayed(), expectedColumnNames);
    }

    @When("I {string} {int}st series")
    @When("I {string} {int}nd series")
    @When("I {string} {int}rd series")
    @When("I {string} {int}th series")
    public void expandCollapseSeries(String iconType, int seriesNumber) {
        studyTreePage.expandCollapseSeries(iconType, seriesNumber);
    }

    @Then("below columns are displayed for Instances table")
    public void verifyInstanceColumnNamesDisplayed(DataTable table) {
        List<String> expectedColumnNames = table.asList();
        Log.printInfo("expected column names -->" + expectedColumnNames);
        Log.printInfo("actual column names -->" + studyTreePage.getInstanceColumnNamesDisplayed());
        assertEquals("Column Names are not displayed as expected", studyTreePage.getInstanceColumnNamesDisplayed(), expectedColumnNames);
    }

    @Then("delete patient icon {string} be visible")
    public void verifyDeletePatientButtonVisible(String status) {
        if (status.equalsIgnoreCase("SHOULD")) {
            assertTrue("Button is not present in report", studyTreePage.isDeletePatientButtonVisible());
        } else {
            assertFalse("Button is present in report", studyTreePage.isDeletePatientButtonVisible());
        }
    }

    @When("click on {string} button")
    public void clickToolBarButton(String button) {
        studyTreePage.clickToolBarButton(button);
    }

    @When("click on Yes")
    public void clickOnYesButton() {
        studyTreePage.clickOnYesButtonInPopup();
        sleep(5, TimeUnit.SECONDS);
    }

    @When("select both the patients from study tree for CD burning")
    public void selectPatients() {
        studyTreePage.selectAllPatients();
    }

    @When("select the patient for CD burning")
    public void selectPatient() {
        studyTreePage.selectFirstPatient();
    }

    @When("I click on Nested filter link")
    public void clickNestedFilterLink() {
        studyTreePage.clickOnNestedFilter();
    }

    @When("I select {string} from the match filter dropdown")
    public void selectOption(String option) {
        testContext.set("MatchCondition", option);
        studyTreePage.selectOptionFromMatchFilterDropDown(option);
    }

    @When("I click on {string} button {int} time")
    public void clickAddButton(String button, Integer number) {
        studyTreePage.clickNestedFilterAddRemoveIcon(button, number);
    }

    @When("I click on search button")
    public void clickSearch() {
        studyTreePage.clickSearchButton();
    }

    @When("I search for the below {int} patients")
    public void searchPatientNames(int numberOfPatients, DataTable table) {
        List<String> patientNameList = table.asList();
        testContext.set("patientNameList", patientNameList);
        studyTreePage.searchPatientInNestedFilter(numberOfPatients, patientNameList);
    }

    @Then("all searched patients should be present")
    public void verifySearchedPatientsArePresent() {
        List<String> enteredPatientNameList = testContext.get("patientNameList");
        assertTrue("Searched Patients Are Not Present",
                studyTreePage.verifySearchedPatientsArePresent(enteredPatientNameList));
    }

    @When("I click on match filter dropdown")
    public void clickMatchDropDown() {
        studyTreePage.clickMatchFilterDropDown();
    }

    @When("click on import study from CD DVD ROM icon")
    public void importStudyFromCdDvdRom() {
        studyTreePage.importStudiesFromCdDvdRom();
    }

    @Then("the Upload Wizard window should load")
    public void uploadWizardWindowShouldLoad() {
        assertTrue("Upload Wizard window is not loaded", studyTreePage.isUploadWizardWindowLoaded());
    }

    @When("follow the prompts and click on finish")
    public void finishWizardImporting() {
        studyTreePage.finishWizardImporting();
    }

    @When("I select the DicomDir file")
    public void selectDicomDirFile() {
        studyTreePage.selectDicomDirFile();
    }

    @When("I expand the first patient")
    public void expandPatient() {
        studyTreePage.expandFirstPatientOnStudyTree();
        studyTreePage.expandStudyByIndex(0);
        studyTreePage.expandFirstSeries();
    }

    @When("click on first thumbnail")
    public void clickOnFirstThumbnail() {
        studyTreePage.clickOnFirstThumbnail(0);
    }

    @Then("image {string} get opened")
    public void isThumbnailImageOpenedInNewTab(String imageDisplayStatus) {
        studyTreePage.switchToImageWindow();
        if (imageDisplayStatus.equalsIgnoreCase("SHOULD")) {
            testContext.set("ThumbnailImageUrl", studyTreePage.getThumbnailImageUrl());
        }
        switch (imageDisplayStatus.toUpperCase()) {
            case "SHOULD":
                assertTrue("Image is not displayed", studyTreePage.isImageOpenedInNewTab());
                break;

            case "SHOULD NOT":
                assertFalse("Image is displayed", studyTreePage.isImageOpenedInNewTab());
                break;
        }
    }

    @When("switch to the other tab")
    public void switchToImageTab() {
        studyTreePage.switchToImageWindow();
    }

    @When("I select second patient")
    public void selectSecondPatient() {
        studyTreePage.selectSecondPatient();
    }

    @When("I expand second patient")
    public void expandSecondPatient() {
        studyTreePage.expandSecondPatient();
        testContext.set("secondPatientInitialStudyCount", studyTreePage.secondPatientStudyCount());
    }

    @When("I select first study")
    public void selectFirstStudyOfFirstPatient() {
        studyTreePage.selectFirstStudyOfFirstPatient();
    }

    @When("select all the studies of {int}th patient")
    @When("select all the studies of {int}rd patient")
    @When("select all the studies of {int}nd patient")
    @When("select all the studies of {int}st patient")
    public void selectAllStudiesOfFirstPatient(int patientNumber) {
        studyTreePage.selectAllStudiesOfPatient(patientNumber - 1);
    }

    @Then("all the studies of second patient {string} display Assigned Users as {string}")
    @Then("all the studies of first patient {string} display Assigned Users as {string}")
    public void verifyPhysicianIsAssignedToStudy(String displayStatus, String physicianName) {
        studyTreePage.waitForDataToLoad();
        if (displayStatus.equalsIgnoreCase("should")) {
            assertTrue("Study list mismatch", studyTreePage.isPhysicianAssignedToStudy(physicianName));
            Log.printInfo("study list contains matching studies");
        } else {
            assertFalse("Study list mismatch", studyTreePage.isPhysicianAssignedToStudy(physicianName));
        }
    }

    @Then("all the studies of second patient {string} display Assigned Groups as {string}")
    @Then("all the studies of first patient {string} display Assigned Groups as {string}")
    public void verifyPhysicianGroupIsAssignedToStudy(String displayStatus, String physicianGroupName) {
        studyTreePage.waitForDataToLoad();
        if (displayStatus.equalsIgnoreCase("should")) {
            assertTrue("Study list mismatch", studyTreePage.isPhysicianGroupAssignedToStudy(physicianGroupName));
            Log.printInfo("study list contains matching studies");
        } else {
            assertFalse("Study list mismatch", studyTreePage.isPhysicianGroupAssignedToStudy(physicianGroupName));
        }
    }

    @When("I unexpand the studies of {int}th patient")
    @When("I unexpand the studies of {int}rd patient")
    @When("I unexpand the studies of {int}nd patient")
    @When("I unexpand the studies of {int}st patient")
    public void unExpandSecondPatient(int patientNumber) {
        studyTreePage.collapsePatientSection(patientNumber - 1);
    }

    @When("I right click on the first study from study tree")
    public void rightClickOnStudy() {
        studyTreePage.rightClickOnFirstStudy();
    }

    @Then("{string} option is {string} from study tree right click menu")
    public void selectOptionFromRightClickMenu(String option, String status) {
        boolean elementVisibleStatus = studyTreePage.isStudyRightClickMenuEnabled(option);
        assertEquals((status.equalsIgnoreCase("PRESENT") || status.equalsIgnoreCase("ENABLED")), elementVisibleStatus);
    }

    @Then("delete series button should be {string}")
    public void isDeleteSeriesButtonEnabled(String status) {
        boolean elementVisibleStatus = studyTreePage.isDeleteSeriesButtonEnabled();
        assertEquals(status.equalsIgnoreCase("ENABLED"), elementVisibleStatus);
    }

    @Then("below icons on study tree should be {string} irrespective of the final report status")
    public void isFloatingIconEnabled(String status, DataTable table) {
        boolean elementVisibleStatus;
        List<String> floatingIconList = table.asList();
        for (String floatingIcon : floatingIconList) {
            elementVisibleStatus = studyTreePage.isFloatingElementVisible(floatingIcon);
            assertEquals(status.equalsIgnoreCase("ENABLED"), elementVisibleStatus);
        }
    }

    @Then("Edit Study Attributes modal should open on study tree page")
    public void verifyEditStudyModalPresent() {
        testContext.set("StudyDetailsList", studyTreePage.getStudyDetailsFromEditStudyAttributesWindow());
        assertTrue("Edit Study Attributes modal not present", studyTreePage.isEditStudyModalPresent());
    }

    @Then("study details should be correct on study tree page")
    public void verifyStudyDetailsOnModal() {
        assertTrue("study details are incorrect on study tree page",
                studyTreePage.verifyStudyDetailsOnEditStudyAttributesModal(testContext.get("StudyDetailsList")));
        studyTreePage.closeEditStudyAttributesWindow();
    }

    @When("I enter {string} in {string} filter under study tree tab")
    public void inputFilter(String filterValue, String filterName) {
        studyTreePage.setFilter(filterName, filterValue);
        studyTreePage.waitForDataToLoad();
    }

    @Then("single study should be visible for created order")
    public void verifySingleStudyIsPresentForCreatedOrder() {
        assertEquals("Study verification failed", 1, studyTreePage.firstPatientStudyCount());
    }

    @Then("{string} should get listed as per the filters applied for Study Tree")
    public void areStudiesListedAsPerNestedFilter(String resultTable) {
        List<String> filterCriteriaList = testContext.get("FilterColumnList");
        List<String> filterValueList = testContext.get("FilterValueList");
        String filterCondition = testContext.get("FilterCondition");
        studyTreePage.waitForDataToLoad();
        assertTrue(studyTreePage.areResultsDisplayedAsPerNestedFilter(filterCriteriaList, filterValueList,
                filterCondition, testContext.get("MatchCondition"), resultTable));
    }

    @Then("delete {string} button should be {string}")
    public void isDeleteSeriesInstanceButtonDisabled(String studyEntity, String string) {
        switch (string.toUpperCase()) {
            case "DISABLED":
                assertTrue(studyEntity + " delete button is enabled", studyTreePage.isDeleteEntityDisabled(studyEntity));
                break;

            case "ENABLED":
                assertTrue(studyEntity + " delete button is disabled", studyTreePage.isDeleteEntityEnabled(studyEntity));
                break;
        }
    }

    @When("I hover on {string} delete button")
    public void hoverOnDeleteIcon(String studyEntity) {
        studyTreePage.hoverOnDeleteIcon(studyEntity);
    }

    @Then("disabled delete icon tooltip should display {string}")
    public void isTooltipMessageDisplayed(String studyEntity) {
        assertTrue("Locked study message is not displayed in tooltip",
                studyTreePage.isTooltipDisplayedForDeleteIcon(studyEntity));
    }

    @When("I {string} the {int}st study")
    @When("I {string} the {int}nd study")
    @When("I {string} the {int}rd study")
    @When("I {string} the {int}th study")
    public void expandCollapseStudy(String iconType, int studyNumber) {
        studyTreePage.expandCollapseStudy(iconType, studyNumber);
    }

    @When("I {string} the {int}st patient")
    @When("I {string} the {int}nd patient")
    @When("I {string} the {int}rd patient")
    @When("I {string} the {int}th patient")
    public void expandCollapsePatient(String iconType, int patientNumber) {
        studyTreePage.expandCollapsePatient(iconType, patientNumber);
    }

    @When("get the series count for the expanded study")
    public void getInstanceCountForExpandedStudy() {
        testContext.set("NumberOfInstances", studyTreePage.getNumberOfInstancesForStudy());
    }
}