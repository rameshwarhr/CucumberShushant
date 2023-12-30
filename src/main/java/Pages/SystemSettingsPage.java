package Pages;

import static Utilities.Common.sleep;
import static Utilities.Constants.SystemSettingsPageORFile;
import static Utilities.Constants.USER_WORK_DIR;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import Actions.RobotActions;
import Actions.SikuliActions;
import Actions.WebActions;
import Actions.WindowsActions;
import ObjectRepository.SystemSettingsPageOR;
import Utilities.Common;
import Utilities.Constants;
import Utilities.Driver;
import Utilities.Log;
import Utilities.Property;
import Utilities.YmlReader;

public class SystemSettingsPage extends Page {

    WebActions webActions;
    Property property;
    WindowsActions winActions;
    RobotActions robotActions;
    SikuliActions sikuliActions;

    public SystemSettingsPage(Driver driver) {
        super(driver);
        property = new Property();
        winActions = new WindowsActions(driver.getWinDriver());
        webActions = new WebActions(driver.getWebDriver());
        robotActions = new RobotActions();
        sikuliActions = new SikuliActions();
    }

    private static final SystemSettingsPageOR systemSettingsPageOR = YmlReader
            .getObjectRepository(USER_WORK_DIR + SystemSettingsPageORFile, SystemSettingsPageOR.class);

    private final By addUserButton = getByLocator(systemSettingsPageOR.addUserButton_Xpath, LocatorIdentifier.Xpath);
    private final By deleteUserButton = getByLocator(systemSettingsPageOR.deleteUserButton_Xpath,
            LocatorIdentifier.Xpath);
    private final By removeUserConfirmationYesButton = getByLocator(
            systemSettingsPageOR.removeUserConfirmationYesButton_Xpath, LocatorIdentifier.Xpath);
    private final By userDialogUserIdTextbox = getByLocator(systemSettingsPageOR.userDialogUserIdTextbox_Xpath,
            LocatorIdentifier.Xpath);
    private final By userDialogPasswordTextbox = getByLocator(systemSettingsPageOR.userDialogPasswordTextbox_Xpath,
            LocatorIdentifier.Xpath);
    private final By userDialogConfirmPasswordTextbox = getByLocator(
            systemSettingsPageOR.userDialogConfirmPasswordTextbox_Xpath, LocatorIdentifier.Xpath);
    private final By userDialogGroupsTab = getByLocator(systemSettingsPageOR.userDialogGroupsTab_Xpath,
            LocatorIdentifier.Xpath);
    private final By userDialogRolesTab = getByLocator(systemSettingsPageOR.userDialogRolesTab_Xpath,
            LocatorIdentifier.Xpath);
    private final By userIdSearchTextbox = getByLocator(systemSettingsPageOR.userIdSearchTextbox_Xpath,
            LocatorIdentifier.Xpath);
    private final By usersListUserIdValue = getByLocator(systemSettingsPageOR.usersListUserIdValue_Xpath,
            LocatorIdentifier.Xpath);
    private final By addNewUserDialogTitle = getByLocator(systemSettingsPageOR.addNewUserDialogTitle_Xpath,
            LocatorIdentifier.Xpath);

    public void createNewUserOperator(String userName, String password,
                                      List<Map<String, String>> listOfGroupsAndRoles) {
        List<String> groupList = listOfGroupsAndRoles.stream().map(groupAndRolesMap -> groupAndRolesMap.get("group"))
                .collect(Collectors.toList());
        List<String> roleList = listOfGroupsAndRoles.stream().map(groupAndRolesMap -> groupAndRolesMap.get("role"))
                .collect(Collectors.toList());
        webActions.click(addUserButton);
        webActions.isVisible(addNewUserDialogTitle);
        webActions.setText(userDialogUserIdTextbox, userName);
        webActions.setText(userDialogPasswordTextbox, password);
        webActions.setText(userDialogConfirmPasswordTextbox, password);
        addGroupsToNewUser(groupList);
        addRolesToNewUser(roleList);
        clickSaveButton();
    }

    public boolean verifyUserDisplayed(String user) throws TimeoutException {
        webActions.setText(userIdSearchTextbox, user);
        webActions.waitForElementToDisappear(
                getByLocator(systemSettingsPageOR.loadingIndicator_Xpath, LocatorIdentifier.Xpath), 10);
        String text = webActions.getText(usersListUserIdValue);
        return text.equals(user);
    }

    public void deleteUserIfExists(String username) throws TimeoutException {
        webActions.setText(userIdSearchTextbox, username);
        webActions.waitForElementToDisappear(
                getByLocator(systemSettingsPageOR.loadingIndicator_Xpath, LocatorIdentifier.Xpath), 10);
        String text = webActions.getText(usersListUserIdValue);
        if (text.equals(username)) {
            webActions.click(usersListUserIdValue);
            webActions.click(deleteUserButton);
            webActions.click(removeUserConfirmationYesButton);
            sleep(100, TimeUnit.MILLISECONDS);
        }
    }

    public boolean isOptionPresentInSettingsPanel(String option) {
        List<String> fieldNames = new ArrayList<>();
        List<WebElement> elements = webActions.waitForElementsClickability(
                getByLocator(systemSettingsPageOR.settingsPanelAllOptions_Xpath, LocatorIdentifier.Xpath), 10);
        for (WebElement element : elements) {
            fieldNames.add(element.getText());
        }
        return fieldNames.contains(option);
    }

    public void assignRoleToUser(String role, String user) {
        webActions.setText(getByLocator(systemSettingsPageOR.userIdSearchTextbox_Xpath, LocatorIdentifier.Xpath), user);
        webActions.waitForElementToDisappear(
                getByLocator(systemSettingsPageOR.loadingIndicator_Xpath, LocatorIdentifier.Xpath), 10);
        webActions.doubleClick(getByLocator(systemSettingsPageOR.userIdText_Xpath, LocatorIdentifier.Xpath));
        webActions.click(userDialogRolesTab);
        webActions.scrollToElement(getByLocator(systemSettingsPageOR.role_Xpath, LocatorIdentifier.Xpath, role));
        webActions.doubleClick(getByLocator(systemSettingsPageOR.role_Xpath, LocatorIdentifier.Xpath, role));
        clickSaveButton();
    }

    public void clickOptionFromSettingsPanel(String option) {
        webActions.click(getByLocator(systemSettingsPageOR.settingsPanelOption_Xpath, LocatorIdentifier.Xpath, option));
    }

    public boolean isContentHeaderDisplayed(String contentHeader) {
        return webActions.waitForElementVisibility(
                getByLocator(systemSettingsPageOR.faxStatusEventsHeader_Xpath, LocatorIdentifier.Xpath, contentHeader),
                10).isDisplayed();
    }

    public void doubleClickToEditFirstUser() {
        webActions.waitForElementToDisappear(
                getByLocator(systemSettingsPageOR.loadingIndicator_Xpath, LocatorIdentifier.Xpath), 20);
        webActions.doubleClick(getByLocator(systemSettingsPageOR.usersListFirstUser_Xpath, LocatorIdentifier.Xpath));
    }

    public void openRolesTab() {
        webActions.click(getByLocator(systemSettingsPageOR.userDialogRolesTab_Xpath, LocatorIdentifier.Xpath));
    }

    public List<String> getAvailableRoles() {
        return webActions
                .getElementTextList(getByLocator(systemSettingsPageOR.availableRoles_Xpath, LocatorIdentifier.Xpath));
    }

    public void closeEditUserWindow() {
        webActions.click(getByLocator(systemSettingsPageOR.closeEditBox_Xpath, LocatorIdentifier.Xpath));
    }

    public void clickAddNewUser() {
        webActions.click(getByLocator(systemSettingsPageOR.addUserButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void searchForUserId(String userId) {
        webActions.setText(getByLocator(systemSettingsPageOR.userIdFilter_Xpath, LocatorIdentifier.Xpath), userId);
    }

    public boolean isResultFiltered(String filterName, String filterValue) {
        boolean result = false;
        if (filterName.equals("User Id")) {
            result = isUserListFilteredByUserId(filterValue);
        }
        return result;
    }

    public boolean isUserListFilteredByUserId(String searchPatientName) {
        boolean flag = true;
        String userId;
        List<String> userIdList = webActions
                .getColumnText(getByLocator(systemSettingsPageOR.userListTable_Xpath, LocatorIdentifier.Xpath), 1);
        for (int i = 0; i < userIdList.size() - 1; i++) {
            userId = userIdList.get(i);
            flag = flag && (userId.contains(searchPatientName));
        }
        return flag;
    }

    public void clickReportDefaults() {
        webActions.click(getByLocator(systemSettingsPageOR.reportDefaultsTab_Xpath, LocatorIdentifier.Xpath));
    }

    public void deleteTemplate(String templateName) {
        webActions.setText(
                getByLocator(systemSettingsPageOR.reportDefaultsSearchTextbox_Xpath, LocatorIdentifier.Xpath),
                templateName);
        sleep(5, TimeUnit.SECONDS);
        String text = webActions.getText(getByLocator(systemSettingsPageOR.reportDefaultsSearchResultRow_Xpath,
                LocatorIdentifier.Xpath, templateName));
        if (text.equals(templateName)) {
            webActions.rightClick(getByLocator(systemSettingsPageOR.reportDefaultsSearchResultRow_Xpath,
                    LocatorIdentifier.Xpath, templateName));
            webActions.click(
                    getByLocator(systemSettingsPageOR.reportDefaultsDeleteButton_Xpath, LocatorIdentifier.Xpath));
            webActions.click(getByLocator(systemSettingsPageOR.removeTemplateConfirmationDeleteButton_Xpath,
                    LocatorIdentifier.Xpath));
        }
    }

    public void addGroupsToNewUser(List<String> listOfGroups) {
        webActions.click(userDialogGroupsTab);
        for (String groupName : listOfGroups) {
            webActions.doubleClick(
                    getByLocator(systemSettingsPageOR.userDialogListValue_Xpath, LocatorIdentifier.Xpath, groupName));
        }
    }

    public void addRolesToNewUser(List<String> listOfRoles) {
        webActions.click(userDialogRolesTab);
        for (String roleName : listOfRoles) {
            webActions.doubleClick(
                    getByLocator(systemSettingsPageOR.userDialogListValue_Xpath, LocatorIdentifier.Xpath, roleName));
        }
    }

    public void deleteServiceLocation() {
        webActions.click(getByLocator(systemSettingsPageOR.deleteServiceLocationButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void setServiceLocationName(String Value) {
        webActions.click(getByLocator(systemSettingsPageOR.addNewServiceLocationButton_Xpath, LocatorIdentifier.Xpath));
        webActions.setText(getByLocator(systemSettingsPageOR.serviceLocationNameTextBox_Xpath, LocatorIdentifier.Xpath),
                Value);
    }

    public void assignHubToServiceLocation(String Value) {
        int retryCount = 0;
        webActions.click(getByLocator(systemSettingsPageOR.serviceLocationHubDropDown_Xpath, LocatorIdentifier.Xpath));
        webActions.setText(getByLocator(systemSettingsPageOR.serviceLocationHubTextBox_Xpath, LocatorIdentifier.Xpath),
                Value);
        robotActions.escape();
        webActions.click(getByLocator(systemSettingsPageOR.serviceLocationHubCheckBox_Xpath, LocatorIdentifier.Xpath));
        while (webActions.isVisible(
                getByLocator(systemSettingsPageOR.serviceLocationHubTextBox_Xpath, LocatorIdentifier.Xpath), 1)
                && retryCount < 5) {
            robotActions.pressTabKey();
            retryCount++;
        }
    }

    public void setCheckBoxOfAccreditationLogo(String operation) {
        String styleValue = webActions.getElementAttributeValue(
                getByLocator(systemSettingsPageOR.showAccreditationLogoCheckBox_Xpath, LocatorIdentifier.Xpath),
                "style");
        if (operation.equals("select") && styleValue.contains("unsetcheck")) {
            webActions.click(
                    getByLocator(systemSettingsPageOR.showAccreditationLogoCheckBox_Xpath, LocatorIdentifier.Xpath));
        } else if (operation.equals("deselect") && styleValue.contains("checked")) {
            webActions.click(
                    getByLocator(systemSettingsPageOR.showAccreditationLogoCheckBox_Xpath, LocatorIdentifier.Xpath));
        }
    }

    public void clickSaveButton() {
        Common.sleep(1, TimeUnit.SECONDS);
        webActions.click(getByLocator(systemSettingsPageOR.saveButton_Xpath, LocatorIdentifier.Xpath));
        webActions.waitForElementToDisappear(getByLocator(systemSettingsPageOR.serviceLocationEditWindow_Xpath, LocatorIdentifier.Xpath),
                5);
    }

    public void searchServiceLocation(String Value) {
        webActions.setText(getByLocator(systemSettingsPageOR.nameSearchTextBox_Xpath, LocatorIdentifier.Xpath), Value);
    }

    public boolean isServiceLocationPresent(String locationName) {
        webActions.waitForElementToDisappear(
                getByLocator(systemSettingsPageOR.loadingIndicator_Xpath, LocatorIdentifier.Xpath), 10);
        List<String> serviceLocationNames = webActions.getColumnText(
                getByLocator(systemSettingsPageOR.serviceLocationsTable_Xpath, LocatorIdentifier.Xpath), 1);
        return serviceLocationNames.contains(locationName);
    }

    public void editServiceLocation() {
        webActions.rightClick(getByLocator(systemSettingsPageOR.searchResultList_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(systemSettingsPageOR.editButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void setLogoFilePath() {
        webActions.click(getByLocator(systemSettingsPageOR.serviceLocationLogo_Xpath, LocatorIdentifier.Xpath));
        winActions.setText(
                getByLocator(systemSettingsPageOR.serviceLocationLogoNameTextBox_Xpath, LocatorIdentifier.Xpath),
                USER_WORK_DIR + "\\src\\test\\resources\\ReportScreenElements\\Common\\Service Location.PNG");
        winActions.click(getByLocator(systemSettingsPageOR.serviceLocationOpenButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void createNewHub(String name, String address) {
        webActions.click(getByLocator(systemSettingsPageOR.addNewHubButton_Xpath, LocatorIdentifier.Xpath));
        webActions.setText(getByLocator(systemSettingsPageOR.hubNameTextBox_Xpath, LocatorIdentifier.Xpath), name);
        webActions.setText(getByLocator(systemSettingsPageOR.hubAddressTextArea_Xpath, LocatorIdentifier.Xpath),
                address);
        robotActions.pressTabKey();
    }

    public void searchHub(String value) {
        webActions.setText(getByLocator(systemSettingsPageOR.nameSearchTextBox_Xpath, LocatorIdentifier.Xpath), value);
    }

    public boolean isHubPresent(String hubName) {
        webActions.waitForElementToDisappear(
                getByLocator(systemSettingsPageOR.loadingIndicator_Xpath, LocatorIdentifier.Xpath), 10);
        List<String> serviceLocationNames = webActions
                .getColumnText(getByLocator(systemSettingsPageOR.hubsTable_Xpath, LocatorIdentifier.Xpath), 1);
        return serviceLocationNames.contains(hubName);
    }

    public void deleteHub() {
        webActions.click(getByLocator(systemSettingsPageOR.searchResultList_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(systemSettingsPageOR.deleteHubButton_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(systemSettingsPageOR.yesPopupButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void createNewGroup(String id, String description) {
        webActions.click(getByLocator(systemSettingsPageOR.addNewGroupButton_Xpath, LocatorIdentifier.Xpath));
        webActions.setText(getByLocator(systemSettingsPageOR.hubIdTextBox_Xpath, LocatorIdentifier.Xpath), id);
        webActions.setText(getByLocator(systemSettingsPageOR.hubDescriptionTextBox_Xpath, LocatorIdentifier.Xpath),
                description);

    }

    public void searchGroup(String value) {
        webActions.setText(getByLocator(systemSettingsPageOR.searchGroupTextBox_Xpath, LocatorIdentifier.Xpath), value);
    }

    public boolean isGroupPresent(String groupName) {
        webActions.waitForElementToDisappear(
                getByLocator(systemSettingsPageOR.loadingIndicator_Xpath, LocatorIdentifier.Xpath), 10);
        List<String> serviceLocationNames = webActions
                .getColumnText(getByLocator(systemSettingsPageOR.groupsTable_Xpath, LocatorIdentifier.Xpath), 1);
        return serviceLocationNames.contains(groupName);
    }

    public void deleteGroup() {
        webActions.click(getByLocator(systemSettingsPageOR.searchResultList_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(systemSettingsPageOR.deleteGroupButton_Xpath, LocatorIdentifier.Xpath));
        webActions.click(getByLocator(systemSettingsPageOR.yesPopupButton_Xpath, LocatorIdentifier.Xpath));
    }

    public List<String> getSettingsDisplayed() {
        return webActions
                .getElementTextList(getByLocator(systemSettingsPageOR.leftPanel_Xpath, LocatorIdentifier.Xpath));
    }

    HashMap<String, String> leftPanelSettingsAndPanels = new HashMap<String, String>() {
        {
            put("Users", "Users");
            put("Groups", "Groups");
            put("Password Complexity", "Edit Password Complexity Settings");
            put("Edit Account", "Edit Account Settings");
            put("My notifications", "Notifications");
            put("Notification administration", "Notifications");
            put("Abbreviation dictionary", "Abbreviation dictionary");
            put("Service Locations", "Service locations");
            put("Hubs", "Hubs");
            put("Code Administration", "Context Groups");
            put("Clinic parameters", "Clinic parameters");
            put("System Names and Numbers", "System Names and Numbers");
        }
    };

    public boolean isSettingPanelVisible(String leftPanelSetting) {
        webActions.click(getByLocator(systemSettingsPageOR.settingsPanelOption_Xpath, LocatorIdentifier.Xpath,
                leftPanelSetting));
        waitForDataToLoad(10);
        if (leftPanelSetting.equals("Notification administration")) {
            return webActions.isVisible(getByLocator(systemSettingsPageOR.notificationAdministrationPanel_Xpath,
                    LocatorIdentifier.Xpath, leftPanelSettingsAndPanels.get(leftPanelSetting)), 3);
        } else {
            return webActions.isVisible(getByLocator(systemSettingsPageOR.systemSettingsPanelName_Xpath,
                    LocatorIdentifier.Xpath, leftPanelSettingsAndPanels.get(leftPanelSetting)), 3);
        }
    }

    public void clickWarningOkButton() {
        if (webActions.isVisible(getByLocator(systemSettingsPageOR.warningOkButton_Xpath, LocatorIdentifier.Xpath),
                2)) {
            webActions.click(getByLocator(systemSettingsPageOR.warningOkButton_Xpath, LocatorIdentifier.Xpath));
        }
    }

    public void assignRevokeRoleToUser(String role) {
        webActions.scrollToElement(getByLocator(systemSettingsPageOR.role_Xpath, LocatorIdentifier.Xpath, role));
        webActions.doubleClick(getByLocator(systemSettingsPageOR.role_Xpath, LocatorIdentifier.Xpath, role));
    }

    public List<String> getAssignedRoles() {
        return webActions
                .getElementTextList(getByLocator(systemSettingsPageOR.assignedRoles_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isExportButtonPresent(String exportButton) {
        return webActions.isVisible(getByLocator(systemSettingsPageOR.functionalityActionButton_Xpath,
                LocatorIdentifier.Xpath, exportButton), 10);
    }

    public void hoverOnFunctionality(String buttonName) {
        webActions.mouseHover(getByLocator(systemSettingsPageOR.functionalityActionButton_Xpath,
                LocatorIdentifier.Xpath, buttonName));
    }

    public void clickOnFunctionality(String buttonName) {
        webActions.click(getByLocator(systemSettingsPageOR.functionalityActionButton_Xpath, LocatorIdentifier.Xpath,
                buttonName));
    }

    public boolean isExportButtonTooltipDisplayed(String tooltipLabel) {
        return webActions.isVisible(
                getByLocator(systemSettingsPageOR.exportCsvButtonTooltip_Xpath, LocatorIdentifier.Xpath, tooltipLabel),
                10);
    }

    public void selectAuditEvent(String audioEvent) {
        webActions.click(getByLocator(systemSettingsPageOR.auditEventDropdown_Xpath, LocatorIdentifier.Xpath));
        webActions.click(
                getByLocator(systemSettingsPageOR.auditEventCheckbox_Xpath, LocatorIdentifier.Xpath, audioEvent));
        robotActions.escape();
    }

    public void setDocumentTitle(String documentTitle) {
        webActions.setText(getByLocator(systemSettingsPageOR.documentTitleTextBox_Xpath, LocatorIdentifier.Xpath),
                documentTitle);
        robotActions.escape();
    }

    public void setDateTimestamp(String gridName, String timePeriodAgo) {
        webActions.setText(getByLocator(systemSettingsPageOR.timestampFromQuantityTextbox_Xpath,
                LocatorIdentifier.Xpath, gridName), timePeriodAgo);
        robotActions.pressTabKey();
        waitForDataToLoad();
    }

    public void saveExportedData(String tabName) {
        Common.createDirectory(Constants.VidistarDownloads);
        winActions.waitForWindowElementToBeVisible(
                getByLocator(systemSettingsPageOR.saveFileNameTextbox_Xpath, LocatorIdentifier.Xpath), 300);
        if (tabName.equalsIgnoreCase("Audit Repository")) {
            winActions.setText(getByLocator(systemSettingsPageOR.saveFileNameTextbox_Xpath, LocatorIdentifier.Xpath),
                    Constants.VidistarDownloads + "\\AuditRepository");
        } else if (tabName.equalsIgnoreCase("Fax Logs")) {
            winActions.setText(getByLocator(systemSettingsPageOR.saveFileNameTextbox_Xpath, LocatorIdentifier.Xpath),
                    Constants.VidistarDownloads + "\\FaxLogs");
        }
        Common.sleep(1, TimeUnit.SECONDS);
        winActions.click(getByLocator(systemSettingsPageOR.saveFileButton_Xpath, LocatorIdentifier.Xpath));
        Common.sleep(2, TimeUnit.SECONDS);
    }

    public boolean isFileExportedInCsvFormat() {
        String exportedFile = Common.getLatestModifiedFile(Constants.VidistarDownloads);
        return exportedFile.contains(".csv");
    }

    public boolean isColumnPresentInCsvFile(List<String> columnName) {
        String exportedFile = Common.getLatestModifiedFile(Constants.VidistarDownloads);
        List<String> columnList;
        boolean isColumnPresent = true;
        try {
            columnList = Files.readAllLines(Paths.get(exportedFile));
            List<String> columns = Arrays.asList(columnList.get(0).split(","));
            columns = columns.stream().map(name -> name.replace("\"", "")).collect(Collectors.toList());
            for (String colName : columnName) {
                isColumnPresent = isColumnPresent && columns.contains(colName);
            }
        } catch (IOException e) {
            Log.printError("File does not exist");
        }
        return isColumnPresent;
    }

    public boolean isCsvContainingFilteredData(String filterName, String filterValue) {
        List<String> columnValueList = Common.getColumnDataFromCsv(filterName, Constants.VidistarDownloads);
        boolean isCsvContainingFilterValue = true;
        for (String name : columnValueList) {
            isCsvContainingFilterValue = isCsvContainingFilterValue && name.contains(filterValue);
        }
        return isCsvContainingFilterValue;
    }

    public int getUILogsCount() {
        return Integer.parseInt(
                webActions.getText(getByLocator(systemSettingsPageOR.logsCountLabel_Xpath, LocatorIdentifier.Xpath))
                        .split(" ")[1]);
    }

    public int getCsvLogsCount() {
        String exportedFile = Common.getLatestModifiedFile(Constants.VidistarDownloads);
        return Common.getRowCountFromCsv(exportedFile);
    }

    public boolean isExportButtonVisible(String leftPanelSetting) {
        webActions.click(getByLocator(systemSettingsPageOR.settingsPanelOption_Xpath, LocatorIdentifier.Xpath,
                leftPanelSetting));
        return webActions.isVisible(getByLocator(systemSettingsPageOR.exportLogsButton_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isSaveFileWindowDisplayed() {
        return winActions
                .isVisible(getByLocator(systemSettingsPageOR.saveFileNameTextbox_Xpath, LocatorIdentifier.Xpath), 300);
    }

    public void sortColumnByOrder(String gridName, String columnName, String sortOrder) {
        webActions.waitForElementsClickability(
                getByLocator(systemSettingsPageOR.columnHeader_Xpath, LocatorIdentifier.Xpath, columnName), 30);
        webActions
                .mouseHover(getByLocator(systemSettingsPageOR.columnHeader_Xpath, LocatorIdentifier.Xpath, columnName));
        sleep(2, TimeUnit.SECONDS);
        webActions.click(
                getByLocator(systemSettingsPageOR.columnHeaderMenuButton_Xpath, LocatorIdentifier.Xpath, gridName));
        if (sortOrder.equalsIgnoreCase("ascending")) {
            webActions.click(getByLocator(systemSettingsPageOR.sortAscendingMenuItem_Xpath, LocatorIdentifier.Xpath));
        } else if (sortOrder.equalsIgnoreCase("descending")) {
            webActions.click(
                    getByLocator(systemSettingsPageOR.sortDescendingMenuItem_Xpath, LocatorIdentifier.Xpath, gridName));
        }
        sleep(3, TimeUnit.SECONDS);
    }

    public List<String> getSystemSettingsGridData(String tabName, String columnName) {
        webActions.waitForElementVisibility(
                getByLocator(systemSettingsPageOR.columnHeaders_Xpath, LocatorIdentifier.Xpath, tabName), 10);
        List<String> columnTexts = webActions.getElementTextList(
                getByLocator(systemSettingsPageOR.columnHeaders_Xpath, LocatorIdentifier.Xpath, tabName));
        int columnIndex = columnTexts.indexOf(columnName);
        webActions.waitForElementVisibility(
                getByLocator(systemSettingsPageOR.systemSettingsGrid_Xpath, LocatorIdentifier.Xpath, tabName), 30);
        return webActions.getColumnText(
                getByLocator(systemSettingsPageOR.systemSettingsGrid_Xpath, LocatorIdentifier.Xpath, tabName),
                columnIndex + 1);
    }

    public boolean isExportErrorMessageDisplayed() {
        return webActions
                .isVisible(getByLocator(systemSettingsPageOR.exportErrorDialogBox_Xpath, LocatorIdentifier.Xpath), 30);
    }

    public void openRolesTab(String username) {
        int cycles = 0;
        while ((!webActions
                .getTextboxValue(getByLocator(systemSettingsPageOR.userIdSearchTextbox_Xpath, LocatorIdentifier.Xpath))
                .equals(username)) && cycles < 20) {
            Common.sleep(500, TimeUnit.MILLISECONDS);
            cycles++;
        }
        webActions.click(getByLocator(systemSettingsPageOR.userDialogRolesTab_Xpath, LocatorIdentifier.Xpath));
    }

    public void searchClinicParameter(String clinicParameterName) {
        webActions.setText(
                getByLocator(systemSettingsPageOR.searchClinicParameterTextbox_Xpath, LocatorIdentifier.Xpath),
                clinicParameterName);
    }

    public String getClinicParameterValue() {
        return webActions.getText(
                getByLocator(systemSettingsPageOR.clinicParameterValueTextarea_Xpath, LocatorIdentifier.Xpath));
    }

    public void setClinicParameterValue(String clinicParameterValue) {
        webActions.setText(
                getByLocator(systemSettingsPageOR.clinicParameterValueTextarea_Xpath, LocatorIdentifier.Xpath),
                clinicParameterValue);
    }

    public void saveClinicParameterValue() {
        webActions.click(
                getByLocator(systemSettingsPageOR.clinicParameterValueSaveButton_Xpath, LocatorIdentifier.Xpath));
        waitForDataToLoad();
    }

    public void clickOnFilteredClinicParameterResultGrid(String filteredClinicParameterResultGrid) {
        webActions.click(getByLocator(systemSettingsPageOR.filteredClinicParameterResultGrid_Xpath,
                LocatorIdentifier.Xpath, filteredClinicParameterResultGrid));
    }

    public void clearClinicParameterValue() {
        webActions.clearText(
                getByLocator(systemSettingsPageOR.clinicParameterValueTextarea_Xpath, LocatorIdentifier.Xpath));

    }

    public void searchPatientName(String patientName) {
        webActions.setText(getByLocator(systemSettingsPageOR.patientNameTextbox_Xpath, LocatorIdentifier.Xpath),
                patientName);

    }

    public void clearTimestampFromField() {
        webActions.clearText(getByLocator(systemSettingsPageOR.timestampFromFilter_Xpath, LocatorIdentifier.Xpath));
        waitForDataToLoad();
    }

    public boolean isExpiredPatientPresentUnderInformationColumn(String patientName) {
        List<String> informationValue = webActions.getColumnText(
                getByLocator(systemSettingsPageOR.auditRepositoryTable_Xpath, LocatorIdentifier.Xpath), 3);
        return informationValue.stream().allMatch(data -> data.contains(patientName));
    }

    public boolean isValueOfClinicParameterChanged(String clinicParameterName, String value) {
        setClinicParameterValue(clinicParameterName);
        return webActions.isVisible(getByLocator(systemSettingsPageOR.clinicParameterSearchTextValue_Xpath,
                LocatorIdentifier.Xpath, value));
    }

    public boolean areSystemNamesAndNumberAvailable() {
        return webActions.isVisible(
                getByLocator(systemSettingsPageOR.systemNamesAndNumbersTable_Xpath, LocatorIdentifier.Xpath), 10);
    }

    public List<String> getSystemNamesAndNumbers() {
        return webActions.getElementTextList(
                getByLocator(systemSettingsPageOR.systemNamesAndNumberLabel_Xpath, LocatorIdentifier.Xpath));
    }

    public void renameSystemDetails(String systemName, String newSystemName) {
        webActions.setText(getByLocator(systemSettingsPageOR.systemDetailsFilterTextbox_Xpath, LocatorIdentifier.Xpath),
                systemName);
        webActions.doubleClick(
                getByLocator(systemSettingsPageOR.systemNamesAndNumberLabel_Xpath, LocatorIdentifier.Xpath));
        sikuliActions.type(newSystemName);
        robotActions.enter();
        Common.sleep(2, TimeUnit.SECONDS);
    }

    public void createNewSystem(String systemName) {
        sikuliActions.type(systemName);
        robotActions.enter();
    }

    public void deleteSystem(String systemName) {
        webActions.setText(getByLocator(systemSettingsPageOR.systemDetailsFilterTextbox_Xpath, LocatorIdentifier.Xpath),
                systemName);
        webActions.click(getByLocator(systemSettingsPageOR.systemNamesAndNumberLabel_Xpath, LocatorIdentifier.Xpath));
        clickOnFunctionality("Delete System Name and Number");
        webActions.click(getByLocator(systemSettingsPageOR.yesButton_Xpath, LocatorIdentifier.Xpath));
        Common.sleep(2, TimeUnit.SECONDS);
    }

    public void enterTemplateInstanceNameInSearchBox(String templateInstanceName) {
        webActions.clearText(
                getByLocator(systemSettingsPageOR.templateInstancesSearchTextbox_Xpath, LocatorIdentifier.Xpath));
        waitForDataToLoad();
        webActions.setText(templateInstanceName,
                getByLocator(systemSettingsPageOR.templateInstancesSearchTextbox_Xpath, LocatorIdentifier.Xpath));
        waitForDataToLoad();
    }

    public void rightClickOnTemplateInstance() {
        webActions.click(
                getByLocator(systemSettingsPageOR.filteredTemplateInstanceResultGrid_Xpath, LocatorIdentifier.Xpath));
        sleep(5, TimeUnit.SECONDS);
        webActions.rightClick(
                getByLocator(systemSettingsPageOR.filteredTemplateInstanceResultGrid_Xpath, LocatorIdentifier.Xpath));
    }

    public void selectRightClickMenuOptionOfTemplateInstance(String menuOption) {
        webActions.click(getByLocator(systemSettingsPageOR.templateInstancesRightClickMenuOption_Xpath,
                LocatorIdentifier.Xpath, menuOption));
    }

    public void enterGUIPathName(String guiPathName) {
        webActions.setText(getByLocator(systemSettingsPageOR.guiPathTextbox_Xpath, LocatorIdentifier.Xpath),
                guiPathName);
    }

    public void clickOnTemplateInstanceFilteredResult() {
        webActions.click(
                getByLocator(systemSettingsPageOR.filteredTemplateInstanceResultGrid_Xpath, LocatorIdentifier.Xpath));
        waitForDataToLoad();
    }

    public void enterCodeDesignatorInSearchBox(String codeDesignator) {
        webActions.setText(
                getByLocator(systemSettingsPageOR.codeDesignatorSearchTextbox_Xpath, LocatorIdentifier.Xpath),
                codeDesignator);
        waitForDataToLoad();
    }

    public void enterMeaningInSearchBox(String meaningName) {
        webActions.setText(getByLocator(systemSettingsPageOR.codeMeaningSearchTextbox_Xpath, LocatorIdentifier.Xpath),
                meaningName);
        waitForDataToLoad();
    }

    public void dragCodeToAllowedStudyTypesSection() {
        webActions.dragAndDrop(getByLocator(systemSettingsPageOR.filteredCodeResultGrid_Xpath, LocatorIdentifier.Xpath),
                getByLocator(systemSettingsPageOR.allowedStudyTypesGrid_Xpath, LocatorIdentifier.Xpath));
        waitForDataToLoad();
    }

    public void unfoldServiceLocationConfiguration() {
        webActions.click(
                getByLocator(systemSettingsPageOR.expandServiceLocationConfiguration_Xpath, LocatorIdentifier.Xpath));
    }

    public void enterServiceLocationNameInSearchBox(String serviceLocationName) {
        webActions.setText(
                getByLocator(systemSettingsPageOR.serviceLocationSearchTextbox_Xpath, LocatorIdentifier.Xpath),
                serviceLocationName);
        waitForDataToLoad();
    }

    public void dragResultToIncludedServiceLocationsGrid() {
        webActions.dragAndDrop(
                getByLocator(systemSettingsPageOR.filteredServiceLocationResultGrid_Xpath, LocatorIdentifier.Xpath),
                getByLocator(systemSettingsPageOR.includedServiceLocationGrid_Xpath, LocatorIdentifier.Xpath));
        waitForDataToLoad();
    }

    public void dragResultToExcludedServiceLocationsGrid() {
        webActions.dragAndDrop(
                getByLocator(systemSettingsPageOR.filteredServiceLocationResultGrid_Xpath, LocatorIdentifier.Xpath),
                getByLocator(systemSettingsPageOR.excludedServiceLocationGrid_Xpath, LocatorIdentifier.Xpath));
        waitForDataToLoad();
    }

    public void unfoldUserRoleAssignmentConfiguration() {
        webActions.click(getByLocator(systemSettingsPageOR.expandUserRoleAssignmentConfiguration_Xpath,
                LocatorIdentifier.Xpath));
    }

    public void enterTemplateInstanceRoleInSearchBox(String role) {
        webActions.setText(getByLocator(systemSettingsPageOR.availableRoleSearchTextbox_Xpath, LocatorIdentifier.Xpath),
                role);
        waitForDataToLoad();
    }

    public void dragResultToVisibleForRolesGrid() {
        webActions.dragAndDrop(getByLocator(systemSettingsPageOR.filteredRoleResultGrid_Xpath, LocatorIdentifier.Xpath),
                getByLocator(systemSettingsPageOR.assignedRoleGrid_Xpath, LocatorIdentifier.Xpath));
        waitForDataToLoad();
    }

    public void removeIncludedServiceLocation(String serviceLocation) {
        webActions.doubleClick(getByLocator(systemSettingsPageOR.includedServiceLocation_Xpath, LocatorIdentifier.Xpath,
                serviceLocation));
        sleep(5, TimeUnit.SECONDS);
    }

    public void removeExcludedServiceLocation(String serviceLocation) {
        webActions.doubleClick(getByLocator(systemSettingsPageOR.excludedServiceLocation_Xpath, LocatorIdentifier.Xpath,
                serviceLocation));
        sleep(5, TimeUnit.SECONDS);
    }

    public String isServiceLocationPresentUnderAvailableServiceLocationsGrid() {
        return webActions.getText(
                getByLocator(systemSettingsPageOR.filteredServiceLocationResultGrid_Xpath, LocatorIdentifier.Xpath));
    }

    public String isNewlyCreatedTemplateInstancePresent() {
        return webActions.getText(
                getByLocator(systemSettingsPageOR.filteredTemplateInstanceResultGrid_Xpath, LocatorIdentifier.Xpath));
    }

    public void removeAllowedStudyType(String codeDesignator) {
        webActions.doubleClick(getByLocator(systemSettingsPageOR.allowedStudyCodeDesignator_Xpath,
                LocatorIdentifier.Xpath, codeDesignator));
        waitForDataToLoad();
    }

    public void unassignTemplateInstanceRole(String role) {
        webActions.doubleClick(getByLocator(systemSettingsPageOR.assignedRole_Xpath, LocatorIdentifier.Xpath, role));
        waitForDataToLoad();
    }

    public void deleteTemplateInstance() {
        webActions.click(getByLocator(systemSettingsPageOR.deleteTemplateInstance_Xpath, LocatorIdentifier.Xpath));
    }

    public String isIncludedServiceLocationEmpty() {
        return webActions.getText(
                getByLocator(systemSettingsPageOR.includedServiceLocationNoItemToShow_Xpath, LocatorIdentifier.Xpath));
    }

    public String isExcludedServiceLocationEmpty() {
        return webActions.getText(
                getByLocator(systemSettingsPageOR.excludedServiceLocationNoItemToShow_Xpath, LocatorIdentifier.Xpath));
    }

    public void selectDeselectServiceLocationProperty(String propertyName, String operation) {
        webActions.waitForElementClickability(getByLocator(systemSettingsPageOR.serviceLocationEditWindow_Xpath, LocatorIdentifier.Xpath),
                10);
        String checkboxStatus = webActions.getElementAttributeValue(
                getByLocator(systemSettingsPageOR.serviceLocationPropertyCheckbox_Xpath, LocatorIdentifier.Xpath, propertyName),
                "style");
        if (operation.equals("select") && checkboxStatus.contains("uncheck")) {
            webActions.click(
                    getByLocator(systemSettingsPageOR.serviceLocationPropertyCheckbox_Xpath, LocatorIdentifier.Xpath, propertyName));
        } else if (operation.equals("deselect") && checkboxStatus.contains("/checked")) {
            webActions.click(
                    getByLocator(systemSettingsPageOR.serviceLocationPropertyCheckbox_Xpath, LocatorIdentifier.Xpath, propertyName));
        }
    }
}