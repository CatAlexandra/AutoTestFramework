package com.palyaeva.test;

import com.palyaeva.driver.MyChromeDriver;
import com.palyaeva.pageobject.BasePage;
import com.palyaeva.pageobject.HomePage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

@Slf4j
public abstract class BaseTest {
    public static WebDriver driver;
    public BasePage basePage;
    public static HomePage homePage;

    @BeforeSuite
    public void setUp() {
        log.info("Starting web driver");
        driver = MyChromeDriver.getDriver();
        basePage = new BasePage(driver);
        homePage = navigate();
        Assert.assertTrue(homePage.isHomePage(), "Not on home page!");
        //new LoginTest().validSignInTest();
    }

    public HomePage navigate() {
        driver.get("https://ru.aliexpress.com/");
        return new HomePage(getDriver());
    }

    public WebDriver getDriver() {
        return driver;
    }

    @AfterSuite
    public void tearDown() {
        new LogoutTest().logout();
        log.info("Web driver quit");
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
