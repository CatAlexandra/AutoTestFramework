package com.palyaeva.util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class WebDriverCheck {

    public static void whileElementsExists(List<WebElement> elementList) {
        whileElementsExists(elementList, TimeUnit.SECONDS.toMillis(30));
    }

    public static void whileElementsExists(List<WebElement> elementList, long time) {
        long currentTime = System.currentTimeMillis();
        while (isElementExists(elementList)) {
            if (System.currentTimeMillis() - currentTime > time) {
                throw new RuntimeException("Waiting for elements " + elementList + " is overloaded!");
            }
        }
    }

    public static void waitTime(long time) {
        long beginTime = System.currentTimeMillis();
        while (true) {
            if (System.currentTimeMillis() - beginTime >= time) {
                break;
            }
        }
    }

    public static boolean isElementExists(List<WebElement> elementList) {
        return !elementList.isEmpty();
    }

    public static boolean isElementClickable(WebElement element) {
        return (element.isDisplayed() && element.isEnabled());
    }

    public static void waitWhilePageLoaded(WebDriver driver) {
        new WebDriverWait(driver, TimeUnit.SECONDS.toSeconds(20)).until(
                webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));
    }
//    public boolean isElementExists(By locator) {
//        return !driver.findElements(locator).isEmpty();
//    }
}
