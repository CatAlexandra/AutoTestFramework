package com.palyaeva.pageobject;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@Slf4j
public class MyWishesPage extends HomePage {

    @FindAll({@FindBy(xpath = "//ul[@class=\"list\"]/li//a[text()='Удалить']")})
    private List<WebElement> removeButtonsList;

    @FindBy(xpath = "//h2[text()='Мои желания']")
    private WebElement myWishesHeader;

    @FindBy(css = "a[href=\"/wishlist/wish_list_product_list.htm\"]")
    private WebElement myWishesLink;

    public MyWishesPage(WebDriver driver) {
        super(driver);
    }

    public boolean isMyWishesPage() {
        try {
            log.info("On My Wishes page");
            return (myWishesHeader.isDisplayed() && myWishesLink.isDisplayed());
        } catch (NoSuchElementException e) {
            log.info("Not on My Wishes page. Current page is {}", driver.getTitle());
            return false;
        }
    }

    public int countItemsInMyWishes() {
        return removeButtonsList.size();
    }

    public void deleteProduct(int index) {
        if (index < 0 || index >= countItemsInMyWishes()) {
            log.error("Delete failed. Index of product out of bounds.");
        } else {
            try {
                removeButtonsList.get(index).click();
                log.info("Product with number {} deleted successfully!", index);
                WebDriverWait wait = new WebDriverWait(driver, 10);
                wait.until(ExpectedConditions.visibilityOf(myWishesHeader));
                wait.until(ExpectedConditions.visibilityOf(myWishesLink));
            } catch (NoSuchElementException e) {
                log.error("Delete failed. Wish list is empty.");
            }
        }
    }

    public void clearMyWishes() {
//        for (int i = 0; i < countItemsInMyWishes(); i++) {
//            deleteProduct(i);
//        }
        while (countItemsInMyWishes() > 0) {
            deleteProduct(0);
        }
    }
}
