package Steps;

import static Utilities.Common.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import Context.TestContext;
import Pages.SystemSettingsPage;
import TestData.TestData;
import Utilities.Common;
import Utilities.Constants;
import Utilities.Log;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SystemSettingsSteps {

    SystemSettingsPage systemSettingsPage;
    TestContext testContext;

    public SystemSettingsSteps(TestContext context) {
        this.testContext = context;
        systemSettingsPage = new SystemSettingsPage(testContext.getDriver());
    }

    @When("create a new  user with below group & role")
    public void createNewUser(DataTable table) {
        List<Map<String, String>> listOfGroupsAndRoles = table.asMaps(String.class, String.class);
        systemSettingsPage.deleteUserIfExists(TestData.userDetails.newUserName);
        Log.printInfo("Deleted the user if already present");
        systemSettingsPage.createNewUserOperator(TestData.userDetails.newUserName, TestData.userDetails.newUserPassword,
                listOfGroupsAndRoles);
        Log.printInfo("Created a new user operator");
        sleep(2, TimeUnit.SECONDS);
    }

    @When("add {string} role to user")
    @When("remove the {string} role for the user")
    @When("I add {string} role to user")
    public void addRoleToUser(String role) {
        systemSettingsPage.assignRoleToUser(role, TestData.userDetails.adminName);
        Log.printInfo("Added " + role + " to created user");
        testContext.set("AssignedRole", role);
        testContext.set("UnassignedRole", role);
    }

    @When("add the {string} role for the {string} user")
    @When("I remove the {string} role for the {string} user")
    public void assignRoleToUser(String role, String user) {
        String userName = null;
        switch (user.toUpperCase()) {
            case "PHYSICIAN":
                userName = TestData.userDetails.physicianUsername;
                break;
            case "GLOBAL ADMIN":
                userName = TestData.userDetails.globalAdminUsername;
                break;
            case "ADMIN":
                userName = TestData.userDetails.adminUsername;
                break;
            case "FAXLOGAUDITOR":
                userName = TestData.userDetails.faxLogAuditorUsername;
                break;
        }
        systemSettingsPage.assignRoleToUser(role, userName);
        testContext.set("AssignedRole", role);
        testContext.set("UnassignedRole", role);
        testContext.set("User", user);
    }

    @When("I click on the {string} option in the settings panel")
    public void clickOptionFromSettingsPanel(String gridTabName) {
        testContext.set("GridName", gridTabName.replace(" ", ""));
        testContext.set("Option", gridTabName);
        systemSettingsPage.clickOptionFromSettingsPanel(gridTabName);
        systemSettingsPage.waitForDataToLoad();
        Log.printInfo("Option is selected in settings panel");
    }

    @Then("new user should be created")
    public void verifyUserIsCreated() {
        assertTrue(systemSettingsPage.verifyUserDisplayed(TestData.userDetails.newUserName));
        Log.printInfo("Created user is present");
    }

    @Then("{string} is {string} in the settings panel")
    public void verifyFaxLogOption(String option, String status) {
        if (status.equals("present")) {
            assertTrue(systemSettingsPage.isOptionPresentInSettingsPanel(option));
            Log.printInfo(option + " is present in settings panel");
        } else {
            assertFalse(systemSettingsPage.isOptionPresentInSettingsPanel(option));
            Log.printInfo(option + " is not present in settings panel");
        }
    }

    @Then("{string} should be displayed on system settings tab")
    public void verifyFaxStatusEventsDisplayed(String contentHeader) {
        assertTrue(systemSettingsPage.isContentHeaderDisplayed(contentHeader));
        Log.printInfo(contentHeader + " is displayed in system settings panel");
    }

    @When("edit the first user from users list")
    public void editFirstUserFromUsersList() {
        systemSettingsPage.doubleClickToEditFirstUser();
        Log.printInfo("First user is selected");
    }

    @When("open Roles tab")
    public void openRolesTab() {
        systemSettingsPage.openRolesTab();
        Log.printInfo("Roles tab is opened");
    }

    @Then("the following roles should be {string} in the Available Roles list")
    public void verifyAvailableRolesDisplayed(String displayStatus, DataTable table) {
        List<String> expectedRoles = table.asList();
        List<String> actualRoles = systemSettingsPage.getAvailableRoles();

        if (displayStatus.equals("displayed")) {
            assertTrue(actualRoles.containsAll(expectedRoles));
            Log.printInfo("Role is displayed in available roles list");
        } else if (displayStatus.equals("not displayed")) {
            assertFalse(actualRoles.containsAll(expectedRoles));
            Log.printInfo("Role is not displayed in available roles list");
        }
    }

    @When("I close Edit User window")
    @When("I close Add New User window")
    public void closeAddEditUserWindow() {
        systemSettingsPage.closeEditUserWindow();
        Log.printInfo("Add new window user is closed");
    }

    @When("I click on Add New User button")
    public void clickOnAddNewUserButton() {
        systemSettingsPage.clickAddNewUser();
        Log.printInfo("Add new role option is selected");
    }

    @When("I enter {string} in {string} filter under System Settings tab")
    public void inputFilter(String filterValue, String filterName) {
        if (filterName.equals("User Id")) {
            systemSettingsPage.searchForUserId(filterValue);
        }
        testContext.set("FilterValue", filterValue);
        testContext.set("FilterName", filterName);
    }

    @Then("users list should display users only which are having {string} as {string} under System Settings tab")
    public void verifyFilterResult(String filterName, String filterValue) {
        assertTrue(systemSettingsPage.isResultFiltered(filterName, filterValue));
    }

    @When("I {string} checkbox of accreditation logo")
    public void selectCheckBoxOfAccreditationLogo(String operation) {
        Common.sleep(3, TimeUnit.SECONDS);
        systemSettingsPage.setCheckBoxOfAccreditationLogo(operation);
    }

    @When("I set name as {string} to add new service location")
    public void addNewServiceLocationName(String Value) {
        systemSettingsPage.setServiceLocationName(Value);
    }

    @When("I select {string} from hubs dropdown")
    public void assignHubToServiceLocation(String Value) {
        systemSettingsPage.assignHubToServiceLocation(Value);
    }

    @When("save the Configuration instance")
    @When("I click save")
    public void clickOnSave() {
        systemSettingsPage.clickSaveButton();
    }

    @When("I search {string} in service location tab")
    public void searchServiceLocation(String value) {
        systemSettingsPage.searchServiceLocation(value);
        testContext.set("ServiceLocation",value);
        systemSettingsPage.waitForDataToLoad();
    }

    @When("I choose the file of accreditation logo")
    public void chooseFileForLogo() {
        systemSettingsPage.setLogoFilePath();
    }

    @When("I edit the service location")
    public void editServiceLocation() {
        systemSettingsPage.editServiceLocation();
    }

    @When("I save service location")
    public void saveServiceLocation() {
        systemSettingsPage.clickSaveButton();
    }

    @Then("The new service location {string} should be present")
    public void verifyNewServiceLocationIsPresent(String location) {
        assertTrue(systemSettingsPage.isServiceLocationPresent(location));
    }

    @When("I create new hub with name {string} & address {string}")
    public void addNewHub(String name, String address) {
        testContext.set("hubName", name);
        systemSettingsPage.createNewHub(name, address);
    }

    @When("I save hub")
    public void saveHub() {
        systemSettingsPage.clickSaveButton();
    }

    @When("I search {string} in Hubs tab")
    public void searchHub(String value) {
        systemSettingsPage.searchHub(value);
        Common.sleep(5, TimeUnit.SECONDS);
    }

    @Then("the newly created hub should be present")
    public void verifyNewHubIsPresent() {
        assertTrue(systemSettingsPage.isHubPresent(testContext.get("hubName")));
    }

    @When("I create new group with id {string} & address {string}")
    public void addNewGroup(String id, String description) {
        testContext.set("groupId", id);
        systemSettingsPage.createNewGroup(id, description);
    }

    @When("I save group")
    public void saveGroup() {
        systemSettingsPage.clickSaveButton();
    }

    @When("I search {string} in Groups tab")
    public void searchGroup(String value) {
        systemSettingsPage.searchGroup(value);
        Common.sleep(5, TimeUnit.SECONDS);
    }

    @Then("the newly created group should be present")
    public void verifyNewGroupIsPresent() {
        assertTrue(systemSettingsPage.isGroupPresent(testContext.get("groupId")));
    }

    @Then("following Settings {string} be available in the left panel")
    public void verifySettingsInLeftPanel(String displayStatus, DataTable table) {
        List<String> expectedSettings = table.asList();
        testContext.set("ExpectedSettings", expectedSettings);
        List<String> displayedSettings = systemSettingsPage.getSettingsDisplayed();
        testContext.set("displayedSettings", displayedSettings);
        switch (displayStatus.toUpperCase()) {
            case "SHOULD":
                assertTrue(displayedSettings.containsAll(expectedSettings));
                break;
            case "SHOULD NOT":
                assertFalse(displayedSettings.containsAll(expectedSettings));
                break;
        }
    }

    @Then("respective panel for each settings should be visible")
    public void verifyIfSettingsAccessible() {
        List<String> leftPanelSettings = testContext.get("displayedSettings");
        for (String leftPanelSetting : leftPanelSettings) {
            assertTrue(leftPanelSetting + " : is not visible",
                    systemSettingsPage.isSettingPanelVisible(leftPanelSetting));
        }
    }

    @When("search {string} in clinic parameter textbox")
    @When("type {string} in the search clinic parameter textbox")
    public void enterClinicParameterName(String clinicParameterName) {
        testContext.set("clinicParameterName", clinicParameterName);
        systemSettingsPage.searchClinicParameter(clinicParameterName);
    }

    @When("I set the parameter value to {string} and click on Save")
    public void enterParameterValue(String clinicParameterValue) {
        testContext.set("ClinicParameterValue", systemSettingsPage.getClinicParameterValue());
        systemSettingsPage.setClinicParameterValue(clinicParameterValue);
        systemSettingsPage.saveClinicParameterValue();
    }

    @When("I click on {string} in the filtered clinic parameter result")
    public void clickFilteredClinicParameterName(String filteredClinicParameterName) {
        testContext.set("filteredClinicParameterName", filteredClinicParameterName);
        systemSettingsPage.clickOnFilteredClinicParameterResultGrid(filteredClinicParameterName);
    }

    @When("remove the {string} role for the {string} user if present")
    public void removeUserRoleIfPresent(String role, String user) {
        String userName = null;
        if ("PHYSICIAN".equalsIgnoreCase(user)) {
            userName = TestData.userDetails.physicianUsername;
        }
        testContext.set("Username", userName);
        systemSettingsPage.searchForUserId(userName);
        systemSettingsPage.doubleClickToEditFirstUser();
        systemSettingsPage.openRolesTab(testContext.get("Username"));
        List<String> assignedRoles = systemSettingsPage.getAssignedRoles();
        if (assignedRoles.contains(role)) {
            systemSettingsPage.assignRevokeRoleToUser(role);
        }
        systemSettingsPage.clickSaveButton();
        sleep(3, TimeUnit.SECONDS);
    }

    @Then("{string} button should be present")
    public void verifyExportButton(String exportButton) {
        assertTrue("Export button is not displayed", systemSettingsPage.isExportButtonPresent(exportButton));
    }

    @When("{string} the {string} button")
    @When("I {string} the {string} button")
    public void hoverClickExportButton(String userAction, String buttonName) {
        switch (userAction.toUpperCase()) {
            case "CLICK":
                systemSettingsPage.clickOnFunctionality(buttonName);
                break;
            case "HOVER":
                systemSettingsPage.hoverOnFunctionality(buttonName);
                break;
        }
    }

    @Then("{string} tooltip should be displayed")
    public void verifyExportButtonTooltip(String toolTipLabel) {
        assertTrue(systemSettingsPage.isExportButtonTooltipDisplayed(toolTipLabel));
    }

    @When("I enter {string} as {string}")
    @When("I select {string} as {string}")
    public void selectLogFilter(String filterName, String filterValue) {
        testContext.set("ColumnName", filterName.toUpperCase());
        testContext.set("FilteredValue", filterValue);
        switch (filterName.toUpperCase()) {
            case "AUDIT EVENT":
                systemSettingsPage.selectAuditEvent(filterValue);
                break;
            case "DOCUMENT TITLE":
                systemSettingsPage.setDocumentTitle(filterValue);
                break;
            case "TIMESTAMP FROM":
                systemSettingsPage.setDateTimestamp(testContext.get("GridName"), filterValue.split(" ")[0]);
                break;
        }
        systemSettingsPage.waitForDataToLoad();
    }

    @Then("exported data should get downloaded in the csv file format")
    public void verifyExportedFileFormat() {
        assertTrue("Exported file is not in csv format", systemSettingsPage.isFileExportedInCsvFormat());
    }

    @When("save the {string} exported data")
    public void saveServerPdfReport(String tabName) {
        systemSettingsPage.saveExportedData(tabName);
    }

    @Then("csv file {string} contain the following fields")
    public void verifyFieldsInCsvFile(String fieldAvailability, DataTable table) {
        List<String> expectedFields = table.asList();
        if (fieldAvailability.equalsIgnoreCase("SHOULD")) {
            assertTrue("Fields are not found in file", systemSettingsPage.isColumnPresentInCsvFile(expectedFields));
        } else if (fieldAvailability.equalsIgnoreCase("SHOULD NOT"))
            assertFalse("Fields are found in file", systemSettingsPage.isColumnPresentInCsvFile(expectedFields));
    }

    @When("get the {string} logs count")
    public void getLogsCount(String tabName) {
        testContext.set("TabName", tabName);
        testContext.set("LogsCount", systemSettingsPage.getUILogsCount());
    }

    @Then("exported logs count should match with UI logs")
    public void verifyExportedLogsCountWithUiLogs() {
        assertEquals("Exported file is not in csv format",
                systemSettingsPage.getUILogsCount(), systemSettingsPage.getCsvLogsCount());
    }

    @Then("exported logs should contain the filtered results")
    public void verifyExportedLogsWithUIFilter() {
        assertTrue("Exported Logs does not match with the filtered results", systemSettingsPage
                .isCsvContainingFilteredData(testContext.get("ColumnName"), testContext.get("FilteredValue")));
    }

    @Then("export button for this settings should not be visible")
    public void verifyIfExportButtonIsAvailable() {
        List<String> leftPanelSettings = testContext.get("ExpectedSettings");
        for (String leftPanelSetting : leftPanelSettings) {
            assertFalse(leftPanelSetting + " : is visible", systemSettingsPage.isExportButtonVisible(leftPanelSetting));
        }
    }

    @Then("save file window should appear")
    public void isSaveFileWindowDisplayed() {
        assertTrue("Save File window is not displayed", systemSettingsPage.isSaveFileWindowDisplayed());
    }

    @When("I sort {string} column in {string} order")
    public void sortColumnsInAscendingAndDescendingOrder(String columnName, String sortOrder) {
        testContext.set("SortedColumn", columnName.toUpperCase());
        systemSettingsPage.sortColumnByOrder(testContext.get("GridName"), columnName, sortOrder);
        Log.printInfo("sorted column in " + sortOrder);
    }

    @Then("{string} grid is sorted by {string} in {string} order")
    public void verifySystemSettingsGridSorting(String tabName, String columnName, String sortOrder) {
        List<String> columnData = systemSettingsPage.getSystemSettingsGridData(tabName, columnName);
        columnData.removeIf(data -> data.equals("") || data.isEmpty());
        List<String> sortedData = systemSettingsPage.getSystemSettingsGridData(tabName, columnName);
        sortedData.removeIf(data -> data.equals("") || data.isEmpty());
        if (sortOrder.equalsIgnoreCase("ascending")) {
            Collections.sort(sortedData);
            sleep(2, TimeUnit.SECONDS);
        } else if (sortOrder.equalsIgnoreCase("descending")) {
            Collections.sort(sortedData);
            sleep(2, TimeUnit.SECONDS);
            Collections.reverse(sortedData);
            sleep(2, TimeUnit.SECONDS);
        }
        assertEquals(columnData, sortedData);
        Log.printInfo("Column is sorted in " + sortOrder + "order");
    }

    @Then("exported excel should be sorted in {string} order")
    public void isExportedDataSortedAsPerUI(String sortOrder) {
        Log.printInfo(testContext.get("SortedColumn"));
        List<String> columnData = Common.getColumnDataFromCsv(testContext.get("SortedColumn"),
                Constants.VidistarDownloads);
        columnData.removeIf(data -> data.equals("") || data.isEmpty());
        List<String> sortedData = Common.getColumnDataFromCsv(testContext.get("SortedColumn"),
                Constants.VidistarDownloads);
        sortedData.removeIf(data -> data.equals("") || data.isEmpty());
        if (sortOrder.equalsIgnoreCase("ascending")) {
            Collections.sort(sortedData);
            sleep(2, TimeUnit.SECONDS);
        } else if (sortOrder.equalsIgnoreCase("descending")) {
            Collections.sort(sortedData);
            sleep(2, TimeUnit.SECONDS);
            Collections.reverse(sortedData);
            sleep(2, TimeUnit.SECONDS);
        }
        assertEquals(columnData, sortedData);
        Log.printInfo("Column is sorted in " + sortOrder + "order");
    }

    @Then("export error message should be displayed")
    public void isExportErrorMessageDisplayed() {
        assertTrue("Export error message is not displayed", systemSettingsPage.isExportErrorMessageDisplayed());
        Log.printInfo("Export error message is displayed as logs exceeds the limit");
    }

    @When("I search {string} patient under audit repository")
    public void searchPatientName(String patientName) {
        systemSettingsPage.searchPatientName(patientName);
        testContext.set("SearchPatientName", patientName);
    }

    @When("I clear the timestamp value textbox field")
    public void clearTimestampField() {
        systemSettingsPage.clearTimestampFromField();
    }

    @Then("the searched patient should be present under information column")
    public void verifyExpiredPatientIsPresentInAuditLogs() {
        assertTrue("Patient not present",
                systemSettingsPage.isExpiredPatientPresentUnderInformationColumn(testContext.get("SearchPatientName")));
    }

    @Then("the value of {string} {string} change to {string}")
    public void verifyParameterValueIsChanged(String clinicParameterName, String displayStatus, String value) {
        if (displayStatus.equalsIgnoreCase("should")) {
            assertTrue("Parameter Value is not changed",
                    systemSettingsPage.isValueOfClinicParameterChanged(clinicParameterName, value));
        } else {
            assertFalse("Parameter Value is changed",
                    systemSettingsPage.isValueOfClinicParameterChanged(clinicParameterName, value));
        }
    }

    @Then("system name and number should be available")
    public void areSystemNamesAndNumberAvailable() {
        assertTrue("System names and numbers are unavailable", systemSettingsPage.areSystemNamesAndNumberAvailable());
    }

    @When("get the system names and numbers")
    public void getSystemDetails() {
        List<String> systemDetailsList = systemSettingsPage.getSystemNamesAndNumbers();
        System.out.println(systemDetailsList);
        testContext.set("SystemDetailsList", systemDetailsList);
    }

    @When("rename {string} system to {string}")
    public void renameSystem(String systemName, String newSystemName) {
        testContext.set("PreviousSystemName", systemName);
        testContext.set("NewSystemName", newSystemName);
        systemSettingsPage.renameSystemDetails(systemName, newSystemName);
    }

    @When("create a new system as {string}")
    public void createNewSystem(String systemName) {
        systemSettingsPage.createNewSystem(systemName);
        testContext.set("NewSystem", systemName);
    }

    @When("I enter {string} under instances search box")
    public void enterTemplateInstanceNameInSearchBox(String templateInstanceName) {
        testContext.set("TemplateInstanceName", templateInstanceName);
        systemSettingsPage.enterTemplateInstanceNameInSearchBox(templateInstanceName);
    }

    @When("I right click on the template instance search result")
    public void rightClickOnTemplateInstance() {
        systemSettingsPage.rightClickOnTemplateInstance();
    }

    @When("select {string} from the right click menu")
    public void selectRightClickMenuOptionOfTemplateInstance(String menuOption) {
        systemSettingsPage.selectRightClickMenuOptionOfTemplateInstance(menuOption);
    }

    @When("enter {string} in the GUI Path textbox")
    public void enterGUIPathName(String guiPathName) {
        systemSettingsPage.enterGUIPathName(guiPathName);
    }

    @When("I click on the template instance filtered result")
    public void clickOnTemplateInstanceFilteredResult() {
        systemSettingsPage.clickOnTemplateInstanceFilteredResult();
    }

    @When("I enter {string} in Designator search box under Codes in selected Context Group")
    public void enterCodeDesignatorInSearchBox(String codeDesignator) {
        testContext.set("CodeDesignator", codeDesignator);
        systemSettingsPage.enterCodeDesignatorInSearchBox(codeDesignator);
    }

    @When("enter {string} in Meaning search box under Codes in selected Context Group")
    public void enterMeaningInSearchBox(String meaningName) {
        systemSettingsPage.enterMeaningInSearchBox(meaningName);
    }

    @When("I select and drag the result to the Allowed study types section")
    public void dragCodeToAllowedStudyTypesSection() {
        systemSettingsPage.dragCodeToAllowedStudyTypesSection();
    }

    @When("I unfold the Service Location configuration")
    public void unfoldServiceLocationConfiguration() {
        systemSettingsPage.unfoldServiceLocationConfiguration();
    }

    @When("I enter {string} in Name search box under Available service locations")
    @When("enter {string} in Name search box under Available service locations")
    public void enterServiceLocationNameInSearchBox(String serviceLocationName) {
        systemSettingsPage.enterServiceLocationNameInSearchBox(serviceLocationName);
    }

    @When("I select and drag the result to the Included service locations section")
    public void dragResultToIncludedServiceLocationsGrid() {
        systemSettingsPage.dragResultToIncludedServiceLocationsGrid();
    }

    @When("I select and drag the result to the Excluded service locations section")
    public void dragResultToExcludedServiceLocationsGrid() {
        systemSettingsPage.dragResultToExcludedServiceLocationsGrid();
    }

    @When("I unfold the User role assignment configuration")
    public void unfoldUserRoleAssignmentConfiguration() {
        systemSettingsPage.unfoldUserRoleAssignmentConfiguration();
    }

    @When("enter {string} in Name search box under Available roles")
    public void enterTemplateInstanceRoleInSearchBox(String role) {
        testContext.set("Role", role);
        systemSettingsPage.enterTemplateInstanceRoleInSearchBox(role);
    }

    @When("I select and drag the result to the Visible for Roles section")
    public void dragResultToVisibleForRolesGrid() {
        systemSettingsPage.dragResultToVisibleForRolesGrid();
    }

    @When("I double click on {string} in the included service locations section")
    public void removeIncludedServiceLocation(String serviceLocation) {
        testContext.set("IncludedServiceLocation", serviceLocation);
        systemSettingsPage.removeIncludedServiceLocation(serviceLocation);
    }

    @When("I double click on {string} in the excluded service locations section")
    public void removeExcludedServiceLocation(String serviceLocation) {
        testContext.set("ExcludedServiceLocation", serviceLocation);
        systemSettingsPage.removeExcludedServiceLocation(serviceLocation);
    }

    @Then("{string} should move under Available service locations")
    public void isServiceLocationPresentUnderAvailableServiceLocationsGrid(String serviceLocation) {
        assertEquals("Service Location mismatch", serviceLocation,
                systemSettingsPage.isServiceLocationPresentUnderAvailableServiceLocationsGrid());
    }

    @Then("{string} should be visible under template instances")
    public void isNewlyCreatedTemplateInstancePresent(String templateName) {
        assertEquals("Template instance validation failed", templateName,
                systemSettingsPage.isNewlyCreatedTemplateInstancePresent());
    }

    @Then("included and excluded service location is empty")
    public void isIncludedAndExcludedServiceLocationEmpty() {
        assertEquals("Included Service location is not empty", "No items to show.",
                systemSettingsPage.isIncludedServiceLocationEmpty());
        assertEquals("Excluded Service location is not empty", "No items to show.",
                systemSettingsPage.isExcludedServiceLocationEmpty());
        systemSettingsPage.unfoldServiceLocationConfiguration();
    }

    @When("I {string} the {string} checkbox")
    public void selectUnselectProprety(String operation, String propertyName) {
        systemSettingsPage.selectDeselectServiceLocationProperty(propertyName, operation);
        testContext.set("ServiceLocationProperty",propertyName);
    }
}