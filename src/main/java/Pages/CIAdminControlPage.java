package Pages;

import static Utilities.Constants.CIAdminControlPageORFile;
import static Utilities.Constants.USER_WORK_DIR;

import Actions.WebActions;
import ObjectRepository.CIAdminControlPageOR;
import Utilities.Driver;
import Utilities.YmlReader;

public class CIAdminControlPage extends Page {

	WebActions webActions;

	public CIAdminControlPage(Driver driver) {
		super(driver);
		webActions = new WebActions(driver.getWebDriver());
	}

	private static final CIAdminControlPageOR ciAdminControlPageOR = YmlReader
			.getObjectRepository(USER_WORK_DIR + CIAdminControlPageORFile, CIAdminControlPageOR.class);

	public boolean isObjectFilterViewDisplayed() {
		return webActions.isVisible(
				getByLocator(ciAdminControlPageOR.adminControlPageHeader_Xpath, LocatorIdentifier.Xpath), 60);
	}

	public void setSessionTimeout(int sessionTimeoutInSeconds) {
		webActions.click(getByLocator(ciAdminControlPageOR.sonovisionArchiveFilterLink_Xpath, LocatorIdentifier.Xpath));
		webActions.waitForElementClickability(
				getByLocator(ciAdminControlPageOR.globalConfigurationServiceLink_Xpath, LocatorIdentifier.Xpath), 15);
		webActions.click(
				getByLocator(ciAdminControlPageOR.globalConfigurationServiceLink_Xpath, LocatorIdentifier.Xpath));
		webActions.click(getByLocator(ciAdminControlPageOR.sessionTimeoutLabel_Xpath, LocatorIdentifier.Xpath));
		webActions.setText(getByLocator(ciAdminControlPageOR.sessionTimeoutTextbox_Xpath, LocatorIdentifier.Xpath),
				String.valueOf(sessionTimeoutInSeconds));
		webActions.click(getByLocator(ciAdminControlPageOR.updateButton_Xpath, LocatorIdentifier.Xpath));
	}

	public void waitForAdminControlPageToLoad() {
		webActions.waitForElementPresence(getByLocator(ciAdminControlPageOR.loadingIcon_Xpath, LocatorIdentifier.Xpath),
				10);
		webActions.waitForElementToDisappear(
				getByLocator(ciAdminControlPageOR.loadingIcon_Xpath, LocatorIdentifier.Xpath), 300);
		webActions.waitForElementPresence(
				getByLocator(ciAdminControlPageOR.adminControlPageHeader_Xpath, LocatorIdentifier.Xpath), 20);
	}
}
