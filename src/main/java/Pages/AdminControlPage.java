package Pages;

import static Utilities.Constants.AdminControlPageORFile;
import static Utilities.Constants.USER_WORK_DIR;

import Actions.WebActions;
import ObjectRepository.AdminControlPageOR;
import Utilities.Driver;
import Utilities.YmlReader;

public class AdminControlPage extends Page {

	WebActions webActions;

	public AdminControlPage(Driver driver) {
		super(driver);
		webActions = new WebActions(driver.getWebDriver());
	}

	private static final AdminControlPageOR adminControlPageOR = YmlReader
			.getObjectRepository(USER_WORK_DIR + AdminControlPageORFile, AdminControlPageOR.class);

	public void switchToObjectFilterViewFrame() {
		webActions.switchToFrame(adminControlPageOR.objectFilterViewFrame_Name);
	}

	public boolean isObjectFilterViewDisplayed() {
		return webActions
				.isVisible(getByLocator(adminControlPageOR.adminControlPageHeader_Xpath, LocatorIdentifier.Xpath), 20);
	}

	public void setSessionTimeout(int sessionTimeoutInSeconds) {
		webActions.click(getByLocator(adminControlPageOR.sonovisionArchiveFilterLink_Xpath, LocatorIdentifier.Xpath));
		webActions.switchToDefaultContentFromFrame();
		webActions.switchToFrame("ObjectNodeView");
		webActions.waitForElementClickability(
				getByLocator(adminControlPageOR.globalConfigurationServiceLink_Xpath, LocatorIdentifier.Xpath), 15);
		webActions
				.click(getByLocator(adminControlPageOR.globalConfigurationServiceLink_Xpath, LocatorIdentifier.Xpath));
		webActions.setText(getByLocator(adminControlPageOR.sessionTimeoutTextbox_Xpath, LocatorIdentifier.Xpath),
				String.valueOf(sessionTimeoutInSeconds));
		webActions.click(getByLocator(adminControlPageOR.applyChangesButton_Xpath, LocatorIdentifier.Xpath));
	}
}
