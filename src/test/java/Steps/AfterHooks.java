package Steps;

import Actions.RobotActions;
import Context.TestContext;
import Pages.*;
import TestData.TestData;
import Utilities.Common;
import Utilities.Constants;
import Utilities.Driver;
import Utilities.Log;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.Status;
import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

import static Utilities.Common.sleep;
import static Utilities.Constants.USER_WORK_DIR;
import static org.junit.Assert.assertFalse;

public class AfterHooks {

    LoginPage loginPage;
    TestContext testContext;
    DicomViewerPage dicomViewerPage;
    StudyListPage studyListPage;
    ReportViewerPage reportViewerPage;
    StudyTreePage studyTreePage;
    SystemSettingsPage systemSettingsPage;
    MediaPage mediaPage;
    AdminControlPage adminControlPage;
    CIAdminControlPage ciAdminControlPage;
    PatientServiceLogsPage patientServiceLogsPage;
    Driver driver;
    RobotActions robotActions;
    OrderListPage orderListPage;
    Scenario scenario;

    public AfterHooks(TestContext testContext) {
        this.testContext = testContext;
        driver = testContext.getDriver();
        loginPage = new LoginPage(driver);
        dicomViewerPage = new DicomViewerPage(driver);
        studyListPage = new StudyListPage(driver);
        reportViewerPage = new ReportViewerPage(driver);
        studyTreePage = new StudyTreePage(driver);
        systemSettingsPage = new SystemSettingsPage(driver);
        mediaPage = new MediaPage(driver);
        robotActions = new RobotActions();
        adminControlPage = new AdminControlPage(driver);
        ciAdminControlPage = new CIAdminControlPage(driver);
        patientServiceLogsPage = new PatientServiceLogsPage(driver);
        orderListPage = new OrderListPage(driver);
        scenario = testContext.getScenario();
    }

    @After("@RevertSignedReport")
    public void revertSignedReport() {
        try {
            studyListPage.switchToVidistarWindow();
            if (testContext.get("RichPatientFirstName") != null) {
                studyListPage.revertSignedReport(testContext.get("RichPatientFirstName"),
                        testContext.get("RichPatientLastName"));
            } else {
                String patientName = testContext.get("FilterValue");
                studyListPage.revertSignedReport(patientName.split("\\^")[1], patientName.split("\\^")[0]);
            }
            studyListPage.waitForDataToLoad();
            Log.printInfo("Signed report is reverted properly");
        } catch (Exception e) {
            logScreenshot(scenario);
        }

    }

    @After("@RevertSignedReportCloseAutomatically")
    public void afterTestSignedReportClosesAutomatically() {
        try {
            studyListPage.revertSignedReport(testContext.get("RichPatientFirstName"),
                    testContext.get("RichPatientLastName"));
            Log.printInfo("Signed report is reverted properly");
            studyListPage.waitForDataToLoad();
            studyListPage.selectStudyWithName(testContext.get("RichPatientFirstName"),
                    testContext.get("RichPatientLastName"));
            Log.printInfo("Selected study to open rich viewer");
            dicomViewerPage.waitForViewerToLoad();
            dicomViewerPage.openMenu("Window");
            Log.printInfo("Window menu is opened");
            dicomViewerPage.selectMenuOption("Preferences");
            Log.printInfo("Window preferences is selected");
            dicomViewerPage.waitForPreferencesWindow();
            dicomViewerPage.unselectPreferencesCheckbox("Close the viewer automatically after signing");
            dicomViewerPage.applyPreferences();
            Log.printInfo("Preferences are applied and closed");
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertSignedReportPrintAutomatically")
    public void revertFinalReportStatus() {
        try {
            reportViewerPage.cancelPrint();
            reportViewerPage.closeReportViewer();
            dicomViewerPage.waitForViewerToLoad();
            dicomViewerPage.openMenu("Window");
            dicomViewerPage.selectMenuOption("Preferences");
            dicomViewerPage.waitForPreferencesWindow();
            dicomViewerPage.selectCheckBox("Print report automatically after signing");
            dicomViewerPage.applyPreferences();
            dicomViewerPage.closeDicomViewer();
            dicomViewerPage.waitForDicomViewerToClose();
            Log.printInfo("Switching back to the Vidistar application");
            loginPage.logout();
            loginPage.loginAsUser("Admin");
            studyListPage.waitForDataToLoad();
            studyListPage.setFilter("Patient Name",
                    testContext.get("RichPatientLastName") + "^" + testContext.get("RichPatientFirstName"));
            studyListPage.revertSignedReport(testContext.get("RichPatientFirstName"),
                    testContext.get("RichPatientLastName"));
            studyListPage.waitForDataToLoad();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@CloseViewerAndDeleteCreatedStudy")
    public void deleteCreatedStudy() {
        try {
            dicomViewerPage.closeDicomViewer();
            Log.printInfo("Dicom viewer is closed");
            studyTreePage.clickDeleteStudy();
            Log.printInfo("New created study is deleted");
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteLastInstance")
    public void deleteLastInstance() {
        try {
            if (testContext.containsKey("AnnotatedImage")) {
                dicomViewerPage.closeDicomViewer();
                String patientName = testContext.get("RichPatientLastName") + "^" + testContext.get("RichPatientFirstName");
                studyListPage.navigateToTab("Study Tree");
                studyTreePage.searchPatientByName(patientName);
                studyTreePage.waitForDataToLoad();
                studyTreePage.expandStudiesForPatient(patientName);
                studyTreePage.expandStudyByIndex(0);
                Common.sleep(1, TimeUnit.SECONDS);
                studyTreePage.expandCollapseSeries("expand", 1);
                Common.sleep(1, TimeUnit.SECONDS);
                studyTreePage.selectLastInstance();
                studyTreePage.deleteSelectedInstance();
            }
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertAssignedRole")
    public void revertAssignedRole() {
        try {
            String assignedRole = testContext.get("AssignedRole");
            systemSettingsPage.waitForDataToLoad();
            systemSettingsPage.clickOptionFromSettingsPanel("Users");
            Log.printInfo("Users options is selected in settings panel");
            systemSettingsPage.assignRoleToUser(assignedRole, TestData.userDetails.adminUsername);
            Log.printInfo(assignedRole + " role is assigned to user");
            sleep(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertUnassignedRole")
    public void assignTemplateAdminRole() {
        try {
            loginPage.logout();
            loginPage.loginAsUser("Admin");
            studyListPage.navigateToTab("System Settings");
            systemSettingsPage.assignRoleToUser(testContext.get("UnassignedRole"), testContext.get("User"));
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertReportPreferences")
    public void afterTestReportAndViewerOpensInSameWindow() {
        try {
            dicomViewerPage.waitForViewerToLoad();
            dicomViewerPage.openMenu("Window");
            dicomViewerPage.selectMenuOption("Preferences");
            dicomViewerPage.waitForPreferencesWindow();
            dicomViewerPage.unselectPreferencesCheckbox("Use just one window (may be useful when having just one screen)");
            dicomViewerPage.applyPreferences();
            Log.printInfo("RevertReportPreferences after scenario is completed");
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DontSaveReport")
    public void dontSaveReport() {
        try {
            reportViewerPage.closeReportViewer();
            Log.printInfo("Closed the report");
            reportViewerPage.dontSaveReport();
            Log.printInfo("Clicked on don't save button");
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteTemplate")
    public void clearReport() {
        try {
            exitReportAndViewer();
            loginPage.logout();
            loginPage.loginAsUser("TEMPLATEADMIN");
            studyListPage.waitForDataToLoad();
            studyListPage.navigateToTab("System Settings");
            systemSettingsPage.clickOptionFromSettingsPanel("Template administration");
            systemSettingsPage.clickWarningOkButton();
            systemSettingsPage.clickReportDefaults();
            systemSettingsPage.deleteTemplate(testContext.get("TemplateName"));
            systemSettingsPage.waitForDataToLoad();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@selectLeftPanelOptionOnWindowPreferences")
    public void selectLeftPanelOption() {
        try {
            dicomViewerPage.getDicomViewerWindow();
            dicomViewerPage.openMenu("Window");
            dicomViewerPage.selectMenuOption("Preferences");
            dicomViewerPage.isWindowPreferencesWindowVisible();
            dicomViewerPage.waitForPreferencesWindow();
            dicomViewerPage.selectCheckBox("Show left panel on startup");
            dicomViewerPage.applyPreferences();
            dicomViewerPage.closeDicomViewer();
        } catch (Exception e) {
            logScreenshot(scenario);
        }

    }

    @After("@RevertServiceLocation")
    public void deleteServiceLocation() {
        try {
            systemSettingsPage.searchServiceLocation("Automation test");
            studyListPage.waitForDataToLoad();
            systemSettingsPage.editServiceLocation();
            studyListPage.waitForDataToLoad();
            systemSettingsPage.assignHubToServiceLocation("Florida");
            systemSettingsPage.clickSaveButton();
            studyListPage.waitForDataToLoad();
            systemSettingsPage.deleteServiceLocation();
            studyTreePage.clickOnYesButtonInPopup();
            studyTreePage.getNoItemsToShowMessage();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@UnselectLogo")
    public void deselectLogo() {
        try {
            reportViewerPage.closeReportViewer();
            reportViewerPage.dontSaveReport();
            dicomViewerPage.closeDicomViewer();
            studyListPage.setFilter(testContext.get("RichPatientLastName") + "^" + testContext.get("RichPatientFirstName"),
                    "Patient Name");
            studyListPage.waitForDataToLoad();
            studyListPage.rightClickOnFirstStudy();
            studyListPage.selectOptionFromRightClickMenu("Edit Study Attributes");
            Common.sleep(3, TimeUnit.SECONDS);
            studyListPage.assignServiceLocationToStudyInStudyList("VidiStar, LLC");
            systemSettingsPage.clickSaveButton();
            studyListPage.clickYesButton();
            studyListPage.waitForDataToLoad();
            loginPage.logout();
            loginPage.loginAsUser("Admin");
            loginPage.isUserLoggedIn("Admin");
            studyListPage.waitForDataToLoad();
            studyListPage.navigateToTab("System Settings");
            studyTreePage.waitForDataToLoad();
            systemSettingsPage.clickOptionFromSettingsPanel("Service Locations");
            systemSettingsPage.searchServiceLocation("Automation test SL");
            studyTreePage.waitForDataToLoad();
            systemSettingsPage.editServiceLocation();
            studyTreePage.waitForDataToLoad();
            systemSettingsPage.assignHubToServiceLocation("Florida");
            systemSettingsPage.clickSaveButton();
            studyTreePage.waitForDataToLoad();
            systemSettingsPage.deleteServiceLocation();
            studyTreePage.clickOnYesButtonInPopup();
            studyTreePage.getNoItemsToShowMessage();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteLatestSeriesInstanceAndRevertFinalStatusReport")
    public void deleteLatestSeriesInstanceAndRevertFinalStatusReport() {
        try {
            String patientName = testContext.get("RichPatientLastName") + "^" + testContext.get("RichPatientFirstName");
            if (reportViewerPage.isReportWindowVisible()) {
                reportViewerPage.closeReportViewer();
                Log.printInfo("Closed the report");
                reportViewerPage.dontSaveReport();
                Log.printInfo("Clicked on dont save button");
            }
            dicomViewerPage.getDicomViewerWindow();
            dicomViewerPage.closeDicomViewer();
            loginPage.logout();
            loginPage.loginAsUser("GLOBAL ADMIN");
            studyListPage.waitForStudyListToLoadTheData();
            studyListPage.searchForPatientName(patientName);
            studyTreePage.waitForDataToLoad();
            revertSignedReport();
            studyListPage.waitForDataToLoad();
            studyListPage.clickAdvancedFilter();
            studyListPage.applyModalityFilter("SR");
            studyListPage.waitForDataToLoad();
            studyListPage.navigateToTab("Study Tree");
            studyTreePage.expandStudiesForPatient(patientName);
            studyTreePage.expandStudyByIndex(0);
            Common.sleep(1, TimeUnit.SECONDS);
            studyTreePage.selectSeriesByIndex(0);
            Common.sleep(1, TimeUnit.SECONDS);
            studyTreePage.deleteSelectedSeries();
            studyTreePage.waitForDataToLoad();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertSignedReportPrintWithDefaultPrinter")
    public void revertFinalReportWindowPreferenceAndStatus() {
        try {
            reportViewerPage.CancelSendFaxEmail();
            reportViewerPage.closeReportViewer();
            dicomViewerPage.waitForViewerToLoad();
            dicomViewerPage.openMenu("Window");
            dicomViewerPage.selectMenuOption("Preferences");
            dicomViewerPage.waitForPreferencesWindow();
            dicomViewerPage.unselectPreferencesCheckbox("Print to default printer");
            dicomViewerPage.unselectPreferencesCheckbox("Send report to referring physician automatically after signing");
            dicomViewerPage.applyPreferences();
            dicomViewerPage.closeDicomViewer();
            dicomViewerPage.waitForDicomViewerToClose();
            Log.printInfo("Switching back to the Vidistar application");
            studyListPage.revertSignedReport(testContext.get("RichPatientFirstName"),
                    testContext.get("RichPatientLastName"));
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertHub")
    public void deleteHub() {
        try {
            systemSettingsPage.searchHub(testContext.get("hubName"));
            studyListPage.waitForDataToLoad();
            systemSettingsPage.deleteHub();
            studyListPage.waitForDataToLoad();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertGroup")
    public void deleteGroup() {
        try {
            systemSettingsPage.searchGroup(testContext.get("groupId"));
            studyListPage.waitForDataToLoad();
            systemSettingsPage.deleteGroup();
            studyListPage.waitForDataToLoad();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertNuclearTabsOrder")
    public void revertNuclearTabOrder() {
        try {
            dicomViewerPage.closeDicomViewer();
            studyListPage.selectStudyWithName(testContext.get("RichPatientFirstName"),
                    (testContext.get("RichPatientLastName")));
            dicomViewerPage.waitForViewerToLoad();
            dicomViewerPage.openMenu("Window");
            dicomViewerPage.selectMenuOption("Preferences");
            dicomViewerPage.waitForPreferencesWindow();
            dicomViewerPage.clickOnSubOptionInPreferences("Active Tabs", "Nuclear Medicine");
            dicomViewerPage.applyPreferences();
            dicomViewerPage.closeDicomViewer();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@UnfinalizeStudy")
    public void unfinalizeStudyUnassignPhysician() {
        try {
            if (studyListPage.isFaxLogWindowDisplayed()) {
                studyListPage.closeFaxLogWindow();
            }
            studyListPage.selectStudyWithName(testContext.get("RichPatientFirstName"),
                    (testContext.get("RichPatientLastName")));
            dicomViewerPage.waitForViewerToLoad();
            dicomViewerPage.openMenu("Window");
            dicomViewerPage.selectMenuOption("Preferences");
            dicomViewerPage.waitForPreferencesWindow();
            dicomViewerPage.unselectPreferencesCheckbox("Send report to referring physician automatically after signing");
            dicomViewerPage.applyPreferences();
            dicomViewerPage.closeDicomViewer();
            studyListPage.revertSignedReport(testContext.get("RichPatientFirstName"),
                    testContext.get("RichPatientLastName"));
            studyListPage.waitForDataToLoad();
            studyListPage.rightClickOnFirstStudy();
            studyListPage.selectOptionFromRightClickMenu("Assign Study to Referring Physicians");
            studyListPage.removedAssignedPhysician();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteCreatedPatient")
    public void deleteCreatedPatient() {
        try {
            studyTreePage.waitForDataToLoad();
            studyListPage.navigateToTab("Study List");
            studyListPage.setFilter("Patient ID", testContext.get("PatientId"));
            studyListPage.waitForDataToLoad();
            studyListPage.clickAdvancedFilter();
            studyListPage.navigateToTab("Study Tree");
            studyTreePage.showPatientsWithoutStudy();
            studyTreePage.selectFirstPatient();
            studyTreePage.deleteSelectedPatient();
            studyTreePage.clickOnYesButtonInPopup();
            studyTreePage.waitForDataToLoad();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteTemplateAndFolder")
    public void deleteTemplateAndFolder() {
        try {
            reportViewerPage.deleteTemplate();
            Common.sleep(2, TimeUnit.SECONDS);
            reportViewerPage.deleteFolder();
            reportViewerPage.closeReportViewer();
            reportViewerPage.saveReport();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertPatientStudySeries")
    public void RevertPatientStudySeries() {
        try {
            studyTreePage.clickEditSeriesIcon();
            studyTreePage.editModality(testContext.get("existingModality"));
            studyTreePage.clickStudyCollapseArrow();
            studyTreePage.waitForDataToLoad();
            studyTreePage.rightClickOnFirstPatient();
            studyTreePage.selectOptionFromRightClickMenu("Edit Patient Attributes");
            studyTreePage.editPatientId(testContext.get("existingId"));
            studyListPage.navigateToTab("Study List");
            studyTreePage.waitForDataToLoad();
            studyListPage.rightClickOnFirstStudy();
            studyListPage.selectOptionFromRightClickMenu("Edit Study Attributes");
            studyListPage.editDescription(testContext.get("Existing description"));
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertFontSettings")
    public void revertFontSettings() {
        try {
            String initialFont = testContext.get("InitialFont");
            String initialFontSize = testContext.get("InitialFontSize");
            dicomViewerPage.getDicomViewerWindow();
            dicomViewerPage.openMenu("Window");
            dicomViewerPage.selectMenuOption("Preferences");
            dicomViewerPage.waitForPreferencesWindow();
            dicomViewerPage.clickChangeFontButton();
            dicomViewerPage.setFontType(initialFont);
            dicomViewerPage.setFontSize(initialFontSize);
            reportViewerPage.pressEnter();
            dicomViewerPage.applyPreferences();
            Common.sleep(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@UnselectPrinterIconOption")
    public void unSelectPrinterIconOption() {
        try {
            dicomViewerPage.getDicomViewerWindow();
            dicomViewerPage.rightClickOnViewport(0);
            dicomViewerPage.clickOnContextMenuOptionOnImage("Unselect all images (for report)");
            dicomViewerPage.openMenu("File");
            dicomViewerPage.selectMenuOption("Exit");
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertReport")
    public void revertReport() {
        try {
            studyListPage.switchToVidistarWindow();
            if (!loginPage.isUserLoggedIn("Admin")) {
                loginPage.logout();
                loginPage.loginAsUser("admin");
            }
            if (testContext.get("RichPatientFirstName") != null) {
                studyListPage.searchForPatientName(
                        testContext.get("RichPatientLastName") + "^" + testContext.get("RichPatientFirstName"));
                studyTreePage.waitForDataToLoad();
                studyListPage.revertSignedReport(testContext.get("RichPatientFirstName"),
                        testContext.get("RichPatientLastName"));
            } else {
                String patientName = testContext.get("FilterValue");
                studyListPage.searchForPatientName(patientName);
                studyTreePage.waitForDataToLoad();
                studyListPage.revertSignedReport(patientName.split("\\^")[0], patientName.split("\\^")[1]);
            }
            Log.printInfo("Signed report is reverted properly");
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertViewerLayout")
    public void revertViewerLayout() {
        try {
            dicomViewerPage.getDicomViewerWindow();
            dicomViewerPage.openMenu("Window");
            dicomViewerPage.selectMenuOption("Preferences");
            dicomViewerPage.waitForPreferencesWindow();
            dicomViewerPage.changeDefaultRowCount(testContext.get("DefaultRowCount"));
            dicomViewerPage.changeDefaultColumnCount(testContext.get("DefaultColumnCount"));
            dicomViewerPage.applyPreferences();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteCorrectedReport")
    public void deleteReport() {
        try {
            String patientName = testContext.get("RichPatientLastName") + "^" + testContext.get("RichPatientFirstName");
            reportViewerPage.closeReportViewer();
            dicomViewerPage.closeDicomViewer();
            revertSignedReport();
            studyListPage.waitForDataToLoad();
            if (testContext.containsKey("IsReportSigned")) {
                loginPage.logout();
                loginPage.login(TestData.userDetails.reportFixerUsername, TestData.userDetails.reportFixerPassword);
                studyListPage.setFilter("Patient Name", patientName);
                studyListPage.waitForDataToLoad();
                studyListPage.clickAdvancedFilter();
                studyListPage.applyModalityFilter("SR");
                studyTreePage.waitForDataToLoad();
                studyListPage.navigateToTab("Study Tree");
                studyTreePage.expandStudiesForPatient(patientName);
                studyTreePage.expandStudyByIndex(0);
                studyTreePage.waitForDataToLoad();
                studyTreePage.selectSeriesByIndex(1);
                studyTreePage.waitForDataToLoad();
                studyTreePage.deleteSelectedSeries();
                studyTreePage.waitForDataToLoad();
            }
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeletePreset")
    public void deletePreset() {
        try {
            loginPage.logout();
            loginPage.login(TestData.userDetails.physicianUsername, TestData.userDetails.physicianUserPassword);
            studyListPage.deletePreset(testContext.get("PresetName"));
            Common.sleep(1, TimeUnit.SECONDS);
            studyListPage.clickOnPresetDropdown();
            assertFalse(studyListPage.getPresetFilterOptions().contains("Test Preset"));
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertFinalReportViewingPreference")
    public void revertFinalReportViewingPreference() {
        try {
            if (reportViewerPage.isReportWindowVisible()) {
                reportViewerPage.closeReportViewer();
                Log.printInfo("Closed the report");
                reportViewerPage.dontSaveReport();
                Log.printInfo("Clicked on dont save button");
                Common.sleep(2, TimeUnit.SECONDS);
            }
            if (!dicomViewerPage.isDicomViewerWindowVisible()) {
                studyListPage.selectStudyWithName(testContext.get("RichPatientFirstName"),
                        testContext.get("RichPatientLastName"));
            }
            dicomViewerPage.waitForViewerToLoad();
            dicomViewerPage.openMenu("Window");
            dicomViewerPage.selectMenuOption("Preferences");
            dicomViewerPage.waitForPreferencesWindow();
            dicomViewerPage.unselectPreferencesCheckbox("Always use last signed preliminary report when creating final");
            dicomViewerPage.applyPreferences();
            dicomViewerPage.closeDicomViewer();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertToggleImageSelection")
    public void revertToggleImageSelection() {
        try {
            reportViewerPage.cancelPrint();
            reportViewerPage.closeReportViewer();
            reportViewerPage.dontSaveReport();
            dicomViewerPage.waitForViewerToLoad();
            dicomViewerPage.rightClickOnViewport(0);
            dicomViewerPage.clickOnContextMenuOptionOnImage("Unselect all images (for export/print)");
            dicomViewerPage.closeDicomViewer();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RemoveAssignedPhysician")
    public void removeAssignedPhysician() {
        try {
            // loginPage.loginAsUser("OPERATOR");
            studyListPage.setFilter("Patient Name",
                    testContext.get("RichPatientLastName") + "^" + testContext.get("RichPatientFirstName"));
            studyListPage.revertAssignedReferringPhysician(testContext.get("RichPatientFirstName"),
                    testContext.get("RichPatientLastName"), testContext.get("PhysicianName"));
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertSignedReportAndAutoSendFax")
    public void RevertSignedReportFaxAddressBookWindow() {
        try {
            if (studyListPage.isFaxLogWindowDisplayed()) {
                studyListPage.closeFaxLogWindow();
            }
            studyListPage.revertSignedReport(testContext.get("RichPatientFirstName"),
                    testContext.get("RichPatientLastName"));
            studyListPage.waitForDataToLoad();
            studyListPage.rightClickOnFirstStudy();
            studyListPage.selectOptionFromRightClickMenu("Assign Study to Referring Physicians");
            studyListPage.removedAssignedPhysician();
            studyListPage.selectStudyWithName(testContext.get("RichPatientFirstName"),
                    testContext.get("RichPatientLastName"));
            dicomViewerPage.waitForViewerToLoad();
            dicomViewerPage.openMenu("Window");
            dicomViewerPage.selectMenuOption("Preferences");
            dicomViewerPage.waitForPreferencesWindow();
            dicomViewerPage.unselectPreferencesCheckbox("Do not prompt with the address book on automatic report sending");
            dicomViewerPage.unselectPreferencesCheckbox("Send report to referring physician automatically after signing");
            dicomViewerPage.applyPreferences();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertAutomaticReportOpenOnStartup")
    public void RevertAutomaticReportWindowOpen() {
        try {
            if (dicomViewerPage.isErrorPopupDisplayed()) {
                dicomViewerPage.dismissErrorPopup();
            } else {
                reportViewerPage.closeReportViewer();
            }
            dicomViewerPage.getDicomViewerWindow();
            dicomViewerPage.openMenu("Window");
            dicomViewerPage.selectMenuOption("Preferences");
            dicomViewerPage.waitForPreferencesWindow();
            dicomViewerPage.unselectPreferencesCheckbox("Automatically open editor window on viewer startup");
            dicomViewerPage.applyPreferences();
            dicomViewerPage.closeDicomViewer();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteImportedStudy")
    public void deleteImportedStudy() {
        try {
            studyListPage.selectPresetFilter("CLEAR");
            studyListPage.waitForDataToLoad();
            studyListPage.navigateToTab("Study Tree");
            studyTreePage.searchPatientByName(testContext.get("FilterValue"));
            studyListPage.waitForDataToLoad();
            studyTreePage.deleteStudy();
            studyTreePage.getNoItemsToShowMessage();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RemovePrintIcon")
    public void RemovePrintIcon() {
        try {
            reportViewerPage.closeReportViewer();
            reportViewerPage.dontSaveReport();
            Common.sleep(3, TimeUnit.SECONDS);
            dicomViewerPage.getDicomViewerWindow();
            dicomViewerPage.rightClickOnViewport(1);
            dicomViewerPage.selectOptionFromViewerRightClickMenu("Unselect all images (for report)");
            dicomViewerPage.openMenu("File");
            dicomViewerPage.selectMenuOption("Exit");
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertReportEditorChanges")
    public void RevertReportGUIChanges() {
        try {
            reportViewerPage.closeReportViewer();
            reportViewerPage.dontSaveReport();
            Common.sleep(3, TimeUnit.SECONDS);
            dicomViewerPage.getDicomViewerWindow();
            dicomViewerPage.selectTabOnDicomViewer("Template Administration");
            dicomViewerPage.selectConfigurationInstanceFromList("com.vidistar.cath.final");
            dicomViewerPage.selectConfigurationInstanceParameterFromList("TEMPLATE_POSTPROCESSING");
            dicomViewerPage.clickThreeDotsOnEditor();
            dicomViewerPage.selectTabOnDicomViewer("Source");
            dicomViewerPage.removeDeleteProcessorTag();
            dicomViewerPage.selectTabOnDicomViewer("Preview");
            dicomViewerPage.dragLabelOverOtherLabel("Systolic blood pressure");
            dicomViewerPage.openMenu("File");
            dicomViewerPage.selectMenuOption("Save");
            dicomViewerPage.selectTabOnDicomViewer("Template Administration");
            dicomViewerPage.openMenu("File");
            dicomViewerPage.selectMenuOption("Save");
            dicomViewerPage.closeDicomViewer();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertNewAssignedPhysician")
    public void revertNewAssignedPhysician() {
        try {
            reportViewerPage.closeReportViewer();
            reportViewerPage.dontSaveReport();
            dicomViewerPage.getDicomViewerWindow();
            dicomViewerPage.closeDicomViewer();
            studyListPage.rightClickOnFirstStudy();
            studyListPage.selectOptionFromRightClickMenu("Assign Study to Referring Physicians");
            studyListPage.assignOrRemoveReferringPhysician("remove", "vidistarfax");
            studyListPage.assignOrRemoveReferringPhysician("add", "amoody");
            studyListPage.clickSaveButton();
            studyListPage.waitForDataToLoad();
            studyListPage.selectStudyWithName(testContext.get("RichPatientFirstName"),
                    testContext.get("RichPatientLastName"));
            dicomViewerPage.waitForViewerToLoad();
            dicomViewerPage.openMenu("Window");
            dicomViewerPage.selectMenuOption("Preferences");
            dicomViewerPage.waitForPreferencesWindow();
            dicomViewerPage.unselectPreferencesCheckbox("Setup referring physician list on the report from study list");
            dicomViewerPage.applyPreferences();
            dicomViewerPage.closeDicomViewer();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@CloseReportViewer")
    public void closeReportViewer() {
        dontSaveReport();
    }

    @After("@DeleteLastSignedReport")
    public void deleteLastSignedReport() {
        try {
            if ((boolean) testContext.get("IsReportSigned")) {
                dicomViewerPage.closeDicomViewer();
                revertReport();
                studyListPage.waitForDataToLoad();
                studyListPage.navigateToTab("Study Tree");
                studyTreePage.expandFirstPatientOnStudyTree();
                studyTreePage.waitForDataToLoad();
                studyTreePage.expandStudyByIndex(0);
                studyTreePage.waitForDataToLoad();
                studyTreePage.selectSeriesByIndex(1);
                studyTreePage.deleteSelectedSeries();
                studyTreePage.waitForDataToLoad();
            }
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteBurnedPatients")
    public void deleteBurnedPatients() {
        try {
            loginPage.logout();
            loginPage.loginAsUser("Admin");
            loginPage.isUserLoggedIn("Admin");
            studyListPage.waitForDataToLoad();
            studyListPage.navigateToTab("Media");
            studyTreePage.waitForDataToLoad();
            mediaPage.searchForPatientName(testContext.get("patientName"));
            studyTreePage.selectAllPatients();
            mediaPage.clickDeleteMediaButton();
            studyTreePage.clickOnYesButtonInPopup();
            studyTreePage.getNoItemsToShowMessage();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@UnmergeStudy")
    public void unmergeStudy() {
        try {
            studyTreePage.selectFirstStudyOfFirstPatient();
            studyTreePage.selectSecondPatient();
            studyTreePage.selectMergePatientOption();
            studyTreePage.waitForDataToLoad();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteTheCreatedStudyAndUncheckOptionInPreferences")
    public void deleteTheCreatedStudyAndUncheckOptionInPreferences() {
        try {
            reportViewerPage.closeReportViewer();
            reportViewerPage.dontSaveReport();
            dicomViewerPage.closeDicomViewer();
            studyTreePage.clickDeleteStudy();
            studyListPage.navigateToTab("Study List");
            studyListPage.revertSignedReport(testContext.get("RichPatientFirstName"),
                    testContext.get("RichPatientLastName"));
            studyListPage.waitForDataToLoad();
            studyListPage.selectStudyWithName(testContext.get("RichPatientFirstName"),
                    testContext.get("RichPatientLastName"));
            dicomViewerPage.waitForViewerToLoad();
            dicomViewerPage.openMenu("Window");
            dicomViewerPage.selectMenuOption("Preferences");
            dicomViewerPage.waitForPreferencesWindow();
            dicomViewerPage.selectCheckBox("Copy history  indications from previous final report");
            dicomViewerPage.applyPreferences();
            dicomViewerPage.closeDicomViewer();
            dicomViewerPage.waitForDicomViewerToClose();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@ResetSessionTimeout")
    public void resetSessionTimeout() {
        try {
            if (loginPage.isLoginAlertDisplayed()) {
                loginPage.login(TestData.userDetails.jBossAdminUsername, TestData.userDetails.jBossAdminPassword);
            }
            switch (System.getProperty("env").toUpperCase()) {
                case "DEMODEVEL":
                    studyListPage.switchToJbossManagementConsole();
                    studyListPage.refreshPage();
                    adminControlPage.switchToObjectFilterViewFrame();
                    adminControlPage.setSessionTimeout(2700);
                    break;
                case "CI":
                    studyListPage.switchToHawtioWindow();
                    studyListPage.refreshPage();
                    ciAdminControlPage.waitForAdminControlPageToLoad();
                    ciAdminControlPage.setSessionTimeout(2700);
                    break;
                default:
                    adminControlPage.switchToObjectFilterViewFrame();
                    adminControlPage.setSessionTimeout(2700);
            }
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@deleteAllMaskedImages")
    public void deleteAllMaskedImages() {
        try {
            Common.deleteAllFilesWithinFolder(USER_WORK_DIR + "\\src\\test\\resources\\Masked Images");
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertSingleMonitorView")
    public void RevertSingleMonitorView() {
        try {
            dicomViewerPage.openMenu("Window");
            dicomViewerPage.selectMenuOption("Preferences");
            dicomViewerPage.waitForPreferencesWindow();
            dicomViewerPage.selectCheckBox("Use just one window (may be useful when having just one screen)");
            dicomViewerPage.applyPreferences();
            dicomViewerPage.clickButtonOnRestartViewerModal("Restart later");
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteServerPdfAndRevertSignedReport")
    public void deleteExistingServerPdfAndRevertSignedReport() {
        try {
            loginPage.logout();
            loginPage.loginAsUser("Admin");
            studyListPage.setFilter("Patient Name",
                    testContext.get("RichPatientLastName") + "^" + testContext.get("RichPatientFirstName"));
            studyListPage.revertSignedReport(testContext.get("RichPatientFirstName"),
                    testContext.get("RichPatientLastName"));
            studyListPage.waitForDataToLoad();
            Common.deleteExistingFolder(Constants.serverPdfZipFile);
            Common.deleteExistingFolder(Common.getLatestModifiedFile(Constants.serverPdfFolder));
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertSignedSimpleViewerReport")
    public void revertSignedSimpleViewerReport() {
        try {
            studyListPage.revertSignedSimpleViewerReport();
            studyListPage.waitForDataToLoad();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteExistingRichViewerFile")
    public void deleteExistingRichViewerFile() {
        try {
            Common.deleteExistingFile(Constants.downloadedViewerFile);
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteServerPdf")
    public void deleteExistingServerPdf() {
        try {
            Common.deleteExistingFolder(Constants.serverPdfZipFile);
            Common.deleteExistingFolder(Common.getLatestModifiedFile(Constants.serverPdfFolder));
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertSignedWorksheetStatus")
    public void revertSignedWorksheetStatus() {
        try {
            String patientName = testContext.get("RichPatientLastName") + "^" + testContext.get("RichPatientFirstName");
            reportViewerPage.closeReportViewer();
            reportViewerPage.dontSaveReport();
            dicomViewerPage.closeDicomViewer();
            if (!loginPage.isUserLoggedIn("ADMIN")) {
                loginPage.logout();
                loginPage.loginAsUser("ADMIN");
            }
            studyListPage.waitForStudyListToLoadTheData();
            studyListPage.searchForPatientName(patientName);
            studyListPage.revertPrelimSignedReport(testContext.get("RichPatientFirstName"),
                    testContext.get("RichPatientLastName"));
            studyListPage.waitForDataToLoad();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteExportedFiles")
    public void deletedExportedFiles() {
        try {
            Common.deleteAllFilesWithinFolder(Constants.VidistarDownloads);
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertAssignedFaxlogAuditorRole")
    public void revertFaxlogAuditorRole() {
        try {
            loginPage.logout();
            loginPage.loginAsUser("Admin");
            studyListPage.waitForStudyListToLoadTheData();
            studyListPage.navigateToTab("System Settings");
            systemSettingsPage.assignRoleToUser("faxlog", testContext.get("User"));
            systemSettingsPage.assignRoleToUser("auditor", testContext.get("User"));
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertUnselectedAutostartPlayback")
    public void revertAutostartPlayback() {
        try {
            dicomViewerPage.openMenu("Window");
            Log.printInfo("Window menu is opened");
            dicomViewerPage.selectMenuOption("Preferences");
            dicomViewerPage.waitForPreferencesWindow();
            dicomViewerPage.selectPreferencesCheckbox("Auto-start playback");
            Log.printInfo("Auto-start preference is selected");
            dicomViewerPage.applyPreferences();
            Common.sleep(2, TimeUnit.SECONDS);
            Log.printInfo("Preferences are applied and closed");
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertAssignedPhysician")
    public void revertAssignedPhysician() {
        try {
            studyListPage.navigateToTab("Study List");
            studyTreePage.clickToolBarButton("Assign Studies to Physicians");
            studyListPage.assignPhysicianToStudy("AutoPhysician1");
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertAssignedPhysicianGroup")
    public void revertAssignedPhysicianGroup() {
        try {
            studyListPage.navigateToTab("Study List");
            studyTreePage.clickToolBarButton("Assign Studies to Physician Groups");
            studyListPage.assignPhysicianToStudy("American League");
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertAndDeleteFinalReport")
    public void deleteReportFromStudyTree() {
        try {
            String patientName = testContext.get("RichPatientLastName") + "^" + testContext.get("RichPatientFirstName");
            studyTreePage.navigateToTab("Study List");
            studyListPage.revertSignedReportForStudyNumber(testContext.get("RichPatientFirstName"),
                    testContext.get("RichPatientLastName"), 2);
            studyListPage.waitForDataToLoad();
            studyListPage.clickAdvancedFilter();
            studyListPage.setFilter("Modality", "SR");
            studyListPage.waitForDataToLoad();
            studyListPage.navigateToTab("Study Tree");
            studyTreePage.waitForDataToLoad();
            studyTreePage.expandFirstPatientOnStudyTree();
            studyTreePage.expandStudiesForPatient(patientName);
            studyTreePage.expandCollapseStudy("expand", 2);
            Common.sleep(1, TimeUnit.SECONDS);
            studyTreePage.selectSeriesByIndex(0);
            Common.sleep(1, TimeUnit.SECONDS);
            studyTreePage.deleteSelectedSeries();
            studyTreePage.waitForDataToLoad();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteStudy")
    public void deleteStudy() {
        try {
            studyTreePage.deleteStudy();
            studyTreePage.getNoItemsToShowMessage();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertClinicParameterValue")
    public void revertClinicParameterValue() {
        try {
            if (dicomViewerPage.isDicomViewerWindowVisible()) {
                dicomViewerPage.closeDicomViewer();
            }
            studyListPage.navigateToTab("System Settings");
            systemSettingsPage.clickOptionFromSettingsPanel(testContext.get("Option"));
            systemSettingsPage.searchClinicParameter(testContext.get("clinicParameterName"));
            systemSettingsPage.clickOnFilteredClinicParameterResultGrid(testContext.get("filteredClinicParameterName"));
            systemSettingsPage.setClinicParameterValue(testContext.get("ClinicParameterValue"));
            systemSettingsPage.saveClinicParameterValue();
            systemSettingsPage.waitForDataToLoad();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteLatestSeriesAndRevertWorksheetStatusReport")
    public void deleteLatestSeriesInstanceAndRevertWorksheetStatusReport() {
        try {
            String patientName = testContext.get("RichPatientLastName") + "^" + testContext.get("RichPatientFirstName");
            reportViewerPage.closeReportViewer();
            reportViewerPage.dontSaveReport();
            dicomViewerPage.closeDicomViewer();
            if (!loginPage.isUserLoggedIn("ADMIN")) {
                loginPage.logout();
                loginPage.loginAsUser("ADMIN");
            }
            studyListPage.waitForStudyListToLoadTheData();
            studyListPage.searchForPatientName(patientName);
            studyListPage.revertPrelimSignedReport(testContext.get("RichPatientFirstName"),
                    testContext.get("RichPatientLastName"));
            studyListPage.waitForDataToLoad();
            studyListPage.clickAdvancedFilter();
            studyListPage.searchForPatientName(patientName);
            studyListPage.applyModalityFilter("SR");
            studyListPage.navigateToTab("Study Tree");
            studyTreePage.expandStudiesForPatient(patientName);
            studyTreePage.expandCollapseStudy("expand", 1);
            studyTreePage.selectSeriesByIndex(0);
            studyTreePage.deleteSelectedSeries();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertBPDCalculatingMethod")
    public void revertBPDCalculatingMethod() {
        try {
            reportViewerPage.closeReportViewer();
            reportViewerPage.dontSaveReport();
            dicomViewerPage.openMenu("Window");
            dicomViewerPage.selectMenuOption("Preferences");
            dicomViewerPage.waitForPreferencesWindow();
            dicomViewerPage.clickOnAnyOptionInPreferencesWindow("OB Settings");
            dicomViewerPage.selectDefaultCalculatingMethod();
            dicomViewerPage.applyPreferences();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteDSRFile")
    public void deleteDSRFile() {
        try {
            Common.deleteExistingFile(Constants.DSRFile);
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteCreatedPatientWithAssignedPhysician")
    public void deleteCreatedPatientWithAssignedPhysician() {
        try {
            loginPage.logout();
            loginPage.loginAsUser("Admin");
            studyListPage.waitForDataToLoad();
            studyListPage.setFilter("Patient ID", testContext.get("PatientId"));
            studyListPage.waitForDataToLoad();
            studyListPage.clickAdvancedFilter();
            studyListPage.navigateToTab("Study Tree");
            studyTreePage.showPatientsWithoutStudy();
            studyTreePage.selectFirstPatient();
            studyTreePage.deleteSelectedPatient();
            studyTreePage.clickOnYesButtonInPopup();
            studyListPage.waitForDataToLoad();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertToDefaultClinicParameterValue")
    public void revertToDefaultClinicParameterValue() {
        try {
            systemSettingsPage.setClinicParameterValue("1");
            systemSettingsPage.saveClinicParameterValue();
            systemSettingsPage.waitForDataToLoad();
            systemSettingsPage.searchClinicParameter("AuditRepositoryManager.logExportLimit");
            systemSettingsPage.clickOnFilteredClinicParameterResultGrid(
                    "com.vidistar.dicom.ejb.rest.AuditRepositoryManager.logExportLimit");
            systemSettingsPage.clearClinicParameterValue();
            systemSettingsPage.saveClinicParameterValue();
            systemSettingsPage.waitForDataToLoad();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertSystemName")
    public void revertSystemName() {
        try {
            studyListPage.navigateToTab("System Settings");
            systemSettingsPage.renameSystemDetails(testContext.get("NewSystemName"), testContext.get("PreviousSystemName"));
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteNewlyAddedSystem")
    public void deleteNewlyAddedSystem() {
        try {
            studyListPage.navigateToTab("System Settings");
            systemSettingsPage.deleteSystem(testContext.get("NewSystem"));
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteStudyCreatedFromOrderList")
    public void deleteStudyCreatedFromOrderList() {
        try {
            orderListPage.navigateToTab("Study Tree");
            studyTreePage.setFilter(testContext.get("FilterName"), testContext.get("FilterValue"));
            studyTreePage.expandFirstPatientOnStudyTree();
            studyTreePage.rightClickOnFirstStudy();
            studyTreePage.selectOptionFromRightClickMenu("Delete Study");
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertFinalReport")
    public void revertFinalReport() {
        try {
            if (dicomViewerPage.isDicomViewerWindowVisible()) {
                dicomViewerPage.closeDicomViewer();
            }
            studyTreePage.navigateToTab("Study List");
            revertSignedReport();
            studyListPage.waitForDataToLoad();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@ExitReportAndViewer")
    public void exitReportAndViewer() {
        try {
            reportViewerPage.closeReportViewer();
            Log.printInfo("Closed the report");
            reportViewerPage.dontSaveReport();
            Log.printInfo("Clicked on dont save button");
            dicomViewerPage.closeDicomViewer();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertAndDeletePrelimAndFinalReport")
    public void revertAndDeletePrelimAndFinalReport() {
        try {
            String patientName = testContext.get("RichPatientLastName") + "^" + testContext.get("RichPatientFirstName");
            studyTreePage.navigateToTab("Study List");
            revertSignedReport();
            studyListPage.waitForDataToLoad();
            studyListPage.clickAdvancedFilter();
            studyListPage.setFilter("Modality", "SR");
            studyListPage.navigateToTab("Study Tree");
            studyTreePage.expandStudiesForPatient(patientName);
            studyTreePage.expandStudyByIndex(0);
            Common.sleep(1, TimeUnit.SECONDS);
            studyTreePage.selectSeriesByIndex(0);
            studyTreePage.selectSeriesByIndex(1);
            Common.sleep(1, TimeUnit.SECONDS);
            studyTreePage.deleteSelectedSeries();
            studyTreePage.waitForDataToLoad();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteReportTemplateInstance")
    public void deleteReportTemplateInstance() {
        try {
            exitReportAndViewer();
            loginPage.logout();
            loginPage.loginAsUser("TEMPLATEADMIN");
            studyListPage.waitForStudyListToLoadTheData();
            studyListPage.navigateToTab("System Settings");
            systemSettingsPage.clickOptionFromSettingsPanel(testContext.get("Option"));
            systemSettingsPage.enterTemplateInstanceNameInSearchBox(testContext.get("TemplateInstanceName"));
            systemSettingsPage.clickOnTemplateInstanceFilteredResult();
            systemSettingsPage.removeAllowedStudyType(testContext.get("CodeDesignator"));
            systemSettingsPage.unfoldServiceLocationConfiguration();
            systemSettingsPage.removeIncludedServiceLocation(testContext.get("IncludedServiceLocation"));
            systemSettingsPage.removeExcludedServiceLocation(testContext.get("ExcludedServiceLocation"));
            systemSettingsPage.unfoldUserRoleAssignmentConfiguration();
            systemSettingsPage.unassignTemplateInstanceRole(testContext.get("Role"));
            systemSettingsPage.deleteTemplateInstance();
            systemSettingsPage.clickWarningOkButton();
            systemSettingsPage.waitForDataToLoad();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteDefaultTemplateInstance")
    public void deleteDefaultTemplateInstance() {
        try {
            exitReportAndViewer();
            loginPage.logout();
            loginPage.loginAsUser("TEMPLATEADMIN");
            studyListPage.waitForStudyListToLoadTheData();
            studyListPage.navigateToTab("System Settings");
            systemSettingsPage.clickOptionFromSettingsPanel(testContext.get("Option"));
            systemSettingsPage.enterTemplateInstanceNameInSearchBox(testContext.get("TemplateInstanceName"));
            systemSettingsPage.clickOnTemplateInstanceFilteredResult();
            systemSettingsPage.removeAllowedStudyType(testContext.get("CodeDesignator"));
            systemSettingsPage.unfoldUserRoleAssignmentConfiguration();
            systemSettingsPage.unassignTemplateInstanceRole(testContext.get("Role"));
            systemSettingsPage.deleteTemplateInstance();
            systemSettingsPage.clickWarningOkButton();
            systemSettingsPage.waitForDataToLoad();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteLatestInstance")
    public void deleteLatestInstance() {
        try {
            if (testContext.containsKey("AnnotatedImage")) {
                loginPage.loginAsUser("Admin");
                loginPage.isUserLoggedIn("Admin");
                studyListPage.waitForStudyListToLoadTheData();
                String patientName = testContext.get("RichPatientLastName") + "^" + testContext.get("RichPatientFirstName");
                studyListPage.navigateToTab("Study Tree");
                studyTreePage.searchPatientByName(patientName);
                studyTreePage.waitForDataToLoad();
                studyTreePage.expandStudiesForPatient(patientName);
                studyTreePage.expandStudyByIndex(0);
                Common.sleep(1, TimeUnit.SECONDS);
                studyTreePage.expandCollapseSeries("expand", 1);
                Common.sleep(1, TimeUnit.SECONDS);
                studyTreePage.selectLastInstance();
                studyTreePage.deleteSelectedInstance();
            }
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertAndDeleteLatestSignedFinalReport")
    public void revertAndDeleteLatestSignedFinalReport() {
        try {
            loginPage.loginAsUser("Admin");
            loginPage.isUserLoggedIn("Admin");
            studyListPage.waitForStudyListToLoadTheData();
            studyListPage.searchForPatientName(
                    testContext.get("RichPatientLastName") + "^" + testContext.get("RichPatientFirstName"));
            studyTreePage.waitForDataToLoad();
            studyListPage.revertSignedReport(testContext.get("RichPatientFirstName"),
                    testContext.get("RichPatientLastName"));
            studyListPage.clickAdvancedFilter();
            studyListPage.setFilter("Modality", "SR");
            studyListPage.navigateToTab("Study Tree");
            studyTreePage.expandStudiesForPatient(
                    testContext.get("RichPatientLastName") + "^" + testContext.get("RichPatientFirstName"));
            studyTreePage.expandStudyByIndex(0);
            Common.sleep(1, TimeUnit.SECONDS);
            studyTreePage.selectSeriesByIndex(0);
            Common.sleep(1, TimeUnit.SECONDS);
            studyTreePage.deleteSelectedSeries();
            studyTreePage.waitForDataToLoad();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteBothSeries")
    public void deleteBothSeries() {
        try {
            String patientName = testContext.get("RichPatientLastName") + "^" + testContext.get("RichPatientFirstName");
            studyListPage.waitForDataToLoad();
            studyListPage.clickAdvancedFilter();
            studyListPage.searchForPatientName(patientName);
            studyListPage.applyModalityFilter("SR");
            studyListPage.navigateToTab("Study Tree");
            studyTreePage.expandStudiesForPatient(patientName);
            studyTreePage.expandCollapseStudy("expand", 1);
            studyTreePage.selectSeriesByIndex(0);
            studyTreePage.selectSeriesByIndex(1);
            studyTreePage.deleteSelectedSeries();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeselectAllAndDontSaveReport")
    public void deselectAllAndDontSaveReport() {
        try {
            reportViewerPage.closeReportViewer();
            Log.printInfo("Closed the report");
            reportViewerPage.deselectAllAndDontSaveReport();
            Log.printInfo("Clicked on dont save button");
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteSeriesFromTwoStudies")
    public void deleteSeriesFromTwoStudies() {
        try {
            String patientName = testContext.get("RichPatientLastName") + "^" + testContext.get("RichPatientFirstName");
            reportViewerPage.closeReportViewer();
            reportViewerPage.dontSaveReport();
            dicomViewerPage.closeDicomViewer();
            studyListPage.clickAdvancedFilter();
            studyListPage.setFilter("Modality", "SR");
            studyListPage.waitForDataToLoad();
            studyListPage.navigateToTab("Study Tree");
            studyTreePage.waitForDataToLoad();
            studyTreePage.expandStudiesForPatient(patientName);
            studyTreePage.waitForDataToLoad();
            for (int i = 5; i >= 4; i--) {
                studyTreePage.expandCollapseStudy("expand", i);
                Common.sleep(1, TimeUnit.SECONDS);
                studyTreePage.selectSeriesByIndex(0);
                Common.sleep(1, TimeUnit.SECONDS);
                studyTreePage.deleteSelectedSeries();
                studyTreePage.expandCollapseStudy("collapse", 1);
            }
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteSeriesFromThreeStudies")
    public void deleteStudiesFromThreeSeries() {
        try {
            String patientName = testContext.get("RichPatientLastName") + "^" + testContext.get("RichPatientFirstName");
            reportViewerPage.closeReportViewer();
            reportViewerPage.dontSaveReport();
            dicomViewerPage.closeDicomViewer();
            studyListPage.clickAdvancedFilter();
            studyListPage.setFilter("Modality", "SR");
            studyListPage.waitForDataToLoad();
            studyListPage.navigateToTab("Study Tree");
            studyTreePage.waitForDataToLoad();
            studyTreePage.expandStudiesForPatient(patientName);
            studyTreePage.waitForDataToLoad();
            for (int i = 5; i >= 3; i--) {
                studyTreePage.expandCollapseStudy("expand", i);
                Common.sleep(1, TimeUnit.SECONDS);
                studyTreePage.selectSeriesByIndex(0);
                Common.sleep(1, TimeUnit.SECONDS);
                studyTreePage.deleteSelectedSeries();
                studyTreePage.expandCollapseStudy("collapse", 1);
            }
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertFinalReportAndDeleteSeriesForAllSignedReports")
    public void deleteSeriesForBothStudies() {
        try {
            reportViewerPage.closeReportViewer();
            reportViewerPage.dontSaveReport();
            dicomViewerPage.closeDicomViewer();
            String patientName = testContext.get("RichPatientLastName") + "^" + testContext.get("RichPatientFirstName");
            studyListPage.waitForDataToLoad();
            studyListPage.clickAdvancedFilter();
            studyListPage.searchForPatientName(patientName);
            studyListPage.applyModalityFilter("SR");
            for (int studyNumber = 3; studyNumber >= 1; studyNumber--) {
                studyListPage.revertSignedReportForStudyNumber(testContext.get("RichPatientFirstName"),
                        testContext.get("RichPatientLastName"), studyNumber);
                studyListPage.waitForDataToLoad();
                Common.sleep(1, TimeUnit.SECONDS);
            }
            studyListPage.navigateToTab("Study Tree");
            studyTreePage.expandStudiesForPatient(patientName);
            for (int study = 3; study >= 1; study--) {
                try {
                    studyTreePage.expandCollapseStudy("expand", study);
                    if (studyTreePage.getSeriesCountForExpandedStudy() > 1) {
                        studyTreePage.selectSeriesByIndex(0);
                        studyTreePage.deleteSelectedSeries();
                    }
                    studyTreePage.expandCollapseStudy("collapse", 1);
                } catch (Exception e) {
                    Log.printError("No series found");
                }
            }
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertFinalReportAndDeleteSeriesForLastStudy")
    public void revertFinalReportAndDeleteSeriesForLastStudy() {
        try {
            reportViewerPage.closeReportViewer();
            reportViewerPage.dontSaveReport();
            dicomViewerPage.closeDicomViewer();
            if (!loginPage.isUserLoggedIn("Admin")) {
                loginPage.logout();
                loginPage.loginAsUser("Admin");
            }
            studyListPage.waitForDataToLoad();
            String patientName = testContext.get("RichPatientLastName") + "^" + testContext.get("RichPatientFirstName");
            studyListPage.clickAdvancedFilter();
            studyListPage.searchForPatientName(patientName);
            studyListPage.applyModalityFilter("SR");

            studyListPage.revertSignedReportForStudyNumber(testContext.get("RichPatientFirstName"),
                    testContext.get("RichPatientLastName"), 3);
            studyListPage.waitForDataToLoad();
            Common.sleep(1, TimeUnit.SECONDS);
            studyListPage.revertSignedReportForStudyNumber(testContext.get("RichPatientFirstName"),
                    testContext.get("RichPatientLastName"), 2);
            studyListPage.waitForDataToLoad();
            Common.sleep(1, TimeUnit.SECONDS);
            studyListPage.navigateToTab("Study Tree");
            studyTreePage.expandStudiesForPatient(patientName);
            for (int studyNumber = 3; studyNumber >= 2; studyNumber--) {
                try {
                    studyTreePage.expandCollapseStudy("expand", studyNumber);
                    if (studyTreePage.getSeriesCountForExpandedStudy() > 1) {
                        studyTreePage.selectSeriesByIndex(0);
                        studyTreePage.deleteSelectedSeries();
                    }
                    studyTreePage.expandCollapseStudy("collapse", 1);
                    Common.sleep(1, TimeUnit.SECONDS);
                } catch (Exception e) {
                    Log.printError("No series found");
                }
            }
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteLastSignedPrelim")
    public void deleteLastSignedPrelim() {
        try {
            String patientName = testContext.get("RichPatientLastName") + "^" + testContext.get("RichPatientFirstName");
            if (reportViewerPage.isReportWindowVisible()) {
                reportViewerPage.closeReportViewer();
                Log.printInfo("Closed the report");
                reportViewerPage.dontSaveReport();
                Log.printInfo("Clicked on dont save button");
            }
            dicomViewerPage.getDicomViewerWindow();
            dicomViewerPage.closeDicomViewer();
            studyListPage.clickAdvancedFilter();
            studyListPage.setFilter("Modality", "SR");
            studyListPage.waitForDataToLoad();
            studyListPage.navigateToTab("Study Tree");
            studyTreePage.waitForDataToLoad();
            studyTreePage.expandStudiesForPatient(patientName);
            studyTreePage.expandStudyByIndex(0);
            studyTreePage.waitForDataToLoad();
            studyTreePage.selectSeriesByIndex(0);
            studyTreePage.waitForDataToLoad();
            studyTreePage.deleteSelectedSeries();
            studyTreePage.waitForDataToLoad();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertReportTitleAndDeleteLastSignedReport")
    public void revertReportTitleAndDeleteLastSignedReport() {
        try {
            String patientName = testContext.get("RichPatientLastName") + "^" + testContext.get("RichPatientFirstName");
            reportViewerPage.closeReportViewer();
            reportViewerPage.dontSaveReport();
            Common.sleep(3, TimeUnit.SECONDS);
            dicomViewerPage.getDicomViewerWindow();
            dicomViewerPage.selectTabOnDicomViewer("Template Administration");
            dicomViewerPage.selectConfigurationInstanceFromList("com.vidistar.angio.final");
            dicomViewerPage.selectConfigurationInstanceParameterFromList("REPORT_TITLE");
            dicomViewerPage.clearTextFromTemplateAdministrationField("REPORT_TITLE");
            reportViewerPage.clickSaveReport();
            dicomViewerPage.closeDicomViewer();
            studyListPage.revertSignedReport(testContext.get("RichPatientFirstName"),
                    testContext.get("RichPatientLastName"));
            studyListPage.waitForDataToLoad();
            studyListPage.clickAdvancedFilter();
            studyListPage.setFilter("Modality", "SR");
            studyListPage.waitForDataToLoad();
            studyListPage.navigateToTab("Study Tree");
            studyTreePage.waitForDataToLoad();
            studyTreePage.expandStudiesForPatient(patientName);
            studyTreePage.expandStudyByIndex(0);
            Common.sleep(1, TimeUnit.SECONDS);
            studyTreePage.selectSeriesByIndex(1);
            Common.sleep(1, TimeUnit.SECONDS);
            studyTreePage.deleteSelectedSeries();
            studyTreePage.waitForDataToLoad();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertReportTitle")
    public void revertReportTitle() {
        try {
            reportViewerPage.closeReportViewer();
            reportViewerPage.dontSaveReport();
            Common.sleep(3, TimeUnit.SECONDS);
            dicomViewerPage.getDicomViewerWindow();
            dicomViewerPage.selectTabOnDicomViewer("Template Administration");
            dicomViewerPage.selectConfigurationInstanceFromList("com.vidistar.angio.final");
            dicomViewerPage.selectConfigurationInstanceParameterFromList("REPORT_TITLE");
            dicomViewerPage.clearTextFromTemplateAdministrationField("REPORT_TITLE");
            reportViewerPage.clickSaveReport();
            dicomViewerPage.closeDicomViewer();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertServiceLocationProperty")
    public void revertServiceLocationProperty() {
        try {
            dicomViewerPage.closeDicomViewer();
            studyListPage.navigateToTab("System Settings");
            systemSettingsPage.editServiceLocation();
            systemSettingsPage.selectDeselectServiceLocationProperty(testContext.get("ServiceLocationProperty"), "deselect");
            systemSettingsPage.clickSaveButton();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RemoveIcon")
    public void removeIcon() {
        try {
            if (reportViewerPage.isReportWindowVisible()) {
                reportViewerPage.closeReportViewer();
                Log.printInfo("Closed the report");
                reportViewerPage.dontSaveReport();
                Log.printInfo("Clicked on dont save button");
            }
            dicomViewerPage.getDicomViewerWindow();
            dicomViewerPage.rightClickOnViewport(testContext.get("viewportIndex"));
            dicomViewerPage.selectOptionFromViewerRightClickMenu(testContext.get("MenuOption"));
            dicomViewerPage.openMenu("File");
            dicomViewerPage.selectMenuOption("Exit");
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RemoveReportIconAndDeleteLastSignedPrelimReport")
    public void removeReportIconAndDeleteLastSignedPrelimReport() {
        try {
            String patientName = testContext.get("RichPatientLastName") + "^" + testContext.get("RichPatientFirstName");
            removeIcon();
            studyListPage.clickAdvancedFilter();
            studyListPage.setFilter("Modality", "SR");
            studyListPage.waitForDataToLoad();
            studyListPage.navigateToTab("Study Tree");
            studyTreePage.waitForDataToLoad();
            studyTreePage.expandStudiesForPatient(patientName);
            studyTreePage.expandStudyByIndex(0);
            studyTreePage.waitForDataToLoad();
            studyTreePage.selectSeriesByIndex(0);
            studyTreePage.waitForDataToLoad();
            studyTreePage.deleteSelectedSeries();
            studyTreePage.waitForDataToLoad();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RemoveReportIconAndDeleteTemplate")
    public void removeReportIconAndDeleteTemplate() {
        try {
            removeIcon();
            loginPage.logout();
            loginPage.loginAsUser("TEMPLATEADMIN");
            studyListPage.waitForDataToLoad();
            studyListPage.navigateToTab("System Settings");
            systemSettingsPage.clickOptionFromSettingsPanel("Template administration");
            systemSettingsPage.clickWarningOkButton();
            systemSettingsPage.clickReportDefaults();
            systemSettingsPage.deleteTemplate(testContext.get("TemplateName"));
            systemSettingsPage.waitForDataToLoad();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@RevertFinalReportStatusAndRevertClinicParameter")
    public void revertFinalReportStatusAndRevertClinicParameter() {
        try {
            revertFinalReport();
            studyListPage.navigateToTab("System Settings");
            systemSettingsPage.clickOptionFromSettingsPanel(testContext.get("Option"));
            systemSettingsPage.searchClinicParameter(testContext.get("clinicParameterName"));
            systemSettingsPage.clickOnFilteredClinicParameterResultGrid(testContext.get("filteredClinicParameterName"));
            systemSettingsPage.setClinicParameterValue("true");
            systemSettingsPage.saveClinicParameterValue();
            systemSettingsPage.waitForDataToLoad();
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After("@DeleteSeriesForLastStudy")
    public void deleteSeriesFromLastStudy() {
        try {
            reportViewerPage.closeReportViewer();
            reportViewerPage.dontSaveReport();
            dicomViewerPage.closeDicomViewer();
            if (!loginPage.isUserLoggedIn("Admin")) {
                loginPage.logout();
                loginPage.loginAsUser("Admin");
            }
            studyListPage.waitForDataToLoad();
            String patientName = testContext.get("RichPatientLastName") + "^" + testContext.get("RichPatientFirstName");
            studyListPage.clickAdvancedFilter();
            studyListPage.searchForPatientName(patientName);
            studyListPage.applyModalityFilter("SR");
            studyListPage.navigateToTab("Study Tree");
            studyTreePage.expandStudiesForPatient(patientName);
            studyTreePage.expandCollapseStudy("expand", 3);
            if (studyTreePage.getSeriesCountForExpandedStudy() > 1) {
                studyTreePage.selectSeriesByIndex(0);
                studyTreePage.deleteSelectedSeries();
            }
        } catch (Exception e) {
            logScreenshot(scenario);
        }
    }

    @After(order = 10001)
    public void logFailureScreenshot(Scenario scenario) {
        if (!scenario.getStatus().equals(Status.PASSED)) {
            scenario.embed(loginPage.getScreenshot(), "image/png", "Failure screenshot");
            Log.printInfo("screenshot is captured");
        }
    }

    @SneakyThrows
    private void logScreenshot(Scenario scenario) {
        scenario.embed(loginPage.getScreenshot(), "image/png", "Failure screenshot");
        Log.printInfo("screenshot is captured");
        throw new Exception();
    }
}
