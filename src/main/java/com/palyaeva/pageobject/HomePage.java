package com.palyaeva.pageobject;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static com.palyaeva.util.WebDriverCheck.isElementExists;
import static com.palyaeva.util.WebDriverCheck.whileElementsExists;

@Slf4j
public class HomePage extends BasePage {

    @FindBy(className = "newuser-container")
    private List<WebElement> popUpNewUser;

    @FindBy(className = "close-layer")
    private WebElement buttonCloseNewUserPopUp;

    @FindBy(className = "user-account-info")
    private WebElement buttonAccountInfo;

    @FindBy(className = "sign-btn")
    private List<WebElement> buttonSignIn;

    @FindBy(xpath = "//a[text()='Выйти']")
    private List<WebElement> buttonSignOut;

    @FindBy(xpath = "//span[text()='Корзина']")
    private WebElement shoppingCartButton;

    @FindBy(css = "#search-key")
    private WebElement searchInput;

    @FindBy(css = ".search-button")
    private WebElement searchButton;

    @FindBy(xpath = "//span[text()='Мои желания']")
    private WebElement myWishesLink;

    @FindBy(xpath = "//span[text()='Мой AliExpress']")
    private WebElement goToMyAliExpressButton;

    private static final String SIGN_IN_FRAME = "alibaba-login-box";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // если появляется всплывающее окно с купоном
    public void closeNewUserPopUp() {
        if (isElementExists(popUpNewUser)) {
            buttonCloseNewUserPopUp.click();
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("ui-mask")));
            log.info("Pop-up closed");
        }
        whileElementsExists(popUpNewUser);
    }

    public LoginPage openLoginPage() {
        // WebDriverWait wait = new WebDriverWait(driver, 40);
        // wait.until(ExpectedConditions.elementToBeClickable(By.className("user-account-info")));
        // if (!isElementExists(popUpNewUser)) {
        buttonAccountInfo.click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(buttonSignIn.get(0)));
        buttonSignIn.get(0).click();
        driver.switchTo().frame(SIGN_IN_FRAME);
        log.info("Login page opened");
        //}
        return new LoginPage(driver);
    }

    public HomePage loginAs(String username, String password) {
        closeNewUserPopUp();
        LoginPage loginPage = openLoginPage();
        loginPage.login(username, password);
        WebDriverWait wait = new WebDriverWait(driver, 40);
        wait.until(ExpectedConditions.visibilityOf(buttonAccountInfo));
        log.info("Logged in as username: {}, password: {}", username, password);
        closeNewUserPopUp();
        return new HomePage(driver);
    }

    public boolean isSignOutBtnExists() {
        buttonAccountInfo.click();
        return isElementExists(buttonSignOut);
    }

    public void signOut() {
        buttonAccountInfo.click();
        buttonSignOut.get(0).click();
        log.info("Signed out");
    }

    public boolean isSignInBtnExists() {
        buttonAccountInfo.click();
        return isElementExists(buttonSignIn);
    }

    public boolean isHomePage() {
        if (driver.getTitle().equals("AliExpress.com - интернет-магазин электроники, " +
                "модных новинок, товаров для дома и сада, игрушек, товаров для спорта," +
                " автотоваров и многого другого.")) {
            log.info("On home page {}", driver.getTitle());
            return true;
        } else {
            log.error("Not on the home page. Current page is {}", driver.getTitle());
            return false;
        }
    }

    public ShoppingCartPage goToCart() {
        log.info("go to shopping cart page");
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(shoppingCartButton));
        shoppingCartButton.click();
        return new ShoppingCartPage(driver);
    }

    public SearchResultsPage search(String productName) {
        log.info("Searching for {}", productName);
        searchInput.sendKeys(productName);
        searchButton.click();
        return new SearchResultsPage(driver);
    }

    public MyWishesPage goToMyWishes() {
        log.info("go to My Wishes");
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(myWishesLink));
        myWishesLink.click();
        return new MyWishesPage(driver);
    }

    public MyAliExpress goToMyAliExpress() {
        log.info("go to My AliExpress");
        goToMyAliExpressButton.click();
        return new MyAliExpress(driver);
    }
}
