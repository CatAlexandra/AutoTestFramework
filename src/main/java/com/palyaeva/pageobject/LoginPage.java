package com.palyaeva.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.palyaeva.util.WebDriverCheck.isElementExists;

public class LoginPage extends HomePage {

    private static final String signInFrame = "alibaba-login-box";

    @FindBy(xpath = "//input[@name='loginId']")
    private WebElement inputUsername;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement inputPassword;

    @FindBy(name = "submit-btn")
    private WebElement buttonSubmit;

    @FindBy(xpath = "//span[text()='Ваши учетное имя или пароль неправильные.']")
    private static List<WebElement> loginError;

    @FindBy(xpath = "//span[text()='Пожалуйста, введите свой пароль.']")
    private static List<WebElement> passwordEmptyError;

    @FindBy(xpath = "//span[text()='Пожалуйста, введите свой адрес электронной почты или ID пользователя.']")
    private static List<WebElement> loginEmptyError;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public HomePage typeUsername(String username) {
        inputUsername.clear();
        inputUsername.sendKeys(username);
        return this;
    }

    public HomePage typePassword(String password) {
        inputPassword.clear();
        inputPassword.sendKeys(password);
        return this;
    }

    public HomePage login(String username, String password) {
        typeUsername(username);
        typePassword(password);
        buttonSubmit.submit();
        driver.switchTo().defaultContent();
        return new HomePage(driver);
    }

    public boolean isIncorrectLoginOrPassword() {
        return isElementExists(loginError);
    }

    public boolean isEmptyPassword() {
        return isElementExists(passwordEmptyError);
    }

    public boolean isEmptyLogin() {
        return isElementExists(loginEmptyError);
    }
}
