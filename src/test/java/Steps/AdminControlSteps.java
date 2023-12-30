package Steps;

import static org.junit.Assert.assertTrue;

import Context.TestContext;
import Pages.AdminControlPage;
import Pages.CIAdminControlPage;
import Pages.StudyListPage;
import Utilities.Log;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class AdminControlSteps {

	TestContext context;
	AdminControlPage adminControlPage;
	CIAdminControlPage ciAdminControlPage;
	StudyListPage studyListPage;

	public AdminControlSteps(TestContext context) {
		this.context = context;
		adminControlPage = new AdminControlPage(context.getDriver());
		ciAdminControlPage = new CIAdminControlPage(context.getDriver());
		studyListPage = new StudyListPage(context.getDriver());
	}

	@Then("JMX Agent View should open")
	public void verifyJmxAgentViewIsDisplayed() {
		if ("CI".equalsIgnoreCase(System.getProperty("env"))) {
			studyListPage.switchToHawtioWindow();
			ciAdminControlPage.waitForAdminControlPageToLoad();
			assertTrue(ciAdminControlPage.isObjectFilterViewDisplayed());
		} else {
			adminControlPage.switchToObjectFilterViewFrame();
			assertTrue(adminControlPage.isObjectFilterViewDisplayed());
		}
		Log.printInfo("Jmx Agent view is displayed");
	}

	@When("I set the session timeout to {int} seconds")
	public void setSessionTimeout(int sessionTimeoutInSeconds) {
		if ("CI".equalsIgnoreCase(System.getProperty("env"))) {
			ciAdminControlPage.setSessionTimeout(sessionTimeoutInSeconds);
		} else {
			adminControlPage.setSessionTimeout(sessionTimeoutInSeconds);
		}
	}
}
