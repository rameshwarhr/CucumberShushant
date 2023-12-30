package Pages;

import static Utilities.Constants.AccreditationPageORFile;
import static Utilities.Constants.USER_WORK_DIR;

import org.openqa.selenium.By;

import Actions.WebActions;
import ObjectRepository.AccreditationPageOR;
import Utilities.Driver;
import Utilities.YmlReader;

public class AccreditationPage extends Page {

	Driver driver;
	WebActions webActions;

	public AccreditationPage(Driver driver) {
		super(driver);
		this.driver = driver;
		webActions = new WebActions(driver.getWebDriver());
	}

	private static final AccreditationPageOR accreditationPageOR = YmlReader
			.getObjectRepository(USER_WORK_DIR + AccreditationPageORFile, AccreditationPageOR.class);

	private final By documentViewerPanel = getByLocator(accreditationPageOR.documentViewerPanel_Xpath,
			LocatorIdentifier.Xpath);
	private final By documentTreeSection = getByLocator(accreditationPageOR.documentTreeSection_Xpath,
			LocatorIdentifier.Xpath);
	private final By accreditationSettings = getByLocator(accreditationPageOR.accreditationSettings_Xpath,
			LocatorIdentifier.Xpath);
	private final By documentList = getByLocator(accreditationPageOR.documentList_Xpath, LocatorIdentifier.Xpath);
	private final By documentNameOnViewPanel = getByLocator(accreditationPageOR.documentNameOnViewPanel_Xpath,
			LocatorIdentifier.Xpath);

	public boolean isDocumentTreeDisplayedOnTheLeftSide() {
		return webActions.getElementLocation(documentViewerPanel).x > webActions
				.getElementLocation(documentTreeSection).x;
	}

	public boolean isDocumentTreeDisplayedUnderSettings() {
		return webActions.getElementLocation(documentTreeSection).y > webActions
				.getElementLocation(accreditationSettings).y;
	}

	public boolean isFirstDocumentNameDisplayedAtTheTopOfDocumentViewingPanel() {
		String firstDocumentName = webActions.getElementTextList(documentList).get(0);
		String documentViewingPanelHeader = webActions.getText(documentNameOnViewPanel).split(":")[1].trim();
		return firstDocumentName.equals(documentViewingPanelHeader);
	}
}
