package com.palyaeva.pageobject;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Slf4j
public class MyAliExpress extends HomePage {

    @FindBy(css = "a[title=\"Адреса доставки\"]")
    private WebElement deliveryAddressesLink;

    public MyAliExpress(WebDriver driver) {
        super(driver);
    }

    public boolean isMyAliExpressPage() {
        return (driver.getTitle().equals("AliExpress.com : My AliExpress") &&
                deliveryAddressesLink.isDisplayed());
    }

    public AddressPage goToDeliveryAddresses() {
        log.info("Go to Delivery addresses");
        deliveryAddressesLink.click();
        return new AddressPage(driver);
    }
}
