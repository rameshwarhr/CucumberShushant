package Actions;

import static Utilities.Constants.ReportScreens;
import static Utilities.Constants.USER_WORK_DIR;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.sikuli.script.Button;
import org.sikuli.script.Env;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

import Utilities.Common;
import Utilities.Constants;
import Utilities.Log;

@SuppressWarnings("deprecation")
public class SikuliActions {

    Screen screen;
    Pattern pattern;
    RobotActions robotActions;

    public static final String CommonElementDir = USER_WORK_DIR + ReportScreens + "Common\\";

    public SikuliActions() {
        screen = Screen.all();
        pattern = new Pattern();
        robotActions = new RobotActions();
    }

    public void click(String patternFile) {
        pattern.setFilename(patternFile);
        try {
            screen.find(pattern).click();
        } catch (FindFailed e) {
            e.printStackTrace();
        }
    }

    public void click() {
        screen.mouseDown(Button.LEFT);
        screen.mouseUp(Button.LEFT);
    }

    public void doubleClick(String patternFile) {
        pattern.setFilename(patternFile);
        try {
            screen.find(pattern).doubleClick();
        } catch (FindFailed e) {
            e.printStackTrace();
        }
    }

    public void wait(double secondsToWait) {
        screen.wait(secondsToWait);
    }

    public void clearTextFromField() {
        screen.type("a", KeyModifier.CTRL);
        screen.type(Key.BACKSPACE);
    }

    public void typeTextInField(String patternFile, String value) {
        try {
            scrollToField(patternFile);
            pattern.setFilename(patternFile);
            screen.find(pattern).click();
            screen.wait(1.0);
            clearTextFromField();
            screen.wait(1.0);
            screen.type(value);
            screen.mouseMove(10, 10);
        } catch (FindFailed e) {
            e.printStackTrace();
        }
    }

    public void typeTextInExactField(String patternFile, String value) {
        try {
            scrollToExactField(patternFile);
            pattern.setFilename(patternFile);
            screen.find(pattern).click();
            screen.wait(1.0);
            clearTextFromField();
            screen.wait(1.0);
            screen.type(value);
            screen.mouseMove(0, 10);
        } catch (FindFailed e) {
            e.printStackTrace();
        } finally {
            pattern.similar(0.70f);
        }
    }

    public void typeTextInField(Rectangle regionBounds, String patternFile, String value) {
        try {
            Region region = Region.create(regionBounds);
            pattern.setFilename(patternFile);
            region.find(pattern).click();
            screen.wait(1.0);
            clearTextFromField();
            screen.wait(1.0);
            screen.type(value);
        } catch (FindFailed e) {
            e.printStackTrace();
        }
    }

    public void typeTextInExactFieldInARegion(Rectangle regionBounds, String patternFile, String value) {
        try {
            Region region = Region.create(regionBounds);
            pattern.setFilename(patternFile).exact();
            region.find(pattern).click();
            screen.wait(1.0);
            clearTextFromField();
            screen.wait(1.0);
            screen.type(value);
        } catch (FindFailed e) {
            e.printStackTrace();
        } finally {
            pattern.similar(0.7f);
        }
    }

    public String getTextFromExactField(String patternFile) {
        pattern.setFilename(patternFile).exact();
        try {
            screen.find(pattern).click();
        } catch (FindFailed e) {
            e.printStackTrace();
        } finally {
            pattern.similar(0.7f);
        }
        screen.wait(1.0);
        screen.type("a", KeyModifier.CTRL);
        screen.wait(1.0);
        screen.type("c", KeyModifier.CTRL);
        return Env.getClipboard();
    }

    public void selectValueFromDropdown(String patternFile, String value) {
        pattern.setFilename(patternFile);
        try {
            scrollToField(patternFile);
            screen.find(pattern);
            screen.click();
            screen.wait(1.0);
            screen.write(value);
            screen.type(Key.ENTER);
            screen.type(Key.TAB);
            screen.wait(1.0);
            screen.mouseMove(0, 10);
        } catch (FindFailed e) {
            e.printStackTrace();
        }
    }

    public void selectValueFromExactDropdown(String patternFile, String value) {
        pattern.setFilename(patternFile).exact();
        try {
            scrollToExactField(patternFile);
            screen.find(pattern);
            screen.click();
            screen.wait(1.0);
            screen.write(value);
            screen.type(Key.ENTER);
            screen.type(Key.TAB);
            screen.wait(1.0);
            screen.mouseMove(0, 10);
        } catch (FindFailed e) {
            e.printStackTrace();
        } finally {
            pattern.similar(0.70f);
        }
    }

    public void selectRadio(String patternFile) {
        pattern.setFilename(patternFile);

        try {
            screen.find(pattern).click();
            screen.mouseMove(0, 10);
        } catch (FindFailed e) {
            e.printStackTrace();
        }
    }

    public void selectRadioFromRegion(String radioPatternFile, String regionPatternFile) {
        try {
            String Deselected = CommonElementDir + "DeselectedRadio.PNG";
            pattern.setFilename(radioPatternFile);
            Pattern workArea = new Pattern(regionPatternFile);
            if (screen.exists(workArea) != null) {
                Region region = screen.find(workArea);
                if (region.find(pattern).exists(new Pattern(Deselected)) != null) {
                    region.click();
                    screen.mouseMove(0, 10);
                    screen.wait(1.0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectRadioFromRegion(String radioPatternFile, Rectangle regionBounds) {
        try {
            pattern.setFilename(radioPatternFile);
            Region region = Region.create(regionBounds);
            region.find(pattern).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectExactRadioFromRegion(String radioPatternFile, Rectangle regionBounds) {
        try {
            pattern.setFilename(radioPatternFile).exact();
            Region region = Region.create(regionBounds);
            region.find(pattern).click();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pattern.similar(0.70f);
        }
    }

    public void selectCheckBox(String patternFile) {
        pattern.setFilename(patternFile);
        scrollToField(patternFile);
        try {
            screen.find(pattern).click();
        } catch (FindFailed e) {
            e.printStackTrace();
        }
        screen.mouseMove(0, 10);
    }

    public Rectangle getExactRegionBounds(String regionPatternFile) {
        Pattern region = new Pattern(regionPatternFile).exact();
        try {
            scrollToExactField(regionPatternFile);
            return screen.find(region).getRect();
        } catch (FindFailed e) {
            Log.printError("Region for " + regionPatternFile + " not found");
            e.printStackTrace();
            return null;
        } finally {
            pattern.similar(0.70f);
        }
    }

    public Rectangle getRegionBounds(String regionPatternFile) {
        Pattern region = new Pattern(regionPatternFile);
        try {
            scrollToField(regionPatternFile);
            return screen.find(region).getRect();
        } catch (FindFailed e) {
            e.printStackTrace();
            return null;
        }
    }

    public void selectValueFromDropdown(Rectangle regionBounds, String patternFile, String value) {
        pattern.setFilename(patternFile);
        Region region = Region.create(regionBounds);
        try {
            region.find(pattern).click();
            screen.wait(1.0);
            screen.write(value);
            screen.type(Key.ENTER);
            screen.type(Key.TAB);
            screen.wait(1.0);
            screen.mouseMove(0, 10);
        } catch (FindFailed e) {
            e.printStackTrace();
        }
    }

    public void selectCheckBox(String checkBoxPatternFile, Rectangle regionBounds) {
        try {
            pattern.setFilename(checkBoxPatternFile);
            Region region = Region.create(regionBounds);
            region.find(pattern).click();
            screen.mouseMove(0, 10);
            screen.wait(1.0);
        } catch (FindFailed e) {
            e.printStackTrace();
        }
    }

    public void selectCheckBox(String checkBoxPatternFile, String regionPatternFile) {
        try {
            String Unchecked = CommonElementDir + "UncheckedCheckBox.PNG";
            pattern.setFilename(checkBoxPatternFile);
            Pattern workArea = new Pattern(regionPatternFile);
            if (screen.exists(workArea) != null) {
                Region region = screen.find(workArea);
                if (region.find(pattern).exists(new Pattern(Unchecked)) != null) {
                    region.click();
                    screen.wait(1.0);

                }
            }
        } catch (FindFailed e) {
            e.printStackTrace();
        }
    }

    public byte[] getScreenshot() {
        BufferedImage image = screen.capture().getImage();
        byte[] imageData = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", byteArrayOutputStream);
            imageData = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageData;
    }

    public byte[] getElementScreenshot(Rectangle elementBounds) {
        BufferedImage image = screen.capture(elementBounds).getImage();
        byte[] imageData = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", byteArrayOutputStream);
            imageData = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageData;
    }

    public void scrollToField(String patternFile) {
        int searchCount = 0;
        pattern.setFilename(patternFile);
        if (screen.exists(pattern) == null) {
            screen.type(Key.HOME, KeyModifier.CTRL);
            screen.wait(1.0);
        }

        while (screen.exists(pattern) == null && searchCount < 5) {
            screen.type(Key.PAGE_DOWN);
            screen.wait(1.0);
            searchCount++;
        }
    }

    public void scrollToExactField(String patternFile) {
        try {
            int searchCount = 0;
            pattern.setFilename(patternFile).exact();
            if (screen.exists(pattern) == null) {
                screen.type(Key.HOME, KeyModifier.CTRL);
                screen.wait(1.0);
            }

            while (screen.exists(pattern) == null && searchCount < 5) {
                screen.type(Key.PAGE_DOWN);
                screen.wait(1.0);
                searchCount++;
            }
        } finally {
            pattern.similar(0.70f);
        }
    }

    public void waitForElementToExist(String patternFile, double retryCount) {
        double retryAttempt = 0;
        pattern.setFilename(patternFile);
        while (screen.exists(pattern) == null && retryAttempt < retryCount) {
            screen.wait(1.0);
            retryAttempt++;
        }
    }

    public boolean isElementVisible(String patternFile) {
        pattern.setFilename(patternFile);
        return screen.exists(pattern) != null;
    }

    public void dragElementToOffset(String patternFile, int xOffset, int yOffset) {
        pattern.setFilename(patternFile);
        try {
            screen.mouseMove(pattern);
            screen.mouseDown(Button.LEFT);
            screen.mouseMove(xOffset, yOffset);
            screen.mouseUp(Button.LEFT);
        } catch (FindFailed e) {
            e.printStackTrace();
            Log.printInfo("Error while dragging the element");
        }
    }

    public void dragMouseOnElementBounds(Rectangle elementBounds, int xOffset, int yOffset) {
        moveToCenterOfElementBounds(elementBounds);
        screen.wait(1.0);
        screen.mouseDown(Button.LEFT);
        screen.wait(1.0);
        screen.mouseMove(xOffset, yOffset);
        screen.wait(1.0);
        screen.mouseUp(Button.LEFT);
    }

    public void moveToCenterOfElementBounds(Rectangle elementBounds) {
        try {
            BufferedImage bufferedImage = getImageWithinBounds(elementBounds);
            pattern.setBImage(bufferedImage);
            screen.mouseMove(pattern);
        } catch (FindFailed e) {
            e.printStackTrace();
        }
    }

    public boolean isImageChanged(Rectangle elementBounds, long delayInMilliseconds) {
        try {
            BufferedImage originalImage = screen.capture(elementBounds).getImage();
            Common.sleep(delayInMilliseconds, TimeUnit.MILLISECONDS);
            Region region = Region.create(elementBounds);
            pattern.setBImage(originalImage).exact();
            boolean isImageChanged = region.exists(pattern) == null;
            Log.printInfo("Is Image Changed: " + isImageChanged);
            return isImageChanged;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            pattern.similar(0.70f);
        }
    }

    public BufferedImage getImageWithinBounds(Rectangle elementBounds) {
        return screen.capture(elementBounds).getImage();
    }

    public void hover(String patternFile) {
        pattern.setFilename(patternFile);
        try {
            screen.find(pattern).hover();
        } catch (FindFailed e) {
            e.printStackTrace();
        }
    }

    public void controlClick() {
        screen.keyDown(Key.CTRL);
        screen.wait(1.0);
        screen.mouseDown(Button.LEFT);
        screen.mouseUp(Button.LEFT);
        screen.keyUp(Key.CTRL);
    }

    public void rightClickExact(String patternFile) {
        pattern.setFilename(patternFile).exact();
        try {
            screen.find(pattern).rightClick();
        } catch (FindFailed e) {
            e.printStackTrace();
        } finally {
            pattern.similar(0.70f);
        }
    }

    public void rightClick(String patternFile) {
        pattern.setFilename(patternFile);
        try {
            screen.find(pattern).rightClick();
        } catch (FindFailed e) {
            e.printStackTrace();
        }
    }

    public boolean isExactElementVisible(String patternFile) {
        try {
            pattern.setFilename(patternFile).exact();
            return screen.exists(pattern) != null;
        } finally {
            pattern.similar(0.70f);
        }
    }

    public boolean isExactElementVisible(Rectangle elementBounds, String patternFile) {
        try {
            Region region = Region.create(elementBounds);
            pattern.setFilename(patternFile).exact();
            return region.exists(pattern) != null;
        } finally {
            pattern.similar(0.70f);
        }
    }

    public void hoverExact(String patternFile) {
        pattern.setFilename(patternFile).exact();
        try {
            screen.find(pattern).hover();
        } catch (FindFailed e) {
            e.printStackTrace();
        } finally {
            pattern.similar(0.70f);
        }
    }

    public void moveToElement(String patternFile) {
        pattern.setFilename(patternFile);
        try {
            Region region = screen.find(pattern);
            screen.mouseMove(region);
        } catch (FindFailed e) {
            e.printStackTrace();
        }
    }

    public void moveMouseByOffset(int xOffset, int yOffset) {
        screen.mouseMove(xOffset, yOffset);
    }

    public void dragMouseByOffset(int xOffset, int yOffset, boolean releaseMouse) {
        if (!releaseMouse) {
            screen.mouseDown(Button.LEFT);
        }
        screen.mouseMove(xOffset, yOffset);
        if (releaseMouse) {
            screen.mouseUp(Button.LEFT);
        }
    }

    public void waitForElementInvisibility(String patternFile, double timeoutInSeconds) {
        pattern.setFilename(patternFile);
        screen.waitVanish(pattern, timeoutInSeconds);
    }

    public void rightClickOnElementAtIndex(String patternFile, int index) {
        try {
            pattern.setFilename(patternFile);
            Iterator<Match> matches = screen.findAll(pattern);
            List<Match> matchList = new ArrayList<>();
            matches.forEachRemaining(matchList::add);
            screen.rightClick(matchList.get(index));
        } catch (FindFailed e) {
            e.printStackTrace();
        }
    }

    public void rightClick() {
        screen.rightClick();
    }

    public void type(String textToType) {
        screen.type(textToType);
    }

    public void scrollToExactField(String patternFile, int retryAttempts) {
        try {
            int retryCount = 0;
            pattern.setFilename(patternFile).exact();
            while (screen.exists(pattern) == null && retryCount < retryAttempts) {
                screen.type(Key.PAGE_DOWN);
                screen.wait(2.0);
                retryCount++;
            }
        } finally {
            pattern.similar(0.70f);
        }
    }

    public void click(Rectangle elementBounds, String patternFile) {
        pattern.setFilename(patternFile);
        try {
            Region region = Region.create(elementBounds);
            region.find(pattern).click();
        } catch (FindFailed e) {
            e.printStackTrace();
        }
    }

    public void dragElementToOffset(Rectangle elementBounds, String patternFile, int xOffset, int yOffset) {
        pattern.setFilename(patternFile);
        try {
            Region region = Region.create(elementBounds);
            region.mouseMove(pattern);
            region.mouseDown(Button.LEFT);
            region.mouseMove(xOffset, yOffset);
            region.mouseUp(Button.LEFT);
        } catch (FindFailed e) {
            e.printStackTrace();
            Log.printInfo("Error while dragging the element");
        }
    }

    public void releaseMouse() {
        screen.mouseUp(Button.LEFT);
    }

    public boolean isElementVisible(BufferedImage bufferedImage, float similarity) {
        pattern.setBImage(bufferedImage).similar(similarity);
        return screen.exists(pattern) != null;
    }

    public boolean isElementVisible(Rectangle elementBounds, String patternFile) {
        pattern.setFilename(patternFile);
        Region region = Region.create(elementBounds);
        return region.exists(pattern) != null;
    }

    public void dragExactElementToOffset(String patternFile, int xOffset, int yOffset) {
        pattern.setFilename(patternFile).exact();
        try {
            screen.mouseMove(pattern);
            screen.mouseDown(Button.LEFT);
            screen.mouseMove(xOffset, yOffset);
            screen.mouseUp(Button.LEFT);
        } catch (FindFailed e) {
            e.printStackTrace();
            Log.printInfo("Error while dragging the element");
        } finally {
            pattern.similar(0.70f);
        }
    }

    public boolean isSimilarElementVisible(String patternFile, float similarity) {
        pattern.setFilename(patternFile).similar(similarity);
        return screen.exists(pattern) != null;
    }

    public boolean isElementVisible(Rectangle elementBounds, BufferedImage bufferedImage) {
        try {
            pattern.setBImage(bufferedImage).exact();
            Region region = Region.create(elementBounds);
            return region.exists(pattern) != null;
        } finally {
            pattern.similar(0.70f);
        }
    }

    public boolean isElementVisible(Rectangle elementBounds, BufferedImage bufferedImage, float similarity) {
        pattern.setBImage(bufferedImage).similar(similarity);
        Region region = Region.create(elementBounds);
        return region.exists(pattern) != null;
    }

    public void dragMouseByOffset(Rectangle elementBounds, int xOffset, int yOffset, boolean releaseMouse) {
        moveToCenterOfElementBounds(elementBounds);
        if (!releaseMouse) {
            screen.mouseDown(Button.LEFT);
        }
        screen.mouseMove(xOffset, yOffset);
        if (releaseMouse) {
            screen.mouseUp(Button.LEFT);
        }
    }

    public void waitForExactElementToExist(String patternFile, double retryCount) {
        try {
            double retryAttempt = 0;
            pattern.setFilename(patternFile).exact();
            while (screen.exists(pattern) == null && retryAttempt < retryCount) {
                screen.wait(1.0);
                retryAttempt++;
            }
        } finally {
            pattern.similar(0.70f);
        }
    }

    public void dragMouseByOffset(int xOffset, int yOffset) {
        screen.wait(1.0);
        screen.mouseDown(Button.LEFT);
        screen.mouseMove(xOffset, yOffset);
        screen.mouseUp(Button.LEFT);
    }

    public void selectMenuItemFromList(String patternFile) {
        pattern.setFilename(patternFile);
        while (screen.exists(pattern) == null) {
            try {
                screen.find(new Pattern(
                        Constants.USER_WORK_DIR + "\\src\\test\\resources\\DicomViewerScreenElements\\MenuDropDown.PNG")
                        .exact())
                        .click();
            } catch (FindFailed e) {
                e.printStackTrace();
            }
        }
        try {
            screen.click(patternFile);
        } catch (FindFailed e) {
            e.printStackTrace();
        } finally {
            pattern.similar(0.70f);
        }
    }

    public String selectAndGetValue() {
        screen.wait(1.0);
        screen.type("a", KeyModifier.CTRL);
        screen.wait(1.0);
        screen.type("c", KeyModifier.CTRL);
        return Env.getClipboard();
    }

    public void dragElementToTargetedLocation(String sourcePatternFile, String targetPatternFile) {
        try {
            screen.find(sourcePatternFile);
            screen.find(targetPatternFile);
            screen.dragDrop(sourcePatternFile, targetPatternFile);
        } catch (FindFailed e) {
            e.printStackTrace();
        }
    }

    public void waitForElementToExist(String patternFile, float similarity, double retryCount) {
        double retryAttempt = 0;
        pattern.setFilename(patternFile).similar(similarity);
        while (screen.exists(pattern) == null && retryAttempt < retryCount) {
            screen.wait(1.0);
            retryAttempt++;
        }
    }

    public void click(String patternFile, float similarity) {
        pattern.setFilename(patternFile).similar(similarity);
        try {
            screen.find(pattern).click();
        } catch (FindFailed e) {
            e.printStackTrace();
        }
    }

    public boolean isElementVisible(String regionPatternFile, String fieldPatternFile) {
        boolean isElementPresent = false;
        Pattern workArea = new Pattern(regionPatternFile);
        Pattern pattern = new Pattern(fieldPatternFile);
        if (screen.exists(workArea) != null) {
            try {
                Region region = screen.find(workArea);
                Region field = screen.find(pattern);
                isElementPresent = region.contains(field);
            } catch (FindFailed e) {
                Log.printError("Unable to detect the region");
            }
        }
        return isElementPresent;
    }

    public void pasteTextIntoField(String value) {
        Env.setClipboard(value);
        screen.wait(1.0);
        screen.type("v", KeyModifier.CTRL);
    }

    public void selectExactCheckBoxFromRegion(String checkBoxPatternFile, Rectangle regionBounds) {
        try {
            pattern.setFilename(checkBoxPatternFile).exact();
            Region region = Region.create(regionBounds);
            region.find(pattern).click();
            screen.mouseMove(0, 10);
            screen.wait(1.0);
        } catch (FindFailed e) {
            e.printStackTrace();
        }
    }

    public void click(Rectangle elementBounds, String patternFile, float similarity) {
        pattern.setFilename(patternFile).similar(similarity);
        try {
            Region region = Region.create(elementBounds);
            region.find(pattern).click();
        } catch (FindFailed e) {
            e.printStackTrace();
        } finally {
            pattern.similar(0.7f);
        }
    }

    public void closeCurrentChromeTab() {
        screen.type("w", KeyModifier.CTRL);
    }
}