package Steps;

import Context.TestContext;
import Pages.SimpleViewerPage;
import Utilities.Common;
import Utilities.Log;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class SimpleViewerSteps {

    TestContext testContext;
    SimpleViewerPage simpleViewerPage;

    public SimpleViewerSteps(TestContext testContext) {
        this.testContext = testContext;
        simpleViewerPage = new SimpleViewerPage(testContext.getDriver());
    }

    @Then("study should be loaded in simple viewer")
    public void verifyStudyLoadedInSimpleViewer() {
        assertTrue(simpleViewerPage.isThumbnailDisplayed());
    }

    @Then("thumbnails should be {string}")
    public void isThumbNailContainerDisplayedOrHidden(String thumbNailContainerViewMode) {
        if (thumbNailContainerViewMode.equals("displayed")) {
            assertTrue("Thumbnail container is hidden", simpleViewerPage.isThumbnailContainerDisplayed());
            Log.printInfo("Thumbnail container is displayed");
        } else if (thumbNailContainerViewMode.equals("hidden")) {
            assertFalse("Thumbnail container is displayed", simpleViewerPage.isThumbnailContainerDisplayed());
            Log.printInfo("Thumbnail container is hidden");
        }
        // simpleViewerPage.isThumbnailContainerDisplayed(thumbNailContainerViewMode);

    }

    @When("I select {string} display")
    public void selectViewportPattern(String layout) {
        simpleViewerPage.selectViewPortLayout(layout);
    }

    @Then("images should be displayed in {string} viewports")
    public void areNumberOfViewportsDisplayed(String numberOfViewports) {
        assertTrue("Selected layout does not displays " + numberOfViewports + "viewports",
                simpleViewerPage.areNumberOfViewportsDisplayed(numberOfViewports));
        Log.printInfo("Viewports for selected layout successfully verified");
    }

    @When("I click on Thumbnails section")
    public void expandCollapseThumbnails() {
        simpleViewerPage.expandCollapseThumbnails();
    }

    @When("I click on {string} arrow button")
    public void openNextOrPreviousImage(String direction) {
        String ImageNumber = simpleViewerPage.getImageNumber();
        testContext.set("defaultImageNumber", ImageNumber);
        simpleViewerPage.navigateToImage(direction);
    }

    @Then("viewer should display next image")
    public void isNextImageDisplayedOnViewer() {
        int ImageNumber = Integer.parseInt(simpleViewerPage.getImageNumber());
        int previousImageNumber = Integer.parseInt(testContext.get("defaultImageNumber"));
        assertEquals(ImageNumber, previousImageNumber + 1);
        Log.printInfo("Next image traversing successfully verified");
    }

    @When("I open the first report under Reports section")
    public void openFirstReportUnderReportsSection() {
        simpleViewerPage.openFirstReportUnderReportSection();
        Log.printInfo("First report is launched");
    }

    @Then("report should get opened in a new window")
    public void isReportOpened() {
        assertTrue("PDF Preview Dialog window is not opened", simpleViewerPage.isReportPdfDialogWindowOpened());
        assertTrue("Selected report has not been opened", simpleViewerPage.isFirstReportUnderReportSectionOpened());
        Log.printInfo("Selected report is successfully opened");
    }

    @When("I close the report window")
    public void closeReport() {
        simpleViewerPage.closeReport();
    }

    @Then("screen should return to Simple Viewer main window")
    public void isReportClosed() {
        assertFalse("Report is still open", simpleViewerPage.isReportPdfDialogWindowOpened());
        Log.printInfo("Report is successfully closed and Simple viewer window is displayed");
    }

    @Then("list of available findings categories should be displayed on the left side of the screen")
    public void verifyFindingsDisplayedOnTheLeftSide() {
        assertTrue("List of findings category is not displayed on the left side",
                simpleViewerPage.areFindingsDisplayedOnTheLeftSide());
        Log.printInfo("Findings on the left side successfully verified");
    }

    @When("I click on {string} icon on any finding category")
    public void expandCollapseFinding(String actionOnFinding) {
        simpleViewerPage.expandCollapseFinding(actionOnFinding);
    }

    @Then("list of statement abbreviations should be displayed")
    public void verifyListOfStatementAbbreviationsDisplayed() {
        assertTrue("Statement abbreviations list is not displayed",
                simpleViewerPage.isListOfStatementAbbreviationsDisplayed());
        Log.printInfo("Statement abbreviation list is been displayed");
    }

    @When("I hover on any abbreviation")
    public void hoverOnAbbreviation() {
        simpleViewerPage.hoverOnAbbreviation();
    }

    @Then("full statement should be displayed in popup")
    public void verifyFullStatementDisplayed() {
        assertTrue("Full statement is not displayed", simpleViewerPage.isFullStatementDisplayed());
        Log.printInfo("Full statement displayed in tooltip upon hovering the abbreviation");
    }

    @When("I select {string} display view")
    public void selectReportPreviewDatasetsView(String viewType) {
        simpleViewerPage.selectViewType(viewType);
        Log.printInfo("Switched to " + viewType + " view");
    }

    @Then("{string} view should be displayed")
    public void verifySelectedViewDisplayed(String viewType) {
        assertTrue(viewType + " is not displayed", simpleViewerPage.isSelectedViewDisplayed(viewType));
        Log.printInfo(viewType + " view is displayed");
    }

    @When("I select an abbreviation")
    public void selectAbbreviation() {
        simpleViewerPage.addAbbreviationToReport();
    }

    @Then("it should get added to the report")
    public void verifyAbbreviationAddedToReport() {
        assertTrue("Abbreviation is not added to the report", simpleViewerPage.isAbbreviationAddedToReport());
        Log.printInfo("Abbreviation has been added to the report");
    }

    @When("I sign report")
    public void signReport() {
        Common.sleep(2, TimeUnit.SECONDS);
        simpleViewerPage.signReport();
        Log.printInfo("Report has been signed successfully");
    }

    @Then("report preview should close and screen should return to Simple Viewer main window")
    public void verifyReportViewerClosedAndMainWindowDisplayed() {
        assertTrue("Simple viewer main window is not displayed", simpleViewerPage.isThumbnailDisplayed());
        Log.printInfo("Report preview is closed successfully");
    }

    @When("I select {string}")
    public void selectLeftPanelReportSection(String leftPanelMenuOption) {
        if (simpleViewerPage.isReportingSectionCollapsed(leftPanelMenuOption)) {
            simpleViewerPage.selectLeftPanelReportSection(leftPanelMenuOption);
        }
    }

    @Then("reporting UI left panel should be displayed")
    public void isReportingUiLeftPanelDisplayed() {
        assertTrue("Reporting UI left panel is not displayed", simpleViewerPage.isReportingLeftPanelDisplayed());
        Log.printInfo("Reporting UI left panel is displayed");
    }

    @Then("expanded section should contain the below fields")
    public void isSelectedTabConsistingRequiredFields(DataTable table) {
        List<String> requiredFields = table.asList();
        for (String attributeName : requiredFields) {
            assertTrue(attributeName + " is not present", simpleViewerPage.isAttributePresent(attributeName));
            Log.printInfo(attributeName + " is present under selected tab");
        }
    }

    @Then("left panel should contain the following tabs")
    public void areListedTabsPresentUnderLeftPanel(DataTable table) {
        List<String> tabList = table.asList();
        for (String tabName : tabList) {
            assertTrue(tabName + " is not missing under left panel",
                    simpleViewerPage.isReportingTabPresentUnderLeftPanel());
            Log.printInfo(tabName + " is present under left panel");
        }
    }

    @When("edit {string} as {string}")
    @When("enter {string} as {string}")
    @When("I edit {string} field as {string}")
    @When("I enter {string} field as {string}")
    public void enterEditPatientDetails(String attributeName, String attributeValue) {
        testContext.set("AttributeName", attributeName);
        simpleViewerPage.enterPatientAttributeValue(attributeName, attributeValue);
    }

    @Then("{string} should get added to the report")
    @Then("report should get populated with {string} data")
    public void isReportContainingUserEnteredData(String tabName) {
        simpleViewerPage.waitForDataToLoad();
        assertTrue(tabName + " is not populated with data", simpleViewerPage.isReportPopulatedWithData(tabName));
        Log.printInfo("Report preview is populated with " + tabName + " data");
    }

    @When("select {string} in {string} category")
    public void selectIndicationCategory(String category, String attribute) {
        simpleViewerPage.selectCodeCategory(category, attribute);
    }

    @When("select code {string} from table")
    public void selectIndicationCode(String indicationCode) {
        simpleViewerPage.selectCode(indicationCode);
    }

    @Then("error should be displayed for negative input")
    @Then("error should be displayed")
    public void isErrorMessageDisplayed() {
        assertTrue("Error message is not displayed", simpleViewerPage.isErrorMessageDisplayedForInvalidFormat());
        Log.printInfo("Error message is displayed as " + testContext.get("AttributeName") + " is in invalid format");
        simpleViewerPage.clickOnOkButton();
        simpleViewerPage.clearExistingInvalidAttributeValue(testContext.get("AttributeName"));
        Log.printInfo(testContext.get("AttributeName") + " field cleared");
    }

    @When("enter {string} in the recommendations area")
    public void enterRecommendations(String recommendations) {
        simpleViewerPage.enterRecommendations(recommendations);
    }

    @When("I enter {string} in code value filter")
    public void applyCodeFilter(String filterValue) {
        simpleViewerPage.applyCodeFilter(filterValue);
    }

    @Then("multiple diagnosis should get added in the code meaning box")
    @Then("multiple indications should get added in the code meaning box")
    @Then("multiple procedures should get added in the code meaning box")
    public void areMultipleCodesAdded() {
        assertTrue("Multiple codes are not available", simpleViewerPage.areMultipleCodesAdded());
    }

    @When("I close the viewer tab")
    public void closeViewerWindow() {
        simpleViewerPage.closeSimpleViewerTab();
    }

    @Then("{string} image should be displayed")
    public void verifyDatasetImageIsDisplayed(String datasetPatternFile) {
        assertTrue("Dataset image is not displayed",
                simpleViewerPage.isDatasetImageDisplayedInECGViewer(datasetPatternFile));
    }

    @Then("zoom percentage should be decreased to {string}")
    @Then("zoom percentage should be increased to {string}")
    public void verifyZoomPercentageIsChanged(String percentageValue) {
        assertTrue("Zoom percentage is not changed", simpleViewerPage.isZoomPercentageChanged(percentageValue));
    }

    @Then("dataset view should be {string}")
    public void verifyDatasetViewIsChanged(String datasetView) {
        assertTrue("Dataset view is not changed", simpleViewerPage.isDatasetViewChanged(datasetView));
    }

    @When("I click on {string} in show datasets")
    public void clickIconInShowDatasets(String iconName) {
        simpleViewerPage.setReportLayoutViewInShowDatasets(iconName);
    }

    @Then("calipers should be displayed on the datasets")
    public void verifyCalipersAreDisplayedOnDataset() {
        assertTrue("Calipers are not displayed", simpleViewerPage.areCalipersDisplayedOnDatasetCanvas());
    }

    @When("I move one of the caliper on the datasets")
    public void changeCaliperPosition() {
        simpleViewerPage.changePositionOfCaliper();
    }

    @When("I hover on {string} enabled button")
    @When("I hover on {string} button")
    public void hoverOnToolbarButton(String buttonName) {
        simpleViewerPage.hoverOnToolbarButton(buttonName);
    }

    @Then("tool tip should display label as {string}")
    public void verifyToolTipLabel(String toolTipLabelMessage) {
        assertTrue("Label not displayed correctly", simpleViewerPage.isToolTipLabelDisplayedProperly(toolTipLabelMessage));
    }

    @When("I click on {string} button in simple viewer")
    public void clickOnToolBarButton(String buttonName) {
        simpleViewerPage.clickOnToolBarButton(buttonName);
    }

    @Then("user should be directed to the {string} of the report")
    public void verifyIsReportDirectedToAppropriatePage(String direction) {
        assertTrue("Report is directed to appropriate page", simpleViewerPage.isReportDirectedToAppropriatePage(direction));
    }

    @Then("error message should be displayed after clicking on sign report")
    public void isErrorMessagedDisplayedAfterClickingOnSignReport() {
        assertTrue("Error message is not displayed", simpleViewerPage.isErrorMessageDisplayedAfterClickingOnSignReportButton());
        simpleViewerPage.clickOnOkButton();
    }

    @Then("an appropriate tooltip message should be displayed when we hover on an exclamation mark")
    public void isToolTipLabelDisplayedForNegativeInputValue() {
        assertTrue("Tool tip label not displayed", simpleViewerPage.isAppropriateToolTipLabelDisplayedForNegativeInputValue());
        simpleViewerPage.clearExistingInvalidAttributeValue(testContext.get("AttributeName"));
        simpleViewerPage.moveMouseByOffset(-10, 0);
    }

    @Then("an appropriate tooltip message should be displayed for decimal value when we hover on an exclamation mark")
    public void isToolTipDisplayedForDecimalInputValue() {
        assertTrue("Tool tip label not displayed", simpleViewerPage.isAppropriateToolTipLabelDisplayedForDecimalInputValue());
        simpleViewerPage.clearExistingInvalidAttributeValue(testContext.get("AttributeName"));
        simpleViewerPage.moveMouseByOffset(-10, 0);
    }

    @Then("exclamation mark should not be displayed")
    public void isExclamationMarkDisplayed() {
        assertFalse("Exclamation mark icon is displayed", simpleViewerPage.isExclamationMarkDisplayed());
    }
}
