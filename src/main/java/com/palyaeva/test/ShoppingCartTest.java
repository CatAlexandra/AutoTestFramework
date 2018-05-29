package com.palyaeva.test;

import com.palyaeva.pageobject.ProductPage;
import com.palyaeva.pageobject.ShoppingCartPage;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static com.palyaeva.util.WebDriverCheck.waitTime;

@Slf4j
public class ShoppingCartTest extends BaseTest {

    private ShoppingCartPage cart;

    @BeforeClass
    public void openShoppingCart() {
        log.info("Open shopping cart");
        cart = homePage.goToCart();
        Assert.assertTrue(cart.isShoppingCart(), "Current page is not shopping cart page!");
    }

    @DataProvider(name = "addProduct")
    public Object[][] credentials() {
        return new Object[][]{{"usb"}, {"фонарик"}, {"pencil"}};
    }

    @Test(dataProvider = "addProduct")
    public void addProductTest(String productName) {
        waitTime(TimeUnit.SECONDS.toMillis(3));

        log.info("Add product to cart test");
        try {
            ProductPage product = cart.search(productName).selectFirstProduct();

            waitTime(TimeUnit.SECONDS.toMillis(2));

            cart = product.addToCart();
        } catch (NullPointerException e) {
            log.error("Can't select first product in search");
        }
        Assert.assertEquals(cart.indicatorCountItemsInCart(), cart.countItemsInCart());
    }

//    @Test
//    public void addProductTest() {
//        log.info("Add product to cart test");
//        try {
//            ProductPage product = cart.search("usb").selectFirstProduct();
//            cart = product.addToCart();
//        } catch (NullPointerException e) {
//            log.error("Can't select first product in search");
//        }
//        Assert.assertEquals(cart.indicatorCountItemsInCart(), cart.countItemsInCart());
//    }

    @Test//(dependsOnMethods = "addProductTest")
    public void deleteProductTest() {
        // waitTime(TimeUnit.SECONDS.toMillis(3));

        log.info("Delete product from cart");
        int countBefore = cart.countItemsInCart();
        cart.deleteProduct(0);
        waitTime(TimeUnit.SECONDS.toMillis(2));
        int countAfter = cart.countItemsInCart();
        Assert.assertEquals(countAfter, countBefore - 1);
    }

    @AfterClass
    public void clearCartTest() {
        //waitTime(TimeUnit.SECONDS.toMillis(3));

        log.info("Delete all products from cart");
        cart.clearShoppingCart();
        int countAfter = cart.countItemsInCart();
        Assert.assertEquals(countAfter, 0);
    }
}
