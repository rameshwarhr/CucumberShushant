package Pages;

import Actions.RobotActions;
import Actions.SikuliActions;
import Actions.WebActions;
import ObjectRepository.SimpleViewerPageOR;
import Utilities.Common;
import Utilities.Driver;
import Utilities.PdfReader;
import Utilities.YmlReader;

import java.util.concurrent.TimeUnit;

import static Utilities.Constants.*;

public class SimpleViewerPage extends Page {
    Driver driver;
    WebActions webActions;
    PdfReader pdfReader;
    SikuliActions sikuliActions;
    RobotActions robotActions;
    public static final String simpleViewerScreenElements = USER_WORK_DIR
            + "\\src\\test\\resources\\SimpleViewerScreenElements\\";

    public SimpleViewerPage(Driver driver) {
        super(driver);
        this.driver = driver;
        webActions = new WebActions(driver.getWebDriver());
        pdfReader = new PdfReader();
        sikuliActions = new SikuliActions();
        robotActions = new RobotActions();
    }

    private static final SimpleViewerPageOR simpleViewerPageOR = YmlReader
            .getObjectRepository(USER_WORK_DIR + SimpleViewerPageORFile, SimpleViewerPageOR.class);

    public boolean isThumbnailDisplayed() {
        return webActions.isVisible(
                getByLocator(simpleViewerPageOR.simpleViewerThumbnailSection_Xpath, LocatorIdentifier.Xpath), 20);
    }

    public boolean isThumbnailContainerDisplayed() {
        String styleValue = webActions.getElementAttributeValue(
                getByLocator(simpleViewerPageOR.simpleViewerThumbnailBox_Xpath, LocatorIdentifier.Xpath), "style");
        return !styleValue.contains("hidden");
    }

    public void selectViewPortLayout(String layout) {
        String layoutPattern = layout.split(":")[0];
        webActions.click(getByLocator(simpleViewerPageOR.layoutIcon_Xpath, LocatorIdentifier.Xpath, layoutPattern));
    }

    public void expandCollapseThumbnails() {
        webActions.click(getByLocator(simpleViewerPageOR.simpleViewerThumbnailSection_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean areNumberOfViewportsDisplayed(String numberOfViewports) {
        int viewportSize = Integer.parseInt(numberOfViewports);
        return viewportSize == webActions
                .getElementCount(getByLocator(simpleViewerPageOR.viewPort_Xpath, LocatorIdentifier.Xpath));
    }

    public void navigateToImage(String direction) {
        switch (direction.toUpperCase()) {
            case "FORWARD":
                webActions.click(getByLocator(simpleViewerPageOR.nextButton_Xpath, LocatorIdentifier.Xpath));
                break;

            case "BACK":
                webActions.click(getByLocator(simpleViewerPageOR.previousButton_Xpath, LocatorIdentifier.Xpath));
                break;
        }
    }

    public String getImageNumber() {
        return webActions.getText(getByLocator(simpleViewerPageOR.imageNumber_Xpath, LocatorIdentifier.Xpath))
                .split("#")[1].split("/")[0];

    }

    public void openFirstReportUnderReportSection() {
        webActions
                .click(getByLocator(simpleViewerPageOR.firstReportUnderReportsSection_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isReportPdfDialogWindowOpened() {
        return (webActions
                .getElementCount(getByLocator(simpleViewerPageOR.pdfDialogWindow_Xpath, LocatorIdentifier.Xpath)) > 0);
    }

    public boolean isFirstReportUnderReportSectionOpened() {
        String firstReportNameUnderReportSection = webActions.getText(
                getByLocator(simpleViewerPageOR.firstReportUnderReportsSection_Xpath, LocatorIdentifier.Xpath));
        webActions.waitForElementVisibility(
                getByLocator(simpleViewerPageOR.reportNameOnPDFViewer_Xpath, LocatorIdentifier.Xpath), 10);
        String reportNameOnPDF = webActions
                .getText(getByLocator(simpleViewerPageOR.reportNameOnPDFViewer_Xpath, LocatorIdentifier.Xpath))
                .split(": ")[1];
        return firstReportNameUnderReportSection.equals(reportNameOnPDF);
    }

    public void closeReport() {
        webActions.click(getByLocator(simpleViewerPageOR.closeReportIcon_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean areFindingsDisplayedOnTheLeftSide() {
        return webActions.getElementLocation(getByLocator(simpleViewerPageOR.findingsTree_Xpath,
                LocatorIdentifier.Xpath)).x < webActions.getElementLocation(
                getByLocator(simpleViewerPageOR.dynamicReportPreviewSection_Xpath, LocatorIdentifier.Xpath)).x;
    }

    public void selectViewType(String viewType) {
        switch (viewType.toUpperCase()) {
            case "SHOW REPORT PREVIEW":
                webActions.click(getByLocator(simpleViewerPageOR.showReportPreviewButton_Xpath, LocatorIdentifier.Xpath));
                break;

            case "SHOW DATASETS":
                webActions.click(getByLocator(simpleViewerPageOR.showDatasetsButton_Xpath, LocatorIdentifier.Xpath));
                break;
        }

    }

    public boolean isSelectedViewDisplayed(String viewType) {

        switch (viewType.toUpperCase()) {
            case "REPORT PREVIEW":
                webActions.waitForElementVisibility(
                        getByLocator(simpleViewerPageOR.reportPreviewContainer_Xpath, LocatorIdentifier.Xpath), 15);
                return webActions.isVisible(
                        getByLocator(simpleViewerPageOR.reportPreviewContainer_Xpath, LocatorIdentifier.Xpath), 10);

            case "DATASETS":
                webActions.waitForElementVisibility(
                        getByLocator(simpleViewerPageOR.datasetsContainer_Xpath, LocatorIdentifier.Xpath), 15);
                return webActions
                        .isVisible(getByLocator(simpleViewerPageOR.datasetsContainer_Xpath, LocatorIdentifier.Xpath), 10);

            default:
                return false;
        }
    }

    public void expandCollapseFinding(String actionOnFinding) {
        switch (actionOnFinding.toUpperCase()) {
            case "EXPAND":
                webActions.click(getByLocator(simpleViewerPageOR.expandFindingsIcon_Xpath, LocatorIdentifier.Xpath));
                break;

            case "COLLAPSE":
                webActions.click(getByLocator(simpleViewerPageOR.collapseFindingsIcon_Xpath, LocatorIdentifier.Xpath));
                break;
        }
    }

    public boolean isListOfStatementAbbreviationsDisplayed() {
        return webActions.isVisible(getByLocator(simpleViewerPageOR.abbreviationsList_Xpath, LocatorIdentifier.Xpath),
                15);
    }

    public void hoverOnAbbreviation() {
        webActions.mouseHover(getByLocator(simpleViewerPageOR.abbreviationTreeItem_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isFullStatementDisplayed() {
        return webActions
                .isVisible(getByLocator(simpleViewerPageOR.abbreviationTreeItemTooltip_Xpath, LocatorIdentifier.Xpath));
    }

    public void addAbbreviationToReport() {
        webActions.click(getByLocator(simpleViewerPageOR.abbreviationTreeItem_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isAbbreviationAddedToReport() {
        setReportLayoutView("fitwidth");
        sikuliActions.waitForElementToExist(simpleViewerScreenElements + "AbbreviationStatement1.png", 20);
        return sikuliActions.isElementVisible(simpleViewerScreenElements + "AbbreviationStatement1.png");
    }

    public void signReport() {
        webActions.click(getByLocator(simpleViewerPageOR.signReportButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void setReportLayoutView(String iconName) {
        switch (iconName.toUpperCase()) {
            case "FIT TO WIDTH":
            case "FITTOWIDTH":
            case "FITWIDTH":
                webActions.click(getByLocator(simpleViewerPageOR.fitWidthButton_Xpath, LocatorIdentifier.Xpath));
                break;
            case "ZOOM IN":
            case "ZOOMIN":
                webActions.click(getByLocator(simpleViewerPageOR.zoomInButton_Xpath, LocatorIdentifier.Xpath));
                break;
            case "ZOOM OUT":
            case "ZOOMOUT":
                webActions.click(getByLocator(simpleViewerPageOR.zoomOutButton_Xpath, LocatorIdentifier.Xpath));
                break;
            case "FITPAGE":
                webActions.click(getByLocator(simpleViewerPageOR.fitPageButton_Xpath, LocatorIdentifier.Xpath));
                break;
            case "ACTUALSIZE":
            case "ACTUAL SIZE":
                webActions.click(getByLocator(simpleViewerPageOR.actualSizeButton_Xpath, LocatorIdentifier.Xpath));
                break;
        }
    }

    public void selectLeftPanelReportSection(String leftPanelMenuOption) {
        webActions.click(getByLocator(simpleViewerPageOR.leftPanelMenuOption_Xpath, LocatorIdentifier.Xpath,
                leftPanelMenuOption));
    }

    public void closeSimpleViewerTab() {
        webActions.click(getByLocator(simpleViewerPageOR.closeViewerIcon_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isReportingLeftPanelDisplayed() {
        return webActions.isVisible(getByLocator(simpleViewerPageOR.reportingLeftPanel_Xpath, LocatorIdentifier.Xpath),
                10);
    }

    public boolean isReportingSectionCollapsed(String leftPanelMenuOption) {
        return webActions.isVisible(
                getByLocator(simpleViewerPageOR.collapsedReportSection, LocatorIdentifier.Xpath, leftPanelMenuOption),
                10);
    }

    public boolean isAttributePresent(String attributeName) {
        return webActions.isVisible(
                getByLocator(simpleViewerPageOR.attributeLabel_Xpath, LocatorIdentifier.Xpath, attributeName), 10);
    }

    public void enterPatientAttributeValue(String attributeName, String attributeValue) {
        webActions.setText(
                getByLocator(simpleViewerPageOR.attributeInputField_Xpath, LocatorIdentifier.Xpath, attributeName),
                attributeValue);
    }

    public void clearExistingInvalidAttributeValue(String attributeName) {
        webActions.clearText(
                getByLocator(simpleViewerPageOR.erroredAttributeField_Xpath, LocatorIdentifier.Xpath, attributeName));
    }

    public void waitForDataToLoad() {
        webActions.waitForElementToDisappear(
                getByLocator(simpleViewerPageOR.dataLoadingIcon_Xpath, LocatorIdentifier.Xpath), 20);
    }

    public boolean isReportPopulatedWithData(String tabName) {
        setReportLayoutView("fitwidth");
        sikuliActions.waitForElementToExist(simpleViewerScreenElements + tabName + ".png", 20);
        return sikuliActions.isElementVisible(simpleViewerScreenElements + tabName + ".png");
    }

    public void selectCodeCategory(String category, String attribute) {
        webActions.click(getByLocator(simpleViewerPageOR.indicationsDropdown_Xpath, LocatorIdentifier.Xpath));
        Common.sleep(2, TimeUnit.SECONDS);
        switch (attribute.toUpperCase()) {
            case "INDICATIONS":
            case "DIAGNOSIS":
                webActions.click(
                        getByLocator(simpleViewerPageOR.codeCategoryListItem_Xpath, LocatorIdentifier.Xpath, category));
                break;
            case "PROCEDURE":
                webActions.click(getByLocator(simpleViewerPageOR.procedureCodeCategoryListItem_Xpath,
                        LocatorIdentifier.Xpath, category));
                break;
        }
    }

    public void selectCode(String indicationCode) {
        webActions.doubleClick(
                getByLocator(simpleViewerPageOR.codeListItem_Xpath, LocatorIdentifier.Xpath, indicationCode));
        waitForDataToLoad();
    }

    public boolean isReportingTabPresentUnderLeftPanel() {
        return webActions.isVisible(getByLocator(simpleViewerPageOR.reportingLeftPanel_Xpath, LocatorIdentifier.Xpath),
                10);
    }

    public boolean isErrorMessageDisplayedForInvalidFormat() {
        robotActions.pressTabKey();
        return webActions.isVisible(getByLocator(simpleViewerPageOR.errorMessageLabel_Xpath, LocatorIdentifier.Xpath),
                5);
    }

    public void clickOnOkButton() {
        webActions.click(getByLocator(simpleViewerPageOR.okButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void enterRecommendations(String recommendations) {
        webActions.setText(getByLocator(simpleViewerPageOR.recommendationsTextArea_Xpath, LocatorIdentifier.Xpath),
                recommendations);
    }

    public void applyCodeFilter(String filterValue) {
        webActions.setText(getByLocator(simpleViewerPageOR.codeFilterTextbox_Xpath, LocatorIdentifier.Xpath),
                filterValue);
        waitForDataToLoad();
    }

    public boolean areMultipleCodesAdded() {
        waitForDataToLoad();
        return webActions
                .getElementCount(getByLocator(simpleViewerPageOR.codeMeaningLabel_Xpath, LocatorIdentifier.Xpath)) > 1;
    }

    public boolean isDatasetImageDisplayedInECGViewer(String datasetPatternFile) {
        sikuliActions.waitForElementToExist(
                USER_WORK_DIR + ReportScreens + "Common\\" + datasetPatternFile + " Image.PNG", 15);
        return sikuliActions
                .isElementVisible(USER_WORK_DIR + ReportScreens + "Common\\" + datasetPatternFile + " Image.PNG");
    }

    public boolean isZoomPercentageChanged(String percentageValue) {
        return webActions.getText(getByLocator(simpleViewerPageOR.zoomPercentage_Xpath, LocatorIdentifier.Xpath))
                .equals(percentageValue);
    }

    public boolean isDatasetViewChanged(String datasetView) {
        sikuliActions.waitForElementToExist(USER_WORK_DIR + ReportScreens + "Common\\" + datasetView + "_Image.PNG", 5);
        return sikuliActions.isElementVisible(USER_WORK_DIR + ReportScreens + "Common\\" + datasetView + "_Image.PNG");
    }

    public void setReportLayoutViewInShowDatasets(String iconName) {
        switch (iconName.toUpperCase()) {
            case "ZOOM IN":
            case "ZOOMIN":
                webActions
                        .click(getByLocator(simpleViewerPageOR.zoomInButtonInShowDatasets_Xpath, LocatorIdentifier.Xpath));
                break;
            case "ZOOM OUT":
            case "ZOOMOUT":
                webActions
                        .click(getByLocator(simpleViewerPageOR.zoomOutButtonInShowDatasets_Xpath, LocatorIdentifier.Xpath));
                break;
            case "CALIPER TOOL":
                webActions.click(getByLocator(simpleViewerPageOR.caliperToolButton_Xpath, LocatorIdentifier.Xpath));
                break;
        }
    }

    public boolean areCalipersDisplayedOnDatasetCanvas() {
        return webActions.isVisible(getByLocator(simpleViewerPageOR.caliperToolKnob_Xpath, LocatorIdentifier.Xpath), 5);
    }

    public void changePositionOfCaliper() {
        webActions.mouseHover(getByLocator(simpleViewerPageOR.caliperToolVerticalKnob_Xpath, LocatorIdentifier.Xpath));
        webActions.moveMouseByOffset(0, 10);
        webActions.dragMouseByOffset(180, 0);
    }

    public void hoverOnToolbarButton(String buttonName) {
        webActions.mouseHover(getByLocator(simpleViewerPageOR.toolBarButton_Xpath, LocatorIdentifier.Xpath, buttonName));
    }

    public boolean isToolTipLabelDisplayedProperly(String toolTipLabelMessage) {
        return webActions.isVisible(getByLocator(simpleViewerPageOR.toolTipLabel_Xpath, LocatorIdentifier.Xpath, toolTipLabelMessage));
    }

    public void clickOnToolBarButton(String buttonName) {
        webActions.click(getByLocator(simpleViewerPageOR.toolBarButton_Xpath, LocatorIdentifier.Xpath, buttonName));
    }

    public boolean isReportDirectedToAppropriatePage(String direction) {
        webActions.waitForElementToDisappear(getByLocator(simpleViewerPageOR.dataLoadingIcon_Xpath, LocatorIdentifier.Xpath), 5);
        return sikuliActions.isElementVisible(USER_WORK_DIR + ReportScreens + "Common\\" + direction + "_Image.PNG");
    }

    public boolean isErrorMessageDisplayedAfterClickingOnSignReportButton() {
        return webActions.isVisible(getByLocator(simpleViewerPageOR.errorDialogMessage_Xpath, LocatorIdentifier.Xpath),
                5);
    }

    public boolean isAppropriateToolTipLabelDisplayedForNegativeInputValue() {
        webActions.mouseHover(getByLocator(simpleViewerPageOR.exclamationMarkIcon_Xpath, LocatorIdentifier.Xpath));
        return webActions.isVisible(getByLocator(simpleViewerPageOR.exclamationMarkToolTipForNegativeInputValue_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isAppropriateToolTipLabelDisplayedForDecimalInputValue() {
        webActions.mouseHover(getByLocator(simpleViewerPageOR.exclamationMarkIcon_Xpath, LocatorIdentifier.Xpath));
        return webActions.isVisible(getByLocator(simpleViewerPageOR.exclamationMarkToolTipForDecimalInputValue_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isExclamationMarkDisplayed() {
        return webActions.isVisible(getByLocator(simpleViewerPageOR.exclamationMarkIcon_Xpath, LocatorIdentifier.Xpath), 3);
    }
}
