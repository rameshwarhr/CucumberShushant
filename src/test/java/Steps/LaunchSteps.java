package Steps;

import Context.TestContext;
import Utilities.Launcher;
import Utilities.Log;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.Status;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class LaunchSteps {

    Launcher launcher;
    TestContext context;

    public LaunchSteps(TestContext context) {
        this.context = context;
        launcher = new Launcher(context.getDriver());
    }

    @Given("vidistar application is launched")
    public void vidistar_application_is_launched() {
        launcher.initializeWebDriver();
        launcher.launchApplication();
        Log.printInfo("vidistar application is launched");
        context.setDriver(launcher.getDriver());
    }

    @When("I open vidistar admin control access")
    public void vidistarAdminControlIsOpened() {
        launcher.launchAdminControl();
        Log.printInfo("Vidistar admin control access is launched");
    }

    @SuppressWarnings("deprecation")


    @After(order = 0)
    public void quitApplication() {
        launcher.quitApplication();
    }
}
