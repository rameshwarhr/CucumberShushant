package Pages;

import Actions.RobotActions;
import Actions.SikuliActions;
import Actions.WebActions;
import Actions.WindowsActions;
import ObjectRepository.ReportViewerPageOR;
import ReportEditors.ReportEditorHelper;
import TestData.TestData;
import Utilities.*;
import org.apache.commons.lang3.Range;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static Utilities.Constants.*;

public class ReportViewerPage extends Page {

    WebActions webActions;
    WindowsActions winActions;
    SikuliActions sikuliActions;
    WebElement reportViewerWindow;
    PdfReader pdfReader;
    RobotActions robotActions;

    public static final String CommonElementDir = USER_WORK_DIR + ReportScreens + "Common\\";
    public static final String MyocardialPerfusionScanReport = USER_WORK_DIR
            + "\\src\\test\\resources\\ReportScreenElements\\MyocardialPerfusionScanReport\\";
    private static final String SendFaxEmailElements = USER_WORK_DIR + "\\src\\test\\resources\\SendFaxEmailElements\\";
    private static final String OBFetalEchoPreliminaryReport = USER_WORK_DIR
            + "\\src\\test\\resources\\ReportScreenElements\\OBFetalEchoPreliminaryReport\\";
    private static final String OBUltrasoundFinalReport = USER_WORK_DIR
            + "\\src\\test\\resources\\ReportScreenElements\\OBUltrasoundFinalReport\\";

    public ReportViewerPage(Driver driver) {
        super(driver);
        webActions = new WebActions(driver.getWebDriver());
        winActions = new WindowsActions(driver.getWinDriver());
        sikuliActions = new SikuliActions();
        pdfReader = new PdfReader();
        robotActions = new RobotActions();
    }

    private static final ReportViewerPageOR reportViewerPageOR = YmlReader
            .getObjectRepository(USER_WORK_DIR + ReportViewerPageORFile, ReportViewerPageOR.class);

    private final By reportsEditorWindow = getByLocator(reportViewerPageOR.reportsEditorWindow_Xpath,
            LocatorIdentifier.Xpath);
    private final By reportsTitleBar = getByLocator(reportViewerPageOR.reportsTitleBar_Xpath, LocatorIdentifier.Xpath);
    private final By signReport = getByLocator(reportViewerPageOR.signReportButton_Xpath, LocatorIdentifier.Xpath);
    private final By signReportDialog = getByLocator(reportViewerPageOR.signReportDialog_Xpath, LocatorIdentifier.Xpath);
    private final By signReportYesButton = getByLocator(reportViewerPageOR.signReportYesButton_Name, LocatorIdentifier.Name);
    private final By saveReportDialog = getByLocator(reportViewerPageOR.saveReportDialog_Xpath, LocatorIdentifier.Xpath);
    private final By saveReportDialogSaveButton = getByLocator(reportViewerPageOR.saveReportDialogSaveButton_Name,
            LocatorIdentifier.Name);
    private final By saveReportDialogDontSaveButton = getByLocator(reportViewerPageOR.saveReportDialogDontSaveButton_Name,
            LocatorIdentifier.Name);
    private final By closeReportButton = getByLocator(reportViewerPageOR.closeReportButton_Name, LocatorIdentifier.Name);

    public void waitForReportViewerToLoad() {
        try {
            winActions.waitForWindowElementToBeVisible(reportsEditorWindow, 30);
            reportViewerWindow = getReportViewerWindow();
        } catch (Exception e) {
            reportViewerWindow = getReportViewerWindow();
            Log.printInfo(e.getMessage());
        }
    }

    public WebElement getReportViewerWindow() {
        try {
            return winActions.findWindowElementBy(reportsEditorWindow);
        } catch (Exception e) {
            acceptTheEditReportPopupIfVisible();
            selectSourceToPopulateData();
            acceptTheSystemLockPopupIfVisible();
            return winActions.findWindowElementBy(reportsEditorWindow);
        }
    }

    public boolean isReportWindowVisible() {
        try {
            return winActions.findWindowElementBy(reportsEditorWindow).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void waitForReportWindowToClose() {
        winActions.waitForWindowElementToBeInvisible(reportsEditorWindow, 10);
    }

    public void saveReport() {
        winActions.waitForWindowElementToBeVisible(saveReportDialog, 60);
        WebElement Dialog = winActions.findWindowElementBy(saveReportDialog);
        winActions.click(Dialog, saveReportDialogSaveButton);
    }

    public void dontSaveReport() {
        if (winActions.isVisible(saveReportDialog, 60)) {
            WebElement dialog = winActions.findWindowElementBy(saveReportDialog);
            winActions.click(dialog, saveReportDialogDontSaveButton);
            try {
                waitForReportWindowToClose();
            } catch (org.openqa.selenium.TimeoutException e) {
                dialog = winActions.findWindowElementBy(saveReportDialog);
                winActions.click(dialog, saveReportDialogDontSaveButton);
            } catch (Exception e) {
                Log.printInfo("Unable to close report viewer");
            }
        }
    }

    public void closeReportViewer() {
        try {
            WebElement titleBar = winActions.findWindowElementBy(reportsTitleBar);
            winActions.click(titleBar, closeReportButton);
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().contains("NOT CLICK")) {
                robotActions.enter();
                Common.sleep(1, TimeUnit.SECONDS);
                closeReportViewer();
            }
        }
    }

    public void fillReportDetails(String reportName, List<Map<String, String>> reportDetails) {
        for (Map<String, String> reportDetail : reportDetails) {
            String type = reportDetail.get("type").toUpperCase();
            String fieldName = reportDetail.get("field");
            String fieldValue = reportDetail.get("value");
            switch (type) {
                case "TEXT":
                    typeInReportField(reportName, fieldName + "_" + type, fieldValue);
                    robotActions.pressTabKey();
                    break;
                case "DROPDOWN":
                    selectValueFromDropdown(reportName, fieldName + "_" + type, fieldValue);
                    break;
                case "RADIO":
                    selectRadioFromRegion(reportName, fieldName, fieldValue + "_" + type);
                    break;
                case "CHECKBOX":
                    selectCheckBox(reportName, fieldName, fieldValue + "_" + type);
                    break;
            }
        }
    }

    public void typeInReportField(String ReportName, String FieldName, String value) {
        String patternFile = Common.getReportLocation(ReportName) + FieldName + ".PNG";
        try {
            robotActions.pressTabKey();
            sikuliActions.scrollToField(patternFile);
            sikuliActions.typeTextInField(patternFile, value);
            robotActions.pressTabKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CancelSendFaxEmail() {
        sikuliActions.waitForElementToExist(CommonElementDir + "SendFaxAndEmail.PNG", 5);
        String cancelButtonPatternFile = CommonElementDir + "CancelButtonSendFaxEmail.PNG";
        sikuliActions.waitForElementToExist(cancelButtonPatternFile, 10);
        sikuliActions.click(cancelButtonPatternFile);
        sikuliActions.waitForElementInvisibility(CommonElementDir + "SendFaxAndEmail.PNG", 5);
    }

    public void scrollToField(String fieldName) {
        String patternFile = USER_WORK_DIR + ReportScreens + "ReportScreen\\" + fieldName + ".PNG";
        sikuliActions.scrollToField(patternFile);
    }

    public void selectValueFromDropdown(String ReportName, String FieldName, String value) {
        String patternFile = Common.getReportLocation(ReportName) + FieldName + ".PNG";
        try {
            sikuliActions.scrollToField(patternFile);
            sikuliActions.selectValueFromDropdown(patternFile, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectRadioFromRegion(String ReportName, String Region, String FieldName) {
        String fieldPatternFile = Common.getReportLocation(ReportName) + FieldName + ".PNG";
        String regionPatternFile = Common.getReportLocation(ReportName) + Region + ".PNG";
        try {
            sikuliActions.scrollToField(regionPatternFile);
            sikuliActions.selectRadioFromRegion(fieldPatternFile, regionPatternFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectCheckBox(String ReportName, String Region, String FieldName) {
        String fieldPatternFile = Common.getReportLocation(ReportName) + FieldName + ".PNG";
        String regionPatternFile = Common.getReportLocation(ReportName) + Region + ".PNG";
        try {
            sikuliActions.scrollToExactField(regionPatternFile);
            sikuliActions.selectCheckBox(fieldPatternFile, regionPatternFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickSignReport() {
        winActions.click(signReport);
    }

    public void clickSignReportYes() {
        winActions.waitForWindowElementToBeVisible(signReportDialog, 30);
        WebElement signDialog = winActions.findWindowElementBy(signReportDialog);
        winActions.click(signDialog, signReportYesButton);
        waitForReportToBeSaved();
    }

    public boolean areEnteredDetailsPresentInReport(List<Map<String, String>> reportDetails) {
        try {
            boolean flag = true;
            webActions.switchToWindow(Constants.reportWindowTitle, 20);
            String reportWindowUrl = webActions.getCurrentWindowUrl();
            String pdfData = pdfReader.getPDFData(reportWindowUrl, TestData.userDetails.globalAdminUsername,
                    TestData.userDetails.globalAdminPassword);
            for (Map<String, String> reportDetail : reportDetails) {
                String fieldValue = reportDetail.get("value");
                flag = flag && pdfData.contains(fieldValue);
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void selectReportContentOnReportViewer(String screenName) {

        String patternFile = USER_WORK_DIR + ReportScreens + "ReportScreen\\" + screenName + ".PNG";
        try {
            sikuliActions.click(patternFile);
            sikuliActions.wait(1.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void typeTextInField(String fieldName, String value) {
        String patternFile = USER_WORK_DIR + ReportScreens + "ReportScreen\\" + fieldName + ".PNG";
        sikuliActions.typeTextInField(patternFile, value);
    }

    public boolean isDiagramPresentOnDynamicPreview() {
        return sikuliActions.isElementVisible(USER_WORK_DIR + ReportScreens + "\\ReportScreen\\WithDiagram.PNG");
    }

    public void moveImageInTheReport() {
        sikuliActions.dragElementToOffset(USER_WORK_DIR + ReportScreens + "\\ReportScreen\\DragSource.PNG", 100, 0);
    }

    public boolean isImageMovedOnReport() {
        sikuliActions.waitForElementToExist(USER_WORK_DIR + ReportScreens + "\\ReportScreen\\MovementVerification.PNG",
                20);
        return sikuliActions
                .isElementVisible(USER_WORK_DIR + ReportScreens + "\\ReportScreen\\MovementVerification.PNG");
    }

    public boolean isReportViewerOpenedInNewWindow() {
        return winActions.getElementsCount(
                getByLocator(reportViewerPageOR.reportViewerWindow_Xpath, LocatorIdentifier.Xpath)) > 1;
    }

    public boolean isSaveReportTemplateWindowVisible() {
        return winActions
                .isVisible(getByLocator(reportViewerPageOR.saveReportTemplateWindow_Name, LocatorIdentifier.Name));
    }

    public void pressEnter() {
        robotActions = new RobotActions();
        robotActions.enter();
    }

    public void enterReportTemplateName(String value) {
        winActions.setText(getByLocator(reportViewerPageOR.saveReportTemplateNameTextbox_Name, LocatorIdentifier.Name),
                value);

    }

    public boolean isReportDefaultViewSet(String viewPattern) {
        String patternFile = null;
        switch (viewPattern.toUpperCase()) {
            case "FIT TO PAGE":
            case "FITTOPAGE":
                patternFile = CommonElementDir + "FitToPage.PNG";
                break;
            case "FIT WIDTH":
            case "FITWIDTH":
            case "FIT THE WIDTH":
                patternFile = CommonElementDir + "FitWidth.PNG";
                break;
            case "ZOOM IN":
            case "ZOOMED IN":
                patternFile = CommonElementDir + "ZoomIn.PNG";
                break;
            case "ZOOM OUT":
            case "ZOOMED OUT":
                patternFile = CommonElementDir + "ZoomOut.PNG";
        }
        return sikuliActions.isElementVisible(patternFile);
    }

    public boolean isChangeReflectedOnReport(String screenName) {
        String patternFile = USER_WORK_DIR + ReportScreens + "ReportScreen\\" + screenName + ".PNG";
        sikuliActions.waitForElementToExist(patternFile, 10);
        return sikuliActions.isElementVisible(patternFile);
    }

    public boolean isPrintWindowDisplayed() {
        winActions.waitForWindowElementToBeVisible(
                getByLocator(reportViewerPageOR.printWindow_Xpath, LocatorIdentifier.Xpath), 10);
        return winActions.isVisible(getByLocator(reportViewerPageOR.printWindow_Xpath, LocatorIdentifier.Xpath));
    }

    public void cancelPrint() {
        if (winActions
                .isVisible(getByLocator(reportViewerPageOR.printWindowCancelButton_Xpath, LocatorIdentifier.Xpath)))
            winActions.click(getByLocator(reportViewerPageOR.printWindowCancelButton_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isAccreditationLogoPresentInReport(String reportName) {
        String patternFile = null;
        switch (reportName) {
            case "TCI Preliminary":
                patternFile = USER_WORK_DIR + ReportScreens + "Common\\TCI Logo.PNG";
                break;
            case "Echo & Exercise Stress Preliminary":
                patternFile = USER_WORK_DIR + ReportScreens + "Common\\Echo Logo.PNG";
                break;
            case "New Myocardial Perfusion Scan Preliminary":
                patternFile = USER_WORK_DIR + ReportScreens + "Common\\Myocardial Logo.PNG";
                break;
        }
        return sikuliActions.isElementVisible(patternFile);
    }

    public void selectSectionFromLeftPanel(String sectionName) {
        sikuliActions.waitForElementToExist(CommonElementDir + sectionName + " Section.PNG", 10);
        sikuliActions.click(CommonElementDir + sectionName + " Section.PNG");
        Common.sleep(1, TimeUnit.SECONDS);
    }

    public void selectSectionFromLeftPanel(String reportName, String sectionName) {
        String screenPath = Common.getReportLocation(reportName) + sectionName + ".PNG";
        sikuliActions.waitForElementToExist(screenPath, 10);
        sikuliActions.click(screenPath);
    }

    public void selectTab(String tabName) {
        sikuliActions.click(CommonElementDir + tabName + " Tab.PNG");
        robotActions.pressTabKey();
    }

    public void selectTab(String reportName, String sectionName, String tabName) {
        sikuliActions.click(
                Common.getReportLocation(reportName) + sectionName + "\\" + tabName + "\\" + tabName + "_Tab.PNG");
        robotActions.pressTabKey();
    }

    public boolean isReportEntryVisibleOnDynamicPreview(String sectionName) {
        sikuliActions.waitForElementToExist(CommonElementDir + sectionName + ".PNG", 10);
        return sikuliActions.isElementVisible(CommonElementDir + sectionName + ".PNG");
    }

    public void selectCodeFromCategory(String code, String codeCategory) {
        sikuliActions.doubleClick(CommonElementDir + codeCategory + " category.PNG");
        sikuliActions.waitForElementToExist(CommonElementDir + code + ".PNG", 10);
        sikuliActions.doubleClick(CommonElementDir + code + ".PNG");
    }

    public void selectContentFromTab(String content, String tabName) {
        selectTab(tabName);
        sikuliActions.selectCheckBox(CommonElementDir + content + ".PNG");
    }

    public void provideRemarks(String remark, String remarkSection) {
        sikuliActions.waitForElementToExist(CommonElementDir + remarkSection + ".PNG", 10);
        sikuliActions.typeTextInField(CommonElementDir + remarkSection + ".PNG", remark);
    }

    public boolean isValidationErrorPopupVisible() {
        return winActions
                .isVisible(getByLocator(reportViewerPageOR.reportValidationPopup_Name, LocatorIdentifier.Name));
    }

    public void controlAndLeftClickHeartRateLabel() {
        sikuliActions.hover(CommonElementDir + "Heart rate.PNG");
        sikuliActions.controlClick();
    }

    public boolean isChangePhrasePopupVisible() {
        return winActions.isVisible(getByLocator(reportViewerPageOR.changePhrasePopup_Name, LocatorIdentifier.Name));
    }

    public void enterPhrase(String value) {
        String patternFile = CommonElementDir + "Change Phrase Text.PNG";
        sikuliActions.typeTextInField(patternFile, value);
    }

    public void clickOKButton() {
        sikuliActions.click(CommonElementDir + "Ok Button.PNG");
    }

    public boolean isPhraseChanged() {
        sikuliActions.hover(CommonElementDir + "Heart rate.PNG");
        return sikuliActions.isElementVisible(CommonElementDir + "Heart rate Phrase.PNG");
    }

    public void selectCheckbox(String checkboxText) {

        robotActions.pressTabKey();
        sikuliActions.selectCheckBox(CommonElementDir + checkboxText + ".PNG");
    }

    public void clickButtonOnDialog(String buttonName) {
        winActions.click(getByLocator(reportViewerPageOR.dialogButton_Xpath, LocatorIdentifier.Xpath, buttonName));
    }

    public void moveToPage(String pageDirection) {
        sikuliActions.waitForElementToExist(CommonElementDir + pageDirection + ".PNG", 20);
        sikuliActions.click(CommonElementDir + pageDirection + ".PNG");
    }

    public boolean isReportPreviewMovedToOtherPage(String direction) {
        boolean flag = false;
        switch (direction.toUpperCase()) {
            case "FORWARD":
                flag = sikuliActions.isElementVisible(CommonElementDir + "Page 2 of 2.PNG");
                break;

            case "BACKWARD":
                flag = sikuliActions.isElementVisible(CommonElementDir + "Page 1 of 2.PNG");
                break;
        }
        return flag;

    }

    public void rightClickMeasurementLabel(String labelName) {
        String patternFile = USER_WORK_DIR + ReportScreens + "ReportScreen\\" + labelName + ".PNG";
        try {
            sikuliActions.rightClickExact(patternFile);
            sikuliActions.wait(1.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isLabelSelected(String labelName) {
        String patternFile = USER_WORK_DIR + ReportScreens + "ReportScreen\\" + labelName + ".PNG";
        return sikuliActions.isExactElementVisible(patternFile);
    }

    public void clickImpressionsTab() {
        sikuliActions.click(USER_WORK_DIR + ReportScreens + "ReportScreen\\" + "Impressions.PNG");
    }

    public void clickFreeTextSubTab() {
        sikuliActions.click(USER_WORK_DIR + ReportScreens + "ReportScreen\\" + "Free text.PNG");
    }

    public boolean isFreeTextListDisplayed() {
        return sikuliActions
                .isExactElementVisible(USER_WORK_DIR + ReportScreens + "ReportScreen\\" + "Labels list.PNG");
    }

    public void changeListElements() {
        String patternFile = USER_WORK_DIR + ReportScreens + "ReportScreen\\" + "List 1.PNG";
        sikuliActions.hoverExact(patternFile);
        sikuliActions.moveToElement(patternFile);
        sikuliActions.moveMouseByOffset(-56, 0);
        sikuliActions.dragMouseByOffset(0, 40, false);
        sikuliActions.releaseMouse();
    }

    public boolean isListChanged() {
        sikuliActions.moveMouseByOffset(-80, 0);
        return sikuliActions.isElementVisible(USER_WORK_DIR + ReportScreens + "ReportScreen\\" + "List Change.PNG");
    }

    public boolean isListChangedOnDynamicPreview() {
        sikuliActions.waitForExactElementToExist(CommonElementDir + "Dynamic Preview Conclusion.PNG", 5);
        return sikuliActions
                .isElementVisible(USER_WORK_DIR + ReportScreens + "ReportScreen\\" + "Dynamic Preview List.PNG");
    }

    public void clickReferringPhysicianIcon() {
        sikuliActions.click(USER_WORK_DIR + ReportScreens + "ReportScreen\\" + "ReferringPhysicianIcon.PNG");
    }

    public void selectReferringPhysician() {
        if (!sikuliActions.isElementVisible(SendFaxEmailElements + "vidistarfax.PNG")) {
            sikuliActions.moveToElement(SendFaxEmailElements + "ReferringPhysicianList.PNG");
            sikuliActions.moveMouseByOffset(0, 20);
            sikuliActions.click();
            sikuliActions.scrollToExactField(SendFaxEmailElements + "vidistarfax.PNG", 10);
        }
        sikuliActions.doubleClick(SendFaxEmailElements + "vidistarfax.PNG");
    }

    public boolean isAssignedRefPhysicianDisplayedOnReport() {
        WebElement reportDynamicPreviewWindow = winActions.findWindowElementBy(
                getByLocator(reportViewerPageOR.reportDynamicPreviewWindow_Name, LocatorIdentifier.Name));
        sikuliActions.waitForElementToExist(USER_WORK_DIR + ReportScreens + "ReportScreen\\" + "ReferredBy.PNG", 3);
        return sikuliActions.isElementVisible(winActions.getElementBounds(reportDynamicPreviewWindow),
                USER_WORK_DIR + ReportScreens + "ReportScreen\\" + "ReferredBy.PNG");
    }

    public boolean isPhysicianPresentInSelectedContacts() {
        WebElement sendFaxEmailDialog = winActions.findWindowElementBy(
                getByLocator(reportViewerPageOR.sendFaxEmailDialog_Xpath, LocatorIdentifier.Xpath));
        sikuliActions.waitForElementToExist(USER_WORK_DIR + ReportScreens + "ReportScreen\\" + "SelectedContacts.PNG",
                10);
        return sikuliActions.isElementVisible(winActions.getElementBounds(sendFaxEmailDialog),
                USER_WORK_DIR + ReportScreens + "ReportScreen\\" + "SelectedContacts.PNG");
    }

    public void clickSendButtonOnFaxEmailDialog() {
        sikuliActions.click(SendFaxEmailElements + "Send.PNG");
        robotActions.enter();
    }

    public void waitForSendFaxAndEmailDialog() {
        winActions.waitForWindowElementToBeVisible(
                getByLocator(reportViewerPageOR.sendFaxEmailDialog_Xpath, LocatorIdentifier.Xpath), 60);
    }

    public boolean isSendFaxAndEmailDialogBoxDisplayed() {
        return winActions.isVisible(getByLocator(reportViewerPageOR.sendFaxEmailDialog_Xpath, LocatorIdentifier.Xpath), 5);
    }

    public void switchToSubtab(String subtabName) {
        sikuliActions.waitForElementToExist(CommonElementDir + subtabName + ".PNG", 10);
        sikuliActions.click(CommonElementDir + subtabName + ".PNG");
    }

    public void performActionOnTemplate(String actionName, String templateName) {
        clickOnTemplateActionButton(actionName);
        sikuliActions.typeTextInField(MyocardialPerfusionScanReport + "Template Name Text Field.PNG", templateName);
        sikuliActions.click(MyocardialPerfusionScanReport + "Ok Button.PNG");
    }

    public boolean isTemplateSaved(String templateName) {
        return sikuliActions.isExactElementVisible(MyocardialPerfusionScanReport + templateName + ".PNG");
    }

    public void createNewFolderUnderTemplateBox(String templateName, String folderName) {
        sikuliActions.rightClickExact(MyocardialPerfusionScanReport + templateName + ".PNG");
        sikuliActions.click(MyocardialPerfusionScanReport + "New folder option.PNG");
        sikuliActions.typeTextInField(MyocardialPerfusionScanReport + "Folder Name Text Field.PNG", folderName);
        sikuliActions.click(MyocardialPerfusionScanReport + "Ok Button.PNG");
    }

    public boolean isNewFolderCreated(String folderName) {
        return sikuliActions.isElementVisible(MyocardialPerfusionScanReport + folderName + ".PNG");
    }

    public void clickOnTemplateActionButton(String buttonName) {
        sikuliActions.click(MyocardialPerfusionScanReport + buttonName + ".PNG");
    }

    public void selectFolder(String folderName) {
        sikuliActions.doubleClick(MyocardialPerfusionScanReport + folderName + ".PNG");
    }

    public boolean isPhraseSavedToFolder() {
        sikuliActions.waitForElementToExist(MyocardialPerfusionScanReport + "Save Template Dialog Box.PNG", 20);
        return sikuliActions.isElementVisible(MyocardialPerfusionScanReport + "Save Template Dialog Box.PNG");
    }

    public void deleteTemplate() {
        sikuliActions.rightClickExact(MyocardialPerfusionScanReport + "Automation Test.PNG");
        sikuliActions.waitForElementToExist(MyocardialPerfusionScanReport + "Delete.PNG", 20);
        sikuliActions.click(MyocardialPerfusionScanReport + "Delete.PNG");
        sikuliActions.waitForElementToExist(CommonElementDir + "Yes Button.PNG", 20);
        sikuliActions.click(CommonElementDir + "Yes Button.PNG");
    }

    public void deleteFolder() {
        sikuliActions.rightClickExact(MyocardialPerfusionScanReport + "Test folder.PNG");
        sikuliActions.waitForElementToExist(MyocardialPerfusionScanReport + "Delete.PNG", 20);
        sikuliActions.click(MyocardialPerfusionScanReport + "Delete.PNG");
        sikuliActions.waitForElementToExist(CommonElementDir + "Yes Button.PNG", 20);
        sikuliActions.click(CommonElementDir + "Yes Button.PNG");
    }

    public void selectNomenclature(String option) {
        String patternFile = USER_WORK_DIR + ReportScreens + "ReportScreen\\" + option + ".PNG";
        sikuliActions.click(patternFile);
        sikuliActions.click(CommonElementDir + "Ok Button.PNG");
    }

    private final HashMap<String, Integer> colorToClick = new HashMap<String, Integer>() {
        {
            put("Normal", 1);
            put("Hypokinesis", 2);
            put("Akinesis", 3);
        }
    };

    public void selectSegments(List<Map<String, String>> segmentsList) {
        maximizeReportEditorTab();
        for (Map<String, String> segmentList : segmentsList) {
            String segmentName = segmentList.get("Segment");
            String filePattern = null;
            switch (segmentName) {
                case "MALSegment":
                    filePattern = CommonElementDir + "MALSegment.PNG";
                    break;
                case "MISegment":
                    filePattern = CommonElementDir + "MISegment.PNG";
                    break;
                case "BISSegment":
                    filePattern = CommonElementDir + "BISSegment.PNG";
                    break;
            }
            for (int i = 0; i < colorToClick.get(segmentList.get("observation")); i++) {
                sikuliActions.moveToElement(filePattern);
                sikuliActions.click();
            }
        }
        restoreReportEditorTab();
    }

    public boolean areAllSegmentsSetToNormal() {
        robotActions.pressPageUpKey();
        return sikuliActions.isElementVisible(USER_WORK_DIR + ReportScreens + "ReportScreen\\" + "RedSegments.PNG");
    }

    public boolean areSegmentObservationsDisplayedOnReport() {
        sikuliActions.waitForElementInvisibility(
                USER_WORK_DIR + ReportScreens + "ReportScreen\\" + "normal segment observations.PNG", 5);
        return sikuliActions
                .isElementVisible(USER_WORK_DIR + ReportScreens + "ReportScreen\\" + "normal segment observations.PNG");
    }

    public boolean areSegmentObservationsCleared() {
        robotActions.pressPageUpKey();
        sikuliActions.waitForElementInvisibility(
                USER_WORK_DIR + ReportScreens + "ReportScreen\\" + "NoSegmentObservations.PNG", 10);
        return sikuliActions
                .isElementVisible(USER_WORK_DIR + ReportScreens + "ReportScreen\\" + "NoSegmentObservations.PNG");
    }

    public void selectRadioOption(Rectangle regionBounds, String radioOption) {
        sikuliActions.selectExactRadioFromRegion(
                USER_WORK_DIR + ReportScreens + "ReportScreen\\" + radioOption + ".PNG", regionBounds);
    }

    public void clickButton(String buttonName) {
        sikuliActions.click(USER_WORK_DIR + ReportScreens + "ReportScreen\\" + buttonName + ".PNG");

    }

    public boolean isAnatomyPrelimDiagramPresentInPreview() {
        sikuliActions.wait(1.0);
        return sikuliActions
                .isElementVisible(USER_WORK_DIR + ReportScreens + "ReportScreen\\Prelim Report Anatomy.PNG");
    }

    public void selectSegmentInTheReport(String screenName) {
        String patternFile = USER_WORK_DIR + ReportScreens + "ReportScreen\\" + screenName + ".PNG";
        sikuliActions.click(patternFile);
        sikuliActions.wait(1.0);
    }

    public void markLesionOnAorta() {
        sikuliActions.click(USER_WORK_DIR + ReportScreens + "ReportScreen\\Aorta.PNG");
        sikuliActions.click(USER_WORK_DIR + ReportScreens + "ReportScreen\\Aorta Vein.PNG");
        sikuliActions.click(USER_WORK_DIR + ReportScreens + "ReportScreen\\Aorta Vein2.PNG");
    }

    public boolean isMarkLesionVisibleInDynamicPreview() {
        sikuliActions.waitForElementToExist(
                USER_WORK_DIR + ReportScreens + "ReportScreen\\Prelim Report Anatomy with Marked Lesion.PNG", 10);
        return sikuliActions.isElementVisible(
                USER_WORK_DIR + ReportScreens + "ReportScreen\\Prelim Report Anatomy with Marked Lesion.PNG");
    }

    public boolean isAnatomyFinalDiagramPresentInPreview() {
        return sikuliActions
                .isExactElementVisible(USER_WORK_DIR + ReportScreens + "ReportScreen\\Final Report Anatomy.PNG");
    }

    public boolean isChangeVisibleInPreview() {
        sikuliActions.waitForElementToExist(
                USER_WORK_DIR + ReportScreens + "ReportScreen\\Final Report Anatomy with Marked Lesion.PNG", 10);
        return sikuliActions.isExactElementVisible(
                USER_WORK_DIR + ReportScreens + "ReportScreen\\Final Report Anatomy with Marked Lesion.PNG");
    }

    public boolean isSignedByFieldPresent(String labelName) {
        return sikuliActions.isElementVisible(CommonElementDir + labelName + ".PNG");
    }

    public void clickOnSubtab(String subTabName) {
        sikuliActions.click(CommonElementDir + subTabName + ".PNG");
    }

    public boolean isReportCorrected() {
        return sikuliActions.isElementVisible(CommonElementDir + "Corrected Report.PNG");
    }

    public boolean isOptionPresentOnReportEditor(String optionName) {
        sikuliActions.scrollToField(USER_WORK_DIR + ReportScreens + "ReportScreen\\" + optionName + ".PNG");
        return sikuliActions.isElementVisible(USER_WORK_DIR + ReportScreens + "ReportScreen\\" + optionName + ".PNG");
    }

    public void selectCheckboxList(List<String> checkBoxPatternFile, String regionPatternFile) {
        sikuliActions.scrollToField(USER_WORK_DIR + ReportScreens + "ReportScreen\\" + regionPatternFile + ".PNG");
        for (String checkBoxText : checkBoxPatternFile) {
            sikuliActions.selectCheckBox(USER_WORK_DIR + ReportScreens + "ReportScreen\\" + checkBoxText + ".PNG",
                    USER_WORK_DIR + ReportScreens + "ReportScreen\\" + regionPatternFile + ".PNG");
        }
    }

    public boolean isSpellingCheckerMarkDisplayed() {
        sikuliActions.wait(5.0);
        return sikuliActions.isExactElementVisible(CommonElementDir + "Incorrect spelling mark.PNG");
    }

    public void clearTextField() {
        sikuliActions.clearTextFromField();
        Common.sleep(3, TimeUnit.SECONDS);
    }

    public void rightClickOnIncorrectWord() {
        sikuliActions.moveToElement(CommonElementDir + "Incorrect spelling mark.PNG");
        sikuliActions.moveMouseByOffset(0, -5);
        sikuliActions.rightClick();
    }

    public boolean isSuggestionWindowDisplayed() {
        return sikuliActions.isElementVisible(CommonElementDir + "Spelling check suggestion.png");
    }

    public void selectCheckboxList(List<String> checkBoxPatternFile) {
        for (String checkBoxText : checkBoxPatternFile) {
            sikuliActions.selectCheckBox(USER_WORK_DIR + ReportScreens + "ReportScreen\\" + checkBoxText + ".PNG");
        }
    }

    public boolean isSignedByFieldSameInCorrectedReport(String labelName) {
        return sikuliActions.isElementVisible(USER_WORK_DIR + ReportScreens + "ReportScreen\\" + labelName + ".PNG");
    }

    public boolean isSubtabPresent(List<String> structureTabList, String subtabName) {
        boolean result = false;
        for (String structureTab : structureTabList) {
            sikuliActions.click(USER_WORK_DIR + ReportScreens + "ReportScreen\\" + structureTab + ".PNG");
            result = sikuliActions
                    .isElementVisible(USER_WORK_DIR + ReportScreens + "ReportScreen\\" + subtabName + ".PNG");
        }
        return result;
    }

    public void selectCorrectWord() {
        sikuliActions.click(CommonElementDir + "Correct word.PNG");
    }

    public boolean isPatientNameFormatCorrect() {
        sikuliActions.waitForElementToExist(USER_WORK_DIR + ReportScreens + "ReportScreen\\PatientNameFormat.PNG", 10);
        return sikuliActions
                .isExactElementVisible(USER_WORK_DIR + ReportScreens + "ReportScreen\\PatientNameFormat.PNG");
    }

    public boolean isBaselineTextDisplayed() {
        return winActions.isVisible(getByLocator(reportViewerPageOR.baselineLabel_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isExaminationDateDisplayed() {
        return sikuliActions.isElementVisible(CommonElementDir + "Examination date.PNG");
    }

    public String getPdfReportContents() {
        String pdfContent = pdfReader.getPDFData(USER_WORK_DIR + "\\Report.pdf");
        List<String> pdfContentLines = new ArrayList<>(Arrays.asList(pdfContent.split("\n")));
        List<String> headerLines = new ArrayList<>();
        for (String line : pdfContentLines) {
            if (line.contains("Patient name:") || line.contains("DOB:")) {
                headerLines.add(line);
            }
        }
        for (int index = 2; index < headerLines.size(); index++) {
            pdfContentLines.remove(headerLines.get(index));
        }
        pdfContent = String.join("\n", pdfContentLines);
        String truncatedPdfContent = pdfContent.replaceAll("\r\n", " ").replaceAll("\\s+", " ");
        truncatedPdfContent = resolveSpaceBetweenHyphens(truncatedPdfContent);
        truncatedPdfContent = truncatedPdfContent.replaceAll("%", "\\% ").replaceAll("\\s+", " ");
        return truncatedPdfContent;
    }

    public Map<String, String> getPdfReportContentsWithTableData(List<String> headers, String marker) {
        String pdfContent = pdfReader.getPDFDataWithLayout(USER_WORK_DIR);
        return getTableData(pdfContent, headers, marker);
    }

    public String getTextReportContents() {
        try {
            String textContent = new String(Files.readAllBytes(Paths.get(USER_WORK_DIR + "\\Report.txt")));
            String truncatedTextContent = textContent.replaceAll("\r\n", " ").replaceAll("\\s+", " ").replaceAll("â€™",
                    "’");
            truncatedTextContent = resolveSpaceBetweenHyphens(truncatedTextContent);
            Log.printInfo("Text report contents");
            Log.printInfo("-----------------------------------------");
            Log.printInfo(truncatedTextContent);
            return truncatedTextContent;
        } catch (IOException e) {
            return null;
        }
    }

    public Map<String, String> getTextReportContentsWithTableData(List<String> headers) {
        List<String> textContent;
        Map<String, String> tableData = new HashMap<>();
        try {
            textContent = Files.readAllLines(Paths.get(USER_WORK_DIR + "\\Report.txt"));
            for (String line : textContent) {
                line = line.trim();
                if (headers.stream().anyMatch(line::endsWith)) {
                    String[] observationKeyValue = line.split(":");
                    tableData.put(observationKeyValue[0].trim(), observationKeyValue[1].trim());
                }
            }
            return tableData;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isReportEntryPresent(String reportName, String sectionName, String tabName,
                                        Map<String, String> reportEntries, String reportContent) {
        boolean containsKey = true;
        boolean containsValue = true;
        ReportEditorHelper reportEditorHelper = new ReportEditorHelper();
        Map<String, String> reportPhraseMap = new LinkedHashMap<>();
        try {
            reportPhraseMap = reportEditorHelper.getReportPhraseMappings(reportName, sectionName, tabName);
        } catch (NullPointerException e) {
            Log.printInfo("No mappings for phrases found");
        }
        for (Map.Entry<String, String> entry : reportEntries.entrySet()) {
            if (entry.getValue().equalsIgnoreCase("Ignore")) {
                continue;
            }
            String fieldName = entry.getKey();
            String key = reportPhraseMap != null
                    ? reportPhraseMap.get(entry.getKey()) == null ? entry.getKey() : reportPhraseMap.get(entry.getKey())
                    : entry.getKey();
            String value = reportPhraseMap != null ? reportPhraseMap.get(entry.getValue()) == null ? entry.getValue()
                    : reportPhraseMap.get(entry.getValue()) : entry.getValue();
            containsKey = containsKey && reportContent.contains(key);
            if ((value != null) && !value.equalsIgnoreCase("Select"))
                containsValue = containsValue && reportContent.contains(value);
            if (!reportContent.contains(key) & entry.getValue().equalsIgnoreCase("Select")) {
                Log.logScenarioInfo(String.format("Field name: '%s' is not present.", key));
            }
            if (!reportContent.contains(key) & !entry.getValue().equalsIgnoreCase("Select")) {
                Log.logScenarioInfo(String.format("Phrase '%s' for '%s' is not present.", key, entry.getKey()));
            }
            if (key.isEmpty() && (value != null) && !value.equalsIgnoreCase("Select")
                    && !reportContent.contains(value)) {
                Log.logScenarioInfo(String.format("Phrase: '%s' for '%s' is not present.", value, fieldName));
            }
            if (!key.isEmpty() && (value != null) && !value.equalsIgnoreCase("Select")
                    && !reportContent.contains(value)) {
                String missingValueForKey = reportContent.contains(key) ? key : entry.getKey();
                Log.logScenarioInfo("Value(s): " + value.replaceAll("%", "%%") + " for " + missingValueForKey + " field is not present.");
            }
        }
        if (containsKey && containsValue) {
            Log.logScenarioInfo("Report details verified successfully");
        }
        return containsKey && containsValue;
    }

    public boolean isImagePresentOnReport(BufferedImage bufferedImage) {
        return sikuliActions.isElementVisible(bufferedImage, 0.01f);
    }

    public void acceptReportValidationError() {
        WebElement Dialog = winActions.findWindowElementBy(
                getByLocator(reportViewerPageOR.reportValidationErrorDialogBox_Xpath, LocatorIdentifier.Xpath));
        winActions.click(Dialog,
                getByLocator(reportViewerPageOR.reportValidationErrorOkButton_Name, LocatorIdentifier.Name));
        closeReportViewer();
        if (winActions.isVisible(getByLocator(reportViewerPageOR.saveReportDialog_Xpath, LocatorIdentifier.Xpath))) {
            saveReport();
        }

    }

    public boolean isPreliminaryReportInfoDisplayedInFinalReport() {
        return sikuliActions
                .isElementVisible(USER_WORK_DIR + ReportScreens + "ReportScreen\\LastSignedPreliminaryReportInfo.PNG");
    }

    public void clickOnPrintButton() {
        sikuliActions.click(CommonElementDir + "Printer Button.png");
    }

    public void openReferringPhysicianAddressBook() {
        sikuliActions.click(USER_WORK_DIR + ReportScreens + "ReportScreen\\Address Book Icon.PNG");
    }

    public boolean isReferredPhysicianDisplayedUnderAddressBook() {
        return sikuliActions
                .isElementVisible(USER_WORK_DIR + ReportScreens + "ReportScreen\\Referring Physician Name.PNG");
    }

    public void clickOnCancelButton() {
        sikuliActions.click(CommonElementDir + "Cancel Button.PNG");
    }

    public boolean isHorizontalScrollbarIsDisplayed() {
        return sikuliActions.isElementVisible(CommonElementDir + "Horizontal scroll bar.PNG");
    }

    public void doubleClickOnDynamicPreview() {
        winActions.doubleClick(getByLocator(reportViewerPageOR.reportDynamicPreview_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isLabelPresentOnReportEditor(String reportName, String labelName, String fieldType) {
        return sikuliActions.isElementVisible(
                USER_WORK_DIR + ReportScreens + reportName + "\\" + labelName + "_" + fieldType.toUpperCase() + ".PNG");
    }

    public boolean isLocationOfDiastolicPressureCorrectOnReportEditor() {
        WebElement reportEditorWindow = winActions
                .findWindowElementBy(getByLocator(reportViewerPageOR.reportEditorTab_Xpath, LocatorIdentifier.Xpath));
        return sikuliActions.isElementVisible(winActions.getElementBounds(reportEditorWindow),
                CommonElementDir + "InterchangedBPLabel.PNG");
    }

    public boolean isAssignedRefPhysicianPresentOnReportEditor() {
        WebElement reportEditorWindow = winActions
                .findWindowElementBy(getByLocator(reportViewerPageOR.reportEditorTab_Xpath, LocatorIdentifier.Xpath));
        return sikuliActions.isElementVisible(winActions.getElementBounds(reportEditorWindow),
                CommonElementDir + "ReferredByReportEditor.PNG");
    }

    public void maximizeReportEditorTab() {
        WebElement reportEditorTab = winActions.findWindowElementBy(reportViewerWindow,
                getByLocator(reportViewerPageOR.reportEditorTab_Xpath, LocatorIdentifier.Xpath));
        if (winActions.isVisible(reportEditorTab,
                getByLocator(reportViewerPageOR.reportEditorMaximizeButton_Xpath, LocatorIdentifier.Xpath))) {
            winActions.click(reportEditorTab,
                    getByLocator(reportViewerPageOR.reportEditorMaximizeButton_Xpath, LocatorIdentifier.Xpath));
        }
    }

    public void logReportEntryScreenshot() {
        robotActions.pressPageUpKey();
        Rectangle elementBounds = winActions.getElementBounds(reportViewerWindow,
                getByLocator(reportViewerPageOR.reportEditorTab_Xpath, LocatorIdentifier.Xpath));
        byte[] screenshot = sikuliActions.getElementScreenshot(elementBounds);
        Log.logScreenshot(screenshot, "Report entries");
        BufferedImage screenImage = sikuliActions.getImageWithinBounds(elementBounds);
        robotActions.pressPageDownKey();
        Common.sleep(2, TimeUnit.SECONDS);
        while (!sikuliActions.isElementVisible(screenImage, 0.5f)) {
            screenImage = sikuliActions.getImageWithinBounds(elementBounds);
            Log.logScreenshot(sikuliActions.getElementScreenshot(elementBounds), "Report entries");
            robotActions.pressPageDownKey();
        }
    }

    public boolean isLabelPresentInImpressionsTab() {
        return sikuliActions
                .isExactElementVisible(USER_WORK_DIR + ReportScreens + "ReportScreen\\Right Clicked Option.PNG");
    }

    public boolean isMeasurementAutoChecked(String autoCheckedMeasurement) {
        return sikuliActions.isExactElementVisible(USER_WORK_DIR + ReportScreens + "PediatricEchoPreliminaryReport\\"
                + autoCheckedMeasurement + "_autochecked.PNG");
    }

    public void selectCodesFromIndicationsHistory(List<String> codeName) {
        for (String codeNameList : codeName) {
            sikuliActions.scrollToExactField(USER_WORK_DIR + ReportScreens + "ReportScreen\\" + codeNameList + ".PNG",
                    10);
            sikuliActions.doubleClick(USER_WORK_DIR + ReportScreens + "ReportScreen\\" + codeNameList + ".PNG");
        }
    }

    public boolean isIndicationHistoryCodePresentInDynamicPreview() {
        sikuliActions.waitForElementToExist(
                USER_WORK_DIR + ReportScreens + "ReportScreen\\Codes On Dynamic Preview.PNG", 10);
        return sikuliActions.isSimilarElementVisible(
                USER_WORK_DIR + ReportScreens + "ReportScreen\\Codes On Dynamic Preview.PNG", 0.5f);
    }

    public Rectangle getRegionBounds(String regionPatternFile) {
        return sikuliActions.getExactRegionBounds(
                USER_WORK_DIR + ReportScreens + "ReportScreen\\" + regionPatternFile + "_region.PNG");
    }

    public boolean isMeasurementValuePopulatedOnPreview(String screenName) {
        String patternFile = USER_WORK_DIR + ReportScreens + "ReportScreen\\" + screenName + ".PNG";
        sikuliActions.waitForElementToExist(patternFile, 5);
        return sikuliActions.isElementVisible(patternFile);
    }

    public void waitForReportEditorToLoad() {
        sikuliActions.waitForElementToExist(CommonElementDir + "LoadingIndicator.PNG", 10);
        sikuliActions.waitForElementInvisibility(CommonElementDir + "LoadingIndicator.PNG", 10);
    }

    public void clickOnBullsEye(String reportName, String measurementLabel) {
        sikuliActions.hoverExact(Common.getReportLocation(reportName) + measurementLabel + "_TEXT.PNG");
        sikuliActions.click(CommonElementDir + "Bullseye.PNG");
    }

    public boolean isLabelAutoChecked(String reportName, String autoCheckedMeasurementLabel) {
        return sikuliActions.isExactElementVisible(
                Common.getReportLocation(reportName) + autoCheckedMeasurementLabel + "_autochecked.PNG");
    }

    public boolean isLabelNameAndValuePresentUnderFindingsSection(String measurementType) {
        sikuliActions.waitForElementToExist(
                USER_WORK_DIR + ReportScreens + "EchoPreliminaryReport\\" + measurementType + ".PNG", 10);
        return sikuliActions.isExactElementVisible(
                USER_WORK_DIR + ReportScreens + "EchoPreliminaryReport\\" + measurementType + ".PNG");
    }

    public boolean isMeasurementsTabYellow() {
        return sikuliActions.isExactElementVisible(CommonElementDir + "Yellow Measurements Section.PNG");
    }

    public boolean isLabelNameAutoPopulatedUnderMeasurementTab(String autoCheckedLabelName) {
        return sikuliActions.isExactElementVisible(
                USER_WORK_DIR + ReportScreens + "EchoPreliminaryReport\\" + autoCheckedLabelName + "_autochecked.PNG");
    }

    public String getServerPdfReportContents(String pdfFile) {
        String pdfContent = pdfReader.getPDFData(pdfFile);
        return pdfContent.replaceAll("\r\n", " ");
    }

    public void restoreReportEditorTab() {
        WebElement reportEditorTab = winActions.findWindowElementBy(reportViewerWindow,
                getByLocator(reportViewerPageOR.reportEditorTab_Xpath, LocatorIdentifier.Xpath));
        if (winActions.isVisible(reportEditorTab,
                getByLocator(reportViewerPageOR.reportEditorRestoreButton_Xpath, LocatorIdentifier.Xpath))) {
            winActions.click(reportEditorTab,
                    getByLocator(reportViewerPageOR.reportEditorRestoreButton_Xpath, LocatorIdentifier.Xpath));
        }
    }

    private Map<String, String> getTableData(String pdfContent, List<String> headers, String marker) {
        List<String> pdfContentLines = Arrays.asList(pdfContent.split("\n"));
        Map<String, Range<Integer>> markerValue = new LinkedHashMap<>();

        List<String> tableLines = pdfContentLines.stream().filter(line -> headers.stream().allMatch(
                header -> line.contains(header) || line.contains(marker) || line.contains(marker.trim() + "\r")))
                .collect(Collectors.toList());
        tableLines.set(0, resolveSpaceBetweenWords(tableLines.get(0)));
        int tableSeparator = tableLines.get(0).indexOf(headers.get(headers.size() - 1));
        List<String> singleTableLines = new ArrayList<>();
        for (String line : tableLines) {
            line = line.substring(8);
            if (line.length() < tableSeparator) {
                singleTableLines.add(line);
            } else {
                singleTableLines.add(line.substring(0, tableSeparator));
                singleTableLines.add(line.substring(tableSeparator));
            }
        }
        String headerRow = singleTableLines.get(0);
        for (String header : headers) {
            markerValue.put(header,
                    Range.between(headerRow.indexOf(header), headerRow.indexOf(header) + header.length()));
        }

        Map<String, String> tableValues = new LinkedHashMap<>();
        for (String tableLine : singleTableLines) {
            if (!tableLine.contains(" " + marker.trim())) {
                continue;
            }
            int valuePosition = tableLine.indexOf(" " + marker.trim());
            for (Map.Entry<String, Range<Integer>> entry : markerValue.entrySet()) {
                if (entry.getValue().contains(valuePosition)) {
                    String value = tableLine.trim().split(marker.trim())[0].trim();
                    value = value.replaceAll("-", " - ");
                    value = value.replaceAll(" +", " ");
                    char[] chars = value.toCharArray();
                    for (int i = 0; i < chars.length - 2; i++) {
                        if (Character.isLowerCase(chars[i]) && Character.isUpperCase(chars[i + 1])) {
                            value = value.substring(0, i + 1) + " " + value.substring(i + 1);
                        }
                    }
                    tableValues.put(value, entry.getKey());
                    break;
                }
            }
        }
        Map<String, String> resolvedTableValues = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : tableValues.entrySet()) {
            resolvedTableValues.put(resolveSpaceBetweenNumberAndWords(entry.getKey()), entry.getValue());
        }
        return resolvedTableValues;
    }

    public String getPdfObservationTableData(String sectionHeader) {
        int tableStartIndex = 0;
        String pdfContent = pdfReader.getPDFDataWithLayout(USER_WORK_DIR + "//Reports//Report.pdf");
        Log.printInfo("Pdf Contents");
        Log.printInfo(pdfContent);
        List<String> pdfContentLines = Arrays.asList(pdfContent.split("\n"));
        for (int startIndex = 0; startIndex < pdfContentLines.size() - 1; startIndex++) {
            if (pdfContentLines.get(startIndex).trim().replaceAll(" +", " ").contains(sectionHeader)) {
                tableStartIndex = startIndex;
                break;
            }
        }
        List<String> tableLines = pdfContentLines.subList(tableStartIndex + 1, pdfContentLines.size() - 1);
        Log.printInfo("Table Lines");
        tableLines.forEach(Log::printInfo);
        List<String> splitTableLines = new ArrayList<>();
        for (String tableLine : tableLines) {
            tableLine = tableLine.substring(7);
            if (tableLine.length() <= 48) {
                splitTableLines.add(tableLine);
                splitTableLines.add(" ");
                splitTableLines.add(" ");
            } else if (tableLine.length() <= 95) {
                splitTableLines.add(tableLine.substring(0, 48));
                splitTableLines.add(tableLine.substring(48));
                splitTableLines.add(" ");
            } else {
                splitTableLines.add(tableLine.substring(0, 48));
                splitTableLines.add(tableLine.substring(48, 95));
                splitTableLines.add(tableLine.substring(95));
            }
        }
        Log.printInfo("Split table Lines");
        splitTableLines.forEach(Log::printInfo);
        List<String> arrangedTableLines = new ArrayList<>();
        try {
            for (int index = 0; index < splitTableLines.size() - 1; index += 3) {
                arrangedTableLines.add(splitTableLines.get(index));
            }
            for (int index = 1; index < splitTableLines.size() - 1; index += 3) {
                arrangedTableLines.add(splitTableLines.get(index));
            }
            for (int index = 2; index < splitTableLines.size() - 1; index += 3) {
                arrangedTableLines.add(splitTableLines.get(index));
            }
        } catch (Exception ignored) {

        }
        Log.printInfo("Arranged table lines");
        arrangedTableLines.forEach(Log::printInfo);
        StringBuilder tableString = new StringBuilder();
        for (String tableLine : arrangedTableLines) {
            Log.printInfo("Table line before resolve:");
            Log.printInfo(tableLine);
            tableLine = tableLine.replaceAll("\r", " ");
            // tableLine = tableLine.trim().replaceAll(" +", " ");
            tableLine = resolveSpaceBetweenHyphens(tableLine);
            tableLine = resolveSpaceBetweenCommaSeparatedValues(tableLine);
            tableLine = resolveSpaceBetweenWords(tableLine);
            if (!tableLine.contains(":")) {
                tableLine = " " + tableLine + " ";
            }
            tableString.append(tableLine);
            Log.printInfo("Table line after resolve:");
            Log.printInfo(tableLine);
        }
        Log.printInfo("Final content before resolve");
        Log.printInfo(tableString.toString());
        String contents = tableString.toString().replaceAll(" +", " ");
        contents = resolveSpaceBetweenHyphens(contents);
        Log.printInfo(contents);
        return contents;
    }

    private String resolveSpaceBetweenHyphens(String inputString) {
        Pattern pattern = Pattern.compile("[a-z0-9]- ");
        Matcher matcher = pattern.matcher(inputString);
        int pattern1Count = 0;
        List<String> matcherGroup = new ArrayList<>();
        while (matcher.find()) {
            matcherGroup.add(matcher.group());
            pattern1Count++;
        }
        Pattern pattern2 = Pattern.compile(" -[a-z]");
        Matcher matcher2 = pattern2.matcher(inputString);
        int pattern3Count = 0;
        List<String> matcher2Group = new ArrayList<>();
        while (matcher2.find()) {
            matcher2Group.add(matcher2.group());
            pattern3Count++;
        }
        Pattern pattern3 = Pattern.compile(" -[A-Z]");
        Matcher matcher3 = pattern3.matcher(inputString);
        int pattern4Count = 0;
        List<String> matcher3Group = new ArrayList<>();
        while (matcher3.find()) {
            matcher3Group.add(matcher3.group());
            pattern4Count++;
        }
        Pattern pattern1 = Pattern.compile("[a-z]-[A-Z]");
        Matcher matcher1 = pattern1.matcher(inputString);
        int pattern2Count = 0;
        while (matcher1.find()) {
            pattern2Count++;
        }
        for (int groupIndex = 0; groupIndex < pattern1Count; groupIndex++) {
            if (groupIndex < pattern2Count) {
                continue;
            }
            inputString = inputString.replaceFirst("[a-z0-9]- ", matcherGroup.get(groupIndex).replaceAll(" ", "").trim());
        }
        for (int groupIndex = 0; groupIndex < pattern3Count; groupIndex++) {
            if (groupIndex < pattern2Count) {
                continue;
            }
            inputString = inputString.replaceFirst(" -[a-z]", matcher2Group.get(groupIndex).replaceAll(" ", "").trim());
        }
        for (int groupIndex = 0; groupIndex < pattern4Count; groupIndex++) {
            if (groupIndex < pattern2Count) {
                continue;
            }
            inputString = inputString.replaceFirst(" -[A-Z]", matcher3Group.get(groupIndex).trim().replaceAll("", " "));
        }
        return inputString;
    }

    private String resolveSpaceBetweenWords(String inputString) {
        Pattern pattern = Pattern.compile("[a-z]{2}[A-Z]");
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.find()) {
            StringBuilder stringBuilder = new StringBuilder(matcher.group());
            inputString = inputString.replaceAll("[a-z]{2}[A-Z]", stringBuilder.insert(2, " ").toString().trim());
        }
        inputString = inputString.replaceAll("ofthe", "of the");
        inputString = inputString.replaceAll("fibroidsseen", "fibroids seen");
        return inputString;
    }

    private String resolveSpaceBetweenNumberAndWords(String inputString) {
        Pattern pattern = Pattern.compile("[0-9][A-Z][a-z]{2}");
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.find()) {
            StringBuilder stringBuilder = new StringBuilder(matcher.group());
            inputString = inputString.replaceAll("[0-9][A-Z][a-z]{2}", stringBuilder.insert(1, " ").toString().trim());
        }
        inputString = inputString.replaceAll("ofthe", "of the");
        inputString = inputString.replaceAll("fibroidsseen", "fibroids seen");
        return inputString;
    }

    private String resolveSpaceBetweenCommaSeparatedValues(String inputString) {
        Pattern pattern = Pattern.compile(",[a-zA-Z]");
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.find()) {
            inputString = inputString.replaceAll(",[a-zA-Z]", matcher.group().replaceAll("", " ").trim());
        }
        return inputString;
    }

    public void hoverOnMeasurement(String measurementPatternFile) {
        sikuliActions.hover(CommonElementDir + measurementPatternFile + " measurement.PNG");
    }

    public void selectBullseye() {
        sikuliActions.waitForElementToExist(CommonElementDir + "Bullseye.PNG", 5);
        sikuliActions.click(CommonElementDir + "Bullseye.PNG");
    }

    public String getBullseyeMeasurement() {
        return sikuliActions.selectAndGetValue();
    }

    public boolean isExtraZeroPresentInHeightFieldInReport() {
        return sikuliActions.isExactElementVisible(
                USER_WORK_DIR + ReportScreens + "TEEPreliminaryReport\\Height Value(in report).PNG");
    }

    public boolean isExtraZeroPresentInHeightFieldInPreview() {
        return sikuliActions
                .isElementVisible(USER_WORK_DIR + ReportScreens + "TEEPreliminaryReport\\Height Value(In Preview).PNG");
    }

    public boolean isHeightValuePopulatedOnPreview(String heightValue) {
        String patternFile = USER_WORK_DIR + ReportScreens + "TEEPreliminaryReport\\" + heightValue + ".PNG";
        sikuliActions.waitForElementToExist(patternFile, 5);
        return sikuliActions.isElementVisible(patternFile);
    }

    public boolean isBSACalculatedValueReflectedInReport() {
        String patternFile = USER_WORK_DIR + ReportScreens + "TEEPreliminaryReport\\BSA Value(In report).PNG";
        sikuliActions.waitForElementToExist(patternFile, 5);
        return sikuliActions.isElementVisible(patternFile);
    }

    public boolean isUnitAndValueOfWeightAndBSASameInFinalReport() {
        String patternFile = USER_WORK_DIR + ReportScreens + "TEEPreliminaryReport\\Weight&BSA Value.PNG";
        return sikuliActions.isElementVisible(patternFile);
    }

    public boolean isExtraZeroPresentInHeightFieldInPreview(String heightValue) {
        Pattern pattern = Pattern.compile("[0-9].[0-9]");
        Matcher matcher = pattern.matcher(heightValue);
        String patternFile = matcher.find()
                ? USER_WORK_DIR + ReportScreens + "TEEPreliminaryReport\\Height Value(In point).PNG"
                : USER_WORK_DIR + ReportScreens + "TEEPreliminaryReport\\Height Value(In three digits).PNG";
        return sikuliActions.isElementVisible(patternFile);
    }

    public void pressKeyButton(String keyButton) {
        switch (keyButton) {
            case "Backspace":
                robotActions.backspace();
                break;
            case "Enter":
                robotActions.enter();
                break;
            case "Escape":
                robotActions.escape();
                break;
        }
    }

    public boolean isElementDeletedWithoutSelecting(String viewMarkedElements) {
        String patternFile = null;
        switch (viewMarkedElements) {
            case "Report":
                patternFile = USER_WORK_DIR + ReportScreens + "ReportScreen\\Marked Elements(Report).PNG";
                break;
            case "Dynamic preview":
                patternFile = USER_WORK_DIR + ReportScreens + "ReportScreen\\Prelim Report Anatomy with Marked Lesion.PNG";
                break;
        }
        return sikuliActions.isExactElementVisible(patternFile);
    }

    public void selectMarkedElement(String element) {
        switch (element.toUpperCase()) {
            case "LESION":
                sikuliActions.click(USER_WORK_DIR + ReportScreens + "ReportScreen\\Marked Lesion.PNG");
                break;
            case "ANNOTATED TEXT(DEFAULT SIZE)":
                sikuliActions.click(USER_WORK_DIR + ReportScreens + "ReportScreen\\Annotated Image(Default Size in Report).PNG");
                break;
            case "ANNOTATED TEXT(ITALICS)":
                sikuliActions.click(USER_WORK_DIR + ReportScreens + "ReportScreen\\Annotated Text Test1(Italics).PNG");
                break;
            case "ANNOTATED TEXT(BOLD AND UNDERLINE)":
                sikuliActions.click(USER_WORK_DIR + ReportScreens + "ReportScreen\\Annotated Text Test(Bold and underline).PNG");
                break;
        }
    }

    public boolean isMarkedElementHighlightedInRed() {
        return sikuliActions
                .isExactElementVisible(USER_WORK_DIR + ReportScreens + "ReportScreen\\Marked Element(Selected).PNG");
    }

    public boolean isSelectedElementDeleted(String viewMarkedElements) {
        String patternFile = null;
        switch (viewMarkedElements) {
            case "Report":
                patternFile = USER_WORK_DIR + ReportScreens + "ReportScreen\\Element Deleted(Report).PNG";
                break;
            case "Dynamic preview":
                patternFile = USER_WORK_DIR + ReportScreens + "ReportScreen\\Element Deleted(Preview).PNG";
                break;
        }
        return sikuliActions.isExactElementVisible(patternFile);
    }

    public void clickOkButtonForClearAllConfirmation() {
        sikuliActions.click(USER_WORK_DIR + ReportScreens + "ReportScreen\\OK.PNG");
    }

    public boolean isAllElementsDeleted(String viewElements) {
        String patternFile = null;
        switch (viewElements) {
            case "Report":
                patternFile = USER_WORK_DIR + ReportScreens + "ReportScreen\\Anatomy Diagram(Report).PNG";
                break;
            case "Dynamic preview":
                patternFile = USER_WORK_DIR + ReportScreens + "ReportScreen\\Prelim Report Anatomy.PNG";
                break;
        }
        return sikuliActions.isExactElementVisible(patternFile);
    }

    public void annotateOnAnatomyDiagram(String value) {
        sikuliActions.click(USER_WORK_DIR + ReportScreens + "ReportScreen\\Annotation Region.PNG");
        sikuliActions.typeTextInExactField(USER_WORK_DIR + ReportScreens + "ReportScreen\\Text Area.PNG", value);
        robotActions.enter();
    }

    public void markElementInReport(String element) {
        switch (element) {
            case "stent":
                sikuliActions.click(USER_WORK_DIR + ReportScreens + "ReportScreen\\Stent Marking Region.PNG");
                break;
            case "aneurysm":
                sikuliActions.click(USER_WORK_DIR + ReportScreens + "ReportScreen\\Aneurysm Marking Region.PNG");
                sikuliActions.dragMouseByOffset(80, 0);
                break;
            case "bypass":
                sikuliActions.click(USER_WORK_DIR + ReportScreens + "ReportScreen\\Bypass Marking Point 1.PNG");
                sikuliActions.click(USER_WORK_DIR + ReportScreens + "ReportScreen\\Bypass Marking Point 2.PNG");
                sikuliActions.dragMouseByOffset(100, 0);
                break;
        }
    }

    public boolean areAllElementsMarkedInDynamicPreview() {
        sikuliActions.waitForExactElementToExist(
                USER_WORK_DIR + ReportScreens + "ReportScreen\\Dynamic Preview with all elements marked.PNG", 5);
        return sikuliActions.isExactElementVisible(
                USER_WORK_DIR + ReportScreens + "ReportScreen\\Dynamic Preview with all elements marked.PNG");
    }

    public void dragSelectedElement(String targetLocation) {
        switch (targetLocation) {
            case "Within":
                sikuliActions.dragElementToTargetedLocation(
                        USER_WORK_DIR + ReportScreens + "ReportScreen\\Marked Element(Selected).PNG",
                        USER_WORK_DIR + ReportScreens + "ReportScreen\\Target location to drag element.PNG");
                break;
            case "Outside":
                sikuliActions.dragMouseByOffset(55, 0);
                break;
        }
    }

    public boolean isElementDraggedWithinTheImage() {
        sikuliActions.waitForExactElementToExist(USER_WORK_DIR + ReportScreens + "ReportScreen\\Element dragged.PNG",
                5);
        return sikuliActions.isExactElementVisible(USER_WORK_DIR + ReportScreens + "ReportScreen\\Element dragged.PNG");
    }

    public boolean isElementDraggedOutsideImage() {
        sikuliActions.waitForExactElementToExist(USER_WORK_DIR + ReportScreens + "ReportScreen\\Element dragged.PNG",
                5);
        return sikuliActions
                .isExactElementVisible(USER_WORK_DIR + ReportScreens + "ReportScreen\\Element Not Dragged.PNG");
    }

    public void clickSaveReport() {
        winActions.click(getByLocator(reportViewerPageOR.saveReport_Name, LocatorIdentifier.Name));
    }

    public void waitForReportToBeSaved() {
        try {
            winActions.waitForWindowElementToBeVisible(
                    getByLocator(reportViewerPageOR.savingReportProgressWindow_Xpath, LocatorIdentifier.Xpath), 10);
            winActions.waitForWindowElementToBeInvisible(
                    getByLocator(reportViewerPageOR.savingReportProgressWindow_Xpath, LocatorIdentifier.Xpath), 10);
        } catch (Exception e) {
            Log.printInfo("Saving progress bar not displayed");
        }
    }

    public boolean isRankCalculated(String rankValue) {
        winActions.doubleClick(getByLocator(reportViewerPageOR.reportDynamicPreview_Xpath, LocatorIdentifier.Xpath));
        sikuliActions.click(CommonElementDir + "FitToWidthIcon.PNG");
        sikuliActions.waitForElementToExist(
                USER_WORK_DIR + ReportScreens + "FetalTTEReport(Standard)\\" + rankValue + " Rank.PNG", 10);
        return sikuliActions.isElementVisible(
                USER_WORK_DIR + ReportScreens + "FetalTTEReport(Standard)\\" + rankValue + " Rank.PNG");
    }

    public boolean isFieldBorderedInRed() {
        return sikuliActions.isElementVisible(
                USER_WORK_DIR + ReportScreens + "FetalTTEReport(Standard)\\BPD & HC(Red Bordered).PNG");
    }

    public void setNumberOfFetus(int number) {
        for (int count = 0; count < number; count++) {
            sikuliActions.click(CommonElementDir + "Fetus Increase Button.PNG");
            Common.sleep(5, TimeUnit.SECONDS);
        }
    }

    public boolean isBPDPercentileCalculatingMethodChanged() {
        return sikuliActions.isElementVisible(
                USER_WORK_DIR + ReportScreens + "ReportScreen\\BPD Calculating Method(Papageorghiou).PNG");
    }

    public boolean isOtherFieldsCalculatingMethodChanged() {
        return sikuliActions.isElementVisible(
                USER_WORK_DIR + ReportScreens + "ReportScreen\\Fields with Hadlock Calculating Method.PNG");
    }

    public void selectReportForComparison(String reportName, String date, String time) {
        sikuliActions.click(CommonElementDir + reportName + " " + date + " " + time + ".PNG");
        robotActions.openContextMenu();
        winActions.waitForWindowElementToBeVisible(getByLocator(reportViewerPageOR.setComparisonBaselineContextMenuItem_Name, LocatorIdentifier.Name), 10);
        winActions.click(
                getByLocator(reportViewerPageOR.setComparisonBaselineContextMenuItem_Name, LocatorIdentifier.Name));
    }

    public boolean isSubtabsHaveAsterisk(String subtabName) {
        return sikuliActions.isElementVisible(CommonElementDir + subtabName + " subtabs asterisk.PNG");
    }

    public boolean isFieldHighlightedWithRectangle(String fieldName) {
        return sikuliActions.isElementVisible(CommonElementDir + fieldName + " rectangle.PNG");
    }

    public boolean isTextPresentInFreeText(String text) {
        return sikuliActions.isElementVisible(CommonElementDir + text + ".PNG");
    }

    public boolean verifyProcedureObservationsOnReportPreview(List<Map<String, String>> procedureDetails) {
        boolean result = false;
        WebElement reportDynamicPreviewWindow = winActions.findWindowElementBy(
                getByLocator(reportViewerPageOR.reportDynamicPreviewWindow_Name, LocatorIdentifier.Name));
        for (Map<String, String> procedureDetail : procedureDetails) {
            sikuliActions.click(CommonElementDir + procedureDetail.get("procedures") + " procedure radio.PNG");
            sikuliActions.waitForElementToExist(CommonElementDir + procedureDetail.get("observations") + "_observation.PNG", 10);
            result = sikuliActions.isElementVisible(winActions.getElementBounds(reportDynamicPreviewWindow),
                    CommonElementDir + procedureDetail.get("observations") + "_observation.PNG");
        }
        return result;
    }

    public boolean isIconPresentOnMeasurementLabel(String measurementLabel) {
        return sikuliActions.isElementVisible(
                USER_WORK_DIR + ReportScreens + "EchoPreliminaryReport\\" + measurementLabel + "_TEXT.PNG");
    }

    public void clickOnMeanCalculationIcon(String measurementLabel) {
        sikuliActions
                .hoverExact(USER_WORK_DIR + ReportScreens + "EchoPreliminaryReport\\" + measurementLabel + "_TEXT.PNG");
        sikuliActions.click(CommonElementDir + "Mean Calculation Icon.PNG");
    }

    public boolean isMeanCalculationDialogVisible() {
        return winActions
                .isVisible(getByLocator(reportViewerPageOR.meanCalculationDialog_Xpath, LocatorIdentifier.Xpath), 3);
    }

    public int getIndexOfSelectedRowHavingMachineIcon() {
        WebElement dataGrid = winActions.findWindowElementBy(
                getByLocator(reportViewerPageOR.meanCalculationDataGrid_Xpath, LocatorIdentifier.Xpath));
        List<WebElement> dataItems = dataGrid
                .findElements(getByLocator(reportViewerPageOR.meanCalculationDataItem_Xpath, LocatorIdentifier.Xpath));
        for (int index = 0; index < dataItems.size(); index++) {
            if (sikuliActions.isElementVisible(winActions.getElementBounds(dataItems.get(index)),
                    CommonElementDir + "Checked Checkbox With Machine Icon.PNG")) {
                return index;
            }
        }
        return -1;
    }

    public void selectUnselectCheckboxValueItem(int rowIndex) {
        WebElement dataGrid = winActions.findWindowElementBy(
                getByLocator(reportViewerPageOR.meanCalculationDataGrid_Xpath, LocatorIdentifier.Xpath));
        List<WebElement> dataItems = dataGrid
                .findElements(getByLocator(reportViewerPageOR.meanCalculationDataItem_Xpath, LocatorIdentifier.Xpath));
        winActions.click(dataItems.get(rowIndex));
        robotActions.pressSpaceBarKey();

    }

    public int getIndexOfVidistarIconRow() {
        WebElement dataGrid = winActions.findWindowElementBy(
                getByLocator(reportViewerPageOR.meanCalculationDataGrid_Xpath, LocatorIdentifier.Xpath));
        List<WebElement> dataItems = dataGrid
                .findElements(getByLocator(reportViewerPageOR.meanCalculationDataItem_Xpath, LocatorIdentifier.Xpath));
        for (int index = 0; index < dataItems.size(); index++) {
            if (sikuliActions.isExactElementVisible(winActions.getElementBounds(dataItems.get(index)),
                    CommonElementDir + "Unchecked Checkbox With Vidistar Icon.PNG")) {
                return index;
            }
        }
        return -1;
    }

    public int getIndexOfSelectedRowHavingVidistarIcon() {
        WebElement dataGrid = winActions.findWindowElementBy(
                getByLocator(reportViewerPageOR.meanCalculationDataGrid_Xpath, LocatorIdentifier.Xpath));
        List<WebElement> dataItems = dataGrid
                .findElements(getByLocator(reportViewerPageOR.meanCalculationDataItem_Xpath, LocatorIdentifier.Xpath));
        for (int index = 0; index < dataItems.size(); index++) {
            if (sikuliActions.isElementVisible(winActions.getElementBounds(dataItems.get(index)),
                    CommonElementDir + "Checked Checkbox With Vidistar Icon.PNG")) {
                return index;
            }
        }
        return -1;
    }

    public boolean isPypScanFinalReportLinkedWithSignedPrelimReportData() {
        return sikuliActions
                .isElementVisible(USER_WORK_DIR + ReportScreens + "\\ReportScreen\\PypScanPrelimReportData.png");
    }

    public void clickOnMeasurementField(String measurementLabel) {
        sikuliActions.wait(2.0);
        sikuliActions.click(USER_WORK_DIR + ReportScreens + "EchoPreliminaryReport\\" + measurementLabel + "_TEXT.PNG");
    }

    public boolean isValueChangedAfterClickingOnMeasurementField() {
        return sikuliActions.isElementVisible(
                USER_WORK_DIR + ReportScreens + "EchoPreliminaryReport\\Measurement Field With Measured values.PNG");
    }

    public boolean isVidistarIconVisibleForGreyedOutField(String measurementLabel) {
        return sikuliActions.isExactElementVisible(
                USER_WORK_DIR + ReportScreens + "PediatricTTEPreliminaryReport\\" + measurementLabel + ".PNG");
    }

    public boolean isMeanCalculationIconPresent() {
        return sikuliActions.isElementVisible(CommonElementDir + "Mean Calculation Icon.PNG");
    }

    public boolean isMeanSignVisibleForGreyedOutField(String measurementLabel) {
        return sikuliActions.isExactElementVisible(
                USER_WORK_DIR + ReportScreens + "PediatricTTEPreliminaryReport\\" + measurementLabel + ".PNG");
    }

    public boolean areFieldsPresentInReport(String reportName, List<Map<String, String>> reportDetails,
                                            String tabName) {
        boolean areFieldsPresent = true;
        for (Map<String, String> reportDetail : reportDetails) {
            String type = reportDetail.get("type").toUpperCase();
            String fieldName = reportDetail.get("field");
            String region = reportDetail.get("region");
            areFieldsPresent = areFieldsPresent
                    && isFieldDisplayedUnderRegion(reportName, region, fieldName + "_" + type, tabName);
        }
        return areFieldsPresent;
    }

    public boolean isFieldDisplayedUnderRegion(String ReportName, String Region, String fieldName, String tabName) {
        boolean isFieldPresent = false;
        String fieldPatternFile = USER_WORK_DIR + ReportScreens + ReportName + "\\" + tabName + "\\" + fieldName
                + ".PNG";
        String regionPatternFile = USER_WORK_DIR + ReportScreens + ReportName + "\\" + tabName + "\\" + Region + ".PNG";
        try {
            sikuliActions.scrollToExactField(regionPatternFile);
            isFieldPresent = sikuliActions.isElementVisible(regionPatternFile, fieldPatternFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isFieldPresent;
    }

    public boolean isUmbilicalArteryComponentDisplayed(String componentName) {
        sikuliActions.waitForElementToExist(CommonElementDir + componentName + ".PNG", 10);
        return sikuliActions.isSimilarElementVisible(CommonElementDir + componentName + ".PNG", 0.4f);
    }

    public void typeTextInFreeTextArea(String fieldName) {
        String patternFile = USER_WORK_DIR + ReportScreens + "ReportScreen\\" + fieldName + ".PNG";
        sikuliActions.click(patternFile);
        sikuliActions.type(Constants.FreeText);
    }

    public void editLinesInFreeTextArea() {
        for (int i = 0; i <= 4; i++) {
            robotActions.backspace();
        }
        sikuliActions.doubleClick(CommonElementDir + "second_multilinetext.PNG");
        robotActions.backspace();
        robotActions.pressPageUpKey();
        for (int i = 0; i <= 4; i++) {
            robotActions.backspace();
        }
    }

    public void editLinesInFreeTextAreaUsingKeyboardArrowKey() {
        for (int i = 0; i <= 4; i++) {
            robotActions.backspace();
        }
        robotActions.pressUpArrowKey();
        for (int i = 0; i <= 14; i++) {
            robotActions.pressLeftArrowKey();
        }
        for (int i = 0; i <= 6; i++) {
            robotActions.backspace();
        }
        robotActions.pressUpArrowKey();
        for (int i = 0; i <= 5; i++) {
            robotActions.backspace();
        }
    }

    public void copyPasteContentInField() {
        robotActions.pressDownArrowKey();
        robotActions.enter();
        sikuliActions.pasteTextIntoField(Constants.FreeText);
    }

    public boolean isGraphDecoratorIconPresent() {
        return sikuliActions.isElementVisible(CommonElementDir + "Graph Decorator.PNG");
    }

    public void clickOnGraphDecoratorIcon(String measurementLabel) {
        sikuliActions.hoverExact(CommonElementDir + measurementLabel + " measurement.PNG");
        sikuliActions.click(CommonElementDir + "Graph Decorator.PNG");
    }

    public boolean isGraphDisplayedForMeasurementLabel(String measurementLabel) {
        return sikuliActions.isElementVisible(CommonElementDir + measurementLabel + " Graph.PNG");
    }

    public boolean isGraphDecoratorIconPresentForCalculatedMeasurementLabel(String measurementLabel) {
        return sikuliActions.isExactElementVisible(CommonElementDir + measurementLabel + " with graph decorator.PNG");
    }

    public void deselectAllAndDontSaveReport() {
        By saveResourcesDialog = getByLocator(reportViewerPageOR.saveResourcesDialog_Xpath, LocatorIdentifier.Xpath);
        By deselectAllButton = getByLocator(reportViewerPageOR.deselectAllButton_Name, LocatorIdentifier.Name);
        winActions.waitForWindowElementToBeVisible(saveResourcesDialog, 60);
        WebElement dialog = winActions.findWindowElementBy(saveResourcesDialog);
        winActions.click(dialog, deselectAllButton);
        winActions.click(getByLocator(reportViewerPageOR.dialogButton_Xpath, LocatorIdentifier.Xpath, "OK"));
    }

    public void clickOnDynamicPreviewIcon(String viewPatternIcon) {
        By locator = null;
        int clickCount = 1;
        switch (viewPatternIcon.toUpperCase()) {
            case "FIT TO WIDTH":
            case "FITTOWIDTH":
            case "FITWIDTH":
                locator = getByLocator(reportViewerPageOR.fitWidth_Name, LocatorIdentifier.Name);
                break;
            case "ZOOM IN":
            case "ZOOMIN":
                locator = getByLocator(reportViewerPageOR.zoomIn_Name, LocatorIdentifier.Name);
                clickCount = 2;
                break;
            case "ZOOM OUT":
            case "ZOOMOUT":
                locator = getByLocator(reportViewerPageOR.zoomOut_Name, LocatorIdentifier.Name);
                clickCount = 2;
                break;
            case "SAVEASTEMPLATE":
            case "SAVE AS TEMPLATE":
                locator = getByLocator(reportViewerPageOR.saveTemplate_Name, LocatorIdentifier.Name);
                clickCount = 2;
                break;
            case "FITPAGE":
                locator = getByLocator(reportViewerPageOR.fitPage_Name, LocatorIdentifier.Name);
                break;
            case "ACTUALSIZE":
            case "ACTUAL SIZE":
                locator = getByLocator(reportViewerPageOR.actualSize_Name, LocatorIdentifier.Name);
                break;
            case "COMPARISON BASELINE":
                locator = getByLocator(reportViewerPageOR.comparisonBaseline_Name, LocatorIdentifier.Name);
                break;
            case "NEXT PAGE":
            case "NEXTPAGE":
                locator = getByLocator(reportViewerPageOR.nextPage_Name, LocatorIdentifier.Name);
                winActions.waitForWindowElementToBeClickable(locator, 4);
                break;
        }
        for (int count = 1; count <= clickCount; count++) {
            winActions.click(locator);
        }
    }

    public void selectCharts(String reportName, List<Map<String, String>> chartDetails, Rectangle regionBounds) {
        for (Map<String, String> chartDetail : chartDetails) {
            String patternFile = Common.getReportLocation(reportName) + chartDetail.get("region") + ".PNG";
            String type = chartDetail.get("type").toUpperCase();
            String fieldValue = Common.getReportLocation(reportName) + chartDetail.get("field") + "_" + type + ".PNG";
            switch (type) {
                case "TEXT":
                    sikuliActions.typeTextInField(regionBounds, patternFile, fieldValue);
                    robotActions.pressTabKey();
                    break;
                case "DROPDOWN":
                    sikuliActions.selectValueFromDropdown(patternFile, fieldValue);
                    break;
                case "RADIO":
                    sikuliActions.selectRadioFromRegion(fieldValue, regionBounds);
                    break;
                case "CHECKBOX":
                    sikuliActions.selectExactCheckBoxFromRegion(fieldValue, regionBounds);
                    break;
            }
        }
    }

    public Rectangle getRegionBounds(String reportName, String region) {
        String regionPatternFile = Common.getReportLocation(reportName) + region + ".PNG";
        return sikuliActions.getRegionBounds(regionPatternFile);
    }

    public boolean areSelectedChartsPlotsDisplayedInReport(List<String> chartsList) {
        boolean areChartsDisplayed = true;
        for (String chartName : chartsList) {
            boolean isChartDisplayed = sikuliActions.isSimilarElementVisible(
                    USER_WORK_DIR + ReportScreens + "ReportScreen\\" + chartName + ".PNG", 0.7f);
            if ((!isChartDisplayed) && isNextPreviousReportPreviewPageAvailable("Next")) {
                moveToPage("Next");
                Common.sleep(1, TimeUnit.SECONDS);
                isChartDisplayed = sikuliActions.isSimilarElementVisible(
                        USER_WORK_DIR + ReportScreens + "ReportScreen\\" + chartName + ".PNG", 0.7f);
            }
            areChartsDisplayed = areChartsDisplayed && isChartDisplayed;
        }
        return areChartsDisplayed;
    }

    public boolean isNextPreviousReportPreviewPageAvailable(String pageDirection) {
        return sikuliActions.isExactElementVisible(CommonElementDir + pageDirection + ".PNG");
    }

    public void selectSubsectionFromLeftPanel(String regionPatternFile, String subsectionName) {
        sikuliActions.waitForElementToExist(CommonElementDir + regionPatternFile + " Panel.PNG", 0.4f, 10);
        Rectangle regionBounds = sikuliActions
                .getExactRegionBounds(CommonElementDir + regionPatternFile + " Panel.PNG");
        sikuliActions.wait(2.0);
        sikuliActions.click(regionBounds, CommonElementDir + subsectionName + " subsection.PNG", 0.7f);
    }

    public void expandCollapseSection(String action, String sectionName) {
        sikuliActions.waitForElementToExist(CommonElementDir + sectionName + " Section.PNG", 20);
        Rectangle regionBounds = sikuliActions.getExactRegionBounds(CommonElementDir + sectionName + " Section.PNG");
        sikuliActions.click(regionBounds, CommonElementDir + action + " Icon.png", 0.9f);
    }

    public void clickOnGraphIconForMeasurementLabel(String reportName, String measurementLabel) {
        Common.sleep(1, TimeUnit.SECONDS);
        robotActions.pressTabKey();
        sikuliActions.hoverExact(Common.getReportLocation(reportName) + "Highlighted " + measurementLabel + ".PNG");
        clickOnTrendIcon();
    }

    public boolean isMeasurementTrendDisplayed(String trendName) {
        sikuliActions.wait(2.0);
        if (winActions.isVisible(getByLocator(reportViewerPageOR.nextPage_Name, LocatorIdentifier.Name)) && winActions
                .getAttribute(getByLocator(reportViewerPageOR.nextPage_Name, LocatorIdentifier.Name), "IsEnabled")
                .equals("True")) {
            moveToPage("Next");
        }
        sikuliActions.waitForElementToExist(
                USER_WORK_DIR + ReportScreens + "ReportScreen\\" + trendName + " measurement trend.PNG", 10);
        return sikuliActions.isSimilarElementVisible(
                USER_WORK_DIR + ReportScreens + "ReportScreen\\" + trendName + " measurement trend.PNG", 0.4f);
    }

    public void clickOnTrendIcon() {
        sikuliActions.click(CommonElementDir + "Trend Icon.PNG");
    }

    public void moveAwayFromField() {
        sikuliActions.moveMouseByOffset(100, 0);
    }

    public boolean isTrendIconDisplayed() {
        sikuliActions.waitForElementInvisibility(CommonElementDir + "Trend Icon.PNG", 5);
        return sikuliActions.isExactElementVisible(CommonElementDir + "Trend Icon.PNG");
    }

    public boolean isSignedMeasurementTrendDisplayedWhileAmendingReport() {
        return sikuliActions
                .isSimilarElementVisible(USER_WORK_DIR + ReportScreens + "ReportScreen\\Measurement Trend.PNG", 0.5f);
    }

    public void selectHighlightedTab(String tabName) {
        sikuliActions.click(CommonElementDir + "Highlighted " + tabName + " Tab.PNG", 0.8f);
        robotActions.pressTabKey();
    }

    public boolean isVidistarIconPresentForCalculatedRankField(String rankField) {
        return sikuliActions.isExactElementVisible(
                USER_WORK_DIR + ReportScreens + "FetalTTEReport(Standard)\\" + rankField + ".PNG");
    }

    public boolean arePurpleFieldsDisplayedInComparisonBoxAndPreview(String displayRegion) {
        return sikuliActions.isElementVisible(displayRegion.equalsIgnoreCase("free text comparison box")
                ? USER_WORK_DIR + ReportScreens + "ReportScreen\\Purple Fields(Free text comparison box).PNG"
                : USER_WORK_DIR + ReportScreens + "ReportScreen\\Purple Fields(Dynamic Preview).PNG");
    }

    public boolean isLineItemDisplayedInSameHamburger() {
        return sikuliActions
                .isElementVisible(USER_WORK_DIR + ReportScreens + "ReportScreen\\Line item in same hamburger.PNG");
    }

    public boolean areLineItemsDisplayedOneBelowTheOther(String displayRegion) {
        return sikuliActions.isElementVisible(displayRegion.equalsIgnoreCase("free text comparison box")
                ? USER_WORK_DIR + ReportScreens + "ReportScreen\\Line Items(Free text comparison).PNG"
                : USER_WORK_DIR + ReportScreens + "ReportScreen\\Line Items(Dynamic preview).PNG");
    }

    public boolean areLineItemsDisplayedOneBelowTheOtherAfterManualEntry(String displayRegion) {
        return sikuliActions.isElementVisible(displayRegion.equalsIgnoreCase("free text comparison box")
                ? USER_WORK_DIR + ReportScreens + "ReportScreen\\Line Item (Manual entry in comparison box).PNG"
                : USER_WORK_DIR + ReportScreens + "ReportScreen\\Line Item(Manual entry in preview).PNG");
    }

    public void typeTextInFreeTextComparisonBox(String value, String fieldName) {
        String patternFile = USER_WORK_DIR + ReportScreens + "ReportScreen\\" + fieldName + ".PNG";
        sikuliActions.click(patternFile);
        sikuliActions.type(value);
        robotActions.enter();
    }

    public boolean areLineItemsDisplayedInAmendedReport(String displayRegion) {
        return sikuliActions.isElementVisible(displayRegion.equalsIgnoreCase("free text comparison box")
                ? USER_WORK_DIR + ReportScreens
                + "ReportScreen\\Line Items In Amended Report(Free text comparison box).PNG"
                : USER_WORK_DIR + ReportScreens + "ReportScreen\\Line Items in amended report(Dynamic preview).PNG");
    }

    public void deleteTextFromComparisonBox(String patternFile) {
        sikuliActions.doubleClick(USER_WORK_DIR + ReportScreens + "ReportScreen\\" + patternFile + ".PNG");
        robotActions.backspace();
        robotActions.delete();
        robotActions.backspace();
    }

    public boolean areLineItemsDisplayedOneBelowTheOtherAfterManualDeletion(String displayRegion) {
        return sikuliActions.isElementVisible(displayRegion.equalsIgnoreCase("free text comparison box")
                ? USER_WORK_DIR + ReportScreens + "ReportScreen\\Line Item(Manual delete in comparison box).PNG"
                : USER_WORK_DIR + ReportScreens + "ReportScreen\\Line Item(Manual delete in dynamic preview).PNG");
    }

    public boolean isCheckboxPhrasePopulatedInDynamicPreview() {
        return sikuliActions.isElementVisible(
                USER_WORK_DIR + ReportScreens + "ReportScreen\\Rest Imaging Phrase in Dynamic Preview.PNG");
    }

    public boolean areFieldsDistorted(String reportName, String tabName) {
        if (reportName.contains("Final")) {
            return sikuliActions.isElementVisible(OBUltrasoundFinalReport + tabName + ".PNG");
        } else {
            return sikuliActions.isElementVisible(OBFetalEchoPreliminaryReport + tabName + ".PNG");
        }
    }

    public void waitForFetusToAddUnderLeftPanel() {
        sikuliActions.waitForElementToExist(CommonElementDir + "Fetuses.PNG", 10);
    }

    public boolean isReportTitleChanged(String editedReportTitle) {
        String reportTitle = winActions.getText(reportViewerWindow,
                getByLocator(reportViewerPageOR.reportEditorTab_Xpath, LocatorIdentifier.Xpath));
        return reportTitle.contains(editedReportTitle);
    }

    public boolean isReportTitleForComparisonBaselineDisplayed(String editedReportTitle) {
        String reportTitle = winActions.getText(reportViewerWindow,
                getByLocator(reportViewerPageOR.comparisonBaselineReportName_Xpath, LocatorIdentifier.Xpath));
        return reportTitle.contains(editedReportTitle);
    }

    public boolean areFieldsAutopopulatedInFindingsSection(List<Map<String, String>> fieldDetails) {
        boolean areFieldsAutopopulatedInFindingsSection = true;
        for (Map<String, String> fieldDetail : fieldDetails) {
            String leftPanelSection = fieldDetail.get("Left panel section");
            String tabName = fieldDetail.get("Tab name");
            String fieldLabel = fieldDetail.get("Field label");
            selectSectionFromLeftPanel(leftPanelSection);
            clickOnSubtab(tabName);
            areFieldsAutopopulatedInFindingsSection = areFieldsAutopopulatedInFindingsSection
                    && isFieldAutopopulatedInFindingsSection(fieldLabel);
        }
        return areFieldsAutopopulatedInFindingsSection;
    }

    public boolean isFieldAutopopulatedInFindingsSection(String fieldName) {
        String fieldPatternFile = USER_WORK_DIR + ReportScreens + "PediatricTTEPreliminaryReport\\" + fieldName
                + ".PNG";
        sikuliActions.scrollToField(fieldPatternFile);
        return sikuliActions.isExactElementVisible(fieldPatternFile);
    }

    public boolean isAmendmentPresentInReport() {
        sikuliActions.waitForElementToExist(CommonElementDir + "Amended content.PNG", 10);
        return sikuliActions.isElementVisible(CommonElementDir + "Amended content.PNG");
    }

    public boolean isReportEditorTabOpened() {
        return winActions.isVisible(getByLocator(reportViewerPageOR.reportEditorTab_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean verifyMeasurementLabelIsAutoPopulated(String reportName, String measurementPatternFile) {
        return sikuliActions.isElementVisible(Common.getReportLocation(reportName) + measurementPatternFile + ".PNG");
    }

    public String getFieldValue(String fieldName) {
        sikuliActions.scrollToField(USER_WORK_DIR + ReportScreens + "EchoPreliminaryReport\\" + fieldName + ".PNG");
        return sikuliActions
                .getTextFromExactField(USER_WORK_DIR + ReportScreens + "EchoPreliminaryReport\\" + fieldName + ".PNG");
    }

    public String verifyValueIsPresentInMeasurementTable() {
        WebElement dataGrid = winActions.findWindowElementBy(
                getByLocator(reportViewerPageOR.meanCalculationDataGrid_Xpath, LocatorIdentifier.Xpath));
        return dataGrid
                .findElements(getByLocator(reportViewerPageOR.meanCalculationDataItem_Xpath, LocatorIdentifier.Xpath))
                .get(0).getAttribute("Name").split("\\s")[0];
    }

    public boolean isSectionVisibleOnReportEditor(String reportName, String sectionName) {
        for (int i = 0; i < 3; i++) {
            robotActions.pressTabKey();
        }
        scrollToField(sectionName + " section");
        return sikuliActions.isExactElementVisible(Common.getReportLocation(reportName) + sectionName + " section.PNG");
    }

    public boolean isVerticleScrollbarDisplayed() {
        return sikuliActions.isElementVisible(
                winActions.getElementBounds(reportViewerWindow,
                        getByLocator(reportViewerPageOR.reportEditorTab_Xpath, LocatorIdentifier.Xpath)),
                CommonElementDir + "VerticalScrollBar.PNG");
    }

    public void resizeReportEditorPane() {
        sikuliActions.dragElementToOffset(CommonElementDir + "Pane.PNG", 100, 0);
        sikuliActions.dragElementToOffset(CommonElementDir + "Pane.PNG", -100, 0);
    }

    public void hoverOnBullsEye(String reportName, String measurementLabel) {
        sikuliActions.hoverExact(Common.getReportLocation(reportName) + measurementLabel + "_TEXT.PNG");
        sikuliActions.hoverExact(CommonElementDir + "Bullseye.PNG");
    }

    public boolean isBullsEyeDisplayed(String bullseyeIcon) {
        return sikuliActions.isElementVisible(CommonElementDir + bullseyeIcon + ".PNG");
    }

    public void clearMeasurementLabelText(String patternFile) {
        sikuliActions.click(CommonElementDir + patternFile + " measurement.PNG");
        sikuliActions.clearTextFromField();
    }

    public boolean areFetusADetailsDuplicatedInOtherFetuses(String reportName, String fieldName) {
        return sikuliActions
                .isExactElementVisible(Common.getReportLocation(reportName) + "Highlighted " + fieldName + ".PNG");
    }

    public boolean isProcedureReportDataForSelectedTabPresentInFetus(String reportName, String tabName) {
        return sikuliActions
                .isExactElementVisible(CommonElementDir + "Procedure report data for " + tabName +
                        " Tab in " + reportName + ".PNG");
    }

    public void openMenu(String menu) {
        WebElement menuBar = winActions.findWindowElementBy(
                getByLocator(reportViewerPageOR.menuBarOption_Xpath, LocatorIdentifier.Xpath));
        winActions.click(menuBar, getByLocator(reportViewerPageOR.menuOption_Xpath, LocatorIdentifier.Xpath, menu));
    }

    public void selectMenuItemUnderMenu(String menu, String menuItem) {
        winActions.click(
                getByLocator(reportViewerPageOR.menuItem_Xpath, LocatorIdentifier.Xpath, menu, menuItem));
    }

    public void selectSubMenuOption(String menuOption, String subMenuOption) {
        WebElement subMenuOptionsWindow = winActions.findWindowElementBy(
                getByLocator(reportViewerPageOR.reportViewerSubMenuOption_Xpath, LocatorIdentifier.Xpath, menuOption));
        winActions.click(subMenuOptionsWindow,
                getByLocator(reportViewerPageOR.reportViewerMenuOption_Xpath, LocatorIdentifier.Xpath, subMenuOption));
    }

    public boolean isImageToggledToReport() {
        return sikuliActions.isElementVisible(USER_WORK_DIR + ReportScreens + "ReportScreen\\Toggled Image.PNG");
    }

    public boolean isImageToggledToPreview() {
        return sikuliActions.isElementVisible(USER_WORK_DIR + ReportScreens + "ReportScreen\\Toggled Image(Preview).PNG");
    }

    public boolean isDefaultFontSizeOfAnatomyDiagramSetTo15() {
        return sikuliActions.isElementVisible(USER_WORK_DIR + ReportScreens + "ReportScreen\\Default Font Size.PNG");
    }

    public boolean isAnnotatedTextDisplayedInDefaultFontSize(String viewAnnotatedText) {
        String patternFile = null;
        switch (viewAnnotatedText) {
            case "Report":
                patternFile = USER_WORK_DIR + ReportScreens + "ReportScreen\\Annotated Image(Default Size in Report).PNG";
                break;
            case "Dynamic Preview":
                patternFile = USER_WORK_DIR + ReportScreens + "ReportScreen\\Annotated Image(Default Size in Preview).PNG";
                break;
        }
        sikuliActions.waitForElementToExist(patternFile, 3.0);
        return sikuliActions.isElementVisible(patternFile);
    }

    public boolean isTextAnnotatedInDesiredFontStyle(String fontStyleFile, String viewAnnotatedText) {
        String patternFile = null;
        switch (viewAnnotatedText) {
            case "Report":
                patternFile = USER_WORK_DIR + ReportScreens + "ReportScreen\\" + fontStyleFile + "_TEXT(Report).PNG";
                break;
            case "Dynamic Preview":
                patternFile = USER_WORK_DIR + ReportScreens + "ReportScreen\\" + fontStyleFile + "_TEXT(Dynamic Preview).PNG";
                break;
        }
        sikuliActions.waitForElementToExist(patternFile, 3.0);
        return sikuliActions.isElementVisible(patternFile);
    }

    public void annotateText(String textValue, String region) {
        switch (region.toUpperCase()) {
            case "REGION1":
                sikuliActions.click(USER_WORK_DIR + ReportScreens + "ReportScreen\\Annotation Region(Second Text).PNG");
                sikuliActions.typeTextInExactField(USER_WORK_DIR + ReportScreens + "ReportScreen\\Text Area.PNG", textValue);
                robotActions.enter();
                break;
            case "REGION2":
                sikuliActions.click(USER_WORK_DIR + ReportScreens + "ReportScreen\\Annotation Region(Second Diagram).PNG");
                sikuliActions.typeTextInExactField(USER_WORK_DIR + ReportScreens + "ReportScreen\\Text Area.PNG", textValue);
                robotActions.enter();
                break;
        }
    }

    public boolean isFontStyleIconHighlightedInBlueBox(String iconName) {
        return sikuliActions.isExactElementVisible(USER_WORK_DIR + ReportScreens + "ReportScreen\\" + iconName + "_HIGHLIGHTED.PNG");
    }

    public void changeAnatomyDiagramFromDropdown(String reportName, String defaultAnatomyName, String dropdownAnatomyName) {
        sikuliActions.click(Common.getReportLocation(reportName) + defaultAnatomyName + "_DROPDOWN.PNG");
        sikuliActions.click(Common.getReportLocation(reportName) + dropdownAnatomyName + ".PNG");
    }

    public boolean isVidistarIconDisplayedInField(String reportName, String measurementField) {
        return sikuliActions.isExactElementVisible(Common.getReportLocation(reportName) + measurementField + " with vidistar icon.PNG");

    }

    public boolean isPhraseAndFormulaDisplayedForGreyedOutField(String fieldName) {
        return sikuliActions.isElementVisible(CommonElementDir + fieldName + "_Phrase&Formula.PNG");
    }

    public boolean areMeasurementToolsDisplayedInField() {
        return sikuliActions.isExactElementVisible(CommonElementDir + "Measurement Tools.PNG");
    }

    public int getIndexOfMachineIconRow() {
        WebElement dataGrid = winActions.findWindowElementBy(
                getByLocator(reportViewerPageOR.meanCalculationDataGrid_Xpath, LocatorIdentifier.Xpath));
        List<WebElement> dataItems = dataGrid
                .findElements(getByLocator(reportViewerPageOR.meanCalculationDataItem_Xpath, LocatorIdentifier.Xpath));
        for (int index = 0; index < dataItems.size(); index++) {
            if (sikuliActions.isExactElementVisible(winActions.getElementBounds(dataItems.get(index)),
                    CommonElementDir + "Unchecked Checkbox With Machine Icon.PNG")) {
                return index;
            }
        }
        return -1;
    }
}
