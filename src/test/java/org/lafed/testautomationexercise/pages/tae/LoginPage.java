package org.lafed.testautomationexercise.pages.tae;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.testng.Assert.assertTrue;

public class LoginPage {
    private static final Logger LOG = LoggerFactory.getLogger(LoginPage.class);

    private final BaseClass baseClass;

    // Constructor initializes the stat of the driver
    public LoginPage(BaseClass baseClass) {
        this.baseClass = baseClass;
        WebDriver driver = this.baseClass.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id=\"header\"]/div/div/div/div[2]/div/ul/li[4]/a")
    WebElement signupLogin;


    @FindBy(xpath = "//*[@id=\"form\"]/div/div/div[1]/div/form/input[2]")
    WebElement username;

    @FindBy(xpath = "//*[@id=\"form\"]/div/div/div[1]/div/form/input[3]")
    WebElement password;

    @FindBy(xpath = "//*[@id=\"form\"]/div/div/div[1]/div/form/button")
    WebElement btnLogin;

    public void setUserName(String user) {
        LOG.info("Username element exists? " + baseClass.exists(username));
        baseClass.waitForElement(username);
        username.sendKeys(user);
    }

    public void setPassword(String pass) {
        baseClass.waitForElement(password);
        password.sendKeys(pass);
    }

    public void clickSubmit() {
        baseClass.waitForElement(btnLogin);
        btnLogin.click();
    }

    public void clickSignupLogin() {
        baseClass.waitForElement(signupLogin);

        LOG.info("Before + Signup/Login element exists?" + baseClass.exists(signupLogin));
        LOG.info("Signup/Login get text: [" + signupLogin.getText() + "]");

        assertTrue(baseClass.exists(signupLogin));

        signupLogin.click();

        assertTrue(baseClass.exists(signupLogin));


        LOG.info("After + Signup/Login element exists?" + baseClass.exists(signupLogin));

    }

    public void login(String env, String user, String pass) {
        if ("local".equals(env) || "bench".equals(env)) {
            clickSignupLogin();
            LOG.info("Click Signup/Login for " + env.toUpperCase() + " environment");
        } else if ("dev".equals(env)) {
            LOG.info("Click Child for DEV environment");
        }

        setUserName(user);
        LOG.info("Setting username...");
        setPassword(pass);
        LOG.info("Setting password...");
        clickSubmit();
    }

    @FindBy(xpath = "/html/body/div/div[2]/div[1]/div[2]/div[2]/button[1]/p")
    WebElement closePopUp;

    public void closePopUp() {
        baseClass.waitForElement(closePopUp);
        closePopUp.click();
    }
}
