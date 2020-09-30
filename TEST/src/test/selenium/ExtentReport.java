package test.selenium;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReport {
	static ExtentTest test;
    static ExtentReports report;
   static int age=26;

	public static void main(String args[])
	{
	report = new ExtentReports("D:\\AccuSelenium\\Ram\\TEST\\ExtentReportResults.html");
	test = report.startTest("ExtentDemo");
	test.log(LogStatus.INFO, "Extents reports working fine");
	try {
		if(age>18)
		{
			test.log(LogStatus.PASS, "Major");	
		}
		else
		{
			test.log(LogStatus.PASS, "Minor");	
		}
	} catch (Exception e) {
		e.printStackTrace();
		test.log(LogStatus.FAIL, "FAILED");	
	}
		
	report.endTest(test);
	report.flush();

	}
}
