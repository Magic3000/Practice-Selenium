package ru.magic3000.practice1.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static ru.magic3000.practice1.helpers.Wait.waitThenClick;

public class ManagerPage {
    final WebDriver driver;

    @FindBy(xpath = "//button[contains(text(), 'Add Customer')]")
    WebElement addCustomerButton;

    @FindBy(xpath = "//button[contains(text(), 'Customers')]")
    WebElement customersButton;

    public ManagerPage(WebDriver webDriver) {
        driver = webDriver;
        PageFactory.initElements(driver, this);
    }

    @Step("Open add customer page")
    public void clickAddCustomer(){
        waitThenClick(driver, addCustomerButton);
    }

    @Step("Open customers page")
    public void clickCustomers() {
        waitThenClick(driver, customersButton);
    }
}
