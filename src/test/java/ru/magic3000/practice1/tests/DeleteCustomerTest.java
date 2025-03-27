package ru.magic3000.practice1.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.magic3000.practice1.pages.CustomersPage;

@Epic("Customers management")
@Feature("Deleting customer")
public class DeleteCustomerTest extends BaseTest {
    private CustomersPage customersPage;

    @BeforeClass
    public void init() {
        managerPage.clickCustomers();
        customersPage = new CustomersPage(getDriver(), getDriverWait());
    }

    /**
     * Delete customer with name length closest to average.
     */
    @Test(description = "Delete Customer Test")
    @Description("Delete customer with name length closest to average")
    public void DeleteCustomer() throws InterruptedException {
        String customerName = customersPage.getCustomerWithAverageNameLength();
        customersPage.deleteCustomerByFirstName(customerName);
        customersPage.checkCustomerPresent(customerName);
    }
}
