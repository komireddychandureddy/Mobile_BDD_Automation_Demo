package com.qa.mobileutil;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import com.qa.step_definitions.RunCukesTest;
import com.qa.utilities.ConfigReader;
import com.qa.utilities.GlobalUtil;
import com.qa.utilities.HTMLReportUtil;
import com.qa.utilities.KeywordUtil;
import com.qa.utilities.LogUtil;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class MobileKeywords extends KeywordUtil {
	public static Dimension size;
	public static int fail = 0;
	public static boolean flag1 = false;
	public static String logging_step;
	static int row = 1;
	
	public static DesiredCapabilities capabilities = new DesiredCapabilities();

	public void logStepPass(String logStep) {
		LogUtil.infoLog(this.getClass(), logStep + "-PASS ");
		RunCukesTest.logger.log(LogStatus.PASS, HTMLReportUtil.passStringGreenColor(logStep));
	}
	public void logStepFail(String logStep) {
		LogUtil.infoLog(this.getClass(), logStep + "-FAIL ");
		RunCukesTest.logger.log(LogStatus.FAIL, HTMLReportUtil.failStringRedColor(logStep));		
	}

	@SuppressWarnings("unchecked")
	public static boolean isWebElementPresent(By locator) {
		Boolean flag = false;
		int timeOut = 0;
		while (timeOut < ConfigReader.getIntValue("explicit_timeout")) {
			
			List<MobileElement> ele = getDriver().findElements(locator);
			if (ele.size() > 0) {
				flag = true;
				break;
			}
			pause(1000);
			timeOut = timeOut + 2;
		}
		return flag;
	}
	public boolean isWebElementPresent1(By locator, String logstep) {
		Boolean flag = false;
		int timeOut = 0;
		while (timeOut < ConfigReader.getIntValue("explicit_timeout")) {
			List<MobileElement> ele = getDriver().findElements(locator);

			if (ele.size() > 0) {
				flag = true;
				break;
			}
			pause(100);
			timeOut = timeOut + 2;
		}

		return flag;

	}

	public void isWebElementPresent(By locator, String logstep) {

		if (isWebElementPresent1(locator, logstep)) {
			logStepPass(logstep);

		} else {
			logStepFail(logstep);
		}

	}

	public boolean isWebElementPresent(By locator, int maxWaitTime) {
		Boolean flag = false;
		int timeOut = 0;
		while (timeOut < maxWaitTime) {
			if (getDriver().findElements(locator).size() > 0) {
				flag = true;
				break;
			}
			pause(2000);
			timeOut = timeOut + 2;

		}

		return flag;

	}

	public boolean hideKeyboard() {

		getDriver().hideKeyboard();
		return true;

	}

	public boolean type(By locator, String data, String logstep) {

		WebElement element = getWebElement(locator);
		if (element == null) {
			logStepFail(logstep);
			return false;
		} else {
			element.sendKeys(data);
			hideKeyboard();
			logStepPass(logstep);
			return true;
		}

	}

	public static boolean enter() throws IOException {
		Runtime.getRuntime().exec("adb shell input keyevent 66");
		return true;

	}

	public static boolean clearAppData() throws IOException {
		Runtime.getRuntime().exec("adb shell pm clear com.cloudfmgroup.cloudFMApp");
		return true;

	}

	public static WebElement getWebElement(By locator) {

		return getDriver().findElement(locator);
	}
	public void click(By locator, String logstep) {
		try {
			isWebElementPresent(locator);
			WebElement element = getWebElement(locator);
			element.click();
			logStepPass(logstep);
		} catch (Exception e) {
			logStepFail(logstep);
			GlobalUtil.ErrorMsg = e.getMessage();
			Assert.fail(e.getMessage());
		}
	}
	public void click2(By locator, String logstep) {
		WebElement element = getWebElement(locator);
		if (element == null) {
			logStepFail(logstep);
		} else {
			element.click();
			logStepPass(logstep);
		}
	}
	public boolean clearInput(By locator, String logstep) {
		WebElement element = getWebElement(locator);
		if (element == null) {
			logStepFail(logstep);
		} else {
			element.clear();
			logStepPass(logstep);
		}
		return true;
	}
	public static boolean switchContext() {
		boolean colFlag1 = false;
		Set<String> contextNames = getDriver().getContextHandles();
		for (String contextName : contextNames) {
			if (contextName.contains("WEBVIEW")) {
				getDriver().context(contextName);
				colFlag1 = true;
				break;
			}
		}
		return colFlag1;
	}

	public static void switchFrame(int frame) {
		getDriver().switchTo().frame(frame);
	}
	public static void switchParentFrame(int frame) {
		getDriver().switchTo().parentFrame();
	}

	public static boolean switchContext1() {
		boolean colFlag1 = false;
		Set<String> contextNames = getDriver().getContextHandles();
		for (String contextName : contextNames) {
			if (contextName.contains("NATIVE")) {
				getDriver().context(contextName);
				// System.out.println("switched to native");
				colFlag1 = true;
				break;
			}
		}
		return colFlag1;
	}
	public static void HideKeyboard() {
		getDriver().hideKeyboard();
	}
	public static String GetTextOfElement(By value) {
		WebElement element = getDriver().findElement(value);
		return element.getText();
	}
	public static String verifyCurrentDate() {

		DateFormat DtFormat = new SimpleDateFormat("MMM dd yyyy");
		Date date = new Date();
		DtFormat.setTimeZone(TimeZone.getTimeZone("BST"));
		String expected = DtFormat.format(date).toString().trim();
		return expected;
	}
	public static String currentdateWithDay() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date1 = new Date();
		String expected = dateFormat.format(date1).toString().trim();
		String[] dateArray = expected.split("/");
		int year = Integer.parseInt(dateArray[0]);
		int month = Integer.parseInt(dateArray[1]);
		int day = Integer.parseInt(dateArray[2]);
		String dateString = String.format("%d-%d-%d", year, month, day);
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
		String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
		return dayOfWeek;
	}

	public String GetCurrentDate(String path, String type) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM d YYYY");
		Date d = new Date();
		String Currentdate = sdf.format(d);
		return Currentdate;
	}

	public static boolean isValidFormat(String format, String value, Locale locale) {
		LocalDateTime ldt = null;
		DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(format, locale);
		try {
			ldt = LocalDateTime.parse(value, fomatter);
			String result = ldt.format(fomatter);
			return result.equals(value);
		} catch (DateTimeParseException e) {
			try {
				LocalDate ld = LocalDate.parse(value, fomatter);
				String result = ld.format(fomatter);
				return result.equals(value);
			} catch (DateTimeParseException exp) {
				try {
					LocalTime lt = LocalTime.parse(value, fomatter);
					String result = lt.format(fomatter);
					return result.equals(value);
				} catch (DateTimeParseException e2) {
					 e2.printStackTrace();
				}
			}
		}

		return false;
	}

	public boolean isValidFormat(String value, String logstep) {
		boolean checkFormat;
		String regex = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		if (matcher.matches()) {
			logStepPass(logstep);

			checkFormat = true;
		} else {
			logStepFail(logstep);
			checkFormat = false;
		}
		return checkFormat;
	}

	public void verifyTwoValues(String expected, String actual) {
		if (actual.contains(expected)) {
			logStepPass("Text : " + actual);
		} else {
			logStepFail("Actual Text : " + actual + " Expected Text :" + expected);
			Assert.fail();
		}
	}

	public void verifyTwoValuesEqual(String expected, String actual) {

		if (actual.equalsIgnoreCase(expected)) {
			logStepPass("Text : " + actual);
		} else {
			logStepFail("Actual Text : " + actual + " Expected Text :" + expected);
			Assert.fail();
		}
	}

	public int getRandomNumber(int min, int max) {
		Random rand = new Random();
		return rand.nextInt(max) + min;
	}

	public String randomString() {
		byte[] array = new byte[7]; // length is bounded by 7
		new Random().nextBytes(array);
		String generatedString = new String(array, Charset.forName("UTF-8"));
		return generatedString;
	}

	public String generatingRandomString() {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		String generatedString = buffer.toString();
		return generatedString;
	}

	public String randomString2() {
		int length = 10;
		boolean useLetters = true;
		boolean useNumbers = false;
		String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
		return generatedString;
	}
	public String randomAlphabetic() {
		String generatedString = RandomStringUtils.randomAlphabetic(10);
		return generatedString;
	}
	public String randomAlphanumeric() {
		String generatedString = RandomStringUtils.randomAlphanumeric(10);
		return generatedString;
	}

	public String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 18) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}

	public static String GetMondayOfCurrentWeek() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String expecteddate = c.getTime().toString().trim();
		String[] expecteddateArray = expecteddate.split(" ");
		expecteddate = expecteddateArray[0] + " " + expecteddateArray[1] + " " + expecteddateArray[2] + " "
				+ expecteddateArray[5];
		return expecteddate;
	}

	public static String GetTuesdayOfCurrentWeek() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		String expecteddate = c.getTime().toString().trim();
		String[] expecteddateArray = expecteddate.split(" ");
		expecteddate = expecteddateArray[0] + " " + expecteddateArray[1] + " " + expecteddateArray[2] + " "
				+ expecteddateArray[5];
		return expecteddate;
	}

	public static String GetWednesdayOfCurrentWeek() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		String expecteddate = c.getTime().toString().trim();
		String[] expecteddateArray = expecteddate.split(" ");
		expecteddate = expecteddateArray[0] + " " + expecteddateArray[1] + " " + expecteddateArray[2] + " "
				+ expecteddateArray[5];
		return expecteddate;
	}

	public static String GetThursdayOfCurrentWeek() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		String expecteddate = c.getTime().toString().trim();
		String[] expecteddateArray = expecteddate.split(" ");
		expecteddate = expecteddateArray[0] + " " + expecteddateArray[1] + " " + expecteddateArray[2] + " "
				+ expecteddateArray[5];
		return expecteddate;
	}

	public static String GetFridayOfCurrentWeek() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		String expecteddate = c.getTime().toString().trim();
		String[] expecteddateArray = expecteddate.split(" ");
		expecteddate = expecteddateArray[0] + " " + expecteddateArray[1] + " " + expecteddateArray[2] + " "
				+ expecteddateArray[5];
		return expecteddate;
	}

	public static String GetSaturdayOfCurrentWeek() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		String expecteddate = c.getTime().toString().trim();
		String[] expecteddateArray = expecteddate.split(" ");
		expecteddate = expecteddateArray[0] + " " + expecteddateArray[1] + " " + expecteddateArray[2] + " "
				+ expecteddateArray[5];
		return expecteddate;
	}

	public static String GetSundayOfCurrentWeek() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		String expecteddate = c.getTime().toString().trim();
		String[] expecteddateArray = expecteddate.split(" ");
		expecteddate = expecteddateArray[0] + " " + expecteddateArray[1] + " " + expecteddateArray[2] + " "
				+ expecteddateArray[5];
		return expecteddate;
	}
	public void runBackGround(Duration time) {
		getDriver().runAppInBackground(time);
	}
	public static void launchBrowser(String BrowserName) {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserName);
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, ConfigReader.getValue("deviceName"));
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, ConfigReader.getValue("platformVersion"));
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, ConfigReader.getValue("platformName"));
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, ConfigReader.getValue("automationName"));
		// capabilities.setCapability(MobileCapabilityType.UDID,Utility.GetValue("deviceID"));
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,
				ConfigReader.getValue("newCommandTimeout"));
		capabilities.setCapability("autoGrantPermissions", true);
		capabilities.setCapability("autoAcceptAlerts", true);
		// capabilities.setCapability("locationServicesAuthorized", true);
		capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
		capabilities.setCapability("autowebview", true);
		// capabilities.setCapability(AndroidMobileCapabilityType.RECREATE_CHROME_DRIVER_SESSIONS, true);
		String chromedriver = System.getProperty("user.dir") + "\\src\\main\\resources\\Driver\\chromegetDriver().exe";
		capabilities.setCapability("chromedriverExecutable", chromedriver);
		AndroidDriver driver2 = null;
		try {
			driver2 = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		driver2.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	public String getFutureDate1(int value) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate today = LocalDate.now();
		LocalDate future = today.plus(value, ChronoUnit.DAYS);
		return dtf.format(future);
	}

	public String getPastDate1(int value) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate today = LocalDate.now();
		LocalDate past = today.minusDays(value);
		return dtf.format(past);
	}

	public String getText(By locator) {
		String s = null;
		WebElement element = getWebElement(locator);
		if (element == null) {
			logStepFail("Text : " + s);
		} else {
			s = element.getText();
			logStepPass("Text : " + s);
		}
		return s;
	}

	/* To ScrollUp using JavaScript Executor */
	public void scrollUp(By locator, String message) throws Exception {
		((JavascriptExecutor) getDriver()).executeScript("scroll(0, -100);");
	}

	/* To ScrollDown using JavaScript Executor */
	public void scrollDown(By locator, String message) throws Exception {
		((JavascriptExecutor) getDriver()).executeScript("scroll(0, 100);");
	}

	/* To click a certain Web Element */
	public void click(WebElement element, String message) {
		if (element == null) {
			logStepFail(message);
		} else {
			element.click();
			pause(300);
			logStepPass(message);
		}
	}

	public void compareTwoValues(String actual, String expected) {
		if (actual.toLowerCase().contains(expected.toLowerCase())) {
			logStepPass("Found actual value " + actual + " and Expected value : " + expected);
		} else {
			logStepFail("Found actual value " + actual + " and Expected value : " + expected);
			Assert.fail();
		}
	}
	public void compareTwoValuesIgnoreCase(String actual, String expected) {
		if (actual.equalsIgnoreCase(expected)) {
			logStepPass("Found actual value :" + actual + " and Expected value : " + expected);
		} else {
			logStepFail("Found actual value :" + actual + " and Expected value : " + expected);
			Assert.fail();
		}
	}

	/**
	 * @param locator
	 * @param data
	 * @return
	 */
	public void inputText(By locator, String data, String logStep) {

		WebElement elm = getWebElement(locator);
		if (elm == null) {
			logStepFail(logStep);
		} else {
			elm.clear();
			elm.sendKeys(data);
			logStepPass(logStep);
		}
	}

	public boolean isWebElementNotPresent1(By locator, String logstep) {
		boolean status = getDriver().findElement(locator).isDisplayed();
		if (!status) {
			logStepPass(logstep);
			return true;
		} else {
			logStepFail(logstep);
			return false;
		}
	}

	/**
	 * @param a
	 * @throws InterruptedException
	 */
	public static void pause(long a) {
		try {
			Thread.sleep(a);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void highLightElement(By locator) {
		WebElement element = getDriver().findElement(locator);
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		js.executeScript("arguments[0].setAttribute('style','');", element);
		// border: solid 2px white
	}

	public void highlightElement(By locator) {
		WebElement element = getDriver().findElement(locator);
		for (int i = 0; i < 2; i++) {
			JavascriptExecutor js = (JavascriptExecutor) getDriver();
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
					"color: yellow; border: 2px solid yellow;");
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
		}
	}

	public void highLightElement1(By locator) {
		WebElement element = getDriver().findElement(locator);
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {

			System.out.println(e.getMessage());
		}

		js.executeScript("arguments[0].setAttribute('style','');", element);
		// border: solid 2px white
	}

	public boolean waitUntilElement(By type, int timeOut) {
		int time = 0;
		boolean status = false;
		while (time < timeOut) {
			status = getDriver().findElements(type).size() > 0;
			if (status) {
				status = true;
				highLightElement(type);
				break;
			}
			time = time + 1;
			pause(1000);
		}
		return status;
	}
	// Get Tag name and locator value of Element
	public static String getElementInfo(By locator) throws Exception {
		return " Locator: " + locator.toString();
	}
	public static String getElementInfo(WebElement element) throws Exception {
		String webElementInfo = "";
		webElementInfo = webElementInfo + "Tag Name: " + element.getTagName() + ", Locator: ["
				+ element.toString().substring(element.toString().indexOf("->") + 2);
		return webElementInfo;
	}

	public boolean enterNumber(By locator, String data) {
		boolean result = false;
		try {
			WebElement element = getDriver().findElement(locator);
			Actions oAction = new Actions(getDriver());
			oAction.click(element);
			oAction.sendKeys(Keys.BACK_SPACE).sendKeys(data).build().perform();
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	public boolean clickAndHold2(By locator1, By locator2) {
		boolean result = false;
		try {
			// KeywordUtil.lastAction="Double click: "+locator.toString();
			// LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
			WebElement element1 = getDriver().findElement(locator1);
			WebElement element2 = getDriver().findElement(locator2);
			Actions action = new Actions(getDriver());
			action.clickAndHold(element1).moveToElement(element2).release().build().perform();
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	public boolean dragAndDrop(By locator1, By locator2) {
		boolean result = false;
		try {
			// KeywordUtil.lastAction="Double click: "+locator.toString();
			// LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
			WebElement element1 = getDriver().findElement(locator1);
			WebElement element2 = getDriver().findElement(locator2);
			Actions action = new Actions(getDriver());
			action.dragAndDrop(element1, element2).build().perform();
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	public boolean dragAndDropBy(By locator1, By locator2) {
		boolean result = false;
		try {
			// KeywordUtil.lastAction="Double click: "+locator.toString();
			// LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
			WebElement element1 = getDriver().findElement(locator1);
			WebElement element2 = getDriver().findElement(locator2);
			Point classname = element2.getLocation();
			int xcordi = classname.getX();
			int ycordi = classname.getY();
			Actions action = new Actions(getDriver());
			action.dragAndDropBy(element1, xcordi, ycordi).build().perform();
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	public int getCoordinates(By locator) throws Exception {
		// Locate element for which you wants to retrieve x y coordinates.
		WebElement Image = getDriver().findElement(locator);
		// Used points class to get x and y coordinates of element.
		Point classname = Image.getLocation();
		int xcordi = classname.getX();
		int ycordi = classname.getY();
		return xcordi + ycordi;
	}

	public int getElementSize(By locator) throws Exception {
		// Locate element for which you wants to get height and width.
		WebElement Image = getDriver().findElement(locator);
		// Get width of element.
		int ImageWidth = Image.getSize().getWidth();
		// Get height of element.
		int ImageHeight = Image.getSize().getHeight();
		return ImageWidth + ImageHeight;
	}

	public int getElementXSize(By locator) throws Exception {
		// Locate element for which you wants to get height and width.
		WebElement Image = getDriver().findElement(locator);
		// Get width of element.
		int ImageWidth = Image.getSize().getWidth();
		return ImageWidth;
	}

	public int getElementYSize(By locator) throws Exception {
		// Locate element for which you wants to get height and width.
		WebElement Image = getDriver().findElement(locator);

		// Get height of element.
		int ImageHeight = Image.getSize().getHeight();
		System.out.println("Image height Is " + ImageHeight + " pixels");
		return ImageHeight;
	}
	public String getRandomMailinatorEmail(String data) {
		// String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String SALTCHARS = "abcdefghijklmnopqrstuvwxyz";

		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 10) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return data + saltStr + "@mailinator.com";

	}

	public String getRandomString1(String data) {
		// String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String SALTCHARS = "abcdefghijklmnopqrstuvwxyz";

		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 10) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr + data;

	}
	
	public String todayDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
		String date = format1.format(cal.getTime());
		return date;

	}

	public String getTodayDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
		String todatdate = sdf.format(date);
		return todatdate;
	}

	public String getTodayDate(String dateformat) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
		String todatdate = sdf.format(date);
		return todatdate;
	}

	public String getYearDate(String dateformat) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Now use today date.
		c.add(Calendar.YEAR, 1); // Adds 365 days
		SimpleDateFormat format1 = new SimpleDateFormat(dateformat);
		String date = format1.format(c.getTime());
		return date;
	}

	public String getTomorrowDate(String dateformat) {

		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Now use today date.
		c.add(Calendar.DATE, 1); // Adds 7 days
		SimpleDateFormat format1 = new SimpleDateFormat(dateformat);
		String date = format1.format(c.getTime());
		return date;
	}

	public String getweekDate(String dateformat) {

		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Now use today date.
		c.add(Calendar.DATE, 7); // Adds 7 days
		SimpleDateFormat format1 = new SimpleDateFormat(dateformat);
		String date = format1.format(c.getTime());
		return date;
	}

	public String getFutureDateTime(String dateformat, int days) {

		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Now use today date.
		c.add(Calendar.DATE, days); // Adds 7 days
		SimpleDateFormat format1 = new SimpleDateFormat(dateformat);
		String date = format1.format(c.getTime());
		return date;
	}

	public String getTodayDateTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public String getTodayDateOnly() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate today = LocalDate.now();
		return dtf.format(today);
	}

	public String getFutureDate(int value) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate today = LocalDate.now();
		LocalDate future = today.plus(value, ChronoUnit.DAYS);
		return dtf.format(future);
	}

	public String getPastDate(int value) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate today = LocalDate.now();
		LocalDate past = today.minusDays(value);
		return dtf.format(past);
	}

	public String getWeekDate(String dateformat) {

		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Now use today date.
		c.add(Calendar.DATE, 7); // Adds 7 days
		SimpleDateFormat format1 = new SimpleDateFormat(dateformat);
		String date = format1.format(c.getTime());
		return date;
	}

	public String getTwoWeekDate(String dateformat) {

		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Now use today date.
		c.add(Calendar.DATE, 14); // Adds 4 days
		SimpleDateFormat format1 = new SimpleDateFormat(dateformat);
		String date = format1.format(c.getTime());
		return date;
	}

	public String getTenDaysDate(String dateformat) {

		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Now use today date.
		c.add(Calendar.DATE, 10); // Adds 4 days
		SimpleDateFormat format1 = new SimpleDateFormat(dateformat);
		String date = format1.format(c.getTime());
		return date;
	}

	public String getTime(String dateformat) {
		DateFormat dateFormat = new SimpleDateFormat(dateformat);
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String getCurrentTime() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String getCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		String currentMonth = new SimpleDateFormat("MMM").format(cal.getTime());
		return currentMonth;
	}

	public String getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		String currentyear = new SimpleDateFormat("YYYY").format(cal.getTime());
		return currentyear;
	}

	public String getDate() {
		Calendar cal = Calendar.getInstance();
		String curerntdate = new SimpleDateFormat("dd").format(cal.getTime());
		return curerntdate;
	}

	public String getRandomEmail(String data) {
		// String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String SALTCHARS = "abcdefghijklmnopqrstuvwxyz";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 10) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return data + saltStr + getRandomNumber(100, 99999) + "@malinator.com";

	}

	public boolean waitForElement(By locator, int maxtime) {
		int time = 0;
		boolean status = false;
		while (time < maxtime) {
			status = getDriver().findElements(locator).size() > 0;
			if (status) {
				status = true;
				break;
			}
			time = time + 2;
			pause(2000);
		}
		return status;
	}

	public boolean waitForElement(By locator) {
		int time = 0;
		boolean status = false;
		while (time < 15) {
			status = getDriver().findElements(locator).size() > 0;
			if (status) {
				status = true;
				highLightElement(locator);
				break;
			}
			time = time + 1;
			pause(1000);
		}
		return status;
	}
	public String alertText() {
		String text = getDriver().switchTo().alert().getText();
		LogUtil.infoLog(this.getClass(), text);
		return text;
	}

	public static boolean deleteFile(String path) {
		try {
			File file = new File(path);
			if (file.exists()) {
				file.delete();
				System.out.println(file.getName() + " is deleted!");
			} else {
				System.out.println("Delete operation is failed.");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return true;
	}

	public void scrollTo(By locator) {
		((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView();",
				getDriver().findElement(locator));
	}

	public void scrollToBottom() {
		((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	public void scrollingByCoordinatesofAPage(int x, int y) {
		((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(" + x + "," + y + ")", "");
	}
	public void scrollingByCoordinatesofAPage1(int x, int y) {
		((JavascriptExecutor) getDriver()).executeScript("scroll(" + x + "," + y + ");");
	}
	/* To click a certain Web Element using DOM/ JavaScript Executor */
	public void JSclick(WebElement element) {
		((JavascriptExecutor) getDriver()).executeScript("return arguments[0].click();", element);
	}
	/* To Type at the specified location */
	public void sendKeys(WebElement element, String value) {
		element.sendKeys(value);
	}
	/* To Clear the content in the input location */
	public void clear(WebElement element) {
		element.clear();
	}
	/* To Close Current Tab */
	public void closeCurrentTab() {
		getDriver().close();
	}
	public void navigateToUrl2(String url) {
		getDriver().navigate().to(url);
	}
	public String getCurrentUrl2() {
		return getDriver().getCurrentUrl();
	}

	/**
	 * @return
	 */
	public String getDateTime2() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return dateFormat.format(date);
	}

	/**
	 * 
	 */
	public void renameFile2() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
		String timeStamp = dateFormat.format(date);
		try {
			File oldFile = new File(System.getProperty(USERDIR) + ConfigReader.getValue("testResultExcelPath"));
			String newFilePath = oldFile.getAbsolutePath().replace(oldFile.getName(), "") + "\\ReportHistory\\"
					+ timeStamp + "-TestResult.xls";
			File newFile = new File(newFilePath);
			FileUtils.copyFile(oldFile, newFile);
			LogUtil.infoLog(KeywordUtil.class, "History File successfully created... ");
		} catch (IOException e) {
			LogUtil.errorLog(KeywordUtil.class, "Exception caught", e);
		}
	}
	
	public boolean verifyWebElementPresent(By locator, String logstep) {

		if (isWebElementPresent(locator)) {
			logStepPass(logstep);
			return true;
		} else {
			logStepFail(logstep);
			Assert.fail();
			return false;
		}

	}

	public boolean isWebElementPresent2(By locator, String logstep) {
		boolean status = waitForElement(locator);
		if (status) {
			logStepPass(logstep);
			return true;
		} else {
			logStepFail(logstep);
			return false;
		}
	}

	public boolean isWebElementNotPresent2(By locator, String logstep) {
		boolean status = waitForElement(locator, 5);
		if (!status) {

			logStepPass(logstep);
			return true;
		} else {
			logStepFail(logstep);
			return false;
		}
	}
	// Random text1
	public String getRandomString1() {
		byte[] array = new byte[7]; // length is bounded by 7
		new Random().nextBytes(array);
		String generatedString = new String(array, Charset.forName("UTF-8"));
		return generatedString;
	}
	// Random text2
	public String getRandomString2() {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		String generatedString = buffer.toString();
		return generatedString;
	}
	// Random text3
	public String getRandomString3() {
		int length = 10;
		boolean useLetters = true;
		boolean useNumbers = false;
		String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
		return generatedString;
	}
	// Random text4
	public String getRandomString4() {
		String generatedString = RandomStringUtils.randomAlphabetic(10);
		return generatedString;
	}
	// Random text5
	public String getRandomString5() {
		String generatedString = RandomStringUtils.randomAlphanumeric(10);
		return generatedString;
	}
	public void inputText2(By locator, String data, String logStep) {
		WebElement elm = getWebElement(locator);
		if (elm == null) {
			logStepFail(logStep);
		} else {
			elm.sendKeys(data);
			logStepPass(logStep);

		}
	}

}