package com.qa.mobileutil;

public class AndroidApplicationFunctions extends AndriodConstants {

	
	public String logging_step;

	
	public void landingPage()
	{
		isWebElementPresent(Testg.login,"Verify Login page");
		//return flag;
	}
	public void clickLogin()
	{
		click(Testg.login, "click on login button at landing page");	
			
	}
	

	public void verifyUsernamePage()
	{
		isWebElementPresent(Testg.username,"click on username at landing page");
		
	}
		
		
		
				
		
		
	
			
}