package Utilities;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedHashMap;

public class YmlReader {

	public static LinkedHashMap<String, String> getReportEntries(String reportEntriesFilePath, String sectionName,
			String tabName) {
		Yaml yaml = new Yaml();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(reportEntriesFilePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> mappings = yaml.load(inputStream);
		return mappings.get(sectionName).get(tabName);
	}

	public static <T> T getObjectRepository(String objectRepositoryFilePath, Class<?> objectRepositoryClass) {
		Yaml yaml = new Yaml(new Constructor(objectRepositoryClass));
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(objectRepositoryFilePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return yaml.load(inputStream);
	}
}
