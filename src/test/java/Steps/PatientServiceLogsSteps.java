package Steps;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import Context.TestContext;
import Pages.PatientServiceLogsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PatientServiceLogsSteps {

	TestContext testContext;
	PatientServiceLogsPage patientServiceLogsPage;

	public PatientServiceLogsSteps(TestContext testContext) {
		this.testContext = testContext;
		patientServiceLogsPage = new PatientServiceLogsPage(testContext.getDriver());
	}

	@When("I right click on log number {int}")
	public void rightClickOnLog(int logNumber) {
		patientServiceLogsPage.rightClickOnLog(logNumber - 1);
	}

	@When("click on {string} menu option")
	public void clickOnMenuOption(String rightClickMenuOption) {
		patientServiceLogsPage.selectMenuOption(rightClickMenuOption);
	}

	@Then("all the system details for respective clinic should be available under Patient Service Logs")
	public void areClinicSystemDetailsAvailableUnderPatientServiceLogs() {
		patientServiceLogsPage.clickOnSystemDetailsDropdown();
		List<String> patientServiceLogsSystemDetails = patientServiceLogsPage
				.areClinicSystemDetailsAvailableUnderPatientServiceLogs();
		List<String> clinicSystemDetails = testContext.get("SystemDetailsList");
		assertTrue("System details are missing", patientServiceLogsSystemDetails.containsAll(clinicSystemDetails));
		patientServiceLogsPage.cancelPatientServiceLogWindow();
	}

	@When("select {string} as {string}")
	public void select_as(String newPatientLogInfoItem, String value) {
		patientServiceLogsPage.selectPatientLogInfoValue(newPatientLogInfoItem, value);
	}

	@When("I close the patient service log window")
	public void closePatientServiceLog() {
		patientServiceLogsPage.cancelPatientServiceLogWindow();
	}

	@When("I click on {string} button icon")
	public void addDeleteLog(String buttonName) {
		patientServiceLogsPage.clickOnAddDeleteLogButton(buttonName);
	}

	@Then("the {string} system should be available in the dropdown list under Patient Service Logs")
	public void isRenamedSystemAvailable(String systemType) {
		patientServiceLogsPage.clickOnSystemDetailsDropdown();
		List<String> patientServiceLogsSystemDetails = patientServiceLogsPage
				.areClinicSystemDetailsAvailableUnderPatientServiceLogs();
		switch (systemType.toUpperCase()) {
		case "RENAMED":
			assertTrue("System details are not updated after the changes",
					patientServiceLogsSystemDetails.contains(testContext.get("NewSystemName")));
			assertFalse("System details are not updated after the changes",
					patientServiceLogsSystemDetails.contains(testContext.get("PreviousSystemName")));
			break;

		case "NEWLY CREATED":
			assertTrue("Renamed system is missing under dropdown list",
					patientServiceLogsSystemDetails.contains(testContext.get("NewSystem")));
			break;
		}
		patientServiceLogsPage.cancelPatientServiceLogWindow();
	}
}
