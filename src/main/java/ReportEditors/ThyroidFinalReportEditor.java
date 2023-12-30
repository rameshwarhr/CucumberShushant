package ReportEditors;

import Actions.RobotActions;
import Actions.SikuliActions;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static Utilities.Constants.ReportScreens;
import static Utilities.Constants.USER_WORK_DIR;

public class ThyroidFinalReportEditor implements ReportEditor {

    SikuliActions sikuliActions;
    RobotActions robotActions;
    String reportName;
    String sectionName;
    String tabName;
    String ReportElements;
    ReportEditorHelper reportEditorHelper;

    public ThyroidFinalReportEditor(String reportName, String sectionName, String tabName) {
        sikuliActions = new SikuliActions();
        robotActions = new RobotActions();
        this.reportName = reportName;
        this.sectionName = sectionName;
        this.tabName = tabName;
        ReportElements = USER_WORK_DIR + ReportScreens + reportName + "\\" + sectionName + "\\" + tabName
                + "\\";
    }

    @Override
    public Map<String, String> fillReport(Map<String, String> reportEntries) {
        reportEditorHelper = new ReportEditorHelper(reportName, sectionName, tabName);
        switch (tabName.toUpperCase()) {
            case "PATIENT PROFILE DATA":
                fillPatientInformation(reportEntries);
                break;
            case "FAVORITES":
            case "CONCLUSIONS":
            case "COPY TO":
            case "FREE TEXT":
            case "STM FAVORITES":
            case "LYMPH NODES FAVORITES":
            case "IMPRESSIONS":
                fillFavoritesTab(reportEntries);
                break;
            case "CODES":
                fillCodesTab(reportEntries);
                break;
            case "PHYSICAL EXAM":
                fillPhysicalExamTab(reportEntries);
                break;
            case "INTERVENTION":
                fillInterventionTab(reportEntries);
                break;
            case "LESION LOCATION":
                fillLesionLocationTab(reportEntries);
                break;
            case "LESION CHARACTERISTIC":
                fillLesionCharacteristicTab(reportEntries);
                break;
            case "BIOPSY APPROACH":
                fillBiopsyApproachTab(reportEntries);
                break;
            case "DEVICE UTILIZED":
                fillDeviceUtilizedTab(reportEntries);
                break;
            case "POST BIOPSY IMAGE":
                fillPostBiopsyImageTab(reportEntries);
                break;
            case "FOLLOW-UP PLAN":
                fillFollowUpTab(reportEntries);
                break;
            case "ISTHMUS":
                fillIsthmusTab(reportEntries);
                break;
            case "RIGHT LOBE":
            case "LEFT LOBE":
                fillLobeTab(reportEntries);
                break;
            case "SOFT TISSUE MASS":
                fillSoftTissueMassTab(reportEntries);
                break;
            case "LYMPH NODES":
                fillLymphNodesTab(reportEntries);
                break;
            case "PARATHYROID":
                fillParathyroidTab(reportEntries);
                break;
            case "GENERAL":
                fillGeneralTab(reportEntries);
                break;
            case "PHONE LOG":
                fillPhoneLogTab(reportEntries);
                break;
        }
        return reportEntries;
    }

    private void fillPatientInformation(Map<String, String> reportEntries) {
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
                    reportEditorHelper.selectAllCheckboxesInARegion(regionBounds.get(key), value.replaceAll("/", " "), "\\.");
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
    }

    private void fillFavoritesTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "FAVORITES":
                case "CONCLUSIONS":
                case "COPY TO":
                case "STUDY DESCRIPTION":
                case "FREE TEXT":
                case "FINDINGS":
                case "IMPRESSION":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
            }
        }
    }

    private void fillCodesTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "DIAGNOSIS":
                case "PROCEDURE":
                    reportEditorHelper.addCode(key, value);
                    break;
            }
        }
    }

    private void fillPhysicalExamTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
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
                    reportEditorHelper.selectCheckbox(key.replace("/", " "));
                    break;
            }
        }
    }

    private void fillLesionLocationTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
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
                        reportEntries.put("Free Text", "Ignore");
                    }
                    break;
                case "LOCATION":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
            }
        }
    }

    private void fillLesionCharacteristicTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
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
    }

    private void fillInterventionTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "INFORMED CONSENT OBTAINED":
                case "SKIN PREPPED WITH ALCOHOL":
                case "ANESTHETIC":
                    reportEditorHelper.selectCheckbox(key);
                    break;
            }
        }
    }

    private void fillBiopsyApproachTab(Map<String, String> reportEntries) {
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
    }

    private void fillDeviceUtilizedTab(Map<String, String> reportEntries) {
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
    }

    private void fillFollowUpTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
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
    }

    private void fillPostBiopsyImageTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String value = entry.getValue();
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
    }

    private void fillIsthmusTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "MEASURES":
                case "TOTAL NODULES":
                case "ISTHMUS NOTES":
                case "LENGTH":
                case "AP":
                case "TR":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "NUMBER OF NODULES":
                    reportEditorHelper.enterTextAndSelectCheckbox(key, value);
                    break;
                case "NO NODULES":
                case "MULTIPLE NODULES":
                case "SURGICALLY REMOVED":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "ECHOGENICITY":
                case "VASCULARITY":
                case "LOCATION1":
                case "LOCATION2":
                case "NODULE ECHOGENICITY":
                case "NODULE VASCULARITY":
                case "COMPARE TO PREVIOUS":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
            }
        }
    }

    private void fillLobeTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "LOBE LENGTH":
                case "LOBE AP":
                case "LOBE WIDTH":
                case "TOTAL NODULES":
                case "LOBE NOTES":
                case "NODULE LENGTH":
                case "NODULE AP":
                case "NODULE TR":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "NUMBER OF NODULES":
                    reportEditorHelper.enterTextAndSelectCheckbox(key, value);
                    break;
                case "ENLARGED":
                case "NO NODULES":
                case "MULTIPLE NODULES":
                case "SURGICALLY REMOVED":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "ECHOGENICITY":
                case "VASCULARITY":
                case "LOCATION1":
                case "LOCATION2":
                case "NODULE ECHOGENICITY":
                case "NODULE VASCULARITY":
                case "COMPARE TO PREVIOUS":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
            }
        }
    }

    private void fillSoftTissueMassTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "LENGTH":
                case "AP":
                case "TR":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "NUMBER OF MASSES":
                    reportEditorHelper.enterTextAndSelectCheckbox(key, value);
                    break;
                case "ECHOGENICITY":
                case "VASCULARITY":
                case "LOCATION1":
                case "LOCATION2":
                case "BORDERS":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
            }
        }
    }

    private void fillLymphNodesTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "LENGTH":
                case "AP":
                case "TR":
                case "CORTEX":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "NUMBER OF LYMPH NODES":
                    reportEditorHelper.enterTextAndSelectCheckbox(key, value);
                    break;
                case "LEVEL":
                case "VASCULARITY":
                case "APPEARANCE":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
            }
        }
    }

    private void fillParathyroidTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "LENGTH":
                case "AP":
                case "TR":
                case "APPEARANCE":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "NUMBER OF PARATHYROIDS":
                    reportEditorHelper.enterTextAndSelectCheckbox(key, value);
                    break;
            }
        }
    }

    private void fillGeneralTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "IMPRESSION":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    entry.setValue(reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
                    break;
            }
        }
    }

    private void fillPhoneLogTab(Map<String, String> reportEntries) {
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
    }

    @Override
    public Map<String, String> getTransformedReportValues(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            switch (entry.getKey().toUpperCase()) {
                case "PATIENT NAME":
                    String patientNameValue = entry.getValue().replace("^", ", ");
                    entry.setValue(patientNameValue);
                    break;
                case "ISTHMUS":
                    String phrase = "The isthmus measures %s cm and appears %s with %s blood flow";
                    entry.setValue(String.format(phrase,
                            reportEntries.put("Measures", "Ignore"),
                            reportEntries.put("Echogenicity", "Ignore"),
                            reportEntries.put("Vascularity", "Ignore")));
                    break;
                case "LOBE":
                    phrase = "The %s measures %s X %s X %s cm and appears %s with %s blood flow";
                    entry.setValue(String.format(phrase,
                            tabName.toLowerCase(),
                            reportEntries.put("Lobe Length", "Ignore"),
                            reportEntries.put("Lobe AP", "Ignore"),
                            reportEntries.put("Lobe Width", "Ignore"),
                            reportEntries.put("Echogenicity", "Ignore"),
                            reportEntries.put("Vascularity", "Ignore")));
                    break;
                case "LOCATION":
                    entry.setValue(reportEntries.put("Location1", "Ignore") + "/" +
                            reportEntries.put("Location2", "Ignore"));
                    break;
                case "RIGHT PARATHYROID":
                    phrase = String.format("Right Parathyroid 1 measures %s x %s x %s cm and appears %s",
                            reportEntries.put("Length", "Ignore"),
                            reportEntries.put("AP", "Ignore"),
                            reportEntries.put("TR", "Ignore"),
                            reportEntries.put("Appearance", "Ignore"));
                    entry.setValue(phrase);
                    break;
                case "LEFT PARATHYROID":
                    phrase = String.format("Left Parathyroid 1 measures %s x %s x %s cm and appears %s",
                            reportEntries.put("Length", "Ignore"),
                            reportEntries.put("AP", "Ignore"),
                            reportEntries.put("TR", "Ignore"),
                            reportEntries.put("Appearance", "Ignore"));
                    entry.setValue(phrase);
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

