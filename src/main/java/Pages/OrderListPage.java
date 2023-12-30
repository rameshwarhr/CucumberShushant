package Pages;

import static Utilities.Common.sleep;
import static Utilities.Constants.OrderListPageORFile;
import static Utilities.Constants.USER_WORK_DIR;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import Actions.WebActions;
import ObjectRepository.OrderListPageOR;
import Utilities.Common;
import Utilities.Driver;
import Utilities.Log;
import Utilities.YmlReader;

public class OrderListPage extends Page {

    Driver driver;
    WebActions webActions;

    public OrderListPage(Driver driver) {
        super(driver);
        this.driver = driver;
        webActions = new WebActions(driver.getWebDriver());
    }

    public static OrderListPageOR orderListPageOR = YmlReader.getObjectRepository(USER_WORK_DIR + OrderListPageORFile,
            OrderListPageOR.class);

    public boolean isOrderListDisplayed() {
        waitForDataToLoad(10);
        return webActions.isVisible(getByLocator(orderListPageOR.orderListTable_Xpath, LocatorIdentifier.Xpath));
    }

    public void setFilter(String filterValue, String filterName) {
        switch (filterName.toUpperCase()) {
            case "PATIENT NAME":
                searchForPatientName(filterValue);
                break;
            case "PATIENT ID":
                searchForPatientId(filterValue);
                break;
            case "ACCESSION NUMBER":
                searchForAccessionNumber(filterValue);
                break;
            case "HAS STUDY":
                applyHasStudyFilter(filterName, filterValue);
                break;
            case "START DATE":
                searchStartDate(filterValue);
                break;
            case "END DATE":
                searchEndDate(filterValue);
                break;
            case "WORKSHEET":
                applyWorksheetFilter(filterValue);
                break;
            case "FINAL REPORT":
                applyFinalReportFilter(filterValue);
                break;
        }
        waitForDataToLoad();
    }

    public void clearFilter(String filterName) {
        switch (filterName.toUpperCase()) {
            case "PATIENT NAME":
                clearPatientNameText();
                break;
            case "PATIENT ID":
                clearPatientIdText();
                break;
            case "ACCESSION NUMBER":
                clearAccessionNumberFilter();
                break;
            case "HAS STUDY":
                clearHasStudyFilter(filterName);
                break;
        }
    }

    public boolean isOrderListFiltered(String filterName, String filterValue) {
        switch (filterName.toUpperCase()) {
            case "PATIENT ID":
            case "PATIENT NAME":
            case "ACCESSION NUMBER":
                List<String> columnData = getColumnData(filterName);
                columnData.removeIf(data -> data.equals("") || data.isEmpty());
                columnData.forEach(Log::printInfo);
                return columnData.stream().allMatch(row -> row.contains(filterValue));
            case "DATE":
                return isOrderListFilteredByDate(getStartDate(), getEndDate());
            case "HAS STUDY":
                boolean result = false;
                webActions.waitForElementVisibility(
                        getByLocator(orderListPageOR.orderListTable_Xpath, LocatorIdentifier.Xpath), 10);
                int count = webActions
                        .getElementCount(getByLocator(orderListPageOR.hasStudyColumnIcon_Xpath, LocatorIdentifier.Xpath));
                for (int i = 1; i <= count; i++) {
                    String hasStudyColumnDataClassValue = webActions.getElementAttributeValue(
                            getByLocator(orderListPageOR.hasStudyColumnIconList_Xpath, LocatorIdentifier.Xpath, count),
                            "class");
                    result = hasStudyColumnDataClassValue.contains("True");
                }
                return result;
            default:
                return false;
        }
    }

    public List<String> getColumnData(String columnName) {
        webActions.waitForElementVisibility(
                getByLocator(orderListPageOR.orderListColumnHeaders_Xpath, LocatorIdentifier.Xpath), 10);
        List<String> columnTexts = webActions.getElementTextList(
                getByLocator(orderListPageOR.orderListColumnHeaders_Xpath, LocatorIdentifier.Xpath));
        int columnIndex = columnTexts.indexOf(columnName);
        webActions.waitForElementVisibility(getByLocator(orderListPageOR.orderListTable_Xpath, LocatorIdentifier.Xpath),
                10);
        return webActions.getColumnText(
                getByLocator(orderListPageOR.orderListTable_Xpath, LocatorIdentifier.Xpath), columnIndex + 1);
    }

    public List<String> fetchDateFilteredOrderList() {
        List<String> dates = new ArrayList<>();
        String actualDate;
        List<String> studyDates = webActions
                .getColumnText(getByLocator(orderListPageOR.orderListTable_Xpath, LocatorIdentifier.Xpath), 7);
        for (String studyDate : studyDates) {
            String[] arr_studyDate = studyDate.split(" ");
            actualDate = arr_studyDate[0];
            dates.add(actualDate);
        }
        return dates;
    }

    public boolean isOrderListFilteredByDate(String studyStartDate, String studyEndDate) {
        boolean flag = false;
        List<String> studyDates = fetchDateFilteredOrderList();
        Date studyStartDateOfTypeDate = Common.StringToDate(studyStartDate);
        Date studyEndDateOfTypeDate = Common.StringToDate(studyEndDate);
        for (String Date : studyDates) {
            Date studyDateOfTypeDate = Common.StringToDate(Date);
            flag = (studyDateOfTypeDate.after(studyStartDateOfTypeDate)
                    || studyDateOfTypeDate.equals(studyEndDateOfTypeDate))
                    && (studyDateOfTypeDate.before(studyEndDateOfTypeDate)
                    || studyDateOfTypeDate.equals(studyEndDateOfTypeDate));
        }
        return flag;
    }

    public void clearHasStudyFilter(String filterName) {
        webActions.click(getByLocator(orderListPageOR.hasStudyDropdown_Xpath, LocatorIdentifier.Xpath, filterName));
        webActions.click(getByLocator(orderListPageOR.hasStudyDropdownClearFilter_Xpath, LocatorIdentifier.Xpath));
    }

    public void clearAccessionNumberFilter() {
        webActions.clearText(getByLocator(orderListPageOR.accessionNumberTextbox_Xpath, LocatorIdentifier.Xpath));
    }

    public void clearPatientIdText() {
        webActions.clearText(getByLocator(orderListPageOR.patientIdTextbox_Xpath, LocatorIdentifier.Xpath));
    }

    public void clearPatientNameText() {
        webActions.clearText(getByLocator(orderListPageOR.patientNameTextbox_Xpath, LocatorIdentifier.Xpath));
    }

    public void searchStartDate(String startDate) {
        webActions.setText(getByLocator(orderListPageOR.startDateTextbox_Xpath, LocatorIdentifier.Xpath), startDate);
    }

    public void searchEndDate(String endDate) {
        webActions.setText(getByLocator(orderListPageOR.endDateTextbox_Xpath, LocatorIdentifier.Xpath), endDate);
    }

    public void applyHasStudyFilter(String filterName, String filterValue) {
        webActions.click(getByLocator(orderListPageOR.hasStudyDropdown_Xpath, LocatorIdentifier.Xpath, filterName));
        webActions
                .click(getByLocator(orderListPageOR.dropdownValue_Xpath, LocatorIdentifier.Xpath, filterValue));
    }

    public void searchForAccessionNumber(String accessionNumber) {
        webActions.setText(getByLocator(orderListPageOR.accessionNumberTextbox_Xpath, LocatorIdentifier.Xpath),
                accessionNumber);
    }

    public void searchForPatientName(String patientName) {
        webActions.setText(getByLocator(orderListPageOR.patientNameTextbox_Xpath, LocatorIdentifier.Xpath),
                patientName);
    }

    public void searchForPatientId(String patientId) {
        webActions.setText(getByLocator(orderListPageOR.patientIdTextbox_Xpath, LocatorIdentifier.Xpath), patientId);
    }

    public String getStartDate() {
        return webActions
                .getTextboxValue(getByLocator(orderListPageOR.startDateTextbox_Xpath, LocatorIdentifier.Xpath));
    }

    public String getEndDate() {
        return webActions.getTextboxValue(getByLocator(orderListPageOR.endDateTextbox_Xpath, LocatorIdentifier.Xpath));
    }

    public void rightClickOnFirstPatient() {
        webActions.rightClick(getByLocator(orderListPageOR.orderListRow_Xpath, LocatorIdentifier.Xpath));

    }

    public void selectOptionFromOrderListRightClickMenu(String option) {
        List<String> menuOptions = webActions.getElementTextList(
                getByLocator(orderListPageOR.orderListRightClickMenuOptions_Xpath, LocatorIdentifier.Xpath));
        int optionIndex = menuOptions.indexOf(option);
        webActions.clickOnElementAtIndex(
                getByLocator(orderListPageOR.orderListRightClickMenuOptions_Xpath, LocatorIdentifier.Xpath),
                optionIndex);
    }

    public boolean isCreateStudyModalPresent() {
        return webActions.isVisible(getByLocator(orderListPageOR.createNewStudyModal_Xpath, LocatorIdentifier.Xpath));
    }

    public List<String> getStudyDetailsFromOrderListPage() {
        List<String> studyDetails = new ArrayList<>();
        studyDetails.add(0, getColumnData("Patient Name").get(0));
        studyDetails.add(1, getColumnData("Patient Id").get(0));
        studyDetails.add(2, getColumnData("Accession Number").get(0));
        studyDetails.add(3, getColumnData("Procedure").get(0));
        return studyDetails;
    }

    public List<String> getStudyDetailsFromCreateStudyModal() {
        List<String> studyDetails = new ArrayList<>();
        studyDetails.add(0, getPatientDetailsFromCreateStudyModal("patientName"));
        studyDetails.add(1, getPatientDetailsFromCreateStudyModal("patientId"));
        studyDetails.add(2, getPatientDetailsFromCreateStudyModal("Accession Number"));
        studyDetails.add(3, getPatientDetailsFromCreateStudyModal("studyDescription"));
        return studyDetails;
    }

    public String getPatientDetailsFromCreateStudyModal(String fieldValue) {
        if (fieldValue.equalsIgnoreCase("Accession Number")) {
            return webActions.getText(getByLocator(orderListPageOR.accessionNumberTextboxCreateStudyModal_Xpath,
                    LocatorIdentifier.Xpath));
        } else {
            return webActions.getTextboxValue(
                    getByLocator(orderListPageOR.patientDetailsTextboxCreateStudyModal_Xpath, LocatorIdentifier.Xpath, fieldValue));
        }
    }

    public boolean verifyStudyDetailsOnModal(List<String> studyDetails) {
        return getStudyDetailsFromCreateStudyModal().equals(studyDetails);
    }

    public void hoverOnFirstPatient() {
        webActions.mouseHover(getByLocator(orderListPageOR.firstPatient_Xpath, LocatorIdentifier.Xpath));
        sleep(1, TimeUnit.SECONDS);
    }

    public boolean verifyInformationPopupIsDisplayed() {
        webActions.waitForElementVisibility(
                getByLocator(orderListPageOR.informationAlertDialog_Xpath, LocatorIdentifier.Xpath), 5);
        return webActions
                .isVisible(getByLocator(orderListPageOR.informationAlertDialog_Xpath, LocatorIdentifier.Xpath));
    }

    public void enterStudyDetails(List<Map<String, String>> studyDetails) {
        for (Map<String, String> studyDetail : studyDetails) {
            String field = studyDetail.get("field");
            switch (field.toUpperCase()) {
                case "SERVICE LOCATION":
                    enterServiceLocation(studyDetail.get("value"));
                    break;
            }
        }
    }

    public void enterServiceLocation(String value) {
        webActions.click(getByLocator(orderListPageOR.serviceLocationDropdown_Xpath, LocatorIdentifier.Xpath));
        webActions.scrollToElement(
                getByLocator(orderListPageOR.dropdownValue_Xpath, LocatorIdentifier.Xpath, value));
        webActions.click(getByLocator(orderListPageOR.dropdownValue_Xpath, LocatorIdentifier.Xpath, value));
    }

    public boolean verifyHasStudyCheckboxIsChecked() {
        return webActions
                .getElementAttributeValue(getByLocator(orderListPageOR.hasStudyCheckbox_Xpath, LocatorIdentifier.Xpath),
                        "class")
                .contains("True");

    }

    public void clickOkButtonOnInformationPopup() {
        webActions.click(getByLocator(orderListPageOR.okButtonInformationAlertDialog_Xpath, LocatorIdentifier.Xpath));
    }

    public void clickTabOnCreateStudyModal(String tabName) {
        webActions.click(getByLocator(orderListPageOR.tabOnCreateStudyModal_Xpath, LocatorIdentifier.Xpath, tabName));
    }

    public void applyWorksheetFilter(String filterValue) {
        webActions.click(getByLocator(orderListPageOR.worksheetDropdown_Xpath, LocatorIdentifier.Xpath));
        webActions.click(
                getByLocator(orderListPageOR.dropdownValue_Xpath, LocatorIdentifier.Xpath, filterValue.toUpperCase()));
    }

    public void applyFinalReportFilter(String filterValue) {
        webActions.click(getByLocator(orderListPageOR.finalReportDropdown_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(orderListPageOR.dropdownValue_Xpath, LocatorIdentifier.Xpath,
                filterValue.toUpperCase()));
    }
}