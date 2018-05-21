package com.palyaeva.test;

import com.palyaeva.pageobject.MyWishesPage;
import com.palyaeva.pageobject.ProductPage;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Slf4j
public class WishListTest extends BaseTest {
    private MyWishesPage myWishesPage;

    @BeforeClass
    public void openMyWishes() {
        log.info("opening My Wishes page");
        myWishesPage = homePage.goToMyWishes();
        Assert.assertTrue(myWishesPage.isMyWishesPage(), "Failed to open My Wishes page");
    }

    @Test
    public void addToMyWishes() {
        log.info("add product to My Wishes test");
        try {
            int beforeAdd = myWishesPage.countItemsInMyWishes();
            ProductPage product = myWishesPage.search("usb").selectFirstProduct();
            myWishesPage = product.addToWishList();
            Assert.assertEquals(myWishesPage.countItemsInMyWishes(), beforeAdd + 1);
        } catch (NullPointerException e) {
            log.error("Can't select first product in search");
        }
    }

    @Test
    public void deleteFromMyWishes() {
        log.info("delete product from My Wishes test");
        int beforeDelete = myWishesPage.countItemsInMyWishes();
        myWishesPage.deleteProduct(0);
        Assert.assertEquals(beforeDelete - 1, myWishesPage.countItemsInMyWishes());
    }

    @AfterClass
    public void clearMyWishes() {
        log.info("clear My Wishes list test");
        myWishesPage.clearMyWishes();
        Assert.assertEquals(myWishesPage.countItemsInMyWishes(), 0);
    }
}
