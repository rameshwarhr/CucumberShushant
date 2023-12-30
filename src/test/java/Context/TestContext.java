package Context;

import Utilities.Driver;
import io.cucumber.java.Scenario;

import java.util.HashMap;
import java.util.Map;

public class TestContext {

	Driver driver;
	Scenario scenario;

	public TestContext() {

	}

	private final Map<String, Object> testContexts = new HashMap<>();

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public Driver getDriver() {
		return this.driver;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String name) {

		return (T) testContexts.get(name);

	}

	public <T> T set(String name, T object) {

		testContexts.put(name, object);
		return object;
	}

	public boolean containsKey(String key) {
		return testContexts.containsKey(key);
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	public Scenario getScenario() {
		return this.scenario;
	}

}
