package org;


import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import uat.Base;


public class Tests extends Base {
    @Test
    public void tests(){
    	//create test
    	ExtentTest test = extent.createTest("Tc001");
    	test.pass("Navigation to google");
    	test.assignAuthor("Yethin");
       sleep(2000);
    }
    
    @Test
	public void typeGoogle(){
		String value = propertyRead("login.properties","search");
		elementInput(".a4bIc > input[role='combobox']",value);
	}



}
