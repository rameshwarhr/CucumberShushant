package ReportEditors;

import static Utilities.Constants.ReportScreens;
import static Utilities.Constants.USER_WORK_DIR;

import java.util.LinkedHashMap;
import java.util.Map;

import Actions.RobotActions;
import Actions.SikuliActions;

public class FetalTTEReportStandardEditor implements ReportEditor {

	SikuliActions sikuliActions;
	RobotActions robotActions;
	String sectionName;
	String tabName;
	String ReportElements;

	public FetalTTEReportStandardEditor(String sectionName, String tabName) {
		sikuliActions = new SikuliActions();
		robotActions = new RobotActions();
		this.sectionName = sectionName;
		this.tabName = tabName;
		ReportElements = USER_WORK_DIR + ReportScreens + "FetalTTEReport(Standard)\\" + sectionName + "\\" + tabName
				+ "\\";
	}

	@Override
	public Map<String, String> fillReport(Map<String, String> reportEntries) {
		Map<String, String> filledReportEntries = new LinkedHashMap<>();
		switch (tabName.toUpperCase()) {
		case "PATIENT INFORMATION":
			filledReportEntries = fillPatientInformation(reportEntries);
			break;
		}
		return filledReportEntries;
	}

	private Map<String, String> fillPatientInformation(Map<String, String> reportEntries) {
		for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
			String key = entry.getKey().replace(" / ", " ");
			System.out.println("Key: " + key);
			String value = entry.getValue();
			System.out.println("Value: " + value);
			switch (entry.getKey().split(":")[0].toUpperCase()) {
			case "EDD TYPE":
				sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
				break;
			}
		}
		return reportEntries;
	}

	@Override
	public Map<String, String> getTransformedReportValues(Map<String, String> reportEntries) {
		return reportEntries;
	}
}
