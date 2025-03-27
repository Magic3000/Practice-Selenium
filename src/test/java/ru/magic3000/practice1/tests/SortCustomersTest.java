package ru.magic3000.practice1.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.magic3000.practice1.pages.CustomersPage;

@Epic("Customers management")
@Feature("Sorting customers")
public class SortCustomersTest extends BaseTest {
    private CustomersPage customersPage;

    @BeforeClass
    public void init() {
        managerPage.clickCustomers();
        customersPage = new CustomersPage(getDriver());
    }

    /**
     * Sort customers and print result.
     */
    @Test(description = "Sort Customers Test")
    @Description("Sort customers and print result")
    public void SortCustomers() throws InterruptedException {
        customersPage.clickSortCustomersByFirstName();
        customersPage.saveSortedCustomers();
    }
}
