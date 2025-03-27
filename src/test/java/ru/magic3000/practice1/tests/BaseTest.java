package ru.magic3000.practice1.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import io.github.bonigarcia.wdm.WebDriverManager;
import ru.magic3000.practice1.helpers.PropertyProvider;
import ru.magic3000.practice1.pages.ManagerPage;

import java.time.Duration;

public class BaseTest {
    private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private final ThreadLocal<WebDriverWait> driverWait = new ThreadLocal<>();
    ManagerPage managerPage;

    /**
     * Initializing.
     */
    @BeforeClass
    public void init(final ITestContext context) {
        String browserName = PropertyProvider.getInstance().getProperty("browser.name");
        int pageLoadTimeout = Integer.parseInt(PropertyProvider.getInstance().getProperty("page.load.timeout"));
        WebDriverManager.getInstance(browserName).setup();

        switch (browserName) {
            case "chrome" -> driver.set(new ChromeDriver(new ChromeOptions()
                    .addArguments("--remote-allow-origins=*")
                    .addArguments("--disable-gpu")
                    .addArguments("--start-maximized")
                    .addArguments("--headless")
                    .addArguments("--no-sandbox")));
            default -> throw new IllegalStateException("Unexpected value: "
                    + browserName);
        }

        getDriver().manage().deleteAllCookies();
        getDriver().manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));

        String webUrl = PropertyProvider.getInstance().getProperty("web.url");
        getDriver().get(webUrl);
        managerPage = new ManagerPage(getDriver(), getDriverWait());
    }

    public WebDriver getDriver() {
        if (driver.get() == null) {
            driver.set(new ChromeDriver(new ChromeOptions()
                    .addArguments("--remote-allow-origins=*")
                    .addArguments("--disable-gpu")
                    .addArguments("--start-maximized")
                    .addArguments("--headless")
                    .addArguments("--no-sandbox")));
        }
        return driver.get();
    }

    public WebDriverWait getDriverWait() {
        if (driverWait.get() == null) {
            driverWait.set(new WebDriverWait(getDriver(),
                    Duration.ofSeconds(Integer.parseInt(PropertyProvider.getInstance().getProperty("wait.timeout")))));
        }
        return driverWait.get();
    }

    /**
     * Tear down.
     */
    @AfterClass(alwaysRun = true)
    public final void tearDown() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            webDriver.quit();
            driver.remove();
        }
    }
}
