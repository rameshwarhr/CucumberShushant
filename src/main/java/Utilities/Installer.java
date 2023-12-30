package Utilities;

import java.io.IOException;

public class Installer {

    Driver driver;
    WinDriver winDriver;

    public Installer() {
        driver = new Driver();
    }

    public void initializeWebDriver() {
        try {
            driver.getDriver();
        } catch (InterruptedException | IOException ignored) {
        }
    }

    public Driver getDriver() {
        return driver;
    }

    public void launchApplicationUrl() {
        winDriver = new WinDriver();
        String env = System.getProperty("env") == null ? "DEMO" : System.getProperty("env");
        switch (env.toUpperCase()) {
            case "CI":
                driver.getWebDriver().get(Constants.ciVidistarApplicationUrl);
                break;
            default:
                driver.getWebDriver().get(Constants.vidistarApplicationUrl);
        }
        driver.winDriver = winDriver.getCurrentDriver();
    }

    public void launchRichViewerExe() {
        winDriver = new WinDriver();
        winDriver.installRichViewer(9999);
        driver.winDriver = winDriver.getCurrentDriver();
    }

    public void relaunchApplication() {
        try {
            new Launcher().relaunchApplication();
            driver.winDriver = winDriver.getCurrentDriver();
        } catch (Exception e) {
            Log.printError("Error");
        }
    }
}
