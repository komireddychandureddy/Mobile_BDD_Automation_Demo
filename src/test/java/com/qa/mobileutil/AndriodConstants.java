package com.qa.mobileutil;

import org.openqa.selenium.By;

import com.qa.step_definitions.MobileTests;

public class AndriodConstants extends MobileKeywords{
	public static class Testg {

	
		public static  By login = By.xpath("//*[@resource-id='com.testg.white.label.dev:id/landing_login_button']");
		public static  By username = By.xpath("//android.widget.EditText[@resource-id='com.testg.white.label.dev:id/onBoarding_entryField_editText']");
        public static  By password = By.xpath("//android.widget.EditText[@resource-id='com.testg.white.label.dev:id/onBoarding_entryField_editText']");
        public static  By memorableword1 = By.xpath("//*[text()='6th']/following-sibling::android.widget.EditText");
        public static  By memorableword2 = By.xpath("//*[text()='8th']/following-sibling::android.widget.EditText");
        public static  By memorableword3 = By.xpath("//*[text()='10th']/following-sibling::android.widget.EditText");
        public static  By yourMoney=By.xpath("//*[text()='Your money']/following-sibling::android.widget.ImageView");
        public static  By continueButoon =By.xpath("//*[text()='CONTINUE']");
        public static  By currency = By.xpath("//*[text()='" + MobileTests.currency + "']");
        
	}

}
