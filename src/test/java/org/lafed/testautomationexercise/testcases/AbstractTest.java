package org.lafed.testautomationexercise.testcases;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.qameta.allure.Step;

import org.lafed.testautomationexercise.config.SeleniumConfig;
import org.lafed.testautomationexercise.handlers.TAEHandler;
import org.lafed.testautomationexercise.pages.tae.BaseClass;
import org.lafed.testautomationexercise.pages.tae.LoginPage;

import org.openqa.selenium.WebDriver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;

@ContextConfiguration(classes = SeleniumConfig.class, loader = AnnotationConfigContextLoader.class)
public abstract class AbstractTest extends AbstractTestNGSpringContextTests {

    @Autowired
    protected WebDriver driver;

    @Autowired
    protected TAEHandler taeHandler;

    protected BaseClass baseClass;
    protected LoginPage loginPage;
    protected int index;

    @BeforeClass
    public void setup() {
        baseClass = new BaseClass(driver);
        baseClass.setup(taeHandler);
        loginPage = new LoginPage(baseClass);
        index = "bench".equals(taeHandler.getEnvironment()) ? 6 : 5;
    }

    @BeforeMethod
    public void setDriver(ITestContext context) {
        context.setAttribute("driver", driver);
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    @AfterClass
    public void tearDown(ITestContext context) {
        baseClass.tearDown();
    }

    protected Object[][] getJsonData(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode n1 = mapper.readTree(new File(path));
        return new Object[][]{{n1}};
    }

    @Step
    public synchronized void runInit(ITestContext context, ObjectNode data, String TestScenario) throws Exception {
        //Initialize transaction
        loginPage.login(taeHandler.getEnvironment(), taeHandler.getUserName(), taeHandler.getPassword());
        Assert.assertEquals(driver.getTitle(), "Automation Exercise");
        logger.info("Logged in! :)");
    }
}
