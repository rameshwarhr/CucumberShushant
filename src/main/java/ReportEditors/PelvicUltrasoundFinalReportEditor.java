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

public class PelvicUltrasoundFinalReportEditor implements ReportEditor {

    SikuliActions sikuliActions;
    RobotActions robotActions;
    String sectionName;
    String tabName;
    String ReportElements;

    public PelvicUltrasoundFinalReportEditor(String sectionName, String tabName) {
        sikuliActions = new SikuliActions();
        robotActions = new RobotActions();
        this.sectionName = sectionName;
        this.tabName = tabName;
        ReportElements = USER_WORK_DIR + ReportScreens + "Pelvic Ultrasound Final Report\\" + sectionName + "\\"
                + tabName + "\\";
    }

    @Override
    public Map<String, String> fillReport(Map<String, String> reportEntries) {
        Map<String, String> filledReportEntries = new LinkedHashMap<>();
        switch (tabName.toUpperCase()) {
            case "RIGHT F-TUBE":
            case "LEFT F-TUBE":
                filledReportEntries = fillFallopianTubeTab(reportEntries);
                break;
            case "BLADDER":
                filledReportEntries = fillBladderTab(reportEntries);
                break;
            case "RIGHT KIDNEY":
            case "LEFT KIDNEY":
                filledReportEntries = fillKidneyTab(reportEntries);
                break;
            case "RECOMMENDATIONS":
                filledReportEntries = fillRecommendationsTab(reportEntries);
                break;
            case "CODES":
                filledReportEntries = fillCodesTab(reportEntries);
                break;
            case "FREE TEXT":
            case "COPY TO":
                filledReportEntries = fillOtherTab(reportEntries);
                break;
            case "EXAMINATION INFORMATION":
                filledReportEntries = fillExaminationInformationTab(reportEntries);
                break;
            case "PATIENT INFORMATION":
                filledReportEntries = fillPatientInformation(reportEntries);
                break;
            case "GENERAL":
                switch (sectionName.toUpperCase()) {
                    case "UTERUS":
                        filledReportEntries = fillUterusGeneralTab(reportEntries);
                        break;
                    case "ENDOMETRIUM":
                        filledReportEntries = fillEndometriumGeneralTab(reportEntries);
                        break;
                    case "CERVIX":
                        filledReportEntries = fillCervixGeneralTab(reportEntries);
                        break;
                    case "RIGHT OVARY":
                    case "LEFT OVARY":
                        filledReportEntries = fillOvaryGeneralTab(reportEntries);
                        break;
                    case "CUL DE SAC":
                        filledReportEntries = fillCulDeSacGeneralTab(reportEntries);
                        break;
                    case "PROSTATE":
                        filledReportEntries = fillProstateGeneralTab(reportEntries);
                        break;
                }
                break;
        }
        return filledReportEntries;
    }

    private Map<String, String> fillUterusGeneralTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            System.out.println("Key: " + key);
            String value = entry.getValue();
            System.out.println("Value: " + value);
            switch (entry.getKey().toUpperCase()) {
                case "NON GRAVID":
                case "PREGNANT":
                case "REMOVED":
                case "POST MENOPAUSAL":
                case "NORMAL":
                case "ENLARGED":
                case "NO MASS SEEN":
                case "ONE MASS SEEN":
                case "MULTIPLE MASSES SEEN. LARGEST CHARACTERIZED":
                case "NO FIBROIDS SEEN":
                case "ONE FIBROID SEEN":
                case "MULTIPLE FIBROIDS SEEN. LARGEST CHARACTERIZED":
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    break;
                case "UTERINE PATHOLOGY: NONE":
                case "UTERINE PATHOLOGY: FIBROIDS":
                case "UTERINE PATHOLOGY: LIPOMA":
                case "UTERINE PATHOLOGY: ADENOMYOSIS":
                case "UTERINE PATHOLOGY: VASCULAR ANOMALIES":
                case "UTERINE PATHOLOGY: SUSPECTED MALIGNANCY":
                    sikuliActions.selectCheckBox(ReportElements + key.split(":")[1].trim() + "_Checkbox.PNG");
                    break;
                case "POSITION":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    for (String valueComponent : value.split(",")) {
                        sikuliActions.selectCheckBox(
                                ReportElements + valueComponent.replace("/", " ").trim() + "_Checkbox.PNG",
                                regionBounds.get(key));
                    }
                    break;
                case "UTERUS LENGTH":
                case "UTERUS WIDTH":
                case "UTERUS HEIGHT":
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    entry.setValue("Ignore");
                    String uterusSize = reportEntries.get("Uterus Size");
                    uterusSize = uterusSize.isEmpty() ? value : uterusSize + " x " + value;
                    reportEntries.put("Uterus Size", uterusSize);
                    break;
                case "TOTAL NUMBER OF MASSES":
                case "TOTAL NUMBER OF FIBROIDS":
                case "MASS COUNT":
                case "FIBROID COUNT":
                    sikuliActions.typeTextInExactField(ReportElements + key + "_Textbox.PNG", value);
                    robotActions.pressTabKey();
                    Common.sleep(2, TimeUnit.SECONDS);
                    break;
                case "FIBROID ORIENTATION":
                case "FIBROID DIRECTION":
                case "FIBROID POSITION":
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue("Ignore");
                    String fibroidsLocation = reportEntries.get("Fibroid Location");
                    reportEntries.put("Fibroid Location", fibroidsLocation + value.toLowerCase() + ", ");
                    break;
                case "FIBROID MID":
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    entry.setValue("Ignore");
                    fibroidsLocation = reportEntries.get("Fibroid Location");
                    reportEntries.put("Fibroid Location", fibroidsLocation + key.split(" ")[1].toLowerCase());
                    break;
                case "FIBROID CHARACTERISTIC":
                case "UTERINE CAVITY":
                case "MASS APPEARANCE1":
                case "MASS APPEARANCE2":
                case "MASS SHAPE":
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue(value.toLowerCase());
                    break;
                case "UTERINE ANOMALIES":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    value = getRandomValue(value);
                    sikuliActions.selectRadioFromRegion(ReportElements + value + "_Radio.PNG", regionBounds.get(key));
                    entry.setValue(value);
                    break;
                case "MASS ORIENTATION":
                case "MASS DIRECTION":
                case "MASS POSITION":
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue("Ignore");
                    String massLocation = reportEntries.get("Mass Location");
                    reportEntries.put("Mass Location", massLocation + value.toLowerCase() + ", ");
                    break;
                case "MASS MID":
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    entry.setValue("Ignore");
                    massLocation = reportEntries.get("Mass Location");
                    reportEntries.put("Mass Location", massLocation + key.split(" ")[1].toLowerCase());
                    break;
                case "MASS LENGTH":
                case "MASS WIDTH":
                case "MASS HEIGHT":
                    if (key.equalsIgnoreCase("Mass Length")) {
                        sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                        sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    }
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    entry.setValue("Ignore");
                    String massSize = reportEntries.get("Mass Size");
                    massSize = massSize.isEmpty() ? value : massSize + " x " + value;
                    reportEntries.put("Mass Size", massSize);
                    break;
                case "FIBROID LENGTH":
                case "FIBROID WIDTH":
                case "FIBROID HEIGHT":
                    if (key.equalsIgnoreCase("Fibroid Length")) {
                        sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                        sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    }
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    entry.setValue("Ignore");
                    String fibroidSize = reportEntries.get("Fibroid Size");
                    fibroidSize = fibroidSize.isEmpty() ? value : fibroidSize + " x " + value;
                    reportEntries.put("Fibroid Size", fibroidSize);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillEndometriumGeneralTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            System.out.println("Key: " + key);
            String value = entry.getValue();
            System.out.println("Value: " + value);
            switch (entry.getKey().toUpperCase()) {
                case "NORMAL":
                case "NO MASS SEEN":
                case "ONE MASS SEEN":
                case "MULTIPLE MASSES SEEN. LARGEST CHARACTERIZED":
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    break;
                case "IUD LOCATION: NORMAL":
                case "IUD LOCATION: MID CAVITY":
                case "IUD LOCATION: CERVICAL":
                case "IUD LOCATION: FUNDAL":
                case "IUD LOCATION: NOT SEEN":
                case "IUD LOCATION: EXTRAUTERINE LOCATION":
                    sikuliActions.selectCheckBox(ReportElements + key.split(":")[1].trim() + "_Checkbox.PNG");
                    break;
                case "POLYP LENGTH":
                case "POLYP WIDTH":
                case "POLYP HEIGHT":
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    entry.setValue("Ignore");
                    String polypSize = reportEntries.get("Polyp Size");
                    polypSize = polypSize.isEmpty() ? value : polypSize + " x " + value;
                    reportEntries.put("Polyp Size", polypSize);
                    break;
                case "TOTAL NUMBER OF MASSES":
                case "MASS COUNT":
                    sikuliActions.typeTextInExactField(ReportElements + key + "_Textbox.PNG", value);
                    robotActions.pressTabKey();
                    Common.sleep(2, TimeUnit.SECONDS);
                    break;
                case "ENDOMETRIAL SIZE":
                case "ENDOMETRIAL FLUID TYPE":
                case "MASS APPEARANCE":
                case "MASS SHAPE":
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue(value.toLowerCase());
                    break;
                case "ENDOMETRIAL FLUID VOLUME QUANTITY":
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue(value.toLowerCase());
                    String endometrialFluidPhrase = String.format(
                            "There is a %s amount of %s fluid seen within the endometrium",
                            reportEntries.get("Endometrial fluid volume quantity"),
                            reportEntries.get("Endometrial fluid type"));
                    reportEntries.put("Endometrial fluid volume quantity", "Ignore");
                    reportEntries.put("Endometrial fluid type", "Ignore");
                    reportEntries.put("Endometrial fluid", endometrialFluidPhrase);
                    break;
                case "ENDOMETRIAL THICKNESS":
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    break;
                case "TEXTURE":
                case "ENDOMETRIAL CAVITY":
                case "TAMOXIFEN EFFECT":
                case "RETAINED PRODUCTS":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    value = getRandomValue(value);
                    sikuliActions.selectRadioFromRegion(ReportElements + value + "_Radio.PNG", regionBounds.get(key));
                    if (key.equalsIgnoreCase("Endometrial cavity")) {
                        if (entry.getValue().equalsIgnoreCase("Normal")) {
                            entry.setValue("The endometrial cavity is normal");
                        } else {
                            entry.setValue("The endometrium cavity is normal");
                        }
                    }
                    entry.setValue(value.toLowerCase());
                    break;
                case "MASS ON":
                case "MASS ORIENTATION":
                case "MASS DIRECTION":
                case "MASS POSITION":
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue("Ignore");
                    String massLocation = reportEntries.get("Mass Location");
                    reportEntries.put("Mass Location", massLocation + value.toLowerCase() + ", ");
                    break;
                case "MASS MID":
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    entry.setValue("Ignore");
                    massLocation = reportEntries.get("Mass Location");
                    reportEntries.put("Mass Location", massLocation + key.split(" ")[1].toLowerCase());
                    break;
                case "MASS LENGTH":
                case "MASS WIDTH":
                case "MASS HEIGHT":
                    if (key.equalsIgnoreCase("Mass Length")) {
                        sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                        sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    }
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    entry.setValue("Ignore");
                    String massSize = reportEntries.get("Mass Size");
                    massSize = massSize.isEmpty() ? value : massSize + " x " + value;
                    reportEntries.put("Mass Size", massSize);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillCervixGeneralTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            System.out.println("Key: " + key);
            String value = entry.getValue();
            System.out.println("Value: " + value);
            switch (entry.getKey().toUpperCase()) {
                case "NORMAL":
                case "ABNORMAL":
                case "NABOTHIAN CYST":
                case "MULTIPLE NABOTHIAN CYSTS":
                case "SUSPECTED MALIGNANCY":
                case "NO MASS SEEN":
                case "ONE MASS SEEN":
                case "MULTIPLE MASSES SEEN. LARGEST CHARACTERIZED":
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    break;
                case "TOTAL NUMBER OF MASSES":
                case "MASS COUNT":
                    sikuliActions.typeTextInExactField(ReportElements + key + "_Textbox.PNG", value);
                    robotActions.pressTabKey();
                    Common.sleep(2, TimeUnit.SECONDS);
                    break;
                case "NABOTHIAN CYST SIZE":
                case "LARGEST SIZE":
                case "MASS VOLUME":
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    robotActions.enter();
                    break;
                case "MASS ON":
                case "MASS ORIENTATION":
                case "MASS DIRECTION":
                case "MASS POSITION":
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue("Ignore");
                    String massLocation = reportEntries.get("Mass Location");
                    reportEntries.put("Mass Location", massLocation + value.toLowerCase() + ", ");
                    break;
                case "MASS MID":
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    entry.setValue("Ignore");
                    massLocation = reportEntries.get("Mass Location");
                    reportEntries.put("Mass Location", massLocation + key.split(" ")[1].toLowerCase());
                    break;
                case "MASS APPEARANCE":
                case "MASS SHAPE":
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue(value.toLowerCase());
                    break;
                case "MASS LENGTH":
                case "MASS WIDTH":
                case "MASS HEIGHT":
                    if (key.equalsIgnoreCase("Mass Length")) {
                        sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                        sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    }
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    entry.setValue("Ignore");
                    String massSize = reportEntries.get("Mass Size");
                    massSize = massSize.isEmpty() ? value : massSize + " x " + value;
                    reportEntries.put("Mass Size", massSize);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillOvaryGeneralTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            System.out.println("Key: " + key);
            String value = entry.getValue();
            System.out.println("Value: " + value);
            switch (entry.getKey().toUpperCase()) {
                case "NO MASS SEEN":
                case "ONE MASS SEEN":
                case "MULTIPLE MASSES SEEN. LARGEST CHARACTERIZED":
                case "MULTIPLE CYSTS SEEN. LARGEST CHARACTERIZED":
                case "SIMPLE CYST":
                case "HEMORRHAGIC CYST":
                case "DERMOID":
                case "ENDOMETRIOMA":
                case "SUSPECTED MALIGNANCY":
                case "CYSTADENOMA":
                case "FIBROMA":
                case "UNREMARKABLE":
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    break;
                case "OVARY FLOW: NORMAL":
                case "OVARIAN PATHOLOGY: NORMAL":
                    sikuliActions.selectCheckBox(ReportElements + key.split(":")[1].trim() + "_Checkbox.PNG");
                    break;
                case "OVARY":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    value = getRandomValue(value);
                    sikuliActions.selectRadioFromRegion(ReportElements + value + "_Radio.PNG", regionBounds.get(key));
                    entry.setValue(value);
                    break;
                case "FREE FLUID1":
                case "FREE FLUID2":
                case "END DIASTOLIC FLOW":
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue(value);
                    break;
                case "OVARY LENGTH":
                case "OVARY WIDTH":
                case "OVARY HEIGHT":
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    entry.setValue("Ignore");
                    String ovarySize = reportEntries.get("Ovary Size");
                    ovarySize = ovarySize.isEmpty() ? value : ovarySize + " x " + value;
                    reportEntries.put("Ovary Size", ovarySize);
                    break;
                case "OVARY VOLUME":
                    entry.setValue(getFieldValue(entry.getKey()));
                    break;
                case "TOTAL NUMBER OF MASSES":
                case "MASS COUNT":
                case "FOLLICLE COUNT":
                case "CYSTS COUNT":
                    sikuliActions.typeTextInExactField(ReportElements + key + "_Textbox.PNG", value);
                    robotActions.pressTabKey();
                    Common.sleep(2, TimeUnit.SECONDS);
                    break;
                case "S/D RATIO":
                case "RESISTIVITY INDEX":
                case "PULSATILITY INDEX":
                case "PEAK VELOCITY":
                    sikuliActions.typeTextInField(ReportElements + key.replaceAll("/", " ") + "_Textbox.PNG", value);
                    break;
                case "MASS ON":
                case "MASS ORIENTATION":
                case "MASS DIRECTION":
                case "MASS POSITION":
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue("Ignore");
                    String massLocation = reportEntries.get("Mass Location");
                    reportEntries.put("Mass Location", massLocation + value.toLowerCase() + ", ");
                    break;
                case "MASS MID":
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    entry.setValue("Ignore");
                    massLocation = reportEntries.get("Mass Location");
                    reportEntries.put("Mass Location", massLocation + key.split(" ")[1].toLowerCase());
                    break;
                case "OVARIAN VENOUS FLOW":
                case "OVARIAN ARTERIAL FLOW":
                case "ASSESSMENT":
                case "MASS APPEARANCE":
                case "MASS SHAPE":
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue(value.toLowerCase());
                    break;
                case "MASS LENGTH":
                case "MASS WIDTH":
                case "MASS HEIGHT":
                    if (!regionBounds.containsKey("Mass"))
                        regionBounds.putIfAbsent("Mass",
                                sikuliActions.getExactRegionBounds(ReportElements + "Mass" + "_Region.PNG"));
                    if (key.equalsIgnoreCase("Mass Length")) {
                        sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG", regionBounds.get("Mass"));
                        sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG", regionBounds.get("Mass"));
                    }
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    entry.setValue("Ignore");
                    String massSize = reportEntries.get("Mass Size");
                    massSize = massSize.isEmpty() ? value : massSize + " x " + value;
                    reportEntries.put("Mass Size", massSize);
                    break;
                case "FOLLICLE LENGTH":
                case "FOLLICLE WIDTH":
                case "FOLLICLE HEIGHT":
                    if (!regionBounds.containsKey("Follicle"))
                        regionBounds.putIfAbsent("Follicle",
                                sikuliActions.getExactRegionBounds(ReportElements + "Follicle" + "_Region.PNG"));
                    if (key.equalsIgnoreCase("Follicle Length")) {
                        sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG", regionBounds.get("Follicle"));
                        sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG", regionBounds.get("Follicle"));
                    }
                    sikuliActions.typeTextInExactField(ReportElements + key + "_Textbox.PNG", value);
                    entry.setValue("Ignore");
                    String follicleSize = reportEntries.get("Follicle Size");
                    follicleSize = follicleSize.isEmpty() ? value : follicleSize + " x " + value;
                    reportEntries.put("Follicle Size", follicleSize);
                    break;
                case "CYST LENGTH":
                case "CYST WIDTH":
                case "CYST HEIGHT":
                    if (!regionBounds.containsKey("Cyst"))
                        regionBounds.putIfAbsent("Cyst",
                                sikuliActions.getExactRegionBounds(ReportElements + "Cyst" + "_Region.PNG"));
                    if (key.equalsIgnoreCase("Cyst Length")) {
                        sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG", regionBounds.get("Cyst"));
						sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG", regionBounds.get("Cyst"));
                    }
                    sikuliActions.typeTextInExactField(ReportElements + key + "_Textbox.PNG", value);
                    entry.setValue("Ignore");
                    String cystSize = reportEntries.get("Cyst Size");
                    cystSize = cystSize.isEmpty() ? value : cystSize + " x " + value;
                    reportEntries.put("Cyst Size", cystSize);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillFallopianTubeTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            System.out.println("Key: " + key);
            String value = entry.getValue();
            System.out.println("Value: " + value);
            switch (entry.getKey().toUpperCase()) {
                case "NO MASS SEEN":
                case "ONE MASS SEEN":
                case "MULTIPLE MASSES SEEN. LARGEST CHARACTERIZED":
                case "ACUTE INFECTION":
                case "CHRONIC HYDROSALPINX":
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    break;
                case "FALLOPIAN TUBE":
                case "MOTION TENDERNESS BY VP":
                case "FLUID":
                case "OVARIAN INVOLVEMENT":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    value = getRandomValue(value.toLowerCase());
                    sikuliActions.selectRadioFromRegion(ReportElements + value + "_Radio.PNG", regionBounds.get(key));
                    entry.setValue(value);
                    break;
                case "TOTAL NUMBER OF MASSES":
                case "MASS COUNT":
                    sikuliActions.typeTextInExactField(ReportElements + key + "_Textbox.PNG", value);
                    robotActions.pressTabKey();
                    Common.sleep(2, TimeUnit.SECONDS);
                    break;
                case "MASS ON":
                case "MASS ORIENTATION":
                case "MASS DIRECTION":
                case "MASS POSITION":
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue("Ignore");
                    String massLocation = reportEntries.get("Mass Location");
                    reportEntries.put("Mass Location", massLocation + value.toLowerCase() + ", ");
                    break;
                case "MASS MID":
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    entry.setValue("Ignore");
                    massLocation = reportEntries.get("Mass Location");
                    reportEntries.put("Mass Location", massLocation + key.split(" ")[1].toLowerCase());
                    break;
                case "MASS LENGTH":
                case "MASS WIDTH":
                case "MASS HEIGHT":
                    if (key.equalsIgnoreCase("Mass Length")) {
                        sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                        sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    }
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    entry.setValue("Ignore");
                    String massSize = reportEntries.get("Mass Size");
                    massSize = massSize.isEmpty() ? value : massSize + " x " + value;
                    reportEntries.put("Mass Size", massSize);
                    break;
                case "MASS APPEARANCE":
                case "MASS SHAPE":
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue(value.toLowerCase());
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillBladderTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            System.out.println("Key: " + key);
            String value = entry.getValue();
            System.out.println("Value: " + value);
            switch (entry.getKey().toUpperCase()) {
                case "NO MASS SEEN":
                case "ONE MASS SEEN":
                case "MULTIPLE MASSES SEEN. LARGEST CHARACTERIZED":
                case "NORMAL":
                case "NORMAL (DETAILED)":
                case "ABNORMAL":
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    break;
                case "ECHOGENICITY":
                case "BLADDER VOLUME":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    value = getRandomValue(value);
                    sikuliActions.selectRadioFromRegion(ReportElements + value + "_Radio.PNG", regionBounds.get(key));
                    entry.setValue(value);
                    break;
                case "LEFT URETERAL JETS":
                case "RIGHT URETERAL JETS":
                case "MASS APPEARANCE":
                case "MASS SHAPE":
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue(value.toLowerCase());
                    break;
                case "BLADDER ENLARGED":
                case "PRE VOID VOLUME":
                case "POST VOID VOLUME":
                    sikuliActions.typeTextInField(ReportElements + key.replaceAll("/", " ") + "_Textbox.PNG", value);
                    break;
                case "TOTAL NUMBER OF MASSES":
                case "MASS COUNT":
                    sikuliActions.typeTextInExactField(ReportElements + key + "_Textbox.PNG", value);
                    robotActions.pressTabKey();
                    Common.sleep(2, TimeUnit.SECONDS);
                    break;
                case "MASS ON":
                case "MASS ORIENTATION":
                case "MASS DIRECTION":
                case "MASS POSITION":
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue("Ignore");
                    String massLocation = reportEntries.get("Mass Location");
                    reportEntries.put("Mass Location", massLocation + value.toLowerCase() + ", ");
                    break;
                case "MASS MID":
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    entry.setValue("Ignore");
                    massLocation = reportEntries.get("Mass Location");
                    reportEntries.put("Mass Location", massLocation + key.split(" ")[1].toLowerCase());
                    break;
                case "MASS LENGTH":
                case "MASS WIDTH":
                case "MASS HEIGHT":
                    if (key.equalsIgnoreCase("Mass Length")) {
                        sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                        sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    }
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    entry.setValue("Ignore");
                    String massSize = reportEntries.get("Mass Size");
                    massSize = massSize.isEmpty() ? value : massSize + " x " + value;
                    reportEntries.put("Mass Size", massSize);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillCulDeSacGeneralTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            System.out.println("Key: " + key);
            String value = entry.getValue();
            System.out.println("Value: " + value);
            switch (entry.getKey().toUpperCase()) {
                case "NO MASS SEEN":
                case "ONE MASS SEEN":
                case "MULTIPLE MASSES SEEN. LARGEST CHARACTERIZED":
                case "ASCITES":
                case "ENDOMETRIOMA":
                case "ABSCESS":
                case "HEMATOMA":
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    break;
                case "LOCULATIONS":
                case "MOTION TENDERNESS":
                case "BOWEL PATTERN":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    value = getRandomValue(value);
                    sikuliActions.selectRadioFromRegion(ReportElements + value + "_Radio.PNG", regionBounds.get(key));
                    entry.setValue(value);
                    break;
                case "AMOUNT":
                case "TYPE":
                case "MASS APPEARANCE":
                case "MASS SHAPE":
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue(value.toLowerCase());
                    break;
                case "TOTAL NUMBER OF MASSES":
                case "MASS COUNT":
                    sikuliActions.typeTextInExactField(ReportElements + key + "_Textbox.PNG", value);
                    robotActions.pressTabKey();
                    Common.sleep(2, TimeUnit.SECONDS);
                    break;
                case "MASS ON":
                case "MASS ORIENTATION":
                case "MASS DIRECTION":
                case "MASS POSITION":
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue("Ignore");
                    String massLocation = reportEntries.get("Mass Location");
                    reportEntries.put("Mass Location", massLocation + value.toLowerCase() + ", ");
                    break;
                case "MASS MID":
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    entry.setValue("Ignore");
                    massLocation = reportEntries.get("Mass Location");
                    reportEntries.put("Mass Location", massLocation + key.split(" ")[1].toLowerCase());
                    break;
                case "MASS LENGTH":
                case "MASS WIDTH":
                case "MASS HEIGHT":
                    if (key.equalsIgnoreCase("Mass Length")) {
                        sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                        sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    }
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    entry.setValue("Ignore");
                    String massSize = reportEntries.get("Mass Size");
                    massSize = massSize.isEmpty() ? value : massSize + " x " + value;
                    reportEntries.put("Mass Size", massSize);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillKidneyTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            System.out.println("Key: " + key);
            String value = entry.getValue();
            System.out.println("Value: " + value);
            switch (entry.getKey().toUpperCase()) {
                case "NO MASS SEEN":
                case "ONE MASS SEEN":
                case "MULTIPLE MASSES SEEN. LARGEST CHARACTERIZED":
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    break;
                case "KIDNEY":
                case "URETERAL PATENCY TEST":
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue(value);
                    break;
                case "KIDNEY LENGTH":
                case "KIDNEY WIDTH":
                case "KIDNEY TRANSVERSE":
                    sikuliActions.typeTextInField(ReportElements + key.replaceAll("/", " ") + "_Textbox.PNG", value);
                    break;
                case "TOTAL NUMBER OF MASSES":
                case "MASS COUNT":
                    sikuliActions.typeTextInExactField(ReportElements + key + "_Textbox.PNG", value);
                    robotActions.pressTabKey();
                    Common.sleep(2, TimeUnit.SECONDS);
                    break;
                case "MASS ON":
                case "MASS ORIENTATION":
                case "MASS DIRECTION":
                case "MASS POSITION":
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue("Ignore");
                    String massLocation = reportEntries.get("Mass Location");
                    reportEntries.put("Mass Location", massLocation + value.toLowerCase() + ", ");
                    break;
                case "MASS MID":
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    entry.setValue("Ignore");
                    massLocation = reportEntries.get("Mass Location");
                    reportEntries.put("Mass Location", massLocation + key.split(" ")[1].toLowerCase());
                    break;
                case "MASS LENGTH":
                case "MASS WIDTH":
                case "MASS HEIGHT":
                    if (key.equalsIgnoreCase("Mass Length")) {
                        sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                        sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    }
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    entry.setValue("Ignore");
                    String massSize = reportEntries.get("Mass Size");
                    massSize = massSize.isEmpty() ? value : massSize + " x " + value;
                    reportEntries.put("Mass Size", massSize);
                    break;
                case "MASS APPEARANCE":
                case "MASS SHAPE":
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue(value.toLowerCase());
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillProstateGeneralTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            System.out.println("Key: " + key);
            String value = entry.getValue();
            System.out.println("Value: " + value);
            switch (entry.getKey().toUpperCase()) {
                case "NO MASS SEEN":
                case "ONE MASS SEEN":
                case "MULTIPLE MASSES SEEN. LARGEST CHARACTERIZED":
                case "MULTIPLE CYSTS SEEN. LARGEST CHARACTERIZED":
                case "NORMAL":
                case "ENLARGED":
                case "HOMOGENEOUS":
                case "HETEROGENEOUS":
                case "CALCIFICATIONS":
                case "NO STONES NOTED":
                case "SEMINAL VESICLES":
                case "PROSTATE":
                case "EJACULATORY DUCTS":
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    break;
                case "PROSTATE LENGTH":
                case "PROSTATE WIDTH":
                case "PROSTATE HEIGHT":
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    entry.setValue("Ignore");
                    String prostateSize = reportEntries.get("Prostate Size");
                    prostateSize = prostateSize.isEmpty() ? value : prostateSize + " x " + value;
                    reportEntries.put("Prostate Size", prostateSize);
                    break;
                case "PROSTATE VOLUME":
                    entry.setValue(getFieldValue(entry.getKey()));
                    break;
                case "TOTAL NUMBER OF MASSES":
                case "MASS COUNT":
                case "CYSTS COUNT":
                    sikuliActions.typeTextInExactField(ReportElements + key + "_Textbox.PNG", value);
                    robotActions.pressTabKey();
                    Common.sleep(2, TimeUnit.SECONDS);
                    break;
                case "MASS ORIENTATION":
                case "MASS DIRECTION":
                case "MASS POSITION":
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue("Ignore");
                    String massLocation = reportEntries.get("Mass Location");
                    reportEntries.put("Mass Location", massLocation + value.toLowerCase() + ", ");
                    break;
                case "MASS MID":
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    entry.setValue("Ignore");
                    massLocation = reportEntries.get("Mass Location");
                    reportEntries.put("Mass Location", massLocation + key.split(" ")[1].toLowerCase());
                    break;
                case "MASS APPEARANCE1":
                case "MASS APPEARANCE2":
                case "MASS SHAPE":
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue(value.toLowerCase());
                    break;
                case "MASS LENGTH":
                case "MASS WIDTH":
                case "MASS HEIGHT":
                    if (key.equalsIgnoreCase("Mass Length")) {
                        sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                        sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    }
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    entry.setValue("Ignore");
                    String massSize = reportEntries.get("Mass Size");
                    massSize = massSize.isEmpty() ? value : massSize + " x " + value;
                    reportEntries.put("Mass Size", massSize);
                    break;
                case "CYST LENGTH":
                case "CYST WIDTH":
                case "CYST HEIGHT":
                    if (!regionBounds.containsKey("Cyst"))
                        regionBounds.putIfAbsent("Cyst",
                                sikuliActions.getExactRegionBounds(ReportElements + "Cyst" + "_Region.PNG"));
                    if (key.equalsIgnoreCase("Cyst Length")) {
                        sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG", regionBounds.get("Cyst"));
						sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG", regionBounds.get("Cyst"));
                    }
                    sikuliActions.typeTextInExactField(ReportElements + key + "_Textbox.PNG", value);
                    entry.setValue("Ignore");
                    String cystSize = reportEntries.get("Cyst Size");
                    cystSize = cystSize.isEmpty() ? value : cystSize + " x " + value;
                    reportEntries.put("Cyst Size", cystSize);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillRecommendationsTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            System.out.println("Key: " + key);
            String value = entry.getValue();
            System.out.println("Value: " + value);
            switch (entry.getKey().toUpperCase()) {
                case "VASCULAR SURGERY EVALUATION RECOMMENDED":
                case "PRN":
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    break;
                case "REPEAT EXAM IN":
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue(value);
                    break;
                case "RECOMMENDATIONS":
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    break;
                case "OTHER":
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
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
                case "INDICATIONS":
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

    private Map<String, String> fillExaminationInformationTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey();
            System.out.println("Key: " + key);
            String value = entry.getValue();
            System.out.println("Value: " + value);
            switch (key.toUpperCase()) {
                case "EXAMINATION TYPE":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    value = getRandomValue(value);
                    sikuliActions.selectRadioFromRegion(ReportElements + value.replace("/", " ").trim() + "_Radio.PNG",
                            regionBounds.get(key));
                    entry.setValue(value);
                    break;
                case "STUDY QUALITY":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    value = getRandomValue(value);
                    if (value.equalsIgnoreCase("Technically limited")) {
                        value = getRandomValue(reportEntries.get("Technically limited"));
                        sikuliActions.selectValueFromDropdown(ReportElements + "Technically limited" + "_Dropdown.PNG",
                                value);
                        entry.setValue("Technically limited because of " + value);
                    } else {
                        sikuliActions.selectRadioFromRegion(ReportElements + value.replace("/", " ").trim() + "_Radio.PNG",
                                regionBounds.get(key));
                        entry.setValue(value);
                    }
                    reportEntries.put("Technically limited", "Ignore");
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillPatientInformation(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            System.out.println("Key: " + key);
            String value = entry.getValue();
            System.out.println("Value: " + value);
            switch (entry.getKey().split(":")[0].toUpperCase()) {
                case "RACE":
                case "PATIENT TYPE":
                    value = getRandomValue(value);
                    sikuliActions.selectValueFromDropdown(ReportElements + key + "_Dropdown.PNG", value);
                    entry.setValue(value);
                    break;
                case "TYPE OF PAYMENT":
                case "DIABETIC":
                    if (!regionBounds.containsKey(key))
                        regionBounds.putIfAbsent(key,
                                sikuliActions.getExactRegionBounds(ReportElements + key + "_Region.PNG"));
                    value = getRandomValue(entry.getValue());
                    sikuliActions.selectRadioFromRegion(ReportElements + value + "_Radio.PNG", regionBounds.get(key));
                    entry.setValue(value);
                    break;
                case "PATIENT NAME":
                case "EXAMINATION DATE":
                case "AGE":
                case "SEX":
                case "DOB":
                case "LMP":
                case "BSA":
                case "EQUIPMENT":
                case "CHART":
                case "REFERRING PHYSICIAN":
                    entry.setValue(getFieldValue(key));
                    break;
                case "WEIGHT":
                case "HEIGHT":
                case "BMI":
                case "HEART RATE":
                case "RESPIRATORY RATE":
                case "TEMPERATURE":
                case "SYSTOLIC BLOOD PRESSURE":
                case "DIASTOLIC BLOOD PRESSURE":
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    break;
                case "NUMBER OF PREGNANCIES":
                case "NUMBER OF LIVING BIRTHS":
                case "NUMBER OF TERM BIRTHS":
                case "NUMBER OF PRETERM BIRTHS":
                case "NUMBER OF ECTOPIC":
                case "NUMBER OF MISCARRIAGES ABORTION":
                case "NUMBER OF LIVING CHILDREN":
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
                    sikuliActions.selectCheckBox(ReportElements + key + "_Checkbox.PNG");
                    break;
            }
        }
        //noinspection StatementWithEmptyBody
        while (reportEntries.values().remove(""))
            ;
        return reportEntries;
    }

    private Map<String, String> fillOtherTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().split(":")[0];
            System.out.println("Key: " + key);
            String value = entry.getValue();
            System.out.println("Value: " + value);
            switch (key.toUpperCase()) {
                case "FREE TEXT":
                case "COPY TO":
                    sikuliActions.typeTextInField(ReportElements + key + "_Textbox.PNG", value);
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
                case "ENDOMETRIAL SIZE PHRASE":
                    entry.setValue(String.format("The endometrium measures %s mm and appears %s",
                            reportEntries.get("Endometrial thickness"), reportEntries.get("Endometrial size")));
                    reportEntries.put("Endometrial thickness", "Ignore");
                    reportEntries.put("Endometrial size", "Ignore");
                    break;
                case "OVARY SIZE":
                    String ovarySize = entry.getValue();
                    String ovarySizePhrase = sectionName.equalsIgnoreCase("Right Ovary")
                            ? "The right ovary measures %s mm with a volume of %s cm3"
                            : "The left ovary measures %s mm with a volume of %s cm3";
                    entry.setValue(String.format(ovarySizePhrase, ovarySize, reportEntries.get("Ovary Volume")));
                    reportEntries.put("Ovary Volume", "Ignore");
                    break;
                case "ASSESSMENT":
                    String assessment = entry.getValue();
                    String ovaryAssessmentPhrase = sectionName.equalsIgnoreCase("Right Ovary")
                            ? "right ovary arterial Doppler"
                            : "left ovary arterial Doppler";
                    entry.setValue(String.format(ovaryAssessmentPhrase, assessment));
                    break;
                case "FREE FLUID":
                    String ovaryFreeFluidPhrase = sectionName.equalsIgnoreCase("Right Ovary")
                            ? "There is a %s of %s free fluid in the right adnexa"
                            : "There is a %s of %s free fluid in the left adnexa";
                    entry.setValue(String.format(ovaryFreeFluidPhrase, reportEntries.get("Free Fluid1"),
                            reportEntries.get("Free Fluid2")));
                    reportEntries.put("Free Fluid1", "Ignore");
                    reportEntries.put("Free Fluid2", "Ignore");
                    break;
                case "FLUID":
                    if (entry.getValue().equalsIgnoreCase("Not seen")) {
                        entry.setValue(entry.getKey() + " : " + entry.getValue());
                    }
                    break;
                case "CUL DE SAC FLUID":
                    entry.setValue(String.format("There is a %s amount of %s free fluid in the posterior cul-de-sac",
                            reportEntries.get("Amount"), reportEntries.get("Type")));
                    reportEntries.put("Amount", "Ignore");
                    reportEntries.put("Type", "Ignore");
                    break;
                case "LOCULATIONS":
                case "MOTION TENDERNESS":
                    entry.setValue(entry.getKey() + ": " + entry.getValue());
                    break;
                case "BLADDER ENLARGED":
                    entry.setValue(String.format("The bladder is enlarged; it extends to a point %s cm below the umbilicus",
                            entry.getValue()));
                    break;
                case "LEFT URETERAL JETS":
                    entry.setValue(
                            String.format("Left ureteral jet was %s within the bladder", entry.getValue().toLowerCase()));
                    break;
                case "RIGHT URETERAL JETS":
                    entry.setValue(
                            String.format("Right ureteral jet was %s within the bladder", entry.getValue().toLowerCase()));
                    break;
                case "KIDNEY":
                    String kidneyValue = entry.getValue().toLowerCase();
                    entry.setValue(String.format("The %s kidney measures %s x %s x %s mm and %s",
                            tabName.equalsIgnoreCase("Right Kidney") ? "right" : "left", reportEntries.get("Kidney Length"),
                            reportEntries.get("Kidney Width"), reportEntries.get("Kidney Transverse"),
                            kidneyValue.equalsIgnoreCase("Normal") ? "is unremarkable"
                                    : kidneyValue.equalsIgnoreCase("Abnormal") ? "is abnormal in appearance"
                                    : "has suboptimal visualization"));
                    reportEntries.put("Kidney Length", "Ignore");
                    reportEntries.put("Kidney Width", "Ignore");
                    reportEntries.put("Kidney Transverse", "Ignore");
                    break;
                case "PROSTATE SIZE":
                    String prostateSize = entry.getValue();
                    entry.setValue(String.format("The prostate gland measures %s with a volume of %s cm3", prostateSize,
                            reportEntries.get("Prostate Volume")));
                    reportEntries.put("Prostate Volume", "Ignore");
                    break;
            }
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

    private String getFieldValue(String fieldName) {
        sikuliActions.scrollToField(ReportElements + fieldName + "_Textbox.PNG");
        return sikuliActions.getTextFromExactField(ReportElements + fieldName + "_Textbox.PNG");
    }

}
