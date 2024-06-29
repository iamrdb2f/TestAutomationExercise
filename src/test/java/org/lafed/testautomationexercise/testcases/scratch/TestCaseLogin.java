package org.lafed.testautomationexercise.testcases.scratch;

import com.aventstack.extentreports.ExtentTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.lafed.testautomationexercise.testcases.AbstractTest;
import org.lafed.testautomationexercise.utilities.extentreports.ExtentTestManager;
import org.lafed.testautomationexercise.utilities.listeners.TestListener;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

@Listeners({TestListener.class})
@Epic("TEST CASE")
@Feature("Test Case 2")
@Story("TestCaseLogin")
public class TestCaseLogin extends AbstractTest {

    private static final Logger logger = LoggerFactory.getLogger(TestCaseLogin.class);

    private static final String dataPath = "test-data/test_case/tc_login_pw_correct.json";
    private ObjectNode data;

    @Test(description = "TestCaseLogin", dataProvider = "tc_login_pw_correct")
    public void runTest(ITestContext context, ObjectNode data) throws Exception {
        this.data = data;
        ExtentTest extentTest = ExtentTestManager.startTest("TestCaseLogin", "TEST CASE - Test Case 2");
        runInit(context, data, "TestCaseLogin");
        extentTest.info("Test Name = " + context.getAttribute("TestCaseLogin").toString());

        runLOGIN();
    }

    @Step
    public void runLOGIN() throws Exception {
        try {

            System.out.println("Login_User_with_correct_email_and_password");

        } catch (Exception e) {
            logger.error("Error: ", e);
            throw e;
        }
    }

    @DataProvider(name = "tc_login_pw_correct")
    private Object[][] getData() throws IOException {
        return super.getJsonData(dataPath);
    }
}

