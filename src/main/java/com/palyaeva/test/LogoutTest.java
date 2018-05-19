package com.palyaeva.test;

import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

@Slf4j
public class LogoutTest extends BaseTest {

    @Test
    public void logout() {
        log.info("Log out test");
        homePage.signOut();
        Assert.assertTrue(homePage.isSignInBtnExists(), "Log out failed!");
    }
}
