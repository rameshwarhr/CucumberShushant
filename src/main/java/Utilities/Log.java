package Utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.cucumber.java.Scenario;

public class Log {

	private static final Logger logger = LogManager.getLogger(Logger.class.getName());
	static Scenario scenario;

	public Log(Scenario scenario) {
		Log.scenario = scenario;
	}

	public static void printInfo(String message) {
		logger.info(message);
	}

	public static void printError(String message) {
		logger.error(message);
	}

	public static void logScenarioInfo(String message) {
		scenario.write(message);
	}

	public static void logScreenshot(byte[] screenshot, String name) {
		scenario.embed(screenshot, "image/png", name);
	}
}
