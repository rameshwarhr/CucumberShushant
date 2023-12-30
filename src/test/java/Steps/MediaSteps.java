package Steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import Context.TestContext;
import Pages.MediaPage;
import Utilities.Common;
import Utilities.Log;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MediaSteps {

	TestContext testContext;
	MediaPage mediaPage;

	public MediaSteps(TestContext testContext) {
		this.testContext = testContext;
		mediaPage = new MediaPage(testContext.getDriver());
	}

	@When("I search patient {string} under media tab")
	public void searchPatient(String patientName) {
		mediaPage.waitForDataToLoad();
		mediaPage.searchForPatientName(patientName);
		mediaPage.waitForDataToLoad();
		testContext.set("patientName", patientName);
		Log.printInfo("Patient search is performed");
	}

	@Then("searched patient should not be present")
	public void patientDoesNotExists() {
		assertEquals("Patient Exists", "No items to show.", mediaPage.getNoItemsToShowMessage());
		Log.printInfo("Patients are not present");
	}

	@Then("the patient should have green check CD icon")
	public void verifyCDIsBurned() {
		assertTrue("CD is not burned completely", mediaPage.isCDBurned(testContext.get("patientName"), 30));
	}

	@When("I enter the current date under media tab")
	public void enterCurrentDate() {
		mediaPage.setDate(Common.getCurrentDate());
	}

	@Then("the the CD icon should be be greyed out")
	public void verifyMediaCreationStatusExpired() {
		assertTrue("Media Creation Status is not expired", mediaPage.isMediaCreationStatusExpired());
	}

	@Then("the tool tip should display the message as {string}")
	public void verifyIsMediaCreationStatusDisplayed(String mediaCreationStatus) {
		assertTrue("Media creation status is not displayed on tool tip",
				mediaPage.isMediaCreationStatusDisplayedOnToolTip(mediaCreationStatus));
	}

	@When("I click on download ISO image icon")
	public void clickDownloadISOImageIcon() {
		mediaPage.clickDownloadISOImageIcon();
	}

	@Then("pop-up should be displayed with error message as {string}")
	public void verifyIsErrorMessageDisplayedCorrectly(String errorMessage) {
		assertTrue("No popup displayed", mediaPage.isErrorMessageDisplayedCorrectInPopup(errorMessage));
	}

	@Then("dropdown should contain {string} option")
	public void verifyOptionIsVisibleUnderMediaCreationStatusDropdown(String option) {
		assertTrue("Dropdown does not contain the required option",
				mediaPage.isOptionVisibleUnderMediaCreationStatusDropdown(option));
	}

	@When("I click on media creation status dropdown")
	public void clickMediaCreationDropdownButton() {
		mediaPage.clickMediaCreationStatusDropdownButton();
	}

	@When("I expand the cd burned patient in media tab")
	public void expandBurnedPatientMediaIcon() {
		mediaPage.expandBurntMediaOnMediaPage();
	}

	@When("I expand the patient to view its studies")
	public void expandPatientOnMediaPage() {
		mediaPage.expandPatientOnMediaPage();
	}
}
