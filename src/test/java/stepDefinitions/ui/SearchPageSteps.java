package stepDefinitions.ui;

import com.ui.BaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.Map;

import static com.ui.pages.PageFactoryLoader.commonPage;

public class SearchPageSteps extends BaseClass {
    private static final Logger logger = LogManager.getLogger(SearchPageSteps.class);

    @Given("user launches the we buy any car website")
    public void user_launches_swag_labs() throws InterruptedException {
        logger.info("Launching 'We Buy Any Car' website.");

        commonPage.handleConsent();
        Assert.assertEquals(commonPage.getPageTitle(), "Sell your car in under an hour | Buy my car | webuyanycar");
    }


    @Given("user search the car details using {string} registration and mileage as {string}")
    public void user_search_the_car_details_using_registration(String registration, String mileage) throws InterruptedException {
        logger.info("Searching car details with registration: " + registration + " and mileage: " + mileage);
        commonPage.searchVehicle(registration, mileage);
    }

    @Then("verify the car details with registration Number {string}")
    public void verify_the_car_details_with(String registration) {
        logger.info("Verifying car details for registration number: " + registration);
        String fileName = "car_output.txt";
        commonPage.carDetails(registration, fileName);
        logger.info("Car details saved to file: " + fileName);

    }

    @Then("Compare the file values with car resale website")
    public void compare_the_file_values_with_car_resale_website() {
        logger.info("Comparing car details from file with car resale website values.");

        commonPage.compareTheVehicalDetails();
        logger.info("Comparison of car details completed.");

    }

}
