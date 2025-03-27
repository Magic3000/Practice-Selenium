package ru.magic3000.practice1.helpers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wait {
    public static void waitUntilVisible(WebDriverWait driverWait, final WebElement webElement) {
        driverWait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public static void waitThenClick(WebDriverWait driverWait, final WebElement webElement) {
        driverWait.until(ExpectedConditions.elementToBeClickable(webElement));
        webElement.click();
    }

    public static void waitUntilAlertIsPresent(WebDriverWait driverWait) {
        driverWait.until(ExpectedConditions.alertIsPresent());
    }
}
