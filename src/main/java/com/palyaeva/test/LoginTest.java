package com.palyaeva.test;

import com.palyaeva.businessobject.Account;
import com.palyaeva.pageobject.LoginPage;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Slf4j
public class LoginTest extends BaseTest {

    private Account account;

    //private static final String VALID_LOGIN = "test-automation_account@mail.ru";
    //private static final String VALID_LOGIN = "dejo@p33.org";
    private String VALID_LOGIN = "alexandra.palyaeva@gmail.com";
    //private static final String VALID_PASSWORD = "123456";
    private String VALID_PASSWORD = "Xdr5789";
    private static final String INVALID_LOGIN = "invalid_login";
    private static final String INVALID_PASSWORD = "invalid_password";

    @Test(dependsOnGroups = "incorrectAuthentication")
    public void validSignInTest() {
        log.info("valid Sign In Test");
        account = new Account(VALID_LOGIN, VALID_PASSWORD);
        homePage.loginAs(account.getLogin(), account.getPassword());
        Assert.assertTrue(homePage.isSignOutBtnExists(), "Sign in failed!");
    }

    @Test(groups = {"incorrectAuthentication"})
    public void emptyPasswordTest() {
        log.info("empty Password Test");
        account = new Account(VALID_LOGIN, "");
        homePage.loginAs(account.getLogin(), account.getPassword());
        Assert.assertTrue(new LoginPage(homePage.driver).isEmptyPassword(), "Sign in failed!");
    }

    @Test(groups = {"incorrectAuthentication"})
    public void emptyLoginTest() {
        log.info("empty Login Test");
        account = new Account(VALID_LOGIN, "");
        homePage.loginAs(account.getLogin(), account.getPassword());
        Assert.assertTrue(new LoginPage(homePage.driver).isEmptyLogin(), "Sign in failed!");
    }

    @DataProvider(name = "Authentication")
    public Object[][] credentials() {
        return new Object[][]{{VALID_LOGIN, INVALID_PASSWORD}, {INVALID_LOGIN, VALID_PASSWORD}};
    }

    @Test(dataProvider = "Authentication", groups = {"incorrectAuthentication"})
    public void incorrectAuthenticationTest(String login, String password) {
        log.info("incorrect Authentication Test");
        account = new Account(login, password);
        homePage.loginAs(account.getLogin(), account.getPassword());
        Assert.assertTrue(new LoginPage(homePage.driver).isIncorrectLoginOrPassword(), "Fail in incorrect authentication test!");
    }
}