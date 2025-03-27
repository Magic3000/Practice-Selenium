package ru.magic3000.practice1.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Wait {
    private static Wait instance;
    private final WebDriverWait wait;

    private static Wait getInstance(WebDriver driver) {
        if (instance == null) {
            instance = new Wait(driver);
        }
        return instance;
    }

    public Wait(WebDriver driver) {
        wait = new WebDriverWait(driver,
                Duration.ofSeconds(Integer.parseInt(PropertyProvider.getInstance().getProperty("wait.timeout"))));
    }

    public static void waitUntilVisible(WebDriver driver, final WebElement webElement) {
        getInstance(driver).wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public static void waitUntilVisible(WebDriver driver, By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void waitUntilClickable(WebDriver driver, final WebElement webElement) {
        getInstance(driver).wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public static void waitThenClick(WebDriver driver, final WebElement webElement) {
        getInstance(driver).wait.until(ExpectedConditions.elementToBeClickable(webElement));
        webElement.click();
    }

    public static void waitUntilAlertIsPresent(WebDriver driver) {
        getInstance(driver).wait.until(ExpectedConditions.alertIsPresent());
    }
}
