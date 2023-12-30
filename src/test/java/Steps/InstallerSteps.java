package Steps;

import static org.junit.Assert.assertTrue;

import Context.TestContext;
import Pages.InstallerPage;
import Utilities.Installer;
import Utilities.Log;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class InstallerSteps {

    Installer installer;
    InstallerPage installerPage;
    TestContext context;

    public InstallerSteps(TestContext context) {
        this.context = context;
        installer = new Installer();
    }

    @When("I install the rich viewer")
    public void installRichViewer() {
        installer.launchRichViewerExe();
        Log.printInfo("Rich Viewer is installed");
        context.setDriver(installer.getDriver());
    }

    @Then("rich viewer should get installed")
    public void verifyIfRichIsInstalled() {
        installerPage = new InstallerPage(context.getDriver());
        assertTrue("Viewer installation has failed", installerPage.isInstallationCompleted());
        Log.printInfo("Viewer installation successfully completed");
        installerPage.dismissInstallationPopup();
    }

    @Given("vidistar url is launched")
    public void vidistar_url_is_launched() {
        installer.initializeWebDriver();
        installer.launchApplicationUrl();
        Log.printInfo("vidistar application is launched");
        context.setDriver(installer.getDriver());
    }

    @When("vidistar application is relaunched through desktop icon")
    public void relaunchVidistarApplication() {
        installer.relaunchApplication();
        Log.printInfo("vidistar application is launched");
        //context.setDriver(installer.getDriver());
    }
}
