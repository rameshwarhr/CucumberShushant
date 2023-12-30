package Actions;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static Utilities.Common.sleep;

public class WebActions {

    private final WebDriver driver;

    public WebActions(WebDriver driver) {
        this.driver = driver;
    }

    public void setText(By locator, String value) throws TimeoutException {
        WebElement webElement = waitForElementVisibility(locator, 30);
        waitForElementClickability(locator, 30);
        webElement.click();
        webElement.clear();
        webElement.sendKeys(value);
    }

    public void clearText(By locator) throws TimeoutException {
        WebElement webElement = waitForElementVisibility(locator, 30);
        webElement.clear();
    }

    public String getText(By locator) throws TimeoutException {
        WebElement webElement = waitForElementVisibility(locator, 30);
        return webElement.getText();
    }

    public String getText(By locator, int seconds) throws TimeoutException {
        WebElement webElement = waitForElementVisibility(locator, seconds);
        return webElement.getText();
    }

    public String getTextboxValue(By locator) {
        WebElement webElement = waitForElementVisibility(locator, 30);
        return webElement.getAttribute("value");
    }

    public void click(By locator) throws TimeoutException {
        WebElement element = null;
        try {
            element = waitForElementClickability(locator, 30);
            element.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    public void click(WebElement element) {
        element.click();
    }

    public void doubleClick(By locator) throws TimeoutException {
        Actions action = new Actions(driver);
        WebElement element = waitForElementClickability(locator, 30);
        action.doubleClick(element).perform();
    }

    public void rightClick(By locator) throws TimeoutException {
        Actions action = new Actions(driver);
        WebElement webElement = driver.findElement(locator);
        action.contextClick(webElement).perform();
    }

    public void mouseHover(By locator) throws TimeoutException {
        Actions action = new Actions(driver);
        action.moveToElement(waitForElementVisibility(locator, 30)).perform();
    }

    public boolean isVisible(By locator) {
        try {
            return (waitForElementVisibility(locator, 30).isDisplayed());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isVisible(By locator, long timeUnitsInSeconds) {
        try {
            return (waitForElementVisibility(locator, timeUnitsInSeconds).isDisplayed());
        } catch (Exception e) {
            return false;
        }
    }

    public void keyPress(By locator, Keys key) {
        driver.findElement(locator).sendKeys(key);
    }

    public void waitForElementPresence(By locator, long timeoutInSeconds) throws TimeoutException {
        sleep(1, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void waitForElementToDisappear(By locator, long timeoutInSeconds) {
        sleep(1, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public WebElement waitForElementClickability(By locator, long timeoutInSeconds) throws TimeoutException {
        sleep(1, TimeUnit.SECONDS);
        WebElement element;
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        element = driver.findElement(locator);
        return element;
    }

    public List<WebElement> waitForElementsClickability(By locator, long timeoutInSeconds) throws TimeoutException {
        sleep(1, TimeUnit.SECONDS);
        List<WebElement> elements;
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        elements = driver.findElements(locator);
        return elements;
    }

    public WebElement waitForElementVisibility(By locator, long timeoutInSeconds) throws TimeoutException {
        sleep(1, TimeUnit.SECONDS);
        WebElement element;
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element = driver.findElement(locator);
        return element;
    }

    public List<String> getElementTextList(By locator) {
        sleep(1, TimeUnit.SECONDS);
        List<WebElement> elements = waitForElementsClickability(locator, 10);
        return elements.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public ArrayList<String> getTabNames(By locator) throws TimeoutException {
        ArrayList<String> tabNames = new ArrayList<>();
        WebElement tableElement = waitForElementVisibility(locator, 30);
        List<WebElement> rowElements = tableElement.findElements(By.xpath("div"));

        for (WebElement row : rowElements) {
            if (row.getText().equals(" ")) {
                break;
            }
            tabNames.add(row.getText());
        }
        return tabNames;
    }

    public void switchToFrame(String frameName) {
        driver.switchTo().frame(frameName);
    }

    private List<WebElement> getRowElements(By locator) throws TimeoutException {
        WebElement tableElement = waitForElementVisibility(locator, 30);
        return tableElement.findElements(By.xpath("tbody/tr"));
    }

    private WebElement getRowElement(By locator, int rowIndex) throws TimeoutException {
        List<WebElement> rowElements = getRowElements(locator);

        if ((rowIndex < 1) || (rowIndex > rowElements.size())) {
            throw new NoSuchElementException(
                    "No such row index, '" + rowIndex + "', within the row elements - '" + rowElements.size() + "'");
        }
        return rowElements.get(rowIndex - 1);
    }

    public List<String> getRowText(By locator, int rowIndex) {
        List<String> rowTextValues = new ArrayList<>();
        try {
            WebElement row = getRowElement(locator, rowIndex);
            List<String> columnElementsText = row.findElements(By.xpath("td")).stream()
                    .map(WebElement::getText).collect(Collectors.toList());

            for (String columnText : columnElementsText) {
                if (columnText.equals("")) {
                    rowTextValues.add(getIconValue());
                } else {
                    rowTextValues.add(columnText);
                }
            }

        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return rowTextValues;
    }

    public List<String> getColumnText(By locator, int columnIndex) {
        List<String> columnTextValues = new ArrayList<>();
        if (columnIndex < 1) {
            throw new NoSuchElementException("Table::getColumnText -- Invalid column index, " + columnIndex);
        }
        try {
            List<WebElement> rowElements = getRowElements(locator);
            for (WebElement row : rowElements) {
                columnTextValues.add(row.findElement(By.xpath("td[" + columnIndex + "]")).getText());
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return columnTextValues;
    }

    public String getCellData(By locator, int rowIndex, int columnIndex) {
        String cellValue;
        List<String> rowContents = this.getRowText(locator, rowIndex);

        if (rowContents.size() > 0) {
            if (rowContents.size() < columnIndex) {
                throw new NoSuchElementException("Table::getCellData -- column index, '" + columnIndex
                        + "' is greater than the number of columns, '" + rowContents.size() + "'.");
            } else {
                cellValue = rowContents.get(columnIndex - 1);
            }
        } else {
            throw new NoSuchElementException("Table::getCellData -- table is empty.");
        }
        return cellValue;
    }

    public void switchToWindow(String windowName, int retryCount) {
        int attemptNumber = 0;
        String currentWindowHandle = driver.getWindowHandle();
        Set<String> windowHandles = driver.getWindowHandles();
        Iterator<String> iterator = windowHandles.iterator();
        while (iterator.hasNext() && (attemptNumber < retryCount) && (!driver.getTitle().equals(windowName))) {
            String newWindow = iterator.next();
            if (!(newWindow.equals(currentWindowHandle))) {

                driver.switchTo().window(newWindow);
            }
            sleep(1, TimeUnit.SECONDS);
            attemptNumber++;
        }
    }

    public String getCurrentWindowUrl() {
        return driver.getCurrentUrl();
    }

    private String getIconValue() {
        return "icon";
    }

    public int getElementCount(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        return elements.size();
    }

    public void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void clickOnElementAtIndex(By locator, int index) {
        List<WebElement> elements = driver.findElements(locator);
        WebElement element = elements.get(index);
        scrollToElement(element);
        element.click();
    }

    public String getElementAttributeValue(By locator, String attribute) {
        WebElement element = driver.findElement(locator);
        return element.getAttribute(attribute);
    }

    public Point getElementLocation(By locator) {
        WebElement webElement = waitForElementVisibility(locator, 30);
        return webElement.getLocation();
    }

    public List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }

    public void setText(WebElement element, String value) {
        element.click();
        element.clear();
        element.sendKeys(value);
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void goToUrl(String url) {
        driver.get(url);
    }

    public void switchToDefaultContentFromFrame() {
        driver.switchTo().defaultContent();
    }

    public void rightClick(WebElement element) {
        Actions action = new Actions(driver);
        action.contextClick(element).perform();
    }

    public void dragAndDrop(By sourceLocator, By targetLocator) {
        Actions actions = new Actions(driver);
        WebElement sourceElement = driver.findElement(sourceLocator);
        WebElement targetElement = driver.findElement(targetLocator);
        actions.dragAndDrop(sourceElement, targetElement).perform();
    }

    public void setText(String value, By locator) throws TimeoutException {
        WebElement webElement = waitForElementVisibility(locator, 30);
        waitForElementClickability(locator, 30);
        webElement.click();
        webElement.sendKeys(value);
    }

    public void mouseHover(WebElement element) throws TimeoutException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    public void waitForElementClickability(WebElement element, long timeoutInSeconds) throws TimeoutException {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void moveMouseByOffset(int xOffset, int yOffset) {
        Actions action = new Actions(driver);
        action.moveByOffset(xOffset, yOffset).perform();
    }

    public void dragMouseByOffset(int xOffset, int yOffset) {
        Actions action = new Actions(driver);
        action.clickAndHold().pause(1000).moveByOffset(xOffset, yOffset).release().build().perform();
    }
}
