package runner.webRunner;

import static com.ui.BaseClass.loadProperties;

import com.ui.BaseClass;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.junit.runner.RunWith;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;


@CucumberOptions(
        features = "src/test/resources/features",
        glue={"stepDefinitions/ui","stepDefinitions/support/ui"},
        plugin={ "pretty","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
        "html:target/report/cucumber.html",
                "json:target/report/cucumber.json"  },
      //  plugin={"pretty","
        dryRun = false,
        tags = "@Resale"
)

public class TestUIRunner extends AbstractTestNGCucumberTests {

    String cucumberTag;
    @BeforeSuite
   public void setUp()
   {
       loadProperties();
       System.out.println("Launching Browser");
       BaseClass.launchBrowser();
   }


   @AfterSuite
    public void tearDown(){
        BaseClass.closeBrowser();
   }

}
