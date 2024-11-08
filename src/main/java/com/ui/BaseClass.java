package com.ui;

import com.ui.pages.PageFactoryLoader;

import io.cucumber.java.Scenario;
import io.cucumber.plugin.event.Step;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.PageFactory;
import reader.ConfigPropertyReader;
import utils.helpers.FileUtil;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class BaseClass {
    private static final Logger logger = LogManager.getLogger(BaseClass.class);

    public static WebDriver driver;
    public static final HashMap<String, String> gMap = new HashMap<>();

    public static byte[] screenshot;
    public static Scenario cScenario;

    public static PageFactoryLoader pageFactoryLoader = new PageFactoryLoader();
    static final ConfigPropertyReader read = new ConfigPropertyReader();
    public static final HashMap<String, byte[]> screenshotMap = new HashMap<>();
    public static FileUtil fileUtil = new FileUtil();
    public static String ParentWindow;


    public static void launchUrl() {
        try {
            if (driver == null) {
                launchBrowser();
                logger.info("Initializing the browser and starting the test.");

            }
            driver.get(System.getProperty("url"));
        } catch (Exception e) {

        }
    }
    public static void initializeMap() {
        gMap.clear();
    }

    public static void launchBrowser() {
        driver = BaseClass.createWebDriver(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        ParentWindow = driver.getWindowHandle();
        driver.manage().window().maximize();
    }


    public static String getBrowserType() {
        return System.getProperty("browser").toLowerCase();
    }

    public static void loadProperties() {
        read.loadProperties();
    }

    public static void setScenario(Scenario scenario) {
        cScenario = scenario;
    }


    public static void closeBrowser() {
        //driver.close();
        driver.quit();
        System.out.println("Completed the execution");
    }

    public static void StepTakesScreenshot(Scenario scenario) {
        try {
            String screenshotName = "Screenshot" + System.currentTimeMillis();
            WebDriver augmentedDriver = new Augmenter().augment(driver);
            File sourceScreenShot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
            fileUtil.createDirectoryAtProjectPath("reportLogs", sourceScreenShot, scenario);
            scenario.attach(screenshot, "image/png", screenshotName);
            screenshotMap.put(scenario.getName() + "_" + screenshotName, screenshot);

        } catch (Exception ignored) {
            System.out.println();
        }
    }

    public static void TakesScreenshot(Scenario scenario) {
        try {
            String screenshotName = scenario.getName().replaceAll(" ", "_");
            WebDriver augmentedDriver = new Augmenter().augment(driver);
            byte[] screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.BYTES);
            screenshotMap.put(scenario.getName(), screenshot);
            scenario.attach(screenshot, "image/png", screenshotName);
        } catch (Exception ignored) {

        }
    }

    public static void launchNewBrowser(String browserName) {
        System.setProperty("browser", browserName);
        driver = createWebDriver(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        ParentWindow = driver.getWindowHandle();
        driver.manage().window().maximize();

    }

    public static void StepTakesScreenshot(Scenario scenario, WebDriver webDriver) {
        try {
            String screenshotName = "Screenshot" + System.currentTimeMillis();
            WebDriver augmentedDriver = new Augmenter().augment(webDriver);
            screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.BYTES);

            scenario.attach(screenshot, "image/png", screenshotName);
            screenshotMap.put(scenario.getName() + "_" + screenshotName, screenshot);
        } catch (Exception ignored) {

        }
    }

    public static WebDriver createWebDriver(WebDriver webDriver) {

        String browser = getBrowserType();
        switch (browser.toUpperCase()) {

            case "CHROME":
                webDriver = chrome();
                break;
            case "CHROMEHEADLESS":
                webDriver = chromeHeadless();
                break;
            case "SAFARI":
                webDriver = safari();
                break;
            case "FIREFOX":
                webDriver = fireFox();
                break;
        }
        return webDriver;
    }


    public static WebDriver chrome() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(getChromeOptions(false));
        driver.manage().window().maximize();
        return driver;
    }

    public static WebDriver chromeHeadless() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(getChromeOptions(true));
        driver.manage().window().maximize();
        return driver;
    }

    private static ChromeOptions getChromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless)
            options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--disable-blink-features");
        options.addArguments("--disable-blink-features=AutomationControlled");
        return options;
    }


    public static WebDriver safari() {
        SafariOptions safariOptions = new SafariOptions();

        WebDriverManager.safaridriver().setup();
        driver = new SafariDriver();

        return driver;
    }


    public static WebDriver fireFox() {
        FirefoxOptions options = new FirefoxOptions();

        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();

        return driver;
    }

}
