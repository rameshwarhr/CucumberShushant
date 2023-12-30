package Steps;

import static Utilities.Common.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import Context.TestContext;
import Pages.DicomViewerPage;
import Utilities.Common;
import Utilities.Log;
import Utilities.Property;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DicomViewerSteps {

    TestContext testContext;
    DicomViewerPage dicomViewerPage;

    static boolean isViewerVersionCaptured = false;

    public DicomViewerSteps(TestContext testContext) {
        this.testContext = testContext;
        dicomViewerPage = new DicomViewerPage(testContext.getDriver());
    }

    @When("I change the layout to {string}")
    public void changeLayoutToOneToOne(String layout) {
        dicomViewerPage.selectScreenLayout(layout);
        Log.printInfo("layout of viewport is changed to:" + layout);
    }

    @When("get the count of total images")
    public void getCountOfTotalImagesInDicomViewer() {
        int imageCountBeforeAnnotation = dicomViewerPage.getImageCount();
        testContext.set("previousThumbnailCount", imageCountBeforeAnnotation);
    }

    @When("I navigate to {string} menu")
    @When("navigate to {string} menu")
    @When("I open the {string} menu")
    @When("open the {string} menu")
    public void openReportsMenu(String menuHeader) {
        dicomViewerPage.openMenu(menuHeader);
        testContext.set("Menu", menuHeader);
        Log.printInfo("Rich viewer " + menuHeader + " is opened");
    }

    @When("go to the {string} option")
    public void goToMenuOption(String menuOption) {
        dicomViewerPage.goToMenuOption(menuOption);
        Log.printInfo("Menu option " + menuOption + " is clicked");
    }

    @When("I select the {string} option")
    @When("select the {string} option")
    public void selectReportOption(String menuOption) {
        dicomViewerPage.selectMenuOption(menuOption);
        Log.printInfo("Menu option " + menuOption + " is clicked");
        testContext.set("ReportName", menuOption);
    }

    @When("I close the dicom viewer")
    @When("close the dicom viewer")
    public void closeDicomViewer() {
        Log.printInfo("closing dicom viewer");
        dicomViewerPage.closeDicomViewer();
        dicomViewerPage.waitForDicomViewerToClose();
        Log.printInfo("dicom viewer is closed");
    }

    @When("scroll viewer window to bottom")
    public void scrollViewerWindowToBottom() {
        dicomViewerPage.scrollWindowToBottom();
        sleep(10, TimeUnit.SECONDS);
        Log.printInfo("viewer window is scrolled");
    }

    @Then("viewer should load the study for patient")
    public void viewerShouldLoadStudyForPatient() {
        String patientLastName = testContext.get("RichPatientLastName");
        dicomViewerPage.waitForViewerToLoad();
        dicomViewerPage.waitForImageToLoad();
        dicomViewerPage.maximizeViewer();
        assertTrue(dicomViewerPage.isPatientInfoPresentOnViewer(patientLastName));
        Log.printInfo("Images are loaded on viewer properly");
        if (!isViewerVersionCaptured) {
            Property.setReportPropertyValue("Rich Viewer Version", dicomViewerPage.getViewerVersion());
            isViewerVersionCaptured = true;
        }
    }

    @Then("viewer should be blank as there are no images")
    public void verifyViewerLoadsWithNoImages() {
        dicomViewerPage.waitForViewerToLoad();
        assertTrue("Dicom window is not visible", dicomViewerPage.isDicomViewerWindowVisible());
        Log.printInfo("dicom viewer window is visible and patient images are loaded");
        assertTrue("Study is not blank", dicomViewerPage.isBlankStudyVisibleOnViewer());
        Log.printInfo("Images are loaded on viewer properly");
    }

    @When("import the PDF report file")
    public void importPDFReport() {
        dicomViewerPage.importPdfFile();
        testContext.set("IsReportSigned", true);
        Log.printInfo("imported PDF");
    }

    @Then("PDF report should be present in the report list")
    public void verifyPDFReportIsPresentInReportList() {
        dicomViewerPage.sortReportsInDescendingDateOrder();
        dicomViewerPage.clickReportsPanel();
        Log.printInfo("Reports panel in rich viewer is selected");
        assertTrue("Imported PDF is not present in reports panel", dicomViewerPage.isReportPresentInReportList());
        Log.printInfo("imported PDF is present in viewer");
    }

    @When("I select {string} thumbnail")
    public void selectThumbnail(String thumbnail) {
        dicomViewerPage.selectThumbnail(thumbnail);
        Log.printInfo("Thumbnail is selected");
    }

    @Then("I should be able to view only the {string} thumbnail")
    public void isSelectedThumbnailVisible(String thumbnail) {
        assertTrue("Selected thumbnail is not visible", dicomViewerPage.isSelectedThumbnailVisible(thumbnail));
        Log.printInfo("Thumbnail is selected");
    }

    @Then("Window Preferences dialog should open")
    public void verifyWindowPreferencesDialog() {
        assertTrue("Window Preferences Dialog is not present", dicomViewerPage.isWindowPreferencesWindowVisible());
        Log.printInfo("Window preference dialog box is present");
    }

    @When("I select {string} option under preferences")
    public void clickOnOptionInWindowPreferencesDialog(String option) {
        dicomViewerPage.clickOnAnyOptionInPreferencesWindow(option);
        Log.printInfo(option + " is selected in preferences under window");
        testContext.set("SelectedPreferencesOption", option);
    }

    @When("save the preferences")
    public void clickApplyAndClose() {
        dicomViewerPage.applyPreferences();
        Log.printInfo("click on Apply & close button after selecting a particular preference");
    }

    @Then("Report should open within the same window as the images and thumbnails")
    public void verifyReportAndViewerOpensInSameWindow() {
        dicomViewerPage.acceptTheEditReportPopupIfVisible();
        assertTrue("Report And Viewer not present in same window", dicomViewerPage.isReportAndViewerOpenInSameWindow());
        Log.printInfo("Report is opened in same window");
    }

    @When("I click on {string} option")
    public void selectToolbarOption(String option) {
        dicomViewerPage.selectToolbarOption(option);
        testContext.set("SelectedToolbarIcon", option);
    }

    @When("I click on image in the window")
    public void clickOnImageInDicomViewer() {
        dicomViewerPage.clickOnImageInViewer();
    }

    @When("I annotate {string} in image")
    public void annotateImageInDicomViewer(String value) {
        dicomViewerPage.setTextOnImageInDicomViewer(value);
    }

    @When("click on {string} option")
    public void clickOnContextMenuOptionOnImage(String option) {
        Common.sleep(2, TimeUnit.SECONDS);
        dicomViewerPage.clickOnContextMenuOptionOnImage(option);
        Common.sleep(2, TimeUnit.SECONDS);
    }

    @Then("annotated image should appear at the end of the study")
    public void annotatedImageAppearLast() {
        int previousThumbnailCount = testContext.get("previousThumbnailCount");
        int countAfterAnnotate = dicomViewerPage.getImageCount();
        assertEquals(previousThumbnailCount + 1, countAfterAnnotate);
        BufferedImage annotatedImage = dicomViewerPage.getImageOfViewport(0);
        testContext.set("AnnotatedImage", annotatedImage);
    }

    @Then("annotated image should still be at the end of the study")
    public void annotatedImageIsStillPresentAfterReLogin() {
        dicomViewerPage.scrollToLastImage();
        Common.sleep(3, TimeUnit.SECONDS);
        assertTrue("Annotated Image is not present",
                dicomViewerPage.isAnnotatedImageVisibleOnViewport(testContext.get("AnnotatedImage")));
    }

    @Then("{string} should be disabled")
    public void isMenuOptionEnabled(String menuItemName) {
        assertFalse(dicomViewerPage.isMenuOptionEnabled(menuItemName));
    }

    @When("I click on {string} button to stop the cineloop playing")
    @When("click on {string} button to stop the cineloop playing")
    @When("I click on {string} button to resume cineloop playing")
    public void clickOnCineButton(String cineButton) {
        dicomViewerPage.clickCineButton(cineButton);
        Common.sleep(2000, TimeUnit.MILLISECONDS);
        if (cineButton.equals("Stop")) {
            dicomViewerPage.waitForImageToLoad();
            int frameNumber = Integer.parseInt(dicomViewerPage.getCineFrameNumber());
            testContext.set("CineStoppedFrameNumber", frameNumber);
        }
    }

    @When("I click on {string} button in viewport {int} to start the cineloop")
    @When("click on {string} button in viewport {int} to start the cineloop")
    @When("I click on {string} button in viewport {int} to stop the cineloop")
    @When("click on {string} button in viewport {int} to stop the cineloop")
    public void clickOnCineButton(String cineButton, Integer viewportIndex) {
        testContext.set("viewportIndex", viewportIndex);
        dicomViewerPage.clickCineButton(cineButton, viewportIndex);
        dicomViewerPage.waitForImageToLoad();
        Common.sleep(500, TimeUnit.MILLISECONDS);
    }

    @Then("animation should {string} playing")
    @Then("image should remain {string}")
    @Then("cineloop should {string} playing")
    public void verifyCineLoopStatus(String cineStatus) {
        switch (cineStatus.toUpperCase()) {
            case "STOP":
            case "PAUSE":
            case "PAUSED":
                assertFalse(dicomViewerPage.isCinePlayingForPatient());
                break;
            case "START":
            case "PLAY":
            case "RESUME":
            case "REMAIN":
                assertTrue(dicomViewerPage.isCinePlayingForPatient());
        }
    }

    @When("move the cine slider {string}")
    public void moveCineSlider(String direction) {
        int frameNumber = Integer.parseInt(dicomViewerPage.getCineFrameNumber());
        testContext.set("InitialFrameNumber", frameNumber);
        Log.printInfo("Frame Number before moving slider: " + frameNumber);
        dicomViewerPage.moveCineSliderInDirection(direction);
        dicomViewerPage.waitForImageToLoad();
    }

    @Then("{string} frame should be displayed")
    public void verifyFrameMovement(String direction) {
        int initialFrameNumber = testContext.get("InitialFrameNumber");
        int currentFrameNumber = Integer.parseInt(dicomViewerPage.getCineFrameNumber());
        Log.printInfo("Frame Number after moving slider: " + currentFrameNumber);
        switch (direction.toUpperCase()) {
            case "NEXT":
                assertTrue(initialFrameNumber < currentFrameNumber);
                break;
            case "PREVIOUS":
                assertTrue(initialFrameNumber > currentFrameNumber);
                break;
            default:
                fail("Invalid direction");
        }
        testContext.set("CineStoppedFrameNumber", currentFrameNumber);
    }

    @Then("images are displayed in {string} layout")
    public void verifyLayout(String layout) {
        int viewPortCount = Integer.parseInt(layout.split(":")[0]);
        assertEquals(viewPortCount, dicomViewerPage.getImageCountOnViewer());

    }

    @When("I go to image number {int}")
    @When("go to image number {int}")
    public void goToImageNumber(Integer imageNumber) {
        dicomViewerPage.scrollToImage(imageNumber);
    }

    @Then("left panel {string} be visible on viewer")
    public void verifyLeftPanelExistence(String visibilityStatus) {
        boolean isElementPresent = dicomViewerPage.isLeftPanelPresentOnDicomViewer();
        assertEquals("Left Panel Verification Failed", visibilityStatus.equalsIgnoreCase("SHOULD"), isElementPresent);
    }

    @When("I click and drag mouse towards {string} on viewport")
    public void dragMouseOnViewport(String direction) {
        Common.sleep(5, TimeUnit.SECONDS);
        try {
            String selectedToolbarIcon = testContext.get("SelectedToolbarIcon");
            if (selectedToolbarIcon.equals("Brightness/Contrast")) {
                int initialViewportContrast = dicomViewerPage.getViewportContrast();
                int initialViewportBrightness = dicomViewerPage.getViewportBrightness();
                testContext.set("InitialViewportContrast", initialViewportContrast);
                testContext.set("InitialViewportBrightness", initialViewportBrightness);
                Log.printInfo("Initial viewport Contrast: " + initialViewportContrast);
                Log.printInfo("Initial viewport Brightness: " + initialViewportBrightness);

            }
            dicomViewerPage.dragMouseOnViewport(direction, selectedToolbarIcon.equals("Brightness/Contrast"));
            Common.sleep(2, TimeUnit.SECONDS);
        } catch (NullPointerException e) {
            Log.printError("selectedToolbarIcon is null");
        }
    }

    @Then("the image contrast should {string}")
    public void verifyImageContrast(String contrastAction) {
        int initialContrast = testContext.get("InitialViewportContrast");
        int viewportContrast = dicomViewerPage.getViewportContrast();
        Log.printInfo("Current viewport Contrast: " + viewportContrast);
        switch (contrastAction.toUpperCase()) {
            case "INCREASE":
            case "INCREASED":
                assertTrue(initialContrast < viewportContrast);
                break;
            case "DECREASE":
            case "DECREASED":
                assertTrue(initialContrast > viewportContrast);
        }
    }

    @Then("the image brightness should {string}")
    public void verifyImageBrightness(String brightnessAction) {
        int initialBrightness = testContext.get("InitialViewportBrightness");
        int viewportBrightness = dicomViewerPage.getViewportBrightness();
        Log.printInfo("Current viewport Brightness: " + viewportBrightness);
        switch (brightnessAction.toUpperCase()) {
            case "INCREASE":
            case "INCREASED":
                assertTrue(initialBrightness < viewportBrightness);
                break;
            case "DECREASE":
            case "DECREASED":
                assertTrue(initialBrightness > viewportBrightness);
        }
    }

    @When("double click on viewport {int}")
    @When("I double click on viewport {int}")
    public void doubleClickOnImage(int viewportIndex) {
        dicomViewerPage.doubleClickOnViewport(viewportIndex);
    }

    @Then("cine on viewport {int} should {string} playing")
    public void verifyCineLoopStatus(Integer viewportIndex, String cineStatus) {
        testContext.set("ViewportIndex", viewportIndex);
        switch (cineStatus.toUpperCase()) {
            case "STOP":
            case "PAUSE":
            case "PAUSED":
                assertFalse(dicomViewerPage.isCinePlayingForPatient(viewportIndex));
                break;
            case "PLAY":
            case "RESUME":
                assertTrue(dicomViewerPage.isCinePlayingForPatient(viewportIndex));
        }
    }

    @When("I double click on VTI section of the image")
    public void doubleClickOnParticularLocationOnImage() {
        dicomViewerPage.doubleClickOnParticularLocationOnImage(70, 400);
    }

    @Then("Template admin screen should load")
    public void verifyTemplateAdminTabExistence() {
        assertTrue("Template admin screen not present", dicomViewerPage.isTemplateAdminTabPresent());
    }

    @When("I select {string} configuration instance on the template admin screen")
    public void selectConfigurationInstanceFromList(String configurationInstanceName) {
        dicomViewerPage.selectConfigurationInstanceFromList(configurationInstanceName);
    }

    @When("select the {string} configuration instance parameter list")
    public void selectConfigurationInstanceParameter(String configurationInstanceParameterName) {
        dicomViewerPage.selectConfigurationInstanceParameterFromList(configurationInstanceParameterName);
    }

    @When("I click on browse button")
    @When("click three dots on the editor parameter")
    public void clickOnThreeDotsButton() {
        dicomViewerPage.clickThreeDotsOnEditor();
    }

    @Then("prelim report template GUI opens")
    public void verifyReportTemplatePresence() {
        assertTrue("Report GUI not present", dicomViewerPage.isReportTemplatePresent());
    }

    @Then("the image should {string}")
    @Then("the image should be {string}")
    public void verifyImageZoom(String imageStatus) {
        Common.sleep(2, TimeUnit.SECONDS);
        assertTrue(dicomViewerPage.isImageManipulated(imageStatus));
        dicomViewerPage.resetViewport();
    }

    @When("verify that {string} checkbox is checked")
    @When("select {string} checkbox")
    @When("I select {string} checkbox")
    public void isCheckboxEnabled(String checkBoxName) {
        dicomViewerPage.selectPreferencesCheckbox(checkBoxName);
    }

    @When("verify that {string} checkbox is unchecked")
    @When("unselect {string} checkbox")
    @When("I unselect {string} checkbox")
    public void isCheckboxUnchecked(String checkBoxName) {
        dicomViewerPage.unselectPreferencesCheckbox(checkBoxName);
    }

    @Then("below tabs are displayed in nuclear study after changes")
    @Then("below tabs are displayed in nuclear study")
    public void verifyTabsDisplayedInSequence(DataTable table) {
        List<String> expectedTabs = table.asList();
        assertEquals("Tabs are not displayed as expected", dicomViewerPage.getNuclearTabNamesDisplayed(), expectedTabs);
    }

    @When("I click on {string} tab")
    public void clickTabInNuclearStudy(String tabName) {
        dicomViewerPage.clickOnNuclearTab(tabName);
    }

    @Then("{string} should be loaded correctly on dicom viewer page")
    @Then("{string} images should be present")
    public void verifyIsImagePresentOnTabs(String tabImage) {
        dicomViewerPage.waitForNuclearImagesToLoad(tabImage);
        assertTrue("Image not present", dicomViewerPage.isImagePresentOnDicomViewer(tabImage));
    }

    @When("I select {string} option under {string} option")
    public void clickOnOption(String childOption, String parentOption) {
        dicomViewerPage.clickOnSubOptionInPreferences(childOption, parentOption);
    }

    @When("I click on {string} button on viewer")
    @When("select {string} under active tabs")
    public void selectOptionUnderActiveTabs(String option) {
        dicomViewerPage.selectOptionInActiveTab(option);
    }

    @When("click {string} button {int} times")
    public void clickButton(String button, Integer number) {
        dicomViewerPage.clickButtonInActiveTab(button, number);
    }

    @Then("viewer should load and images {string} be prefetched")
    public void verifyPrefetchingImages(String imagePrefetchingStatus) {
        String patientFirstName = testContext.get("RichPatientFirstName");
        String patientLastName = testContext.get("RichPatientLastName");
        if (imagePrefetchingStatus.equalsIgnoreCase("SHOULD NOT")) {
            assertTrue("Images are not prefetched", dicomViewerPage.isImagePrefetchingInProgress());
        } else if (imagePrefetchingStatus.equalsIgnoreCase("SHOULD")) {
            assertFalse("Images are prefetched", dicomViewerPage.isImagePrefetchingCompleted());
            assertTrue(dicomViewerPage.isPatientInfoPresentOnViewer(patientFirstName + " " + patientLastName));
        }
    }

    @When("I close the dicom viewer after prefetching is completed")
    public void closeDicomViewerAfterImagePrefetchingIsCompleted() {
        dicomViewerPage.waitTillImagesArePrefetched();
        dicomViewerPage.closeDicomViewer();
        dicomViewerPage.waitForDicomViewerToClose();
    }

    @When("I right click on the report from the Reports tab on viewer")
    public void rightClickOnReport() {
        dicomViewerPage.rightClickOnReportFromReportsTab("First Trimester Report");
    }

    @When("export report as {string}")
    public void exportReportAs(String reportFormat) {
        dicomViewerPage.exportReportAs(reportFormat);
    }

    @When("click on {string} option for report")
    public void selectOptionFromReportsRightClickMenu(String operation) {
        dicomViewerPage.performOperationOnReport(operation);
    }

    @When("I add a referring physician to selected contacts")
    public void addReferringPhysicianToSelectedContacts() {
        dicomViewerPage.addReferringPhysicianToSelectedContacts();
    }

    @When("click on Send button")
    public void clickSendFaxButton() {
        dicomViewerPage.clickSendButton();
    }

    @Then("distance measurement in {string} should be displayed")
    @Then("{string} completion should be displayed")
    @Then("{string} measurement should be displayed")
    public void verifyMeasurement(String measurement) {
        assertTrue(dicomViewerPage.isMeasurementDisplayed(measurement));
        dicomViewerPage.resetViewport();
    }

    @When("I draw {string} annotation on viewport")
    public void drawAnnotation(String annotationType) {
        dicomViewerPage.drawAnnotation(annotationType);
    }

    @Then("cineloop should resume playing from the point it was stopped")
    public void verifyCineloopResumedFromLastPoint() {
        int stoppedCineFrameNumber = testContext.get("CineStoppedFrameNumber");
        dicomViewerPage.clickCineButton("STOP");
        dicomViewerPage.waitForImageToLoad();
        int currentFrameNumber = Integer.parseInt(dicomViewerPage.getCineFrameNumber());
        dicomViewerPage.clickCineButton("PLAY");
        assertTrue(currentFrameNumber > stoppedCineFrameNumber);
    }

    @Then("the {string} image should be {string}")
    public void verifyMultipleManipulations(String firstManipulation, String secondManipulation) {
        assertTrue(dicomViewerPage.isImageManipulated(firstManipulation + " " + secondManipulation));
        dicomViewerPage.resetViewport();
    }

    @When("I set the speed for cine loop to {string}")
    public void setCineSpeed(String speed) {
        dicomViewerPage.setCineSpeed(speed);
    }

    @When("select {string} as units of measurements")
    public void selectMeasurementUnit(String unitOfMeasurement) {
        dicomViewerPage.selectUnitOfMeasurement(unitOfMeasurement);
    }

    @Then("measurement should be displayed in {string}")
    public void verifyMeasurementUnit(String unitOfMeasurement) {
        assertTrue(dicomViewerPage.isMeasurementDisplayedInSelectedUnit(unitOfMeasurement));
        dicomViewerPage.resetViewport();
    }

    @Then("signed report should be displayed under Reports tab")
    public void verifySignedReportUnderReportsTab() {
        dicomViewerPage.sortReportsInDescendingDateOrder();
        assertTrue(dicomViewerPage.isSignedReportDisplayedUnderReportsTab());
    }

    @When("I select {string} for amending")
    public void amendReport(String patternFile) {
        dicomViewerPage.amendReport(patternFile);
    }

    @When("I view the amended report")
    public void viewAmendedReport() {
        dicomViewerPage.viewAmendedReport();
    }

    @Then("the printer icon {string} appear")
    public void verifyIsPrinterIconVisible(String printerIconStatus) {
        if (printerIconStatus.equalsIgnoreCase("SHOULD")) {
            assertTrue("Printer Icon Not Present", dicomViewerPage.isPrinterIconVisible());
        } else {
            assertFalse("Printer Icon Present", dicomViewerPage.isPrinterIconVisible());
        }
    }

    @Then("the printer icon should appear on all images")
    public void verifyPrinterIcon() {
        for (int imageNumber = 1; imageNumber <= dicomViewerPage.getImageCount(); imageNumber++) {
            assertTrue("Printer icon not visible on " + imageNumber, dicomViewerPage.isPrinterIconVisible());
            dicomViewerPage.goToNextImage();
            Common.sleep(200, TimeUnit.MILLISECONDS);
        }
    }

    @Then("cineloop {string} start playing automatically")
    public void verifyCineAutomaticPlay(String status) {
        assertEquals(status.equalsIgnoreCase("SHOULD"), dicomViewerPage.isCinePlayingForPatient());
    }

    @When("I right click a completed and signed {string} report from reports list")
    public void rightClickOnReportFromList(String reportName) {
        dicomViewerPage.clickReportsPanel();
        Log.printInfo("Reports panel in rich viewer is selected");
        dicomViewerPage.rightClickOnReportFromList(reportName);
    }

    @Then("report modification popup window should appear")
    public void verifyPopupWindow() {
        assertTrue("Modification Popup not present", dicomViewerPage.isReportModificationWindowVisible());
    }

    @When("I click on {string} button on report modification popup")
    public void clickButton(String button) {
        dicomViewerPage.clickOnModificationPopUpButton(button);
    }

    @When("I right click corrected {string} from reports list")
    public void rightClickCorrectedReport(String reportName) {
        Common.sleep(5, TimeUnit.SECONDS);
        dicomViewerPage.clickReportsPanel();
        dicomViewerPage.rightClickOnReport(reportName, 1);
    }

    @When("I right click on {string} from reports list")
    public void rightClickReport(String reportName) {
        Common.sleep(1, TimeUnit.SECONDS);
        dicomViewerPage.sortReportsInDescendingDateOrder();
        dicomViewerPage.clickReportsPanel();
        dicomViewerPage.rightClickOnReport(reportName, 0);
        Common.sleep(1, TimeUnit.SECONDS);
    }

    @When("I click on change font button")
    public void clickChangeFontButton() {
        dicomViewerPage.clickChangeFontButton();
    }

    @When("set the font as {string}")
    public void setFontType(String fontType) {
        testContext.set("InitialFont", dicomViewerPage.getFontValue());
        dicomViewerPage.setFontType(fontType);
    }

    @When("set the font size as {string}")
    public void setFontSize(String fontSize) {
        testContext.set("InitialFontSize", dicomViewerPage.getFontSizeValue());
        dicomViewerPage.setFontSize(fontSize);
    }

    @Then("text annotation should be displayed in font {string} with size {string}")
    public void verifyTextAnnotationWithFont(String fontType, String fontSize) {
        dicomViewerPage.waitForViewerToLoad();
        assertTrue(dicomViewerPage.isTextAnnotationDisplayedWithFont(fontType, fontSize));
    }

    @When("I change default row count to {string}")
    public void changeDefaultRowCount(String rowCount) {
        testContext.set("DefaultRowCount", dicomViewerPage.getDefaultRowCount());
        dicomViewerPage.changeDefaultRowCount(rowCount);
    }

    @When("I change default column count to {string}")
    public void changeDefaultColumnCount(String columnCount) {
        testContext.set("DefaultColumnCount", dicomViewerPage.getDefaultColumnCount());
        dicomViewerPage.changeDefaultColumnCount(columnCount);
    }

    @Then("viewer row count should be {string}")
    public void verifyViewerRowCount(String expectedRowCount) {
        assertEquals(Integer.parseInt(expectedRowCount), dicomViewerPage.getDisplayedViewerRowCount());
    }

    @Then("viewer column count should be {string}")
    public void verifyViewerColumnCount(String expectedColumnCount) {
        assertEquals(Integer.parseInt(expectedColumnCount), dicomViewerPage.getDisplayedViewerColumnCount());
    }

    @When("I right click on {string} and view the menu options")
    public void viewReportOptions(String reportName) {
        dicomViewerPage.viewReportOptions(reportName);
    }

    @Then("{string} option {string} be displayed")
    public void verifyReportOptions(String reportOption, String displayStatus) {
        if (displayStatus.equalsIgnoreCase("should")) {
            assertTrue(dicomViewerPage.isReportOptionDisplayed(reportOption));
        } else {
            assertFalse(dicomViewerPage.isReportOptionDisplayed(reportOption));
        }
    }

    @When("select {string} option for previous report")
    @When("I select {string} option for previous report")
    public void selectPreviousReportOption(String reportOption) {
        dicomViewerPage.selectReportOption(reportOption);
        dicomViewerPage.selectSourceToPopulateData();
        dicomViewerPage.selectSourceToPopulateData();
        dicomViewerPage.acceptTheSystemLockPopupIfVisible();
    }

    @When("I drag the cineslider to end position on viewport {int}")
    public void dragCinesliderToEndPosition(Integer viewportIndex) {
        dicomViewerPage.moveCineSliderToEndPosition(viewportIndex);
    }

    @When("draw an arrow in the paused cineloop")
    public void drawAnnotationOnCineloop() {
        dicomViewerPage.clickOnViewport(testContext.get("viewportIndex"));
    }

    @When("I click on {string} button on the Restart required modal")
    @When("click on {string} button on the Restart required modal")
    public void clickButtonOnRestartViewerModal(String buttonName) {
        dicomViewerPage.clickButtonOnRestartViewerModal(buttonName);
    }

    @Then("printer icon should be displayed")
    public void isPrinterIconDisplayedUponImageToggling() {
        assertTrue(dicomViewerPage.isPrinterIconDisplayed());
    }

    @When("preview the selected image")
    public void previewSelectedImage() {
        dicomViewerPage.selectReportPrintPreview();
    }

    @When("I increase the left panel size to {int} percent")
    @When("I decrease the left panel size to {int} percent")
    public void setLeftPanelSize(Integer sizeInPercent) {
        dicomViewerPage.setLeftPanelWidth(sizeInPercent);
    }

    @Then("left panel size should be set to {int} percent of the viewer")
    public void verifyLeftPanelSize(Integer sizeInPercent) {
        double leftPanelWidthInPercent = dicomViewerPage.getLeftPanelSizeInPercent();
        assertTrue(Math.abs(sizeInPercent - leftPanelWidthInPercent) <= 1);
    }

    @When("save the DICOM SR report")
    @When("save the PDF report")
    @When("save the TXT report")
    public void saveReport() {
        dicomViewerPage.saveFile();
    }

    @Then("{int} viewports should be displayed")
    @Then("{int} viewport should be displayed")
    public void verifyViewportCount(Integer numberOfViewports) {
        assertTrue(dicomViewerPage.areViewportsDisplayedAsPerSelectedLayout(numberOfViewports));
    }

    @When("I right click on viewport {int}")
    public void rightClickOnImage(int viewportIndex) {
        testContext.set("viewportIndex", viewportIndex);
        dicomViewerPage.rightClickOnViewport(viewportIndex);
    }

    @When("I select {string} option from rich viewer right click menu")
    public void selectOptionFromViewerRightClickMenu(String option) {
        dicomViewerPage.selectOptionFromViewerRightClickMenu(option);
        BufferedImage viewportImage = dicomViewerPage.getImageOfViewport(testContext.get("viewportIndex"));
        List<BufferedImage> viewportImageList = testContext.containsKey("bufferedImageList")
                ? testContext.get("bufferedImageList") : new ArrayList<>();
        viewportImageList.add(viewportImage);
        Log.printInfo("viewportImage list---->" + viewportImageList.size());
        testContext.set("MenuOption", option);
        testContext.set("bufferedImageList", viewportImageList);
    }

    @When("I click on {string} option for {int} time\\(s)")
    public void clickOnScrollButton(String option, int clickCount) {
        testContext.set("clickCount", clickCount);
        testContext.set("imageNumber", dicomViewerPage.getImageNumber());
        dicomViewerPage.scrollByButton(option, clickCount);
    }

    @Then("next sequence of image should be displayed")
    public void verifyNextImageSequence() {
        int clickCount = testContext.get("clickCount");
        int imageNumber = testContext.get("imageNumber");
        assertEquals(clickCount + imageNumber, dicomViewerPage.getImageNumber());
    }

    @Then("previous sequence of image should be displayed")
    public void verifyPreviousImageSequence() {
        int clickCount = testContext.get("clickCount");
        int imageNumber = testContext.get("imageNumber");
        assertEquals(imageNumber - clickCount, dicomViewerPage.getImageNumber());
    }

    @When("I scroll the mouse wheel {string} for {int} time\\(s)")
    public void scrollMouseWheel(String option, int clickCount) {
        testContext.set("clickCount", clickCount);
        testContext.set("imageNumber", dicomViewerPage.getImageNumber());
        dicomViewerPage.scrollMouseWheel(option, clickCount);
    }

    @When("I scroll the scroll bar {string} for {int} time\\(s)")
    public void scrollByScrollBar(String option, int clickCount) {
        testContext.set("clickCount", clickCount);
        testContext.set("imageNumber", dicomViewerPage.getImageNumber());
        dicomViewerPage.scrollByScrollBar(option, clickCount);
    }

    @When("I drag {string} to new location")
    public void dragLabelOverOtherLabel(String labelName) {
        dicomViewerPage.dragLabelOverOtherLabel(labelName);
    }

    @Then("report template editor should load")
    public void verifyReportTemplatePostProcessingEditorPresentOnDicomViewer() {
        assertTrue("Report Template Post Processing Editor not present",
                dicomViewerPage.isReportTemplatePostProcessingEditorPresentOnDicomViewer());
    }

    @When("I delete {string} label")
    public void deleteLabelFromReportTemplatePostProcessingEditor(String labelName) {
        dicomViewerPage.deleteLabelFromReportTemplatePostProcessingEditor(labelName);
    }

    @When("I select {string} tab")
    public void selectTabOnDicomViewer(String tabName) {
        dicomViewerPage.selectTabOnDicomViewer(tabName);
    }

    @When("I unclick {string} button in nuclear toolbar")
    @When("I click {string} button in nuclear toolbar")
    public void clickNuclearToolBarButton(String button) {
        dicomViewerPage.clickNuclearStudyToolBarButton(button);
    }

    @Then("contour lines {string} appear on the images")
    public void verifyContourLinesAresPresent(String displayStatus) {
        if (displayStatus.equalsIgnoreCase("SHOULD")) {
            assertTrue("Contour Lines are not present on Images", dicomViewerPage.isContourLinePresentOnImage());
            Log.printInfo("Contour lines are present");
        } else {
            assertFalse("Contour Lines are present on Images", dicomViewerPage.isContourLinePresentOnImage());
            Log.printInfo("Contour lines are not present");
        }
    }

    @When("I click {string} menu in nuclear toolbar")
    public void clickNuclearToolBarMenuItem(String menu) {
        dicomViewerPage.clickNuclearStudyToolBarMenuItem(menu);
    }

    @Then("the study should re-process the images")
    public void waitForImagesToProcess() {
        assertTrue("Images are not processed", dicomViewerPage.areImagesProcessing());
        dicomViewerPage.waitForImagesProcessingToBeCompleted();
    }

    @Then("images should contain numbers")
    public void verifyNumberIsPresentOnImage() {
        assertTrue("Numbers are absent", dicomViewerPage.isNumberPresentOnImage());
        Log.printInfo("Numbers are present on image");
    }

    @Then("images should not contain numbers")
    public void verifyNumberIsNotPresentOnImage() {
        assertTrue("Numbers are absent", dicomViewerPage.isNumberNotPresentOnImage());
        Log.printInfo("Numbers are not present on image");
    }

    @When("click {int} times on {string} arrow of scroll bar under STRESS GATED section in viewport")
    @When("click {int} times on {string} arrow of scroll bar under SAX\\(APEX-BASE) section in viewport")
    public void clickScrollBarButton(int clickCount, String arrowButton) {
        BufferedImage viewportImage = dicomViewerPage.getImageWithinNuclearViewport();
        testContext.set("viewportImage", viewportImage);
        dicomViewerPage.clickScrollBarButton(clickCount, arrowButton);
    }

    @When("move {string} marker under STRESS GATED section in viewport")
    public void moveWhiteLine(String linePattern) {
        BufferedImage viewportImage = dicomViewerPage.getImageWithinNuclearViewport();
        testContext.set("viewportImage", viewportImage);
        dicomViewerPage.moveWhiteLineOfImage(linePattern);
    }

    @When("I drag the image box in viewport")
    @When("drag the image box under STRESS GATED in viewport")
    public void dragImageBox() {
        BufferedImage viewportImage = dicomViewerPage.getImageWithinNuclearViewport();
        testContext.set("viewportImage", viewportImage);
        dicomViewerPage.dragImageBoxUpwards();
    }

    @Then("image box should change accordingly in viewport")
    @Then("image should change accordingly in viewport")
    @Then("3d image should change accordingly in viewport")
    @Then("slice image should change accordingly in viewport")
    @Then("clip image should change in viewport")
    @Then("array slices should change accordingly in viewport")
    public void verifyIfImageChanged() {
        assertFalse("Image verification failed", dicomViewerPage
                .isImageChanged(dicomViewerPage.getNuclearViewportBounds(), testContext.get("viewportImage")));
    }

    @When("I go to image number {int} in secondary capture")
    public void goToImageNumberInSecondaryCapture(Integer imageNumber) {
        dicomViewerPage.scrollToImageInSecondaryCapture(imageNumber);
    }

    @When("I click and drag mouse towards {string} on viewport in secondary capture")
    public void dragMouseOnViewportInSecondaryCapture(String direction) {
        dicomViewerPage.dragMouseByOffset(direction);
    }

    @Then("viewer should load and get closed in {int} seconds")
    public void verifyViewerClosureAfterSessionTimeout(Integer sessionTimeout) {
        dicomViewerPage.waitForViewerToLoad();
        dicomViewerPage.waitForImageToLoad();
        sleep(sessionTimeout, TimeUnit.SECONDS);
        assertTrue(dicomViewerPage.isViewerClosed());
    }

    @When("I right click and select {string} option for first {int} viewports")
    public void selectOptionFromRightClickMenuForViewports(String option, int viewportIndex) {
        dicomViewerPage.selectOptionFromRightClickMenuOnViewports(option, viewportIndex);
    }

    @Then("images should display {string} on the top right for {int} viewport")
    @Then("images should display {string} on the top right for first {int} viewports")
    public void verifyExportIcon(String iconFile, int viewportIndex) {
        assertTrue("Export Icon not visible for viewport: " + viewportIndex,
                dicomViewerPage.isExportIconVisibleOnViewports(iconFile, viewportIndex));
    }

    @Then("Anonymization window should be displayed")
    public void isAnonymizationWindowVisible() {
        assertTrue("Anonymization window not visible", dicomViewerPage.isAnonymizationWindowVisible());
        testContext.set("capturedPatientInformationImage", dicomViewerPage.getPatientInformationBufferedImage());
        testContext.set("maskedImageCount", dicomViewerPage.getImageCount());
    }

    @When("I mask area of patient details")
    public void maskPatientDetailsArea() {
        dicomViewerPage.maskPatientDetailsArea();
    }

    @Then("selected area becomes blacked out")
    public void verifySelectedAreaIsMasked() {
        assertFalse(dicomViewerPage
                .isPatientInformationIsMaskedOnAnonymizationWindow(testContext.get("capturedPatientInformationImage")));
    }

    @Then("next images should also be masked")
    public void verifySubsequentImagesAreMaskedOnAnonymizationWindow() {
        assertFalse(dicomViewerPage
                .isSubsequentImagesAreMaskedOnAnonymizationWindow(testContext.get("capturedPatientInformationImage")));
    }

    @When("select the export location")
    public void selectExportLocation() {
        String option = testContext.get("ReportName");
        dicomViewerPage.exportAnonymizedImagesToFileLocation(option);
    }

    @Then("masked images should be downloaded")
    public void verifyDownloadedImagesAreMasked() {
        int maskedImageCount = testContext.get("maskedImageCount");
        for (int i = 1; i <= maskedImageCount; i++) {
            assertFalse(dicomViewerPage.isExportedImagesAreMasked(testContext.get("capturedPatientInformationImage"), i,
                    testContext.get("ReportName")));
            dicomViewerPage.clickClosePhotoViewerButton();
        }
    }

    @When("measure the value for {string} using the bullseye on the image")
    @When("I measure the value for {string} using the bullseye on the image")
    public void measureValue(String labelName) {
        dicomViewerPage.drawMeasurementOnImageUsingBullseye(labelName);
    }

    @When("I click on {string} button in a single monitor view")
    public void clickButtonInSingleMonitorView(String buttonName) {
        dicomViewerPage.clickShowPreviewAndImagesButtonInSingleMonitorView(buttonName);
    }

    @Then("{string} tab should be displayed by default")
    public void verifySelectedTab(String tabName) {
        assertTrue(dicomViewerPage.isTabSelectedForNuclearStudy(tabName));
    }

    @When("I exit dicom viewer")
    public void exitViewer() {
        dicomViewerPage.openMenu("File");
        dicomViewerPage.selectMenuOption("Exit");
    }

    @When("I click the {string} keyboard button")
    @When("click the {string} keyboard button")
    @When("I click the {string} keyboard button again")
    @When("I click the {string} arrow keyboard button")
    @When("click the {string} arrow keyboard button")
    public void performActionsUsingKeyboardKeys(String key) {
        testContext.set("OriginalImageNumber", dicomViewerPage.getImageNumber());
        switch (key.toUpperCase()) {
            case "RIGHT":
                dicomViewerPage.goToNextImage();
                break;
            case "LEFT":
                dicomViewerPage.goToPreviousImage();
                break;
            case "SPACEBAR":
                dicomViewerPage.playPauseCine();
                break;
        }
    }

    @Then("viewer should display the {string} image")
    public void isImageChangedUsingKeyboardControls(String direction) {
        int imageNumber = testContext.get("OriginalImageNumber");
        switch (direction.toUpperCase()) {
            case "NEXT":
                assertEquals(dicomViewerPage.getImageNumber(), imageNumber + 1);
                break;
            case "PREVIOUS":
                assertEquals(dicomViewerPage.getImageNumber(), imageNumber - 1);
                break;
        }
    }

    @When("draw a measurement on the image")
    public void drawLineOnImage() {
        dicomViewerPage.drawLineOnImage();
    }

    @Then("the distance and the label should appear on the image")
    public void verify() {
        String measurement = dicomViewerPage.getImageLabel();
        testContext.set("Measurement", measurement);
    }

    @When("I switch to web application window")
    @When("I switch to the viewer window")
    @When("I switch to the report window")
    public void switchBetweenWindows() {
        dicomViewerPage.switchBetweenWindows();
    }

    @Then("{string} option should not be present in DICOM viewer")
    public void verifyPreferenceIsNotPresentInDicomViewer(String checkBoxName) {
        assertFalse("Checkbox is present", dicomViewerPage.isCheckboxPresentOnWindowPreferences(checkBoxName));
    }

    @When("click on viewport {int}")
    @When("I click on viewport {int}")
    public void clickOnViewport(int viewportIndex) {
        dicomViewerPage.clickOnViewport(viewportIndex);
        testContext.set("ViewportIndex", viewportIndex);
        testContext.set("ImageNumber", dicomViewerPage.getImageNumber());
    }

    @Then("{string} set of images should be displayed")
    public void isNextPreviousSetOfImagesDisplayed(String imageSet) {
        int initialImageNumber = testContext.get("ImageNumber");
        int previousImageNumber = testContext.get("OriginalImageNumber");
        switch (imageSet.toUpperCase()) {
            case "NEXT":
                assertEquals(dicomViewerPage.getAllViewportElements().size() + initialImageNumber,
                        dicomViewerPage.getImageNumber());
                break;

            case "PREVIOUS":
                assertEquals(previousImageNumber - dicomViewerPage.getAllViewportElements().size(),
                        dicomViewerPage.getImageNumber());
                break;
        }
    }

    @When("perform the {string} arrow keyboard control")
    @When("I perform the {string} arrow keyboard control")
    public void changeFrameUsingKeyboardControls(String keyDirection) {
        int frameNumber;
        BufferedImage originalImage = dicomViewerPage.getImageOfViewport(testContext.get("ViewportIndex"));
        testContext.set("OriginalImage", originalImage);
        if (keyDirection.contains("Left") || keyDirection.contains("Right")) {
            frameNumber = Integer.parseInt(dicomViewerPage.getCineFrameNumber());
            Log.printInfo("1:1 viewport baseline frame " + frameNumber);
            testContext.set("InitialFrameNumber", frameNumber);
        }
        switch (keyDirection.toUpperCase()) {
            case "CTRL+RIGHT":
                dicomViewerPage.moveToNextFrameUsingKeyboardControls();
                break;
            case "CTRL+LEFT":
                dicomViewerPage.moveToPreviousFrameUsingKeyboardControls();
                break;
            case "CTRL+UP":
                dicomViewerPage.zoomInCineImageUsingKeyboardControls();
                break;
            case "CTRL+DOWN":
                dicomViewerPage.zoomOutCineImageUsingKeyboardControls();
                break;
        }
        dicomViewerPage.waitForImageToLoad();
    }

    @When("I click on the {string} button on slider")
    public void clickOnFrameButton(String frameButton) {
        int frameNumber = Integer.parseInt(dicomViewerPage.getCineFrameNumber());
        Log.printInfo("Previous frame " + frameNumber);
        testContext.set("InitialFrameNumber", frameNumber);
        dicomViewerPage.clickOnViewportFrameControlIcon(frameButton);
        dicomViewerPage.waitForImageToLoad();
    }

    @Then("the image should be zoomed out")
    @Then("the image should be zoomed in")
    public void verifyCineImageZoom() {
        assertFalse("Image verification failed",
                dicomViewerPage.isImageChanged(
                        dicomViewerPage.getViewportBoundsByIndex(testContext.get("ViewportIndex")),
                        testContext.get("OriginalImage")));
    }

    @When("scroll the mouse wheel {string} for {int} time\\(s) in {string} layout")
    @When("I scroll the mouse wheel {string} for {int} time\\(s) in {string} layout")
    public void scrollMouseWheelOverCine(String option, int clickCount, String layout) {
        int frameNumber;
        if (layout.equals("4:1")) {
            frameNumber = Integer.parseInt(dicomViewerPage.getCineFrameNumberOnSecondViewport());
            System.out.println("4:1 viewport baseline frame " + frameNumber);
            testContext.set("InitialFrameNumber", frameNumber);
        } else if (layout.equals("1:1")) {
            frameNumber = Integer.parseInt(dicomViewerPage.getCineFrameNumber());
            System.out.println("1:1 viewport baseline frame " + frameNumber);
            testContext.set("InitialFrameNumber", frameNumber);
        }
        dicomViewerPage.moveToViewportCenterAndScrollMouseWheel(option, clickCount, testContext.get("ViewportIndex"));
        dicomViewerPage.waitForImageToLoad();
    }

    @When("I set the speed for cine loop to {string} in viewport {int}")
    public void setCineSpeedInViewport(String speed, int viewportIndex) {
        testContext.set("ViewportIndex", viewportIndex);
        dicomViewerPage.setCineSpeed(speed, viewportIndex);
    }

    @Then("frame should not get changed")
    public void verifyFrameLabelWhileCinePlaying() {
        assertFalse("Frame number label is displayed", dicomViewerPage.isFrameNumberLabelDisplayed());
    }

    @When("I click on the {string} button on slider without pausing cine")
    @When("perform the {string} arrow keyboard control without pausing cine")
    @When("I perform the {string} arrow keyboard control without pausing cine")
    public void performKeyboardActions(String frameControlSelection) {
        switch (frameControlSelection.toUpperCase()) {
            case "CTRL+RIGHT":
                dicomViewerPage.moveToNextFrameUsingKeyboardControls();
                break;
            case "CTRL+LEFT":
                dicomViewerPage.moveToPreviousFrameUsingKeyboardControls();
                break;
            case "Next frame":
            case "Previous frame":
                dicomViewerPage.clickOnViewportFrameControlIcon(frameControlSelection);
                break;
        }
    }

    @When("I change the default percentile calculating method of BPD to {string}")
    public void changeDefaultPercentileCalculatingMethod(String calculatingMethod) {
        dicomViewerPage.changeDefaultPercentileCalculatingMethod(calculatingMethod);
    }

    @When("I click on previous {string} study from the study list tab")
    public void selectPreviousStudy(String studyDescription) {
        dicomViewerPage.selectPreviousStudy(studyDescription);
    }

    @When("I select {string} with date {string} and time {string} for amending")
    public void amendReportWithDateTime(String reportName, String date, String time) {
        dicomViewerPage.amendReportWithDateTime(reportName, date, time);
    }

    @When("I click on {string} tab on the viewer")
    public void clickTabOnLeftPanel(String tabName) {
        dicomViewerPage.clickTabOnLeftPanel(tabName);
    }

    @When("I double click on the {string} from reports list")
    @When("I double click on the {string} under SR Others tab on viewer")
    public void doubleClickOnReportInReportsTab(String reportName) {
        dicomViewerPage.doubleClickOnReportInReportsTab(reportName);
    }

    @Then("Dicom SR tab for {string} should open in dicom viewer window")
    public void verifyDSRTabOpenedInViewer(String reportName) {
        assertTrue(dicomViewerPage.isDSRTabOpenedInViewerWindow(reportName));
    }

    @When("I {string} node in DSR viewer")
    public void expandAndCollapseNodesInDSRTab(String arrow) {
        dicomViewerPage.clickArrowInDSRTab(arrow);
    }

    @Then("tree item should unfold on DSR viewer")
    public void verifyTreeItemIsUnfoldedOnViewer() {
        assertTrue(dicomViewerPage.verifyTreeItemIsUnfoldedOnDSRViewer());
    }

    @Then("tree item should collapse on DSR viewer")
    public void verifyTreeItemIsCollapsedOnDSRViewer() {
        assertTrue(dicomViewerPage.verifyTreeItemIsCollapsedOnDSRViewer());
    }

    @When("I sort Description in ascending in study list tab for study {string}")
    public void sortDescriptionInAscendingOrder(String studyDescription) {
        dicomViewerPage.sortDescriptionInAscendingOrder(studyDescription);
    }

    @Then("{string} option should be {string}")
    public void verifyOptionPresentInRightClickMenuForReport(String option, String visibility) {
        boolean isElementPresent = dicomViewerPage.isOptionPresentInRightClickMenuForReport(option);
        assertEquals("Menu option validation failed", visibility.equalsIgnoreCase("PRESENT"), isElementPresent);
    }

    @When("I expand all nodes for the first tree item")
    public void expandTreeItemInDSRViewer() {
        dicomViewerPage.doubleClickOnTreeItemInDSRViewer();
    }

    @When("I scroll the SR by mouse wheel {string} for {int} time\\(s)")
    public void scrollUsingMouseWheel(String option, int clickCount) {
        dicomViewerPage.scrollUsingMouseWheel(option, clickCount);
    }

    @Then("SR should be scrolled")
    public void verifySRIsScrolled() {
        assertTrue("SR is not scrolled", dicomViewerPage.isSRInDSRViewerScrolled());
    }

    @When("I right click on {string} under SR Others tab on viewer")
    public void rightClickReportInSRTab(String reportName) {
        dicomViewerPage.rightClickOnReport(reportName, 0);
    }

    @When("import the DSR report")
    public void importDsrReport() {
        dicomViewerPage.importDsrReport();
    }

    @Then("all SR nodes should be collapsed")
    public void verifySRNodesAreCollapsed() {
        assertTrue("SR nodes are not collapsed", dicomViewerPage.verifySRNodesAreCollapsed());
    }

    @Then("below options should be greyed out for the SR reports")
    public void verifyRightClickMenuOptionAreDisabled(DataTable menuOptions) {
        List<String> menuOptionsList = menuOptions.asList();
        for (String menuOption : menuOptionsList) {
            assertTrue("Menu option is not greyed out", dicomViewerPage.isRightClickMenuOptionDisabled(menuOption));
        }
    }

    @Then("Dicom SR tab for {string} should open only once in dicom viewer window")
    public void verify(String reportName) {
        assertEquals("DSR tab opened more than once", 1, dicomViewerPage.getDSRTabCountInViewerWindow(reportName));
    }

    @Then("incorrect measurement unit should not be displayed")
    public void isIncorrectMeasurementUnitDisplayedOnImage() {
        assertFalse("Incorrect measurement unit is displayed", dicomViewerPage.isIncorrectUnitDisplayedOnImage());
        dicomViewerPage.resetViewport();
        Log.printInfo("Measurement unit is successfully verified");
    }

    @Then("ecg waveform should be displayed in rich viewer")
    public void isEcgWaveformDisplayedInRichViewer() {
        assertTrue("Ecg waveform is not displayed", dicomViewerPage.isEcgWaveformDisplayed());
        Log.printInfo("Ecg waveform is displayed in rich viewer");
    }

    @Then("{string} option {string} be present in the reports menu")
    public void isOptionPresentInReportsMenu(String menuOption, String visibility) {
        boolean isOptionPresent = dicomViewerPage.isOptionPresentInReportsMenu(menuOption);
        assertEquals(visibility.equalsIgnoreCase("should"), isOptionPresent);
    }

    @Then("viewer should not reopen")
    public void viewerShouldNotReopenAfterRestartLater() {
        assertTrue("viewer reopened for different patient", dicomViewerPage.isDicomViewerWindowVisible());
    }

    @Then("restart required pop up should be displayed")
    public void verifyRestartRequiredDialogIsPresent() {
        assertTrue("restart required pop up is not displayed", dicomViewerPage.isRestartRequiredDialogPresent());
    }

    @Then("viewer should load for the same study")
    public void viewerShouldReopenForSamePatientAfterRestartNow() {
        String patientFirstName = testContext.get("RichPatientFirstName");
        String patientLastName = testContext.get("RichPatientLastName");
        dicomViewerPage.waitForDicomViewerToClose();
        dicomViewerPage.waitForViewerToLoad();
        assertTrue("viewer verification failed",
                dicomViewerPage.isPatientInfoPresentOnViewer(patientFirstName + " " + patientLastName));
    }

    @When("I change the {string} to {string} in template administration")
    public void editFieldInTemplateAdministration(String fieldName, String value) {
        dicomViewerPage.editFieldInTemplateAdministration(fieldName, value);
    }

    @Then("the report icon {string} appear")
    public void isReportIconVisible(String reportIconStatus) {
        if (reportIconStatus.equalsIgnoreCase("SHOULD")) {
            assertTrue("Report Icon Not Present", dicomViewerPage.isReportIconVisible());
        } else {
            assertFalse("Report Icon Present", dicomViewerPage.isReportIconVisible());
        }
    }

    @Then("the report icon should appear on all images")
    public void verifyReportIcon() {
        for (int imageNumber = 1; imageNumber <= dicomViewerPage.getImageCount(); imageNumber++) {
            assertTrue("Report icon not visible on " + imageNumber, dicomViewerPage.isReportIconVisible());
            dicomViewerPage.goToNextImage();
            Common.sleep(200, TimeUnit.MILLISECONDS);
        }
    }

    @Then("instance count should match with the number of images")
    public void areInstancesEqualToTheNumberOfImages() {
        int instancesCount = testContext.get("NumberOfInstances");
        assertEquals("Images does not match with the instance count",
                instancesCount, dicomViewerPage.getImageCount());
    }

    @When("select the {string} sub menu option")
    public void selectSubMenuOption(String subMenuOption) {
        dicomViewerPage.selectSubMenuOption(testContext.get("MenuOption"), subMenuOption);
        Log.printInfo("Sub menu option " + subMenuOption + " is clicked");
    }

    @When("report icon {string} be present on the image")
    public void verifyReportIconIsVisible(String displayStatus) {
        if (displayStatus.equalsIgnoreCase("should")) {
            assertTrue("Report icon is not visible", dicomViewerPage.isReportIconVisible());
        } else {
            assertFalse("Report icon is visible", dicomViewerPage.isReportIconVisible());
        }
    }

    @Then("the following options should be {string} in the right click menu list")
    public void verifyRightClickMenuOptions(String displayStatus, DataTable table) {
        List<String> expectedMenuOptions = table.asList();
        List<String> actualMenuOptions = dicomViewerPage.getRightClickMenuOptions();
        if (displayStatus.equals("displayed")) {
            assertTrue("Option is not displayed in available options menu", actualMenuOptions.containsAll(expectedMenuOptions));
        } else if (displayStatus.equals("not displayed")) {
            assertFalse("Option is displayed in available options menu", actualMenuOptions.containsAll(expectedMenuOptions));
        }
    }

    @Then("export and print icon {string} be present on the image")
    public void verifyIfExportAndPrintIconIsDisplayed(String displayStatus) {
        if (displayStatus.equalsIgnoreCase("should")) {
            assertTrue("Export and print icon not displayed", dicomViewerPage.isExportAndPrintIconDisplayed());
        } else {
            assertFalse("Export and print icon displayed", dicomViewerPage.isExportAndPrintIconDisplayed());
        }
    }
}