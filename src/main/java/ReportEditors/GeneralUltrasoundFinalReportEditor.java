package ReportEditors;

import static Utilities.Constants.ReportScreens;
import static Utilities.Constants.USER_WORK_DIR;

import java.awt.Rectangle;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import Actions.RobotActions;
import Actions.SikuliActions;
import Utilities.Common;
import Utilities.Log;

public class GeneralUltrasoundFinalReportEditor implements ReportEditor {

    SikuliActions sikuliActions;
    RobotActions robotActions;
    String sectionName;
    String tabName;
    String ReportElements;

    public GeneralUltrasoundFinalReportEditor(String sectionName, String tabName) {
        sikuliActions = new SikuliActions();
        robotActions = new RobotActions();
        this.sectionName = sectionName;
        this.tabName = tabName;
        ReportElements = USER_WORK_DIR + ReportScreens + "General - Final Report\\" + sectionName + "\\" + tabName
                + "\\";
    }

    @Override
    public Map<String, String> fillReport(Map<String, String> reportEntries) {
        Map<String, String> filledReportEntries = new LinkedHashMap<>();
        switch (tabName.toUpperCase()) {
            case "PATIENT PROFILE DATA":
                filledReportEntries = fillPatientInformation(reportEntries);
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
            case "PHONE LOG":
                filledReportEntries = fillPhoneLogTab(reportEntries);
                break;
            case "RIGHT":
            case "LEFT":
            case "FAVORITES":
            case "CONCLUSIONS":
            case "COPY TO":
            case "FREE TEXT":
                filledReportEntries = fillUSFindingsTab(reportEntries);
                break;
            case "CODES":
                filledReportEntries = fillCodesTab(reportEntries);
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
                    value = getRandomValue(value);
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue(value);
                    break;
                case "CARDIAC RHYTHM":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectRadioFromRegion(ReportElements + value + "_Radio.PNG", regionBounds.get(key));
                    entry.setValue(value);
                    break;
                case "HISTORY":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    for (String valueComponent : value.split("\\.")) {
                        sikuliActions.selectCheckBox(ReportElements + valueComponent.trim() + "_Checkbox.PNG",
                                regionBounds.get(key));
                    }
                    break;
                case "PATIENT NAME":
                case "EXAMINATION DATE":
                case "AGE":
                case "SEX":
                case "DOB":
                case "EQUIPMENT":
                case "CHART":
                case "REFERRING PHYSICIAN":
                    entry.setValue(getFieldValue(key));
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillLesionLocationTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            System.out.println("Key: " + key);
            String value = entry.getValue();
            System.out.println("Value: " + value);
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "LATERALITY":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    value = getRandomValue(entry.getValue());
                    if (value.equalsIgnoreCase("Free text")) {
                        sikuliActions.typeTextInField(ReportElements + value + "_Textbox.PNG", reportEntries.get(value));
                        sikuliActions.selectRadioFromRegion(ReportElements + value + "_Radio.PNG", regionBounds.get(key));
                        entry.setValue(reportEntries.get(value));
                        reportEntries.put(value, "Ignore");
                    } else {
                        sikuliActions.selectRadioFromRegion(ReportElements + value + "_Radio.PNG", regionBounds.get(key));
                        entry.setValue(value);
                    }
                    break;
                case "LOCATION":
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    robotActions.enter();
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
                        sikuliActions.selectRadioFromRegion(ReportElements + value + "_Radio.PNG", regionBounds.get(key));
                        reportEntries.put("Other " + key, "Ignore");
                        entry.setValue(value);
                    }
                    break;
                case "ORIENTATION":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectRadioFromRegion(ReportElements + value + "_Radio.PNG", regionBounds.get(key));
                    entry.setValue(value);
                    break;
                case "HEIGHT":
                case "WIDTH":
                case "DEPTH":
                case "OTHER":
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    robotActions.enter();
                    break;
                case "POST ACOUSTIC SHADOWING":
                case "POST ACOUSTIC ENHANCEMENT":
                case "EDGE SHADOWING":
                case "COMPRESSIBLE":
                case "INTERNAL FLOW PRESENT":
                case "NO INTERNAL FLOW":
                case "NO PERIPHERAL FLOW":
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    break;
                case "FREMITUS EFFECT":
                case "EFFECT ON SURROUNDING PARENCHYMA":
                case "FREMITUS":
                case "BLOOD FLOW":
                    value = getRandomValue(value);
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue(value);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillPhysicalExamTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            System.out.println("Key: " + key);
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "CANNOT PALPATE":
                case "DOMINANT MASS, IRREGULAR":
                case "DOMINANT MASS, SMOOTH":
                case "THICKENED":
                case "SCAR TISSUE":
                case "SKIN RETRACTION":
                case "RED SKIN/PEAU D'ORANGE":
                case "AXILLARY LYMPH NODE, ENLARGED":
                case "AXILLARY LYMPH NODE, CANNOT PALPATE":
                    sikuliActions.selectCheckBox(ReportElements + key.replace("/", " ") + "_Checkbox.PNG");
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillInterventionTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            System.out.println("Key: " + key);
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "INFORMED CONSENT OBTAINED":
                case "SKIN PREPPED WITH ALCOHOL":
                case "ANESTHETIC":
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillBiopsyApproachTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            System.out.println("Key: " + key);
            String value = entry.getValue();
            System.out.println("Value: " + value);
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "INSTRUMENT APPROACH":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectRadioFromRegion(ReportElements + value + "_Radio.PNG", regionBounds.get(key));
                    entry.setValue(value);
                    break;
                case "NUMBER OF CORES":
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    robotActions.enter();
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillDeviceUtilizedTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            System.out.println("Key: " + key);
            String value = entry.getValue();
            System.out.println("Value: " + value);
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "INTACT":
                case "FINE NEEDLE ASPIRATION":
                case "SENORX":
                case "ATEC":
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    break;
                case "SPRING LOADED BIOPSYS":
                case "OTHER":
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    robotActions.enter();
                    break;
            }
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
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    break;
                case "BENIGN PATHOLOGY TYPE":
                case "BENIGN PATHOLOGY DURATION IN":
                    value = getRandomValue(value);
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue(value);
                    break;
                case "BENIGN PATHOLOGY DURATION":
                case "OTHER":
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    robotActions.enter();
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

    private Map<String, String> fillPostBiopsyImageTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            System.out.println("Key: " + key);
            String value = entry.getValue();
            System.out.println("Value: " + value);
            value = getRandomValue(value);
            if (value.equalsIgnoreCase("Other")) {
                sikuliActions.typeTextInField(ReportElements + value + "_Radio.PNG", reportEntries.get(value));
                entry.setValue(reportEntries.get(value));
                reportEntries.put(value, "Ignore");
            } else {
                sikuliActions.selectRadio(ReportElements + value + "_Radio.PNG");
                entry.setValue(value);
                reportEntries.put("Other", "Ignore");
            }
            break;
        }
        return reportEntries;
    }

    private Map<String, String> fillUSFindingsTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            System.out.println("Key: " + key);
            String value = entry.getValue();
            System.out.println("Value: " + value);
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "IMAGING FINDINGS":
                case "FAVORITES":
                case "CONCLUSIONS":
                case "COPY TO":
                case "STUDY DESCRIPTION":
                case "FREE TEXT":
                case "FINDINGS-GENERAL":
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillPhoneLogTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            System.out.println("Key: " + key);
            String value = entry.getValue();
            System.out.println("Value: " + value);
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "CALLED REPORT TO":
                case "NUMBER CALLED":
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    robotActions.enter();
                    break;
                case "INSTRUCTIONS":
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    robotActions.enter();
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    break;
                case "TIME CALL":
                    entry.setValue(getFieldValue(key));
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
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    sikuliActions.waitForElementToExist(ReportElements + "Loading.PNG", 5);
                    sikuliActions.waitForElementInvisibility(ReportElements + "Loading.PNG", 30);
                    robotActions.pressTabKey();
                    Common.sleep(1, TimeUnit.SECONDS);
                    robotActions.pressTabKey();
                    Common.sleep(1, TimeUnit.SECONDS);
                    robotActions.pressDownArrowKey();
                    sikuliActions.click(ReportElements + "Add" + "_Button.PNG");
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
                case "SPRING LOADED BIOPSYS":
                    entry.setValue(String.format("Spring loaded biopsys (BARD) %s gauge", entry.getValue()));
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

    private String getFieldValue(String fieldName) {
        sikuliActions.scrollToField(ReportElements + fieldName + "_Textbox.PNG");
        String fieldValue = sikuliActions.getTextFromExactField(ReportElements + fieldName + "_Textbox.PNG");
        sikuliActions.moveMouseByOffset(10, 10);
        return fieldValue;
    }

}
