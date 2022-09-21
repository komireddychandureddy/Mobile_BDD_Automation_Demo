package step_definitions;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import utilities.DriverUtil;
import utilities.GlobalUtil;
import utilities.LogUtil;

public class Hooks {

	String imagePath;
	String pathForLogger;
	String testCaseDescription;

	@Before("@MobileTest")
	public void beforeMobileMethod(Scenario scenario) {

		testCaseDescription = scenario.getName();
		RunCukesTest.logger = RunCukesTest.extent.startTest(testCaseDescription);

		LogUtil.infoLog(getClass(),
				"\n+----------------------------------------------------------------------------------------------------------------------------+");
		LogUtil.infoLog(getClass(), "Mobile Tests Started: " + scenario.getName());

		LogUtil.infoLog("Mobile Test Environment",
				"Mobile Test is executed in OS: " + GlobalUtil.getCommonSettings().getAndroidName());
		
		GlobalUtil.setDriver(DriverUtil.getMobileApp());
	//	GlobalUtil.setDriver(DriverUtil.getIosMobileApp());
		
	}
	
	@After("@MobileTest")
	public void afterMobileMethod(Scenario scenario){
	GlobalUtil.getDriver().quit();
	}



	}


