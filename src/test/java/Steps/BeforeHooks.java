package Steps;

import Context.TestContext;
import Pages.LoginPage;
import PdfReportGenerator.ReportGenerator;
import Utilities.*;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BeforeHooks {

    TestContext testContext;
    Launcher launcher;
    Property property;

    public static boolean isBeforeHookExecuted = false;

    public BeforeHooks(TestContext testContext) {
        this.testContext = testContext;
        property = new Property();
    }

    @Before(order = 0)
    public void launchWiniumDriver(Scenario scenario) {
        new Log(scenario);
        if (!isBeforeHookExecuted) {
            ReportGenerator.dryRun = false;
            Common.killTask("Winium.Desktop.Driver");
            Property.clearReportPropertyFile();
            isBeforeHookExecuted = true;
            Common.sleep(3, TimeUnit.SECONDS);
        }
    }

    @Before(order = 1)
    public void setScenario(Scenario scenario) {
        testContext.setScenario(scenario);
    }

    @Before("@ClearCacheForRichViewer")
    public void clearRichViewerCache() {
        Common.deleteRichViewerCache();
    }

    @Before("@DeleteExistingRichViewerFile")
    public void deleteExistingRichViewerFile() {
        Common.deleteExistingFile(Constants.downloadedViewerFile);
    }

}
