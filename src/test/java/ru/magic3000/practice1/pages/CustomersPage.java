package ru.magic3000.practice1.pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.atomic.AtomicLong;

import static ru.magic3000.practice1.helpers.Wait.waitThenClick;
import static ru.magic3000.practice1.helpers.Wait.waitUntilVisible;

public class CustomersPage {
    final WebDriver driver;
    final WebDriverWait driverWait;

    @FindBy(xpath = "//a[contains(text(), 'First Name')]")
    WebElement sortByFirstNameButton;

    @FindBy(xpath = "//tbody/tr/td[1]")
    List<WebElement> nameElements;

    @FindBy(css = "div.marTop")
    WebElement customersContainer;

    public CustomersPage(WebDriver webDriver, WebDriverWait webDriverWait) {
        driver = webDriver;
        driverWait = webDriverWait;
        PageFactory.initElements(driver, this);
    }

    @Step("Click sort customers by name button")
    public void clickSortCustomersByFirstName() {
        waitThenClick(driverWait, sortByFirstNameButton);
    }

    @Step("Get a list of customer names")
    public List<String> getCustomerNames() {
        waitUntilVisible(driverWait, sortByFirstNameButton);
        //nameElements.forEach(webElement -> waitUntilVisible(driverWait, webElement));

        List<String> customerNames = new ArrayList<>();
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        AtomicLong lastHeight = new AtomicLong((long)jsExecutor.executeScript("return arguments[0].scrollHeight", customersContainer));

        int attempts = 0;
        while (attempts < 2) {
            int previousSize = customerNames.size();
            nameElements.forEach(element -> {
                if (!customerNames.contains(element.getText()))
                    customerNames.add(element.getText());
            });
            jsExecutor.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", customersContainer);

            try {
                boolean isNewContentLoaded = wait.until(driver -> {
                    long newHeight = (long)jsExecutor.executeScript("return arguments[0].scrollHeight", customersContainer);
                    return newHeight > lastHeight.get() || nameElements.size() > previousSize;
                });

                if (isNewContentLoaded) {
                    lastHeight.set((long)jsExecutor.executeScript("return arguments[0].scrollHeight", customersContainer));
                    attempts = 0;
                } else {
                    attempts++;
                }
            } catch (TimeoutException e) {
                break;
            }
        }

        return new ArrayList<>(customerNames);
    }

    @Step("Delete customer with first name: {fName}")
    public void deleteCustomerByFirstName(String fName) {
        List<String> customers = getCustomerNames();
        if (!customers.isEmpty()) {
            driver.findElement(By.xpath("//td[text()='" + fName + "']/following-sibling::td/button")).click();
        }
    }

    @Step("Get customer with name length closest to average name length from customers list")
    public String getCustomerWithAverageNameLength() {
        waitUntilVisible(driverWait, customersContainer);
        List<String> customers = getCustomerNames();

        OptionalDouble avgLen = customers.stream().mapToInt(String::length).average();
        return customers.stream()
                .min(Comparator.comparingDouble(a -> Math.abs(a.length() - avgLen.orElse(0))))
                .orElse(null);
    }

    @Step("Add sorted customer names to allure report")
    public void saveSortedCustomers() {
        List<String> customers = getCustomerNames();
        Allure.addAttachment("Sorted customer names: ", String.join(", ", customers));
    }
}
