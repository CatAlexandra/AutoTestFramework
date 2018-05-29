package com.palyaeva.pageobject;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static com.palyaeva.util.WebDriverCheck.isElementClickable;
import static com.palyaeva.util.WebDriverCheck.waitWhilePageLoaded;

@Slf4j
public class ProductPage extends HomePage {

    @FindBy(xpath = "//a[text()='Добавить в корзину']")
    private WebElement addToCartButton;

    @FindBy(xpath = "//a[text()='Добавить в \"Мои желания\"']")
    private WebElement addToMyWishesButton;

    @FindBy(xpath = "//em[text()='Мои желания']")
    private WebElement chooseFirstItemFromDropDown;

    @FindBy(xpath = "//a[text()='Перейти к «Моим желаниям»']")
    private WebElement goToMyWishesButton;

    // добавить магазин в список любимых
    @FindBy(css = ".store-follow-btn")
    private WebElement storeFollowButton;

    @FindBy(xpath = "//a[text()='Перейти к корзине']")
    private WebElement goToCartButton;

    @FindBy(xpath = "//div[contains(text(), \"Пожалуйста, выберите Цвет\")]")
    private WebElement messageErrorChooseColor;

    @FindBy(xpath = "//div[@id='j-product-info-sku']")
    private WebElement productAttributes;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public ShoppingCartPage addToCart() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        if (isElementClickable(addToCartButton)) {
            chooseProductAttributes();
            addToCartButton.click();
            log.info("Product added to cart");
            goToCartButton.click();
        }
        ShoppingCartPage cart = new ShoppingCartPage(driver);

        wait.until(ExpectedConditions.titleIs("Your AliExpress shopping cart - Buy directly from China"));
        wait.until(ExpectedConditions.visibilityOf(cart.getIndicatorItemsInCart()));
        waitWhilePageLoaded(driver);

        return cart;
    }

    public MyWishesPage addToWishList() {
        log.info("Add product to Wish list");
        addToMyWishesButton.click();
        chooseFirstItemFromDropDown.click();
        goToMyWishesButton.click();
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(2));
        return new MyWishesPage(driver);
    }

    public void chooseProductAttributes() {
        List<WebElement> childs = productAttributes.findElements(By.xpath(".//*"));
        for (WebElement child : childs) {
            if (child.getTagName().equals("ul")) {
                WebElement firstItem = child.findElement(By.xpath("./li[1]"));
                if (isElementClickable(firstItem)) {
                    firstItem.click();
                }
            }
        }
    }
}