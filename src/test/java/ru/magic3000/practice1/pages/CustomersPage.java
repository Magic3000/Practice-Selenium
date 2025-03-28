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
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Set;
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

    List<String> getCustomerNames() {
        nameElements.forEach(webElement -> waitUntilVisible(driverWait, webElement));

        Set<String> customerNames = new HashSet<>();
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        AtomicLong lastHeight = new AtomicLong((long)jsExecutor.executeScript("return arguments[0].scrollHeight", customersContainer));

        int attempts = 0;
        while (attempts < 2) {
            int previousSize = customerNames.size();
            nameElements.forEach(element -> customerNames.add(element.getText()));
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
                System.out.println("Timeout: scrolling doesn't affect height");
                break;
            }
        }

        return new ArrayList<>(customerNames);
    }

    public void deleteCustomerByFirstName(String fName) {
        List<String> customers = getCustomerNames();
        if (customers.isEmpty()) {
            System.out.println("Customers not found");
        } else {
            driver.findElement(By.xpath("//td[text()='" + fName + "']/following-sibling::td/button")).click();
            System.out.println("Customer deleted: " + fName);
        }
    }

    @Step("Check if customer present in customers list")
    public void checkCustomerPresent(String removedCustomerName) {
        List<String> customers = getCustomerNames();
        customers.forEach(customerName ->
                Assert.assertNotEquals(removedCustomerName, customerName));
    }

    @Step("Get customer with name length closest to average name length from customers list")
    public String getCustomerWithAverageNameLength() {
        waitUntilVisible(driverWait, customersContainer);
        List<String> customers = getCustomerNames();

        OptionalDouble avgLen = customers.stream().mapToInt(String::length).average();
        String avgName = customers.stream()
                .min(Comparator.comparingDouble(a -> Math.abs(a.length() - avgLen.orElse(0))))
                .orElse(null);

        System.out.println("Customer with average closest name: " + avgName);
        return avgName;
    }

    public void saveSortedCustomers() {
        List<String> customers = getCustomerNames();
        Allure.addAttachment("Sorted customer names: ", String.join(", ", customers));
    }
}
