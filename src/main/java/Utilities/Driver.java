package Utilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.winium.WiniumDriver;

public class Driver {

	public WebDriver webDriver;
	public WiniumDriver winDriver;
	Property property;
	public static final String USER_WORK_DIR = System.getProperty("user.dir");
	public static final String USER_HOME_DIR = System.getProperty("user.home");

	public Driver() {
		property = new Property();
	}

	public WebDriver getDriver() throws InterruptedException, IOException {
		this.initializeDriver();
		return webDriver;
	}

	public WebDriver getWebDriver() {
		return webDriver;
	}

	public WiniumDriver getWinDriver() {
		return winDriver;
	}

	private void initializeDriver() throws IOException {
		// determining which OS these tests are running on

		Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
		String propertyFile = "Automation.properties";
		//String path = USER_WORK_DIR + property.getPropertyValue(propertyFile, "chromeDriverPath");
		// checking to see if chromePath exists
		WebDriverManager.chromedriver().setup();
		String ProfileDir = USER_HOME_DIR + property.getPropertyValue(propertyFile, "chromeProfilePath");
		ChromeOptions options = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("profile.exit_type", "none");
		prefs.put("profile.exited_cleanly", "true");
		prefs.put("autofill.enabled", false);
		prefs.put("autofill.auxiliary_profiles_enabled", false);
		prefs.put("download.prompt_for_download", true);
		options.setExperimentalOption("prefs", prefs);

		options.addArguments("user-data-dir=" + ProfileDir);
		options.addArguments("--start-maximized");
		options.addArguments("--disable-single-click-autofill");
		options.addArguments("--remote-debugging-port=9222");
		try {
			//System.setProperty("webdriver.chrome.driver", path);
			webDriver = new ChromeDriver(options);
			//Common.sleep(2, TimeUnit.SECONDS);
			webDriver.manage().deleteAllCookies();
			webDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		} catch (WebDriverException e) {
			e.printStackTrace();
			this.killDriver();
			Common.sleep(2, TimeUnit.SECONDS);
			webDriver = new ChromeDriver(options);

		}
	}

	private void killDriver() {
		String driverStr = "chromedriver.exe";
		try {
			Runtime rt = Runtime.getRuntime();
			rt.exec("taskkill /f /im " + driverStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
