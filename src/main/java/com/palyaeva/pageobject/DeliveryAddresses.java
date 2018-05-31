package com.palyaeva.pageobject;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static com.palyaeva.util.WebDriverUtils.isElementExists;

@Slf4j
public class DeliveryAddresses extends MyAliExpress {

    @FindBy(xpath = "//a[text()='Добавить новый адрес']")
    private WebElement addNewAddressButton;

    @FindBy(xpath = "//a[text()='Удалить']")
    private WebElement deleteAddressButton;

    @FindBy(css = "input[value='Ok']")
    private WebElement confirmDelete;

    @FindBy(css = ".sa-address-item")
    private List<WebElement> savedAddressForm;

    public DeliveryAddresses(WebDriver driver) {
        super(driver);
    }

    public AddressPage addNewAddress() {
        log.info("Go to create address page");
        addNewAddressButton.click();
        return new AddressPage(driver);
    }

    public DeliveryAddresses deleteAddress() {
        log.info("Delete address");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(deleteAddressButton));
        deleteAddressButton.click();
        confirmDelete.click();
        return this;
    }

    public boolean isAddressExist() {
        return isElementExists(savedAddressForm);
    }
}
