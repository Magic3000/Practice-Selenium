package ru.magic3000.practice1.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.magic3000.practice1.pages.CustomersPage;

import java.util.Collections;
import java.util.List;

@Epic("Customers management")
@Feature("Sorting customers")
public class SortCustomersTest extends BaseTest {
    private CustomersPage customersPage;

    @BeforeClass
    public void init() {
        customersPage = managerPage.clickCustomers();
    }

    /**
     * Sort customers and print result.
     */
    @Test(description = "Sort Customers Test")
    @Description("Sort customers and print result")
    public void SortCustomers() {
        List<String> notSortedCustomers = customersPage.getCustomerNames();

        customersPage.clickSortCustomersByFirstName();
        customersPage.saveSortedCustomers();

        List<String> sortedCustomers = customersPage.getCustomerNames();
        notSortedCustomers.sort(Collections.reverseOrder());
        Assert.assertEquals(sortedCustomers, notSortedCustomers, "Customer list is not sorted correctly!");
    }
}
