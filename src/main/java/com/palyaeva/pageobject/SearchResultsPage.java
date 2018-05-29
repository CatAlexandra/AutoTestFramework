package com.palyaeva.pageobject;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;

@Slf4j
public class SearchResultsPage extends HomePage {

    @FindBy(xpath = "//ul[contains(@id,\"list-items\")]/li[1]//img")
    private WebElement firstElementInList;

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public ProductPage selectFirstProduct() {
        try {
            firstElementInList.click();
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            if (tabs.size() > 1) {
                int lastOpenedWindow = tabs.size() - 1;
                driver.switchTo().window(tabs.get(lastOpenedWindow));
            }

            //driver.close();
            //driver.switchTo().window(tabs2.get(0)).close();
            //driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "\t");
            //driver.switchTo().window(driver.getWindowHandles());
            log.info("Go to the product page");
            return new ProductPage(driver);
        } catch (NoSuchElementException e) {
            log.error("First product not found in search results");
            return null;
        }
    }
}
