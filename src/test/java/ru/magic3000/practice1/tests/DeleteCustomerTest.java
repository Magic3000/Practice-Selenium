package ru.magic3000.practice1.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.magic3000.practice1.pages.CustomersPage;

import java.util.List;

@Epic("Customers management")
@Feature("Deleting customer")
public class DeleteCustomerTest extends BaseTest {
    private CustomersPage customersPage;

    @BeforeClass
    public void init() {
        customersPage = managerPage.clickCustomers();
    }

    /**
     * Delete customer with name length closest to average.
     */
    @Test(description = "Delete Customer Test")
    @Description("Delete customer with name length closest to average")
    public void DeleteCustomer() throws InterruptedException {
        String customerName = customersPage.getCustomerWithAverageNameLength();
        customersPage.deleteCustomerByFirstName(customerName);
        checkCustomerNotPresent(customerName);
    }

    @Step("Check if customer present in customers list")
    public void checkCustomerNotPresent(String removedCustomerName) {
        List<String> customers = customersPage.getCustomerNames();
        customers.forEach(customerName ->
                Assert.assertNotEquals(removedCustomerName, customerName));
    }
}
