package Pages;

import static Utilities.Constants.PatientServiceLogsPageORFile;
import static Utilities.Constants.USER_WORK_DIR;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;

import Actions.WebActions;
import ObjectRepository.PatientServiceLogsPageOR;
import Utilities.Common;
import Utilities.Driver;
import Utilities.YmlReader;

public class PatientServiceLogsPage extends Page {

	WebActions webActions;

	public PatientServiceLogsPage(Driver driver) {
		super(driver);
		this.driver = driver;
		webActions = new WebActions(driver.getWebDriver());
	}

	private static final PatientServiceLogsPageOR patientServiceLogsPageOR = YmlReader
			.getObjectRepository(USER_WORK_DIR + PatientServiceLogsPageORFile, PatientServiceLogsPageOR.class);

	public void rightClickOnLog(int logNumber) {
		List<WebElement> patientServiceLogs = webActions
				.getElements(getByLocator(patientServiceLogsPageOR.patientServiceLogs_Xpath, LocatorIdentifier.Xpath));
		webActions.rightClick(patientServiceLogs.get(logNumber));
	}

	public void selectMenuOption(String rightClickMenuOption) {
		webActions.click(getByLocator(patientServiceLogsPageOR.rightClickMenuOption_Xpath, LocatorIdentifier.Xpath,
				rightClickMenuOption));
	}

	public void clickOnSystemDetailsDropdown() {
		webActions.click(
				getByLocator(patientServiceLogsPageOR.systemNameAndNumberDropdown_Xpath, LocatorIdentifier.Xpath));
	}

	public List<String> areClinicSystemDetailsAvailableUnderPatientServiceLogs() {
		return webActions.getElementTextList(
				getByLocator(patientServiceLogsPageOR.systemNamesAndNumbersListItem_Xpath, LocatorIdentifier.Xpath));
	}

	public void selectPatientLogInfoValue(String newPatientLogInfoItem, String value) {
		webActions.click(getByLocator(patientServiceLogsPageOR.newPatientServiceLogMenuItemDropdown_Xpath,
				LocatorIdentifier.Xpath, newPatientLogInfoItem));
		waitForDataToLoad();
		webActions.scrollToElement(getByLocator(patientServiceLogsPageOR.newPatientServiceLogDropdownListItem_Xpath,
				LocatorIdentifier.Xpath, value));
		webActions.click(getByLocator(patientServiceLogsPageOR.newPatientServiceLogDropdownListItem_Xpath,
				LocatorIdentifier.Xpath, value));
	}


	public void clickOnAddDeleteLogButton(String buttonName) {
		webActions.click(
				getByLocator(patientServiceLogsPageOR.addDeleteLogButton_Xpath, LocatorIdentifier.Xpath, buttonName));
	}
	
	public void cancelPatientServiceLogWindow() {
		webActions.click(
				getByLocator(patientServiceLogsPageOR.cancelPatientServiceLogButton_Xpath, LocatorIdentifier.Xpath));
		webActions.waitForElementToDisappear(getByLocator(patientServiceLogsPageOR.patientServiceLogWindow_Xpath, LocatorIdentifier.Xpath), 10);
		Common.sleep(1, TimeUnit.SECONDS);
	}
}
