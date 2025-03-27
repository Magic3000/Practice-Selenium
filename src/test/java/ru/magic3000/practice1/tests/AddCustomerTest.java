package ru.magic3000.practice1.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.magic3000.practice1.helpers.CustomerDataGenerator;
import ru.magic3000.practice1.pages.AddCustomerPage;
import ru.magic3000.practice1.pages.CustomersPage;

@Epic("Customers management")
@Feature("Adding customer")
public class AddCustomerTest extends BaseTest {
    private AddCustomerPage addCustomerPage;

    @BeforeMethod
    public void init() {
        managerPage.clickAddCustomer();
        addCustomerPage = new AddCustomerPage(getDriver(), getDriverWait());
    }

    /**
     * Add customer with random data and delete it.
     */
    @Test(description = "Add Customer Test")
    @Description("Add customer with random data and delete it")
    public void AddCustomer() {
        addCustomerPage.enterCustomerData();
        addCustomerPage.addCustomer();

        managerPage.clickCustomers();
        CustomersPage customersPage = new CustomersPage(getDriver(), getDriverWait());
        customersPage.deleteCustomerByFirstName(CustomerDataGenerator.cachedFirstName);
    }

    /**
     * Trying to add customer without any data provided.
     */
    @Test(description = "Negative test - Add Customer With Empty Data Test")
    @Description("Trying to add customer without any data provided")
    public void AddEmptyCustomer() {
        addCustomerPage.addEmptyCustomer();
    }
}
