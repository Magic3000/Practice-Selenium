package ru.magic3000.practice1.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import ru.magic3000.practice1.helpers.CustomerDataGenerator;

import static ru.magic3000.practice1.helpers.Wait.*;

public class AddCustomerPage {
    final WebDriver driver;

    @FindBy(xpath = "//input[@ng-model='fName']")
    WebElement firstNameField;

    @FindBy(xpath = "//input[@ng-model='lName']")
    WebElement lastNameField;

    @FindBy(xpath = "//input[@ng-model='postCd']")
    WebElement postCodeField;

    @FindBy(css = "button.btn.btn-default")
    WebElement addCustomerButton;

    public AddCustomerPage(WebDriver webDriver)
    {
        driver = webDriver;
        PageFactory.initElements(driver, this);
    }

    @Step("Click add customer button")
    public void clickAddCustomer() {
        waitThenClick(driver, addCustomerButton);
    }

    @Step("Accept alert with successfully added customer")
    public void clickAlert() {
        waitUntilAlertIsPresent(driver);

        Alert alert = driver.switchTo().alert();
        Assert.assertTrue(alert.getText().contains("Customer added successfully with customer id"), "Alert message isn't wat was expected.");
        alert.accept();
    }

    @Step("Check for error message under first name input field in case name is empty")
    public void checkError() {
        waitUntilVisible(driver, firstNameField);
        String validationMessage = firstNameField.getAttribute("validationMessage");

        assert validationMessage != null;
        if (validationMessage.contains("Please"))
            Assert.assertTrue("Please fill out this field.".contains(validationMessage), "Error message not what was expected.");
        else if (validationMessage.contains("Заполните"))
            Assert.assertTrue("Заполните это поле.".contains(validationMessage), "Error message not what was expected.");
    }

    @Step("Clear all data from customer fields while creating if it present")
    public void clearEnteredDataIfPresent() {
        if (firstNameField == null)
            return;
        waitUntilVisible(driver, firstNameField);
        waitUntilVisible(driver, lastNameField);
        waitUntilVisible(driver, postCodeField);

        if (!firstNameField.getAttribute("value").isEmpty())
            firstNameField.clear();
        if (!lastNameField.getAttribute("value").isEmpty())
            lastNameField.clear();
        if (!postCodeField.getAttribute("value").isEmpty())
            postCodeField.clear();
    }

    @Step("Enter customer data in fields while creating")
    public void customerDataInput(String fName, String lName, String postCode) {
        waitUntilVisible(driver, firstNameField);
        waitUntilVisible(driver, lastNameField);
        waitUntilVisible(driver, postCodeField);

        firstNameField.sendKeys(fName);
        lastNameField.sendKeys(lName);
        postCodeField.sendKeys(postCode);
    }

    @Step("Generate test data for customer to use in creating process")
    public void enterCustomerData() {
        String postCode = CustomerDataGenerator.getPostCode();
        String fName = CustomerDataGenerator.getFirstName(postCode);
        String lName = CustomerDataGenerator.getLastName();
        customerDataInput(fName, lName, postCode);
    }

    public void addCustomer() {
        clickAddCustomer();
        clickAlert();
    }

    public void addEmptyCustomer() {
        clickAddCustomer();
        checkError();
    }
}
