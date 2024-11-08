package stepDefinitions.support.ui;

import com.aventstack.extentreports.reporter.FileUtil;
import com.ui.BaseClass;
import io.cucumber.java.*;
import io.cucumber.plugin.event.PickleStepTestStep;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;


import static com.ui.BaseClass.*;

public class Hooks {
    private static final Logger logger = LogManager.getLogger(Hooks.class);

    public static Scenario cScenario;
    private static HashMap<Integer, String> scenarios;
    PickleStepTestStep currentStep;
    private int counter = 0;

    public Hooks() {
        if (scenarios == null)
            scenarios = new HashMap<Integer, String>();
    }

    @Before(order = 0)
    public static void initialization(Scenario scenario) {
        //   loadProperties();
        cScenario = scenario;
        Collection<String> tags = cScenario.getSourceTagNames();
        //  setScenario(scenario);
        launchUrl();
        initializeMap();
        pageFactoryLoader.loadPageFactory(driver);
        setScenario(scenario);
    }

    @BeforeStep
    public void afterBeforeStep(Scenario scenario) {
        System.out.println(scenario.getSourceTagNames());
        System.out.println("Starting " + scenario.getName());


    }

    @AfterStep
    public void afterEveryStep(Scenario scenario) {
        System.out.println("Completed " + scenario.getName());

        BaseClass.TakesScreenshot(scenario);

    }

    @After(order = 0)
    public void afterScenario(Scenario scenario) {
        fileUtil.createDirectoryAtProjectPath("reportLogs");
        driver.manage().deleteAllCookies();
        //   driver.quit();
        logger.info("Execution Completed for the scenario " + scenario.getName());

        BaseClass.TakesScreenshot(scenario);
    }

}
