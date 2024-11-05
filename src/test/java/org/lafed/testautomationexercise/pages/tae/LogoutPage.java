package org.lafed.testautomationexercise.pages.tae;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogoutPage {

    private static final Logger LOG = LoggerFactory.getLogger(LogoutPage.class);

    private final BaseClass baseClass;

    // Constructor initializes the stat of the driver
    public LogoutPage(BaseClass baseClass) {
        this.baseClass = baseClass;
        WebDriver driver = this.baseClass.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "/html/body/header/div/div/div/div[2]/div/ul/li[4]/a")
    WebElement logoutBtn;
    public void clickLogout() {
        baseClass.waitForElement(logoutBtn);
        logoutBtn.click();
    }
}
