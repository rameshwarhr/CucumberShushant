package ReportEditors;

import Actions.RobotActions;
import Actions.SikuliActions;
import Utilities.Common;
import Utilities.Constants;
import Utilities.Log;
import Utilities.YmlReader;

import java.awt.*;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static Utilities.Constants.ReportScreens;
import static Utilities.Constants.USER_WORK_DIR;

public class ReportEditorHelper {

    SikuliActions sikuliActions;
    RobotActions robotActions;
    String reportName;
    String sectionName;
    String tabName;

    public ReportEditorHelper() {
        sikuliActions = new SikuliActions();
        robotActions = new RobotActions();
    }

    public ReportEditorHelper(String reportName, String sectionName, String tabName) {
        this();
        this.reportName = reportName;
        this.sectionName = sectionName;
        this.tabName = tabName;
    }

    public String getReportScreenPath() {
        return USER_WORK_DIR + ReportScreens + reportName + "\\" + sectionName + "\\" + tabName + "\\";
    }

    public Map<String, String> getReportEntries(String reportName, String sectionName, String tabName) {
        this.reportName = reportName;
        this.sectionName = sectionName;
        this.tabName = tabName;
        return YmlReader.getReportEntries(
                String.format(USER_WORK_DIR + Constants.ReportEntriesFile, reportName.replaceAll(" ", "")), sectionName,
                tabName);
    }

    public Map<String, String> getReportPhraseMappings(String reportName, String sectionName, String tabName) {
        return YmlReader.getReportEntries(String.format(USER_WORK_DIR + Constants.ReportMapFile,
                reportName.replaceAll(" ", "")), sectionName, tabName);
    }

    public void selectCheckbox(String fieldName) {
        sikuliActions.selectCheckBox(getReportScreenPath() + fieldName + "_Checkbox.PNG");
    }

    public void selectCheckboxInARegion(String fieldName, Rectangle regionBounds) {
        sikuliActions.selectCheckBox(getReportScreenPath() + fieldName + "_Checkbox", regionBounds);
    }

    public void selectAllCheckboxesInARegion(Rectangle regionBounds, String commaSeparatedValues) {
        for (String valueComponent : commaSeparatedValues.split(",")) {
            sikuliActions.selectCheckBox(getReportScreenPath() + valueComponent.trim() + "_Checkbox.PNG",
                    regionBounds);
        }
    }

    public void selectAllCheckboxesInARegion(Rectangle regionBounds, String commaSeparatedValues, String separator) {
        for (String valueComponent : commaSeparatedValues.split(separator)) {
            sikuliActions.selectCheckBox(getReportScreenPath() + valueComponent.trim() + "_Checkbox.PNG",
                    regionBounds);
        }
    }

    public void enterValueInTextbox(String fieldName, String value) {
        sikuliActions.typeTextInExactField(getReportScreenPath() + fieldName + "_Textbox", value);
        robotActions.enter();
    }

    public void enterValueInTextboxFromARegion(Rectangle regionBounds, String fieldName, String value) {
        sikuliActions.typeTextInExactFieldInARegion(regionBounds, getReportScreenPath() + fieldName + "_Textbox", value);
        robotActions.enter();
    }

    public Rectangle getRegionBounds(String region) {
        return sikuliActions.getExactRegionBounds(getReportScreenPath() + region + "_Region.PNG");
    }

    public String selectRandomValueFromDropdown(String fieldName, String semiColonSeparatedValues) {
        String value = getRandomValue(semiColonSeparatedValues);
        sikuliActions.selectValueFromDropdown(getReportScreenPath() + fieldName + "_Dropdown.PNG", value);
        return value;
    }

    public String selectRandomValueFromDropdownInARegion(Rectangle regionBounds, String fieldName, String semiColonSeparatedValues) {
        String value = getRandomValue(semiColonSeparatedValues);
        sikuliActions.selectValueFromDropdown(regionBounds, getReportScreenPath() + fieldName + "_Dropdown.PNG", value);
        return value;
    }

    public String selectRandomValueFromExactDropdown(String fieldName, String semiColonSeparatedValues) {
        String value = getRandomValue(semiColonSeparatedValues);
        sikuliActions.selectValueFromExactDropdown(getReportScreenPath() + fieldName + "_Dropdown.PNG", value);
        return value;
    }

    public String selectRandomRadioButton(String semiColonSeparatedRadioValues) {
        String radioButtonValue = getRandomValue(semiColonSeparatedRadioValues);
        sikuliActions.scrollToField(getReportScreenPath() + radioButtonValue + "_Radio.PNG");
        sikuliActions.selectRadio(getReportScreenPath() + radioButtonValue + "_Radio.PNG");
        return radioButtonValue;
    }

    public String selectRandomRadioButtonFromARegion(Rectangle regionBounds, String semiColonSeparatedValues) {
        String value = getRandomValue(semiColonSeparatedValues);
        sikuliActions.selectRadioFromRegion(getReportScreenPath() + value.replaceAll(" / ", " ").replaceAll("/", " ") + "_Radio.PNG", regionBounds);
        return value;
    }

    public void selectRadioButton(String radioButtonName) {
        sikuliActions.scrollToField(getReportScreenPath() + radioButtonName + "_Radio.PNG");
        sikuliActions.selectRadio(getReportScreenPath() + radioButtonName + "_Radio.PNG");
    }

    public void enterTextAndSelectCheckbox(String fieldName, String value) {
        sikuliActions.typeTextInField(getReportScreenPath() + fieldName + "_Textbox.PNG", value);
        robotActions.enter();
        sikuliActions.selectCheckBox(getReportScreenPath() + fieldName + "_Checkbox.PNG");
    }

    public String getFieldValue(String fieldName) {
        sikuliActions.scrollToField(getReportScreenPath() + fieldName + "_Textbox.PNG");
        return sikuliActions.getTextFromExactField(getReportScreenPath() + fieldName + "_Textbox.PNG");
    }

    public void addCode(String fieldName, String code) {
        enterValueInTextbox(fieldName, code);
        sikuliActions.waitForElementToExist(getReportScreenPath() + "Loading.PNG", 5);
        sikuliActions.waitForElementInvisibility(getReportScreenPath() + "Loading.PNG", 30);
        robotActions.pressTabKey();
        Common.sleep(1, TimeUnit.SECONDS);
        robotActions.pressTabKey();
        Common.sleep(1, TimeUnit.SECONDS);
        robotActions.pressDownArrowKey();
        sikuliActions.click(getReportScreenPath() + "Add" + "_Button.PNG");
    }

    public String getRandomValue(String entryValues) {
        String[] values = entryValues.split(";");
        Random random = new Random();
        int index = random.nextInt(values.length);
        Log.printInfo("Random value: " + values[index]);
        return values[index];
    }
}
