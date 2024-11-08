package com.ui.pages;

import org.openqa.selenium.WebDriver;

public class PageFactoryLoader {

    public static CommonPage commonPage;

    public void loadPageFactory(WebDriver driver)
    {

         commonPage = new CommonPage(driver);
    }


}
