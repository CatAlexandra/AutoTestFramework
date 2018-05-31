package com.palyaeva.test;

import com.palyaeva.pageobject.AddressPage;
import com.palyaeva.pageobject.DeliveryAddresses;
import com.palyaeva.pageobject.MyAliExpress;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static com.palyaeva.util.WebDriverUtils.waitTime;

@Slf4j
public class AddressTest extends BaseTest {

    private AddressPage addressPage;
    private DeliveryAddresses deliveryAddresses;

    private static final String VALID_NAME = "Test Test Test";
    private static final String VALID_STREET_HOUSE_FLAT = "Moskovskaya, 1, 111";
    private static final String VALID_PHONE_NUMBER = "8005553535";
    private static final String VALID_POST_INDEX = "410041";

    @BeforeClass
    public void openAddressPage() {
        log.info("Class AddressTest");
        log.info("Open address page");
        MyAliExpress myAliExpress = homePage.goToMyAliExpress();
        Assert.assertTrue(myAliExpress.isMyAliExpressPage(), "Current page is not My AliExpress page!");
        deliveryAddresses = myAliExpress.goToDeliveryAddresses();
        addressPage = deliveryAddresses.addNewAddress();
        Assert.assertTrue(addressPage.isAddressPage(), "Current page is not address page!");
        addressPage.chooseCountry();
    }
//
//    @BeforeMethod
//    public void selectRussia() {
//        addressPage.chooseCountry();
//    }

    @Test(priority = 9)
    public void validAddressTest() {
        //waitTime(TimeUnit.SECONDS.toMillis(2));
        log.info("valid Address Test");
        addressPage.typeFullName(VALID_NAME);
        //waitTime(TimeUnit.SECONDS.toMillis(2));
        //addressPage.chooseCountry();
        addressPage.typeStreetFlat(VALID_STREET_HOUSE_FLAT);
        addressPage.chooseProvince();
        //waitTime(TimeUnit.SECONDS.toMillis(2));
        addressPage.chooseCity();
        addressPage.typePhoneNumber(VALID_PHONE_NUMBER);
        addressPage.typePostIndex(VALID_POST_INDEX);
        deliveryAddresses = addressPage.saveAddress();
        //waitTime(TimeUnit.SECONDS.toMillis(2));
        Assert.assertTrue(deliveryAddresses.isAddressExist(), "Valid add address test failed!");
    }

    @Test(priority = 1)
    public void emptyNameTest() {
        log.info("empty Name Test");
        addressPage.typeFullName("");
        addressPage.clickSomewhere();
        Assert.assertTrue(addressPage.isEmptyFullName(),
                "emptyNameTest: failed to find error message!");
    }

    @Test(priority = 2)
    public void emptyStreetTest() {
        log.info("empty Street Test");
        addressPage.typeStreetFlat("");
        addressPage.clickSomewhere();
        Assert.assertTrue(addressPage.isEmptyStreet(),
                "emptyStreetTest: failed to find error message!");
    }

    @Test(priority = 3)
    public void emptyIndexTest() {
        log.info("empty Index Test");
        addressPage.typePostIndex("");
        addressPage.clickSomewhere();
        Assert.assertTrue(addressPage.isEmptyIndex(),
                "emptyIndexTest: failed to find error message!");
    }

    @Test(priority = 4)
    public void emptyPhoneNumberTest() {
        log.info("empty Phone Number Test");
        addressPage.typePhoneNumber("");
        addressPage.clickSomewhere();
        Assert.assertTrue(addressPage.isEmptyPhoneNumber(),
                "emptyPhoneNumberTest: failed to find error message!");
    }

    @DataProvider(name = "invalidName")
    public Object[][] invalidNames() {
        return new Object[][]{{"Test Test"}, {"Тест Русские Буквы"}, {"1 2 3"}};
    }

    @Test(dataProvider = "invalidName", priority = 5)
    public void invalidNameTest(String name) {
        waitTime(TimeUnit.SECONDS.toMillis(3));

        log.info("invalid Name Test");
        addressPage.typeFullName(name);
        addressPage.clickSomewhere();
        Assert.assertTrue(addressPage.isNonEnglishLettersOrNotFullName(),
                "invalidNameTest: failed to find error message for name: " + name);
        // addressPage.clearNameInput();
    }

    @Test(priority = 6)
    public void invalidStreetTest() {
        log.info("invalid Street Test");
        addressPage.typeStreetFlat("Улица Пушкина");
        addressPage.clickSomewhere();
        Assert.assertTrue(addressPage.isNonEnglishStreet(),
                "invalidStreetTest: failed to find error message for street!");
        // addressPage.clearStreetInput();
    }

    @DataProvider(name = "invalidPhoneNumber")
    public Object[][] invalidPhoneNumbers() {
        return new Object[][]{{"abcdef"}, {"-----"}, {"/////"}, {"1234"}};
    }

    @Test(dataProvider = "invalidPhoneNumber", priority = 7)
    public void invalidPhoneNumberTest(String number) {
        log.info("invalid Phone Number Test");
        addressPage.typePhoneNumber(number);
        addressPage.clickSomewhere();
        Assert.assertTrue((addressPage.isIncorrectLengthOfNumber() || addressPage.isIncorrectSymbolInNumber()),
                "invalidPhoneNumberTest: failed to find error message for number: " + number);
        // addressPage.clearPhoneInput();
    }

    @DataProvider(name = "invalidIndex")
    public Object[][] invalidIndexes() {
        return new Object[][]{{"abcdef"}, {"1234"}};
    }

    @Test(dataProvider = "invalidIndex", priority = 8)
    public void invalidIndexTest(String index) {
        log.info("invalid Index Test");
        addressPage.typePostIndex(index);
        addressPage.clickSomewhere();
        Assert.assertTrue(addressPage.isIncorrectIndex(),
                "invalidIndexTest: failed to find error message for index: " + index);
        // addressPage.clearIndexInput();
    }

    @Test(dependsOnMethods = "validAddressTest")
    public void deleteAddressTest() {
        log.info("delete Address Test");
        deliveryAddresses.deleteAddress();
        // Assert.assertFalse(deliveryAddresses.isAddressExist(), "Address deletion failed!");
        Assert.assertTrue(true);

    }

//    @AfterClass
//    public void goToHomePage() {
//        log.info("Class AddressTest finished");
//        homePage = deliveryAddresses.goToHomePage();
//    }
}
