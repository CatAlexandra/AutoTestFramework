package com.palyaeva.pageobject;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Slf4j
public class AddressPage extends MyAliExpress {

    @FindBy(css = "input[name='contactPerson']")
    private WebElement nameInput;

    @FindBy(css = "select[name='country']")
    private WebElement countrySelect;

    @FindBy(css = "input[name='address']")
    private WebElement streetHouseFlatInput;

    @FindBy(css = "input[name='address2']")
    private WebElement streetHouseFlat2Input;

    @FindBy(css = ".sa-province-wrapper > select")
    private WebElement provinceSelect;

    @FindBy(css = "input[name='city']")
    private WebElement cityInput;

    @FindBy(css = "input[name='zip']")
    private WebElement postIndexInput;

    @FindBy(css = "input[name='mobileNo']")
    private WebElement mobileNumberInput;

    @FindBy(xpath = "//a[text()=\"Сохранить\"]")
    private WebElement saveButton;

    @FindBy(css = "#meShippingAddressTitle")
    private WebElement addressPageTitle;

    public AddressPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAddressPage() {
        try {
            return addressPageTitle.isDisplayed();
        } catch (NoSuchElementException e) {
            log.error("Failed to navigate to Address Page");
            return false;
        }
    }
}
