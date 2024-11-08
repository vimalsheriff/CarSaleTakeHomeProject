package com.ui.pages;

import com.ui.BaseClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.actions.UIUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonPage extends BaseClass {

    private static final Logger logger = LogManager.getLogger(CommonPage.class);

    HashMap<String, String> actualValues = new HashMap<>();
    Map<String, String> ExpectedVehicleDetails = new HashMap<>();

    public CommonPage(WebDriver driver) {
        BaseClass.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public UIUtils utils = new UIUtils(driver);

    @FindBy(name = "vehicleReg")
    public WebElement enterReg;

    @FindBy(name = "Mileage")
    public WebElement mileageInputBox;

    @FindBy(id = "btn-go")
    public WebElement getMyCarButton;

    @FindBy(id = "onetrust-accept-btn-handler")
    public WebElement consentAccept;


    @FindBy(xpath = "(//div[@class='d-table-row details-vehicle-row ng-star-inserted']//div)")
    public List<WebElement> getCarDetails;

    @FindBy(xpath = "(//div[@class='details-image']//following-sibling::div)[4]")
    public WebElement registrationValue;
    @FindBy(xpath = "(//div[contains(text(),'Manufacturer:')])[2]//following-sibling::div")
    public WebElement manufacturerValue;

    @FindBy(xpath = "(//div[contains(text(),'Model:')])[2]//following-sibling::div")
    public WebElement modelValue;

    @FindBy(xpath = "(//div[contains(text(),'Year:')])[2]//following-sibling::div")
    public WebElement yearValue;


    public void searchVehicle(String registrationNumber, String mileage) throws InterruptedException {
        logger.info("Initiating vehicle search with registration: {} and mileage: {}", registrationNumber, mileage);

        Thread.sleep(6000);
        utils.setValue(enterReg, registrationNumber);
        logger.debug("Entered registration number: {}", registrationNumber);

        Thread.sleep(3000);
        utils.setValue(mileageInputBox, mileage);
        utils.click(getMyCarButton);
        logger.debug("Entered mileage: {}", mileage);

        StepTakesScreenshot(cScenario);
        checkIfConsetVisible(utils.isElementVisible(consentAccept));
    }

    public void handleConsent() {
        logger.info("Handling consent pop-up.");

        utils.waitForElementTobeDisplayed(consentAccept);
        consentAccept.click();
        logger.info("Consent accepted.");

    }


    public String getPageTitle() {
        String title = driver.getTitle();
        logger.debug("Retrieved page title: {}", title);
        return title;
    }

    public void checkIfConsetVisible(boolean value) {
        if (value) {
            logger.info("Consent pop-up is visible. Clicking to accept.");

            consentAccept.click();
        } else {
            logger.info("No consent pop-up visible.");
        }
    }


    public void carDetails(String variant, String fileName) {
        try {
            logger.info("Fetching car details for variant: {} and saving to file: {}", variant, fileName);

            ExpectedVehicleDetails = getVehicleDetails(variant, fileName);

            if (ExpectedVehicleDetails != null) {
                logger.debug("Vehicle Details for variant {}: {}", variant, ExpectedVehicleDetails);
                System.out.println("Make: " + ExpectedVehicleDetails.get("VARIANT_REG"));
                System.out.println("Make: " + ExpectedVehicleDetails.get("MAKE"));
                System.out.println("Model: " + ExpectedVehicleDetails.get("MODEL"));
                System.out.println("Year: " + ExpectedVehicleDetails.get("YEAR"));
            } else {
                System.out.println("No vehicle found for VARIANT_REG: " + variant);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        actualValues.put("ActualVehicleDetails_registrationValue", registrationValue.getText());
        actualValues.put("ActualVehicleDetails_manufacturerValue", manufacturerValue.getText());
        actualValues.put("ActualVehicleDetails_modelValue", modelValue.getText());
        actualValues.put("ActualVehicleDetails_yearValue", yearValue.getText());

        System.out.println("Actual Value " + actualValues);


    }

    public static Map<String, String> getVehicleDetails(String variantReg, String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/car_output.txt"));

        String line;
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] columns = line.split(",");

            if (columns[0].trim().equalsIgnoreCase(variantReg)) {
                Map<String, String> vehicleDetails = new HashMap<>();
                vehicleDetails.put("VARIANT_REG", columns[0].trim());
                vehicleDetails.put("MAKE", columns[1].trim());
                vehicleDetails.put("MODEL", columns[2].trim());
                vehicleDetails.put("YEAR", columns[3].trim());
                reader.close();
                return vehicleDetails;
            }
        }

        reader.close();
        return null;
    }

    public void compareTheVehicalDetails() {

        // Assert for VARIANT_REG
        Assert.assertEquals(actualValues.get("ActualVehicleDetails_registrationValue"),
                ExpectedVehicleDetails.get("VARIANT_REG"),
                "Expected VARIANT_REG does not match");

        // Assert for MAKE
        Assert.assertEquals(actualValues.get("ActualVehicleDetails_manufacturerValue"),
                ExpectedVehicleDetails.get("MAKE"),
                "Expected MAKE does not match");

        // Assert for MODEL
        Assert.assertEquals(actualValues.get("ActualVehicleDetails_modelValue"),
                ExpectedVehicleDetails.get("MODEL"),
                "Expected MODEL does not match");

        // Assert for YEAR
        Assert.assertEquals(actualValues.get("ActualVehicleDetails_yearValue"),
                ExpectedVehicleDetails.get("YEAR"),
                "Expected YEAR does not match");
    }
}
