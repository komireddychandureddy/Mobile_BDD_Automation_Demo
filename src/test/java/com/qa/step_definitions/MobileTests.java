package com.qa.step_definitions;

import org.testng.Assert;

import com.qa.mobileutil.AndroidApplicationFunctions;
import com.qa.utilities.GlobalUtil;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class MobileTests extends AndroidApplicationFunctions{

public static String currency="";


@Given("^I launched the application$")
public void i_launched_the_application() {
	
		landingPage();
	
}

@Given("^I click on login Button\\.$")
public void i_click_on_login_Button(){
	try{
		clickLogin();
	}
catch (Exception e) {
	GlobalUtil.ErrorMsg = e.getMessage();
	Assert.fail(e.getMessage());
} 
}

@Then("^I should be able to see textfiled emailid$")
public void i_should_be_able_to_see_textfiled_emailid() {
	try{
		verifyUsernamePage();
	}
catch (Exception e) {
	GlobalUtil.ErrorMsg = e.getMessage();
	Assert.fail(e.getMessage());
} 
}


}
