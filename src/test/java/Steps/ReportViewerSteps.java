package Steps;

import Context.TestContext;
import Pages.ReportViewerPage;
import ReportEditors.ReportEditor;
import ReportEditors.ReportEditorFactory;
import ReportEditors.ReportEditorHelper;
import Utilities.Common;
import Utilities.Log;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static Utilities.Common.sleep;
import static org.junit.Assert.*;

public class ReportViewerSteps {

    TestContext testContext;
    ReportViewerPage reportViewerPage;
    ReportEditorFactory reportEditorFactory;

    public ReportViewerSteps(TestContext testContext) {
        this.testContext = testContext;
        reportViewerPage = new ReportViewerPage(testContext.getDriver());
        reportEditorFactory = new ReportEditorFactory();
    }

    @When("I enter below details in the {string} fields")
    public void enterReportFields(String reportName, DataTable table) {
        List<Map<String, String>> reportDetails = table.asMaps(String.class, String.class);
        reportViewerPage.fillReportDetails(reportName, reportDetails);
        Log.printInfo("Entered values using data table in respective fields");
        testContext.set("ReportDetails", reportDetails);
        testContext.set("ReportName", reportName);
    }

    @When("I open {string} section on the left panel")
    public void openSectionFromLeftPanel(String sectionName) {
        String reportName = testContext.get("ReportName");
        reportViewerPage.selectSectionFromLeftPanel(reportName, sectionName);
    }

    @When("I enter below details in the below fields under {string} tab in {string} section")
    public void enterReportDetails(String tabName, String sectionName) {
        String reportName = testContext.get("ReportName");
        List<String> sections = new ArrayList<>();
        List<String> tabs = new ArrayList<>();
        if (!testContext.containsKey("ReportEditorMaximized")) {
            reportViewerPage.maximizeReportEditorTab();
            testContext.set("ReportEditorMaximized", true);
        }
        if (!testContext.containsKey("SectionName") || !testContext.get("SectionName").equals(sectionName)) {
            reportViewerPage.selectSectionFromLeftPanel(reportName, sectionName);
            testContext.set("SectionName", sectionName);
            Common.sleep(1, TimeUnit.SECONDS);
        }
        reportViewerPage.selectTab(reportName, sectionName, tabName);

        Map<String, String> reportEntries = new ReportEditorHelper().getReportEntries(reportName, sectionName, tabName);
        ReportEditor reportEditor = reportEditorFactory.getReportEditor(reportName, sectionName, tabName);
        reportEntries = reportEditor.fillReport(reportEntries);
        reportViewerPage.logReportEntryScreenshot();
        List<Map<String, String>> reportEntriesList = new ArrayList<>();
        if (testContext.containsKey("ReportEntries")) {
            reportEntriesList = testContext.get("ReportEntriesList");
            reportEntriesList.add(reportEntries);
            testContext.set("ReportEntriesList", reportEntriesList);
            sections = testContext.get("Sections");
            sections.add(sectionName);
            testContext.set("Sections", sections);
            tabs = testContext.get("Tabs");
            tabs.add(tabName);
        } else {
            reportEntriesList.add(reportEntries);
            testContext.set("ReportEntriesList", reportEntriesList);
            testContext.set("ReportEntries", reportEntries);
            sections.add(sectionName);
            tabs.add(tabName);
            testContext.set("Sections", sections);
        }
        testContext.set("Tabs", tabs);
        testContext.set("SectionName", sectionName);
        testContext.set("TabName", tabName);
    }

    @When("I set below values in {string} tab under {string} section")
    public void setEDDOutsideValue(String tabName, String sectionName, DataTable table) {
        List<Map<String, String>> reportDetails = table.asMaps(String.class, String.class);
        Map<String, String> reportEntries = new LinkedHashMap<>();
        for (Map<String, String> reportDetail : reportDetails) {
            reportEntries.put(reportDetail.get("field"), reportDetail.get("value"));
        }
        String reportName = testContext.get("ReportName");
        reportViewerPage.selectSectionFromLeftPanel(reportName, "Patient information");
        reportViewerPage.selectTab(reportName, sectionName, tabName);
        ReportEditor reportEditor = reportEditorFactory.getReportEditor(reportName, sectionName, tabName);
        reportEditor.fillReport(reportEntries);
    }

    @When("close report viewer")
    @When("I close report viewer")
    public void closeReportViewer() {
        if (reportViewerPage.isSendFaxAndEmailDialogBoxDisplayed()) {
            reportViewerPage.CancelSendFaxEmail();
        }
        reportViewerPage.closeReportViewer();
        Common.sleep(2, TimeUnit.SECONDS);
        Log.printInfo("report viewer is closed");
    }

    @When("I click OK")
    public void pressEnter() {
        reportViewerPage.pressEnter();
    }

    @When("click on Don't Save")
    public void dontSaveReport() {
        reportViewerPage.dontSaveReport();
        Log.printInfo("clicked on Don't save button");
    }

    @When("I sign the report")
    public void signReport() {
        reportViewerPage.clickSignReport();
        reportViewerPage.clickSignReportYes();
        ZonedDateTime currentEstDateTime = Common.getCurrentEasternDateTime();
        testContext.set("FaxSentDateTimeEST", currentEstDateTime);
        testContext.set("IsReportSigned", true);
        Log.printInfo("Report is signed");
    }

    @When("I sign the report after changing the window preferences")
    public void signReportAfterChangingWindowPreferences() {
        reportViewerPage.clickSignReport();
        reportViewerPage.clickSignReportYes();
        Log.printInfo("Report is signed");
    }

    @Then("the Report Editor window should load automatically")
    @Then("the Report Editor window should load")
    public void reportEditorWindowShouldLoad() {
        reportViewerPage.waitForReportViewerToLoad();
        assertTrue(reportViewerPage.isReportWindowVisible());
        Log.printInfo("Report editor is loaded");
    }

    @Then("the Report Editor window should close")
    public void reportEditorWindowShouldClose() {
        if (reportViewerPage.isSendFaxAndEmailDialogBoxDisplayed()) {
            reportViewerPage.CancelSendFaxEmail();
        }
        reportViewerPage.waitForReportWindowToClose();
        assertFalse("Report editor window is visible", reportViewerPage.isReportWindowVisible());
    }

    @Then("report should contain the entered details")
    public void reportShouldContainEnteredDetails() {
        List<Map<String, String>> enteredReportDetails = testContext.get("ReportDetails");
        assertTrue("entered details are not present in report",
                reportViewerPage.areEnteredDetailsPresentInReport(enteredReportDetails));
        Log.printInfo("entered details are present in pdf reports");
    }

    @When("I select {string} in report editor")
    @When("I click on {string} in report editor")
    @When("I click on {string} in report editor left panel")
    public void clickOnOptionInReportEditor(String option) {
        reportViewerPage.selectReportContentOnReportViewer(option);
        testContext.set("Section", option);
        sleep(3, TimeUnit.SECONDS);
        Log.printInfo("Selected option in report");
    }

    @Then("report dynamic preview {string} display diagram")
    public void verifyDiagramIsNotPresentOnReport(String displayStatus) {
        if (displayStatus.equalsIgnoreCase("SHOULD")) {
            assertTrue("Image is not present in report", reportViewerPage.isDiagramPresentOnDynamicPreview());
            Log.printInfo("Dynamic preview image is present in report");
        } else if (displayStatus.equalsIgnoreCase("SHOULD NOT")) {
            assertFalse("Image is present in report", reportViewerPage.isDiagramPresentOnDynamicPreview());
            Log.printInfo("Dynamic preview image is not present in report");
        }
    }

    @When("I adjust the presentation diagram on report editor")
    public void adjustImageOnReport() {
        reportViewerPage.moveImageInTheReport();
        Log.printInfo("Image is dragged to desired location");
    }

    @Then("report dynamic preview should display adjusted diagram")
    public void verifyTheImageChanges() {
        assertTrue(reportViewerPage.isImageMovedOnReport());
        Log.printInfo("Dynamic preview is present in report after adjusting the report");
    }

    @Then("report should open in new window")
    public void reportViewerPage() {
        assertTrue(reportViewerPage.isReportViewerOpenedInNewWindow());
    }

    @Then("report dynamic preview should be stretched to {string} of the screen")
    @Then("report should be {string}")
    @Then("report dynamic preview should be {string} by default")
    public void verifyDefaultView(String viewPattern) {
        assertTrue(reportViewerPage.isReportDefaultViewSet(viewPattern));
    }

    @When("I click on {string} icon")
    public void clickOnViewTypeIcon(String viewPatternIcon) {
        reportViewerPage.clickOnDynamicPreviewIcon(viewPatternIcon);
        sleep(2, TimeUnit.SECONDS);
    }

    @Then("print report window should be displayed")
    public void isReportPrinted() {
        assertTrue(reportViewerPage.isPrintWindowDisplayed());
    }

    @Then("lab updates should be reflected on the Report")
    public void verifyTextOnReport() {
        assertTrue(reportViewerPage.isChangeReflectedOnReport("Lab Changes"));
    }

    @Then("Save Report Template window is opened")
    public void saveReportTemplate() {
        assertTrue(reportViewerPage.isSaveReportTemplateWindowVisible());
    }

    @When("type {string} in {string} on report editor")
    public void typeTextInField(String value, String fieldName) {
        testContext.set("PreliminaryReportInfo", value);
        reportViewerPage.typeTextInField(fieldName, value);
    }

    @When("I enter name {string} for normal template")
    public void enterName(String value) {
        reportViewerPage.enterReportTemplateName(value);
        testContext.set("TemplateName", value);
    }

    @Then("accreditation logo {string} be present in {string} report")
    public void verifyLogoInReport(String displayStatus, String reportName) {
        if (displayStatus.equalsIgnoreCase("should")) {
            assertTrue("Logo is not present", reportViewerPage.isAccreditationLogoPresentInReport(reportName));
            Log.printInfo("Logo is present in report");
        } else {
            assertFalse("Logo is present", reportViewerPage.isAccreditationLogoPresentInReport(reportName));
            Log.printInfo("Logo is not present in report");
        }
    }

    @When("I click on {string} under left panel")
    public void selectTabUnderReportViewer(String sectionName) {
        reportViewerPage.selectSectionFromLeftPanel(sectionName);
        testContext.set("Section", sectionName);
    }

    @Then("{string} should be displayed in the Report Dynamic Preview")
    @Then("Report Dynamic Preview should get updated with {string}")
    public void verifyReportDynamicPreview(String sectionName) {
        testContext.set("AmendedSection", sectionName);
        assertTrue(reportViewerPage.isReportEntryVisibleOnDynamicPreview(sectionName));
        Log.printInfo(sectionName + " section successfully verified");
    }

    @When("select a {string} from the {string} category")
    @When("select an {string} from the {string} category")
    public void selectCodeFromCategory(String code, String codeCategory) {
        reportViewerPage.selectCodeFromCategory(code, codeCategory);
    }

    @When("I type {string} under the {string}")
    @When("type {string} under the {string}")
    @When("I provide {string} under the {string}")
    @When("provide {string} under the {string}")
    @When("enter {string} under the {string}")
    public void providePhysicianRemarks(String remark, String remarkSection) {
        if (remark.contains("Amended")) {
            testContext.set("Amendment", remark);
        } else if (remark.contains("Server PDF Content Verification")) {
            testContext.set("Server PDF Content Verification", remark);
        }
        reportViewerPage.provideRemarks(remark, remarkSection);
    }

    @When("I select {string} under the {string} tab")
    @When("select {string} under the {string} tab")
    public void selectContentFromTab(String content, String tabName) {
        reportViewerPage.selectContentFromTab(content, tabName);
    }

    @Then("validation error should pop-up")
    public void verifyValidationErrorPopup() {
        assertTrue("Validation Popup is not visible", reportViewerPage.isValidationErrorPopupVisible());
    }

    @When("I click on {string} pop button")
    public void clickButtonOnDialog(String buttonName) {
        reportViewerPage.clickButtonOnDialog(buttonName);
    }

    @When("I press Ctrl+click on Heart rate in report")
    public void controlClickHeartRateLabel() {
        reportViewerPage.controlAndLeftClickHeartRateLabel();
    }

    @Then("phrase edit box should pop up")
    public void verifyPhraseEditBox() {
        assertTrue("Change phrase popup not present", reportViewerPage.isChangePhrasePopupVisible());
    }

    @When("I type {string} in phrase edit box")
    public void setTextInPhraseBox(String value) {
        reportViewerPage.enterPhrase(value);
    }

    @When("I click on OK")
    public void clickOkButton() {
        reportViewerPage.clickOKButton();
    }

    @Then("new phrase should appear on report")
    public void verifyPhraseIsChanged() {
        assertTrue("Phrase is not changed", reportViewerPage.isPhraseChanged());
    }

    @When("unselect {string} on report editor")
    @When("select {string} on report editor")
    public void selectDiagram(String checkboxText) {
        reportViewerPage.selectCheckbox(checkboxText);
    }

    @When("I click on {string} page icon")
    public void moveToNextPreviousPage(String pageDirection) {
        reportViewerPage.moveToPage(pageDirection);
    }

    @Then("preview window should move {string} by one page")
    public void isReportPreviewWindowMovedToOtherPage(String direction) {
        assertTrue("Report Preview has not moved " + direction,
                reportViewerPage.isReportPreviewMovedToOtherPage(direction));
    }

    @Then("send fax and email window should be displayed")
    public void isSendFaxAndEmailDialogBoxDisplayed() {
        reportViewerPage.waitForSendFaxAndEmailDialog();
        assertTrue(reportViewerPage.isSendFaxAndEmailDialogBoxDisplayed());
    }

    @When("I right click on {string} label")
    public void rightClickLabels(String labelName) {
        reportViewerPage.rightClickMeasurementLabel(labelName);
    }

    @Then("{string} should turn purple")
    public void verifyLabelColorChange(String labelName) {
        assertTrue("Label color not changed", reportViewerPage.isLabelSelected(labelName));
    }

    @When("I click on Impressions tab")
    public void clickImpressionTab() {
        reportViewerPage.clickImpressionsTab();
    }

    @When("I click Free text subtab")
    public void clickFreeTextSubTab() {
        reportViewerPage.clickFreeTextSubTab();
    }

    @Then("list should display all the labels that had been right clicked")
    public void verifyListIsDisplayed() {
        assertTrue("List not displayed", reportViewerPage.isFreeTextListDisplayed());
    }

    @When("I click on the hamburger on one of the impressions and move it down")
    public void changeList() {
        reportViewerPage.changeListElements();
    }

    @Then("that line item should move down and relocate to the place where it was dropped")
    public void verifyListIsChanged() {
        assertTrue("List not changed", reportViewerPage.isListChanged());
    }

    @Then("the order should also change in the report")
    public void verifyListOnDynamicPreview() {
        assertTrue("List not changed", reportViewerPage.isListChangedOnDynamicPreview());
    }

    @Then("preliminary report diagram {string} be present in dynamic report")
    public void verifyAnatomyPrelimDiagram(String displayStatus) {
        if (displayStatus.equalsIgnoreCase("SHOULD")) {
            assertTrue("Image is not present in report", reportViewerPage.isAnatomyPrelimDiagramPresentInPreview());
        } else {
            assertFalse("Image is present in report", reportViewerPage.isAnatomyPrelimDiagramPresentInPreview());
        }
    }

    @When("I deselect {string} option")
    @When("I select {string} option")
    public void clickOption(String option) {
        reportViewerPage.selectSegmentInTheReport(option);
    }

    @When("I mark lesion on aorta in the report")
    public void clickOnAorta() {
        reportViewerPage.markLesionOnAorta();
    }

    @Then("all the elements should also be present in dynamic report")
    @Then("marked lesion should be visible in the prelim report dynamic preview")
    public void isChangeVisibleInPreview() {
        assertTrue("Changes not visible", reportViewerPage.isMarkLesionVisibleInDynamicPreview());
    }

    @Then("final report diagram {string} be present in dynamic report")
    public void verifyAnatomyDiagram(String displayStatus) {
        if (displayStatus.equalsIgnoreCase("SHOULD")) {
            assertTrue("Image is not present in report", reportViewerPage.isAnatomyFinalDiagramPresentInPreview());
        } else {
            assertFalse("Image is present in report", reportViewerPage.isAnatomyFinalDiagramPresentInPreview());
        }
    }

    @Then("marked lesion should be visible in the final report dynamic preview")
    public void isChangeVisible() {
        assertTrue("Changes not visible", reportViewerPage.isChangeVisibleInPreview());
    }

    @When("I click on the select Referring Physician Icon")
    @When("click on the select Referring Physician Icon")
    public void clickReferringPhysicianIcon() {
        reportViewerPage.clickReferringPhysicianIcon();
    }

    @When("select a referring physician from the list of physicians")
    public void selectReferringPhysician() {
        reportViewerPage.selectReferringPhysician();
    }

    @Then("assigned physician should be present in dynamic report preview")
    @Then("selected physician should be displayed in Referred By field on dynamic preview")
    public void verifyAssignedPhysicianIsDisplayedOnDynamicPreview() {
        assertTrue("Referred By field is empty in dynamic preview",
                reportViewerPage.isAssignedRefPhysicianDisplayedOnReport());
    }

    @Then("assigned referring physician is listed under selected contacts")
    public void isPhysicianPresentInSelectedContacts() {
        assertTrue("Referring physician is not listed in selected contacts",
                reportViewerPage.isPhysicianPresentInSelectedContacts());
    }

    @When("I click send button on fax email dialog")
    public void clickSendButtonOnFaxEmailDialog() {
        reportViewerPage.clickSendButtonOnFaxEmailDialog();
        testContext.set("FaxSentDateTimeEST", Common.getCurrentEasternDateTime());
    }

    @When("switch to {string} subtab")
    @When("I switch to {string} subtab")
    public void switchToSubtab(String subtabName) {
        reportViewerPage.switchToSubtab(subtabName);
    }

    @When("I {string} it as {string}")
    public void saveRenameTemplate(String actionName, String templateName) {
        testContext.set("Template Name", templateName);
        reportViewerPage.performActionOnTemplate(actionName, templateName);
    }

    @Then("{string} should be available in the Template box")
    public void isTemplateFolderAvailableUnderTemplateBox(String checklistItem) {
        switch (checklistItem.toUpperCase()) {
            case "TEMPLATE":
                assertTrue(testContext.get("Template Name") + " is not saved",
                        reportViewerPage.isTemplateSaved(testContext.get("Template Name")));
                break;

            case "FOLDER":
                assertTrue(testContext.get("Folder Name") + " is not saved",
                        reportViewerPage.isNewFolderCreated(testContext.get("Folder Name")));
                break;
        }
    }

    @When("click on Save")
    public void saveReport() {
        reportViewerPage.saveReport();
        Log.printInfo("clicked on save button");
    }

    @When("I create a {string} under Template Box")
    public void createNewFolder(String folderName) {
        testContext.set("Folder Name", folderName);
        reportViewerPage.createNewFolderUnderTemplateBox(testContext.get("Template Name"), folderName);
    }

    @When("I click on {string} button")
    public void savePhraseToFolder(String buttonName) {
        reportViewerPage.clickOnTemplateActionButton(buttonName);
    }

    @Then("the phrase should get saved to the created folder")
    public void isPhraseSavedToCreatedFolder() {
        assertTrue(reportViewerPage.isPhraseSavedToFolder());
    }

    @When("I select the created folder")
    public void selectCreatedFolder() {
        reportViewerPage.selectFolder(testContext.get("Folder Name"));
    }

    @Then("report should be signed by {string}")
    public void isFieldPresentInPreview(String labelName) {
        assertTrue("Signed By field not present", reportViewerPage.isSignedByFieldPresent(labelName));
    }

    @When("I click on {string} subtab")
    public void clickOnSubTab(String subTabName) {
        reportViewerPage.clickOnSubtab(subTabName);
    }

    @Then("the report contains the corrected information")
    public void verifyInformation() {
        assertTrue("Information not present", reportViewerPage.isReportCorrected());
    }

    @Then("the corrected report should be signed by {string}")
    public void isSignedByFieldSameInCorrectedReport(String labelName) {
        assertTrue("Signed By field not present", reportViewerPage.isSignedByFieldSameInCorrectedReport(labelName));
    }

    @When("select {string} tab")
    public void selectTab(String tabName) {
        reportViewerPage.selectTab(tabName);
        testContext.set("TabName", tabName);
    }

    @When("select {string} nomenclature")
    public void selectNomenclature(String option) {
        reportViewerPage.selectNomenclature(option);
    }

    @When("set below segment observations on report")
    public void selectSegments(DataTable table) {
        List<Map<String, String>> segmentsList = table.asMaps(String.class, String.class);
        reportViewerPage.selectSegments(segmentsList);
    }

    @Then("{string} should be reflected on the dynamic preview")
    public void verifyChangesOnReport(String fieldName) {
        assertTrue(reportViewerPage.isChangeReflectedOnReport(fieldName));
    }

    @When("I click on {string} button on {string} tab")
    public void clickSetButton(String buttonName, String tabName) {
        reportViewerPage.selectTab(tabName);
        reportViewerPage.scrollToField(buttonName);
        reportViewerPage.clickButton(buttonName);
    }

    @When("I click on {string} option under {string} on {string} tab")
    public void selectRadioOptionUnderRegionOnTab(String radioOption, String region, String tabName) {
        reportViewerPage.selectTab(tabName);
        reportViewerPage.maximizeReportEditorTab();
        Rectangle regionBounds = testContext.containsKey(region + "region") ? testContext.get(region + "region")
                : reportViewerPage.getRegionBounds(region);
        reportViewerPage.selectRadioOption(regionBounds, radioOption);
        reportViewerPage.restoreReportEditorTab();
        testContext.set(region + "region", regionBounds);
    }

    @Then("all segments should be displayed in red")
    public void verifyIfSegmentColorChanged() {
        assertTrue("Segments are not displayed in red", reportViewerPage.areAllSegmentsSetToNormal());
    }

    @Then("all the segments observations should be cleared")
    public void verifyClearedSegments() {
        assertTrue("Phrases are not displayed on report", reportViewerPage.areSegmentObservationsCleared());
        assertFalse("Phrases are displayed on report", reportViewerPage.areSegmentObservationsDisplayedOnReport());
    }

    @Then("only {string} observations are displayed")
    public void unselectOptionPhraseChange(String observation) {
        assertTrue(reportViewerPage.isChangeReflectedOnReport(observation));
    }

    @When("close the send fax and email window")
    public void closeSendFaxAndEmailWindow() {
        reportViewerPage.CancelSendFaxEmail();
    }

    @Then("report should contain the amendments performed")
    public void verifyAmendments() {
        assertTrue(reportViewerPage.isReportEntryVisibleOnDynamicPreview(testContext.get("AmendedSection")));
        Log.printInfo(testContext.get("AmendedSection") + " section successfully verified");
    }

    @Then("{string} are available on report editor under {string} tab")
    public void verifyOptionsOnReport(String option, String tabName) {
        reportViewerPage.maximizeReportEditorTab();
        reportViewerPage.selectTab(tabName);
        assertTrue(reportViewerPage.isOptionPresentOnReportEditor(option));
        reportViewerPage.restoreReportEditorTab();
    }

    @When("select below checkbox under {string} on {string} tab")
    public void selectCheckboxUnderRegionOnTab(String region, String tabName, DataTable table) {
        List<String> checkboxText = table.asList();
        reportViewerPage.selectTab(tabName);
        reportViewerPage.selectCheckboxList(checkboxText, region);
    }

    @When("I select below checkbox on {string} subtab")
    @When("select below checkbox on {string} tab")
    public void selectCheckboxOnTab(String tabName, DataTable table) {
        List<String> checkboxText = table.asList();
        reportViewerPage.selectTab(tabName);
        reportViewerPage.selectCheckboxList(checkboxText);
    }

    @Then("{string} subtab should be present under below structure tabs in report editor left panel")
    public void verifyTabOnStructureTabs(String subtabName, DataTable table) {
        List<String> structureTabList = table.asList();
        assertTrue(reportViewerPage.isSubtabPresent(structureTabList, subtabName));
    }

    @Then("spelling checker mark {string} be displayed")
    public void verifySpellingCheckerMark(String spellingCheckerMarkDisplayStatus) {
        switch (spellingCheckerMarkDisplayStatus.toUpperCase()) {
            case "SHOULD NOT":
                assertFalse(reportViewerPage.isSpellingCheckerMarkDisplayed());
                break;

            case "SHOULD":
                assertTrue(reportViewerPage.isSpellingCheckerMarkDisplayed());
                break;
        }
    }

    @When("I clear the Favourites Text Area")
    public void clearTextFromField() {
        reportViewerPage.clearTextField();
    }

    @When("I right click on incorrect word")
    public void rightClickOnIncorrectWord() {
        reportViewerPage.rightClickOnIncorrectWord();
    }

    @Then("spelling suggestion window should be displayed")
    public void verifySuggestionWindow() {
        assertTrue(reportViewerPage.isSuggestionWindowDisplayed());
    }

    @When("I select the correct word")
    public void selectCorrectWord() {
        reportViewerPage.selectCorrectWord();
    }

    @Then("verify the patient name is in the format of LastName,FirstName MiddleName")
    public void verifyPatientNameFormat() {
        assertTrue("Patient Name Format Incorrect", reportViewerPage.isPatientNameFormatCorrect());
    }

    @Then("send fax and email window should not be displayed")
    public void verifySendFaxAndEmailDialogBoxPresent() {
        assertFalse("Send fax and Email Dialog Box is Displayed",
                reportViewerPage.isSendFaxAndEmailDialogBoxDisplayed());
    }

    @Then("previous report data should be available")
    public void verifyPreviousReport() {
        reportViewerPage.waitForReportViewerToLoad();
        assertTrue(reportViewerPage.isBaselineTextDisplayed());
        assertTrue(reportViewerPage.isExaminationDateDisplayed());
    }

    @Then("the txt report contents should be same as that of pdf report")
    @Then("all entered details should be present in PDF and TXT reports")
    public void verifyReportPdfAndText() {
        assertTrue(areReportContentsPresentInPdfAndTextReport());
    }

    @Then("all observations marked by {string} for below header values should be correct with entered details in PDF and TXT reports")
    public void verifyTableMarkers(String marker, DataTable columnHeaders) {
        boolean isObservationTableCorrect = (isObservationTableCorrectInPdfAndTextReport(marker, columnHeaders));
        boolean areReportEntriesCorrect = areReportContentsPresentInPdfAndTextReport();
        assertTrue(isObservationTableCorrect && areReportEntriesCorrect);
    }

    @Then("all observations marked by {string} for below header values should be correct with entered details in PDF and TXT reports for {string}")
    public void verifyTableMarkersAndAbnormalities(String marker, String sectionHeader, DataTable columnHeaders) {
        boolean isObservationTableCorrect = (isObservationTableCorrectInPdfAndTextReport(marker, columnHeaders));
        boolean areReportEntriesCorrect = areTableContentsPresentInPdfAndTextReport(sectionHeader);
        assertTrue(isObservationTableCorrect && areReportEntriesCorrect);
    }

    @Then("all entered details for {string} should be present in PDF and TXT reports")
    public void verifyEnteredDetailsForPdfAndTextReport(String sectionHeader) {
        assertTrue(areTableContentsPresentInPdfAndTextReport(sectionHeader));
    }

    @Then("only selected images should be present in the report")
    @Then("only selected image should be present in the report")
    public void verifyImageOnReport() {
        List<BufferedImage> bufferedImage = testContext.get("bufferedImageList");
        for (BufferedImage image : bufferedImage) {
            assertTrue("Image verification failed", reportViewerPage.isImagePresentOnReport(image));
        }
    }

    @Then("report should contain information from last signed preliminary report")
    public void verifyPreliminaryReportInfoInFinalReport() {
        assertTrue(reportViewerPage.isPreliminaryReportInfoDisplayedInFinalReport());
    }

    @When("accept the report validation and close the viewer")
    public void acceptReportValidationError() {
        reportViewerPage.acceptReportValidationError();
    }

    @When("I click on print button")
    public void clickOnPrintButton() {
        reportViewerPage.clickOnPrintButton();
    }

    @When("I open the referring physician address book")
    public void openReferringPhysicianAddressBook() {
        reportViewerPage.openReferringPhysicianAddressBook();
    }

    @Then("referred physician should be displayed under address book")
    public void verifyReferringPhysicianNameUnderAddressBook() {
        assertTrue(reportViewerPage.isReferredPhysicianDisplayedUnderAddressBook());
        reportViewerPage.clickOnCancelButton();
    }

    @Then("horizontal scroll bar should not be displayed")
    public void verifyIfHorizontalScrollbarIsDisplayed() {
        assertFalse("Horizontal scroll bar is displayed", reportViewerPage.isHorizontalScrollbarIsDisplayed());
    }

    @When("I double click on report dynamic preview")
    public void doubleClickDynamicPreview() {
        reportViewerPage.doubleClickOnDynamicPreview();
    }

    @Then("{string} {string} field {string} be present on the {string} editor window")
    public void verifyLabelNotPresentOnReportEditor(String fieldName, String fieldType, String fieldAvailability,
                                                    String reportName) {
        if (fieldAvailability.equalsIgnoreCase("SHOULD")) {
            assertTrue("Label is missing on " + reportName + " editor",
                    reportViewerPage.isLabelPresentOnReportEditor(reportName, fieldName, fieldType));
        } else if (fieldAvailability.equalsIgnoreCase("SHOULD NOT")) {
            assertFalse("Label is present on " + reportName + " editor",
                    reportViewerPage.isLabelPresentOnReportEditor(reportName, fieldName, fieldType));
        }
    }

    @Then("updated location of Diastolic blood pressure should be reflected")
    public void verifyLocationOfDiastolicPressureOnReportEditor() {
        assertTrue("Interchanged location of diastolic pressure incorrect",
                reportViewerPage.isLocationOfDiastolicPressureCorrectOnReportEditor());
    }

    @Then("assigned physician should be present in report editor window")
    public void verifyAssignedRefPhysicianPresentOnReportEditor() {
        assertTrue("Assigned physician incorrect on editor window",
                reportViewerPage.isAssignedRefPhysicianPresentOnReportEditor());
    }

    @Then("the right clicked labels should be present under impressions tab")
    public void verifyLabelsAreAdded() {
        assertTrue("Right Clicked Labels Are Not Present", reportViewerPage.isLabelPresentInImpressionsTab());
    }

    @Then("below measurements should be auto-checked")
    public void verifyMeasurementsAreAutoChecked(DataTable table) {
        List<String> autoCheckedMeasurementList = table.asList();
        for (String autoCheckedMeasurement : autoCheckedMeasurementList) {
            assertTrue(reportViewerPage.isMeasurementAutoChecked(autoCheckedMeasurement));
        }
    }

    @Then("all entered details should be present in viewer report PDF and server PDF")
    public void verifyReportPdfAndServerPdf() {
        String pdfReportContent = testContext.get("Server PDF Content Verification");
        String pdfContentFromDicomViewer = reportViewerPage.getPdfReportContents();
        Log.printInfo("PDF Contents");
        Log.printInfo("--------------------------------------------------------------------------------------");
        Log.printInfo(pdfContentFromDicomViewer);
        boolean isReportEntryPresentInPdfReport = pdfContentFromDicomViewer.contains(pdfReportContent);
        String serverPdfContent = reportViewerPage
                .getServerPdfReportContents(testContext.get("ExportedPdfFromZipFile"));
        Log.printInfo("Server PDF Contents");
        Log.printInfo("--------------------------------------------------------------------------------------");
        Log.printInfo(serverPdfContent);
        boolean isReportEntryPresentInServerPdfReport = serverPdfContent.contains(pdfReportContent);
        assertTrue(isReportEntryPresentInPdfReport && isReportEntryPresentInServerPdfReport);
    }

    @When("I select the below codes")
    public void selectCodes(DataTable table) {
        List<String> codeNameList = table.asList();
        reportViewerPage.selectCodesFromIndicationsHistory(codeNameList);
    }

    @Then("the selected codes should be present on dynamic preview")
    public void verifyCodesArePresentOnDynamicPreview() {
        assertTrue("Codes not present", reportViewerPage.isIndicationHistoryCodePresentInDynamicPreview());
    }

    @Then("{string} measurement value and unit should be reflected on the dynamic preview")
    @Then("{string} measurement value should be reflected on the dynamic preview")
    public void verifyMeasurementValueIsPopulatedOnDynamicPreview(String fieldName) {
        assertTrue("Measurement Value Not Populated", reportViewerPage.isMeasurementValuePopulatedOnPreview(fieldName));
        Log.printInfo("Measurement value is populated in dynamic preview");
    }

    @Then("report editor should open within the same window")
    public void waitForEditorToLoad() {
        reportViewerPage.waitForReportEditorToLoad();
    }

    @When("I click on the bullseye of {string} measurement label")
    @When("click on the bullseye of {string} measurement label")
    public void clickBullsEye(String measurementLabel) {
        reportViewerPage.clickOnBullsEye(testContext.get("ReportName"), measurementLabel);
    }

    @Then("{string} measurement label should be auto-checked")
    public void verifyLabelIsAutoChecked(String autoCheckedMeasurementLabel) {
        assertTrue(reportViewerPage.isLabelAutoChecked(testContext.get("ReportName"), autoCheckedMeasurementLabel));
    }

    @Then("the {string} should be present under findings section in dynamic preview")
    public void isLabelNameAndValuePresentUnderFindingsSection(String measurementType) {
        assertTrue("Label name and value not present",
                reportViewerPage.isLabelNameAndValuePresentUnderFindingsSection(measurementType));
    }

    @Then("measurement tab should turn yellow")
    public void verifyMeasurementsTabIsYellow() {
        assertTrue("Measurements tab has not turned yellow", reportViewerPage.isMeasurementsTabYellow());
    }

    @Then("{string} should be auto-checked under measurements tab")
    public void verifyLabelNameIsAutoCheckedUnderMeasurementsTab(String autoCheckedLabelName) {
        assertTrue("Label Names are not auto-checked",
                reportViewerPage.isLabelNameAutoPopulatedUnderMeasurementTab(autoCheckedLabelName));
    }

    @When("I hover on {string} measurement")
    @When("hover on {string} measurement")
    public void hoverOnMeasurement(String measurementPatternFile) {
        reportViewerPage.hoverOnMeasurement(measurementPatternFile);
    }

    @When("select the bullseye")
    public void selectBullseye() {
        reportViewerPage.selectBullseye();
    }

    @Then("bullseye captured value should appear in the text field of the selected measurement in the report editor")
    public void verifyMeasurementValueByBullseye() {
        String fieldValue = reportViewerPage.getBullseyeMeasurement();
        String expectedMeasurement = testContext.get("Measurement");
        assertEquals(expectedMeasurement, fieldValue);
    }

    private boolean isObservationTableCorrectInPdfAndTextReport(String marker, DataTable columnHeaders) {
        List<String> headers = columnHeaders.asList();
        List<Map<String, String>> reportEntriesList = testContext.get("ReportEntriesList");
        Map<String, String> reportEntries = reportEntriesList.get(0);
        Map<String, String> pdfTableValues = reportViewerPage.getPdfReportContentsWithTableData(headers, marker);
        Map<String, String> txtTableValues = reportViewerPage.getTextReportContentsWithTableData(headers);
        String txtReportContents = reportViewerPage.getTextReportContents();
        boolean isTableObservationPresentInPdf = true;
        boolean isTableObservationPresentInText = true;
        for (Map.Entry<String, String> entry : pdfTableValues.entrySet()) {
            if (!reportEntries.containsKey(entry.getKey())) {
                Log.logScenarioInfo("Field name '" + entry.getKey() + "' not present");
                isTableObservationPresentInPdf = false;
                continue;
            }
            isTableObservationPresentInPdf = reportEntries.get(entry.getKey()).equalsIgnoreCase(entry.getValue())
                    && isTableObservationPresentInPdf;
            if (!reportEntries.get(entry.getKey()).equalsIgnoreCase(entry.getValue())) {
                Log.logScenarioInfo("Marking for field " + entry.getKey() + " is incorrect");
            }
        }
        if (txtReportContents == null) {
            isTableObservationPresentInText = false;
            Log.logScenarioInfo("Observation table malformed in text report");
        } else {
            for (Map.Entry<String, String> entry : txtTableValues.entrySet()) {
                if (!reportEntries.containsKey(entry.getKey())) {
                    Log.logScenarioInfo("Field name '" + entry.getKey() + "' not present");
                    isTableObservationPresentInText = false;
                    continue;
                }
                isTableObservationPresentInText = reportEntries.get(entry.getKey()).equalsIgnoreCase(entry.getValue())
                        && isTableObservationPresentInText;
                if (!reportEntries.get(entry.getKey()).equalsIgnoreCase(entry.getValue())) {
                    Log.logScenarioInfo("Marking for field " + entry.getKey() + " is incorrect");
                }
            }
        }
        for (Map.Entry<String, String> entry : pdfTableValues.entrySet()) {
            reportEntries.remove(entry.getKey());
        }
        reportEntriesList.set(0, reportEntries);
        testContext.set("ReportEntriesList", reportEntriesList);
        if (isTableObservationPresentInPdf) {
            Log.logScenarioInfo("Observation table verified successfully in PDF report");
        }
        if (isTableObservationPresentInText) {
            Log.logScenarioInfo("Observation table verified successfully in Text report");
        }
        return isTableObservationPresentInPdf & isTableObservationPresentInText;
    }

    private boolean areReportContentsPresentInPdfAndTextReport() {
        String reportName = testContext.get("ReportName");
        List<String> sections = testContext.get("Sections");
        List<String> tabs = testContext.get("Tabs");
        List<Map<String, String>> reportEntriesList = testContext.get("ReportEntriesList");
        boolean isReportEntryPresentInTextReport = true, isReportEntryPresentInPdfReport = true;
        for (int index = 0; index < reportEntriesList.size(); index++) {
            String pdfContent = reportViewerPage.getPdfReportContents();
            String textContent = reportViewerPage.getTextReportContents();
            Log.printInfo("PDF Contents");
            Log.printInfo("--------------------------------------------------------------------------------------");
            Log.printInfo(pdfContent);
            Map<String, String> reportData = reportEditorFactory
                    .getReportEditor(reportName, sections.get(index), tabs.get(index))
                    .getTransformedReportValues(reportEntriesList.get(index));
            Log.logScenarioInfo("PDF report verification for tab: " + tabs.get(index));
            Log.logScenarioInfo(
                    "----------------------------------------------------------------------------------------");
            isReportEntryPresentInPdfReport = reportViewerPage.isReportEntryPresent(reportName, sections.get(index),
                    tabs.get(index), reportData, pdfContent) && isReportEntryPresentInPdfReport;
            Log.logScenarioInfo(
                    "----------------------------------------------------------------------------------------");
            Log.logScenarioInfo("Text report verification for tab: " + tabs.get(index));
            Log.logScenarioInfo(
                    "----------------------------------------------------------------------------------------");
            isReportEntryPresentInTextReport = reportViewerPage.isReportEntryPresent(reportName, sections.get(index),
                    tabs.get(index), reportData, textContent) && isReportEntryPresentInTextReport;
            Log.logScenarioInfo(
                    "----------------------------------------------------------------------------------------");
        }
        return isReportEntryPresentInPdfReport && isReportEntryPresentInTextReport;
    }

    @Then("the height field in the report should not contain an extra zero")
    public void verifyHeightFieldInReportContainsAnExtraZero() {
        assertTrue("Height Field contains an extra zero in report",
                reportViewerPage.isExtraZeroPresentInHeightFieldInReport());
        Log.printInfo("Height Field does not contains an extra zero in report");
    }

    @Then("the height field in dynamic preview should not contain an extra zero")
    @Then("the height field in dynamic preview should not contain an extra zero and should be displayed in cm")
    public void verifyHeightFieldInPreviewDoesNotContainsAnExtraZero() {
        assertTrue("Height Field contains an extra zero in dynamic preview",
                reportViewerPage.isExtraZeroPresentInHeightFieldInPreview());
        Log.printInfo("Height Field does not contains an extra zero in dynamic preview");
    }

    @Then("{string} value should be reflected on the dynamic preview")
    public void verifyHeightValueIsPopulatedOnPreview(String heightValue) {
        assertTrue("Height value not populated", reportViewerPage.isHeightValuePopulatedOnPreview(heightValue));
        Log.printInfo("Height value populated on preview");
    }

    @Then("BSA value should not be updated on report editor")
    @Then("BSA value should be reflected on report editor")
    public void verifyBSACalculatedValueIsReflectedInReport() {
        assertTrue("BSA value not present in report", reportViewerPage.isBSACalculatedValueReflectedInReport());
        Log.printInfo("BSA value is present in report");
    }

    @Then("the weight and BSA field in the dynamic preview should have the same unit and value as prelim report")
    public void verifyWeightAndBSAHaveSameUnitAndValueInFinalReport() {
        assertTrue("Weight and BSA does not have same value and unit in final report",
                reportViewerPage.isUnitAndValueOfWeightAndBSASameInFinalReport());
        Log.printInfo("Weight and BSA have same value and unit in final report");
    }

    @Then("the height field in dynamic preview should not contain an extra zero when the value is entered in three digit or in decimal")
    public void verifyHeightContainsExtraZeroOrNot() {
        List<Map<String, String>> enteredReportDetails = testContext.get("ReportDetails");
        String heightValue = enteredReportDetails.get(0).get("value");
        assertTrue("Height Field contains an extra zero in dynamic preview",
                reportViewerPage.isExtraZeroPresentInHeightFieldInPreview(heightValue));
    }

    @When("I press {string} key")
    public void pressKey(String keyButton) {
        reportViewerPage.pressKeyButton(keyButton);
    }

    @Then("no element should get deleted from the {string}")
    public void verifyIfElementDeletedWhenNoElementIsSelected(String viewElements) {
        assertTrue("Marked elements are deleted", reportViewerPage.isElementDeletedWithoutSelecting(viewElements));
    }

    @When("I select {string} from the anatomy diagram")
    public void selectMarkedElement(String element) {
        reportViewerPage.selectMarkedElement(element);
    }

    @Then("the marked element should get selected")
    public void verifyElementIsSelected() {
        assertTrue("Element is not selected", reportViewerPage.isMarkedElementHighlightedInRed());
    }

    @Then("the selected element {string} get deleted from {string}")
    public void verifyIfSelectedElementIsDeleted(String displayStatus, String viewElements) {
        if (displayStatus.equals("should")) {
            assertTrue("Selected Element is not deleted", reportViewerPage.isSelectedElementDeleted(viewElements));
        } else if (displayStatus.equals("should not")) {
            assertTrue("Marked elements are deleted", reportViewerPage.isElementDeletedWithoutSelecting(viewElements));
        }
    }

    @When("I click on Ok button for the confirmation")
    public void clickOkButtonForClearAllConfirmation() {
        reportViewerPage.clickOkButtonForClearAllConfirmation();
    }

    @Then("all the elements should get deleted from the {string}")
    public void verifyAllElementsAreDeleted(String viewElements) {
        assertTrue("All Elements are not deleted", reportViewerPage.isAllElementsDeleted(viewElements));
    }

    @When("I annotate {string} in the report")
    public void annotateDiagram(String value) {
        reportViewerPage.annotateOnAnatomyDiagram(value);
    }

    @When("I mark {string} in the report")
    public void markStent(String element) {
        reportViewerPage.markElementInReport(element);
    }

    @Then("all the marked elements should be present in dynamic preview")
    public void verifyAllElementsAreMarked() {
        assertTrue("All the elements are not marked in dynamic preview",
                reportViewerPage.areAllElementsMarkedInDynamicPreview());
    }

    @When("I drag the selected element to another location {string} the image")
    public void dragSelectedElementInsideImage(String targetLocation) {
        reportViewerPage.dragSelectedElement(targetLocation);
    }

    @Then("the selected element {string} get dragged to the targeted location")
    public void verifyIfElementIsDragged(String displayStatus) {
        if (displayStatus.equals("should")) {
            assertTrue("Element is not dragged within the image", reportViewerPage.isElementDraggedWithinTheImage());
        } else if (displayStatus.equals("should not")) {
            assertTrue("Element is dragged outside the image", reportViewerPage.isElementDraggedOutsideImage());
        }
    }

    @When("I save the report")
    public void clickSaveReport() {
        reportViewerPage.clickSaveReport();
    }

    private boolean areTableContentsPresentInPdfAndTextReport(String sectionHeader) {
        String reportName = testContext.get("ReportName");
        List<String> sections = testContext.get("Sections");
        List<String> tabs = testContext.get("Tabs");
        List<Map<String, String>> reportEntriesList = testContext.get("ReportEntriesList");
        boolean isReportEntryPresentInTextReport = true, isReportEntryPresentInPdfReport = true;
        for (int index = 0; index < reportEntriesList.size(); index++) {
            String pdfTableContents = reportViewerPage.getPdfObservationTableData(sectionHeader);
            String textTableContents = reportViewerPage.getTextReportContents();
            Log.printInfo("PDF Contents");
            Log.printInfo("--------------------------------------------------------------------------------------");
            Map<String, String> reportData = reportEditorFactory
                    .getReportEditor(reportName, sections.get(index), tabs.get(index))
                    .getTransformedReportValues(reportEntriesList.get(index));
            Log.logScenarioInfo("PDF report verification for tab: " + tabs.get(index));
            Log.logScenarioInfo(
                    "----------------------------------------------------------------------------------------");
            isReportEntryPresentInPdfReport = reportViewerPage.isReportEntryPresent(reportName, sections.get(index),
                    tabs.get(index), reportData, pdfTableContents) && isReportEntryPresentInPdfReport;
            Log.logScenarioInfo(
                    "----------------------------------------------------------------------------------------");
            Log.logScenarioInfo("Text report verification for tab: " + tabs.get(index));
            Log.logScenarioInfo(
                    "----------------------------------------------------------------------------------------");
            isReportEntryPresentInTextReport = reportViewerPage.isReportEntryPresent(reportName, sections.get(index),
                    tabs.get(index), reportData, textTableContents) && isReportEntryPresentInTextReport;
            Log.logScenarioInfo(
                    "----------------------------------------------------------------------------------------");
        }
        return isReportEntryPresentInPdfReport && isReportEntryPresentInTextReport;
    }

    @Then("{string} rank {string} get calculated by hadlock method")
    @Then("{string} rank {string} get calculated by papageorghiou method")
    @Then("{string} rank {string} be present on dynamic preview of final report")
    @Then("{string} rank {string} be present on the amended report")
    @Then("{string} rank {string} get calculated")
    public void verifyIsRankCalculated(String measurementName, String displayStatus) {
        if (displayStatus.equalsIgnoreCase("should")) {
            assertTrue("Rank is not calculated", reportViewerPage.isRankCalculated(measurementName));
        } else {
            assertFalse("Rank is calculated", reportViewerPage.isRankCalculated(measurementName));
        }
        reportViewerPage.doubleClickOnDynamicPreview();
    }

    @Then("BPD and HC fields should be marked in red")
    public void verifyFieldsAreBorderedInRed() {
        assertTrue("Fields are not bordered", reportViewerPage.isFieldBorderedInRed());
    }

    @When("I increase the number of fetus by {int}")
    public void clickOnIncreaseFetusButton(Integer number) {
        reportViewerPage.setNumberOfFetus(number);
    }

    @Then("the BPD's rank calculating method {string} be changed")
    public void verifyBPDCalculatingMethodIsChanged(String displayStatus) {
        if (displayStatus.equalsIgnoreCase("should")) {
            assertTrue("BPD calculating method not changed",
                    reportViewerPage.isBPDPercentileCalculatingMethodChanged());
        } else {
            assertTrue("BPD calculating method is changed", reportViewerPage.isBPDPercentileCalculatingMethodChanged());
        }
    }

    @Then("other fields should have their default rank calculating method as Hadlock")
    public void verifyOthersHaveSameCalculatingMethod() {
        assertTrue("Other fields have their default calculating method changed",
                reportViewerPage.isOtherFieldsCalculatingMethodChanged());
    }

    @When("select {string} with date {string} and time {string} for comparison")
    public void selectReportForComparison(String reportName, String date, String time) {
        reportViewerPage.selectReportForComparison(reportName, date, time);
    }

    @Then("all {string} subtabs should have asterisk")
    public void verifyIfSubtabsHaveAsterisk(String subtabName) {
        assertTrue(reportViewerPage.isSubtabsHaveAsterisk(subtabName));
    }

    @Then("{string} field under Classification section is unselected and highlighted with red rectangle")
    public void verifyFieldIsHighlightedWithRectangle(String fieldName) {
        assertTrue(reportViewerPage.isFieldHighlightedWithRectangle(fieldName));
    }

    @Then("{string} text {string} be present in Free Text")
    public void verifyTextNotPresentInField(String text, String visibilityStatus) {
        boolean isElementPresent = reportViewerPage.isTextPresentInFreeText(text);
        assertEquals(visibilityStatus.equalsIgnoreCase("SHOULD"), isElementPresent);
    }

    @When("I maximize report editor")
    public void clickMaximizeButtonOnReportEditor() {
        reportViewerPage.maximizeReportEditorTab();
    }

    @Then("below fields should be highlighted with red rectangle")
    public void verifyFieldsAreHighlightedWithRectangle(DataTable fields) {
        List<String> fieldsList = fields.asList();
        for (String field : fieldsList) {
            assertTrue(reportViewerPage.isFieldHighlightedWithRectangle(field));
        }
    }

    @Then("{string} field should change to vidistar icon")
    @Then("{string} field should have a vidistar icon")
    @Then("{string} field should have a machine icon")
    public void verifyIsIconVisible(String measurementLabel) {
        assertTrue("Icon not visible", reportViewerPage.isIconPresentOnMeasurementLabel(measurementLabel));
    }

    @When("I click on mean calculation icon of {string} measurement field")
    public void clickMeanCalculationIcon(String measurementLabel) {
        reportViewerPage.clickOnMeanCalculationIcon(measurementLabel);
    }

    @Then("Mean Calculation dialog should open")
    public void verifyMeanCalculationDialogIsVisible() {
        assertTrue("Dialog not present", reportViewerPage.isMeanCalculationDialogVisible());
    }

    @Then("the checkbox of the machine icon value should be checked")
    public void isMachineIconValueSelected() {
        assertTrue("CheckBox is not checked with machine icon in mean calculation dialog",
                reportViewerPage.getIndexOfSelectedRowHavingMachineIcon() > -1);
    }

    @When("I move the mouse cursor out of the measurement field")
    public void moveMouseOffset() {
        reportViewerPage.moveMouseByOffset(-80, 0);
    }

    @When("I click on the vidistar icon checkbox")
    public void clickOnVidistarIconCheckbox() {
        int vidistarIconRowIndex = reportViewerPage.getIndexOfVidistarIconRow();
        reportViewerPage.selectUnselectCheckboxValueItem(vidistarIconRowIndex);
    }

    @Then("the checkbox of the vidistar icon value should be checked")
    public void isVidistarIconValueSelected() {
        assertTrue("CheckBox is not checked with vidistar icon in mean calculation dialog",
                reportViewerPage.getIndexOfSelectedRowHavingVidistarIcon() > -1);
    }

    @When("I click on {string} button to close the mean calculation dialog")
    public void clickButtonToCloseMeanCalculationDialog(String buttonName) {
        reportViewerPage.clickButtonOnDialog(buttonName);
    }

    @Then("procedures should be reflected in the report preview")
    public void verifyProcedureObservationsOnReportPreview(DataTable table) {
        List<Map<String, String>> procedureDetails = table.asMaps(String.class, String.class);
        assertTrue(reportViewerPage.verifyProcedureObservationsOnReportPreview(procedureDetails));
    }

    @Then("pyp scan final report should get linked with latest signed prelim report report")
    @Then("pyp scan final report should get linked with signed prelim report")
    public void isPypScanFinalReportLinkedWithPrelimReport() {
        assertTrue("Pyp scan reports are not linked",
                reportViewerPage.isPypScanFinalReportLinkedWithSignedPrelimReportData());
    }

    @When("I click on {string} measurement field")
    public void clickOnMeasurementField(String measurementLabel) {
        reportViewerPage.clickOnMeasurementField(measurementLabel);
    }

    @Then("AV PV, AV Mean Vel, AV Peak Grad, AV Mean Grad field value should change to vidistar icon")
    @Then("AV PV, AV Mean Vel, AV Peak Grad, AV Mean Grad field value should not change to its original value")
    public void verifyIsValueChanged() {
        assertTrue("Value is changed", reportViewerPage.isValueChangedAfterClickingOnMeasurementField());
    }

    @Then("the value for {string} greyed out field should not be updated")
    @Then("{string} auto-calculated field should have a vidistar icon")
    @Then("{string} measurement field should be greyed out and should have a fx sign")
    @Then("{string} greyed out measurement field should display vidistar icon")
    public void verifyIfVidistarIconIsPresentForGreyedOutField(String measurementLabel) {
        assertTrue("Vidistar icon is not present",
                reportViewerPage.isVidistarIconVisibleForGreyedOutField(measurementLabel));
    }

    @Then("mean calculation icon should not be visible")
    public void verifyIfMeanCalculationIconIsPresent() {
        assertFalse("Mean Calculation Icon is present", reportViewerPage.isMeanCalculationIconPresent());
    }

    @Then("{string} measurement fields should not display mean sign for greyed out fields")
    public void verifyIfMeanSignIsPresent(String measurementLabel) {
        assertTrue("Mean Sign is present for greyed out field",
                reportViewerPage.isMeanSignVisibleForGreyedOutField(measurementLabel));
    }

    @Then("below fields should be present in {string} report")
    public void areFieldsPresent(String reportName, DataTable table) {
        List<Map<String, String>> fieldDetails = table.asMaps(String.class, String.class);
        assertTrue("Report fields validation failed", reportViewerPage.areFieldsPresentInReport(reportName,
                fieldDetails, testContext.get("Section") + "\\" + testContext.get("TabName")));
        Log.printInfo("All fields are successfully validated");
    }

    @Then("umbilical artery {string} should be displayed in report preview")
    public void isUmbilicalArteryDisplayed(String componentName) {
        assertTrue(componentName + " is not displayed",
                reportViewerPage.isUmbilicalArteryComponentDisplayed(componentName));
    }

    @When("press the tab key")
    public void pressTabKey() {
        reportViewerPage.pressTabKey();
    }

    @When("edit each line in free text area")
    public void editLinesInFreeTextArea() {
        reportViewerPage.editLinesInFreeTextArea();
    }

    @When("type multiple lines in {string} on report editor GUI")
    public void typeTextInFreeTextArea(String fieldName) {
        reportViewerPage.typeTextInFreeTextArea(fieldName);
    }

    @When("edit each line in free text area using keyboard arrow keys")
    public void editLinesInFreeTextAreaUsingKeyboardArrowKey() {
        reportViewerPage.editLinesInFreeTextAreaUsingKeyboardArrowKey();
    }

    @When("copy paste the content in the {string}")
    public void copyPasteContentInField(String fieldName) {
        Log.printInfo("Pasting content in field " + fieldName);
        reportViewerPage.copyPasteContentInField();
    }

    @When("I restore report editor")
    public void clickRestoreButtonOnReportEditor() {
        reportViewerPage.restoreReportEditorTab();
    }

    @Then("the graph decorator {string} appear")
    public void verifyIfGraphDecoratorIsPresent(String displayStatus) {
        if (displayStatus.equalsIgnoreCase("should")) {
            assertTrue("Graph Decorator icon is not present", reportViewerPage.isGraphDecoratorIconPresent());
        } else {
            assertFalse("Graph decorator icon is present", reportViewerPage.isGraphDecoratorIconPresent());
        }
    }

    @When("I click on the graph decorator of {string} measurement field")
    public void clickOnGraphDecoratorIcon(String measurementLabel) {
        reportViewerPage.clickOnGraphDecoratorIcon(measurementLabel);
    }

    @Then("the graph of {string} measurement field {string} be displayed on dynamic preview")
    public void verifyIsGraphDisplayedOnDynamicPreview(String measurementLabel, String displayStatus) {
        if (displayStatus.equalsIgnoreCase("should")) {
            assertTrue("Graph not displayed on preview",
                    reportViewerPage.isGraphDisplayedForMeasurementLabel(measurementLabel));
        } else {
            assertFalse("Graph is displayed on preview",
                    reportViewerPage.isGraphDisplayedForMeasurementLabel(measurementLabel));
        }
    }

    @Then("the graph decorator of {string} measurement field {string} be visible on the report")
    public void verifyIfGraphDecoratorIsPresentForCalculatedMeasurementLabel(String measurementLabel,
                                                                             String displayStatus) {
        if (displayStatus.equalsIgnoreCase("should")) {
            assertTrue("Graph decorator not present",
                    reportViewerPage.isGraphDecoratorIconPresentForCalculatedMeasurementLabel(measurementLabel));
        } else {
            assertFalse("Graph decorator not present",
                    reportViewerPage.isGraphDecoratorIconPresentForCalculatedMeasurementLabel(measurementLabel));
        }
    }

    @When("I select the below charts in the {string} under the {string} region")
    public void selectChartToBeDisplayedInReportPreview(String reportName, String region, DataTable table) {
        List<Map<String, String>> chartDetails = table.asMaps(String.class, String.class);
        testContext.set("ChartDetails", chartDetails);
        testContext.set("Region", region);
        reportViewerPage.selectCharts(reportName, chartDetails, reportViewerPage.getRegionBounds(reportName, region));
        reportViewerPage.restoreReportEditorTab();
        Common.sleep(1, TimeUnit.SECONDS);
    }

    @Then("selected charts {string} should be present in report preview")
    public void areSelectedChartsDisplayedInReportPreview(String historyStatus) {
        List<Map<String, String>> selectedChartDetails = testContext.get("ChartDetails");
        List<String> chartsList = selectedChartDetails.stream()
                .map(chart -> chart.get("field") + " chart " + historyStatus).collect(Collectors.toList());
        assertTrue(reportViewerPage.areSelectedChartsPlotsDisplayedInReport(chartsList));
    }

    @When("I {string} the {string} section under the left panel")
    public void expandCollapseSectionUnderLeftPanel(String action, String section) {
        String sectionName = action.equalsIgnoreCase("EXPAND") ? "Collapsed " + section : "Expanded " + section;
        reportViewerPage.expandCollapseSection(action, sectionName);
        testContext.set("Section", section);
    }

    @When("I click on {string} subsection")
    @When("click on {string} subsection")
    public void clickOnSubsectionUnderExpandedSection(String subsectionName) {
        reportViewerPage.selectSubsectionFromLeftPanel(testContext.get("Section"), subsectionName);
    }

    @Then("plots for selected charts should be present in report preview")
    public void arePlotsDisplayedOnCharts() {
        List<Map<String, String>> selectedChartDetails = testContext.get("ChartDetails");
        List<String> chartsList;
        if (testContext.get("StudyNumber") != null) {
            chartsList = selectedChartDetails.stream().map(chart -> testContext.get("Region") + "-" + chart.get("field")
                    + " plot for study " + testContext.get("StudyNumber")).collect(Collectors.toList());
        } else {
            chartsList = selectedChartDetails.stream()
                    .map(chart -> testContext.get("Region") + "-" + chart.get("field") + " plot for study ")
                    .collect(Collectors.toList());
        }
        assertTrue(reportViewerPage.areSelectedChartsPlotsDisplayedInReport(chartsList));
    }

    @Then("measurement trends should be displayed for above fields in report preview on clicking the respective trend icon")
    public void areMeasurementTrendsDisplayed() {
        List<Map<String, String>> reportDetails = testContext.get("ReportDetails");
        List<String> measurementList = new ArrayList<>();
        for (Map<String, String> measurementDetails : reportDetails) {
            measurementList.add(measurementDetails.get("field"));
        }
        for (String measurementTrend : measurementList) {
            reportViewerPage.clickOnGraphIconForMeasurementLabel(testContext.get("ReportName"), measurementTrend);
            assertTrue(measurementTrend + " trend is not displayed in the field",
                    reportViewerPage.isMeasurementTrendDisplayed(measurementTrend));
            reportViewerPage.clickOnTrendIcon();
            reportViewerPage.moveAwayFromField();
            assertFalse(measurementTrend + " trend icon is displayed in the field",
                    reportViewerPage.isTrendIconDisplayed());
        }
    }

    @When("I click on the trend icon of {string} measurement label")
    public void selectGraphForMeasurementLabel(String measurementLabel) {
        reportViewerPage.clickOnGraphIconForMeasurementLabel(testContext.get("ReportName"), measurementLabel);
    }

    @Then("measurement trend should be displayed")
    public void isMeasurementTrendDisplayed() {
        assertTrue("Trend icon is not displayed",
                reportViewerPage.isSignedMeasurementTrendDisplayedWhileAmendingReport());
    }

    @When("select the trend for the {string} field")
    public void selectMeasurementTrend(String measurementTrend) {
        reportViewerPage.moveAwayFromField();
        reportViewerPage.clickOnGraphIconForMeasurementLabel(testContext.get("ReportName"), measurementTrend);
    }

    @When("select the highlighted {string} tab")
    @When("switch back to the {string} tab")
    public void selectHighlightedTab(String tabName) {
        reportViewerPage.selectHighlightedTab(tabName);
        testContext.set("TabName", tabName);
    }

    @Then("fields should not be distorted in below subtabs")
    public void areFieldsDistorted(DataTable table) {
        List<Map<String, String>> fieldList = table.asMaps(String.class, String.class);
        for (Map<String, String> fields : fieldList) {
            String subtab = fields.get("subtab");
            String subtabFields = fields.get("subtab fields");
            reportViewerPage.clickOnSubtab(subtab);
            assertTrue(reportViewerPage.areFieldsDistorted(testContext.get("ReportName"), subtabFields));
        }
    }

    @When("respective fetuses should be added under left panel")
    public void waitForFetusToAddUnderLeftPanel() {
        reportViewerPage.waitForFetusToAddUnderLeftPanel();
    }

    @Then("{string} rank field should have a vidistar icon")
    public void verifyVidistarIconIsPresentForRankField(String rankField) {
        assertTrue("Rank Field does not displays vidistar icon",
                reportViewerPage.isVidistarIconPresentForCalculatedRankField(rankField));
    }

    @Then("purple fields {string} be displayed in {string}")
    public void verifyPurpleFieldsAreDisplayedInComparisonBox(String displayStatus, String displayRegion) {
        if (displayStatus.equalsIgnoreCase("should not")) {
            assertFalse("Purple fields are displayed in free text comparison box",
                    reportViewerPage.arePurpleFieldsDisplayedInComparisonBoxAndPreview(displayRegion));
        } else {
            assertTrue("Purple fields are not displayed in dynamic preview",
                    reportViewerPage.arePurpleFieldsDisplayedInComparisonBoxAndPreview(displayRegion));
        }
    }

    @Then("free text comparison box should display line item in the same hamburger")
    public void verifyLineItemIsDisplayedInSameHamburger() {
        assertTrue("Line item is not displayed in single hamburger",
                reportViewerPage.isLineItemDisplayedInSameHamburger());
    }

    @Then("line items should be displayed one below the other in {string}")
    public void verifyLineItemsAreDisplayedOneBelowTheOther(String displayRegion) {
        assertTrue("Line items are not displayed one below the other",
                reportViewerPage.areLineItemsDisplayedOneBelowTheOther(displayRegion));
    }

    @Then("line item should be displayed after the manual entry in {string}")
    public void verifyLineItemsDisplayedOneBelowTheOtherAfterManualEntry(String displayRegion) {
        assertTrue("Line items are not displayed one below the other after manual entry",
                reportViewerPage.areLineItemsDisplayedOneBelowTheOtherAfterManualEntry(displayRegion));
    }

    @When("type {string} in {string} box")
    public void typeTextInComparisonBox(String value, String fieldName) {
        reportViewerPage.typeTextInFreeTextComparisonBox(value, fieldName);
    }

    @Then("line items should be displayed in {string} in the amending report")
    public void verifyLineItemsAreDisplayedInAmendedReport(String displayRegion) {
        assertTrue("Line items are not displayed one below the other in the report",
                reportViewerPage.areLineItemsDisplayedInAmendedReport(displayRegion));
    }

    @Then("I delete {string} from free text comparison box")
    public void deleteTextFromComparisonBox(String patternFile) {
        reportViewerPage.deleteTextFromComparisonBox(patternFile);
    }

    @Then("line item should be displayed after the manual deletion in {string}")
    public void verifyLineItemsDisplayedOneBelowTheOtherAfterManualDeletion(String displayRegion) {
        assertTrue("Line items are not displayed one below the other after manual entry",
                reportViewerPage.areLineItemsDisplayedOneBelowTheOtherAfterManualDeletion(displayRegion));
    }

    @Then("below fields should be auto populated for Findings section")
    public void areFieldsAutopopulatedInFindingsSection(DataTable dataTable) {
        List<Map<String, String>> fieldDetails = dataTable.asMaps(String.class, String.class);
        assertTrue("Fields auto population verification failed",
                reportViewerPage.areFieldsAutopopulatedInFindingsSection(fieldDetails));
    }

    @Then("phrases related to rest imaging should be displayed on the preview")
    public void verifyCheckboxPhraseIsDisplayedInPreview() {
        assertTrue("Phrases are not displayed in the preview",
                reportViewerPage.isCheckboxPhrasePopulatedInDynamicPreview());
    }

    @Then("the report contains the amended data")
    public void verifyReportContainsAmendments() {
        assertTrue("Amendments are not displayed in the preview", reportViewerPage.isAmendmentPresentInReport());
    }

    @Then("the report {string} display {string} as report title")
    public void verifyReportTitleIsChanged(String displayStatus, String reportTitleName) {
        if (displayStatus.equalsIgnoreCase("should")) {
            assertTrue("Report title is not changed", reportViewerPage.isReportTitleChanged(reportTitleName));
        } else {
            assertFalse("Report title is changed", reportViewerPage.isReportTitleChanged(reportTitleName));
        }
    }

    @Then("the comparison baseline should display {string} as report title")
    public void verifyReportTitleIsDisplayedCorrectlyInReport(String reportTitleName) {
        assertTrue("Report title is not displayed correctly while creating new report",
                reportViewerPage.isReportTitleForComparisonBaselineDisplayed(reportTitleName));
    }

    @Then("report editor tab should open")
    public void verifyReportEditorTabIsOpened() {
        assertTrue("Report editor tab is not opened", reportViewerPage.isReportEditorTabOpened());
    }

    @Then("Mean Calculation dialog should close")
    public void verifyMeanCalculationDialogIsNotVisible() {
        assertFalse("Dialog not present", reportViewerPage.isMeanCalculationDialogVisible());
    }

    @Then("{string} measurement label should get auto populated")
    public void verifyMeasurementLabelIsAutoPopulated(String measurementPatternFile) {
        testContext.set("fieldValue", reportViewerPage.getFieldValue(measurementPatternFile));
        assertTrue("measurement label is not auto populated", reportViewerPage
                .verifyMeasurementLabelIsAutoPopulated(testContext.get("ReportName"), measurementPatternFile));
    }

    @Then("value should be present in the measurement table")
    public void verifyValueIsPresentInMeasurementTable() {
        assertEquals(reportViewerPage.verifyValueIsPresentInMeasurementTable(), testContext.get("fieldValue"));
    }

    @Then("{string} section should be visible on report editor")
    public void isSectionVisibleOnReportEditor(String sectionName) {
        assertTrue(sectionName + " section is not available on report editor",
                reportViewerPage.isSectionVisibleOnReportEditor(testContext.get("ReportName"), sectionName));
    }

    @Then("vertical scroll bar should not be displayed")
    public void isVerticalScrollbarPresent() {
        assertFalse("Verticle scrollbar is present", reportViewerPage.isVerticleScrollbarDisplayed());
    }

    @When("I resize report editor pane")
    public void resizeReportEditorPane() {
        reportViewerPage.resizeReportEditorPane();
    }

    @When("hover on the bullseye of {string} measurement label")
    public void hoverOnBullsEye(String measurementLabel) {
        reportViewerPage.hoverOnBullsEye(testContext.get("ReportName"), measurementLabel);
    }

    @Then("{string} should be displayed for the measurement label")
    public void verifyIsBullsEyeDisplayed(String bullseyeIcon) {
        assertTrue("Bullseye not displayed", reportViewerPage.isBullsEyeDisplayed(bullseyeIcon));
    }

    @When("I clear the text value field of {string} measurement label")
    public void clearMeasurementLabelText(String patternFile) {
        reportViewerPage.clearMeasurementLabelText(patternFile);
    }

    @Then("details entered in Fetus A should not be present in {string}")
    public void verifyFetusADataInOtherFetus(String fetusName) {
        List<Map<String, String>> reportDetails = testContext.get("ReportDetails");
        List<String> entryFields = new ArrayList<>();
        for (Map<String, String> measurementDetails : reportDetails) {
            entryFields.add(measurementDetails.get("field") + "_" + measurementDetails.get("type"));
        }
        for (String fieldName : entryFields) {
            assertFalse(fieldName + " is displayed in " + fetusName, reportViewerPage
                    .areFetusADetailsDuplicatedInOtherFetuses(testContext.get("ReportName"), fieldName));
        }
    }

    @Then("procedure report data {string} be present in {string} for the selected tab")
    public void isProcedureReportDataForSelectedTabPresentInFetus(String procedureReportDataStatus, String fetusName) {
        if (procedureReportDataStatus.equalsIgnoreCase("SHOULD")) {
            assertTrue("Procedure report data for " + testContext.get("TabName") + " is missing for " + fetusName,
                    reportViewerPage.isProcedureReportDataForSelectedTabPresentInFetus(testContext.get("ReportName"),
                            testContext.get("TabName")));
        } else if (procedureReportDataStatus.equalsIgnoreCase("SHOULD NOT")) {
            assertFalse("Procedure report data for " + testContext.get("TabName") + " is duplicated for " + fetusName,
                    reportViewerPage.isProcedureReportDataForSelectedTabPresentInFetus(testContext.get("ReportName"),
                            testContext.get("TabName")));
        }
    }

    @When("I click on {string} menu on report viewer")
    public void openReportsMenu(String menu) {
        reportViewerPage.openMenu(menu);
        testContext.set("Menu", menu);
    }

    @When("click on {string} option on report viewer")
    @When("click on {string} menu item on report viewer")
    public void selectReportOption(String menuOption) {
        reportViewerPage.selectMenuItemUnderMenu(testContext.get("Menu"), menuOption);
        testContext.set("MenuOption", menuOption);
    }

    @When("select the {string} sub option")
    public void selectSubMenuOption(String subMenuOption) {
        reportViewerPage.selectSubMenuOption(testContext.get("MenuOption"), subMenuOption);
        Log.printInfo("Sub menu option " + subMenuOption + " is clicked");
    }

    @Then("toggled image should be {string} from the report")
    @Then("toggled image should be {string} to the report")
    public void verifyIsImageToggledToReport(String status) {
        if (status.equalsIgnoreCase("added")) {
            assertTrue("Image is not toggled to the report", reportViewerPage.isImageToggledToReport());
        } else {
            assertFalse("Image is toggled to report", reportViewerPage.isImageToggledToReport());
        }
    }

    @Then("image should be toggled to the preview")
    public void verifyImageIsToggledToPreview() {
        assertTrue("Image is not toggled to the preview", reportViewerPage.isImageToggledToPreview());
    }

    @Then("default font size should be 15")
    public void verifyIsDefaultFontSizeOfAnatomyDiagramSetTo15() {
        assertTrue("Default font size is not set to 15", reportViewerPage.isDefaultFontSizeOfAnatomyDiagramSetTo15());
    }

    @Then("annotated text {string} be displayed in its default font size in {string}")
    public void verifyIsAnnotatedTextDisplayedInDefaultSize(String displayStatus, String viewText) {
        if (displayStatus.equals("should")) {
            assertTrue("Displayed text is not in its default size", reportViewerPage.isAnnotatedTextDisplayedInDefaultFontSize(viewText));
        } else if (displayStatus.equals("should not")) {
            assertFalse("Displayed text is in its default size", reportViewerPage.isAnnotatedTextDisplayedInDefaultFontSize(viewText));
        }
    }

    @Then("annotated text should be displayed in {string} in {string}")
    @Then("annotated text should be displayed in {string} font style in {string}")
    public void verifyIsTextAnnotatedInDesiredFontStyle(String fontStyle, String viewText) {
        assertTrue("Text is not annotated in desired font style", reportViewerPage.isTextAnnotatedInDesiredFontStyle(fontStyle, viewText));
    }

    @When("I annotate text {string} in {string} in the report")
    public void annotateText(String textValue, String region) {
        reportViewerPage.annotateText(textValue, region);
    }

    @Then("{string} font style icon should be highlighted in blue box")
    public void verifyIsFontStyleIconHighlightedInBlueBox(String iconName) {
        assertTrue("Font style icon is not highlighted", reportViewerPage.isFontStyleIconHighlightedInBlueBox(iconName));
    }

    @When("change the dropdown from {string} to {string} anatomy diagram")
    public void changeAnatomyDiagramFromDropdown(String defaultAnatomyName, String dropdownAnatomyName) {
        reportViewerPage.changeAnatomyDiagramFromDropdown(testContext.get("ReportName"), defaultAnatomyName, dropdownAnatomyName);
    }

    @Then("vidistar icon should be displayed for {string} field in the report editor")
    public void isVidistarIconDisplayedInField(String measurementField) {
        moveMouseOffset();
        assertTrue(" vidistar icon is not displayed in the field",
                reportViewerPage.isVidistarIconDisplayedInField(testContext.get("ReportName"), measurementField));
    }

    @Then("phrase and formula should be displayed for {string} greyed out measurement field")
    public void isPhraseAndFormulaDisplayedForGreyedOutField(String fieldName) {
        assertTrue("Phrase and formula not displayed", reportViewerPage.isPhraseAndFormulaDisplayedForGreyedOutField(fieldName));
    }

    @Then("measurement tools should be displayed for the field")
    public void verifyMeasurementToolsAreDisplayed() {
        assertTrue("Measurement tools not displayed", reportViewerPage.areMeasurementToolsDisplayedInField());
    }

    @When("I click on the machine icon checkbox")
    public void clickOnMachineIconCheckbox() {
        int machineIconRowIndex = reportViewerPage.getIndexOfMachineIconRow();
        reportViewerPage.selectUnselectCheckboxValueItem(machineIconRowIndex);
    }
}