package ru.magic3000.practice1.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.magic3000.practice1.helpers.CustomerDataGenerator;

import static ru.magic3000.practice1.helpers.Wait.waitUntilAlertIsPresent;
import static ru.magic3000.practice1.helpers.Wait.waitUntilVisible;

public class AddCustomerPage {
    final WebDriver driver;
    final WebDriverWait driverWait;

    @FindBy(xpath = "//input[@ng-model='fName']")
    WebElement firstNameField;

    @FindBy(xpath = "//input[@ng-model='lName']")
    WebElement lastNameField;

    @FindBy(xpath = "//input[@ng-model='postCd']")
    WebElement postCodeField;

    @FindBy(css = "button.btn.btn-default")
    WebElement addCustomerButton;

    public AddCustomerPage(WebDriver webDriver, WebDriverWait webDriverWait)
    {
        driver = webDriver;
        driverWait = webDriverWait;
        PageFactory.initElements(driver, this);
    }

    @Step("Click add customer button")
    public void clickAddCustomer() {
        addCustomerButton.click();
    }

    @Step("Accept alert with successfully added customer")
    public void clickAlert() {
        waitUntilAlertIsPresent(driverWait);

        Alert alert = driver.switchTo().alert();
        Assert.assertTrue(alert.getText().contains("Customer added successfully with customer id"), "Alert message isn't wat was expected.");
        alert.accept();
    }

    @Step("Check for error message under first name input field in case name is empty")
    public void checkError() {
        waitUntilVisible(driverWait, firstNameField);
        String validationMessage = firstNameField.getAttribute("validationMessage");

        assert validationMessage != null;
        if (validationMessage.contains("Please"))
            Assert.assertTrue("Please fill out this field.".contains(validationMessage), "Error message not what was expected.");
        else if (validationMessage.contains("Заполните"))
            Assert.assertTrue("Заполните это поле.".contains(validationMessage), "Error message not what was expected.");
    }

    @Step("Enter customer data in fields while creating")
    public void customerDataInput(String fName, String lName, String postCode) {
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

    @Step("Click add customer button and accept alert that the customer has been successfully added")
    public void addCustomer() {
        clickAddCustomer();
        clickAlert();
    }

    @Step("Click add customer button and check if an error about not being able to add a customer appears")
    public void addEmptyCustomer() {
        clickAddCustomer();
        checkError();
    }
}
