package Pages;

import Actions.RobotActions;
import Actions.SikuliActions;
import Actions.WindowsActions;
import ObjectRepository.DicomViewerPageOR;
import Utilities.*;
import org.apache.commons.lang.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static Utilities.Constants.*;

public class DicomViewerPage extends Page {

    WindowsActions winActions;
    SikuliActions sikuliActions;
    WebElement dicomViewerWindow;
    RobotActions robotActions;
    ImageReader imageReader;
    WebElement anonymizationWindow;
    WebElement preferencesWindow;

    public DicomViewerPage(Driver driver) {
        super(driver);
        winActions = new WindowsActions(driver.getWinDriver());
        sikuliActions = new SikuliActions();
        robotActions = new RobotActions();
        imageReader = new ImageReader();
    }

    private static final DicomViewerPageOR dicomViewerPageOR = YmlReader
            .getObjectRepository(USER_WORK_DIR + DicomViewerPageORFile, DicomViewerPageOR.class);
    private static final String DicomViewerElements = USER_WORK_DIR
            + "\\src\\test\\resources\\DicomViewerScreenElements\\";
    public static final String CommonElementDir = USER_WORK_DIR + ReportScreens + "Common\\";
    private static final String SendFaxEmailElements = USER_WORK_DIR + "\\src\\test\\resources\\SendFaxEmailElements\\";

    private final By loadingViewer = getByLocator(dicomViewerPageOR.loadingViewerWindow_Name, LocatorIdentifier.Name);
    private final By dicomViewer = getByLocator(dicomViewerPageOR.dicomViewerWindow_Name, LocatorIdentifier.Name);
    private final By oneToOneLayout = getByLocator(dicomViewerPageOR.oneToOneLayout_Name, LocatorIdentifier.Name);
    private final By twoToOneLayout = getByLocator(dicomViewerPageOR.twoToOneLayout_Name, LocatorIdentifier.Name);
    private final By fourToOneLayout = getByLocator(dicomViewerPageOR.fourToOneLayout_Name, LocatorIdentifier.Name);
    private final By sixToOneLayout = getByLocator(dicomViewerPageOR.sixToOneLayout_Name, LocatorIdentifier.Name);
    private final By closeViewerButton = getByLocator(dicomViewerPageOR.closeViewerButton_Name, LocatorIdentifier.Name);

    public void waitForViewerToLoad() {
        try {
            Log.printInfo("Waiting for Dicom Viewer Window");
            winActions.waitForWindowElementToBeVisible(dicomViewer, 180);
            Log.printInfo("Dicom viewer window found");
            Log.printInfo("Waiting for loading window to disappear");
            //sikuliActions.waitForElementInvisibility(DicomViewerElements + "Loading Viewer.Png", 180);
            Log.printInfo("Loading window disappeared");
            winActions.waitForWindowElementToBeInvisible(loadingViewer, 180);
            Log.printInfo("Creating dicom viewer object");
            dicomViewerWindow = getDicomViewerWindow();
            Log.printInfo("Dicom viewer captured in object");
        } catch (Exception e) {
            Log.printInfo("Executing getDicomViewerWindow from catch block");
            dicomViewerWindow = getDicomViewerWindow();
            Log.printError(e.getMessage());
            e.printStackTrace();
        }
    }

    public WebElement getDicomViewerWindow() {
        WebElement dicomViewerWindow = winActions.findWindowElementBy(dicomViewer);
        this.dicomViewerWindow = dicomViewerWindow;
        return dicomViewerWindow;
    }

    public boolean isDicomViewerWindowVisible() {
        try {
            return winActions.findWindowElementBy(dicomViewer).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPatientInfoPresentOnViewer(String patientName) {
        By patientTab = getByLocator(dicomViewerPageOR.dicomViewerTab_Xpath, LocatorIdentifier.Xpath, patientName);
        WebElement tabPatientInfo = winActions.findWindowElementBy(dicomViewerWindow, patientTab);
        return tabPatientInfo.isDisplayed();
    }

    public void selectScreenLayout(String layout) {
        switch (layout) {
            case "1:1":
                winActions.click(oneToOneLayout);
                break;
            case "2:1":
                winActions.click(twoToOneLayout);
                break;
            case "4:1":
                winActions.click(fourToOneLayout);
                break;
            case "6:1":
                winActions.click(sixToOneLayout);
                break;
        }
    }

    public void closeDicomViewer() {
        try {
            getDicomViewerWindow();
            if (dicomViewerWindow.isDisplayed()) {
                Log.printInfo("Closing dicom viewer");
                winActions.click(dicomViewerWindow, closeViewerButton);
            }
        } catch (Exception ignored) {

        }
    }

    public void openMenu(String menuHeader) {
        try {
            winActions.click(dicomViewerWindow,
                    getByLocator(dicomViewerPageOR.menuBarOption_Xpath, LocatorIdentifier.Xpath, menuHeader));
        } catch (Exception e) {
            winActions.click(getDicomViewerWindow(),
                    getByLocator(dicomViewerPageOR.menuBarOption_Xpath, LocatorIdentifier.Xpath, menuHeader));
        }
    }

    public void goToMenuOption(String menuOption) {
        winActions
                .hoverOnElement(dicomViewerWindow, getByLocator(dicomViewerPageOR.menuOption_Xpath, LocatorIdentifier.Xpath, menuOption));
    }

    public void selectMenuOption(String menuOption) {
        /*if (!winActions
                .findWindowElementBy(dicomViewerWindow,
                        getByLocator(dicomViewerPageOR.menuOption_Xpath, LocatorIdentifier.Xpath, menuOption))
                .isDisplayed()) {
            sikuliActions.selectMenuItemFromList(DicomViewerElements + menuOption + ".PNG");
        } else {
            winActions.click(getDicomViewerWindow(), getByLocator(dicomViewerPageOR.menuOption_Xpath, LocatorIdentifier.Xpath, menuOption));
        }*/
        try {
            winActions.click(getDicomViewerWindow(), getByLocator(dicomViewerPageOR.menuOption_Xpath, LocatorIdentifier.Xpath, menuOption));
        } catch (Exception e) {
            Log.printInfo("Unable to select menu item using winium. Trying with Sikuli");
            sikuliActions.selectMenuItemFromList(DicomViewerElements + menuOption + ".PNG");
        }
    }

    public void waitForDicomViewerToClose() {
        try {
            winActions.waitForWindowElementToBeInvisible(dicomViewer, 60);
        } catch (Exception e) {
            Log.printError("Dicom viewer not closed");
        }
    }

    public void scrollWindowToBottom() {
        winActions.moveScrollbarToBottom(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.scrollbar_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isBlankStudyVisibleOnViewer() {
        String blankStudyPatternFile = DicomViewerElements + "BlankStudy.png";
        return sikuliActions.isElementVisible(blankStudyPatternFile);
    }

    public void importPdfFile() {
        winActions.setText(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.importFileDialogFileNameTextbox_Xpath, LocatorIdentifier.Xpath),
                USER_WORK_DIR + "\\src\\test\\resources\\Report Pdf\\PDF Report.pdf");
        winActions.click(dicomViewerWindow, getByLocator(dicomViewerPageOR.importFileDialogOpenButton_Xpath, LocatorIdentifier.Xpath));
        winActions.click(getDicomViewerWindow(), getByLocator(dicomViewerPageOR.importFileDialogOkButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void saveFile() {
        winActions.setText(getByLocator(dicomViewerPageOR.importFileDialogFileNameTextbox_Xpath, LocatorIdentifier.Xpath),
                USER_WORK_DIR + "\\Report");
        winActions.click(getByLocator(dicomViewerPageOR.fileDialogSaveButton_Xpath, LocatorIdentifier.Xpath));
        Common.sleep(2, TimeUnit.SECONDS);
        robotActions.pressDownArrowKey();
    }

    public void clickReportsPanel() {
        winActions.click(getByLocator(dicomViewerPageOR.reportsPanel_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isReportPresentInReportList() {
        WebElement reportListPane = winActions.findWindowElementBy(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.reportsListPane_Xpath, LocatorIdentifier.Xpath));
        String importPDFPatternFile = DicomViewerElements + "PDF Report.png";
        sikuliActions.scrollToField(importPDFPatternFile);
        return sikuliActions.isExactElementVisible(winActions.getElementBounds(reportListPane), importPDFPatternFile);
    }

    public void selectThumbnail(String thumbnailName) {
        sikuliActions.doubleClick(DicomViewerElements + thumbnailName + ".PNG");
    }

    public boolean isSelectedThumbnailVisible(String thumbnailName) {
        return sikuliActions.isElementVisible(DicomViewerElements + thumbnailName + ".PNG");
    }

    public void waitForPreferencesWindow() {
        winActions.waitForWindowElementToBeVisible(getByLocator(dicomViewerPageOR.preferencesModal_Xpath, LocatorIdentifier.Xpath), 5);
        preferencesWindow = winActions.findWindowElementBy(dicomViewerWindow, getByLocator(dicomViewerPageOR.preferencesModal_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isWindowPreferencesWindowVisible() {
        try {
            winActions.waitForWindowElementToBeVisible(dicomViewerWindow, getByLocator(dicomViewerPageOR.preferencesModal_Xpath, LocatorIdentifier.Xpath), 5);
            preferencesWindow = winActions.findWindowElementBy(dicomViewerWindow, getByLocator(dicomViewerPageOR.preferencesModal_Xpath, LocatorIdentifier.Xpath));
            return preferencesWindow.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickOnAnyOptionInPreferencesWindow(String option) {
        waitForPreferencesWindow();
        winActions.click(preferencesWindow, getByLocator(dicomViewerPageOR.propertyTreeItem_Xpath, LocatorIdentifier.Xpath, option));
    }

    public void selectCheckBox(String checkBoxName) {
        winActions.click(preferencesWindow, getByLocator(dicomViewerPageOR.dicomViewerOptionsCheckbox_Xpath, LocatorIdentifier.Xpath,
                checkBoxName));
    }

    public void applyPreferences() {
        try {
            winActions.click(preferencesWindow, getByLocator(dicomViewerPageOR.applyAndCloseButton_Xpath, LocatorIdentifier.Xpath));
        } catch (Exception e) {
            waitForPreferencesWindow();
            winActions.click(preferencesWindow, getByLocator(dicomViewerPageOR.applyAndCloseButton_Xpath, LocatorIdentifier.Xpath));
        }
    }

    public boolean isReportAndViewerOpenInSameWindow() {
        winActions.waitForWindowElementToBeVisible(
                getByLocator(dicomViewerPageOR.dicomViewerReportsTab_Xpath, LocatorIdentifier.Xpath), 10);
        return sikuliActions.isElementVisible(
                USER_WORK_DIR + "\\src\\test\\resources\\ReportScreenElements\\Common\\ReportVerify2.PNG");
    }

    public boolean isMenuOptionEnabled(String menuItemName) {
        return winActions.isElementEnabled(
                getByLocator(dicomViewerPageOR.menuOption_Xpath, LocatorIdentifier.Xpath, menuItemName));
    }

    public void clickOnImageInViewer() {
        winActions.click(dicomViewerWindow, getByLocator(dicomViewerPageOR.imagePane_Xpath, LocatorIdentifier.Xpath));

    }

    public void setTextOnImageInDicomViewer(String value) {
        winActions.setText(dicomViewerWindow, getByLocator(dicomViewerPageOR.imagePane_Xpath, LocatorIdentifier.Xpath),
                value);
        robotActions.enter();
    }

    public void clickOnContextMenuOptionOnImage(String option) {
        winActions.click(getByLocator(dicomViewerPageOR.optionsForImageInMenu_Xpath, LocatorIdentifier.Xpath, option));
    }

    public int getImageCount() {
        String value = winActions.getText(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.imageNumberInDicomViewer_Xpath, LocatorIdentifier.Xpath));
        return Integer.parseInt(value.split("/")[1]);
    }

    public void scrollToLastImage() {
        String imageNumberLabelText = winActions.getText(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.dicomImageNumberLabel_Xpath, LocatorIdentifier.Xpath));
        int lastImage = Integer.parseInt(imageNumberLabelText.split("/")[1]);
        for (int imageNumber = 1; imageNumber < lastImage; imageNumber++) {
            robotActions.pressRightArrowKey();
            Common.sleep(100, TimeUnit.MILLISECONDS);
        }
        Log.printInfo("Scrolled to last image");
    }

    public boolean isLeftPanelPresentOnDicomViewer() {

        return winActions.isVisible(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.leftPanelStudyListPaneOnViewerWindow_Xpath, LocatorIdentifier.Xpath))
                && winActions.isVisible(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.leftPanelThumbnailsPaneOnViewerWindow_Xpath,
                        LocatorIdentifier.Xpath))
                && winActions.isVisible(dicomViewerWindow, getByLocator(
                dicomViewerPageOR.leftPanelReportsPaneOnViewerWindow_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean isAnnotatedImageVisibleOnViewport(BufferedImage annotatedImage) {
        return sikuliActions.isElementVisible(getViewportBoundsByIndex(0), annotatedImage, 0.5f);
    }

    public void clickCineButton(String cineButton) {
        String buttonPatternFile = null;
        switch (cineButton.toUpperCase()) {
            case "STOP":
            case "PAUSE":
                buttonPatternFile = DicomViewerElements + "CineStopButton.PNG";
                break;
            case "PLAY":
            case "RESUME":
                buttonPatternFile = DicomViewerElements + "CinePlayButton.PNG";
        }
        sikuliActions.click(buttonPatternFile, 0.4f);
    }

    public void clickCineButton(String cineButton, Integer viewportIndex) {
        String buttonPatternFile = null;
        WebElement viewport = getViewportByIndex(viewportIndex);
        Rectangle viewportRegion = winActions.getElementBounds(viewport);
        switch (cineButton.toUpperCase()) {
            case "STOP":
            case "PAUSE":
                buttonPatternFile = DicomViewerElements + "CineStopButton.PNG";
                break;
            case "PLAY":
            case "RESUME":
                buttonPatternFile = DicomViewerElements + "CinePlayButton.PNG";
        }
        sikuliActions.click(viewportRegion, buttonPatternFile);
    }

    public boolean isCinePlayingForPatient() {
        Rectangle elementBounds = winActions.getElementBounds(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.viewportPane_Xpath, LocatorIdentifier.Xpath));
        return sikuliActions.isImageChanged(elementBounds, 1000);
    }

    public boolean isCinePlayingForPatient(int viewportIndex) {
        WebElement viewport = getViewportByIndex(viewportIndex);
        Rectangle elementBounds = winActions.getElementBounds(viewport);
        return sikuliActions.isImageChanged(elementBounds, 500);
    }

    public void moveCineSliderInDirection(String direction) {
        switch (direction.toUpperCase()) {
            case "FORWARD":
                sikuliActions.dragElementToOffset(DicomViewerElements + "CineSlider.PNG", 50, 0);
                break;
            case "BACKWARD":
                sikuliActions.dragElementToOffset(DicomViewerElements + "CineSlider.PNG", -50, 0);
                break;
        }
    }

    public String getCineFrameNumber() {
        Rectangle viewportBounds = winActions.getElementBounds(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.viewportPane_Xpath, LocatorIdentifier.Xpath));
        Rectangle elementBounds = winActions.getLeftBottomRegionFromBounds(viewportBounds, 140, 70);
        Rectangle frameTextBounds = winActions
                .getLeftTopRegionFromBounds(winActions.getLeftBottomRegionFromBounds(elementBounds, 125, 55), 125, 17);
        String imageText = imageReader.getImageNumbers(sikuliActions.getImageWithinBounds(frameTextBounds), 2);
        Log.printInfo("Image text: ");
        Log.printInfo(imageText);
        Pattern pattern = Pattern.compile("[0-9]{1,3}");
        Matcher matcher = pattern.matcher(imageText);
        List<String> matchedStrings = new ArrayList<>();
        while (matcher.find()) {
            matchedStrings.add(matcher.group());
        }
        return matchedStrings.get(matchedStrings.size() - 2).trim();
    }

    public int getImageCountOnViewer() {
        return winActions.getElementsCount(
                getByLocator(dicomViewerPageOR.imageNumberInDicomViewer_Xpath, LocatorIdentifier.Xpath));
    }

    public void scrollToImage(int imageNumber) {
        int difference = imageNumber - getImageNumber();
        for (int i = 1; i <= Math.abs(difference); i++) {
            if (difference > 0) {
                robotActions.pressRightArrowKey();
            } else {
                robotActions.pressLeftArrowKey();
            }
            Common.sleep(100, TimeUnit.MILLISECONDS);
        }
    }

    public void selectPreferencesCheckbox(String checkBoxName) {
        WebElement preferencesDialog = winActions.findWindowElementBy(getDicomViewerWindow(),
                getByLocator(dicomViewerPageOR.preferencesModal_Xpath, LocatorIdentifier.Xpath));
        winActions.selectElement(preferencesDialog, getByLocator(
                dicomViewerPageOR.reportEditorOptionsCheckbox_Xpath, LocatorIdentifier.Xpath, checkBoxName));
        Log.printInfo("CheckBox is already selected");
    }

    public void selectToolbarOption(String option) {
        winActions.selectElement(dicomViewerWindow, getByLocator(dicomViewerPageOR.actionButton_Xpath, LocatorIdentifier.Xpath, option));
    }

    public void dragMouseOnViewport(String direction, boolean isBrightnessContrastOperation) {
        Rectangle elementBounds = winActions.getElementBounds(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.viewportPane_Xpath, LocatorIdentifier.Xpath));
        switch (direction.toUpperCase()) {
            case "UP":
            case "TOP":
                sikuliActions.dragMouseOnElementBounds(elementBounds, 0, -50);
                break;
            case "DOWN":
            case "BOTTOM":
                sikuliActions.dragMouseOnElementBounds(elementBounds, 0, 50);
                break;
            case "LEFT":
                sikuliActions.dragMouseOnElementBounds(elementBounds, isBrightnessContrastOperation ? -100 : -50, 0);
                break;
            case "RIGHT":
                sikuliActions.dragMouseOnElementBounds(elementBounds, isBrightnessContrastOperation ? 100 : 50, 0);
                break;
        }
        Common.sleep(2, TimeUnit.SECONDS);
    }

    public void drawAnnotation(String annotationType) {
        waitForViewerToLoad();
        Rectangle elementBounds = winActions.getElementBounds(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.viewportPane_Xpath, LocatorIdentifier.Xpath));
        switch (annotationType.toUpperCase()) {
            case "REGION OF INTEREST":
            case "FREE HAND SURFACE":
                sikuliActions.moveToCenterOfElementBounds(elementBounds);
                Common.sleep(2, TimeUnit.SECONDS);
                sikuliActions.dragMouseByOffset(0, -40, false);
                sikuliActions.dragMouseByOffset(40, 0, true);
                break;

            case "TEXT":
                sikuliActions.moveToCenterOfElementBounds(elementBounds);
                Common.sleep(2, TimeUnit.SECONDS);
                sikuliActions.click();
                sikuliActions.type("test");
                robotActions.enter();
                break;

            case "VOLUME - METHOD OF DISKS":
                sikuliActions.moveToCenterOfElementBounds(elementBounds);
                Common.sleep(2, TimeUnit.SECONDS);
                sikuliActions.dragMouseByOffset(0, -40, false);
                sikuliActions.releaseMouse();
                sikuliActions.dragMouseByOffset(40, 40);
                break;
        }
    }

    public int getViewportContrast() {
        Rectangle viewportBounds = winActions.getElementBounds(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.viewportPane_Xpath, LocatorIdentifier.Xpath));
        Rectangle elementBounds = winActions.getLeftBottomRegionFromBounds(viewportBounds, 120, 25);
        String imageText = imageReader.getImageNumbers(sikuliActions.getImageWithinBounds(elementBounds), 0);
        Log.printInfo("Image text: ");
        Log.printInfo(imageText);
        Pattern pattern = Pattern.compile("[0-9]{2,3}");
        Matcher matcher = pattern.matcher(imageText);
        List<String> matchedStrings = new ArrayList<>();
        while (matcher.find()) {
            matchedStrings.add(matcher.group());
        }
        return Integer.parseInt(matchedStrings.get(matchedStrings.size() - 1).trim());
    }

    public int getViewportBrightness() {
        Rectangle viewportBounds = winActions.getElementBounds(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.viewportPane_Xpath, LocatorIdentifier.Xpath));
        Rectangle elementBounds = winActions.getLeftBottomRegionFromBounds(viewportBounds, 120, 25);
        String imageText = imageReader.getImageNumbers(sikuliActions.getImageWithinBounds(elementBounds), 0);
        Log.printInfo("Image text: ");
        Log.printInfo(imageText);
        Pattern pattern = Pattern.compile("[0-9]{2,3}");
        Matcher matcher = pattern.matcher(imageText);
        List<String> matchedStrings = new ArrayList<>();
        while (matcher.find()) {
            matchedStrings.add(matcher.group());
        }
        return Integer.parseInt(matchedStrings.get(0).trim());
    }

    public void doubleClickOnParticularLocationOnImage(int xOffset, int yOffset) {
        winActions.doubleClick(getByLocator(dicomViewerPageOR.imageNumberInDicomViewer_Xpath, LocatorIdentifier.Xpath),
                xOffset, yOffset);

    }

    public boolean isTemplateAdminTabPresent() {
        return winActions.isVisible(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.templateAdminTab_Xpath, LocatorIdentifier.Xpath));

    }

    public void selectConfigurationInstanceFromList(String configurationInstanceName) {
        winActions.click(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.tabLabel_Xpath, LocatorIdentifier.Xpath, configurationInstanceName));
    }

    public void selectConfigurationInstanceParameterFromList(String configurationInstanceParameterName) {
        winActions.click(dicomViewerWindow, getByLocator(dicomViewerPageOR.propertyTreeItem_Xpath,
                LocatorIdentifier.Xpath, configurationInstanceParameterName));
    }

    public void clickThreeDotsOnEditor() {
        winActions.click(dicomViewerWindow, getByLocator(dicomViewerPageOR.browseButton_Name, LocatorIdentifier.Name));
    }

    public boolean isReportTemplatePresent() {
        sikuliActions.waitForElementToExist(
                USER_WORK_DIR + "\\src\\test\\resources\\ReportScreenElements\\Common\\CathGuiReportTemplate.PNG", 10);
        return sikuliActions.isElementVisible(
                USER_WORK_DIR + "\\src\\test\\resources\\ReportScreenElements\\Common\\CathGuiReportTemplate.PNG");
    }

    public WebElement getViewportByIndex(int index) {
        return getAllViewportElements().get(index);
    }

    public List<WebElement> getAllViewportElements() {
        waitForImageToLoad();
        List<WebElement> imageNumbers = winActions.findWindowElementsBy(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.imageNumberInDicomViewer_Xpath, LocatorIdentifier.Xpath));
        List<WebElement> viewportList = new ArrayList<>();
        imageNumbers.forEach(
                imageNumber -> viewportList.add(winActions.findWindowElementBy(imageNumber, By.xpath("./.."))));
        return viewportList;
    }

    public void doubleClickOnViewport(int viewportIndex) {
        WebElement viewportElement = getViewportByIndex(viewportIndex);
        winActions.doubleClick(viewportElement);
    }

    public boolean isImageManipulated(String imageStatus) {
        Rectangle viewportBounds = winActions.getElementBounds(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.viewportPane_Xpath, LocatorIdentifier.Xpath));
        sikuliActions.getImageWithinBounds(viewportBounds);
        return sikuliActions.isSimilarElementVisible(DicomViewerElements + imageStatus.replace(" ", "") + ".PNG", 0.6f);
    }

    public void resetViewport() {
        robotActions.pressRightArrowKey();
        Common.sleep(500, TimeUnit.MILLISECONDS);
        robotActions.pressLeftArrowKey();
    }

    public void unselectPreferencesCheckbox(String checkBoxName) {
        WebElement preferencesDialog = winActions.findWindowElementBy(getDicomViewerWindow(),
                getByLocator(dicomViewerPageOR.preferencesModal_Xpath, LocatorIdentifier.Xpath));
        winActions.unselectElement(preferencesDialog, getByLocator(
                dicomViewerPageOR.reportEditorOptionsCheckbox_Xpath, LocatorIdentifier.Xpath, checkBoxName));
        Log.printInfo("CheckBox " + checkBoxName + "is unselected");
    }

    public boolean isImagePrefetchingInProgress() {
        sikuliActions.waitForElementToExist(DicomViewerElements + "PrefetchingImages.png", 180);
        return sikuliActions.isElementVisible(DicomViewerElements + "PrefetchingImages.png");
    }

    public boolean isImagePrefetchingCompleted() {
        int retryCount = 0;
        waitForViewerToLoad();
        while (sikuliActions.isElementVisible(DicomViewerElements + "PrefetchingImages.png") && retryCount < 5) {
            Common.sleep(5, TimeUnit.SECONDS);
            retryCount++;
        }
        return sikuliActions.isElementVisible(DicomViewerElements + "PrefetchingImages.png");
    }

    public void waitTillImagesArePrefetched() {
        sikuliActions.waitForElementInvisibility(DicomViewerElements + "PrefetchingImages.png", 360);
    }

    public void rightClickOnReportFromReportsTab(String reportName) {
        getDicomViewerWindow();
        sikuliActions.click(DicomViewerElements + reportName + ".PNG");
        sikuliActions.rightClick();
    }

    public void exportReportAs(String reportFormat) {
        int itemOrder = 0;
        switch (reportFormat) {
            case "PDF":
                itemOrder = 2;
                break;
            case "TXT":
                itemOrder = 3;
                break;
            case "DICOM SR":
                itemOrder = 4;
                break;
        }
        for (int order = 1; order <= (System.getProperty("env") == null || System.getProperty("env").equalsIgnoreCase("demodevel") ? 8 : 7); order++) {
            robotActions.pressDownArrowKey();
        }
        robotActions.pressRightArrowKey();
        for (int order = 1; order < itemOrder; order++) {
            robotActions.pressDownArrowKey();
        }
        robotActions.enter();
    }

    public void performOperationOnReport(String operation) {
        int itemOrder = 0;
        switch (operation.toUpperCase()) {
            case "VIEW":
                itemOrder = 1;
                break;
            case "AMEND":
                itemOrder = 4;
                break;
            case "FAX & EMAIL":
                itemOrder = 3;
                break;
            case "CORRECT":
                itemOrder = 8;
                break;
            case "VIEW AS DSR":
                itemOrder = 2;
                break;
            case "CREATE NEW FINAL REPORT":
                itemOrder = 7;
                break;
            case "CREATE NEW PRELIMINARY REPORT":
                itemOrder = 6;
                break;
        }
        for (int order = 1; order <= itemOrder; order++) {
            robotActions.pressDownArrowKey();
        }
        robotActions.enter();
    }

    public void addReferringPhysicianToSelectedContacts() {
        sikuliActions.moveToElement(SendFaxEmailElements + "vidistarfax.PNG");
        Common.sleep(1, TimeUnit.SECONDS);
        sikuliActions.click();
        sikuliActions.moveToElement(SendFaxEmailElements + "Add.PNG");
        Common.sleep(1, TimeUnit.SECONDS);
        sikuliActions.click();
        Common.sleep(1, TimeUnit.SECONDS);
    }

    public void clickSendButton() {
        sikuliActions.moveToElement(SendFaxEmailElements + "Send.PNG");
        Common.sleep(1, TimeUnit.SECONDS);
        sikuliActions.click();
        sikuliActions.moveToElement(SendFaxEmailElements + "Yes.PNG");
        Common.sleep(1, TimeUnit.SECONDS);
        sikuliActions.click();
    }

    public boolean isMeasurementDisplayed(String measurement) {
        sikuliActions.waitForElementToExist(DicomViewerElements + measurement + " Measurement.PNG", 10);
        return sikuliActions.isSimilarElementVisible(DicomViewerElements + measurement + " Measurement.PNG", 0.5f);
    }

    public void waitForImageToLoad() {
        Log.printInfo("Waiting for loading indicator to disappear");
        winActions.waitForWindowElementToBeInvisible(getDicomViewerWindow(),
                getByLocator(dicomViewerPageOR.loadingTextInDicomViewer_Name, LocatorIdentifier.Name), 180);
        Log.printInfo("Loading icon disappeared");
    }

    public List<String> getNuclearTabNamesDisplayed() {
        WebElement viewportPaneWindow = winActions
                .findWindowElementBy(getByLocator(dicomViewerPageOR.viewportPane_Xpath, LocatorIdentifier.Xpath));
        List<WebElement> nuclearTabs = winActions.findWindowElementsBy(viewportPaneWindow,
                getByLocator(dicomViewerPageOR.nuclearStudyTabs_Xpath, LocatorIdentifier.Xpath));
        List<String> nuclearTabNames = new ArrayList<>();
        for (WebElement tab : nuclearTabs) {
            nuclearTabNames.add(tab.getAttribute("Name"));
        }
        return nuclearTabNames;
    }

    public void clickOnNuclearTab(String tabName) {
        winActions.click(getByLocator(dicomViewerPageOR.nuclearStudyTab_Xpath, LocatorIdentifier.Xpath, tabName));
    }

    public void waitForNuclearImagesToLoad(String tabName) {
        switch (tabName.toUpperCase()) {
            case "ARRAY":
                winActions.waitForWindowElementToBeVisible(
                        getByLocator(dicomViewerPageOR.saxLabel_Xpath, LocatorIdentifier.Xpath), 60);
                break;
            case "RAW":
            case "POLAR PLOTS":
            case "SLICE":
            case "3D":
            case "FULL DISPLAY":
            case "QUICK VIEW":
                winActions.waitForWindowElementToBeVisible(
                        getByLocator(dicomViewerPageOR.stressGatedLabel_Xpath, LocatorIdentifier.Xpath), 60);
                break;
            case "SECONDARY CAPTURE":
                winActions.waitForWindowElementToBeInvisible(
                        getByLocator(dicomViewerPageOR.imageLoadingLabel_Xpath, LocatorIdentifier.Xpath), 60);
                break;
        }
    }

    public boolean isImagePresentOnDicomViewer(String labelName) {
        sikuliActions.waitForElementToExist(DicomViewerElements + labelName + " Images.PNG", 0.4f, 60);
        return sikuliActions.isSimilarElementVisible(DicomViewerElements + labelName + " Images.PNG", 0.4f);
    }

    public void clickOnSubOptionInPreferences(String childOption, String parentOption) {
        winActions.click(getByLocator(dicomViewerPageOR.propertyTreeItem_Xpath, LocatorIdentifier.Xpath, parentOption));
        robotActions.pressRightArrowKey();
        winActions.click(getByLocator(dicomViewerPageOR.propertyTreeItem_Xpath, LocatorIdentifier.Xpath, childOption));
    }

    public void selectOptionInActiveTab(String option) {
        winActions.click(getByLocator(dicomViewerPageOR.tabLabel_Xpath, LocatorIdentifier.Xpath, option));
    }

    public void clickButtonInActiveTab(String button, int number) {
        for (int count = 0; count < number; count++) {
            winActions.click(getByLocator(dicomViewerPageOR.actionButton_Xpath, LocatorIdentifier.Xpath, button));
        }
    }

    public void setCineSpeed(String speed) {
        switch (speed.trim()) {
            case "1:10":
                sikuliActions.click(DicomViewerElements + "CineOptionMenuExpander.PNG", 0.5f);
                Common.sleep(1, TimeUnit.SECONDS);
                sikuliActions.click(DicomViewerElements + "CineSpeed1_10Option.PNG", 0.5f);
                break;
            case "1:20":
                sikuliActions.click(DicomViewerElements + "CineOptionMenuExpander.PNG", 0.5f);
                Common.sleep(1, TimeUnit.SECONDS);
                sikuliActions.click(DicomViewerElements + "CineSpeed1_20Option.PNG", 0.5f);
                break;
        }
    }

    public String getFontValue() {
        return winActions
                .getTextFromTextbox(getByLocator(dicomViewerPageOR.fontTextbox_Xpath, LocatorIdentifier.Xpath));
    }

    public String getFontSizeValue() {
        return winActions
                .getTextFromTextbox(getByLocator(dicomViewerPageOR.fontSizeTextbox_Xpath, LocatorIdentifier.Xpath));
    }

    public void clickChangeFontButton() {
        winActions.click(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.changeFontButton_Xpath, LocatorIdentifier.Xpath));
    }

    public void setFontType(String fontType) {
        winActions.selectOptionFromList(getByLocator(dicomViewerPageOR.fontListBox_Xpath, LocatorIdentifier.Xpath),
                fontType);
    }

    public void setFontSize(String fontSize) {
        winActions.selectOptionFromList(getByLocator(dicomViewerPageOR.fontSizeListBox_Xpath, LocatorIdentifier.Xpath),
                fontSize);
    }

    public boolean isTextAnnotationDisplayedWithFont(String fontType, String fontSize) {
        sikuliActions.waitForElementToExist(
                DicomViewerElements + "Text Measurement " + fontType + " " + fontSize + ".PNG", 10);
        return sikuliActions
                .isElementVisible(DicomViewerElements + "Text Measurement " + fontType + " " + fontSize + ".PNG");
    }

    public void selectUnitOfMeasurement(String unitOfMeasurement) {
        winActions.click(getByLocator(dicomViewerPageOR.unitsOfMeasurementRadioButton_Xpath, LocatorIdentifier.Xpath,
                unitOfMeasurement));
    }

    public boolean isMeasurementDisplayedInSelectedUnit(String unitOfMeasurement) {
        return sikuliActions.isElementVisible(DicomViewerElements + unitOfMeasurement + ".PNG");
    }

    public boolean isSignedReportDisplayedUnderReportsTab() {
        Common.sleep(3, TimeUnit.SECONDS);
        return sikuliActions.isElementVisible(DicomViewerElements + "Signed Report.PNG");
    }

    public void amendReport(String patternFile) {
        sikuliActions.rightClickExact(DicomViewerElements + patternFile + ".PNG");
        performOperationOnReport("AMEND");
    }

    public void viewAmendedReport() {
        sikuliActions.rightClickExact(DicomViewerElements + "Signed Report.PNG");
        performOperationOnReport("View");
        // sikuliActions.click(DicomViewerElements + "ViewMenuItem.png");
    }

    public boolean isPrinterIconVisible() {
        return sikuliActions.isExactElementVisible(CommonElementDir + "Printer Icon.PNG");
    }

    public void goToNextImage() {
        robotActions.pressRightArrowKey();
    }

    public void changeDefaultRowCount(String rowCount) {
        winActions.setText(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.defaultRowCountTextbox_Xpath, LocatorIdentifier.Xpath), rowCount);
    }

    public void changeDefaultColumnCount(String columnCount) {
        winActions.setText(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.defaultColumnCountTextbox_Xpath, LocatorIdentifier.Xpath), columnCount);
    }

    public int getDisplayedViewerRowCount() {
        return (int) getAllViewportElements().stream().map(viewport -> winActions.getElementBounds(viewport).y)
                .collect(Collectors.toList()).stream().distinct().count();
    }

    public int getDisplayedViewerColumnCount() {
        return (int) getAllViewportElements().stream().map(viewport -> winActions.getElementBounds(viewport).x)
                .collect(Collectors.toList()).stream().distinct().count();
    }

    public String getDefaultRowCount() {
        return winActions.getTextFromTextbox(
                getByLocator(dicomViewerPageOR.defaultRowCountTextbox_Xpath, LocatorIdentifier.Xpath));
    }

    public String getDefaultColumnCount() {
        return winActions.getTextFromTextbox(
                getByLocator(dicomViewerPageOR.defaultColumnCountTextbox_Xpath, LocatorIdentifier.Xpath));
    }

    public void maximizeViewer() {
        Log.printInfo("maximizing viewer");
        WebElement titleBar = winActions.findWindowElementBy(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.viewerTitleBar_Xpath, LocatorIdentifier.Xpath));
        try {
            winActions.click(titleBar,
                    getByLocator(dicomViewerPageOR.viewerMaximizeButton_Name, LocatorIdentifier.Name));
        } catch (Exception e) {
            Log.printError("Maximize button not found");
        }
        Log.printInfo("Viewer maximized");
    }

    public void rightClickOnReportFromList(String reportName) {
        String name = DicomViewerElements + reportName + ".PNG";
        sikuliActions.hover(name);
        sikuliActions.rightClick(name);

    }

    public boolean isReportModificationWindowVisible() {
        return winActions
                .isVisible(getByLocator(dicomViewerPageOR.reportModificationPopUp_Xpath, LocatorIdentifier.Xpath));
    }

    public void clickOnModificationPopUpButton(String buttonName) {
        winActions.click(getByLocator(dicomViewerPageOR.actionButton_Xpath, LocatorIdentifier.Xpath, buttonName));
    }

    public void rightClickOnReport(String reportName, int index) {
        String name = DicomViewerElements + reportName + ".PNG";
        sikuliActions.rightClickOnElementAtIndex(name, index);
        Common.sleep(1, TimeUnit.SECONDS);
    }

    public void viewReportOptions(String reportName) {
        rightClickOnReport(reportName, 0);
    }

    public boolean isReportOptionDisplayed(String reportOption) {
        return sikuliActions.isExactElementVisible(DicomViewerElements + reportOption + ".png");
    }

    public void selectReportOption(String reportOption) {
        Common.sleep(2, TimeUnit.SECONDS);
        sikuliActions.click(DicomViewerElements + reportOption + ".png");
    }

    public void clickOnViewport(int viewportIndex) {
        WebElement viewportElement = getViewportByIndex(viewportIndex);
        winActions.click(viewportElement);
    }

    public void sortReportsInDescendingDateOrder() {
        winActions.waitForWindowElementToBeVisible(
                getByLocator(dicomViewerPageOR.reportsTab_Xpath, LocatorIdentifier.Xpath), 30);
        WebElement reportsTabWindow = winActions
                .findWindowElementBy(getByLocator(dicomViewerPageOR.reportsTab_Xpath, LocatorIdentifier.Xpath));
        Rectangle reportsWindow = winActions.getElementBounds(reportsTabWindow);
        if (sikuliActions.isExactElementVisible(reportsWindow, DicomViewerElements + "Date Sorter.PNG"))
            sikuliActions.click(reportsWindow, DicomViewerElements + "Date Sorter.PNG");
        Common.sleep(1, TimeUnit.SECONDS);
    }

    public void moveCineSliderToEndPosition(int viewportIndex) {
        WebElement viewport = getViewportByIndex(viewportIndex);
        Rectangle viewportRegion = winActions.getElementBounds(viewport);
        try {
            sikuliActions.dragElementToOffset(viewportRegion, DicomViewerElements + "CineSlider.PNG", 500, 0);
        } catch (Exception e) {
            sikuliActions.dragElementToOffset(viewportRegion, DicomViewerElements + "CineSliderStartPoint.png", 500, 0);
            e.printStackTrace();
        }
    }

    public void clickButtonOnRestartViewerModal(String buttonName) {
        if (winActions
                .isVisible(getByLocator(dicomViewerPageOR.actionButton_Xpath, LocatorIdentifier.Xpath, buttonName))) {
            winActions.click(getByLocator(dicomViewerPageOR.actionButton_Xpath, LocatorIdentifier.Xpath, buttonName));
        }
    }

    public boolean isPrinterIconDisplayed() {
        return sikuliActions.isElementVisible(DicomViewerElements + "Printer Icon.PNG");
    }

    public void selectReportPrintPreview() {
        WebElement Dialog = winActions.findWindowElementBy(
                getByLocator(dicomViewerPageOR.printSelectedImagesDialogBox_Name, LocatorIdentifier.Name));
        winActions.click(Dialog, getByLocator(dicomViewerPageOR.previewButton_Name, LocatorIdentifier.Name));
    }

    public void setLeftPanelWidth(int widthPercent) {
        WebElement slider = winActions.findWindowElementBy(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.leftPanelSizeSlider_Xpath, LocatorIdentifier.Xpath));
        WebElement sliderThumb = winActions.findWindowElementBy(slider,
                getByLocator(dicomViewerPageOR.leftPanelSizeSliderThumb_Xpath, LocatorIdentifier.Xpath));
        sliderThumb.click();
        robotActions.pressHomeKey();
        for (int percent = 0; percent <= widthPercent; percent++) {
            robotActions.pressRightArrowKey();
            Common.sleep(100, TimeUnit.MILLISECONDS);
        }
    }

    public double getLeftPanelSizeInPercent() {
        WebElement viewerPerspective = winActions.findWindowElementBy(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.viewerPerspective_Name, LocatorIdentifier.Name));
        double viewerWidth = winActions.getElementBounds(viewerPerspective).width;
        WebElement studyListTab = winActions.findWindowElementBy(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.leftPanelStudyListPaneOnViewerWindow_Xpath, LocatorIdentifier.Xpath));
        double leftPanelWidth = winActions.getElementBounds(studyListTab).width;
        double widthPercentage = (leftPanelWidth / viewerWidth) * 100;
        return Math.round(widthPercentage);
    }

    public boolean areViewportsDisplayedAsPerSelectedLayout(Integer numberOfViewports) {
        return getDisplayedViewerRowCount() * getDisplayedViewerColumnCount() == numberOfViewports;
    }


    public void rightClickOnViewport(int viewportIndex) {
        winActions.rightClick(getViewportByIndex(viewportIndex));
    }

    public BufferedImage getImageOfViewport(int viewportIndex) {
        WebElement viewportElement = getViewportByIndex(viewportIndex);
        Rectangle elementBounds = winActions.getElementBounds(viewportElement);
        return sikuliActions.getImageWithinBounds(elementBounds);
    }

    public void selectOptionFromRightClickMenu(String option) {
        winActions.waitForWindowElementToBeVisible(getByLocator(dicomViewerPageOR.viewerMenuOption_Xpath, LocatorIdentifier.Xpath, option), 10);
        winActions.click(getByLocator(dicomViewerPageOR.viewerMenuOption_Xpath, LocatorIdentifier.Xpath, option));
    }

    public void selectOptionFromViewerRightClickMenu(String option) {
        switch (option.toUpperCase()) {
            case "TOGGLE IMAGE SELECTION (FOR REPORT)":
                sikuliActions.type("T");
                break;
            case "SELECT ALL STILL IMAGES (FOR REPORT)":
                sikuliActions.type("S");
                break;
            case "UNSELECT ALL IMAGES (FOR REPORT)":
                sikuliActions.type("U");
                break;
            case "TOGGLE IMAGE SELECTION (FOR VIEWING)":
                sikuliActions.type("T");
                sikuliActions.type("T");
                break;
            case "UNSELECT ALL IMAGES (FOR VIEWING)":
                sikuliActions.type("U");
                sikuliActions.type("U");
                break;
            case "TOGGLE IMAGE SELECTION (FOR EXPORT/PRINT)":
                sikuliActions.type("T");
                sikuliActions.type("T");
                sikuliActions.type("T");
                break;
            case "UNSELECT ALL IMAGES (FOR EXPORT/PRINT)":
                sikuliActions.type("U");
                sikuliActions.type("U");
                sikuliActions.type("U");
                break;
            case "CLEAR ALL MEASUREMENTS":
                sikuliActions.type("C");
                break;
            case "CLEAR SELECTED MEASUREMENTS":
                sikuliActions.type("C");
                sikuliActions.type("C");
                break;
            case "SAVE":
                sikuliActions.type("S");
                sikuliActions.type("S");
                break;
            default:
                selectOptionFromRightClickMenu(option);
        }
        Common.sleep(1, TimeUnit.SECONDS);
        robotActions.enter();
    }

    public void scrollByButton(String option, int clickCount) {
        for (int i = 1; i <= clickCount; i++) {
            winActions.click(dicomViewerWindow,
                    getByLocator(dicomViewerPageOR.actionButton_Xpath, LocatorIdentifier.Xpath, option));
        }
    }

    public int getImageNumber() {
        String imageNumberLabelText = winActions.getText(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.dicomImageNumberLabel_Xpath, LocatorIdentifier.Xpath));
        return Integer.parseInt(imageNumberLabelText.split("/")[0].split("#")[1].trim());
    }

    public void scrollMouseWheel(String option, int clickCount) {
        sikuliActions.moveMouseByOffset(0, 50);
        robotActions.scrollMouseWheel(option.equalsIgnoreCase("upwards") ? -clickCount : clickCount);
    }

    public void scrollByScrollBar(String option, int clickCount) {
        Rectangle scrollBarBounds = winActions.getElementBounds(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.scrollbar_Xpath, LocatorIdentifier.Xpath));
        for (int i = 1; i <= clickCount; i++) {
            if (option.equalsIgnoreCase("downwards")) {
                robotActions.mouseMove(scrollBarBounds.x + 5, scrollBarBounds.y + scrollBarBounds.height - 5);
            } else {
                robotActions.mouseMove(scrollBarBounds.x + 5, scrollBarBounds.y + 5);
            }
            sikuliActions.click();
        }
    }

    public void dragLabelOverOtherLabel(String labelName) {
        sikuliActions.dragExactElementToOffset(CommonElementDir + labelName + "TextField.PNG", 0, -20);
        sikuliActions.waitForElementInvisibility(CommonElementDir + "LoadingIndicator.PNG", 5);
    }

    public boolean isReportTemplatePostProcessingEditorPresentOnDicomViewer() {
        sikuliActions.waitForElementToExist(USER_WORK_DIR + ReportScreens + "\\ReportScreen\\ReportGUI.PNG", 10);
        return sikuliActions.isElementVisible(USER_WORK_DIR + ReportScreens + "\\ReportScreen\\ReportGUI.PNG");
    }

    public void deleteLabelFromReportTemplatePostProcessingEditor(String labelName) {
        sikuliActions.hover(USER_WORK_DIR + ReportScreens + "OBConsultationFinalReport\\" + labelName + ".PNG");
        sikuliActions.click(CommonElementDir + "DeleteButton.PNG");
        sikuliActions.waitForElementInvisibility(CommonElementDir + "LoadingIndicator.PNG", 5);
    }

    public void selectTabOnDicomViewer(String tabName) {
        winActions.click(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.dicomViewerTab_Xpath, LocatorIdentifier.Xpath, tabName));
    }

    public void removeDeleteProcessorTag() {
        sikuliActions.moveToElement(CommonElementDir + "DeleteProcessorTag.PNG");
        for (int i = 0; i < 3; i++) {
            sikuliActions.click();
        }
        robotActions.enter();
    }

    public void clickNuclearStudyToolBarButton(String button) {
        winActions.hoverOnElement(getByLocator(dicomViewerPageOR.actionButton_Xpath, LocatorIdentifier.Xpath, button));
        Common.sleep(1, TimeUnit.SECONDS);
        winActions.click(getByLocator(dicomViewerPageOR.actionButton_Xpath, LocatorIdentifier.Xpath, button));
    }

    public boolean isContourLinePresentOnImage() {
        if (winActions.isVisible(
                getByLocator(dicomViewerPageOR.loadingResultsProgressBar_Xpath, LocatorIdentifier.Xpath), 10))
            winActions.waitForWindowElementToBeInvisible(
                    getByLocator(dicomViewerPageOR.loadingResultsProgressBar_Xpath, LocatorIdentifier.Xpath), 300);
        return sikuliActions.isSimilarElementVisible(DicomViewerElements + "Contour Images.PNG", 0.9f);
    }

    public void clickNuclearStudyToolBarMenuItem(String menu) {
        winActions.click(getByLocator(dicomViewerPageOR.viewerMenuOption_Xpath, LocatorIdentifier.Xpath, menu));
    }

    public boolean areImagesProcessing() {
        return winActions.isVisible(getByLocator(dicomViewerPageOR.progressBar_ClassName, LocatorIdentifier.ClassName),
                60);
    }

    public boolean isNumberPresentOnImage() {
        return sikuliActions.isElementVisible(DicomViewerElements + "Numbered Image.PNG");
    }

    public boolean isNumberNotPresentOnImage() {
        return sikuliActions.isElementVisible(DicomViewerElements + "Non Numbered Image.PNG");
    }

    public boolean isViewerClosed() {
        return winActions.findWindowElementsBy(dicomViewer).size() == 0;
    }

    public void clickShowPreviewAndImagesButtonInSingleMonitorView(String buttonName) {
        winActions.click(getByLocator(dicomViewerPageOR.tabLabel_Xpath, LocatorIdentifier.Xpath, buttonName));
        sikuliActions.wait(5.0);
    }

    public void drawMeasurementOnImageUsingBullseye(String labelName) {
        Rectangle elementBounds = winActions.getElementBounds(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.viewportPane_Xpath, LocatorIdentifier.Xpath));
        sikuliActions.moveToCenterOfElementBounds(getViewportBoundsByIndex(0));
        switch (labelName) {
            case "Asc Ao Dia (2D-Mode)":
            case "LVOT Diameter (2D)":
            case "AoV Ann d":
            case "LVOT Diam(2D)":
            case "LVOT Diam(M-Mode)":
            case "RVIDd(M-Mode)":
                sikuliActions.dragMouseOnElementBounds(elementBounds, 120, 0);
                break;
            case "Ao Root Dia":
                sikuliActions.dragMouseOnElementBounds(elementBounds, 0, 120);
                break;
            case "PISA-Radius":
            case "PV Ann d":
                sikuliActions.dragMouseOnElementBounds(elementBounds, 120, 80);
                break;
            case "AV VTI":
                sikuliActions.dragMouseOnElementBounds(elementBounds, 80, 0);
                break;
            case "PDA Sys VTI":
                sikuliActions.moveMouseByOffset(0, 80);
                sikuliActions.dragMouseByOffset(80, 0);
                break;
            case "LVPWd(M-Mode)":
                sikuliActions.moveMouseByOffset(0, -160);
                sikuliActions.dragMouseByOffset(120, 0);
                break;
        }
    }

    public boolean isTabSelectedForNuclearStudy(String tabName) {
        sikuliActions.waitForElementToExist(DicomViewerElements + tabName + " Tab.PNG", 10);
        return sikuliActions.isElementVisible(DicomViewerElements + tabName + " Tab.PNG");
    }

    public void clickScrollBarButton(int clickCount, String arrowButton) {
        WebElement viewportPaneWindow = winActions
                .findWindowElementBy(getByLocator(dicomViewerPageOR.viewportPane_Xpath, LocatorIdentifier.Xpath));
        List<WebElement> scrollBarList = winActions.findWindowElementsBy(viewportPaneWindow,
                getByLocator(dicomViewerPageOR.scrollbar_Xpath, LocatorIdentifier.Xpath));
        Rectangle scrollBarBounds = winActions.getElementBounds(scrollBarList.get(0));
        for (int i = 1; i <= clickCount; i++) {
            if (arrowButton.equalsIgnoreCase("next")) {
                robotActions.mouseMove(scrollBarBounds.x + scrollBarBounds.width - 10, scrollBarBounds.y + 10);
            } else {
                robotActions.mouseMove(scrollBarBounds.x + 10, scrollBarBounds.y + 10);
            }
            sikuliActions.click();
        }
    }

    public void moveWhiteLineOfImage(String linePattern) {
        sikuliActions.dragElementToOffset(getNuclearViewportBounds(), DicomViewerElements + linePattern + "marker.PNG",
                0, 40);
    }

    public void dragImageBoxUpwards() {
        sikuliActions.dragMouseOnElementBounds(getNuclearViewportBounds(), 0, -100);
    }

    public Rectangle getViewportBoundsByIndex(int viewportIndex) {
        return winActions.getElementBounds(getViewportByIndex(viewportIndex));
    }

    public boolean isImageChanged(Rectangle elementBounds, BufferedImage bufferedImage) {
        return sikuliActions.isElementVisible(elementBounds, bufferedImage, 1.0f);
    }

    public void scrollToImageInSecondaryCapture(int imageNumber) {
        sikuliActions.moveToCenterOfElementBounds(winActions.getElementBounds(getViewportByIndex(0)));
        sikuliActions.click();
        scrollToImage(imageNumber);
    }

    public void dragMouseByOffset(String direction) {
        Rectangle elementBounds = winActions.getElementBounds(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.viewportPane_Xpath, LocatorIdentifier.Xpath));
        switch (direction.toUpperCase()) {
            case "UP":
            case "TOP":
                sikuliActions.dragMouseByOffset(elementBounds, 0, -100, false);
                break;
            case "DOWN":
            case "BOTTOM":
                sikuliActions.dragMouseByOffset(elementBounds, 0, 100, false);
                break;
            case "LEFT":
                sikuliActions.dragMouseByOffset(elementBounds, -100, 0, false);
                break;
            case "RIGHT":
                sikuliActions.dragMouseByOffset(elementBounds, 100, 0, false);
                break;
        }
        Common.sleep(2, TimeUnit.SECONDS);
    }

    public void selectOptionFromRightClickMenuOnViewports(String option, int viewportIndex) {
        for (int i = 0; i < viewportIndex; i++) {
            sikuliActions.moveToCenterOfElementBounds(getViewportBoundsByIndex(i));
            winActions.rightClick(getViewportByIndex(i));
            winActions.click(getByLocator(dicomViewerPageOR.viewerMenuOption_Xpath, LocatorIdentifier.Xpath, option));
        }
    }

    public boolean isExportIconVisibleOnViewports(String iconFile, int viewportIndex) {
        boolean result = false;
        for (int i = 0; i <= viewportIndex; i++) {
            result = sikuliActions.isElementVisible(winActions.getElementBounds(getViewportByIndex(i)),
                    DicomViewerElements + iconFile + ".PNG");
        }
        return result;
    }

    public boolean isAnonymizationWindowVisible() {
        winActions.waitForWindowElementToBeVisible(
                getByLocator(dicomViewerPageOR.anonymizationWindow_Xpath, LocatorIdentifier.Xpath), 10);
        anonymizationWindow = winActions.findWindowElementBy(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.anonymizationWindow_Xpath, LocatorIdentifier.Xpath));
        return winActions.isVisible(getByLocator(dicomViewerPageOR.anonymizationWindow_Xpath, LocatorIdentifier.Xpath));
    }

    public void maskPatientDetailsArea() {
        Rectangle imageBounds = winActions.getElementBounds(anonymizationWindow,
                getByLocator(dicomViewerPageOR.viewportCanvas_Xpath, LocatorIdentifier.Xpath));
        robotActions.mouseMove(imageBounds.x, imageBounds.y);
        sikuliActions.dragMouseByOffset(imageBounds.width, 50);
    }

    public boolean isSubsequentImagesAreMaskedOnAnonymizationWindow(BufferedImage bufferedImage) {
        boolean result;
        result = sikuliActions.isElementVisible(getMaskedAreaBoundsInAnonymizationWindow(), bufferedImage);
        for (int i = 1; i < getImageCount(); i++) {
            winActions.click(anonymizationWindow,
                    getByLocator(dicomViewerPageOR.anonymizationWindowNextImageButton_Xpath, LocatorIdentifier.Xpath));
            result = sikuliActions.isElementVisible(getMaskedAreaBoundsInAnonymizationWindow(), bufferedImage);
        }
        return result;
    }

    public Rectangle getMaskedAreaBoundsInAnonymizationWindow() {
        Rectangle imageBounds = winActions.getElementBounds(anonymizationWindow,
                getByLocator(dicomViewerPageOR.viewportCanvas_Xpath, LocatorIdentifier.Xpath));
        return winActions.getLeftTopRegionFromBounds(imageBounds, imageBounds.width, 50);
    }

    public BufferedImage getPatientInformationBufferedImage() {
        return sikuliActions.getImageWithinBounds(getMaskedAreaBoundsInAnonymizationWindow());
    }

    public boolean isPatientInformationIsMaskedOnAnonymizationWindow(BufferedImage capturedPatientInformationImage) {
        return sikuliActions.isElementVisible(getMaskedAreaBoundsInAnonymizationWindow(),
                capturedPatientInformationImage);
    }

    public void exportAnonymizedImagesToFileLocation(String option) {
        Common.createDirectory(USER_WORK_DIR + "\\src\\test\\resources\\Masked Images");
        String imageType = option.split("\\s")[2];
        WebElement SaveAsWindow = winActions.findWindowElementBy(
                getByLocator(dicomViewerPageOR.fileDialogSaveWindow_Xpath, LocatorIdentifier.Xpath));
        winActions.setText(SaveAsWindow,
                getByLocator(dicomViewerPageOR.importFileDialogFileNameTextbox_Xpath, LocatorIdentifier.Xpath),
                USER_WORK_DIR + "\\src\\test\\resources\\Masked Images\\MaskedImage." + imageType);
        winActions.click(getByLocator(dicomViewerPageOR.fileDialogSaveButton_Xpath, LocatorIdentifier.Xpath));
        Common.sleep(2, TimeUnit.SECONDS);
        winActions.click(anonymizationWindow,
                getByLocator(dicomViewerPageOR.anonymizationWindowFinishButton_Xpath, LocatorIdentifier.Xpath));
        Common.sleep(5, TimeUnit.SECONDS);
    }

    public boolean isExportedImagesAreMasked(BufferedImage bufferedimage, int viewportIndex, String option) {
        String imageType = option.split("\\s")[2];
        Common.openImagesInSystemImageViewer(USER_WORK_DIR + "\\src\\test\\resources\\Masked Images\\MaskedImage-000"
                + viewportIndex + "." + imageType);
        if (viewportIndex == 1) {
            winActions.waitForWindowElementToBeVisible(
                    getByLocator(dicomViewerPageOR.imageViewerTaskbarIcon_Xpath, LocatorIdentifier.Xpath), 30);
            winActions.click(getByLocator(dicomViewerPageOR.imageViewerTaskbarIcon_Xpath, LocatorIdentifier.Xpath));
        }

        Rectangle photoViewerElementBounds = winActions.getElementBounds(winActions
                .findWindowElementBy(getByLocator(dicomViewerPageOR.imageViewer_Xpath, LocatorIdentifier.Xpath)));
        return sikuliActions.isElementVisible(photoViewerElementBounds, bufferedimage);
    }

    public void clickClosePhotoViewerButton() {
        winActions.click(getByLocator(dicomViewerPageOR.closePhotoButtonOfPhotoViewer_Xpath, LocatorIdentifier.Xpath));
    }

    public void goToPreviousImage() {
        robotActions.pressLeftArrowKey();
    }

    public void playPauseCine() {
        robotActions.pressSpaceBarKey();
    }

    public void drawLineOnImage() {
        Rectangle elementBounds = winActions.getElementBounds(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.viewportPane_Xpath, LocatorIdentifier.Xpath));
        sikuliActions.moveToCenterOfElementBounds(getViewportBoundsByIndex(0));
        sikuliActions.dragMouseOnElementBounds(elementBounds, 400, 0);
    }

    public void switchBetweenWindows() {
        robotActions.switchWindow();
        if (winActions.isVisible(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.imageNumberInDicomViewer_Xpath, LocatorIdentifier.Xpath))) {
            winActions.click(dicomViewerWindow,
                    getByLocator(dicomViewerPageOR.imageNumberInDicomViewer_Xpath, LocatorIdentifier.Xpath));
        }
    }

    public String getImageLabel() {
        Rectangle elementBounds = winActions.getElementBounds(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.viewportPane_Xpath, LocatorIdentifier.Xpath));
        Rectangle regionBounds = winActions.getMiddleRegionFromBounds(elementBounds, 400, 20);
        Rectangle textRegion = winActions.getRightBottomRegionFromBounds(regionBounds, 220, 17);
        String imageText = imageReader.getImageNumbers(sikuliActions.getImageWithinBounds(textRegion), 2);
        Log.printInfo("Image text: ");
        System.out.println("imageText->" + imageText);
        Pattern pattern = Pattern.compile("[0-9]{1,3}.[0-9]{1,2}");
        Matcher matcher = pattern.matcher(imageText);
        List<String> matchedStrings = new ArrayList<>();
        while (matcher.find()) {
            matchedStrings.add(matcher.group());
        }
        return matchedStrings.get(matchedStrings.size() - 1).trim();
    }

    public boolean isCheckboxPresentOnWindowPreferences(String checkBoxName) {
        WebElement windowPreferences = winActions
                .findWindowElementBy(getByLocator(dicomViewerPageOR.preferencesModal_Xpath, LocatorIdentifier.Xpath));
        return winActions.getElementsCount(windowPreferences, getByLocator(
                dicomViewerPageOR.rightPanelInWindowPreferences_Xpath, LocatorIdentifier.Xpath, checkBoxName)) > 0;
    }

    public void moveToNextFrameUsingKeyboardControls() {
        robotActions.pressControlKeyAndRightArrowKey();
    }

    public void moveToPreviousFrameUsingKeyboardControls() {
        robotActions.pressControlKeyAndLeftArrowKey();
    }

    public void zoomInCineImageUsingKeyboardControls() {
        robotActions.pressControlKeyAndUpArrowKey();
    }

    public void zoomOutCineImageUsingKeyboardControls() {
        robotActions.pressControlKeyAndDownArrowKey();
    }

    public void clickOnViewportFrameControlIcon(String frameButton) {
        sikuliActions.click(DicomViewerElements + frameButton + " Button.PNG");
    }

    public String getCineFrameNumberOnSecondViewport() {
        Rectangle viewportBounds = getViewportBoundsByIndex(1);
        Rectangle elementBounds = winActions.getLeftBottomRegionFromBounds(viewportBounds, 115, 70);
        Rectangle frameTextBounds = winActions
                .getLeftTopRegionFromBounds(winActions.getLeftBottomRegionFromBounds(elementBounds, 115, 55), 115, 17);
        String imageText = imageReader.getImageNumbers(sikuliActions.getImageWithinBounds(frameTextBounds), 2);
        Log.printInfo("Image text: ");
        Log.printInfo(imageText);
        Pattern pattern = Pattern.compile("[0-9]{1,3}");
        Matcher matcher = pattern.matcher(imageText);
        List<String> matchedStrings = new ArrayList<>();
        while (matcher.find()) {
            matchedStrings.add(matcher.group());
        }
        return matchedStrings.get(matchedStrings.size() - 2).trim();
    }

    public void moveToViewportCenterAndScrollMouseWheel(String option, int clickCount, int viewportIndex) {
        Rectangle viewportBounds = getViewportBoundsByIndex(viewportIndex);
        sikuliActions.moveToCenterOfElementBounds(viewportBounds);
        robotActions.scrollMouseWheel(option.equalsIgnoreCase("upwards") ? -clickCount : clickCount);
    }

    public void setCineSpeed(String speed, int viewportIndex) {
        Rectangle viewportBounds = getViewportBoundsByIndex(viewportIndex);
        switch (speed.trim()) {
            case "1:10":
                sikuliActions.click(viewportBounds, DicomViewerElements + "CineOptionMenuExpander.PNG");
                Common.sleep(1, TimeUnit.SECONDS);
                sikuliActions.click(DicomViewerElements + "CineSpeed1_10Option.PNG");
            case "1:20":
                sikuliActions.click(viewportBounds, DicomViewerElements + "CineOptionMenuExpander.PNG");
                Common.sleep(1, TimeUnit.SECONDS);
                sikuliActions.click(DicomViewerElements + "CineSpeed1_20Option.PNG");
        }
    }

    public boolean isFrameNumberLabelDisplayed() {
        return sikuliActions.isElementVisible(DicomViewerElements + "Frame Number label.png");
    }

    public void waitForImagesProcessingToBeCompleted() {
        sikuliActions.waitForElementToExist(DicomViewerElements + "Loading Results label.png", 60);
        sikuliActions.waitForElementInvisibility(DicomViewerElements + "Loading Results label.png", 60);
    }

    public Rectangle getNuclearViewportBounds() {
        WebElement nuclearViewport = winActions
                .findWindowElementBy(getByLocator(dicomViewerPageOR.nuclearViewport_Xpath, LocatorIdentifier.Xpath));
        return winActions.getElementBounds(nuclearViewport);
    }

    public BufferedImage getImageWithinNuclearViewport() {
        Rectangle nuclearViewportBounds = getNuclearViewportBounds();
        return sikuliActions.getImageWithinBounds(nuclearViewportBounds);
    }

    public boolean isErrorPopupDisplayed() {
        return winActions.isVisible(getByLocator(dicomViewerPageOR.errorPopupWindow_Xpath, LocatorIdentifier.Xpath));
    }

    public void dismissErrorPopup() {
        WebElement popupWindow = winActions
                .findWindowElementBy(getByLocator(dicomViewerPageOR.errorPopupWindow_Xpath, LocatorIdentifier.Xpath));
        winActions.findWindowElementBy(popupWindow,
                getByLocator(dicomViewerPageOR.questionDialogBoxOKButton_Name, LocatorIdentifier.Name));
    }

    public void changeDefaultPercentileCalculatingMethod(String calculatingMethod) {
        winActions.click(
                getByLocator(dicomViewerPageOR.BPDPercentileCalculationMethodDropdown_Xpath, LocatorIdentifier.Xpath));
        sikuliActions.click(DicomViewerElements + calculatingMethod + ".PNG");
    }

    public void selectDefaultCalculatingMethod() {
        winActions.click(
                getByLocator(dicomViewerPageOR.BPDPercentileCalculationMethodDropdown_Xpath, LocatorIdentifier.Xpath));
        sikuliActions.click(DicomViewerElements + "Hadlock Calculating Method.PNG");
    }

    public void selectPreviousStudy(String studyDescription) {
        WebElement studyListTabWindow = winActions
                .findWindowElementBy(getByLocator(dicomViewerPageOR.studyListTab_Xpath, LocatorIdentifier.Xpath));
        Rectangle studyListWindow = winActions.getElementBounds(studyListTabWindow);
        sikuliActions.click(studyListWindow, DicomViewerElements + studyDescription + " study.PNG");
    }

    public void amendReportWithDateTime(String reportName, String date, String time) {
        sikuliActions.waitForElementToExist(DicomViewerElements + reportName + " " + date + " " + time + ".PNG", 10);
        sikuliActions.rightClickExact(DicomViewerElements + reportName + " " + date + " " + time + ".PNG");
        performOperationOnReport("AMEND");
        acceptTheEditReportPopupIfVisible();
    }

    public void clickTabOnLeftPanel(String tabName) {
        sikuliActions.click(DicomViewerElements + tabName + " tab.PNG");
    }

    public void doubleClickOnReportInReportsTab(String reportName) {
        sikuliActions.waitForElementToExist(DicomViewerElements + reportName + ".PNG", 10);
        sikuliActions.doubleClick(DicomViewerElements + reportName + ".PNG");
    }

    public boolean isDSRTabOpenedInViewerWindow(String reportName) {
        WebElement viewerPerspective = winActions.findWindowElementBy(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.viewerPerspective_Name, LocatorIdentifier.Name));
        return winActions.isVisible(viewerPerspective,
                getByLocator(dicomViewerPageOR.dsrViewerTabReportName_Xpath, LocatorIdentifier.Xpath, reportName));
    }

    public void clickArrowInDSRTab(String arrow) {
        sikuliActions.click(DicomViewerElements + arrow + "ArrowInDSRTab.PNG");
    }

    public boolean verifyTreeItemIsUnfoldedOnDSRViewer() {
        return sikuliActions.isElementVisible(DicomViewerElements + "TreeItemUnfoldOnDSRTab.PNG");
    }

    public boolean verifyTreeItemIsCollapsedOnDSRViewer() {
        return sikuliActions.isElementVisible(DicomViewerElements + "TreeItemCollapsedOnDSRTab.PNG");
    }

    public void sortDescriptionInAscendingOrder(String studyDescription) {
        WebElement studyListTabWindow = winActions
                .findWindowElementBy(getByLocator(dicomViewerPageOR.studyListTab_Xpath, LocatorIdentifier.Xpath));
        Rectangle studyListWindow = winActions.getElementBounds(studyListTabWindow);
        if (!sikuliActions.isElementVisible(studyListWindow, DicomViewerElements + studyDescription + " study.PNG")) {
            sikuliActions.click(studyListWindow, DicomViewerElements + "Description.PNG");
        }
        sikuliActions.click(studyListWindow, DicomViewerElements + studyDescription + " study.PNG");
    }

    public boolean isOptionPresentInRightClickMenuForReport(String option) {
        sikuliActions.waitForElementToExist(DicomViewerElements + option + "MenuItem.PNG", 10);
        return sikuliActions.isElementVisible(DicomViewerElements + option + "MenuItem.PNG");
    }

    public void doubleClickOnTreeItemInDSRViewer() {
        sikuliActions.doubleClick(DicomViewerElements + "expandArrowInDSRTab.PNG");
        sikuliActions.doubleClick(DicomViewerElements + "ChildNode.PNG");
    }

    public void scrollUsingMouseWheel(String option, int clickCount) {
        robotActions.scrollMouseWheel(option.equalsIgnoreCase("upwards") ? -clickCount : clickCount);
    }

    public boolean isSRInDSRViewerScrolled() {
        return sikuliActions.isElementVisible(DicomViewerElements + "SRContent.PNG");
    }

    public void importDsrReport() {
        winActions.setText(dicomViewerWindow,
                getByLocator(dicomViewerPageOR.importFileDialogFileNameTextbox_Xpath, LocatorIdentifier.Xpath),
                Constants.DSRFile);
        winActions.click(getByLocator(dicomViewerPageOR.importFileDialogOpenButton_Xpath, LocatorIdentifier.Xpath));
    }

    public boolean verifySRNodesAreCollapsed() {
        return sikuliActions.isElementVisible(DicomViewerElements + "SrNodesCollapsed.PNG");
    }

    public boolean isRightClickMenuOptionDisabled(String menuOption) {
        return sikuliActions.isElementVisible(DicomViewerElements + menuOption + "GreyedOut.PNG");
    }

    public int getDSRTabCountInViewerWindow(String reportName) {
        return winActions.getElementsCount(
                getByLocator(dicomViewerPageOR.dsrViewerTabReportName_Xpath, LocatorIdentifier.Xpath, reportName));
    }

    public boolean isIncorrectUnitDisplayedOnImage() {
        return sikuliActions.isSimilarElementVisible(DicomViewerElements + "UnitError.png", 0.7f);
    }

    public boolean isEcgWaveformDisplayed() {
        return sikuliActions.isSimilarElementVisible(DicomViewerElements + "EcgWaveform.png", 0.4f);
    }

    public boolean isOptionPresentInReportsMenu(String menuOption) {
        return winActions
                .isVisible(getByLocator(dicomViewerPageOR.menuOption_Xpath, LocatorIdentifier.Xpath, menuOption));
    }

    public boolean isRestartRequiredDialogPresent() {
        winActions.waitForWindowElementToBeVisible(
                getByLocator(dicomViewerPageOR.viewerRestartRequiredModal_Name, LocatorIdentifier.Name), 5);
        return winActions
                .isVisible(getByLocator(dicomViewerPageOR.viewerRestartRequiredModal_Name, LocatorIdentifier.Name));
    }

    public boolean isReportIconVisible() {
        return sikuliActions.isExactElementVisible(DicomViewerElements + "Report Icon.PNG");
    }

    public void editFieldInTemplateAdministration(String fieldName, String value) {
        winActions.setText(getByLocator(dicomViewerPageOR.propertyTreeItem_Xpath, LocatorIdentifier.Xpath, fieldName),
                value);
        robotActions.enter();
    }

    public void clearTextFromTemplateAdministrationField(String fieldName) {
        winActions
                .clearText(getByLocator(dicomViewerPageOR.propertyTreeItem_Xpath, LocatorIdentifier.Xpath, fieldName));
        robotActions.enter();
    }

    public void selectSubMenuOption(String menuOption, String subMenuOption) {
        WebElement subMenuOptionsWindow = winActions.findWindowElementBy(
                getByLocator(dicomViewerPageOR.rightClickSubMenuOption_Xpath, LocatorIdentifier.Xpath, menuOption));
        winActions.click(subMenuOptionsWindow,
                getByLocator(dicomViewerPageOR.viewerMenuOption_Xpath, LocatorIdentifier.Xpath, subMenuOption));
    }

    public List<String> getRightClickMenuOptions() {
        List<WebElement> menuOptions = winActions.findWindowElementsBy(
                getByLocator(dicomViewerPageOR.rightClickMenuItems_Xpath, LocatorIdentifier.Xpath));
        List<String> menuOptionNames = new ArrayList<>();
        for (WebElement tab : menuOptions) {
            menuOptionNames.add(tab.getAttribute("Name"));
        }
        return menuOptionNames;
    }

    public boolean isExportAndPrintIconDisplayed() {
        return sikuliActions.isElementVisible(DicomViewerElements + "Export and Print Icon.PNG");
    }

    public String getViewerVersion() {
        String viewerVersion = "";
        openMenu("Help");
        selectMenuOption("About VidiStar DICOM viewer");
        winActions.waitForWindowElementToBeVisible(getByLocator(dicomViewerPageOR.aboutViewerWindow_Name, LocatorIdentifier.Name), 5);
        WebElement aboutViewerWindow = winActions.findWindowElementBy(getByLocator(dicomViewerPageOR.aboutViewerWindow_Name, LocatorIdentifier.Name));
        aboutViewerWindow.click();
        robotActions.pressTabKey();
        String[] labelTextLines = sikuliActions.selectAndGetValue().split("\n");
        for (String labelTextLine : labelTextLines) {
            if (labelTextLine.contains("Version:")) {
                viewerVersion = labelTextLine;
                break;
            }
        }
        winActions.click(aboutViewerWindow, getByLocator(dicomViewerPageOR.aboutViewerCloseButton_Name, LocatorIdentifier.Name));
        return viewerVersion;
    }
}