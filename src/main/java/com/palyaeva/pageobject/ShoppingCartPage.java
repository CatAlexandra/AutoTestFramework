package com.palyaeva.pageobject;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.palyaeva.util.WebDriverCheck.waitTime;
import static com.palyaeva.util.WebDriverCheck.waitWhilePageLoaded;

@Slf4j
public class ShoppingCartPage extends HomePage {

    @FindBy(css = ".cart-number")
    private WebElement indicatorItemsInCart;

    @FindAll({@FindBy(css = ".item-group-wrapper")})
    private List<WebElement> itemsInCart;

    @FindAll({@FindBy(css = ".remove-single-product")})
    private List<WebElement> deleteSingleProductButtons;

    @FindBy(css = ".remove-all-product")
    private WebElement deleteAllProductsButton;

    @FindBy(css = "input[value='ОК'][type='button']")
    private WebElement deleteAllProductsConfirmButton;

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getIndicatorItemsInCart() {
        return indicatorItemsInCart;
    }

    public boolean isShoppingCart() {
        if (driver.getTitle().equals("Your AliExpress shopping cart - Buy directly from China")) {
            log.info("In shopping cart");
            return true;
        } else {
            log.info("Not in shopping cart. Current page is {}", driver.getTitle());
            return false;
        }
    }

    public Integer indicatorCountItemsInCart() {
        waitWhilePageLoaded(driver);
        // WebDriverWait wait = new WebDriverWait(driver, 5);
        // wait.until(ExpectedConditions.textToBePresentInElementValue(indicatorItemsInCart, countItemsInCart().toString()));
        waitTime(TimeUnit.SECONDS.toMillis(3));
        return Integer.parseInt(indicatorItemsInCart.getText());
    }

    public Integer countItemsInCart() {
        return itemsInCart.size();
    }

    public void deleteProduct(int index) {
        if (index < 0 || index >= deleteSingleProductButtons.size()) {
            log.error("Delete failed. Index of product out of bounds.");
        } else {
            try {
                deleteSingleProductButtons.get(index).click();
                log.info("Product with number {} deleted successfully!", index);
            } catch (NoSuchElementException e) {
                log.error("Delete failed. Shopping cart is empty.");
            }
        }
    }

    public void clearShoppingCart() {
        if (countItemsInCart() > 0) {
            deleteAllProductsButton.click();
            deleteAllProductsConfirmButton.click();
            log.info("Cart cleared successfully");
        } else {
            log.error("Cart clear failed - cart is empty");
        }
    }
}
