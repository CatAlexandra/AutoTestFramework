package com.palyaeva.driver;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

@Slf4j
public class MyChromeDriver {

    private static final String DRIVER_RESOURCE_PATH = "chromedriver.exe";

    private static final String WEB_DRIVER_PROPERTY_KEY = "webdriver.chrome.driver";

    private static final WebDriver driver;

    static {
        log.info("Start loading Chrome driver.");
        String driverFilePath = DRIVER_RESOURCE_PATH;
        System.setProperty(WEB_DRIVER_PROPERTY_KEY, driverFilePath);
        WebDriver chromeDriver = new ChromeDriver();
        chromeDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        // Causes TimeoutException: timeout - Timed out receiving message from renderer
        //chromeDriver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver = chromeDriver;
        driver.manage().window().maximize();
        log.info("Chrome driver loaded successfully.");
    }

    public static WebDriver getDriver() {
        return driver;
    }


}
