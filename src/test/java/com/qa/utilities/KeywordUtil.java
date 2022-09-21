package com.qa.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/**
 * @author TX
 *
 */
public class KeywordUtil extends DriverUtil {
	public static String cucumberTagName;
	public static void onExecutionFinish() {
		/*
		 * // TODO Auto-generated method stub LogUtil.infoLog("TestProcessEnd",
		 * "Test process has ended");
		 * 
		 * // Send Mail functionality if
		 * (GlobalUtil.getCommonSettings().getEmailOutput().equalsIgnoreCase("Y"
		 * )) try { //SendMail.sendEmailToClient(); } catch (IOException e1) {
		 * // TODO Auto-generated catch block //e1.printStackTrace(); } catch
		 * (MessagingException e1) { // TODO Auto-generated catch block
		 * //e1.printStackTrace(); } //2. Extenet Report Finish
		 * 
		 * 
		 * //3. Report open for view
		 */
		String htmlReportFile = System.getProperty("user.dir") + "\\" + ConfigReader.getValue("HtmlReportFullPath");
		File f = new File(htmlReportFile);
		if (f.exists()) {

			try {
				Runtime.getRuntime().exec(
						"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe \"" + htmlReportFile + "\"");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (cucumberTagName.equals("Mobile") || cucumberTagName.equals("Web")) {
			String htmlExtentReportFile = System.getProperty("user.dir") + "\\"
					+ ConfigReader.getValue("extentReportPath");
			File extentReport = new File(htmlExtentReportFile);
			if (extentReport.exists()) {

				try {
					Runtime.getRuntime().exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe \""
							+ htmlExtentReportFile + "\"");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static byte[] takeScreenshot(String screenshotFilePath) {
		try {
			byte[] screenshot = ((TakesScreenshot) GlobalUtil.getDriver()).getScreenshotAs(OutputType.BYTES);
			FileOutputStream fileOuputStream = new FileOutputStream(screenshotFilePath);
			fileOuputStream.write(screenshot);
			fileOuputStream.close();
			return screenshot;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] takeMobileScreenshot(String screenshotFilePath) {
		try {
			byte[] screenshot = ((TakesScreenshot) GlobalUtil.getDriver()).getScreenshotAs(OutputType.BYTES);
			FileOutputStream fileOuputStream = new FileOutputStream(screenshotFilePath);
			fileOuputStream.write(screenshot);
			fileOuputStream.close();
			return screenshot;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getCurrentDateTime() {

		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		System.out.println(strDate);
		return strDate;
	}
	
}
