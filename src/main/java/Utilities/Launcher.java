package Utilities;

import Actions.RobotActions;
import Actions.SikuliActions;
import Actions.WebActions;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static Utilities.Common.sleep;

public class Launcher {

    Driver driver;
    WinDriver winDriver;
    WebActions webActions;

    public Launcher(Driver driver) {
        if (driver == null)
            this.driver = new Driver();
        else {
            this.driver = driver;
        }
    }

    public Launcher() {
        this.driver = new Driver();
    }

    public void initializeWebDriver() {
        try {
            driver.getDriver();
        } catch (InterruptedException | IOException e) { // TODO Auto-generated catch block e.printStackTrace();
        }
    }

    public void launchApplication() {
        if (winDriver == null) {
            winDriver = new WinDriver();
        }
        winDriver.setApplicationDriver(9999, true);
        switchToApplicationWindow();
        driver.winDriver = winDriver.getCurrentDriver();
        webActions = new WebActions(driver.getWebDriver());
    }

    public void relaunchApplication() {
        if (winDriver == null) {
            winDriver = new WinDriver();
        }
        winDriver.setApplicationDriver(9999, true);
        switchToApplicationWindowAfterInstall();
        driver.winDriver = winDriver.getCurrentDriver();
        webActions = new WebActions(driver.getWebDriver());
    }

    public void switchToApplicationWindow() {
        WebDriver webDriver = driver.getWebDriver();
        String driverWindow = webDriver.getWindowHandle();
        Set<String> windows = webDriver.getWindowHandles();
        for (String appWindow : windows) {
            if (!(appWindow.equals(driverWindow))) {
                webDriver.switchTo().window(driverWindow).close();
                webDriver.switchTo().window(appWindow);
            }
        }
    }

    public void switchToApplicationWindowAfterInstall() {
        new SikuliActions().closeCurrentChromeTab();
    }

    public void launchAdminControl() {
        RobotActions robotActions = new RobotActions();
        robotActions.openNewTab();
        sleep(2, TimeUnit.SECONDS);
        switchToNewTab();
        switch (System.getProperty("env").toUpperCase()) {
            case "CI":
                driver.getWebDriver().navigate().to("http://10.10.10.32:9995/vidiadm-ctrl/jmx");
                break;
            default:
                driver.getWebDriver().navigate().to("https://demo.vidistar.net:9999/vidiadm-ctrl/");
        }
    }

    public void switchToNewTab() {
        webActions.switchToWindow(Constants.newTabWindowTitle, 20);
    }

    public Driver getDriver() {
        return driver;
    }

    public void quitApplication() {
        driver.getWebDriver().quit();
        Common.killTask("chromedriver");
        Common.killTask("viewerc");
        sleep(2, TimeUnit.SECONDS);
    }
}
