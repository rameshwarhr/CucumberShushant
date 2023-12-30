package Pages;

import Actions.RobotActions;
import Actions.SikuliActions;
import Actions.WebActions;
import Actions.WindowsActions;
import ObjectRepository.StudyListPageOR;
import TestData.TestData;
import Utilities.*;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static Utilities.Common.sleep;
import static Utilities.Constants.StudyListPageORFile;
import static Utilities.Constants.USER_WORK_DIR;

public class StudyListPage extends Page {

    Driver driver;
    WebActions webActions;
    Property property;
    RobotActions robotActions;
    PdfReader pdfReader;
    WindowsActions winActions;
    SikuliActions sikuliActions;

    public static final String StudyListScreenElements = USER_WORK_DIR
            + "\\src\\test\\resources\\StudyListScreenElements\\";

    public StudyListPage(Driver driver) {
        super(driver);
        this.driver = driver;
        webActions = new WebActions(driver.getWebDriver());
        property = new Property();
        robotActions = new RobotActions();
        pdfReader = new PdfReader();
        winActions = new WindowsActions(driver.getWinDriver());
        sikuliActions = new SikuliActions();
    }

    private static final StudyListPageOR studyListPageOR = YmlReader
            .getObjectRepository(USER_WORK_DIR + StudyListPageORFile, StudyListPageOR.class);

    private final By patientNameTextbox = getByLocator(studyListPageOR.patientNameTextbox_Xpath,
            LocatorIdentifier.Xpath);
    private final By patientIdTextbox = getByLocator(studyListPageOR.patientIdTextbox_Xpath, LocatorIdentifier.Xpath);
    private final By tabBar = getByLocator(studyListPageOR.tabBar_Xpath, LocatorIdentifier.Xpath);
    private final By studyListTable = getByLocator(studyListPageOR.studyListTable_Xpath, LocatorIdentifier.Xpath);
    private final By studyListRowPdfButton = getByLocator(studyListPageOR.studyListRowPdfButton_Xpath,
            LocatorIdentifier.Xpath);

    public List<String> getTabNamesDisplayed() {
        return webActions.getTabNames(tabBar);
    }

    public void searchForPatientName(String patientName) {
        webActions.setText(patientNameTextbox, patientName);
        waitForDataToLoad();
    }

    public void searchForPatientName(String firstName, String lastName) {
        webActions.setText(patientNameTextbox, lastName + "^" + firstName);
        waitForDataToLoad();
    }

    public void searchForPatientId(String searchPatientID) {
        webActions.setText(patientIdTextbox, searchPatientID);
        waitForDataToLoad();
    }

    public boolean isStudyListFilteredByStartDate(String numOfDays) {
        boolean flag = false;

        List<String> studyDates = fetchDateFilteredStudyList();
        studyDates.removeIf(data -> data.equals("") || data.isEmpty() || data.equals(" "));

        String strDaysAgo = getStudyDate(numOfDays);
        int daysAgo = Integer.parseInt(strDaysAgo);

        String studyStartDate = Common.GetTestDate(-daysAgo);
        String StudyEndDate = Common.GetTestDate(0);
        Date studyStartDate1 = Common.StringToDate(studyStartDate);
        Date studyStartDate3 = Common.StringToDate(StudyEndDate);

        for (String date : studyDates) {
            Date studyDate = Common.StringToDate(date);
            flag = (studyDate.after(studyStartDate1) || studyDate.equals(studyStartDate1))
                    && (studyDate.before(studyStartDate3) || studyDate.equals(studyStartDate3));
        }
        return flag;
    }

    public List<String> fetchDateFilteredStudyList() {
        List<String> dates = new ArrayList<>();
        String actualDate;
        List<String> studyDates = getColumnData("Study Date/Time");
        for (String studyDate : studyDates) {
            String[] arr_studyDates = studyDate.split(" ");
            actualDate = arr_studyDates[0];
            dates.add(actualDate);
        }
        return dates;
    }

    public void selectStudyWithName(String patientName) {
        String lastName = patientName.split("\\^")[0];
        String firstName = patientName.split("\\^")[1];
        By studyListRowWithPatientName = getByLocator(studyListPageOR.studyListRowWithName_Xpath,
                LocatorIdentifier.Xpath, lastName.toUpperCase(), firstName.toUpperCase());
        webActions.click(studyListRowWithPatientName);
    }

    public void selectStudyWithName(String firstName, String lastName) {
        By studyListRowWithPatientName = getByLocator(studyListPageOR.studyListRowWithName_Xpath,
                LocatorIdentifier.Xpath, lastName.toUpperCase(), firstName.toUpperCase());
        webActions.click(studyListRowWithPatientName);
    }

    public void openPdfForPatient(String firstName, String lastName) {
        webActions.mouseHover(
                getByLocator(studyListPageOR.studyListRowWithName_Xpath, LocatorIdentifier.Xpath, firstName, lastName));
        sleep(2, TimeUnit.SECONDS);
        webActions.click(studyListRowPdfButton);
        sleep(10, TimeUnit.SECONDS);
    }

    public void clearPatientNameText() {
        webActions.clearText(patientNameTextbox);
    }

    public void clearPatientIdText() {
        webActions.clearText(patientIdTextbox);
    }

    public void revertSignedReport(String patientFirstName, String patientLastName) {
        By studyListRowForPatient = getByLocator(studyListPageOR.studyListRowWithName_Xpath, LocatorIdentifier.Xpath,
                patientFirstName, patientLastName);
        webActions.mouseHover(studyListRowForPatient);
        webActions.rightClick(studyListRowForPatient);
        selectOptionFromRightClickMenu("Edit Study Attributes");
        webActions.click(getByLocator(studyListPageOR.advancedTab_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyListPageOR.finalReportStatusDropdown_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyListPageOR.scheduledOption_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyListPageOR.saveStudyAttributesButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void revertPrelimSignedReport(String patientFirstName, String patientLastName) {
        By studyListRowForPatient = getByLocator(studyListPageOR.studyListRowWithName_Xpath, LocatorIdentifier.Xpath,
                patientFirstName, patientLastName);
        webActions.mouseHover(studyListRowForPatient);
        webActions.rightClick(studyListRowForPatient);
        selectOptionFromRightClickMenu("Edit Study Attributes");
        webActions.click(getByLocator(studyListPageOR.advancedTab_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyListPageOR.worksheetReportStatus_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyListPageOR.scheduledOption_Xpath, LocatorIdentifier.Xpath));
        robotActions.enter();
        waitForDataToLoad();
    }

    public void sortByColumnByOrder(String columnName, String sortOrder) {
        webActions.waitForElementsClickability(
                getByLocator(studyListPageOR.studyListColumnHeader_Xpath, LocatorIdentifier.Xpath, columnName), 30);
        webActions.mouseHover(
                getByLocator(studyListPageOR.studyListColumnHeader_Xpath, LocatorIdentifier.Xpath, columnName));
        sleep(2, TimeUnit.SECONDS);
        webActions.click(getByLocator(studyListPageOR.studyListHeaderMenuButton_Xpath, LocatorIdentifier.Xpath));
        if (sortOrder.equalsIgnoreCase("ascending")) {
            webActions
                    .click(getByLocator(studyListPageOR.studyListSortAscendingMenuItem_Xpath, LocatorIdentifier.Xpath));
        } else if (sortOrder.equalsIgnoreCase("descending")) {
            webActions.click(
                    getByLocator(studyListPageOR.studyListSortDescendingMenuItem_Xpath, LocatorIdentifier.Xpath));
        }
        sleep(5, TimeUnit.SECONDS);
    }

    public List<String> getColumnData(String columnName) {
        webActions.waitForElementVisibility(
                getByLocator(studyListPageOR.studyListColumnHeaders_Xpath, LocatorIdentifier.Xpath), 10);
        List<String> columnTexts = webActions.getElementTextList(
                getByLocator(studyListPageOR.studyListColumnHeaders_Xpath, LocatorIdentifier.Xpath));
        int columnIndex = columnTexts.indexOf(columnName);
        webActions.waitForElementVisibility(getByLocator(studyListPageOR.studyListTable_Xpath, LocatorIdentifier.Xpath),
                30);
        return webActions.getColumnText(getByLocator(studyListPageOR.studyListTable_Xpath, LocatorIdentifier.Xpath),
                columnIndex + 1);
    }

    public String getStudyDate(String input) {
        return input.split(" ")[0];
    }

    public void applyStudyStartDateFilter(String numOfDays) {
        webActions.setText(getByLocator(studyListPageOR.startDateInput_Xpath, LocatorIdentifier.Xpath), "N days ago");
        waitForDataToLoad();
        webActions.setText(getByLocator(studyListPageOR.startDateQuantityField_Xpath, LocatorIdentifier.Xpath),
                numOfDays);
    }

    public boolean isStudyListFiltered(String filterName, String filterValue) {
        switch (filterName.toUpperCase()) {
            case "STUDY START DATE":
                return isStudyListFilteredByStartDate(filterValue);
            case "PATIENT ID":
            case "PATIENT NAME":
            case "ASSIGNED USERS":
            case "WORKSHEET":
            case "FINAL REPORT":
            case "ASSIGNED GROUPS":
            case "CHART #":
            case "DESCRIPTION":
            case "STUDY DATE/TIME":
                List<String> columnData = getColumnData(filterName);
                columnData.removeIf(data -> data.equals("") || data.isEmpty() || data.equals(" "));
                columnData.forEach(Log::printInfo);
                return columnData.stream().allMatch(row -> row.contains(filterValue));
            case "MODALITY":
                String modalityFilter = webActions
                        .getTextboxValue(getByLocator(studyListPageOR.modalityTextbox_Xpath, LocatorIdentifier.Xpath));
                webActions.rightClick(getByLocator(studyListPageOR.firstPatient_Xpath, LocatorIdentifier.Xpath));
                webActions.click(getByLocator(studyListPageOR.editStudyAttributes_Xpath, LocatorIdentifier.Xpath));
                sleep(3, TimeUnit.SECONDS);
                String modalityEdit = webActions
                        .getTextboxValue(getByLocator(studyListPageOR.modalityEdit_Xpath, LocatorIdentifier.Xpath));
                robotActions.escape();
                return modalityEdit.contains(modalityFilter);
            case "STUDY ID":
                String studyIdFilter = webActions
                        .getTextboxValue(getByLocator(studyListPageOR.studyId_Xpath, LocatorIdentifier.Xpath));
                webActions.rightClick(getByLocator(studyListPageOR.firstPatient_Xpath, LocatorIdentifier.Xpath));
                webActions.click(getByLocator(studyListPageOR.editStudyAttributesStudyId_Xpath, LocatorIdentifier.Xpath));
                sleep(3, TimeUnit.SECONDS);
                String studyValue = webActions
                        .getTextboxValue(getByLocator(studyListPageOR.studyIdTextbox_Xpath, LocatorIdentifier.Xpath));
                robotActions.escape();
                return studyValue.contains(studyIdFilter);
            default:
                return false;
        }
    }

    public boolean isStudyListPageDisplayed() {
        String studyListTabClassValue = webActions.getElementAttributeValue(
                getByLocator(studyListPageOR.studyListTab_Xpath, LocatorIdentifier.Xpath), "class");
        webActions.waitForElementVisibility(getByLocator(studyListPageOR.studyListTable_Xpath, LocatorIdentifier.Xpath),
                10);
        boolean isStudyListTabSelected = studyListTabClassValue.contains("Selected");
        boolean isStudyListTableDisplayed = webActions
                .isVisible(getByLocator(studyListPageOR.studyListTable_Xpath, LocatorIdentifier.Xpath));
        return isStudyListTabSelected && isStudyListTableDisplayed;
    }

    public void clickAdvancedFilter() {
        webActions.click(getByLocator(studyListPageOR.advancedFilterLink_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isAdvancedFilterUnfolded() {
        List<String> expectedAdvancedFilterLabels = Arrays.asList("DOB Start", "DOB End", "Assigned Users",
                "Assigned Groups", "Worksheet", "Final Report", "Chart #", "Modality", "Study ID", "Service Location");
        webActions.waitForElementVisibility(
                getByLocator(studyListPageOR.advancedFilterLabels_Xpath, LocatorIdentifier.Xpath), 10);
        List<String> actualAdvancedFilterLabels = webActions
                .getElementTextList(getByLocator(studyListPageOR.advancedFilterLabels_Xpath, LocatorIdentifier.Xpath));
        return expectedAdvancedFilterLabels.equals(actualAdvancedFilterLabels);
    }

    public void setFilter(String filterName, String filterValue) {
        String nDaysAgo;
        switch (filterName) {

            case "Patient Name":
                searchForPatientName(filterValue);
                break;

            case "Patient ID":
                searchForPatientId(filterValue);
                break;

            case "Study Start Date":
                nDaysAgo = getStudyDate(filterValue);
                applyStudyStartDateFilter(nDaysAgo);
                break;

            case "Assigned Users":
                applyAssignedUserFilter(filterValue);
                break;

            case "Worksheet":
                applyWorksheetFilter(filterValue);
                break;

            case "Final Report":
                applyFinalReportFilter(filterValue);
                break;

            case "Modality":
                applyModalityFilter(filterValue);
                break;

            case "Study ID":
                applyStudyIdFilter(filterValue);
                break;

            case "Chart #":
                applyChartNumberFilter(filterValue);
                break;

            case "Study End Date":
                nDaysAgo = getStudyDate(filterValue);
                applyStudyEndDateFilter(nDaysAgo);
                break;

            case "Assigned Groups":
                applyAssignedGroupsFilter(filterValue);
                break;
        }
    }

    public void clearFilter(String filterName, String filterValue) {
        switch (filterName.toUpperCase()) {
            case "PATIENT NAME":
                clearPatientNameText();
                break;

            case "PATIENT ID":
                clearPatientIdText();
                break;

            case "ASSIGNED USERS":
                clearAssignedUserFilter(filterValue);
                break;

            case "WORKSHEET":
                clearWorkSheetFilter();
                break;

            case "FINAL REPORT":
                clearFinalReportFilter();
                break;

            case "MODALITY":
                clearModalityFilter();
                break;

            case "STUDY ID":
                clearStudyIdFilter();
                break;
        }
    }

    public void applyAssignedUserFilter(String filterValue) {
        webActions.click(getByLocator(studyListPageOR.assignedUsersDropdown_Xpath, LocatorIdentifier.Xpath));
        webActions.setText(getByLocator(studyListPageOR.assignedUserTextbox_Xpath, LocatorIdentifier.Xpath),
                filterValue);
        webActions.click(getByLocator(studyListPageOR.assignedUserCheckbox_Xpath, LocatorIdentifier.Xpath));
        robotActions.escape();
        waitForDataToLoad();
    }

    public void applyWorksheetFilter(String filterValue) {
        webActions.click(getByLocator(studyListPageOR.worksheetDropdown_Xpath, LocatorIdentifier.Xpath));
        webActions.click(
                getByLocator(studyListPageOR.worksheetValue_Xpath, LocatorIdentifier.Xpath, filterValue.toUpperCase()));
        waitForDataToLoad();
    }

    public void applyFinalReportFilter(String filterValue) {
        webActions.click(getByLocator(studyListPageOR.finalReportDropdown_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyListPageOR.finalReportValue_Xpath, LocatorIdentifier.Xpath,
                filterValue.toUpperCase()));
        waitForDataToLoad();
    }

    public void applyModalityFilter(String filterValue) {
        webActions.setText(getByLocator(studyListPageOR.modalityTextbox_Xpath, LocatorIdentifier.Xpath), filterValue);
        waitForDataToLoad();
    }

    public void applyStudyIdFilter(String filterValue) {
        webActions.setText(getByLocator(studyListPageOR.studyIdTextbox_Xpath, LocatorIdentifier.Xpath), filterValue);
        waitForDataToLoad();
    }

    public void applyChartNumberFilter(String filterValue) {
        webActions.setText(getByLocator(studyListPageOR.chartNumberTextbox_Xpath, LocatorIdentifier.Xpath),
                filterValue);
        waitForDataToLoad();
    }

    public void clearAssignedUserFilter(String filterValue) {
        waitForDataToLoad();
        webActions.click(getByLocator(studyListPageOR.assignedUsersDropdown_Xpath, LocatorIdentifier.Xpath));
        webActions.setText(getByLocator(studyListPageOR.assignedUserTextbox_Xpath, LocatorIdentifier.Xpath),
                filterValue);
        webActions.click(getByLocator(studyListPageOR.assignedUserCheckbox_Xpath, LocatorIdentifier.Xpath));
        robotActions.escape();
    }

    public void clearWorkSheetFilter() {
        robotActions.pressTabKey();
        webActions.click(getByLocator(studyListPageOR.worksheetDropdown_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyListPageOR.blankListItem_Xpath, LocatorIdentifier.Xpath));
    }

    public void clearFinalReportFilter() {
        robotActions.pressTabKey();
        webActions.click(getByLocator(studyListPageOR.finalReportDropdown_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyListPageOR.blankListItem_Xpath, LocatorIdentifier.Xpath));
    }

    public void clearModalityFilter() {
        webActions.clearText(getByLocator(studyListPageOR.modalityTextbox_Xpath, LocatorIdentifier.Xpath));
    }

    public void clearStudyIdFilter() {
        webActions.clearText(getByLocator(studyListPageOR.studyIdTextbox_Xpath, LocatorIdentifier.Xpath));
    }

    public void rightClickOnFirstStudy() {
        webActions.rightClick(getByLocator(studyListPageOR.studyListRow_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isOptionPresentInRightClickMenu(String option) {
        List<String> menuOptions = webActions.getElementTextList(
                getByLocator(studyListPageOR.studyListRightClickMenuOptions_Xpath, LocatorIdentifier.Xpath));
        return menuOptions.contains(option);
    }

    public void selectOptionFromRightClickMenu(String option) {
        List<String> menuOptions = webActions.getElementTextList(
                getByLocator(studyListPageOR.studyListRightClickMenuOptions_Xpath, LocatorIdentifier.Xpath));
        int optionIndex = menuOptions.indexOf(option);
        webActions.clickOnElementAtIndex(
                getByLocator(studyListPageOR.studyListRightClickMenuOptions_Xpath, LocatorIdentifier.Xpath),
                optionIndex);
    }

    public boolean isSimpleViewerOpenedForPatient(String patientName) {
        List<String> tabNames = webActions
                .getElementTextList(getByLocator(studyListPageOR.studyListPageTabs_Xpath, LocatorIdentifier.Xpath));
        return tabNames.stream().anyMatch(tabName -> tabName.contains(patientName));
    }

    public void assignServiceLocationToStudyInStudyList(String Location) {
        webActions.click(getByLocator(studyListPageOR.serviceLocationDropDown_Xpath, LocatorIdentifier.Xpath));
        webActions.setText(getByLocator(studyListPageOR.serviceLocationTextBox_Xpath, LocatorIdentifier.Xpath),
                Location);
        robotActions.enter();
    }

    public void clickYesButton() {
        webActions.click(getByLocator(studyListPageOR.yesPopupButton_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isTabDisplayed(String tabName) {
        List<String> tabNames = webActions
                .getElementTextList(getByLocator(studyListPageOR.studyListPageTabs_Xpath, LocatorIdentifier.Xpath));
        return tabNames.contains(tabName);
    }

    public void acceptErrorPopupIfDisplayed() {
        try {
            if (webActions.isVisible(
                    getByLocator(studyListPageOR.studyValidationErrorDialog_Xpath, LocatorIdentifier.Xpath), 2)) {
                webActions.click(getByLocator(studyListPageOR.studyValidationErrorDialogYesButton_Xpath,
                        LocatorIdentifier.Xpath));
            }
        } catch (Exception e) {
            Log.printInfo("Study validation error popup not displayed");
        }
    }

    public void hoverOnFirstStudy() {
        webActions.mouseHover(getByLocator(studyListPageOR.firstPatient_Xpath, LocatorIdentifier.Xpath));
        sleep(1, TimeUnit.SECONDS);
    }

    public void hoverAgainOnSameStudy() {
        int retryCount = 0;
        while (!isFloatingToolbarVisible() && retryCount < 2) {
            hoverOnFirstStudy();
            retryCount++;
        }
    }

    public boolean isFaxQueuedIconVisible() {
        return webActions.isVisible(getByLocator(studyListPageOR.faxQueuedIcon_Xpath, LocatorIdentifier.Xpath), 5);
    }

    public void clickOnPresetDropdown() {
        webActions.click(getByLocator(studyListPageOR.presetDropdownButton_Xpath, LocatorIdentifier.Xpath));
    }

    public List<String> getPresetFilterOptions() {
        return webActions
                .getElementTextList(getByLocator(studyListPageOR.presetFilterOptions_Xpath, LocatorIdentifier.Xpath));
    }

    public String getStudyCount() {
        return webActions.getText(getByLocator(studyListPageOR.studyCountLabel_Xpath, LocatorIdentifier.Xpath))
                .split(" ")[1];
    }

    public void selectPresetFilter(String presetFilterName) {
        for (WebElement presetFilter : webActions
                .getElements(getByLocator(studyListPageOR.presetFilterOptions_Xpath, LocatorIdentifier.Xpath))) {
            if (presetFilter.getText().equals(presetFilterName)) {
                presetFilter.click();
                break;
            }
        }
    }

    public boolean isFilterSetToExpectedValue(String filterName, String filterValue) {
        switch (filterName.toUpperCase()) {
            case "STUDY START DATE":
                return webActions
                        .getTextboxValue(
                                getByLocator(studyListPageOR.studyStartDateInputField_Xpath, LocatorIdentifier.Xpath))
                        .equalsIgnoreCase(filterValue);

            case "START DATE N VALUE":
                return webActions
                        .getTextboxValue(
                                getByLocator(studyListPageOR.startDateQuantityField_Xpath, LocatorIdentifier.Xpath))
                        .equalsIgnoreCase(filterValue);

            case "STUDY END DATE":
                return webActions
                        .getTextboxValue(
                                getByLocator(studyListPageOR.studyEndDateInputField_Xpath, LocatorIdentifier.Xpath))
                        .equalsIgnoreCase(filterValue);

            case "FINAL REPORT":
                return webActions
                        .getText(
                                getByLocator(studyListPageOR.finalReportFilterSelectedValue_Xpath, LocatorIdentifier.Xpath))
                        .equalsIgnoreCase(filterValue);

            case "WORKSHEET":
                return webActions
                        .getText(getByLocator(studyListPageOR.worksheetFilterSelectedValue_Xpath, LocatorIdentifier.Xpath))
                        .equalsIgnoreCase(filterValue);

            case "PATIENT NAME":
                return webActions
                        .getTextboxValue(getByLocator(studyListPageOR.patientNameTextbox_Xpath, LocatorIdentifier.Xpath))
                        .equalsIgnoreCase(filterValue);

            case "ASSIGNED GROUPS":
                return webActions.getText(
                        getByLocator(studyListPageOR.assignedGroupsFilterSelectedValue_Xpath, LocatorIdentifier.Xpath))
                        .equalsIgnoreCase(filterValue);

            case "CHART #":
                return webActions
                        .getTextboxValue(getByLocator(studyListPageOR.chartNumberTextbox_Xpath, LocatorIdentifier.Xpath))
                        .equalsIgnoreCase(filterValue);

            default:
                return false;
        }
    }

    public void removedAssignedPhysician() {
        webActions.doubleClick(getByLocator(studyListPageOR.assignedUserNameCell_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyListPageOR.saveStudyAttributesButton_Xpath, LocatorIdentifier.Xpath));
    }

    public String getLatestFaxSentTimeStamp() {

        return webActions.getText(getByLocator(studyListPageOR.faxSentTimeStampCell_Xpath, LocatorIdentifier.Xpath));

    }

    public boolean isFaxLogWindowDisplayed() {
        return webActions
                .isVisible(getByLocator(studyListPageOR.faxLogDialogCloseButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void closeFaxLogWindow() {
        webActions.click(getByLocator(studyListPageOR.faxLogDialogCloseButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void clickPhoneIcon() {
        webActions.click(getByLocator(studyListPageOR.faxQueuedIcon_Xpath, LocatorIdentifier.Xpath));
        webActions.waitForElementToDisappear(
                getByLocator(studyListPageOR.loadingIndicator_Xpath, LocatorIdentifier.Xpath), 5);
    }

    public boolean isEditStudyModalPresent() {
        return webActions
                .isVisible(getByLocator(studyListPageOR.editStudyAttributesModal_Xpath, LocatorIdentifier.Xpath), 5);
    }

    public void editDescription(String newDescription) {
        webActions.setText(getByLocator(studyListPageOR.patientDetailsTextboxEditStudyModal_Xpath,
                LocatorIdentifier.Xpath, "studyDescription"), newDescription);
        webActions.click(getByLocator(studyListPageOR.saveStudyAttributesButton_Xpath, LocatorIdentifier.Xpath));
    }

    public String getDescriptionForFirstStudy() {
        return webActions.getCellData(studyListTable, 1, 7);
    }

    public void openReportPDFForTheStudy(String firstName, String lastName) {
        webActions.mouseHover(getByLocator(studyListPageOR.studyListRowWithName_Xpath, LocatorIdentifier.Xpath,
                lastName.toUpperCase(), firstName.toUpperCase()));
        webActions.click(getByLocator(studyListPageOR.studyListRowPdfButton_Xpath, LocatorIdentifier.Xpath));
    }

    public String getReportPdfData() {
        webActions.switchToWindow(Constants.reportWindowTitle, 20);
        String reportWindowUrl = webActions.getCurrentWindowUrl();
        return pdfReader.getPDFData(reportWindowUrl, TestData.userDetails.globalAdminUsername,
                TestData.userDetails.globalAdminPassword);
    }

    public void switchToVidistarWindow() {
        webActions.switchToWindow(Constants.vidistarWindowTitle, 20);
    }

    public void applyStudyEndDateFilter(String numOfDays) {

        webActions.setText(getByLocator(studyListPageOR.endDateInputField_Xpath, LocatorIdentifier.Xpath),
                "N days ago");
        waitForDataToLoad();
        webActions.setText(getByLocator(studyListPageOR.endDateQuantityField_Xpath, LocatorIdentifier.Xpath),
                numOfDays);
    }

    public void applyAssignedGroupsFilter(String filterValue) {
        webActions.click(getByLocator(studyListPageOR.assignedGroupsDropdown_Xpath, LocatorIdentifier.Xpath));
        webActions.click(
                getByLocator(studyListPageOR.assignedGroupInputField_Xpath, LocatorIdentifier.Xpath, filterValue));
        robotActions.escape();
        waitForDataToLoad();
    }

    public void savePreset(String presetName) {
        webActions.click(getByLocator(studyListPageOR.saveAsPresetLink_Xpath, LocatorIdentifier.Xpath));
        webActions.setText(getByLocator(studyListPageOR.presetNameInputField_Xpath, LocatorIdentifier.Xpath),
                presetName);
        webActions.click(getByLocator(studyListPageOR.savePresetButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void deletePreset(String presetName) {
        waitForStudyListToLoadTheData();
        webActions.click(getByLocator(studyListPageOR.managePresetLink_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyListPageOR.presetToBeDeleted_Xpath, LocatorIdentifier.Xpath, presetName));
        webActions.click(getByLocator(studyListPageOR.deletePresetButton_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyListPageOR.savePresetButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void addReferringPhysician(String physicianName) {
        if (webActions.isVisible(getByLocator(studyListPageOR.assignedUserModalUserIdLabel_Xpath,
                LocatorIdentifier.Xpath, physicianName), 2)) {
            Log.printInfo("Assigned Referring physician is already present");
        } else {
            webActions.doubleClick(
                    getByLocator(studyListPageOR.physicianNameLabel_Xpath, LocatorIdentifier.Xpath, physicianName));
        }
        webActions.click(getByLocator(studyListPageOR.saveButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void removeReferringPhysician(String physicianName) {
        if (webActions.isVisible(getByLocator(studyListPageOR.assignedUserModalUserIdLabel_Xpath,
                LocatorIdentifier.Xpath, physicianName), 2)) {
            webActions.doubleClick(
                    getByLocator(studyListPageOR.physicianNameLabel_Xpath, LocatorIdentifier.Xpath, physicianName));
        }
        webActions.click(getByLocator(studyListPageOR.saveButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void revertAssignedReferringPhysician(String patientFirstName, String patientLastName,
                                                 String physicianName) {
        By studyListRowForPatient = getByLocator(studyListPageOR.studyListRowWithName_Xpath, LocatorIdentifier.Xpath,
                patientFirstName, patientLastName);
        webActions.mouseHover(studyListRowForPatient);
        webActions.rightClick(studyListRowForPatient);
        selectOptionFromRightClickMenu("Assign Study to Referring Physicians");
        removeReferringPhysician(physicianName);
    }

    public void downloadRichViewer() {
        webActions.waitForElementClickability(
                getByLocator(studyListPageOR.viewerDownloadButton_Xpath, LocatorIdentifier.Xpath), 5);
        webActions.click(getByLocator(studyListPageOR.viewerDownloadButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void clickServiceLocationDropdown() {
        webActions.click(
                getByLocator(studyListPageOR.advancedFilterServiceLocationDropDown_Xpath, LocatorIdentifier.Xpath));
    }

    public void searchServiceLocation(String serviceLocation) {
        webActions.setText(
                getByLocator(studyListPageOR.advancedFilterServiceLocationTextBox_Xpath, LocatorIdentifier.Xpath),
                serviceLocation);
    }

    public void closeServiceLocation() {
        int counter = 0;
        while (webActions.isVisible(getByLocator(studyListPageOR.filterPickList_Xpath, LocatorIdentifier.Xpath), 5)
                && counter < 5) {
            robotActions.pressTabKey();
            counter++;
        }
    }

    public String getAssignedPhysicianName(String refPhysicianName) {
        return webActions.getText(getByLocator(studyListPageOR.assignedUserModalUserIdLabel_Xpath,
                LocatorIdentifier.Xpath, refPhysicianName));
    }

    public void assignOrRemoveReferringPhysician(String action, String physicianName) {
        if (action.equalsIgnoreCase("add")) {
            webActions.setText(getByLocator(studyListPageOR.physicianUserIdTextBox_Xpath, LocatorIdentifier.Xpath),
                    physicianName);
        }
        webActions.doubleClick(
                getByLocator(studyListPageOR.physicianNameLabel_Xpath, LocatorIdentifier.Xpath, physicianName));
    }

    public void clickSaveButton() {
        webActions.click(getByLocator(studyListPageOR.saveButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void switchToImageUrl(String imageThumbnailUrl) {
        webActions.goToUrl(imageThumbnailUrl);
    }

    public String getAssignedUserForFirstStudy() {
        return getColumnData("Assigned Users").get(0);
    }

    public void refreshPage() {
        webActions.refreshPage();
    }

    public void switchToJbossManagementConsole() {
        webActions.switchToWindow("JBoss JMX Management Console - vidistar (pacstest)", 5);
    }

    public void exportTheFinalReportPdf() {
        webActions.waitForElementClickability(
                getByLocator(studyListPageOR.exportCompletedFinalReportButton_Xpath, LocatorIdentifier.Xpath), 20);
        webActions.click(getByLocator(studyListPageOR.exportCompletedFinalReportButton_Xpath, LocatorIdentifier.Xpath));
        if (!(webActions.getElementCount(getByLocator(studyListPageOR.singlePdfFileInZipArchiveUnselectedCheckbox_Xpath,
                LocatorIdentifier.Xpath)) == 1)) {
            webActions.click(
                    getByLocator(studyListPageOR.singlePdfFileInZipArchiveCheckbox_Xpath, LocatorIdentifier.Xpath));
        }
        if (webActions.getElementCount(getByLocator(
                studyListPageOR.multiplePdfFilesInZipArchiveUnselectedCheckbox_Xpath, LocatorIdentifier.Xpath)) == 1) {
            webActions.click(
                    getByLocator(studyListPageOR.multiplePdfFilesInZipArchiveCheckbox_Xpath, LocatorIdentifier.Xpath));
        }
        webActions.click(getByLocator(studyListPageOR.downloadPdfButton_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyListPageOR.okButton_Xpath, LocatorIdentifier.Xpath));
    }

    public String unzipExportedPdfFile() {
        int retryCount = 0;
        Common.waitForFileToExist(Constants.serverPdfZipFile);
        Common.unzipFile(Constants.serverPdfZipFile, Constants.serverPdfFolder);
        String exportedPdfFile = Common.getLatestModifiedFile(Constants.serverPdfFolder);
        while ((!exportedPdfFile.contains(".pdf")) && retryCount < 5) {
            exportedPdfFile = Common.getLatestModifiedFile(exportedPdfFile);
            retryCount++;
        }
        return exportedPdfFile;
    }

    public void saveServerPdfFile() {
        Common.createDirectory(Constants.serverPdfFolder);
        winActions.waitForWindowElementToBeVisible(
                getByLocator(studyListPageOR.saveFileNameTextbox_Xpath, LocatorIdentifier.Xpath), 300);
        winActions.setText(getByLocator(studyListPageOR.saveFileNameTextbox_Xpath, LocatorIdentifier.Xpath),
                Constants.serverPdfFolder + "\\Report");
        winActions.click(getByLocator(studyListPageOR.saveFileButton_Xpath, LocatorIdentifier.Xpath));
        Common.sleep(2, TimeUnit.SECONDS);
    }

    public void verifyExportedPdfDownload() {
        winActions.waitForWindowElementToBeVisible(
                getByLocator(studyListPageOR.downloadedPdfZipFileBar_Name, LocatorIdentifier.Name), 300);
    }

    public void waitForStudyListToLoadTheData() {
        winActions.waitForWindowElementToBeInvisible(
                getByLocator(studyListPageOR.studyListDataLoadingBar_Xpath, LocatorIdentifier.Xpath), 120);
    }

    public int getFileCountInZippedFolder() {
        Common.unzipFile(Constants.serverPdfZipFile, Constants.serverPdfFolder);
        String unzippedFolder = Common.getLatestModifiedFile(Constants.serverPdfFolder);
        String reportsFolder = Common.getLatestModifiedFile(unzippedFolder);
        return Common.getFilesCount(reportsFolder, "pdf");
    }

    public void saveRichViewerExeFile() {
        sikuliActions.waitForElementToExist(StudyListScreenElements + "SaveFileNameTextbox.png", 180);
        sikuliActions.typeTextInField(StudyListScreenElements + "SaveFileNameTextbox.png",
                Constants.downloadsFolder + "\\ViewerDownloader.exe");
        sikuliActions.click(StudyListScreenElements + "SaveButton.png");
        Common.sleep(2, TimeUnit.SECONDS);
    }

    public void revertSignedSimpleViewerReport() {
        webActions.mouseHover(getByLocator(studyListPageOR.firstPatient_Xpath, LocatorIdentifier.Xpath));
        webActions.rightClick(getByLocator(studyListPageOR.firstPatient_Xpath, LocatorIdentifier.Xpath));
        selectOptionFromRightClickMenu("Edit Study Attributes");
        webActions.click(getByLocator(studyListPageOR.advancedTab_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyListPageOR.finalReportStatusDropdown_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyListPageOR.revertedOption_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyListPageOR.saveStudyAttributesButton_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isUnfinalizeStudyAlertVisible() {
        return webActions.isVisible(getByLocator(studyListPageOR.unfinalizeStudyAlert_Xpath, LocatorIdentifier.Xpath),
                5);
    }

    public void enterTextInUnfinalizeStudyAlertTextbox(String reason) {
        webActions.setText(getByLocator(studyListPageOR.unfinalizeStudyAlertTextBox_Xpath, LocatorIdentifier.Xpath),
                reason);
    }

    public void clickUnfinalizeStudyAlertButton(String buttonName) {
        webActions.click(
                getByLocator(studyListPageOR.unfinalizeStudyAlertButton_Xpath, LocatorIdentifier.Xpath, buttonName));
        sleep(5, TimeUnit.SECONDS);
    }

    public String getFinalReportStatusOfFirstStudy() {
        return webActions
                .getText(getByLocator(studyListPageOR.firstStudyFinalReportStatus_Xpath, LocatorIdentifier.Xpath));
    }

    public void changeFinalReportStatusOfFirstStudy(String finalReportStatus) {
        webActions.click(getByLocator(studyListPageOR.advancedTab_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyListPageOR.finalReportStatusDropdown_Xpath, LocatorIdentifier.Xpath));
        webActions.click(
                getByLocator(studyListPageOR.dropdownValueLabel_Xpath, LocatorIdentifier.Xpath, finalReportStatus));
        webActions.click(getByLocator(studyListPageOR.saveStudyAttributesButton_Xpath, LocatorIdentifier.Xpath,
                finalReportStatus));
    }

    public boolean isPatientDetailPresentInLogFile(String patientName, String dateOfBirth) {
        boolean flag = true;
        String logFile = Common.getLatestModifiedFile(Constants.vidistarLogFile);
        try {
            List<String> logs = Files.readAllLines(Paths.get(logFile));
            for (String log : logs) {
                flag = flag && (log.contains(patientName)) && (log.contains(dateOfBirth));
            }
        } catch (IOException e) {
            Log.printError(e.getMessage());
        }
        return flag;
    }

    public void switchToHawtioWindow() {
        webActions.switchToWindow("Hawtio", 20);
    }

    public void clickNestedFilter(String tabName) {
        webActions.click(getByLocator(studyListPageOR.nestedFilterLink_Xpath, LocatorIdentifier.Xpath, tabName));
    }

    public void clickSearchButton() {
        webActions.click(getByLocator(studyListPageOR.searchButton_Xpath, LocatorIdentifier.Xpath));
        waitForDataToLoad();
    }

    public boolean isAssignStudyToPhysicianModalPresent() {
        return webActions
                .isVisible(getByLocator(studyListPageOR.assignStudyToPhysicianModal_Xpath, LocatorIdentifier.Xpath), 5);
    }

    public void assignPhysicianToStudy(String physicianOrGroupId) {
        waitForDataToLoad();
        webActions.setText(getByLocator(studyListPageOR.userAndGroupIdTextBox_Xpath, LocatorIdentifier.Xpath),
                physicianOrGroupId);
        webActions.click(getByLocator(studyListPageOR.assignPhysicianCheckBox_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyListPageOR.saveButton_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyListPageOR.okButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void editStudyAttribute(String attributeName, String newValue) {
        switch (attributeName.toUpperCase()) {
            case "ACCESSION NUMBER":
                webActions.setText(getByLocator(studyListPageOR.accessionNumberTextBox_Xpath, LocatorIdentifier.Xpath),
                        newValue);
                break;
            case "DESCRIPTION":
                webActions.setText(getByLocator(studyListPageOR.patientDetailsTextboxEditStudyModal_Xpath,
                        LocatorIdentifier.Xpath, "studyDescription"), newValue);
                break;
            case "STUDY DATE/TIME":
                webActions.setText(getByLocator(studyListPageOR.studyDateTimeTextBox_Xpath, LocatorIdentifier.Xpath),
                        newValue);
                break;
        }

        webActions.click(getByLocator(studyListPageOR.saveStudyAttributesButton_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyListPageOR.yesPopupButton_Xpath, LocatorIdentifier.Xpath));
        waitForDataToLoad();
    }

    public void changeReportStatusInBulk(String reportType, String studyListContent, String status) {
        webActions.click(getByLocator(studyListPageOR.advancedTab_Xpath, LocatorIdentifier.Xpath));
        webActions.click(reportType.equalsIgnoreCase("worksheet")
                ? getByLocator(studyListPageOR.worksheetReportStatus_Xpath, LocatorIdentifier.Xpath)
                : getByLocator(studyListPageOR.finalReportStatusDropdown_Xpath, LocatorIdentifier.Xpath));
        switch (status.toUpperCase()) {
            case "SCHEDULED":
                webActions.click(getByLocator(studyListPageOR.scheduledOption_Xpath, LocatorIdentifier.Xpath));
                break;
            case "REVERTED":
                webActions.click(getByLocator(studyListPageOR.revertedOption_Xpath, LocatorIdentifier.Xpath));
                break;
            case "COMPLETED":
                webActions.click(getByLocator(studyListPageOR.completedOption_Xpath, LocatorIdentifier.Xpath));
                break;
        }
        webActions.click(getByLocator(studyListPageOR.saveStudyAttributesButton_Xpath, LocatorIdentifier.Xpath));
        if (studyListContent.equalsIgnoreCase("studies")) {
            webActions.click(getByLocator(studyListPageOR.yesPopupButton_Xpath, LocatorIdentifier.Xpath));
        }
        waitForDataToLoad();
    }

    public void changeWorksheetStatus(String status) {
        String worksheetStatus = getColumnData("Worksheet").get(0);
        if (!worksheetStatus.equalsIgnoreCase(status)) {
            rightClickOnFirstStudy();
            selectOptionFromRightClickMenu("Edit Study Attributes");
            webActions.click(getByLocator(studyListPageOR.advancedTab_Xpath, LocatorIdentifier.Xpath));
            webActions.click(getByLocator(studyListPageOR.worksheetReportStatus_Xpath, LocatorIdentifier.Xpath));
            webActions.click(getByLocator(studyListPageOR.worksheetOrFinalStatusOption_Xpath, LocatorIdentifier.Xpath,
                    status.toUpperCase()));
            webActions.click(getByLocator(studyListPageOR.saveStudyAttributesButton_Xpath, LocatorIdentifier.Xpath));
            waitForDataToLoad();
        }
    }

    public void changeFinalReportStatus(String status) {
        String finalReportStatus = getColumnData("Final Report").get(0);
        if (!finalReportStatus.equalsIgnoreCase(status)) {
            rightClickOnFirstStudy();
            selectOptionFromRightClickMenu("Edit Study Attributes");
            webActions.click(getByLocator(studyListPageOR.advancedTab_Xpath, LocatorIdentifier.Xpath));
            webActions.click(getByLocator(studyListPageOR.finalReportStatusDropdown_Xpath, LocatorIdentifier.Xpath));
            webActions.click(getByLocator(studyListPageOR.worksheetOrFinalStatusOption_Xpath, LocatorIdentifier.Xpath,
                    status.toUpperCase()));
            webActions.click(getByLocator(studyListPageOR.saveStudyAttributesButton_Xpath, LocatorIdentifier.Xpath));
            waitForDataToLoad();
        }
    }

    public boolean isReportStatusUpdated(String reportType, String status) {
        if (webActions.isVisible(getByLocator(studyListPageOR.alertDialogBox_Xpath, LocatorIdentifier.Xpath), 3)) {
            webActions.click(getByLocator(studyListPageOR.okButton_Xpath, LocatorIdentifier.Xpath));
        } else {
            navigateToTab("Study List");
        }
        return getColumnData(reportType).get(0).equalsIgnoreCase(status);
    }

    public void navigateToSimpleViewerTab() {
        webActions.click(getByLocator(studyListPageOR.simpleViewerTabHeader_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isStudyListEmpty() {
        return webActions.isVisible(getByLocator(studyListPageOR.noItemsToShowLabel_Xpath, LocatorIdentifier.Xpath),
                10);
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
        return webActions.getTextboxValue(getByLocator(studyListPageOR.patientDetailsTextboxEditStudyModal_Xpath,
                LocatorIdentifier.Xpath, fieldValue));
    }

    public boolean verifyStudyDetailsOnEditStudyAttributesModal(List<String> studyDetails) {
        return getStudyDetailsFromEditStudyAttributesWindow().equals(studyDetails);
    }

    public void selectNestedFilterCriteria(String filterCriteria) {
        List<WebElement> nestedFilterDropdown = webActions.getElements(
                getByLocator(studyListPageOR.nestedFilterAttributeDropdown_Xpath, LocatorIdentifier.Xpath));
        nestedFilterDropdown.get(nestedFilterDropdown.size() - 1).click();
        webActions.scrollToElement(getByLocator(studyListPageOR.nestedFilterDropdownListItem_Xpath,
                LocatorIdentifier.Xpath, filterCriteria));
        webActions.click(getByLocator(studyListPageOR.nestedFilterDropdownListItem_Xpath, LocatorIdentifier.Xpath,
                filterCriteria));
    }

    public void selectNestedFilterCondition(String filterCondition) {
        List<WebElement> nestedFilterDropdown = webActions.getElements(
                getByLocator(studyListPageOR.nestedFilterConditionDropdown_Xpath, LocatorIdentifier.Xpath));
        nestedFilterDropdown.get(nestedFilterDropdown.size() - 1).click();
        webActions.scrollToElement(getByLocator(studyListPageOR.nestedFilterDropdownListItem_Xpath,
                LocatorIdentifier.Xpath, filterCondition));
        webActions.click(getByLocator(studyListPageOR.nestedFilterDropdownListItem_Xpath, LocatorIdentifier.Xpath,
                filterCondition));
    }

    public void selectNestedFilterValue(String filterCriteria, String filterCondition, String filterValue,
                                        boolean isNonLastFilter) {
        if (!filterCondition.equals("is null") && !filterCondition.equals("is not null")) {

            if (filterCriteria.equalsIgnoreCase("Patient Name") || filterCriteria.equalsIgnoreCase("Patient ID")
                    || filterCriteria.equalsIgnoreCase("Referring Physician")) {
                List<WebElement> filterValueTextBoxes = webActions
                        .getElements(getByLocator(studyListPageOR.nestedFilterTextBox_Xpath, LocatorIdentifier.Xpath));
                try {
                    webActions.setText(filterValueTextBoxes.get(filterValueTextBoxes.size() - 1), filterValue);
                } catch (StaleElementReferenceException e) {
                    List<WebElement> filterTextValueElements = webActions.getElements(
                            getByLocator(studyListPageOR.nestedFilterTextBox_Xpath, LocatorIdentifier.Xpath));
                    webActions.setText(filterTextValueElements.get(filterTextValueElements.size() - 1), filterValue);
                }
            } else {
                List<WebElement> filterDropdownValues = webActions.getElements(
                        getByLocator(studyListPageOR.nestedFilterValueDropdown_Xpath, LocatorIdentifier.Xpath));
                if (filterCriteria.equalsIgnoreCase("Worksheet") || filterCriteria.equalsIgnoreCase("Final Report")
                        || filterCriteria.equalsIgnoreCase("Patient Sex")) {
                    webActions.click(filterDropdownValues.get(filterDropdownValues.size() - 1));
                    webActions.click(getByLocator(studyListPageOR.dropdownValueLabel_Xpath, LocatorIdentifier.Xpath,
                            filterValue));
                } else {
                    webActions.click(filterDropdownValues.get(filterDropdownValues.size() - 1));
                    webActions.scrollToElement(getByLocator(studyListPageOR.nestedFilterAssignedUserValueCheckbox_Xpath,
                            LocatorIdentifier.Xpath, filterValue));
                    webActions.click(getByLocator(studyListPageOR.nestedFilterAssignedUserValueCheckbox_Xpath,
                            LocatorIdentifier.Xpath, filterValue));
                    robotActions.escape();
                }
            }
        }
        if (isNonLastFilter) {
            webActions.click(
                    getByLocator(studyListPageOR.nestedFilterAddRemoveIcon_Xpath, LocatorIdentifier.Xpath, "Add"));
        }
    }

    public void setupNestedFilter(List<String> filterCriteriaList, List<String> filterValueList,
                                  String filterCondition) {
        for (int filterIndex = 0; filterIndex < filterCriteriaList.size(); filterIndex++) {
            selectNestedFilterCriteria(filterCriteriaList.get(filterIndex));
            selectNestedFilterCondition(filterCondition);
            selectNestedFilterValue(filterCriteriaList.get(filterIndex), filterCondition,
                    filterValueList.get(filterIndex), filterIndex < filterCriteriaList.size() - 1);
        }
    }

    public boolean areResultsDisplayedAsPerNestedFilter(List<String> filterCriteriaList, List<String> filterValueList,
                                                        String filterCondition, String matchCondition) {
        if ((filterCondition.equals("is null") && matchCondition.equals("Match All"))
                || (filterCondition.equals("is not null") && matchCondition.equals("Match None"))) {
            return isStudyListEmpty();
        } else if (filterCondition.equals("is null") || filterCondition.equals("is not null")) {
            return !isStudyListEmpty();

        } else {
            if (filterCriteriaList.contains("Patient Sex")) {
                int patientSexValueIndex = filterCriteriaList.indexOf("Patient Sex");
                filterValueList.add(patientSexValueIndex, filterValueList.get(patientSexValueIndex).substring(0, 1));
                filterValueList.remove(patientSexValueIndex + 1);
            }
            List<List<String>> listOfColumnDataList = new ArrayList<>();
            for (String columnName : filterCriteriaList) {
                listOfColumnDataList.add(getColumnData(columnName));
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

    public void selectStudyWithName(String firstName, String lastName, int studyNumber) {
        List<WebElement> patientStudies = webActions
                .getElements(getByLocator(studyListPageOR.studyListRowWithName_Xpath, LocatorIdentifier.Xpath,
                        lastName.toUpperCase(), firstName.toUpperCase()));
        WebElement studyListRowWithPatientName = patientStudies.get(studyNumber - 1);
        webActions.click(studyListRowWithPatientName);
    }

    public void revertSignedReportForStudyNumber(String patientFirstName, String patientLastName, int studyNumber) {
        List<WebElement> patientStudies = webActions
                .getElements(getByLocator(studyListPageOR.studyListRowWithName_Xpath, LocatorIdentifier.Xpath,
                        patientFirstName, patientLastName));
        WebElement studyListRowWithPatientName = patientStudies.get(studyNumber - 1);
        webActions.mouseHover(studyListRowWithPatientName);
        try {
            webActions.rightClick(studyListRowWithPatientName);
        } catch (StaleElementReferenceException e) {
            List<WebElement> studies = webActions.getElements(getByLocator(studyListPageOR.studyListRowWithName_Xpath,
                    LocatorIdentifier.Xpath, patientFirstName, patientLastName));
            WebElement studyListRow = studies.get(studyNumber - 1);
            webActions.rightClick(studyListRow);
        }
        selectOptionFromRightClickMenu("Edit Study Attributes");
        webActions.click(getByLocator(studyListPageOR.advancedTab_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyListPageOR.finalReportStatusDropdown_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyListPageOR.scheduledOption_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(studyListPageOR.saveStudyAttributesButton_Xpath, LocatorIdentifier.Xpath));
    }
}
