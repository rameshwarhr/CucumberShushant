package Pages;

import static Utilities.Common.sleep;
import static Utilities.Constants.StudyTreePageORFile;
import static Utilities.Constants.USER_WORK_DIR;
import static Utilities.Constants.dicomDirFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import Actions.WebActions;
import Actions.WindowsActions;
import ObjectRepository.StudyTreePageOR;
import Utilities.Driver;
import Utilities.Log;
import Utilities.Property;
import Utilities.YmlReader;

public class StudyTreePage extends Page {
    Driver driver;
    WebActions webActions;
    Property property;
    WindowsActions winActions;

    public StudyTreePage(Driver driver) {
        super(driver);
        this.driver = driver;
        webActions = new WebActions(driver.getWebDriver());
        property = new Property();
        winActions = new WindowsActions(driver.getWinDriver());
    }

    private static final StudyTreePageOR studyTreePageOR = YmlReader.getObjectRepository(USER_WORK_DIR + StudyTreePageORFile,
            StudyTreePageOR.class);

    public void selectFirstPatient() {
        sleep(1, TimeUnit.SECONDS);
        webActions.click(getByLocator(studyTreePageOR.checkFirstPatient_Xpath, LocatorIdentifier.Xpath));
    }

    public int firstPatientStudyCount() {
        int studyCount = webActions
                .getElementCount(getByLocator(studyTreePageOR.firstPatientStudyCount_Xpath, LocatorIdentifier.Xpath));
        Log.printInfo("First Patient study count---->" + studyCount);
        return studyCount;
    }

    public void expandSecondPatient() {
        webActions.click(getByLocator(studyTreePageOR.expandSecondPatient_Xpath, LocatorIdentifier.Xpath));
        waitForDataToLoad();
    }

    public void selectMergePatientOption() {
        webActions.click(getByLocator(studyTreePageOR.mergeStudyButton_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyTreePageOR.confirmYesButton_Xpath, LocatorIdentifier.Xpath));
        try {
            if (webActions.isVisible(getByLocator(studyTreePageOR.warningOkButton_Xpath, LocatorIdentifier.Xpath),
                    10)) {
                webActions.click(getByLocator(studyTreePageOR.warningOkButton_Xpath, LocatorIdentifier.Xpath));
            }
        } catch (Exception e) {
            Log.printInfo("No pop up");
        }
        sleep(1, TimeUnit.SECONDS);
    }

    public void clickAddNewStudy() {
        webActions.click(getByLocator(studyTreePageOR.addStudyButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void clickDeleteStudy() {
        webActions.mouseHover(getByLocator(studyTreePageOR.patientsSectionHeader_Xpath, LocatorIdentifier.Xpath));
        sleep(1, TimeUnit.SECONDS);
        webActions.mouseHover(
                getByLocator(studyTreePageOR.studyTreeTableStudySelectionCheckbox_Xpath, LocatorIdentifier.Xpath, 1));
        webActions.waitForElementPresence(
                getByLocator(studyTreePageOR.deleteStudyButton_Xpath, LocatorIdentifier.Xpath), 10);
        webActions.click(getByLocator(studyTreePageOR.deleteStudyButton_Xpath, LocatorIdentifier.Xpath));
        webActions.waitForElementPresence(getByLocator(studyTreePageOR.confirmYesButton_Xpath, LocatorIdentifier.Xpath),
                10);
        webActions.click(getByLocator(studyTreePageOR.confirmYesButton_Xpath, LocatorIdentifier.Xpath));
        waitForDataToLoad();
    }

    public boolean isAddStudyModalDisplayed() {
        return webActions.waitForElementVisibility(
                getByLocator(studyTreePageOR.addNewStudyModal_Xpath, LocatorIdentifier.Xpath), 10).isDisplayed();
    }

    public void enterStudyDetails(List<Map<String, String>> studyDetails) {
        for (Map<String, String> studyDetail : studyDetails) {
            String field = studyDetail.get("field");
            switch (field.toUpperCase()) {
                case "STUDY DESCRIPTION":
                    enterStudyDescription(studyDetail.get("value"));
                    break;
                case "SERVICE LOCATION":
                    enterServiceLocation(studyDetail.get("value"));
                    break;
            }
        }
    }

    public String getStudyCreationDateTime() {
        return webActions
                .getTextboxValue(getByLocator(studyTreePageOR.addNewStudyDateTimeText_Xpath, LocatorIdentifier.Xpath));
    }

    public void saveStudy() {
        webActions.click(getByLocator(studyTreePageOR.saveButton_Xpath, LocatorIdentifier.Xpath));
        sleep(2, TimeUnit.SECONDS);
    }

    public void selectStudyByStudyDateTime(String studyDateTime) {
        sleep(2, TimeUnit.SECONDS);
        selectStudyByStudyIndex(getStudyIndexByStudyDateTime(studyDateTime) + 1);
    }

    public boolean isStudyCreated(String studyCreationDateTime) {
        List<String> studyDateTimeList = getStudiesTableColumnData("Study Date/Time");
        return studyDateTimeList.contains(studyCreationDateTime);
    }

    public int getStudyIndexByStudyDateTime(String studyDateTime) {
        List<String> studyDateTimeList = webActions
                .getColumnText(getByLocator(studyTreePageOR.studyTreeStudyListTable_Xpath, LocatorIdentifier.Xpath), 5);
        return studyDateTimeList.indexOf(studyDateTime);
    }

    public void selectStudyByStudyIndex(int studyIndex) {
        webActions.click(getByLocator(studyTreePageOR.studyTreeTableStudySelectionCheckbox_Xpath,
                LocatorIdentifier.Xpath, studyIndex));
    }

    public void openStudyInRichViewer(String studyCreationDateTime) {
        webActions.rightClick(
                getByLocator(studyTreePageOR.studyRowByDate_Xpath, LocatorIdentifier.Xpath, studyCreationDateTime));
        webActions.waitForElementPresence(
                getByLocator(studyTreePageOR.studyListRightClickMenu_Xpath, LocatorIdentifier.Xpath), 10);
        webActions.click(getByLocator(studyTreePageOR.openWithRichViewerOption_Xpath, LocatorIdentifier.Xpath));
    }

    public void searchPatientByName(String patientName) {
        webActions.setText(getByLocator(studyTreePageOR.patientNameSearchTextBox_Xpath, LocatorIdentifier.Xpath),
                patientName);
    }

    public void expandStudiesForPatient(String patientName) {
        int patientRowIndex = getPatientRowIndex(patientName);
        webActions.clickOnElementAtIndex(
                getByLocator(studyTreePageOR.patientExpandStudyIcon_Xpath, LocatorIdentifier.Xpath), patientRowIndex);
        waitForDataToLoad();
    }

    public List<String> getPatientTableColumnData(String columnName) {
        List<String> columnTexts = webActions.getElementTextList(
                getByLocator(studyTreePageOR.studyTreeTableColumnHeaders_Xpath, LocatorIdentifier.Xpath));
        int columnIndex = columnTexts.indexOf(columnName);
        return webActions.getColumnText(
                getByLocator(studyTreePageOR.studyTreeTable_Xpath, LocatorIdentifier.Xpath), columnIndex + 1);
    }

    public void expandStudyByIndex(int studyIndex) {
        webActions.clickOnElementAtIndex(
                getByLocator(studyTreePageOR.studyTreeStudyExpandStudyIcon_Xpath, LocatorIdentifier.Xpath), studyIndex);
        waitForDataToLoad();
    }

    public void hoverOnFirstStudyOnStudyTree() {
        webActions.mouseHover(getByLocator(studyTreePageOR.firstStudyOnStudyTree_Xpath, LocatorIdentifier.Xpath));
    }

    public void expandCollapseSeries(String iconType, int seriesNumber) {
        webActions.waitForElementClickability(
                getByLocator(studyTreePageOR.seriesTreeGrid_Xpath, LocatorIdentifier.Xpath), 10);
        String icon = iconType.equalsIgnoreCase("EXPAND") ? "collapse" : "expand";
        List<WebElement> series = webActions.getElements(
                getByLocator(studyTreePageOR.seriesExpandCollapseIcon_Xpath, LocatorIdentifier.Xpath, icon));
        try {
            webActions.scrollToElement(series.get(seriesNumber - 1));
        } catch (StaleElementReferenceException e) {
            series = webActions.getElements(
                    getByLocator(studyTreePageOR.seriesExpandCollapseIcon_Xpath, LocatorIdentifier.Xpath, icon));
            webActions.scrollToElement(series.get(seriesNumber - 1));
        }
        webActions.click(series.get(seriesNumber - 1));
        webActions.waitForElementVisibility(
                getByLocator(studyTreePageOR.instanceTreeGrid_Xpath, LocatorIdentifier.Xpath), 10);
    }

    public void selectSeriesByIndex(int seriesIndex) {
        webActions.waitForElementClickability(
                getByLocator(studyTreePageOR.seriesTreeGrid_Xpath, LocatorIdentifier.Xpath), 10);
        webActions.clickOnElementAtIndex(
                getByLocator(studyTreePageOR.studyTreeSeriesCheckbox_Xpath, LocatorIdentifier.Xpath), seriesIndex);
        waitForDataToLoad();
    }

    public void hoverOnFirstSeriesOnStudyTree() {
        webActions.mouseHover(getByLocator(studyTreePageOR.seriesExpandIcon_Xpath, LocatorIdentifier.Xpath));
    }

    public int getInstanceCountForExpandedSeries() {
        return webActions.getElementCount(
                getByLocator(studyTreePageOR.studyTreeInstanceCheckbox_Xpath, LocatorIdentifier.Xpath));
    }

    public void selectLastInstance() {
        int instanceCount = getInstanceCountForExpandedSeries();
        webActions.clickOnElementAtIndex(
                getByLocator(studyTreePageOR.studyTreeInstanceCheckbox_Xpath, LocatorIdentifier.Xpath),
                instanceCount - 1);
    }

    public void deleteSelectedInstance() {
        webActions
                .rightClick(getByLocator(studyTreePageOR.studyTreeSelectedInstanceRow_Xpath, LocatorIdentifier.Xpath));
        webActions.click(
                getByLocator(studyTreePageOR.studyTreeDeleteSelectedInstanceMenuItem_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyTreePageOR.confirmationPopupYesButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void deleteSelectedSeries() {
        if (webActions.isVisible(getByLocator(studyTreePageOR.searchFilterContainer_Xpath, LocatorIdentifier.Xpath),
                5)) {
            webActions.click(getByLocator(studyTreePageOR.searchFilterContainer_Xpath, LocatorIdentifier.Xpath));
        }
        webActions.waitForElementToDisappear(
                getByLocator(studyTreePageOR.searchFilterContainer_Xpath, LocatorIdentifier.Xpath), 10);
        webActions.rightClick(getByLocator(studyTreePageOR.studyTreeSelectedSeriesRow_Xpath, LocatorIdentifier.Xpath));
        webActions.scrollToElement(
                getByLocator(studyTreePageOR.studyTreeDeleteSelectedSeriesMenuItem_Xpath, LocatorIdentifier.Xpath));
        webActions.click(
                getByLocator(studyTreePageOR.studyTreeDeleteSelectedSeriesMenuItem_Xpath, LocatorIdentifier.Xpath));

        webActions.click(getByLocator(studyTreePageOR.confirmationPopupYesButton_Xpath, LocatorIdentifier.Xpath));
        waitForDataToLoad();
    }

    public void deleteSelectedPatient() {
        webActions.click(getByLocator(studyTreePageOR.deleteSelectedPatientsButton_Xpath, LocatorIdentifier.Xpath));
    }

    private int getPatientRowIndex(String patientName) {
        int patientRowIndex = -1;
        List<String> patientNames = getPatientTableColumnData("Patient Name");
        for (String patient : patientNames) {
            if (patient.contains(patientName)) {
                patientRowIndex = patientNames.indexOf(patient);
                break;
            }
        }
        return patientRowIndex;
    }

    private void enterStudyDescription(String value) {
        webActions.setText(getByLocator(studyTreePageOR.patientDetailsTextboxEditStudyModal_Xpath,
                LocatorIdentifier.Xpath, "studyDescription"), value);
        sleep(2, TimeUnit.SECONDS);
        webActions.keyPress(getByLocator(studyTreePageOR.patientDetailsTextboxEditStudyModal_Xpath,
                LocatorIdentifier.Xpath, "studyDescription"), Keys.ENTER);
    }

    private void enterServiceLocation(String value) {
        try {
            webActions.click((getByLocator(studyTreePageOR.serviceLocationBox_Xpath, LocatorIdentifier.Xpath)));
        } catch (Exception e) {
            webActions.click((getByLocator(studyTreePageOR.erroredServiceLocationBox_Xpath, LocatorIdentifier.Xpath)));
        }
        webActions.scrollToElement(
                getByLocator(studyTreePageOR.serviceLocationOption_Xpath, LocatorIdentifier.Xpath, value));
        webActions.click(getByLocator(studyTreePageOR.serviceLocationOption_Xpath, LocatorIdentifier.Xpath, value));
    }

    public void hoverOnFirstPatientOnStudyTree() {
        webActions.mouseHover((getByLocator(studyTreePageOR.firstPatientRow_Xpath, LocatorIdentifier.Xpath)));
    }

    public void expandFirstPatientOnStudyTree() {
        webActions.click((getByLocator(studyTreePageOR.clickStudyArrow_Xpath, LocatorIdentifier.Xpath)));
        waitForDataToLoad();
    }

    public void expandFirstStudyOfFirstPatientOnStudyTree() {
        webActions.click((getByLocator(studyTreePageOR.expandArrowOfFirstStudy_Xpath, LocatorIdentifier.Xpath)));
    }

    public void clickOnYesButtonInPopup() {
        webActions.click(getByLocator(studyTreePageOR.confirmationPopupYesButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void addNewPatient(String patientId, String patientFirstName, String patientLastName) {
        webActions.click(getByLocator(studyTreePageOR.addNewPatientButton_Xpath, LocatorIdentifier.Xpath));
        webActions.waitForElementPresence(
                getByLocator(studyTreePageOR.addNewPatientModal_Xpath, LocatorIdentifier.Xpath), 5);
        webActions.setText(getByLocator(studyTreePageOR.newPatientIdTextbox_Xpath, LocatorIdentifier.Xpath), patientId);
        webActions.setText(getByLocator(studyTreePageOR.newPatientLastNameTextbox_Xpath, LocatorIdentifier.Xpath),
                patientLastName);
        webActions.setText(getByLocator(studyTreePageOR.newPatientFirstNameTextbox_Xpath, LocatorIdentifier.Xpath),
                patientFirstName);
        webActions.click(getByLocator(studyTreePageOR.saveButton_Xpath, LocatorIdentifier.Xpath));
    }

    public List<String> getPatientColumnNamesDisplayed() {
        List<String> displayedColumnNames = webActions.getElementTextList(
                getByLocator(studyTreePageOR.studyTreePatientColumnHeader_Xpath, LocatorIdentifier.Xpath));
        displayedColumnNames.removeIf(columnName -> columnName.isEmpty() || columnName.equals(" "));
        return displayedColumnNames;
    }

    public List<String> getStudyColumnNamesDisplayed() {
        List<String> displayedColumnNames = webActions.getElementTextList(
                getByLocator(studyTreePageOR.studyTreeStudiesColumnHeader_Xpath, LocatorIdentifier.Xpath));
        displayedColumnNames.removeIf(columnName -> columnName.isEmpty() || columnName.equals(" ")
                || columnName.contains("1") || columnName.contains("2"));
        return displayedColumnNames;
    }

    public List<String> getSeriesColumnNamesDisplayed() {
        List<String> displayedColumnNames = webActions.getElementTextList(
                getByLocator(studyTreePageOR.studyTreeSeriesColumnHeader_Xpath, LocatorIdentifier.Xpath));
        displayedColumnNames.removeIf(columnName -> columnName.isEmpty() || columnName.equals(" "));
        return displayedColumnNames;
    }

    public List<String> getInstanceColumnNamesDisplayed() {
        List<String> displayedColumnNames = webActions.getElementTextList(
                getByLocator(studyTreePageOR.studyTreeInstanceColumnHeader_Xpath, LocatorIdentifier.Xpath));
        displayedColumnNames.removeIf(columnName -> columnName.isEmpty() || columnName.equals(" "));
        return displayedColumnNames;
    }

    public boolean isDeletePatientButtonVisible() {
        return webActions.isVisible(
                getByLocator(studyTreePageOR.deleteSelectedPatientsButton_Xpath, LocatorIdentifier.Xpath), 10);
    }

    public void editPatientId(String newPatientId) {
        webActions.setText(getByLocator(studyTreePageOR.patientDetailsTextboxEditStudyModal_Xpath,
                LocatorIdentifier.Xpath, "patientId"), newPatientId);
        webActions.click(getByLocator(studyTreePageOR.saveButton_Xpath, LocatorIdentifier.Xpath));
        waitForDataToLoad();
    }

    public String getPatientIdForFirstPatient() {
        return webActions.getCellData(getByLocator(studyTreePageOR.studyTreeTable_Xpath, LocatorIdentifier.Xpath), 1,
                4);
    }

    public void editModality(String newModality) {
        waitForDataToLoad();
        webActions.setText(getByLocator(studyTreePageOR.editModalityTextbox_Xpath, LocatorIdentifier.Xpath),
                newModality);
        webActions.click(getByLocator(studyTreePageOR.saveButton_Xpath, LocatorIdentifier.Xpath));
        waitForDataToLoad();
    }

    public String getSeriesModality(int seriesIndex) {
        return webActions.getCellData(
                getByLocator(studyTreePageOR.studyTreeSeriesListTable_Xpath, LocatorIdentifier.Xpath), seriesIndex, 4);
    }

    public void selectOptionFromRightClickMenu(String option) {
        List<String> menuOptions;
        try {
            webActions.waitForElementClickability(
                    getByLocator(studyTreePageOR.studyTreeRightClickMenu_Xpath, LocatorIdentifier.Xpath), 10);
            menuOptions = webActions.getElementTextList(
                    getByLocator(studyTreePageOR.studyTreeRightClickMenu_Xpath, LocatorIdentifier.Xpath));
            int optionIndex = menuOptions.indexOf(option);
            webActions.clickOnElementAtIndex(
                    getByLocator(studyTreePageOR.studyTreeRightClickMenu_Xpath, LocatorIdentifier.Xpath), optionIndex);
        } catch (Exception e) {
            webActions.waitForElementClickability(
                    getByLocator(studyTreePageOR.studyTreeRightClickMenuOptions_Xpath, LocatorIdentifier.Xpath), 10);
            menuOptions = webActions.getElementTextList(
                    getByLocator(studyTreePageOR.studyTreeRightClickMenuOptions_Xpath, LocatorIdentifier.Xpath));
            int optionIndex = menuOptions.indexOf(option);
            webActions.clickOnElementAtIndex(
                    getByLocator(studyTreePageOR.studyTreeRightClickMenuOptions_Xpath, LocatorIdentifier.Xpath),
                    optionIndex);
            if (webActions.isVisible(getByLocator(studyTreePageOR.confirmYesButton_Xpath, LocatorIdentifier.Xpath), 2))
                webActions.click(getByLocator(studyTreePageOR.confirmYesButton_Xpath, LocatorIdentifier.Xpath));
            if (webActions.isVisible(
                    getByLocator(studyTreePageOR.deleteStudyWarningWindow_Xpath, LocatorIdentifier.Xpath), 2))
                webActions.click(getByLocator(studyTreePageOR.deleteStudyWarningWindowContinueButton_Xpath,
                        LocatorIdentifier.Xpath));
            waitForDataToLoad();
        }
    }

    public void rightClickOnFirstPatient() {
        waitForDataToLoad();
        webActions.rightClick(getByLocator(studyTreePageOR.studyTreeRow_Xpath, LocatorIdentifier.Xpath));
    }

    public void clickStudyCollapseArrow() {
        webActions
                .scrollToElement(getByLocator(studyTreePageOR.clickStudyCollapseArrow_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyTreePageOR.clickStudyCollapseArrow_Xpath, LocatorIdentifier.Xpath));
    }

    public void clickToolBarButton(String button) {
        waitForDataToLoad();
        webActions.click(getByLocator(studyTreePageOR.toolBarButton_Xpath, LocatorIdentifier.Xpath, button));
    }

    public void selectAllPatients() {
        waitForDataToLoad();
        webActions.click(getByLocator(studyTreePageOR.selectAllPatientsCheckbox_Xpath, LocatorIdentifier.Xpath));
    }

    public void clickOnNestedFilter() {
        webActions.click(getByLocator(studyTreePageOR.nestedFilterLink_Xpath, LocatorIdentifier.Xpath));
    }

    public void clickMatchFilterDropDown() {
        webActions.click(getByLocator(studyTreePageOR.matchFilterDropDown_Xpath, LocatorIdentifier.Xpath));
    }

    public void selectOptionFromMatchFilterDropDown(String option) {
        webActions.click(getByLocator(studyTreePageOR.matchFilterOptions_Xpath, LocatorIdentifier.Xpath, option));
    }

    public void clickNestedFilterAddRemoveIcon(String button, int number) {
        for (int count = 0; count < number; count++) {
            webActions.click(
                    getByLocator(studyTreePageOR.nestedFilterAddRemoveIcon_Xpath, LocatorIdentifier.Xpath, button));
        }
    }

    public void searchPatientInNestedFilter(int indexSize, List<String> patientNames) {
        List<WebElement> patientNameTextBoxes = webActions
                .getElements(getByLocator(studyTreePageOR.nestedFilterTextBox_Xpath, LocatorIdentifier.Xpath));
        for (int index = 0; index < indexSize; index++) {
            webActions.setText(patientNameTextBoxes.get(index), patientNames.get(index));
        }
    }

    public void clickSearchButton() {
        webActions.click(getByLocator(studyTreePageOR.searchButton_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean verifySearchedPatientsArePresent(List<String> enteredPatientNameList) {
        waitForDataToLoad();
        List<String> patientNames = getPatientTableColumnData("Patient Name");
        return patientNames.containsAll(enteredPatientNameList);
    }

    public void importStudiesFromCdDvdRom() {
        webActions.click(getByLocator(studyTreePageOR.importStudiesFromCdDvdRomIcon_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isUploadWizardWindowLoaded() {
        winActions.waitForWindowElementToBeVisible(
                getByLocator(studyTreePageOR.uploadWizardWindow_Name, LocatorIdentifier.Name), 60);
        return winActions.isVisible(getByLocator(studyTreePageOR.uploadWizardWindow_Name, LocatorIdentifier.Name), 10);
    }

    public void finishWizardImporting() {
        WebElement Dialog = winActions
                .findWindowElementBy(getByLocator(studyTreePageOR.uploadWizardWindow_Name, LocatorIdentifier.Name));
        winActions.click(Dialog, getByLocator(studyTreePageOR.uploadWizardNextButton_Name, LocatorIdentifier.Name));
        sleep(3, TimeUnit.SECONDS);
        winActions.click(Dialog, getByLocator(studyTreePageOR.uploadWizardNextButton_Name, LocatorIdentifier.Name));
        winActions.waitForWindowElementToBeVisible(
                getByLocator(studyTreePageOR.availableLocationListItem_Name, LocatorIdentifier.Name), 60);
        winActions.click(Dialog, getByLocator(studyTreePageOR.availableLocationListItem_Name, LocatorIdentifier.Name));
        winActions.waitForWindowElementToBeVisible(
                getByLocator(studyTreePageOR.availableGroupListItem_Name, LocatorIdentifier.Name), 60);
        winActions.click(Dialog, getByLocator(studyTreePageOR.availableGroupListItem_Name, LocatorIdentifier.Name));
        winActions.click(Dialog, getByLocator(studyTreePageOR.uploadWizardFinishButton_Name, LocatorIdentifier.Name));
        winActions.waitForWindowElementToBeInvisible(
                getByLocator(studyTreePageOR.progressInformationDialogBox_Xpath, LocatorIdentifier.Xpath), 180);
    }

    public void selectDicomDirFile() {

        winActions.waitForWindowElementToBeVisible(
                getByLocator(studyTreePageOR.dicomDirFileRadioButton_Name, LocatorIdentifier.Name), 10);
        winActions.click(getByLocator(studyTreePageOR.dicomDirFileRadioButton_Name, LocatorIdentifier.Name));
        winActions.waitForWindowElementToBeVisible(
                getByLocator(studyTreePageOR.fileLocation_Xpath, LocatorIdentifier.Xpath), 5);
        winActions.click(getByLocator(studyTreePageOR.fileLocation_Xpath, LocatorIdentifier.Xpath));
        winActions.setText(getByLocator(studyTreePageOR.fileLocation_Xpath, LocatorIdentifier.Xpath), dicomDirFile);
    }

    public void deleteStudy() {
        webActions.click(getByLocator(studyTreePageOR.checkFirstPatient_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyTreePageOR.deleteSelectedPatientsButton_Xpath, LocatorIdentifier.Xpath));
        webActions.waitForElementPresence(getByLocator(studyTreePageOR.confirmYesButton_Xpath, LocatorIdentifier.Xpath),
                10);
        webActions.click(getByLocator(studyTreePageOR.confirmYesButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void clickOnFirstThumbnail(int thumbnailIndex) {
        webActions.clickOnElementAtIndex(getByLocator(studyTreePageOR.wadoImageIcon_Xpath, LocatorIdentifier.Xpath),
                thumbnailIndex);
    }

    public void expandFirstSeries() {
        webActions.click(getByLocator(studyTreePageOR.firstSeriesExpandButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void switchToImageWindow() {
        webActions.switchToWindow("wado (1024×768)", 10);
    }

    public String getThumbnailImageUrl() {
        return webActions.getCurrentWindowUrl();
    }

    public boolean isImageOpenedInNewTab() {
        return webActions.isVisible(getByLocator(studyTreePageOR.wadoImageIcon_Xpath, LocatorIdentifier.Xpath), 20);
    }

    public int secondPatientStudyCount() {
        int studyCount = webActions
                .getElementCount(getByLocator(studyTreePageOR.secondPatientStudyCount_Xpath, LocatorIdentifier.Xpath));
        Log.printInfo("Second Patient study count---->" + studyCount);
        return studyCount;
    }

    public void selectFirstStudyOfFirstPatient() {
        webActions.click(getByLocator(studyTreePageOR.firstStudyCheckbox_Xpath, LocatorIdentifier.Xpath));
    }

    public void selectSecondPatient() {
        webActions.click(getByLocator(studyTreePageOR.secondPatientCheckbox_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isPhysicianAssignedToStudy(String physicianName) {
        String columnData = getStudiesTableColumnData("Assigned Users").get(0);
        return columnData.contains(physicianName);
    }

    public boolean isPhysicianGroupAssignedToStudy(String physicianGroupName) {
        String columnData = getStudiesTableColumnData("Assigned Groups").get(0);
        return columnData.contains(physicianGroupName);
    }

    public void selectAllStudiesOfPatient(int patientIndex) {
        List<WebElement> selectAllStudiesCheckBoxes = webActions.getElements(
                getByLocator(studyTreePageOR.selectAllStudiesOfPatientCheckBox_Xpath, LocatorIdentifier.Xpath));
        webActions.click(selectAllStudiesCheckBoxes.get(patientIndex));
    }

    public void collapsePatientSection(int patientIndex) {
        waitForDataToLoad();
        List<WebElement> collapsePatientButtons = webActions
                .getElements(getByLocator(studyTreePageOR.collapsePatientButton_Xpath, LocatorIdentifier.Xpath));
        webActions.scrollToElement(collapsePatientButtons.get(patientIndex));
        webActions.click(collapsePatientButtons.get(patientIndex));
    }

    public void rightClickOnFirstStudy() {
        webActions.waitForElementVisibility(
                getByLocator(studyTreePageOR.firstStudyOnStudyTree_Xpath, LocatorIdentifier.Xpath), 5);
        webActions.rightClick(getByLocator(studyTreePageOR.firstStudyOnStudyTree_Xpath, LocatorIdentifier.Xpath));
    }

    public void showPatientsWithoutStudy() {
        webActions.click(getByLocator(studyTreePageOR.patientWithoutStudyButton_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isEditStudyModalPresent() {
        return webActions
                .isVisible(getByLocator(studyTreePageOR.editStudyAttributesModal_Xpath, LocatorIdentifier.Xpath), 5);
    }

    public List<String> getStudyDetailsFromEditStudyAttributesWindow() {
        List<String> studyDetails = new ArrayList<>();
        studyDetails.add(0, getPatientNameFromEditStudyAttributesWindow("patientName"));
        studyDetails.add(1, getPatientNameFromEditStudyAttributesWindow("patientId"));
        studyDetails.add(2, getPatientNameFromEditStudyAttributesWindow("accessionNumber"));
        studyDetails.add(3, getPatientNameFromEditStudyAttributesWindow("studyDescription"));
        return studyDetails;
    }

    public String getPatientNameFromEditStudyAttributesWindow(String fieldValue) {
        return webActions.getTextboxValue(getByLocator(studyTreePageOR.patientDetailsTextboxEditStudyModal_Xpath,
                LocatorIdentifier.Xpath, fieldValue));
    }

    public boolean verifyStudyDetailsOnEditStudyAttributesModal(List<String> studyDetails) {
        return getStudyDetailsFromEditStudyAttributesWindow().equals(studyDetails);
    }

    public void setFilter(String filterName, String filterValue) {
        switch (filterName) {
            case "Patient Name":
                searchPatientByName(filterValue);
                break;
        }
    }

    public boolean isStudyListEmpty() {
        return webActions.isVisible(getByLocator(studyTreePageOR.noItemsToShowLabel_Xpath, LocatorIdentifier.Xpath),
                10);
    }

    public List<String> getStudiesTableColumnData(String columnName) {
        List<String> columnTexts = webActions
                .getElementTextList(getByLocator(studyTreePageOR.studiesColumnHeaders_Xpath, LocatorIdentifier.Xpath));
        int columnIndex = columnTexts.indexOf(columnName);
        return webActions.getColumnText(
                getByLocator(studyTreePageOR.studiesTable_Xpath, LocatorIdentifier.Xpath), columnIndex + 3);
    }

    public boolean areResultsDisplayedAsPerNestedFilter(List<String> filterCriteriaList, List<String> filterValueList,
                                                        String filterCondition, String matchCondition, String resultTable) {
        if ((filterCondition.equals("is null") && matchCondition.equals("Match All"))
                || (filterCondition.equals("is not null") && matchCondition.equals("Match None"))) {
            return isStudyListEmpty();
        } else if (filterCondition.equals("is null") || filterCondition.equals("is not null")) {
            return !isStudyListEmpty();

        } else {
            List<List<String>> listOfColumnDataList = new ArrayList<>();
            for (String s : filterCriteriaList) {
                listOfColumnDataList.add(resultTable.equalsIgnoreCase("STUDIES")
                        ? getStudiesTableColumnData(s)
                        : getPatientTableColumnData(s));
            }
            int postFilterRowCount = listOfColumnDataList.get(0).size();
            List<String> rowValues = new ArrayList<>();
            boolean isNestedConditionMatched = true;
            for (int rowNumber = 0; rowNumber < postFilterRowCount; rowNumber++) {
                rowValues.clear();
                for (List<String> strings : listOfColumnDataList) {
                    rowValues.add(strings.get(rowNumber));
                }
                System.out.println(rowValues);
                boolean isRowEmpty = true;
                for (String rowValue : rowValues) {
                    isRowEmpty = isRowEmpty && (rowValue.equals(" ") || rowValue.equals(""));
                }
                if (!isRowEmpty) {
                    isNestedConditionMatched = isNestedConditionMatched && (isNestedFilterConditionMet(filterCondition,
                            rowValues, filterValueList, matchCondition));
                }
            }
            return isNestedConditionMatched;
        }
    }

    public boolean isDeleteEntityDisabled(String studyEntity) {
        webActions.scrollToElement(
                getByLocator(studyTreePageOR.disabledDeleteIcon_Xpath, LocatorIdentifier.Xpath, studyEntity));
        return webActions.isVisible(
                getByLocator(studyTreePageOR.disabledDeleteIcon_Xpath, LocatorIdentifier.Xpath, studyEntity));
    }

    public boolean isDeleteEntityEnabled(String studyEntity) {
        webActions.scrollToElement(
                getByLocator(studyTreePageOR.deleteStudyEntityButton_Xpath, LocatorIdentifier.Xpath, studyEntity));
        return webActions.isVisible(
                getByLocator(studyTreePageOR.deleteStudyEntityButton_Xpath, LocatorIdentifier.Xpath, studyEntity));
    }

    public void hoverOnDeleteIcon(String studyEntity) {
        webActions.scrollToElement(
                getByLocator(studyTreePageOR.disabledDeleteIcon_Xpath, LocatorIdentifier.Xpath, studyEntity));
        webActions.mouseHover(
                getByLocator(studyTreePageOR.disabledDeleteIcon_Xpath, LocatorIdentifier.Xpath, studyEntity));
    }

    public boolean isTooltipDisplayedForDeleteIcon(String studyEntity) {
        return webActions.isVisible(
                getByLocator(studyTreePageOR.deleteButtonTooltipLabel_Xpath, LocatorIdentifier.Xpath, studyEntity));
    }

    public void expandCollapseStudy(String iconType, int studyNumber) {
        webActions.waitForElementClickability(
                getByLocator(studyTreePageOR.studyTreeGrid_Xpath, LocatorIdentifier.Xpath), 10);
        String icon = iconType.equalsIgnoreCase("EXPAND") ? "collapse" : "expand";
        List<WebElement> studies = webActions.getElements(
                getByLocator(studyTreePageOR.studiesExpandCollapseIcon_Xpath, LocatorIdentifier.Xpath, icon));

        try {
            webActions.scrollToElement(studies.get(studyNumber - 1));
        } catch (StaleElementReferenceException e) {
            studies = webActions.getElements(
                    getByLocator(studyTreePageOR.studiesExpandCollapseIcon_Xpath, LocatorIdentifier.Xpath, icon));
            webActions.scrollToElement(studies.get(studyNumber - 1));
        }
        webActions.waitForElementClickability(studies.get(studyNumber - 1), 5);
        webActions.click(studies.get(studyNumber - 1));
    }

    public void expandCollapsePatient(String iconType, int patientNumber) {
        webActions.waitForElementClickability(
                getByLocator(studyTreePageOR.patientTreeGrid_Xpath, LocatorIdentifier.Xpath), 10);
        String icon = iconType.equalsIgnoreCase("EXPAND") ? "collapse" : "expand";
        List<WebElement> patients = webActions.getElements(
                getByLocator(studyTreePageOR.patientExpandCollapseIcon_Xpath, LocatorIdentifier.Xpath, icon));
        try {
            webActions.scrollToElement(patients.get(patientNumber - 1));
        } catch (StaleElementReferenceException e) {
            patients = webActions.getElements(
                    getByLocator(studyTreePageOR.patientExpandCollapseIcon_Xpath, LocatorIdentifier.Xpath, icon));
            webActions.scrollToElement(patients.get(patientNumber - 1));
        }
        webActions.click(patients.get(patientNumber - 1));
    }

    public int getSeriesCountForExpandedStudy() {
        webActions.waitForElementClickability(
                getByLocator(studyTreePageOR.seriesTreeGrid_Xpath, LocatorIdentifier.Xpath), 10);
        return webActions
                .getElementCount(getByLocator(studyTreePageOR.studyTreeSeriesCheckbox_Xpath, LocatorIdentifier.Xpath));
    }

    public void acceptWarningAtStudyCreation() {
        if (webActions.isVisible(getByLocator(studyTreePageOR.alertDialogBox_Xpath, LocatorIdentifier.Xpath), 10)) {
            webActions.click(getByLocator(studyTreePageOR.confirmationPopupYesButton_Xpath, LocatorIdentifier.Xpath));
        }
    }

    public List<String> getseriesGridColumnData(String columnName) {
        webActions.waitForElementVisibility(
                getByLocator(studyTreePageOR.seriesGridRowHeader_Xpath, LocatorIdentifier.Xpath), 10);
        List<String> columnTexts = webActions.getElementTextList(
                getByLocator(studyTreePageOR.seriesGridRowHeader_Xpath, LocatorIdentifier.Xpath));
        int columnIndex = columnTexts.indexOf(columnName);
        return webActions.getColumnText(getByLocator(studyTreePageOR.seriesGridColumn_Xpath, LocatorIdentifier.Xpath),
                columnIndex + 3);
    }

    public int getNumberOfInstancesForStudy() {
        if (webActions.isVisible(getByLocator(studyTreePageOR.searchFilterContainer_Xpath, LocatorIdentifier.Xpath),
                5)) {
            webActions.click(getByLocator(studyTreePageOR.searchFilterContainer_Xpath, LocatorIdentifier.Xpath));
        }
        int numberOfInstances = 0;
        webActions.waitForElementClickability(
                getByLocator(studyTreePageOR.seriesTreeGrid_Xpath, LocatorIdentifier.Xpath), 10);
        List<String> seriesInstanceCount = getseriesGridColumnData("Number Of Instances");
        seriesInstanceCount.removeIf(noOfInstances -> noOfInstances.equals("") ||
                noOfInstances.isEmpty() || noOfInstances.equals(" "));
        for (String instancesCount : seriesInstanceCount) {
            numberOfInstances = numberOfInstances + Integer.parseInt(instancesCount);
        }
        return numberOfInstances;
    }
}