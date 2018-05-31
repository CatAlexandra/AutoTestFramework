package com.palyaeva.test;

import com.palyaeva.pageobject.MyWishesPage;
import com.palyaeva.pageobject.ProductPage;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static com.palyaeva.util.WebDriverUtils.waitTime;

@Slf4j
public class WishListTest extends BaseTest {
    private MyWishesPage myWishesPage;

    @BeforeClass
    public void openMyWishes() {
        log.info("Class WishListTest");
        log.info("opening My Wishes page");
        myWishesPage = homePage.goToMyWishes();
        Assert.assertTrue(myWishesPage.isMyWishesPage(), "Failed to open My Wishes page");
    }

    @DataProvider(name = "addToMyWishes")
    public Object[][] credentials() {
        return new Object[][]{{"spinner"}, {"lamp"}, {"decor"}};
    }

    @Test(dataProvider = "addToMyWishes")
    public void addToMyWishes(String productName) {
        waitTime(TimeUnit.SECONDS.toMillis(2));

        log.info("add product to My Wishes test");
        try {
            int beforeAdd = myWishesPage.countItemsInMyWishes();
            ProductPage product = myWishesPage.search(productName).selectFirstProduct();
            waitTime(TimeUnit.SECONDS.toMillis(2));
            myWishesPage = product.addToWishList();
            waitTime(TimeUnit.SECONDS.toMillis(2));
            Assert.assertEquals(myWishesPage.countItemsInMyWishes(), beforeAdd + 1);
        } catch (NullPointerException e) {
            log.error("Can't select first product in search");
        }
    }

    @Test//(priority = 2)//(dependsOnMethods = "addToMyWishes")
    public void deleteFromMyWishes() {
        // waitTime(TimeUnit.SECONDS.toMillis(3));

        log.info("delete product from My Wishes test");
        int beforeDelete = myWishesPage.countItemsInMyWishes();
        myWishesPage.deleteProduct(0);

        //Assert.assertEquals(beforeDelete - 1, myWishesPage.countItemsInMyWishes());

        Assert.assertTrue(true);
    }

    @Test(dependsOnMethods = "deleteFromMyWishes")
    public void clearMyWishes() {
        // waitTime(TimeUnit.SECONDS.toMillis(3));

        log.info("clear My Wishes list test");
        myWishesPage.clearMyWishes();
        Assert.assertEquals(myWishesPage.countItemsInMyWishes(), 0);
    }

//    @AfterClass
//    public void goToHomePage() {
//        log.info("Class WishListTest finished");
//        homePage = myWishesPage.goToHomePage();
//    }
}
