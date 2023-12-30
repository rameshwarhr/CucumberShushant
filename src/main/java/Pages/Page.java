package Pages;

import static Utilities.Constants.PageORFile;
import static Utilities.Constants.USER_WORK_DIR;

import java.util.List;

import Actions.WindowsActions;
import Utilities.Log;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

import Actions.RobotActions;
import Actions.SikuliActions;
import Actions.WebActions;
import ObjectRepository.PageOR;
import Utilities.Driver;
import Utilities.YmlReader;
import org.openqa.selenium.WebElement;

public class Page {
    Driver driver;
    WebActions webActions;
    SikuliActions sikuliActions;
    RobotActions robotActions;
    WindowsActions winActions;

    public Page(Driver driver) {
        this.driver = driver;
        webActions = new WebActions(driver.getWebDriver());
        sikuliActions = new SikuliActions();
        robotActions = new RobotActions();
        winActions = new WindowsActions(driver.getWinDriver());
    }

    private static final PageOR PageOR = YmlReader.getObjectRepository(USER_WORK_DIR + PageORFile, PageOR.class);

    public By getByLocator(String locator, LocatorIdentifier identifier) {

        switch (identifier) {
            case Id:
                return By.id(locator);
            case Name:
                return By.name(locator);
            case ClassName:
                return By.className(locator);
            default:
                return By.xpath(locator);
        }
    }

    public By getByLocator(String locator, LocatorIdentifier identifier, Object... parameters) {
        switch (identifier) {
            case Id:
                return By.id(String.format(locator, parameters));
            case Xpath:
                return By.xpath(String.format(locator, parameters));
            case Name:
                return By.name(String.format(locator, parameters));
            case ClassName:
                return By.className(String.format(locator, parameters));
            default:
                return By.xpath(String.format(locator, parameters));
        }
    }

    public boolean isFloatingToolbarVisible() {
        try {
            return webActions.isVisible(getByLocator(PageOR.floatingToolbar_Xpath, LocatorIdentifier.Xpath), 5);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEditIconVisible() {
        try {
            return webActions.isVisible(getByLocator(PageOR.editIcon_Xpath, LocatorIdentifier.Xpath), 5);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEditSeriesIconVisible() {
        try {
            return webActions.isVisible(getByLocator(PageOR.editSeriesIcon_Xpath, LocatorIdentifier.Xpath), 5);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFloatingElementVisible(String element) {
        boolean elementVisibleStatus = false;
        switch (element.toUpperCase()) {
            case "TOOLBAR":
            case "STUDY TOOLBAR":
                elementVisibleStatus = isFloatingToolbarVisible();
                break;
            case "ICON":
                elementVisibleStatus = isEditIconVisible();
                break;
            case "SERIES ICON":
                elementVisibleStatus = isEditSeriesIconVisible();
                break;
            case "VIEW LAST FINAL REPORT":
            case "ASSIGN STUDY TO PHYSICIANS":
            case "ASSIGN STUDY TO REFERRING PHYSICIANS":
            case "ASSIGN STUDY TO PHYSICIAN GROUPS":
            case "OCR STUDY":
            case "EXPORT LAST FINAL REPORT WITH HL7":
            case "STUDY CHARGES":
            case "EDIT STUDY ATTRIBUTES":
                elementVisibleStatus = !webActions
                        .getElementAttributeValue(getByLocator(PageOR.floatingIcon_Xpath, LocatorIdentifier.Xpath, element),
                                "class")
                        .contains("Disabled");
                break;
        }
        return elementVisibleStatus;
    }

    public void clickEditSeriesIcon() {
        webActions.click(getByLocator(PageOR.editSeriesIcon_Xpath, LocatorIdentifier.Xpath));
    }

    public String getNoItemsToShowMessage() {
        return webActions.getText(getByLocator(PageOR.noItemsToShow_Xpath, LocatorIdentifier.Xpath), 10);
    }

    public void waitForDataToLoad() {
        webActions.waitForElementToDisappear(getByLocator(PageOR.loadingIndicator_Xpath, LocatorIdentifier.Xpath), 30);
    }

    public boolean isStudyRightClickMenuEnabled(String option) {
        boolean elementVisibleStatus = false;
        switch (option.toUpperCase()) {
            case "VIEW LAST FINAL REPORT":
                elementVisibleStatus = !webActions.getElementAttributeValue(
                        getByLocator(PageOR.viewLastFinalReportRightClickMenuItem_Xpath, LocatorIdentifier.Xpath), "class")
                        .contains("Disabled");
                break;
        }
        return elementVisibleStatus;
    }

    public void waitForDataToLoad(long seconds) {
        webActions.waitForElementToDisappear(getByLocator(PageOR.loadingIndicator_Xpath, LocatorIdentifier.Xpath),
                seconds);
    }

    public boolean isDeleteSeriesButtonEnabled() {
        return !webActions
                .getElementAttributeValue(getByLocator(PageOR.deleteSeriesButton_Xpath, LocatorIdentifier.Xpath), "src")
                .contains("Disabled");
    }

    public void moveMouseByOffset(int xOffset, int yOffset) {
        sikuliActions.moveMouseByOffset(xOffset, yOffset);
    }

    public void closeEditStudyAttributesWindow() {
        webActions.click(getByLocator(PageOR.closeButtonPopUpWindow_Xpath, LocatorIdentifier.Xpath));
    }

    public void navigateToTab(String tabName) {
        webActions.click(getByLocator(PageOR.tabHeader_Xpath, LocatorIdentifier.Xpath, tabName));
        waitForDataToLoad();
    }

    public void clickFloatingToolbarIcon(String icon) {
        webActions.click(getByLocator(PageOR.floatingIcon_Xpath, LocatorIdentifier.Xpath, icon));
    }

    public boolean isNestedFilterConditionMet(String filterCondition, List<String> rowValueList,
                                              List<String> filterValueList, String matchCondition) {
        boolean isNestedFilterApplied = true;
        boolean isMatchConditionMet = true;
        for (int rowValueIndex = 0; rowValueIndex < filterValueList.size(); rowValueIndex++) {
            String value = rowValueList.get(rowValueIndex);
            String filterValue = filterValueList.get(rowValueIndex);
            switch (filterCondition.toUpperCase()) {
                case "CONTAINS":
                case "CONTAINS (MATCH CASE)":
                    isNestedFilterApplied = isNestedFilterApplied
                            && (filterCondition.equals("MATCH CASE") ? StringUtils.containsNone(value, filterValue)
                            : StringUtils.containsIgnoreCase(value, filterValue));
                    break;

                case "DOES NOT CONTAIN":
                case "DOES NOT CONTAIN (MATCH CASE)":
                    isNestedFilterApplied = isNestedFilterApplied
                            && (filterCondition.equals("MATCH CASE") ? !StringUtils.containsNone(value, filterValue)
                            : !StringUtils.containsIgnoreCase(value, filterValue));
                    break;

                case "EQUALS":
                case "EQUALS (DISREGARD CASE)":
                    isNestedFilterApplied = isNestedFilterApplied
                            && (filterCondition.equals("DISREGARD CASE") ? StringUtils.equalsIgnoreCase(value, filterValue)
                            : StringUtils.equals(value, filterValue));
                    break;

                case "NOT EQUAL":
                case "NOT EQUAL (DISREGARD CASE)":
                    isNestedFilterApplied = isNestedFilterApplied
                            && (filterCondition.equals("DISREGARD CASE") ? !StringUtils.equalsIgnoreCase(value, filterValue)
                            : !StringUtils.equals(value, filterValue));
                    break;

                case "STARTS WITH":
                case "STARTS WITH (MATCH CASE)":
                    isNestedFilterApplied = isNestedFilterApplied
                            && (filterCondition.equals("MATCH CASE") ? StringUtils.startsWith(value, filterValue)
                            : StringUtils.startsWithIgnoreCase(value, filterValue));
                    break;

                case "DOES NOT START WITH":
                case "DOES NOT START WITH (MATCH CASE)":
                    isNestedFilterApplied = isNestedFilterApplied
                            && (filterCondition.equals("MATCH CASE") ? !StringUtils.startsWith(value, filterValue)
                            : !StringUtils.startsWithIgnoreCase(value, filterValue));
                    break;

                case "IS ONE OF":
                    isNestedFilterApplied = isNestedFilterApplied && (StringUtils.containsIgnoreCase(value, filterValue));
                    break;
            }

            switch (matchCondition.toUpperCase()) {
                case "MATCH ANY":
                    isMatchConditionMet = isMatchConditionMet || (isNestedFilterApplied);
                    break;
                case "MATCH ALL":
                    isMatchConditionMet = isMatchConditionMet && (isNestedFilterApplied);
                    break;
                case "MATCH NONE":
                    isMatchConditionMet = isMatchConditionMet && !(isNestedFilterApplied);
                    break;
            }
        }
        return isMatchConditionMet;
    }

    public void pressTabKey() {
        robotActions.pressTabKey();
    }

    public void pressEnterKey() {
        robotActions.enter();
    }

    public void acceptTheEditReportPopupIfVisible() {
        try {
            if (winActions.isVisible(getByLocator(PageOR.editReportDialog_Xpath, LocatorIdentifier.Xpath),
                    3)) {
                winActions.click(getByLocator(PageOR.reportDialogNoButton_Name, LocatorIdentifier.Name));
            }
        } catch (Exception e) {
            Log.printError("Edit Report Dialog is not visible");
        }
    }

    public void selectSourceToPopulateData() {
        try {
            if (winActions.isVisible(getByLocator(PageOR.questionDialogBox_Xpath, LocatorIdentifier.Xpath), 3)) {
                WebElement dialog = winActions.findWindowElementBy(
                        getByLocator(PageOR.questionDialogBox_Xpath, LocatorIdentifier.Xpath));
                winActions.click(dialog,
                        getByLocator(PageOR.questionDialogBoxOKButton_Name, LocatorIdentifier.Name));
            }
        } catch (Exception e) {
            Log.printError("Source To Populate Data Dialog is not visible");
        }
    }

    public void acceptTheSystemLockPopupIfVisible() {
        try {
            if (winActions.isVisible(getByLocator(PageOR.systemLockDialog_Xpath, LocatorIdentifier.Xpath),
                    3)) {
                WebElement dialog = winActions.findWindowElementBy(getByLocator(PageOR.systemLockDialog_Xpath, LocatorIdentifier.Xpath));
                winActions.click(dialog, getByLocator(PageOR.systemLockYesButton_Name, LocatorIdentifier.Name));
            }
        } catch (Exception e) {
            Log.printError("System Lock Dialog is not visible");
        }
    }

    public byte[] getScreenshot() {
        return new SikuliActions().getScreenshot();
    }
}