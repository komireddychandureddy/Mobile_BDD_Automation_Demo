package mobileutil;

public class AndroidApplicationFunctions extends AndriodConstants {

	
	public String logging_step;

	
	public void landingPage()
	{
		isWebElementPresent(Orwell.login,"Verify Login page");
		//return flag;
	}
	public void clickLogin()
	{
		click(Orwell.login, "click on login button at landing page");	
			
	}
	

	public void verifyUsernamePage()
	{
		isWebElementPresent(Orwell.username,"click on username at landing page");
		
	}
		
		
		
				
		
		
	
			
}