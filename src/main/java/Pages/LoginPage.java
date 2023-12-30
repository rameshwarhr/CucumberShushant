package Pages;

import Actions.WebActions;
import Actions.WindowsActions;
import ObjectRepository.LoginPageOR;
import TestData.TestData;
import Utilities.Common;
import Utilities.Driver;
import Utilities.Log;
import Utilities.YmlReader;
import org.openqa.selenium.TimeoutException;

import java.util.concurrent.TimeUnit;

import static Utilities.Constants.LoginPageORFile;
import static Utilities.Constants.USER_WORK_DIR;

public class LoginPage extends Page {

    WebActions webActions;
    WindowsActions winActions;

    public LoginPage(Driver driver) {
        super(driver);
        webActions = new WebActions(driver.getWebDriver());
        winActions = new WindowsActions(driver.getWinDriver());
    }

    private static final LoginPageOR loginPageOR = YmlReader.getObjectRepository(USER_WORK_DIR + LoginPageORFile,
            LoginPageOR.class);

    public void login(String username, String password) throws TimeoutException {
        verifyLoginPageExists();
        enterUserName(username);
        enterPassword(password);
        clickLogin();
    }

    public void loginAsUser(String userType) {
        switch (userType.toUpperCase()) {
            case "GLOBAL ADMIN":
                login(TestData.userDetails.globalAdminUsername, TestData.userDetails.globalAdminPassword);
                break;
            case "ADMIN":
                login(TestData.userDetails.adminUsername, TestData.userDetails.adminPassword);
                break;
            case "OPERATOR":
                login(TestData.userDetails.operatorUsername, TestData.userDetails.operatorPassword);
                break;
            case "PHYSICIAN":
                login(TestData.userDetails.physicianUsername, TestData.userDetails.physicianUserPassword);
                break;
            case "NEW USER":
                login(TestData.userDetails.newUserName, TestData.userDetails.newUserPassword);
                break;
            case "INVALIDUSER1":
                login(TestData.userDetails.invalidUsername, TestData.userDetails.adminPassword);
                break;
            case "INVALIDUSER2":
                login(TestData.userDetails.adminUsername, TestData.userDetails.invalidUserPassword);
                break;
            case "PHYSICIAN2":
                login(TestData.userDetails.physician2Username, TestData.userDetails.physicianUser2Password);
                break;
            case "AUDITOR":
                login(TestData.userDetails.auditorUsername, TestData.userDetails.auditorPassword);
                break;
            case "JBOSSADMIN":
            case "JBOSS ADMIN":
                login(TestData.userDetails.jBossAdminUsername, TestData.userDetails.jBossAdminPassword);
                break;
            case "TEMPLATEADMIN":
            case "TEMPLATE ADMIN":
                login(TestData.userDetails.templateAdminUsername, TestData.userDetails.templateAdminPassword);
                break;
            case "VIEWERADMIN":
            case "VIEWER ADMIN":
                login(TestData.userDetails.viewerAdminUsername, TestData.userDetails.viewerAdminPassword);
                break;
            case "FAXLOGAUDITOR":
            case "FAX LOG AUDITOR":
            case "FAXLOG AUDITOR":
                login(TestData.userDetails.faxLogAuditorUsername, TestData.userDetails.faxLogAuditorPassword);
                break;
            case "REPORTFIXER":
            case "REPORT FIXER":
                login(TestData.userDetails.reportFixerUsername, TestData.userDetails.reportFixerPassword);
                break;
            case "TECHNICIAN":
                login(TestData.userDetails.technicianUsername, TestData.userDetails.technicianPassword);
                break;
            case "MIDLEVEL":
                login(TestData.userDetails.midlevelUsername, TestData.userDetails.midlevelPassword);
                break;
            case "WADOUSER":
                login(TestData.userDetails.wadoUsername, TestData.userDetails.wadoPassword);
                break;
        }
    }

    public void enterUserName(String username) {
        webActions.setText(getByLocator(loginPageOR.username_Name, LocatorIdentifier.Name), username);
    }

    public void enterPassword(String password) {
        webActions.setText(getByLocator(loginPageOR.password_Name, LocatorIdentifier.Name), password);
    }

    public void clickLogin() {
        webActions.click(getByLocator(loginPageOR.loginButton_Xpath, LocatorIdentifier.Xpath));
        Log.printInfo("Clicked on login button");
    }

    public void acceptEulaTerms() throws TimeoutException {
        if (webActions.isVisible(getByLocator(loginPageOR.eulaDialog_Xpath, LocatorIdentifier.Xpath))) {
            webActions.click(getByLocator(loginPageOR.eulaCheckbox_Xpath, LocatorIdentifier.Xpath));
            webActions.click(getByLocator(loginPageOR.eulaNextButton_Xpath, LocatorIdentifier.Xpath));
        }
    }

    public boolean isUserLoggedIn(String userType) {
        String actualLoginText = webActions.getText(getByLocator(loginPageOR.loginText_Xpath, LocatorIdentifier.Xpath));
        String username = "";
        switch (userType.toUpperCase()) {
            case "GLOBAL ADMIN":
                username = TestData.userDetails.globalAdminName;
                break;
            case "ADMIN":
                username = TestData.userDetails.adminName;
                break;
            case "OPERATOR":
                username = TestData.userDetails.operatorName;
                break;
            case "PHYSICIAN":
                username = TestData.userDetails.physicianName;
                break;
            case "NEW USER":
                username = TestData.userDetails.newUserName;
                break;
            case "PHYSICIAN2":
                username = TestData.userDetails.physician2Name;
                break;
            case "TECHNICIAN":
                username = TestData.userDetails.technicianName;
                break;
            case "AUDITOR":
                username = TestData.userDetails.auditorName;
                break;
            case "JBOSSADMIN":
            case "JBOSS ADMIN":
                username = TestData.userDetails.jBossAdminName;
                break;
            case "TEMPLATEADMIN":
            case "TEMPLATE ADMIN":
                username = TestData.userDetails.templateAdminName;
                break;
            case "VIEWERADMIN":
            case "VIEWER ADMIN":
                username = TestData.userDetails.viewerAdminName;
                break;
            case "FAXLOGAUDITOR":
            case "FAX LOG AUDITOR":
            case "FAXLOG AUDITOR":
                username = TestData.userDetails.faxLogAuditorName;
                break;
            case "REPORTFIXER":
            case "REPORT FIXER":
                username = TestData.userDetails.reportFixerName;
                break;
            case "MIDLEVEL":
                username = TestData.userDetails.midlevelName;
                break;
            case "WADOUSER":
                username = TestData.userDetails.wadoName;
                break;
        }
        waitForDataToLoad();
        return actualLoginText.contains(username);
    }

    public void logout() throws TimeoutException {
        try {
            webActions.click(getByLocator(loginPageOR.logoutButton_Xpath, LocatorIdentifier.Xpath));
        } catch (Exception e) {
            Log.printInfo("User did not log out");
        }
    }

    private void verifyLoginPageExists() throws TimeoutException {
        webActions.isVisible(getByLocator(loginPageOR.loginPopup_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isEulaDialogDisplayed() {
        return webActions.isVisible(getByLocator(loginPageOR.eulaDialog_Xpath, LocatorIdentifier.Xpath));
    }

    public void closeEulaDialogBox() {
        webActions.click(getByLocator(loginPageOR.eulaCloseButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void waitForLoginPopup() {
        webActions.waitForElementVisibility(getByLocator(loginPageOR.loginPopup_Xpath, LocatorIdentifier.Xpath), 10);
    }

    public boolean isAlertPopupVisible() {
        return webActions.isVisible(getByLocator(loginPageOR.alertPopup_Xpath, LocatorIdentifier.Xpath));
    }

    public void clickOnOKOfAlertPopup() {
        webActions.click(getByLocator(loginPageOR.okAlertPopupButton_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isMessageBannerVisible() {
        return webActions.isVisible(getByLocator(loginPageOR.messageBanner_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isLoginAlertDisplayed() {
        return winActions.isVisible(getByLocator(loginPageOR.loginAlertBox_Name, LocatorIdentifier.Name));
    }

    public boolean isVidistarLoginPageDisplayed() {
        webActions.waitForElementVisibility(
                getByLocator(loginPageOR.vidistarLoginDialogBox_Xpath, LocatorIdentifier.Xpath), 10);
        return webActions.isVisible(getByLocator(loginPageOR.vidistarLoginDialogBox_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isLoginWindowDisplayed() {
        return webActions.isVisible(getByLocator(loginPageOR.loginWindow_Xpath, LocatorIdentifier.Xpath), 10);
    }

    public String getWebVersion() {
        return webActions.getText(getByLocator(loginPageOR.webVersion_Xpath, LocatorIdentifier.Xpath));
    }
}
