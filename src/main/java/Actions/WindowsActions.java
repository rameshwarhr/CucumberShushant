package Actions;

import java.awt.Rectangle;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.winium.WiniumDriver;

import winium.elements.desktop.ListBox;

public class WindowsActions {

    Actions actions;
    WiniumDriver winDriver;

    public WindowsActions(WiniumDriver winDriver) {
        this.winDriver = winDriver;
    }

    public List<WebElement> findWindowElementsBy(By locator) {
        return winDriver.findElements(locator);
    }

    public List<WebElement> findWindowElementsBy(WebElement window, By locator) {
        return window.findElements(locator);
    }

    public WebElement findWindowElementBy(By locator) {
        return winDriver.findElement(locator);
    }

    public WebElement findWindowElementBy(WebElement window, By locator) {
        return window.findElement(locator);
    }

    public void waitForWindowElementToBeVisible(By locator, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(winDriver, timeoutInSeconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForWindowElementToBeVisible(WebElement element, By locator, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(winDriver, timeoutInSeconds);
        wait.until(ExpectedConditions.visibilityOf(element.findElement(locator)));
    }

    public void waitForWindowElementToBeInvisible(By locator, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(winDriver, timeoutInSeconds);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void waitForWindowElementToBeInvisible(WebElement window, By locator, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(winDriver, timeoutInSeconds);
        try {
            WebElement element = window.findElement(locator);
            wait.until(ExpectedConditions.invisibilityOf(element));
        } catch (NoSuchElementException ignored) {
        }
    }

    public void click(By locator) {
        findWindowElementBy(locator).click();
    }

    public void click(WebElement window, By locator) {
        findWindowElementBy(window, locator).click();
    }

    public void rightClick(WebElement window, By locator) {
        actions = new Actions(winDriver);
        WebElement element = findWindowElementBy(window, locator);
        actions.contextClick(element).build().perform();
    }

    public void rightClick(By locator) {
        actions = new Actions(winDriver);
        WebElement element = findWindowElementBy(locator);
        actions.contextClick(element).build().perform();
    }

    public void setText(WebElement window, By locator, String value) {
        WebElement element = findWindowElementBy(window, locator);
        element.clear();
        element.sendKeys(value);
    }

    public void setText(By locator, String value) {
        WebElement element = findWindowElementBy(locator);
        element.clear();
        element.sendKeys(value);
    }

    public void hoverOnElement(By locator) {
        actions = new Actions(winDriver);
        WebElement element = findWindowElementBy(locator);
        actions.moveToElement(element).perform();
    }

    public void hoverOnElement(WebElement window, By locator) {
        actions = new Actions(winDriver);
        WebElement element = findWindowElementBy(window, locator);
        actions.moveToElement(element).perform();
    }

    public void moveScrollbarToBottom(WebElement window, By scrollbarLocator) {
        actions = new Actions(winDriver);
        WebElement element = findWindowElementBy(window, scrollbarLocator);
        int scrollHeight = element.getSize().height;
        actions.moveToElement(element, 10, 25);
        actions.clickAndHold().moveByOffset(0, scrollHeight).release();
        actions.build().perform();
    }

    public boolean isVisible(By locator) {
        try {
            waitForWindowElementToBeVisible(locator, 10);
            return winDriver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isVisible(By locator, long timeoutInSeconds) {
        try {
            waitForWindowElementToBeVisible(locator, timeoutInSeconds);
            return winDriver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isVisible(WebElement window, By locator) {
        try {
            return window.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clearText(By locator) {
        findWindowElementBy(locator).click();
        findWindowElementBy(locator).clear();
    }

    public boolean isElementEnabled(By locator) {
        return findWindowElementBy(locator).isEnabled();
    }

    public int getElementsCount(By locator) {
        return findWindowElementsBy(locator).size();
    }

    public String getText(WebElement window, By locator) {
        WebElement element = findWindowElementBy(window, locator);
        return element.getAttribute("Name");
    }

    public Rectangle getElementBounds(WebElement window, By locator) {
        Rectangle rectangle = new Rectangle();
        WebElement element = findWindowElementBy(window, locator);
        String[] rectangleValue = element.getAttribute("BoundingRectangle").split(",");
        rectangle.x = Integer.parseInt(rectangleValue[0]);
        rectangle.y = Integer.parseInt(rectangleValue[1]);
        rectangle.width = Integer.parseInt(rectangleValue[2]);
        rectangle.height = Integer.parseInt(rectangleValue[3]);
        return rectangle;
    }

    public boolean isElementSelected(WebElement element, By locator) {
        return findWindowElementBy(element, locator).isSelected();
    }

    public Rectangle getLeftBottomRegionFromBounds(Rectangle elementBounds, int width, int height) {
        Rectangle rectangle = new Rectangle();
        rectangle.x = elementBounds.x;
        rectangle.y = (elementBounds.y + elementBounds.height) - height;
        rectangle.width = width;
        rectangle.height = height;
        return rectangle;
    }

    public Rectangle getLeftTopRegionFromBounds(Rectangle elementBounds, int width, int height) {
        Rectangle rectangle = new Rectangle();
        rectangle.x = elementBounds.x;
        rectangle.y = elementBounds.y;
        rectangle.width = width;
        rectangle.height = height;
        return rectangle;
    }

    public Rectangle getRightBottomRegionFromBounds(Rectangle elementBounds, int width, int height) {
        Rectangle rectangle = new Rectangle();
        rectangle.x = (elementBounds.x + elementBounds.width) - width;
        rectangle.y = (elementBounds.y + elementBounds.height) - height;
        rectangle.width = width;
        rectangle.height = height;
        return rectangle;
    }

    public void doubleClick(By locator, int xOffset, int yOffset) {
        actions = new Actions(winDriver);
        WebElement element = findWindowElementBy(locator);
        actions.moveToElement(element, xOffset, yOffset).doubleClick().build().perform();
    }

    public void doubleClick(WebElement element) {
        actions = new Actions(winDriver);
        actions.moveToElement(element).doubleClick().build().perform();
    }

    public Rectangle getElementBounds(WebElement element) {
        Rectangle rectangle = new Rectangle();
        String[] rectangleValue = element.getAttribute("BoundingRectangle").split(",");
        rectangle.x = Integer.parseInt(rectangleValue[0]);
        rectangle.y = Integer.parseInt(rectangleValue[1]);
        rectangle.width = Integer.parseInt(rectangleValue[2]);
        rectangle.height = Integer.parseInt(rectangleValue[3]);
        return rectangle;
    }

    public void selectOptionFromList(By listBoxLocator, String option) {
        ListBox listBox = new ListBox(winDriver.findElement(listBoxLocator));
        listBox.scrollTo(By.name(option)).click();
    }

    public String getTextFromTextbox(By textBoxLocator) {
        WebElement textbox = winDriver.findElement(textBoxLocator);
        return textbox.getText();
    }

    public void click(WebElement element) {
        actions = new Actions(winDriver);
        actions.moveToElement(element).click().build().perform();
    }

    public void rightClick(WebElement element) {
        actions = new Actions(winDriver);
        actions.contextClick(element).build().perform();
    }

    public void doubleClick(By locator) {
        actions = new Actions(winDriver);
        WebElement element = findWindowElementBy(locator);
        actions.moveToElement(element).doubleClick().build().perform();
    }

    public Rectangle getMiddleRegionFromBounds(Rectangle elementBounds, int width, int height) {
        Rectangle rectangle = new Rectangle();
        rectangle.x = elementBounds.x + elementBounds.width / 2;
        rectangle.y = elementBounds.y + elementBounds.height / 2;
        rectangle.width = width;
        rectangle.height = height;
        return rectangle;
    }

    public int getElementsCount(WebElement element, By locator) {
        return element.findElements(locator).size();
    }

    public void waitForWindowElementToBeClickable(By locator, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(winDriver, timeoutInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public String getAttribute(By locator, String attributeName) {
        return winDriver.findElement(locator).getAttribute(attributeName);
    }

    public void selectElement(WebElement window, By locator) {
        WebElement checkbox = window.findElement(locator);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void unselectElement(WebElement window, By locator) {
        WebElement checkbox = window.findElement(locator);
        if (checkbox.isSelected()) {
            checkbox.click();
        }
    }
}
