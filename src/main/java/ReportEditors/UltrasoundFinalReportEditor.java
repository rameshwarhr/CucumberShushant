package ReportEditors;

import Actions.RobotActions;
import Actions.SikuliActions;
import Utilities.Common;
import Utilities.Log;

import java.awt.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static Utilities.Constants.ReportScreens;
import static Utilities.Constants.USER_WORK_DIR;

public class UltrasoundFinalReportEditor implements ReportEditor {

    SikuliActions sikuliActions;
    RobotActions robotActions;
    ReportEditorHelper reportEditorHelper;
    String sectionName;
    String tabName;
    String ReportElements;

    public UltrasoundFinalReportEditor(String sectionName, String tabName) {
        sikuliActions = new SikuliActions();
        robotActions = new RobotActions();
        this.sectionName = sectionName;
        this.tabName = tabName;
        ReportElements = USER_WORK_DIR + ReportScreens + "OB Ultrasound Final Report\\" + sectionName + "\\" + tabName
                + "\\";
    }

    private final String reportName = "OB Ultrasound Final Report";

    @Override
    public Map<String, String> fillReport(Map<String, String> reportEntries) {
        reportEditorHelper = new ReportEditorHelper(reportName, sectionName, tabName);
        Map<String, String> filledReportEntries = new LinkedHashMap<>();
        switch (tabName.toUpperCase()) {
            case "IMPRESSIONS":
                filledReportEntries = fillImpressionsTab(reportEntries);
                break;
            case "SINGLETON MULTIPLE GESTATION":
                filledReportEntries = fillSingletonMultipleGestationTab(reportEntries);
                break;
            case "MATERNAL":
                filledReportEntries = fillMaternalTab(reportEntries);
                break;
            case "FETAL GENERAL":
                filledReportEntries = fillFetalGeneralTab(reportEntries);
                break;
            case "BIOPHYSICAL PROFILE":
                filledReportEntries = fillBiophysicalProfileTab(reportEntries);
                break;
            case "PRESENTATION":
                filledReportEntries = fillPresentationTab(reportEntries);
                break;
            case "PLACENTA":
                filledReportEntries = fillPlacentaTab(reportEntries);
                break;
            case "ANTENATAL TESTING":
                filledReportEntries = fillAntenatalTestingTab(reportEntries);
                break;
            case "AFI":
                filledReportEntries = fillAFITab(reportEntries);
                break;
            case "CORD":
                filledReportEntries = fillCordTab(reportEntries);
                break;
            case "HEART RATE AND RHYTHM":
                filledReportEntries = fillHeartRateAndRhythmTab(reportEntries);
                break;
            case "PATIENT INFORMATION":
                filledReportEntries = fillPatientInformation(reportEntries);
                break;
            case "HEAD":
                filledReportEntries = fillHeadTab(reportEntries);
                break;
            case "NECK":
                filledReportEntries = fillNeckTab(reportEntries);
                break;
            case "SPINE":
                filledReportEntries = fillSpineTab(reportEntries);
                break;
            case "FACE":
                filledReportEntries = fillFaceTab(reportEntries);
                break;
            case "ABDOMEN":
                filledReportEntries = fillAbdomenTab(reportEntries);
                break;
            case "THORAX":
                filledReportEntries = fillThoraxTab(reportEntries);
                break;
            case "UPPER EXTREMITIES":
                filledReportEntries = fillUpperExtremitiesTab(reportEntries);
                break;
            case "LOWER EXTREMITIES":
                filledReportEntries = fillLowerExtremitiesTab(reportEntries);
                break;
            case "AMNIOCENTESIS":
                filledReportEntries = fillAmniocentesisTab(reportEntries);
                break;
            case "GENERAL":
                filledReportEntries = fillGeneralTab(reportEntries);
                break;
            case "CVS (A)":
                filledReportEntries = fillCVSTab(reportEntries);
                break;
            case "FETAL REDUCTION":
                filledReportEntries = fillFetalReductionTab(reportEntries);
                break;
            case "FETAL BLOOD SAMPLING (A)":
                filledReportEntries = fillFetalBloodSamplingTab(reportEntries);
                break;
            case "FETAL TRANSFUSION (A)":
                filledReportEntries = fillFetalTransfusionTab(reportEntries);
                break;
            case "TECH COMMENTS":
            case "LABS":
            case "FREE TEXT":
            case "OTHER":
            case "CLINICAL SUMMARY":
            case "CONSULTATION":
                filledReportEntries = fillOtherTab(reportEntries);
                break;
            case "UTERUS":
                filledReportEntries = fillUterusTab(reportEntries);
                break;
            case "ENDOMETRIUM":
                filledReportEntries = fillEndometriumTab(reportEntries);
                break;
            case "CERVIX":
                filledReportEntries = fillCervixTab(reportEntries);
                break;
            case "OVARY-RIGHT":
            case "OVARY-LEFT":
                filledReportEntries = fillOvaryTab(reportEntries);
                break;
            case "F TUBE-RIGHT":
            case "F TUBE-LEFT":
                filledReportEntries = fillFallopianTubeTab(reportEntries);
                break;
            case "CUL DE SAC":
                filledReportEntries = fillCulDeSacTab(reportEntries);
                break;
            case "KIDNEY BLADDER":
                filledReportEntries = fillKidneyBladderTab(reportEntries);
                break;
            case "ECTOPIC":
                filledReportEntries = fillEctopicTab(reportEntries);
                break;
            case "COMMENTS":
                filledReportEntries = fillCommentsTab(reportEntries);
                break;
            case "EXAMINATION INFORMATION":
                filledReportEntries = fillExaminationInformationTab(reportEntries);
                break;
            case "FAVORITES":
                filledReportEntries = fillFavoritesTab(reportEntries);
                break;
            case "CODES":
                filledReportEntries = fillCodesTab(reportEntries);
                break;
            case "MEDICATIONS":
            case "ALLERGIES":
                filledReportEntries = fillMedicationsOrAllergiesTab(reportEntries);
                break;
            case "NEW PATIENT":
            case "FOLLOW-UP PATIENT":
            case "BPP":
            case "CERVICAL EXAM":
            case "FETAL ECHO CARDIAC":
            case "OTHER ANOMALIES AND MISC":
            case "TWIN GESTATION":
                filledReportEntries = fillSonographerCommentsTab(reportEntries);
                break;
            case "MULTIPLE GESTATION":
                filledReportEntries = fillMultipleGestationTab(reportEntries);
                break;
        }
        return filledReportEntries;
    }

    private Map<String, String> fillImpressionsTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "SINGLETON IUP":
                case "TODAYS FETAL BIOMETRY CLOSELY CORRESPONDS WITH PATIENT'S ASSIGNED EDD":
                case "FETAL MOVEMENT CHARTING WAS REINFORCED":
                case "I DO NOT FEEL SHE NEEDS AN AMNIOCENTESIS AT THIS TIME":
                case "I DO NOT RECOMMEND FFN TESTING WITH ONGOING CONCERNS OF PTL":
                case "NO MAJOR STRUCTURAL MALFORMATION WITHIN THE LIMITS OF ULTRASOUND AND FETAL CROWDING":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "AVERAGE GESTATIONAL AGE":
                case "PRESENTATION":
                case "PLACENTA":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "ANATOMY SURVEY":
                case "HEART RATE":
                case "FETAL MOVEMENT AND ACTIVITY":
                case "VISIBLE INTRACRANIAL ANATOMY":
                case "NEURAL TUBE":
                case "CARDIAC STRUCTURES":
                case "GASTROINTESTINAL AND GENITOURINARY SYSTEMS":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
                case "PATIENT DENIES":
                case "PATIENT ADMITS":
                case "TRANSVAGINAL IMAGES":
                case "TRANSVAGINAL IMAGES OF LOWER UTERINE SEGMENT":
                    String region = key.split(":")[0].trim();
                    String field = key.split(":")[1].trim();
                    if (!regionBounds.containsKey(region))
                        regionBounds.put(region, reportEditorHelper.getRegionBounds(region));
                    if (value.equalsIgnoreCase("Select")) {
                        reportEditorHelper.selectCheckboxInARegion(field, regionBounds.get(region));
                    } else {
                        reportEditorHelper.enterValueInTextboxFromARegion(regionBounds.get(region), field,
                                value);
                    }
                    break;
                case "MEDICATIONS":
                    entry.setValue(reportEditorHelper.selectRandomRadioButton(entry.getValue()));
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillSingletonMultipleGestationTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "TYPE OF GESTATION":
                case "DISCORDANCE RATIO":
                case "REVEALS THE FOLLOWING":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "GESTATION DESCRIPTION":
                    reportEditorHelper.enterTextAndSelectCheckbox(key, value);
                    break;
                case "DIVIDING MEMBRANE":
                case "DIVIDING MEMBRANE THICKNESS":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
                case "TWINS":
                case "TRIPLETS":
                    String radio = reportEditorHelper.getRandomValue(entry.getValue());
                    if (radio.equalsIgnoreCase("Other")) {
                        value = "Other remarks";
                        reportEditorHelper.enterValueInTextbox(radio, value);
                        entry.setValue(value);
                    } else {
                        reportEditorHelper.selectRadioButton(radio.replace(" / ", " "));
                        entry.setValue(radio);
                    }
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillMaternalTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "REVEALS A FIBROID":
                case "REVEALS FIBROIDS":
                case "REVEALS A SUBCHORIONIC HEMORRHAGE":
                case "REVEALS A BICORNATE UTERUS":
                case "REVEALS A UTERINE SEPTUM":
                case "REVEALS AN ATTENUATED LOWER UTERUS":
                case "IMAGED BY VAGINAL ULTRASOUND":
                case "IMAGED BY ABDOMINAL ULTRASOUND":
                case "SHORT WITH FUNNELING":
                case "SHORT WITHOUT FUNNELING":
                case "NOTED TO HAVE CERCLAGE":
                case "NOTED TO HAVE PESSARY":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "REVEALS THE FOLLOWING":
                case "OTHER":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "MATERNAL ANATOMY":
                case "REVEALS A UTERINE DIDELPHIS":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
                case "UTERINE ULTRASOUND":
                case "ADNEXAE":
                case "CERVIX":
                    String region = key.split(":")[0].trim();
                    String field = key.split(":")[1].trim();
                    if (!regionBounds.containsKey(region))
                        regionBounds.put(region, sikuliActions.getExactRegionBounds(ReportElements + region + "_Region.PNG"));
                    if (value.equalsIgnoreCase("Select")) {
                        reportEditorHelper.selectCheckboxInARegion(field, regionBounds.get(region));
                    } else {
                        reportEditorHelper.enterValueInTextboxFromARegion(regionBounds.get(region), field, value);
                    }
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillFetalGeneralTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "APPROPRIATE FOR GESTATIONAL AGE":
                case "LARGE FOR GESTATIONAL AGE":
                case "SMALL FOR GESTATIONAL AGE":
                case "WITHIN NORMAL LIMITS":
                case "OLIGOHYDRAMNIOS":
                case "POLYHYDRAMNIOS":
                case "SUBJECTIVELY INCREASED":
                case "SUBJECTIVELY DECREASED":
                case "BPP":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "NOTABLE FOR":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "FETAL PRESENTATION":
                case "BREECH":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value).toLowerCase());
                    break;
                case "MIDDLE CEREBRAL ARTERY DOPPLER":
                case "UMBILICAL ARTERY DOPPLER":
                case "DUCTUS VENOSUS DOPPLER":
                case "ANATOMICAL EVALUATION":
                    String region = key.split(":")[0].trim();
                    String field = key.split(":")[1].trim();
                    if (!regionBounds.containsKey(region))
                        regionBounds.put(region, sikuliActions.getExactRegionBounds(ReportElements + region + "_Region.PNG"));
                    if (value.equalsIgnoreCase("Select")) {
                        reportEditorHelper.selectCheckboxInARegion(field, regionBounds.get(region));
                    } else {
                        reportEditorHelper.enterValueInTextboxFromARegion(regionBounds.get(region), field, value);
                    }
                    break;
                case "PLACENTAL LOCATION":
                    String placentalLocationField = key.split(":")[1].trim();
                    if (value.equalsIgnoreCase("Select")) {
                        reportEditorHelper.selectCheckbox(placentalLocationField);
                        entry.setValue("fundal");
                    } else {
                        entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(placentalLocationField, value).toLowerCase());
                    }
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillBiophysicalProfileTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();

            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "BREATHING":
                case "MOVEMENT":
                case "TONE":
                case "AFV":
                case "FHR REACTIVITY":
                    String version = reportEntries.get("Version");
                    key = version.equalsIgnoreCase("BPP") ? key : key + " NST";
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "VERSION":
                    entry.setValue(reportEditorHelper.selectRandomRadioButton(value));
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillPresentationTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "OTHER":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "FETAL PRESENTATION":
                case "BREECH":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillPlacentaTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "LAKES":
                case "PLACENTOMEGALY":
                case "RETROPLACENTAL CLOT":
                case "RETROMEMBRANOUS COLLECTION":
                case "SUCCENTURIATE LOBE":
                case "CHORIOANGIOMA":
                case "CIRCUMVALLATE":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "CERVIX TO PLACENTA MEASUREMENT":
                case "ABNORMAL APPEARANCE OTHER":
                case "OTHER":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "PRESENTATION":
                case "BREECH":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value).toLowerCase());
                    break;
                case "ABNORMAL APPEARANCE":
                    String region = key.split(":")[0].trim();
                    String field = key.split(":")[1].trim();
                    if (!regionBounds.containsKey(region))
                        regionBounds.put(region, sikuliActions.getExactRegionBounds(ReportElements + region + "_Region.PNG"));
                    reportEditorHelper.enterValueInTextboxFromARegion(regionBounds.get(region), field, value);
                    break;
                case "POSITION":
                    String positionField = key.split(":")[1].trim();
                    if (value.equalsIgnoreCase("Select")) {
                        reportEditorHelper.selectCheckbox(positionField);
                        entry.setValue("Fundal");
                    } else {
                        entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(positionField, value));
                    }
                    break;
                case "GRADE":
                case "PLACENTATION":
                    entry.setValue(reportEditorHelper.selectRandomRadioButton(value));
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillAntenatalTestingTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace("/", " ");
            String value = entry.getValue();
            switch (key.split(":")[0].toUpperCase()) {
                case "NORMAL":
                case "LATE DECELERATIONS":
                case "SUSPICIOUS":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "CEREBROPLACENTAL RATIO":
                case "HR":
                case "S D RATIO":
                case "D S RATIO":
                case "RESISTIVITY INDEX":
                case "PULSATILITY INDEX":
                case "PSV":
                case "EDV":
                case "OTHER":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "VARIABLE DECELERATIONS":
                case "LOCATION":
                case "END DIASTOLIC FLOW":
                case "ASSESSMENT":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
                case "NST":
                case "CST":
                    String region = key.split(":")[0].trim();
                    String field = key.split(":")[1].trim();
                    if (!regionBounds.containsKey(region))
                        regionBounds.put(region, sikuliActions.getExactRegionBounds(ReportElements + region + "_Region.PNG"));
                    if (!entry.getValue().contains(";")) {
                        reportEditorHelper.enterValueInTextboxFromARegion(regionBounds.get(region), field, value);
                    } else {
                        entry.setValue(reportEditorHelper.selectRandomValueFromDropdownInARegion(regionBounds.get(region), field, value));
                    }
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillAFITab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace("/", " ");
            String value = entry.getValue();
            switch (key.split(":")[0].toUpperCase()) {
                case "NORMAL":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "RUQ":
                case "LUQ":
                case "RLQ":
                case "LLQ":
                case "MAXIMUM VERTICAL POCKET":
                case "OTHER":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "SUBJECTIVE AF VOLUME DECREASED":
                case "SUBJECTIVE AF VOLUME INCREASED":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
                case "OVERALL ASSESSMENT":
                    entry.setValue(reportEditorHelper.selectRandomRadioButton(value));
                    break;
                case "AFI":
                case "AFI RANK":
                    entry.setValue(reportEditorHelper.getFieldValue(key));
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillCordTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "OTHER":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "CORD INSERTION":
                case "UMBILICAL CORD":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    entry.setValue(reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillHeartRateAndRhythmTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "NORMAL SINUS RHYTHM":
                case "NORMAL AV CONDUCTION":
                case "SINUS BRADYCARDIA":
                case "SINUS TACHYCARDIA":
                case "SUPRAVENTRICULAR TACHYCARDIA":
                case "FIRST DEGREE HEART BLOCK":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "FHR":
                case "ATRIAL RATE":
                case "VENTRICULAR RATE":
                case "PR INTERVAL":
                case "OTHER":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "PACS":
                case "PVCS":
                case "ATRIAL FLUTTER":
                case "SECOND DEGREE HEART BLOCK":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillPatientInformation(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "EDD TYPE":
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    break;
                case "EDD (OUTSIDE)":
                    sikuliActions.click(ReportElements + key + "_Textbox.PNG");
                    break;
                case "EDD BY":
                    String edd = null, ga = null;
                    value = reportEditorHelper.selectRandomValueFromDropdown(key, value);
                    switch (value) {
                        case "By LMP":
                            edd = reportEditorHelper.getFieldValue("EDD (LMP)");
                            ga = reportEditorHelper.getFieldValue("GA (LMP)");
                            reportEntries.put("EDD (LMP)", edd);
                            reportEntries.put("GA (LMP)", ga);
                            break;
                        case "Outside EDD":
                            edd = reportEditorHelper.getFieldValue("EDD (Outside)");
                            ga = reportEditorHelper.getFieldValue("GA (Outside)");
                            reportEntries.put("EDD (Outside)", edd);
                            reportEntries.put("GA (Outside)", ga);
                            break;
                        case "First sono EDD":
                            edd = reportEditorHelper.getFieldValue("EDD (First sono)");
                            ga = reportEditorHelper.getFieldValue("GA (First sono)");
                            reportEntries.put("EDD (First sono)", edd);
                            reportEntries.put("GA (First sono)", ga);
                            break;
                        case "Todays EDD":
                            edd = reportEditorHelper.getFieldValue("EDD (AUA)");
                            ga = reportEditorHelper.getFieldValue("GA (EDD)");
                            reportEntries.put("EDD (AUA)", edd);
                            reportEntries.put("GA (AUA)", ga);
                            break;
                        case "Largest fetus":
                            edd = reportEditorHelper.getFieldValue("EDD (Largest fetus)");
                            ga = reportEditorHelper.getFieldValue("GA (Largest fetus)");
                            reportEntries.put("EDD (Largest fetus)", edd);
                            reportEntries.put("GA (Largest fetus)", ga);
                            break;
                    }
                    entry.setValue(value);
                    reportEntries.put("EDD", edd);
                    reportEntries.put("GA (EDD)", ga);
                    break;
                case "RACE":
                case "PATIENT TYPE":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
                case "WORKING ECD BASED ON":
                case "TYPE OF PAYMENT":
                case "DIABETIC":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    entry.setValue(reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
                    break;
                case "PATIENT NAME":
                case "EXAMINATION DATE":
                case "AGE":
                case "SEX":
                case "DOB":
                case "LMP":
                case "CONCEPTION DATE":
                case "PRE PREGNANCY BMI":
                case "BMI":
                case "BSA":
                case "EQUIPMENT":
                case "CHART":
                case "REFERRING PHYSICIAN":
                    entry.setValue(reportEditorHelper.getFieldValue(key));
                    break;
                case "HEART RATE":
                case "RESPIRATORY RATE":
                case "TEMPERATURE":
                case "SYSTOLIC BLOOD PRESSURE":
                case "DIASTOLIC BLOOD PRESSURE":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "PRE PREGNANCY WEIGHT":
                case "WEIGHT":
                case "HEIGHT":
                case "NUMBER OF PREGNANCIES":
                case "NUMBER OF LIVING BIRTHS":
                case "NUMBER OF TERM BIRTHS":
                case "NUMBER OF PRETERM BIRTHS":
                case "NUMBER OF ECTOPIC":
                case "NUMBER OF MISCARRIAGES ABORTION":
                case "NUMBER OF LIVING CHILDREN":
                    reportEditorHelper.enterTextAndSelectCheckbox(key, value);
                    break;
            }
        }
        reportEntries.values().removeIf(val -> val.equals(""));
        return reportEntries;
    }

    private Map<String, String> fillHeadTab(Map<String, String> reportEntries) {
        Map<String, String> abnormalitiesEntries = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "CALVARIUM":
                case "INTRACRANIAL ANATOMY":
                case "LATERAL VENTRICLES":
                case "RIGHT VENTRICLE":
                case "LEFT VENTRICLE":
                case "3RD VENTRICLE":
                case "4TH VENTRICLE":
                case "CHOROID PLEXUS":
                case "RIGHT CHOROID PLEXUS":
                case "LEFT CHOROID PLEXUS":
                case "CEREBELLUM":
                case "VERMIS":
                case "CISTERNA MAGNA":
                case "CSP":
                    value = reportEditorHelper.selectRandomValueFromDropdown(key, value);
                    entry.setValue(value);
                    if (value.equalsIgnoreCase("Abnormal")) {
                        Map<String, String> abnormalityEntry = fillAbnormalities(key);
                        if (abnormalityEntry != null) {
                            abnormalitiesEntries.putAll(abnormalityEntry);
                        }
                    }
                    break;
                case "LATERAL VENTRICLE L":
                case "LATERAL VENTRICLE R":
                case "CEREBELLUM MEASUREMENT":
                case "CISTERNA MAGNA MEASUREMENT":
                case "LATERAL VENTRICLE":
                case "OTHER":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
            }
        }
        reportEntries.putAll(abnormalitiesEntries);
        return reportEntries;
    }

    private Map<String, String> fillNeckTab(Map<String, String> reportEntries) {
        Map<String, String> abnormalitiesEntries = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "NECK ANATOMY":
                case "NUCHAL FOLD":
                    value = reportEditorHelper.selectRandomValueFromDropdown(key, value);
                    entry.setValue(value);
                    if (value.equalsIgnoreCase("Abnormal")) {
                        Map<String, String> abnormalityEntry = fillAbnormalities(key);
                        if (abnormalityEntry != null) {
                            abnormalitiesEntries.putAll(abnormalityEntry);
                        }
                    }
                    break;
                case "NUCHAL FOLD MEASUREMENT":
                case "OTHER":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
            }
        }
        reportEntries.putAll(abnormalitiesEntries);
        return reportEntries;
    }

    private Map<String, String> fillSpineTab(Map<String, String> reportEntries) {
        Map<String, String> abnormalitiesEntries = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "CERVICAL SPINE":
                case "THORACIC SPINE":
                case "LUMBAR SPINE":
                case "SACRAL SPINE":
                    value = reportEditorHelper.selectRandomValueFromDropdown(key, value);
                    entry.setValue(value);
                    if (value.equalsIgnoreCase("Abnormal")) {
                        Map<String, String> abnormalityEntry = fillAbnormalities(key);
                        if (abnormalityEntry != null) {
                            abnormalitiesEntries.putAll(abnormalityEntry);
                        }
                    }
                    break;
                case "OTHER":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
            }
        }
        reportEntries.putAll(abnormalitiesEntries);
        return reportEntries;
    }

    private Map<String, String> fillFaceTab(Map<String, String> reportEntries) {
        Map<String, String> abnormalitiesEntries = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "RIGHT EAR":
                case "LEFT EAR":
                case "PROFILE":
                case "ORBITS":
                case "NASAL BONE":
                case "LIPS":
                case "FACE":
                case "MAXILLARY RIDGE":
                case "MANDIBLE":
                    value = reportEditorHelper.selectRandomValueFromDropdown(key, value);
                    entry.setValue(value);
                    if (value.equalsIgnoreCase("Abnormal")) {
                        Map<String, String> abnormalityEntry = fillAbnormalities(key);
                        if (abnormalityEntry != null) {
                            abnormalitiesEntries.putAll(abnormalityEntry);
                        }
                    }
                    break;
                case "NASAL BONE MEASUREMENT":
                case "OTHER":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
            }
        }
        reportEntries.putAll(abnormalitiesEntries);
        return reportEntries;
    }

    private Map<String, String> fillAbdomenTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        Map<String, String> abnormalitiesEntries = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "SITUS":
                case "VENTRAL WALL":
                case "STOMACH":
                case "LIVER":
                case "GALL BLADDER ON RIGHT":
                case "SPLEEN":
                case "BLADDER":
                case "BOWEL":
                case "ABDOMINAL CAVITY":
                case "3 VESSEL CORD":
                case "LUS":
                case "GENDER":
                case "CORD INSERTION":
                case "LEFT KIDNEY":
                case "RIGHT KIDNEY":
                    value = reportEditorHelper.selectRandomValueFromDropdown(key, value);
                    entry.setValue(value);
                    if (value.equalsIgnoreCase("Abnormal")) {
                        Map<String, String> abnormalityEntry = fillAbnormalities(key);
                        if (abnormalityEntry != null) {
                            abnormalitiesEntries.putAll(abnormalityEntry);
                        }
                    }
                    break;
                case "LEFT KIDNEY MEASUREMENT":
                case "RIGHT KIDNEY MEASUREMENT":
                    String region = key.split(":")[0].trim();
                    String field = key.split(":")[1].trim();
                    regionBounds.putIfAbsent(region,
                            sikuliActions.getExactRegionBounds(ReportElements + region + "_Region.PNG"));
                    reportEditorHelper.enterValueInTextboxFromARegion(regionBounds.get(region), field, value);
                    break;
                case "OTHER":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
            }
        }
        reportEntries.putAll(abnormalitiesEntries);
        return reportEntries;
    }

    private Map<String, String> fillThoraxTab(Map<String, String> reportEntries) {
        Map<String, String> abnormalitiesEntries = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "4C HEART":
                case "IVS":
                case "LVOT":
                case "RVOT":
                case "AORTIC ARCH":
                case "IVC":
                case "SVC":
                case "PULMONARY ARTERY":
                case "CARDIAC ACTIVITY":
                case "CARDIAC AXIS":
                case "SHORT AXIS HEART":
                case "THREE VESSEL VIEW":
                case "3 VESSEL TRACHEA VIEW":
                case "LUNGS":
                case "RIGHT LUNG":
                case "LEFT LUNG":
                case "DIAPHRAGM":
                case "RIGHT DIAPHRAGM":
                case "LEFT DIAPHRAGM":
                    value = reportEditorHelper.selectRandomValueFromDropdown(key, value);
                    entry.setValue(value);
                    if (value.equalsIgnoreCase("Abnormal")) {
                        Map<String, String> abnormalityEntry = fillAbnormalities(key);
                        if (abnormalityEntry != null) {
                            abnormalitiesEntries.putAll(abnormalityEntry);
                        }
                    }
                    break;
                case "FHR":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
            }
        }
        abnormalitiesEntries.putAll(fillAbnormalities("Heart Position"));
        abnormalitiesEntries.putAll(fillAbnormalities("Heart Anatomy"));
        abnormalitiesEntries.putAll(fillAbnormalities("Heart Rate"));
        reportEntries.putAll(abnormalitiesEntries);
        return reportEntries;
    }

    private Map<String, String> fillUpperExtremitiesTab(Map<String, String> reportEntries) {
        Map<String, String> abnormalitiesEntries = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            value = reportEditorHelper.selectRandomValueFromDropdown(key, value);
            entry.setValue(value);
            if (value.equalsIgnoreCase("Abnormal")) {
                Map<String, String> abnormalityEntry = fillAbnormalities(key);
                if (abnormalityEntry != null) {
                    abnormalitiesEntries.putAll(abnormalityEntry);
                }
            }
        }
        reportEntries.putAll(abnormalitiesEntries);
        return reportEntries;
    }

    private Map<String, String> fillLowerExtremitiesTab(Map<String, String> reportEntries) {
        Map<String, String> abnormalitiesEntries = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = reportEditorHelper.selectRandomValueFromDropdown(key, entry.getValue());
            entry.setValue(value);
            if (value.equalsIgnoreCase("Abnormal")) {
                Map<String, String> abnormalityEntry = fillAbnormalities(key);
                if (abnormalityEntry != null) {
                    abnormalitiesEntries.putAll(abnormalityEntry);
                }
            }
        }
        reportEntries.putAll(abnormalitiesEntries);
        return reportEntries;
    }

    private Map<String, String> fillAmniocentesisTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().split(":")[0];
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "SUCCESSFUL":
                case "PLACENTA PENETRATED":
                case "FLUID DESCRIPTION":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    entry.setValue(reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
                    break;
                case "TYPE":
                case "FLUID SENT FOR":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    reportEditorHelper.selectAllCheckboxesInARegion(regionBounds.get(key), value);
                    break;
                case "NUMBER OF ATTEMPTS":
                    reportEditorHelper.enterTextAndSelectCheckbox(key, value);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillGeneralTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().split(":")[0];
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "MATERNAL BLOOD TYPE":
                case "COMPLICATIONS":
                case "FHR POST PROCEDURE":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    entry.setValue(reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
                    break;
                case "MEDICATIONS GIVEN":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    reportEditorHelper.selectAllCheckboxesInARegion(regionBounds.get(key), value);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillCVSTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().split(":")[0];
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "SUCCESSFUL":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    entry.setValue(reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
                    break;
                case "TYPE":
                case "SAMPLE SENT FOR":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    reportEditorHelper.selectAllCheckboxesInARegion(regionBounds.get(key), value);
                    break;
                case "NUMBER OF ATTEMPTS":
                    reportEditorHelper.enterTextAndSelectCheckbox(key, value);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillFetalReductionTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().split(":")[0];
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "SUCCESSFUL":
                case "TYPE":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    entry.setValue(reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
                    break;
                case "PROCEDURAL TECHNIQUE":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    reportEditorHelper.selectAllCheckboxesInARegion(regionBounds.get(key), value);
                    break;
                case "PROCEDURE START":
                case "PROCEDURE FINISH":
                    reportEditorHelper.enterTextAndSelectCheckbox(key, value);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillFetalBloodSamplingTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().split(":")[0];
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "SUCCESSFUL":
                case "SAMPLE SITE":
                case "BLOOD CONFIRMED FETAL":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    entry.setValue(reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
                    break;
                case "BLOOD SENT FOR":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    reportEditorHelper.selectAllCheckboxesInARegion(regionBounds.get(key), value.replaceAll("/", " "));
                    break;
                case "VOLUME OF BLOOD":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillFetalTransfusionTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().split(":")[0];
            String value = entry.getValue();
            if (!regionBounds.containsKey(key))
                regionBounds.putIfAbsent(key, sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
            entry.setValue(reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
        }
        return reportEntries;
    }

    private Map<String, String> fillOtherTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().split(":")[0];
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "OTHER PROCEDURES":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    reportEditorHelper.selectAllCheckboxesInARegion(regionBounds.get(key), value);
                    break;
                case "OTHER":
                case "FREE TEXT":
                case "LABS":
                case "SONOGRAPHER COMMENTS":
                case "CLINICAL SUMMARY":
                case "CONSULTATION":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillSonographerCommentsTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().split(":")[0];
            String fieldValue = reportEditorHelper.getFieldValue(key);
            Log.printInfo("Copied Contents");
            Log.printInfo("------------------------");
            String message = fieldValue.replaceAll("\r\n", " ").replaceAll("\\s+", " ").replaceAll("%", "\\% ");
            Log.printInfo(message);
            entry.setValue(message);
            reportEditorHelper.selectCheckbox(key);
        }
        return reportEntries;
    }

    private Map<String, String> fillUterusTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "UTERUS":
                case "UTERINE ANOMALIES":
                    regionBounds.putIfAbsent(key,
                            sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    entry.setValue(reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
                    break;
                case "UTERUS POSITION":
                case "UTERINE PATHOLOGY":
                    regionBounds.putIfAbsent(key,
                            sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    reportEditorHelper.selectAllCheckboxesInARegion(regionBounds.get(key), value);
                    break;
                case "UTERUS LENGTH":
                case "UTERUS WIDTH":
                case "UTERUS HEIGHT":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    if (key.equalsIgnoreCase("Uterus Height")) {
                        reportEntries.put("Uterus Volume", reportEditorHelper.getFieldValue("Uterus Volume"));
                    }
                    break;
            }
        }
        fillMassDetails(reportEntries);
        fillFibroidsDetails(reportEntries);
        return reportEntries;
    }

    private Map<String, String> fillEndometriumTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "ENDOMETRIAL CAVITY":
                case "LOCATION":
                case "TAMOXIFEN EFFECT":
                case "RETAINED PRODUCTS":
                    regionBounds.putIfAbsent(key,
                            sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    entry.setValue(reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
                    break;
                case "TEXTURE":
                case "IUD LOCATION":
                    regionBounds.putIfAbsent(key,
                            sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    reportEditorHelper.selectAllCheckboxesInARegion(regionBounds.get(key), value);
                    break;
                case "ENDOMETRIAL FLUID":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
                case "POLYP LENGTH":
                case "POLYP WIDTH":
                case "POLYP HEIGHT":
                case "ENDOMETRIUM THICKNESS":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    if (key.equalsIgnoreCase("Polyp Height")) {
                        Common.sleep(1, TimeUnit.SECONDS);
                        reportEntries.put("Polyp volume", reportEditorHelper.getFieldValue("Polyp volume"));
                    }
                    break;
            }
        }
        fillMassDetails(reportEntries);
        return reportEntries;
    }

    private Map<String, String> fillCervixTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "CERVIX":
                case "APPROACH":
                case "FUNDAL PRESSURE":
                case "CERCLAGE":
                    regionBounds.putIfAbsent(key,
                            sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    entry.setValue(reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
                    break;
                case "APPEARANCE":
                case "FINDINGS":
                    regionBounds.putIfAbsent(key,
                            sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    reportEditorHelper.selectAllCheckboxesInARegion(regionBounds.get(key), value);
                    break;
                case "CERVIX LENGTH":
                case "OVERALL LENGTH":
                case "FUNNEL LENGTH":
                case "FUNCTIONAL LENGTH":
                case "DILATION OF INTERNAL OS":
                case "FUNNEL WIDTH":
                case "CERVICAL INDEX":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    if (key.equalsIgnoreCase("Polyp Height")) {
                        Common.sleep(2, TimeUnit.SECONDS);
                        reportEntries.put("Polyp volume", reportEditorHelper.getFieldValue("Polyp volume"));
                    }
                    break;
            }
        }
        fillMassDetails(reportEntries);
        return reportEntries;
    }

    private Map<String, String> fillOvaryTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "OVARY":
                    regionBounds.putIfAbsent(key,
                            sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    entry.setValue(reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
                    break;
                case "OVARIAN PATHOLOGY":
                case "FINDING":
                case "RIGHT OVARY CYSTS":
                case "LEFT OVARY CYSTS":
                case "ADNEXA":
                    regionBounds.putIfAbsent(key,
                            sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    reportEditorHelper.selectAllCheckboxesInARegion(regionBounds.get(key), value);
                    break;
                case "OVARIAN VENOUS FLOW":
                case "OVARIAN ARTERIAL FLOW":
                case "END DIASTOLIC FLOW":
                case "ASSESSMENT":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
                case "FREE FLUID1":
                case "FREE FLUID2":
                    value = reportEditorHelper.selectRandomValueFromDropdown(key, value);
                    entry.setValue("Ignore");
                    String freeFluid = reportEntries.get("Free Fluid");
                    freeFluid = freeFluid.isEmpty() ? value : freeFluid + ", " + value;
                    reportEntries.put("Free Fluid", freeFluid);
                    break;
                case "OVARY SIZE":
                    String[] ovarySize = entry.getValue().split(" x ");
                    reportEditorHelper.enterValueInTextbox("Length", ovarySize[0]);
                    reportEditorHelper.enterValueInTextbox("Width", ovarySize[1]);
                    reportEditorHelper.enterValueInTextbox("Height", ovarySize[2]);
                    break;
                case "S/D RATIO":
                case "RESISTIVITY INDEX":
                case "PULSATILITY INDEX":
                case "PEAK VELOCITY":
                case "FOLLICLE LENGTH":
                case "FOLLICLE WIDTH":
                case "FOLLICLE HEIGHT":
                    reportEditorHelper.enterValueInTextbox(key.replaceAll("/", " "), value);
                    break;
                case "OVARY VOLUME":
                    reportEditorHelper.enterValueInTextbox(key.split(" ")[1].trim(), value);
                    break;
                case "CYST SIZE":
                    String[] cystSize = entry.getValue().split(" x ");
                    reportEditorHelper.selectCheckbox("Length");
                    reportEditorHelper.enterValueInTextbox("Length", cystSize[0]);
                    reportEditorHelper.enterValueInTextbox("Width", cystSize[1]);
                    reportEditorHelper.enterValueInTextbox("Height", cystSize[2]);
                    reportEntries.put("Cyst Volume", reportEditorHelper.getFieldValue("Volume"));
                    break;
                case "CYST COUNT":
                case "FOLLICLE COUNT":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    Common.sleep(1, TimeUnit.SECONDS);
                    break;
            }
        }
        fillMassDetails(reportEntries);
        return reportEntries;
    }

    private Map<String, String> fillFallopianTubeTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "FALLOPIAN TUBE":
                case "INVOLVEMENT":
                case "MOTION TENDERNESS BY VP":
                case "OVARIAN INVOLVEMENT":
                    regionBounds.putIfAbsent(key,
                            sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    entry.setValue(reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
                    break;
                case "CUL DE SAC FLUID":
                case "FLUID":
                    regionBounds.putIfAbsent(key,
                            sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    if (value.equalsIgnoreCase("None")) {
                        value = reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value.replace("/", " ").trim());
                    } else {
                        value = reportEditorHelper.selectRandomValueFromDropdown(key, value);
                    }
                    entry.setValue(value);
                    break;
                case "FINDINGS":
                    regionBounds.putIfAbsent(key,
                            sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    reportEditorHelper.selectAllCheckboxesInARegion(regionBounds.get(key), value);
                    break;
            }
        }
        fillMassDetails(reportEntries);
        return reportEntries;
    }

    private Map<String, String> fillCulDeSacTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "CUL DE SAC FLUID":
                case "ECHOGENICITY":
                case "LOCULATIONS":
                case "BOWEL PATTERN":
                case "PAPILLARITIES ON PELVIC WALL":
                case "MOTION TENDERNESS BY VP":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    entry.setValue(reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
                    break;
                case "PATHOLOGY":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    reportEditorHelper.selectAllCheckboxesInARegion(regionBounds.get(key), value);
                    break;
            }
        }
        fillMassDetails(reportEntries);
        return reportEntries;
    }

    private Map<String, String> fillKidneyBladderTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "BLADDER":
                case "ECHOGENICITY":
                case "BLADDER VOLUME":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    entry.setValue(reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
                    break;
                case "RIGHT KIDNEY: KIDNEY":
                case "LEFT KIDNEY: KIDNEY":
                case "RIGHT KIDNEY: URETAL PATENCY TEST":
                case "LEFT KIDNEY: URETAL PATENCY TEST":
                    String region = key.split(":")[0].trim();
                    String field = key.split(":")[1].trim();
                    if (!regionBounds.containsKey(region))
                        regionBounds.putIfAbsent(region,
                                sikuliActions.getExactRegionBounds(ReportElements + region + "_Region.PNG"));
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdownInARegion(regionBounds.get(region), field, value));
                    break;
                case "RIGHT KIDNEY: LENGTH":
                case "RIGHT KIDNEY: WIDTH":
                case "RIGHT KIDNEY: TRANSVERSE":
                case "LEFT KIDNEY: LENGTH":
                case "LEFT KIDNEY: WIDTH":
                case "LEFT KIDNEY: TRANSVERSE":
                    region = key.split(":")[0].trim();
                    field = key.split(":")[1].trim();
                    if (!regionBounds.containsKey(region))
                        regionBounds.putIfAbsent(region,
                                sikuliActions.getExactRegionBounds(ReportElements + region + "_Region.PNG"));
                    sikuliActions.typeTextInField(regionBounds.get(region), ReportElements + field + "_Textbox.PNG", value);
                    reportEditorHelper.enterValueInTextboxFromARegion(regionBounds.get(region), field, value);
                    break;
            }
        }
        fillMassDetails(reportEntries);
        return reportEntries;
    }

    private Map<String, String> fillEctopicTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "ECTOPIC LOCATION":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    entry.setValue(reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
                    break;
                case "GESTATIONAL SAC":
                case "FETAL HEART ACTIVITY":
                case "ECTOPIC VIABILITY":
                case "UTERINE CHANGES":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
                case "ECTOPIC TYPE":
                case "ECTOPIC ORIENTATION":
                case "ECTOPIC DIRECTION":
                    value = reportEditorHelper.selectRandomValueFromDropdown(key, value);
                    entry.setValue("Ignore");
                    String ectopicLocation = reportEntries.get("Location");
                    ectopicLocation = ectopicLocation.isEmpty() ? value : ectopicLocation + ", " + value;
                    reportEntries.put("Location", ectopicLocation);
                    break;
                case "ECTOPIC PREGNANCY LENGTH":
                case "ECTOPIC PREGNANCY WIDTH":
                case "ECTOPIC PREGNANCY HEIGHT":
                case "CROWN RUMP LENGTH":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "GESTATIONAL AGE":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "ECTOPIC PREGNANCY VOLUME":
                    reportEntries.put(key, reportEditorHelper.getFieldValue(key));
                    robotActions.escape();
                    robotActions.pressTabKey();
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillFavoritesTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            reportEditorHelper.enterValueInTextbox(key, value);
        }
        return reportEntries;
    }

    private Map<String, String> fillCodesTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            reportEditorHelper.enterValueInTextbox(key, value);
            sikuliActions.waitForElementToExist(ReportElements + "Loading.PNG", 5);
            sikuliActions.waitForElementInvisibility(ReportElements + "Loading.PNG", 30);
            robotActions.pressTabKey();
            Common.sleep(1, TimeUnit.SECONDS);
            robotActions.pressTabKey();
            Common.sleep(1, TimeUnit.SECONDS);
            robotActions.pressDownArrowKey();
            sikuliActions.click(ReportElements + "Add" + "_Button.PNG");
        }
        return reportEntries;
    }

    private Map<String, String> fillMedicationsOrAllergiesTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String[] values = value.split("\\.");
            for (String val : values) {
                reportEditorHelper.enterValueInTextbox(key, val.trim());
                robotActions.pressTabKey();
                Common.sleep(1, TimeUnit.SECONDS);
                robotActions.pressDownArrowKey();
                sikuliActions.click(ReportElements + "Add" + "_Button.PNG");
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillCommentsTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "RESCAN ONLY IF CLINICALLY NECESSARY":
                case "WOULD USE SONO EDC":
                case "RECALCULATE OR REDRAW TRISCREEN IF APPLICABLE":
                case "FETUS IS NORMAL":
                case "FETUS IS LARGER THAN EXPECTED FOR STATE EDC":
                case "FETUS IS MUCH SMALLER THAN EXPECTED FOR STATED EDC":
                case "BILATERAL PYELECTASIS IS OBSERVED WITH THE RIGHT KIDNEY MEASURING":
                case "WARRANTS FOLLOW-UP EXAM":
                case "NO OTHER ANOMALIES WERE OBSERVED":
                case "A 2-VESSEL UMBILICAL CORD WAS OBSERVED":
                case "A 3-VESSEL UMBILICAL CORD IS OBSERVED":
                case "THE HEART AND KIDNEYS APPEAR NORMAL":
                case "ACTIVE FETAL MOVEMENT WAS OBSERVED DURING THE EXAMINATION":
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    break;
                case "REPEAT SONOGRAM":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key, sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    entry.setValue(reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillExaminationInformationTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "EXAMINATION TYPE1":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));

                    reportEntries.put("Examination type", reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
                    entry.setValue("Ignore");
                    break;
                case "EXAMINATION TYPE2":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    for (String valueComponent : value.split(",")) {
                        reportEditorHelper.selectCheckboxInARegion(valueComponent.replace("/", " ").trim(), regionBounds.get(key));
                        String examinationType = reportEntries.get("Examination type");
                        reportEntries.put("Examination type", examinationType + ", " + valueComponent.trim());
                    }
                    reportEntries.put(key, "Ignore");
                    break;
                case "STUDY QUALITY":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key, sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    if (value.equalsIgnoreCase("Technically limited")) {
                        entry.setValue("Technically limited because of " + reportEditorHelper.selectRandomValueFromDropdown("Technically limited", reportEntries.get("Technically limited")));
                    } else {
                        entry.setValue(reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
                    }
                    reportEntries.put("Technically limited", "Ignore");
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillMultipleGestationTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "TYPE OF GESTATION":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "GESTATION DESCRIPTION":
                    reportEditorHelper.enterTextAndSelectCheckbox(key, value);
                    break;
                case "DIVIDING MEMBRANE":
                case "DIVIDING MEMBRANE THICKNESS":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
                case "TWINS":
                case "TRIPLETS":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    entry.setValue(reportEditorHelper.selectRandomRadioButtonFromARegion(regionBounds.get(key), value));
                    break;
            }
        }
        return reportEntries;
    }

    private void fillMassDetails(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        Rectangle regionBound;
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "BLADDER MASS":
                case "RIGHT KIDNEY MASS":
                case "LEFT KIDNEY MASS":
                case "CUL DE SAC MASS":
                case "RIGHT FALLOPIAN TUBE MASS":
                case "LEFT FALLOPIAN TUBE MASS":
                case "RIGHT OVARY MASS":
                case "LEFT OVARY MASS":
                case "CERVIX MASS":
                case "ENDOMETRIUM MASS":
                case "UTERINE MASS":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    regionBound = regionBounds.get(key);
                    reportEditorHelper.selectAllCheckboxesInARegion(regionBound, value);
                    break;
                case "BLADDER: MASS APPEARANCE":
                case "BLADDER: MASS SHAPE":
                case "RIGHT KIDNEY: MASS APPEARANCE":
                case "RIGHT KIDNEY: MASS SHAPE":
                case "LEFT KIDNEY: MASS APPEARANCE":
                case "LEFT KIDNEY: MASS SHAPE":
                case "MASS APPEARANCE":
                case "MASS SHAPE":
                case "MASS APPEARANCE1":
                case "MASS APPEARANCE2":
                    entry.setValue(reportEditorHelper.selectRandomValueFromExactDropdown(key.contains(":")
                            ? key.split(":")[1].trim()
                            : key, value));
                    break;
                case "BLADDER: MASS ON":
                case "BLADDER: MASS ORIENTATION":
                case "BLADDER: MASS DIRECTION":
                case "BLADDER: MASS POSITION":
                case "RIGHT KIDNEY: MASS ON":
                case "RIGHT KIDNEY: MASS POLE":
                case "RIGHT KIDNEY: MASS ORIENTATION":
                case "RIGHT KIDNEY: MASS DIRECTION":
                case "RIGHT KIDNEY: MASS POSITION":
                case "LEFT KIDNEY: MASS ON":
                case "LEFT KIDNEY: MASS POLE":
                case "LEFT KIDNEY: MASS ORIENTATION":
                case "LEFT KIDNEY: MASS DIRECTION":
                case "LEFT KIDNEY: MASS POSITION":
                    value = reportEditorHelper.selectRandomValueFromDropdown(key.split(":")[1].trim(), value);
                    entry.setValue("Ignore");
                    String massLocation = reportEntries.get(key.split(":")[0].trim() + " Mass Location");
                    reportEntries.put(key.split(":")[0].trim() + " Mass Location",
                            massLocation + value.toLowerCase() + ", ");
                    break;
                case "BLADDER: MASS MID":
                case "RIGHT KIDNEY: MASS MID":
                case "LEFT KIDNEY: MASS MID":
                    reportEditorHelper.selectCheckbox(key.split(":")[1].trim());
                    entry.setValue("Ignore");
                    massLocation = reportEntries.get(key.split(":")[0].trim() + " Mass Location");
                    reportEntries.put(key.split(":")[0].trim() + " Mass Location",
                            massLocation + key.split(":")[1].trim().split(" ")[1].toLowerCase());
                    break;
                case "BLADDER: MASS LENGTH":
                case "BLADDER: MASS WIDTH":
                case "BLADDER: MASS HEIGHT":
                case "BLADDER: MASS VOLUME":
                case "RIGHT KIDNEY: MASS LENGTH":
                case "RIGHT KIDNEY: MASS WIDTH":
                case "RIGHT KIDNEY: MASS HEIGHT":
                case "RIGHT KIDNEY: MASS VOLUME":
                case "LEFT KIDNEY: MASS LENGTH":
                case "LEFT KIDNEY: MASS WIDTH":
                case "LEFT KIDNEY: MASS HEIGHT":
                case "LEFT KIDNEY: MASS VOLUME":
                    key = key.split(":")[1].trim();
                    if (key.equalsIgnoreCase("Mass Length")) {
                        reportEditorHelper.selectCheckbox(key);
                        reportEditorHelper.selectCheckbox(key);
                    }
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "BLADDER MASS: MASS COUNT":
                case "RIGHT KIDNEY MASS: MASS COUNT":
                case "LEFT KIDNEY MASS: MASS COUNT":
                    String region = key.split(":")[0].trim();
                    key = key.split(":")[1].trim();
                    value = getRandomValue(value);
                    if (key.contains("Bladder")) {
                        reportEditorHelper.enterValueInTextboxFromARegion(regionBounds.get(region), key, value);
                    } else {
                        reportEditorHelper.enterValueInTextboxFromARegion(regionBounds.get(region), "Kidney " + key, value);
                    }
                    Common.sleep(1, TimeUnit.SECONDS);
                    break;
                case "MASS ON":
                case "MASS ORIENTATION":
                case "MASS DIRECTION":
                case "MASS POSITION":
                    value = reportEditorHelper.selectRandomValueFromExactDropdown(key, value);
                    entry.setValue("Ignore");
                    massLocation = reportEntries.get("Mass Location");
                    reportEntries.put("Mass Location", massLocation + value.toLowerCase() + ", ");
                    break;
                case "MASS MID":
                    reportEditorHelper.selectCheckbox(key);
                    entry.setValue("Ignore");
                    massLocation = reportEntries.get("Mass Location");
                    reportEntries.put("Mass Location", massLocation + key.split(" ")[1].toLowerCase());
                    break;
                case "MASS LENGTH":
                case "MASS WIDTH":
                case "MASS HEIGHT":
                case "MASS VOLUME":
                    if (key.equalsIgnoreCase("Mass Length")) {
                        reportEditorHelper.selectCheckbox(key);
                        reportEditorHelper.selectCheckbox(key);
                    }
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "MASS COUNT":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    Common.sleep(1, TimeUnit.SECONDS);
                    break;
            }
        }
    }

    private void fillFibroidsDetails(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (entry.getKey().toUpperCase()) {
                case "FIBROIDS":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    reportEditorHelper.selectAllCheckboxesInARegion(regionBounds.get(key), value);
                    break;
                case "FIBROIDS CHARACTERISTIC":
                case "UTERINE CAVITY":
                case "RETROPLACENTAL":
                    entry.setValue(reportEditorHelper.selectRandomValueFromExactDropdown(key, value));
                    break;
                case "FIBROID ORIENTATION":
                case "FIBROID DIRECTION":
                case "FIBROID LOCATION":
                    value = reportEditorHelper.selectRandomValueFromExactDropdown(key, value);
                    entry.setValue("Ignore");
                    String fibroidsLocation = reportEntries.get("Fibroids location");
                    reportEntries.put("Fibroids location", fibroidsLocation + value + ", ");
                    break;
                case "FIBROID MID":
                    reportEditorHelper.selectCheckbox(key);
                    entry.setValue("Ignore");
                    fibroidsLocation = reportEntries.get("Fibroids location");
                    reportEntries.put("Fibroids location", fibroidsLocation + key.split(" ")[1]);
                    break;
                case "FIBROID LENGTH":
                case "FIBROID WIDTH":
                case "FIBROID HEIGHT":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    if (key.equalsIgnoreCase("Fibroid Width")) {
                        reportEntries.put("Fibroid Volume", reportEditorHelper.getFieldValue("Fibroid Volume"));
                    }
                    break;
            }
        }
    }

    private Map<String, String> fillAbnormalities(String fieldName) {
        Map<String, String> abnormalitiesEntries = new ReportEditorHelper().getReportEntries(reportName, sectionName,
                fieldName + " Abnormalities");
        if (abnormalitiesEntries != null) {
            Rectangle regionBounds = sikuliActions
                    .getExactRegionBounds(ReportElements + fieldName + " Abnormalities" + "_Region.PNG");
            for (Map.Entry<String, String> entry : abnormalitiesEntries.entrySet()) {
                String key = entry.getKey().contains(":") ? entry.getKey().split(":")[1].trim() : entry.getKey();
                reportEditorHelper.selectCheckboxInARegion(key, regionBounds);
            }
        }
        return abnormalitiesEntries;
    }

    @Override
    public Map<String, String> getTransformedReportValues(Map<String, String> reportEntries) {
        String phrase;
        String placentalLocationPhrase = null;
        int score = 0;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String initialWeight = "0.00";
        String initialHeight = "0.00";
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        boolean isPlacentalLocation = false;
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toLowerCase();
            switch (entry.getKey().toUpperCase().split(":")[0]) {
                case "WEIGHT":
                case "PRE PREGNANCY WEIGHT":
                    double weightValue = Double.parseDouble(entry.getValue());
                    decimalFormat.setRoundingMode(RoundingMode.UP);
                    weightValue = weightValue / 2.205;
                    initialWeight = entry.setValue(decimalFormat.format(weightValue));
                    break;
                case "HEIGHT":
                    double heightValue = Double.parseDouble(entry.getValue());
                    decimalFormat.setRoundingMode(RoundingMode.DOWN);
                    heightValue = heightValue / 39.37;
                    initialHeight = entry.setValue(decimalFormat.format(heightValue));
                    break;
                case "BMI":
                    double weight = Double.parseDouble(initialWeight);
                    double height = Double.parseDouble(initialHeight);
                    double bmiValue = weight * 703 / (height * height);
                    decimalFormat.setRoundingMode(RoundingMode.UP);
                    entry.setValue(decimalFormat.format(bmiValue));
                    break;
                case "PATIENT NAME":
                    String patientNameValue = entry.getValue().replace("^", ", ");
                    entry.setValue(patientNameValue);
                    break;
                case "EXAMINATION DATE":
                case "DOB":
                case "CONCEPTION DATE":
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
                    value = " " + entry.getValue() + " ";
                    entry.setValue(value);
                    break;
                case "FETAL MOVEMENT AND ACTIVITY":
                    phrase = "Patient admits %s %s";
                    entry.setValue(String.format(phrase, value, key));
                    break;
                case "VISIBLE INTRACRANIAL ANATOMY":
                case "NEURAL TUBE":
                case "CARDIAC STRUCTURES":
                case "GASTROINTESTINAL AND GENITOURINARY SYSTEMS":
                    phrase = "Evaluation of fetal anatomy suggests %s %s";
                    entry.setValue(String.format(phrase, value, key));
                    break;
                case "MATERNAL ANATOMY":
                    phrase = "Evaluation of the %s %s";
                    entry.setValue(String.format(phrase, key.toLowerCase(), value));
                    break;
                case "REVEALS A UTERINE DIDELPHIS":
                    phrase = "The uterine ultrasound %s %s";
                    entry.setValue(String.format(phrase, key.toLowerCase(), value));
                    break;
                case "PLACENTAL LOCATION":
                    String orientation = reportEntries.get(entry.getKey().split(":")[0] + ": Orientation");
                    String direction = reportEntries.get(entry.getKey().split(":")[0] + ": Direction");
                    String fundal = reportEntries.get(entry.getKey().split(":")[0] + ": Fundal");
                    String previa = reportEntries.get(entry.getKey().split(":")[0] + ": Previa");
                    placentalLocationPhrase = fundal.isEmpty()
                            ? String.format("The placental location is %s, %s, %s", orientation, direction, previa)
                            : String.format("The placental location is %s, %s, %s, %s", orientation, direction, fundal,
                            previa);
                    isPlacentalLocation = true;
                    break;
                case "BREATHING":
                case "MOVEMENT":
                case "TONE":
                case "AFV":
                case "FHR REACTIVITY":
                    score += Integer.parseInt(entry.getValue());
                    break;
                case "BPP":
                case "TOTAL SCORE":
                    String total = reportEntries.get("Version").equalsIgnoreCase("BPP") ? "8" : "10";
                    entry.setValue(score + " / " + total);
                    break;
                case "AFI RANK":
                    double afiRank = (double) Math.round(Double.parseDouble(value));
                    value = (int) afiRank + " %";
                    entry.setValue(value);
                    break;
                case "PACS":
                    phrase = "Sinus rhythm with %s PACs";
                    entry.setValue(String.format(phrase, entry.getValue()));
                    break;
                case "PVCS":
                    phrase = "Sinus rhythm with %s PVCs";
                    entry.setValue(String.format(phrase, entry.getValue()));
                    break;
                case "ATRIAL FLUTTER":
                    phrase = "Atrial flutter with %s AV conduction";
                    entry.setValue(String.format(phrase, entry.getValue()));
                    break;
                case "SECOND DEGREE HEART BLOCK":
                    phrase = "Second degree %s heart block";
                    entry.setValue(String.format(phrase, entry.getValue()));
                    break;
                case "GESTATIONAL AGE":
                    String[] ectopicGASplit = entry.getValue().split("[a-z]");
                    double days = (Double.parseDouble(ectopicGASplit[0]) * 7) + Double.parseDouble(ectopicGASplit[1]);
                    entry.setValue(Double.toString(days));
                    break;
            }
        }
        if (isPlacentalLocation) {
            reportEntries.remove("Placental Location: Orientation");
            reportEntries.remove("Placental Location: Direction");
            reportEntries.remove("Placental Location: Fundal");
            reportEntries.remove("Placental Location: Previa");
            reportEntries.put("Placental Location", placentalLocationPhrase);
        }
        if (reportEntries.containsKey("Systolic blood pressure")
                && reportEntries.containsKey("Diastolic blood pressure")) {
            reportEntries.put("B/P", String.format("%s / %s", reportEntries.get("Systolic blood pressure"),
                    reportEntries.get("Diastolic blood pressure")));
            reportEntries.remove("Systolic blood pressure");
            reportEntries.remove("Diastolic blood pressure");
        }
        return reportEntries;
    }
}