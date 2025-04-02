package ru.magic3000.practice1.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import static ru.magic3000.practice1.helpers.Wait.waitThenClick;

public class ManagerPage {
    final WebDriver driver;
    final WebDriverWait driverWait;

    @FindBy(xpath = "//button[contains(text(), 'Add Customer')]")
    WebElement addCustomerButton;

    @FindBy(xpath = "//button[contains(text(), 'Customers')]")
    WebElement customersButton;

    public ManagerPage(WebDriver webDriver, WebDriverWait webDriverWait) {
        driver = webDriver;
        driverWait = webDriverWait;
        PageFactory.initElements(driver, this);
    }

    @Step("Open add customer page")
    public AddCustomerPage clickAddCustomer(){
        waitThenClick(driverWait, addCustomerButton);
        return new AddCustomerPage(driver, driverWait);
    }

    @Step("Open customers page")
    public CustomersPage clickCustomers() {
        waitThenClick(driverWait, customersButton);
        return new CustomersPage(driver, driverWait);
    }
}
