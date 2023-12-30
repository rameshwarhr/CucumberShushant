package Pages;

import static Utilities.Constants.InstallerPageORFile;
import static Utilities.Constants.USER_WORK_DIR;

import java.util.concurrent.TimeUnit;

import Actions.RobotActions;
import Actions.WindowsActions;
import ObjectRepository.InstallerPageOR;
import Utilities.Common;
import Utilities.Driver;
import Utilities.YmlReader;

public class InstallerPage extends Page {

    WindowsActions winActions;
    RobotActions robotActions;

    public InstallerPage(Driver driver) {
        super(driver);
        winActions = new WindowsActions(driver.getWinDriver());
        robotActions = new RobotActions();
    }

    private static final InstallerPageOR installerPageOR = YmlReader.getObjectRepository(USER_WORK_DIR + InstallerPageORFile,
            InstallerPageOR.class);

    public boolean isInstallationCompleted() {
        try {
            winActions.waitForWindowElementToBeVisible(
                    getByLocator(installerPageOR.vidistarTaskbarIcon_Xpath, LocatorIdentifier.Xpath), 60);
            winActions.click(getByLocator(installerPageOR.vidistarTaskbarIcon_Xpath, LocatorIdentifier.Xpath));
            return winActions
                    .isVisible(getByLocator(installerPageOR.viewerInstallationDialogBox_Name, LocatorIdentifier.Name));
        } catch (Exception e) {
            return winActions
                    .isVisible(getByLocator(installerPageOR.viewerInstallationDialogBox_Name, LocatorIdentifier.Name));
        }
    }

    public void dismissInstallationPopup() {
        winActions.click(getByLocator(installerPageOR.viewerInstallationDialogBox_Name, LocatorIdentifier.Name));
        Common.sleep(5, TimeUnit.SECONDS);
        robotActions.enter();
    }
}
