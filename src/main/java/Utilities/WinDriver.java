package Utilities;

import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static Utilities.Constants.*;

public class WinDriver {

    private static final String propertyFile = "Automation.properties";
    private static boolean isWiniumServiceStarted = false;
    private static WiniumDriverService winiumService;
    WiniumDriver winDriver;
    Property property;

    public WinDriver() {
        property = new Property();
    }

    public void launchWiniumDriver(String appPath, int port) {
        String AppDriverPath = USER_WORK_DIR + WiniumDriverPath;
        try {
            Runtime.getRuntime().exec(AppDriverPath + " --port " + port);
            DesktopOptions options = new DesktopOptions();
            options.setApplicationPath(appPath);
            String localHost = property.getPropertyValue(propertyFile, "localHost");
            winDriver = new WiniumDriver(new URL(localHost + port), options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void launchWiniumDriverService(String appPath, int port) {
        try {
            if (!isWiniumServiceStarted) {
                winiumService = new WiniumDriverService.Builder()
                        .usingDriverExecutable(new File(USER_WORK_DIR + WiniumDriverPath))
                        .usingPort(port)
                        .withVerbose(true)
                        .withSilent(false)
                        .withLogFile(new File(USER_WORK_DIR + "\\Logs\\WiniumLogs.txt"))
                        .buildDesktopService();
                winiumService.start();
                isWiniumServiceStarted = true;
                Common.sleep(5, TimeUnit.SECONDS);
            }
            DesktopOptions options = new DesktopOptions();
            options.setApplicationPath(appPath);
            if (winiumService != null) {
                winDriver = new WiniumDriver(winiumService, options);
            }
        } catch (Exception e) {
            Log.printError(e.getMessage());
        }
    }

    public static void stopWiniumService() {
        if (winiumService != null)
            winiumService.stop();
        Common.killTask("Winium.Desktop.Driver");
    }

    public void setApplicationDriver(int port, boolean isWiniumAsService) {
        if (isWiniumAsService) {
            launchWiniumDriverService(getAppPath(), port);
        } else {
            launchWiniumDriver(getAppPath(), port);
        }
        waitForChromeProcess();
        Common.sleep(5, TimeUnit.SECONDS);
    }

    public String getAppPath() {
        String appPath;
        if (System.getProperty("env") == null) {
            appPath = USER_HOME_DIR + property.getPropertyValue(propertyFile, "appName");
        } else {
            switch (System.getProperty("env").toUpperCase()) {
                case "MEDNAX":
                    appPath = USER_HOME_DIR + "\\Desktop\\VidiStar - HHA-MEDNAX-TEST.lnk";
                    break;
                case "CI":
                    appPath = USER_HOME_DIR + "\\Desktop\\VidiStar - wildfly-integration.lnk";
                    break;
                case "TEST":
                    appPath = USER_HOME_DIR + "\\Desktop\\VidiStar - Test_WildFly.lnk";
                    break;
                default:
                    appPath = USER_HOME_DIR + "\\Desktop\\VidiStar - demo-devel.lnk";
            }
        }
        return appPath;
    }

    private void waitForChromeProcess() {
        try {
            String findProcess = "chrome.exe";
            String filenameFilter = "/nh /fi \"Imagename eq " + findProcess + "\"";
            String tasksCmd = System.getenv("windir") + "/system32/tasklist.exe " + filenameFilter;

            Process p = Runtime.getRuntime().exec(tasksCmd);
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            while ((line = input.readLine()) != null) {
                if (!line.contains(findProcess)) {
                    try {
                        //noinspection BusyWait
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WiniumDriver getCurrentDriver() {
        return winDriver;
    }

    public void installRichViewer(int port) {

        try {

            String appPath = USER_HOME_DIR + property.getPropertyValue(propertyFile, "richViewerExeFile");
            String AppDriverPath = USER_WORK_DIR + WiniumDriverPath;
            String localHost = property.getPropertyValue(propertyFile, "localHost");

            Runtime.getRuntime().exec(AppDriverPath + " --port " + port);
            DesktopOptions options = new DesktopOptions();
            options.setApplicationPath(appPath);

            int cycles = 0;
            File downloadedRichViewerFile = new File(appPath);
            while (!downloadedRichViewerFile.exists() && cycles < 180) {
                Common.sleep(1, TimeUnit.SECONDS);
                cycles++;
            }
            try {
                winDriver = new WiniumDriver(new URL(localHost + port), options);
            } catch (IOException e) {
                System.out.println("File does not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
