package com.qa.step_definitions;

import java.io.File;
import java.util.Date;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.qa.utilities.ConfigReader;
import com.qa.utilities.ExcelDataUtil;
import com.qa.utilities.GlobalUtil;
import com.qa.utilities.KeywordUtil;
import com.qa.utilities.LogUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
@CucumberOptions(features = "classpath:features", plugin = { "pretty", "html:target/cucumber-html-report","json:target/cucumber.json" }, 
tags = { "@MobileTest" })
public class RunCukesTest extends AbstractTestNGCucumberTests{
	
	static ExtentReports extent;
	public static ExtentTest logger;

	@BeforeClass
	public void onStart() {
		try {
			
			extent = new ExtentReports(System.getProperty("user.dir") + ConfigReader.getValue("extentReportPath"));
			extent.loadConfig(new File(System.getProperty("user.dir") + ConfigReader.getValue("extentConfigPath")));
			// Get all the common setting from excel file that are required for
			GlobalUtil.setCommonSettings(ExcelDataUtil.getCommonSettings());

			String browser = "";
			browser = GlobalUtil.getCommonSettings().getBrowser();
			System.out.println(browser);

			String executionEnv = "";
			executionEnv = GlobalUtil.getCommonSettings().getExecutionEnv();

			String url = "";
			url = GlobalUtil.getCommonSettings().getUrl();

			if (browser == null) {
				browser = ConfigReader.getValue("defaultBrowser");
			}

			if (executionEnv == null) {
				executionEnv = ConfigReader.getValue("defaultExecutionEnvironment");
			}			

			
			if (url == null) {
				url = ConfigReader.getValue("BASE_URL");
				GlobalUtil.getCommonSettings().setUrl(url);
			}
			LogUtil.infoLog(getClass(),
					"\n\n+===========================================================================================================+");
			LogUtil.infoLog(getClass()," Suite started" + " at " + new Date());
			LogUtil.infoLog(getClass(), "Suite Execution For Web application on environment : " + executionEnv);
			LogUtil.infoLog(getClass(), "Suite Execution For Android mobile application on version: " + GlobalUtil.getCommonSettings().getAndroidVersion());
			LogUtil.infoLog(getClass(), "Suite Execution For RestAPI on URL: " + GlobalUtil.getCommonSettings().getRestURL());
			LogUtil.infoLog(getClass(),
					"\n\n+===========================================================================================================+");


		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.errorLog(getClass(), "Common Settings not properly set may not run the scripts properly");
		}
	}

	@AfterClass
	public void onFinish() {
		LogUtil.infoLog(getClass()," suite finished" + " at " + new Date());
		LogUtil.infoLog(getClass(),
				"\n\n+===========================================================================================================+");
		extent.flush();
		extent.close();
		
		KeywordUtil.onExecutionFinish();
	}

}