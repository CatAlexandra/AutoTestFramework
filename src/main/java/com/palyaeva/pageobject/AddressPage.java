package com.palyaeva.pageobject;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Slf4j
public class AddressPage extends DeliveryAddresses {

    @FindBy(css = "input[name='contactPerson']")
    private WebElement nameInput;

    @FindBy(xpath = "//p[text()=\"Пожалуйста, укажите  имя и фамилию получателя\"]")
    private WebElement emptyNameError;

    @FindBy(xpath = "//p[text()=\"Пожалуйста, введите ваше ФИО на английском языке, " +
            "оставляя пробел между словами (Фамилия Имя Отчество)\"]")
    private WebElement nonEnglishLettersOrNotFullNameError;

    @FindBy(css = "select[name='country']")
    private WebElement countrySelect;
    // select[name='country'] option[value="RU"]

    @FindBy(css = "input[name='address']")
    private WebElement streetHouseFlatInput;

    @FindBy(xpath = "//p[text()=\"Please enter English only\"]")
    private WebElement nonEnglishStreetError;

    @FindBy(xpath = "//p[text()=\"Пожалуйста, укажите название улицы\"]")
    private WebElement emptyStreet;

    @FindBy(css = "input[name='address2']")
    private WebElement flatInput;

    @FindBy(css = ".sa-province-wrapper > select")
    private WebElement provinceSelect;
    // .sa-province-wrapper > select option[value="Saratov Oblast"]

    @FindBy(xpath = "//p[text()=\"Пожалуйста, укажите край/область/pегион\"]")
    private WebElement emptyProvinceError;

    @FindBy(css = ".sa-city-wrapper > select")
    private WebElement citySelect;
    // .sa-city-wrapper > select option[value="Saratov"]

    @FindBy(xpath = "//p[text()=\"Пожалуйста, введите город\"]")
    private WebElement emptyCityError;

    @FindBy(css = "input[name='zip']")
    private WebElement postIndexInput;

    @FindBy(xpath = "//p[text()=\"Введите почтовый индекс, например 123456\"]")
    private WebElement postIndexError;

    @FindBy(xpath = "//p[text()=\"Пожалуйста, укажите почтовый индекс\"]")
    private WebElement emptyPostIndexError;

    @FindBy(css = "input[name='mobileNo']")
    private WebElement mobileNumberInput;

    @FindBy(xpath = "//p[text()=\"You must include a Mobile number\"]")
    private WebElement emptyMobileNumberError;

    @FindBy(xpath = "//p[contains(text(), 'Пожалуйста, используйте только цифры')]")
    private WebElement mobileNumberContainsIncorrectSymbolError;

    @FindBy(xpath = "//p[text()= 'Пожалуйста, введите 5-16 символов.']")
    private WebElement mobileNumberLengthError;

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

    public AddressPage typeToInput(WebElement element, String infoType, String data) {
        log.info("Type {}", infoType);
        element.sendKeys(data);
        return this;
    }

    public void typeFullName(String fullName) {
        typeToInput(nameInput, "full name", fullName);
    }

    public void typeStreetFlat(String address) {
        typeToInput(streetHouseFlatInput, "street, House, Flat", address);
    }

    public void typePostIndex(String index) {
        typeToInput(postIndexInput, "post Index", index);
    }

    public void typePhoneNumber(String number) {
        typeToInput(mobileNumberInput, "mobile Number", number);
    }

    public AddressPage chooseCountry() {
        log.info("Choose country");
        countrySelect.click();
        try {
            countrySelect.findElement(By.cssSelector("option[value=\"RU\"]")).click();
        } catch (NoSuchElementException e) {
            log.error("Failed to find Russian Federation in select");
        }
        return this;
    }

    public AddressPage chooseProvince() {
        log.info("Choose Province");
        countrySelect.click();
        try {
            provinceSelect.findElement(By.cssSelector("option[value=\"Saratov Oblast\"]")).click();
        } catch (NoSuchElementException e) {
            log.error("Failed to find Saratov Oblast in select");
        }
        return this;
    }

    public AddressPage chooseCity() {
        log.info("Choose Province");
        countrySelect.click();
        try {
            citySelect.findElement(By.cssSelector("option[value=\"Saratov\"]")).click();
        } catch (NoSuchElementException e) {
            log.error("Failed to find Saratov in select");
        }
        return this;
    }

    public boolean isIncorrectInput(WebElement element, String elementName) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            log.error("Failed to find error message (element: {})", elementName);
            return false;
        }
    }

    public boolean isEmptyFullName() {
        return isIncorrectInput(emptyNameError, "emptyNameError");
    }

    public boolean isEmptyStreet() {
        return isIncorrectInput(emptyStreet, "emptyStreet");
    }

    public boolean isEmptyProvince() {
        return isIncorrectInput(emptyProvinceError, "emptyProvinceError");
    }

    public boolean isEmptyCity() {
        return isIncorrectInput(emptyCityError, "emptyCityError");
    }

    public boolean isEmptyIndex() {
        return isIncorrectInput(emptyPostIndexError, "emptyPostIndexError");
    }

    public boolean isEmptyPhoneNumber() {
        return isIncorrectInput(emptyMobileNumberError, "emptyMobileNumberError");
    }

    public boolean isNonEnglishLettersOrNotFullName() {
        return isIncorrectInput(nonEnglishLettersOrNotFullNameError,
                "nonEnglishLettersOrNotFullNameError");
    }

    public boolean isNonEnglishStreet() {
        return isIncorrectInput(nonEnglishStreetError,
                "nonEnglishStreetError");
    }

    public boolean isIncorrectIndex() {
        return isIncorrectInput(postIndexError,
                "postIndexError");
    }

    public boolean isIncorrectLengthOfNumber() {
        return isIncorrectInput(mobileNumberLengthError,
                "mobileNumberLengthError");
    }

    public boolean isIncorrectSymbolInNumber() {
        return isIncorrectInput(mobileNumberContainsIncorrectSymbolError,
                "mobileNumberContainsIncorrectSymbolError");
    }

    public DeliveryAddresses saveAddress() {
        saveButton.click();
        return new DeliveryAddresses(driver);
    }

}
