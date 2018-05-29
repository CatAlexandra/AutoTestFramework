package com.palyaeva.test;

import com.palyaeva.pageobject.AddressPage;
import com.palyaeva.pageobject.DeliveryAddresses;
import com.palyaeva.pageobject.MyAliExpress;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Slf4j
public class AddressTest extends BaseTest {

    private AddressPage addressPage;
    private DeliveryAddresses deliveryAddresses;

    private static final String VALID_NAME = "Test Test Test";
    private static final String VALID_STREET_HOUSE_FLAT = "Moskovskaya, 1, 111";
    private static final String VALID_PHONE_NUMBER = "8005553535";
    private static final String VALID_POST_INDEX = "410041";

//    private static final String INVALID_NAME = "Test Test";
//    private static final String INVALID_NAME2 = "Тест Русские Буквы";
//    private static final String INVALID_NAME3 = "1 2 3";
//
//    private static final String INVALID_NAME4 = "";
//
//    private static final String INVALID_STREET_HOUSE_FLAT = "";
//
//    private static final String INVALID_STREET_HOUSE_FLAT = "Улица Пушкина";
//
//    private static final String INVALID_PHONE_NUMBER = "1234";
//    private static final String INVALID_PHONE_NUMBER = "-----";
//    private static final String INVALID_PHONE_NUMBER = "/////";
//    private static final String INVALID_PHONE_NUMBER = "abcdef";
//
//    private static final String INVALID_PHONE_NUMBER = "";
//
//    private static final String INVALID_POST_INDEX = "";
//
//    private static final String INVALID_POST_INDEX = "1234";
//    private static final String INVALID_POST_INDEX = "abcdef";

    @BeforeClass
    public void openAddressPage() {
        log.info("Open address page");
        MyAliExpress myAliExpress = homePage.goToMyAliExpress();
        Assert.assertTrue(myAliExpress.isMyAliExpressPage(), "Current page is not My AliExpress page!");
        deliveryAddresses = myAliExpress.goToDeliveryAddresses();
        addressPage = deliveryAddresses.addNewAddress();
        Assert.assertTrue(addressPage.isAddressPage(), "Current page is not address page!");
    }

    @Test
    public void validAddressTest() {
        log.info("valid Address Test");
        addressPage.typeFullName(VALID_NAME);
        addressPage.chooseCountry();
        addressPage.typeStreetFlat(VALID_STREET_HOUSE_FLAT);
        addressPage.chooseProvince();
        addressPage.chooseCity();
        addressPage.typePhoneNumber(VALID_PHONE_NUMBER);
        addressPage.typePostIndex(VALID_POST_INDEX);
        deliveryAddresses = addressPage.saveAddress();
        Assert.assertTrue(deliveryAddresses.isAddressExist(), "Valid add address test failed!");
    }

    @Test
    public void emptyNameTest() {
        log.info("empty Name Test");
        addressPage.typeFullName("");
        Assert.assertTrue(addressPage.isEmptyFullName(),
                "emptyNameTest: failed to find error message!");
    }

    @Test
    public void emptyStreetTest() {
        log.info("empty Street Test");
        addressPage.typeStreetFlat("");
        Assert.assertTrue(addressPage.isEmptyStreet(),
                "emptyStreetTest: failed to find error message!");
    }

    @Test
    public void emptyIndexTest() {
        log.info("empty Index Test");
        addressPage.typePostIndex("");
        Assert.assertTrue(addressPage.isEmptyIndex(),
                "emptyIndexTest: failed to find error message!");
    }

    @Test
    public void emptyPhoneNumberTest() {
        log.info("empty Phone Number Test");
        addressPage.typePhoneNumber("");
        Assert.assertTrue(addressPage.isEmptyPhoneNumber(),
                "emptyPhoneNumberTest: failed to find error message!");
    }

    @DataProvider(name = "invalidName")
    public Object[][] invalidNames() {
        return new Object[][]{{"Test Test"}, {"Тест Русские Буквы"}, {"1 2 3"}};
    }

    @Test(dataProvider = "invalidName")
    public void invalidNameTest(String name) {
        log.info("invalid Name Test");
        addressPage.typeFullName(name);
        Assert.assertTrue(addressPage.isNonEnglishLettersOrNotFullName(),
                "invalidNameTest: failed to find error message for name: " + name);
    }

    @DataProvider(name = "invalidPhoneNumber")
    public Object[][] invalidPhoneNumbers() {
        return new Object[][]{{"abcdef"}, {"-----"}, {"/////"}, {"1234"}};
    }

    @Test(dataProvider = "invalidPhoneNumber")
    public void invalidPhoneNumberTest(String number) {
        log.info("invalid Phone Number Test");
        addressPage.typePhoneNumber(number);
        Assert.assertTrue((addressPage.isIncorrectLengthOfNumber() || addressPage.isIncorrectSymbolInNumber()),
                "invalidPhoneNumberTest: failed to find error message for number: " + number);
    }

    @DataProvider(name = "invalidIndex")
    public Object[][] invalidIndexes() {
        return new Object[][]{{"abcdef"}, {"1234"}};
    }

    @Test(dataProvider = "invalidIndex")
    public void invalidIndexTest(String index) {
        log.info("invalid Index Test");
        addressPage.typePostIndex(index);
        Assert.assertTrue(addressPage.isIncorrectIndex(),
                "invalidIndexTest: failed to find error message for index: " + index);
    }

    @Test
    public void invalidStreetTest() {
        log.info("invalid Street Test");
        addressPage.typeStreetFlat("Улица Пушкина");
        Assert.assertTrue(addressPage.isNonEnglishStreet(),
                "invalidStreetTest: failed to find error message for street!");
    }

    @Test(dependsOnMethods = "validAddressTest")
    public void deleteAddressTest() {
        log.info("delete Address Test");
        deliveryAddresses.deleteAddress();
        Assert.assertFalse(deliveryAddresses.isAddressExist(), "Address deletion failed!");
    }
}
