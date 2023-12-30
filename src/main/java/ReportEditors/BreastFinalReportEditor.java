package ReportEditors;

import Actions.RobotActions;
import Actions.SikuliActions;
import Utilities.Common;
import Utilities.Log;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static Utilities.Constants.ReportScreens;
import static Utilities.Constants.USER_WORK_DIR;

public class BreastFinalReportEditor implements ReportEditor {

    SikuliActions sikuliActions;
    RobotActions robotActions;
    ReportEditorHelper reportEditorHelper;
    String reportName;
    String sectionName;
    String tabName;
    String ReportElements;

    public BreastFinalReportEditor(String reportName, String sectionName, String tabName) {
        sikuliActions = new SikuliActions();
        robotActions = new RobotActions();
        this.reportName = reportName;
        this.sectionName = sectionName;
        this.tabName = tabName;
        ReportElements = USER_WORK_DIR + ReportScreens + "Breast - Final Report\\" + sectionName + "\\" + tabName
                + "\\";
    }

    @Override
    public Map<String, String> fillReport(Map<String, String> reportEntries) {
        reportEditorHelper = new ReportEditorHelper(reportName, sectionName, tabName);
        Map<String, String> filledReportEntries = new LinkedHashMap<>();
        switch (tabName.toUpperCase()) {
            case "PATIENT PROFILE DATA":
                filledReportEntries = fillPatientInformation(reportEntries);
                break;
            case "FAVORITES":
            case "CONCLUSIONS":
            case "COPY TO":
            case "FREE TEXT":
                filledReportEntries = fillFavoritesTab(reportEntries);
                break;
            case "CODES":
                filledReportEntries = fillCodesTab(reportEntries);
                break;
            case "LESION LOCATION":
                filledReportEntries = fillLesionLocationTab(reportEntries);
                break;
            case "LESION CHARACTERISTIC":
                filledReportEntries = fillLesionCharacteristicTab(reportEntries);
                break;
            case "PHYSICAL EXAM":
                filledReportEntries = fillPhysicalExamTab(reportEntries);
                break;
            case "INTERVENTION":
                filledReportEntries = fillInterventionTab(reportEntries);
                break;
            case "BIOPSY APPROACH":
                filledReportEntries = fillBiopsyApproachTab(reportEntries);
                break;
            case "DEVICE UTILIZED":
                filledReportEntries = fillDeviceUtilizedTab(reportEntries);
                break;
            case "POST BIOPSY IMAGE":
                filledReportEntries = fillPostBiopsyImageTab(reportEntries);
                break;
            case "FOLLOW-UP PLAN":
                filledReportEntries = fillFollowUpTab(reportEntries);
                break;
            case "RIGHT BREAST":
            case "LEFT BREAST":
                filledReportEntries = fillUSResultsTab(reportEntries);
                break;
            case "RIGHT":
            case "LEFT":
                filledReportEntries = fillUSFindingsTab(reportEntries);
                break;
            case "PHONE LOG":
                filledReportEntries = fillPhoneLogTab(reportEntries);
                break;
        }
        return filledReportEntries;
    }

    private Map<String, String> fillPatientInformation(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            System.out.println("Key: " + key);
            String value = entry.getValue();
            System.out.println("Value: " + value);
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "PATIENT TYPE":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
                case "CARDIAC RHYTHM":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    entry.setValue(reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
                    break;
                case "HISTORY":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    reportEditorHelper.selectAllCheckboxesInARegion(regionBounds.get(key), value, "\\.");
                    break;
                case "PATIENT NAME":
                case "EXAMINATION DATE":
                case "AGE":
                case "SEX":
                case "DOB":
                case "EQUIPMENT":
                case "CHART":
                case "REFERRING PHYSICIAN":
                    entry.setValue(reportEditorHelper.getFieldValue(key));
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillFavoritesTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            System.out.println("Key: " + key);
            String value = entry.getValue();
            System.out.println("Value: " + value);
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "FAVORITES":
                case "CONCLUSIONS":
                case "COPY TO":
                case "STUDY DESCRIPTION":
                case "FREE TEXT":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillCodesTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey();
            System.out.println("Key: " + key);
            String value = entry.getValue();
            System.out.println("Value: " + value);
            switch (key.toUpperCase()) {
                case "DIAGNOSIS":
                case "PROCEDURE":
                    reportEditorHelper.addCode(key, value);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillLesionLocationTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "LATERALITY":
                case "QUADRANT LOCATION":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    entry.setValue(reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
                    break;
                case "DISTANCE FROM NIPPLE":
                case "DISTANCE FROM SKIN":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "CLOCKFACE OR REGION":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillLesionCharacteristicTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            System.out.println("Key: " + key);
            String value = entry.getValue();
            System.out.println("Value: " + value);
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "ECHOGENICITY":
                case "LESION MARGIN":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    value = getRandomValue(entry.getValue());
                    if (value.equalsIgnoreCase("Other")) {
                        sikuliActions.typeTextInField(regionBounds.get(key), ReportElements + value + "_Textbox.PNG",
                                reportEntries.get(value));
                        robotActions.enter();
                        entry.setValue(reportEntries.get(value));
                        reportEntries.put(value + " " + key, "Ignore");
                    } else {
                        entry.setValue(reportEditorHelper.selectRandomRadioButton(value));
                        reportEntries.put("Other " + key, "Ignore");
                    }
                    break;
                case "ORIENTATION":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    entry.setValue(reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
                    break;
                case "HEIGHT":
                case "WIDTH":
                case "DEPTH":
                case "OTHER":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "POST ACOUSTIC SHADOWING":
                case "POST ACOUSTIC ENHANCEMENT":
                case "EDGE SHADOWING":
                case "COMPRESSIBLE":
                case "INTERNAL FLOW PRESENT":
                case "NO INTERNAL FLOW":
                case "NO PERIPHERAL FLOW":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "FREMITUS EFFECT":
                case "EFFECT ON SURROUNDING PARENCHYMA":
                case "FREMITUS":
                case "BLOOD FLOW":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillPhysicalExamTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace("/", " ");
            reportEditorHelper.selectCheckbox(key);
        }
        return reportEntries;
    }

    private Map<String, String> fillInterventionTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey();
            reportEditorHelper.selectCheckbox(key);
            Common.sleep(1, TimeUnit.SECONDS);
        }
        return reportEntries;
    }

    private Map<String, String> fillBiopsyApproachTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "INSTRUMENT APPROACH":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    entry.setValue(reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
                    break;
                case "NUMBER OF CORES":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillDeviceUtilizedTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "INTACT":
                case "FINE NEEDLE ASPIRATION":
                case "SENORX":
                case "ATEC":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "SPRING LOADED BIOPSYS":
                case "OTHER":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillPostBiopsyImageTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String value = entry.getValue();
            value = getRandomValue(value);
            if (value.equalsIgnoreCase("Other")) {
                sikuliActions.typeTextInField(ReportElements + value + "_Textbox.PNG", reportEntries.get(value));
                entry.setValue(reportEntries.get(value));
                reportEntries.put(value, "Ignore");
            } else {
                sikuliActions.selectRadio(ReportElements + value + "_Radio.PNG");
                entry.setValue(reportEditorHelper.selectRandomRadioButton(value));
                reportEntries.put("Other", "Ignore");
            }
            break;
        }
        return reportEntries;
    }

    private Map<String, String> fillFollowUpTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            System.out.println("Key: " + key);
            String value = entry.getValue();
            System.out.println("Value: " + value);
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "CALL PATHOLOGY RESULTS TO PATIENT TOMORROW":
                case "MALIGNANT PATHOLOGY":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "BENIGN PATHOLOGY TYPE":
                case "BENIGN PATHOLOGY DURATION IN":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
                case "BENIGN PATHOLOGY DURATION":
                case "OTHER":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
            }
        }
        reportEntries.put("Benign pathology",
                String.format("Benign pathology, follow up here with %s in %s %s(s)",
                        reportEntries.get("Benign pathology type"), reportEntries.get("Benign pathology duration"),
                        reportEntries.get("Benign pathology duration in")));
        reportEntries.put("Benign pathology type", "Ignore");
        reportEntries.put("Benign pathology duration", "Ignore");
        reportEntries.put("Benign pathology duration in", "Ignore");
        return reportEntries;
    }

    private Map<String, String> fillUSResultsTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().split(":")[1].trim();
            String value = entry.getValue();
            String region = entry.getKey().split(":")[0].trim();
            switch (key.toUpperCase()) {
                case "ECHOGENICITY":
                case "VASCULAR FLOW":
                case "BIOPSY":
                case "CHANGE":
                    if (!regionBounds.containsKey(region))
                        regionBounds.putIfAbsent(region,
                                sikuliActions.getExactRegionBounds(ReportElements + region + "_Region.PNG"));
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdownInARegion(regionBounds.get(region), key, value));
                    break;
                case "LENGTH":
                case "AP":
                case "TRANS":
                case "DEPTH":
                case "DISTANCE":
                    if (!regionBounds.containsKey(region))
                        regionBounds.putIfAbsent(region,
                                sikuliActions.getExactRegionBounds(ReportElements + region + "_Region.PNG"));
                    reportEditorHelper.enterValueInTextboxFromARegion(regionBounds.get(region), key, value);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillUSFindingsTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "LOBE MEASURES":
                    String[] measurements = value.split("X");
                    for (int index = 0; index < measurements.length; index++) {
                        if (index == 0) {
                            reportEditorHelper.enterValueInTextbox(key, measurements[index].trim());
                            continue;
                        }
                        robotActions.pressTabKey();
                        Common.sleep(500, TimeUnit.MILLISECONDS);
                        robotActions.pressTabKey();
                        Common.sleep(500, TimeUnit.MILLISECONDS);
                        sikuliActions.type(measurements[index].trim());
                    }
                    break;
                case "NUMBER OF NODULES":
                case "MID SECTION OF LARGEST NODULE":
                case "LARGEST NODULE MEASURING":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillPhoneLogTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "CALLED REPORT TO":
                case "NUMBER CALLED":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "INSTRUCTIONS":
                    reportEditorHelper.enterTextAndSelectCheckbox(key, value);
                    break;
                case "TIME CALL":
                    entry.setValue(reportEditorHelper.getFieldValue(key));
                    break;
            }
        }
        return reportEntries;
    }

    @Override
    public Map<String, String> getTransformedReportValues(Map<String, String> reportEntries) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            switch (entry.getKey().toUpperCase()) {
                case "PATIENT NAME":
                    String patientNameValue = entry.getValue().replace("^", ", ");
                    entry.setValue(patientNameValue);
                    break;
                case "EXAMINATION DATE":
                case "DOB":
                    try {
                        Date date = formatter.parse(entry.getValue());
                        SimpleDateFormat reportDateFormatter = new SimpleDateFormat("MMM dd, yyyy");
                        String reportDate = reportDateFormatter.format(date);
                        entry.setValue(reportDate);
                    } catch (ParseException e) {
                        Log.printInfo("Error while parsing date");
                    }
                    break;
                case "SEX":
                    entry.setValue(" " + entry.getValue() + " ");
                    break;
                case "CALLED":
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    SimpleDateFormat reportTimeFormat = new SimpleDateFormat("h:mm a");
                    String callTime = reportEntries.get("Time call");
                    try {
                        Date time = timeFormat.parse(callTime);
                        callTime = reportTimeFormat.format(time);
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    entry.setValue(String.format("%s at %s and provided a preliminary report to %s",
                            reportEntries.get("Number called"), callTime, reportEntries.get("Called report to")));
                    reportEntries.put("Number called", "Ignore");
                    reportEntries.put("Time call", "Ignore");
                    reportEntries.put("Called report to", "Ignore");
                    break;
            }
        }
        return reportEntries;
    }
}
