package utils.actions;

import com.ui.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UIUtils extends BaseClass {
    public UIUtils(WebDriver driver) {
       BaseClass.driver= driver;
    }

    final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

    public void click(WebElement element){
        waitForElementTobeClickable(element);
    }

    public void waitForElementTobeClickable(WebElement element)
    {
        FluentWait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(60)).pollingEvery(Duration.ofSeconds(10)).ignoring(NoSuchElementException.class);

        wait.until(driver1 -> element.isEnabled());
        element.click();
    }
    public void waitForElementTobeDisplayed(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public boolean isElementVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void sleepFor(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void setValue(WebElement element, String value)
    {
        waitForElementForSeconds(element,80);
        element.sendKeys(value);
    }
    public void StoreValue(String key, String value) {
        gMap.put(key, value);
    }
    public String getValue(String key) {
        if (containsKey(key))
            return gMap.get(key);
        else
            return "";
    }

    public boolean containsKey(String key) {
        return gMap.containsKey(key);
    }
    public  void waitForElementForSeconds(WebElement element, int timeout){
        WebDriverWait wait1 = new WebDriverWait(driver,Duration.ofSeconds(timeout));
        wait1.until(ExpectedConditions.visibilityOf(element));
    }


}
