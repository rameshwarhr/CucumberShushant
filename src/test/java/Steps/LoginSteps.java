package Steps;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import Context.TestContext;
import Pages.LoginPage;
import Utilities.Common;
import Utilities.Constants;
import Utilities.Log;
import Utilities.Property;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

    LoginPage loginPage;
    TestContext testContext;

    static boolean isWebVersionCaptured = false;

    public LoginSteps(TestContext context) {
        this.testContext = context;
        loginPage = new LoginPage(context.getDriver());
    }

    @When("I log in to the application as {string} user")
    @When("log back in the application as {string} user")
    @Given("I have logged in the application as {string} user")
    public void loginAsUser(String userType) {
        if (!isWebVersionCaptured) {
            Property.setReportPropertyValue("Web Version", loginPage.getWebVersion());
            isWebVersionCaptured = true;
        }
        loginPage.loginAsUser(userType);
        if (!userType.contains("INVALID")) {
            Common.sleep(100, TimeUnit.MILLISECONDS);
            loginPage.pressEnterKey();
            assertTrue(loginPage.isUserLoggedIn(userType));
        }
        Log.printInfo(userType + " has logged into the Vidistar application");
        testContext.set("UserType", userType);
    }

    @When("click on login button")
    public void clickOnLoginButton() {
        loginPage.clickLogin();
        Log.printInfo("clicked on login button");
    }

    @When("I log out of the application")
    public void logOut() {
        loginPage.logout();
        Log.printInfo("clicked on logout button");
    }

    @When("log back in the application with new user credentials")
    public void loginAsUser() {
        loginPage.loginAsUser(Constants.NEWUSER);
        Log.printInfo("New user has logged into the Vidistar application");
    }

    @When("accept EULA terms to proceed")
    public void acceptEulaAndProceed() {
        loginPage.acceptEulaTerms();
        Log.printInfo("Accepted EULA terms");
    }

    @Then("I am logged in as user {string}")
    @Then("I am logged in as {string}")
    public void verifyLoggedInUser(String username) {
        assertTrue("user is not logged in", loginPage.isUserLoggedIn(username));
    }

    @Then("The EULA should be presented for confirmation")
    public void isEulaDialogDisplayed() {
        assertTrue("Eula dialog box is not displayed", loginPage.isEulaDialogDisplayed());
    }

    @When("I discard the Eula dialog box")
    public void closeEulaDialogBox() {
        loginPage.closeEulaDialogBox();
        loginPage.waitForLoginPopup();
    }

    @Then("login should fail")
    public void verifyAlertPopupAfterInvalidLogin() {
        assertTrue("Alert Popup Not Present", loginPage.isAlertPopupVisible());
        Common.sleep(2, TimeUnit.SECONDS);
        loginPage.clickOnOKOfAlertPopup();
    }

    @Then("message banner should be visible")
    public void verifyMessageBanner() {
        assertTrue("Message banner not present", loginPage.isMessageBannerVisible());
    }

    @Then("login alert should get displayed")
    public void isLoginAlertBoxDisplayed() {
        if ("CI".equalsIgnoreCase(System.getProperty("env"))) {
            assertTrue(loginPage.isLoginWindowDisplayed());
        } else {
            assertTrue(loginPage.isLoginAlertDisplayed());
        }
    }

    @Then("login screen should appear")
    public void isLoginScreenDisplayed() {
        assertTrue(loginPage.isVidistarLoginPageDisplayed());
    }
}