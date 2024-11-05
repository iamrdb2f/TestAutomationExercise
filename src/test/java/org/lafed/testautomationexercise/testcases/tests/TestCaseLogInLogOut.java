package org.lafed.testautomationexercise.testcases.tests;

import org.lafed.testautomationexercise.pages.tae.LogoutPage;
import org.lafed.testautomationexercise.testcases.AbstractTest;
import org.lafed.testautomationexercise.utilities.listeners.TestListener;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.testng.ITestContext;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestListener.class})
@Epic("TEST CASE")
@Feature("Test Case 2")
@Story("TestCaseLogin")
public class TestCaseLogInLogOut extends AbstractTest {

    private static final Logger logger = LoggerFactory.getLogger(TestCaseLogInLogOut.class);

    @Test(description = "TestCaseLogInLogOut")
    public void runTest(ITestContext context) throws Exception {
        runInit(context, "TestCaseLogInLogOut");

        runLOGOUT();
    }

    @Step
    public void runLOGOUT(){
        try {
            LogoutPage logoutPage = new LogoutPage(baseClass);

            logger.info("Performing logout scenario...");

            logoutPage.clickLogout();

            logger.info("Logout successful");

        } catch (Exception e) {
            logger.error("Error: ", e);
            throw e;
        }

        logger.info("Test case LoginLogOut completed successfully");
    }
}

