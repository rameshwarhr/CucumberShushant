package ReportEditors;

import java.util.Map;
import java.util.Random;

import Utilities.Log;

public interface ReportEditor {

	Map<String,String> fillReport(Map<String, String> reportEntries);

	Map<String, String> getTransformedReportValues(Map<String, String> reportEntries);

	default String getRandomValue(String entryValues) {
		String[] values = entryValues.split(";");
		Random random = new Random();
		int index = random.nextInt(values.length);
		Log.printInfo("Random value: " + values[index]);
		return values[index];
	}
}
