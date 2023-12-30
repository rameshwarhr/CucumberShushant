package Steps;

import static org.junit.Assert.assertTrue;

import Context.TestContext;
import Pages.AccreditationPage;
import Utilities.Log;
import io.cucumber.java.en.Then;

public class AccreditationSteps {

	TestContext testContext;
	AccreditationPage accreditationPage;

	public AccreditationSteps(TestContext testContext) {
		this.testContext = testContext;
		accreditationPage = new AccreditationPage(testContext.getDriver());
	}

	@Then("Documents Tree should be displayed on the left side of the screen under Settings")
	public void isDocumentTreeDisplayedOnTheLeftSide() {
		assertTrue("Document Tree is not displayed on The left side of the screen",
				accreditationPage.isDocumentTreeDisplayedOnTheLeftSide());
		Log.printInfo("Document Tree position on the left side successfully verified");
		assertTrue("Document Tree is not displayed under Settings",
				accreditationPage.isDocumentTreeDisplayedUnderSettings());
		Log.printInfo("Document Tree under the Settings successfully verified");
	}

	@Then("first document name under it should be displayed at the top of the document viewing panel")
	public void isFirstDocumentNameDisplayedAtTheTopOfDocumentViewingPanel() {
		assertTrue("First Document name does not match",
				accreditationPage.isFirstDocumentNameDisplayedAtTheTopOfDocumentViewingPanel());
		Log.printInfo("");
	}

}