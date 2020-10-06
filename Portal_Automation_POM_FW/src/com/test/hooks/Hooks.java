package com.test.hooks;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;



import org.apache.commons.exec.ExecuteException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.excel.Excel_Read;

public class Hooks 
{
	//public static WebDriver driver;
	public static RemoteWebDriver driver;
	public static ChromeOptions options=new ChromeOptions();
	public static WebDriverWait ww;
	
	public static int Pass_Count=0;
	public static int Fail_Count=0;
	
	public static String CurrentTime;
	public static int ImageNumber=0;
	
	public static String PA_Number;
	//public static String Claim_Number;
	
	public static String TakeScreenShots ="No";
	
	public static DateFormat format =new java.text.SimpleDateFormat("_yyyy-MMM-dd_h-mm");
	public static Date date=new Date();
	public static String Execution_Time =format.format(date);
	
	public static ExtentReports er=new ExtentReports("Test_Reports/Log_"+Hooks.Execution_Time+".html");
	public static ExtentTest et=er.startTest("Portal Automation-Test Reports");

	/**CBT Credentials @MANOJ Account */
	public static String username = "manoj.narra%40tecra.com"; // Your user name
	public static String authkey = "u8f7ce62dfcf0b83";  // Your authkey
	public static String testScore = "pass"; 
	
	public static void InitiateBrowser() throws Exception
	{
		if(Config.BrowserEnvironment.equalsIgnoreCase("Local"))
		{
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
			driver=new ChromeDriver();
			et.log(LogStatus.INFO," Chrome Browser is Initiated");
		}
		else if(Config.BrowserEnvironment.equalsIgnoreCase("CBT"))
		{
			DesiredCapabilities caps = new DesiredCapabilities();      
			
			caps.setCapability("name", "Portal Test Automation");
	        caps.setCapability("browserName", "Chrome");
	        caps.setCapability("version", "80");
	        caps.setCapability("platform", "Windows 8");
	        caps.setCapability("screenResolution", "1366x768");
	        caps.setCapability("record_video" , "true"); 
	        
	        driver = new RemoteWebDriver(new URL("http://" + username + ":" + authkey +"@hub.crossbrowsertesting.com:80/wd/hub"), caps);
	        System.out.println(driver.getSessionId()); 
	        et.log(LogStatus.INFO, "Session Id is "+driver.getSessionId()+ " ");
	  
		
		}
		
		driver.manage().window().maximize();
		
	}
	
	
	public static void navigate_to_URL() throws Exception
	{
		driver.get(Config.URL);
		Wait.wait5Second();
		et.log(LogStatus.INFO," Navigated to "+Config.URL+" website");
		
	}
	

	
	
	public static void EndTest() throws InterruptedException
	{
		driver.close();
		Wait.wait5Second();	
	}
	
	public static void closeReports()
	{

		et.log(LogStatus.PASS,"TOTAL PASS COUNT -->" +Pass_Count);
		et.log(LogStatus.INFO,"TOTAL FAIL COUNT -->" +Fail_Count);
		er.flush();
		er.endTest(et);
	}
	
	
	
	public static void VerifyByElement(WebElement element, String SuccessMessage, String ErrorMessage)
	{
		try {
		if(element.isDisplayed())
		{
			et.log(LogStatus.PASS, SuccessMessage);
		}
		else
		{
			et.log(LogStatus.FAIL, ErrorMessage);
			captureScreenshot();
		}
		}catch (Exception Exception) 
		{
			System.out.println("Test Interrupted, Error is -->" + Exception);
		}
	}
	
	
	//Select value from drop down
	public static Select s;
	public static Actions a;
	public static void  select_value_from_DropDown(WebElement e, String value) throws Exception
	{	
		Wait.wait_a_second();
		s=new Select(e);
		Wait.wait_a_second();
		s.selectByVisibleText(value);
		Wait.wait_a_second();
		System.out.println("The Selected value from dropdown is: "+ value);
		Wait.wait_a_second();
	}
	
	public static void  Pick_Value( String value) throws Exception
	{	
		Wait.wait_a_second();
		a=new Actions(driver);
		a.sendKeys(value,Keys.ENTER).build().perform(); 
		Wait.wait_a_second();	
		System.out.println("The Selected value from dropdown is: "+ value);
		Wait.wait_a_second();
	}
	
	//Upload file
		public static Robot robot;
		public static void upload_File() throws AWTException
		{
			String fileLocation = System.getProperty("user.dir") + "\\TestData\\Img1.jpg";
			
			//D:\Portal_automation\Portal_Automation_POM_FW\TestData\Img1.jpg
			
		   	StringSelection sb = new StringSelection(fileLocation);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sb, null);

			//imitate mouse events like ENTER, CTRL+C, CTRL+V
			robot = new Robot();	
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);		
			System.out.println("File Uploaded successfully");
			
		
		}
	
	//TO Get Screenshots 
		 public static void captureScreenshot() throws InterruptedException
		 {
		 	 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH mm");
			 Date date=new Date();
			 CurrentTime= df.format(date);
			 if(TakeScreenShots.equalsIgnoreCase("Yes")) 
			 {
				
				File src= ((TakesScreenshot)driver). getScreenshotAs(OutputType. FILE);
				try
				{
				ImageNumber = ImageNumber+1;
				FileUtils. copyFile(src, new File("ScreenShots\\"+CurrentTime+"\\Error_Img"+ImageNumber+".png"));
				}
				catch(Exception e)
				{
				System.out.println("Screenshot failed");
				}
			    }
		 	}
	 
		 
		 public static void select_value_from_list(WebElement locatorOfList, String value)
		 {
			 try {
					List<WebElement> Funds=driver.findElements(By.xpath("(//ul[@class='k-list k-reset'])[2]/li"));
					for(int i=0;i<Funds.size();i++)
					{
						String selectfund= Funds.get(i).getText();
						if(selectfund.contains(Excel_Read.FundName))
						{
							System.out.println(driver.findElements(By.xpath("(//ul[@class='k-list k-reset'])[2]/li")).get(i).getText());
							driver.findElements(By.xpath("(//ul[@class='k-list k-reset'])[2]/li")).get(i).click();	
						}
					}
					}catch (Exception e) 
					{
						System.out.println("Choose fund dropDown issue : " + e);
					}
				
		 }
		 


}
