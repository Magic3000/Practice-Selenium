package ru.magic3000.practice1.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.magic3000.practice1.helpers.CustomerDataGenerator;
import ru.magic3000.practice1.pages.AddCustomerPage;
import ru.magic3000.practice1.pages.CustomersPage;

@Epic("Customers management")
@Feature("Adding customer")
public class AddCustomerTest extends BaseTest {
    private AddCustomerPage addCustomerPage;
    private CustomersPage customersPage;

    @BeforeClass
    public void init() {
        managerPage.clickAddCustomer();
        addCustomerPage = new AddCustomerPage(getDriver());

        managerPage.clickCustomers();
        customersPage = new CustomersPage(getDriver());
    }

    /**
     * Add customer with random data and delete it.
     */
    @Test(description = "Add Customer Test")
    @Description("Add customer with random data and delete it")
    public void AddCustomer() {
        managerPage.clickAddCustomer();
        addCustomerPage.enterCustomerData();
        addCustomerPage.addCustomer();

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
