package Pages;

import static Utilities.Constants.MediaPageORFile;
import static Utilities.Constants.USER_WORK_DIR;

import java.util.concurrent.TimeUnit;

import Actions.WebActions;
import ObjectRepository.MediaPageOR;
import Utilities.Common;
import Utilities.Driver;
import Utilities.YmlReader;

public class MediaPage extends Page {
	Driver driver;
	WebActions webActions;
	StudyListPage studyListPage;

	public MediaPage(Driver driver) {
		super(driver);
		this.driver = driver;
		webActions = new WebActions(driver.getWebDriver());
		studyListPage = new StudyListPage(driver);
	}

	private static final MediaPageOR mediaPageOR = YmlReader.getObjectRepository(USER_WORK_DIR + MediaPageORFile,
			MediaPageOR.class);

	public void clickDeleteMediaButton() {
		webActions.click(getByLocator(mediaPageOR.deleteMediaButton_Xpath, LocatorIdentifier.Xpath));
	}

	public void searchForPatientName(String patientName) {
		webActions.setText(getByLocator(mediaPageOR.patientNameTextbox_Xpath, LocatorIdentifier.Xpath), patientName);
	}

	public boolean isCDBurned(String patientName, int retryAttempts) {
		int retryCount = 1;
		patientName = patientName.replace("^", " ");
		while (!webActions.getColumnText(getByLocator(mediaPageOR.mediaTable_Xpath, LocatorIdentifier.Xpath), 4)
				.contains(patientName) && retryCount < retryAttempts) {
			Common.sleep(1, TimeUnit.SECONDS);
			webActions.click(getByLocator(mediaPageOR.searchButton_Xpath, LocatorIdentifier.Xpath));
			waitForDataToLoad();
			retryCount++;
		}
		while (!webActions.getElementAttributeValue(
				getByLocator(mediaPageOR.cdBurnStatusIcon_Xpath, LocatorIdentifier.Xpath), "src").contains("completed")
				&& retryCount < retryAttempts) {
			Common.sleep(4, TimeUnit.SECONDS);
			webActions.click(getByLocator(mediaPageOR.searchButton_Xpath, LocatorIdentifier.Xpath));
			waitForDataToLoad();
			retryCount++;
		}
		return webActions.getElementAttributeValue(
				getByLocator(mediaPageOR.cdBurnStatusIcon_Xpath, LocatorIdentifier.Xpath), "src").contains("completed");
	}

	public void setDate(String value) {
		webActions.setText(getByLocator(mediaPageOR.startDateInput_Xpath, LocatorIdentifier.Xpath), value);
	}

	public boolean isMediaCreationStatusExpired() {
		return webActions.getElementAttributeValue(
				getByLocator(mediaPageOR.cdBurnStatusIcon_Xpath, LocatorIdentifier.Xpath), "src").contains("expired");
	}

	public boolean isMediaCreationStatusDisplayedOnToolTip(String mediaCreationStatus) {
		webActions.mouseHover(getByLocator(mediaPageOR.cdBurnStatusIcon_Xpath, LocatorIdentifier.Xpath));
		return webActions.isVisible(
				getByLocator(mediaPageOR.toolTipMessage_Xpath, LocatorIdentifier.Xpath, mediaCreationStatus));
	}

	public void clickDownloadISOImageIcon() {
		webActions.mouseHover(getByLocator(mediaPageOR.cdBurnStatusIcon_Xpath, LocatorIdentifier.Xpath));
		webActions.click(getByLocator(mediaPageOR.downloadIsoImageIcon_Xpath, LocatorIdentifier.Xpath));
	}

	public boolean isErrorMessageDisplayedCorrectInPopup(String errorMessage) {
		return webActions
				.isVisible(getByLocator(mediaPageOR.popupErrorMessage_Xpath, LocatorIdentifier.Xpath, errorMessage));
	}

	public boolean isOptionVisibleUnderMediaCreationStatusDropdown(String option) {
		return webActions.isVisible(
				getByLocator(mediaPageOR.mediaCreationStatusDropdownOption_Xpath, LocatorIdentifier.Xpath, option));
	}

	public void clickMediaCreationStatusDropdownButton() {
		webActions.click(getByLocator(mediaPageOR.mediaCreationStatusDropdownButton_Xpath, LocatorIdentifier.Xpath));
	}

	public void expandBurntMediaOnMediaPage() {
		webActions.click(getByLocator(mediaPageOR.burnedMediaPatientExpandIcon_Xpath, LocatorIdentifier.Xpath));
		waitForDataToLoad();
	}

	public void expandPatientOnMediaPage() {
		webActions.click(getByLocator(mediaPageOR.patientExpandIcon_Xpath, LocatorIdentifier.Xpath));
		waitForDataToLoad();
	}
}
