package com.palyaeva;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestNGListener;
import org.testng.TestNG;
import org.uncommons.reportng.HTMLReporter;
import org.uncommons.reportng.JUnitXMLReporter;

import java.util.List;

@Slf4j
public class Main {

    public static void main(String[] args) {
        TestNG testng = new TestNG();
        testng.setUseDefaultListeners(false);
        List<Class<? extends ITestNGListener>> listeners = Lists.newArrayList();
        listeners.add(HTMLReporter.class);
        listeners.add(JUnitXMLReporter.class);
        testng.setListenerClasses(listeners);
        List<String> suites = Lists.newArrayList();
        String resource = "testng.xml";
        log.info("PATH TO TESTNG.XML {}", resource);
        suites.add(resource);
        testng.setTestSuites(suites);
        testng.run();
    }

}
