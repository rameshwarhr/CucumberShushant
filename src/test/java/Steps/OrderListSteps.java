package Steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import Context.TestContext;
import Pages.OrderListPage;
import Utilities.Log;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class OrderListSteps {

	TestContext testContext;
	OrderListPage orderListPage;

	public OrderListSteps(TestContext testContext) {
		this.testContext = testContext;
		orderListPage = new OrderListPage(testContext.getDriver());
	}

	@Then("order list should be loaded")
	public void verifyIfOrderListIsDisplayed() {
		assertTrue("Order list is not displayed", orderListPage.isOrderListDisplayed());
	}

	@When("I select {string} in {string} filter under order list tab")
	@When("I enter {string} in {string} filter under order list tab")
	public void inputFilter(String filterValue, String filterName) {
		orderListPage.setFilter(filterValue, filterName);
		testContext.set("FilterName", filterName);
		testContext.set("FilterValue", filterValue);
	}

	@Then("order list should display orders only which are having {string} as {string}")
	public void filterResult(String filterName, String filterValue) {
		orderListPage.waitForDataToLoad();
		assertTrue("Order list mismatch", orderListPage.isOrderListFiltered(filterName, filterValue));
	}

	@When("I clear the {string} filter under order list tab")
	public void clearFilters(String filterName) {
		orderListPage.clearFilter(filterName);
		orderListPage.waitForDataToLoad();
	}

	@When("I right click on the patient from order list")
	@When("right click on the patient from order list")
	public void rightClickOnPatient() {
		testContext.set("StudyDetailsList", orderListPage.getStudyDetailsFromOrderListPage());
		orderListPage.rightClickOnFirstPatient();
	}

	@When("I select {string} option from order list right click menu")
	public void selectOptionFromOrderListRightClickMenu(String option) {
		orderListPage.selectOptionFromOrderListRightClickMenu(option);
	}

	@Then("create new study modal should be displayed")
	public void verifyCreateStudyModalIsPresent() {
		assertTrue("create new study modal not present", orderListPage.isCreateStudyModalPresent());
	}

	@Then("study details should be correct on order list page")
	public void verifyStudyDetailsOnModal() {
		assertTrue("Study details verification failed",
				orderListPage.verifyStudyDetailsOnModal(testContext.get("StudyDetailsList")));
	}

	@When("I hover on the first patient under order list tab")
	@When("hover on the first patient under order list tab")
	public void hoverOnFirstPatient() {
		testContext.set("StudyDetailsList", orderListPage.getStudyDetailsFromOrderListPage());
		orderListPage.hoverOnFirstPatient();
	}

	@When("I click {string} icon from the floating toolbar")
	public void clickFloatingToolbarIcon(String icon) {
		orderListPage.clickFloatingToolbarIcon(icon);
	}

	@Then("information pop up should be displayed")
	public void verifyInformationPopupIsDisplayed() {
		assertTrue("information pop up is not displayed", orderListPage.verifyInformationPopupIsDisplayed());
		orderListPage.clickOkButtonOnInformationPopup();
	}

	@When("I enter study details in create study window")
	public void enterStudyDetails(DataTable table) {
		List<Map<String, String>> studyDetails = table.asMaps(String.class, String.class);
		orderListPage.enterStudyDetails(studyDetails);
		Log.printInfo("Study details are entered in respective fields");
	}

	@Then("Has Study checkbox should be {string} again")
	@Then("Has Study checkbox should be {string}")
	public void verifyHasStudyCheckbox(String visibility) {
		assertEquals("Has Study verification failed", visibility.equalsIgnoreCase("CHECKED"), orderListPage.verifyHasStudyCheckboxIsChecked());
	}

	@When("I click on {string} tab on the create study modal")
	public void clickTabOnCreateStudyModal(String tabName) {
		orderListPage.clickTabOnCreateStudyModal(tabName);
	}

	@When("I navigate to {string} tab from order list")
	public void navigateToTab(String tabName) {
		orderListPage.navigateToTab(tabName);
		Log.printInfo("user navigated to " + tabName);
	}
}