package ReportEditors;

import Actions.RobotActions;
import Actions.SikuliActions;
import Utilities.Common;
import Utilities.Log;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static Utilities.Constants.ReportScreens;
import static Utilities.Constants.USER_WORK_DIR;

public class GeneralAbdominalFinalReportEditor implements ReportEditor {

    SikuliActions sikuliActions;
    RobotActions robotActions;
    String reportName;
    String sectionName;
    String tabName;
    String ReportElements;
    ReportEditorHelper reportEditorHelper;

    public GeneralAbdominalFinalReportEditor(String reportName, String sectionName, String tabName) {
        sikuliActions = new SikuliActions();
        robotActions = new RobotActions();
        this.reportName = reportName;
        this.sectionName = sectionName;
        this.tabName = tabName;
        ReportElements = USER_WORK_DIR + ReportScreens + "General Abdominal Final Report\\" + sectionName + "\\" + tabName
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
            case "RIGHT RENAL FAVORITES":
            case "LEFT RENAL FAVORITES":
            case "FAVORITES GALLBLADDER":
            case "FAVORITES BILE DUCTS":
            case "BLADDER FAVORITES":
            case "PROSTATE FAVORITES":
            case "FAVORITES AORTA":
            case "FAVORITES IVC":
            case "FAVORITES":
            case "COPY TO":
            case "FREE TEXT":
                filledReportEntries = fillFavoritesTab(reportEntries);
                break;
            case "PROCEDURE":
                filledReportEntries = fillProcedureTab(reportEntries);
                break;
            case "CODES":
                filledReportEntries = fillCodesTab(reportEntries);
                break;
            case "STUDY QUALITY":
                filledReportEntries = fillStudyQualityTab(reportEntries);
                break;
            case "FOCAL LESIONS":
                filledReportEntries = fillFocalLesionsTab(reportEntries);
                break;
            case "PORTAL AND HEPATIC VEINS":
                filledReportEntries = fillPortalAndHepaticVeinsTab(reportEntries);
                break;
            case "FLUID SURVEY":
                filledReportEntries = fillFluidSurveyTab(reportEntries);
                break;
            case "GALLBLADDER":
                filledReportEntries = fillGallbladderTab(reportEntries);
                break;
            case "BILE DUCTS":
                filledReportEntries = fillBileDuctsTab(reportEntries);
                break;
            case "GENERAL":
                switch (sectionName.toUpperCase()) {
                    case "LIVER":
                        filledReportEntries = fillLiverGeneralTab(reportEntries);
                        break;
                    case "PANCREAS":
                        filledReportEntries = fillPancreasGeneralTab(reportEntries);
                        break;
                }
                break;
            case "RIGHT KIDNEY":
            case "LEFT KIDNEY":
                filledReportEntries = fillKidneyTab(reportEntries);
                break;
            case "BILATERAL KIDNEYS":
                filledReportEntries = fillBilateralKidneysTab(reportEntries);
                break;
            case "BLADDER":
                filledReportEntries = fillBladderTab(reportEntries);
                break;
            case "PROSTATE":
                filledReportEntries = fillProstateTab(reportEntries);
                break;
            case "SPLEEN":
                filledReportEntries = fillSpleenTab(reportEntries);
                break;
            case "LYMPH NODES":
                filledReportEntries = fillLymphNodesTab(reportEntries);
                break;
            case "AORTA":
                filledReportEntries = fillAortaTab(reportEntries);
                break;
            case "INFERIOR VENA CAVA":
                filledReportEntries = fillIVCTab(reportEntries);
                break;
            case "RECOMMENDATIONS":
                filledReportEntries = fillRecommendationsTab(reportEntries);
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
            String value = entry.getValue();
            switch (key.toUpperCase()) {
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
                    reportEditorHelper.selectAllCheckboxesInARegion(regionBounds.get(key), value);
                    break;
                case "PATIENT NAME":
                case "EXAMINATION DATE":
                case "AGE":
                case "SEX":
                case "DOB":
                case "EQUIPMENT":
                case "CHART":
                case "REFERRING PHYSICIAN":
                case "BSA":
                    entry.setValue(reportEditorHelper.getFieldValue(key));
                    break;
                case "WEIGHT":
                case "HEIGHT":
                case "HEART RATE":
                case "SYSTOLIC BLOOD PRESSURE":
                case "DIASTOLIC BLOOD PRESSURE":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillFavoritesTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (key.split(":")[0].toUpperCase()) {
                case "STUDY DESCRIPTION":
                case "GENERAL FINDINGS":
                case "FAVORITES":
                case "COPY TO":
                case "FREE TEXT":
                case "IMPRESSIONS":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillProcedureTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            entry.setValue(reportEditorHelper.selectRandomRadioButton(entry.getValue()));
            break;
        }
        return reportEntries;
    }

    private Map<String, String> fillCodesTab(Map<String, String> reportEntries) {
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
        return reportEntries;
    }

    private Map<String, String> fillStudyQualityTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "STUDY QUALITY":
                case "PATIENT TYPE":
                    entry.setValue(reportEditorHelper.selectRandomRadioButton(value));
                    break;
                case "RECOMMEND OTHER IMAGING MODALITY":
                case "INADEQUATE DUE TO BOWEL GAS":
                case "SUBOPTIMAL DUE TO POOR BOWEL PREP":
                case "DIAGNOSTIC QUALITY":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "NONDIAGNOSTIC OF":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillLiverGeneralTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "MIDCLAVICULAR LENGTH":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "NORMAL":
                case "NORMAL DETAILED":
                case "POORLY VISUALIZED":
                case "LOBULATED":
                case "FATTY INFILTRATION WITH ATTENUATION":
                case "DIAPHRAGM OBSCURED":
                case "DIAPHRAGM AND PORTA HEPATIS OBSCURED":
                case "MULTINODULAR":
                case "DILATED BILIARY RADICLES":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "LIVER ECHOGENICITY":
                case "SIZE":
                case "ECHO-TEXTURE":
                case "FATTY LIVER":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillFocalLesionsTab(Map<String, String> reportEntries) {
        fillMassDetails(reportEntries);
        fillCystDetails(reportEntries);
        return reportEntries;
    }

    private Map<String, String> fillPortalAndHepaticVeinsTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "PORTAL VENOUS FLOW":
                case "HEPATIC VEIN FLOW":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
                case "NORMAL":
                case "PORTAL VEIN THROMBOSIS":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "PORTAL VEIN":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillFluidSurveyTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "SUBPHRENIC SPACE FREE FLUID":
                case "SUBHEPATIC SPACE FREE FLUID":
                case "ASCITES":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
                case "PERICHOLECYSTIC FLUID":
                case "MORRISON'S POUCH FREE FLUID":
                case "RETROPERITONEAL FLUID":
                case "PERINEPHRIC GUTTER FREE FLUID":
                case "PELVIC FREE FLUID":
                    reportEditorHelper.selectCheckbox(key);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillGallbladderTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "GALLBLADDER MEASURES":
                case "MOBILE CALCULUS":
                case "IMMOBILE CALCULUS":
                case "POLYP":
                case "GALLBLADDER WALL MEASURES":
                case "FOCAL WALL THICKENING":
                    String[] measurements = value.split("x");
                    for (int index = 0; index < measurements.length; index++) {
                        if (index == 0) {
                            reportEditorHelper.enterValueInTextbox(key, measurements[index].trim());
                            continue;
                        }
                        Common.sleep(500, TimeUnit.MILLISECONDS);
                        robotActions.pressTabKey();
                        sikuliActions.type(measurements[index].trim());
                    }
                    break;
                case "NORMAL":
                case "POORLY VISUALIZED":
                case "SURGICALLY ABSENT":
                case "DECOMPRESSED":
                case "MULTIPLE MOBILE CALCULI":
                case "VALVE OF HEISTER":
                case "SLUDGE":
                case "NEGATIVE MURPHY SIGN":
                case "POSITIVE MURPHY SIGN":
                case "PERICHOLECYSTIC FLUID":
                case "HETEROGENEOUS WITH INTERSTITIAL FLUID LEVELS":
                case "PORCELAIN":
                case "FOCAL CALCIFICATIONS":
                case "FOCAL RING DOWN ARTIFACT":
                    reportEditorHelper.selectCheckbox(key);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillBileDuctsTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "CBD MEASURES":
                case "CBD PANCREAS MEASURES":
                case "CHD MEASURES":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "NORMAL":
                case "DILATED BILIARY RADICLES":
                    reportEditorHelper.selectCheckbox(key);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillPancreasGeneralTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "HEAD":
                case "BODY":
                case "TAIL":
                case "PANCREATIC DUCT MEASURE":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "NORMAL":
                case "REGULAR BORDERS":
                case "HOMOGENEOUS ECHOTEXTURE":
                case "TAIL OBSCURED":
                case "NOT VISUALIZED":
                case "HETEROGENEOUS":
                case "FREE FLUID":
                case "CALCIFICATION IN HEAD":
                case "PANCREATIC DUCT NOT VISUALIZED":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "ECHOGENICITY":
                case "SIZE":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
                case "PANCREATIC DUCT SIZE":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    //robotActions.pressTabKey();
                    sikuliActions.type(reportEntries.get("Pancreatic duct measure"));
                    break;
            }
        }
        fillMassDetails(reportEntries);
        return reportEntries;
    }

    private Map<String, String> fillKidneyTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "KIDNEY LENGTH":
                case "VOLUME":
                case "RENAL CORTEX":
                case "URETER":
                case "LARGEST CALCULUS SIZE":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "NORMAL SIZE, SHAPE AND ECHOGENICITY":
                case "NORMAL LENGTH":
                case "CORTICAL-MEDULLARY DEFINITION":
                case "POORLY VISUALIZED":
                case "ABSENT":
                case "ATROPHIC":
                case "MEDULLARY SPONGE KIDNEY":
                case "NORMAL":
                case "NO DILATION":
                case "EXTRARENAL PELVIS":
                case "RENAL PELVIS CALCIFICATION":
                case "MULTIPLE CALCIFICATIONS":
                case "STAGHORN CALCULUS":
                case "DUPLICATED COLLECTION SYSTEM":
                case "NONE":
                case "MULTIPLE CYSTS":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "ECHOGENICITY":
                case "DILATED CALYCES":
                case "HYDRONEPHROSIS":
                case "CALCULUS URETER":
                case "LARGEST CALCULUS LOCATION":
                case "KIDNEY SIZE":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
                case "LARGEST CYST":
                    String[] measurements = value.split("x");
                    for (int index = 0; index < measurements.length; index++) {
                        if (index == 0) {
                            reportEditorHelper.enterValueInTextbox(key, measurements[index].trim());
                            continue;
                        }
                        Common.sleep(500, TimeUnit.MILLISECONDS);
                        robotActions.pressTabKey();
                        sikuliActions.type(measurements[index].trim());
                    }
                    break;
                case "KIDNEY MEASURE":
                    entry.setValue(reportEntries.get("Kidney Length"));
                    break;
            }
        }
        fillMassDetails(reportEntries);
        fillCalculiDetails(reportEntries);
        return reportEntries;
    }

    private Map<String, String> fillBilateralKidneysTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            reportEditorHelper.selectCheckbox(entry.getKey());
        }
        return reportEntries;
    }

    private Map<String, String> fillBladderTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "PRE VOID VOLUME":
                case "RESIDUAL VOLUME":
                case "FOCAL WALL THICKENING":
                case "RIGHT URETERAL CALCULUS":
                case "LEFT URETERAL CALCULUS":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "DISTENDED":
                case "FOCAL MASS":
                case "MULTIPLE MASSES, LARGEST":
                    String[] measurements = value.split("x");
                    for (int index = 0; index < measurements.length; index++) {
                        if (index == 0) {
                            reportEditorHelper.enterValueInTextbox(key, measurements[index].trim());
                            continue;
                        }
                        robotActions.pressTabKey();
                        Common.sleep(500, TimeUnit.MILLISECONDS);
                        sikuliActions.type(measurements[index].trim());
                    }
                    break;
                case "POST VOID BLADDER LENGTH":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    robotActions.pressTabKey();
                    Common.sleep(500, TimeUnit.MILLISECONDS);
                    sikuliActions.type(reportEntries.get("Post void bladder width"));
                    robotActions.pressTabKey();
                    Common.sleep(500, TimeUnit.MILLISECONDS);
                    sikuliActions.type(reportEntries.get("Post void bladder height"));
                    break;
                case "POST VOID VOLUME":
                case "DISTENDED VOLUME":
                    entry.setValue(reportEditorHelper.getFieldValue(key));
                    break;
                case "NORMAL":
                case "NORMAL DETAILED":
                case "CATHETER":
                case "FUNCTIONAL URETERAL VALVES":
                case "DISTAL URETERS NOT DILATED":
                case "FREE FLUID":
                case "MOBILE CALCULI":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "POST VOID RESIDUAL":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillProstateTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "PROSTATE MASS MEASURES":
                    String[] measurements = value.split("x");
                    for (int index = 0; index < measurements.length; index++) {
                        if (index == 0) {
                            reportEditorHelper.enterValueInTextbox(key, measurements[index].trim());
                            continue;
                        }
                        robotActions.pressTabKey();
                        Common.sleep(500, TimeUnit.MILLISECONDS);
                        sikuliActions.type(measurements[index].trim());
                    }
                    break;
                case "PROSTATE VOLUME":
                    entry.setValue(reportEditorHelper.getFieldValue(key));
                    break;
                case "BLADDER DISTENDED":
                case "PROSTATE REMOVED":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "PROSTATE SIZE":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    String[] prostateMeasurements = reportEntries.get("Prostate measures").split("x");
                    for (String prostateMeasurement : prostateMeasurements) {
                        Common.sleep(500, TimeUnit.MILLISECONDS);
                        sikuliActions.type(prostateMeasurement.trim());
                        Common.sleep(500, TimeUnit.MILLISECONDS);
                        robotActions.pressTabKey();
                    }
                    break;
                case "MASS LOCATION":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillSpleenTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "SPLEEN VOLUME":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "ACCESSORY SPLEEN":
                    String[] measurements = value.split("x");
                    for (int index = 0; index < measurements.length; index++) {
                        if (index == 0) {
                            reportEditorHelper.enterValueInTextbox(key, measurements[index].trim());
                            continue;
                        }
                        robotActions.pressTabKey();
                        Common.sleep(500, TimeUnit.MILLISECONDS);
                        sikuliActions.type(measurements[index].trim());
                    }
                    break;
                case "NORMAL":
                case "SURGICALLY ABSENT":
                case "SPLENOMEGALY":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "SPLEEN SIZE":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    String[] prostateMeasurements = reportEntries.get("Spleen measures").split("x");
                    for (String prostateMeasurement : prostateMeasurements) {
                        Common.sleep(500, TimeUnit.MILLISECONDS);
                        sikuliActions.type(prostateMeasurement.trim());
                        Common.sleep(500, TimeUnit.MILLISECONDS);
                        robotActions.pressTabKey();
                    }
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillLymphNodesTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "PORTA HEPATIS: ENLARGED LYMPH NODE":
                case "PORTA HEPATIS: LARGEST LYMPH NODE":
                case "CELIAC AXIS: ENLARGED LYMPH NODE":
                case "CELIAC AXIS: LARGEST LYMPH NODE":
                    String region = key.split(":")[0].trim();
                    String field = key.split(":")[1].trim();
                    if (!regionBounds.containsKey(region))
                        regionBounds.putIfAbsent(region,
                                sikuliActions.getExactRegionBounds(ReportElements + region + "_Region.PNG"));
                    reportEditorHelper.enterValueInTextboxFromARegion(regionBounds.get(region), field, value);
                    break;
                case "PORTA HEPATIS: NORMAL":
                case "PORTA HEPATIS: MULTIPLE NODES":
                case "PORTA HEPATIS: INCREASED NUMBER":
                case "CELIAC AXIS: NORMAL":
                case "CELIAC AXIS: MULTIPLE NODES":
                case "CELIAC AXIS: INCREASED NUMBER":
                    region = key.split(":")[0].trim();
                    field = key.split(":")[1].trim();
                    if (!regionBounds.containsKey(region))
                        regionBounds.putIfAbsent(region,
                                sikuliActions.getExactRegionBounds(ReportElements + region + "_Region.PNG"));
                    reportEditorHelper.selectCheckboxInARegion(field, regionBounds.get(region));
                    break;
                case "DILATED PERIAORTIC LYMPH NODES":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "FREE TEXT":
                    reportEditorHelper.enterValueInTextbox(key, value);
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillAortaTab(Map<String, String> reportEntries) {
        Map<String, Rectangle> regionBounds = new HashMap<>();
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "PROX":
                case "MID":
                case "DIST":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "RIGHT CIA: CIA":
                case "RIGHT CIA: PROX CIA":
                case "RIGHT CIA: MID CIA":
                case "RIGHT CIA: DIST CIA":
                case "LEFT CIA: CIA":
                case "LEFT CIA: PROX CIA":
                case "LEFT CIA: MID CIA":
                case "LEFT CIA: DIST CIA":
                case "RIGHT EIA: EIA":
                case "RIGHT EIA: PROX EIA":
                case "RIGHT EIA: MID EIA":
                case "RIGHT EIA: DIST EIA":
                case "LEFT EIA: EIA":
                case "LEFT EIA: PROX EIA":
                case "LEFT EIA: MID EIA":
                case "LEFT EIA: DIST EIA":
                    String region = key.split(":")[0].trim();
                    String field = key.split(":")[1].trim();
                    if (!regionBounds.containsKey(region))
                        regionBounds.putIfAbsent(region,
                                sikuliActions.getExactRegionBounds(ReportElements + region + "_Region.PNG"));
                    String[] measurements = value.split("x");
                    for (int index = 0; index < measurements.length; index++) {
                        if (index == 0) {
                            reportEditorHelper.enterValueInTextboxFromARegion(regionBounds.get(region), field, measurements[index].trim());
                            continue;
                        }
                        robotActions.pressTabKey();
                        Common.sleep(500, TimeUnit.MILLISECONDS);
                        sikuliActions.type(measurements[index].trim());
                    }
                    break;
                case "FOCAL DILATION MEASURES":
                    measurements = value.split("x");
                    for (int index = 0; index < measurements.length; index++) {
                        if (index == 0) {
                            reportEditorHelper.enterValueInTextbox(key, measurements[index].trim());
                            continue;
                        }
                        robotActions.pressTabKey();
                        Common.sleep(500, TimeUnit.MILLISECONDS);
                        sikuliActions.type(measurements[index].trim());
                    }
                    break;
                case "AORTA NOT VISUALIZED":
                case "UNREMARKABLE":
                case "ATHEROSCLEROTIC":
                case "ATHEROSCLEROTIC AND TORTUOUS":
                case "ECTASIA":
                case "RENAL INVOLVEMENT":
                case "FREE FLUID PRESENT":
                case "PERIAORTIC FLUID PRESENT":
                case "DOUBLE LUMEN SIGN":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "FOCAL DILATION LOCATION":
                case "PLAQUE QUANTITY":
                case "PLAQUE TEXTURE":
                case "PLAQUE TYPE":
                case "PLAQUE LOCATION":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillIVCTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "NORMAL":
                case "DILATED":
                case "NOT VISUALIZED":
                case "IVC PATENT":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "FLOW":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
                case "ICV":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
            }
        }
        return reportEntries;
    }

    private Map<String, String> fillRecommendationsTab(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey().replace(" / ", " ");
            String value = entry.getValue();
            switch (entry.getKey().toUpperCase()) {
                case "VASCULAR SURGERY EVALUATION RECOMMENDED":
                case "PRN":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "REPEAT EXAM IN":
                    entry.setValue(reportEditorHelper.selectRandomValueFromDropdown(key, value));
                    break;
                case "RECOMMENDATIONS":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "OTHER":
                    reportEditorHelper.enterTextAndSelectCheckbox(key, value);
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

    private void fillMassDetails(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "NO MASSES SEEN":
                case "MULTIPLE MASSES SEEN":
                case "MULTIPLE MASSES SEEN. LARGEST CHARACTERIZED":
                case "TARGET SIGN":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "MASS APPEARANCE":
                case "MASS SHAPE":
                case "MASS LOCATION":
                case "MASS TYPE":
                    entry.setValue(reportEditorHelper.selectRandomValueFromExactDropdown(key.contains(":")
                            ? key.split(":")[1].trim()
                            : key, value));
                    break;
                case "MASS LENGTH":
                case "MASS WIDTH":
                case "MASS HEIGHT":
                case "MASS SEGMENT":
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

    private void fillCystDetails(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "MULTIPLE CYSTS":
                    reportEditorHelper.selectCheckbox(key);
                    break;
                case "CYST LOCATION":
                    entry.setValue(reportEditorHelper.selectRandomValueFromExactDropdown(key.contains(":")
                            ? key.split(":")[1].trim()
                            : key, value));
                    break;
                case "CYST LENGTH":
                case "CYST WIDTH":
                case "CYST HEIGHT":
                case "CYST SEGMENT":
                    if (key.equalsIgnoreCase("Cyst Length")) {
                        reportEditorHelper.selectCheckbox(key);
                        reportEditorHelper.selectCheckbox(key);
                    }
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "CYST COUNT":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    Common.sleep(1, TimeUnit.SECONDS);
                    break;
            }
        }
    }

    private void fillCalculiDetails(Map<String, String> reportEntries) {
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key.toUpperCase()) {
                case "CALCULI LOCATION":
                case "CALCULI TYPE":
                    entry.setValue(reportEditorHelper.selectRandomValueFromExactDropdown(key.contains(":")
                            ? key.split(":")[1].trim()
                            : key, value));
                    break;
                case "TARGET SIGN":
                    reportEditorHelper.selectCheckbox(key);
                case "CALCULI SIZE":
                    reportEditorHelper.selectCheckbox(key);
                    reportEditorHelper.selectCheckbox(key);
                    reportEditorHelper.enterValueInTextbox(key, value);
                    break;
                case "CALCULI COUNT":
                    reportEditorHelper.enterValueInTextbox(key, value);
                    Common.sleep(1, TimeUnit.SECONDS);
                    break;
                case "CALCULI":
                    entry.setValue(String.format("There is a %s cm %s calculus in the %s %s",
                            reportEntries.put("Calculi size", "Ignore"),
                            reportEntries.put("Calculi type", "Ignore"),
                            reportEntries.put("Calculi location", "Ignore"),
                            tabName.toLowerCase()));
                    break;
            }
        }
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
                case "HISTORY":
                    entry.setValue(entry.getValue().replaceAll(",", "\\."));
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
                case "FOCAL LESION MASS":
                    entry.setValue(String.format("There is a %s, %s mass in the %s segment of the %s of the liver measuring %s x %s x %s mm",
                            reportEntries.put("Mass Appearance", "Ignore"),
                            reportEntries.put("Mass Shape", "Ignore"),
                            reportEntries.put("Mass Segment", "Ignore"),
                            reportEntries.put("Mass Location", "Ignore"),
                            reportEntries.put("Mass Length", "Ignore"),
                            reportEntries.put("Mass Width", "Ignore"),
                            reportEntries.put("Mass Height", "Ignore")));
                    break;
                case "PANCREATIC MASS":
                    entry.setValue(String.format("There is a %s mass in the pancreatic %s measuring %s x %s x %s mm",
                            reportEntries.put("Mass Appearance", "Ignore"),
                            reportEntries.put("Mass Location", "Ignore"),
                            reportEntries.put("Mass Length", "Ignore"),
                            reportEntries.put("Mass Width", "Ignore"),
                            reportEntries.put("Mass Height", "Ignore")));
                    break;
                case "RIGHT KIDNEY MASS":
                case "LEFT KIDNEY MASS":
                    entry.setValue(String.format("There is a %s x %s x %s %s, %s mass in the %s %s",
                            reportEntries.put("Mass Length", "Ignore"),
                            reportEntries.put("Mass Width", "Ignore"),
                            reportEntries.put("Mass Height", "Ignore"),
                            reportEntries.put("Mass Appearance", "Ignore"),
                            reportEntries.put("Mass Type", "Ignore"),
                            reportEntries.put("Mass Location", "Ignore"),
                            this.tabName.toLowerCase()));
                    break;
                case "FOCAL LESION CYST":
                    entry.setValue(String.format("There is an anechoic, smooth-walled cyst in the %s segment of the %s of the liver measuring %s x %s x %s mm",
                            reportEntries.put("Cyst Segment", "Ignore"),
                            reportEntries.put("Cyst Location", "Ignore"),
                            reportEntries.put("Cyst Length", "Ignore"),
                            reportEntries.put("Cyst Width", "Ignore"),
                            reportEntries.put("Cyst Height", "Ignore")));
                    break;
                case "PORTAL VEIN":
                    entry.setValue(String.format("The portal vein measures %s cm in diameter", entry.getValue()));
                    break;
                case "HEPATIC VEIN FLOW":
                    if (entry.getValue().equalsIgnoreCase("reversed")) {
                        entry.setValue(String.format("There is %s flow in the Dopplered hepatic vein", entry.getValue()));
                    } else {
                        entry.setValue(String.format("There is %s hepatic vein flow", entry.getValue()));
                    }
                    break;
                case "SUBPHRENIC SPACE FREE FLUID":
                    entry.setValue(String.format("There is a %s amount of free fluid in the subphrenic space", entry.getValue()));
                    break;
                case "SUBHEPATIC SPACE FREE FLUID":
                    entry.setValue(String.format("There is a %s amount of free fluid in the subhepatic space", entry.getValue()));
                    break;
                case "ASCITES":
                    entry.setValue(String.format("%s amount of ascites seen", entry.getValue()));
                    break;
                case "GALLBLADDER MEASURES":
                    String[] gallBladderMeasurement = entry.getValue().split("x");
                    entry.setValue(String.format("The gallbladder measures %s cm in length, %s cm in width and %s cm in height",
                            gallBladderMeasurement[0].trim(), gallBladderMeasurement[1].trim(), gallBladderMeasurement[2].trim()));
                    break;
                case "PANCREAS SIZE":
                    entry.setValue(String.format("The pancreas measures %s cm at the head, %s cm at the body and %s cm at the tail",
                            reportEntries.put("Head", "Ignore"),
                            reportEntries.put("Body", "Ignore"),
                            reportEntries.put("Tail", "Ignore")));
                    break;
                case "PANCREAS":
                    entry.setValue(String.format("The pancreas is %s and %s",
                            reportEntries.put("Size", "Ignore"),
                            reportEntries.put("Echogenicity", "Ignore")));
                    break;
                case "PANCREATIC DUCT":
                    String pancreaticDuctSize = reportEntries.put("Pancreatic duct size", "Ignore");
                    pancreaticDuctSize = Objects.requireNonNull(pancreaticDuctSize).equalsIgnoreCase("Enlarged") ? "dilated" : pancreaticDuctSize;
                    entry.setValue(String.format("The pancreatic duct is %s and measures %s cm in diameter",
                            pancreaticDuctSize,
                            reportEntries.put("Pancreatic duct measure", "Ignore")));
                    break;
                case "KIDNEY LENGTH":
                    entry.setValue(String.format("The %s measures %s cm in length",
                            this.tabName.toLowerCase(),
                            entry.getValue()));
                    break;
                case "KIDNEY SIZE":
                    entry.setValue(String.format("The %s is %s and measures %s cm in length", this.tabName.toLowerCase(),
                            entry.getValue(), reportEntries.put("Kidney measure", "Ignore")));
                    break;
                case "ECHOGENICITY":
                    entry.setValue(String.format("There is %s echogenicity in the %s",
                            entry.getValue(), this.tabName.toLowerCase()));
                    break;
                case "CALCULUS URETER":
                    entry.setValue(String.format("A calculus is identified in the %s ureter with a dilated column that measures %s cm in diameter",
                            entry.getValue().toLowerCase(), reportEntries.get("Ureter")));
                    break;
                case "LARGEST CALCULUS":
                    entry.setValue(String.format("The largest calcification is in the %s kidney measuring %s cm",
                            reportEntries.put("Largest calculus location", "Ignore"),
                            reportEntries.put("Largest calculus size", "Ignore")));
                    break;
                case "POST VOID RESIDUAL":
                    entry.setValue(String.format("There is %s post void residual", entry.getValue()));
                    break;
                case "POST VOID BLADDER LENGTH":
                    entry.setValue(String.format("The bladder measures %s cm in length", entry.getValue()));
                    break;
                case "POST VOID BLADDER WIDTH":
                    entry.setValue(String.format("The bladder measures %s cm in width", entry.getValue()));
                    break;
                case "POST VOID BLADDER HEIGHT":
                    entry.setValue(String.format("The bladder measures %s cm in height", entry.getValue()));
                    break;
                case "PROSTATE":
                    boolean isProstateSizeNormal = reportEntries.get("Prostate size").equalsIgnoreCase("Normal");
                    if (isProstateSizeNormal) {
                        entry.setValue(String.format("Transabdominal measures of the prostate are %s cm",
                                reportEntries.put("Prostate measures", "Ignore")));
                    } else {
                        entry.setValue(String.format("The prostate appears %s and heterogeneous measuring %s cm",
                                Objects.requireNonNull(reportEntries.put("Prostate size", "Ignore")).toLowerCase(),
                                reportEntries.put("Prostate measures", "Ignore")));
                    }
                    break;
                case "PROSTATE MASS":
                    entry.setValue(String.format("A mass measuring %s cm appears to be in the %s zone of the prostate",
                            Objects.requireNonNull(reportEntries.put("Prostate Mass measures", "Ignore")).toLowerCase(),
                            reportEntries.put("Mass location", "Ignore")));
                    break;
                case "SPLEEN":
                    entry.setValue(String.format("The spleen is %s and measures %s cm",
                            Objects.requireNonNull(reportEntries.put("Spleen Size", "Ignore")).equalsIgnoreCase("Normal") ? "unremarkable" : reportEntries.get("Spleen Size").toLowerCase(),
                            reportEntries.put("Spleen measures", "Ignore")));
                    break;
                case "FOCAL DILATION":
                    String[] focalDilationMeasure = Objects.requireNonNull(reportEntries.put("Focal dilation measures", "Ignore")).split("x");
                    entry.setValue(String.format("There is a focal dilation of the %s abdominal aorta measuring %s cm in length, %s cm in height and %s cm in width",
                            reportEntries.put("Focal dilation location", "Ignore"),
                            focalDilationMeasure[0].trim(), focalDilationMeasure[1].trim(), focalDilationMeasure[2].trim()));
                    break;
                case "AORTA PLAQUE":
                    entry.setValue(String.format("There is %s %s %s plaque in the %s aorta",
                            reportEntries.put("Plaque quantity", "Ignore"),
                            reportEntries.put("Plaque texture", "Ignore"),
                            reportEntries.put("Plaque type", "Ignore"),
                            reportEntries.put("Plaque location", "Ignore")));
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
}
