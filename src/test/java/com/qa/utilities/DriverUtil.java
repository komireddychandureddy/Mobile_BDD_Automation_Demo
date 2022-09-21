package com.qa.utilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.qa.mobileutil.MobileKeywords;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

/**
 * This DriverUtil class refer to browsers, os details, browser versions and
 * will close all browsers
 *
 */

public class DriverUtil extends GlobalUtil{

	public static String appium_ip_address=ConfigReader.getValue("appium_ip_address");
	public static String appium_port=ConfigReader.getValue("appium_port");
	public static DesiredCapabilities capabilities = new DesiredCapabilities();
	//public static AppiumDriver<?> driver = null;
	/**
	 * will use this getting browser(chrome, ie, ff)
	 * @param browserName
	 * @return
	 */
	public static AppiumDriver<MobileElement> getMobileApp(){
		File app=new File(USERDIR+ConfigReader.getValue("apkFilePath"));
		try {
			//capabilities.setCapability(MobileCapabilityType.BROWSER_NAME,"");
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, ConfigReader.getValue("DeviceName"));
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, GlobalUtil.getCommonSettings().getAndroidVersion());
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,ConfigReader.getValue("platformName"));			
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,ConfigReader.getValue("automationName"));			
			capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,"180");
			//capabilities.setCapability("appWaitActivity",ConfigReader.getValue("Activity"));
			capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, ConfigReader.getValue("Activity"));
			//capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, ConfigReader.getValue("Activity"));
			//capabilities.setCapability("appActivity", ".ui.activity.IntroSlideShowActivity");
			capabilities.setCapability("autoGrantPermissions",true);
			capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
			driver = new AndroidDriver<MobileElement>(new URL("http://"+appium_ip_address+":"+appium_port+"/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return driver;
	}
	
	public static AppiumDriver<MobileElement> getIosMobileApp(){
		File app = new File(ConfigReader.getValue("IosFilePath"));
		
		try {
			capabilities.setCapability(MobileCapabilityType.BROWSER_NAME,"");
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, GlobalUtil.getCommonSettings().getIosName());
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, GlobalUtil.getCommonSettings().getIosVersion());
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,ConfigReader.getValue("Ios_platformName"));			
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,ConfigReader.getValue("IOS_XCUI_TEST"));			
			//capabilities.setCapability(MobileCapabilityType.UDID,GlobalUtil.getCommonSettings().getAndroidID());
			capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,ConfigReader.getValue("newCommandTimeout"));
			capabilities.setCapability("autoGrantPermissions",true);
			if(ConfigReader.getValue("realDevice").equals("Y")){
				capabilities.setCapability(MobileCapabilityType.UDID, ConfigReader.getValue("iosID"));
			}
			capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
			driver = new IOSDriver<MobileElement>(new URL("http://"+appium_ip_address+":"+appium_port+"/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return driver;
	}

}// End class
