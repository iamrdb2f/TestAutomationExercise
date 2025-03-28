package org.lafed.testautomationexercise.config;

import org.lafed.testautomationexercise.handlers.TAEHandler;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Objects;

@Configuration
@PropertySource("classpath:application-${env}.properties")
public class SeleniumConfig {
    private static final Logger LOG = LoggerFactory.getLogger(SeleniumConfig.class);

    @Value("${env}")
    private String environment;

    @Value("${browser.type}")
    private String browserType;

    @Value("${download.path}")
    private String downloadPath;

    @Value("${tae.url}")
    private String taeUrl;

    @Value("${tae.password}")
    private String taePassword;

    @Value("${tae.userName}")
    private String taeUserName;

    @Value("${remote}")
    private String remote;

    @Value("${chrome.path}")
    private String chromePath;

    @Value("${chrome.driver.label}")
    private String chromeDriverLabel;

    @Value("${selenium.hub.url}")
    private String seleniumHuburl;

    private final static String NO = "no";

    private final static String YES = "yes";

    @Bean
    public TAEHandler setupFTI() throws MalformedURLException {
        String url = new URL(Objects.requireNonNull(taeUrl)).toString();
        return new TAEHandler(
                environment,
                browserType,
                downloadPath,
                url,
                taeUserName,
                taePassword
        );
    }

    @Bean
    @Scope("prototype")
    @Lazy
    public WebDriver setupDriver() {
        if (NO.equals(remote)) {
            return localDriver();
        } else if (YES.equals(remote)) {
            return webDriver();
        } else {
            LOG.error("Please specify running environment in application.properties file (jenkins.env=yes/no)", new NullPointerException());
        }
        return null;
    }


    //Return WebDriver based on browserType (Chrome, IE or Firefox) for local env
    public WebDriver localDriver() {

        if (Objects.requireNonNull(browserType).equalsIgnoreCase("chrome")) {
            System.setProperty(chromeDriverLabel, Objects.requireNonNull(chromePath));
            LOG.info("Executing ChromeDriver()...");

            ChromeOptions options = new ChromeOptions();

            HashMap<String, Object> chromePrefs = new HashMap<>();
            chromePrefs.put("plugins.always_open_pdf_externally", true);
            chromePrefs.put("profile.default_content_settings.popups", 0);

            options.setExperimentalOption("prefs", chromePrefs);
            options.setScriptTimeout(Duration.ofMinutes(15)); //Defaulting to 15 minutes Timeout rule
            options.setPageLoadTimeout(Duration.ofMinutes(15));//Defaulting to 15 minutes Timeout rule
            options.addArguments("start-maximized");
            options.addArguments("enable-automation");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-browser-side-navigation");
            options.addArguments("--remote-allow-origins=*");

            return new ChromeDriver(options);
        } else if (browserType.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "drivers/geckodriver.exe");
            LOG.info("Executing FireFoxDriver()...");

            return new FirefoxDriver();
        } else if (browserType.equalsIgnoreCase("ie") || browserType.equalsIgnoreCase("internet explorer")) {
            System.setProperty("webdriver.ie.driver", "drivers/IEDriverServer.exe");
            LOG.info("Executing InternetExplorerDriver()...");

            return new InternetExplorerDriver();
        } else {
            LOG.error("No driver is running!", new NullPointerException());
            return null;
        }
    }

    //Driver to run on Jenkins
    public WebDriver webDriver() {

        try {
            ChromeOptions options = new ChromeOptions();

            HashMap<String, Object> chromePrefs = new HashMap<>();
            chromePrefs.put("plugins.always_open_pdf_externally", true);
            chromePrefs.put("profile.default_content_settings.popups", 0);

            options.setExperimentalOption("prefs", chromePrefs);
            options.setScriptTimeout(Duration.ofMinutes(15)); //Defaulting to 15 minutes Timeout rule
            options.setPageLoadTimeout(Duration.ofMinutes(15));//Defaulting to 15 minutes Timeout rule
            options.addArguments("start-maximized");
            options.addArguments("enable-automation");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-browser-side-navigation");
            options.addArguments("--disable-gpu");

            //The access url to the Selenium Hub is specified here:  http://localhost:4444/wd/hub

            return new RemoteWebDriver(new URL(Objects.requireNonNull(seleniumHuburl)), options);
        } catch (MalformedURLException e) {
            LOG.error("Invalid Selenium Hub URL", e);
        }

        return null;
    }
}
