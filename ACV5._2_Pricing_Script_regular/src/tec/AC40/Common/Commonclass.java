package tec.AC40.Common;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
//import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.remote.DesiredCapabilities;
//import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import tec.AC40.Config.Config;
import tec.AC40.Config.Property;
import tec.AC40.ExcelFiles.Xls_Reader;

import tec.AC40.Suite.OrderFlow;
import tec.AC40.Utility.RW_File;

// Main class starts here
public class Commonclass 
{
	public static Xls_Reader datatable_suite1 = null;
	//public static WebDriver d = null;
	
	public static RemoteWebDriver d=null; 
	public static int ErrorNumber = 0;
	public static String SheetName = null;
	public static String SheetNameErrorLog = null;
	public static String TotalFunds1 = null;
	public static String CoopFundused1 = null;
	public static String AvailableFunds1 = null;
	public static String ActualTotalFunds2 = null;
	public static String ActualCoopFundused2 = null;
	public static String ActualAvailableFunds2 = null;
	public static String ActualOverAllTaxValue = null;
	public static String VOupdatedshippingprice = null;
	

	public static DateFormat format =new java.text.SimpleDateFormat("_yyyy-MMM-dd_hh");
	public static Date date=new Date();
	public static String Execution_Time =format.format(date);
	

	public static ExtentReports er=new ExtentReports(System.getProperty("user.dir")+"\\ExtentReports\\Log_"+Execution_Time+".html");
	public static ExtentTest et=er.startTest("AccuConnet Regular Pricing Automation :: Test Reports");

	
	/**CBT Credentials @MANOJ Account */
	public static String username = "manoj.narra%40tecra.com"; // Your user name
	public static String authkey = "u8f7ce62dfcf0b83";  // Your authkey
	public static String testScore = "unset"; 

	/**	
	 //  CBT Credentials @SREERAM Account
	public static String username = "sreeram.ravella%40tecra.com"; // Your user name
	public static String authkey = "ue45ee38971b4943";  // Your Auth key
	public static String testScore = "Pass"; 
	*/
	static HSSFWorkbook workbook;
	// define an Excel Work sheet
	static HSSFSheet sheet;
	// define a test result data object
	protected static Map<String, Object[]> testresultdata;

	public static void initialize() throws Exception {
		
		DateFormat dateFormat = new SimpleDateFormat("_yyyy-MMM-dd_h-mm-ss_a");
		Date date = new Date();
		SheetName = "\\ACCUCONNECT4x_PRICING_CHECKLIST_TEAT_"+Config.ReleaseNo+ dateFormat.format(date)+".csv";
		System.out.println(dateFormat.format(date));
		RW_File.CreateFile();
		datatable_suite1 = new Xls_Reader(System.getProperty("user.dir")
				+ "//Testdata//TestData.xlsx");
		setupBeforeSuite();
	}

	public void stopDriver() throws IOException, InterruptedException 
	{
		d.quit();
		Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
		tec.AC40.Common.Wait.wait5Second();
	}

	public static void setupBeforeSuite()
			throws FileNotFoundException, IOException, AWTException 
	{
		// baseUrl = "http://www.seleniummaster.com";
		//System.out.println("SA");
		MouseAdjFunction();
		String fileName = System.getProperty("user.dir")+"\\Results\\TestResult.xls";
		POIFSFileSystem fileSystem = new POIFSFileSystem(new FileInputStream(fileName));
		workbook = new HSSFWorkbook(fileSystem);
		// create a new work sheet
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_a");
		Date date = new Date();
		SheetNameErrorLog = ""+dateFormat.format(date);  // Create this variable for error console file name
		String SheetName = "TestResults" + dateFormat.format(date);
		//String SheetName = "TestResults" ;
		System.out.println(" SheetName : "+SheetName);
		int sheetcount = workbook.getNumberOfSheets();
		
		//System.out.println(" sheetcount : "+sheetcount);
		if (sheetcount == 3) {
			
			sheet = workbook.createSheet("Test Result");
			
		} else 
		{
			sheet = workbook.createSheet(SheetName);
		}

		testresultdata = new LinkedHashMap<String, Object[]>();
		// add test result excel file column header
		// write the header in the first row
		testresultdata.put("1", new Object[] { "Test Step Id", "Action",
				"Expected Result", "Actual Result" });
		//System.out.println("SB");
	}

	public static void setupAfterSuite() throws AWTException {
		MouseAdjFunction();
		// write excel file and file name is TestResult.xls
		Set<String> keyset = testresultdata.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sheet.createRow(rownum++);
			Object[] objArr = testresultdata.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof Date)
					cell.setCellValue((Date) obj);
				else if (obj instanceof Boolean)
					cell.setCellValue((Boolean) obj);
				else if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Double)
					cell.setCellValue((Double) obj);
			}
		}
		try {
			FileOutputStream out = new FileOutputStream(System.getProperty("user.dir")+"\\Results\\TestResult.xls");
			workbook.write(out);
			out.close();
			System.out.println("Excel written successfully..");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void userLogin()
			throws InterruptedException, IOException {
		try{
			MouseAdjFunction();
		WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
		
		FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
		 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
		 waitfl.pollingEvery(1, TimeUnit.SECONDS);
		 waitfl.ignoring(NoSuchElementException.class);
		 waitfl.ignoring(StaleElementReferenceException.class);
		
		//d.get(Config.url);
		waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.username));
		tec.AC40.Common.Wait.wait2Second();
		if(Config.LayoutType.equals("Classic"))
		{
			waitfl.until(new Function<WebDriver, WebElement>() 
   				 {
   				   public WebElement apply(WebDriver driver) {
   				   return driver.findElement(Property.UserName);
   				 }
   				 });
			d.findElement(Property.username).sendKeys(Config.UserName);
			d.findElement(Property.Password).sendKeys(Config.UserPwd);
		}
		else if(Config.LayoutType.equals("Layout1"))
		{
			waitfl.until(new Function<WebDriver, WebElement>() 
   				 {
   				   public WebElement apply(WebDriver driver) {
   				   return driver.findElement(Property.UserName);
   				 }
   				 });
			d.findElement(Property.username).sendKeys(Config.UserNamel1);
			d.findElement(Property.Password).sendKeys(Config.UserPwdl1);
		}
		else if(Config.LayoutType.equals("Layout2"))
		{
			waitfl.until(new Function<WebDriver, WebElement>() 
   				 {
   				   public WebElement apply(WebDriver driver) {
   				   return driver.findElement(Property.UserName);
   				 }
   				 });
			d.findElement(Property.username).sendKeys(Config.UserNamel2);
			d.findElement(Property.Password).sendKeys(Config.UserPwdl2);
		}
		waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.LoginButton));
		d.findElement(Property.LoginButton).click();
		tec.AC40.Common.Wait.wait5Second();
		}
		catch(Exception e)
		{
			captureScreenshot();
			e.printStackTrace();
		}
		
	}
	
	public static void ApproverLogin()
			throws InterruptedException, IOException {
		try{
			
			MouseAdjFunction();
		WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
		
		FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
		 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
		 waitfl.pollingEvery(1, TimeUnit.SECONDS);
		 waitfl.ignoring(NoSuchElementException.class);
		 waitfl.ignoring(StaleElementReferenceException.class);
		
		//d.get(Config.url);
		waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.username));
		//d.findElement(Property.username).isDisplayed();
		tec.AC40.Common.Wait.wait2Second();
		if(Config.LayoutType.equals("Classic"))
		{
			waitfl.until(new Function<WebDriver, WebElement>() 
   				 {
   				   public WebElement apply(WebDriver driver) {
   				   return driver.findElement(Property.UserName);
   				 }
   				 });
			d.findElement(Property.username).sendKeys(Config.ApproverName);
			d.findElement(Property.Password).sendKeys(Config.ApproverPwd);
		}
		else if(Config.LayoutType.equals("Layout1"))
		{
			waitfl.until(new Function<WebDriver, WebElement>() 
   				 {
   				   public WebElement apply(WebDriver driver) {
   				   return driver.findElement(Property.UserName);
   				 }
   				 });
			d.findElement(Property.username).sendKeys(Config.ApproverNamel1);
			d.findElement(Property.Password).sendKeys(Config.ApproverPwdl1);
		}
		else if(Config.LayoutType.equals("Layout2"))
		{
			waitfl.until(new Function<WebDriver, WebElement>() 
   				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.UserName);
				 }
				 });
			d.findElement(Property.username).sendKeys(Config.ApproverNamel2);
			d.findElement(Property.Password).sendKeys(Config.ApproverPwdl2);
		}
		d.findElement(Property.LoginButton).click();
		tec.AC40.Common.Wait.wait5Second();
		}
		catch(Exception e)
		{
			captureScreenshot();
			e.printStackTrace();
		}
	}
	
	public static void PSLogin()
			throws InterruptedException, IOException {
		try{
			MouseAdjFunction();
		WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
		
		FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
		 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
		 waitfl.pollingEvery(1, TimeUnit.SECONDS);
		 waitfl.ignoring(NoSuchElementException.class);
		 waitfl.ignoring(StaleElementReferenceException.class);
		
		//d.get(Config.url);
		waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.username));
		waitfl.until(ExpectedConditions.elementToBeClickable(Property.username));
		if(Config.LayoutType.equals("Classic"))
		{
			waitfl.until(new Function<WebDriver, WebElement>() 
   				 {
   				   public WebElement apply(WebDriver driver) {
   				   return driver.findElement(Property.UserName);
   				 }
   				 });
			d.findElement(Property.username).sendKeys(Config.PSName);
			d.findElement(Property.Password).sendKeys(Config.PSPwd);
		}
		else if(Config.LayoutType.equals("Layout1"))
		{
			waitfl.until(new Function<WebDriver, WebElement>() 
   				 {
   				   public WebElement apply(WebDriver driver) 
   				   {
   					   return driver.findElement(Property.UserName);
   				   }
   				 });
			Thread.sleep(3000);
			d.findElement(Property.username).sendKeys(Config.PSNamel1);
			d.findElement(Property.Password).sendKeys(Config.PSPwdl1);
		}
		else if(Config.LayoutType.equals("Layout2"))
		{
			waitfl.until(new Function<WebDriver, WebElement>() 
   				 {
   				   public WebElement apply(WebDriver driver) {
   				   return driver.findElement(Property.UserName);
   				 }
   				 });
			d.findElement(Property.username).sendKeys(Config.PSNamel2);
			d.findElement(Property.Password).sendKeys(Config.PSPwdl2);
		}
		
		d.findElement(Property.LoginButton).click();
		tec.AC40.Common.Wait.wait5Second();
		}
		catch(Exception e)
		{
			captureScreenshot();
			e.printStackTrace();
		}
	}
	
	public static String Decimalsetting(String Datavalue, String DecimalValue)
			throws InterruptedException, AWTException {
		MouseAdjFunction();
		String Datavalue1 = null;
		if(Datavalue.isEmpty())
		{
			return "";	
		}
		else
		{
			if(DecimalValue.equals("2.0"))
			{
				Datavalue1 = String.format("%.2f", new BigDecimal(Datavalue));
			}
			else if(DecimalValue.equals("3.0"))
			{
				Datavalue1 = String.format("%.3f", new BigDecimal(Datavalue));
			}
			else if(DecimalValue.equals("4.0"))
			{
				Datavalue1 = String.format("%.4f", new BigDecimal(Datavalue));
			}
			else if(DecimalValue.equals("0.0"))
			{
				Datavalue1 = String.format("%.0f", new BigDecimal(Datavalue));
			}
			//System.out.println("Base test result : "+Datavalue1);
			return Datavalue1;
		}
		
	}
	
	public static void StartBrowser()
			throws InterruptedException, NullPointerException {
      try{
    	  MouseAdjFunction();
    	  if (Config.browser.equals("FF")) {
    		  System.setProperty("webdriver.gecko.driver", "D:\\ACV5._2_Pricing_Script\\TestData\\Drivers\\geckodriver.exe");
    		  
  			d = new FirefoxDriver();

  		} else if (Config.browser.equals("IE")) {
  			System.setProperty("webdriver.ie.driver",
  					System.getProperty("user.dir")+"\\TestData\\Drivers\\IEDriverServer.exe");
  			d = new InternetExplorerDriver();

  		} else if (Config.browser.equals("GC")) {
  			
  				System.setProperty("webdriver.chrome.driver",
  					System.getProperty("user.dir")+"\\TestData\\Drivers\\chromedriver.exe");
  			d = new ChromeDriver();
  		}
 
    	  ErrorNumber = 0;
    	d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		d.manage().window().maximize();
		d.manage().timeouts().setScriptTimeout(3,TimeUnit.SECONDS);
		//d.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		MouseAdjFunction();
      }catch (Exception e){
    	  
    	  ErrorNumber = ErrorNumber+1;
    	  captureScreenshot();
    	  e.printStackTrace();
      }
	}
	
public static void CBT_Browser() throws MalformedURLException
{
		DesiredCapabilities caps = new DesiredCapabilities();      
        caps.setCapability("name", "AccuConnect Regular pricing script");
        caps.setCapability("build", "1.0");
        caps.setCapability("browserName", "Chrome");
        caps.setCapability("version", "70x64");
        caps.setCapability("platform", "Windows 10");
        caps.setCapability("screenResolution", "1366x768");
        caps.setCapability("record_video" , "true"); 
        caps.setCapability("max_duration" , "2400"); 

        
         d = new RemoteWebDriver(new URL("http://" + username + ":" + authkey +"@hub.crossbrowsertesting.com:80/wd/hub"), caps);
        System.out.println(d.getSessionId()); 
        et.log(LogStatus.INFO, "Session Id is "+d.getSessionId()+" "); 
		
	}
	
	public static void MouseAdjFunction() throws AWTException
	{
		try
		{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		
		//System.out.println("width :"+width);
		//System.out.println("height :"+height);
		
		Robot robot = new Robot();
		if(Config.IsAdjustMOuse.equalsIgnoreCase("Yes"))
		{
 	    robot.mouseMove(width-30, height);
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void adminLogin()
			throws InterruptedException, NullPointerException 
	{
      try{
    	
    	MouseAdjFunction(); 
		d.get(Config.url);
		d.manage().window().maximize();
		Thread.sleep(2500);
		//tec.AC40.Common.Wait.wait5Second();
		
		 FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
		 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
		 waitfl.pollingEvery(1, TimeUnit.SECONDS);
		 waitfl.ignoring(NoSuchElementException.class);
		 waitfl.ignoring(StaleElementReferenceException.class);
		 
		 waitfl.until(ExpectedConditions.elementToBeClickable(Property.UserName));
		 
		
		/* WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
		waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserName));
		tec.AC40.Common.Wait.wait2Second();*/
		if(Config.LayoutType.equals("Classic"))
		{
			waitfl.until(new Function<WebDriver, WebElement>() 
   				 {
   				   public WebElement apply(WebDriver driver) {
   				   return driver.findElement(Property.UserName);
   				 }
   				 });
			d.findElement(Property.UserName).sendKeys(Config.AdminName);
			Thread.sleep(1000);
			d.findElement(Property.Password).sendKeys(Config.AdminPwd);
		}
		else if(Config.LayoutType.equals("Layout1"))
		{
			waitfl.until(new Function<WebDriver, WebElement>() 
   				 {
   				   public WebElement apply(WebDriver driver) {
   				   return driver.findElement(Property.UserName);
   				 }
   				 });
			d.findElement(Property.UserName).sendKeys(Config.AdminNamel1);
			Thread.sleep(1000);
			d.findElement(Property.Password).sendKeys(Config.AdminPwdl1);
		}
		else if(Config.LayoutType.equals("Layout2"))
		{	
			waitfl.until(new Function<WebDriver, WebElement>() 
   				 {
   				   public WebElement apply(WebDriver driver) {
   				   return driver.findElement(Property.UserName);
   				 }
   				 });
			d.findElement(Property.UserName).sendKeys(Config.AdminNamel2);
			Thread.sleep(1000);
			d.findElement(Property.Password).sendKeys(Config.AdminPwdl2);
			
		}
		Thread.sleep(2000);
		d.findElement(Property.LoginButton).click();
		//tec.AC40.Common.Wait.wait5Second();
		MouseAdjFunction();
      }
      catch (Exception e)
      {  
    	  ErrorNumber = ErrorNumber+1;
    	  captureScreenshot();
    	  e.printStackTrace();
      }
	}
	
	
	public static void adminsettings() 
			throws InterruptedException, NullPointerException, AWTException	{
		FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
		 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
		 waitfl.pollingEvery(1, TimeUnit.SECONDS);
		 waitfl.ignoring(NoSuchElementException.class);
		 waitfl.ignoring(StaleElementReferenceException.class);
		
		//pricing code	 
		 if(Config.LayoutType.equalsIgnoreCase("Classic"))
		  {
			 waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.CompanyLink);
				 }
				 });
			 tec.AC40.Common.Wait.wait5Second();
			  d.findElement(Property.CompanyLink).click();
			tec.AC40.Common.Wait.wait5Second();
			d.findElement(Property.PricingIcon).click();
		  }
		  else if(Config.LayoutType.equalsIgnoreCase("Layout1"))
		  {
			  waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
			  waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.CompanyIconL1);
				 }
				 });
			  tec.AC40.Common.Wait.wait5Second();
			  d.findElement(Property.CompanyIconL1).click();
			  
			 tec.AC40.Common.Wait.wait5Second();
			  d.findElement(Property.PricingIconL1).click();
		  }
		  else if(Config.LayoutType.equalsIgnoreCase("Layout2"))
		  {
			  waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.CompanyIconL2);
				 }
				 });
			  d.findElement(Property.CompanyIconL2).click();
			 tec.AC40.Common.Wait.wait5Second();
			  d.findElement(Property.PricingIconL2).click();
		  }
		 waitfl.until(new Function<WebDriver, WebElement>() 
		 {
		   public WebElement apply(WebDriver driver) {
		   return driver.findElement(Property.PricingSearchBox);
		 }
		 });
		 d.findElement(Property.PricingSearchBox).clear();
		 d.findElement(Property.PricingSearchBox).sendKeys("price4");
		if(d.findElement(Property.PricingNorecord).isDisplayed()) {
	  d.findElement(Property.MoreActions).click();
	  tec.AC40.Common.Wait.wait5Second();
	  d.findElement(Property.uplodePrice).click();
	  tec.AC40.Common.Wait.wait5Second();
	  d.findElement(By.cssSelector("div[class*='k-button k-upload-button']")).click();
		 tec.AC40.Common.Wait.wait5Second();
		 String fileLocation6 = System.getProperty("user.dir") + "\\TestData" + "\\Lists" + "\\10recordslist.xlsx";
			StringSelection sc = new StringSelection(fileLocation6);
		    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sc, null);

		    //imitate mouse events like ENTER, CTRL+C, CTRL+V
		    Robot robot = new Robot();
		    robot.keyPress(KeyEvent.VK_ENTER);
		    robot.keyRelease(KeyEvent.VK_ENTER);
		    robot.keyPress(KeyEvent.VK_CONTROL);
		    robot.keyPress(KeyEvent.VK_V);
		    robot.keyRelease(KeyEvent.VK_V);
		    robot.keyRelease(KeyEvent.VK_CONTROL);
		    robot.keyPress(KeyEvent.VK_ENTER);
		    robot.keyRelease(KeyEvent.VK_ENTER);
		    tec.AC40.Common.Wait.wait5Second(); 
		    
		   d.findElement(Property.uplodeSave).click();
		   tec.AC40.Common.Wait.wait5Second();
		 d.findElement(Property.Priceclose).click();
		   tec.AC40.Common.Wait.wait2Second();
		   
		}
		else{
			 //System.out.println("********do Nothing*******");
			 tec.AC40.Common.Wait.wait2Second();
		 }	
		
		d.findElement(Property.UserBackToHome).click();
		 tec.AC40.Common.Wait.wait5Second(); 
		 
		 
		 
		 //Landing page Enable/settings
		 if(Config.LayoutType.equalsIgnoreCase("Classic"))
		  {
			 waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.CompanyLink);
				 }
				 });
			 tec.AC40.Common.Wait.wait5Second(); 
			  d.findElement(Property.CompanyLink).click();
			tec.AC40.Common.Wait.wait5Second();
			d.findElement(Property.ManageOrgunitIcon).click();
		  }
		  else if(Config.LayoutType.equalsIgnoreCase("Layout1"))
		  {
			  waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.CompanyIconL1);
				 }
				 });
			  tec.AC40.Common.Wait.wait5Second();
			  d.findElement(Property.CompanyIconL1).click();
			  
			 tec.AC40.Common.Wait.wait5Second();
			  d.findElement(Property.ManageOrguntiIconL1).click();
		  }
		  else if(Config.LayoutType.equalsIgnoreCase("Layout2"))
		  {
			  waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.CompanyIconL2);
				 }
				 });
			  tec.AC40.Common.Wait.wait5Second();
			  d.findElement(Property.CompanyIconL2).click();
			 tec.AC40.Common.Wait.wait5Second();
			 d.findElement(Property.ManageOrguntiIconL2).click();
		  } 
		 
		    waitfl.until(new Function<WebDriver, WebElement>() 
		    {
		     public WebElement apply(WebDriver driver) {
		     return driver.findElement(Property.Backtohome);
		     }
		     });
		    tec.AC40.Common.Wait.wait5Second();
		     d.findElement(Property.Response).click();
		     tec.AC40.Common.Wait.wait5Second();
		  boolean RLandingPage=d.findElement(Property.RLandingpage).isSelected();
		  if (RLandingPage==true )
		  {
			  System.out.println("Landing page selected in Response");  
		  }
		  else
		  {
			  d.findElement(Property.RLandingpage).click();
			  tec.AC40.Common.Wait.wait2Second();
		  }
		  
		  boolean AccuPURL=d.findElement(Property.AccuPURLoption).isSelected();	
		  
		  if (AccuPURL==true )
		  {
			  System.out.println("AccuPURL selected in Response");  
		  }
		  else
		  {
			  d.findElement(Property.AccuPURLoption).click();
			  tec.AC40.Common.Wait.wait2Second();
			  d.findElement(Property.Accpurlconnection).click();
			  tec.AC40.Common.Wait.wait2Second();
			  d.findElement(Property.APIstage).clear();
			  d.findElement(Property.APIstage).sendKeys("http://testqa123.com/tecpurl/");
			  tec.AC40.Common.Wait.wait2Second();
			  d.findElement(Property.APIlive).clear();
			  d.findElement(Property.APIlive).sendKeys("http://testqa123.com/tecpurl/");
			  tec.AC40.Common.Wait.wait2Second();
			  d.findElement(Property.AuthTokenstage).clear();
			  d.findElement(Property.AuthTokenstage).sendKeys("21EC2020-3AEA-4069-A2DD-08002B30309D");
			  tec.AC40.Common.Wait.wait2Second();
			  d.findElement(Property.AuthTokenlive).clear();
			  d.findElement(Property.AuthTokenlive).sendKeys("21EC2020-3AEA-4069-A2DD-08002B30309D");
			  tec.AC40.Common.Wait.wait2Second();
			  d.findElement(Property.Connectionsettsave).click();
			  tec.AC40.Common.Wait.wait2Second();
			  d.findElement(Property.Connectionsettclose).click();
		  }
		  tec.AC40.Common.Wait.wait2Second();
		  d.findElement(Property.ResponseSave).click();
		  tec.AC40.Common.Wait.wait5Second();
		  d.findElement(Property.UserBackToHome).click(); 
		  tec.AC40.Common.Wait.wait5Second();
		  
		  
		  //Discount
		  if(Config.LayoutType.equalsIgnoreCase("Classic"))
		  {
			 waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.ProductsLink);
				 }
				 });
			 tec.AC40.Common.Wait.wait5Second(); 
			  d.findElement(Property.ProductsLink).click();
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PromotionsIcon).click();
		  }
		  else if(Config.LayoutType.equalsIgnoreCase("Layout1"))
		  {
			  waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.ProductsIconL1);
				 }
				 });
			  tec.AC40.Common.Wait.wait2Second();
			  d.findElement(Property.ProductsIconL1).click();
			  
			 tec.AC40.Common.Wait.wait5Second();
			  d.findElement(Property.PromotionsIconL1).click();
		  }
		  else if(Config.LayoutType.equalsIgnoreCase("Layout2"))
		  {
			  waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.ProductsIconL2);
				 }
				 });
		  
			  d.findElement(Property.ProductsIconL2).click();
			 tec.AC40.Common.Wait.wait5Second();
			 d.findElement(Property.PromotionsIconL2).click();
		  } 
		  waitfl.until(new Function<WebDriver, WebElement>() 
			 {
			   public WebElement apply(WebDriver driver) {
			   return driver.findElement(Property.PromotionsSearch);
			 }
			 });

	tec.AC40.Common.Wait.wait2Second();
		d.findElement(Property.PromotionsSearch).sendKeys(Config.DiscountName2);
		tec.AC40.Common.Wait.wait2Second();
		boolean discount = d.findElement(Property.discount2).isDisplayed();
		if(discount==true)
		{
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.Ldiscount).click();
			tec.AC40.Common.Wait.wait2Second();
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.discountname));
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.discountname).sendKeys(Config.DiscountName2);
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.discountdis).sendKeys(Config.DiscountName2);
			d.findElement(Property.discountval).click();
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.discountstr).click();
			tec.AC40.Common.Wait.wait2Second();
			String today =null;
			DateFormat dateFormat = null;
			dateFormat = new SimpleDateFormat("d");
			Date date1 = new Date();
			today = dateFormat.format(date1);
			d.findElement(By.linkText(today)).click();
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.discountAct).click();
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.discountPro).click();
			d.findElement(Property.discountsel).click();
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.discountPro1).click();
			d.findElement(Property.discountsel).click();
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.discountPro2).click();
			d.findElement(Property.discountsel).click();
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.Lpricesave).click();
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.UserBackToHome));
			tec.AC40.Common.Wait.wait2Second();
			System.out.println("Disocunt Added successfully..............");
			d.findElement(Property.UserBackToHome).click();
			tec.AC40.Common.Wait.wait2Second();
			
		}
		else{
			System.out.println("Discount2 is already created");
			d.findElement(Property.UserBackToHome).click();
			tec.AC40.Common.Wait.wait2Second();
			
		}
		 
		
	}
	
	public static void creatlandingPage () throws AWTException, InterruptedException {
		
		//Creating Landing Page
		
		FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
		 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
		 waitfl.pollingEvery(1, TimeUnit.SECONDS);
		 waitfl.ignoring(NoSuchElementException.class);
		 waitfl.ignoring(StaleElementReferenceException.class);
		if(Config.LayoutType.equalsIgnoreCase("Classic"))
		  {
			 waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.ProductsLink);
				 }
				 });
			 tec.AC40.Common.Wait.wait5Second(); 
			  d.findElement(Property.ProductsLink).click();
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.ManageProductsIcon).click();
		  }
		  else if(Config.LayoutType.equalsIgnoreCase("Layout1"))
		  {
			  waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.ProductsIconL1);
				 }
				 });
			  tec.AC40.Common.Wait.wait2Second();
			  d.findElement(Property.ProductsIconL1).click();
			  
			 tec.AC40.Common.Wait.wait5Second();
			  d.findElement(Property.ManageProductsIconL1).click();
		  }
		  else if(Config.LayoutType.equalsIgnoreCase("Layout2"))
		  {
			  waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.ProductsIconL2);
				 }
				 });
		  
			  d.findElement(Property.ProductsIconL2).click();
			 tec.AC40.Common.Wait.wait5Second();
			 d.findElement(Property.ManageProductsIconL2).click();
		  } 
		
		waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsSearchBox));
		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsSearchBox));
		
		waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.ProductsSearchBox);
				 }
				 });
		
		  d.findElement(Property.LandingProduct).click();
		  Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
		  tec.AC40.Common.Wait.wait2Second();
		  kb.pressKey(Keys.ARROW_DOWN);
		  kb.pressKey(Keys.ARROW_DOWN);
		  kb.pressKey(Keys.ARROW_DOWN);
		  kb.pressKey(Keys.ARROW_DOWN);
		  kb.pressKey(Keys.ENTER);
		  tec.AC40.Common.Wait.wait10Second();
		  
		  int i=0;
		  for(i=1;i<3;i++)
		  {
			
			 if(i==0){
				 boolean Purl= d.findElement(Property.ProductsSearchBox).isDisplayed();
				 if(Purl==true){
		             d.findElement(Property.ProductsSearchBox).clear();
		             d.findElement(Property.ProductsSearchBox).sendKeys("PURL");
		             tec.AC40.Common.Wait.wait5Second();
				               }
				 else{
					 System.out.println("NO Landing Products");
				 	 }
		              if(d.findElement(Property.NoLandingProduct).isDisplayed())
		                {
		            	  d.findElement(Property.NewLandingProduct).click();
		            	  waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Backtohome));
		            		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Backtohome));
		            		
		            		waitfl.until(new Function<WebDriver, WebElement>() 
		            				 {
		            				   public WebElement apply(WebDriver driver) {
		            				   return driver.findElement(Property.Backtohome);
		            				 }
		            		});
		            		tec.AC40.Common.Wait.wait5Second();
		            	  d.findElement(Property.ProviderL).click();
		            	  kb.pressKey(Keys.ARROW_DOWN);
		            	  kb.pressKey(Keys.ARROW_DOWN);
		            	  kb.pressKey(Keys.ENTER);
		            	  tec.AC40.Common.Wait.wait5Second();
		            	  d.findElement(Property.HURL).clear();
		            	  tec.AC40.Common.Wait.wait2Second();
		            	  d.findElement(Property.HURL).sendKeys("http://192.168.1.121/LandingPages/purl/step1.html");
		            	  tec.AC40.Common.Wait.wait5Second();
		            	  d.findElement(Property.Scema).click();
		     			  By.xpath(".//*[@id='divFlupSchemaDocumentFile']/div/div/div/div/div");
		     			  tec.AC40.Common.Wait.wait5Second();
		     			 //Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\TestData\\IMAGES\\red.png");
		     				//tec.Config.wait.wait10Second();
		     			 String fileLocation = System.getProperty("user.dir") + "\\TestData" + "\\Lists" + "\\ACC_RQ_000015_LandingPageTemplate.xml";
		     				StringSelection ss = new StringSelection(fileLocation);
		     			    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

		     			    //imitate mouse events like ENTER, CTRL+C, CTRL+V
		     			    Robot robot = new Robot();
		     			    robot.keyPress(KeyEvent.VK_ENTER);
		     			    robot.keyRelease(KeyEvent.VK_ENTER);
		     			    robot.keyPress(KeyEvent.VK_CONTROL);
		     			    robot.keyPress(KeyEvent.VK_V);
		     			    robot.keyRelease(KeyEvent.VK_V);
		     			    robot.keyRelease(KeyEvent.VK_CONTROL);
		     			    robot.keyPress(KeyEvent.VK_ENTER);
		     			    robot.keyRelease(KeyEvent.VK_ENTER);
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.DominName).sendKeys("testqa123.com");
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.ActiveDays).sendKeys("10");
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.Productname).sendKeys("PURL");
		     			    tec.AC40.Common.Wait.wait2Second();
		     			    d.findElement(Property.Productcode).sendKeys("PURL");
		     			    tec.AC40.Common.Wait.wait2Second();
		     			    d.findElement(Property.ProdcutDesciption).click();
		     			    kb.sendKeys("PURL Description");
		     			    tec.AC40.Common.Wait.wait2Second();
		     			    d.findElement(Property.Lcategory1).click();
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.Lcategoryoption).click();
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.LcategorySave).click();
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.Thumbnail).click();
		     			    tec.AC40.Common.Wait.wait5Second();
		     				d.findElement(Property.Thumbnail1).click();
		     				tec.AC40.Common.Wait.wait5Second();
		     				 String fileLocation1 = System.getProperty("user.dir") + "\\TestData" + "\\IMAGES" + "\\download1.jpg";
		     				StringSelection sb3 = new StringSelection(fileLocation1);
		     					    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sb3, null);

		     					    //imitate mouse events like ENTER, CTRL+C, CTRL+V
		     					    robot.keyPress(KeyEvent.VK_ENTER);
		     					    robot.keyRelease(KeyEvent.VK_ENTER);
		     					    robot.keyPress(KeyEvent.VK_CONTROL);
		     					    robot.keyPress(KeyEvent.VK_V);
		     					    robot.keyRelease(KeyEvent.VK_V);
		     					    robot.keyRelease(KeyEvent.VK_CONTROL);
		     					    robot.keyPress(KeyEvent.VK_ENTER);
		     					    robot.keyRelease(KeyEvent.VK_ENTER);
		     				   tec.AC40.Common.Wait.wait5Second();
		     			    
		     			    
		     			    d.findElement(Property.LMinQnt).sendKeys("1");
		     			    tec.AC40.Common.Wait.wait2Second();
		     			    d.findElement(Property.Lleaddays).sendKeys("0");
		     			    tec.AC40.Common.Wait.wait2Second();
		     			    d.findElement(Property.Lstartdate).click();
		     			    tec.AC40.Common.Wait.wait2Second();
		     			    kb.pressKey(Keys.ENTER);
		     			    tec.AC40.Common.Wait.wait2Second();
		     			    d.findElement(Property.Lexpiry).click();
		     			    tec.AC40.Common.Wait.wait2Second();
		     			    kb.pressKey(Keys.ENTER);
		     			    tec.AC40.Common.Wait.wait2Second();
		     			    d.findElement(Property.ProductInfoSave).click();
		     			    tec.AC40.Common.Wait.wait5Second();
		     			   waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserBackToHome));
		            		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.UserBackToHome));
		            		
		            		waitfl.until(new Function<WebDriver, WebElement>() 
		            				 {
		            				   public WebElement apply(WebDriver driver) {
		            				   return driver.findElement(Property.UserBackToHome);
		            				 }
		            		});
		            		Thread.sleep(10000);
		     			    d.findElement(Property.LPricing).click();
		     			   waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Lpricingrd));
		            		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Lpricingrd));
		            		
		            		waitfl.until(new Function<WebDriver, WebElement>() 
		            				 {
		            				   public WebElement apply(WebDriver driver) {
		            				   return driver.findElement(Property.Lpricingrd);
		            				 }
		            				 });
		     			    d.findElement(Property.Lpricingrd).click();
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.Lpricecode).click();
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    kb.pressKey(Keys.ARROW_DOWN);
		     			    kb.pressKey(Keys.ARROW_DOWN);
		     			    kb.pressKey(Keys.ARROW_DOWN);
		     			    kb.pressKey(Keys.ARROW_DOWN);
		     			    kb.pressKey(Keys.ENTER);
		     			   tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.Lpricesave).click();
		     			   tec.AC40.Common.Wait.wait5Second();
		     			  waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Llisttype));
		            		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Llisttype));
		     			  waitfl.until(new Function<WebDriver, WebElement>() 
	     				 {
	     				   public WebElement apply(WebDriver driver) {
	     				   return driver.findElement(Property.Llisttype);
	     				 }
	     				 });
		     			    d.findElement(Property.Llisttype).click();
		     			   tec.AC40.Common.Wait.wait2Second();
		     			  waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Ldisplayname));
		            		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Ldisplayname));
		     			  waitfl.until(new Function<WebDriver, WebElement>() 
	   				 {
	   				   public WebElement apply(WebDriver driver) {
	   				   return driver.findElement(Property.Ldisplayname);
	   				 }
	   				 });
		     			    d.findElement(Property.Ldisplayname).sendKeys("price list10");
		     			   tec.AC40.Common.Wait.wait2Second();
		     			    d.findElement(Property.Ldisplay).sendKeys("1");
		     			   tec.AC40.Common.Wait.wait2Second();
		     			    d.findElement(Property.LlistSave).click();
		     			   tec.AC40.Common.Wait.wait5Second();
		     			  waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductOnlineLink));
		            		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductOnlineLink));
		     			  waitfl.until(new Function<WebDriver, WebElement>() 
	 				 {
	 				   public WebElement apply(WebDriver driver) {
	 				   return driver.findElement(Property.ProductOnlineLink);
	 				 }
	 				 });
		     			    d.findElement(Property.ProductOnlineLink).click();
		     			   tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.ProductAlertOK).click();
		     			   tec.AC40.Common.Wait.wait5Second();
		     			    System.out.println("Product Info Settings completed successfully......");
		     			   tec.AC40.Common.Wait.wait2Second();
		     			   waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserBackToHome));
		            		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.UserBackToHome));
		     			  waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.UserBackToHome);
					 }
					 });
		     			    d.findElement(Property.LBackToProducts).click();
		     			    Thread.sleep(7000);
		     			   waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsSearchBox));
		   				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsSearchBox));
		   				
		   				waitfl.until(new Function<WebDriver, WebElement>() 
		   						 {
		   						   public WebElement apply(WebDriver driver) {
		   						   return driver.findElement(Property.ProductsSearchBox);
		   						 }
		   						 });  
		     			    
		                	}
		              		else{
		              			System.out.println("PURL Product already created");
		              		}
			 
			 }
			 if(i==1){
				 
				 boolean Purl= d.findElement(Property.ProductsSearchBox).isDisplayed();
				 if(Purl==true){
		             			d.findElement(Property.ProductsSearchBox).clear();
		             			d.findElement(Property.ProductsSearchBox).sendKeys("GURL");
		             			tec.AC40.Common.Wait.wait5Second();
				               }
				               
				 			else{
				 					System.out.println("NO Landing Products");
				 				}
							if(d.findElement(Property.NoLandingProduct).isDisplayed())
								{
								d.findElement(Property.NewLandingProduct).click();
								waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProviderL));
								waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProviderL));
	    		
								waitfl.until(new Function<WebDriver, WebElement>() 
								{
									public WebElement apply(WebDriver driver) {
										return driver.findElement(Property.ProviderL);
									}
								});
								d.findElement(Property.ProviderL).click();
								kb.pressKey(Keys.ARROW_DOWN);
								kb.pressKey(Keys.ARROW_DOWN);
								kb.pressKey(Keys.ENTER);
								
		            	  d.findElement(Property.LItemProces).click();
		            	  tec.AC40.Common.Wait.wait2Second();
		            	  kb.pressKey(Keys.ARROW_DOWN);
		            	  kb.pressKey(Keys.ENTER);
		            	  tec.AC40.Common.Wait.wait5Second();
		            	  d.findElement(Property.HURL).clear();
		            	  tec.AC40.Common.Wait.wait2Second();
		            	  d.findElement(Property.HURL).sendKeys("http://192.168.1.121/LandingPages/purl/step1.html");
		            	  tec.AC40.Common.Wait.wait5Second();
		            	  d.findElement(Property.Scema).click();
		     			  By.xpath(".//*[@id='divFlupSchemaDocumentFile']/div/div/div/div/div");
		     			  tec.AC40.Common.Wait.wait5Second();
		     			 //Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\TestData\\IMAGES\\red.png");
		     				//tec.Config.wait.wait10Second();
		     			  
		     			 String fileLocation2 = System.getProperty("user.dir") + "\\TestData" + "\\Lists" + "\\ACC_RQ_000015_LandingPageTemplate.xml";
		     				StringSelection ss = new StringSelection(fileLocation2);
		     			    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

		     			    //imitate mouse events like ENTER, CTRL+C, CTRL+V
		     			    Robot robot = new Robot();
		     			    robot.keyPress(KeyEvent.VK_ENTER);
		     			    robot.keyRelease(KeyEvent.VK_ENTER);
		     			    robot.keyPress(KeyEvent.VK_CONTROL);
		     			    robot.keyPress(KeyEvent.VK_V);
		     			    robot.keyRelease(KeyEvent.VK_V);
		     			    robot.keyRelease(KeyEvent.VK_CONTROL);
		     			    robot.keyPress(KeyEvent.VK_ENTER);
		     			    robot.keyRelease(KeyEvent.VK_ENTER);
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.DominName).sendKeys("testqa123.com");
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.ActiveDays).sendKeys("10");
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.Productname).sendKeys("GURL");
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.Productcode).sendKeys("GURL");
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.ProdcutDesciption).click();
		     			    kb.sendKeys("GURL Description");
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.Lcategory1).click();
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.Lcategoryoption).click();
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.LcategorySave).click();
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.Thumbnail).click();
		     			    tec.AC40.Common.Wait.wait5Second();
		     				d.findElement(Property.Thumbnail1).click();
		     				tec.AC40.Common.Wait.wait5Second();
		     				
		     				String fileLocation3 = System.getProperty("user.dir") + "\\TestData" + "\\IMAGES" + "\\download2.jpg";
		     				StringSelection sb3 = new StringSelection(fileLocation3);
		     					    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sb3, null);

		     					    //imitate mouse events like ENTER, CTRL+C, CTRL+V
		     					    robot.keyPress(KeyEvent.VK_ENTER);
		     					    robot.keyRelease(KeyEvent.VK_ENTER);
		     					    robot.keyPress(KeyEvent.VK_CONTROL);
		     					    robot.keyPress(KeyEvent.VK_V);
		     					    robot.keyRelease(KeyEvent.VK_V);
		     					    robot.keyRelease(KeyEvent.VK_CONTROL);
		     					    robot.keyPress(KeyEvent.VK_ENTER);
		     					    robot.keyRelease(KeyEvent.VK_ENTER);
		     				   tec.AC40.Common.Wait.wait5Second();
		     			    
		     			    
		     			    d.findElement(Property.LMinQnt).sendKeys("1");
		     			    tec.AC40.Common.Wait.wait2Second();
		     			    d.findElement(Property.Lleaddays).sendKeys("0");
		     			    tec.AC40.Common.Wait.wait2Second();
		     			    d.findElement(Property.Lstartdate).click();
		     			    tec.AC40.Common.Wait.wait2Second();
		     			    kb.pressKey(Keys.ENTER);
		     			    tec.AC40.Common.Wait.wait2Second();
		     			    d.findElement(Property.Lexpiry).click();
		     			    tec.AC40.Common.Wait.wait2Second();
		     			    kb.pressKey(Keys.ENTER);
		     			    tec.AC40.Common.Wait.wait2Second();
		     			    d.findElement(Property.ProductInfoSave).click();
		     			    tec.AC40.Common.Wait.wait5Second();
		     			   waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserBackToHome));
		            		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.UserBackToHome));
		            		
		            		waitfl.until(new Function<WebDriver, WebElement>() 
		            				 {
		            				   public WebElement apply(WebDriver driver) {
		            				   return driver.findElement(Property.UserBackToHome);
		            				 }
		            		});
		     			    d.findElement(Property.LPricing).click();
		     			   waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Lpricingrd));
		            		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Lpricingrd));
		            		
		            		waitfl.until(new Function<WebDriver, WebElement>() 
		            				 {
		            				   public WebElement apply(WebDriver driver) {
		            				   return driver.findElement(Property.Lpricingrd);
		            				 }
		            				 });
		     			    d.findElement(Property.Lpricingrd).click();
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.Lpricecode).click();
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    kb.pressKey(Keys.ARROW_DOWN);
		     			    kb.pressKey(Keys.ARROW_DOWN);
		     			    kb.pressKey(Keys.ARROW_DOWN);
		     			    kb.pressKey(Keys.ARROW_DOWN);
		     			    kb.pressKey(Keys.ENTER);
		     			   tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.Lpricesave).click();
		     			   tec.AC40.Common.Wait.wait5Second();

		     			  d.findElement(Property.ProductOnlineLink).click();
		     			   tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.ProductAlertOK).click();
		     			   tec.AC40.Common.Wait.wait5Second();
		     			    System.out.println("Product Info Settings completed successfully......");
		     			   waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserBackToHome));
		            		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.UserBackToHome));
		     			  waitfl.until(new Function<WebDriver, WebElement>() 
		     			  {
		     				  public WebElement apply(WebDriver driver) {
		     					  return driver.findElement(Property.UserBackToHome);
		     				  }
		     			  	});
		     			 tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.LBackToProducts).click();
		     			   waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsSearchBox));
		     				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsSearchBox));
		     				
		     				waitfl.until(new Function<WebDriver, WebElement>() 
		     						 {
		     						   public WebElement apply(WebDriver driver) {
		     						   return driver.findElement(Property.ProductsSearchBox);
		     						 }
		     						 });
							}
							
		              		else{
		              			System.out.println("GURL Product already created");
		              		}
		   }
			 if(i==2){
				 
				 boolean Purl= d.findElement(Property.ProductsSearchBox).isDisplayed();
				 if(Purl==true){
		             d.findElement(Property.ProductsSearchBox).clear();
		             d.findElement(Property.ProductsSearchBox).sendKeys("PGURL");
		             tec.AC40.Common.Wait.wait5Second();
				               }
				 else{
					 System.out.println("NO Landing Products");
				 	 }
		              if(d.findElement(Property.NoLandingProduct).isDisplayed())
		                {
		            	  d.findElement(Property.NewLandingProduct).click();
		            	  waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProviderL));
		            		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProviderL));
		            		
		            		waitfl.until(new Function<WebDriver, WebElement>() 
		            				 {
		            				   public WebElement apply(WebDriver driver) {
		            				   return driver.findElement(Property.ProviderL);
		            				 }
		            		});
		            	  d.findElement(Property.ProviderL).click();
		            	  kb.pressKey(Keys.ARROW_DOWN);
		            	  kb.pressKey(Keys.ARROW_DOWN);
		            	  kb.pressKey(Keys.ENTER);
		            	  tec.AC40.Common.Wait.wait5Second();
		            	  d.findElement(Property.LItemProces).click();
		            	  tec.AC40.Common.Wait.wait2Second();
		            	  kb.pressKey(Keys.ARROW_DOWN);
		            	  kb.pressKey(Keys.ARROW_DOWN);
		            	  kb.pressKey(Keys.ENTER);
		            	  d.findElement(Property.HURL).clear();
		            	  tec.AC40.Common.Wait.wait2Second();
		            	  d.findElement(Property.HURL).sendKeys("http://192.168.1.121/LandingPages/purl/step1.html");
		            	  tec.AC40.Common.Wait.wait5Second();
		            	  d.findElement(Property.Scema).click();
		     			  By.xpath(".//*[@id='divFlupSchemaDocumentFile']/div/div/div/div/div");
		     			  tec.AC40.Common.Wait.wait5Second();
		     			 //Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\TestData\\IMAGES\\red.png");
		     				//tec.Config.wait.wait10Second();
		     				String fileLocation4 = System.getProperty("user.dir") + "\\TestData" + "\\Lists" + "\\ACC_RQ_000015_LandingPageTemplate.xml";
		     				StringSelection ss = new StringSelection(fileLocation4);
		     			    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

		     			    //imitate mouse events like ENTER, CTRL+C, CTRL+V
		     			    Robot robot = new Robot();
		     			    robot.keyPress(KeyEvent.VK_ENTER);
		     			    robot.keyRelease(KeyEvent.VK_ENTER);
		     			    robot.keyPress(KeyEvent.VK_CONTROL);
		     			    robot.keyPress(KeyEvent.VK_V);
		     			    robot.keyRelease(KeyEvent.VK_V);
		     			    robot.keyRelease(KeyEvent.VK_CONTROL);
		     			    robot.keyPress(KeyEvent.VK_ENTER);
		     			    robot.keyRelease(KeyEvent.VK_ENTER);
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.DominName).sendKeys("testqa123.com");
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.ActiveDays).sendKeys("10");
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.Productname).sendKeys("PGURL");
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.Productcode).sendKeys("PGURL");
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.ProdcutDesciption).click();
		     			    kb.sendKeys("PGURL Description");
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.Lcategory1).click();
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.Lcategoryoption).click();
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.LcategorySave).click();
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.Thumbnail).click();
		     			    tec.AC40.Common.Wait.wait5Second();
		     				d.findElement(Property.Thumbnail1).click();
		     				tec.AC40.Common.Wait.wait5Second();
		     				String fileLocation5 = System.getProperty("user.dir") + "\\TestData" + "\\IMAGES" + "\\download3.jpg";
		     				StringSelection sb3 = new StringSelection(fileLocation5);
		     					    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sb3, null);

		     					    //imitate mouse events like ENTER, CTRL+C, CTRL+V
		     					    robot.keyPress(KeyEvent.VK_ENTER);
		     					    robot.keyRelease(KeyEvent.VK_ENTER);
		     					    robot.keyPress(KeyEvent.VK_CONTROL);
		     					    robot.keyPress(KeyEvent.VK_V);
		     					    robot.keyRelease(KeyEvent.VK_V);
		     					    robot.keyRelease(KeyEvent.VK_CONTROL);
		     					    robot.keyPress(KeyEvent.VK_ENTER);
		     					    robot.keyRelease(KeyEvent.VK_ENTER);
		     				   tec.AC40.Common.Wait.wait5Second();
		     			    
		     			    
		     			    d.findElement(Property.LMinQnt).sendKeys("1");
		     			    tec.AC40.Common.Wait.wait2Second();
		     			    d.findElement(Property.Lleaddays).sendKeys("0");
		     			    tec.AC40.Common.Wait.wait2Second();
		     			    d.findElement(Property.Lstartdate).click();
		     			    tec.AC40.Common.Wait.wait2Second();
		     			    kb.pressKey(Keys.ENTER);
		     			    tec.AC40.Common.Wait.wait2Second();
		     			    d.findElement(Property.Lexpiry).click();
		     			    tec.AC40.Common.Wait.wait2Second();
		     			    kb.pressKey(Keys.ENTER);
		     			    tec.AC40.Common.Wait.wait2Second();
		     			    d.findElement(Property.ProductInfoSave).click();
		     			    tec.AC40.Common.Wait.wait5Second();
		     			   waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserBackToHome));
		            		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.UserBackToHome));
		            		
		            		waitfl.until(new Function<WebDriver, WebElement>() 
		            				 {
		            				   public WebElement apply(WebDriver driver) {
		            				   return driver.findElement(Property.LPricing);
		            				 }
		            		});
		     			    d.findElement(Property.LPricing).click();
		     			   waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Lpricingrd));
		            		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Lpricingrd));
		            		
		            		waitfl.until(new Function<WebDriver, WebElement>() 
		            				 {
		            				   public WebElement apply(WebDriver driver) {
		            				   return driver.findElement(Property.Lpricingrd);
		            				 }
		            				 });
		     			    d.findElement(Property.Lpricingrd).click();
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.Lpricecode).click();
		     			    tec.AC40.Common.Wait.wait5Second();
		     			    kb.pressKey(Keys.ARROW_DOWN);
		     			    kb.pressKey(Keys.ARROW_DOWN);
		     			    kb.pressKey(Keys.ARROW_DOWN);
		     			    kb.pressKey(Keys.ARROW_DOWN);
		     			    kb.pressKey(Keys.ENTER);
		     			   tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.Lpricesave).click();
		     			   tec.AC40.Common.Wait.wait5Second();
		     			  waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Llisttype));
		            		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Llisttype));
		     			  waitfl.until(new Function<WebDriver, WebElement>() 
	     				 {
	     				   public WebElement apply(WebDriver driver) {
	     				   return driver.findElement(Property.Llisttype);
	     				 }
	     				 });
		     			    d.findElement(Property.Llisttype).click();
		     			   tec.AC40.Common.Wait.wait2Second();
		     			  waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Ldisplayname));
		            		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Ldisplayname));
		     			  waitfl.until(new Function<WebDriver, WebElement>() 
	   				 {
	   				   public WebElement apply(WebDriver driver) {
	   				   return driver.findElement(Property.Ldisplayname);
	   				 }
	   				 });
		     			    d.findElement(Property.Ldisplayname).sendKeys("price list10");
		     			   tec.AC40.Common.Wait.wait2Second();
		     			    d.findElement(Property.Ldisplay).sendKeys("1");
		     			   tec.AC40.Common.Wait.wait2Second();
		     			    d.findElement(Property.LlistSave).click();
		     			   tec.AC40.Common.Wait.wait5Second();
		     			  waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductOnlineLink));
		            		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductOnlineLink));
		     			  waitfl.until(new Function<WebDriver, WebElement>() 
	 				 {
	 				   public WebElement apply(WebDriver driver) {
	 				   return driver.findElement(Property.ProductOnlineLink);
	 				 }
	 				 });
		     			    d.findElement(Property.ProductOnlineLink).click();
		     			   tec.AC40.Common.Wait.wait5Second();
		     			    d.findElement(Property.ProductAlertOK).click();
		     			   tec.AC40.Common.Wait.wait5Second();
		     			    System.out.println("Product Info Settings completed successfully......");
		     			   waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserBackToHome));
		            		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.UserBackToHome));
		     			  waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.UserBackToHome);
					 }
					 });
		     			    d.findElement(Property.LBackToProducts).click();
		     			    Thread.sleep(7000);
		     			   waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsSearchBox));
		   				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsSearchBox));
		   				
		   				waitfl.until(new Function<WebDriver, WebElement>() 
		   						 {
		   						   public WebElement apply(WebDriver driver) {
		   						   return driver.findElement(Property.ProductsSearchBox);
		   						 }
		   						 }); 
		     			    
		                	}
		              		else{
		              			System.out.println("PGURL Product already created");
		              		}
			 
			 }
			 
		  }
		  System.out.println("Landing Products are created Successfully ");
		  d.findElement(Property.Backtohome).click();
			
		}
	
	
	
	public static String Decimalsetting2(String Datavalue, String DecimalValue)
			throws InterruptedException, AWTException {
		NumberFormat numberFormatter = null;
		String Datavalue1 = null;
		 String amountOut;
		 String amount = Datavalue;
		 String value = null;
		 MouseAdjFunction();
		if(Datavalue.isEmpty())
		{
			return "";	
		}
		else
		{
			if(DecimalValue.equals("2.0"))
			{
				Datavalue1 = String.format("%.2f", new BigDecimal(amount));
				String[] amountparts = Datavalue1.split("\\.");
				numberFormatter = NumberFormat.getNumberInstance();
				Double num = Double.parseDouble(amountparts[0]);
				amountOut = numberFormatter.format(num);
				value = amountOut+"."+amountparts[1];
			}
			else if(DecimalValue.equals("3.0"))
			{

				Datavalue1 = String.format("%.3f", new BigDecimal(amount));
				//System.out.println(Datavalue1);
				String[] amountparts = Datavalue1.split("\\.");
				//System.out.println(amountparts[0]);
				//System.out.println(amountparts[1]);
				numberFormatter = NumberFormat.getNumberInstance();
				Double num = Double.parseDouble(amountparts[0]);
				amountOut = numberFormatter.format(num);
				value = amountOut+"."+amountparts[1];
				//System.out.println("value : "+value);
				
			}
			else if(DecimalValue.equals("4.0"))
			{

				Datavalue1 = String.format("%.4f", new BigDecimal(amount));
				//System.out.println(Datavalue1);
				String[] amountparts = Datavalue1.split("\\.");
				//System.out.println(amountparts[0]);
				//System.out.println(amountparts[1]);
				numberFormatter = NumberFormat.getNumberInstance();
				Double num = Double.parseDouble(amountparts[0]);
				amountOut = numberFormatter.format(num);
				value = amountOut+"."+amountparts[1];
				//System.out.println("value : "+value);
				
			}
			//System.out.println("Base test result : "+value);
			
		}
	
		return value;
	}
	
	public static void DecimalvalueSetting(String DecimalValue, String Tax, String IsShippingTaxable,
			String OrderAmountValue, String Weightdecimalvalue,String Weighttype,String userordershippingorhandlingfee,
            String IsPostageTaxable, String PaymentSubOpt, String PaymentType,String CalculateTaxCondition, 
            String EnablePromotionsORDiscounts,String FullfilmentShippingOrHandlingFee,String FullfilmentShippingMarkupFee,
            String OrderBase,String EnableZeroAmountOrder, String TestStep, String CostCenter,
            String ShipAddSameAsBillAdd, String WeightPerPackage, String IsExternalPricingON,String Reorderflow, String ApproverEdit,String OrderType)
			throws InterruptedException {
      try{
    	  MouseAdjFunction();
    	  
    	  WebDriverWait wait1 = new WebDriverWait(d, Config.ElementWaitTime);
    	  Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
    	  
    	/*  	FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
    		 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
    		 waitfl.pollingEvery(1, TimeUnit.SECONDS);
    		 waitfl.ignoring(NoSuchElementException.class);
    		 waitfl.ignoring(StaleElementReferenceException.class);
 		 */
    		 
    		 FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d)
    				    .withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS)
    				    .pollingEvery(1, TimeUnit.SECONDS)
    				    .ignoring(NoSuchElementException.class)
    		 			.ignoring(StaleElementReferenceException.class);
    		 if(Config.LayoutType.equals("Layout1"))
       	  { 
    		 waitfl.until(new Function<WebDriver, WebElement>() 
    				 {
    				   public WebElement apply(WebDriver driver) {
    				   return driver.findElement(Property.HomeLinkL1);
    				 }
    				 });
       	  }
    	  if(Config.LayoutType.equals("Classic"))
    	  {
    	  waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
    	  waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
    	  
    	  waitfl.until(new Function<WebDriver, WebElement>() 
 				 {
 				   public WebElement apply(WebDriver driver) {
 				   return driver.findElement(Property.CompanyLink);
 				 }
 				 });
    	  
    	  tec.AC40.Common.Wait.wait2Second();
    	  d.findElement(Property.CompanyLink).click();
  		  waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ManageOrgunitIcon));
  		  waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ManageOrgunitIcon));
  		  
  		 waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.ManageOrgunitIcon);
				 }
				 });
  		  
  		  tec.AC40.Common.Wait.wait2Second();
  		 d.findElement(Property.ManageOrgunitIcon).click();
  		 tec.AC40.Common.Wait.wait2Second();
    	  }
    	  else if(Config.LayoutType.equals("Layout1"))
    	  {
    		  /*waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
    		  waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
    		  tec.AC40.Common.Wait.wait2Second();*/
    		  waitfl.until(ExpectedConditions.elementToBeClickable(Property.CompanyIconL1));
    		  waitfl.until(new Function<WebDriver, WebElement>() 
     				 {
     				   public WebElement apply(WebDriver driver) {
     				   return driver.findElement(Property.CompanyIconL1);
     				 }
     				 });
    		  
    		  tec.AC40.Common.Wait.wait2Second();
    		  d.findElement(Property.CompanyIconL1).click();
    		  waitfl.until(ExpectedConditions.elementToBeClickable(Property.ManageOrguntiIconL1));
    		  //waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ManageOrguntiIconL1));
    		  //waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ManageOrguntiIconL1));
    		  
    		  waitfl.until(new Function<WebDriver, WebElement>() 
     				 {
     				   public WebElement apply(WebDriver driver) {
     				   return driver.findElement(Property.ManageOrguntiIconL1);
     				 }
     				 });
    		  
    		  tec.AC40.Common.Wait.wait2Second();
    		  d.findElement(Property.ManageOrguntiIconL1).click();
    		  tec.AC40.Common.Wait.wait2Second();
    	  }
    	  else if(Config.LayoutType.equals("Layout2"))
    	  {
    		  waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
    		  waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
    		  
    		  waitfl.until(new Function<WebDriver, WebElement>() 
     				 {
     				   public WebElement apply(WebDriver driver) {
     				   return driver.findElement(Property.CompanyIconL2);
     				 }
     				 });
    		  
    		  tec.AC40.Common.Wait.wait2Second();
    		  d.findElement(Property.CompanyIconL2).click();
    		  waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ManageOrguntiIconL2));
    		  waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ManageOrguntiIconL2));
    		  
    		  waitfl.until(new Function<WebDriver, WebElement>() 
     				 {
     				   public WebElement apply(WebDriver driver) {
     				   return driver.findElement(Property.ManageOrguntiIconL2);
     				 }
     				 });
    		  
    		  tec.AC40.Common.Wait.wait2Second();
    		  d.findElement(Property.ManageOrguntiIconL2).click();
    			tec.AC40.Common.Wait.wait2Second();
    	  }
    	//waitfl.until(ExpectedConditions.elementToBeClickable(Property.EnableProductionCheckBox));
    	//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.EnableProductionCheckBox));
    	waitfl.until(ExpectedConditions.elementToBeClickable(Property.EnableProductionCheckBox));
     
    	 waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.EnableProductionCheckBox);
				 }
				 });
    	
		  // Reorder option
    	tec.AC40.Common.Wait.wait2Second();
    	boolean reorder=d.findElement(Property.reorderoption1).isSelected();

		if(Reorderflow.equalsIgnoreCase("YES")){
			if(reorder==true){
				//System.out.println("******do nothing********");
			}else{
				d.findElement(Property.reorderoption1).click();
			}
	
        }else if(Reorderflow.equalsIgnoreCase("NO")){
			if(reorder==true){
				//System.out.println("******click on check box****");
				d.findElement(Property.reorderoption1).click();
			}else{
				//System.out.println("do nothing");							
				}
        }
    	tec.AC40.Common.Wait.wait2Second();
    	
    	//Approver edit

    	boolean approver1=d.findElement(Property.approveredit1).isSelected();

		if(ApproverEdit.equalsIgnoreCase("YES")){
			if(approver1==true){
				//System.out.println("******do nothing********");
			}else{
				d.findElement(Property.approveredit1).click();
			}
	
        }else if(ApproverEdit.equalsIgnoreCase("NO")){
			if(approver1==true){
				//System.out.println("******click on check box****");
				d.findElement(Property.approveredit1).click();
			}else{
			//	System.out.println("do nothing");							
				}
        }
    	
    	
    	boolean approver=d.findElement(Property.approveredit).isSelected();

		if(ApproverEdit.equalsIgnoreCase("YES")){
			if(approver==true){
				//System.out.println("******do nothing********");
			}else{
				d.findElement(Property.approveredit).click();
			}
	
        }else if(ApproverEdit.equalsIgnoreCase("NO")){
			if(approver==true){
				//System.out.println("******click on check box****");
				d.findElement(Property.approveredit).click();
			}else{
			//	System.out.println("do nothing");							
				}
        }

    	// Select Tax provider in drop down
    	  String SelectedTaxProvider = d.findElement(Property.SelectedTaxProvider).getText();
    	  
    	  String[] TaxCalculationCondition = CalculateTaxCondition.split("_");
    	  
    	  if(!SelectedTaxProvider.equals(TaxCalculationCondition[0]))
    	  {
        	  d.findElement(Property.TaxProviderDropDown).click();
        	  
        	  tec.AC40.Common.Wait.wait2Second();
        	  String Taxlist = d.findElement(Property.TaxProviderList).getText();
        	 // System.out.println("Tax List : "+Taxlist);
        	  String[] TaxValues = Taxlist.split("\n");
    		  
    		  for(int i = 0; i< TaxValues.length; i++)
    		  {
    			  if(TaxValues[i].equals(TaxCalculationCondition[0]))
    			  {
    			  int j = i+1;
    			  d.findElement(By.xpath("//ul[@id='ddlTaxProviders_listbox']/li["+j+"]")).click();
    			  kb.pressKey(Keys.ENTER);
    			  break;
    			  }
    		  }
    	  }
    	  Thread.sleep(1000);
    	  // Enable Calculate tax condition check box
    	  
    	  boolean IsTaxCheckBoxChecked = d.findElement(Property.CalculateTaxCheckBox).isSelected();
    	  
    	  if(TaxCalculationCondition[1].equals("ON"))
    	  {
    		  if(IsTaxCheckBoxChecked == true)
    		  {
    			  
    		  }
    		  else
    		  {
    			  d.findElement(Property.CalculateTaxCheckBox).click();
    		  }
    	  }
    	  else
    	  {
    		  if(IsTaxCheckBoxChecked == true)
    		  {
    			  d.findElement(Property.CalculateTaxCheckBox).click();
    		  }
    		 
    	  }
    	  
    	  // Enable promotions or Discounts
    	  	boolean EnablePromotionOrDiscounts = d.findElement(Property.EnablePromotionsORDiscounts).isSelected();
    	  
    	  	//System.out.println("EnablePromotionOrDiscounts"+EnablePromotionOrDiscounts);
    	  	//System.out.println("EnablePromotionsORDiscounts"+EnablePromotionsORDiscounts);
    	  	
    	  if(EnablePromotionsORDiscounts.equals("ON"))
    	  {
    		  if(EnablePromotionOrDiscounts == true)
    		  {
    			  
    		  }
    		  else
    		  {
    			  d.findElement(Property.EnablePromotionsORDiscounts).click();
    		  }
    	  }
    	  else
    	  {
    		  if(EnablePromotionOrDiscounts == true)
    		  {
    			  d.findElement(Property.EnablePromotionsORDiscounts).click();
    		  }
    		 
    	  }
    	 
 
  		//System.out.println("DecimalValue : "+DecimalValue);
  		
  		int Decimalvalue = Double.valueOf(DecimalValue).intValue();
  		int Weightdecimalvalue1 = Double.valueOf(Weightdecimalvalue).intValue();
  		int OrderAmountvalue = Double.valueOf(OrderAmountValue).intValue();
  		tec.AC40.Common.Wait.wait2Second();
  		
  		
  		// ***************  Decimal value setting  ************************
  		
  		if(Decimalvalue == 0)  			
  		{
  			//System.out.println("Presnet Decimal value is : "+Decimalvalue);
  			if(Config.browser.equals("GC"))
  			{
  				//waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CurrencyDecimaldropdown));
  				waitfl.until(ExpectedConditions.elementToBeClickable(Property.CurrencyDecimaldropdown));
  				tec.AC40.Common.Wait.wait2Second();
  				
  				 waitfl.until(new Function<WebDriver, WebElement>() 
  	    				 {
  	    				   public WebElement apply(WebDriver driver) {
  	    				   return driver.findElement(Property.CurrencyDecimaldropdown);
  	    				 }
  	    				 });
  				
  				d.findElement(Property.CurrencyDecimaldropdown).click();
  	  			//tec.AC40.Common.Wait.wait5Second();
  	  			//d.findElement(Property.CurrencyDecimaldropdown).click();
  	  			//tec.AC40.Common.Wait.wait2Second();
  			/*	waitfl.until(ExpectedConditions.elementToBeClickable(Property.CurrencyDecimalSelection1));
  				
  				 waitfl.until(new Function<WebDriver, WebElement>() 
  	    				 {
  	    				   public WebElement apply(WebDriver driver) {
  	    				   return driver.findElement(Property.CurrencyDecimalSelection1);
  	    				 }
  	    				 });
  				
  				tec.AC40.Common.Wait.wait2Second();
  				d.findElement(Property.CurrencyDecimalSelection1).click();
  	  			tec.AC40.Common.Wait.wait2Second();*/
  				tec.AC40.Common.Wait.wait2Second();
  				kb.sendKeys("0");
  	  			kb.pressKey(Keys.ENTER);
  	  			tec.AC40.Common.Wait.wait2Second();
  	  		
  			}
  			else if(Config.browser.equals("FF"))
  			{
  				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CurrencyDecimaldropdown));
  				tec.AC40.Common.Wait.wait2Second();
  				d.findElement(Property.CurrencyDecimaldropdown).click();
  	  			tec.AC40.Common.Wait.wait2Second();
  	  			d.findElement(Property.CurrencyDecimalSelection1).click();
  	  			tec.AC40.Common.Wait.wait2Second();
  			}
  		
  		}

		 Thread.sleep(2000);

  		if(Decimalvalue == 2)  			
  		{
  			//System.out.println("Presnet Decimal value is : "+Decimalvalue);
  			if(Config.browser.equals("GC"))
  			{
  				waitfl.until(ExpectedConditions.elementToBeClickable(Property.CurrencyDecimaldropdown));
  				
  				 waitfl.until(new Function<WebDriver, WebElement>() 
  	    				 {
  	    				   public WebElement apply(WebDriver driver) {
  	    				   return driver.findElement(Property.CurrencyDecimaldropdown);
  	    				 }
  	    				 });
  				
  				tec.AC40.Common.Wait.wait2Second();
  				d.findElement(Property.CurrencyDecimaldropdown).click();
  				/*waitfl.until(ExpectedConditions.elementToBeClickable(Property.CurrencyDecimalSelection2));
  				
  				 waitfl.until(new Function<WebDriver, WebElement>() 
  	    				 {
  	    				   public WebElement apply(WebDriver driver) {
  	    				   return driver.findElement(Property.CurrencyDecimalSelection2);
  	    				 }
  	    				 });
  				
  				tec.AC40.Common.Wait.wait2Second();
  				d.findElement(Property.CurrencyDecimalSelection2).click();*/
  				
  	  		
  	  		kb.sendKeys("2");
  			kb.pressKey(Keys.ENTER);
  			tec.AC40.Common.Wait.wait2Second();
  			}
  			else if(Config.browser.equals("FF"))
  			{
  				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CurrencyDecimaldropdown));
  				tec.AC40.Common.Wait.wait2Second();
  				d.findElement(Property.CurrencyDecimaldropdown).click();
  	  			tec.AC40.Common.Wait.wait2Second();
  	  			d.findElement(Property.CurrencyDecimalSelection2).click();
  	  			tec.AC40.Common.Wait.wait2Second();
  			}
  		}
  		else if (Decimalvalue == 3)
  		{
  			//System.out.println("Presnet Decimal value is : "+Decimalvalue);
  			if(Config.browser.equals("GC"))
  			{
  				waitfl.until(ExpectedConditions.elementToBeClickable(Property.CurrencyDecimaldropdown));
  				
  				 waitfl.until(new Function<WebDriver, WebElement>() 
  	    				 {
  	    				   public WebElement apply(WebDriver driver) {
  	    				   return driver.findElement(Property.CurrencyDecimaldropdown);
  	    				 }
  	    				 });
  				
  				tec.AC40.Common.Wait.wait2Second();
  				d.findElement(Property.CurrencyDecimaldropdown).click();
  			/*	waitfl.until(ExpectedConditions.elementToBeClickable(Property.CurrencyDecimalSelection3));
  	  			
  				 waitfl.until(new Function<WebDriver, WebElement>() 
  	    				 {
  	    				   public WebElement apply(WebDriver driver) {
  	    				   return driver.findElement(Property.CurrencyDecimalSelection3);
  	    				 }
  	    				 });
  				
  				tec.AC40.Common.Wait.wait2Second();
  	  			d.findElement(Property.CurrencyDecimalSelection3).click();
  	  			tec.AC40.Common.Wait.wait2Second();*/
  				tec.AC40.Common.Wait.wait2Second();
  				kb.sendKeys("3");
  	  			kb.pressKey(Keys.ENTER);
  	  			tec.AC40.Common.Wait.wait2Second();
  	  		
  			}
  			else if(Config.browser.equals("FF"))
  			{
  				waitfl.until(ExpectedConditions.elementToBeClickable(Property.CurrencyDecimaldropdown));
  				tec.AC40.Common.Wait.wait2Second();
  				d.findElement(Property.CurrencyDecimaldropdown).click();
  	  			tec.AC40.Common.Wait.wait2Second();
  	  			d.findElement(Property.CurrencyDecimalSelection3).click();
  	  			tec.AC40.Common.Wait.wait2Second();
  			}
  		}
  		else if(Decimalvalue  == 4)
  		{
  			if(Config.browser.equals("GC"))
  			{
  				waitfl.until(ExpectedConditions.elementToBeClickable(Property.CurrencyDecimaldropdown));
  				
  				 waitfl.until(new Function<WebDriver, WebElement>() 
  	    				 {
  	    				   public WebElement apply(WebDriver driver) {
  	    				   return driver.findElement(Property.CurrencyDecimaldropdown);
  	    				 }
  	    				 });
  				
  				tec.AC40.Common.Wait.wait2Second();
  				d.findElement(Property.CurrencyDecimaldropdown).click();
  	  		/*	tec.AC40.Common.Wait.wait2Second();
  	  			//d.findElement(Property.CurrencyDecimaldropdown).click();
  	  			waitfl.until(ExpectedConditions.elementToBeClickable(Property.CurrencyDecimalSelection4));
  	  			
  	  		 waitfl.until(new Function<WebDriver, WebElement>() 
    				 {
    				   public WebElement apply(WebDriver driver) {
    				   return driver.findElement(Property.CurrencyDecimalSelection4);
    				 }
    				 });
  	  			
  	  			tec.AC40.Common.Wait.wait2Second();
  	  			d.findElement(Property.CurrencyDecimalSelection4).click();
  	  			tec.AC40.Common.Wait.wait2Second();*/
  				tec.AC40.Common.Wait.wait2Second();
  				kb.sendKeys("4");
  	  			kb.pressKey(Keys.ENTER);
  	  			tec.AC40.Common.Wait.wait2Second();
  	  	
  			}
  			else if(Config.browser.equals("FF"))
  			{
  				waitfl.until(ExpectedConditions.elementToBeClickable(Property.CurrencyDecimaldropdown));
  				tec.AC40.Common.Wait.wait2Second();
  				d.findElement(Property.CurrencyDecimaldropdown).click();
  	  			tec.AC40.Common.Wait.wait2Second();
  	  			d.findElement(Property.CurrencyDecimalSelection4).click();
  	  			tec.AC40.Common.Wait.wait2Second();
  			}
  		}
  		
		//*******Weight decimal value setting *************
  		tec.AC40.Common.Wait.wait2Second();
  		if(Weightdecimalvalue1 == 0)  			
  		{
  			//System.out.println("Presnet Decimal value is : "+Decimalvalue);
  			if(Config.browser.equals("GC"))
  			{
  				waitfl.until(ExpectedConditions.elementToBeClickable(Property.weightdecimaldropdown));
  				
  				 waitfl.until(new Function<WebDriver, WebElement>() 
  	    				 {
  	    				   public WebElement apply(WebDriver driver) {
  	    				   return driver.findElement(Property.weightdecimaldropdown);
  	    				 }
  	    				 });
  				
  				tec.AC40.Common.Wait.wait2Second();
  				d.findElement(Property.weightdecimaldropdown).click();
  	  			/*tec.AC40.Common.Wait.wait2Second();
  	  			waitfl.until(ExpectedConditions.elementToBeClickable(Property.weightdecimalselection1));
  	  			
  	  		 waitfl.until(new Function<WebDriver, WebElement>() 
    				 {
    				   public WebElement apply(WebDriver driver) {
    				   return driver.findElement(Property.weightdecimalselection1);
    				 }
    				 });
  	  			
  	  			tec.AC40.Common.Wait.wait2Second();
  	  			d.findElement(Property.weightdecimalselection1).click();
  	  			tec.AC40.Common.Wait.wait2Second();*/
  				tec.AC40.Common.Wait.wait2Second();
  				kb.sendKeys("0");
  	  			kb.pressKey(Keys.ENTER);
  	  			tec.AC40.Common.Wait.wait2Second();
  	  		
  			}
  			else if(Config.browser.equals("FF"))
  			{
  				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.weightdecimaldropdown));
  				tec.AC40.Common.Wait.wait2Second();
  				d.findElement(Property.weightdecimaldropdown).click();
  	  			tec.AC40.Common.Wait.wait2Second();
  	  			d.findElement(Property.weightdecimalselection1).click();
  	  			tec.AC40.Common.Wait.wait2Second();
  			}
  		
  		}
  		if(Weightdecimalvalue1 == 2)  			
  		{
  			//System.out.println("Presnet Decimal value is : "+Decimalvalue);
  			if(Config.browser.equals("GC"))
  			{
  				waitfl.until(ExpectedConditions.elementToBeClickable(Property.weightdecimaldropdown));
  				
  				 waitfl.until(new Function<WebDriver, WebElement>() 
  	    				 {
  	    				   public WebElement apply(WebDriver driver) {
  	    				   return driver.findElement(Property.weightdecimaldropdown);
  	    				 }
  	    				 });
  				
  				tec.AC40.Common.Wait.wait2Second();
  				d.findElement(Property.weightdecimaldropdown).click();
  		/*		waitfl.until(ExpectedConditions.elementToBeClickable(Property.weightdecimalselection2));
  				
  				 waitfl.until(new Function<WebDriver, WebElement>() 
  	    				 {
  	    				   public WebElement apply(WebDriver driver) {
  	    				   return driver.findElement(Property.weightdecimalselection2);
  	    				 }
  	    				 });

  				
  				tec.AC40.Common.Wait.wait2Second();
  				d.findElement(Property.weightdecimalselection2).click();*/
  				tec.AC40.Common.Wait.wait2Second();
  				kb.sendKeys("2");
  	  			kb.pressKey(Keys.ENTER);
  	  			tec.AC40.Common.Wait.wait2Second();
  	  		
  			}
  			else if(Config.browser.equals("FF"))
  			{
  				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.weightdecimaldropdown));
  				tec.AC40.Common.Wait.wait2Second();
  				d.findElement(Property.weightdecimaldropdown).click();
  	  			tec.AC40.Common.Wait.wait2Second();
  	  			d.findElement(Property.weightdecimalselection2).click();
  	  			tec.AC40.Common.Wait.wait2Second();
  			}
  			
  		
  		}
  		else if (Weightdecimalvalue1 == 3)
  		{
  			//System.out.println("Presnet Decimal value is : "+Decimalvalue);
  			if(Config.browser.equals("GC"))
  			{
  				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.weightdecimaldropdown));
  				
  				 waitfl.until(new Function<WebDriver, WebElement>() 
  	    				 {
  	    				   public WebElement apply(WebDriver driver) {
  	    				   return driver.findElement(Property.weightdecimaldropdown);
  	    				 }
  	    				 });

  				
  				tec.AC40.Common.Wait.wait2Second();
  				d.findElement(Property.weightdecimaldropdown).click();
  	  			tec.AC40.Common.Wait.wait2Second();
  	  			//d.findElement(Property.CurrencyDecimaldropdown).click();
  	  			/*waitfl.until(ExpectedConditions.elementToBeClickable(Property.weightdecimalselection3));
  	  			
  	  		 waitfl.until(new Function<WebDriver, WebElement>() 
    				 {
    				   public WebElement apply(WebDriver driver) {
    				   return driver.findElement(Property.weightdecimalselection3);
    				 }
    				 });
  	  			
  	  			tec.AC40.Common.Wait.wait2Second();
  	  			d.findElement(Property.weightdecimalselection3).click();*/
  	  		tec.AC40.Common.Wait.wait2Second();
				kb.sendKeys("3");
	  			kb.pressKey(Keys.ENTER);
	  			tec.AC40.Common.Wait.wait2Second();
  	  		
  			}
  			else if(Config.browser.equals("FF"))
  			{
  				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.weightdecimaldropdown));
  				tec.AC40.Common.Wait.wait2Second();
  				d.findElement(Property.weightdecimaldropdown).click();
  	  			tec.AC40.Common.Wait.wait2Second();
  	  			d.findElement(Property.weightdecimalselection3).click();
  	  			tec.AC40.Common.Wait.wait2Second();
  			}
  		}
  		else if(Weightdecimalvalue1  == 4)
  		{
  			if(Config.browser.equals("GC"))
  			{
  				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.weightdecimaldropdown));
  				
  				 waitfl.until(new Function<WebDriver, WebElement>() 
  	    				 {
  	    				   public WebElement apply(WebDriver driver) {
  	    				   return driver.findElement(Property.weightdecimaldropdown);
  	    				 }
  	    				 });

  				
  				tec.AC40.Common.Wait.wait2Second();
  				d.findElement(Property.weightdecimaldropdown).click();
  	  		/*	tec.AC40.Common.Wait.wait5Second();
  	  			waitfl.until(ExpectedConditions.elementToBeClickable(Property.weightdecimalselection4));
  	  			
  	  			
  	  		 waitfl.until(new Function<WebDriver, WebElement>() 
    				 {
    				   public WebElement apply(WebDriver driver) {
    				   return driver.findElement(Property.weightdecimalselection4);
    				 }
    				 });
  	  			
  	  			tec.AC40.Common.Wait.wait2Second();
  	  			d.findElement(Property.weightdecimalselection4).click();*/
  				tec.AC40.Common.Wait.wait2Second();
  				kb.sendKeys("4");
  	  			kb.pressKey(Keys.ENTER);
  	  			tec.AC40.Common.Wait.wait2Second();
  			}
  			else if(Config.browser.equals("FF"))
  			{
  				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.weightdecimaldropdown));
  				tec.AC40.Common.Wait.wait2Second();
  				d.findElement(Property.weightdecimaldropdown).click();
  	  			tec.AC40.Common.Wait.wait2Second();
  	  			d.findElement(Property.weightdecimalselection4).click();
  	  			tec.AC40.Common.Wait.wait2Second();
  			}
  		}
		      tec.AC40.Common.Wait.wait2Second();
		      if(OrderAmountvalue == 0)  			
		      {
  			if(Config.browser.equals("GC"))
  			{
  				waitfl.until(ExpectedConditions.elementToBeClickable(Property.OrderAmoutDecimal));
  				
  				
  				 waitfl.until(new Function<WebDriver, WebElement>() 
  	    				 {
  	    				   public WebElement apply(WebDriver driver) {
  	    				   return driver.findElement(Property.OrderAmoutDecimal);
  	    				 }
  	    				 });
  				
  				tec.AC40.Common.Wait.wait2Second();
  	  			d.findElement(Property.OrderAmoutDecimal).click();
	  		/*	waitfl.until(ExpectedConditions.elementToBeClickable(Property.OrderAmountSelection1));
	  			
	  			 waitfl.until(new Function<WebDriver, WebElement>() 
	    				 {
	    				   public WebElement apply(WebDriver driver) {
	    				   return driver.findElement(Property.OrderAmountSelection1);
	    				 }
	    				 });

	  			tec.AC40.Common.Wait.wait2Second();
	  			d.findElement(Property.OrderAmountSelection1).click();*/
  	  		tec.AC40.Common.Wait.wait2Second();
				kb.sendKeys("0");
	  			kb.pressKey(Keys.ENTER);
	  			tec.AC40.Common.Wait.wait2Second();
  			}
  			else if(Config.browser.equals("FF"))
  			{
  				d.findElement(Property.OrderAmoutDecimal).click();
	  			tec.AC40.Common.Wait.wait2Second();
	  			d.findElement(Property.OrderAmountSelection1).click();
	  			tec.AC40.Common.Wait.wait2Second();
  			}
  		}
		      
  		if(OrderAmountvalue == 2)  			
  		{
  			//System.out.println("Presnet Decimal value is : "+Decimalvalue);
  			if(Config.browser.equals("GC"))
  			{
  				waitfl.until(ExpectedConditions.elementToBeClickable(Property.OrderAmoutDecimal));
  				
  				 waitfl.until(new Function<WebDriver, WebElement>() 
  	    				 {
  	    				   public WebElement apply(WebDriver driver) {
  	    				   return driver.findElement(Property.OrderAmoutDecimal);
  	    				 }
  	    				 });

  				
  				tec.AC40.Common.Wait.wait2Second();
  				d.findElement(Property.OrderAmoutDecimal).click();
  			/*	waitfl.until(ExpectedConditions.elementToBeClickable(Property.OrderAmountSelection2));
  				
  				 waitfl.until(new Function<WebDriver, WebElement>() 
  	    				 {
  	    				   public WebElement apply(WebDriver driver) {
  	    				   return driver.findElement(Property.OrderAmountSelection2);
  	    				 }
  	    				 });

  				
  				tec.AC40.Common.Wait.wait2Second();
  				d.findElement(Property.OrderAmountSelection2).click();*/
  				tec.AC40.Common.Wait.wait2Second();
  				kb.sendKeys("2");
  	  			kb.pressKey(Keys.ENTER);
  	  			tec.AC40.Common.Wait.wait2Second();
  			}
  			else if(Config.browser.equals("FF"))
  			{
  				d.findElement(Property.OrderAmoutDecimal).click();
	  			tec.AC40.Common.Wait.wait2Second();
	  			d.findElement(Property.OrderAmountSelection2).click();
	  			tec.AC40.Common.Wait.wait2Second();
  			}
  			
  		
  		}
  		else if (OrderAmountvalue == 3)
  		{
  			//System.out.println("Presnet Decimal value is : "+Decimalvalue);
  			if(Config.browser.equals("GC"))
  			{
  				waitfl.until(ExpectedConditions.elementToBeClickable(Property.OrderAmoutDecimal));
  				
  				 waitfl.until(new Function<WebDriver, WebElement>() 
  	    				 {
  	    				   public WebElement apply(WebDriver driver) {
  	    				   return driver.findElement(Property.OrderAmoutDecimal);
  	    				 }
  	    				 });

  				
  				tec.AC40.Common.Wait.wait2Second();
  				d.findElement(Property.OrderAmoutDecimal).click();
/*	  			waitfl.until(ExpectedConditions.elementToBeClickable(Property.OrderAmountSelection3));
	  			
	  			 waitfl.until(new Function<WebDriver, WebElement>() 
	    				 {
	    				   public WebElement apply(WebDriver driver) {
	    				   return driver.findElement(Property.OrderAmountSelection3);
	    				 }
	    				 });
	  			
	  			tec.AC40.Common.Wait.wait2Second();
	  			d.findElement(Property.OrderAmountSelection3).click();*/
  				tec.AC40.Common.Wait.wait2Second();
  				kb.sendKeys("3");
  	  			kb.pressKey(Keys.ENTER);
  	  			tec.AC40.Common.Wait.wait2Second();
  			}
  			else if(Config.browser.equals("FF"))
  			{
  				d.findElement(Property.OrderAmoutDecimal).click();
	  			tec.AC40.Common.Wait.wait2Second();
	  			d.findElement(Property.OrderAmountSelection3).click();
	  			tec.AC40.Common.Wait.wait2Second();
  			}
  		}
  		else if(OrderAmountvalue  == 4)
  		{
  			if(Config.browser.equals("GC"))
  			{
  				waitfl.until(ExpectedConditions.elementToBeClickable(Property.OrderAmoutDecimal));
  				tec.AC40.Common.Wait.wait2Second();
  				
  				 waitfl.until(new Function<WebDriver, WebElement>() 
  	    				 {
  	    				   public WebElement apply(WebDriver driver) {
  	    				   return driver.findElement(Property.OrderAmoutDecimal);
  	    				 }
  	    				 });

  				
  				d.findElement(Property.OrderAmoutDecimal).click();
	  			/*waitfl.until(ExpectedConditions.elementToBeClickable(Property.OrderAmountSelection4));
	  			
	  			 waitfl.until(new Function<WebDriver, WebElement>() 
	    				 {
	    				   public WebElement apply(WebDriver driver) {
	    				   return driver.findElement(Property.OrderAmountSelection4);
	    				 }
	    				 });

	  			
	  			tec.AC40.Common.Wait.wait2Second();
	  			d.findElement(Property.OrderAmountSelection4).click();*/
  				tec.AC40.Common.Wait.wait2Second();
  				kb.sendKeys("4");
  	  			kb.pressKey(Keys.ENTER);
  	  			tec.AC40.Common.Wait.wait2Second();
  			}
  			else if(Config.browser.equals("FF"))
  			{
  				d.findElement(Property.OrderAmoutDecimal).click();
	  			tec.AC40.Common.Wait.wait2Second();
	  			d.findElement(Property.OrderAmountSelection4).click();
	  			tec.AC40.Common.Wait.wait2Second();
  			}
  		}
  		
  		//int Tax1 = Double.valueOf(Tax).intValue();
  		
  		if(IsShippingTaxable.equalsIgnoreCase("Yes"))
  		{
  			d.findElement(Property.ShippingisTaxableYes).click();
  		}
  		else if(IsShippingTaxable.equalsIgnoreCase("No"))
  		{
  			d.findElement(Property.ShippingisTaxableNO).click();
  		}
  		
  		if(IsPostageTaxable.equalsIgnoreCase("Yes"))
  		{
  			d.findElement(Property.PostageTaxableYes).click();
  		}
  		else if(IsPostageTaxable.equalsIgnoreCase("No"))
  		{
  			d.findElement(Property.PostageTaxableNO).click();
  		}
  		
  		// Enable Zero amount order setting
  		
  		boolean EnableZeroAmountOrderStatus = d.findElement(Property.EnableZeroAmountOrder).isSelected();
  		boolean ShowBillingAddressToZeroAmountYESStatus = d.findElement(Property.ShowBillingAddressToZeroAmountYES).isSelected();
  		String[] ZeroAmountOrder = EnableZeroAmountOrder.split("_");
  		
  		if(EnableZeroAmountOrderStatus == true && ZeroAmountOrder[0].equals("NO"))
  		{
  			d.findElement(Property.EnableZeroAmountOrder).click();
  		}
  		else if(EnableZeroAmountOrderStatus == false && ZeroAmountOrder[0].equals("YES"))
  		{
  			d.findElement(Property.EnableZeroAmountOrder).click();
  		}
  		
  		if(ZeroAmountOrder[1].equalsIgnoreCase("YES"))
  		{
  			d.findElement(Property.ShowBillingAddressToZeroAmountYES).click();
  		}
  		
  		else
  		{
  			d.findElement(Property.ShowBillingAddressToZeroAmountNO).click();
  		}
  		
  		//************ External pricing related code *************
  		
  		String ExternalPricingDDLSelectedText = d.findElement(Property.ExternalpricingDDL).getText();
  		//System.out.println("ExternalPricingDDLSelectedText :"+ExternalPricingDDLSelectedText);
  		if(IsExternalPricingON.equals("YES") && ExternalPricingDDLSelectedText.contains("---Select---"))
  		{
  			d.findElement(Property.ExternalpricingDDL).click();
  			tec.AC40.Common.Wait.wait2Second();
  			waitfl.until(ExpectedConditions.elementToBeClickable(Property.ExternalpricingDDLExternalPrice));
  			
  			 waitfl.until(new Function<WebDriver, WebElement>() 
    				 {
    				   public WebElement apply(WebDriver driver) {
    				   return driver.findElement(Property.ExternalpricingDDLExternalPrice);
    				 }
    				 });
  			
  			tec.AC40.Common.Wait.wait2Second();
  			d.findElement(Property.ExternalpricingDDLExternalPrice).click();
  		}
  		else if(IsExternalPricingON.equals("NO") && ExternalPricingDDLSelectedText.contains("External Price API"))
  		{
  			d.findElement(Property.ExternalpricingDDL).click();
  			tec.AC40.Common.Wait.wait2Second();
  			waitfl.until(ExpectedConditions.elementToBeClickable(Property.EnternalpricingDDLSelect));
  			
  			 waitfl.until(new Function<WebDriver, WebElement>() 
    				 {
    				   public WebElement apply(WebDriver driver) {
    				   return driver.findElement(Property.EnternalpricingDDLSelect);
    				 }
    				 });

  			
  			tec.AC40.Common.Wait.wait2Second();
  			d.findElement(Property.EnternalpricingDDLSelect).click();
  		}
  		
  			d.findElement(Property.GeneralConfigSave).click();
  			tec.AC40.Common.Wait.wait2Second();
  			waitfl.until(ExpectedConditions.elementToBeClickable(Property.GeneralConfigSaveSuccessMsg));
  			
  			 waitfl.until(new Function<WebDriver, WebElement>() 
    				 {
    				   public WebElement apply(WebDriver driver) {
    				   return driver.findElement(Property.generalconfigshipping);
    				 }
    				 });
  			
  			tec.AC40.Common.Wait.wait2Second();
  		  	d.findElement(Property.generalconfigshipping).click();
  		  	waitfl.until(ExpectedConditions.elementToBeClickable(Property.Enableweightpackage));
  		  
  		  waitfl.until(new Function<WebDriver, WebElement>() 
 				 {
 				   public WebElement apply(WebDriver driver) {
 				   return driver.findElement(Property.Enableweightpackage);
 				 }
 				 });
  		  	
  		  	tec.AC40.Common.Wait.wait2Second();
  		  boolean isweightperpackage1 = d.findElement(Property.Enableweightpackage).isSelected();
  	  	  if(isweightperpackage1 == true && WeightPerPackage.equals("UnCheck"))
  	  	  {
  	  		 // System.out.println("check box already selected");
  	  		d.findElement(Property.Enableweightpackage).click();
  	  	  }
  	  	  else if(isweightperpackage1 == false && !WeightPerPackage.equals("UnCheck"))
  	  	  {
  	  		d.findElement(Property.Enableweightpackage).click();
  	  	  }	  	
  		  	
  		if(Weighttype.equalsIgnoreCase("KGS")|| Weighttype.equalsIgnoreCase("LBS"))
  		{
  	//enable weight per package in shipping
  	  boolean isweightperpackage = d.findElement(Property.Enableweightpackage).isSelected();
  	  if(isweightperpackage == true)
  	  {
  		 // System.out.println("check box already selected");
  	  }
  	  else
  	  {
  		d.findElement(Property.Enableweightpackage).click();
  	  }
  	  if(Weighttype.equals("LBS"))
  	  {
  		  
  		 waitfl.until(ExpectedConditions.elementToBeClickable(Property.upsdropdown));
  		 
  		 waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.upsdropdown);
				 }
				 });
  		  tec.AC40.Common.Wait.wait2Second();
  		  d.findElement(Property.upsdropdown).click();
  		 
  		  WebElement shippingmethodbox = d.findElement(Property.upsnextdayselectbox);
  		  waitfl.until(ExpectedConditions.elementToBeClickable(Property.upsnextdayselectbox));
  		  
  		 waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.upsnextdayselectbox);
				 }
				 });
  		  
  		  if(shippingmethodbox.isSelected())
  	 {
  		 //System.out.println("Shippng method alrdy selcted 11.");
  		 d.findElement(Property.shippingmethodselectbox).click();
  		 tec.AC40.Common.Wait.wait2Second();
  	 }
  	 else
  	 {
  		tec.AC40.Common.Wait.wait2Second();
  		d.findElement(Property.shippingmethodselectbox).click();
  		tec.AC40.Common.Wait.wait2Second();
  	 }
  	  }
  	  else if (Weighttype.equals("KGS")){
  		  	waitfl.until(ExpectedConditions.elementToBeClickable(Property.Uspsdropdown));
  	  		
  		  waitfl.until(new Function<WebDriver, WebElement>() 
 				 {
 				   public WebElement apply(WebDriver driver) {
 				   return driver.findElement(Property.Uspsdropdown);
 				 }
 				 });

  		  	d.findElement(Property.Uspsdropdown).click();
  	  		waitfl.until(ExpectedConditions.elementToBeClickable(Property.UspsPriorityselectbox));
  	  		WebElement shippingmethodbox = d.findElement(Property.UspsPriorityselectbox);
  	  	
  	  	 waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.Uspsshippingselctbox);
				 }
				 });
  	  		
  	  		if(shippingmethodbox.isSelected())
  	  	 {
  	  		// System.out.println("Shippng method alrdy selcted 11.");
  	  		 d.findElement(Property.Uspsshippingselctbox).click();
  	  		 tec.AC40.Common.Wait.wait2Second();
  	  	 }
  	  	 else{
  	  		tec.AC40.Common.Wait.wait2Second();
  	  		d.findElement(Property.Uspsshippingselctbox).click();
  	  		tec.AC40.Common.Wait.wait2Second();
  	  	 }
  	  }
  		}
  	   if(Weighttype.equals("--Select--"))
   	   {
   	      //d.findElement(Property.shippingmethodselectbox).click();
  		   waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Uspsdropdown));
  		   
  		 waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.Uspsdropdown);
				 }
				 });

  		   tec.AC40.Common.Wait.wait2Second();
  		   d.findElement(Property.Uspsdropdown).click();
  		   waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UspsPriorityselectbox));
  		   
  		   		waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.UspsPriorityselectbox);
				 }
				 });

  		   WebElement shippingmethodbox1 = d.findElement(Property.UspsPriorityselectbox);
  		   tec.AC40.Common.Wait.wait2Second();
   		if(shippingmethodbox1.isSelected())
   	  	 {
   			//shippingmethodbox.click(); 
   			//System.out.println("no need of 3rd party shipping services  so we de-Uspsshippingselctboxselected 33");
   			d.findElement(Property.Uspsshippingselctbox).click();
   			
   			waitfl.until(ExpectedConditions.elementToBeClickable(Property.Uspsshippingselctbox));
   			
   			waitfl.until(new Function<WebDriver, WebElement>() 
   				 {
   				   public WebElement apply(WebDriver driver) {
   				   return driver.findElement(Property.Uspsshippingselctbox);
   				 }
   				 });

   			tec.AC40.Common.Wait.wait2Second();
   			d.findElement(Property.Uspsshippingselctbox).click();
   			tec.AC40.Common.Wait.wait2Second();
   	  	 }
   		else{
   			//System.out.println("3rd party services already unselected 44");
   			tec.AC40.Common.Wait.wait2Second();
   		}
    	    //d.findElement(Property.shippingmethodselectbox).click();
    		d.findElement(Property.upsdropdown).click();
    		waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.upsdropdown));
    		
    		waitfl.until(new Function<WebDriver, WebElement>() 
   				 {
   				   public WebElement apply(WebDriver driver) {
   				   return driver.findElement(Property.upsdropdown);
   				 }
   				 });

    		
    		WebElement shippingmethodbox = d.findElement(Property.upsnextdayselectbox);
    		waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.upsnextdayselectbox));
    		
    		waitfl.until(new Function<WebDriver, WebElement>() 
   				 {
   				   public WebElement apply(WebDriver driver) {
   				   return driver.findElement(Property.upsnextdayselectbox);
   				 }
   				 });

    		
    		tec.AC40.Common.Wait.wait2Second();
    		if(shippingmethodbox.isSelected())
    	  	 {
    			//shippingmethodbox.click(); 
    			//System.out.println("no need of 3rd party shipping services  so we deselected 33");
    			d.findElement(Property.shippingmethodselectbox).click();
    			tec.AC40.Common.Wait.wait2Second();
    			d.findElement(Property.shippingmethodselectbox).click();
    			tec.AC40.Common.Wait.wait2Second();
    	  	 }
    		else{
    			//System.out.println("3rd party services aiready unselected 44");
    			tec.AC40.Common.Wait.wait2Second();
    		}
  		}
  		 	WebElement isshippingorhandlingfee = d.findElement(Property.ordershippinghandlingfee);
  		 	//Thread.sleep(5000);
  		 	waitfl.until(ExpectedConditions.elementToBeClickable(Property.ordershippinghandlingfee));
  		 	
  		 			waitfl.until(new Function<WebDriver, WebElement>() 
  					 {
  					   public WebElement apply(WebDriver driver) {
  					   return driver.findElement(Property.ordershippinghandlingfee);
  					 }
  					 });
  		 	
  		 	//waitfl.until(ExpectedConditions.elementToBeClickable((Property.ordershippinghandlingfee)));
   		 if(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||userordershippingorhandlingfee.equals("0.0000"))
    	  {
   			// System.out.println("admin entered");
   			 if(isshippingorhandlingfee.isSelected())
     	      {
     		     isshippingorhandlingfee.click();
     		   // System.out.println("shipping handling fee check box deselected");
     		   }
   			 else
   			 {
   				 //System.out.println("no need of handiling fee");
   			 }
       	  }
    	  else
    	  {
    		//  System.out.println("admin dont enetr");
    		// WebElement isshippingorhandlingfee = d.findElement(Property.ordershippinghandlingfee);
    		 //Thread.sleep(5000);
    		 waitfl.until(ExpectedConditions.elementToBeClickable((Property.ordershippinghandlingfee)));
      	     
    		 waitfl.until(new Function<WebDriver, WebElement>() 
    				 {
    				   public WebElement apply(WebDriver driver) {
    				   return driver.findElement(Property.ordershippinghandlingfee);
    				 }
    				 });

    		 if(isshippingorhandlingfee.isSelected())
      	      {
      		  // System.out.println("shipping handling fee check box already selected");
      	      }
      	     else
      	      {
      		   isshippingorhandlingfee.click();
      	      }
      	    waitfl.until(ExpectedConditions.elementToBeClickable(Property.feeentertextbox));
      	   
      	  waitfl.until(new Function<WebDriver, WebElement>() 
 				 {
 				   public WebElement apply(WebDriver driver) {
 				   return driver.findElement(Property.feeentertextbox);
 				 }
 				 });
      	    
      	    d.findElement(Property.feeentertextbox).click();
           	kb.sendKeys(Keys.chord(Keys.CONTROL, "a"));
      	    Thread.sleep(200);
		    kb.pressKey(Keys.DELETE);
		    Thread.sleep(200);
		    d.findElement(Property.generalconfigshipping).click();
		    d.findElement(Property.feeentertextbox).click();
		  //  System.out.println("enteredd");
		    kb.sendKeys(userordershippingorhandlingfee);
		   }
   		 if(OrderBase.equals("Item"))
   		 {
   			 OrderBaseShippingSetting(OrderBase, TestStep,WeightPerPackage);
   		 }
   		 else if(OrderBase.equals("Split Ship"))
   		 {
   			if(OrderType.equals("Select List")){
   	   			OrderBaseShippingSetting(OrderBase, TestStep, WeightPerPackage);	
   			}else{
   	   			OrderBaseShippingSetting(OrderBase, TestStep, WeightPerPackage);	
   			}
   		 }else
   		 {
   			OrderBaseShippingSetting(OrderBase, TestStep, WeightPerPackage);
   		 }
   		 waitfl.until(ExpectedConditions.elementToBeClickable(Property.weightshippingsave));
   		 
   		waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.weightshippingsave);
				 }
				 });

   		 
   		 d.findElement(Property.weightshippingsave).click();   
   		 tec.AC40.Common.Wait.wait10Second();
   	
   	     // Payment Settings
         tec.AC40.Common.Wait.wait5Second();
	     waitfl.until(ExpectedConditions.elementToBeClickable(Property.PaymentSettingsTab));
   	     waitfl.until(new Function<WebDriver, WebElement>() 
		 {
		   public WebElement apply(WebDriver driver) {
		   return driver.findElement(Property.PaymentSettingsTab);
		 }
		 });
   	    tec.AC40.Common.Wait.wait5Second();
   	     d.findElement(Property.PaymentSettingsTab).click();
   	     System.out.println("** Payment button worked fine **");
	     tec.AC40.Common.Wait.wait2Second();
	     waitfl.until(ExpectedConditions.elementToBeClickable(Property.CCAutoNet));
   	     
	     waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.CCAutoNet);
				 }
				 });

	     
	     if(!PaymentType.contains(","))
   	     {
   	    	paymentTypeSelection(PaymentType, PaymentSubOpt);
   	     }
   	     else
   	     {
   	    	 String[] MultiPayments = PaymentType.split(",");
   	    	 String[] MultiSubOpts = PaymentSubOpt.split(",");
   	    	 int MultiPaymentLen = MultiPayments.length;
   	    	 System.out.println("MultiPaymentLen :"+MultiPaymentLen);
   	    	 for(int i =0 ; i<MultiPaymentLen; i++)
   	    	 {
   	    		// System.out.println("MultiPayments :"+MultiPayments[i]);
   	    		// System.out.println("MultiSubOpts :"+MultiSubOpts[i]);
   	    		paymentTypeSelection(MultiPayments[i], MultiSubOpts[i]);
   	    	 }
   	     }
   	     //Get cost center check box status
	     boolean CostcenterStatus = d.findElement(Property.CostCenterStatus).isSelected();
	     // Update the cost center value based on data sheet value (Excel file)
	     if(CostCenter.equals("YES") && CostcenterStatus ==  false)
	     {
	    	 d.findElement(Property.CostCenterStatus).click();
	    	 Thread.sleep(500);
	    	 d.findElement(Property.DisplayAllCostCenterYes).click();
	     }
	     else if(CostCenter.equals("NO") && CostcenterStatus == true)
	     {
	    	 d.findElement(Property.CostCenterStatus).click();
	     }
	     
	     // Shipping Address same as Billing Address
	     Thread.sleep(500);
	     if(ShipAddSameAsBillAdd.equals("YES"))
	     {
	    	 //ShipAddSameAsBillAdd
	    	 d.findElement(Property.ShipAddSameAsBillAddYes).click();
	     }
	     else 
	     {
	    	 d.findElement(Property.ShipAddSameAsBillAddNO).click();
	     }
	     Thread.sleep(5000);
	     d.findElement(Property.PaymentSettingsSave).click();
	     Thread.sleep(5000);
	     waitfl.until(ExpectedConditions.elementToBeClickable(Property.PaymentSaveSuccessMsg));
	     waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.PaymentSaveSuccessMsg);
				 }
				 });

	     
	     tec.AC40.Common.Wait.wait2Second();
   	     
   	     // List settings
   	     d.findElement(Property.ListSettingsTab).click();
   	     //tec.AC40.Common.Wait.wait5Second();
   	     waitfl.until(ExpectedConditions.elementToBeClickable(Property.RestrictedFieldsFormatCheckBox));
   	    
   	  waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.RestrictedFieldsFormatCheckBox);
				 }
				 });
   	     
   	     tec.AC40.Common.Wait.wait2Second();
   	     boolean RestrictedfieldsFormatCheckBoxStatus = d.findElement(Property.RestrictedFieldsFormatCheckBox).isSelected();
   	     // The below code helps to uncheck the restricted fields check box and save the list settings
   	     if(RestrictedfieldsFormatCheckBoxStatus == true)
   	     {
   	    	d.findElement(Property.RestrictedFieldsFormatCheckBox).click();
   	    	d.findElement(Property.ListSettingsSaveButton).click();
   	    	tec.AC40.Common.Wait.wait2Second();
   	    	waitfl.until(ExpectedConditions.elementToBeClickable(Property.AdminHomeLink));
   	    	
   	    	waitfl.until(new Function<WebDriver, WebElement>() 
   				 {
   				   public WebElement apply(WebDriver driver) {
   				   return driver.findElement(Property.AdminHomeLink);
   				 }
   				 });
   	    	
   	     }
   	  Wait.Fluentwait(Property.AdminHomeLink);
  		 d.findElement(Property.AdminHomeLink).click();
  		 tec.AC40.Common.Wait.wait2Second();
      }catch (Exception e){
    	  ErrorNumber = ErrorNumber +1;
    	  captureScreenshot();
    	  e.printStackTrace();
      }
	}
	
	public static void paymentTypeSelection(String PaymentType, String PaymentSubOpt) throws InterruptedException, AWTException
	{
		MouseAdjFunction();
  	     String[] PaymentSubOptions = PaymentSubOpt.split("_");
	     if(PaymentType.equals("Billing"))
	     {
	    	 boolean BillingCheckBoxStatus = d.findElement(Property.BillingCheckBox).isSelected();
	    	 if(BillingCheckBoxStatus == false)
	    	 {
	    		 d.findElement(Property.BillingCheckBox).click();
	    		 tec.AC40.Common.Wait.wait2Second();
	    		 d.findElement(Property.BillingSubOption).click();
	    		 tec.AC40.Common.Wait.wait2Second();
	    	 }
	     }
	     else if(PaymentType.equals("Co-Op Fund") || PaymentType.equals("Money on Account"))
	     {
	    	 boolean CoopfundCheckBoxStatus = d.findElement(Property.CoOpFund).isSelected();
	    	 if(CoopfundCheckBoxStatus ==  false)
	    	 {
	    		 d.findElement(Property.CoOpFund).click();
	    		 tec.AC40.Common.Wait.wait2Second();
	    	 }
	    	 
	    	 // Sub option selection
	    	 boolean CoopFundSubtionStatus = d.findElement(Property.CoopFundSubOption).isSelected();
	    	 boolean CoopFundMoneyOnAcSQLStatus = d.findElement(Property.MoneyOnAccountSQL).isSelected();
	    	 if(PaymentType.equals("Co-Op Fund") && CoopFundSubtionStatus == false)
	    	 {
	    		 d.findElement(Property.CoopFundSubOption).click();
	    		 tec.AC40.Common.Wait.wait2Second();
	    	 }
	    	 // Money on Account SQL option select/ De-select condition
	    	 else if(PaymentType.equals("Money on Account") && CoopFundMoneyOnAcSQLStatus == false)
	    	 {
	    		 d.findElement(Property.MoneyOnAccountSQL).click();
	    	 }
	    	 
	    	 //Write else part While using "Money on Account SQL"
	    	 
	    	 // Second Level sub option selection
	    	 if(PaymentSubOptions[0].equals("ExpiryDate"))
	    	 {
	    		 d.findElement(Property.CFExpiryDate).click();
	    		 tec.AC40.Common.Wait.wait2Second();
	    	 }
	    	 else if(PaymentSubOptions[0].equals("FIFO"))
	    	 {
	    		 d.findElement(Property.CFFIFO).click();
	    		 tec.AC40.Common.Wait.wait2Second();
	    	 }
	     }
	     else if(PaymentType.equals("Credit Card"))
	     {
	    	 boolean CreditCardStatus = d.findElement(Property.CreditCardStatus).isSelected();
	    	 if(CreditCardStatus ==  false)
	    	 {
	    		 d.findElement(Property.CreditCardStatus).click();
	    		 tec.AC40.Common.Wait.wait2Second();
	    	 }
	    	 
	    	 // Sub option selection
	    	 if(PaymentSubOptions[0].equals("AuthNet"))
	    	 {
	    		 d.findElement(Property.CCAutoNet).click();
	    		 tec.AC40.Common.Wait.wait2Second();
	    	 }
	    	 else if(PaymentSubOptions[0].equals("CybSou"))
	    	 {
	    		 d.findElement(Property.CCCybSou).click();
	    		 tec.AC40.Common.Wait.wait2Second();
	    	 }
	    	 else if(PaymentSubOptions[0].equals("XiPay"))
	    	 {
	    		 d.findElement(Property.CCXipay).click();
	    		 tec.AC40.Common.Wait.wait2Second();
	    	 }
	    	 else if(PaymentSubOptions[0].equals("PayPal"))
	    	 {
	    		 d.findElement(Property.CCPayPal).click();
	    		 tec.AC40.Common.Wait.wait2Second();
	    	 }
	    	 
	    	 // Second Level sub option selection
	    	 if(PaymentSubOptions[1].equals("Aonly"))
	    	 {
	    		 d.findElement(Property.CCAonly).click();
	    		 tec.AC40.Common.Wait.wait2Second();
	    	 }
	    	 else if(PaymentSubOptions[1].equals("Acap"))
	    	 {
	    		 d.findElement(Property.CCACap).click();
	    		 tec.AC40.Common.Wait.wait2Second();
	    	 }
	    	 else if(PaymentSubOptions[1].equals("AChaLat"))
	    	 {
	    		 d.findElement(Property.CCAChaLat).click();
	    		 tec.AC40.Common.Wait.wait2Second();
	    	 }
	    	 
	     }
	     else if(PaymentType.equals("Gift Card"))
	     {
	    	boolean GiftCardStatus = d.findElement(Property.GiftcardStatus).isSelected();
	    	if(GiftCardStatus == false)
	    	{
	    		d.findElement(Property.GiftcardStatus).click();
	    		tec.AC40.Common.Wait.wait2Second();
	    		d.findElement(Property.GiftcardSQLRadioButton).click();
	    		tec.AC40.Common.Wait.wait2Second();
	    	}
	     }
	     else if(PaymentType.equals("PayPal Credit") || PaymentType.equals("PayPal External Checkout") ||PaymentType.equals("PayUbiz External Checkout"))
	     {
	    	 boolean DirectORExpressStatus = d.findElement(Property.DirectORExpressStatus).isSelected();
	    	 if(DirectORExpressStatus  ==  false)
	    	 {
	    		 d.findElement(Property.DirectORExpressStatus).click(); 
	    		 tec.AC40.Common.Wait.wait2Second();
	    	 }
	    	 boolean PayPalCreditStatus = d.findElement(Property.PayPalCreditStatus).isSelected();
	    	 boolean PayPalExterCheckOut = d.findElement(Property.PayPalExternalCheckOut).isSelected();
	    	 boolean PayubizExtrenalCheckOut = d.findElement(Property.PayUbizExtrenalCheckOut).isSelected();
	    	 if(PaymentType.equals("PayPal Credit") && PayPalCreditStatus == false)
	    	 {
	    		 d.findElement(Property.PayPalCreditStatus).click();
	    	 }
	    	 if(PaymentType.equals("PayPal External Checkout") && PayPalExterCheckOut == false)
	    	 {
	    		 d.findElement(Property.PayPalExternalCheckOut).click();
	    	 }
	    	 if(PaymentType.equals("PayUbiz External Checkout")&& PayubizExtrenalCheckOut == false)
	    	 {
	    		 d.findElement(Property.PayUbizExtrenalCheckOut).click();
	    	 } 
	     }
	}
	public static void payubizpayment(String Total) throws IOException, InterruptedException{
		try{		
		 FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
		 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
		 waitfl.pollingEvery(1, TimeUnit.SECONDS);
		 waitfl.ignoring(NoSuchElementException.class);											
		 waitfl.ignoring(StaleElementReferenceException.class);
		 
		 String PageName = "PayUBizpaymentpage";
		 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Payubizamountlabel));
		 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Payubizamount)); 
		 String actualpayubizamount = d.findElement(Property.Payubizamount).getText();
		 System.out.println("amount in payubiz page is" + actualpayubizamount);
		 String actualpayubizamount1 = Decimalsetting(actualpayubizamount, "0.0");
		 //String actualpayubizamount1 = new BigDecimal(actualpayubizamount).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
		 String Expectedpayubiztotal = Total;
		 //String Expectedpayubiztotal1 = Expectedpayubiztotal.substring(1, Expectedpayubiztotal.length());
		 System.out.println("paybiz total in sheet is" + Expectedpayubiztotal);
		 String Expectedpayubiztotal2 = Decimalsetting(Expectedpayubiztotal, "0.0");
		 //String Expectedpayubiztotal2 = new BigDecimal(Expectedpayubiztotal).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
		 System.out.println("after roundoff total is"+Expectedpayubiztotal2);
		 if(actualpayubizamount1.equals(Expectedpayubiztotal2))
		 {
			 System.out.println("both applciation and paybiz totals are same, so valyes rouneded in apyubiz.");
		 }
		 else{
			 ErrorNumber = ErrorNumber+1;
			 captureScreenshot();
			 String PriceType = "Total";
			 System.out.println("<------------ PayUBiz Extrenal checkout rounded total values are different -------->"+ErrorNumber);
			 System.out.println("ActualOCTotal : "+actualpayubizamount1);
			 System.out.println("ExpectedTotal : "+Expectedpayubiztotal2);
			 RW_File.WriteResult(actualpayubizamount1, Expectedpayubiztotal2,  PageName, PriceType);		 
		 }
		 waitfl.until(ExpectedConditions.elementToBeClickable(Property.payubizcardnumber));
  		 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.payubizcardnumber));
  		 d.findElement(Property.payubizcardnumber).clear();
  		 d.findElement(Property.payubizcardnumber).sendKeys("5123");
  		 d.findElement(Property.payubizcardnumber).sendKeys("4567");
  		 d.findElement(Property.payubizcardnumber).sendKeys("8901");
  		 d.findElement(Property.payubizcardnumber).sendKeys("2346");
  		 waitfl.until(ExpectedConditions.elementToBeClickable(Property.payubiznameoncard));
  		 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.payubiznameoncard));
  		 d.findElement(Property.payubiznameoncard).clear();
  		 d.findElement(Property.payubiznameoncard).sendKeys("tecrasystems");
  		 waitfl.until(ExpectedConditions.elementToBeClickable(Property.payubizcvv));
 		 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.payubizcvv));
 		 d.findElement(Property.payubizcvv).clear();
 		 d.findElement(Property.payubizcvv).sendKeys("123");
 		 waitfl.until(ExpectedConditions.elementToBeClickable(Property.payUbizexpirymonthdd));
 		 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.payUbizexpirymonthdd));
 		 d.findElement(Property.payUbizexpirymonthdd).click();
 		 Select payubizmonth = new Select(d.findElement(Property.payUbizexpirymonthdd));
 		 payubizmonth.selectByValue("05");
 		 waitfl.until(ExpectedConditions.elementToBeClickable(Property.payUbizexpiryyeardd));
  		 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.payUbizexpiryyeardd));
  		 d.findElement(Property.payUbizexpiryyeardd).click();
 		 Select payubizyer = new Select(d.findElement(Property.payUbizexpiryyeardd));
 		 payubizyer.selectByValue("2017");
 		 waitfl.until(ExpectedConditions.elementToBeClickable(Property.payubizpaynow));
 		 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.payubizpaynow));
 		 d.findElement(Property.payubizpaynow).click();
 		 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PrintLink));
 		 System.out.println("Sucessfully navigated to application page");
	   }
		
	   catch (Exception e){ 
  	       ErrorNumber = ErrorNumber+1;
  	       captureScreenshot();
  	       e.printStackTrace();
    }
}
	public static void suboptions(String suboption) throws InterruptedException, AWTException {
		MouseAdjFunction();
		    tec.AC40.Common.Wait.wait2Second();	
			if(suboption.equals("AOnly"))  // Authorize only
			{
				d.findElement(Property.CCAonly).click();
			}
			else if(suboption.equals("ACap")) //Authorize and Capture
			{
				d.findElement(Property.CCACap).click();
			}
			else if(suboption.equals("AChaLat")) // Authorize and Charge Later
			{
				d.findElement(Property.CCAChaLat).click();
			}
	}
	
	public static void ItemPerPrice(String ItemPerPrice, String FlatRate,String Weighttype,String Weightdecimaltext)
			throws InterruptedException {
		try{
			// WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
			MouseAdjFunction();
			 FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
			 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
			 waitfl.pollingEvery(1, TimeUnit.SECONDS);
			 waitfl.ignoring(NoSuchElementException.class);											
			 waitfl.ignoring(StaleElementReferenceException.class);
			 
			if(Config.LayoutType.equals("Classic"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyLink);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.CompanyLink).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PricingIcon));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PricingIcon));
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.PricingIcon);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.PricingIcon).click();
			}
			else if(Config.LayoutType.equals("Layout1"))
			{
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.CompanyIconL1));
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL1);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.CompanyIconL1).click();
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.PricingIconL1));
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.PricingIconL1);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.PricingIconL1).click();
				
			}
			else if(Config.LayoutType.equals("Layout2"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.CompanyIconL2).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PricingIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PricingIconL2));
		  		
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.PricingIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.PricingIconL2).click();
			}
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.PricingSearchBox));
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PricingSearchBox);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PricingSearchBox).sendKeys(Config.ProductPriceCode);
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.priceDetailsLink).click();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PriceEditLink));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PriceEditLink));
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PriceEditLink);
					 }
					 });
			
			tec.AC40.Common.Wait.wait5Second();
		
			waitfl.until(new Function<WebDriver, WebElement>() 
			 {
			   public WebElement apply(WebDriver driver) {
			   return driver.findElement(Property.PriceEditLink);
			 
			 }
			 });
			Thread.sleep(3500);
			d.findElement(Property.PriceEditLink).click();
			  System.out.println("Edit link is working fine  ");;
			tec.AC40.Common.Wait.wait2Second();
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.PriceEntertextBox));
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PriceEntertextBox);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
			for(int i=1; i<= 1; i++)
			{
				d.findElement(Property.PriceEntertextBox).click();
				Thread.sleep(1000);
				kb.sendKeys(Keys.chord(Keys.CONTROL, "a"));
				Thread.sleep(1000);
				kb.pressKey(Keys.DELETE);
				Thread.sleep(500);
				d.findElement(Property.PriceMinimumQuantity).click();
				
			}
			tec.AC40.Common.Wait.wait2Second();
			double ItemPerPrice1 =  Double.valueOf(ItemPerPrice).doubleValue();
			//System.out.println("ItemPerPrice1 : "+ItemPerPrice1);
			if(ItemPerPrice1 == 0.0)
			{
				//d.findElement(Property.PriceEntertextBox).sendKeys(FlatRate);
				d.findElement(Property.PriceEntertextBox).click();
				Thread.sleep(500);
				//System.out.println("FlatRate : "+FlatRate);
				kb.sendKeys(FlatRate);
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.PriceTypeDropDown).click();
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.PriceTypeValueSelectionFlat));
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.PriceTypeValueSelectionFlat);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.PriceTypeValueSelectionFlat).click();
				tec.AC40.Common.Wait.wait2Second();
			}
			else
			{
			//d.findElement(Property.PriceEntertextBox).sendKeys(ItemPerPrice);
			d.findElement(Property.PriceEntertextBox).click();
			Thread.sleep(500);
			kb.sendKeys(ItemPerPrice);
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PriceTypeDropDown).click();
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.PriceTypeValueSelection));
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PriceTypeValueSelection);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PriceTypeValueSelection).click();
			tec.AC40.Common.Wait.wait2Second();
			
			}
			d.findElement(Property.Weightentertextbox).click();
			Thread.sleep(500);
			kb.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			Thread.sleep(2000);
			kb.pressKey(Keys.DELETE);
			Thread.sleep(500);
			d.findElement(Property.PriceMinimumQuantity).click();
			d.findElement(Property.Weightentertextbox).click();
			Thread.sleep(500);
			if(Weightdecimaltext.equals("0.00")|| Weightdecimaltext.equals("0.000")||Weightdecimaltext.equals("0.0000")||Weighttype.equalsIgnoreCase("--Select--"))
			{
				d.findElement(Property.shippingtypedropdown).click();
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.shippingtypedropdown));
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.selectshippingdropdoen));
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.selectshippingdropdoen);
						 }
						 });
				
				d.findElement(Property.selectshippingdropdoen).click();
				
			}
			else{ 
			kb.sendKeys(Weightdecimaltext);
			String s=d.findElement(Property.shippingtypeddselection1).getText();
			//System.out.println("selected type is"+ s);
			d.findElement(Property.shippingtypedropdown).click();
			Thread.sleep(500);
			kb.sendKeys(Weighttype);
			if(Weighttype.equals(s))
			{
				kb.pressKey(Keys.ENTER);
				Thread.sleep(500);
			}
			else
			{
				kb.pressKey(Keys.UP);
				kb.pressKey(Keys.DOWN);
				kb.pressKey(Keys.ENTER);
				Thread.sleep(500);
			}
			}
			d.findElement(Property.PriceDetailsSaveButton).click();
			tec.AC40.Common.Wait.wait2Second();
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.priceSaveSuccessMsg));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.priceSaveSuccessMsg));
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.priceSaveSuccessMsg);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			Wait.Fluentwait(Property.AdminHomeLink);
			 d.findElement(Property.AdminHomeLink).click();
				if(Config.LayoutType.equals("Classic"))
				{
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.CompanyLink);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
				}
				else if(Config.LayoutType.equals("Layout1"))
				{
					waitfl.until(ExpectedConditions.elementToBeClickable(Property.CompanyIconL1));
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.CompanyIconL1);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
				}
				else if(Config.LayoutType.equals("Layout2"))
				{
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.CompanyIconL2);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
				}
				
		}catch (Exception e){
			ErrorNumber = ErrorNumber +1;
			captureScreenshot();
	    	  e.printStackTrace();
	      }
	}
	public static void LandingPageItemPerPrice(String ItemPerPrice, String FlatRate,String Weighttype,String Weightdecimaltext,
			String ProdutType,String PresetLPSettings,String SiteNameType)
			throws InterruptedException {
		try{
			// WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
			MouseAdjFunction();
			 FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
			 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
			 waitfl.pollingEvery(1, TimeUnit.SECONDS);
			 waitfl.ignoring(NoSuchElementException.class);											
			 waitfl.ignoring(StaleElementReferenceException.class);
			 
			if(Config.LayoutType.equals("Classic"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyLink);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.CompanyLink).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PricingIcon));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PricingIcon));
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.PricingIcon);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.PricingIcon).click();
			}
			else if(Config.LayoutType.equals("Layout1"))
			{
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.CompanyIconL1));
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL1);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.CompanyIconL1).click();
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.PricingIconL1));
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.PricingIconL1);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.PricingIconL1).click();
				
			}
			else if(Config.LayoutType.equals("Layout2"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.CompanyIconL2).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PricingIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PricingIconL2));
		  		
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.PricingIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.PricingIconL2).click();
			}
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.PricingSearchBox));
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PricingSearchBox);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PricingSearchBox).sendKeys(Config.LandingPriceCode);
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.priceDetailsLink).click();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PriceEditLink));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PriceEditLink));
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PriceEditLink);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PriceEditLink).click();
			tec.AC40.Common.Wait.wait2Second();
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.PriceEntertextBox));
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PriceEntertextBox);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
			for(int i=1; i<= 1; i++)
			{
				d.findElement(Property.PriceEntertextBox).click();
				Thread.sleep(1000);
				kb.sendKeys(Keys.chord(Keys.CONTROL, "a"));
				Thread.sleep(1000);
				kb.pressKey(Keys.DELETE);
				Thread.sleep(500);
				d.findElement(Property.PriceMinimumQuantity).click();
				
			}
			tec.AC40.Common.Wait.wait2Second();
			double ItemPerPrice1 =  Double.valueOf(ItemPerPrice).doubleValue();
			//System.out.println("ItemPerPrice1 : "+ItemPerPrice1);
			 if(ItemPerPrice1 == 0.0)
			   {
			    //d.findElement(Property.PriceEntertextBox).sendKeys(FlatRate);
			    d.findElement(Property.PriceEntertextBox).click();
			    Thread.sleep(500);
			    //System.out.println("FlatRate : "+FlatRate);
			    kb.sendKeys(FlatRate);
			    tec.AC40.Common.Wait.wait2Second();
			    d.findElement(Property.PriceTypeDropDown).click();
			    /*waitfl.until(ExpectedConditions.elementToBeClickable(Property.LandingFlatRate));
			    
			    waitfl.until(new Function<WebDriver, WebElement>() 
			       {
			         public WebElement apply(WebDriver driver) {
			         return driver.findElement(Property.LandingFlatRate);
			       }
			       });*/
			    
			    tec.AC40.Common.Wait.wait2Second();
			   
			    kb.pressKey(Keys.UP);
			    kb.pressKey(Keys.UP);
			    kb.pressKey(Keys.UP);
			    kb.pressKey(Keys.DOWN);
			    //kb.pressKey(Keys.ENTER); 
			       tec.AC40.Common.Wait.wait2Second();
			    /*d.findElement(Property.LandingFlatRate).click();
			    tec.AC40.Common.Wait.wait2Second();*/
			   }
			   else
			   {
			   //d.findElement(Property.PriceEntertextBox).sendKeys(ItemPerPrice);
			   d.findElement(Property.PriceEntertextBox).click();
			   Thread.sleep(500);
			   kb.sendKeys(ItemPerPrice);
			   tec.AC40.Common.Wait.wait2Second();
			   d.findElement(Property.PriceTypeDropDown).click();
			   
			   tec.AC40.Common.Wait.wait2Second();
			   kb.pressKey(Keys.DOWN);
			   //kb.pressKey(Keys.ENTER);
			      tec.AC40.Common.Wait.wait2Second();
			   /*waitfl.until(ExpectedConditions.elementToBeClickable(Property.LandingPerpice));
			   
			   waitfl.until(new Function<WebDriver, WebElement>() 
			      {
			        public WebElement apply(WebDriver driver) {
			        return driver.findElement(Property.LandingPerpice);
			      }
			      }); 
			   
			   tec.AC40.Common.Wait.wait2Second();
			   d.findElement(Property.LandingPerpice).click();
			   tec.AC40.Common.Wait.wait2Second();*/
			   
			   }
			   d.findElement(Property.Weightentertextbox).click();
			   Thread.sleep(500);
			   kb.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			   Thread.sleep(2000);
			   kb.pressKey(Keys.DELETE);
			   Thread.sleep(500);
			   d.findElement(Property.PriceMinimumQuantity).click();
			   d.findElement(Property.Weightentertextbox).click();
			   Thread.sleep(500);
			   //kb.sendKeys("0");
			   //tec.AC40.Common.Wait.wait2Second();
			   
			   if(Weightdecimaltext.equals("0.00")|| Weightdecimaltext.equals("0.000")||Weightdecimaltext.equals("0.0000")||Weighttype.equalsIgnoreCase("--Select--"))
			   {
			    d.findElement(Property.shippingtypedropdown).click();
			    /*waitfl.until(ExpectedConditions.elementToBeClickable(Property.shippingtypedropdown));
			    waitfl.until(ExpectedConditions.elementToBeClickable(Property.selectshippingdropdoen));
			    
			    waitfl.until(new Function<WebDriver, WebElement>() 
			       {
			         public WebElement apply(WebDriver driver) {
			         return driver.findElement(Property.selectshippingdropdoen);
			       }
			       });*/
			    
			    //d.findElement(Property.selectshippingdropdoen).click();
			    
			   }
			   else{ 
			   kb.sendKeys(Weightdecimaltext);
			   String s=d.findElement(Property.shippingtypeddselection1).getText();
			   //System.out.println("selected type is"+ s);
			   d.findElement(Property.shippingtypedropdown).click();
			   Thread.sleep(500);
			   kb.sendKeys(Weighttype);
			   if(Weighttype.equals(s))
			   {
			    kb.pressKey(Keys.ENTER);
			    Thread.sleep(500);
			   }
			   else
			   {
			    kb.pressKey(Keys.UP);
			    kb.pressKey(Keys.DOWN);
			    kb.pressKey(Keys.ENTER);
			    Thread.sleep(500);
			   }
			   }
			d.findElement(Property.PriceDetailsSaveButton).click();
			tec.AC40.Common.Wait.wait2Second();
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.priceSaveSuccessMsg));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.priceSaveSuccessMsg));
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.priceSaveSuccessMsg);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			Wait.Fluentwait(Property.AdminHomeLink);
			 d.findElement(Property.AdminHomeLink).click();
				if(Config.LayoutType.equals("Classic"))
				{
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.CompanyLink);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
				}
				else if(Config.LayoutType.equals("Layout1"))
				{
					waitfl.until(ExpectedConditions.elementToBeClickable(Property.CompanyIconL1));
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.CompanyIconL1);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
				}
				else if(Config.LayoutType.equals("Layout2"))
				{
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.CompanyIconL2);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
				}
				 
				if(Config.LayoutType.equals("Classic"))
				{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsLink));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsLink));
			   // d.findElement(Property.ProductsLink).isDisplayed();
			    
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductsLink);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.ProductsLink).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ManageProductsIcon));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ManageProductsIcon));
				//d.findElement(Property.ManageProductsIcon).isDisplayed();
		  		
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ManageProductsIcon);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.ManageProductsIcon).click();
				}
				else if(Config.LayoutType.equals("Layout1"))
				{
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsIconL1));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsIconL1));
					//d.findElement(Property.ProductsIconL1).isDisplayed();
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.ProductsIconL1);
							 }
							 });

					tec.AC40.Common.Wait.wait2Second();	
					d.findElement(Property.ProductsIconL1).click();
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ManageProductsIconL1));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ManageProductsIconL1));
					//d.findElement(Property.ManageProductsIconL1).isDisplayed();
			  		
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.ManageProductsIconL1);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
					d.findElement(Property.ManageProductsIconL1).click();
				}
				else if(Config.LayoutType.equals("Layout2"))
				{
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsIconL2));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsIconL2));
					//d.findElement(Property.ProductsIconL2).isDisplayed();
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.ProductsIconL2);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
					d.findElement(Property.ProductsIconL2).click();
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ManageProductsIconL2));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ManageProductsIconL2));
					//d.findElement(Property.ManageProductsIconL2).isDisplayed();
			  		
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.ManageProductsIconL2);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
					d.findElement(Property.ManageProductsIconL2).click();
				}
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsSearchBox));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsSearchBox));
				//d.findElement(Property.ProductsSearchBox).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductsSearchBox);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.ProductsSearchBox).sendKeys(ProdutType);
				tec.AC40.Common.Wait.wait5Second();
				if (ProdutType.equals("GURL")){
				d.findElement(Property.EditGurl).click();
				}
				else{
				d.findElement(Property.EditLink).click();
				}
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductOffline);
						 }
						 });
				tec.AC40.Common.Wait.wait10Second();
				String ProductStatus = d.findElement(Property.ProductStatus).getText();
				tec.AC40.Common.Wait.wait5Second();
				if(ProductStatus.equals("ACTIVE"))
				{
				d.findElement(Property.ProductOffline).click();
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.ProductAlertOK).click();
				tec.AC40.Common.Wait.wait5Second();
				}
				boolean PresetLPSettingsoption=d.findElement(Property.PresetLPSettingscheck).isSelected();
				if(PresetLPSettings.equalsIgnoreCase("YES")){
					
						if(PresetLPSettingsoption==true){
							//System.out.println("******do nothing********");
						}
						else{
							d.findElement(Property.PresetLPSettingscheck).click();
						}
						if (SiteNameType.equalsIgnoreCase("Custom")){
							d.findElement(Property.siteoption).click();
							tec.AC40.Common.Wait.wait2Second();
							 DateFormat dateFormat = new SimpleDateFormat("_yyyy-MMM-dd_h-mm-ss_a");
				     		 Date date = new Date();
							d.findElement(Property.sitename).clear();
							d.findElement(Property.sitename).sendKeys("sitename"+dateFormat.format(date));
						}
						tec.AC40.Common.Wait.wait2Second();	
						if (ProdutType.equalsIgnoreCase("GURL")){
							tec.AC40.Common.Wait.wait2Second();
					      	//System.out.println("selected landing page is GURL");
						     
						     }
						     else{
						    	 tec.AC40.Common.Wait.wait2Second();
							    // System.out.println("selected landing page is not GURL");
							    	 d.findElement(Property.userlandingfield).click();
							      	 tec.AC40.Common.Wait.wait2Second();
							      	 Keyboard LP = ((RemoteWebDriver) d).getKeyboard();
							      	 LP.pressKey(Keys.DOWN);
							      	 LP.pressKey(Keys.DOWN);
							      	 LP.pressKey(Keys.DOWN);
							      	 LP.pressKey(Keys.ENTER);
					      	 
						     }
						
					} 
		         else {
						if(PresetLPSettingsoption==true)
						{
						//	System.out.println("******click on check box****");
							d.findElement(Property.PresetLPSettingscheck).click();
						}
						else{
							//System.out.println("******do nothing********");
						}
		         }
		         
						 tec.AC40.Common.Wait.wait2Second();
						 d.findElement(Property.ProductInfoSave).click();
							tec.AC40.Common.Wait.wait5Second();
							waitfl.until(ExpectedConditions.elementToBeClickable(Property.ProductInfoSuccessMsg));
							waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductInfoSuccessMsg)); 
					
				
				
				tec.AC40.Common.Wait.wait5Second();
				d.findElement(Property.PricingTab).click();
				tec.AC40.Common.Wait.wait5Second();
		        boolean pricecode=d.findElement(By.xpath("//div[@id='divPriceInfo']/div/div/div/article[2]/fieldset/div/div/input")).isSelected();
		        if(pricecode==true){
				    //nothing to do
				   }else{
				    d.findElement(By.xpath("//div[@id='divPriceInfo']/div/div/div/article[2]/fieldset/div/div/input")).click(); 
				    tec.AC40.Common.Wait.wait5Second();
				   }
					//d.findElement(By.id("lnkPricing")).click();
					tec.AC40.Common.Wait.wait2Second();
					tec.AC40.Common.Wait.wait2Second();
					d.findElement(Property.LandingPagepricecode).click();
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.pricingselectlabel).click();
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.LandingPagepricecode).click();
					tec.AC40.Common.Wait.wait5Second();
					kb.pressKey(Keys.DOWN);
					Thread.sleep(1000);
					kb.pressKey(Keys.DOWN);
					Thread.sleep(1000);
					kb.pressKey(Keys.DOWN);
					Thread.sleep(1000);	
					kb.pressKey(Keys.DOWN);
					Thread.sleep(1000);
					kb.pressKey(Keys.ENTER);
					Thread.sleep(1000);	
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.pricecodeSavebtn).click();
					tec.AC40.Common.Wait.wait5Second();
					waitfl.until(ExpectedConditions.elementToBeClickable(Property.PricecodeSaveSuccessMsg));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PricecodeSaveSuccessMsg));
					//d.findElement(Property.ShipPriceSaveSuccessMsg).isDisplayed();
					//d.findElement(Property.ShipPriceSaveSuccessMsg).isEnabled();
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.PricecodeSaveSuccessMsg);
							 }
							 });

					
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.ProductOnlineLink).click();
					tec.AC40.Common.Wait.wait2Second();
					d.findElement(Property.ProductAlertOK).click();
					tec.AC40.Common.Wait.wait10Second();
					Wait.Fluentwait(Property.AdminHomeLink);
					 d.findElement(Property.AdminHomeLink).click();
					 if(Config.LayoutType.equals("Classic"))
					 {
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
					 	//d.findElement(Property.CompanyLink).isDisplayed();
					 	
						waitfl.until(new Function<WebDriver, WebElement>() 
								 {
								   public WebElement apply(WebDriver driver) {
								   return driver.findElement(Property.CompanyLink);
								 }
								 });

						
						tec.AC40.Common.Wait.wait2Second();	
					 }
					 else if(Config.LayoutType.equals("Layout1"))
					 {
						waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
						waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
					 	//d.findElement(Property.CompanyIconL1).isDisplayed();
					 	
						waitfl.until(new Function<WebDriver, WebElement>() 
								 {
								   public WebElement apply(WebDriver driver) {
								   return driver.findElement(Property.CompanyIconL1);
								 }
								 });

						
						tec.AC40.Common.Wait.wait2Second();	
					 }
					 else if(Config.LayoutType.equals("Layout2"))
					 {
						 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
						 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
					 	//d.findElement(Property.CompanyIconL2).isDisplayed();
					 	
						 waitfl.until(new Function<WebDriver, WebElement>() 
								 {
								   public WebElement apply(WebDriver driver) {
								   return driver.findElement(Property.CompanyIconL2);
								 }
								 });

						 
						 tec.AC40.Common.Wait.wait2Second();	
					 }
		}
					
		catch (Exception e){
			ErrorNumber = ErrorNumber +1;
			captureScreenshot();
	    	  e.printStackTrace();
	      }
		}
	
	public static void ShippingPrice3(String OrderType, String ShippingPrice, String ShippingPricePerPiece, String ProdutType)
			throws InterruptedException {
		try{
			// WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
			MouseAdjFunction();
			 FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
			 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
			 waitfl.pollingEvery(1, TimeUnit.SECONDS);
			 waitfl.ignoring(NoSuchElementException.class);
			 waitfl.ignoring(StaleElementReferenceException.class);
			 
			if(Config.LayoutType.equals("Classic"))
			{
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsLink));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsLink));
		   // d.findElement(Property.ProductsLink).isDisplayed();
		    
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ProductsLink);
					 }
					 });

			
			tec.AC40.Common.Wait.wait2Second();	
			d.findElement(Property.ProductsLink).click();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ManageProductsIcon));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ManageProductsIcon));
			//d.findElement(Property.ManageProductsIcon).isDisplayed();
	  		
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ManageProductsIcon);
					 }
					 });

			
			tec.AC40.Common.Wait.wait2Second();	
			d.findElement(Property.ManageProductsIcon).click();
			}
			else if(Config.LayoutType.equals("Layout1"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsIconL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsIconL1));
				//d.findElement(Property.ProductsIconL1).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductsIconL1);
						 }
						 });

				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.ProductsIconL1).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ManageProductsIconL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ManageProductsIconL1));
				//d.findElement(Property.ManageProductsIconL1).isDisplayed();
		  		
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ManageProductsIconL1);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.ManageProductsIconL1).click();
			}
			else if(Config.LayoutType.equals("Layout2"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsIconL2));
				//d.findElement(Property.ProductsIconL2).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductsIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.ProductsIconL2).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ManageProductsIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ManageProductsIconL2));
				//d.findElement(Property.ManageProductsIconL2).isDisplayed();
		  		
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ManageProductsIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.ManageProductsIconL2).click();
			}
			Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsSearchBox));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsSearchBox));
			//d.findElement(Property.ProductsSearchBox).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ProductsSearchBox);
					 }
					 });

			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.ProductsSearchBox).sendKeys(ProdutType);
			tec.AC40.Common.Wait.wait5Second();
			Wait.Fluentwait(Property.EditLink);
			d.findElement(Property.EditLink).click();
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ProductOffline);
					 }
					 });
			tec.AC40.Common.Wait.wait10Second();
			String ProductStatus = d.findElement(Property.ProductStatus).getText();
			//System.out.println("ProductStatus :"+ProductStatus);
			tec.AC40.Common.Wait.wait5Second();

			d.findElement(Property.ShippingTab).click();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingEditlink));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingEditlink));
			//d.findElement(Property.ShippingEditlink).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ShippingEditlink);
					 }
					 });

			
			tec.AC40.Common.Wait.wait2Second();
			tec.AC40.Common.Wait.wait5Second();
			//String colorvalue = d.findElement(Property.ProductOffline).getCssValue("background-color");
			//System.out.println("colorvalue :"+colorvalue);
			
			if(ProductStatus.equals("ACTIVE"))
			{
			d.findElement(Property.ProductOffline).click();
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.ProductAlertOK).click();
			tec.AC40.Common.Wait.wait5Second();
			}
			d.findElement(Property.ShippingEditlink).click();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ShippingPrice));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingPrice));
			//d.findElement(Property.ShippingPrice).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ShippingPrice);
					 }
					 });

			
			tec.AC40.Common.Wait.wait2Second();
			for(int i=1; i<= 1; i++)
			{
				//d.findElement(Property.PriceDetailsDownArrow).click();
				//d.findElement(Property.ShippingPrice).sendKeys(Keys.ENTER, "\b");
				d.findElement(Property.ShippingPrice).click();
				Thread.sleep(1000);
				kb.sendKeys(Keys.chord(Keys.CONTROL, "a"));
				Thread.sleep(2000);
				kb.pressKey(Keys.DELETE);
				Thread.sleep(500);
				d.findElement(Property.ShippingMinQty).click();
			}
			
			tec.AC40.Common.Wait.wait2Second();
			//d.findElement(Property.ShippingMinQty).click();
			d.findElement(Property.ShippingPrice).click();
			Thread.sleep(500);
			kb.sendKeys(ShippingPrice);
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.ShipUnitTypeDropDown1).click();
			tec.AC40.Common.Wait.wait2Second();
			kb.pressKey(Keys.UP);
			Thread.sleep(500);
			kb.pressKey(Keys.UP);
			Thread.sleep(500);
			kb.pressKey(Keys.UP);
			Thread.sleep(500);
			if(ShippingPricePerPiece.equals("0") || ShippingPricePerPiece.equals("0.00") || ShippingPricePerPiece.equals("0.000") || ShippingPricePerPiece.equals("0.0000") )
			{
			d.findElement(Property.PriceTypeDropDown).click();
			tec.AC40.Common.Wait.wait2Second();
			kb.pressKey(Keys.DOWN);
			Thread.sleep(500);
			}
			else
			{
				d.findElement(Property.PriceTypeDropDown).click();
				tec.AC40.Common.Wait.wait2Second();
				kb.pressKey(Keys.DOWN);
				Thread.sleep(500);
				kb.pressKey(Keys.DOWN);
				Thread.sleep(500);
				kb.pressKey(Keys.ENTER);
				Thread.sleep(500);
			}
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.ShipPriceDetailsSave).click();
			tec.AC40.Common.Wait.wait5Second();
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShipPriceSaveSuccessMsg));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShipPriceSaveSuccessMsg));
			//d.findElement(Property.ShipPriceSaveSuccessMsg).isDisplayed();
			//d.findElement(Property.ShipPriceSaveSuccessMsg).isEnabled();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ShipPriceSaveSuccessMsg);
					 }
					 });

			
			tec.AC40.Common.Wait.wait5Second();
			d.findElement(Property.ProductOnlineLink).click();
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.ProductAlertOK).click();
			Wait.Fluentwait(Property.AdminHomeLink);
			tec.AC40.Common.Wait.wait10Second();
			 d.findElement(Property.AdminHomeLink).click();
			 if(Config.LayoutType.equals("Classic"))
			 {
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
			 	//d.findElement(Property.CompanyLink).isDisplayed();
			 	
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyLink);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();	
			 }
			 else if(Config.LayoutType.equals("Layout1"))
			 {
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
			 	//d.findElement(Property.CompanyIconL1).isDisplayed();
			 	
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL1);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();	
			 }
			 else if(Config.LayoutType.equals("Layout2"))
			 {
				 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
				 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
			 	//d.findElement(Property.CompanyIconL2).isDisplayed();
			 	
				 waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL2);
						 }
						 });

				 
				 tec.AC40.Common.Wait.wait2Second();	
			 }
		}catch (Exception e){
			ErrorNumber = ErrorNumber +1;
			captureScreenshot();
	    	  e.printStackTrace();
	      }
	}
	

	public static void BasePriceSetting(String BasePrice, String ProdutType, String LandingPageProduct, String LandingPageOption,
			String LandingPage, String IsExternalPricingON)
			throws InterruptedException {
		try{
			// WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
			MouseAdjFunction();
			 FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
			 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
			 waitfl.pollingEvery(1, TimeUnit.SECONDS);
			 waitfl.ignoring(NoSuchElementException.class);
			 waitfl.ignoring(StaleElementReferenceException.class);
			 
			if(Config.LayoutType.equals("Classic"))
			{
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsLink));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsLink));
		   // d.findElement(Property.ProductsLink).isDisplayed();
		    
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ProductsLink);
					 }
					 });

			
			tec.AC40.Common.Wait.wait2Second();	
			d.findElement(Property.ProductsLink).click();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ManageProductsIcon));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ManageProductsIcon));
			//d.findElement(Property.ManageProductsIcon).isDisplayed();
	  		
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ManageProductsIcon);
					 }
					 });

			
			tec.AC40.Common.Wait.wait2Second();	
			d.findElement(Property.ManageProductsIcon).click();
			}
			else if(Config.LayoutType.equals("Layout1"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsIconL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsIconL1));
				//d.findElement(Property.ProductsIconL1).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductsIconL1);
						 }
						 });

				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.ProductsIconL1).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ManageProductsIconL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ManageProductsIconL1));
				//d.findElement(Property.ManageProductsIconL1).isDisplayed();
		  		
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ManageProductsIconL1);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.ManageProductsIconL1).click();
			}
			else if(Config.LayoutType.equals("Layout2"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsIconL2));
				//d.findElement(Property.ProductsIconL2).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductsIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.ProductsIconL2).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ManageProductsIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ManageProductsIconL2));
				//d.findElement(Property.ManageProductsIconL2).isDisplayed();
		  		
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ManageProductsIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.ManageProductsIconL2).click();
			}
			Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsSearchBox));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsSearchBox));
			//d.findElement(Property.ProductsSearchBox).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ProductsSearchBox);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.ProductsSearchBox).sendKeys(ProdutType);
			tec.AC40.Common.Wait.wait5Second();
			Wait.Fluentwait(Property.EditLink);
			d.findElement(Property.EditLink).click();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductOffline));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductOffline));
			//d.findElement(Property.ShippingEditlink).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ProductOffline);
					 }
					 });
			
			tec.AC40.Common.Wait.wait10Second();
			tec.AC40.Common.Wait.wait10Second();
			
			String ProductStatus = d.findElement(Property.ProductStatus).getText();
			//System.out.println("ProductStatus :"+ProductStatus);
			if(ProductStatus.equals("ACTIVE"))
			{
			d.findElement(Property.ProductOffline).click();
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.ProductAlertOK).click();
			tec.AC40.Common.Wait.wait5Second();
			}
			for(int i=1; i<= 1; i++)
			{
				//d.findElement(Property.PriceDetailsDownArrow).click();
				//d.findElement(Property.ShippingPrice).sendKeys(Keys.ENTER, "\b");
				if(ProdutType.equals("Static Print") || ProdutType.equals("Static Inventory"))
				{
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.BasePriceTextBoxStatic).click();
				}
				else if(ProdutType.equals("Broadcast")){
					d.findElement(Property.BasePriceTextBoxBroadcast).click();

				}
				else if(ProdutType.equals("Dynamic Print"))
				{
					d.findElement(Property.BasePriceTextBoxDynamic).click();
				}
				Thread.sleep(1000);
				kb.sendKeys(Keys.chord(Keys.CONTROL, "a"));
				Thread.sleep(2000);
				kb.pressKey(Keys.DELETE);
				Thread.sleep(500);
				//d.findElement(Property.ShippingMinQty).click();
			}
			
			//tec.AC40.Common.Wait.wait2Second();
			//d.findElement(Property.ShippingMinQty).click();
			//d.findElement(Property.BasePriceTextBox).click();
			//Thread.sleep(500);
			kb.sendKeys(BasePrice);
			tec.AC40.Common.Wait.wait2Second();
			
			// Product code update related script
			if(IsExternalPricingON.equalsIgnoreCase("NO"))
			{
			d.findElement(Property.ProductCodeTextBox).clear();
			Thread.sleep(500);
			d.findElement(Property.ProductCodeTextBox).sendKeys(ProdutType);
			}
			else if(IsExternalPricingON.equalsIgnoreCase("YES"))
			{
				d.findElement(Property.ProductCodeTextBox).clear();
				Thread.sleep(500);
				d.findElement(Property.ProductCodeTextBox).sendKeys("798644");
			}
			
			tec.AC40.Common.Wait.wait5Second();
			//Landing Page Option
	if(LandingPageOption.equalsIgnoreCase("YES")){
      	if(LandingPage.equals("Optional"))
			{
				d.findElement(Property.Optional).click();
			}
			else if(LandingPage.equals("Required"))
			{
				d.findElement(Property.Required).click();
			}
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.SelectProductslink).click();
			tec.AC40.Common.Wait.wait5Second();
			
			d.findElement(Property.searchProduct).clear();
			d.findElement(Property.searchProduct).sendKeys("PURL");
			tec.AC40.Common.Wait.wait2Second();
			if(d.findElement(Property.SelectLandingProduct).isSelected()){
				//System.out.println("Landing page selected");
			}
			else{
			d.findElement(Property.SelectLandingProduct).click();
			}
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.searchProduct).clear();
			d.findElement(Property.searchProduct).sendKeys("GURL");
			tec.AC40.Common.Wait.wait2Second();
			if(d.findElement(Property.SelectLandingProduct).isSelected()){
				System.out.println("Landing page selected");
			}
			else{
			d.findElement(Property.SelectLandingProduct).click();
			}
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.searchProduct).clear();
			d.findElement(Property.searchProduct).sendKeys("PGURL");
			tec.AC40.Common.Wait.wait2Second();
			if(d.findElement(Property.SelectLandingProduct).isSelected()){
				//System.out.println("Landing page selected");
			}
			else{
			d.findElement(Property.SelectLandingProduct).click();
			}
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.LandingProductSelect).click();
			tec.AC40.Common.Wait.wait5Second();
			d.findElement(Property.LandingProducts).isDisplayed(); 
			tec.AC40.Common.Wait.wait5Second();
			d.findElement(Property.LandingURL).click();
			tec.AC40.Common.Wait.wait2Second();
			Keyboard LU = ((RemoteWebDriver) d).getKeyboard();
			LU.pressKey(Keys.HOME);
			LU.pressKey(Keys.DOWN);
			LU.pressKey(Keys.DOWN);
			LU.pressKey(Keys.DOWN);
			LU.pressKey(Keys.ENTER);
		//	d.findElement(Property.LandingURLoption).click();
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.LandingKeyfield).click();
			tec.AC40.Common.Wait.wait2Second();
			LU.pressKey(Keys.HOME);
			LU.pressKey(Keys.DOWN);
			LU.pressKey(Keys.DOWN);
			LU.pressKey(Keys.DOWN);
			LU.pressKey(Keys.DOWN);
			LU.pressKey(Keys.ENTER);
			//d.findElement(Property.LandingKeyoption).click();
			}
			else if(ProdutType.equals("Dynamic Print")||ProdutType.equals("Broadcast")){
				d.findElement(Property.None).click();
			}
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.ProductInfoSave).click();
			tec.AC40.Common.Wait.wait5Second();
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.ProductInfoSuccessMsg));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductInfoSuccessMsg));
			//d.findElement(Property.ShipPriceSaveSuccessMsg).isDisplayed();
			//d.findElement(Property.ShipPriceSaveSuccessMsg).isEnabled();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ProductInfoSuccessMsg);
					 }
					 });
			
			tec.AC40.Common.Wait.wait5Second();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductOnlineLink));
			d.findElement(Property.ProductOnlineLink).click();
			tec.AC40.Common.Wait.wait2Second();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductAlertOK));
			d.findElement(Property.ProductAlertOK).click();
			System.out.println("*Product Alert ok working fine*");
			tec.AC40.Common.Wait.wait10Second();
			Wait.Fluentwait(Property.AdminHomeLink);
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.AdminHomeLink));
			System.out.println("*Product Home link pre-click *");
			Wait.Fluentwait(Property.AdminHomeLink);
			 d.findElement(Property.AdminHomeLink).click();
				System.out.println("*Product Home link fine*");
				tec.AC40.Common.Wait.wait10Second();
			 if(Config.LayoutType.equals("Classic"))
			 {
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
			 	//d.findElement(Property.CompanyLink).isDisplayed();
			 	
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyLink);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();	
			 }
			 else if(Config.LayoutType.equals("Layout1"))
			 {
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
			 	//d.findElement(Property.CompanyIconL1).isDisplayed();
			 	
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL1);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();	
			 }
			 else if(Config.LayoutType.equals("Layout2"))
			 {
				 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
				 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
			 	//d.findElement(Property.CompanyIconL2).isDisplayed();
			 	
				 waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL2);
						 }
						 });

				 
				 tec.AC40.Common.Wait.wait2Second();	
			 }
		}catch (Exception e){
			ErrorNumber = ErrorNumber +1;
			captureScreenshot();
	    	  e.printStackTrace();
	      }
	}
	
	
	public static void DownloadPriceSetting(String DownloadPrice, String DecimalValue, String ProdutType)
			throws InterruptedException {
		try{
			 WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
			 MouseAdjFunction();
			 FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
			 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
			 waitfl.pollingEvery(1, TimeUnit.SECONDS);
			 waitfl.ignoring(NoSuchElementException.class);
			 waitfl.ignoring(StaleElementReferenceException.class);
			 
			if(Config.LayoutType.equals("Classic"))
			{
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsLink));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsLink));
			//d.findElement(Property.ProductsLink).isDisplayed();
		
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ProductsLink);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();	
			d.findElement(Property.ProductsLink).click();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ManageProductsIcon));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ManageProductsIcon));
			//d.findElement(Property.ManageProductsIcon).isDisplayed();
	  		
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ManageProductsIcon);
					 }
					 });

			
			tec.AC40.Common.Wait.wait2Second();	
			d.findElement(Property.ManageProductsIcon).click();
			}
			else if(Config.LayoutType.equals("Layout1"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsIconL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsIconL1));
				//d.findElement(Property.ProductsIconL1).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductsIconL1);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.ProductsIconL1).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ManageProductsIconL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ManageProductsIconL1));
				//d.findElement(Property.ManageProductsIconL1).isDisplayed();
		  		
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ManageProductsIconL1);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.ManageProductsIconL1).click();
			}
			else if(Config.LayoutType.equals("Layout2"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsIconL2));
				//d.findElement(Property.ProductsIconL2).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductsIconL2);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.ProductsIconL2).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ManageProductsIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ManageProductsIconL2));
				//d.findElement(Property.ManageProductsIconL2).isDisplayed();
		  		
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ManageProductsIconL2);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.ManageProductsIconL2).click();
			}
			Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsSearchBox));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsSearchBox));
			//d.findElement(Property.ProductsSearchBox).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ProductsSearchBox);
					 }
					 });

			
			d.findElement(Property.ProductsSearchBox).sendKeys(ProdutType);
			tec.AC40.Common.Wait.wait5Second();
			
			d.findElement(Property.EditLink).click();
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ProductOffline);
					 }
					 });
			tec.AC40.Common.Wait.wait10Second();
			String ProductStatus = d.findElement(Property.ProductStatus).getText();
			//System.out.println("ProductStatus :"+ProductStatus);
			d.findElement(Property.ListTypesTab).click();
			
			//d.findElement(Property.ListTypeEditLink).click();
			tec.AC40.Common.Wait.wait10Second();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductOffline));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductOffline));
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ProductOffline);
					 }
					 });

			
			tec.AC40.Common.Wait.wait10Second();
			
			if(ProductStatus.equals("ACTIVE"))
			{

			d.findElement(Property.ProductOffline).click();
			tec.AC40.Common.Wait.wait5Second();
			d.findElement(Property.ProductAlertOK).click();
			}
			tec.AC40.Common.Wait.wait10Second();
			boolean downloadorprint=d.findElement(Property.DownloadOrPrintRatioButton).isSelected();
				if(downloadorprint==true){
					//System.out.println("******do nothing********");
				}else{
					d.findElement(Property.DownloadOrPrintRatioButton).click();
				}
	   	   tec.AC40.Common.Wait.wait5Second();
			for(int i=1; i<= 1; i++)
			{
				//d.findElement(Property.DownloadPriceTextBox).sendKeys(Keys.ENTER, "\b");
				d.findElement(Property.DownloadPriceTextBox).click();
				Thread.sleep(1000);
				kb.sendKeys(Keys.chord(Keys.CONTROL, "a"));
				Thread.sleep(2000);
				kb.pressKey(Keys.DELETE);
				Thread.sleep(500);
				d.findElement(Property.ShipToMyAddressDisplayName).click();
			}
			
			if(ProdutType.equals("Dynamic Print"))
			{
				d.findElement(Property.DownloadPriceTextBox).click();
				Thread.sleep(1500);
				kb.sendKeys(DownloadPrice);
			}
			else if(ProdutType.equals("Static Print"))
			{
				d.findElement(Property.StaticDownloadPriceTextBox).click();
				Thread.sleep(1500);
				kb.sendKeys(DownloadPrice);
			}
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.ListTypesSave).click();
			tec.AC40.Common.Wait.wait5Second();
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.ListSavedSuccessMsg));
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ListSavedSuccessMsg);
					 }
					 });

			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.ProductOnlineLink).click();
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.ProductAlertOK).click();
			tec.AC40.Common.Wait.wait10Second();
			Wait.Fluentwait(Property.AdminHomeLink);
		    d.findElement(Property.AdminHomeLink).click();
		    if(Config.LayoutType.equals("Classic"))
			 {
		    	waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
		    	waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
			 	//d.findElement(Property.CompanyLink).isDisplayed();
			 	
		    	waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyLink);
						 }
						 });

		    	
		    	tec.AC40.Common.Wait.wait2Second();	
			 }
			 else if(Config.LayoutType.equals("Layout1"))
			 {
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
			 	//d.findElement(Property.CompanyIconL1).isDisplayed();
			 	
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL1);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();	
			 }
			 else if(Config.LayoutType.equals("Layout2"))
			 {
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
			 	//d.findElement(Property.CompanyIconL2).isDisplayed();
			 	
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL2);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();	
			 }
		}catch (Exception e){
			ErrorNumber = ErrorNumber +1;
			captureScreenshot();
	    	  e.printStackTrace();
	      }
	}

  public static void ListSetting(String ProdutType)throws InterruptedException
  {
	  try{
		    WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
		    MouseAdjFunction();
		    FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
			 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
			 waitfl.pollingEvery(1, TimeUnit.SECONDS);
			 waitfl.ignoring(NoSuchElementException.class);
			 waitfl.ignoring(StaleElementReferenceException.class);
		    
			if(Config.LayoutType.equals("Classic"))
			{
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsLink));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsLink));
			//d.findElement(Property.ProductsLink).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ProductsLink);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();	
			d.findElement(Property.ProductsLink).click();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ManageProductsIcon));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ManageProductsIcon));
			//d.findElement(Property.ManageProductsIcon).isDisplayed();
	  		
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ManageProductsIcon);
					 }
					 });

			
			tec.AC40.Common.Wait.wait2Second();	
			d.findElement(Property.ManageProductsIcon).click();
			}
			else if(Config.LayoutType.equals("Layout1"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsIconL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsIconL1));
				//d.findElement(Property.ProductsIconL1).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductsIconL1);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.ProductsIconL1).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ManageProductsIconL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ManageProductsIconL1));
				//d.findElement(Property.ManageProductsIconL1).isDisplayed();
		  		
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ManageProductsIconL1);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.ManageProductsIconL1).click();
			}
			else if(Config.LayoutType.equals("Layout2"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsIconL2));
				//d.findElement(Property.ProductsIconL2).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductsIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.ProductsIconL2).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ManageProductsIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ManageProductsIconL2));
				//d.findElement(Property.ManageProductsIconL2).isDisplayed();
		  		
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ManageProductsIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.ManageProductsIconL2).click();
			}
			//org.openqa.selenium.Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsSearchBox));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsSearchBox));
			//d.findElement(Property.ProductsSearchBox).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ProductsSearchBox);
					 }
					 });

			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.ProductsSearchBox).sendKeys(ProdutType);
			tec.AC40.Common.Wait.wait5Second();
			d.findElement(Property.EditLink).click();
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ProductOffline);
					 }
					 });
			tec.AC40.Common.Wait.wait10Second();
			String ProductStatus = d.findElement(Property.ProductStatus).getText();
			//System.out.println("ProductStatus :"+ProductStatus);
			d.findElement(Property.ListTypesTab).click();
			
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ListTypeEditLink));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ListTypeEditLink));
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ListTypeEditLink);
					 }
					 });

			
			tec.AC40.Common.Wait.wait2Second();	
			//d.findElement(Property.ListTypeEditLink).click();
			tec.AC40.Common.Wait.wait15Second();	
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.ProductOffline));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductOffline));
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ProductOffline);
					 }
					 });

			
			tec.AC40.Common.Wait.wait5Second();	
			if(ProductStatus.equals("ACTIVE"))
			{
				tec.AC40.Common.Wait.wait2Second();	
			d.findElement(Property.ProductOffline).click();
			tec.AC40.Common.Wait.wait10Second();
			d.findElement(Property.ProductAlertOK).click();
			}
			tec.AC40.Common.Wait.wait10Second();
			if(ProdutType.equals("Dynamic Print")||ProdutType.equals("Static Print")||ProdutType.equals("Static Inventory"))
			{
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShiptomultipleLocations));
				//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShiptomultipleLocations));
				WebElement Shiptomultiplelocation = d.findElement(Property.ShiptomultipleLocations);
		   		
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ShiptomultipleLocations);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();
		   		if(Shiptomultiplelocation.isSelected())
		   		{	
		   			//System.out.println("ship to multiple alrdy selected");
		   		}
		   		else
		   		{
		   			d.findElement(Property.ShiptomultipleLocations).click();
		   		}
			}//closing if of products
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.ListTypesSave));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ListTypesSave));
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ListTypesSave);
					 }
					 });

			
			d.findElement(Property.ListTypesSave).click();
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.ListSavedSuccessMsg));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ListSavedSuccessMsg));
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ListSavedSuccessMsg);
					 }
					 });

			
			d.findElement(Property.ListSavedSuccessMsg).click();	
			tec.AC40.Common.Wait.wait5Second();
			d.findElement(Property.ProductOnlineLink).click();
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.ProductAlertOK).click();
			tec.AC40.Common.Wait.wait10Second();
			Wait.Fluentwait(Property.AdminHomeLink);
			d.findElement(Property.AdminHomeLink).click();
			if(Config.LayoutType.equals("Classic"))
			 {
		    	waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
		    	waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
			 	//d.findElement(Property.CompanyLink).isDisplayed();
			 	
		    	waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyLink);
						 }
						 });

		    	
		    	tec.AC40.Common.Wait.wait2Second();	
			 }
		    else if(Config.LayoutType.equals("Layout1"))
			 {
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
			 	//d.findElement(Property.CompanyIconL1).isDisplayed();
			 	
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL1);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();	
			 }
		   else if(Config.LayoutType.equals("Layout2"))
			 {
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
			 	//d.findElement(Property.CompanyIconL2).isDisplayed();
			 	
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL2);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();	
			 }
	  }catch (Exception e){
		  ErrorNumber = ErrorNumber +1;
		  captureScreenshot();
    	  e.printStackTrace();
      }
  }
	
 public static void Discount(String Discount, String DiscountPercentage)
			throws InterruptedException {
		try{
			 WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
			 MouseAdjFunction();
			 FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
			 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
			 waitfl.pollingEvery(1, TimeUnit.SECONDS);
			 waitfl.ignoring(NoSuchElementException.class);
			 waitfl.ignoring(StaleElementReferenceException.class);
			 
			if(Config.LayoutType.equals("Classic"))
			{
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsLink));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsLink));
			//d.findElement(Property.ProductsLink).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ProductsLink);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.ProductsLink).click();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PromotionsIcon));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PromotionsIcon));
			//d.findElement(Property.PromotionsIcon).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PromotionsIcon);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PromotionsIcon).click();
			}
			else if(Config.LayoutType.equals("Layout1"))
			{
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.ProductsIconL1));
				//waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsIconL1));
				//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsIconL1));
				//d.findElement(Property.ProductsIconL1).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductsIconL1);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.ProductsIconL1).click();
				//waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PromotionsIconL1));
				//d.findElement(Property.PromotionsIconL1).isDisplayed();
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.PromotionsIconL1).click();
			}
			else if(Config.LayoutType.equals("Layout2"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsIconL2));
				//d.findElement(Property.ProductsIconL2).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductsIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.ProductsIconL2).click();
				tec.AC40.Common.Wait.wait2Second();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PromotionsIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PromotionsIconL2));
				//d.findElement(Property.PromotionsIconL2).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.PromotionsIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.PromotionsIconL2).click();
			}
		Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
		waitfl.until(ExpectedConditions.elementToBeClickable(Property.PromotionsSearch));
			//waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PromotionsSearch));
			//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PromotionsSearch));
			//d.findElement(Property.PromotionsSearch).isDisplayed();
			waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) 
				   {
					   return driver.findElement(Property.PromotionsSearch);
				   }
				 });
		
		tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PromotionsSearch).sendKeys(Config.DiscountName);
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PromotionsEdit).click();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PromotionDiscountNO));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PromotionDiscountNO));
			//d.findElement(Property.PromotionDiscountNO).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PromotionDiscountNO);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			if(DiscountPercentage.equals("N"))
			{
				d.findElement(Property.PromotionDiscountNO).click();
			}
			
			else
			{
				d.findElement(Property.PromotionDisocuntYes).click();
			}
			if(Discount.equals("0") || Discount.equals("0.00") || Discount.equals("0.000") || Discount.equals("0.0000"))
			{
				d.findElement(Property.PromotionDiscountDeactive).click();
				
			}
			else
			{
				for(int i=1; i<=1; i++)
				{
					//d.findElement(Property.PromotionValue).sendKeys(Keys.ENTER, "\b");
					d.findElement(Property.PromotionValue).click();
					Thread.sleep(1000);
					kb.sendKeys(Keys.chord(Keys.CONTROL, "a"));
					Thread.sleep(2000);
					kb.pressKey(Keys.DELETE);
					Thread.sleep(500);
					d.findElement(Property.PromotionName).click();
					Thread.sleep(500);
				}
				//d.findElement(Property.PromotionName).click();
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.PromotionValue).click();
				Thread.sleep(500);
				kb.sendKeys(Discount);
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.PromotionDisocuntActive).click();
			}
			d.findElement(Property.PromotionSave).click();
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.PromotionSuccessMsg));
			//waitfl.until(ExpectedConditions.elementToBeClickable(Property.PromotionSuccessMsg));
			//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PromotionSuccessMsg));
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PromotionSuccessMsg);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			Wait.Fluentwait(Property.AdminHomeLink);
			d.findElement(Property.AdminHomeLink).click();
			
			if(Config.LayoutType.equals("Classic"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
				//d.findElement(Property.CompanyLink).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyLink);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
			}
			else if(Config.LayoutType.equals("Layout1"))
			{
				Thread.sleep(4000);
				//waitfl.until(ExpectedConditions.elementToBeClickable(Property.CompanyIconL1));
				//waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
				//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
				//d.findElement(Property.CompanyIconL1).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL1);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
			}
			else if(Config.LayoutType.equals("Layout2"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
				//d.findElement(Property.CompanyIconL2).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
			}
			
			}catch (Exception e){
				ErrorNumber = ErrorNumber +1;
				captureScreenshot();
	    	  e.printStackTrace();
	      }
	}
 public static void LandingPageDiscount(String Discount, String DiscountPercentage)
			throws InterruptedException {
		try{
			 WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
			 MouseAdjFunction();
			 FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
			 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
			 waitfl.pollingEvery(1, TimeUnit.SECONDS);
			 waitfl.ignoring(NoSuchElementException.class);
			 waitfl.ignoring(StaleElementReferenceException.class);
			 
			if(Config.LayoutType.equals("Classic"))
			{
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsLink));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsLink));
			//d.findElement(Property.ProductsLink).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ProductsLink);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.ProductsLink).click();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PromotionsIcon));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PromotionsIcon));
			//d.findElement(Property.PromotionsIcon).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PromotionsIcon);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PromotionsIcon).click();
			}
			else if(Config.LayoutType.equals("Layout1"))
			{
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.ProductsIconL1));
				//waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsIconL1));
				//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsIconL1));
				//d.findElement(Property.ProductsIconL1).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductsIconL1);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.ProductsIconL1).click();
				//waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PromotionsIconL1));
				//d.findElement(Property.PromotionsIconL1).isDisplayed();
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.PromotionsIconL1).click();
			}
			else if(Config.LayoutType.equals("Layout2"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsIconL2));
				//d.findElement(Property.ProductsIconL2).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductsIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.ProductsIconL2).click();
				tec.AC40.Common.Wait.wait2Second();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PromotionsIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PromotionsIconL2));
				//d.findElement(Property.PromotionsIconL2).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.PromotionsIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.PromotionsIconL2).click();
			}
		Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
		waitfl.until(ExpectedConditions.elementToBeClickable(Property.PromotionsSearch));
			//waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PromotionsSearch));
			//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PromotionsSearch));
			//d.findElement(Property.PromotionsSearch).isDisplayed();
		waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.PromotionsSearch);
				 }
				 });
		
		tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PromotionsSearch).sendKeys(Config.DiscountName2);
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PromotionsEdit).click();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PromotionDiscountNO));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PromotionDiscountNO));
			//d.findElement(Property.PromotionDiscountNO).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PromotionDiscountNO);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			if(DiscountPercentage.equals("N"))
			{
				d.findElement(Property.PromotionDiscountNO).click();
			}
			
			else
			{
				d.findElement(Property.PromotionDisocuntYes).click();
			}
			if(Discount.equals("0") || Discount.equals("0.00") || Discount.equals("0.000") || Discount.equals("0.0000"))
			{
				d.findElement(Property.PromotionDiscountDeactive).click();
				
			}
			else
			{
				for(int i=1; i<=1; i++)
				{
					//d.findElement(Property.PromotionValue).sendKeys(Keys.ENTER, "\b");
					d.findElement(Property.PromotionValue).click();
					Thread.sleep(1000);
					kb.sendKeys(Keys.chord(Keys.CONTROL, "a"));
					Thread.sleep(2000);
					kb.pressKey(Keys.DELETE);
					Thread.sleep(500);
					d.findElement(Property.PromotionName).click();
					Thread.sleep(500);
				}
				//d.findElement(Property.PromotionName).click();
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.PromotionValue).click();
				Thread.sleep(500);
				kb.sendKeys(Discount);
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.PromotionDisocuntActive).click();
			}
			d.findElement(Property.PromotionSave).click();
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.PromotionSuccessMsg));
			//waitfl.until(ExpectedConditions.elementToBeClickable(Property.PromotionSuccessMsg));
			//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PromotionSuccessMsg));
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PromotionSuccessMsg);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			Wait.Fluentwait(Property.AdminHomeLink);
			d.findElement(Property.AdminHomeLink).click();
			
			if(Config.LayoutType.equals("Classic"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
				//d.findElement(Property.CompanyLink).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyLink);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
			}
			else if(Config.LayoutType.equals("Layout1"))
			{
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.CompanyIconL1));
				//waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
				//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
				//d.findElement(Property.CompanyIconL1).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL1);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
			}
			else if(Config.LayoutType.equals("Layout2"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
				//d.findElement(Property.CompanyIconL2).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
			}
			
			}catch (Exception e){
				ErrorNumber = ErrorNumber +1;
				captureScreenshot();
	    	  e.printStackTrace();
	      }
	}
	public static void AddonPrice(String AddonPrice, String AddonPricePerPiece)
			throws InterruptedException {
		try{
			 WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
			 MouseAdjFunction();
			 FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
			 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
			 waitfl.pollingEvery(1, TimeUnit.SECONDS);
			 waitfl.ignoring(NoSuchElementException.class);
			 waitfl.ignoring(StaleElementReferenceException.class);
			 
			if(Config.LayoutType.equals("Classic"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
				//d.findElement(Property.CompanyLink).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyLink);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
			d.findElement(Property.CompanyLink).click();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PricingIcon));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PricingIcon));
			//d.findElement(Property.PricingIcon).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PricingIcon);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();	
			d.findElement(Property.PricingIcon).click();
			}
			else if(Config.LayoutType.equals("Layout1"))
			{
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.CompanyIconL1));
				//waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
				//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
				//d.findElement(Property.CompanyIconL1).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL1);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.CompanyIconL1).click();
				//waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PricingIconL1));
				//d.findElement(Property.PricingIconL1).isDisplayed();
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.PricingIconL1).click();
			}
			else if(Config.LayoutType.equals("Layout2"))
			{
				
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
				//d.findElement(Property.CompanyIconL2).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.CompanyIconL2).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PricingIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PricingIconL2));
				//d.findElement(Property.PricingIconL2).isDisplayed();
		  		
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.PricingIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.PricingIconL2).click();
			}
		Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
		waitfl.until(ExpectedConditions.elementToBeClickable(Property.PricingSearchBox));
			//waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PricingSearchBox));
			//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PricingSearchBox));
			//d.findElement(Property.PricingSearchBox).isDisplayed();
		
		waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.PricingSearchBox);
				 }
				 });
		
		tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PricingSearchBox).sendKeys(Config.AddonPriceCode);
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.priceDetailsLink).click();
			Thread.sleep(1000);
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.PriceEditLink));
			//waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PriceEditLink));
			//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PriceEditLink));
			//d.findElement(Property.PriceEditLink).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PriceEditLink);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PriceEditLink).click();
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.PriceEntertextBox));
			//waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PriceEntertextBox));
			//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PriceEntertextBox));
			//d.findElement(Property.PriceEntertextBox).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PriceEntertextBox);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			for(int i=1; i<= 1; i++)
			{
				//d.findElement(Property.PriceDetailsDownArrow).click();
				//d.findElement(Property.PriceEntertextBox).sendKeys(Keys.ENTER, "\b");
				d.findElement(Property.PriceEntertextBox).click();
				Thread.sleep(1000);
				kb.sendKeys(Keys.chord(Keys.CONTROL, "a"));
				Thread.sleep(2000);
				kb.pressKey(Keys.DELETE);
				Thread.sleep(2000);
				d.findElement(Property.PriceMinimumQuantity).click();
			}
			tec.AC40.Common.Wait.wait2Second();
			//d.findElement(Property.PriceMinimumQuantity).click();
			//tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PriceEntertextBox).click();
			Thread.sleep(500);
			kb.sendKeys(AddonPrice);
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PriceTypeDropDown).click();
			tec.AC40.Common.Wait.wait2Second();
			kb.pressKey(Keys.UP);
			Thread.sleep(500);
			kb.pressKey(Keys.UP);
			Thread.sleep(500);
			kb.pressKey(Keys.UP);
			Thread.sleep(500);
			if(AddonPricePerPiece.equals("0") || AddonPricePerPiece.equals("0.00") || AddonPricePerPiece.equals("0.000") || AddonPricePerPiece.equals("0.0000"))
			{
				d.findElement(Property.PriceTypeDropDown).click();
				tec.AC40.Common.Wait.wait2Second();
				kb.pressKey(Keys.DOWN);
				Thread.sleep(500);
			}
			else
			{
				d.findElement(Property.PriceTypeDropDown).click();
				tec.AC40.Common.Wait.wait2Second();
				kb.pressKey(Keys.DOWN);
				Thread.sleep(500);
				kb.pressKey(Keys.DOWN);
				Thread.sleep(500);
				kb.pressKey(Keys.ENTER);
				Thread.sleep(500);
			}
			d.findElement(Property.PriceDetailsSaveButton).click();
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.priceSaveSuccessMsg));
			//tec.AC40.Common.Wait.wait5Second();
			//waitfl.until(ExpectedConditions.elementToBeClickable(Property.priceSaveSuccessMsg));
			//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.priceSaveSuccessMsg));
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.priceSaveSuccessMsg);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			//d.findElement(Property.priceSaveSuccessMsg).isDisplayed();
			//d.findElement(Property.priceSaveSuccessMsg).isEnabled();
			//tec.AC40.Common.Wait.wait2Second();
			Wait.Fluentwait(Property.AdminHomeLink);
			d.findElement(Property.AdminHomeLink).click();
			
			if(Config.LayoutType.equals("Classic"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
				//d.findElement(Property.CompanyLink).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyLink);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
			}
			else if(Config.LayoutType.equals("Layout1"))
			{
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.CompanyIconL1));
				//waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
				//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
				//d.findElement(Property.CompanyIconL1).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL1);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
			}
			else if(Config.LayoutType.equals("Layout2"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
				//d.findElement(Property.CompanyIconL2).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
			}
			}catch (Exception e){
			ErrorNumber = ErrorNumber +1;
			captureScreenshot();
	    	  e.printStackTrace();
	      }
	}
	
	public static void ShippingPriceSetting(String OrderBaseShipping, String ShippingPricePerPiece)
			throws InterruptedException {
		try{
			 Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
			 WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);	
			 MouseAdjFunction();
			 FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
			 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
			 waitfl.pollingEvery(1, TimeUnit.SECONDS);
			 waitfl.ignoring(NoSuchElementException.class);
			 waitfl.ignoring(StaleElementReferenceException.class);
			 
			 
			if(Config.LayoutType.equals("Classic"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
			//d.findElement(Property.CompanyLink).isDisplayed();
			
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyLink);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();	
			d.findElement(Property.CompanyLink).click();
			
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PricingIcon));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PricingIcon));
			//d.findElement(Property.PricingIconL2).isDisplayed();
	  		
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PricingIcon);
					 }
					 });

			
			tec.AC40.Common.Wait.wait2Second();	
			d.findElement(Property.PricingIcon).click();
			}
			else if(Config.LayoutType.equals("Layout1"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
				//d.findElement(Property.CompanyIconL1).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL1);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.CompanyIconL1).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PricingIconL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PricingIconL1));
				//d.findElement(Property.PricingIconL1).isDisplayed();
		  		
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.PricingIconL1);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.PricingIconL1).click();
			}
			else if(Config.LayoutType.equals("Layout2"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
				//d.findElement(Property.CompanyIconL2).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.CompanyIconL2).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PricingIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PricingIconL2));
				//d.findElement(Property.PricingIconL2).isDisplayed();
		  		
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.PricingIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.PricingIconL2).click();
			}
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PricingSearchBox));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PricingSearchBox));
			//d.findElement(Property.PricingSearchBox).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PricingSearchBox);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PricingSearchBox).sendKeys(Config.ShippingPriceCode);
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.priceDetailsLink).click();
			tec.AC40.Common.Wait.wait2Second();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PriceEditLink));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PriceEditLink));
			//d.findElement(Property.PriceEditLink).isDisplayed();
			
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PriceEditLink);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PriceEditLink).click();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PriceEntertextBox));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PriceEntertextBox));
			//d.findElement(Property.PriceEntertextBox).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PriceEntertextBox);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			//org.openqa.selenium.Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
			//d.findElement(OR.PriceEntertextBox).clear();
			for(int i=1; i<= 1; i++)
			{
				//d.findElement(Property.PriceDetailsDownArrow).click();
				//d.findElement(Property.PriceEntertextBox).sendKeys(Keys.ENTER, "\b");
				d.findElement(Property.PriceEntertextBox).click();
				Thread.sleep(1000);
				kb.sendKeys(Keys.chord(Keys.CONTROL, "a"));
				Thread.sleep(2000);
				kb.pressKey(Keys.DELETE);
				Thread.sleep(500);
				d.findElement(Property.PriceMinimumQuantity).click();
			}
			//tec.AC40.Common.Wait.wait2Second();
			//d.findElement(Property.PriceMinimumQuantity).click();
			tec.AC40.Common.Wait.wait2Second();
			//d.findElement(Property.PriceEntertextBox).sendKeys(AddonPrice);
			d.findElement(Property.PriceEntertextBox).click();
			Thread.sleep(500);
			kb.sendKeys(OrderBaseShipping);
			//d.findElement(OR.PriceEntertextBox).
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PriceTypeDropDown).click();
			tec.AC40.Common.Wait.wait2Second();
			kb.pressKey(Keys.UP);
			Thread.sleep(500);
			kb.pressKey(Keys.UP);
			Thread.sleep(500);
			kb.pressKey(Keys.UP);
			Thread.sleep(500);
			if(ShippingPricePerPiece.equals("0") || ShippingPricePerPiece.equals("0.00") || ShippingPricePerPiece.equals("0.000") || ShippingPricePerPiece.equals("0.0000") )
			{
			d.findElement(Property.PriceTypeDropDown).click();
			tec.AC40.Common.Wait.wait2Second();
			kb.pressKey(Keys.DOWN);
			Thread.sleep(500);
			}
			else
			{
				d.findElement(Property.PriceTypeDropDown).click();
				tec.AC40.Common.Wait.wait2Second();
				kb.pressKey(Keys.DOWN);
				Thread.sleep(500);
				kb.pressKey(Keys.DOWN);
				Thread.sleep(500);
				kb.pressKey(Keys.ENTER);
				Thread.sleep(500);
			}
			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PriceDetailsSaveButton).click();
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.priceSaveSuccessMsg));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.priceSaveSuccessMsg));
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.priceSaveSuccessMsg);
					 }
					 });
			
			tec.AC40.Common.Wait.wait5Second();
			Wait.Fluentwait(Property.AdminHomeLink);
		d.findElement(Property.AdminHomeLink).click();
		if(Config.LayoutType.equals("Classic"))
		{
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
			//d.findElement(Property.CompanyLink).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.CompanyLink);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();	
		}
		else if(Config.LayoutType.equals("Layout1"))
		{
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
			//d.findElement(Property.CompanyIconL1).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.CompanyIconL1);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();	
		}
		else if(Config.LayoutType.equals("Layout2"))
		{
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
			//d.findElement(Property.CompanyIconL2).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.CompanyIconL2);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();	
		}
		
		}catch (Exception e){
			ErrorNumber = ErrorNumber +1;
			captureScreenshot();
	    	e.printStackTrace();
	      }
	}
	
	public static void PostageSetting(String Postage)
			throws InterruptedException {
		try{
			 WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
			 MouseAdjFunction();
			 FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
			 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
			 waitfl.pollingEvery(1, TimeUnit.SECONDS);
			 waitfl.ignoring(NoSuchElementException.class);
			 waitfl.ignoring(StaleElementReferenceException.class);
			 
			if(Config.LayoutType.equals("Classic"))
			{
				tec.AC40.Common.Wait.wait2Second();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
				 //d.findElement(Property.CompanyLink).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyLink);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();
				 d.findElement(Property.CompanyLink).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ManageOrgunitIcon));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ManageOrgunitIcon));
				// d.findElement(Property.ManageOrgunitIcon).isDisplayed();
		  		
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ManageOrgunitIcon);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();
		  		d.findElement(Property.ManageOrgunitIcon).click();
		  		tec.AC40.Common.Wait.wait5Second();
			}
			else if(Config.LayoutType.equals("Layout1"))
			{
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.CompanyIconL1));
				//waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
				//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
				//d.findElement(Property.CompanyIconL1).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL1);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.CompanyIconL1).click();
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.ManageOrguntiIconL1));
				//waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ManageOrguntiIconL1));
				//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ManageOrguntiIconL1));
				//d.findElement(Property.ManageOrguntiIconL1).isDisplayed();
			  	//tec.AC40.Common.Wait.wait2Second();
			  	
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ManageOrguntiIconL1);
						 }
						 });
				
				d.findElement(Property.ManageOrguntiIconL1).click();
			  	tec.AC40.Common.Wait.wait10Second();
			}
			else if(Config.LayoutType.equals("Layout2"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
				//d.findElement(Property.CompanyIconL2).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.CompanyIconL2).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ManageOrguntiIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ManageOrguntiIconL2));
				//d.findElement(Property.ManageOrguntiIconL2).isDisplayed();
		  		
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ManageOrguntiIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();
		  		d.findElement(Property.ManageOrguntiIconL2).click();
		  		tec.AC40.Common.Wait.wait2Second();
			}
			Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
			//d.findElement(Property.Miscellaneoustab).isDisplayed();
			tec.AC40.Common.Wait.wait10Second();
			Wait.Fluentwait(Property.Miscellaneoustab);
			d.findElement(Property.Miscellaneoustab).click();
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.PostagePricingLink));
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PostagePricingLink));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PostagePricingLink));
			//d.findElement(Property.PostagePricingLink).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PostagePricingLink);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PostagePricingLink).click();
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.PostagSearch));
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PostagSearch));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PostagSearch));
			//d.findElement(Property.PostagSearch).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PostagSearch);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PostagSearch).sendKeys(Config.PostageName);
			tec.AC40.Common.Wait.wait5Second();
			d.findElement(Property.PostageEditLink).click();
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.PostagePrice));
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PostagePrice));
			//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PostagePrice));
			//waitfl.until(ExpectedConditions.elementToBeClickable(Property.PostagePrice));
			//d.findElement(Property.PostagePrice).isDisplayed();
			//tec.AC40.Common.Wait.wait5Second();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PostagePrice);
					 }
					 });
			
			for(int i=1; i<=1; i++)
			{
				//d.findElement(Property.PostagePriceDownArrow).click();
				//d.findElement(Property.PostagePrice).sendKeys(Keys.ENTER, "\b");
				d.findElement(Property.PostagePrice).click();
				Thread.sleep(1000);
				kb.sendKeys(Keys.chord(Keys.CONTROL, "a"));
				Thread.sleep(2000);
				kb.pressKey(Keys.DELETE);
				Thread.sleep(500);
				d.findElement(Property.PostageName).click();
			}
			//d.findElement(Property.PostageName).click();
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PostagePrice).click();
			Thread.sleep(500);
			kb.pressKey(Keys.END);
			Thread.sleep(500);
			kb.pressKey(Keys.BACK_SPACE);
			Thread.sleep(500);
			d.findElement(Property.PostageName).click();
			Thread.sleep(500);
			d.findElement(Property.PostagePrice).click();
			Thread.sleep(500);
			kb.sendKeys(Postage);
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PostagePriceUpdate).click();
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.PostagepriceSuccessMsg));
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PostagepriceSuccessMsg);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			Wait.Fluentwait(Property.AdminHomeLink);
			d.findElement(Property.AdminHomeLink).click();
			if(Config.LayoutType.equals("Classic"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
				//d.findElement(Property.CompanyLink).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyLink);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
			}
			else if(Config.LayoutType.equals("Layout1"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
				//d.findElement(Property.CompanyIconL1).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL1);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
			}
			else if(Config.LayoutType.equals("Layout2"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
				//d.findElement(Property.CompanyIconL2).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
			}
				}catch (Exception e){
					ErrorNumber = ErrorNumber +1;
					captureScreenshot();
					e.printStackTrace();
	      }
	}
	
	 public static void IsTaxExemptSettings(String IsTaxExempt,String ProdutType) throws InterruptedException{

			try{
				// WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
				MouseAdjFunction();
				 FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
				 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
				 waitfl.pollingEvery(1, TimeUnit.SECONDS);
				 waitfl.ignoring(NoSuchElementException.class);
				 waitfl.ignoring(StaleElementReferenceException.class);
				 
				if(Config.LayoutType.equals("Classic"))
				{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsLink));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsLink));
			   // d.findElement(Property.ProductsLink).isDisplayed();
			    
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductsLink);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.ProductsLink).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ManageProductsIcon));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ManageProductsIcon));
				//d.findElement(Property.ManageProductsIcon).isDisplayed();
		  		
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ManageProductsIcon);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.ManageProductsIcon).click();
				}
				else if(Config.LayoutType.equals("Layout1"))
				{
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsIconL1));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsIconL1));
					//d.findElement(Property.ProductsIconL1).isDisplayed();
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.ProductsIconL1);
							 }
							 });

					tec.AC40.Common.Wait.wait2Second();	
					d.findElement(Property.ProductsIconL1).click();
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ManageProductsIconL1));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ManageProductsIconL1));
					//d.findElement(Property.ManageProductsIconL1).isDisplayed();
			  		
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.ManageProductsIconL1);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
					d.findElement(Property.ManageProductsIconL1).click();
				}
				else if(Config.LayoutType.equals("Layout2"))
				{
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsIconL2));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsIconL2));
					//d.findElement(Property.ProductsIconL2).isDisplayed();
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.ProductsIconL2);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
					d.findElement(Property.ProductsIconL2).click();
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ManageProductsIconL2));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ManageProductsIconL2));
					//d.findElement(Property.ManageProductsIconL2).isDisplayed();
			  		
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.ManageProductsIconL2);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
					d.findElement(Property.ManageProductsIconL2).click();
				}
				Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsSearchBox));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsSearchBox));
				//d.findElement(Property.ProductsSearchBox).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductsSearchBox);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.ProductsSearchBox).sendKeys(ProdutType);
				tec.AC40.Common.Wait.wait5Second();
				d.findElement(Property.EditLink).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductOffline));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductOffline));
				//d.findElement(Property.ShippingEditlink).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductOffline);
						 }
						 });
				
				tec.AC40.Common.Wait.wait10Second();
				tec.AC40.Common.Wait.wait10Second();
				
				String ProductStatus = d.findElement(Property.ProductStatus).getText();
				//System.out.println("ProductStatus :"+ProductStatus);
				if(ProductStatus.equals("ACTIVE"))
				{
				d.findElement(Property.ProductOffline).click();
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.ProductAlertOK).click();
				tec.AC40.Common.Wait.wait5Second();
				}
				for(int i=1; i<= 1; i++)
				{
				boolean taxexmpt=d.findElement(Property.TaxExemptCheckBoxStatic).isSelected();

						if(IsTaxExempt.equalsIgnoreCase("YES")){
							if(taxexmpt==true){
								//System.out.println("******do nothing********");
							}else{
								d.findElement(Property.TaxExemptCheckBoxStatic).click();
							}
					
			            }else if(IsTaxExempt.equalsIgnoreCase("NO")){
							if(taxexmpt==true){
								//System.out.println("******click on check box****");
								d.findElement(Property.TaxExemptCheckBoxStatic).click();
							}else{
								//System.out.println("do nothing");							
								}
			            }
						
				}
		
				
				d.findElement(Property.ProductInfoSave).click();
				tec.AC40.Common.Wait.wait5Second();
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.ProductInfoSuccessMsg));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductInfoSuccessMsg));
				//d.findElement(Property.ShipPriceSaveSuccessMsg).isDisplayed();
				//d.findElement(Property.ShipPriceSaveSuccessMsg).isEnabled();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductInfoSuccessMsg);
						 }
						 });

				
				tec.AC40.Common.Wait.wait5Second();
				d.findElement(Property.ProductOnlineLink).click();
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.ProductAlertOK).click();
				tec.AC40.Common.Wait.wait10Second();
				Wait.Fluentwait(Property.AdminHomeLink);
				 d.findElement(Property.AdminHomeLink).click();
				 if(Config.LayoutType.equals("Classic"))
				 {
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
				 	//d.findElement(Property.CompanyLink).isDisplayed();
				 	
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.CompanyLink);
							 }
							 });

					
					tec.AC40.Common.Wait.wait2Second();	
				 }
				 else if(Config.LayoutType.equals("Layout1"))
				 {
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
				 	//d.findElement(Property.CompanyIconL1).isDisplayed();
				 	
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.CompanyIconL1);
							 }
							 });

					
					tec.AC40.Common.Wait.wait2Second();	
				 }
				 else if(Config.LayoutType.equals("Layout2"))
				 {
					 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
					 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
				 	//d.findElement(Property.CompanyIconL2).isDisplayed();
				 	
					 waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.CompanyIconL2);
							 }
							 });

					 
					 tec.AC40.Common.Wait.wait2Second();	
				 }
			}catch (Exception e){
				ErrorNumber = ErrorNumber +1;
				captureScreenshot();
		    	  e.printStackTrace();
		      }
		
		  
	 }
	public static void CaptureCoOpfundDetails(String PaymentSubOpt)
			throws InterruptedException {
		try{
			 WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
			 MouseAdjFunction();
			 FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
			 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
			 waitfl.pollingEvery(1, TimeUnit.SECONDS);
			 waitfl.ignoring(NoSuchElementException.class);
			 waitfl.ignoring(StaleElementReferenceException.class);
			 
			if(Config.LayoutType.equals("Classic"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.SecurityLink));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.SecurityLink));
				 //d.findElement(Property.CompanyLink).isDisplayed();
				 tec.AC40.Common.Wait.wait2Second();
				 d.findElement(Property.SecurityLink).click();
				 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UsersIconC));
				 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.UsersIconC));
				// d.findElement(Property.ManageOrgunitIcon).isDisplayed();
		  		tec.AC40.Common.Wait.wait2Second();
		  		d.findElement(Property.UsersIconC).click();
		  		tec.AC40.Common.Wait.wait5Second();
			}
			else if(Config.LayoutType.equals("Layout1"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.SecurityLinkL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.SecurityLinkL1));
				//d.findElement(Property.CompanyIconL1).isDisplayed();
				tec.AC40.Common.Wait.wait5Second();
				d.findElement(Property.SecurityLinkL1).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserLinkL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.UserLinkL1));
				//d.findElement(Property.ManageOrguntiIconL1).isDisplayed();
			  	tec.AC40.Common.Wait.wait5Second();
			  	d.findElement(Property.UserLinkL1).click();
			  	tec.AC40.Common.Wait.wait2Second();
			}
			else if(Config.LayoutType.equals("Layout2"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.SecurityLinkL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.SecurityLinkL2));
				//d.findElement(Property.CompanyIconL2).isDisplayed();
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.SecurityLinkL2).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserLinkL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.UserLinkL2));
				//d.findElement(Property.ManageOrguntiIconL2).isDisplayed();
		  		tec.AC40.Common.Wait.wait2Second();
		  		d.findElement(Property.UserLinkL2).click();
		  		tec.AC40.Common.Wait.wait2Second();
		  	//	System.out.println("Layout2 users icon clicked successfully");
			}
			//org.openqa.selenium.Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
			if(Config.LayoutType.equals("Classic"))
			{
				d.findElement(Property.UsersSearchTextBox).sendKeys(Config.UserName);
			}
			else if(Config.LayoutType.equals("Layout1"))
			{
				Wait.Fluentwait(Property.UsersSearchTextBox);
				d.findElement(Property.UsersSearchTextBox).sendKeys(Config.UserNamel1);
			}
			else if(Config.LayoutType.equals("Layout2"))
			{
				d.findElement(Property.UsersSearchTextBox).sendKeys(Config.UserNamel2);
			}
			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.UserCoopFund).click();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.AddCoopFundButton));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.AddCoopFundButton));
			tec.AC40.Common.Wait.wait2Second();
			
			if(PaymentSubOpt.equals("FIFO"))
			{
				TotalFunds1 = d.findElement(Property.TotalFunds).getText();
				CoopFundused1 = d.findElement(Property.CoopFundsUsed).getText();
				AvailableFunds1 = d.findElement(Property.AvailableFunds).getText();
			}
			else 
			{
				TotalFunds1 = d.findElement(Property.TotalFunds2).getText();
				CoopFundused1 = d.findElement(Property.CoopFundsUsed2).getText();
				AvailableFunds1 = d.findElement(Property.AvailableFunds2).getText();
			}
			//System.out.println("TotalFunds1 :"+TotalFunds1);
			//System.out.println("CoopFundused1 :"+CoopFundused1);
			//System.out.println("AvailableFunds1 :"+AvailableFunds1);
			tec.AC40.Common.Wait.wait2Second();
			
			
			if(Config.LayoutType.equals("Classic"))
			{
				d.findElement(Property.AdminHomeLink).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
				//d.findElement(Property.CompanyLink).isDisplayed();
				tec.AC40.Common.Wait.wait2Second();	
			}
			else if(Config.LayoutType.equals("Layout1"))
			{
				d.findElement(Property.HomeLinkL1).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
				//d.findElement(Property.CompanyIconL1).isDisplayed();
				tec.AC40.Common.Wait.wait2Second();	
			}
			else if(Config.LayoutType.equals("Layout2"))
			{
				d.findElement(Property.HomeLinkL2).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
				//d.findElement(Property.CompanyIconL2).isDisplayed();
				tec.AC40.Common.Wait.wait2Second();	
			}
				}catch (Exception e){
					ErrorNumber = ErrorNumber +1;
					captureScreenshot();
					e.printStackTrace();
	      }
	}
	
	public static void CompareCOopFundDetails(String PaymentSubOpt, String Total, String DecimalValue)
			throws InterruptedException {
		try{
			 WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
			 MouseAdjFunction();
			 FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
			 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
			 waitfl.pollingEvery(1, TimeUnit.SECONDS);
			 waitfl.ignoring(NoSuchElementException.class);
			 waitfl.ignoring(StaleElementReferenceException.class);
				if(Config.LayoutType.equals("Classic"))
				{
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.SecurityLink));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.SecurityLink));
					 //d.findElement(Property.CompanyLink).isDisplayed();
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.SecurityLink);
							 }
							 });
					 tec.AC40.Common.Wait.wait2Second();
					 d.findElement(Property.SecurityLink).click();
					 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UsersIconC));
					 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.UsersIconC));
					// d.findElement(Property.ManageOrgunitIcon).isDisplayed();
			  		tec.AC40.Common.Wait.wait2Second();
			  		d.findElement(Property.UsersIconC).click();
			  		tec.AC40.Common.Wait.wait5Second();
				}
				else if(Config.LayoutType.equals("Layout1"))
				{
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.SecurityLinkL1));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.SecurityLinkL1));
					//d.findElement(Property.CompanyIconL1).isDisplayed();
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.SecurityLinkL1);
							 }
							 });
					tec.AC40.Common.Wait.wait2Second();
					d.findElement(Property.SecurityLinkL1).click();
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserLinkL1));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.UserLinkL1));
					//d.findElement(Property.ManageOrguntiIconL1).isDisplayed();
				  	tec.AC40.Common.Wait.wait2Second();
				  	d.findElement(Property.UserLinkL1).click();
				  	tec.AC40.Common.Wait.wait2Second();
				}
				else if(Config.LayoutType.equals("Layout2"))
				{
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.SecurityLinkL2));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.SecurityLinkL2));
					//d.findElement(Property.CompanyIconL2).isDisplayed();
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.SecurityLinkL2);
							 }
							 });
					tec.AC40.Common.Wait.wait2Second();
					d.findElement(Property.SecurityLinkL2).click();
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.UserLinkL2));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.UserLinkL2));
					//d.findElement(Property.ManageOrguntiIconL2).isDisplayed();
			  		tec.AC40.Common.Wait.wait2Second();
			  		d.findElement(Property.UserLinkL2).click();
			  		tec.AC40.Common.Wait.wait2Second();
			  		System.out.println("Layout2 users icon clicked successfully");
				}
				//org.openqa.selenium.Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
				if(Config.LayoutType.equals("Classic"))
				{
					d.findElement(Property.UsersSearchTextBox).sendKeys(Config.UserName);
				}
				else if(Config.LayoutType.equals("Layout1"))
				{
					d.findElement(Property.UsersSearchTextBox).sendKeys(Config.UserNamel1);
				}
				else if(Config.LayoutType.equals("Layout2"))
				{
					d.findElement(Property.UsersSearchTextBox).sendKeys(Config.UserNamel2);
				}
				
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.UserCoopFund).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.AddCoopFundButton));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.AddCoopFundButton));
				tec.AC40.Common.Wait.wait2Second();
				
				if(PaymentSubOpt.equals("FIFO"))
				{
					ActualTotalFunds2 = d.findElement(Property.TotalFunds).getText();
					ActualCoopFundused2 = d.findElement(Property.CoopFundsUsed).getText();
					ActualAvailableFunds2 = d.findElement(Property.AvailableFunds).getText();
				}
				else 
				{
					ActualTotalFunds2 = d.findElement(Property.TotalFunds2).getText();
					ActualCoopFundused2 = d.findElement(Property.CoopFundsUsed2).getText();
					ActualAvailableFunds2 = d.findElement(Property.AvailableFunds2).getText();
				}
				//System.out.println("ActualTotalFunds2 :"+ActualTotalFunds2);
				//System.out.println("ActualCoopFundused2 :"+ActualCoopFundused2);
				//System.out.println("ActualAvailableFunds2 :"+ActualAvailableFunds2);
				tec.AC40.Common.Wait.wait2Second();
				
				String ExpectedTotalFund = TotalFunds1;
				
				String[] OldCoopFundUsed = CoopFundused1.split("\\.");
				
				String[] OldAvailableFund = AvailableFunds1.split("\\.");
				
				String[] OrderTotal = Total.split("\\.");
				
				Number OldCoopFundUsedValue = NumberFormat.getNumberInstance(java.util.Locale.US).parse(OldCoopFundUsed[0]);
				Number OldAvailableFundValue = NumberFormat.getNumberInstance(java.util.Locale.US).parse(OldAvailableFund[0]);
				Number OrderTotalValue = NumberFormat.getNumberInstance(java.util.Locale.US).parse(OrderTotal[0]);
				
				String OldCoopFundUsedValue2 = OldCoopFundUsedValue +"."+OldCoopFundUsed[1];
				String OldAvailableFundValue2 = OldAvailableFundValue +"."+OldAvailableFund[1];
				String OrderTotalValue2 = OrderTotalValue +"."+OrderTotal[1];
				
				
				
				
				double OldCoopFundUsedValue3 = Double.valueOf(OldCoopFundUsedValue2).doubleValue();
				double OldAvailableFundValue3 = Double.valueOf(OldAvailableFundValue2).doubleValue();
				double OrderTotalValue3 = Double.valueOf(OrderTotalValue2).doubleValue();
				
				//System.out.println("OldCoopFundUsedValue3 :"+OldCoopFundUsedValue3);
				//System.out.println("OldAvailableFundValue3 :"+OldAvailableFundValue3);
				//System.out.println("OrderTotalValue3 :"+OrderTotalValue3);
				
				double ExpectedCoopFundUsed= OldCoopFundUsedValue3 + OrderTotalValue3;
				double ExpectedAvailableFunds= OldAvailableFundValue3 - OrderTotalValue3;
				
				String ExpectedCoopFundUsed3a= ""+ExpectedCoopFundUsed;
				String ExpectedAvailableFunds3a= ""+ExpectedAvailableFunds;
				
				String ExpectedCoopFundUsed3b = Decimalsetting(ExpectedCoopFundUsed3a, DecimalValue);
				
				String ExpectedAvailableFunds3b = Decimalsetting(ExpectedAvailableFunds3a, DecimalValue);
				
				
				//System.out.println("ExpectedCoopFundUsed3b :"+ExpectedCoopFundUsed3b);
				//System.out.println("ExpectedAvailableFunds3b :"+ExpectedAvailableFunds3b);
				
				String[] ExpectedCoopFundUsed3c = ExpectedCoopFundUsed3b.split("\\.");
				String[] ExpectedAvailableFunds3c = ExpectedAvailableFunds3b.split("\\.");
				
				double ExpectedCoopFundUsed3d = Double.parseDouble(ExpectedCoopFundUsed3b);
				double ExpectedAvailableFunds3d = Double.parseDouble(ExpectedAvailableFunds3b);
				
				NumberFormat format = NumberFormat.getNumberInstance();
				String ExpectedCoopFundUsed2 = format.format(ExpectedCoopFundUsed3d);
				String ExpectedAvailableFunds2 = format.format(ExpectedAvailableFunds3d);
				
				
				String[] ExpectedCoopFundUsed2a = ExpectedCoopFundUsed2.split("\\.");
				String[] ExpectedAvailableFunds2a = ExpectedAvailableFunds2.split("\\.");
				
				String ExpectedCoopFundUsed2ab = ExpectedCoopFundUsed2a[0]+"."+ExpectedCoopFundUsed3c[1];
				String ExpectedAvailableFunds2ab =ExpectedAvailableFunds2a[0]+"."+ExpectedAvailableFunds3c[1];
				
				//System.out.println("ExpectedCoopFundUsed2ab :"+ExpectedCoopFundUsed2ab);
				//System.out.println("ExpectedAvailableFunds2ab :"+ExpectedAvailableFunds2ab);
				
				String PageName = "Admin Coop Fund";
				if(ExpectedTotalFund.equals(ActualTotalFunds2))
				{
					//!System.out.println("Both Total funds are same");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "Total Fund";
					System.out.println("<----------- Both Total Fund amounts are different ------------>"+ErrorNumber);
					System.out.println("ActualTotalFunds2 : "+ActualTotalFunds2);
					System.out.println("ExpectedTotalFund : "+ExpectedTotalFund);
					RW_File.WriteResult(ExpectedTotalFund, ActualTotalFunds2, PageName, PriceType);
				
				}
				
				if(ExpectedCoopFundUsed2ab.equals(ActualCoopFundused2))
				{
					//!System.out.println("Both CoopFunds used values are same");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "TaxAmount";
					System.out.println("<----------- Both CoopFund used amounts are different ------------>"+ErrorNumber);
					System.out.println("ActualCoopFundused2 : "+ActualCoopFundused2);
					System.out.println("ExpectedCoopFundUsed2ab : "+ExpectedCoopFundUsed2ab);
					RW_File.WriteResult(ExpectedCoopFundUsed2ab, ActualCoopFundused2, PageName, PriceType);
				
				}
				
				if(ExpectedAvailableFunds2ab.equals(ActualAvailableFunds2))
				{
					//!System.out.println("Both Available Funds are same");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "TaxAmount";
					System.out.println("<----------- Both Avaible fund amounts are different ------------>"+ErrorNumber);
					System.out.println("ActualAvailableFunds2 : "+ActualAvailableFunds2);
					System.out.println("ExpectedAvailableFunds2ab : "+ExpectedAvailableFunds2ab);
					RW_File.WriteResult(ExpectedAvailableFunds2ab, ActualAvailableFunds2, PageName, PriceType);
				}
				
			tec.AC40.Common.Wait.wait2Second();

			if(Config.LayoutType.equals("Classic"))
			{
				Wait.Fluentwait(Property.AdminHomeLink);
				d.findElement(Property.AdminHomeLink).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
				waitfl.until(new Function<WebDriver, WebElement>() 
 	    				 {
 	    				   public WebElement apply(WebDriver driver) {
 	    				   return driver.findElement(Property.CompanyLink);
 	    				 }
 	    				 });

				//d.findElement(Property.CompanyLink).isDisplayed();
				tec.AC40.Common.Wait.wait2Second();	
			}
			else if(Config.LayoutType.equals("Layout1"))
			{
				d.findElement(Property.HomeLinkL1).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
				waitfl.until(new Function<WebDriver, WebElement>() 
 	    				 {
 	    				   public WebElement apply(WebDriver driver) {
 	    				   return driver.findElement(Property.CompanyIconL1);
 	    				 }
 	    				 });

				//d.findElement(Property.CompanyIconL1).isDisplayed();
				tec.AC40.Common.Wait.wait2Second();	
			}
			else if(Config.LayoutType.equals("Layout2"))
			{
				d.findElement(Property.HomeLinkL2).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
				waitfl.until(new Function<WebDriver, WebElement>() 
 	    				 {
 	    				   public WebElement apply(WebDriver driver) {
 	    				   return driver.findElement(Property.CompanyIconL2);
 	    				 }
 	    				 });

				//d.findElement(Property.CompanyIconL2).isDisplayed();
				tec.AC40.Common.Wait.wait2Second();	
			}
				}catch (Exception e){
					ErrorNumber = ErrorNumber +1;
					captureScreenshot();
					e.printStackTrace();
	      }
	}
	
public static void OrderBaseShippingSetting(String OrderBase, String TestStep, String WeightPerPackage)
			throws InterruptedException , AWTException{
		try{
			MouseAdjFunction();		
	    	  Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
			  String SelectedText ;
			  SelectedText = d.findElement(Property.shippingbasis).getText();
			  String[] SelectedBaseType = SelectedText.split("\n");
			  //System.out.println("Actual :"+SelectedBaseType[0]);
			 
			  if(OrderBase.equals("Item"))
			  {
				  d.findElement(Property.shippingbasis).click();
				  tec.AC40.Common.Wait.wait2Second();	
				  kb.sendKeys("Item");
				  kb.pressKey(Keys.ENTER);
				   //d.findElement(By.xpath("//ul[@id='ddlShippingBasis_listbox']/li[3]")).click();
			  }
			  else if(OrderBase.equals("Order"))
			  {
				  d.findElement(Property.shippingbasis).click();
				  tec.AC40.Common.Wait.wait2Second();	
				  kb.sendKeys("Order");
				  kb.pressKey(Keys.ENTER);
				//  d.findElement(By.xpath("//ul[@id='ddlShippingBasis_listbox']/li[2]")).click();
				  tec.AC40.Common.Wait.wait2Second();	
				  OrderBaseShippingpriceSetting(TestStep,OrderBase,WeightPerPackage);
			  }else{

				  d.findElement(Property.shippingbasis).click();
				  tec.AC40.Common.Wait.wait2Second();	
				  kb.sendKeys("Split Ship");
				  kb.pressKey(Keys.ENTER);
				//  d.findElement(By.xpath("//ul[@id='ddlShippingBasis_listbox']/li[2]")).click();
				  tec.AC40.Common.Wait.wait2Second();	
				  OrderBaseShippingpriceSetting(TestStep,OrderBase,WeightPerPackage);
			  
			  }
			 
			 
			 tec.AC40.Common.Wait.wait2Second();	
			
				}catch (Exception e){
					ErrorNumber = ErrorNumber +1;
					captureScreenshot();
					e.printStackTrace();
	      }
	}
	
public static void OrderBaseShippingpriceSetting(String TestStep,String OrderBase, String WeightPerPackage)
		throws InterruptedException , AWTException{
	try{
		MouseAdjFunction();									
		  String SelectedText ;
		  String TestStepvalue = TestStep;
		  
		//  int TestStepvalue1 = Double.valueOf(TestStepvalue).intValue();
		  if(!WeightPerPackage.equals("UnCheck"))
		  {
			  String[] WeightPerpackageOptions =WeightPerPackage.split("_"); 
			  d.findElement(Property.Shippingpicebasis).click();
			  tec.AC40.Common.Wait.wait5Second();	
			  if(WeightPerpackageOptions[1].equals("Individual"))
			  {
				  try{
				  d.findElement(Property.ShippingpicebasisIndividual).click();
				  }
				  catch(Exception e)
				  {
					  d.findElement(Property.ShippingpicebasisIndividual2).click(); 
				  }
			  }
			  else
			  {
				  try{
				  d.findElement(Property.ShippingpicebasisConsolidated).click();
				  }
				  catch(Exception e)
				  {
					  d.findElement(Property.ShippingpicebasisConsolidated2).click();
				  }
				  tec.AC40.Common.Wait.wait2Second();	
				  d.findElement(Property.Defaultlocation).click();
				  tec.AC40.Common.Wait.wait2Second();	
				  Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
				  kb.pressKey(Keys.DOWN);
				  kb.pressKey(Keys.ENTER);
				  tec.AC40.Common.Wait.wait2Second();
				  d.findElement(Property.DefaultPackageWeight).click();
				  tec.AC40.Common.Wait.wait2Second();
				  kb.sendKeys(WeightPerpackageOptions[2]);
				  kb.pressKey(Keys.ENTER);
			  }
		  }
		  else
		  {
		  SelectedText = d.findElement(Property.Shippingpicebasis).getText();
		  String[] OrderPriceBasis = SelectedText.split("\n");
		  //System.out.println(OrderPriceBasis[0]);
		  if(!OrderPriceBasis[0].equals("Individual"))
		  {
		  d.findElement(Property.Shippingpicebasis).click();
		  Thread.sleep(2000);
		  
		  Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
		  kb.pressKey(Keys.DOWN);
		  kb.pressKey(Keys.ENTER);
		  Thread.sleep(2000);
		  }
		  
		  
		  
		  }
		
			}catch (Exception e){
				ErrorNumber = ErrorNumber +1;
				captureScreenshot();
				e.printStackTrace();
      }
}
	public static void CouponCodePrice(String PromotionDiscountPercentage, String PromotionCoupon)
			throws InterruptedException {
		try{
			 WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
			 MouseAdjFunction();
			 FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
			 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
			 waitfl.pollingEvery(1, TimeUnit.SECONDS);
			 waitfl.ignoring(NoSuchElementException.class);
			 waitfl.ignoring(StaleElementReferenceException.class);
			 
			if(Config.LayoutType.equals("Classic"))
			{
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsLink));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsLink));
			//d.findElement(Property.ProductsLink).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ProductsLink);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.ProductsLink).click();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PromotionsIcon));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PromotionsIcon));
			//d.findElement(Property.PromotionsIcon).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PromotionsIcon);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PromotionsIcon).click();
			}
			else if(Config.LayoutType.equals("Layout1"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsIconL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsIconL1));
				//d.findElement(Property.ProductsIconL1).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductsIconL1);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.ProductsIconL1).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PromotionsIconL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PromotionsIconL1));
				//d.findElement(Property.PromotionsIconL1).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.PromotionsIconL1);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.PromotionsIconL1).click();
			}
			else if(Config.LayoutType.equals("Layout2"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductsIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductsIconL2));
				//d.findElement(Property.ProductsIconL2).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductsIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.ProductsIconL2).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PromotionsIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PromotionsIconL2));
				//d.findElement(Property.PromotionsIconL2).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.PromotionsIconL2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.PromotionsIconL2).click();
			}
			Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PromotionsSearch));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PromotionsSearch));
			//d.findElement(Property.PromotionsSearch).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PromotionsSearch);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PromotionsSearch).sendKeys(Config.CouponCodeName);
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PromotionsEdit).click();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PromotionDiscountNO));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PromotionDiscountNO));
			//d.findElement(Property.PromotionDiscountNO).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PromotionDiscountNO);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			if(PromotionDiscountPercentage.equals("N"))
			{
				d.findElement(Property.PromotionDiscountNO).click();
			}
			else
			{
				d.findElement(Property.PromotionDisocuntYes).click();
			}
			
			if(PromotionCoupon.equals("0") || PromotionCoupon.equals("0.00") || PromotionCoupon.equals("0.000") || PromotionCoupon.equals("0.0000"))
			{
				d.findElement(Property.PromotionDiscountDeactive).click();
			}
			else
			{
			for(int i=1; i<=1; i++)
			{
			//d.findElement(Property.PromotionCouponDownArrow).click();
				//d.findElement(Property.PromotionCouponValue).sendKeys(Keys.ENTER, "\b");
				d.findElement(Property.PromotionCouponValue).click();
				Thread.sleep(1000);
				kb.sendKeys(Keys.chord(Keys.CONTROL, "a"));
				Thread.sleep(2000);
				kb.pressKey(Keys.DELETE);

				Thread.sleep(500);
				d.findElement(Property.PromotionCouponName).click();
			}
			//d.findElement(Property.PromotionCouponName).click();
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PromotionCouponValue).click();
			Thread.sleep(500);
			kb.sendKeys(PromotionCoupon);
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.PromotionDisocuntActive).click();
			}
			d.findElement(Property.PromotionSave).click();
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.PromotionSuccessMsg));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PromotionSuccessMsg));
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.PromotionSuccessMsg);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			Wait.Fluentwait(Property.AdminHomeLink);
			d.findElement(Property.AdminHomeLink).click();
			if(Config.LayoutType.equals("Classic"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
				//d.findElement(Property.CompanyLink).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyLink);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
			}
			else if(Config.LayoutType.equals("Layout1"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
				//d.findElement(Property.CompanyIconL1).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL1);
						 }
						 });			
				tec.AC40.Common.Wait.wait2Second();	
			}
			else if(Config.LayoutType.equals("Layout2"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
				//d.findElement(Property.CompanyIconL2).isDisplayed();			
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL2);
						 }
						 });
				tec.AC40.Common.Wait.wait2Second();	
			}
			}catch (Exception e){
				ErrorNumber = ErrorNumber +1;
				captureScreenshot();
				e.printStackTrace();
	      }
		
	}	
	public static void TaxSettings(String Tax)
			throws InterruptedException {
		try{
			 WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
			 MouseAdjFunction();
			 FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
			 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
			 waitfl.pollingEvery(1, TimeUnit.SECONDS);
			 waitfl.ignoring(NoSuchElementException.class);
			 waitfl.ignoring(StaleElementReferenceException.class);
			 
			if(Config.LayoutType.equals("Classic"))
			{
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
			//d.findElement(Property.CompanyLink).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.CompanyLink);
					 }
					 });

			
			tec.AC40.Common.Wait.wait2Second();	
			d.findElement(Property.CompanyLink).click();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.TaxesIcon));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.TaxesIcon));
			//d.findElement(Property.TaxesIcon).isDisplayed();
		    
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.TaxesIcon);
					 }
					 });

			
			tec.AC40.Common.Wait.wait2Second();	
			d.findElement(Property.TaxesIcon).click();
			}
			else if(Config.LayoutType.equals("Layout1"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
				//d.findElement(Property.CompanyIconL1).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL1);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.CompanyIconL1).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.TaxIconL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.TaxIconL1));
				//d.findElement(Property.TaxIconL1).isDisplayed();
		  		
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.TaxIconL1);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();	
		  		d.findElement(Property.TaxIconL1).click();
			}
			else if(Config.LayoutType.equals("Layout2"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
				//d.findElement(Property.CompanyIconL2).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL2);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();	
				d.findElement(Property.CompanyIconL2).click();
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.TaxIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.TaxIconL2));
				//d.findElement(Property.TaxIconL2).isDisplayed();
		  		
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.TaxIconL2);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();	
		  		d.findElement(Property.TaxIconL2).click();
			}
			Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.TaxEditLink));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.TaxEditLink));
			//d.findElement(Property.TaxEditLink).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.TaxEditLink);
					 }
					 });

			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.TaxEditLink).click();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.TaxValue));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.TaxValue));
			//d.findElement(Property.TaxValue).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.TaxValue);
					 }
					 });

			
			tec.AC40.Common.Wait.wait2Second();
			for(int i=0; i<=1; i++)
			{
			//d.findElement(Property.TaxValue).sendKeys(Keys.ENTER, "\b");
				d.findElement(Property.TaxValue).click();
				Thread.sleep(1000);
				kb.sendKeys(Keys.chord(Keys.CONTROL, "a"));
				Thread.sleep(2000);
				kb.pressKey(Keys.DELETE);
			Thread.sleep(500);
			d.findElement(Property.TaxCountry).click();
			}
			d.findElement(Property.TaxValue).click();
			Thread.sleep(500);
			kb.sendKeys(Tax);
			d.findElement(Property.TaxSaveButton).click();
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.TaxSaveSuccessMsg));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.TaxSaveSuccessMsg));
			//d.findElement(Property.TaxSaveSuccessMsg).isDisplayed();
			//d.findElement(Property.TaxSaveSuccessMsg).isEnabled();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.TaxSaveSuccessMsg);
					 }
					 });

			
			tec.AC40.Common.Wait.wait2Second();
			Wait.Fluentwait(Property.AdminHomeLink);
			d.findElement(Property.AdminHomeLink).click();
			if(Config.LayoutType.equals("Classic"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
				//d.findElement(Property.CompanyLink).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyLink);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();	
			}
			else if(Config.LayoutType.equals("Layout1"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
				//d.findElement(Property.CompanyIconL1).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL1);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();	
			}
			else if(Config.LayoutType.equals("Layout2"))
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
				//d.findElement(Property.CompanyIconL2).isDisplayed();
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.CompanyIconL2);
						 }
						 });

				
				tec.AC40.Common.Wait.wait2Second();	
			}
			}catch (Exception e){
				ErrorNumber = ErrorNumber +1;
				captureScreenshot();
				e.printStackTrace();
	      }		
	}
	public static void fullfillmentdetails(String FullfilmentShippingOrHandlingFee,String FullfilmentShippingMarkupFee,
			String FullfilmentShippingOrHandlingFeeTypeAmountPercent,String FullfilmentShippingMarkupFeeAmountPercentTypeAmount)
			throws InterruptedException{
		try{
			 WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
			 MouseAdjFunction();
			 FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
			 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
			 waitfl.pollingEvery(1, TimeUnit.SECONDS);
			 waitfl.ignoring(NoSuchElementException.class);
			 waitfl.ignoring(StaleElementReferenceException.class);
			 
			 tec.AC40.Common.Wait.wait2Second();
			 if(Config.LayoutType.equals("Classic"))
				{
				    tec.AC40.Common.Wait.wait2Second();	
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.CompanyLink);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
					d.findElement(Property.CompanyLink).click();
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.FulfillmentLocationsIcon));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.FulfillmentLocationsIcon));
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.FulfillmentLocationsIcon);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
					d.findElement(Property.FulfillmentLocationsIcon).click();
					tec.AC40.Common.Wait.wait2Second();	
				}
				else if(Config.LayoutType.equals("Layout1"))
				{
					tec.AC40.Common.Wait.wait2Second();	
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.CompanyIconL1);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
					//org.openqa.selenium.Keyboard kb = (Keyboard) ((RemoteWebDriver) d).getKeyboard();
					d.findElement(Property.CompanyIconL1).click();
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.FulfillmentLocationsIconl1));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.FulfillmentLocationsIconl1));
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.FulfillmentLocationsIconl1);
							 }
							 });
					
					d.findElement(Property.FulfillmentLocationsIconl1).click();
					tec.AC40.Common.Wait.wait2Second();		
				}
				else if(Config.LayoutType.equals("Layout2"))
				{
					tec.AC40.Common.Wait.wait2Second();	
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.CompanyIconL2);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
					d.findElement(Property.CompanyIconL2).click();
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.FullfilmentlocationL2));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.FullfilmentlocationL2));
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.FullfilmentlocationL2);
							 }
							 });
					
					d.findElement(Property.FullfilmentlocationL2).click();
					tec.AC40.Common.Wait.wait2Second();		
				}
			 Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
			 waitfl.until(ExpectedConditions.elementToBeClickable(Property.FulFillmentEdit));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.FulFillmentEdit));
			 
			 waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.FulFillmentEdit);
					 }
					 });
			 
			 d.findElement(Property.FulFillmentEdit).click();
			 waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShiipingHandilingEdit));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShiipingHandilingEdit));
			 
			 waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ShiipingHandilingEdit);
					 }
					 });
			 
			 d.findElement(Property.ShiipingHandilingEdit).click();
			 Thread.sleep(1000);
			 kb.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			 Thread.sleep(2000);
		     kb.pressKey(Keys.DELETE);
			 Thread.sleep(500);
			 kb.sendKeys(FullfilmentShippingOrHandlingFee);
			 waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShippingMarkupEdit));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingMarkupEdit));
			 
			 waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ShippingMarkupEdit);
					 }
					 });
			 
			 d.findElement(Property.ShippingMarkupEdit).click();
			 Thread.sleep(1000);
			 kb.sendKeys(Keys.chord(Keys.CONTROL,"a"));
			 Thread.sleep(2000);
			 kb.pressKey(Keys.DELETE);
			 Thread.sleep(500);
			 kb.sendKeys(FullfilmentShippingMarkupFee);
			 waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShippingDropDown));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingDropDown));
			
			 waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ShippingDropDown);
					 }
					 });
			 
			 tec.AC40.Common.Wait.wait2Second();
			 d.findElement(Property.ShippingDropDown).click();
			 tec.AC40.Common.Wait.wait2Second();
			 if((FullfilmentShippingOrHandlingFeeTypeAmountPercent.equals("N"))&&(FullfilmentShippingMarkupFeeAmountPercentTypeAmount.equals("N")))
			   {
				 waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShippingDDAmount));
				 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingDDAmount));
				 
				 waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ShippingDDAmount);
						 }
						 });
				 
				 d.findElement(Property.ShippingDDAmount).click();
				 tec.AC40.Common.Wait.wait2Second();
				 waitfl.until(ExpectedConditions.elementToBeClickable(Property.MarkupDropDown));
				 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.MarkupDropDown));
				 
				 waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.MarkupDropDown);
						 }
						 });
				 
				 d.findElement(Property.MarkupDropDown).click();
				 tec.AC40.Common.Wait.wait2Second();
				 waitfl.until(ExpectedConditions.elementToBeClickable(Property.MarkupDdAmount));
				 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.MarkupDdAmount));
				
				 waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.MarkupDdAmount);
						 }
						 });
				 
				 tec.AC40.Common.Wait.wait2Second();
				 d.findElement(Property.MarkupDdAmount).click();
				 //System.out.println("nnnnnnNNN");
			   }
			 else if((FullfilmentShippingOrHandlingFeeTypeAmountPercent.equals("Y"))&&(FullfilmentShippingMarkupFeeAmountPercentTypeAmount.equals("Y")))
			   {
				// System.out.println("222");
				 waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShippingDDPercent));
				 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingDDPercent));
				
				 waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ShippingDDPercent);
						 }
						 });
				 
				 d.findElement(Property.ShippingDDPercent).click();
				 waitfl.until(ExpectedConditions.elementToBeClickable(Property.MarkupDropDown));
				 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.MarkupDropDown));
				
				 waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.MarkupDropDown);
						 }
						 });
				 
				 tec.AC40.Common.Wait.wait2Second();
				 d.findElement(Property.MarkupDropDown).click();
				 tec.AC40.Common.Wait.wait2Second();
				 waitfl.until(ExpectedConditions.elementToBeClickable(Property.MarkupDDPercent));
				 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.MarkupDDPercent));
				 
				 waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.MarkupDDPercent);
						 }
						 });
				 
				 tec.AC40.Common.Wait.wait2Second();
				 d.findElement(Property.MarkupDDPercent).click();
			   }
			 else if((FullfilmentShippingOrHandlingFeeTypeAmountPercent.equals("Y"))&&(FullfilmentShippingMarkupFeeAmountPercentTypeAmount.equals("N")))
			   {
				 //System.out.println("3333");
				 waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShippingDDPercent));
				 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingDDPercent));
				 
				 waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ShippingDDPercent);
						 }
						 });
				 
				 d.findElement(Property.ShippingDDPercent).click();
				 waitfl.until(ExpectedConditions.elementToBeClickable(Property.MarkupDropDown));
				 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.MarkupDropDown));
				 
				 waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.MarkupDropDown);
						 }
						 });
				 
				 tec.AC40.Common.Wait.wait2Second();
				 d.findElement(Property.MarkupDropDown).click();
				 tec.AC40.Common.Wait.wait2Second();
				 waitfl.until(ExpectedConditions.elementToBeClickable(Property.MarkupDdAmount));
				 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.MarkupDdAmount));
				
				 waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.MarkupDdAmount);
						 }
						 });
				 
				 tec.AC40.Common.Wait.wait2Second();
				 d.findElement(Property.MarkupDdAmount).click();
				 tec.AC40.Common.Wait.wait2Second();
			   }
			 else
			  {
				 //System.out.println("444");
				 waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShippingDDAmount));
				 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingDDAmount));
				 
				 waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ShippingDDAmount);
						 }
						 });
				 
				 tec.AC40.Common.Wait.wait2Second();
				 d.findElement(Property.ShippingDDAmount).click();
				 tec.AC40.Common.Wait.wait2Second();
				 waitfl.until(ExpectedConditions.elementToBeClickable(Property.MarkupDropDown));
				 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.MarkupDropDown));
				 
				 waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.MarkupDropDown);
						 }
						 });
				 
				 tec.AC40.Common.Wait.wait2Second();
				 d.findElement(Property.MarkupDropDown).click();
				 tec.AC40.Common.Wait.wait2Second();
				 waitfl.until(ExpectedConditions.elementToBeClickable(Property.MarkupDDPercent));
				 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.MarkupDDPercent));
				 
				 waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.MarkupDDPercent);
						 }
						 });
				 
				 tec.AC40.Common.Wait.wait2Second();
				 d.findElement(Property.MarkupDDPercent).click(); 
			   }
			 tec.AC40.Common.Wait.wait2Second();
			 waitfl.until(ExpectedConditions.elementToBeClickable(Property.FullfillmentSave));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.FullfillmentSave));
			 
			 waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.FullfillmentSave);
					 }
					 });
			 
			 d.findElement(Property.FullfillmentSave).click();
			 tec.AC40.Common.Wait.wait2Second();
			 waitfl.until(ExpectedConditions.elementToBeClickable(Property.FullfillmentSucessMessage));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.FullfillmentSucessMessage));
			 
			 waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.FullfillmentSucessMessage);
					 }
					 });
			 
			 tec.AC40.Common.Wait.wait2Second();
			 d.findElement(Property.FullfillmentSucessMessage).click();
			 tec.AC40.Common.Wait.wait2Second();
			 waitfl.until(ExpectedConditions.elementToBeClickable(Property.Backtohome));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Backtohome));
			 
			 waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.Backtohome);
					 }
					 });
			 
			 d.findElement(Property.Backtohome).click(); 
			 
			 if(Config.LayoutType.equals("Classic"))
				{
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
					//d.findElement(Property.CompanyLink).isDisplayed();
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.CompanyLink);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
				}
				else if(Config.LayoutType.equals("Layout1"))
				{
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
					//d.findElement(Property.CompanyIconL1).isDisplayed();
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.CompanyIconL1);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
				}
				else if(Config.LayoutType.equals("Layout2"))
				{
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
					//d.findElement(Property.CompanyIconL2).isDisplayed();
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.CompanyIconL2);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
				}
			 
			 
		}catch (Exception e){
				ErrorNumber = ErrorNumber +1;
				captureScreenshot();
				e.printStackTrace();}
	  }
	
	public static void deleteingfullfillmentdetails(String FullfilmentShippingOrHandlingFee,String FullfilmentShippingMarkupFee,
			String FullfilmentShippingOrHandlingFeeTypeAmountPercent,String FullfilmentShippingMarkupFeeAmountPercentTypeAmount)
			throws InterruptedException{
		try{
			 WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
			 MouseAdjFunction();
			 FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
			 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
			 waitfl.pollingEvery(1, TimeUnit.SECONDS);
			 waitfl.ignoring(NoSuchElementException.class);
			 waitfl.ignoring(StaleElementReferenceException.class);
			 
			 tec.AC40.Common.Wait.wait2Second();
			 if(Config.LayoutType.equals("Classic"))
				{
				    tec.AC40.Common.Wait.wait2Second();	
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.CompanyLink);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
					d.findElement(Property.CompanyLink).click();
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.FulfillmentLocationsIcon));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.FulfillmentLocationsIcon));
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.FulfillmentLocationsIcon);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
					d.findElement(Property.FulfillmentLocationsIcon).click();
					tec.AC40.Common.Wait.wait2Second();	
				}
				else if(Config.LayoutType.equals("Layout1"))
				{
					tec.AC40.Common.Wait.wait2Second();	
					tec.AC40.Common.Wait.wait2Second();	

					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.CompanyIconL1);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
					d.findElement(Property.CompanyIconL1).click();
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.FulfillmentLocationsIconl1));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.FulfillmentLocationsIconl1));
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.FulfillmentLocationsIconl1);
							 }
							 });
					
					d.findElement(Property.FulfillmentLocationsIconl1).click();
					tec.AC40.Common.Wait.wait2Second();		
				}
				else if(Config.LayoutType.equals("Layout2"))
				{
					tec.AC40.Common.Wait.wait2Second();	
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.CompanyIconL2);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
					d.findElement(Property.CompanyIconL2).click();
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.FullfilmentlocationL2));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.FullfilmentlocationL2));
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.FullfilmentlocationL2);
							 }
							 });
					
					d.findElement(Property.FullfilmentlocationL2).click();
					tec.AC40.Common.Wait.wait2Second();	
				}
			 Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
			 waitfl.until(ExpectedConditions.elementToBeClickable(Property.FulFillmentEdit));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.FulFillmentEdit));
			 
			 waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.FulFillmentEdit);
					 }
					 });
			 
			 d.findElement(Property.FulFillmentEdit).click();
			 waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShiipingHandilingEdit));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShiipingHandilingEdit));
			
			 waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ShiipingHandilingEdit);
					 }
					 });
			 
			 tec.AC40.Common.Wait.wait2Second();	
			 d.findElement(Property.ShiipingHandilingEdit).click();
			 Thread.sleep(1000);
			 kb.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			 Thread.sleep(2000);
		     kb.pressKey(Keys.DELETE);
			 Thread.sleep(500);
			 //kb.sendKeys(FullfilmentShippingOrHandlingFee);
			 waitfl.until(ExpectedConditions.elementToBeClickable(Property.ShippingMarkupEdit));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ShippingMarkupEdit));
			 
			 waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ShippingMarkupEdit);
					 }
					 });
			 
			 d.findElement(Property.ShippingMarkupEdit).click();
			 Thread.sleep(1000);
			 kb.sendKeys(Keys.chord(Keys.CONTROL,"a"));
			 Thread.sleep(2000);
			 kb.pressKey(Keys.DELETE);
			 Thread.sleep(500);
			 //kb.sendKeys(FullfilmentShippingMarkupFee);
			 waitfl.until(ExpectedConditions.elementToBeClickable(Property.FullfillmentSave));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.FullfillmentSave));
			 
			 waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.FullfillmentSave);
					 }
					 });
			 
			 d.findElement(Property.FullfillmentSave).click();
			 tec.AC40.Common.Wait.wait5Second();
			 waitfl.until(ExpectedConditions.elementToBeClickable(Property.FullfillmentSucessMessage));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.FullfillmentSucessMessage));
			 
			 waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.FullfillmentSucessMessage);
					 }
					 });
			 
			 tec.AC40.Common.Wait.wait5Second();
			 d.findElement(Property.FullfillmentSucessMessage).click();
			 tec.AC40.Common.Wait.wait5Second();
			 waitfl.until(ExpectedConditions.elementToBeClickable(Property.Backtohome));
			 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Backtohome));
			 
			 waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.Backtohome);
					 }
					 });
			 
			 d.findElement(Property.Backtohome).click(); 
			 
			 if(Config.LayoutType.equals("Classic"))
				{
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyLink));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyLink));
					//d.findElement(Property.CompanyLink).isDisplayed();
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.CompanyLink);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
				}
				else if(Config.LayoutType.equals("Layout1"))
				{
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
					//d.findElement(Property.CompanyIconL1).isDisplayed();
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.CompanyIconL1);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
				}
				else if(Config.LayoutType.equals("Layout2"))
				{
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL2));
					waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL2));
					//d.findElement(Property.CompanyIconL2).isDisplayed();
					
					waitfl.until(new Function<WebDriver, WebElement>() 
							 {
							   public WebElement apply(WebDriver driver) {
							   return driver.findElement(Property.CompanyIconL2);
							 }
							 });
					
					tec.AC40.Common.Wait.wait2Second();	
				}
			 
			 
		}
		catch (Exception e){
				ErrorNumber = ErrorNumber +1;
				captureScreenshot();
				e.printStackTrace();}
	  }
	
	public static String PersonalizeProductDetails(String ItemPerPrice, String Discount, String DiscountPercentage,
			 int productdetailsnumber, String EnablePromotionsORDiscounts,
			String IsExternalPricingON, String Quantity1, String BasePrice)
			throws InterruptedException {
		String ActualItemPerprice = null;
		String ActualItemPerprice2 = null;
		try{
			 WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
			 MouseAdjFunction();
			 FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
			 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
			 waitfl.pollingEvery(1, TimeUnit.SECONDS);
			 waitfl.ignoring(NoSuchElementException.class);
			 waitfl.ignoring(StaleElementReferenceException.class);
			String ActualDiscount =  null;
			String ActualBasePrice = null;
			
		if(Config.LayoutType.equals("Layout2"))
		{
				if(productdetailsnumber == 1)
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductDetailslink1));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductDetailslink1));
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductDetailslink1);
						 }
						 });
				
				d.findElement(Property.ProductDetailslink1).click();
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.ProductNamePrdDetails));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductNamePrdDetails));
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductNamePrdDetails);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.ProductNamePrdDetails).click();
				Thread.sleep(500);
				if(IsExternalPricingON.equalsIgnoreCase("NO"))
				{
					ActualItemPerprice = d.findElement(Property.ProductItemPrice).getText();
				}
				ActualDiscount = d.findElement(Property.ProductDiscout).getText();
				if(BasePrice.equals("0")|| BasePrice.equals("0.0") || BasePrice.equals("0.00") ||
						BasePrice.equals("0.000") || BasePrice.equals("0.0000"))
				{
					// Application displays base price value in else condition only
				}
				else
				{
					ActualBasePrice = d.findElement(Property.ProductBasePrice).getText();
				}
			
			}
			else if(productdetailsnumber == 2)
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductDetailslink2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductDetailslink2));
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductDetailslink2);
						 }
						 });
				
				d.findElement(Property.ProductDetailslink2).click();
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.ProductNamePrdDetails));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductNamePrdDetails));
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductNamePrdDetails);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.ProductNamePrdDetails).click();
				Thread.sleep(500);
				if(IsExternalPricingON.equalsIgnoreCase("NO"))
				{
				ActualItemPerprice =	d.findElement(Property.Layout2ProductItemPrice2).getText();
				}
				ActualDiscount = d.findElement(Property.Layout2ProductDisocut2).getText();
				if(BasePrice.equals("0")|| BasePrice.equals("0.0") || BasePrice.equals("0.00") ||
						BasePrice.equals("0.000") || BasePrice.equals("0.0000"))
				{
					// Application displays base price value in else condition only
				}
				else
				{
					ActualBasePrice = d.findElement(Property.ProductBasePrice).getText();
				}
			}
			else if(productdetailsnumber == 3)
			{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductDetailslink3));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductDetailslink3));
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductDetailslink3);
						 }
						 });
				
				d.findElement(Property.ProductDetailslink3).click();
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.ProductNamePrdDetails2));
				waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductNamePrdDetails2));
				
				waitfl.until(new Function<WebDriver, WebElement>() 
						 {
						   public WebElement apply(WebDriver driver) {
						   return driver.findElement(Property.ProductNamePrdDetails2);
						 }
						 });
				
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.ProductNamePrdDetails2).click();
				Thread.sleep(500);
				if(IsExternalPricingON.equalsIgnoreCase("NO"))
				{
				ActualItemPerprice =	d.findElement(Property.Layout2ProductItemPrice3).getText();
				}
				ActualDiscount = d.findElement(Property.Layout2ProductDisocut3).getText();
				if(BasePrice.equals("0")|| BasePrice.equals("0.0") || BasePrice.equals("0.00") ||
						BasePrice.equals("0.000") || BasePrice.equals("0.0000"))
				{
					// Application displays base price value in else condition only
				}
				else
				{
					ActualBasePrice = d.findElement(Property.ProuctBasePriceL2).getText();
				}
			}
		}
		else
		{
			try{
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductDetailslink));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductDetailslink));
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ProductDetailslink);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.ProductDetailslink).click();}
			catch(Exception e){
				waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.ProductDetailslink21));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductDetailslink21));
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.ProductDetailslink21);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();
			d.findElement(Property.ProductDetailslink21).click();}
		
		//boolean ItemPriceStatus = d.findElement(Property.ProductItemPrice).isEnabled();
		//System.out.println("ItemPriceStatus :"+ItemPriceStatus);
		tec.AC40.Common.Wait.wait2Second();
		waitfl.until(ExpectedConditions.elementToBeClickable(Property.ProductNamePrdDetails));
		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.ProductNamePrdDetails));
		
		waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.ProductNamePrdDetails);
				 }
				 });
		
		tec.AC40.Common.Wait.wait2Second();
		d.findElement(Property.ProductNamePrdDetails).click();
		//System.out.println("Quantity1 :"+Quantity1);
		/*if(IsExternalPricingON.equalsIgnoreCase("NO"))
		{*/
		if(IsExternalPricingON.equalsIgnoreCase("NO"))
		{
			ActualItemPerprice =d.findElement(Property.ProductItemPrice).getText();
		}
			//System.out.println("ActualItemPerprice1 :"+ActualItemPerprice);
		//}
		// Because of External price application change, we commented below code (deleted External 
		// price grid in user flow).
		/*else if(IsExternalPricingON.equalsIgnoreCase("Yes"))
		{
			int QuantityNumber = Integer.parseInt(Quantity1);
			//ActualItemPerprice =	d.findElement(Property.ProductItemPrice).getText();	
			if(QuantityNumber>= 1 && QuantityNumber <= 249)
			{
				ActualItemPerprice =	d.findElement(Property.ProductItemPriceExternalPrice1).getText();
			}
			else if(QuantityNumber>= 250 && QuantityNumber <= 2499)
			{	
				ActualItemPerprice =	d.findElement(Property.ProductItemPriceExternalPrice2).getText();
			}
			else if(QuantityNumber>= 2500 && QuantityNumber <= 4999)
			{
				ActualItemPerprice =	d.findElement(Property.ProductItemPriceExternalPrice3).getText();
			}
			else if(QuantityNumber>= 5000 && QuantityNumber <= 9999)
			{
				ActualItemPerprice =	d.findElement(Property.ProductItemPriceExternalPrice4).getText();
			}
			else if(QuantityNumber>= 10000 && QuantityNumber <= 24999)
			{
				ActualItemPerprice =	d.findElement(Property.ProductItemPriceExternalPrice5).getText();
			}
			else if(QuantityNumber>= 25000 )
			{
				ActualItemPerprice =	d.findElement(Property.ProductItemPriceExternalPrice6).getText();
			}
			
		System.out.println("ActualItemPerprice :"+ActualItemPerprice);	
		
		}*/
		ActualDiscount = d.findElement(Property.ProductDiscout).getText();
		if(BasePrice.equals("0")|| BasePrice.equals("0.0") || BasePrice.equals("0.00") ||
				BasePrice.equals("0.000") || BasePrice.equals("0.0000"))
		{
			// Application displays base price value in else condition only
		}
		else
		{
			ActualBasePrice = d.findElement(Property.ProductBasePrice).getText();
		}
		}
		String PageName = "ProductDetailsPopUp";
		//System.out.println("DiscountPercentage : "+DiscountPercentage);
		String ExpectedDiscount = null;
		//String Expectedshippingmethod = null;
		
		if(DiscountPercentage.equals("N"))
		{
			ExpectedDiscount = Config.Currency+Discount;
		}
		else if(Discount.equals("0")|| Discount.equals("0.0") || Discount.equals("0.00") ||
				Discount.equals("0.000") || Discount.equals("0.0000"))
		{
			ExpectedDiscount = Config.Currency+Discount;
		}
		else
		{
			ExpectedDiscount = Discount+Config.PercentageSymbol;
		}
		String ExpectedBasePrice = Config.Currency+BasePrice;
		//System.out.println("ExpectedDiscount : "+ExpectedDiscount);
		String ExpectedItemPerprice = null;
		
		if(IsExternalPricingON.equalsIgnoreCase("NO"))
		{
			ExpectedItemPerprice = Config.Currency+ItemPerPrice;
		}
		else if(IsExternalPricingON.equalsIgnoreCase("YES"))
		{
			/*ActualItemPerprice2 = ActualItemPerprice.substring(1,ActualItemPerprice.length());
			ExpectedItemPerprice = Config.Currency+ActualItemPerprice2;*/
		}
		
		if(IsExternalPricingON.equalsIgnoreCase("NO"))
		{
			if(ExpectedItemPerprice.equals(ActualItemPerprice))
			{
			//!System.out.println("Both Item prices are same");
			}
			else
			{
			ErrorNumber = ErrorNumber+1;
			captureScreenshot();
			String PriceType = "Item price";
			System.out.println("<------- Both Item prices are different ------> "+ErrorNumber);
			System.out.println("Expected value: "+ExpectedItemPerprice);
			System.out.println(" Actual value :"+ActualItemPerprice);
			 
			RW_File.WriteResult(ExpectedItemPerprice, ActualItemPerprice,PageName, PriceType);
			
			
			}
		}
		if(EnablePromotionsORDiscounts.equals("ON"))
		{

			if(ExpectedDiscount.equals(ActualDiscount))
			{
				//!System.out.println("Both discounts are same");
			}
			else
			{
			ErrorNumber = ErrorNumber+1;
			captureScreenshot();
			String PriceType = "Discount";
			System.out.println("<----  Both Discount prices are different ------> "+ErrorNumber);
			System.out.println("Exptected value :"+ExpectedDiscount);
			System.out.println(" Actual value :"+ActualDiscount);
			RW_File.WriteResult(ExpectedDiscount, ActualDiscount,PageName, PriceType);
			}
			
		}
		else
		{
			if(ActualDiscount.equals(""))
			{
				//!System.out.println("Both discounts are same");
			}
			else
			{
				captureScreenshot();
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Discount";
				System.out.println("<----  Both Discount prices are different ----> "+ErrorNumber);
				System.out.println("Exptected value :"+"");
				System.out.println(" Actual value :"+ActualDiscount);
				RW_File.WriteResult("", ActualDiscount,PageName, PriceType);
			}
			/*
			String Totapopupdatavalue = d.findElement(Property.TotalPopupData).getText();
			//System.out.println("Totapopupdatavalue"+Totapopupdatavalue);
			if(Totapopupdatavalue.contains("Discount"))
			{
				ErrorNumber = ErrorNumber+1;
				System.out.println("Both Actual and Expected are difference ");
			}*/
			
		}
		if(BasePrice.equals("0")|| BasePrice.equals("0.0") || BasePrice.equals("0.00") ||
				BasePrice.equals("0.000") || BasePrice.equals("0.0000"))
		{
			// Application displays base price value in else condition only
		}
		else
		{
			if(ExpectedBasePrice.equals(ActualBasePrice))
			{
				//!System.out.println("Both Base price values are same");
			}
			else
			{
				captureScreenshot();
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Base Price";
				System.out.println("<----  Both Base prices are different ----> "+ErrorNumber);
				System.out.println("Exptected value :"+ExpectedBasePrice);
				System.out.println(" Actual value :"+ActualBasePrice);
				RW_File.WriteResult(ExpectedBasePrice, ActualBasePrice,PageName, PriceType);
			}
		}
		
		tec.AC40.Common.Wait.wait2Second();
		if(Config.LayoutType.equals("Classic"))
		{
			d.findElement(Property.ProductClose).click();
		}
		else if(Config.LayoutType.equals("Layout1"))
		{
			d.findElement(Property.ProductCloseL1).click();
		}
		else if(Config.LayoutType.equals("Layout2"))
		{
			if(productdetailsnumber == 1)
			{
				d.findElement(Property.ProductCloseL21).click();
			}
			else if(productdetailsnumber == 2)
			{
				d.findElement(Property.ProductCloseL22).click();
			}
			else if(productdetailsnumber == 3)
			{
				d.findElement(Property.ProductCloseL23).click();
			}
		}
		
		tec.AC40.Common.Wait.wait5Second();
		}catch (Exception e){
			ErrorNumber = ErrorNumber+1;
			captureScreenshot();
	    	  e.printStackTrace();
	    	  //return ActualItemPerprice;
	      }
		
		return ActualItemPerprice2;		
		
	}
	
	public static String SubTotalCalculation(String ItemPerPrice, String FlatRate,
			String Quantity1, String DiscountCalculationFromSubTotal, String OrderAmountValue,
			String BasePrice, String OrderType, String DownloadPrice)
	{
		double Itemprice = 0;
		String ItemPrice3 = "";
		try
		{
			double ItemPerPrice1 = Double.parseDouble(ItemPerPrice);
			double FlatRate1 = Double.parseDouble(FlatRate);
			double Quantity11 = Double.parseDouble(Quantity1);
			double Discount = Double.parseDouble(DiscountCalculationFromSubTotal);
			double DownloadPrice1 = Double.parseDouble(DownloadPrice);
			double Subtotal = 0;
			
			if(FlatRate1 > 0)
			{
				Subtotal = FlatRate1 - Discount;
				//System.out.println("A");
			}
			else
			{
				Subtotal = (ItemPerPrice1 * Quantity11) - Discount;
			//	System.out.println("B");
			}
			Subtotal = Subtotal+DownloadPrice1;
			String Subtotal1 = ""+Subtotal;
			String Subtotal2 = Decimalsetting(Subtotal1, OrderAmountValue);
			String Subtotal3 = Subtotal2;
			
			Subtotal = Double.parseDouble(Subtotal3);
			double BasePrice1 = Double.parseDouble(BasePrice);
			
			if(Subtotal >= BasePrice1)
			{
                 System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
				Itemprice = Subtotal+Discount;
				//System.out.println("C");
			}
			else
			{
                System.out.println("Biiiiiiiiiiiiiiiiiiiiiiiiiiiii");

				Itemprice = BasePrice1;
				OrderFlow.BasePriceIncrementValue = 1;
				//System.out.println("D");
			}
			if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload"))
			{
				Itemprice = DownloadPrice1;
				OrderFlow.BasePriceDownload = 1;
				OrderFlow.BasePriceIncrementValue = 0;
				//System.out.println("E");
			}
			else
			{
				Itemprice = Itemprice+0;// No value for zero
				//System.out.println("F");
			}
			String ItemPrice2 = Decimalsetting(""+Itemprice, OrderAmountValue);
			 ItemPrice3 = ItemPrice2;
			// System.out.println("ItemPrice3 :"+ItemPrice3);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return ItemPrice3;
	}
	
	
	public static void ViewPricingDetails(String ItemPerPrice, String Discount, String DiscountPercentage, String IsExternalPricingON, String Quantity1
			)throws InterruptedException {
		try{
		WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
		MouseAdjFunction();
		FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
		 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
		 waitfl.pollingEvery(1, TimeUnit.SECONDS);
		 waitfl.ignoring(NoSuchElementException.class);
		 waitfl.ignoring(StaleElementReferenceException.class);
		d.findElement(Property.Viewpricinglink).click();
		tec.AC40.Common.Wait.wait2Second();
		String PageName = "VeiwPricingPopUp";
		waitfl.until(ExpectedConditions.elementToBeClickable(Property.ViewPricingItemPrice));
		tec.AC40.Common.Wait.wait2Second();
		String ActualItemPerprice =	null;
		int QuantityNumber = Integer.parseInt(Quantity1);
		
		/*if(IsExternalPricingON.equalsIgnoreCase("NO"))
		{*/
			ActualItemPerprice =	d.findElement(Property.ViewPricingItemPrice).getText();
			//System.out.println("ActualItemPerprice2 :"+ActualItemPerprice);
		//}
		// Because of External price application change, we commented below code (deleted External 
		// price grid in user flow).
		/*else if(IsExternalPricingON.equalsIgnoreCase("Yes"))
		{
			if(QuantityNumber>= 1 && QuantityNumber <= 249)
			{
				ActualItemPerprice =	d.findElement(Property.ViewPricingItemPrice).getText();
			}
			else if(QuantityNumber>= 250 && QuantityNumber <= 2499)
			{	
				ActualItemPerprice =	d.findElement(Property.ViewPricingItemPriceEP2).getText();
			}
			else if(QuantityNumber>= 2500 && QuantityNumber <= 4999)
			{
				ActualItemPerprice =	d.findElement(Property.ViewPricingItemPriceEP3).getText();
			}
			else if(QuantityNumber>= 5000 && QuantityNumber <= 9999)
			{
				ActualItemPerprice =	d.findElement(Property.ViewPricingItemPriceEP4).getText();
			}
			else if(QuantityNumber>= 10000 && QuantityNumber <= 24999)
			{
				ActualItemPerprice =	d.findElement(Property.ViewPricingItemPriceEP5).getText();
			}
			else if(QuantityNumber>= 25000 )
			{
				ActualItemPerprice =	d.findElement(Property.ViewPricingItemPriceEP6).getText();
			}
		}*/
		
		
		String ExpectedItemPerprice = Config.Currency+ItemPerPrice;
		
		if(ExpectedItemPerprice.equals(ActualItemPerprice))
		{
			//!System.out.println("Both Item prices are same");
		}
		else
		{
			ErrorNumber = ErrorNumber+1;
			captureScreenshot();
			String PriceType = "Item price";
			System.out.println("<------- Both Itemm prices are different ------> "+ErrorNumber);
			System.out.println("Expected value: "+ExpectedItemPerprice);
			System.out.println("Actual value :"+ActualItemPerprice);
			RW_File.WriteResult(ExpectedItemPerprice, ActualItemPerprice,PageName, PriceType);
		}
		d.findElement(Property.ViewPricingClose).click();
		tec.AC40.Common.Wait.wait5Second();
		}
		catch (Exception e){
				ErrorNumber = ErrorNumber +1;
				captureScreenshot();
	    	  e.printStackTrace();
	      }		
	}
	
	public static String ExternalPricingSubTotalCalculation(String Quantity1, String ItemPerPrice,
			String FlatRate) throws InterruptedException, AWTException
	{
		MouseAdjFunction();
		String SubTotalValue1 = null;
		try{
			int Quantity = Integer.parseInt(Quantity1);
			double ItemPrice = Double.parseDouble(ItemPerPrice);
			double SubTotalValue = 0;
			if(FlatRate.equals("0") || FlatRate.equals("0.00") || FlatRate.equals("0.000") || 
					FlatRate.equals("0.0000"))
			{
				SubTotalValue = Quantity * ItemPrice;
			}
			else 
			{
				SubTotalValue = ItemPrice;
			}
			SubTotalValue1 = ""+SubTotalValue;
		}
		catch(Exception e)
		{
			ErrorNumber = ErrorNumber +1;
			captureScreenshot();
			e.printStackTrace();
		}
		return SubTotalValue1;
	}
	
	public static String ExternalPricingTotalPriceCalculation(String SubTotal,String Discount, String Addons,
			String Postage,String ShippingPrice, String DownloadPrice) throws AWTException
	{
		MouseAdjFunction();
		String TotalPrice1 = null;
		try{
			double SubTotal2 = Double.parseDouble(SubTotal);
			double Discount2 = Double.parseDouble(Discount);
			double Addons2 = Double.parseDouble(Addons);
			double Postage2 = Double.parseDouble(Postage);
			double ShippingPrice2 = Double.parseDouble(ShippingPrice);
			double DownloadPrice2 = Double.parseDouble(DownloadPrice);
			double AmtAfterDiscount = (SubTotal2 - Discount2);
			double TotalPrice = 0.0;
			if(AmtAfterDiscount > 0)
			{
				 TotalPrice = AmtAfterDiscount + Addons2 + Postage2 + ShippingPrice2 + DownloadPrice2;
			}
			else
			{
				TotalPrice = 0 + Addons2 + Postage2 + ShippingPrice2 + DownloadPrice2;
			}
			
			TotalPrice1 = ""+TotalPrice;
			//System.out.println("EP View orders paage TotalPrice :"+TotalPrice);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return TotalPrice1;
	}
	
	public static void AddonPriceverify(String Addons, String AddonPricePerPiece, String TestCase, 
			String TestStep, String Parameters) throws InterruptedException {
		try{
			MouseAdjFunction();
			String PageName = "AddonPrice";
			String ActualAddonPrice = d.findElement(Property.Addonprice).getText();
			String ExpectedAddonPrice = null;
			if(AddonPricePerPiece.equals("0") || AddonPricePerPiece.equals("0.00") || AddonPricePerPiece.equals("0.000") || AddonPricePerPiece.equals("0.0000"))
			{
				if(Addons.equals("0") || Addons.equals("0.00") || Addons.equals("0.000") || Addons.equals("0.0000"))
				{
					ExpectedAddonPrice = "Addon1"; 
				}
				else
				{
					ExpectedAddonPrice = "Addon1 - "+Config.Currency+Addons+" ( Flat Rate )";
				}
				
			}
			else
			{
				if(Addons.equals("0") || Addons.equals("0.00") || Addons.equals("0.000") || Addons.equals("0.0000"))
				{
					ExpectedAddonPrice = "Addon1";
				}
				else
				{
					ExpectedAddonPrice = "Addon1 - "+Config.Currency+Addons+" ( Per Piece )";
				}
		     
			}
			
			if(ExpectedAddonPrice.equals(ActualAddonPrice))
			{
				/**/System.out.println("Both addon prices are same");
			}
			else
			{
				
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Addon price";
				System.out.println("<----- View Summary Both Addon prices are different ----->"+ErrorNumber);
				System.out.println("Actual Addon price is: "+ActualAddonPrice);
				System.out.println("Expected Addon price is: "+ExpectedAddonPrice);
				RW_File.WriteResult(ExpectedAddonPrice, ActualAddonPrice, PageName, PriceType);
			}
		}catch (Exception e){
			ErrorNumber = ErrorNumber+1;
			captureScreenshot();
	    	  e.printStackTrace();
	      }		
	}
	
	public static void PaypalCredit()
	{
		/*
	  	/// if(PaymentType.contains("PayPal Credit") || PaymentType.contains("PayPal External Checkout"))
	  	 {
	  		tec.AC40.Common.Wait.wait60Second();
	  		tec.AC40.Common.Wait.wait10Second();
	  		//waitfl.until(ExpectedConditions.elementToBeClickable(Property.PPEUserName));
	  		//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PPEUserName));
	  		
	  		d.findElement(Property.PPEUserName).clear();
	  		d.findElement(Property.PPEUserName).sendKeys("kr.madala1@gmail.com");
	  		d.findElement(Property.PPEPassword).clear();
	  		d.findElement(Property.PPEPassword).sendKeys("Test#135");
	  		d.findElement(Property.PPELoginButton).click();
	  		tec.AC40.Common.Wait.wait10Second();
	  		d.findElement(Property.PPEContiueButton).click();
	  		tec.AC40.Common.Wait.wait60Second();
	  		 waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PrintLink));
	  		 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.PrintLink));
	  		 tec.AC40.Common.Wait.wait5Second();
	  	 }
	  	 */
	}
	
	
	public static void ViewSummaryPriceInformation(String Quantity, String ItemPerPrice, String SubTotal, String Discount, String Addons,
			String Postage, String TotalPrice, String DiscountPercentage, String DiscountCalculationFromSubTotal, String ShippingPrice,
			String DownloadPrice, String OrderType, String TestCase, String TestStep, String Parameters, String ProdutType,
			String OrderBase, String EnablePromotionsORDiscounts,String Weighttype,String DiscountcalculationfromSubTotal,
			String Priceafterapplyingfulfillmentshippingmarkupfee,String OrderAmountValue,String LandingPageOption,String DecimalValue, 
			int BasePriceIncrementValue,String Orderelements,String OrderelementsAddOns,String OrderelementsBillingAddress,String OrderelementsInventoryLocation,String OrderelementsInventoryNumber,
			String OrderelementsPaymentDetail,String OrderelementsShippingAddress,String OrderelementsShippingMethod,String OrderelementsSpecialInstructions,String OrderelementsCheckout,
			String OrderelementsJobTicket,String OrderelementsPrintPopup) throws InterruptedException {
	try{
		String PageName = "ViewSummary";
		MouseAdjFunction();
		String ExpectedItemPrice = Config.Currency+ItemPerPrice;
		String ExpectedSubTotal = Config.Currency+SubTotal;
		String ExpectedDiscount = null;
		String ExpectedDiscountpercentage = null;
		String ExpectedDownloadPrice = Config.Currency+DownloadPrice;
		// For scroll down to screen, we click on total amount attribute (Screen shot purpose)
		Wait.wait10Second();
		d.findElement(Property.VSTotalPrice).click();
		String AcutalDownloadPrice = null;
		// We get the Actual download price values based on Layouts, if we have download value
		if(DownloadPrice.equals("0")||DownloadPrice.equals("0.00") || DownloadPrice.equals("0.000") || DownloadPrice.equals("0.0000"))
		{
			// 
		}
		else
		{
			if(BasePriceIncrementValue == 0)
			{
			if(Config.LayoutType.equals("Classic"))
			{
			AcutalDownloadPrice = d.findElement(Property.VSDownloadPrice).getText();
			}
			else if(Config.LayoutType.equals("Layout1"))
			{
				AcutalDownloadPrice = d.findElement(Property.VSLayout1DownloadPrice).getText();
			}
			else 
			{
				AcutalDownloadPrice = d.findElement(Property.VSLayout2DownloadPrice).getText();
			}
			}
		}
		
		String ActualDiscountPercentage= null;
		//We get the expected Discount percentage based on Discount type like Amount or percentage
		if(DiscountPercentage.equals("N"))
		{
			System.out.println("OrderFlow.IsBaseDiscountZero :"+OrderFlow.IsBaseDiscountZero);
			System.out.println("BasePriceIncrementValue :"+BasePriceIncrementValue);
			// This part works if Discount value is amount
			//First if block related to Base price cases only
			if(Discount.equals("0") ||
					Discount.equals("0.00") || Discount.equals("0.000") ||
					Discount.equals("0.0000"))
			{
				ExpectedDiscount = "-"+Config.Currency+Discount;
				if(BasePriceIncrementValue == 1 && OrderFlow.IsBaseDiscountZero == true)
				{
					ExpectedDiscount = "-"+Config.Currency+Discount;
				}
				else if(BasePriceIncrementValue == 1 && OrderFlow.IsBaseDiscountZero == false)
				{
					ExpectedDiscount = "-( "+Config.Currency+Discount+" )";
				}
			}
			else 
			{
				// Execute this code when Discount value is more than zero.;
				ExpectedDiscount = "-( "+Config.Currency+Discount+" )";
			}
			
		}
		else
		{
			// This part works if Discount value is Percentage
			
			// Get the expected Discount values
			ExpectedDiscount = "-"+Config.Currency+DiscountCalculationFromSubTotal;
			// Get expected Discount percentage  (We need to verify both amount and percentage values if discount type is percentage)
			ExpectedDiscountpercentage = "("+Discount+" "+Config.PercentageSymbol+")";
			
			
			//Below code related to Discount percentage verification in user
			if(EnablePromotionsORDiscounts.equals("ON"))
			{
				if(BasePriceIncrementValue == 0)
				{
				
				//Get ViewSummary page Actual discount percentage based on Layouts
				if(Config.LayoutType.equals("Classic"))
				{
					if(LandingPageOption.equalsIgnoreCase("YES"))
					{
						ActualDiscountPercentage = d.findElement(Property.VSDiscountPercent).getText();

					}
					else{
					ActualDiscountPercentage = d.findElement(Property.VSDiscountPercent).getText();
					}
				}
				else
				{
					if(LandingPageOption.equalsIgnoreCase("YES"))
					{
					ActualDiscountPercentage = d.findElement(Property.Layout2VSDiscountPercent1).getText();
					}
					else{
						ActualDiscountPercentage = d.findElement(Property.Layout2VSDiscountPercent).getText();
					}
				}
			
				// This part execute when admin enable PromotionsORDiscounts check box
				if(ExpectedDiscountpercentage.equals(ActualDiscountPercentage))
				{
					//!System.out.println("Both Discount percentages are same");
				}
				else
				{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Discount percentage";
				System.out.println("<---- View Summary Both Discount percentages are different ------> "+ErrorNumber);
				System.out.println("Actual Discount percentage is : "+ActualDiscountPercentage);
				System.out.println("Expected Discount percentage is is : "+ExpectedDiscountpercentage);
				RW_File.WriteResult(ExpectedDiscountpercentage, ActualDiscountPercentage, PageName, PriceType);
				}
			}
			
			}
		}
		//System.out.println(ActualDiscountPercentage);
		String ExpectedAddons = Config.Currency+Addons;
		String ExpectedPostagepirce = Config.Currency+Postage;
		String ExpectedShippingPrice = Config.Currency+ShippingPrice;
		//System.out.println("expected shipping in view summary is" +ExpectedShippingPrice);
		String ExpectedTotal = Config.Currency+TotalPrice;
		String ExpectedShippingHandilingPrice = Config.Currency+Priceafterapplyingfulfillmentshippingmarkupfee;

		
		String ActualQuantity = null;
		// Get Actual quantity value from View summary page based on layouts
		if(Config.LayoutType.equals("Layout2"))
		{
			
			ActualQuantity = d.findElement(Property.Layout2VSQuantity).getText();
		}
		else
		{
			ActualQuantity = d.findElement(Property.VSTQuantity).getText();
		}
		int ActualQuantity1 = Double.valueOf(ActualQuantity).intValue();
		int Quantity1 = Double.valueOf(Quantity).intValue();
		System.out.println("Quantity11234____"+Quantity1);
		// Compare the Expected Quantity value with Actual Quantity value. 
		if(Quantity1 == ActualQuantity1)
		{
			//!System.out.println("Both quantitys are same ");
		}
		else
		{
			ErrorNumber = ErrorNumber+1;
			captureScreenshot();
			String PriceType = "Quantity";
			System.out.println("<---- View Summary Both quantitys are different ----> "+ErrorNumber);
			System.out.println("Actual quantity is : "+ActualQuantity);
			System.out.println("Expected quantity is : "+Quantity);
			RW_File.WriteResult(Quantity, ActualQuantity, PageName, PriceType);
		}
		
		
		String ActualItemPerPrice = null;
		if(OrderType.equals("DynDownload") || OrderType.equals("DynShipTOMultipleLocations") || OrderType.equals("StaticDownload") 
				|| OrderType.equals("StaticShipTOMultipleLocations") || OrderType.equals("StaticInventoryShipTOMultipleLocations"))
		{
			// We do not have Item price value for above all conditions like DynDownload.
		}
		else
		{
			// We get the Item price value for the remaining conditions
			ActualItemPerPrice = d.findElement(Property.VSItemPrice).getText();
			if(ExpectedItemPrice.equals(ActualItemPerPrice))
			{
				//!System.out.println("Both Item prices are same");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				String PriceType = "Item price";
				captureScreenshot();
				System.out.println("<----- View Summary Both item prices are different ----->"+ErrorNumber);
				System.out.println("Actual Item price is: "+ActualItemPerPrice);
				System.out.println("Expected Item price is: "+ExpectedItemPrice);
				RW_File.WriteResult(ExpectedItemPrice, ActualItemPerPrice, PageName, PriceType);
			}
			
		}
		// Get subtotal value and compared with expected value with Actual value.
		String ActualSubTotal = d.findElement(Property.VSSubTotal).getText();
		if(ExpectedSubTotal.equals(ActualSubTotal))
		{
			//!System.out.println("Both Sub totals are same ");
		}
		else
		{
			ErrorNumber = ErrorNumber+1;
			captureScreenshot();
			String PriceType = "subtotal";
			System.out.println("<----- View Summary Both subtotals are different ----->"+ErrorNumber);
		    System.out.println("Actual subtotal is : "+ActualSubTotal);
		    System.out.println("Expected subtotal is : "+ExpectedSubTotal);
		    RW_File.WriteResult(ExpectedSubTotal, ActualSubTotal, PageName, PriceType);
		}
		
		// Get and verify the Discount value in view summary page.
		String ActualDiscount = d.findElement(Property.VSDiscountApplied).getText();
		System.out.println("ActualDiscount"+ActualDiscount);
		System.out.println("ExpectedDiscount"+ExpectedDiscount);

		if(EnablePromotionsORDiscounts.equals("ON"))
		{
			System.out.println("ActualDiscount0908hh"+ActualDiscount);
			// This part execute when admin enable promotionsORDiscount check box enables.
			if(ExpectedDiscount.equals(ActualDiscount))
			{
				//!System.out.println("Both Discount prices are same ");
			}
			else
			{
			ErrorNumber = ErrorNumber+1;
			captureScreenshot();
			String PriceType = "Discount";
			System.out.println("<------ View Summary Both Discount prices are different ------>"+ErrorNumber);
			System.out.println("Actual Discount price is : "+ActualDiscount);
			System.out.println("Expected Discount price is : "+ExpectedDiscount);
			RW_File.WriteResult(ExpectedDiscount, ActualDiscount, PageName, PriceType);
			}
		}
		else
		{
			System.out.println("ActualDiscount0908hhuuu"+ActualDiscount);

			// This part execute when admin disable promotionsORDiscount check box enables. And application not displays discount value
			if(ActualDiscount.equals(""))
			{
				//!System.out.println("Both Discount prices are same ");
			}
			else
			{
			ErrorNumber = ErrorNumber+1;
			captureScreenshot();
			String PriceType = "Discount";
			System.out.println("<---- View Summary Both Discount prices are different ------>"+ErrorNumber);
			System.out.println("Actual Discount price is : "+ActualDiscount);
			System.out.println("Expected Discount price is : "+"");
			RW_File.WriteResult("", ActualDiscount, PageName, PriceType);
			}
			
		}
		
		if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") || 
				ProdutType.equals("Dynamic Email"))
		{
			//We do not have addon value in application for the above conditions like Download or Broadcast
		}
		else
		{
		// Get the addon price and compare expected value with actual value
		String ActualAddons = d.findElement(Property.VSAddonos).getText();
		if(ExpectedAddons.equals(ActualAddons))
		{
			//!System.out.println("Both Addon prices are same");
		}
		else
		{
			ErrorNumber = ErrorNumber+1;
			captureScreenshot();
			String PriceType = "Addon price";
			System.out.println("<------- View Summary Both Addon prices are different ------>"+ErrorNumber);
			System.out.println("Actual Addon price is : "+ActualAddons);
			System.out.println("Expected Addon price is : "+ExpectedAddons);
			RW_File.WriteResult(ExpectedAddons, ActualAddons, PageName, PriceType);
		}
		}
		
		//Below code related to third party shipping price verification 
		//We do not have shipping price in view summary page whenever Orderbase equal to "Order"
		 if((OrderelementsCheckout.equalsIgnoreCase("Yes")&&OrderelementsShippingAddress.equalsIgnoreCase("NO"))||(OrderelementsCheckout.equalsIgnoreCase("Yes")&&OrderelementsShippingMethod.equalsIgnoreCase("NO"))){}else{

		if(OrderBase.equals("Item")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
		{
			String Actualthrdpartyshippingprice = d.findElement(Property.VSShippingPrice).getText();
			if(Actualthrdpartyshippingprice.matches("\\$\\d{1,5}?\\,?\\d{1,3}\\.\\d{2,4}"))
			 {
				//!System.out.println("actual 3rd party shipping value is " +Actualthrdpartyshippingprice);
			 }
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "ThirdParty Shipping Price";
				System.out.println("<------- View Summary Both Third Party Shipping prices are different ------>"+ErrorNumber);
				System.out.println("Actual Third Shipping price is : "+Actualthrdpartyshippingprice);
				System.out.println("Expected Third Party shipping price is : "+"\\$\\d{1,5}?\\,?\\d{1,3}\\.\\d{2,4}");
				RW_File.WriteResult("\\$\\d{1,5}?\\,?\\d{1,3}\\.\\d{2,4}", Actualthrdpartyshippingprice, PageName, PriceType);
			}
		}

		//Below code related to shipping price verification when orderbase is equal to Item
		if(OrderBase.equals("Item"))
		{// Item base code starts here (Level I)
			//double ShippingPrice1 = Double.valueOf(ShippingPrice).doubleValue();
			if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") || 
				ProdutType.equals("Dynamic Email") )
			{ // Condition for with out shipping or postage values (Level II)
			// we do not have shipping price value for above conditions.
			}
			else
			{ // else part which conditions have shipping or postage values (Level II)
			 // This part executed remaining conditions (Download , Broadcast, Dynamic Email)
			 // Application displays only postage price value when order process through List.
				if((ShippingPrice.equals("0.00") || ShippingPrice.equals("0.000") || ShippingPrice.equals("0.0000"))&&
						(OrderType.equalsIgnoreCase("Select List")))
				{ //Condition starts for postage value verification (Level III)
					String ActualPostageprice = d.findElement(Property.VSPostagePrice).getText();
			
					if(ExpectedPostagepirce.equals(ActualPostageprice))
					{
						//!System.out.println("Both postage prices are same");
					}
					else
					{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "postage price";
					System.out.println("<------ View Summary Both postage prices are different ----->"+ErrorNumber);
					System.out.println("Actual postage price value is: "+ActualPostageprice);
					System.out.println("Expected postage price value is: "+ExpectedPostagepirce);
					RW_File.WriteResult(ExpectedPostagepirce, ActualPostageprice, PageName, PriceType);
					}
				}// Postage price value verification condition completed (Level III)
		    	else
		    	{// Condition starts for Shipping price value (Level III)
		    		//String ActualPostageprice = d.findElement(Property.VSShippingPrice).getText();
		    		//if 3rd party shipping selection shipping price changes
			
		    		if(OrderBase.equals("Item")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
		    		{// Condition starts for Third party shipping value (Level IV)
		    			//Already we have covered this code
		    			/*String Actualthrdpartyshippingprice1 = d.findElement(Property.VSShippingPrice).getText();
						if(Actualthrdpartyshippingprice1.matches("\\$\\d{1,3}\\.\\d{2,4}"))
				 		{
				   		System.out.println("actual 3rd party shipping method is " +Actualthrdpartyshippingprice1);
				   		System.out.println("Its 3rd party shipping we just verified t whether it is value or not");
				 		}*/
		    		}// Condition ends Third party shipping "Already we covered" (Level IV)
		    		else if(OrderBase.equals("Item"))
		    		{// Condition starts for general shipping (Level IV)
		    			if(Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00")||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000")
							||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000") || Priceafterapplyingfulfillmentshippingmarkupfee.equals("0"))
		    			{// Condition starts for with out shipping markup fee and handling fee 
					
		    				String ActualPostageprice = d.findElement(Property.VSShippingPrice).getText();
		    				if(ExpectedShippingPrice.equals(ActualPostageprice))
		    				{
		    					//!System.out.println("upadetd shipping price is updated");
		    				}
		    				else
		    				{
		    					//System.out.println("helllo");
		    					ErrorNumber = ErrorNumber+1;
		    					captureScreenshot();
		    					String PriceType = "Shipping price";
		    					System.out.println("<------ View Summary Both Shipping prices are different ----->"+ErrorNumber);
		    					System.out.println("Actual Shipping price value is: "+ActualPostageprice);
		    					System.out.println("Expected Shipping price value is: "+ExpectedShippingPrice);
		    					RW_File.WriteResult(ExpectedShippingPrice, ActualPostageprice, PageName, PriceType);
		    				}
		    			}
		    		}// Condition ends for with out shipping handling fee and markup fee (Level IV)
		    		else if(!(Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00")||
		    				Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000")
						||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000") ||
						Priceafterapplyingfulfillmentshippingmarkupfee.equals("0")))
		    			{  // Condition starts for with shipping handling fee and Markup fee 
		    			String Actualupdatedshippinghandilingfee = d.findElement(Property.VSShippingPrice).getText();
		    			if(ExpectedShippingHandilingPrice.equals(Actualupdatedshippinghandilingfee))
		    			{
		    				//!System.out.println("upadetd shipping price is updated");
		    			}
		    			else
		    			{
			         	ErrorNumber = ErrorNumber+1;
			         	captureScreenshot();
			        	String PriceType = "Shipping price";
				        System.out.println("<-------- View Summary Both Shipping prices are different ----->"+ErrorNumber);
				        System.out.println("Actual Shipping price value is: "+Actualupdatedshippinghandilingfee);
			         	System.out.println("Expected Shipping price value is: "+ExpectedShippingHandilingPrice);
				        RW_File.WriteResult(ExpectedShippingHandilingPrice, Actualupdatedshippinghandilingfee, PageName, PriceType);
		    			}
		    		}// Condition ends for with shipping handling fee and markup fee (Level IV)
		     }// Condition ends for Shipping price value (Level III)
		   }// Condition end for with shipping or postage values (Level II)
		}// Item base code ends here (we do not have shipping value for Order base). (Level I)
	}
		if(BasePriceIncrementValue == 0)
		{
		if(DownloadPrice.equals("0") || DownloadPrice.equals("0.00") || DownloadPrice.equals("0.000") || DownloadPrice.equals("0.0000"))
		{
			
		}
		else
		{
		if(AcutalDownloadPrice.equals(ExpectedDownloadPrice))
		{
			//!System.out.println("Both Downloaded prices are same");
		}
		else
		{
			ErrorNumber = ErrorNumber+1;
			captureScreenshot();
			String PriceType = "DownloadPrice";
			System.out.println("<------View Summary Both Download Priceses are different ----->"+ErrorNumber);
			System.out.println("AcutalDownloadPrice is :"+AcutalDownloadPrice);
			System.out.println("ExpectedDownloadPrice is :"+ExpectedDownloadPrice);
			RW_File.WriteResult(ExpectedDownloadPrice, AcutalDownloadPrice, PageName, PriceType);
			
		}
		}
	}
		// Below code related to get total price value and verify expected value with actual value
		String ActualTotal = d.findElement(Property.VSTotalPrice).getText();
		String ExpectedTotal1 = null;
		// Below code execute when third party shipping is available
		if(OrderBase.equals("Item")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
		{
			String Actualthrdpartyshippingprice2 = d.findElement(Property.VSShippingPrice).getText();
			String Actualthrdpartyshippingprice3 = Actualthrdpartyshippingprice2.substring(1,Actualthrdpartyshippingprice2.length());
			// To calculate total value by using third party shipping value.
			ExpectedTotal1 = viewsummaryshippingtotal(SubTotal,Actualthrdpartyshippingprice3,Addons,DiscountcalculationfromSubTotal,OrderAmountValue,DecimalValue,Postage);
		   // Compare the Actual total value with generated total value. 
			if(ExpectedTotal1.equals(ActualTotal))
		    {
				//!System.out.println("Both Total values are same with kgs ");
		    }
		    else
		    {
		    	ErrorNumber = ErrorNumber+1;
		    	captureScreenshot();
				String PriceType = "total";
				System.out.println("<------View Summary Both Total values are different ----->"+ErrorNumber);
				System.out.println("Actual total value is :"+ActualTotal);
				System.out.println("Expected total value is :"+ExpectedTotal1);
				RW_File.WriteResult(ExpectedTotal1, ActualTotal, PageName, PriceType);
		    }
		}
		else
		{// Else part execute general shipping cases (with out third party)
		   if(ExpectedTotal.equals(ActualTotal))
		   {
			   //!System.out.println("Both Total values are same ");
		   }
		   else
		   {
			ErrorNumber = ErrorNumber+1;
			captureScreenshot();
			String PriceType = "total";
			System.out.println("<------View Summary Both Total values are different ----->"+ErrorNumber);
			System.out.println("Actual total value is :"+ActualTotal);
			System.out.println("Expected total value is :"+ExpectedTotal);
			RW_File.WriteResult(ExpectedTotal, ActualTotal, PageName, PriceType);
		   }
		}
		}catch (Exception e){
			ErrorNumber = ErrorNumber+1;
			captureScreenshot();
	    	  e.printStackTrace();
	      }		
	}
	
	public static void ShoppingCartPriceInformation(String Quantity,String SubTotal, String ItemPerPrice, String Discount, String Addons,
			String Postage, String TotalPrice, String DiscountPercentage, String ShippingPrice,
			String OrderType, String TestCase, String TestStep, String Parameters, String ProdutType,String OrderBase,String Weighttype,
			String EnablePromotionsORDiscounts,String DiscountcalculationfromSubTotal,String Priceafterapplyingfulfillmentshippingmarkupfee,
			String OrderAmountValue,String LandingPageOption,String LandingPageProduct,String LandingPageProductPP,String LandingPageDiscount,
			String LandingPageDiscountValue,String SubtotalAfterPurlPrice,String DiscountCalculationFromSubTotal,String DecimalValue) throws InterruptedException {
		try{
			
			MouseAdjFunction();
			if(LandingPageOption.equalsIgnoreCase("YES")){ 
				String PageName = "ShoppingCart";
		        String ShoppingCartTotal = d.findElement(Property.SCartTotal).getText();	
		        String ExpectedShippingHandilingPrice = null;
		        // Generated Expected shipping handling price based on order base
		        if(OrderBase.equals("Order"))
		        {// In order base shipping handling price or markup price not effected in shopping cart page.
		        	ExpectedShippingHandilingPrice = Config.Currency+ShippingPrice;
		        }
		        else
		        {// Item base
		        	ExpectedShippingHandilingPrice = Config.Currency+Priceafterapplyingfulfillmentshippingmarkupfee;
		        }
				int Quantity1 = Double.valueOf(Quantity).intValue();
				// Generated the expected Item price value
				String ExpectedItemPrice = null;
				String ExpectedLandingPageProductprice=null;
				if(OrderType.equals("DynShipTOMultipleLocations") || OrderType.equals("StaticShipTOMultipleLocations")
						|| OrderType.equals("StaticInventoryShipTOMultipleLocations"))
				{// In shopping cart Item price displays "-" value for the above conditions
					ExpectedItemPrice ="-";	
				}
				else
				{// In other all conditions directly Item price value displays.
					ExpectedItemPrice =Config.Currency+ItemPerPrice;
					ExpectedLandingPageProductprice =Config.Currency+LandingPageProductPP;

				}
				// Generated exepected Discount value in shopping cart page based on amount or percentage
				String ExpectedDiscount = null;
				String ExpectedLandingDiscount = null;
				if(DiscountPercentage.equals("N"))
				{
					ExpectedDiscount = Config.Currency+Discount+"";
				}
				else if(Discount.equals("0") || Discount.equals("0.0") || Discount.equals("0.00") ||
						Discount.equals("0.000") || Discount.equals("0.0000"))
				{
					ExpectedDiscount = Config.Currency+Discount+"";
				}
				else
				{
					ExpectedDiscount = Discount+Config.PercentageSymbol;
				}
				if(LandingPageDiscount.equals("N"))
				{
					ExpectedLandingDiscount = Config.Currency+LandingPageDiscountValue+"";
				}
				else if(LandingPageDiscountValue.equals("0") || LandingPageDiscountValue.equals("0.0") || LandingPageDiscountValue.equals("0.00") ||
						LandingPageDiscountValue.equals("0.000") || LandingPageDiscountValue.equals("0.0000"))
				{
					ExpectedLandingDiscount = Config.Currency+LandingPageDiscountValue+"";
				}
				else
				{
					ExpectedLandingDiscount = LandingPageDiscountValue+Config.PercentageSymbol;
				}
				String ExpectedAddons = Config.Currency+Addons;
				String ExpectedPostagepirce = Config.Currency+Postage;
				String ExpectedShippingPrice = Config.Currency+ShippingPrice;
				String ExpectedTotal =null;
				if(DiscountPercentage.equals("Y"))
				{
					if((Addons.equals("0") || Addons.equals("0.0") || Addons.equals("0.00") ||
							Addons.equals("0.000") || Addons.equals("0.0000")) &&( Postage.equals("0") || Postage.equals("0.0") || 
							Postage.equals("0.00") ||Postage.equals("0.000") || Postage.equals("0.0000")) &&( ShippingPrice.equals("0") ||
							ShippingPrice.equals("0.0") || ShippingPrice.equals("0.00") ||ShippingPrice.equals("0.000") || ShippingPrice.equals("0.0000")))
							{
				     double discount1 = Double.valueOf(DiscountCalculationFromSubTotal).doubleValue();
				     double discount2 = Double.valueOf(SubTotal).doubleValue();
				     double discount3 = discount2-discount1;
				     String discount4 = ""+discount3;
				     String discount5 = Decimalsetting(discount4, DecimalValue);
					   ExpectedTotal = Config.Currency+discount5;
					}
					else{
						double discount1 = Double.valueOf(DiscountCalculationFromSubTotal).doubleValue();
						double discount2 = Double.valueOf(SubTotal).doubleValue();
						double Postage1 = Double.valueOf(Postage).doubleValue();
						double Addons1 = Double.valueOf(Addons).doubleValue();
						double Shipping1 = Double.valueOf(ShippingPrice).doubleValue();
						double discount3 = discount2-discount1+Addons1+Postage1+Shipping1;
						String discount4 = ""+discount3;
						String discount5 = Decimalsetting(discount4, DecimalValue);
							   ExpectedTotal = Config.Currency+discount5;
					}
				}
				
				else{
					
					if(Addons.equals("0") || Addons.equals("0.0") || Addons.equals("0.00") ||
							Addons.equals("0.000") || Addons.equals("0.0000")||Postage.equals("0") || Postage.equals("0.0") || 
							Postage.equals("0.00") ||Postage.equals("0.000") || Postage.equals("0.0000"))
					   {
						
						double discount1 = Double.valueOf(DiscountCalculationFromSubTotal).doubleValue();
						String discount2 = ""+discount1;
						String discount3 = Decimalsetting(discount2, DecimalValue);
							   ExpectedTotal = Config.Currency+discount3;
					
					    }
						else{
					double discount1 = Double.valueOf(DiscountCalculationFromSubTotal).doubleValue();
					double discount2 = Double.valueOf(Postage).doubleValue();
					double discount3 = Double.valueOf(Addons).doubleValue();
					double discount4 = discount1+discount2+discount3;
					String discount5 = ""+discount4;
					String discount6 = Decimalsetting(discount5, DecimalValue);
					   ExpectedTotal = Config.Currency+discount6;
					}
					
						
					
				}
				String ExpectedLandingpageTotal = Config.Currency+SubtotalAfterPurlPrice;
				String ExpectedTotal2 = Config.Currency+TotalPrice;
				// Get the Actual quantity and compare with expected quantity
				String ActualQuantity = d.findElement(Property.SCartQuantity).getText();
				int ActualQuantity1 = Double.valueOf(ActualQuantity).intValue();
				if(Quantity1 == ActualQuantity1)
				{
					//!System.out.println("Both quantitys are same ");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "Quantity";
					System.out.println("<----- Shopping cart Both quantitys are different(Landing Page) ----->"+ErrorNumber);
					System.out.println("Actual quantity is : "+ActualQuantity);
					System.out.println("Expected quantity is : "+Quantity);
					RW_File.WriteResult(Quantity, ActualQuantity, PageName, PriceType);
				}
				// Get the Item price based on product type conditions
				String ActualItemPerPrice = null;
				String ActualLandingPerPrice = null;
				if(ProdutType.equals("Broadcast") || ProdutType.equals("Dynamic Email") || ProdutType.equals("Dynamic Print"))
				{
					if (Addons.equals("0") || Addons.equals("0.0") || Addons.equals("0.00") ||
							Addons.equals("0.000") || Addons.equals("0.0000")){
					ActualItemPerPrice = d.findElement(Property.SCartItemPrice1).getText();
					ActualLandingPerPrice=d.findElement(Property.SCartLandingPrice1).getText();
					}
					else{
						
						ActualItemPerPrice = d.findElement(Property.SCartItemPrice2).getText();
						ActualLandingPerPrice=d.findElement(Property.SCartLandingPrice2).getText();
					}
					
				}
				//Compare the Expected item price with actual item price.
				if(ExpectedItemPrice.equals(ActualItemPerPrice))
				{
					//!System.out.println("Both Item prices are same");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "Item price";
					System.out.println("<----- Shopping cart Both item prices are different(Landing Page) -------->"+ErrorNumber);
					System.out.println("Actual Item price is: "+ActualItemPerPrice);
					System.out.println("Expected Item price is: "+ExpectedItemPrice);
					RW_File.WriteResult(ExpectedItemPrice, ActualItemPerPrice, PageName, PriceType);
				}
				if(ExpectedLandingPageProductprice.equals(ActualLandingPerPrice))
				{
					//!System.out.println("Both Item prices are same");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "Item price";
					System.out.println("<----- Shopping cart Both Landin page item prices are different(Landing Page) -------->"+ErrorNumber);
					System.out.println("Actual Landing Item price is: "+ActualLandingPerPrice);
					System.out.println("Expected Landing Item price is: "+ExpectedLandingPageProductprice);
					RW_File.WriteResult(ExpectedLandingPageProductprice, ActualLandingPerPrice, PageName, PriceType);
				}
				// Get discount value based on enable promotionsordiscounts check box status
				// We do not have discount value when enable promotions or discounts check box false condition
				if(EnablePromotionsORDiscounts.equals("ON"))
				{// If Enable promotions or discounts is true (in this case only discount displays)
					String ActualDiscount = null;
					String ActualLandingDiscount = null;

					// Get discount value based on order process type
					if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") || 
						 ProdutType.equals("Dynamic Email") || ProdutType.equals("Dynamic Print"))
					{
						if((Addons.equals("0") || Addons.equals("0.0") || Addons.equals("0.00") ||
							Addons.equals("0.000") || Addons.equals("0.0000")) &&( Postage.equals("0") || Postage.equals("0.0") || 
							Postage.equals("0.00") ||Postage.equals("0.000") || Postage.equals("0.0000")) &&( ShippingPrice.equals("0") ||
							ShippingPrice.equals("0.0") || ShippingPrice.equals("0.00") ||ShippingPrice.equals("0.000") || ShippingPrice.equals("0.0000")))
							{
							
							ActualDiscount = d.findElement(Property.SCartDiscount1).getText();
							ActualLandingDiscount= d.findElement(Property.SCartLandingDiscount1).getText();
							}
						else{
							ActualDiscount = d.findElement(Property.SCartDiscount2).getText();
							ActualLandingDiscount= d.findElement(Property.SCartLandingDiscount2).getText();
						}
					}
					else
					{//
						ActualDiscount = d.findElement(Property.SCartDiscount).getText();
						ActualLandingDiscount= d.findElement(Property.SCartLandingDiscount1).getText();
					}
					// Compare expected discount value with Actual discount value
					if(ExpectedDiscount.equals(ActualDiscount))
					{
						//!System.out.println("Both Discount prices are same ");
					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
					String PriceType = "Discount price";
					System.out.println("<-------- Shopping cart Both Discount prices are different(Landing Page) ------>"+ErrorNumber);
					System.out.println("Actual Discount price is : "+ActualDiscount);
					System.out.println("Expected Discount price is : "+ExpectedDiscount);
					RW_File.WriteResult(ExpectedDiscount, ActualDiscount, PageName, PriceType);
					}
					if(ExpectedLandingDiscount.equals(ActualLandingDiscount))
					{
						//!System.out.println("Both Discount prices are same ");
					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
					String PriceType = "Discount price";
					System.out.println("<-------- Shopping cart Both Landing Discount prices are different(Landing Page) ------>"+ErrorNumber);
					System.out.println("Actual Landing Discount price is : "+ActualLandingDiscount);
					System.out.println("Expected Landing Discount price is : "+ExpectedLandingDiscount);
					RW_File.WriteResult(ExpectedLandingDiscount, ActualLandingDiscount, PageName, PriceType);
					}
				}// enable promotions or discounts on if condition completed.
				
				String ActualAddons = null;
				 // Get the addon price value based on product
				 if(ProdutType.equals("Broadcast") || ProdutType.equals("Dynamic Email"))
				 {
					 ActualAddons = d.findElement(Property.ScartAddonPriceBroadcast).getText();
				 }
				 else
				 {
					 ActualAddons = d.findElement(Property.SCartAddonPrice1).getText();
				 }
				 // Compare the expected addon price value with actual addon price value
				if(ExpectedAddons.equals(ActualAddons))
				{
					//!System.out.println("Both Addon prices are same");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "Addon price";
					System.out.println("<-------- Shopping cart Both Addon prices are different(Landing Page) ------->"+ErrorNumber);
					System.out.println("Actual Addon price is : "+ActualAddons);
					System.out.println("Expected Addon price is : "+ExpectedAddons);
					RW_File.WriteResult(ExpectedAddons, ActualAddons, PageName, PriceType);
				}
				// Get the postage price value based on order process type
				String ActualPostageprice = null;
				if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") || 
						 ProdutType.equals("Dynamic Email"))
				{
					ActualPostageprice = d.findElement(Property.SCartDownLoadPostagePrice).getText();
					//System.out.println("dynadownload");
				}
				else
				{
					ActualPostageprice = d.findElement(Property.SCartPostagePrice).getText();
				}
				// Any time we have only shipping or postage value, so if shipping value is zero in that time we verify the postage value
				if((ShippingPrice.equals("0") || ShippingPrice.equals("0.00") || ShippingPrice.equals("0.000") || ShippingPrice.equals("0.0000")) && (OrderType.equalsIgnoreCase("select list")))
				{// Condition postage verification starts here
					if(ExpectedPostagepirce.equals(ActualPostageprice))
					{
						//!System.out.println("Both postage prices are same in shopping cart page");
					}
					else
					{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "postage price";
					System.out.println("<-------- Shopping cart Both postage prices are different(Landing Page) -------->"+ErrorNumber);
					System.out.println("Actual postage price value is: "+ActualPostageprice);
					System.out.println("Expected postage price value is: "+ExpectedPostagepirce);
					RW_File.WriteResult(ExpectedPostagepirce, ActualPostageprice, PageName, PriceType);
					}
				}// Condition postage verification ends here.
				else if(OrderType.equals("DynDownloadShipping")||OrderType.equals("DynShipTOMultipleLocations")||OrderType.equals("ShipToMyAddress")
						||OrderType.equals("StaticDownloadShipping")||OrderType.equals("StaticShipTOMultipleLocations")||
						OrderType.equals("StaticInventoryShipTOMultipleLocations")||OrderType.equals("DynDropShipment with List"))
				{// The above if checks only shipping related order process types (Level I)
					//if 3rd party shipping selection shipping price changes in sc page
					//System.out.println("hiiii");
				  if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
				    {// Condition for Third party shipping (Level II)
					  String Actualthrdpartyshippingprice2 = d.findElement(Property.SCartPostagePrice).getText();
					  if(Actualthrdpartyshippingprice2.matches("\\$\\d{1,3}\\.\\d{2,4}"))
						 {
						   //System.out.println("actual 3rd party shipping method is in sc page" +Actualthrdpartyshippingprice2);
						   //System.out.println("Its 3rd party shipping we just verified in sc page whether it is value or not");
						 }
					}// Condition for Third party shipping completed. (Level II)
			      	else if(OrderBase.equals("Item"))
				   {// Condition for Order base (why we maintain this condition in this page, because we do not have shipping value in
			      		// Shopping cart page for Order base). (Level II)
			    	 if((Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00")||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000")
						||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000") || Priceafterapplyingfulfillmentshippingmarkupfee.equals("0")))
			    	 {// Condition for with out shipping handling fee for markup fee (Level III)
					 String ActualPostageprice1 = d.findElement(Property.SCartPostagePrice1).getText();
					 if(ExpectedShippingPrice.equals(ActualPostageprice1))
				     {
						 //!System.out.println("Both postage prices are same");
				     }
					else
					 {
								//System.out.println("fullfillment");
								ErrorNumber = ErrorNumber+1;
								captureScreenshot();
					        	String PriceType = "Shipping price";
							    System.out.println("<-------- Shopping cart Both Shipping prices are different(Landing Page) -------->"+ErrorNumber);
						        System.out.println("Actual Shipping price value is: "+ActualPostageprice1);
					         	System.out.println("Expected Shipping price value is: "+ExpectedShippingPrice);
						        RW_File.WriteResult(ExpectedShippingPrice, ActualPostageprice, PageName, PriceType);
					 }
			    	}// Condition for with out shipping handling fee or markup fee (Level III)
				  }// Item base condition completed (Level II) 
			      else if(!(Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00")||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000")
					||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000") ||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0")))
				  {// condition for with shipping handling fee or markup fee condition completed. (Level II)
			    	 String Actualupdatedshippinghandilingfee = d.findElement(Property.SCartPostagePrice1).getText();
			    	 if(ExpectedShippingHandilingPrice.equals(Actualupdatedshippinghandilingfee))
					  {
			    		 //!System.out.println("upadetd shipping price is updated in sc page");
					  }
					   else
					     {
			           //  System.out.println("In shopping cart page common grid cell both postage and shipping");
					    ErrorNumber = ErrorNumber+1;
					    captureScreenshot();
					    String PriceType = "Shipping price";
					    System.out.println("<-------- Shopping cart Both Shipping prices are different(Landing Page) -------->"+ErrorNumber);
					    System.out.println("Actual Shipping price value is: "+Actualupdatedshippinghandilingfee);  
					    System.out.println("Expected shipping price value is: "+ExpectedShippingHandilingPrice);
					    RW_File.WriteResult(ExpectedShippingHandilingPrice, Actualupdatedshippinghandilingfee, PageName, PriceType);
					     }
				  }// COndition for shipping handlig fee for markup fee condition completed (Level II)
				} // The above if checks only shipping related order process types (Level I)
				
				if(EnablePromotionsORDiscounts.equals("ON"))
				{
				String ActualTotal = null;
				String ExpectedTotal1 = null;
				String ActualLandingTotal = null;
				// Generate shopping cart total amount
				if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") || 
						ProdutType.equals("Dynamic Email") || ProdutType.equals("Dynamic Print"))
				{
					if((Addons.equals("0") || Addons.equals("0.0") || Addons.equals("0.00") ||
							Addons.equals("0.000") || Addons.equals("0.0000")) &&( Postage.equals("0") || Postage.equals("0.0") || 
							Postage.equals("0.00") ||Postage.equals("0.000") || Postage.equals("0.0000")) &&( ShippingPrice.equals("0") ||
							ShippingPrice.equals("0.0") || ShippingPrice.equals("0.00") ||ShippingPrice.equals("0.000") || ShippingPrice.equals("0.0000")))
							{
					ActualTotal = d.findElement(Property.SCartAmount1).getText();
					ActualLandingTotal = d.findElement(Property.SCartLandingAmount1).getText();
					}
					else{
						ActualTotal = d.findElement(Property.SCartAmount11).getText();
						ActualLandingTotal = d.findElement(Property.SCartLandingAmount11).getText();
					}

				}
				else
				{
					ActualTotal = d.findElement(Property.SCartAmount2).getText();
					ActualLandingTotal = d.findElement(Property.SCartLandingAmount2).getText();
					
				}
				// Below code related to whenever we have third party shipping (in this we have calculate total value)
				if(OrderBase.equals("Item")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
				{
					String Actualthrdpartyshippingprice2 = d.findElement(Property.SCartPostagePrice).getText();
					String Actualthrdpartyshippingprice3 = Actualthrdpartyshippingprice2.substring(1,Actualthrdpartyshippingprice2.length());
					ExpectedTotal1 = viewsummaryshippingtotal(SubTotal,Actualthrdpartyshippingprice3,Addons,DiscountcalculationfromSubTotal,OrderAmountValue,DecimalValue,Postage);
				}
				// Compare shopping cart generated total value with Actual total value 
				if(OrderBase.equals("Item")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
				{// COndition for thrid party shipping is available
					if(ExpectedTotal1.equals(ActualTotal))
					{
						//!System.out.println("Both Total values are same no need to enter into else ");
					}
					else
					{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "GridTotal";
					System.out.println("<------Shopping cart  Both Total values are different1(Landing Page) -------->"+ErrorNumber);
					System.out.println("Actual total value is :"+ActualTotal);
					System.out.println("Expected total value is :"+ExpectedTotal1);
					RW_File.WriteResult(ExpectedTotal1, ActualTotal, PageName, PriceType);
					}	
					if(ExpectedLandingpageTotal.equals(ActualLandingTotal))
					{
						//!System.out.println("Both Total values are same no need to enter into else ");
					}
					else
					{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "GridTotal";
					System.out.println("<---------Shopping cart  Both Landing page Total values are different 1-------->"+ErrorNumber);
					System.out.println("Actual Landing page total value is 1:"+ActualLandingTotal);
					System.out.println("Expected Landing page total value is 1:"+ExpectedLandingpageTotal);
					RW_File.WriteResult(ExpectedLandingpageTotal, ActualLandingTotal, PageName, PriceType);
					}	
				}
				else
				{// Condition for general total value (with out third party shipping value)
					if(ExpectedTotal.equals(ActualTotal))
					{
						//!System.out.println("Both Total values are same no need to enter into else ");
					}
					else
					{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "GridTotal";
					System.out.println("<---------Shopping cart  Both Total values are different2 (Landing Page)-------->"+ErrorNumber);
					System.out.println("Actual total value is :"+ActualTotal);
					System.out.println("Expected total value is :"+ExpectedTotal);
					RW_File.WriteResult(ExpectedTotal, ActualTotal, PageName, PriceType);
					}	
					if(ExpectedLandingpageTotal.equals(ActualLandingTotal))
					{
						//!System.out.println("Both Total values are same no need to enter into else ");
					}
					else
					{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "GridTotal";
					System.out.println("<---------Shopping cart  Both Landing page Total values are different 2(Landing Page)-------->"+ErrorNumber);
					System.out.println("Actual Landing page total value is 2:"+ActualLandingTotal);
					System.out.println("Expected Landing page total value is 2:"+ExpectedLandingpageTotal);
					RW_File.WriteResult(ExpectedLandingpageTotal, ActualLandingTotal, PageName, PriceType);
					}	
				}
			  }// Enable promotion or discounts on condition completed
				else
				{// Enable promotion or discounts off condition starts
					// Get the total value based on order process type
					String ActualTotal = null;
					String ActualLandingTotal=null;
					if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") || 
							ProdutType.equals("Dynamic Email"))
					{
						ActualTotal = d.findElement(Property.SCartDownLoadAmountOFF1).getText();
						ActualLandingTotal=d.findElement(Property.SCartLandingDownLoadAmountOFF1).getText();
					}
					else
					{
						ActualTotal = d.findElement(Property.SCartAmountOFF1).getText();
						ActualLandingTotal=d.findElement(Property.SCartLandingAmountOFF1).getText();
					}
					//Verify the Actual total value with Expected total value
					if(ExpectedTotal.equals(ActualTotal))
					{
						//!System.out.println("Both Total values are same ");
					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "GridTotal";
						System.out.println("<--------Shopping cart  Both Total values are different3(Landing Page) ------->"+ErrorNumber);
						System.out.println("Actual total value is :"+ActualTotal);
						System.out.println("Expected total value is :"+ExpectedTotal);
						RW_File.WriteResult(ExpectedTotal, ActualTotal, PageName, PriceType);
					}	
					if(ExpectedLandingpageTotal.equals(ActualLandingTotal))
					{
						//!System.out.println("Both Total values are same no need to enter into else ");
					}
					else
					{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "GridTotal";
					System.out.println("<---------Shopping cart  Both Landing page Total values are different 3(Landing Page)-------->"+ErrorNumber);
					System.out.println("Actual Landing page total value is 3:"+ActualLandingTotal);
					System.out.println("Expected Landing page total value is 3:"+ExpectedLandingpageTotal);
					RW_File.WriteResult(ExpectedLandingpageTotal, ActualLandingTotal, PageName, PriceType);
					}	
				}// Enable promotion or discounts off condition completed.
				
				// Generate the grand total value when we have third party shipping
				String ExpectedTotal3 = null;
				if(OrderBase.equals("Item")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
				{
					String Actualthrdpartyshippingprice2 = d.findElement(Property.SCartPostagePrice).getText();
					String Actualthrdpartyshippingprice3 = Actualthrdpartyshippingprice2.substring(1,Actualthrdpartyshippingprice2.length());
					ExpectedTotal3 = viewsummaryshippingtotal(SubTotal,Actualthrdpartyshippingprice3,Addons,DiscountcalculationfromSubTotal,OrderAmountValue,DecimalValue,Postage);
				}
				// Compare the total price value when we have third party shipping value
				if(OrderBase.equals("Item")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
				{
					if((ExpectedTotal3.equals(ShoppingCartTotal)))
					{
						//!System.out.println("Both Total values are same ");
					}
					else
					{
					//System.out.println("3333");
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "total";
					System.out.println("<-------- Shopping cart Both Total values are different (Landing Page)------------>"+ErrorNumber);
					System.out.println("Actual total value is :"+ShoppingCartTotal);
					System.out.println("Expected total value is :"+ExpectedTotal3);
					RW_File.WriteResult(ExpectedTotal3, ShoppingCartTotal, PageName, PriceType);
					
					}
				}
				else
				{// Compare total value general condition (with out third party shipping)
					if((ExpectedTotal2.equals(ShoppingCartTotal)))
					{
						//!System.out.println("Both Total values are same ");
					}
					else
					{
						//System.out.println("3333");
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "total";
						System.out.println("<---------- Shopping cart Both Total values are different (Landing Page)---------->"+ErrorNumber);
						System.out.println("Actual total value is :"+ShoppingCartTotal);
						System.out.println("Expected total value is :"+ExpectedTotal2);
						RW_File.WriteResult(ExpectedTotal2, ShoppingCartTotal, PageName, PriceType);
						
					}
				}// else part completed.
				//for order base 3rd party shipping services in order check out page and user handiling fee
				WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
				
				FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
				 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
				 waitfl.pollingEvery(1, TimeUnit.SECONDS);
				 waitfl.ignoring(NoSuchElementException.class);
				 waitfl.ignoring(StaleElementReferenceException.class);
				if((Weighttype.equalsIgnoreCase("KGS")||Weighttype.equalsIgnoreCase("LBS"))&&OrderBase.equalsIgnoreCase("Base") )
				{
					waitfl.until(ExpectedConditions.elementToBeClickable(Property.ordershippingdropdown));
					d.findElement(Property.ordershippingdropdown).click();
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.ordershippingmethodselection).click();
					tec.AC40.Common.Wait.wait5Second();
					
				}
				
			}
			else{
			String PageName = "ShoppingCart";
			MouseAdjFunction();
	        String ShoppingCartTotal = d.findElement(Property.SCartTotal).getText();	
	        String ExpectedShippingHandilingPrice = null;
	        // Generated Expected shipping handling price based on order base
	        if(OrderBase.equals("Order"))
	        {// In order base shipping handling price or markup price not effected in shopping cart page.
	        	ExpectedShippingHandilingPrice = Config.Currency+ShippingPrice;
	        }
	        else
	        {// Item base
	        	ExpectedShippingHandilingPrice = Config.Currency+Priceafterapplyingfulfillmentshippingmarkupfee;
	        }
			int Quantity1 = Double.valueOf(Quantity).intValue();
			// Generated the expected Item price value
			String ExpectedItemPrice = null;
			if(OrderType.equals("DynShipTOMultipleLocations") || OrderType.equals("StaticShipTOMultipleLocations")
					|| OrderType.equals("StaticInventoryShipTOMultipleLocations"))
			{// In shopping cart Item price displays "-" value for the above conditions
				ExpectedItemPrice ="-";	
			}
			else
			{// In other all conditions directly Item price value displays.
				ExpectedItemPrice =Config.Currency+ItemPerPrice;	
			}
			// Generated exepected Discount value in shopping cart page based on amount or percentage
			String ExpectedDiscount = null;
			
			if(DiscountPercentage.equals("N"))
			{
				ExpectedDiscount = Config.Currency+Discount+"";
			}
			else if(Discount.equals("0") || Discount.equals("0.0") || Discount.equals("0.00") ||
					Discount.equals("0.000") || Discount.equals("0.0000"))
			{
				ExpectedDiscount = Config.Currency+Discount+"";
			}
			else
			{
				ExpectedDiscount = Discount+Config.PercentageSymbol;
			}
			
			String ExpectedAddons = Config.Currency+Addons;
			String ExpectedPostagepirce = Config.Currency+Postage;
			String ExpectedShippingPrice = Config.Currency+ShippingPrice;
			String ExpectedTotal = Config.Currency+TotalPrice;
			
			String ExpectedTotal2 = ExpectedTotal;
			// Get the Actual quantity and compare with expected quantity
			String ActualQuantity = d.findElement(Property.SCartQuantity).getText();
			int ActualQuantity1 = Double.valueOf(ActualQuantity).intValue();
			if(Quantity1 == ActualQuantity1)
			{
				//!System.out.println("Both quantitys are same ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Quantity";
				System.out.println("<----- Shopping cart Both quantitys are different ----->"+ErrorNumber);
				System.out.println("Actual quantity is : "+ActualQuantity);
				System.out.println("Expected quantity is : "+Quantity);
				RW_File.WriteResult(Quantity, ActualQuantity, PageName, PriceType);
			}
			// Get the Item price based on product type conditions
			String ActualItemPerPrice = null;
			if(ProdutType.equals("Broadcast") || ProdutType.equals("Dynamic Email"))
			{
				ActualItemPerPrice = d.findElement(Property.ScartItemPriceBroadcast).getText();
			}
			else
			{
				ActualItemPerPrice = d.findElement(Property.SCartItemPrice).getText();
			}
			//Compare the Expected item price with actual item price.
			if(ExpectedItemPrice.equals(ActualItemPerPrice))
			{
				//!System.out.println("Both Item prices are same");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Item price";
				System.out.println("<----- Shopping cart Both item prices are different -------->"+ErrorNumber);
				System.out.println("Actual Item price is: "+ActualItemPerPrice);
				System.out.println("Expected Item price is: "+ExpectedItemPrice);
				RW_File.WriteResult(ExpectedItemPrice, ActualItemPerPrice, PageName, PriceType);
			}
			
			// Get discount value based on enable promotionsordiscounts check box status
			// We do not have discount value when enable promotions or discounts check box false condition
			if(EnablePromotionsORDiscounts.equals("ON"))
			{// If Enable promotions or discounts is true (in this case only discount displays)
				String ActualDiscount = null;
				// Get discount value based on order process type
				if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") || 
					 ProdutType.equals("Dynamic Email"))
				{
					ActualDiscount = d.findElement(Property.SCartDownLoadDiscount).getText();
				}
				else
				{//
					ActualDiscount = d.findElement(Property.SCartDiscount).getText();
				}
				// Compare expected discount value with Actual discount value
				if(ExpectedDiscount.equals(ActualDiscount))
				{
					//!System.out.println("Both Discount prices are same ");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
				String PriceType = "Discount price";
				System.out.println("<-------- Shopping cart Both Discount prices are different ------>"+ErrorNumber);
				System.out.println("Actual Discount price is : "+ActualDiscount);
				System.out.println("Expected Discount price is : "+ExpectedDiscount);
				RW_File.WriteResult(ExpectedDiscount, ActualDiscount, PageName, PriceType);
				}
			}// enable promotions or discounts on if condition completed.
			
			String ActualAddons = null;
			 // Get the addon price value based on product
			 if(ProdutType.equals("Broadcast") || ProdutType.equals("Dynamic Email"))
			 {
				 ActualAddons = d.findElement(Property.ScartAddonPriceBroadcast).getText();
			 }
			 else
			 {
				 ActualAddons = d.findElement(Property.SCartAddonPrice).getText();
			 }
			 // Compare the expected addon price value with actual addon price value
			if(ExpectedAddons.equals(ActualAddons))
			{
				//!System.out.println("Both Addon prices are same");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Addon price";
				System.out.println("<-------- Shopping cart Both Addon prices are different ------->"+ErrorNumber);
				System.out.println("Actual Addon price is : "+ActualAddons);
				System.out.println("Expected Addon price is : "+ExpectedAddons);
				RW_File.WriteResult(ExpectedAddons, ActualAddons, PageName, PriceType);
			}
			// Get the postage price value based on order process type
			String ActualPostageprice = null;
			if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") || 
					 ProdutType.equals("Dynamic Email"))
			{
				ActualPostageprice = d.findElement(Property.SCartDownLoadPostagePrice).getText();
				//System.out.println("dynadownload");
			}
			else
			{
				ActualPostageprice = d.findElement(Property.SCartPostagePrice).getText();
			}
			// Any time we have only shipping or postage value, so if shipping value is zero in that time we verify the postage value
			if((ShippingPrice.equals("0") || ShippingPrice.equals("0.00") || ShippingPrice.equals("0.000") || ShippingPrice.equals("0.0000")) && (OrderType.equalsIgnoreCase("select list")))
			{// Condition postage verification starts here
				if(ExpectedPostagepirce.equals(ActualPostageprice))
				{
					//!System.out.println("Both postage prices are same in shopping cart page");
				}
				else
				{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "postage price";
				System.out.println("<-------- Shopping cart Both postage prices are different -------->"+ErrorNumber);
				System.out.println("Actual postage price value is: "+ActualPostageprice);
				System.out.println("Expected postage price value is: "+ExpectedPostagepirce);
				RW_File.WriteResult(ExpectedPostagepirce, ActualPostageprice, PageName, PriceType);
				}
			}// Condition postage verification ends here.
			else if(OrderType.equals("DynDownloadShipping")||OrderType.equals("DynShipTOMultipleLocations")||OrderType.equals("ShipToMyAddress")
					||OrderType.equals("StaticDownloadShipping")||OrderType.equals("StaticShipTOMultipleLocations")||
					OrderType.equals("StaticInventoryShipTOMultipleLocations")||OrderType.equals("DynDropShipment with List"))
			{// The above if checks only shipping related order process types (Level I)
				//if 3rd party shipping selection shipping price changes in sc page
				//System.out.println("hiiii");
			  if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
			    {// Condition for Third party shipping (Level II)
				  String Actualthrdpartyshippingprice2 = d.findElement(Property.SCartPostagePrice).getText();
				  if(Actualthrdpartyshippingprice2.matches("\\$\\d{1,3}\\.\\d{2,4}"))
					 {
					   //System.out.println("actual 3rd party shipping method is in sc page" +Actualthrdpartyshippingprice2);
					   //System.out.println("Its 3rd party shipping we just verified in sc page whether it is value or not");
					 }
				}// Condition for Third party shipping completed. (Level II)
		      	else if(OrderBase.equals("Item"))
			   {// Condition for Order base (why we maintain this condition in this page, because we do not have shipping value in
		      		// Shopping cart page for Order base). (Level II)
		    	 if((Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00")||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000")
					||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000") || Priceafterapplyingfulfillmentshippingmarkupfee.equals("0")))
		    	 {// Condition for with out shipping handling fee for markup fee (Level III)
				 String ActualPostageprice1 = d.findElement(Property.SCartPostagePrice).getText();
				 if(ExpectedShippingPrice.equals(ActualPostageprice))
			     {
					 //!System.out.println("Both postage prices are same");
			     }
				else
				 {
							//System.out.println("fullfillment");
							ErrorNumber = ErrorNumber+1;
							captureScreenshot();
				        	String PriceType = "Shipping price";
						    System.out.println("<-------- Shopping cart Both Shipping prices are different -------->"+ErrorNumber);
					        System.out.println("Actual Shipping price value is: "+ActualPostageprice1);
				         	System.out.println("Expected Shipping price value is: "+ExpectedShippingPrice);
					        RW_File.WriteResult(ExpectedShippingPrice, ActualPostageprice, PageName, PriceType);
				 }
		    	}// Condition for with out shipping handling fee or markup fee (Level III)
			  }// Item base condition completed (Level II) 
		      else if(!(Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00")||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000")
				||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000") ||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0")))
			  {// condition for with shipping handling fee or markup fee condition completed. (Level II)
		    	 String Actualupdatedshippinghandilingfee = d.findElement(Property.SCartPostagePrice).getText();
		    	 if(ExpectedShippingHandilingPrice.equals(Actualupdatedshippinghandilingfee))
				  {
		    		 //!System.out.println("upadetd shipping price is updated in sc page");
				  }
				   else
				     {
		           //  System.out.println("In shopping cart page common grid cell both postage and shipping");
				    ErrorNumber = ErrorNumber+1;
				    captureScreenshot();
				    String PriceType = "Shipping price";
				    System.out.println("<-------- Shopping cart Both Shipping prices are different -------->"+ErrorNumber);
				    System.out.println("Actual Shipping price value is: "+Actualupdatedshippinghandilingfee);  
				    System.out.println("Expected shipping price value is: "+ExpectedShippingHandilingPrice);
				    RW_File.WriteResult(ExpectedShippingHandilingPrice, Actualupdatedshippinghandilingfee, PageName, PriceType);
				     }
			  }// COndition for shipping handlig fee for markup fee condition completed (Level II)
			} // The above if checks only shipping related order process types (Level I)
			
			if(EnablePromotionsORDiscounts.equals("ON"))
			{
			String ActualTotal = null;
			String ExpectedTotal1 = null;
			// Generate shopping cart total amount
			if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") || 
					ProdutType.equals("Dynamic Email"))
			{
				ActualTotal = d.findElement(Property.SCartDownLoadAmount).getText();
			}
			else{
							ActualTotal = d.findElement(Property.SCartAmount).getText();
			}
			// Below code related to whenever we have third party shipping (in this we have calculate total value)
			if(OrderBase.equals("Item")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
			{
				String Actualthrdpartyshippingprice2 = d.findElement(Property.SCartPostagePrice).getText();
				String Actualthrdpartyshippingprice3 = Actualthrdpartyshippingprice2.substring(1,Actualthrdpartyshippingprice2.length());
				ExpectedTotal1 = viewsummaryshippingtotal(SubTotal,Actualthrdpartyshippingprice3,Addons,DiscountcalculationfromSubTotal,OrderAmountValue,DecimalValue,Postage);
			}
			// Compare shopping cart generated total value with Actual total value 
			if(OrderBase.equals("Item")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
			{// COndition for thrid party shipping is available
				if(ExpectedTotal1.equals(ActualTotal))
				{
					//!System.out.println("Both Total values are same no need to enter into else ");
				}
				else
				{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "GridTotal";
				System.out.println("<------Shopping cart  Both Total values are different -------->"+ErrorNumber);
				System.out.println("Actual total value is :"+ActualTotal);
				System.out.println("Expected total value is :"+ExpectedTotal1);
				RW_File.WriteResult(ExpectedTotal1, ActualTotal, PageName, PriceType);
				}	
			}
			else
			{// Condition for general total value (with out third party shipping value)
				if(ExpectedTotal.equals(ActualTotal))
				{
					//!System.out.println("Both Total values are same no need to enter into else ");
				}
				else
				{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "GridTotal";
				System.out.println("<---------Shopping cart  Both Total values are different -------->"+ErrorNumber);
				System.out.println("Actual total value is :"+ActualTotal);
				System.out.println("Expected total value is :"+ExpectedTotal);
				RW_File.WriteResult(ExpectedTotal, ActualTotal, PageName, PriceType);
				}	
			}
		  }// Enable promotion or discounts on condition completed
			else
			{// Enable promotion or discounts off condition starts
				// Get the total value based on order process type
				String ActualTotal = null;
				if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") || 
						ProdutType.equals("Dynamic Email"))
				{
					ActualTotal = d.findElement(Property.SCartDownLoadAmountOFF).getText();
				}
				else
				{
					ActualTotal = d.findElement(Property.SCartAmountOFF).getText();
				}
				//Verify the Actual total value with Expected total value
				if(ExpectedTotal.equals(ActualTotal))
				{
					//!System.out.println("Both Total values are same ");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "GridTotal";
					System.out.println("<--------Shopping cart  Both Total values are different ------->"+ErrorNumber);
					System.out.println("Actual total value is :"+ActualTotal);
					System.out.println("Expected total value is :"+ExpectedTotal);
					RW_File.WriteResult(ExpectedTotal, ActualTotal, PageName, PriceType);
				}	
			}// Enable promotion or discounts off condition completed.
			
			// Generate the grand total value when we have third party shipping
			String ExpectedTotal3 = null;
			if(OrderBase.equals("Item")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
			{
				String Actualthrdpartyshippingprice2 = d.findElement(Property.SCartPostagePrice).getText();
				String Actualthrdpartyshippingprice3 = Actualthrdpartyshippingprice2.substring(1,Actualthrdpartyshippingprice2.length());
				ExpectedTotal3 = viewsummaryshippingtotal(SubTotal,Actualthrdpartyshippingprice3,Addons,DiscountcalculationfromSubTotal,OrderAmountValue,DecimalValue,Postage);
			}
			// Compare the total price value when we have third party shipping value
			if(OrderBase.equals("Item")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
			{
				if((ExpectedTotal3.equals(ShoppingCartTotal)))
				{
					//!System.out.println("Both Total values are same ");
				}
				else
				{
				//System.out.println("3333");
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "total";
				System.out.println("<-------- Shopping cart Both Total values are different ------------>"+ErrorNumber);
				System.out.println("Actual total value is :"+ShoppingCartTotal);
				System.out.println("Expected total value is :"+ExpectedTotal3);
				RW_File.WriteResult(ExpectedTotal3, ShoppingCartTotal, PageName, PriceType);
				
				}
			}
			else
			{// Compare total value general condition (with out third party shipping)
				if((ExpectedTotal2.equals(ShoppingCartTotal)))
				{
					//!System.out.println("Both Total values are same ");
				}
				else
				{
					//System.out.println("3333");
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "total";
					System.out.println("<---------- Shopping cart Both Total values are different ---------->"+ErrorNumber);
					System.out.println("Actual total value is :"+ShoppingCartTotal);
					System.out.println("Expected total value is :"+ExpectedTotal2);
					RW_File.WriteResult(ExpectedTotal2, ShoppingCartTotal, PageName, PriceType);
					
				}
			}// else part completed.
			//for order base 3rd party shipping services in order check out page and user handiling fee
			WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
			
			FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
			 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
			 waitfl.pollingEvery(1, TimeUnit.SECONDS);
			 waitfl.ignoring(NoSuchElementException.class);
			 waitfl.ignoring(StaleElementReferenceException.class);
			if((Weighttype.equalsIgnoreCase("KGS")||Weighttype.equalsIgnoreCase("LBS"))&&OrderBase.equalsIgnoreCase("Base") )
			{
				waitfl.until(ExpectedConditions.elementToBeClickable(Property.ordershippingdropdown));
				d.findElement(Property.ordershippingdropdown).click();
				tec.AC40.Common.Wait.wait5Second();
				d.findElement(Property.ordershippingmethodselection).click();
				tec.AC40.Common.Wait.wait5Second();
				
			}
			}	
		}catch (Exception e){
			ErrorNumber = ErrorNumber+1;
			captureScreenshot();
	    	  e.printStackTrace();
	      }		
	}
	
	public static void VerifyAppliedAndRemaingPayments(String Total, String DecimalValue,
			String CalculateTaxCondition,String OrderBase,String Weighttype,String Subtotal,String PromotionCoupon,
		    String Addons,String DiscountcalculationfromSubTotal,
			String OrderAmountValue,String userordershippingorhandlingfee, String TotalPrice,
			String IsShippingTaxable, String Tax, String PriceAfterApplyingCoupon) throws InterruptedException {
		try{
			
			String PageName = "OrderCheckout";
			MouseAdjFunction();
			
	        String ActualAppliedPayment = d.findElement(Property.AppliedPayment).getText();	
	        //String ActualRemainingPayment = d.findElement(Property.RemainingBalance).getText();	
	        
	        String ExpectedAppliedPayment = Config.Currency+Total;
	       // String ExpectedRemainingPayment = Config.Currency+DecimalValue;	
	        
	        String[] TaxType = CalculateTaxCondition.split("_");
	        
	        if(TaxType[0].equals("Vertex"))
	        { 
	        	if(TaxType[1].equals("ON"))
	        	{	
	        		String ExpectedAppliedPayment1 =VertexTotal(ExpectedAppliedPayment);
	        		if(ExpectedAppliedPayment1.equals(ActualAppliedPayment))
	        		{
	        			//!System.out.println("Both Total values are same ");
	        		}
	        		else
	        		{
	        			ErrorNumber = ErrorNumber+1;
	        			captureScreenshot();
	        			String PriceType = "AppliedPayment";
	        			System.out.println("<----- Ordercheckout Both applied payments values are different ------>"+ErrorNumber);
	        			System.out.println("Actual Applied value is :"+ActualAppliedPayment);
	        			System.out.println("Expected Applied value is :"+ExpectedAppliedPayment1);
	        			RW_File.WriteResult(ExpectedAppliedPayment1, ActualAppliedPayment, PageName, PriceType);
				
	        		}
	        	}
	        	else
	        	{
	        		String ExpectedAppliedPayment1 =ExpectedAppliedPayment;
	        		if(ExpectedAppliedPayment1.equals(ActualAppliedPayment))
	        		{
	        			//!System.out.println("Both Total values are same ");
	        		}
	        		else
	        		{
	        			ErrorNumber = ErrorNumber+1;
	        			captureScreenshot();
	        			String PriceType = "AppliedPayment";
	        			System.out.println("<--- Ordercheckout Both applied payments values are different ---------->"+ErrorNumber);
	        			System.out.println("Actual Applied value is :"+ActualAppliedPayment);
	        			System.out.println("Expected Applied value is :"+ExpectedAppliedPayment1);
	        			RW_File.WriteResult(ExpectedAppliedPayment1, ActualAppliedPayment, PageName, PriceType);
				
	        		}
	        	}
	        }
	        else
	        {
	        	String OCappliedpayment = null;
	        	String OCmarkupgrandtotal = null;
	        	
	        	if(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
			    		  userordershippingorhandlingfee.equals("0.0000"))
	        	{
	        	 if(OrderBase.equals("Item")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
	        	 {
	        		String OC3rdpartytax = d.findElement(Property.OCTaxAmount).getText();
					String OC3rdpartytax2 = OC3rdpartytax.substring(1,OC3rdpartytax.length());
				    String OC3rdpartyshippingprice = d.findElement(Property.OrderCheckoutGridPostage).getText();
					String OC3rdpartyshippingprice2 = OC3rdpartyshippingprice.substring(1,OC3rdpartyshippingprice.length());
	        		OCappliedpayment = OCgrandtotal(Subtotal,PriceAfterApplyingCoupon,OC3rdpartytax2,OC3rdpartyshippingprice2,
							Addons,DiscountcalculationfromSubTotal,OrderAmountValue, Total, IsShippingTaxable, Tax);
	        	 }
	        	}
	        	else
	        	{
	        		if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
	        		{
	        		String OCmarkupfee = d.findElement(Property.OCHandilingfee).getText();
					String OCmarkupfee1 = OCmarkupfee.substring(1,OCmarkupfee.length());
					String OC3rdpartytax = d.findElement(Property.OCTaxAmount).getText();
					String OC3rdpartytax1 = OC3rdpartytax.substring(1,OC3rdpartytax.length());
					String OC3rdpartyshippingprice = d.findElement(Property.OrderCheckoutGridPostage).getText();
					String OC3rdpartyshippingprice1 = OC3rdpartyshippingprice.substring(1,OC3rdpartyshippingprice.length());
					OCmarkupgrandtotal = Ocupdatedmarkupgrandtotal(Subtotal,PromotionCoupon,OCmarkupfee1,OC3rdpartytax1,OrderAmountValue,
							Addons,DiscountcalculationfromSubTotal,OC3rdpartyshippingprice1);
					
	        	    }
	        	}
	        	if(Weighttype.equals("--Select--")&&(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
			    		  userordershippingorhandlingfee.equals("0.0000")))
	        	{
	        	     if(ExpectedAppliedPayment.equals(ActualAppliedPayment))
				      {
	        	    	 //!System.out.println("Both Total values are same ");
				      }
	        	     else
	 				  {
	 					ErrorNumber = ErrorNumber+1;
	 					captureScreenshot();
	 					String PriceType = "AppliedPayment";
	 					System.out.println("<--------- Ordercheckout Both applied payments values are different -------->"+ErrorNumber);
	 					System.out.println("Actual Applied value is :"+ActualAppliedPayment);
	 					System.out.println("Expected Applied value is :"+ExpectedAppliedPayment);
	 					RW_File.WriteResult(ExpectedAppliedPayment, ActualAppliedPayment, PageName, PriceType);
	 					
	 				  }
	        	}
	        	if(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
			    		  userordershippingorhandlingfee.equals("0.0000"))
	        	{
	        		 if(OrderBase.equals("Item")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
	        		 {
	        	        if(OCappliedpayment.equals(ActualAppliedPayment))
	        	        {
	        	        	//!System.out.println("Both Total values are same ");
	        	        }
	        	     else
	 				   {
	 					ErrorNumber = ErrorNumber+1;
	 					captureScreenshot();
	 					String PriceType = "AppliedPayment";
	 					System.out.println("<--------- Ordercheckout Both applied payments values are different ---------->"+ErrorNumber);
	 					System.out.println("Actual Applied value is :"+ActualAppliedPayment);
	 					System.out.println("Expected Applied value is :"+OCappliedpayment);
	 					RW_File.WriteResult(OCappliedpayment, ActualAppliedPayment, PageName, PriceType);
	 				   }
	        		 }
	        	}
	        	else if(!(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
			    		  userordershippingorhandlingfee.equals("0.0000")))
	        	{
	        		if(OrderBase.equals("Item")&&(Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
	        		{
	        	       if(OCmarkupgrandtotal.equals(ActualAppliedPayment))
	        	       {
	        	    	   //!System.out.println("Both Total values are same ");
	        	       }
				      else
				      {
					    ErrorNumber = ErrorNumber+1;
					    captureScreenshot();
					    String PriceType = "AppliedPayment";
					    System.out.println("<-------- Ordercheckout Both applied payments values are different --------->"+ErrorNumber);
					    System.out.println("Actual Applied value is :"+ActualAppliedPayment);
					    System.out.println("Expected Applied value is :"+OCmarkupgrandtotal);
					    RW_File.WriteResult(OCmarkupgrandtotal, ActualAppliedPayment, PageName, PriceType);
				      }
				    }
	        		else
	        		{
	        			if(ExpectedAppliedPayment.equals(ActualAppliedPayment))
					      {
	        				//!System.out.println("Both Total values are same ");
					      }
		        	     else
		 				  {
		        	    	//System.out.println("lavanya");
		 					ErrorNumber = ErrorNumber+1;
		 					captureScreenshot();
		 					String PriceType = "AppliedPayment";
		 					System.out.println("<------- Ordercheckout Both applied payments values are different ---------->"+ErrorNumber);
		 					System.out.println("Actual Applied value is :"+ActualAppliedPayment);
		 					System.out.println("Expected Applied value is :"+ExpectedAppliedPayment);
		 					RW_File.WriteResult(ExpectedAppliedPayment, ActualAppliedPayment, PageName, PriceType);
		 				  }
	        		}
	        	}	
	        }
			/*if(ExpectedRemainingPayment.equals(ActualRemainingPayment))
				{
					//System.out.println("Both Total values are same ");
				}
			else
				{
				
					ErrorNumber = ErrorNumber+1;
					String PriceType = "RemainingPayment";
					System.out.println("<--------- Ordercheckout Both Remaining payments values are different ---------->"+ErrorNumber);
					System.out.println("Actual Remaining value is :"+ActualRemainingPayment);
					System.out.println("Expected Remaining value is :"+ExpectedRemainingPayment);
					RW_File.WriteResult(ExpectedRemainingPayment, ActualRemainingPayment, PageName, PriceType);
					
				}*/
		  }
	        	catch (Exception e){
	        		ErrorNumber = ErrorNumber+1;
	        		captureScreenshot();
	        		e.printStackTrace();
	      }		
	}
	
	public static void VerifyMultiAppliedAndRemaingPayments(String Payment1Price, String Payment2Price,
			String Payment3Price, 
			String Payment4Price, String Payment5Price, String DecimalValue,
			String PaymentType, int paymentLength) throws InterruptedException {
		try{
			String ActualAppliedPayment1Price  = null;
			String ActualAppliedPayment2Price  = null;
			String ActualAppliedPayment3Price  = null;
			String ActualAppliedPayment4Price  = null;
			String ActualAppliedPayment5Price  = null;
			MouseAdjFunction();
			String PageName = "OrderCheckout";
			if(paymentLength >= 1)
			{
	         ActualAppliedPayment1Price = d.findElement(Property.AppliedPayment).getText();
			}
			if(paymentLength >= 2)
			{
	         ActualAppliedPayment2Price = d.findElement(Property.AppliedPayment2).getText();
			}
			if(paymentLength >= 3)
			{
	         ActualAppliedPayment3Price = d.findElement(Property.AppliedPayment3).getText();
			}
			if(paymentLength >= 4)
			{
	         ActualAppliedPayment4Price = d.findElement(Property.AppliedPayment4).getText();
			}
			if(paymentLength == 5)
			{
	         ActualAppliedPayment5Price = d.findElement(Property.AppliedPayment5).getText();
			}
	        
			String ActualRemainingPayment = d.findElement(Property.RemainingBalance).getText();	
	        
	        String ExpectedAppliedPayment1Price = Config.Currency+Payment1Price;
	        
	        String ExpectedAppliedPayment2Price = Config.Currency+Payment2Price;
	        String ExpectedAppliedPayment3Price = Config.Currency+Payment3Price;
	        String ExpectedAppliedPayment4Price = Config.Currency+Payment4Price;
	        String ExpectedAppliedPayment5Price = Config.Currency+Payment5Price;
	        
	        String ExpectedRemainingPayment = Config.Currency+DecimalValue;	
			
	        if(paymentLength >= 1)
	        {
			if(ExpectedAppliedPayment1Price.equals(ActualAppliedPayment1Price))
			{
				//!System.out.println("Both Total values are same ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "AppliedPayment";
				System.out.println("<----- Ordercheckout Both applied payments values are different ---------->"+ErrorNumber);
				System.out.println("Actual Applied value is :"+ActualAppliedPayment1Price);
				System.out.println("Expected Applied value is :"+ExpectedAppliedPayment1Price);
				RW_File.WriteResult(ExpectedAppliedPayment1Price, ActualAppliedPayment1Price, PageName, PriceType);
			}
	        }
			
	        if(paymentLength >= 2)
	        {
			if(ExpectedAppliedPayment2Price.equals(ActualAppliedPayment2Price))
			{
				//!System.out.println("Both Total values are same ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "AppliedPayment";
				System.out.println("<--------- Ordercheckout Both applied payments values are different ----->"+ErrorNumber);
				System.out.println("Actual Applied value is :"+ActualAppliedPayment2Price);
				System.out.println("Expected Applied value is :"+ExpectedAppliedPayment2Price);
				RW_File.WriteResult(ExpectedAppliedPayment2Price, ActualAppliedPayment2Price, PageName, PriceType);
				
			}
	        }
			
	        if(paymentLength >= 3)
	        {
			if(ExpectedAppliedPayment3Price.equals(ActualAppliedPayment3Price))
			{
				//!System.out.println("Both Total values are same ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "AppliedPayment";
				System.out.println("<------- Ordercheckout Both applied payments values are different -------->"+ErrorNumber);
				System.out.println("Actual Applied value is :"+ActualAppliedPayment3Price);
				System.out.println("Expected Applied value is :"+ExpectedAppliedPayment3Price);
				RW_File.WriteResult(ExpectedAppliedPayment3Price, ActualAppliedPayment3Price, PageName, PriceType);
			}
	        }
			
	        if(paymentLength >= 4)
	        {
			if(ExpectedAppliedPayment4Price.equals(ActualAppliedPayment4Price))
			{
				//!System.out.println("Both Total values are same ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "AppliedPayment";
				System.out.println("<------- Ordercheckout Both applied payments values are different -------->"+ErrorNumber);
				System.out.println("Actual Applied value is :"+ActualAppliedPayment4Price);
				System.out.println("Expected Applied value is :"+ExpectedAppliedPayment4Price);
				RW_File.WriteResult(ExpectedAppliedPayment4Price, ActualAppliedPayment4Price, PageName, PriceType);
			}
	        }
			
	        if(paymentLength == 5)
	        {
			if(ExpectedAppliedPayment5Price.equals(ActualAppliedPayment5Price))
			{
				//!System.out.println("Both Total values are same ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "AppliedPayment";
				System.out.println("<------- Ordercheckout Both applied payments values are different -------->"+ErrorNumber);
				System.out.println("Actual Applied value is :"+ActualAppliedPayment5Price);
				System.out.println("Expected Applied value is :"+ExpectedAppliedPayment5Price);
				RW_File.WriteResult(ExpectedAppliedPayment5Price, ActualAppliedPayment5Price, PageName, PriceType);
			}
	        }
		        
			if(ExpectedRemainingPayment.equals(ActualRemainingPayment))
				{
				//!System.out.println("Both Total values are same ");
				}
			else
				{
				
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "RemainingPayment";
					System.out.println("<--------- Ordercheckout Both Remaining payments values are different ---------->"+ErrorNumber);
					System.out.println("Actual Remaining value is :"+ActualRemainingPayment);
					System.out.println("Expected Remaining value is :"+ExpectedRemainingPayment);
					RW_File.WriteResult(ExpectedRemainingPayment, ActualRemainingPayment, PageName, PriceType);
					
				}
		}catch (Exception e){
			ErrorNumber = ErrorNumber+1;
			captureScreenshot();
	    	  e.printStackTrace();
	      }		
	}

	public static void captureScreenshot() throws InterruptedException {
		try{
			OrderFlow.ImageNumber = OrderFlow.ImageNumber +1;
			if(Config.TakeScreenShot.equalsIgnoreCase("Yes")){
				// capture screen shot
				//!System.out.println("Enter in to screen shot method");
				File scrFile = ((TakesScreenshot)d).getScreenshotAs(OutputType.FILE);
			    FileUtils.copyFile(scrFile, new File(RW_File.FolderPath+"\\"+"TestStep_"+OrderFlow.TestStep1+"_"+OrderFlow.ImageNumber+".jpg"));
			}
			
		}catch (Exception e){
			// take screen shots
	    	  e.printStackTrace();
	      }		
	}

	
	
	public static void BillingAddressVerification(String EnableZeroAmountOrder, String BillingAddValue, 
			 String PageName, String TotalPrice) throws InterruptedException {
		try{
			MouseAdjFunction();
			String[] ZeroAmountOrder = EnableZeroAmountOrder.split("_");
			if(ZeroAmountOrder[1].equalsIgnoreCase("NO") && (TotalPrice.equals("0") || TotalPrice.equals("0.00") ||
					TotalPrice.equals("0.000") || TotalPrice.equals("0.0000")))
			{
				if(BillingAddValue.equals(""))
				{
					//!System.out.println("Billing Address Not displayed for user");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "Billing Address";
					System.out.println("<-------- Billing Address displayed in "+PageName +"  page ---------->"+ErrorNumber);
					System.out.println("Actual Billing Address status : "+"Billing Address displayed");
					System.out.println("Expected Billing Address status : "+"Billing Address not displayed");
					RW_File.WriteResult("Billing Address not displayed", "Billing Address displayed",  "Order Checkout", PriceType);
				}
			}
			else // YES condition and total amount have grater than zero value
			{
				if(BillingAddValue.contains("38345"))
				{
					//!System.out.println("Billing Address displayed for user");
				}
				else
				{
					//ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "Billing Address";
					//System.out.println("<-------- Billing Address not displayed in "+PageName +" page ---------->"+ErrorNumber);
					//System.out.println("Actual Billing Address Status : "+"Billing address not displayed");
					//System.out.println("Expected Billing Address Status : "+"Billing address displays to user");
					RW_File.WriteResult("Billing address displays to user", "Billing address not displayed",  "Order checkout", PriceType);
				}
			}
			
		}catch (Exception e){
			ErrorNumber = ErrorNumber+1;
			captureScreenshot();
	    	  e.printStackTrace();
	      }		
	}
	
	public static void BillingAddressVerificationOrderSummary(String EnableZeroAmountOrder, String BillingAddValue, 
			 String PageName) throws InterruptedException {
		try{
			MouseAdjFunction();
			String[] ZeroAmountOrder = EnableZeroAmountOrder.split("_");
			if(ZeroAmountOrder[1].equalsIgnoreCase("NO"))
			{
				System.out.println("billing0"+BillingAddValue);

				if(BillingAddValue.equals(""))
				{
					//!System.out.println(PageName +" Billing Address Not displayed for user");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "Billing Address";
					System.out.println("<-------- Billing Address displayed in "+PageName +" page ---------->"+ErrorNumber);
					System.out.println("Actual Billing Address status : "+"Billing Address displayed");
					System.out.println("Expected Billing Address status : "+"Billing Address not displayed");
					RW_File.WriteResult("Billing Address not displayed", "Billing Address displayed",  PageName, PriceType);
				}
			}
			else if(ZeroAmountOrder[1].equalsIgnoreCase("YES"))
			{				System.out.println("billing1"+BillingAddValue);

				if(BillingAddValue.contains("38345"))
				{
					//!System.out.println(PageName +" Billing Address displayed for user");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "Billing Address";
					System.out.println("<-------- Billing Address not displayed in "+PageName +" page ---------->"+ErrorNumber);
					System.out.println("Actual Billing Address Status : "+"Billing address not displayed");
					System.out.println("Expected Billing Address Status : "+"Billing address displays to user");
					RW_File.WriteResult("Billing address displays to user", "Billing address not displayed",  PageName, PriceType);
				}
			}
			
		}catch (Exception e){
			ErrorNumber = ErrorNumber+1;
			captureScreenshot();
	    	  e.printStackTrace();
	      }		
	}
	
	
	public static void SelectPaymentTypeInCheckOutPage1(String OrderBase, String PaymentType, String TestStep,
			String PaymentPrice, String PaymentSubOpt, boolean paymentsListBoxCount,
			int s, int paymentLength1) throws InterruptedException, AWTException
	{
		MouseAdjFunction();
		try{
			
			 if(OrderBase.equals("Item"))
		     {

				d.findElement(Property.PaymentTypeDownArrow).click();
		       tec.AC40.Common.Wait.wait2Second();
		       Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
		       System.out.println("PaymentType........"+PaymentType);
		       if(PaymentType.equals("Billing"))
		       {
		       kb.pressKey(Keys.DOWN);
		       tec.AC40.Common.Wait.wait2Second();
		       kb.pressKey(Keys.ENTER);
		       tec.AC40.Common.Wait.wait2Second();
		       }
		       else if(PaymentType.equals("Co-Op Fund"))
		       {
		       kb.pressKey(Keys.DOWN);
		       tec.AC40.Common.Wait.wait2Second();
		       kb.pressKey(Keys.DOWN);
		       tec.AC40.Common.Wait.wait2Second();
		       kb.pressKey(Keys.ENTER);
		       }
		      }
		      else
		      {
					d.findElement(Property.PaymentTypeDownArrowOrder).click();
		       tec.AC40.Common.Wait.wait2Second();
		       Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
		       System.out.println("PaymentType........"+PaymentType);
		       if(PaymentType.equals("Billing"))
		       {
		       kb.pressKey(Keys.DOWN);
		       tec.AC40.Common.Wait.wait2Second();
		       kb.pressKey(Keys.ENTER);
		       tec.AC40.Common.Wait.wait2Second();
		       }
		       else if(PaymentType.equals("Co-Op Fund"))
		       {
		       kb.pressKey(Keys.DOWN);
		       tec.AC40.Common.Wait.wait2Second();
		       kb.pressKey(Keys.DOWN);
		       tec.AC40.Common.Wait.wait2Second();
		       kb.pressKey(Keys.ENTER);
		       }
		      }
				tec.AC40.Common.Wait.wait10Second();
				
				if(PaymentType.equals("Billing"))
				{
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.PONumber).sendKeys("124");
				}
				else if(PaymentType.equals("Co-Op Fund"))
				{
					// No need to do other actions
				}
				else if(PaymentType.equals("Gift Card"))
				{
					// No need to do other actions
				}
				else if(PaymentType.equals("Credit Card"))
				{
					if(PaymentSubOpt.contains("AChaLat"))
					 {
						 d.findElement(Property.AuthAndChrgLaterRadioButton).click();
					 }
					else
					{
					if(Config.LayoutType.equals("Classic"))
					{
						d.findElement(Property.CreditCardDownC).click();
					}
					else
					{
						d.findElement(Property.CreditCardDown).click();
					}
				tec.AC40.Common.Wait.wait2Second();
				String CreditCardType = d.findElement(Property.CreditCardTypeList).getText();
				String[] CreditCardValues  = CreditCardType.split("\n");
				int CreditCardTypeListSize = CreditCardValues.length; 
				System.out.println("** Credit card type list size **-->"+CreditCardTypeListSize);
				int RamdonNumber1 = 0;
				int c = 0;
				int f = 0;
				int e = 0;
				int TestNumber = Double.valueOf(TestStep).intValue();
				 RamdonNumber1 = TestNumber % 2;
				for(c=0; c < CreditCardTypeListSize; c++)
					{
					//System.out.println(CreditCardValues[c]);
					if(CreditCardValues[c].equals("Visa"))
						{
						f = c+1;
						//System.out.println("D 1 :"+d);
						}
					else if(CreditCardValues[c].equals("Mastercard"))
						{
						e = c+1;
						//System.out.println("E 1 : "+e);
						}
					}
				if(RamdonNumber1 == 0)
				{
					d.findElement(By.cssSelector("ul[id*='ddlCreditCardTypes_listbox'] li:nth-child("+f+")")).click();
				}
				else
				{
					d.findElement(By.cssSelector("ul[id*='ddlCreditCardTypes_listbox'] li:nth-child("+e+")")).click();
				}
				System.out.println("Credit card type drop down is working fine**");
				Thread.sleep(2000);
				d.findElement(Property.CreditCardNumber).sendKeys("4111111111111111");
				Thread.sleep(2000);
				if(PaymentSubOpt.contains("PayPal"))
				{
					d.findElement(Property.CreditCardCVVNumber).sendKeys("123");
				}
				d.findElement(Property.CreditCardNameOnCard).sendKeys("sat");
				d.findElement(Property.CreditCardLastName).sendKeys("SelTest");
				}
				}
				d.findElement(Property.MultipaymentApplyButton).click();
				tec.AC40.Common.Wait.wait5Second();
			
		}
		catch(Exception e)
		{
			ErrorNumber = ErrorNumber+1;
			captureScreenshot();
			e.printStackTrace();
		}
	}
	public static void SelectPaymentTypeInCheckOutPage(String OrderBase, String PaymentType, String TestStep,
			String PaymentPrice, String PaymentSubOpt, boolean paymentsListBoxCount,
			int s, int paymentLength1) throws InterruptedException, AWTException
	{
		MouseAdjFunction();
		try{
				if(OrderBase.equals("Item"))
				{
				d.findElement(Property.PaymentTypeDownArrow).click();
				}
				else
				{
					d.findElement(Property.PaymentTypeDownArrowOrder).click();
				}
				tec.AC40.Common.Wait.wait2Second();
				//System.out.println("paymentsListBoxCount :"+paymentsListBoxCount);
				String PaymentDropdownData = null;
				if(paymentsListBoxCount == false)
				{
					PaymentDropdownData = d.findElement(Property.PaymentTypelength).getText();
				}
				else
				{
					PaymentDropdownData = d.findElement(Property.PaymentTypeDropDownData2).getText();
				}
				String[] PaymentDropdownDatvalues = PaymentDropdownData.split("\n");
				//System.out.println("PaymentDropdownDatvalues : "+PaymentDropdownData);
				int PaymentDropDownLength = PaymentDropdownDatvalues.length;
				for(int a = 0; a< PaymentDropDownLength; a++)
				{
					int b = 0;
					if(PaymentType.equals(PaymentDropdownDatvalues[a]))
					{
						//System.out.println("PaymentDropdownDatvalues[a] : "+PaymentDropdownDatvalues[a]);
						b = a+1;
						//System.out.println("s value is :"+s);
						if(paymentsListBoxCount == false)
						{
						d.findElement(By.cssSelector("ul[id*='ddlPaymentType_listbox'] li:nth-child("+b+")")).click();
						}
						else
						{
							System.out.println("enter in to true code :"+s);
						d.findElement(By.xpath("(//*[@id='ddlPaymentType_listbox']/li["+b+"])[2]")).click();	
						}
					}
						if(b == a+1)
						{
							break;
						}
				}
				
				if(s < 9 && paymentLength1 != (s+1))
				{
				Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
				Thread.sleep(5000);
				d.findElement(Property.OCBalanceAmountTextBox).click();
				//kb.sendKeys(Keys.TAB);
			    Thread.sleep(2000);
				kb.sendKeys(Keys.chord(Keys.CONTROL, "a"));
				Thread.sleep(2000);
				kb.pressKey(Keys.DELETE);
				Thread.sleep(500);
				kb.sendKeys(""+PaymentPrice);
				}
				
				tec.AC40.Common.Wait.wait10Second();
				
				if(PaymentType.equals("Billing"))
				{
					tec.AC40.Common.Wait.wait5Second();
					d.findElement(Property.PONumber).sendKeys("124");
				}
				else if(PaymentType.equals("Co-Op Fund"))
				{
					// No need to do other actions
				}
				else if(PaymentType.equals("Gift Card"))
				{
					// No need to do other actions
				}
				else if(PaymentType.equals("Credit Card"))
				{
					if(PaymentSubOpt.contains("AChaLat"))
					 {
						 d.findElement(Property.AuthAndChrgLaterRadioButton).click();
					 }
					else
					{
					if(Config.LayoutType.equals("Classic"))
					{
						d.findElement(Property.CreditCardDownC).click();
					}
					else
					{
						//d.findElement(Property.CreditCardDown).click();
					}
		/*		tec.AC40.Common.Wait.wait2Second();
				String CreditCardType = d.findElement(Property.CreditCardTypeList).getText();
				String[] CreditCardValues  = CreditCardType.split("\n");
				int CreditCardTypeListSize = CreditCardValues.length; 
				int RamdonNumber1 = 0;
				int c = 0;
				int f = 0;
				int e = 0;
				int TestNumber = Double.valueOf(TestStep).intValue();
				 RamdonNumber1 = TestNumber % 2;
				for(c=0; c < CreditCardTypeListSize; c++)
					{
					//System.out.println(CreditCardValues[c]);
					if(CreditCardValues[c].equals("Visa"))
						{
						f = c+1;
						//System.out.println("D 1 :"+d);
						}
					else if(CreditCardValues[c].equals("Mastercard"))
						{
						e = c+1;
						//System.out.println("E 1 : "+e);
						}
					}
				if(RamdonNumber1 == 0)
				{
					d.findElement(By.cssSelector("ul[id*='ddlCreditCardTypes_listbox'] li:nth-child("+f+")")).click();
				}
				else
				{
					d.findElement(By.cssSelector("ul[id*='ddlCreditCardTypes_listbox'] li:nth-child("+e+")")).click();
				}*/
					System.out.println("** CAME UP TO HERE **");
					Thread.sleep(2000);
				d.findElement(Property.CreditCardNumber).sendKeys("4111111111111111");
				if(PaymentSubOpt.contains("PayPal"))
				{
					d.findElement(Property.CreditCardCVVNumber).sendKeys("123");
				}
				d.findElement(Property.CreditCardNameOnCard).sendKeys("sat");
				d.findElement(Property.CreditCardLastName).sendKeys("SelTest");
				}
				}
				d.findElement(Property.MultipaymentApplyButton).click();
				tec.AC40.Common.Wait.wait5Second();
			
		}
		catch(Exception e)
		{
			ErrorNumber = ErrorNumber+1;
			captureScreenshot();
			e.printStackTrace();
		}
	}
	public static void VerfiyShippingAmount(String SelectListActualShipping,String ShippingPrice,
			String Priceafterapplyingfulfillmentshippingmarkupfee) throws AWTException
	{
		MouseAdjFunction();
		try
		{
			String PageName = "Select List";
			String ExpectedShippingPrice = null;
			//System.out.println("ShippingPrice2 :"+ShippingPrice);
			if(Priceafterapplyingfulfillmentshippingmarkupfee.equals("0") ||
					Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00") ||
					Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000") ||
					Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000"))
			{
				ExpectedShippingPrice = Config.Currency+ShippingPrice;
			}
			else
			{
			ExpectedShippingPrice = Config.Currency+Priceafterapplyingfulfillmentshippingmarkupfee;
			}
			
			
			//System.out.println("ShippingPrice3 :"+ShippingPrice);
			if(SelectListActualShipping.equals(ExpectedShippingPrice))
			{
				//!System.out.println("Both Select List Shipping prices are same");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Shipping Price";
				System.out.println("<------- Select List Both Shipping Prices are different ------> "+ErrorNumber);
				System.out.println("Actual Shipping price is : "+SelectListActualShipping);
				System.out.println("Expected shipping price is : "+ExpectedShippingPrice);
				RW_File.WriteResult(ExpectedShippingPrice, SelectListActualShipping, PageName, PriceType);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void VerfiyShippingAmountShipToMultipleLocations(String SelectListActualShipping,String ShippingPrice,
			String Priceafterapplyingfulfillmentshippingmarkupfee,int QuantitySub,int Quantitytot,
			String DecimalValue, String ShippingPricePerPiece) throws AWTException
	{
		MouseAdjFunction();
		try
		{
			String ShippingPrice3 = null;
			String PageName = "Select List";
			String ExpectedShippingPrice = null;
			
			if(ShippingPricePerPiece.equals("0.00") ||
					ShippingPricePerPiece.equals("0.000") ||
					ShippingPricePerPiece.equals("0.0000") ||
					ShippingPricePerPiece.equals("0"))
			{
				ShippingPrice3 = ShippingPrice;
			}
			else
			{
				double ShippingPriceValue = Double.parseDouble(ShippingPrice);
				double ShippingPriceValue2 = ShippingPriceValue/Quantitytot;
				//System.out.println("ShippingPriceValue2 :"+ShippingPriceValue2);
				double ShippingPriceValue3 = ShippingPriceValue2 * QuantitySub;
				//System.out.println("ShippingPriceValue3 :"+ShippingPriceValue3);
				String ShippingPrice2 = ""+ShippingPriceValue3;
				 ShippingPrice3 = Decimalsetting(ShippingPrice2, DecimalValue);
				//System.out.println("ShippingPrice3 :"+ShippingPrice3);
				//System.out.println("ShippingPrice2 :"+ShippingPrice);
			}
			if(Priceafterapplyingfulfillmentshippingmarkupfee.equals("0") ||
					Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00") ||
					Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000") ||
					Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000"))
			{
				ExpectedShippingPrice = Config.Currency+ShippingPrice3;
			}
			else
			{
			ExpectedShippingPrice = Config.Currency+Priceafterapplyingfulfillmentshippingmarkupfee;
			}
			
			
			//System.out.println("ShippingPrice3 :"+ShippingPrice);
			if(SelectListActualShipping.equals(ExpectedShippingPrice))
			{
				//!System.out.println("Both Select List Shipping prices are same");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Shipping Price";
				System.out.println("<------- Select List Both Shipping Prices are different ------> "+ErrorNumber);
				System.out.println("Actual Shipping price is : "+SelectListActualShipping);
				System.out.println("Expected shipping price is : "+ExpectedShippingPrice);
				RW_File.WriteResult(ExpectedShippingPrice, SelectListActualShipping, PageName, PriceType);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void VerfiyOrderBaseShippingAmount(String SelectListActualShipping,String ShippingPrice,
			String Priceafterapplyingfulfillmentshippingmarkupfee) throws AWTException
	{
		MouseAdjFunction();
		try
		{
			String PageName = "Order check out";
			String ExpectedShippingPrice = null;
			if(Priceafterapplyingfulfillmentshippingmarkupfee.equals("0") ||
					Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00") ||
					Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000") ||
					Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000"))
			{
				ExpectedShippingPrice = Config.Currency+ShippingPrice;
			}
			else
			{
				ExpectedShippingPrice = Config.Currency+Priceafterapplyingfulfillmentshippingmarkupfee;
			}
			
			if(SelectListActualShipping.equals(ExpectedShippingPrice))
			{
				//!System.out.println("Both Order check out Shipping prices are same");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Shipping Price";
				System.out.println("<------- Order check out Both Shipping Prices are different ------> "+ErrorNumber);
				System.out.println("Actual Shipping price is : "+SelectListActualShipping);
				System.out.println("Expected shipping price is : "+ExpectedShippingPrice);
				RW_File.WriteResult(ExpectedShippingPrice, SelectListActualShipping, PageName, PriceType);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void OrderCheckOutPriceInformantion(String Quantity, String ItemPerPrice, String Discount, String Addons,
			String Postage, String TotalPrice, String Total, String PromotionCode, 
			String PromotionCoupon, String ShippingPrice, String Tax, String PriceAfterCalculatingTax, 
			String AddonPricePerPiece, String DiscountPercentage, String PromotionDiscountAfterSubtractingFromSubTotal, 
			String PromotionDiscountPercentage, String DiscountCalculationFromSubTotal, String OrderType,
			String DownloadPrice, String TestCase, String TestStep, String Parameters, String ProdutType, String OrderBase,
			String OrderBaseShipping, String CalculateTaxCondition, String EnablePromotionsORDiscounts,String Weighttype,
			String DiscountcalculationfromSubTotal,String Subtotal,String OrderAmountValue,
			String userordershippingorhandlingfee,String Priceafterapplyingfulfillmentshippingmarkupfee,String IsShippingTaxable,
			String PriceAfterApplyingCoupon,String IsTaxExempt,String DecimalValue,String LandingPageOption,String LandingPageProduct,
			String LandingPageProductPP,String LandingPageDiscount,String LandingPageDiscountValue,String SubtotalAfterPurlPrice,String SubTotal,String Orderelements,
			String OrderelementsAddOns,String OrderelementsBillingAddress,String OrderelementsInventoryLocation,String OrderelementsInventoryNumber,
			String OrderelementsPaymentDetail,String OrderelementsShippingAddress,String OrderelementsShippingMethod,String OrderelementsSpecialInstructions,String OrderelementsCheckout) throws InterruptedException  {
		try{
			MouseAdjFunction();
//			if(LandingPageOption.equalsIgnoreCase("YES")){
//				String PageName = "OrderCheckOut";
	//}else{
			String PageName = "OrderCheckOut";
           	if(IsTaxExempt.equalsIgnoreCase("yes")){
			PriceAfterCalculatingTax="0.00";
			String PriceAfterCalculatingTax1 = Decimalsetting(PriceAfterCalculatingTax, DecimalValue);
			PriceAfterCalculatingTax = PriceAfterCalculatingTax1;
		}
			tec.AC40.Common.Wait.wait5Second();
			// Apply promotion code if value more than zero
			if(PromotionCoupon.equals("0") || PromotionCoupon.equals("0.00") || PromotionCoupon.equals("0.000") || PromotionCoupon.equals("0.0000"))
			{
				//!System.out.println("Promotion coupon value is empty");
			}
			else
			{
				if(EnablePromotionsORDiscounts.equals("ON"))
				{
					if(Subtotal.equals("0") || Subtotal.equals("0.00") || Subtotal.equals("0.000") || Subtotal.equals("0.0000")){
						System.out.println("if subtotal is  zero promotion text field will be in hidden");
						
					}else{
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.PromotionCodeTextBox).sendKeys(PromotionCode);
				tec.AC40.Common.Wait.wait2Second();
				d.findElement(Property.PromotionApplyButton).click();
					}
				}
			}
			
			tec.AC40.Common.Wait.wait2Second();
			
			String ExpectedShippingPrice = Config.Currency+ShippingPrice;
			String ExpectedOrderBaseShippingPrice = Config.Currency+OrderBaseShipping;
			String ExpectedQuantity = Quantity;
			int ExpectedQuantity1 = Double.valueOf(ExpectedQuantity).intValue();
			String ExpectedShippingHandilingPrice = Config.Currency+Priceafterapplyingfulfillmentshippingmarkupfee;
			
			String ExpectedItemPrice = null;
			String ExpectedLandingpageItemPrice = null;
			// Generate expected Item price based on condition		
			if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload"))
			{
				ExpectedItemPrice = Config.Currency+DownloadPrice;
				ExpectedLandingpageItemPrice=Config.Currency+LandingPageProductPP;
			}
			else if(OrderType.equals("DynShipTOMultipleLocations") || OrderType.equals("StaticShipTOMultipleLocations") 
					|| OrderType.equals("StaticInventoryShipTOMultipleLocations"))
			{
				ExpectedItemPrice = "-";
			}
			else
			{
				ExpectedItemPrice = Config.Currency+ItemPerPrice;
				ExpectedLandingpageItemPrice=Config.Currency+LandingPageProductPP;
			}
			// Generate discount price based on discount amount or percentage
			String ExpectedDiscount = null;	
			String ExpectedLandingPageDiscount = null;		
			if(DiscountPercentage.equals("N")&&LandingPageDiscount.equals("N"))
			{
				ExpectedDiscount = Config.Currency+Discount;
				ExpectedLandingPageDiscount= Config.Currency+LandingPageDiscountValue;
			}
			else if(DiscountPercentage.equals("N")&&LandingPageDiscount.equals("Y"))
			{
				ExpectedDiscount = Config.Currency+Discount;
				ExpectedLandingPageDiscount = LandingPageDiscountValue+Config.PercentageSymbol;
			}
			else if(DiscountPercentage.equals("Y")&&LandingPageDiscount.equals("N"))
			{
				ExpectedDiscount = Discount+Config.PercentageSymbol;
				ExpectedLandingPageDiscount= Config.Currency+LandingPageDiscountValue;
			}
			else if(Discount.equals("0") || Discount.equals("0.0") || Discount.equals("0.00") ||
					Discount.equals("0.000") || Discount.equals("0.0000")||LandingPageDiscount.equals("0") ||
					LandingPageDiscount.equals("0.0") || LandingPageDiscount.equals("0.00") ||
					LandingPageDiscount.equals("0.000") || LandingPageDiscount.equals("0.0000"))
			{
				ExpectedDiscount = Config.Currency+Discount+"";
				ExpectedLandingPageDiscount = Config.Currency+LandingPageDiscountValue+"";

			}
			else
			{
				ExpectedDiscount = Discount+Config.PercentageSymbol;
				ExpectedLandingPageDiscount = LandingPageDiscountValue+Config.PercentageSymbol;
			}
				
			String ExpectedPostagepirce = Config.Currency+Postage;
			String ExpectedAmount=null;
			String ExpectedLandingAmount=null;
			if(LandingPageOption.equalsIgnoreCase("YES")){
				if (DiscountPercentage.equalsIgnoreCase("Y"))
				{
					if((Addons.equals("0") || Addons.equals("0.0") || Addons.equals("0.00") ||
							Addons.equals("0.000") || Addons.equals("0.0000")) &&( Postage.equals("0") || Postage.equals("0.0") || 
							Postage.equals("0.00") ||Postage.equals("0.000") || Postage.equals("0.0000")) &&( ShippingPrice.equals("0") ||
							ShippingPrice.equals("0.0") || ShippingPrice.equals("0.00") ||ShippingPrice.equals("0.000") || ShippingPrice.equals("0.0000")))
							{
					double discount1 = Double.valueOf(DiscountCalculationFromSubTotal).doubleValue();
					double discount2 = Double.valueOf(SubTotal).doubleValue();
					double discount3 = discount2-discount1;
					String discount4 = ""+discount3;
					String discount5 = Decimalsetting(discount4, DecimalValue);
					ExpectedAmount = Config.Currency+discount5;
					ExpectedLandingAmount = Config.Currency+SubtotalAfterPurlPrice ;
					}
					else{
						double discount1 = Double.valueOf(DiscountCalculationFromSubTotal).doubleValue();
						double discount2 = Double.valueOf(SubTotal).doubleValue();
						double Addons1 = Double.valueOf(Addons).doubleValue();
						double Postage1 = Double.valueOf(Postage).doubleValue();
						double Shipping1 = Double.valueOf(ShippingPrice).doubleValue();
						double discount3 = discount2-discount1+Addons1+Postage1+Shipping1;
						String discount4 = ""+discount3;
						String discount5 = Decimalsetting(discount4, DecimalValue);
						ExpectedAmount = Config.Currency+discount5;
						ExpectedLandingAmount = Config.Currency+SubtotalAfterPurlPrice ;
						
					}
				}
				else{	
					if(Addons.equals("0") || Addons.equals("0.0") || Addons.equals("0.00") ||
							Addons.equals("0.000") || Addons.equals("0.0000")||Postage.equals("0") ||
							Postage.equals("0.0") || Postage.equals("0.00") ||
							Postage.equals("0.000") || Postage.equals("0.0000"))
					{
					
			 ExpectedAmount = Config.Currency+DiscountcalculationfromSubTotal;
		     ExpectedLandingAmount = Config.Currency+SubtotalAfterPurlPrice ;
				    }
					else{
						double discount1 = Double.valueOf(DiscountCalculationFromSubTotal).doubleValue();
						double discount2 = Double.valueOf(Addons).doubleValue();
						double discount3 = Double.valueOf(Postage).doubleValue();
						double discount4 = discount1+discount2+discount3;
						String discount5 = ""+discount4;
						String discount6 = Decimalsetting(discount5, DecimalValue);
						
						ExpectedAmount = Config.Currency+discount6;
					     ExpectedLandingAmount = Config.Currency+SubtotalAfterPurlPrice ;
					}
				}	
			}else{
			 ExpectedAmount = Config.Currency+TotalPrice;
			}
			String ExpectedPromotionDiscount = "-"+Config.Currency+PromotionCoupon;
			// Generate promotion discount percentage based on promotion discount amount or percentage
			if(PromotionDiscountPercentage.equals("N"))
			{
				if(Subtotal.equals("0") || Subtotal.equals("0.00") || Subtotal.equals("0.000") || Subtotal.equals("0.0000")){
					System.out.println("if subtotal is  zero promotion value should be zero");
					ExpectedPromotionDiscount = "-"+Config.Currency+PromotionDiscountAfterSubtractingFromSubTotal;
				}else{
				ExpectedPromotionDiscount = "-"+Config.Currency+PromotionCoupon;
				}
			}
			else
			{
				ExpectedPromotionDiscount = "-"+Config.Currency+PromotionDiscountAfterSubtractingFromSubTotal;
			}
			//System.out.println("ExpectedPromotionDiscount : "+ExpectedPromotionDiscount);
			//String ExpectedShippingAmount = Config.Currency+ShippingPrice;
			String ExpectedTaxpercentage = "("+Tax+"%)";
			String ExpectedTaxAmount = Config.Currency+PriceAfterCalculatingTax;
			String ExpectedTotal = Config.Currency+Total;
			String ExpectedAppliedDiscount = "-"+Config.Currency+Discount;	
			// Generate Applied discount based on Discount amount or percentage
			if(DiscountPercentage.equals("N"))
			{
				ExpectedAppliedDiscount = "-"+Config.Currency+Discount;	
			}
			else
			{
				ExpectedAppliedDiscount = "-"+Config.Currency+DiscountCalculationFromSubTotal;	
			}
			
			String ExpectedAppliedAddonPrice = null;
			// Generate applied addon price based on perpice or flat rate
			if(AddonPricePerPiece.equals(""))
			{
				ExpectedAppliedAddonPrice = Config.Currency+Addons;
			}
			else
			{
				ExpectedAppliedAddonPrice = Config.Currency+AddonPricePerPiece;
			}
			
			// Click on Quantity value to scroll up screen (Screen shot purpose).
			tec.AC40.Common.Wait.wait5Second();
			//d.findElement(Property.OrderCheckoutGridQuantity).click();
			String ActualQuantity1=null;
		if(Orderelements.equalsIgnoreCase("YES")&& OrderelementsShippingAddress.equalsIgnoreCase("NO")){
			 ActualQuantity1 = d.findElement(Property.OrderCheckoutGridQuantitys).getText();
		}else{
			 ActualQuantity1 = d.findElement(Property.OrderCheckoutGridQuantity).getText();
		}

			int ActualQuantity = Double.valueOf(ActualQuantity1).intValue();
			// Compare the expected quantity with actual quantity
			if(ExpectedQuantity1 == ActualQuantity)
			{
				//!System.out.println("Both Quantity are same ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Quantity";
				System.out.println("<------- Order Checkout Both Quantitys are different ------> "+ErrorNumber);
				System.out.println("Actual Quantity is : "+ActualQuantity);
				System.out.println("Expected Quantity is : "+ExpectedQuantity);
				RW_File.WriteResult(ExpectedQuantity, ActualQuantity1, PageName, PriceType);
			}
			// Get the grid item price based on order process type condition
			String ActualOCItemPrice = null;
			String ActualOCLandingItemPrice = null;

			if(LandingPageOption.equalsIgnoreCase("YES")){
			if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") ||
					ProdutType.equals("Dynamic Email"))
			{
				ActualOCItemPrice = d.findElement(Property.OrderCheckOutGridDownloadItemPrice1).getText();
				ActualOCLandingItemPrice=d.findElement(Property.OrderCheckOutGridDownloadLandingPageItemPrice1).getText();
				if(ExpectedLandingpageItemPrice.equals(ActualOCLandingItemPrice))
				{
					//!System.out.println("Both Item prices are same ");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "ItemPrice";
					System.out.println("<------- Order Checkout Both Landing  Item prices are different 1--------> "+ErrorNumber);
					System.out.println("ActualOCLandingItemPrice 1: "+ActualOCLandingItemPrice);
					System.out.println("ExpectedLandingItemPrice 1: "+ExpectedLandingpageItemPrice);
					RW_File.WriteResult(ExpectedItemPrice, ActualOCLandingItemPrice,PageName, PriceType);
				}
			}
			else
			{
			
				ActualOCItemPrice = d.findElement(Property.OrderCheckoutGridItemPrice1).getText();
				ActualOCLandingItemPrice=d.findElement(Property.OrderCheckoutGridLandingPageItemPrice1).getText();
				if(ExpectedLandingpageItemPrice.equals(ActualOCLandingItemPrice))
				{
					//!System.out.println("Both Item prices are same ");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "ItemPrice";
					System.out.println("<------- Order Checkout Both Item prices are different 2--------> "+ErrorNumber);
					System.out.println("ActualOCLandingItemPrice 2: "+ActualOCLandingItemPrice);
					System.out.println("ExpectedLandingItemPrice 2: "+ExpectedLandingpageItemPrice);
					RW_File.WriteResult(ExpectedLandingpageItemPrice, ActualOCLandingItemPrice,PageName, PriceType);
				}
			}
			}else{
				if((Orderelements.equalsIgnoreCase("YES")&& OrderelementsAddOns.equalsIgnoreCase("NO"))||(Orderelements.equalsIgnoreCase("YES")&& OrderelementsShippingAddress.equalsIgnoreCase("NO"))){

					if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") ||
							ProdutType.equals("Dynamic Email"))
					{
						ActualOCItemPrice = d.findElement(Property.OrderCheckOutGridDownloadItemPriceo).getText();
					}
					else
					{
						ActualOCItemPrice = d.findElement(Property.OrderCheckoutGridItemPriceo).getText();
					}
				}else{
				if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") ||
						ProdutType.equals("Dynamic Email"))
				{
					ActualOCItemPrice = d.findElement(Property.OrderCheckOutGridDownloadItemPrice).getText();
				}
				else
				{
					ActualOCItemPrice = d.findElement(Property.OrderCheckoutGridItemPrice).getText();
				}
				}
			}
			// Compare grid expected item price with actual item price 
			if(ExpectedItemPrice.equals(ActualOCItemPrice))
			{
				//!System.out.println("Both Item prices are same ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "ItemPrice";
				System.out.println("<------- Order Checkout Both Item prices are different --------> "+ErrorNumber);
				System.out.println("ActualOCItemPrice : "+ActualOCItemPrice);
				System.out.println("ExpectedItemPrice : "+ExpectedItemPrice);
				RW_File.WriteResult(ExpectedItemPrice, ActualOCItemPrice,PageName, PriceType);
			}
			
			// Get grid postage price based on condition
			if(OrderBase.equals("Order") && (OrderType.equals("ShipToMyAddress") || OrderType.equals("DynDownload+Shipping") 
					|| OrderType.equals("StaticDownload+Shipping") || OrderType.equals("DynDownloadShipping") || 
					OrderType.equals("StaticDownloadShipping")))
			{
				
			}
			else
			{
			String ActualPostage = d.findElement(Property.OrderCheckoutGridPostage).getText();
			// 
			if(ExpectedPostagepirce.equals(ActualPostage))
			{
				//System.out.println("Both Postage prices are same");
			}
			else
			{
				// Order check out page postage name displayed in grid, so i have commented the below code.
				//ErrorNumber = ErrorNumber+1;
				//System.out.println("<--------- Both Postage priceses are Different --------->"+ErrorNumber);
				//System.out.println("ActualPostage : "+ActualPostage);
				//System.out.println("ExpectedPostagepirce : "+ExpectedPostagepirce);
			}
			}
			
			// Get the actual discount based on order process type
			if(EnablePromotionsORDiscounts.equals("ON"))
			{// Enable promotions or discounts on condition 
				// Get discount value
				
			String ActualOCDiscount= null;
			String ActualOCLandingDiscount= null;
			if(LandingPageOption.equalsIgnoreCase("YES")){
				if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") ||
						ProdutType.equals("Dynamic Email"))
				{
					System.out.println("ac");
					ActualOCDiscount= d.findElement(Property.OrderCheckOutGridDownloadDiscount1).getText();
					ActualOCLandingDiscount=d.findElement(Property.OrderCheckOutGridLandingPageDownloadDiscount1).getText();
				}
				else if(OrderBase.equals("Order") && (OrderType.equals("ShipToMyAddress") || OrderType.equals("DynDownload+Shipping") 
						|| OrderType.equals("StaticDownload+Shipping") || OrderType.equals("DynDownloadShipping") ||
						OrderType.equals("StaticDownloadShipping")))
				{
					System.out.println("dd");
					ActualOCDiscount= d.findElement(Property.OrderCheckOutGridDownloadDiscount1).getText();
					ActualOCLandingDiscount=d.findElement(Property.OrderCheckOutGridLandingPageDownloadDiscount1).getText();

				}
				else
				{
					System.out.println("cc");
					ActualOCDiscount= d.findElement(Property.OrderCheckoutGridDiscount1).getText();
					ActualOCLandingDiscount= d.findElement(Property.OrderCheckoutGridLandingPageDiscount1).getText();

				}
				}
			else{
			if((Orderelements.equalsIgnoreCase("YES")&& OrderelementsAddOns.equalsIgnoreCase("NO"))||(Orderelements.equalsIgnoreCase("YES")&& OrderelementsShippingAddress.equalsIgnoreCase("NO"))){
			if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") ||
					ProdutType.equals("Dynamic Email"))
			{
				System.out.println("bc");

				ActualOCDiscount= d.findElement(Property.OrderCheckOutGridDownloadDiscounto1).getText();
			}
			else if((OrderBase.equals("Order") && OrderBase.equals("Split Ship"))|| (OrderType.equals("ShipToMyAddress") || OrderType.equals("DynDownload+Shipping") 
					|| OrderType.equals("StaticDownload+Shipping") || OrderType.equals("DynDownloadShipping") ||
					OrderType.equals("StaticDownloadShipping")))
			{
				System.out.println("oc");

				ActualOCDiscount= d.findElement(Property.OrderCheckOutGridDownloadDiscounto).getText();
			}
			else
			{
				System.out.println("mc");

				ActualOCDiscount= d.findElement(Property.OrderCheckoutGridDiscounto).getText();
			}
			}else{

				if((OrderBase.equals("Order") ||OrderBase.equals("Split Ship"))&& OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") ||
						ProdutType.equals("Dynamic Email"))
				{
					System.out.println("lc");
					ActualOCDiscount= d.findElement(Property.OrderCheckOutGridDownloadDiscount).getText();
				}
				else if((OrderBase.equals("Order") || OrderBase.equals("Split Ship")) && (OrderType.equals("ShipToMyAddress") || OrderType.equals("DynDownload+Shipping") 
						|| OrderType.equals("StaticDownload+Shipping") || OrderType.equals("DynDownloadShipping") ||
						OrderType.equals("StaticDownloadShipping")||OrderType.equals("StaticDownloadShipping")||OrderType.equalsIgnoreCase("Ship items to multiple adresses")))
				{
					System.out.println("nc");
					ActualOCDiscount= d.findElement(Property.OrderCheckOutGridDownloadDiscount).getText();
				}
				else
				{
					System.out.println("gc");
					ActualOCDiscount= d.findElement(Property.OrderCheckoutGridDiscount).getText();
				}
				
			}
			}
			// Compare expected discount with Actual discount value.
			if(ExpectedDiscount.equals(ActualOCDiscount))
			{
				//!System.out.println("Both Discount prices are same ");
			}
			else
			{
				//ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Discount";
				//System.out.println("<--------Order Checkout Both Discount prices are different ---------->"+ErrorNumber);
				//System.out.println("ActualOCDiscount : "+ActualOCDiscount);
				//System.out.println("ExpectedDiscount : "+ExpectedDiscount);
				//RW_File.WriteResult(ExpectedDiscount, ActualOCDiscount,PageName, PriceType);
			}
			if(LandingPageOption.equalsIgnoreCase("YES")){
				// Compare expected Landing page discount with Actual discount value.
				if(ExpectedLandingPageDiscount.equals(ActualOCLandingDiscount))
				{
					//!System.out.println("Both Discount prices are same ");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "Discount";
					System.out.println("<--------Order Checkout Both Landing page Discount prices are different ---------->"+ErrorNumber);
					System.out.println("ActualOCLandingDiscount : "+ActualOCLandingDiscount);
					System.out.println("ExpectedLandingDiscount : "+ExpectedLandingPageDiscount);
					RW_File.WriteResult(ExpectedLandingPageDiscount, ActualOCLandingDiscount,PageName, PriceType);
				}	
			}
			}
			
			// Get the shipping or postage value based on order process type
			if(OrderBase.equals("Item"))
			{
			String ActualPostageprice = null;
			if((Orderelements.equalsIgnoreCase("YES")&& OrderelementsAddOns.equalsIgnoreCase("NO"))||(Orderelements.equalsIgnoreCase("YES")&& OrderelementsShippingAddress.equalsIgnoreCase("NO"))){
			if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") 
					|| ProdutType.equals("Dynamic Email"))
			{
				ActualPostageprice = d.findElement(Property.OrderCheckOutGridShippingorPostageBroadCasto).getText();
			}
			else
			{
				ActualPostageprice = d.findElement(Property.OrderCheckoutGridPostageo).getText();
			}
			}else{

				if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") 
						|| ProdutType.equals("Dynamic Email"))
				{
					ActualPostageprice = d.findElement(Property.OrderCheckOutGridShippingorPostageBroadCast).getText();
				}
				else
				{
					ActualPostageprice = d.findElement(Property.OrderCheckoutGridPostage).getText();
				}
				
			}
			if((ShippingPrice.equals("0") || ShippingPrice.equals("0.00") || ShippingPrice.equals("0.000") || ShippingPrice.equals("0.0000")) && (OrderType.equalsIgnoreCase("select list")))
			{// Postage price verification starts (in this we do not have shipping value)
				if(ExpectedPostagepirce.equals(ActualPostageprice))
				{
					//!System.out.println("Both postage prices are same");
				}
				else
				{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "postage price";
				System.out.println("<-------- Order check out Both postage prices are different -------->"+ErrorNumber);
				System.out.println("Actual postage price value is: "+ActualPostageprice);
				System.out.println("Expected postage price value is: "+ExpectedPostagepirce);
				RW_File.WriteResult(ExpectedPostagepirce, ActualPostageprice, PageName, PriceType);
				}
			}// Postage price verification condition ends
			else if(OrderType.equals("DynDownloadShipping")||OrderType.equals("DynShipTOMultipleLocations")||OrderType.equals("ShipToMyAddress")
					||OrderType.equals("StaticDownloadShipping")||OrderType.equals("StaticShipTOMultipleLocations")||
					OrderType.equals("StaticInventoryShipTOMultipleLocations")||OrderType.equals("DynDropShipment with List"))
			{// Shipping verification condition starts (Level I)
				//if 3rd party shipping selection shipping price changes in order check out page page
				//System.out.println("hiiii");
			   if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
			    {// Third party shipping verification starts (Level II)
					String Actualthrdpartyshippingprice2 = d.findElement(Property.OrderCheckoutGridPostage).getText();
					if(Actualthrdpartyshippingprice2.matches("\\$\\d{1,3}\\.\\d{2,4}"))
					 {
					  // System.out.println("actual 3rd party shipping method is in sc page" +Actualthrdpartyshippingprice2);
					  // System.out.println("Its 3rd party shipping we just verified in ordercheckout page whether it is value or not");
					   String expected3rdpartyshpngpricesc = Actualthrdpartyshippingprice2;
					 }
			    }// Third party shipping verification ends (Levle II)
			   else if(OrderBase.equals("Item"))
				 {//COndition with out shipping handling fee and markup fee starts (Level II)
				   if((Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00")||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000")
					||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000") || Priceafterapplyingfulfillmentshippingmarkupfee.equals("0")))
				   {
					if(ExpectedShippingPrice.equals(ActualPostageprice))
					{
						//!System.out.println("Both postageprices are same");
					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
			        	String PriceType = "Shipping price";
						System.out.println("<-------- Order CheckOut Both Shipping prices are different -------->"+ErrorNumber);
				        System.out.println("Actual Shipping price value is: "+ActualPostageprice);
			         	System.out.println("Expected Shipping price value is: "+ExpectedShippingPrice);
				        RW_File.WriteResult(ExpectedShippingPrice, ActualPostageprice, PageName, PriceType);
					}
				 }
				 }//COndition with out shipping handling fee and markup fee ends (Level II)

			 else if(!(Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00")||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000")
				||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000") || Priceafterapplyingfulfillmentshippingmarkupfee.equals("0")))
			 {// Condition with shipping handling fee are mark fee starts (Level II)
				 
				 
				 String Actualupdatedshippinghandilingfee = d.findElement(Property.OrderCheckoutGridPostage).getText();
				if(ExpectedShippingHandilingPrice.equals(Actualupdatedshippinghandilingfee))
					{
					//!System.out.println("upadetd shipping price is updated");
					}
				else
				{
					// In shopping cart page common grid cell both postage and shipping
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Shipping price";
				System.out.println("<-------- Order CheckOut Both Shipping prices are different -------->"+ErrorNumber);
				System.out.println("Actual Shipping price value is: "+Actualupdatedshippinghandilingfee);  
				System.out.println("Expected shipping price value is: "+ExpectedShippingHandilingPrice);
				RW_File.WriteResult(ExpectedShippingHandilingPrice, Actualupdatedshippinghandilingfee, PageName, PriceType);
				}
			}// Condition with shipping handling fee are mark fee ends (Level II)
			}// Shipping verification condition ends (Level I)
		}// shipping verification ends
			
			// Get the Ordercheckout grid base amount
			String ActualOCAmount = null;
			String ExpectedTotal4 = null;
			String ActualOCLandingAmount = null;
			if(LandingPageOption.equalsIgnoreCase("YES")){
				if(OrderBase.equals("Order") && (OrderType.equals("ShipToMyAddress") || OrderType.equals("DynDownload+Shipping")
						|| OrderType.equals("StaticDownload+Shipping") || OrderType.equals("DynDownloadShipping") ||
						OrderType.equals("StaticDownloadShipping")))
				{
					ActualOCAmount = d.findElement(Property.OrderCheckOutGridOrderBaseAmount1).getText();
					ActualOCLandingAmount=d.findElement(Property.OrderCheckOutGridOrderBaseLandingPageAmount1).getText();
				}
				else if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") ||
						ProdutType.equals("Dynamic Email") || OrderBase.equals("Order"))
				{
					ActualOCAmount = d.findElement(Property.OrderCheckOutGridDownloadAmount1).getText();
					ActualOCLandingAmount=d.findElement(Property.OrderCheckOutGridLandingPageDownloadAmount1).getText();
				}
				
				else
				{
					ActualOCAmount = d.findElement(Property.OrderCheckoutGridAmount1).getText();
					ActualOCLandingAmount = d.findElement(Property.OrderCheckoutGridLandingPageAmount1).getText();
				}
			}
			else{
				if((Orderelements.equalsIgnoreCase("YES")&& OrderelementsAddOns.equalsIgnoreCase("NO"))||(Orderelements.equalsIgnoreCase("YES")&& OrderelementsShippingAddress.equalsIgnoreCase("NO"))){
			if((OrderBase.equals("Order")||OrderBase.equals("Split Ship")) && (OrderType.equals("ShipToMyAddress") || OrderType.equals("DynDownload+Shipping")
					|| OrderType.equals("StaticDownload+Shipping") || OrderType.equals("DynDownloadShipping") ||
					OrderType.equals("StaticDownloadShipping")))
			{
				
				ActualOCAmount = d.findElement(Property.OrderCheckOutGridOrderBaseAmounto).getText();
			}
			else if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") ||
					ProdutType.equals("Dynamic Email") || (OrderBase.equals("Order")||OrderBase.equals("Split Ship")))
			{
				ActualOCAmount = d.findElement(Property.OrderCheckOutGridDownloadAmounto).getText();
			}
			else
			{
				ActualOCAmount = d.findElement(Property.OrderCheckoutGridAmounto).getText();
			}
		
			// Generate the order checkout grid amount if we have third party shipping
		
			if(OrderBase.equals("Item")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
			{
				String Actualthrdpartyshippingprice2 = d.findElement(Property.OrderCheckoutGridPostageo).getText();
				String Actualthrdpartyshippingprice3 = Actualthrdpartyshippingprice2.substring(1,Actualthrdpartyshippingprice2.length());
				ExpectedTotal4 = viewsummaryshippingtotal(Subtotal,Actualthrdpartyshippingprice3,Addons,DiscountcalculationfromSubTotal,OrderAmountValue,DecimalValue,Postage);
			}
		}else{

			if((OrderBase.equals("Order")||OrderBase.equals("Split Ship")) && (OrderType.equals("ShipToMyAddress") || OrderType.equals("DynDownload+Shipping")
					|| OrderType.equals("StaticDownload+Shipping") || OrderType.equals("DynDownloadShipping") ||
					OrderType.equals("StaticDownloadShipping")))
			{
				if(OrderBase.equals("Split Ship")){
					ActualOCAmount = d.findElement(Property.OrderCheckOutGridOrderBaseAmounts).getText();

				}else{
					System.out.println("pp");
				ActualOCAmount = d.findElement(Property.OrderCheckOutGridOrderBaseAmount).getText();
				}
			}
			else if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") ||
					ProdutType.equals("Dynamic Email") || (OrderBase.equals("Order")||OrderBase.equals("Split Ship")))
			{
				if(OrderBase.equals("Split Ship")){
					ActualOCAmount = d.findElement(Property.OrderCheckOutGridDownloadAmountS).getText();

				}else{
					System.out.println("map");
				ActualOCAmount = d.findElement(Property.OrderCheckOutGridDownloadAmount).getText();
				}
			}
			
			else
			{
				System.out.println("mp");
				ActualOCAmount = d.findElement(Property.OrderCheckoutGridAmount).getText();
			}
		
			// Generate the order checkout grid amount if we have third party shipping
		
			if(OrderBase.equals("Item")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
			{
				String Actualthrdpartyshippingprice2 = d.findElement(Property.OrderCheckoutGridPostage).getText();
				String Actualthrdpartyshippingprice3 = Actualthrdpartyshippingprice2.substring(1,Actualthrdpartyshippingprice2.length());
				ExpectedTotal4 = viewsummaryshippingtotal(Subtotal,Actualthrdpartyshippingprice3,Addons,DiscountcalculationfromSubTotal,OrderAmountValue,DecimalValue,Postage);
			}
		}
		}
			// Compare the order check out grid total amount if we have third party shipping
			if(OrderBase.equals("Item")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
			{
			
				if((ExpectedTotal4.equals(ActualOCAmount)))
				{
					//!System.out.println("Both Amounts are same ");
				}
				else
				{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Amount";
				System.out.println("ActualOCAmount HI");
				System.out.println("<------------ Order Checkout Both Amounts are different ------------>"+ErrorNumber);
				System.out.println("ActualOCAmount : "+ActualOCAmount);
				System.out.println("ExpectedAmount : "+ExpectedTotal4);
				RW_File.WriteResult(ExpectedTotal4, ActualOCAmount, PageName, PriceType);
				}
			}
			else
			{// Comapre the order check out grid total amount general condition (with our third party shipping)
				if((ExpectedAmount.equals(ActualOCAmount)))
				{
					//!System.out.println("Both Amounts are same ");
				}
				else
				{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Amount";
				System.out.println("<---------- Order Checkout Both Amounts are different1 ------------>"+ErrorNumber);
				System.out.println("ActualOCAmount : "+ActualOCAmount);
				System.out.println("ExpectedAmount : "+ExpectedAmount);
				RW_File.WriteResult(ExpectedAmount, ActualOCAmount, PageName, PriceType);
				}
			}
			if(LandingPageOption.equalsIgnoreCase("YES")){
				// Comapre the order check out grid landing page  total amount
				if((ExpectedLandingAmount.equals(ActualOCLandingAmount)))
				{                                    
					//!System.out.println("Both Amounts are same ");
				}
				else
				{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Amount";
				System.out.println("<---------- Order Checkout Both Landing Page Amounts are different ------------>"+ErrorNumber);
				System.out.println("ActualOCLandinAmount : "+ActualOCLandingAmount);
				System.out.println("ExpectedLandinAmount : "+ExpectedLandingAmount);
				RW_File.WriteResult(ExpectedLandingAmount, ActualOCLandingAmount, PageName, PriceType);
				}
			}
			//Click on total amount attribute to scroll down screen (Screen shot purpose).
			d.findElement(Property.OCTotal).click();
			
			//Get order check out subtotal value 
			ExpectedAmount = Config.Currency+TotalPrice;
			String ActualOCSubTotal = d.findElement(Property.OCSubTotal).getText();
			// Generate order checkout subtotal if we have third party shipping 
			if(OrderBase.equals("Item")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
			{
				String Actualthrdpartyshippingprice2 = d.findElement(Property.OrderCheckoutGridPostage).getText();
				String Actualthrdpartyshippingprice3 = Actualthrdpartyshippingprice2.substring(1,Actualthrdpartyshippingprice2.length());
				ExpectedTotal4 = viewsummaryshippingtotal(Subtotal,Actualthrdpartyshippingprice3,Addons,DiscountcalculationfromSubTotal,OrderAmountValue,DecimalValue,Postage);
			}
			
			// Verify the subtotal value in order check out page 
			if(OrderBase.equals("Item")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
			{// if third party shipping is available
					if((ExpectedTotal4.equals(ActualOCSubTotal)))
					{
						//!System.out.println("Both SubTotals are same");
					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "SubTotal";
						System.out.println("<----------- Order Checkout Both SubTotals are different ------------>"+ErrorNumber);
						System.out.println("ActualOCSubTotal : "+ActualOCSubTotal);
						System.out.println("ExpectedAmount : "+ExpectedTotal4);
						RW_File.WriteResult(ExpectedTotal4, ActualOCSubTotal, PageName, PriceType);
					}
			}
			else
			{// verify subtotal in general conditio (with out third party shipping)
				if((ExpectedAmount.equals(ActualOCSubTotal)))
				{
					//!System.out.println("Both SubTotals are same");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "SubTotal";
					System.out.println("<----------- Order Checkout Both SubTotals are different ---------->"+ErrorNumber);
					System.out.println("ActualOCSubTotal : "+ActualOCSubTotal);
					System.out.println("ExpectedAmount : "+ExpectedAmount);
					RW_File.WriteResult(ExpectedAmount, ActualOCSubTotal, PageName, PriceType);
				}
			}
			
			// Get the discount value if enable promotions or discounts on conditions
			if(EnablePromotionsORDiscounts.equals("ON"))
			{
			String ActualOCPromotionDiscount = d.findElement(Property.OCPromotionDiscount).getText();
			// Compare expecte promotion with actual promotion
			if(ExpectedPromotionDiscount.equals(ActualOCPromotionDiscount))
			{
				//!System.out.println("Both Promotion discounts are same");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "PromotionDiscount";
				System.out.println("<-------- Order Checkout Both Promotion discounts are different ---------->"+ErrorNumber);
				System.out.println("ActualOCPromotionDiscount : "+ActualOCPromotionDiscount);
				System.out.println("ExpectedPromotionDiscount : "+ExpectedPromotionDiscount);
				RW_File.WriteResult(ExpectedPromotionDiscount, ActualOCPromotionDiscount,  PageName, PriceType);
			}
			}
			//
			else
			{// Enalbe promotions or discounts off condition 
			/*	// in this we just verify the promotion discount is not available in order check out page
				String OCData = d.findElement(Property.OCData).getText();
				//System.out.println("OCData value :"+OCData);
				if(OCData.contains("Promotion Discount"))
				{
					ErrorNumber = ErrorNumber+1;
					System.out.println("Both Actual and expected values are different.");
				}*/
			}
			
			// This shipping price is availabe with only order base 
			if(OrderBase.equals("Order"))
			{
				String ActualOCShippingAmount = d.findElement(Property.OCTotalShippingPrice).getText();
				if(!(Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00")||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000")
				||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000") || Priceafterapplyingfulfillmentshippingmarkupfee.equals("0")))
				{//condition with shipping handling fee and mark up fee
					if(ExpectedShippingHandilingPrice.equals(ActualOCShippingAmount))
					{
						//!System.out.println("Both Shipping Amounts are same ");
					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "Shipping";
						System.out.println("<------------ Order Checkout Both Shipping Amounts are different -------->"+ErrorNumber);
						System.out.println("ActualOCShippingAmount : "+ActualOCShippingAmount);
						System.out.println("ExpectedShippingAmount : "+ExpectedShippingHandilingPrice);
						RW_File.WriteResult(ExpectedShippingHandilingPrice, ActualOCShippingAmount,  PageName, PriceType);
					}
				}// ends
				else if(((Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00")||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000")
						||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000") ||
						Priceafterapplyingfulfillmentshippingmarkupfee.equals("0"))) && !(Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
				{// condtion with out shipping handling fee and mark up fee
					if(ExpectedOrderBaseShippingPrice.equals(ActualOCShippingAmount))
					{
						//!System.out.println("Both Shipping Amounts are same ");
					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "Shipping";
						System.out.println("<------------ Order Checkout Both Shipping Amounts are different ---------->"+ErrorNumber);
						System.out.println("ActualOCShippingAmount : "+ActualOCShippingAmount);
						System.out.println("ExpectedShippingAmount : "+ExpectedOrderBaseShippingPrice);
						RW_File.WriteResult(ExpectedOrderBaseShippingPrice, ActualOCShippingAmount,  PageName, PriceType);
					}
				}// condition with out shipping handling fee and mark fee ends
			}// order base ends
			
		
			String[] CalculateTaxConditions = CalculateTaxCondition.split("_");
			if(IsTaxExempt.equalsIgnoreCase("yes")){}else{
			 if(OrderelementsCheckout.equalsIgnoreCase("NO")||OrderelementsBillingAddress.equalsIgnoreCase("NO"))
             {}else{	
			if(!CalculateTaxConditions[0].equals("---Select---") && CalculateTaxConditions[1].equals("ON"))
			{	String ActualOCTaxPercentage=null;
			 if(OrderelementsCheckout.equalsIgnoreCase("NO")||OrderelementsBillingAddress.equalsIgnoreCase("NO"))
			 { }else{
					 ActualOCTaxPercentage = d.findElement(Property.OCTaxPercentage).getText();
				 }
			//
			if(CalculateTaxConditions[0].equals("Vertex"))
			{
			if(ActualOCTaxPercentage.equals(""))
			{
				//!System.out.println("Both Tax percentages are saeme ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "TaxPercentage";
				System.out.println("<------------- Order Checkout Both Tax percentages are different ------------>"+ErrorNumber);
				System.out.println("ActualOCTaxPercentage : "+ActualOCTaxPercentage);
				System.out.println("ExpectedTaxpercentage : "+"");
				RW_File.WriteResult("", ActualOCTaxPercentage,  PageName, PriceType);
			}
			}
			else
			{
				//Due to 3rd party shipping methods tax changed according to the shipping method
				
				if(ExpectedTaxpercentage.equals(ActualOCTaxPercentage))
				{
					//!System.out.println("Both Tax percentages are saeme ");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "TaxPercentage";
					System.out.println("<------------- Order Checkout Both Tax percentages are different ------------>"+ErrorNumber);
					System.out.println("ActualOCTaxPercentage : "+ActualOCTaxPercentage);
					System.out.println("ExpectedTaxpercentage : "+ExpectedTaxpercentage);
					RW_File.WriteResult(ExpectedTaxpercentage, ActualOCTaxPercentage,  PageName, PriceType);
				}
			}
			}
			if(CalculateTaxConditions[0].equals("Vertex"))
			{
			String ActualOCTaxAmount = d.findElement(Property.OCTaxAmount).getText();
			if(ActualOCTaxAmount.matches("\\$\\d{1,3}\\.\\d{2,4}"))
			{
				//!System.out.println("Both Tax amounts are same");
				//!System.out.println("ActualOCTaxAmount : "+ActualOCTaxAmount);
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "TaxAmount";
				System.out.println("<--------- Order Checkout Both Tax amounts are different -------------->"+ErrorNumber);
				System.out.println("ActualOCTaxAmount : "+ActualOCTaxAmount);
				System.out.println("ExpectedTaxAmount : "+"\\$\\d{1,3}\\.\\d{2,4}");
				RW_File.WriteResult("\\$\\d{1,3}\\.\\d{2,4}", ActualOCTaxAmount, PageName, PriceType);
			}
			}
			else
			{
				String ActualOCTaxAmount = d.findElement(Property.OCTaxAmount).getText();
				String OCchangedtax = null;
				//System.out.println("expected tax is" +ExpectedTaxAmount);
				if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
				{
					OCchangedtax = d.findElement(Property.OCTaxAmount).getText();
					//System.out.println("its changed tac due to 3rd party shipping methods");
					if((OCchangedtax.equals(ActualOCTaxAmount)))
					{
						//!System.out.println("Both Tax amounts are same which was changed by shipping methods");
					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "TaxAmount";
						System.out.println("<----------- Order Checkout Both Tax amounts are different ------------>"+ErrorNumber);
						System.out.println("ActualOCTaxAmount : "+ActualOCTaxAmount);
						System.out.println("ExpectedTaxAmount : "+ExpectedTaxAmount);
						RW_File.WriteResult(ExpectedTaxAmount, ActualOCTaxAmount, PageName, PriceType);
					}
				}
				else if (OrderBase.equals("Order")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")) &&
						IsShippingTaxable.equals("Yes"))
				{
					String OrderbaseThirdPartyShippingPrice = d.findElement(Property.OCTotalShippingPrice).getText();
				
					String ExpectedTaxCalculatedValue4 = TaxValueWithThirdPartyShippingIsTaxable(OrderbaseThirdPartyShippingPrice, TotalPrice, PriceAfterApplyingCoupon,
							Tax, OrderAmountValue);
					if(ActualOCTaxAmount.equals(ExpectedTaxCalculatedValue4))
					{
						//!System.out.println("Both Tax values are Same ");
					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "TaxAmount";
						System.out.println("<------- Order Checkout Both Tax amounts are different ------------->"+ErrorNumber);
						System.out.println("ActualOCTaxAmount : "+ActualOCTaxAmount);
						System.out.println("ExpectedTaxAmount : "+ExpectedTaxCalculatedValue4);
						RW_File.WriteResult(ExpectedTaxCalculatedValue4, ActualOCTaxAmount, PageName, PriceType);
					}
					
				}
				else 
				{
					if((ExpectedTaxAmount.equals(ActualOCTaxAmount)))
					{
						//!System.out.println("Both Tax amounts are same which was changed by shipping methods");
					}
					else
					{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "TaxAmount";
					System.out.println("<---------- Order Checkout Both Tax amounts are different ------------->"+ErrorNumber);
					System.out.println("ActualOCTaxAmount : "+ActualOCTaxAmount);
					System.out.println("ExpectedTaxAmount : "+ExpectedTaxAmount);
					RW_File.WriteResult(ExpectedTaxAmount, ActualOCTaxAmount, PageName, PriceType);
					}
				}
			}
			
			}
				}
			if(CalculateTaxConditions[0].equals("Vertex"))
			{
				String ExpectedTotal1 = null;
				if(CalculateTaxConditions[1].equals("ON"))
				{
					ExpectedTotal1 =VertexTotal(ExpectedTotal);
				}
				else
				{
					ExpectedTotal1 =ExpectedTotal;
				}
				
			String ActualOCTotal = d.findElement(Property.OCTotal).getText();
			if(ExpectedTotal1.equals(ActualOCTotal))
			{
				//!System.out.println("Both GrandTotal Amounts are same ");
			}
			else
			{
				//System.out.println("666666");
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Total";
				System.out.println("<--------- Order Checkout Both GrandTotal Amounts are different ----------->"+ErrorNumber);
				System.out.println("ActualOCTotal : "+ActualOCTotal);
				System.out.println("ExpectedTotal : "+ExpectedTotal1);
				RW_File.WriteResult(ExpectedTotal1, ActualOCTotal,  PageName, PriceType);
			}
			}
			else
			{
			    String OCgrandtotal3 = null;
				String OCmarkupgrandtotal = null;
				//OCmarkupgrandtotal = null;
				String ActualOCTotal = d.findElement(Property.OCTotal).getText();
				//System.out.println("grand total printing" +ActualOCTotal);
				if(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
			    		  userordershippingorhandlingfee.equals("0.0000"))
				{
				 if(OrderBase.equals("Item")&&(Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
				 {
					//String OCactualsubtotal = d.findElement(Property.OrderCheckoutGridAmount).getText();
					//String OCactualsubtotal1 = OCactualsubtotal.substring(1,OCactualsubtotal.length());
					//String OCgrandtotal3 = null;
					// System.out.println("hello");
					String OC3rdpartytax = d.findElement(Property.OCTaxAmount).getText();
					String OC3rdpartytax1 = OC3rdpartytax.substring(1,OC3rdpartytax.length());
				    String OC3rdpartyshippingprice = d.findElement(Property.OrderCheckoutGridPostage).getText();
					String OC3rdpartyshippingprice1 = OC3rdpartyshippingprice.substring(1,OC3rdpartyshippingprice.length());
					if(IsShippingTaxable.equals("Yes"))
					{
						String UpdatedTax  = UpdatedtaxOCWithOutMarkup(Subtotal,PromotionCoupon,OrderAmountValue,Addons,
								DiscountcalculationfromSubTotal,OC3rdpartyshippingprice1,Tax); 
						if(OC3rdpartytax.equals(UpdatedTax))
						 {
							//System.out.println("oredr check out");
							//System.out.println("both upadted tax and displayed correctly.");
						 }
						else
						{
							ErrorNumber = ErrorNumber+1;
							System.out.println("tax is not correct");
							System.out.println("OP3rdpartytax :"+OC3rdpartytax);
							System.out.println("UpdatedTax :"+UpdatedTax);
						}
					}
					OCgrandtotal3 = OCgrandtotal(Subtotal,PriceAfterApplyingCoupon,OC3rdpartytax1,OC3rdpartyshippingprice1,
							Addons,DiscountcalculationfromSubTotal,OrderAmountValue, Total , IsShippingTaxable, Tax);
					//String OCgrandtotal4 = OCgrandtotal3;
				  }
				 }
				/*if(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||userordershippingorhandlingfee.equals("0.0000"))
				{
				}*/
				else
				{
					if(OrderBase.equals("Item")&&(Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
					{
						//System.out.println("hiiii");
					String OCmarkupfee = d.findElement(Property.OCHandilingfee).getText();
					String OCmarkupfee1 = OCmarkupfee.substring(1,OCmarkupfee.length());
					String OC3rdpartytax = d.findElement(Property.OCTaxAmount).getText();
					String OC3rdpartytax1 = OC3rdpartytax.substring(1,OC3rdpartytax.length());
					String OC3rdpartyshippingprice = d.findElement(Property.OrderCheckoutGridPostage).getText();
					String OC3rdpartyshippingprice1 = OC3rdpartyshippingprice.substring(1,OC3rdpartyshippingprice.length());
					if(IsShippingTaxable.equals("Yes"))
					{
						String UpdatedTax  = UpdatedtaxOCMarkup(Subtotal,PromotionCoupon,OrderAmountValue,Addons,
								DiscountcalculationfromSubTotal,OC3rdpartyshippingprice1,Tax,userordershippingorhandlingfee); 
						if(OC3rdpartytax.equals(UpdatedTax))
						 {
							System.out.println("both upadted tax and displayed correctly.");
						 }
						else
						{
							ErrorNumber = ErrorNumber+1;
							System.out.println("tax is not correct");
							System.out.println("OP3rdpartytax :"+OC3rdpartytax);
							System.out.println("UpdatedTax :"+UpdatedTax);
						}
					}
					OCmarkupgrandtotal = Ocupdatedmarkupgrandtotal(Subtotal,PromotionCoupon,OCmarkupfee1,OC3rdpartytax1,OrderAmountValue,
							Addons,DiscountcalculationfromSubTotal,OC3rdpartyshippingprice1);
					}
				}
				//String OCgrandtotal4 = OCgrandtotal3;
				if(Weighttype.equals("--Select--")&&(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
			    		  userordershippingorhandlingfee.equals("0.0000")))
				{
				   if(ExpectedTotal.equals(ActualOCTotal))
				    {
					   //!System.out.println("Both GrandTotal Amounts are same with out kgs");
				    }
				   else
				   {
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "Total";
					System.out.println("<------------ Order Checkout Both GrandTotal Amounts are different ----------->"+ErrorNumber);
					System.out.println("ActualOCTotal : "+ActualOCTotal);
					System.out.println("ExpectedTotal : "+ExpectedTotal);
					RW_File.WriteResult(ExpectedTotal, ActualOCTotal,  PageName, PriceType);
				  }
				}
				else if(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
			    		  userordershippingorhandlingfee.equals("0.0000"))
				{
				  if(OrderBase.equals("Item")&&(Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
				   {
					if(OCgrandtotal3.equals(ActualOCTotal))
					{
						//!System.out.println("Both GrandTotal Amounts are same ");
					}
				   
					else
					{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "Total";
					System.out.println("<---------- Order Checkout Both GrandTotal Amounts are different --------->"+ErrorNumber);
					System.out.println("ActualOCTotal : "+ActualOCTotal);
					System.out.println("ExpectedTotal : "+OCgrandtotal3);
					RW_File.WriteResult(OCgrandtotal3, ActualOCTotal,  PageName, PriceType);
					}
				   }
				  else
				  {
					  
				  }
				}
				else if(!(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
			    		  userordershippingorhandlingfee.equals("0.0000")))
				{
					if(OrderBase.equals("Item")&&(Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
					{
						if(OCmarkupgrandtotal.equals(ActualOCTotal))
						{
							//!System.out.println("Both GrandTotal Amounts are same handilingggg");
						}
						else
						{
							ErrorNumber = ErrorNumber+1;
							captureScreenshot();
							String PriceType = "Total";
							System.out.println("<------------ Order Checkout Both GrandTotal Amounts are different -------->"+ErrorNumber);
							System.out.println("ActualOCTotal : "+ActualOCTotal);
							System.out.println("ExpectedTotal : "+OCmarkupgrandtotal);
							RW_File.WriteResult(OCmarkupgrandtotal, ActualOCTotal,  PageName, PriceType);
		
						}
					}
					else 
					{
						if(ExpectedTotal.equals(ActualOCTotal))
						{
							//!System.out.println("Both GrandTotal Amounts are same with out kgs");  
						}
					    else
					     {
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "Total";
						System.out.println("<--------- Order Checkout Both GrandTotal Amounts are different ------>"+ErrorNumber);
						System.out.println("ActualOCTotal : "+ActualOCTotal);
						System.out.println("ExpectedTotal : "+OCmarkupgrandtotal);
						RW_File.WriteResult(OCmarkupgrandtotal, ActualOCTotal,  PageName, PriceType);
					     }
				    }
				}
			}
			/*
			String ActualAppliedTotalDiscount = d.findElement(Property.OCTotalDiscountPrice).getText();
			if(ExpectedAppliedDiscount.equals(ActualAppliedTotalDiscount))
			{
				//System.out.println("Both AppliedTotal discount are same ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				String PriceType = "AppliedTotalDiscount";
				System.out.println("<------------ Order Checkout Both AppliedTotal discount are different ---------> "+ErrorNumber);
				System.out.println("ActualAppliedTotalDiscount : "+ActualAppliedTotalDiscount);
				System.out.println("ExpectedAppliedDiscount : "+ExpectedAppliedDiscount);
				RW_File.WriteResult(ExpectedAppliedDiscount, ActualAppliedTotalDiscount,  PageName, PriceType);
			}
			
			
			String ActualAppliedAddonPrice = d.findElement(Property.OCTotalAddonPrice).getText();		
			if(ExpectedAppliedAddonPrice.equals(ActualAppliedAddonPrice))
			{
				//System.out.println("Both Applied Addon Prices are same ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				String PriceType = "AppliedAddonPrice";
				System.out.println("<--------- Order Checkout Both Applied Addon Prices are different ----------->"+ErrorNumber);
				System.out.println("ActualAppliedAddonPrice : "+ActualAppliedAddonPrice);
				System.out.println("ExpectedAppliedAddonPrice : "+ExpectedAppliedAddonPrice);
				RW_File.WriteResult(ExpectedAppliedAddonPrice, ActualAppliedAddonPrice, PageName, PriceType);
			}
			
			*/
		
		}catch (Exception e){
			ErrorNumber = ErrorNumber+1;
			captureScreenshot();
	    	  e.printStackTrace();
	      }		
	}
	
	public static String TaxValueWithThirdPartyShippingIsTaxable(String OrderbaseThirdPartyShippingPrice, String TotalPrice, 
			String PriceAfterApplyingCoupon, String Tax, String OrderAmountValue) throws InterruptedException, AWTException
	{
		MouseAdjFunction();
		String OrderbaseThirdPartyShippingPrice2 = OrderbaseThirdPartyShippingPrice.substring(1, OrderbaseThirdPartyShippingPrice.length());
		double OrderbaseThirdPartyShippingPrice3 = Double.valueOf(OrderbaseThirdPartyShippingPrice2).doubleValue();
		
		//System.out.println("TotalPrice :"+TotalPrice);
		//System.out.println("PriceAfterApplyingCoupon :"+PriceAfterApplyingCoupon);
		
		double TotalPrice2 = Double.valueOf(TotalPrice).doubleValue();
		double CouponCode2 = Double.valueOf(PriceAfterApplyingCoupon).doubleValue();
		double Taxpercentage = Double.valueOf(Tax).doubleValue();
		double ExpectedTaxVlaue2 = ((TotalPrice2+OrderbaseThirdPartyShippingPrice3-CouponCode2)*Taxpercentage)/100;
		String ExpectedTaxVlaue3 = String.valueOf(ExpectedTaxVlaue2);
		String ExpectedTaxCalculationValue = Decimalsetting(ExpectedTaxVlaue3, OrderAmountValue);
		//System.out.println("ExpectedTaxCalculationValue :"+ExpectedTaxCalculationValue);
		String  ExpectedTaxCalculatedValue4 = Config.Currency+ExpectedTaxCalculationValue;
		return ExpectedTaxCalculatedValue4;
		
	}
	
	public static String VertexTotal(String ExpectedTotal)
	{
		String ActualOCTaxAmount = d.findElement(Property.OCTaxAmount).getText();
		String ActualOCTaxAmount1 = ActualOCTaxAmount.substring(1, ActualOCTaxAmount.length());
		double ActualOCTaxAmount2 = Double.valueOf(ActualOCTaxAmount1).doubleValue();
		String ExpectedTotalAmount1 = ExpectedTotal.substring(1,ExpectedTotal.length());
		double ExpectedTotalAmount2 = Double.valueOf(ExpectedTotalAmount1).doubleValue();
		
		double Total = ActualOCTaxAmount2 + ExpectedTotalAmount2;
		String TotalValue = "$"+Total;
		
		return TotalValue;
	}
	//for third party shipping services
	
	public static String viewsummaryshippingtotal(String SubTotal,String Actualthrdpartyshippingprice3,String Addons,String DiscountcalculationfromSubTotal,
			String OrderAmountValue,String DecimalValue,String Postage) throws InterruptedException
	{
		try{
		//System.out.println("Total value calculation method starts");
			String  ExpectedSubTotal = null;	
		if (Addons.equals("0") || Addons.equals("0.0") || Addons.equals("0.00") ||
				Addons.equals("0.000") || Addons.equals("0.0000")||Postage.equals("0") || Postage.equals("0.0") || 
				Postage.equals("0.00") ||Postage.equals("0.000") || Postage.equals("0.0000")){
			System.out.println("HI");
	            ExpectedSubTotal = Config.Currency+DiscountcalculationfromSubTotal;
		}
		else{
			double subtotal1 = Double.valueOf(DiscountcalculationfromSubTotal).doubleValue();
			double Addons1 = Double.valueOf(Addons).doubleValue();
			double Postage1 = Double.valueOf(Postage).doubleValue();
			double postagetotal = subtotal1+Addons1+Postage1;
			String postagetotal1 = ""+postagetotal;
			String postagetotal2 = Decimalsetting(postagetotal1, DecimalValue);
			ExpectedSubTotal = Config.Currency+postagetotal2;
		}
	    String ExpectedAddons = Config.Currency+Addons;
		double discount1 = Double.valueOf(DiscountcalculationfromSubTotal).doubleValue();
		double discount2 = Double.valueOf(SubTotal).doubleValue();
		double discount3 = discount2-discount1;
		String discount4 = ""+discount3;
		String discount5 = Decimalsetting(discount4, DecimalValue);
		String Expecteddiscount = Config.Currency + discount5;
		String Actualaddons1 = ExpectedAddons.substring(1,ExpectedAddons.length()); 
		double ActualAddons2 = Double.valueOf(Actualaddons1).doubleValue();
		String ActualsubTotal1 = ExpectedSubTotal.substring(1,ExpectedSubTotal.length());
		double ActualSubTotal2 = Double.valueOf(ActualsubTotal1).doubleValue();
		double Actualthrdpartyshippingprice4= Double.valueOf(Actualthrdpartyshippingprice3).doubleValue();
		String ActualDiscountPercentage1 = Expecteddiscount.substring(1,Expecteddiscount.length());
		double ActualDiscountPercentage2 = Double.valueOf(ActualDiscountPercentage1).doubleValue();
		
		//System.out.println("ActualAddons2 :"+ActualAddons2); 
		//System.out.println("ActualSubTotal2 :"+ActualSubTotal2);
		//System.out.println("Actualthrdpartyshippingprice4 :"+Actualthrdpartyshippingprice4);
		//System.out.println("ActualDiscountPercentage2 :"+ActualDiscountPercentage2);
		
		
		double viewsummarytotal = ActualAddons2 + ActualSubTotal2 + Actualthrdpartyshippingprice4 - ActualDiscountPercentage2;
		String viewsummarytotal1 = "" + viewsummarytotal;
		String viewsummarytotal2 = Decimalsetting(viewsummarytotal1,OrderAmountValue);
		viewsummarytotal1 = viewsummarytotal2;
		String viewsummarytotal3 = Config.Currency+viewsummarytotal1;
		//System.out.println("shipping updated total "+viewsummarytotal3);
		return viewsummarytotal3;
		//String Viewsummarytotalfor3rdpartyshppng = "$" + viewsummarytotal;
		//return Viewsummarytotalfor3rdpartyshppng;
		}
		catch(Exception e)
		{
			ErrorNumber = ErrorNumber+1;
			captureScreenshot();
			e.printStackTrace();
			return null;
		}
		
	}
	// for grand total verification in order check out page page
	
   public static String OCgrandtotal(String Subtotal,String PromotionCoupon,String OC3rdpartytax1,
		   String OC3rdpartyshippingprice1,String Addons,String DiscountcalculationfromSubTotal,
		   String OrderAmountValue, String Total, String IsShippingTaxable, String Tax) throws InterruptedException, AWTException 
	{
		String  ExpectedSubTotal = Config.Currency+Subtotal;
		String ActualsubTotal1 = ExpectedSubTotal.substring(1,ExpectedSubTotal.length());
		double ActualsubTotal2 = Double.valueOf(ActualsubTotal1).doubleValue();
		//double OCactualsubtotal2 = Double.valueOf(OCactualsubtotal1).doubleValue();
		//System.out.println("oc subtotal is"+OCactualsubtotal2);
		String Expecteddiscount = Config.Currency + DiscountcalculationfromSubTotal;
		String ActualDiscountPercentage1 = Expecteddiscount.substring(1,Expecteddiscount.length());
	    double ActualDiscountPercentage2 = Double.valueOf(ActualDiscountPercentage1).doubleValue();
		String Expectedcouponvalue = Config.Currency+PromotionCoupon;
		//System.out.println("expected promotion is " + Expectedcouponvalue);
		String Actualcouponvalue1 = Expectedcouponvalue.substring(1,Expectedcouponvalue.length());
		double Actualcouponvalue2 = Double.valueOf(Actualcouponvalue1).doubleValue();
		double OC3rdpartytax2 = Double.valueOf(OC3rdpartytax1).doubleValue();
		double OC3rdpartyshippingprice2 = Double.valueOf(OC3rdpartyshippingprice1).doubleValue();
		String ExpectedAddons = Config.Currency+Addons;
		String Actualaddons1 = ExpectedAddons.substring(1,ExpectedAddons.length()); 
		double ActualAddons2 = Double.valueOf(Actualaddons1).doubleValue();
		
		double OCgrandtotal = ActualsubTotal2 + OC3rdpartytax2 + OC3rdpartyshippingprice2+ActualAddons2 - (Actualcouponvalue2+ActualDiscountPercentage2);
		
		/*System.out.println("ActualsubTotal2 :"+ActualsubTotal2);
		System.out.println("OC3rdpartytax2 :"+OC3rdpartytax2);
		System.out.println("OC3rdpartyshippingprice2 :"+OC3rdpartyshippingprice2);
		System.out.println("ActualAddons2 :"+ActualAddons2);
		System.out.println("Actualcouponvalue2 :"+Actualcouponvalue2);
		System.out.println("ActualDiscountPercentage2 :"+ActualDiscountPercentage2);*/
		
		String OCgrandtotal1 = "" + OCgrandtotal;
		String OCgrandtotal4 = Decimalsetting(OCgrandtotal1,OrderAmountValue);
		OCgrandtotal1 = OCgrandtotal4;
		String OCgrandtotal2 = Config.Currency+OCgrandtotal1;
		//System.out.println("ocgrand total"+OCgrandtotal2);
		return OCgrandtotal2;
		
	}
   /*
   public static String OCgrandtotalsubbu(String Subtotal,String PromotionCoupon,String OC3rdpartytax1,
		   String OC3rdpartyshippingprice1,String Addons,String DiscountcalculationfromSubTotal,
		   String OrderAmountValue, String Total, String IsShippingTaxable, String Tax) throws InterruptedException, AWTException 
	{
		
	   double TotalValue = Double.valueOf(Total).doubleValue();
	   double ShippingValue = Double.valueOf(OC3rdpartyshippingprice1).doubleValue();
	   double TaxValue = Double.valueOf(Tax).doubleValue();
	   double ShippingTax = 0;
	   if(IsShippingTaxable.equalsIgnoreCase("YES"))
	   {
		   ShippingTax = (ShippingValue * TaxValue)/100;
	   }
	   String ShippingTax1 = ""+ShippingTax;
	   String ShippingTax2 = Decimalsetting(ShippingTax1, OrderAmountValue);
	   ShippingTax1 = ShippingTax2;
	   
	   double ShippingTaxvalue = Double.valueOf(ShippingTax1).doubleValue();
	   
	   //System.out.println("STotalValue :"+TotalValue);
	   //System.out.println("SShippingValue :"+ShippingValue);
	   //System.out.println("STaxValue :"+TaxValue);
	   //System.out.println("SShippingTax :"+ShippingTaxvalue);
	   
	   double GrandTotal = TotalValue + ShippingValue + ShippingTaxvalue;
	   
	   String GradnTtoal1 = ""+GrandTotal;
	   String GradnTtoal2 = Decimalsetting(GradnTtoal1, OrderAmountValue);
	   GradnTtoal1 = GradnTtoal2;
	   GradnTtoal1 = Config.Currency+GradnTtoal1;
	   //System.out.println("SGradnTtoal1 :"+GradnTtoal1);
	   
	   return GradnTtoal1;
		
	}
   */
	//for handiling/shipping fee updated grand total
   
   public static String Ocupdatedmarkupgrandtotal(String Subtotal,String PromotionCoupon,String OCmarkupfee1,
		   String OC3rdpartytax1,String OrderAmountValue,String Addons,String DiscountcalculationfromSubTotal,
		   String OC3rdpartyshippingprice1) throws InterruptedException, AWTException
   {
	    String  ExpectedSubTotal = Config.Currency+Subtotal;
		String ActualsubTotal1 = ExpectedSubTotal.substring(1,ExpectedSubTotal.length());
		double ActualsubTotal2 = Double.valueOf(ActualsubTotal1).doubleValue();
		String Expectedcouponvalue = Config.Currency+PromotionCoupon;
		String Actualcouponvalue1 = Expectedcouponvalue.substring(1,Expectedcouponvalue.length());
		double Actualcouponvalue2 = Double.valueOf(Actualcouponvalue1).doubleValue();
		double OCmarkupfee2 = Double.valueOf(OCmarkupfee1).doubleValue();
		double OC3rdpartytax2 = Double.valueOf(OC3rdpartytax1).doubleValue();
		String ExpectedAddons = Config.Currency+Addons;
		String Actualaddons1 = ExpectedAddons.substring(1,ExpectedAddons.length()); 
		double ActualAddons2 = Double.valueOf(Actualaddons1).doubleValue();
		String Expecteddiscount = Config.Currency + DiscountcalculationfromSubTotal;
		String ActualDiscountPercentage1 = Expecteddiscount.substring(1,Expecteddiscount.length());
	    double ActualDiscountPercentage2 = Double.valueOf(ActualDiscountPercentage1).doubleValue();
	    double OC3rdpartyshippingprice2 = Double.valueOf(OC3rdpartyshippingprice1).doubleValue();
	    
	    
	    //System.out.println("ActualsubTotal2 :"+ActualsubTotal2);
	    //System.out.println("OC3rdpartytax2 :"+OC3rdpartytax2);
	    //System.out.println("OCmarkupfee2 :"+OCmarkupfee2);
	    //System.out.println("OC3rdpartyshippingprice2 :"+OC3rdpartyshippingprice2);
	    // System.out.println("ActualAddons2 :"+ActualAddons2);
	    //System.out.println("Actualcouponvalue2 :"+Actualcouponvalue2);
	    //System.out.println("ActualDiscountPercentage2 :"+ActualDiscountPercentage2);
		
		double OCupdatedtotal = ActualsubTotal2 + OC3rdpartytax2 + OCmarkupfee2 + OC3rdpartyshippingprice2 + ActualAddons2
				- (Actualcouponvalue2+ActualDiscountPercentage2);
		
		String OCupdatedtotal1 = "" + OCupdatedtotal;
		String OCupdatedtotal2 = Decimalsetting(OCupdatedtotal1,OrderAmountValue);
		OCupdatedtotal1 = OCupdatedtotal2;
		String OCupdatedtotal3 = Config.Currency+OCupdatedtotal1;
		//System.out.println("handiling fee grand ttal is"+OCupdatedtotal3);
		
		return OCupdatedtotal3;	
   }
   //updated tax due to 3rd party shipping methods with markup fee
   
   public static String UpdatedtaxOCMarkup(String Subtotal,String PromotionCoupon,String OrderAmountValue,String Addons,String DiscountcalculationfromSubTotal,
		   String OC3rdpartyshippingprice1,String Tax,String userordershippingorhandlingfee) throws InterruptedException, AWTException 
		   {
			    String  ExpectedSubTotal = Config.Currency+Subtotal;
				String ActualsubTotal1 = ExpectedSubTotal.substring(1,ExpectedSubTotal.length());
				double ActualsubTotal2 = Double.valueOf(ActualsubTotal1).doubleValue();
				String Expectedcouponvalue = Config.Currency+PromotionCoupon;
				String Actualcouponvalue1 = Expectedcouponvalue.substring(1,Expectedcouponvalue.length());
				double Actualcouponvalue2 = Double.valueOf(Actualcouponvalue1).doubleValue();
				String ExpectedAddons = Config.Currency+Addons;
				String Actualaddons1 = ExpectedAddons.substring(1,ExpectedAddons.length()); 
				double ActualAddons2 = Double.valueOf(Actualaddons1).doubleValue();
				String Expecteddiscount = Config.Currency + DiscountcalculationfromSubTotal;
				String ActualDiscountPercentage1 = Expecteddiscount.substring(1,Expecteddiscount.length());
			    double ActualDiscountPercentage2 = Double.valueOf(ActualDiscountPercentage1).doubleValue();
			    double OC3rdpartyshippingprice2 = Double.valueOf(OC3rdpartyshippingprice1).doubleValue();
			    String ExpectedTax = Tax+Config.PercentageSymbol;
			    String ActualUpdatedTax = ExpectedTax.substring(0,ExpectedTax.length()-1);
			    double ActualUpdatedTax2 =Double.valueOf(ActualUpdatedTax).doubleValue();
			    String ExpectedHandilingFee = Config.Currency+userordershippingorhandlingfee;
			    String ActualHandilingFee = ExpectedHandilingFee.substring(1,ExpectedHandilingFee.length());
			    double ActualHandilingFee1 = Double.valueOf(ActualHandilingFee).doubleValue();
			    double UpdatedTax = (((ActualsubTotal2+OC3rdpartyshippingprice2+ActualAddons2+ActualHandilingFee1) - 
			          (ActualDiscountPercentage2+Actualcouponvalue2))* ActualUpdatedTax2)/100;
			    
			    String UpdatedTax1 = ""+UpdatedTax;
			    String UpdatedTax2 = Decimalsetting(UpdatedTax1,OrderAmountValue);
			    String UpdatedTax3 = Config.Currency+UpdatedTax2;
			    ///System.out.println("updated markup tax is" +UpdatedTax3);
			    return UpdatedTax3;
			    
		   }
   
   // updated tax with out markup value
   
   public static String UpdatedtaxOCWithOutMarkup(String Subtotal,String PromotionCoupon,String OrderAmountValue,String Addons,String DiscountcalculationfromSubTotal,
		   String OC3rdpartyshippingprice1,String Tax) throws InterruptedException, AWTException 
		   {
			    String  ExpectedSubTotal = Config.Currency+Subtotal;
				String ActualsubTotal1 = ExpectedSubTotal.substring(1,ExpectedSubTotal.length());
				double ActualsubTotal2 = Double.valueOf(ActualsubTotal1).doubleValue();
				String Expectedcouponvalue = Config.Currency+PromotionCoupon;
				String Actualcouponvalue1 = Expectedcouponvalue.substring(1,Expectedcouponvalue.length());
				double Actualcouponvalue2 = Double.valueOf(Actualcouponvalue1).doubleValue();
				String ExpectedAddons = Config.Currency+Addons;
				String Actualaddons1 = ExpectedAddons.substring(1,ExpectedAddons.length()); 
				double ActualAddons2 = Double.valueOf(Actualaddons1).doubleValue();
				String Expecteddiscount = Config.Currency + DiscountcalculationfromSubTotal;
				String ActualDiscountPercentage1 = Expecteddiscount.substring(1,Expecteddiscount.length());
			    double ActualDiscountPercentage2 = Double.valueOf(ActualDiscountPercentage1).doubleValue();
			    double OC3rdpartyshippingprice2 = Double.valueOf(OC3rdpartyshippingprice1).doubleValue();
			    String ExpectedTax = Tax+Config.PercentageSymbol;
			    String ActualUpdatedTax = ExpectedTax.substring(0,ExpectedTax.length()-1);
			    double ActualUpdatedTax2 =Double.valueOf(ActualUpdatedTax).doubleValue();
			    //String ExpectedHandilingFee = Config.Currency+userordershippingorhandlingfee;
			    //String ActualHandilingFee = ExpectedHandilingFee.substring(0,ExpectedHandilingFee.length());
			   // double ActualHandilingFee1 = Double.valueOf(ActualHandilingFee).doubleValue();
			    double UpdatedTax = (((ActualsubTotal2+OC3rdpartyshippingprice2+ActualAddons2) - 
			          (ActualDiscountPercentage2+Actualcouponvalue2))* ActualUpdatedTax2)/100;
			    
			    String UpdatedTax1 = ""+UpdatedTax;
			    String UpdatedTax2 = Decimalsetting(UpdatedTax1,OrderAmountValue);
			    String UpdatedTax3 = Config.Currency+UpdatedTax2;
			    //System.out.println("updated tax " +UpdatedTax3);
			    return UpdatedTax3;
			    
		   }
   
	public static String VertexTotalOrdSum(String ExpectedTotal)
	{
		String ActualOCTaxAmount =  d.findElement(Property.OSTaxValue).getText();
		String ActualOCTaxAmount1 = ActualOCTaxAmount.substring(1, ActualOCTaxAmount.length());
		double ActualOCTaxAmount2 = Double.valueOf(ActualOCTaxAmount1).doubleValue();
		String ExpectedTotalAmount1 = ExpectedTotal.substring(1,ExpectedTotal.length());
		double ExpectedTotalAmount2 = Double.valueOf(ExpectedTotalAmount1).doubleValue();
		
		double Total = ActualOCTaxAmount2 + ExpectedTotalAmount2;
		String TotalValue = "$"+Total;
		
		return TotalValue;
	}
	
	public static String VertexTotalPrintPopup(String ExpectedTotal)
	{
		String ActualOCTaxAmount =  d.findElement(Property.PrintTaxvalue).getText();
		String ActualOCTaxAmount1 = ActualOCTaxAmount.substring(1, ActualOCTaxAmount.length());
		double ActualOCTaxAmount2 = Double.valueOf(ActualOCTaxAmount1).doubleValue();
		String ExpectedTotalAmount1 = ExpectedTotal.substring(1,ExpectedTotal.length());
		double ExpectedTotalAmount2 = Double.valueOf(ExpectedTotalAmount1).doubleValue();
		
		double Total = ActualOCTaxAmount2 + ExpectedTotalAmount2;
		String TotalValue = "$"+Total;
		
		return TotalValue;
	}
	public static String VertexTotalUserViewOrders(String ExpectedTotal)
	{
		String ActualOCTaxAmount = d.findElement(Property.VOoverallTaxValue).getText();
		String ActualOCTaxAmount1 = ActualOCTaxAmount.substring(1, ActualOCTaxAmount.length());
		double ActualOCTaxAmount2 = Double.valueOf(ActualOCTaxAmount1).doubleValue();
		String ExpectedTotalAmount1 = ExpectedTotal.substring(1,ExpectedTotal.length());
		double ExpectedTotalAmount2 = Double.valueOf(ExpectedTotalAmount1).doubleValue();
		
		double Total = ActualOCTaxAmount2 + ExpectedTotalAmount2;
		String TotalValue = "$"+Total;
		
		return TotalValue;
	}
	
	public static String PromotionvalueCalculation(String TotalPrice,String PromotionCoupon)
	{
		String Promotionvalue1 = null;
		try{
			
			double TotalPrice1 = Double.parseDouble(TotalPrice);
			double PromotionCoupon1 = Double.parseDouble(PromotionCoupon);
			double Promotionvalue = (TotalPrice1 * PromotionCoupon1)/100;
			Promotionvalue1 = ""+Promotionvalue;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return Promotionvalue1;
	}
	
	public static String TaxCalculation(String Tax, double PriceAfterSubtractCouponCode			)
	{
		String Taxvalue = null;
		try{
			double Tax1 = Double.parseDouble(Tax);
			
			double Taxvalue1 = ((PriceAfterSubtractCouponCode) * Tax1)/100;
			Taxvalue = ""+Taxvalue1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return Taxvalue;
	}
	
	public static String TaxCalculation1(String Tax, double PriceAfterSubtractCouponCode, String OrderBaseShipping,
			String userordershippingorhandlingfee)
	{
		String Taxvalue = null;
		try{
			double Tax1 = Double.parseDouble(Tax);
			double userordershippingorhandlingfee1 = Double.parseDouble(userordershippingorhandlingfee);
			double OrderBaseShipping1 = Double.parseDouble(OrderBaseShipping);
			double Taxvalue1 = ((PriceAfterSubtractCouponCode+OrderBaseShipping1+userordershippingorhandlingfee1) * Tax1)/100;
			Taxvalue = ""+Taxvalue1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return Taxvalue;
	}
	
	public static String TotalPriceCalculation(double PriceAfterSubtractCouponCode, String PriceAfterCalculatingTax
			)
	{
		String Total1 = null;
		try{
			
			double PriceAfterCalculatingTax1 = Double.parseDouble(PriceAfterCalculatingTax);
			double Total = PriceAfterSubtractCouponCode + PriceAfterCalculatingTax1;
			Total1 = ""+Total;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return Total1;
	}
	
	public static String TotalPriceCalculation1(double PriceAfterSubtractCouponCode, String PriceAfterCalculatingTax,
			String OrderBaseShipping, String userordershippingorhandlingfee)
	{
		String Total1 = null;
		try{
			
			double PriceAfterCalculatingTax1 = Double.parseDouble(PriceAfterCalculatingTax);
			double OrderBaseShipping1 = Double.parseDouble(OrderBaseShipping);
			double userordershippingorhandlingfee1 = Double.parseDouble(userordershippingorhandlingfee);
			double Total = PriceAfterSubtractCouponCode + PriceAfterCalculatingTax1+OrderBaseShipping1+userordershippingorhandlingfee1;
			Total1 = ""+Total;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return Total1;
	}
	
	public static String CalculateDiscount(String SubTotal, String Discount, String OrderAmountValue)
	{
		String Disocunt2 = null;
		try{
			double SubTotal1 = Double.parseDouble(SubTotal);
			double Discount1 =Double.parseDouble(Discount);
			double DicountValue = (SubTotal1 * Discount1)/100;
			
			Disocunt2 = ""+DicountValue;
			String DicountValue3 = Decimalsetting(Disocunt2, OrderAmountValue);
			Disocunt2 = DicountValue3;
			System.out.println("Disocunt2 :"+Disocunt2);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return Disocunt2;
	}
	
	public static String OrderSummaryPagePriceInformantion(String Quantity, String ItemPerPrice, String Discount, String Addons,
			String Postage, String TotalPrice, String Total, String PromotionCode, 
			String PromotionCoupon, String ShippingPrice, String Tax, String PriceAfterCalculatingTax, 
			String AddonPricePerPiece, String DiscountPercentage, String PromotionDiscountPercentage,
			String PromotionDiscountAfterSubtractingFromSubTotal, String DiscountCalculationFromSubTotal, String DownloadPrice,
			String OrderType, String TestCase, String TestStep, String Parameters, String ProdutType, String OrderBase
			, String OrderBaseShipping, String CalculateTaxCondition, String EnablePromotionsORDiscounts,String Weighttype,
			String Subtotal,String DiscountcalculationfromSubTotal,String OrderAmountValue,String userordershippingorhandlingfee,
			String Priceafterapplyingfulfillmentshippingmarkupfee,String IsShippingTaxable, String PriceAfterApplyingCoupon,String LandingPageOption,
			String LandingPageProduct,String LandingPageProductPP,String LandingPageDiscount,String LandingPageDiscountValue,
			String SubtotalAfterPurlPrice,String LDiscuntCalulationFromSubtotal,String DecimalValue,String SubTotal,String Orderelements,
			String OrderelementsAddOns,String OrderelementsBillingAddress,String OrderelementsInventoryLocation,String OrderelementsInventoryNumber,
			String OrderelementsPaymentDetail,String OrderelementsShippingAddress,String OrderelementsShippingMethod,String OrderelementsSpecialInstructions,
			String OrderelementsCheckout,String IsTaxExempt)
					throws InterruptedException {
		String ActualOSTotal = null;
		try{
			
			String PageName = "Order Summary";
			String ExpectedShippingHandilingPrice = Config.Currency+Priceafterapplyingfulfillmentshippingmarkupfee;
			String ActualOSPromotionDiscount = null;
			if(PromotionCoupon.equals("0") || PromotionCoupon.equals("0.00") || PromotionCoupon.equals("0.000") || PromotionCoupon.equals("0.0000"))
			{
				//System.out.println("promotion coupon code is empty");
			}
			else
			{
				if(EnablePromotionsORDiscounts.equals("ON"))
					if(Subtotal.equals("0") || Subtotal.equals("0.00") || Subtotal.equals("0.000") || Subtotal.equals("0.0000")){
						System.out.println("if subtotal is  zero promotion is not shown in  application");
					}else{
				{
				ActualOSPromotionDiscount = d.findElement(Property.OSPromotionDiscount).getText();
				}}
			}
			
			
			int ExpectedOSQuantity = Double.valueOf(Quantity).intValue();
			
			String ExpectedOSItemPrice = null;
			String ExpectedOSLandngItemPrice =Config.Currency+LandingPageProductPP;

			
			if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload"))
			{
			ExpectedOSItemPrice =Config.Currency+DownloadPrice;
			}
			else if(OrderType.equals("DynShipTOMultipleLocations") || OrderType.equals("StaticShipTOMultipleLocations")
					|| OrderType.equals("StaticInventoryShipTOMultipleLocations"))
			{
				ExpectedOSItemPrice = "-";
			}
			else
			{
				ExpectedOSItemPrice =Config.Currency+ItemPerPrice;
			}
			String ExpectedOSDiscount = null;
			String ExpectedOSLandingDiscount = null;

			if(DiscountPercentage.equals("N") && LandingPageDiscount.equals("N"))
			{
				ExpectedOSDiscount = Config.Currency+Discount;
				ExpectedOSLandingDiscount= Config.Currency+LandingPageDiscountValue;
			}
			else if(DiscountPercentage.equals("N") && LandingPageDiscount.equals("Y"))
			{
				ExpectedOSDiscount = Config.Currency+Discount;
				ExpectedOSLandingDiscount = LandingPageDiscountValue+Config.PercentageSymbol;
			}
			else if(DiscountPercentage.equals("Y") && LandingPageDiscount.equals("N"))
			{
				ExpectedOSDiscount = Discount+Config.PercentageSymbol;
				ExpectedOSLandingDiscount= Config.Currency+LandingPageDiscountValue;
			}
			else if(Discount.equals("0") || Discount.equals("0.0") || Discount.equals("0.00") ||
					Discount.equals("0.000") || Discount.equals("0.0000")||LandingPageDiscount.equals("0") ||
					LandingPageDiscount.equals("0.0") || LandingPageDiscount.equals("0.00") ||
					LandingPageDiscount.equals("0.000") || LandingPageDiscount.equals("0.0000"))
			{
				ExpectedOSDiscount = Config.Currency+Discount;
				ExpectedOSLandingDiscount= Config.Currency+LandingPageDiscountValue;
			}
			else
			{
				ExpectedOSDiscount = Discount+Config.PercentageSymbol;
				ExpectedOSLandingDiscount = LandingPageDiscountValue+Config.PercentageSymbol;
			}
			String ExpectedShippingPrice = Config.Currency+ShippingPrice;
			String ExpectedOrderBaseShippingPrice = null;
			if(OrderBase.equals("Order"))
			{
				ExpectedOrderBaseShippingPrice = Config.Currency+OrderBaseShipping;
			}
			else
			{
				ExpectedOrderBaseShippingPrice = Config.Currency+ShippingPrice;
			}
			String ExpectedPostagepirce = Config.Currency+Postage;
			String ExpectedOSAmount=null;
			String ExpectedOSLandingAmount=null;
			if(LandingPageOption.equalsIgnoreCase("YES")){ 
				if (DiscountPercentage.equalsIgnoreCase("Y"))
				{
					if((Addons.equals("0") || Addons.equals("0.0") || Addons.equals("0.00") ||
							Addons.equals("0.000") || Addons.equals("0.0000")) &&( Postage.equals("0") || Postage.equals("0.0") || 
							Postage.equals("0.00") ||Postage.equals("0.000") || Postage.equals("0.0000")) &&( ShippingPrice.equals("0") ||
							ShippingPrice.equals("0.0") || ShippingPrice.equals("0.00") ||ShippingPrice.equals("0.000") || ShippingPrice.equals("0.0000")))
							{
					double discount1 = Double.valueOf(DiscountCalculationFromSubTotal).doubleValue();
					double discount2 = Double.valueOf(SubTotal).doubleValue();
					double discount3 = discount2-discount1;
					String discount4 = ""+discount3;
					String discount5 = Decimalsetting(discount4, DecimalValue);
		
					ExpectedOSAmount  = Config.Currency+discount5;
					ExpectedOSLandingAmount = Config.Currency+SubtotalAfterPurlPrice;
					}
					else{
						if(AddonPricePerPiece.equals("0") || AddonPricePerPiece.equals("0.0") || AddonPricePerPiece.equals("0.00") ||
								AddonPricePerPiece.equals("0.000") || AddonPricePerPiece.equals("0.0000"))
						{
						double discount1 = Double.valueOf(DiscountCalculationFromSubTotal).doubleValue();
						double discount2 = Double.valueOf(SubTotal).doubleValue();
						double Addons1 = Double.valueOf(Addons).doubleValue();
						double Postage1 = Double.valueOf(Postage).doubleValue();
						double Shipping1 = Double.valueOf(ShippingPrice).doubleValue();
						double discount3 = discount2-discount1+Postage1+Addons1+Shipping1;
						String discount4 = ""+discount3;
						String discount5 = Decimalsetting(discount4, DecimalValue);
						ExpectedOSAmount  = Config.Currency+discount5;
						ExpectedOSLandingAmount = Config.Currency+SubtotalAfterPurlPrice;
						}
						else{
							double discount1 = Double.valueOf(DiscountCalculationFromSubTotal).doubleValue();
							double discount2 = Double.valueOf(SubTotal).doubleValue();
							double Addons1 = Double.valueOf(AddonPricePerPiece).doubleValue();
							double Postage1 = Double.valueOf(Postage).doubleValue();
							double Shipping1 = Double.valueOf(ShippingPrice).doubleValue();
							double discount3 = discount2-discount1+Postage1+Addons1+Shipping1;
							String discount4 = ""+discount3;
							String discount5 = Decimalsetting(discount4, DecimalValue);
							ExpectedOSAmount  = Config.Currency+discount5;
							System.out.println("ExpectedOSAmount3" +ExpectedOSAmount);
							ExpectedOSLandingAmount = Config.Currency+SubtotalAfterPurlPrice;
						}
					}
				}
				else if (DiscountPercentage.equalsIgnoreCase("N"))
				{
					if((Addons.equals("0") || Addons.equals("0.0") || Addons.equals("0.00") ||
						Addons.equals("0.000") || Addons.equals("0.0000")) && (Postage.equals("0") || Postage.equals("0.0") || 
						Postage.equals("0.00") ||Postage.equals("0.000") || Postage.equals("0.0000")) && (ShippingPrice.equalsIgnoreCase("0") ||
						ShippingPrice.equals("0.0") || ShippingPrice.equals("0.00") ||ShippingPrice.equals("0.000") || ShippingPrice.equals("0.0000")))
					{
						
					    ExpectedOSAmount  = Config.Currency+DiscountCalculationFromSubTotal;
					    ExpectedOSLandingAmount = Config.Currency+SubtotalAfterPurlPrice;
					}
					else{
						if(AddonPricePerPiece.equals("0") || AddonPricePerPiece.equals("0.0") || AddonPricePerPiece.equals("0.00") ||
								AddonPricePerPiece.equals("0.000") || AddonPricePerPiece.equals("0.0000"))
						  {
						  double discount1 = Double.valueOf(DiscountCalculationFromSubTotal).doubleValue();
						  double Addons1 = Double.valueOf(Addons).doubleValue();
						  double Postage1 = Double.valueOf(Postage).doubleValue();
						  double Shipping1 = Double.valueOf(ShippingPrice).doubleValue();
						  double discount3 = discount1+Postage1+Addons1+Shipping1;
						  String discount4 = ""+discount3;
						  String discount5 = Decimalsetting(discount4, DecimalValue);
						  ExpectedOSAmount  = Config.Currency+discount5;
						  ExpectedOSLandingAmount = Config.Currency+SubtotalAfterPurlPrice;
						  }
						  else{
							double discount1 = Double.valueOf(DiscountCalculationFromSubTotal).doubleValue();
							double Addons1 = Double.valueOf(AddonPricePerPiece).doubleValue();
							double Postage1 = Double.valueOf(Postage).doubleValue();
							double Shipping1 = Double.valueOf(ShippingPrice).doubleValue();
							double discount3 = discount1+Postage1+Addons1+Shipping1;
							String discount4 = ""+discount3;
							String discount5 = Decimalsetting(discount4, DecimalValue);
							ExpectedOSAmount  = Config.Currency+discount5;
							System.out.println("ExpectedOSAmount3" +ExpectedOSAmount);
							ExpectedOSLandingAmount = Config.Currency+SubtotalAfterPurlPrice;
						      }
					      }
				}
				
				}else{
				ExpectedOSAmount  = Config.Currency+TotalPrice;
			}			
			String ExpectedOSSubTotal =Config.Currency+TotalPrice;
			String ExpectedOSPromotionDiscount =null;
			if(PromotionDiscountPercentage.equals("N"))
			{
				ExpectedOSPromotionDiscount ="- "+Config.Currency+PromotionCoupon;
			}
			else
			{
				ExpectedOSPromotionDiscount ="- "+Config.Currency+PromotionDiscountAfterSubtractingFromSubTotal;
			}
			String ExpectedOSShipingAmount =Config.Currency+ShippingPrice;
			
			String ExpectedOSTaxPercentage =null;
			
				ExpectedOSTaxPercentage ="("+Tax+"%)";
			
			String ExpectedOSTaxValue =Config.Currency+PriceAfterCalculatingTax;
			String ExpectedOSTotal =Config.Currency+Total;
			String ExpectedOSAppliedDiscontPrice=null;
			String Discount2 = null;
			String Discount8 = null;
			if(LandingPageOption.equalsIgnoreCase("YES")){
				if(DiscountPercentage.equals("N") )
				{
				double  Discount1= Double.valueOf(Discount).doubleValue();
				double  LandingPageDiscountValue1= Double.valueOf(LDiscuntCalulationFromSubtotal).doubleValue();
				double 	Discount3=Discount1+LandingPageDiscountValue1;
				String  Discount4 = ""+Discount3;
						Discount2 = Decimalsetting(Discount4, DecimalValue);
						System.out.println("Discount2"+Discount2);
						ExpectedOSAppliedDiscontPrice ="- "+Config.Currency+Discount2;
				}
				
				else if(DiscountPercentage.equals("Y"))
				{
					double  Discount5= Double.valueOf(DiscountCalculationFromSubTotal).doubleValue();
					double  LandingPageDiscountValue2= Double.valueOf(LDiscuntCalulationFromSubtotal).doubleValue();
					double	Discount6=Discount5+LandingPageDiscountValue2;
							String  Discount7 = ""+Discount6;
							Discount8 = Decimalsetting(Discount7, DecimalValue);
							System.out.println("Discount8"+Discount8);
							ExpectedOSAppliedDiscontPrice ="- "+Config.Currency+Discount8;
				}
				
				else 
				{
					ExpectedOSAppliedDiscontPrice ="- "+Config.Currency+Discount2;
						
				}
			}
			else{
			 ExpectedOSAppliedDiscontPrice ="- "+Config.Currency+Discount;
			if(DiscountPercentage.equals("N"))
			{
				ExpectedOSAppliedDiscontPrice ="- "+Config.Currency+Discount;
			}
			else
			{
				ExpectedOSAppliedDiscontPrice ="- "+Config.Currency+DiscountCalculationFromSubTotal;
			}
			}
			String ExpectedOSAppliedAddonPrice =Config.Currency+Addons;
			String ExpectedOSAppliedTotalPostagePrice =Config.Currency+Postage;
			String ActualOSQuantity=null;
			
			if(Orderelements.equalsIgnoreCase("YES")&& OrderelementsShippingAddress.equalsIgnoreCase("NO")){
				ActualOSQuantity = d.findElement(Property.OSQuantitys).getText();
			}else{
				ActualOSQuantity = d.findElement(Property.OSQuantity).getText();
			}
			
			int ActualOSQuantity1= Double.valueOf(ActualOSQuantity).intValue();
			if(ExpectedOSQuantity == ActualOSQuantity1)
			{
				//!System.out.println("Both are Quantitys are same ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Quantity";
				System.out.println("<-------- Order Summary Bothe Quantitys are different : -------->"+ErrorNumber);
				System.out.println("ExpectedOSQuantity : "+ExpectedOSQuantity);
				System.out.println("ActualOSQuantity1 : "+ActualOSQuantity1);
				RW_File.WriteResult(Quantity, ActualOSQuantity,  PageName, PriceType);
				
			}
			String ActualOSItemPrice=null;
			String ActualOSLandingPageItemPrice=null;
			if(LandingPageOption.equalsIgnoreCase("YES")){
				ActualOSItemPrice = d.findElement(Property.OSItemPrice1).getText(); 
				ActualOSLandingPageItemPrice=d.findElement(Property.OSLandigPageItemPrice1).getText();
			}else{
				if(Orderelements.equalsIgnoreCase("YES")&& OrderelementsShippingAddress.equalsIgnoreCase("NO")){
					 ActualOSItemPrice = d.findElement(Property.OSItemPrices).getText(); 
	
				}else{
					 ActualOSItemPrice = d.findElement(Property.OSItemPrice).getText(); 

				}

			}
			if(ExpectedOSItemPrice.equals(ActualOSItemPrice))
			{
				//!System.out.println("Both item prices are same ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "ItemPrice";
				System.out.println("<----------- Order Summary Bothe Item prices are different -------->"+ErrorNumber);
				System.out.println("ExpectedOSItemPrice : "+ExpectedOSItemPrice);
				System.out.println("ActualOSItemPrice : "+ActualOSItemPrice);
				RW_File.WriteResult(ExpectedOSItemPrice, ActualOSItemPrice,  PageName, PriceType);
				
			}
			if(LandingPageOption.equalsIgnoreCase("YES")){
				if(ExpectedOSLandngItemPrice.equals(ActualOSLandingPageItemPrice))
				{
					//!System.out.println("Both item prices are same ");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "ItemPrice";
					System.out.println("<----------- Order Summary Bothe Landing Page Item prices are different -------->"+ErrorNumber);
					System.out.println("ExpectedOSLandingItemPrice : "+ExpectedOSLandngItemPrice);
					System.out.println("ActualOSLandingItemPrice : "+ActualOSLandingPageItemPrice);
					RW_File.WriteResult(ExpectedOSLandngItemPrice, ActualOSLandingPageItemPrice,  PageName, PriceType);
					
				}
			}
			if(EnablePromotionsORDiscounts.equals("ON"))
			{
				  String ActualOSDiscount =null;
				  String ActualOSLandingPageDiscount =null;
				if(LandingPageOption.equalsIgnoreCase("YES")){
					ActualOSDiscount = d.findElement(Property.OSDiscount1).getText();	
					ActualOSLandingPageDiscount=d.findElement(Property.OSLandigPageDiscount1).getText();	
					if(ExpectedOSLandingDiscount.equals(ActualOSLandingPageDiscount))
					{
						//!System.out.println("Both Discount prices are same ");
					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "Discount";
						System.out.println("<--------- Order Summary Both Landing Page Discount prices are different ------------>"+ErrorNumber);
						System.out.println("ExpectedOSLandingDiscount : "+ExpectedOSLandingDiscount);
						System.out.println("ActualOSLandingDiscount : "+ActualOSLandingPageDiscount);
						RW_File.WriteResult(ExpectedOSLandingDiscount, ActualOSLandingPageDiscount,  PageName, PriceType);
						
					}
				}else{
					if(Orderelements.equalsIgnoreCase("YES")&& OrderelementsShippingAddress.equalsIgnoreCase("NO")){
					       ActualOSDiscount = d.findElement(Property.OSDiscounts).getText();

					}else{
					       ActualOSDiscount = d.findElement(Property.OSDiscount).getText();

					}
				}
			if(ExpectedOSDiscount.equals(ActualOSDiscount))
			{
				//!System.out.println("Both Discount prices are same ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Discount";
				System.out.println("<--------- Order Summary Both Discount prices are different ------------>"+ErrorNumber);
				System.out.println("ExpectedOSDiscount : "+ExpectedOSDiscount);
				System.out.println("ActualOSDiscount : "+ActualOSDiscount);
				RW_File.WriteResult(ExpectedOSDiscount, ActualOSDiscount,  PageName, PriceType);
				
			}
			}
			
			if(OrderBase.equals("Item"))
			{
			String ActualPostageprice = null;
			if((Orderelements.equalsIgnoreCase("YES")&& OrderelementsAddOns.equalsIgnoreCase("NO"))||(Orderelements.equalsIgnoreCase("YES")&& OrderelementsShippingAddress.equalsIgnoreCase("NO"))){
				if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") || 
						ProdutType.equals("Dynamic Email"))
				{
					ActualPostageprice = d.findElement(Property.OSDownloadpostageo).getText();
				}
				else
				{
					ActualPostageprice = d.findElement(Property.OSPostageo).getText();
				}
			}else{
				if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") || 
						ProdutType.equals("Dynamic Email"))
				{
					ActualPostageprice = d.findElement(Property.OSDownloadpostage).getText();
				}
				else
				{
					ActualPostageprice = d.findElement(Property.OSPostage).getText();
				}
			}

			
			if((Orderelements.equalsIgnoreCase("YES")&& OrderelementsShippingAddress.equalsIgnoreCase("NO"))||(Orderelements.equalsIgnoreCase("YES")&& OrderelementsShippingMethod.equalsIgnoreCase("NO"))){}else{

			if((ShippingPrice.equals("0") || ShippingPrice.equals("0.00") || ShippingPrice.equals("0.000") || ShippingPrice.equals("0.0000")) && (OrderType.equalsIgnoreCase("select list")))
			{
				if(ExpectedPostagepirce.equals(ActualPostageprice))
				{
					//!System.out.println("Both postageprices are same");
				}
				else
				{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "postage price";
				System.out.println("<-------- Order Summary Both postage prices are different -------->"+ErrorNumber);
				System.out.println("Actual postage price value is: "+ActualPostageprice);
				System.out.println("Expected postage price value is: "+ExpectedPostagepirce);
				RW_File.WriteResult(ExpectedPostagepirce, ActualPostageprice,  PageName, PriceType);
				}
			}
			else if(OrderType.equals("DynDownloadShipping")||OrderType.equals("DynShipTOMultipleLocations")||OrderType.equals("ShipToMyAddress")
					||OrderType.equals("StaticDownloadShipping")||OrderType.equals("StaticShipTOMultipleLocations")||
					OrderType.equals("StaticInventoryShipTOMultipleLocations")||OrderType.equals("DynDropShipment with List"))

			{
				if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
				{
					//String OS3rdpartyshippingprice = d.findElement(Property.OSPostage).getText();
					//System.out.println("its 3rd party shipping service we verified in os page");
				}
				else if(OrderBase.equals("Item"))
				 {
					if((Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00")||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000")
					||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000") ||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0")))
					{
					  if(ExpectedShippingPrice.equals(ActualPostageprice))
					   {
						  //!System.out.println("Both postageprices are same");
					  }
					  else
					  {
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
			        	String PriceType = "Shipping price";
						System.out.println("<-------- Order Summary Both Shipping prices are different1 -------->"+ErrorNumber);
				        System.out.println("Actual Shipping price value is: "+ActualPostageprice);
			         	System.out.println("Expected Shipping price value is: "+ExpectedShippingPrice);
				        RW_File.WriteResult(ExpectedShippingPrice, ActualPostageprice, PageName, PriceType);
					  }
				   }
			    }
				else
				{
					String Actualupdatedshippinghandilingfee = d.findElement(Property.OSPostage).getText();
					if(ExpectedShippingHandilingPrice.equals(Actualupdatedshippinghandilingfee))
					{
						//!System.out.println("upadetd shipping price is updated");
					}
			    	else
			    	{
					// In shopping cart page common grid cell both postage and shipping
			    		ErrorNumber = ErrorNumber+1;
			    		captureScreenshot();
					String PriceType = "Shipping price";
					System.out.println("<-------- Order Summary Both Shipping prices are different2 -------->"+ErrorNumber);
					System.out.println("Actual Shipping price value is: "+Actualupdatedshippinghandilingfee);  
					System.out.println("Expected shipping price value is: "+ExpectedShippingHandilingPrice);
					RW_File.WriteResult(ExpectedShippingHandilingPrice, Actualupdatedshippinghandilingfee, PageName, PriceType);
				}
			}
		}
	}			
}		String ActualOSAmount = null;
			String ActualOSLandngAmount = null;
			if(LandingPageOption.equalsIgnoreCase("YES")){
				if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") || 
						ProdutType.equals("Dynamic Email") || OrderBase.equals("Order") )
						{
					ActualOSAmount = d.findElement(Property.OSDownloadAmount1).getText();
					ActualOSLandngAmount = d.findElement(Property.OSLandigPageDownloadAmount1).getText();
						}
				else
				{
					ActualOSAmount = d.findElement(Property.OSAmount).getText();
					ActualOSLandngAmount = d.findElement(Property.OSLandigPageAmount1).getText();
				}
			}
			else{
				if((Orderelements.equalsIgnoreCase("YES")&& OrderelementsAddOns.equalsIgnoreCase("NO"))||(Orderelements.equalsIgnoreCase("YES")&& OrderelementsShippingAddress.equalsIgnoreCase("NO"))){
			if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") || 
					ProdutType.equals("Dynamic Email") || OrderBase.equals("Order")||OrderBase.equals("Split Ship"))
					{
				ActualOSAmount = d.findElement(Property.OSDownloadAmounto).getText();
					}
			else
			{
				
				ActualOSAmount = d.findElement(Property.OSAmounto).getText();
			}
		}else{
			if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") || 
					ProdutType.equals("Dynamic Email") || OrderBase.equals("Order")||OrderBase.equals("Split Ship"))
					{
				if(OrderBase.equals("Split Ship")){
					ActualOSAmount = d.findElement(Property.OSDownloadAmountS).getText();

				}else{
					if(OrderBase.equals("Split Ship")){
				ActualOSAmount = d.findElement(Property.OSDownloadAmount).getText();
					}else{
						ActualOSAmount = d.findElement(Property.OSDownloadAmountS).getText();

					}
				}
				}
			else
			{
				
				ActualOSAmount = d.findElement(Property.OSAmount).getText();
			}
		}}
			String OSamount = null;
			if(OrderBase.equals("Item")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
			{
				String OS3rdpartyshippingmethod1 = d.findElement(Property.OSPostage).getText();
				String Actualthrdpartyshippingprice3= OS3rdpartyshippingmethod1.substring(1,OS3rdpartyshippingmethod1.length());
				OSamount = viewsummaryshippingtotal(Subtotal,Actualthrdpartyshippingprice3,Addons,DiscountcalculationfromSubTotal,OrderAmountValue,DecimalValue,Postage);
			}
			if(OrderBase.equals("Item")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
			{
				if(OSamount.equals(ActualOSAmount))
				{
					//!System.out.println("Both Amounts are same ");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "Amount";
					System.out.println("<-------- Order Summary Both Amounts are different1 ------------>"+ErrorNumber);
					System.out.println("ExpectedOSAmount : "+OSamount);
					System.out.println("ActualOSAmount : "+ActualOSAmount);
					RW_File.WriteResult(OSamount, ActualOSAmount, PageName, PriceType);
				}
			}
			else{
			if(ExpectedOSAmount.equals(ActualOSAmount))
			{
				//!System.out.println("Both Amounts are same ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Amount";
				System.out.println("<---------- Order Summary Both Amounts are different2 ------------>"+ErrorNumber);
				System.out.println("ExpectedOSAmount : "+ExpectedOSAmount);
				System.out.println("ActualOSAmount : "+ActualOSAmount);
				RW_File.WriteResult(ExpectedOSAmount, ActualOSAmount, PageName, PriceType);
				
			}
			}
			if(LandingPageOption.equalsIgnoreCase("YES")){
				if(ExpectedOSLandingAmount.equals(ActualOSLandngAmount))
				{
					//!System.out.println("Both Amounts are same ");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "Amount";
					System.out.println("<---------- Order Summary Both Landing Amounts are different ------------>"+ErrorNumber);
					System.out.println("ExpectedOSLandingAmount : "+ExpectedOSLandingAmount);
					System.out.println("ActualOSLandingAmount : "+ActualOSLandngAmount);
					RW_File.WriteResult(ExpectedOSLandingAmount, ActualOSLandngAmount, PageName, PriceType);
					
				}
			}
			//Click on Total amount to scroll down screen (Screen shot purpose).
			d.findElement(Property.OSTotal).click();
			
			String ActualOSSubTotal = d.findElement(Property.OSSubtotal).getText();
			String OSsubtotal = null;
			if(OrderBase.equals("Item")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
			{
				String OS3rdpartyshippingmethod1 = d.findElement(Property.OSPostage).getText();
				String Actualthrdpartyshippingprice3= OS3rdpartyshippingmethod1.substring(1,OS3rdpartyshippingmethod1.length());
				OSsubtotal = viewsummaryshippingtotal(Subtotal,Actualthrdpartyshippingprice3,Addons,DiscountcalculationfromSubTotal,OrderAmountValue,DecimalValue,Postage);
				if(OSsubtotal.equals(ActualOSSubTotal))
				{
					//!System.out.println("Both subtotal are same ");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "SubTotal";
					System.out.println("<--------Order Summary Both SubTotal are different ---------->"+ErrorNumber);
					System.out.println("ExpectedOSSubTotal : "+ExpectedOSSubTotal);
					System.out.println("ActualOSSubTotal : "+ActualOSSubTotal);
					RW_File.WriteResult(ExpectedOSSubTotal, ActualOSSubTotal, PageName, PriceType);
					
				}
			}
			else{
			if(ExpectedOSSubTotal.equals(ActualOSSubTotal))
			{
				//!System.out.println("Both subtotal are same ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "SubTotal";
				System.out.println("<----------Order Summary Both SubTotal are different ---------->"+ErrorNumber);
				System.out.println("ExpectedOSSubTotal : "+ExpectedOSSubTotal);
				System.out.println("ActualOSSubTotal : "+ActualOSSubTotal);
				RW_File.WriteResult(ExpectedOSSubTotal, ActualOSSubTotal, PageName, PriceType);
				
			}
			}
			
			if(PromotionCoupon.equals("0") || PromotionCoupon.equals("0.00") || PromotionCoupon.equals("0.000") || PromotionCoupon.equals("0.0000"))
			{
				
			}
			else
			{
				if(EnablePromotionsORDiscounts.equals("ON"))
				{
					if(Subtotal.equals("0") || Subtotal.equals("0.00") || Subtotal.equals("0.000") || Subtotal.equals("0.0000")){
						System.out.println("if subtotal is  zero promotion value not displayed in OS");
					}else{
				if(ExpectedOSPromotionDiscount.equals(ActualOSPromotionDiscount))
				{
					
					//!System.out.println("Both Promotion Discount are same");
				}
				else
				{
					
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "PromotionDiscount";
				System.out.println("<--------- Order Summary Both Promotion Discount are different --------> "+ErrorNumber);
				System.out.println("ExpectedOSPromotionDiscount : "+ExpectedOSPromotionDiscount);
				System.out.println("ActualOSPromotionDiscount : "+ActualOSPromotionDiscount);
				RW_File.WriteResult(ExpectedOSPromotionDiscount, ActualOSPromotionDiscount,  PageName, PriceType);
				
				}
			    }
				}
				/*else
				{
					String OSData1 = d.findElement(Property.OSData1).getText();
					//System.out.println("OSData1 value "+OSData1);
					if(OSData1.contains("Promotion Discount"))
					{
						ErrorNumber = ErrorNumber+1;
						System.out.println("Both Actual and expected values are different");
					}
				}*/
			}
			
			if(OrderBase.equals("Order"))
			{
			String ActualOSShipingAmount = d.findElement(Property.OSShippingAmount).getText();
				if (Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
				{ 
					if(ActualOSShipingAmount.matches("\\$\\d{1,3}\\.\\d{2,4}"))
					{
						//!System.out.println("Shipping value is number");
					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "Third party ShipingAmount";
						System.out.println("<----------- Order Summary Both shipping amounts are different -------->"+ErrorNumber);
						System.out.println("ExpectedOSShipingAmount : "+"\\$\\d{1,3}\\.\\d{2,4}");
						System.out.println("ActualOSShipingAmount : "+ActualOSShipingAmount);
						RW_File.WriteResult("\\$\\d{1,3}\\.\\d{2,4}", ActualOSShipingAmount,  PageName, PriceType);
					}
					
				}
				else if((Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00")||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000")
				||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000") || Priceafterapplyingfulfillmentshippingmarkupfee.equals("0")))
				{
					if(ExpectedOrderBaseShippingPrice.equals(ActualOSShipingAmount))
					{
						//!System.out.println("Both Shipping amounts are same ");
					}
					else
					{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "ShipingAmount";
					System.out.println("<----------- Order Summary Both shipping amounts are different -------->"+ErrorNumber);
					System.out.println("ExpectedOSShipingAmount : "+ExpectedOrderBaseShippingPrice);
					System.out.println("ActualOSShipingAmount : "+ActualOSShipingAmount);
					RW_File.WriteResult(ExpectedOrderBaseShippingPrice, ActualOSShipingAmount,  PageName, PriceType);
					}
				}
				else
				{
					if(ExpectedShippingHandilingPrice.equals(ActualOSShipingAmount))
					{
						//!System.out.println("Both Shipping amounts are same ");
					}
					else
					{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "ShipingAmount";
					System.out.println("<----------- Order Summary Bothe shipping amounts are different ------------>"+ErrorNumber);
					System.out.println("ExpectedOSShipingAmount : "+ExpectedShippingHandilingPrice);
					System.out.println("ActualOSShipingAmount : "+ActualOSShipingAmount);
					RW_File.WriteResult(ExpectedShippingHandilingPrice, ActualOSShipingAmount,  PageName, PriceType);
					}
				}
			}
			
			String[] CalculateTaxConditions = CalculateTaxCondition.split("_");
			if(IsTaxExempt.equalsIgnoreCase("yes")){}else{
			 if(OrderelementsCheckout.equalsIgnoreCase("NO")||OrderelementsBillingAddress.equalsIgnoreCase("NO")){}else{

			if(!CalculateTaxConditions[0].equals("---Select---") && CalculateTaxConditions[1].equals("ON"))
			{
			String ActualOSTaxPercentage = null;
			if(PromotionCoupon.equals("0") || PromotionCoupon.equals("0.00") || PromotionCoupon.equals("0.000") || PromotionCoupon.equals("0.0000"))
			{
				ActualOSTaxPercentage = d.findElement(Property.OSTaxpercentageWhenDiscountZero).getText();
			}
			else
			{
			ActualOSTaxPercentage = d.findElement(Property.OSTaxpercentage).getText();
			}
			
			if(CalculateTaxConditions[0].equals("Vertex"))
			{
			if(ActualOSTaxPercentage.equals(""))
			{
				//!System.out.println("Both Tax percentages are same ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "TaxPercentage";
				System.out.println("<----------- Order Summary Both Tax percentages are different ------------->"+ErrorNumber);
				System.out.println("ExpectedOSTaxPercentage : "+"");
				System.out.println("ActualOSTaxPercentage : "+ActualOSTaxPercentage);
				RW_File.WriteResult("", ActualOSTaxPercentage,PageName, PriceType);
				
			}
			}
			else
			{
				if(ExpectedOSTaxPercentage.equals(ActualOSTaxPercentage))
				{
					//!System.out.println("Both Tax percentages are same ");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "TaxPercentage";
					System.out.println("<------------- Order Summary Both Tax percentages are different ------------->"+ErrorNumber);
					System.out.println("ExpectedOSTaxPercentage : "+ExpectedOSTaxPercentage);
					System.out.println("ActualOSTaxPercentage : "+ActualOSTaxPercentage);
					RW_File.WriteResult(ExpectedOSTaxPercentage, ActualOSTaxPercentage,PageName, PriceType);
					
				}
			}
			///////////kkkkkkkkkkkk
			if(CalculateTaxConditions[0].equals("Vertex"))
			{
			String ActualOSTaxValue = d.findElement(Property.OSTaxValue).getText();
			if(ActualOSTaxValue.matches("\\$\\d{1,3}\\.\\d{2,4}"))
			{
				//!System.out.println("Both Tax values are same ");
				//!System.out.println("ActualOSTaxValue : "+ActualOSTaxValue);
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "TaxValue";
				System.out.println("<----------Order Summary Both Tax values are different ---------->"+ErrorNumber);
				System.out.println("ExpectedOSTaxValue : "+"\\$\\d{1,3}\\.\\d{2,4}");
				System.out.println("ActualOSTaxValue : "+ActualOSTaxValue);
				RW_File.WriteResult("\\$\\d{1,3}\\.\\d{2,4}", ActualOSTaxValue,  PageName, PriceType);
				
			}
			}
			else
			{
				String ActualOSTaxValue = d.findElement(Property.OSTaxValue).getText();
				String OS3rdpartychagedtax1 = null;
				if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
				{
					OS3rdpartychagedtax1 = d.findElement(Property.OSTaxValue).getText();
					//System.out.println("these tax changed according to the 3rd party shipping prices just verified it");
				}
				if(OrderBase.equals("Item")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
				{
					if(OS3rdpartychagedtax1.equals(ActualOSTaxValue))
					{
						//!System.out.println("Both Tax values are same ");
					}
					else
					{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "TaxValue";
					System.out.println("<------------Order Summary Both Tax values are different --------->"+ErrorNumber);
					System.out.println("ExpectedOSTaxValue : "+ExpectedOSTaxValue);
					System.out.println("ActualOSTaxValue : "+ActualOSTaxValue);
					RW_File.WriteResult(ExpectedOSTaxValue, ActualOSTaxValue,  PageName, PriceType);
					
					}
				}
				else if(OrderBase.equals("Order")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")) &&
						IsShippingTaxable.equals("Yes"))
				{
					
					String OrderbaseThirdPartyShippingPrice = d.findElement(Property.OSShippingAmount).getText();

					 OS3rdpartychagedtax1 = TaxValueWithThirdPartyShippingIsTaxable(OrderbaseThirdPartyShippingPrice, TotalPrice, PriceAfterApplyingCoupon,
												Tax, OrderAmountValue);
					
					if(OS3rdpartychagedtax1.equals(ActualOSTaxValue))
					{
						//!System.out.println("Both Tax values are same ");
					}
					else
					{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "TaxValue";
					System.out.println("<------------Order Summary Both Tax values are different --------->"+ErrorNumber);
					System.out.println("ExpectedOSTaxValue : "+OS3rdpartychagedtax1);
					System.out.println("ActualOSTaxValue : "+ActualOSTaxValue);
					RW_File.WriteResult(OS3rdpartychagedtax1, ActualOSTaxValue,  PageName, PriceType);
					
					}
				}
				else
				{
					if(ExpectedOSTaxValue.equals(ActualOSTaxValue))
					{
						//!System.out.println("Both Tax values are same ");
					}
					else
					{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "TaxValue";
					System.out.println("<--------Order Summary Both Tax values are different ---------->"+ErrorNumber);
					System.out.println("ExpectedOSTaxValue : "+ExpectedOSTaxValue);
					System.out.println("ActualOSTaxValue : "+ActualOSTaxValue);
					RW_File.WriteResult(ExpectedOSTaxValue, ActualOSTaxValue,  PageName, PriceType);
					
					}
				}
			}
		}
			else
			{
				String ActualOSTaxPercentage = null;
				if(PromotionCoupon.equals("0") || PromotionCoupon.equals("0.00") || PromotionCoupon.equals("0.000") || PromotionCoupon.equals("0.0000"))
				{
					ActualOSTaxPercentage = d.findElement(Property.OSTaxpercentageWhenDiscountZero).getText();
				}
				else
				{
				ActualOSTaxPercentage = d.findElement(Property.OSTaxpercentage).getText();
				}
				if(ActualOSTaxPercentage.equals(""))
				{
					//!System.out.println("Both Tax percentages are same ");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "TaxPercentage";
					System.out.println("<----------- Order Summary Both Tax percentages are different ------------->"+ErrorNumber);
					System.out.println("ExpectedOSTaxPercentage : "+"");
					System.out.println("ActualOSTaxPercentage : "+ActualOSTaxPercentage);
					RW_File.WriteResult("", ActualOSTaxPercentage,PageName, PriceType);
					
				}
				
				
				String ActualOSTaxValue = d.findElement(Property.OSTaxValue).getText();
				if(ActualOSTaxValue.equals(""))
				{
					//!System.out.println("Both Tax values are same ");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "TaxValue";
					System.out.println("<----------- Order Summary Both Tax values are different ---------->"+ErrorNumber);
					System.out.println("ExpectedOSTaxValue : "+"");
					System.out.println("ActualOSTaxValue : "+ActualOSTaxValue);
					RW_File.WriteResult("", ActualOSTaxValue,  PageName, PriceType);
					
				}
				
			}}
		}
			String ActualOSOrderBaseValue=null;
		
			if((OrderelementsCheckout.equalsIgnoreCase("Yes")&&OrderelementsShippingAddress.equalsIgnoreCase("NO"))||(OrderelementsCheckout.equalsIgnoreCase("Yes")&&OrderelementsShippingMethod.equalsIgnoreCase("NO")))
			{}else{
				ActualOSOrderBaseValue= d.findElement(Property.OSShippingValue).getText();

			}
			
			String OSchangedshippingvalue = null;
		if(OrderType.equals("DynDownloadShipping")||OrderType.equals("DynShipTOMultipleLocations")||OrderType.equals("ShipToMyAddress")
					||OrderType.equals("StaticDownloadShipping")||OrderType.equals("StaticShipTOMultipleLocations")||
					OrderType.equals("StaticInventoryShipTOMultipleLocations")||OrderType.equals("DynDropShipment with List"))
		{
			  if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
			   {
				  if(Orderelements.equalsIgnoreCase("YES")&& OrderelementsAddOns.equalsIgnoreCase("NO")||((Orderelements.equalsIgnoreCase("YES")&& OrderelementsShippingAddress.equalsIgnoreCase("NO")))){
				OSchangedshippingvalue = d.findElement(Property.OSShippingValue).getText();
				  }else{
						OSchangedshippingvalue = d.findElement(Property.OSShippingValue).getText();
				  }
			   }
			  else if(OrderBase.equals("Item"))
			    {
				  if((Orderelements.equalsIgnoreCase("YES")&& OrderelementsShippingAddress.equalsIgnoreCase("NO"))||(Orderelements.equalsIgnoreCase("YES")&& OrderelementsShippingMethod.equalsIgnoreCase("NO"))){}else{
				  if((Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00")||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000")
							||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000") || Priceafterapplyingfulfillmentshippingmarkupfee.equals("0")))
							{
							  if(ExpectedOrderBaseShippingPrice.equals(ActualOSOrderBaseValue))
						       {
								  //!System.out.println("Both Tax values are same ");
						       }
							 else
							  {
								ErrorNumber = ErrorNumber+1;
								captureScreenshot();
					        	String PriceType = "Shipping price";
								System.out.println("<-------- Order Summary Both Shipping prices are different3 -------->"+ErrorNumber);
						        System.out.println("Actual Shipping price value is: "+ActualOSOrderBaseValue);
					         	System.out.println("Expected Shipping price value is: "+ExpectedOrderBaseShippingPrice);
						        RW_File.WriteResult(ExpectedOrderBaseShippingPrice, ActualOSOrderBaseValue, PageName, PriceType);
							  }
						 }
			    }
		 }
		 else if(!(Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00")||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000")
						||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000") || 
						Priceafterapplyingfulfillmentshippingmarkupfee.equals("0")) && OrderBase.equals("Item"))
			  {
			  if(Orderelements.equalsIgnoreCase("YES")&& OrderelementsShippingAddress.equalsIgnoreCase("NO")){}else{

				 String Actualupdatedshippinghandilingfee = d.findElement(Property.OSPostage).getText();
					if(ExpectedShippingHandilingPrice.equals(Actualupdatedshippinghandilingfee))
					{
						//!System.out.println("upadetd shipping price is updated");
					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "Shipping";
						System.out.println("<------------Order Summary Both Shipping values are different ---------->"+ErrorNumber);
						System.out.println("ExpectedOSShippingValue : "+ExpectedShippingHandilingPrice);
						System.out.println("ActualOSShippingValue : "+Actualupdatedshippinghandilingfee);
						RW_File.WriteResult(ExpectedShippingHandilingPrice, Actualupdatedshippinghandilingfee,  PageName, PriceType);
					}
			 }
		 }
		}
			if(CalculateTaxConditions[0].equals("Vertex"))
			{
				if(CalculateTaxConditions[1].equals("ON"))
				{
					ActualOSTotal = d.findElement(Property.OSTotal).getText();
					String ExpectedOSTotal1 =VertexTotalOrdSum(ExpectedOSTotal);
					if(ExpectedOSTotal1.equals(ActualOSTotal))
					{
						//!System.out.println("Both Total values are same ");
						//System.out.println("ExpectedOSTotal1 : "+ExpectedOSTotal1);
					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "Total";
						System.out.println("<--------- Order Summary Both Total values are different -------->"+ErrorNumber);
						System.out.println("ExpectedOSTotal : "+ExpectedOSTotal1);
						System.out.println(" ActualOSTotal : "+ActualOSTotal);
						RW_File.WriteResult(ExpectedOSTotal1, ActualOSTotal,  PageName, PriceType);
					}
				}
				else
				{
					ActualOSTotal = d.findElement(Property.OSTotal).getText();
					String ExpectedOSTotal1 =ExpectedOSTotal;
					if(ExpectedOSTotal1.equals(ActualOSTotal))
					{
						//!System.out.println("Both Total values are same ");
						//System.out.println("ExpectedOSTotal1 : "+ExpectedOSTotal1);
					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "Total";
						System.out.println("<-------- Order Summary Both Total values are different -------->"+ErrorNumber);
						System.out.println("ExpectedOSTotal : "+ExpectedOSTotal1);
						System.out.println(" ActualOSTotal : "+ActualOSTotal);
						RW_File.WriteResult(ExpectedOSTotal1, ActualOSTotal,  PageName, PriceType);
					}
				}
			}
			else
			{
				ActualOSTotal = d.findElement(Property.OSTotal).getText();
				String OSgrandtotal = null;
				String updatedmarkupgrandtotal = null;
				if(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
			    		  userordershippingorhandlingfee.equals("0.0000"))
				{
				 if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
				  {
					//String OCactualsubtotal = d.findElement(Property.OrderCheckoutGridAmount).getText();
					//String OCactualsubtotal1 = OCactualsubtotal.substring(1,OCactualsubtotal.length());
					String OS3rdpartytax = d.findElement(Property.OSTaxValue).getText();
					String OS3rdpartytax1 = OS3rdpartytax.substring(1,OS3rdpartytax.length());
				    String OS3rdpartyshippingprice =  d.findElement(Property.OSShippingValue).getText();
					String OS3rdpartyshippingprice1 = OS3rdpartyshippingprice.substring(1,OS3rdpartyshippingprice.length());
					if(IsShippingTaxable.equals("Yes"))
					{
						String UpdatedTax  = UpdatedtaxOCWithOutMarkup(Subtotal,PromotionCoupon,OrderAmountValue,Addons,
								DiscountcalculationfromSubTotal,OS3rdpartyshippingprice1,Tax); 
						if(OS3rdpartytax.equals(UpdatedTax))
						 {
							System.out.println("both upadted tax and displayed correctly.");
						 }
						else
						{
							ErrorNumber = ErrorNumber+1;
							System.out.println("tax is not correct");
							System.out.println("OP3rdpartytax :"+OS3rdpartytax);
							System.out.println("UpdatedTax :"+UpdatedTax);
						}
					}
					OSgrandtotal = OCgrandtotal(Subtotal,PriceAfterApplyingCoupon,OS3rdpartytax1,OS3rdpartyshippingprice1,
							Addons,DiscountcalculationfromSubTotal,OrderAmountValue, Total, IsShippingTaxable, Tax);
					
				  }
				}
				else
				{  
					if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
					{
					String OS3rdpartytax = d.findElement(Property.OSTaxValue).getText();
					String OS3rdpartytax1 = OS3rdpartytax.substring(1,OS3rdpartytax.length());
				    String OS3rdpartyshippingprice =  d.findElement(Property.OSShippingValue).getText();
					String OS3rdpartyshippingprice1 = OS3rdpartyshippingprice.substring(1,OS3rdpartyshippingprice.length());
					String OShandilingfee = null;
					if(Config.LayoutType.equals("Layout1"))
					{
						OShandilingfee = d.findElement(Property.OSHandlingVlaueL1).getText();
					}
					else
					{
						OShandilingfee = d.findElement(Property.OShandilingvalue).getText();
					}
					String OShandilingfee1 = OShandilingfee.substring(1,OShandilingfee.length());
					if(IsShippingTaxable.equals("Yes"))
					{
						String UpdatedTax  = UpdatedtaxOCMarkup(Subtotal,PromotionCoupon,OrderAmountValue,Addons,
								DiscountcalculationfromSubTotal,OS3rdpartyshippingprice1,Tax,userordershippingorhandlingfee); 
						if(OS3rdpartytax.equals(UpdatedTax))
						 {
							System.out.println("both upadted tax and displayed correctly.");
						 }
						else
						{
							ErrorNumber = ErrorNumber+1;
							System.out.println("tax is not correct");
							System.out.println("OP3rdpartytax :"+OS3rdpartytax);
							System.out.println("UpdatedTax :"+UpdatedTax);
						}
					}
					updatedmarkupgrandtotal = Ocupdatedmarkupgrandtotal(Subtotal,PriceAfterApplyingCoupon,OShandilingfee1,OS3rdpartytax1,OrderAmountValue,
							Addons,DiscountcalculationfromSubTotal,OS3rdpartyshippingprice1);
					}
				}
				if(Weighttype.equals("--Select--")&&(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
			    		  userordershippingorhandlingfee.equals("0.0000")))
				  {
				     if(ExpectedOSTotal.equals(ActualOSTotal))
				      {
				    	 //!System.out.println("Both Total values are same in OS page");
				      }
				     else
						{
							ErrorNumber = ErrorNumber+1;
							captureScreenshot();
							String PriceType = "Total";
							System.out.println("<---------- Order Summary Both Total values are different ---------->"+ErrorNumber);
							System.out.println("ExpectedOSTotal : "+ExpectedOSTotal);
							System.out.println(" ActualOSTotal : "+ActualOSTotal);
							RW_File.WriteResult(ExpectedOSTotal, ActualOSTotal,  PageName, PriceType);
						}
				  }
				else if(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
			    		  userordershippingorhandlingfee.equals("0.0000"))
				{
					if(OrderBase.equals("Item")&&(Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
					{
					  if(OSgrandtotal.equals(ActualOSTotal))
				        {
						  //!System.out.println("Both Total values are same in OS page");
				        }
					  else
					    {
							ErrorNumber = ErrorNumber+1;
							captureScreenshot();
							String PriceType = "Total";
							System.out.println("<------- Order Summary Both Total values are different -------->"+ErrorNumber);
							System.out.println("ExpectedOSTotal : "+OSgrandtotal);
							System.out.println(" ActualOSTotal : "+ActualOSTotal);
							RW_File.WriteResult(OSgrandtotal, ActualOSTotal,  PageName, PriceType);
						} 
					}
				}
				else if(!(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
			    		  userordershippingorhandlingfee.equals("0.0000")))
				{
					if(OrderBase.equals("Item")&&(Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
					  {
				        if(updatedmarkupgrandtotal.equals(ActualOSTotal))
				           {
				        	//!System.out.println("Both Total values are same in OS page");
				           }
				        else
				          {
				        	ErrorNumber = ErrorNumber+1;
				        	captureScreenshot();
							String PriceType = "Total";
							System.out.println("<----------- Order Summary Both Total values are different -------->"+ErrorNumber);
							System.out.println("ExpectedOSTotal : "+updatedmarkupgrandtotal);
							System.out.println(" ActualOSTotal : "+ActualOSTotal);
							RW_File.WriteResult(updatedmarkupgrandtotal, ActualOSTotal,  PageName, PriceType);
				          }
					  }
					else
					{
						if(ExpectedOSTotal.equals(ActualOSTotal))
					      {
							//!System.out.println("Both Total values are same in OS page");
					      }
					     else
							{
								ErrorNumber = ErrorNumber+1;
								captureScreenshot();
								String PriceType = "Total";
								System.out.println("<------ Order Summary Both Total values are different -------->"+ErrorNumber);
								System.out.println("ExpectedOSTotal : "+ExpectedOSTotal);
								System.out.println(" ActualOSTotal : "+ActualOSTotal);
								RW_File.WriteResult(ExpectedOSTotal, ActualOSTotal,  PageName, PriceType);
							}
					}
				}
			}
			
			if(EnablePromotionsORDiscounts.equals("ON"))
			{
			String ActualOSAppliedDiscontPrice = d.findElement(Property.OSAppliedTotalDiscount).getText();
			if(ExpectedOSAppliedDiscontPrice.equals(ActualOSAppliedDiscontPrice))
			{
				//!System.out.println("Both Applied Discount prices are same");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "AppliedDiscontPrice";
				System.out.println("<----------Order Summary Both Applied Discount values are different ------------->"+ErrorNumber);
				System.out.println("ExpectedOSAppliedDiscontPrice : "+ExpectedOSAppliedDiscontPrice);
				System.out.println("ActualOSAppliedDiscontPrice : "+ActualOSAppliedDiscontPrice);
				RW_File.WriteResult(ExpectedOSAppliedDiscontPrice, ActualOSAppliedDiscontPrice,  PageName, PriceType);
			}
			}
			/*else  // Unable to find the element in promotion discount OFF mode so comment the code
			{
				String OSData2 = d.findElement(Property.OSData2).getText();
				//System.out.println("OSData2 value "+OSData2);
				if(!OSData2.contains(""))
				{
					ErrorNumber = ErrorNumber+1;
					System.out.println("Both Actual ane expected values are different");
				}
			}*/
			
			if(OrderelementsAddOns.equalsIgnoreCase("NO")){}else{
			String ActualOSAppliedAddonPrice = d.findElement(Property.OSAppliedTotalAddonPrice).getText();
			if(ExpectedOSAppliedAddonPrice.equals(ActualOSAppliedAddonPrice))
			{
				//!System.out.println("Both Applied Addon prices are same ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "AppliedAddonPrice";
				System.out.println("<------------ Order Summary Both Applied Addon prices are different----------->"+ErrorNumber);
				System.out.println("ExpectedOSAppliedAddonPrice : "+ExpectedOSAppliedAddonPrice);
				System.out.println("ActualOSAppliedAddonPrice : "+ActualOSAppliedAddonPrice);
				RW_File.WriteResult(ExpectedOSAppliedAddonPrice, ActualOSAppliedAddonPrice,  PageName, PriceType);
				
			}
			}
			if(Postage.equals("0") || Postage.equals("0.00") || Postage.equals("0.000") || Postage.equals("0.0000"))
			{
				
			}
			else
			{			
			
			String ActualOSAppliedTotalPostagePrice = d.findElement(Property.OSAppliedTotalPostagePrice).getText();
			if(ExpectedOSAppliedTotalPostagePrice.equals(ActualOSAppliedTotalPostagePrice))
			{
				//!System.out.println("Both Applied Total postage prices are same ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "AppliedTotalPostagePrice";
				System.out.println("<-------------- Order Summary Both Applied Total Postage prices are different ------------->"+ErrorNumber);
				System.out.println("ExpectedOSAppliedTotalPostagePrice : "+ExpectedOSAppliedTotalPostagePrice);
				System.out.println("ActualOSAppliedTotalPostagePrice : "+ActualOSAppliedTotalPostagePrice);
				RW_File.WriteResult(ExpectedOSAppliedTotalPostagePrice, ActualOSAppliedTotalPostagePrice,  PageName, PriceType);
				
			}
			}
			
			tec.AC40.Common.Wait.wait5Second();
			
				}catch (Exception e){
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					e.printStackTrace();
	      }
		return ActualOSTotal;
	}
	
	public static void ShipAddSameAsBillAdd(String BillingAddressValue,
			String ShippingAddressValue, String PageName, String ShipAddSameAsBillAdd,
			String ShipAddSameAsBillAddSub) throws InterruptedException
	{
	  		try{
	  	
		// Verify Both Shipping and Billing address values --First time 
		String ExpectedBillAdd1 = "Address 1\n38345 W.10 Mile Roada\nCity\nFormington Hills\nState\nTexas\nCountry\nUSA\nZip\n78732";
		String ExpectedShippAdd1 = "Address 1\n38345 W.10 Mile Roada\nCity\nFormington Hills\nState\nTexas\nCountry\nUSA\nZip\n78732";
		String ExpectedShippAdd1b = null;
		if(ShipAddSameAsBillAddSub.equals("UserSelectedShip"))
		{
			 ExpectedShippAdd1b = "Address 1\n38345 W.10 Mile Road\nCity\nFormington Hills\nState\nTexas\nCountry\nUSA\nZip\n78732";
		}
		else
		{
			 ExpectedShippAdd1b = "Address 1\n38345 W.10 Mile Road\nCity\nFormington Hills\nState\nTexas\nCountry\nUSA\nZip\n78732";
		}
		
		
		if(ShipAddSameAsBillAdd.equals("YES") || ShipAddSameAsBillAddSub.equals("UserChkBoxYES"))
		{
			if(BillingAddressValue.equals(ExpectedBillAdd1) && 
					(ShippingAddressValue.equals(ExpectedShippAdd1)))
			{
				//System.out.println("Both Billing, Shipping Address are same and Shipping address do not have Edit | Select Links");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "ShipAddSameAsBillAdd";
    			System.out.println("<----- In "+PageName+" page Billing and Shipping address are different -------->"+ErrorNumber);
    			System.out.println("*********************************************");
    			System.out.println("Actual Billg value is :"+BillingAddressValue);
    			System.out.println("Actual Shipping value is :"+ShippingAddressValue);
    			System.out.println("ExpectedBillAdd1 :"+ExpectedBillAdd1);
    			System.out.println("ExpectedShippAdd1 :"+ExpectedShippAdd1);
    			System.out.println("*************************************************");
    			RW_File.WriteResult("Both BIlling, Shipping address are same", "Both BIlling, Shipping address are Different", PageName, PriceType);
		
			}
		}
		else
		{
			if(BillingAddressValue.equals(ExpectedBillAdd1) && 
					(ShippingAddressValue.equals(ExpectedShippAdd1b)))
			{
				//System.out.println("Both Billing, Shipping Address are same and Shipping address do not have Edit | Select Links");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "ShipAddSameAsBillAdd";
    			System.out.println("<------- In "+PageName+" page Billing and Shipping address are different ------>"+ErrorNumber);
    			System.out.println("ExpectedBillAdd1 value is :"+ExpectedBillAdd1);
    			System.out.println("ExpectedShippAdd1b value is :"+ExpectedShippAdd1b);
    			System.out.println("BillingAddressValue :"+BillingAddressValue);
    			System.out.println("ShippingAddressValue :"+ShippingAddressValue);
    			RW_File.WriteResult("Both BIlling, Shipping address are same", "Both BIlling, Shipping address are Different", PageName, PriceType);
		
			}
		}
	  		}
	  		catch(Exception e)
	  		{
	  			ErrorNumber = ErrorNumber+1;
				captureScreenshot(); 
	  			e.printStackTrace();
	  		}
	  	
	  	}
	
	public static void ShipAddNotSameAsBillAdd(String BillingAddressValue,
			String ShippingAddressValue, String PageName, String ShipAddSameAsBillAdd,
			String ShipAddSameAsBillAddSub) throws InterruptedException
	{
	 try{
	  	//BillingAddressValue.equals(ExpectedBillAdd1) &&
		// Verify Both Shipping and Billing address values --First time 
		String ExpectedBillAdd1 = "Address 1\n38345 W.10 Mile Roada\nCity\nFormington Hills\nState\nTexas\nCountry\nUSA\nZip\n78732";
		String ExpectedShippAdd1 = "Address 1\n38345 W.10 Mile Roada\nCity\nFormington Hills\nState\nMI\nCountry\nUSA\nZip\n49806";
		if(ShipAddSameAsBillAdd.equals("NO") || ShipAddSameAsBillAddSub.equals("No"))
		{
			if(ShippingAddressValue.equals(ExpectedShippAdd1))
			{
				System.out.println("Both Billing, Shipping Address are same and shipiing address are same ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "ShipAddSameAsBillAdd";
    			System.out.println("<----- In "+PageName+" page Billing and Shipping address are different -------->"+ErrorNumber);
    			System.out.println("*********************************************");
    			System.out.println("Actual Billg value is :"+BillingAddressValue);
    			System.out.println("Actual Shipping value is :"+ShippingAddressValue);
    			System.out.println("ExpectedBillAdd1 :"+ExpectedBillAdd1);
    			System.out.println("ExpectedShippAdd1 :"+ExpectedShippAdd1);
    			System.out.println("*************************************************");
    			RW_File.WriteResult("Both BIlling, Shipping address are same", "Both BIlling, Shipping address are Different", PageName, PriceType);
		
			}
		   }
	  		}
	  		catch(Exception e)
	  		{
	  			ErrorNumber = ErrorNumber+1;
				captureScreenshot(); 
	  			e.printStackTrace();
	  		}
	  	
	  	}
	public static void ShipAddSameAsBillAddViewOrders(String BillingAddressValue,
			String ShippingAddressValue, String PageName, String ShipAddSameAsBillAdd,
			String ShipAddSameAsBillAddSub) throws InterruptedException
	{
	try{
	  	
	  			//System.out.println("View orders page");
		// Verify Both Shipping and Billing address values --First time 
		String ExpectedBillAdd1 = "Billing Information\nAddress 1\n38345 W.10 Mile Roada\nCity\nFormington Hills\nState\nTexas\nCountry\nUSA\nZip\n78732\n";
		String ExpectedShippAdd1 = "\nAddress 1\n38345 W.10 Mile Roada\nAddress 2\nsuite 115b\nCity\nFormington Hills\nState\nTexas\nCountry\nUSA\nZip\n78732\n";
		String ExpectedShippAdd1b = null;
		if(ShipAddSameAsBillAddSub.equals("UserSelectedShip"))
		{
			ExpectedShippAdd1b = "\nAddress 1\n38345 W.10 Mile Road\nAddress 2\nsuite 115\nCity\nFormington Hills\nState\nTexas\nCountry\nUSA\nZip\n78732\n";
		}
		else
		{
			ExpectedShippAdd1b = "\nAddress 1\n38345 W.10 Mile Road\nAddress 2\nsuite 115\nCity\nFormington Hills\nState\nTexas\nCountry\nUSA\nZip\n78732\n";
		}
		
		if(ShipAddSameAsBillAdd.equals("YES") || ShipAddSameAsBillAddSub.equals("UserChkBoxYES"))
		{
			if(BillingAddressValue.equals(ExpectedBillAdd1) && 
					(ShippingAddressValue.equals(ExpectedShippAdd1)))
			{
				//System.out.println("Both Billing and Shipping Address are same");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "ShipAddSameAsBillAdd";
    			System.out.println("<----- In "+PageName+" page Billig and Shipping address are different  ------>"+ErrorNumber);
    			System.out.println("Actual Billg value is :"+BillingAddressValue);
    			System.out.println("Actual Shipping value is :"+ShippingAddressValue);
    			System.out.println("ExpectedBillAdd1 :"+ExpectedBillAdd1);
    			System.out.println("ExpectedShippAdd1 :"+ExpectedShippAdd1);
    			RW_File.WriteResult("Both BIlling and Shipping address are same", "Both BIlling and Shipping address are Different", PageName, PriceType);
			}
		}
		else
		{
			if(BillingAddressValue.equals(ExpectedBillAdd1) && 
					(ShippingAddressValue.equals(ExpectedShippAdd1b)))
			{
				//System.out.println("Both Billing and Shipping Address are same");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "ShipAddSameAsBillAdd";
    			System.out.println("<------- In "+PageName+" page Billig and Shipping address are different ------>"+ErrorNumber);
    			System.out.println("Expected Billg view value is  :"+ExpectedBillAdd1);
    			System.out.println("Expected Shipping view value is :"+ExpectedShippAdd1b);
    			System.out.println("Actual Billg value is :"+BillingAddressValue);
    			System.out.println("Actual Shipping value is :"+ShippingAddressValue);
    			RW_File.WriteResult("Both BIlling, Shipping address are same", "Both BIlling, Shipping address are Different ", PageName, PriceType);
			}
		}
	  		}
	  		catch(Exception e)
	  		{
	  			ErrorNumber = ErrorNumber+1;
				captureScreenshot();
	  			e.printStackTrace();
	  		}
	  	}
	public static void ShipAddNotSameAsBillAddViewOrders(String BillingAddressValue,
			String ShippingAddressValue, String PageName, String ShipAddSameAsBillAdd,
			String ShipAddSameAsBillAddSub) throws InterruptedException
	{
	try{
	  	//BillingAddressValue.equals(ExpectedBillAdd1) && 
	  	//System.out.println("View orders page");
		// Verify Both Shipping and Billing address values --First time 
		String ExpectedBillAdd1 = "Billing Information2\nAddress 1\n38345 W.10 Mile Roada\nCity\nFormington Hills\nState\nTexas\nCountry\nUSA\nZip\n78732";
		String ExpectedShippAdd1 = "\nAddress 1\n38345 W.10 Mile Roada\nAddress 2\nsuite 115b\nCity\nFormington Hills\nState\nMI\nCountry\nUSA\nZip\n49806\n";
		String ExpectedShippAdd1b = null;                      
		if(ShipAddSameAsBillAdd.equals("NO") || ShipAddSameAsBillAddSub.equals("NO"))
		{
			if(ShippingAddressValue.equals(ExpectedShippAdd1))
			{
				//System.out.println("Both Billing and Shipping Address are as Expecded");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "ShipAddSameAsBillAdd";
    			System.out.println("<----- In "+PageName+" page Billig and Shipping address are not as Expecded  ------>"+ErrorNumber);
    			System.out.println("Actual Billg value is :"+BillingAddressValue);
    			System.out.println("Actual Shipping value is :"+ShippingAddressValue);
    			System.out.println("ExpectedBillAdd1 :"+ExpectedBillAdd1);
    			System.out.println("ExpectedShippAdd1 :"+ExpectedShippAdd1);
    			RW_File.WriteResult("Both BIlling and Shipping address are same", "Both BIlling and Shipping address are Different", PageName, PriceType);
			}
		}
		
	  		}
	  		catch(Exception e)
	  		{
	  			ErrorNumber = ErrorNumber+1;
				captureScreenshot();
	  			e.printStackTrace();
	  		}
	  	}
		
	public static void ShipAddSameAsBillAddPopUp(String BillingAddressValue,
			String ShippingAddressValue, String PageName, String ShipAddSameAsBillAdd,
			String ShipAddSameAsBillAddSub) throws InterruptedException
	{
	  		try{
	  	
	  			//System.out.println("Order summary print pop up:");
		// Verify Both Shipping and Billing address values --First time 
		String ExpectedBillAdd1 = "Billing Information\nAddress 1\n38345 W.10 Mile Roada\nAddress 2\nsuite 115b\nCity\nFormington Hills\nState\nTexas\nCountry\nUSA\nZip\n78732";
		String ExpectedShippAdd1 = "Shipping Information\nAddress 1\n38345 W.10 Mile Roada\nAddress 2\nsuite 115b\nCity\nFormington Hills\nState\nTexas\nCountry\nUSA\nZip\n78732";
		String ExpectedShippAdd1b = null;
		if(ShipAddSameAsBillAddSub.equals("UserSelectedShip"))
		{
			ExpectedShippAdd1b = "Shipping Information\nAddress 1\n38345 W.10 Mile Road\nAddress 2\nsuite 115\nCity\nFormington Hills\nState\nTexas\nCountry\nUSA\nZip\n78732";
		}
		else
		{
			ExpectedShippAdd1b = "Shipping Information\nAddress 1\n38345 W.10 Mile Road\nAddress 2\nsuite 115\nCity\nFormington Hills\nState\nTexas\nCountry\nUSA\nZip\n78732";
		}
		
		
		if(ShipAddSameAsBillAdd.equals("YES")|| ShipAddSameAsBillAddSub.equals("UserChkBoxYES"))
		{
			if(BillingAddressValue.equals(ExpectedBillAdd1) && 
					(ShippingAddressValue.equals(ExpectedShippAdd1)))
			{
				//System.out.println("Both Billing, Shipping Address are same and Shipping address do not have Edit | Select Links");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "ShipAddSameAsBillAdd";
    			System.out.println("<----- In "+PageName+" page Billig and Shipping address are different or Ship address have Eidt | Select links ------>"+ErrorNumber);
    			System.out.println("Actual Billg value is :"+BillingAddressValue);
    			System.out.println("Actual Shipping value is :"+ShippingAddressValue);
    			System.out.println("ExpectedBillAdd1 :"+ExpectedBillAdd1);
    			System.out.println("ExpectedShippAdd1 :"+ExpectedShippAdd1);
    			RW_File.WriteResult("Both BIlling, Shipping address are same and shipping address do not have Edit | Select LInks", "Both BIlling, Shipping address are Different and/or shipping address have Edit | Select LInks", PageName, PriceType);
			}
		}
		else
		{
			if(BillingAddressValue.equals(ExpectedBillAdd1) && 
					(ShippingAddressValue.equals(ExpectedShippAdd1b)))
			{
				//System.out.println("Both Billing, Shipping Address are same and Shipping address have Edit | Select Links");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "ShipAddSameAsBillAdd";
    			System.out.println("<------- In "+PageName+" page Billig and Shipping address are different or Ship address do not have Eidt | Select links ------>"+ErrorNumber);
    			System.out.println("Expected Billg value is :"+ExpectedBillAdd1);
    			System.out.println("Expected Shipping value is :"+ExpectedShippAdd1b);
    			System.out.println("BillingAddressValue :"+BillingAddressValue);
    			System.out.println("ShippingAddressValue :"+ShippingAddressValue);
    			RW_File.WriteResult("Both BIlling, Shipping address are same and shipping address do not have Edit | Select LInks", "Both BIlling, Shipping address are Different and/or shipping address have Edit | Select LInks", PageName, PriceType);
			}
		}
	  		}
	  		catch(Exception e)
	  		{
	  			ErrorNumber = ErrorNumber+1;
				captureScreenshot();
	  			e.printStackTrace();
	  		}
	  	}
	public static void ShipAddNotSameAsBillAddPopUp(String BillingAddressValue,
			String ShippingAddressValue, String PageName, String ShipAddSameAsBillAdd,
			String ShipAddSameAsBillAddSub) throws InterruptedException
	{
	  		try{
	  	//BillingAddressValue.equals(ExpectedBillAdd1) && 
	  			//System.out.println("Order summary print pop up:");
		// Verify Both Shipping and Billing address values --First time 
		String ExpectedBillAdd1 = "Billing Information\nAddress 1\n38345 W.10 Mile Roada\nAddress 2\nsuite 115b\nCity\nFormington Hills\nState\nTexas\nCountry\nUSA\nZip\n78732";
		String ExpectedShippAdd1 = "Shipping Information\nAddress 1\n38345 W.10 Mile Road\nAddress 2\nsuite 115b\nCity\nFormington Hills\nState\nMichigan\nCountry\nUSA\nZip\n49806";
	
		if(ShipAddSameAsBillAdd.equals("NO")|| ShipAddSameAsBillAddSub.equals("NO"))
		{
			if(ShippingAddressValue.equals(ExpectedShippAdd1))
			{
				//System.out.println("Both Billing, Shipping Address are as not expected ");
				
			}
			
			else
			{

				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "ShipAddSameAsBillAdd";
    			System.out.println("<----- In "+PageName+" page Billig and Shipping address are not expected  ------>"+ErrorNumber);
    			System.out.println("Actual Billg value is :"+BillingAddressValue);
    			System.out.println("Actual Shipping value is :"+ShippingAddressValue);
    			System.out.println("ExpectedBillAdd1 :"+ExpectedBillAdd1);
    			System.out.println("ExpectedShippAdd1 :"+ExpectedShippAdd1);
    			RW_File.WriteResult("Both BIlling, Shipping address are same and shipping address do not have Edit | Select LInks", "Both BIlling, Shipping address are Different and/or shipping address have Edit | Select LInks", PageName, PriceType);
			
			}
		}
	  		}	
	  		catch(Exception e)
	  		{
	  			ErrorNumber = ErrorNumber+1;
				captureScreenshot();
	  			e.printStackTrace();
	  		}
	  	}
	public static String PrintPopUpPriceVerification(String Quantity, String ItemPerPrice, String Discount, String Addons,
			String Postage, String TotalPrice, String Total, String PromotionCode, 
			String PromotionCoupon, String ShippingPrice, String Tax, String PriceAfterCalculatingTax, 
			String AddonPricePerPiece, String DiscountPercentage, String PromotionDiscountPercentage,
			String PromotionDiscountAfterSubtractingFromSubTotal, String DiscountCalculationFromSubTotal,
			String DownloadPrice, String OrderType, String TestCase, String TestStep, String Parameters,
			String OrderBase, String OrderBaseShipping, String Payment1Price, String Payment2Price,
			String Payment3Price, String Payment4Price, String Payment5Price, String PaymentType, 
			String CalculateTaxCondition, String AppPageName1,
			String EnablePromotionsORDiscounts,String Weighttype,String Subtotal,String DiscountcalculationfromSubTotal,
			String OrderAmountValue,String userordershippingorhandlingfee,String Priceafterapplyingfulfillmentshippingmarkupfee,
			String IsShippingTaxable, String PriceAfterApplyingCoupon, String EnableZeroAmountOrder,
			String ShipAddSameAsBillAdd, String ShipAddSameAsBillAddSub, int paymentLength,String IfShippingaddressIseditble,
			String LandingPageOption,String LandingPageProduct,String LandingPageProductPP,String LandingPageDiscount,
			String LandingPageDiscountValue,String SubtotalAfterPurlPrice,String LDiscuntCalulationFromSubtotal,String DecimalValue,String SubTotal,String Orderelements,
			String OrderelementsAddOns,String OrderelementsBillingAddress,String OrderelementsInventoryLocation,String OrderelementsInventoryNumber,
			String OrderelementsPaymentDetail,String OrderelementsShippingAddress,String OrderelementsShippingMethod,String OrderelementsSpecialInstructions,String OrderelementsCheckout,String OrderelementsJobTicket,String OrderelementsPrintPopup,String ProdutType,String IsTaxExempt) throws InterruptedException {
		String ActualShippingPriceForReturn = null; 
		try{
			
			//WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
			tec.AC40.Common.Wait.wait5Second();
			String PageName = AppPageName1;
			 for (String winHandle : d.getWindowHandles()) {
			        d.switchTo().window(winHandle); 
			    }
			    tec.AC40.Common.Wait.wait2Second();
			/*Set<String> windowId = d.getWindowHandles();
		    Iterator<String> itererator = windowId.iterator(); 
		    tec.AC40.Common.Wait.wait2Second();
		    
		    String mainWinID = itererator.next();
		    String  newAdwinID = itererator.next();
		    d.switchTo().window(newAdwinID);*/
		   // waitfl.until(ExpectedConditions.elementToBeClickable(Property.PrintTotal1));
		    tec.AC40.Common.Wait.wait2Second();
		    //String pagetitle = d.getTitle();
		    //System.out.println("Connected to pring popup3"+pagetitle);
		    String PrintTotalPrice = null;
		    String PrintTotalPrice12 = null;
			String PrintTotalPrice13 = null;
			String PrintTotalPrice14 = null;
			String PrintTotalPrice15 = null;
			
			String ExpectedPrintTotal1 = null;
			String ExpectedPrintTotal12 = null;
			String ExpectedPrintTotal13 = null;
			String ExpectedPrintTotal14 = null;
			String ExpectedPrintTotal15 = null;
			 if(OrderelementsCheckout.equalsIgnoreCase("NO")||OrderelementsBillingAddress.equalsIgnoreCase("NO")){}else{
				System.out.println("BillingAddress & BillingAddress");
				Thread.sleep(3500);
				 d.findElement(By.id("divAddress")).getText();
					String BillingAddValue = d.findElement(By.id("divAddress")).getText();
              //System.out.println(BillingAddValue);
			BillingAddressVerificationOrderSummary(EnableZeroAmountOrder, BillingAddValue, PageName);
			String[] BillingAddExpectedStatus = EnableZeroAmountOrder.split("_");
			
			try
			{
				String Popup2ndBillingAdd = null;
				if(OrderBase.equals("Item"))
				{
					Popup2ndBillingAdd = d.findElement(By.xpath("//tr[14]/td/table/tbody")).getText();
				}
				else
				{
					if(OrderBase.equalsIgnoreCase("Split Ship") && (OrderType.equalsIgnoreCase("Ship items to multiple adresses")||OrderType.equals("ShipToMyAddress"))){
						Popup2ndBillingAdd = d.findElement(By.xpath("//tr[16]/td")).getText();
					}else{
						if(ProdutType.equals("ShipToMyAddress")){
							System.out.println("popup address===================");
							Popup2ndBillingAdd = d.findElement(By.xpath("//tr[16]/td")).getText();

						}else{
							Popup2ndBillingAdd = d.findElement(By.xpath("//tr[14]/td")).getText();

						}

					}
					//tr[16]/td/table/tbody
				}
			BillingAddressVerificationOrderSummary(EnableZeroAmountOrder, Popup2ndBillingAdd, PageName);
			}
			catch(Exception e)
			{ 
				if(BillingAddExpectedStatus[1].equalsIgnoreCase("NO"))
				{
					//!System.out.println("Success Billing address not displayed to user");
				}
				else
				{
					System.out.println("no address in the page");
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "Billing Address";
					System.out.println("<-------- "+ PageName +" Failed Billing address not Displays to user ---------->"+ErrorNumber);
					System.out.println("Actual Billing Address status : "+"Billing Address Not displayed");
					System.out.println("Expected Billing Address status : "+"Billing Address displays to user");
					RW_File.WriteResult("Billing Address displays to user", "Billing Address Not displayed",  PageName, PriceType);
				}
				
			}
			 }
			String ExpectedShippingHandilingPrice = Config.Currency+Priceafterapplyingfulfillmentshippingmarkupfee;
		    if((Total.equals("0.00") || Total.equals("0.000") || Total.equals("0.0000")))
		    {
		    	
		    }
		    else
		    {
		    	Thread.sleep(5000);
			PrintTotalPrice = d.findElement(Property.PrintTotal1).getText();
			
			
			if(PaymentType.contains(","))
			{
				if(paymentLength >= 2)
				{
					PrintTotalPrice12 = d.findElement(Property.PrintTotal12).getText();
				}
				if(paymentLength >= 3)
				{
					PrintTotalPrice13 = d.findElement(Property.PrintTotal13).getText();
				}
				if(paymentLength >= 4)
				{
					PrintTotalPrice14 = d.findElement(Property.PrintTotal14).getText();
				}
				if(paymentLength == 5)
				{
					PrintTotalPrice15 = d.findElement(Property.PrintTotal15).getText();
				}
			
			}
		    }
			if(PaymentType.contains(","))
			{
				ExpectedPrintTotal1 = Config.Currency+Total;
			    ExpectedPrintTotal12 = Config.Currency+Payment2Price;
			    ExpectedPrintTotal13 = Config.Currency+Payment3Price;
			    ExpectedPrintTotal14 = Config.Currency+Payment4Price;
			    ExpectedPrintTotal15 = Config.Currency+Payment5Price;
			}
			else
			{
				ExpectedPrintTotal1 = Config.Currency+Total;
			}
		    
			int ExpectedPrintQuantity = Double.valueOf(Quantity).intValue();
			
			String ExpectedPrintItemPrice = null;
			String ExpectedPrintLandingItemPrice = null;

			if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload"))
			{
				ExpectedPrintItemPrice =Config.Currency+DownloadPrice;
				ExpectedPrintLandingItemPrice=Config.Currency+LandingPageProductPP;
			}
			else if(OrderType.equals("DynShipTOMultipleLocations") || OrderType.equals("StaticShipTOMultipleLocations")
					|| OrderType.equals("StaticInventoryShipTOMultipleLocations"))
			{
				ExpectedPrintItemPrice ="-";
			}
			else
			{
				ExpectedPrintItemPrice =Config.Currency+ItemPerPrice;
				ExpectedPrintLandingItemPrice=Config.Currency+LandingPageProductPP;

			}
			String ExpectedPrintDiscount = null;
			String ExpectedPrintLandingDiscount = null;
			
			
		
				if(DiscountPercentage.equals("N") && LandingPageDiscount.equals("N"))
			 {
				ExpectedPrintDiscount = Config.Currency+Discount;
				ExpectedPrintLandingDiscount= Config.Currency+LandingPageDiscountValue;
			 }
				else if(DiscountPercentage.equals("N") && LandingPageDiscount.equals("Y"))
				{
					ExpectedPrintDiscount = Config.Currency+Discount;
					ExpectedPrintLandingDiscount= LandingPageDiscountValue+Config.PercentageSymbol;
				}
				else if(DiscountPercentage.equals("Y") && LandingPageDiscount.equals("N"))
				{
					ExpectedPrintDiscount = Discount+Config.PercentageSymbol;
					ExpectedPrintLandingDiscount= Config.Currency+LandingPageDiscountValue;
				}
			
				else if(Discount.equals("0") || Discount.equals("0.0") || Discount.equals("0.00") ||
						Discount.equals("0.000") || Discount.equals("0.0000")||LandingPageDiscount.equals("0") ||
						LandingPageDiscount.equals("0.0") || LandingPageDiscount.equals("0.00") ||
						LandingPageDiscount.equals("0.000") || LandingPageDiscount.equals("0.0000"))
		    {
				ExpectedPrintDiscount = Config.Currency+Discount;
				ExpectedPrintLandingDiscount= Config.Currency+LandingPageDiscountValue;

		    }
			else
			{
				ExpectedPrintDiscount = Discount+Config.PercentageSymbol;
				ExpectedPrintLandingDiscount= LandingPageDiscountValue+Config.PercentageSymbol;

			}
			String ExpectedPrintAmount  = null;
			String ExpectedLandingPrintAmount  = null;
			if(LandingPageOption.equalsIgnoreCase("yes")){
				if (DiscountPercentage.equalsIgnoreCase("Y"))
				{
					if((Addons.equals("0") || Addons.equals("0.0") || Addons.equals("0.00") ||
							Addons.equals("0.000") || Addons.equals("0.0000")) &&( Postage.equals("0") || Postage.equals("0.0") || 
							Postage.equals("0.00") ||Postage.equals("0.000") || Postage.equals("0.0000")) &&( ShippingPrice.equals("0") ||
							ShippingPrice.equals("0.0") || ShippingPrice.equals("0.00") ||ShippingPrice.equals("0.000") || ShippingPrice.equals("0.0000")))
							{
					double discount1 = Double.valueOf(DiscountCalculationFromSubTotal).doubleValue();
					double discount2 = Double.valueOf(SubTotal).doubleValue();
					double discount3 = discount2-discount1;
					String discount4 = ""+discount3;
					String discount5 = Decimalsetting(discount4, DecimalValue);	
					ExpectedPrintAmount  = Config.Currency+discount5;
					ExpectedLandingPrintAmount = Config.Currency+SubtotalAfterPurlPrice;
					}
					else{
						double discount1 = Double.valueOf(DiscountCalculationFromSubTotal).doubleValue();
						double discount2 = Double.valueOf(SubTotal).doubleValue();
						double Addons1 = Double.valueOf(Addons).doubleValue();
						double Postage1 = Double.valueOf(Postage).doubleValue();
						double Shipping1 = Double.valueOf(ShippingPrice).doubleValue();
						double discount3 = discount2-discount1+Postage1+Addons1+Shipping1;
						String discount4 = ""+discount3;
						String discount5 = Decimalsetting(discount4, DecimalValue);	
						ExpectedPrintAmount  = Config.Currency+discount5;
						ExpectedLandingPrintAmount = Config.Currency+SubtotalAfterPurlPrice;
					}
				}
				else{
					if((Addons.equals("0") || Addons.equals("0.0") || Addons.equals("0.00") ||Addons.equals("0.000") ||
							   Addons.equals("0.0000")) && (Postage.equals("0") || Postage.equals("0.0") || Postage.equals("0.00") ||
							   Postage.equals("0.000") || Postage.equals("0.0000")) && (ShippingPrice.equalsIgnoreCase("0") ||
							   ShippingPrice.equals("0.0") || ShippingPrice.equals("0.00") ||ShippingPrice.equals("0.000") ||
							   ShippingPrice.equals("0.0000")))
					{
						
						ExpectedPrintAmount  = Config.Currency+DiscountCalculationFromSubTotal;
						ExpectedLandingPrintAmount = Config.Currency+SubtotalAfterPurlPrice; 
				    }
					else{
						double discount1 = Double.valueOf(DiscountCalculationFromSubTotal).doubleValue();
						double discount2 = Double.valueOf(Addons).doubleValue();
						double discount3 = Double.valueOf(Postage).doubleValue();
						double Shipping1 = Double.valueOf(ShippingPrice).doubleValue();
						double discount4 = discount1+discount2+discount3+Shipping1;
						String discount5 = ""+discount4;
						String discount6 = Decimalsetting(discount5, DecimalValue);
						
						ExpectedPrintAmount = Config.Currency+discount6;
						ExpectedLandingPrintAmount = Config.Currency+SubtotalAfterPurlPrice ;
					}
			
				}
			}else{
				ExpectedPrintAmount  = Config.Currency+TotalPrice;
			}
			String ExpectedPrintSubTotal =Config.Currency+TotalPrice;
			String ExpectedPrintPromotionDiscount =null;
			
			if(PromotionDiscountPercentage.equals("N"))
			{
				ExpectedPrintPromotionDiscount ="-"+Config.Currency+PromotionCoupon;
			}
			else
			{
				ExpectedPrintPromotionDiscount ="-"+Config.Currency+PromotionDiscountAfterSubtractingFromSubTotal;
			}
			String ExpectedPrintTaxValue =Config.Currency+PriceAfterCalculatingTax;
			
			String ExpectedPrintShippingValue = null;
			if(OrderBase.equals("Item"))
			{
				ExpectedPrintShippingValue =Config.Currency+ShippingPrice;
			}
			else
			{
				ExpectedPrintShippingValue =Config.Currency+OrderBaseShipping;
			}
			//String ExpectedPrintTotal = Config.Currency+Total;
			String ExpectedPrintAppliedDiscontPrice =null;
			if(LandingPageOption.equalsIgnoreCase("YES")){
				if(DiscountPercentage.equals("N"))
				{
				double  Discount1= Double.valueOf(Discount).doubleValue();
				double  LandingPageDiscountValue1= Double.valueOf(LDiscuntCalulationFromSubtotal).doubleValue();
				double   Discount2=Discount1+LandingPageDiscountValue1;
				String discount3 = ""+Discount2;
				String discount4 = Decimalsetting(discount3, DecimalValue);
				
				System.out.println("Discount2"+discount4);
				ExpectedPrintAppliedDiscontPrice ="-"+Config.Currency+discount4;
					
				}
				else
				{
					double  Discount11= Double.valueOf(DiscountCalculationFromSubTotal).doubleValue();
					double  LandingPageDiscountValue11= Double.valueOf(LDiscuntCalulationFromSubtotal).doubleValue();
					double   Discount12=Discount11+LandingPageDiscountValue11;
					String discount13 = ""+Discount12;
					String discount14 = Decimalsetting(discount13, DecimalValue);
					ExpectedPrintAppliedDiscontPrice ="-"+Config.Currency+discount14;
				}	
			}
			else{
			if(DiscountPercentage.equals("N"))
			{
				ExpectedPrintAppliedDiscontPrice ="-"+Config.Currency+Discount;
			}
			else
			{
				ExpectedPrintAppliedDiscontPrice ="-"+Config.Currency+DiscountCalculationFromSubTotal;
			}
			}
			String ExpectedPrintAppliedAddonPrice =Config.Currency+Addons;
			String ExpectedPrintAppliedTotalPostagePrice =Config.Currency+Postage;
			//String ActualPrintTotal1 = d.findElement(Property.PrintTotal1).getText();
			
			String[] TaxType = CalculateTaxCondition.split("_");
			
			
			if(TaxType[0].equals("Vertex"))
			{
				if(TaxType[1].equals("ON"))
				{
					String ExpectedPrintTotal2 =VertexTotalPrintPopup(ExpectedPrintTotal1);
					if(ExpectedPrintTotal2.equals(PrintTotalPrice))
					{
						//!System.out.println("Both Total1 prices are same ");
					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "PrintTotal";
						System.out.println("<----- Both Total1 prices are different --------->"+ErrorNumber);
						System.out.println("ExpectedPrintTotal1 : "+ExpectedPrintTotal2);
						System.out.println("ActualPrintTotal1 : "+PrintTotalPrice);
						RW_File.WriteResult(ExpectedPrintTotal2, PrintTotalPrice,  PageName, PriceType);
					}
				}
				else
				{
					String ExpectedPrintTotal2 =ExpectedPrintTotal1;
					if(ExpectedPrintTotal2.equals(PrintTotalPrice))
					{
						//!System.out.println("Both Total1 prices are same ");
					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "PrintTotal";
						System.out.println("<-------- Both Total1 prices are different --------->"+ErrorNumber);
						System.out.println("ExpectedPrintTotal1 : "+ExpectedPrintTotal2);
						System.out.println("ActualPrintTotal1 : "+PrintTotalPrice);
						RW_File.WriteResult(ExpectedPrintTotal2, PrintTotalPrice,  PageName, PriceType);
					}
				}
			}
			else
			{
				String updatingshippingprinttotal = null;
				String OPmarkupgrandtotal = null;
				if(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
			    		  userordershippingorhandlingfee.equals("0.0000"))
				{
				 if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
				 {
				  String OP3rdpartytax = d.findElement(Property.PrintTaxvalue).getText();
				  String OP3rdpartytax1 = OP3rdpartytax.substring(1,OP3rdpartytax.length());
				  String OP3rdpartyshippingprice =  d.findElement(Property.printAppliedTotalShippingPrice).getText();
				  String OP3rdpartyshippingprice1 = OP3rdpartyshippingprice.substring(1,OP3rdpartyshippingprice.length());
					if(IsShippingTaxable.equals("Yes"))
					{
						String UpdatedTax  = UpdatedtaxOCWithOutMarkup(Subtotal,PromotionCoupon,OrderAmountValue,Addons,
								DiscountcalculationfromSubTotal,OP3rdpartyshippingprice1,Tax); 
						if(OP3rdpartytax.equals(UpdatedTax))
						 {
							System.out.println("both upadted tax and displayed correctly.");
						 }
						else
						{
							ErrorNumber = ErrorNumber+1;
							System.out.println("tax is not correct");
							System.out.println("OP3rdpartytax :"+OP3rdpartytax);
							System.out.println("UpdatedTax :"+UpdatedTax);
						}
					}
				  updatingshippingprinttotal  = OCgrandtotal(Subtotal,PriceAfterApplyingCoupon,OP3rdpartytax1,OP3rdpartyshippingprice1,
								Addons,DiscountcalculationfromSubTotal,OrderAmountValue, Total, IsShippingTaxable, Tax);
						
				 }		
				}
				else
				{
					if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
					{
					String OPmarkupfee = null;
					if(Config.LayoutType.equals("Layout1") || Config.LayoutType.equals("Layout2"))
					{
						OPmarkupfee = d.findElement(Property.OPhandilingfeeL1).getText();
					}
					else
					{
						OPmarkupfee = d.findElement(Property.OPhandilingfee).getText();
					}
					System.out.println("OPmarkupfee123 :"+OPmarkupfee);
					String OPmarkupfee1 = OPmarkupfee.substring(1,OPmarkupfee.length());
					String OP3rdpartytax = d.findElement(Property.PrintTaxvalue).getText();
					String OP3rdpartytax1 = OP3rdpartytax.substring(1,OP3rdpartytax.length());
					String OP3rdpartyshippingprice = d.findElement(Property.printAppliedTotalShippingPrice).getText();
					String OP3rdpartyshippingprice1 = OP3rdpartyshippingprice.substring(1,OP3rdpartyshippingprice.length());
					if(IsShippingTaxable.equals("Yes"))
					{
						String UpdatedTax  = UpdatedtaxOCMarkup(Subtotal,PromotionCoupon,OrderAmountValue,Addons,
								DiscountcalculationfromSubTotal,OP3rdpartyshippingprice1,Tax,userordershippingorhandlingfee); 
						if(OP3rdpartytax.equals(UpdatedTax))
						 {
							System.out.println("both upadted tax and displayed correctly.");
						 }
						else
						{
							ErrorNumber = ErrorNumber+1;
							System.out.println("tax is not correct");
							System.out.println("OP3rdpartytax :"+OP3rdpartytax);
							System.out.println("UpdatedTax :"+UpdatedTax);
						}
					}
					OPmarkupgrandtotal = Ocupdatedmarkupgrandtotal(Subtotal,PromotionCoupon,OPmarkupfee1,OP3rdpartytax1,OrderAmountValue,
							Addons,DiscountcalculationfromSubTotal,OP3rdpartyshippingprice1);
				     }
				}
				if((Total.equals("0.00") || Total.equals("0.000") || Total.equals("0.0000")))
				{
					// Execute this block whenever we have zero total value.
				}
				else if(Weighttype.equals("--Select--")&&(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
			    		  userordershippingorhandlingfee.equals("0.0000")))
				{
				     if(ExpectedPrintTotal1.equals(PrintTotalPrice))
				     {
				    	 //!System.out.println("Both Total1 prices are same in PRIINT page111");
				     }
				     else
						{
							ErrorNumber = ErrorNumber+1;
							captureScreenshot();
							String PriceType = "PrintTotal";
							System.out.println("<--------- Both Total1 prices are different --------->"+ErrorNumber);
							System.out.println("ExpectedPrintTotal1 : "+ExpectedPrintTotal1);
							System.out.println("ActualPrintTotal1 : "+PrintTotalPrice);
							RW_File.WriteResult(ExpectedPrintTotal1, PrintTotalPrice,  PageName, PriceType);
						}
				}
				else if(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
			    		  userordershippingorhandlingfee.equals("0.0000"))
				{
					if(OrderBase.equals("Item")&&(Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
					{
						if (updatingshippingprinttotal.equals(PrintTotalPrice))
						{
							//!System.out.println("Both Total1 prices are same in PRIINT page");
						}
						else
						{
							ErrorNumber = ErrorNumber+1;
							captureScreenshot();
							String PriceType = "PrintTotal";
							System.out.println("<---------- Both Total1 prices are different --------->"+ErrorNumber);
							System.out.println("ExpectedPrintTotal1 : "+updatingshippingprinttotal);
							System.out.println("ActualPrintTotal1 : "+PrintTotalPrice);
							RW_File.WriteResult(updatingshippingprinttotal, PrintTotalPrice,  PageName, PriceType);
						}
					}
				}
				else if(!(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
			    		  userordershippingorhandlingfee.equals("0.0000")))
				{
					if(OrderBase.equals("Item")&&(Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
					{
						if(OPmarkupgrandtotal.equals(PrintTotalPrice))
						{
							//!System.out.println("Both Total1 prices are same in PRIINT page222");
						}
						else
						{
							ErrorNumber = ErrorNumber+1;
							captureScreenshot();
							String PriceType = "PrintTotal";
							System.out.println("<------- Both Total1 prices are different --------->"+ErrorNumber);
							System.out.println("ExpectedPrintTotal1 : "+OPmarkupgrandtotal);
							System.out.println("ActualPrintTotal1 : "+PrintTotalPrice);
							RW_File.WriteResult(OPmarkupgrandtotal, PrintTotalPrice,  PageName, PriceType);
						}
					}
				  else
				  {
					  if(ExpectedPrintTotal1.equals(PrintTotalPrice))
					     {
						  //!System.out.println("Both Total1 prices are same in PRIINT page111");
					     }
					  else
						{
								ErrorNumber = ErrorNumber+1;
								captureScreenshot();
								String PriceType = "PrintTotal";
								System.out.println("<-------- Both Total1 prices are different ------->"+ErrorNumber);
								System.out.println("ExpectedPrintTotal1 : "+ExpectedPrintTotal1);
								System.out.println("ActualPrintTotal1 : "+PrintTotalPrice);
								RW_File.WriteResult(ExpectedPrintTotal1, PrintTotalPrice,  PageName, PriceType);
						}
				  }
	     	}
		}
			if(PaymentType.contains(","))
			{
				if(paymentLength >= 2)
				{
				if(ExpectedPrintTotal12.equals(PrintTotalPrice12))
				{
					//!System.out.println("Both Total1 prices are same ");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "PrintTotal";
					System.out.println("<--- Both Total1 prices are different --------->"+ErrorNumber);
					System.out.println("ExpectedPrintTotal2 : "+ExpectedPrintTotal12);
					System.out.println("ActualPrintTotal2 : "+PrintTotalPrice12);
					RW_File.WriteResult(ExpectedPrintTotal12, PrintTotalPrice12,  PageName, PriceType);
				}
				}
				if(paymentLength >= 3)
				{
				if(ExpectedPrintTotal13.equals(PrintTotalPrice13))
				{
					//!System.out.println("Both Total1 prices are same ");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "PrintTotal";
					System.out.println("<-------- Both Total1 prices are different ------>"+ErrorNumber);
					System.out.println("ExpectedPrintTotal3 : "+ExpectedPrintTotal13);
					System.out.println("ActualPrintTotal3 : "+PrintTotalPrice13);
					RW_File.WriteResult(ExpectedPrintTotal13, PrintTotalPrice13,  PageName, PriceType);
				}
				}
				if(paymentLength >= 4)
				{
					if(ExpectedPrintTotal14.equals(PrintTotalPrice14))
					{
						//!System.out.println("Both Total1 prices are same ");
					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "PrintTotal";
						System.out.println("<-------- Both Total1 prices are different ------>"+ErrorNumber);
						System.out.println("ExpectedPrintTotal4 : "+ExpectedPrintTotal14);
						System.out.println("ActualPrintTotal4 : "+PrintTotalPrice14);
						RW_File.WriteResult(ExpectedPrintTotal14, PrintTotalPrice14,  PageName, PriceType);
					}
				}
				if(paymentLength == 5)
				{
					if(ExpectedPrintTotal15.equals(PrintTotalPrice15))
					{
						//!System.out.println("Both Total1 prices are same ");
					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "PrintTotal";
						System.out.println("<-------- Both Total1 prices are different ------>"+ErrorNumber);
						System.out.println("ExpectedPrintTotal5 : "+ExpectedPrintTotal15);
						System.out.println("ActualPrintTotal5 : "+PrintTotalPrice15);
						RW_File.WriteResult(ExpectedPrintTotal15, PrintTotalPrice15,  PageName, PriceType);
					}
				}
			}
			String ActualPrintQuantity = null;
			if(OrderBase.equals("Item"))
			{
				ActualPrintQuantity = d.findElement(Property.PrintQuantity).getText();
			}
			else
			{
				if(OrderBase.equals("Split Ship")&&OrderType.equalsIgnoreCase("Ship items to multiple adresses")){
					ActualPrintQuantity = d.findElement(Property.PrintQuantity).getText();
				}else{
					ActualPrintQuantity = d.findElement(Property.PrintQuantityOB).getText();

				}
			}
			int ActualPrintQuantity1= Double.valueOf(ActualPrintQuantity).intValue();
			if(ExpectedPrintQuantity == ActualPrintQuantity1)
			{
				//!System.out.println("Both are Quantitys are same ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Quantity";
				System.out.println("<-------- Bothe Quantitys are different : --------->"+ErrorNumber);
				System.out.println("ExpectedPrintPOPUPQuantity : "+ExpectedPrintQuantity);
				System.out.println("ActualPrintPOPUPQuantity1 : "+ActualPrintQuantity);
				RW_File.WriteResult(Quantity, ActualPrintQuantity, PageName, PriceType);
				
			}
			String ActualPrintItemPrice = null;
			String ActualPrintLandingItemPrice = null;
			if(LandingPageOption.equalsIgnoreCase("YES")){
				if(OrderBase.equals("Item"))
				{
				    String	PrintItemPrice = d.findElement(Property.PrintItemPrice).getText();
				    String[] PrintItemPrice1 = PrintItemPrice.split("\\s+");
				    ActualPrintItemPrice = PrintItemPrice1[0];
					ActualPrintLandingItemPrice= d.findElement(Property.PrintItemPrice1).getText();
				}
				else
				{
					String	PrintItemPrice = d.findElement(Property.PrintItemPriceOB).getText();
				    String[] PrintItemPrice1 = PrintItemPrice.split("\\s+");
					ActualPrintItemPrice = PrintItemPrice1[0];
					ActualPrintLandingItemPrice= d.findElement(Property.PrintItemPriceOB1).getText();

				}

				if(ExpectedPrintLandingItemPrice.equals(ActualPrintLandingItemPrice))
				{
					//!System.out.println("Both item prices are same ");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "ItemPrice";
					System.out.println("<-------- Bothe Pop Landing  page Item prices are different --------->"+ErrorNumber);
					System.out.println("ExpectedPrintPOPUPSLandingItemPrice : "+ExpectedPrintLandingItemPrice);
					System.out.println("ActualPrintPOPUPSLandingItemPrice : "+ActualPrintLandingItemPrice);
					RW_File.WriteResult(ExpectedPrintLandingItemPrice, ActualPrintLandingItemPrice, PageName, PriceType);
				}
			}else{
				if(OrderBase.equals("Item"))
				{
					
					ActualPrintItemPrice = d.findElement(Property.PrintItemPrice).getText(); 
				}
				else
				{
					if(OrderBase.equals("Split Ship")&&OrderType.equalsIgnoreCase("Ship items to multiple adresses")){
						ActualPrintItemPrice = d.findElement(Property.PrintItemPriceOBS).getText(); 

					}else{
					ActualPrintItemPrice = d.findElement(Property.PrintItemPriceOB).getText(); 
					}
				}
			}
			
			
			if(ExpectedPrintItemPrice.equals(ActualPrintItemPrice))
			{
				//!System.out.println("Both item prices are same ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "ItemPrice";
				System.out.println("<-------- Bothe Item prices are different --------->"+ErrorNumber);
				System.out.println("ExpectedPrintPOPUPSItemPrice : "+ExpectedPrintItemPrice);
				System.out.println("ActualPrintPOPUPSItemPrice : "+ActualPrintItemPrice);
				RW_File.WriteResult(ExpectedPrintItemPrice, ActualPrintItemPrice, PageName, PriceType);
			}
			
			if(EnablePromotionsORDiscounts.equals("ON"))
			{
//				String	PrintItemPrice = d.findElement(Property.PrintItemPrice).getText();
//			    String[] PrintItemPrice1 = PrintItemPrice.split("\\s+");
//			    ActualPrintItemPrice = PrintItemPrice1[0];
				String ActualPrintDiscount = null;
				String ActualPrintLandingDiscount = null;
				if(LandingPageOption.equalsIgnoreCase("YES")){
					if(OrderBase.equals("Item"))
					{
						String	PrintDiscount = d.findElement(Property.PrintDiscount).getText();
					    String[] PrintDiscount1 = PrintDiscount.split("\\s+");
					    ActualPrintDiscount = PrintDiscount1[0];
					//ActualPrintLandingDiscount=d.findElement(Property.PrintDiscount1).getText();
					String	PrintDiscount2 = d.findElement(Property.PrintDiscount1).getText();
				    String[] PrintDiscount3 = PrintDiscount2.split("\\s+");
				    ActualPrintLandingDiscount = PrintDiscount3[1];
					
					}
					else
					{
						String	PrintDiscount = d.findElement(Property.PrintDiscountOB).getText();
					    String[] PrintDiscount1 = PrintDiscount.split("\\s+");
					    ActualPrintDiscount = PrintDiscount1[0];
					    System.out.println("ActualPrintDiscount  "+ActualPrintDiscount );
					   // ActualPrintLandingDiscount=d.findElement(Property.PrintDiscountOB1).getText();
					    String	PrintDiscount2 = d.findElement(Property.PrintDiscountOB1).getText();
					    String[] PrintDiscount3 = PrintDiscount2.split("\\s+");
					    ActualPrintLandingDiscount = PrintDiscount3[1];
					}
					if(ExpectedPrintLandingDiscount.equals(ActualPrintLandingDiscount))
					{
						//!System.out.println("Both Discount prices are same ");
					}
					else
					{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "Discount";
					System.out.println("<------- Both Landing page Discount prices are different --------->"+ErrorNumber);
					System.out.println("ExpectedPrintPOPUPSLandingpageDiscount : "+ExpectedPrintLandingDiscount);
					System.out.println("ActualPrintPOPUPLandingpageDiscount : "+ActualPrintLandingDiscount);
					RW_File.WriteResult(ExpectedPrintLandingDiscount, ActualPrintLandingDiscount, PageName, PriceType);
					}
				}else{
					if(OrderBase.equals("Item"))
					{
					ActualPrintDiscount = d.findElement(Property.PrintDiscount).getText();
					}
					else
					{
						if(OrderBase.equals("Split Ship")&&OrderType.equalsIgnoreCase("Ship items to multiple adresses")){
							ActualPrintDiscount = d.findElement(Property.PrintDiscountOBS).getText();

						}else{
							ActualPrintDiscount = d.findElement(Property.PrintDiscountOB).getText();

						}
					}
				}
				
			
				if(ExpectedPrintDiscount.equals(ActualPrintDiscount))
				{
					//!System.out.println("Both Discount prices are same ");
				}
				else
				{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Discount";
				System.out.println("<------- Both Discount prices are different --------->"+ErrorNumber);
				System.out.println("ExpectedPrintPOPUPSDiscount : "+ExpectedPrintDiscount);
				System.out.println("ActualPrintPOPUPDiscount : "+ActualPrintDiscount);
				RW_File.WriteResult(ExpectedPrintDiscount, ActualPrintDiscount, PageName, PriceType);
				}
			}
			
			if(EnablePromotionsORDiscounts.equals("ON"))
			{    String ActualPrintAmount = null;
				String ActualLandingPrintAmount = null;
				if(LandingPageOption.equalsIgnoreCase("YES")){
					if(OrderBase.equals("Item"))
					{
				
					String	PrintAmount =  d.findElement(Property.PrintAmount).getText();
				    String[] PrintAmount1 = PrintAmount.split("\\s+");
				    ActualPrintAmount = PrintAmount1[0];
				    System.out.println("ActualPrintAmount"+ActualPrintAmount);
				    ActualLandingPrintAmount=d.findElement(Property.PrintAmount1).getText();
				    System.out.println("ActualLandingPrintAmount"+ActualLandingPrintAmount);
					}
					else
					{
					String	PrintAmount =  d.findElement(Property.PrintAmountOB).getText();
				    String[] PrintAmount1 = PrintAmount.split("\\s+");
				    ActualPrintAmount = PrintAmount1[0];
				    ActualLandingPrintAmount=d.findElement(Property.PrintAmountOB1).getText();
					}
					if(ExpectedLandingPrintAmount.equals(ActualLandingPrintAmount))
					{
						//!System.out.println("Both Amounts are same PRINT22222");
					}
					else
					{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "Amount";
					System.out.println("<-------- Both LandingPage    print Amounts are different-------> "+ErrorNumber);
					System.out.println("ExpectedPrintPOPUPLandingPageAmount : "+ExpectedLandingPrintAmount);
					System.out.println("ActualPrintPOPUPLandingPageAmount : "+ActualLandingPrintAmount);
					RW_File.WriteResult(ExpectedLandingPrintAmount, ActualLandingPrintAmount, PageName, PriceType);
					}
					}else{
						if(Orderelements.equalsIgnoreCase("YES")&&OrderelementsAddOns.equalsIgnoreCase("NO")){
						if(OrderBase.equals("Item"))
						{
						ActualPrintAmount = d.findElement(Property.PrintAmounto).getText();
						}
						else
						{
						ActualPrintAmount = d.findElement(Property.PrintAmountOBo).getText();
						}
						}else{

							if(OrderBase.equals("Item"))
							{
							ActualPrintAmount = d.findElement(Property.PrintAmount).getText();
							}
							else
							{
								if(OrderBase.equals("Split Ship")&&OrderType.equalsIgnoreCase("Ship items to multiple adresses")){
									ActualPrintAmount = d.findElement(Property.PrintAmountOBS).getText();

								}else{
									ActualPrintAmount = d.findElement(Property.PrintAmountOB).getText();

								}
							}
						}
					}
				
				String updatingshippingprinttotal1 = null;
				if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
				{
					String OP3rdpartytax = d.findElement(Property.PrintTaxvalue).getText();
					String OP3rdpartytax1 = OP3rdpartytax.substring(1,OP3rdpartytax.length());
					String OP3rdpartyshippingprice = "$0.00";
					if(OrderBase.equals("Item"))
					{
						OP3rdpartyshippingprice =  d.findElement(Property.printAppliedTotalShippingPrice).getText();
					}
					String OP3rdpartyshippingprice1 = OP3rdpartyshippingprice.substring(1,OP3rdpartyshippingprice.length());
					updatingshippingprinttotal1  = viewsummaryshippingtotal(Subtotal,OP3rdpartyshippingprice1,Addons,DiscountcalculationfromSubTotal,OrderAmountValue,DecimalValue,Postage);
					if(updatingshippingprinttotal1.equals(ActualPrintAmount))
					{
						//!System.out.println("Both Amounts are same PRINT22222");
					}
					else
					{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "Amount";
					System.out.println("Amount");
					System.out.println("<-------- Both Amounts are different1--------> "+ErrorNumber);
					System.out.println("ExpectedPrintPOPUPAmount : "+updatingshippingprinttotal1);
					System.out.println("ActualPrintPOPUPAmount : "+ActualPrintAmount);
					RW_File.WriteResult(updatingshippingprinttotal1, ActualPrintAmount, PageName, PriceType);
					}	
				}
				else 
				{
				if(ExpectedPrintAmount.equals(ActualPrintAmount))
				{
					//!System.out.println("Both Amounts are same PRINT22222");
				}
				else
				{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Amount";
				System.out.println("<-------- Both Amounts are different-------> "+ErrorNumber);
				System.out.println("ExpectedPrintPOPUPAmount : "+ExpectedPrintAmount);
				System.out.println("ActualPrintPOPUPAmount : "+ActualPrintAmount);
				RW_File.WriteResult(ExpectedPrintAmount, ActualPrintAmount, PageName, PriceType);
				}
			}
			}
			else
			{
				    String ActualPrintAmount = null;
					String ActualLandingPrintAmount = null;
					if(LandingPageOption.equalsIgnoreCase("YES")){
						if(OrderBase.equals("Item"))
						{
					
						String	PrintAmount =  d.findElement(Property.PrintAmountOFF).getText();
					    String[] PrintAmount1 = PrintAmount.split("\\s+");
					    ActualPrintAmount = PrintAmount1[0];
					    ActualLandingPrintAmount=d.findElement(Property.PrintAmountOFF1).getText();
						}
						else
						{
						String	PrintAmount =  d.findElement(Property.PrintAmountOBOFF).getText();
					    String[] PrintAmount1 = PrintAmount.split("\\s+");
					    ActualPrintAmount = PrintAmount1[0];
					    ActualLandingPrintAmount=d.findElement(Property.PrintAmountOBOFF1).getText();
						}
						if(ExpectedLandingPrintAmount.equals(ActualLandingPrintAmount))
						{
							//!System.out.println("Both Amounts are same PRINT22222");
						}
						else
						{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "Amount";
						System.out.println("<-------- Both LandingPage    print Amounts are different-------> "+ErrorNumber);
						System.out.println("ExpectedPrintPOPUPLandingPageAmount : "+ExpectedLandingPrintAmount);
						System.out.println("ActualPrintPOPUPLandingPageAmount : "+ActualLandingPrintAmount);
						RW_File.WriteResult(ExpectedLandingPrintAmount, ActualLandingPrintAmount, PageName, PriceType);
						}
						}else{
				if(OrderBase.equals("Item"))
				{
					ActualPrintAmount = d.findElement(Property.PrintAmountOFF).getText();
				}
				else
				{
					
					if(OrderBase.equals("Split Ship")&&OrderType.equalsIgnoreCase("Ship items to multiple adresses")){
						ActualPrintAmount = d.findElement(Property.PrintAmountOBOFFS).getText();
					}else{
						ActualPrintAmount = d.findElement(Property.PrintAmountOBOFF).getText();
					}
					
				}
				}
				if(ExpectedPrintAmount.equals(ActualPrintAmount))
				{
					//!System.out.println("Both Amounts are same ");
				}
				else
				{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Amount";
				System.out.println("<-------- Both Amounts are different1---------> "+ErrorNumber);
				System.out.println("ExpectedPrintPOPUPAmount : "+ExpectedPrintAmount);
				System.out.println("ActualPrintPOPUPAmount : "+ActualPrintAmount);
				RW_File.WriteResult(ExpectedPrintAmount, ActualPrintAmount, PageName, PriceType);
				}
			}
			
			String ActualPrintSubTotal = d.findElement(Property.PrintSubtotal).getText();
			String UPdatedprintpagesubtotal = null;
			if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
			{
				//String OP3rdpartytax = d.findElement(Property.PrintTaxvalue).getText();
				//String OP3rdpartytax1 = OP3rdpartytax.substring(1,OP3rdpartytax.length());
				String OP3rdpartyshippingprice = "$0.00";
				if(OrderBase.equals("Item"))
				{
					OP3rdpartyshippingprice =  d.findElement(Property.printAppliedTotalShippingPrice).getText();	
				}
			    String OP3rdpartyshippingprice1 = OP3rdpartyshippingprice.substring(1,OP3rdpartyshippingprice.length());
				UPdatedprintpagesubtotal  = viewsummaryshippingtotal(Subtotal,OP3rdpartyshippingprice1,Addons,DiscountcalculationfromSubTotal,OrderAmountValue,DecimalValue,Postage);
				if(UPdatedprintpagesubtotal.equals(ActualPrintSubTotal))
				{
					//!System.out.println("Both subtotal are same in pRINT PAGEEE");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "SubTotal";
					System.out.println("<-------- Both SubTotal are different--------> "+ErrorNumber);
					System.out.println("ExpectedPrintPOPUPSubTotal :"+UPdatedprintpagesubtotal);
					System.out.println("ActualPrintPOPUPSubTotal :"+ActualPrintSubTotal);
					RW_File.WriteResult(ExpectedPrintSubTotal, ActualPrintSubTotal, PageName, PriceType);
				}	
			}
			else{
			if(ExpectedPrintSubTotal.equals(ActualPrintSubTotal))
			{
				//!System.out.println("Both subtotal are same in pRINT PAGEEE");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "SubTotal";
				System.out.println("<-------- Both SubTotal are different---------> "+ErrorNumber);
				System.out.println("ExpectedPrintPOPUPSubTotal :"+ExpectedPrintSubTotal);
				System.out.println("ActualPrintPOPUPSubTotal :"+ActualPrintSubTotal);
				RW_File.WriteResult(ExpectedPrintSubTotal, ActualPrintSubTotal, PageName, PriceType);
			}
		}
			if(PromotionCoupon.equals("0") || PromotionCoupon.equals("0.00") || PromotionCoupon.equals("0.000") || PromotionCoupon.equals("0.0000"))
			{
				
			}
			else
			{
				if(EnablePromotionsORDiscounts.equals("ON"))
				{
					if(Subtotal.equals("0") || Subtotal.equals("0.00") || Subtotal.equals("0.000") || Subtotal.equals("0.0000")){
						System.out.println("if subtotal is  zero promotion value not displayed in pop up");
					}else{
				String ActualPrintPromotionDiscount = d.findElement(Property.PrintPrmotionDiscount).getText();
				if(ExpectedPrintPromotionDiscount.equals(ActualPrintPromotionDiscount))
				{
					//!System.out.println("Both Promotion Discount are same");
				}
				else
				{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "PromotionDiscount";
				System.out.println("<-------- Both Promotion Discounts are different --------->"+ErrorNumber);
				System.out.println("ExpectedPrintPOPUPPromotionDiscount : "+ExpectedPrintPromotionDiscount);
				System.out.println("ActualPrintPOPUPPromotionDiscount : "+ActualPrintPromotionDiscount);
				RW_File.WriteResult(ExpectedPrintPromotionDiscount, ActualPrintPromotionDiscount, PageName, 
						PriceType);
				}
				}
				}
				else
				{
					String PrintPopupData1 = d.findElement(Property.PrintData1).getText();
					//System.out.println("PrintPopupData1 value "+PrintPopupData1);
					if(PrintPopupData1.contains("Promotion Discount"))
					{
						ErrorNumber = ErrorNumber+1;
						System.out.println("Both Actual and expected values are different");
					}
				}
			}
			String ActualPrintShipingAmount=null;
			if(OrderelementsCheckout.equalsIgnoreCase("yes")||OrderelementsShippingMethod.equalsIgnoreCase("NO")){}else{
			 ActualPrintShipingAmount = d.findElement(Property.printAppliedTotalShippingPrice).getText();
			String updated3rdpartyprintpageshipping = null;
		if(OrderType.equals("DynDownloadShipping")||OrderType.equals("DynShipTOMultipleLocations")||OrderType.equals("ShipToMyAddress")
					||OrderType.equals("StaticDownloadShipping")||OrderType.equals("StaticShipTOMultipleLocations")||
					OrderType.equals("StaticInventoryShipTOMultipleLocations")||OrderType.equals("DynDropShipment with List"))
		{
			if( Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
			{
				updated3rdpartyprintpageshipping = d.findElement(Property.printAppliedTotalShippingPrice).getText();
				ActualShippingPriceForReturn = updated3rdpartyprintpageshipping;// Use this for return value
				//System.out.println("3rd party shiiping method in print page we just verified itt");
			}
			else if(OrderBase.equals("Item"))
			 {
				if((Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00")||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000")
				||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000") || Priceafterapplyingfulfillmentshippingmarkupfee.equals("0")))
				{
					if(ExpectedPrintShippingValue.equals(ActualPrintShipingAmount))
				    {
						//!System.out.println("Both Shipping amounts are same ");
				   }
					else
					{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
		        	String PriceType = "Shipping price";
					System.out.println("<-------- Bothe shipping amounts are different --------->"+ErrorNumber);
			        System.out.println("Actual Shipping price value is: "+ActualPrintShipingAmount);
		         	System.out.println("Expected Shipping price value is: "+ExpectedPrintShippingValue);
			        RW_File.WriteResult(ExpectedPrintShippingValue, ActualPrintShipingAmount, PageName, PriceType);
					}
				}
			 }
			else if(!(Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00")||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000")
			||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000") || Priceafterapplyingfulfillmentshippingmarkupfee.equals("0")))
			{
				String Actualupdatedshippinghandilingfee = d.findElement(Property.printAppliedTotalShippingPrice).getText();
				//ActualShippingPriceForReturn = Actualupdatedshippinghandilingfee;// Use this for return value
				if(ExpectedShippingHandilingPrice.equals(Actualupdatedshippinghandilingfee))
				{
					//!System.out.println("upadetd shipping price is updated");
				}
			  else
			   {
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "ShipingAmount";
				System.out.println("<-------- Bothe shipping amounts are different --------->"+ErrorNumber);
				System.out.println("ExpectedPrintPOPUPShipingAmount : "+ExpectedShippingHandilingPrice);
				System.out.println("ActualPrintPOPUPShipingAmount : "+Actualupdatedshippinghandilingfee);
				RW_File.WriteResult(ExpectedShippingHandilingPrice, Actualupdatedshippinghandilingfee,  PageName, PriceType);
			  }
			}
		}
		}	if(IsTaxExempt.equalsIgnoreCase("YES")){
			//dont look for tax
		     }else{
			String[] CalculateTaxConditions = CalculateTaxCondition.split("_");
			
			if(!CalculateTaxConditions[0].equals("---Select---") && CalculateTaxConditions[1].equals("ON"))
			{
			
				if(CalculateTaxConditions[0].equals("Vertex"))
				{
		    	String ActualPrintTaxValue = d.findElement(Property.PrintTaxvalue).getText();
			if(ActualPrintTaxValue.matches("\\$\\d{1,3}\\.\\d{2,4}"))
			{
				//!System.out.println("Both Tax values are same ");
				//!System.out.println(PageName+" Tax Value : "+ActualPrintTaxValue);
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "PrintTaxValue";
				System.out.println("<---------- Both Tax values are different --------->"+ErrorNumber);
				System.out.println("ExpectedPrintTaxValue : "+"\\$\\d{1,3}\\.\\d{2,4}");
				System.out.println("ActualPrintPOPUPTaxValue : "+ActualPrintTaxValue);
				RW_File.WriteResult("\\$\\d{1,3}\\.\\d{2,4}", ActualPrintTaxValue,  PageName, PriceType);
			}
				}
				else
				{
					String ActualPrintTaxValue = d.findElement(Property.PrintTaxvalue).getText();
					String updatedtaxinprintpage = null;
					if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
					{
						updatedtaxinprintpage = d.findElement(Property.PrintTaxvalue).getText();
					}
					if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
					{
					if(updatedtaxinprintpage.equals(ActualPrintTaxValue))
					{
						//!System.out.println("Both Tax values are same ");
					}
					else
					{
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						String PriceType = "PrintTaxValue";
						System.out.println("<--------- Both Tax values are different ---------->"+ErrorNumber);
						System.out.println("ExpectedPrintTaxValue : "+ExpectedPrintTaxValue);
						System.out.println("ActualPrintPOPUPTaxValue : "+ActualPrintTaxValue);
						RW_File.WriteResult(ExpectedPrintTaxValue, ActualPrintTaxValue,  PageName, PriceType);
					}
					}
					else if(OrderBase.equals("Order")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")) &&
							IsShippingTaxable.equals("Yes"))
					{
						
						String OrderbaseThirdPartyShippingPrice = d.findElement(Property.PrintShippingvalue).getText();

						updatedtaxinprintpage = TaxValueWithThirdPartyShippingIsTaxable(OrderbaseThirdPartyShippingPrice, TotalPrice, PriceAfterApplyingCoupon,
													Tax, OrderAmountValue);
						
						if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
						{
						if(updatedtaxinprintpage.equals(ActualPrintTaxValue))
						{
							//!System.out.println("Both Tax values are same ");
						}
						else
						{
							ErrorNumber = ErrorNumber+1;
							captureScreenshot();
							String PriceType = "PrintTaxValue";
							System.out.println("<------ Both Tax values are different ---------->"+ErrorNumber);
							System.out.println("ExpectedPrintTaxValue : "+updatedtaxinprintpage);
							System.out.println("ActualPrintPOPUPTaxValue : "+ActualPrintTaxValue);
							RW_File.WriteResult(updatedtaxinprintpage, ActualPrintTaxValue,  PageName, PriceType);
						}
						}
					}
					else
					{
						if(ExpectedPrintTaxValue.equals(ActualPrintTaxValue))
						{
							//!System.out.println("Both Tax values are same ");
						}
						else
						{
							ErrorNumber = ErrorNumber+1;
							captureScreenshot();
							String PriceType = "PrintTaxValue";
							System.out.println("<-------- Both Tax values are different --------->"+ErrorNumber);
							System.out.println("ExpectedPrintTaxValue : "+ExpectedPrintTaxValue);
							System.out.println("ActualPrintPOPUPTaxValue : "+ActualPrintTaxValue);
							RW_File.WriteResult(ExpectedPrintTaxValue, ActualPrintTaxValue,  PageName, PriceType);
						}
					}
					
				}
			
			}
		     }
			String ActualPrintShippingValue=null;
if(OrderelementsCheckout.equalsIgnoreCase("Yes")||Orderelements.equalsIgnoreCase("No")){}else{
			ActualPrintShippingValue = d.findElement(Property.PrintShippingvalue).getText();
			String updatedtaxinprintpage1 = null;
	  if(OrderType.equals("DynDownloadShipping")||OrderType.equals("DynShipTOMultipleLocations")||OrderType.equals("ShipToMyAddress")
					||OrderType.equals("StaticDownloadShipping")||OrderType.equals("StaticShipTOMultipleLocations")||
					OrderType.equals("StaticInventoryShipTOMultipleLocations")||OrderType.equals("DynDropShipment with List"))
	    { 
			if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
			{
				updatedtaxinprintpage1 = d.findElement(Property.PrintShippingvalue).getText();
				//System.out.println("3rd party tax in print page we just veried it");  
			}
			else if(OrderBase.equals("Item"))
			 {
				if((Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00")||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000")
				||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000") || Priceafterapplyingfulfillmentshippingmarkupfee.equals("0")))
				{
					if(ExpectedPrintShippingValue.equals(ActualPrintShipingAmount))
				    {
						//!System.out.println("Both Shipping amounts are same ");
				   }
					else
					{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
		        	String PriceType = "Shipping price";
					System.out.println("<-------- Bothe shipping amounts are different --------->"+ErrorNumber);
			        System.out.println("Actual Shipping price value is: "+ActualPrintShipingAmount);
		         	System.out.println("Expected Shipping price value is: "+ExpectedPrintShippingValue);
			        RW_File.WriteResult(ExpectedPrintShippingValue, ActualPrintShipingAmount, PageName, PriceType);
					}
				}
			 }
			else if(!(Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00")||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000")
			||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000") || Priceafterapplyingfulfillmentshippingmarkupfee.equals("0")))
			{
				String Actualupdatedshippinghandilingfee = d.findElement(Property.printAppliedTotalShippingPrice).getText();
				if(ExpectedShippingHandilingPrice.equals(Actualupdatedshippinghandilingfee))
				{
					//!System.out.println("upadetd shipping price is updated");
				}
			  else
			   {
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "ShipingAmount";
				System.out.println("<-------- Bothe shipping amounts are different --------->"+ErrorNumber);
				System.out.println("ExpectedPrintPOPUPShipingAmount : "+ExpectedShippingHandilingPrice);
				System.out.println("ActualPrintPOPUPShipingAmount : "+Actualupdatedshippinghandilingfee);
				RW_File.WriteResult(ExpectedShippingHandilingPrice, Actualupdatedshippinghandilingfee,  PageName, PriceType);
			  }
			}
		}
		}	
			/*if(TaxType[0].equals("Vertex"))
			{
				String ExpectedPrintTotal2 =VertexTotalPrintPopup(ExpectedPrintTotal);
			ExpectedPrintTotal=Config.Currency+Total;
			String ActualPrintTotal = d.findElement(Property.PrintTotal2).getText();
			if(ExpectedPrintTotal2.equals(ActualPrintTotal))
			{
				//System.out.println("Both Total values are same ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				String PriceType = "PrintTotal";
				System.out.println("<-------- Both Total values are different --------->"+ErrorNumber);
				System.out.println("ExpectedPrintTotal : "+ExpectedPrintTotal2);
				System.out.println(" ActualPrintTotal : "+ActualPrintTotal);
				RW_File.WriteResult(ExpectedPrintTotal2, ActualPrintTotal, PageName, PriceType);
			}
			}
			else
			{
				ExpectedPrintTotal=Config.Currency+Total;
				System.out.println("ex total is " +ExpectedPrintTotal);
				String ActualPrintTotal = d.findElement(Property.PrintTotal2).getText();
				System.out.println("act total is" +ActualPrintTotal);
				String OPupdatedgrandtotal = null;
				if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
				{
					String OP3rdpartytax = d.findElement(Property.PrintTaxvalue).getText();
					String OP3rdpartytax1 = OP3rdpartytax.substring(1,OP3rdpartytax.length());
					String OP3rdpartyshippingprice =  d.findElement(Property.printAppliedTotalShippingPrice).getText();
					String OP3rdpartyshippingprice1 = OP3rdpartyshippingprice.substring(1,OP3rdpartyshippingprice.length());
				    OPupdatedgrandtotal  = OCgrandtotal(Subtotal,PromotionCoupon,OP3rdpartytax1,OP3rdpartyshippingprice1,
								Addons,DiscountcalculationfromSubTotal,OrderAmountValue);
						
				}
				if(ExpectedPrintTotal.equals(ActualPrintTotal))
				{
					System.out.println("Both Total values are same ");
				}
				else if (OPupdatedgrandtotal.equals(ActualPrintTotal))
				{
					//System.out.println("Both Total values are same ");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					String PriceType = "PrintTotal";
					System.out.println("<-------- Both Total values are different --------->"+ErrorNumber);
					System.out.println("ExpectedPrintTotal : "+ExpectedPrintTotal);
					System.out.println(" ActualPrintTotal : "+ActualPrintTotal);
					RW_File.WriteResult(ExpectedPrintTotal, ActualPrintTotal, PageName, PriceType);
				}
			}*/
			
			
		/*	if(PrintTotalPrice.equals(ActualPrintTotal))
			{
				//System.out.println("Both total values are same ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				String PriceType = "TotalPrice";
				System.out.println("<-------- Both Total values are different --------->"+ErrorNumber);
				System.out.println("PrintTotalPrice : "+PrintTotalPrice);
				System.out.println(" ActualPrintTotal : "+ActualPrintTotal);
				RW_File.WriteResult(PrintTotalPrice, ActualPrintTotal,  PageName, PriceType);
			}*/
			if(EnablePromotionsORDiscounts.equals("ON"))
			{
			String ActualPrintAppliedDiscontPrice = d.findElement(Property.PrintAppliedTotalDiscount).getText();
			if(ExpectedPrintAppliedDiscontPrice.equals(ActualPrintAppliedDiscontPrice))
			{
				//!System.out.println("Both Applied Discount prices are same");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "AppliedDiscontPrice";
				System.out.println("<-------- Both Applied Discount prices are different --------->"+ErrorNumber);
				System.out.println("ExpectedPrintPOPUPAppliedDiscontPrice : "+ExpectedPrintAppliedDiscontPrice);
				System.out.println("ActualPrintPOPUPAppliedDiscontPrice : "+ActualPrintAppliedDiscontPrice);
				RW_File.WriteResult(ExpectedPrintAppliedDiscontPrice, ActualPrintAppliedDiscontPrice, PageName, PriceType);
			}
			}
			else
			{
				String PrintPopupData2 = d.findElement(Property.PrintData2).getText();
				//System.out.println("PrintPopupData2");
				if(PrintPopupData2.contains("Applied Total Discount Price :"))
				{
					ErrorNumber = ErrorNumber+1;
					System.out.println("Both Actual and expected values are different");
				}
			}
			
			String ActualPrintAppliedAddonPrice = d.findElement(Property.PrintAppliedTotalAddonPrice).getText();
			if(ExpectedPrintAppliedAddonPrice.equals(ActualPrintAppliedAddonPrice))
			{
				//!System.out.println("Both Applied Addon prices are same ");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "AppliedAddonPrice";
				System.out.println("<-------- Both Applied Addon prices are different --------->"+ErrorNumber);
				System.out.println("ExpectedPrintPOPUPAppliedAddonPrice : "+ExpectedPrintAppliedAddonPrice);
				System.out.println("ActualPrintPOPUPAppliedAddonPrice : "+ActualPrintAppliedAddonPrice);
				RW_File.WriteResult(ExpectedPrintAppliedAddonPrice, ActualPrintAppliedAddonPrice,  PageName, PriceType);
			}
			if(Postage.equals("0") || Postage.equals("0.00") || Postage.equals("0.000") || Postage.equals("0.0000"))
			{
				
			}
			else
			{	
			String ActualPrintAppliedTotalPostagePrice = d.findElement(Property.PrintAppliedTotalPostagePrice).getText();
			if(ExpectedPrintAppliedTotalPostagePrice.equals(ActualPrintAppliedTotalPostagePrice))
			{
				//!System.out.println("Both Applied Total postage prices are same ");
			}
			else
			{
				String PriceType = "AppliedTotalPostagePrice";
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				System.out.println("<-------- Both Applied Total Postage prices are different --------->"+ErrorNumber);
				System.out.println("ExpectedPrintPOPUPAppliedTotalPostagePrice : "+ExpectedPrintAppliedTotalPostagePrice);
				System.out.println("ActualPrintPOPUPAppliedTotalPostagePrice : "+ActualPrintAppliedTotalPostagePrice);
				RW_File.WriteResult(ExpectedPrintAppliedTotalPostagePrice, ActualPrintAppliedTotalPostagePrice, PageName, PriceType);
			}
			}
			
			 if(OrderelementsCheckout.equalsIgnoreCase("NO")||OrderelementsBillingAddress.equalsIgnoreCase("NO")){}else{
			// Shipping address same as Billing Address case
			if(OrderBase.equalsIgnoreCase("Order") && (OrderType.equals("DynDropShipment with List") ||
					OrderType.equals("DynDownloadShipping") || OrderType.equals("StaticDownloadShipping")
					|| OrderType.equals("ShipToMyAddress")) && IfShippingaddressIseditble.equalsIgnoreCase("NO"))
		  	{
		  		
		  		//Verify Both Shipping and Billing address are same or not first Time
			String BillingAddressValue = d.findElement(Property.PopUpBillingAddress).getText();
			String ShippingAddressValue = d.findElement(Property.PopUpShippingAddress).getText();		
			//System.out.println("BillingAddressValue :"+BillingAddressValue);
			//System.out.println("ShippingAddressValue :"+ShippingAddressValue);
			
			ShipAddSameAsBillAddPopUp(BillingAddressValue,ShippingAddressValue,AppPageName1,
					ShipAddSameAsBillAdd, ShipAddSameAsBillAddSub);
		  	
		  	}
			// Shipping address NOT same as Billing Address case
			if(OrderBase.equalsIgnoreCase("Order") && (OrderType.equals("DynDropShipment with List") ||
					OrderType.equals("DynDownloadShipping") || OrderType.equals("StaticDownloadShipping")
					|| OrderType.equals("ShipToMyAddress")) && IfShippingaddressIseditble.equalsIgnoreCase("YES"))
		  	{
		  		
		  		//Verify Both Shipping and Billing address are same or not first Time
			String BillingAddressValue = d.findElement(Property.PopUpBillingAddress).getText();
			String ShippingAddressValue = d.findElement(Property.PopUpShippingAddress).getText();		
			//System.out.println("BillingAddressValue :"+BillingAddressValue);
			//System.out.println("ShippingAddressValue :"+ShippingAddressValue);
			
			ShipAddNotSameAsBillAddPopUp(BillingAddressValue,ShippingAddressValue,AppPageName1,
					ShipAddSameAsBillAdd, ShipAddSameAsBillAddSub);
		  	
		  	}
		 }
			
		    d.close();
			tec.AC40.Common.Wait.wait5Second();
			 //d.switchTo().window(mainWinID);
			 tec.AC40.Common.Wait.wait2Second();
				}catch (Exception e){
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					e.printStackTrace();
	      }
		//System.out.println("ActualShippingPriceForReturn :"+ActualShippingPriceForReturn);
		return ActualShippingPriceForReturn;
	}
	public static void jobticketPriceVerification(String OrderBase,String EnableZeroAmountOrder,String ProdutType,String Quantity,String OrderelementsBillingAddress,
			String OrderelementsShippingAddress,String OrderelementsCheckout,String OrderelementsJobTicket,String ShipAddSameAsBillAdd, String ShipAddSameAsBillAddSub,String AppPageName1,
			String OrderType,String IfShippingaddressIseditble) throws InterruptedException
	{
	try{
		tec.AC40.Common.Wait.wait5Second();
		String PageName = AppPageName1;
		 for (String winHandle : d.getWindowHandles()) {
		        d.switchTo().window(winHandle); 
		    }
		    tec.AC40.Common.Wait.wait2Second();
			 if(OrderelementsCheckout.equalsIgnoreCase("NO")||OrderelementsBillingAddress.equalsIgnoreCase("NO")){}else{

		String BillingAddValue = d.findElement(By.xpath("//td[2]")).getText();		
		

		BillingAddressVerificationOrderSummary(EnableZeroAmountOrder, BillingAddValue, PageName);
		String[] BillingAddExpectedStatus = EnableZeroAmountOrder.split("_");
		
		try
		{
			String Popup2ndBillingAdd = null;
			if(OrderBase.equals("Item"))
			{
				Popup2ndBillingAdd = d.findElement(By.xpath("//td[2]")).getText();
			}
			else
			{
				Popup2ndBillingAdd = d.findElement(By.xpath("//td[2]")).getText();
			}
			System.out.println("Popup2ndBillingAddjobtick"+Popup2ndBillingAdd);
		BillingAddressVerificationOrderSummary(EnableZeroAmountOrder, Popup2ndBillingAdd, PageName);
		
		
		}
		catch(Exception e)
		{ 
			if(BillingAddExpectedStatus[1].equalsIgnoreCase("NO"))
			{
				//!System.out.println("Success Billing address not displayed to user");
			}
			else
			{
				ErrorNumber = ErrorNumber+1;
				captureScreenshot();
				String PriceType = "Billing Address";
				System.out.println("<-------- "+ PageName +" Failed Billing address not Displays to user ---------->"+ErrorNumber);
				System.out.println("Actual Billing Address status : "+"Billing Address Not displayed");
				System.out.println("Expected Billing Address status : "+"Billing Address displays to user");
				RW_File.WriteResult("Billing Address displays to user", "Billing Address Not displayed",  PageName, PriceType);
			}
			
		}
	} 
		String ActualPrintQuantity = null;
		if(OrderBase.equals("Item"))
		{
			ActualPrintQuantity = d.findElement(Property.jobticketQuantity).getText();
		}
		else
		{
			ActualPrintQuantity = d.findElement(Property.jobticketQuantity).getText();
		}
		int ExpectedPrintQuantity = Double.valueOf(Quantity).intValue();
		int ActualPrintQuantity1= Double.valueOf(ActualPrintQuantity).intValue();
		System.out.println("Both are Quantitys are same in jobticket "+ActualPrintQuantity1);

		if(ExpectedPrintQuantity == ActualPrintQuantity1)
		{
			System.out.println("Both are Quantitys are same in jobticket ");
		}
		else
		{
			ErrorNumber = ErrorNumber+1;
			captureScreenshot();
			String PriceType = "Quantity";
			System.out.println("<-------- Bothe Quantitys are different in job ticket : --------->"+ErrorNumber);
			System.out.println("ExpectedPrintPOPUPQuantity : "+ExpectedPrintQuantity);
			System.out.println("ActualPrintPOPUPQuantity1 : "+ActualPrintQuantity);
			RW_File.WriteResult(Quantity, ActualPrintQuantity, PageName, PriceType);
			
		}
		 if(OrderelementsCheckout.equalsIgnoreCase("NO")||OrderelementsBillingAddress.equalsIgnoreCase("NO")){}else{

		String ShippingAddressValue = null;
		ShippingAddressValue=d.findElement(Property.jobticketShippingAddress).getText();		

		// Shipping address same as Billing Address case
					if(OrderBase.equalsIgnoreCase("Order") && (OrderType.equals("DynDropShipment with List") ||
							OrderType.equals("DynDownloadShipping") || OrderType.equals("StaticDownloadShipping")
							|| OrderType.equals("ShipToMyAddress")) && IfShippingaddressIseditble.equalsIgnoreCase("NO"))
				  	{
				  		
				  		//Verify Both Shipping and Billing address are same or not first Time
					String BillingAddressValue = d.findElement(Property.jobticketBillingAddress).getText();
				 ShippingAddressValue = d.findElement(Property.jobticketShippingAddress).getText();		
					//System.out.println("BillingAddressValue :"+BillingAddressValue);
					//System.out.println("ShippingAddressValue :"+ShippingAddressValue);
					
					ShipAddSameAsBillAddPopUp(BillingAddressValue,ShippingAddressValue,AppPageName1,
							ShipAddSameAsBillAdd, ShipAddSameAsBillAddSub);
				  	
				  	}
					// Shipping address NOT same as Billing Address case
					if(OrderBase.equalsIgnoreCase("Order") && (OrderType.equals("DynDropShipment with List") ||
							OrderType.equals("DynDownloadShipping") || OrderType.equals("StaticDownloadShipping")
							|| OrderType.equals("ShipToMyAddress")) && IfShippingaddressIseditble.equalsIgnoreCase("YES"))
				  	{
				  		
				  		//Verify Both Shipping and Billing address are same or not first Time
					String BillingAddressValue = d.findElement(Property.jobticketBillingAddress).getText();
				 ShippingAddressValue = d.findElement(Property.jobticketShippingAddress).getText();		
					//System.out.println("BillingAddressValue :"+BillingAddressValue);
					//System.out.println("ShippingAddressValue :"+ShippingAddressValue);
					
					ShipAddNotSameAsBillAddPopUp(BillingAddressValue,ShippingAddressValue,AppPageName1,
							ShipAddSameAsBillAdd, ShipAddSameAsBillAddSub);
				  	
				  	}
					System.out.println("Popup2ndshippingAddjobtick"+ShippingAddressValue);
	
					
		   }		
					
		
		}catch (Exception e){
		ErrorNumber = ErrorNumber+1;
		captureScreenshot();
		e.printStackTrace();
}}

	public static void ViewOrderGridVerification(String SubTotal, String Total, String OrderBase, 
			String ShippingPrice, String OrderBaseShipping, String Postage, String PageName,
			String ShippingPricePerPiece, String GrandTotal, String ActualShippingPrice,
			String Weighttype, String Priceafterapplyingfulfillmentshippingmarkupfee,
			String LandingPageOption,String LandingPageSubtotal,String DecimalValue,String ItemPerPrice,
			String PromotionDiscountPercentage,String Addons,String Tax,String Discount,
			String DiscountPercentage,String DiscountCalculationFromSubTotal,String PromotionCoupon,String PromotionDiscountAfterSubtractingFromSubTotal,
			String LDiscuntCalulationFromSubtotal,String PriceAfterCalculatingTax,String Quantity,String OrderType,
			String DownloadPrice,String LandingPageProductPP,int BasePriceIncrementValue,String ProdutType,String LandingPageDiscountValue,
			String EnablePromotionsORDiscounts,String CalculateTaxCondition,
			String userordershippingorhandlingfee,String OrderAmountValue,String PriceAfterApplyingCoupon,String TotalPrice,String IsShippingTaxable,String Orderelements,String OrderelementsAddOns,String OrderelementsBillingAddress,
			String OrderelementsInventoryLocation,String OrderelementsInventoryNumber,
			String OrderelementsPaymentDetail,String OrderelementsShippingAddress,String OrderelementsShippingMethod,String OrderelementsSpecialInstructions,String OrderelementsCheckout)throws InterruptedException {
		try{
		WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
		tec.AC40.Common.Wait.wait2Second();
		/**
		 *  code is blocked due to changes in view orders grid page
		 */
		FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
		 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
		 waitfl.pollingEvery(1, TimeUnit.SECONDS);
		 waitfl.ignoring(NoSuchElementException.class);
		 waitfl.ignoring(StaleElementReferenceException.class);
		waitfl.until(ExpectedConditions.elementToBeClickable(Property.VOGSubTotal));
		tec.AC40.Common.Wait.wait2Second();
		String ActualVOGSubtotal =	d.findElement(Property.VOGSubTotal).getText();
		String ExpectedVOGSubTotal =null;
		if(LandingPageOption.equalsIgnoreCase("yes")){
			double  Subtotal1= Double.valueOf(SubTotal).doubleValue();
			double  LandingPageSubtotal1= Double.valueOf(LandingPageSubtotal).doubleValue();
			double   SubTotal2=Subtotal1+LandingPageSubtotal1;
			String   SubTotal3=""+SubTotal2;
			String SubTotal4 = Decimalsetting(SubTotal3, DecimalValue);
			ExpectedVOGSubTotal = Config.Currency+SubTotal4;
			}else{
				ExpectedVOGSubTotal = Config.Currency + SubTotal;
			}
		      // Sub total price verification
				if(ActualVOGSubtotal.equals(ExpectedVOGSubTotal))
				{
					//!System.out.println("Both Sub total prices are same");
				}
				else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					String PriceType = "Sub Total";
					System.out.println("<--------- Both View orders grid subtotal prices are different ------> "+ErrorNumber);
					System.out.println("Expected value: "+ExpectedVOGSubTotal);
					System.out.println("  Actual value :"+ActualVOGSubtotal);
					RW_File.WriteResult(ExpectedVOGSubTotal, ActualVOGSubtotal,PageName, PriceType);
				}
				    d.findElement(Property.VOGSubTotal).click();
					tec.AC40.Common.Wait.wait2Second();
				    waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
					 waitfl.pollingEvery(1, TimeUnit.SECONDS);
					 waitfl.ignoring(NoSuchElementException.class);
					 waitfl.ignoring(StaleElementReferenceException.class);
					waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.VOGPOPUPSubTotal));
					String s1=d.findElement(Property.VOGPOPUPSubTotal).getText();
					System.out.println(" VO Popup Sub total price is -->"+s1);
					String ExpectedVOGPOPUPShippingPrice=null;
				    if(OrderBase.equals("Item"))
				    {
				    	ExpectedVOGPOPUPShippingPrice = Config.Currency+ShippingPrice;
				    }
				    else
				    {
				    	ExpectedVOGPOPUPShippingPrice = Config.Currency+OrderBaseShipping;
				    } 
				    String ExpectedVOGPOPUPTaxValue = Config.Currency+PriceAfterCalculatingTax;
				    String ExpectedVOGPOPUPTotalPrice = Config.Currency+Total;	
				    String ExpectedVOGPOPUPProdQuantity = Quantity;
				    Thread.sleep(5000);
				    System.out.println("Entered into PS View orders Pop-Up");
				    String ActualQuantity=null;
				    if(Config.LayoutType.equalsIgnoreCase("Layout2")||Config.LayoutType.equalsIgnoreCase("Classic")){
				    	 ActualQuantity=d.findElement(Property.VOGPOPUPProdQuantityl).getText();
						    System.out.println("**ActualQuantity**"+ActualQuantity);
				    }else{
				     ActualQuantity=d.findElement(Property.VOGPOPUPProdQuantity).getText();
				    }
				    
				    System.out.println(" Actual View order popup quantiy -->  " + ActualQuantity);
				    
					int ActualQuantity1 = Double.valueOf(ActualQuantity).intValue();
					
					System.out.println("Int Actual Qunaity -->" + ActualQuantity1);
					int Quantity1 = Double.valueOf(Quantity).intValue();
				    System.out.println("**ActualQuantity**"+ActualQuantity1);
				    if(Quantity1==ActualQuantity1){
				    	System.out.println("Both Item quantitys are same");
				    }else
				    {
		    	        String PriceType = "Quantity";
		    	        ErrorNumber = ErrorNumber+1;
		    	        captureScreenshot();
		    	        System.out.println("<-------- Both View Order Grid Pop-Up  Quantitys are different --------->"+ErrorNumber);
		    	        System.out.println("Expected View Order Grid Pop-Up  Quantitys : "+ExpectedVOGPOPUPProdQuantity);
		    	        System.out.println("Actual View Order Grid Pop-Up  Quantitys: "+ActualQuantity);
		    	        RW_File.WriteResult(ExpectedVOGPOPUPProdQuantity, ActualQuantity, PageName, PriceType);
				    }
				    String ExpectedProdItemPrice=null;
				    String ExpectedLandingPageProductPP=null;
				    if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload"))
					{
				    	ExpectedProdItemPrice = Config.Currency+DownloadPrice;
				    	ExpectedLandingPageProductPP = Config.Currency+DownloadPrice;    
					}else if(OrderType.equals("DynShipTOMultipleLocations") || OrderType.equals("StaticShipTOMultipleLocations")
							|| OrderType.equals("StaticInventoryShipTOMultipleLocations"))
					{
						ExpectedProdItemPrice ="-";
						ExpectedLandingPageProductPP="-";
					}
					else
					{
						ExpectedProdItemPrice = Config.Currency+ItemPerPrice;
						ExpectedLandingPageProductPP = Config.Currency+LandingPageProductPP;
						
					}
				    
				    String ActualProdItemPrice = d.findElement(Property.VOGPOPUPProdItemPrice).getText();
				    if(ActualProdItemPrice.equals(ExpectedProdItemPrice))
				    {
				    	System.out.println("View Order Grid Pop-Up Item prices are same");
				    }
				    else
				    {
				    	String PriceType = "Item Price";
				    	ErrorNumber = ErrorNumber+1;
				    	captureScreenshot();
				    	System.out.println("<-------- Both View Order Grid Pop-Up Item Prices are different --------->"+ErrorNumber);
				    	System.out.println("Expected View Order Grid  Item Prices price : "+ExpectedProdItemPrice);
				    	System.out.println("Actual Users View Order Grid  price : "+ActualProdItemPrice);
				    	RW_File.WriteResult(ExpectedProdItemPrice, ActualProdItemPrice, PageName, PriceType);
				    }
				    String ExpectedVOGPOPItemSubTotal=null;
				    if(LandingPageOption.equalsIgnoreCase("yes")){
						
				    	ExpectedVOGPOPItemSubTotal = Config.Currency + SubTotal;
						}else{
							ExpectedVOGPOPItemSubTotal = Config.Currency + SubTotal;
						}

				    String ActualSubtotal=d.findElement(Property.VOGPOPUPSubTotal).getText();
					if(ActualSubtotal.equals(ExpectedVOGPOPItemSubTotal))
					{
						System.out.println("Both item Subtotals in view order grid popup values are same");
					}
					else
					{
			    	String PriceType = "SubTotal";
			    	ErrorNumber = ErrorNumber+1;
			    	captureScreenshot();
			    	System.out.println("<-------- Both item Subtotals in view order grid popup values are different --------->"+ErrorNumber);
			    	System.out.println("Expected View Order Grid  Subtotal price : "+ExpectedVOGPOPItemSubTotal);
			    	System.out.println("Actual View Order Grid  Subtotal price : "+ActualSubtotal);
			    	RW_File.WriteResult(ExpectedVOGPOPItemSubTotal, ActualSubtotal, PageName, PriceType);
					}
				    String ExpectedAddonPrice = Config.Currency+Addons;
				    String ActuaAddonPrice = d.findElement(Property.VOGPOPUPAddOnPrice).getText();
				    if(ActuaAddonPrice.equals(ExpectedAddonPrice))
				    {
				    	System.out.println("Both view order grid popup Addon prices are same ");
				    }
				    else
				    {
				    	String PriceType = "Addon price";
				    	ErrorNumber = ErrorNumber+1;
				    	captureScreenshot();
				    	System.out.println("<-------- Both view order grid popup Addon prices are different --------->"+ErrorNumber);
				    	System.out.println("Expected View Order Grid  Addon price : "+ExpectedAddonPrice);
				    	System.out.println("Actual View Order Grid  Addon price : "+ActuaAddonPrice);
				    	RW_File.WriteResult(ExpectedAddonPrice, ActuaAddonPrice, PageName, PriceType);
				    }
				    String  ExpectedProdDiscount = null;
				    
				    String ActualDiscountPercentage= null;
					//We get the expected Discount percentage based on Discount type like Amount or percentage
					if(DiscountPercentage.equals("N"))
					{
						System.out.println("OrderFlow.IsBaseDiscountZero :"+OrderFlow.IsBaseDiscountZero);
						System.out.println("BasePriceIncrementValue :"+BasePriceIncrementValue);
						// This part works if Discount value is amount
						//First if block related to Base price cases only
						if(Discount.equals("0") ||
								Discount.equals("0.00") || Discount.equals("0.000") ||
								Discount.equals("0.0000"))
						{
							ExpectedProdDiscount = Config.Currency+Discount;
							if(BasePriceIncrementValue == 1 && OrderFlow.IsBaseDiscountZero == true)
							{
								ExpectedProdDiscount = Config.Currency+Discount;
							}
							else if(BasePriceIncrementValue == 1 && OrderFlow.IsBaseDiscountZero == false)
							{
								ExpectedProdDiscount = Config.Currency+Discount;
							}
						}
						else 
						{
							// Execute this code when Discount value is more than zero.;
							ExpectedProdDiscount = Config.Currency+Discount;
						}
						
					}
					else
					{
						ExpectedProdDiscount = Discount+Config.PercentageSymbol;
					}
					if(EnablePromotionsORDiscounts.equals("ON"))
				    {
					String ActualProdDiscount=d.findElement(Property.VOGPOPUPDiscount).getText();
					   if(ActualProdDiscount.equals(ExpectedProdDiscount))
					    {
					    	System.out.println("Both VOGPOPUP item Discount prices are same");
					    }
					    else
					    {
					    	String PriceType = "Discount";
					    	ErrorNumber = ErrorNumber+1;
					    	captureScreenshot();
					    	System.out.println("<-------- Both VOGPOPUP item Discount prices are different --------->"+ErrorNumber);
					    	System.out.println("Expected View Order Grid Discount price : "+ExpectedProdDiscount);
					    	System.out.println("Actual View Order Grid  Discount price : "+ActualProdDiscount);
					    	RW_File.WriteResult(ExpectedProdDiscount, ActualProdDiscount, PageName, PriceType);
					    }
				    }
					String ExpectedShippingHandilingPrice =null;
					if((Orderelements.equalsIgnoreCase("YES")&& OrderelementsShippingAddress.equalsIgnoreCase("NO"))||(Orderelements.equalsIgnoreCase("YES")&& OrderelementsShippingMethod.equalsIgnoreCase("NO"))){}else{
						ExpectedShippingHandilingPrice = Config.Currency+Priceafterapplyingfulfillmentshippingmarkupfee;
						//Below code related to third party shipping price verification 
						//We do not have shipping price in view summary page whenever Orderbase equal to "Order"
						if(OrderBase.equals("Item")&& (Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
						{
							String Actualthrdpartyshippingprice = d.findElement(Property.VOGPOPUPShipping).getText();
							if(Actualthrdpartyshippingprice.matches("\\$\\d{1,5}?\\,?\\d{1,3}\\.\\d{2,4}"))
							 {
								//!System.out.println("actual 3rd party shipping value is " +Actualthrdpartyshippingprice);
							 }
							else
							{
								ErrorNumber = ErrorNumber+1;
								captureScreenshot();
								String PriceType = "ThirdParty Shipping Price";
								System.out.println("<------- VOGPOPUP Both Third Party Shipping prices are different ------>"+ErrorNumber);
								System.out.println("Actual View Order Grid  Third Shipping price is : "+Actualthrdpartyshippingprice);
								System.out.println("Expected View Order Grid  Third Party shipping price is : "+"\\$\\d{1,5}?\\,?\\d{1,3}\\.\\d{2,4}");
								RW_File.WriteResult("\\$\\d{1,5}?\\,?\\d{1,3}\\.\\d{2,4}", Actualthrdpartyshippingprice, PageName, PriceType);
							}
						}

						//Below code related to shipping price verification when orderbase is equal to Item
						if(OrderBase.equals("Item"))
						{
							if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload") || ProdutType.equals("Broadcast") || 
								ProdutType.equals("Dynamic Email") )
							{ // Condition for with out shipping or postage values (Level II)
							// we do not have shipping price value for above conditions.
							}
							else
							{	
					    		if(OrderBase.equals("Item"))
					    		{// Condition starts for general shipping (Level IV)
					    			if(Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00")||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000")
										||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000") || Priceafterapplyingfulfillmentshippingmarkupfee.equals("0"))
					    			{// Condition starts for with out shipping markup fee and handling fee 
								
					    				String ActualVOGPOPUPShipping = d.findElement(Property.VOGPOPUPShipping).getText();
					    				if(ExpectedVOGPOPUPShippingPrice.equals(ActualVOGPOPUPShipping))
					    				{
					    					System.out.println("VOGPOPUP shipping price is same");
					    				}
					    				else
					    				{
					    					//System.out.println("helllo");
					    					ErrorNumber = ErrorNumber+1;
					    					captureScreenshot();
					    					String PriceType = "Shipping price";
					    					System.out.println("<------ VOGPOPUP Both Shipping prices are different1 ----->"+ErrorNumber);
					    					System.out.println("Actual View Order Grid  Shipping price value is: "+ActualVOGPOPUPShipping);
					    					System.out.println("Expected View Order Grid  Shipping price value is: "+ExpectedVOGPOPUPShippingPrice);
					    					RW_File.WriteResult(ExpectedVOGPOPUPShippingPrice, ActualVOGPOPUPShipping, PageName, PriceType);
					    				}
					    			}
					    		}// Condition ends for with out shipping handling fee and markup fee (Level IV)
					    		else if(!(Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00")||
					    				Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000")
									||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000") ||
									Priceafterapplyingfulfillmentshippingmarkupfee.equals("0")))
					    			{  // Condition starts for with shipping handling fee and Markup fee 
					    			String Actualupdatedshippinghandilingfee = d.findElement(Property.VOGPOPUPShipping).getText();
					    			if(ExpectedShippingHandilingPrice.equals(Actualupdatedshippinghandilingfee))
					    			{
					    				//!System.out.println("upadetd shipping price is updated");
					    			}
					    			else
					    			{
						         	ErrorNumber = ErrorNumber+1;
						         	captureScreenshot();
						        	String PriceType = "Shipping price";
							        System.out.println("<-------- VOGPOPUP Both Shipping prices are different1 ----->"+ErrorNumber);
							        System.out.println("Actual View Order Grid Shipping price value is: "+Actualupdatedshippinghandilingfee);
						         	System.out.println("Expected View Order Grid  Shipping price value is: "+ExpectedShippingHandilingPrice);
							        RW_File.WriteResult(ExpectedShippingHandilingPrice, Actualupdatedshippinghandilingfee, PageName, PriceType);
					    			}
					    		}// Condition ends for with shipping handling fee and markup fee (Level IV)
					     
							}// Condition end for with shipping or postage values (Level II)
						}
		}// Item base code ends here (we do not have shipping value for Order base). (Level I)
					String ExpectedItemPostage = null;
				    if(Postage.equals("0") || Postage.equals("0.00") || Postage.equals("0.000") || Postage.equals("0.0000"))
				    {
				    	ExpectedItemPostage = Config.Currency+Postage;
				    }
				    else
				    {
				    	ExpectedItemPostage = Config.Currency+Postage;
				    }
				    
				    String ActualItemPostage = null;
				    if(Postage.equals("0") || Postage.equals("0.00") || Postage.equals("0.000") || Postage.equals("0.0000"))
				    {
				    	ActualItemPostage = d.findElement(Property.VOGPOPUPPostage).getText();	
				    }
				    else
				    {
				    	ActualItemPostage = d.findElement(Property.VOGPOPUPPostage).getText();
				    }
				    if(Postage.equals("0") || Postage.equals("0.00") || Postage.equals("0.000") || Postage.equals("0.0000"))
				    {
				    	
				    }
				    else
				    {
				    
				    if(ActualItemPostage.equals(ExpectedItemPostage))
				    {
				    	System.out.println("Both VOGPOPUP item Postage prices are same");
				    }
				    else
				    {
				    	String PriceType = "Postage";
				    	ErrorNumber = ErrorNumber+1;
				    	captureScreenshot();
				    	System.out.println("<-------- Both VOGPOPUP item Postage prices are different --------->"+ErrorNumber);
				    	System.out.println("Expected View Order Grid  Postage price : "+ExpectedItemPostage);
				    	System.out.println("Actual View Order Grid  Postage price : "+ActualItemPostage);
				    	RW_File.WriteResult(ExpectedItemPostage, ActualItemPostage, PageName, PriceType);
				    }
				    }  
				   // Order total starts here
				    String ActualOrderSubtotal=d.findElement(Property.VOGPOPUPSubTotal1).getText();
					if(ActualOrderSubtotal.equals(ExpectedVOGSubTotal))
					{
						System.out.println("Both order total  Subtotals in view order grid popup values are same");
					}
					else
					{
			    	String PriceType = "SubTotal";
			    	ErrorNumber = ErrorNumber+1;
			    	captureScreenshot();
			    	System.out.println("<-------- Both order total Subtotals in view order grid popup values are different --------->"+ErrorNumber);
			    	System.out.println("Expected View Order Grid  Subtotal price : "+ExpectedVOGSubTotal);
			    	System.out.println("Actual View Order Grid  Subtotal price : "+ActualOrderSubtotal);
			    	RW_File.WriteResult(ExpectedVOGSubTotal, ActualOrderSubtotal, PageName, PriceType);
					}
				    String ExpectedOrderAddonPrice = Config.Currency+Addons;
				    String ActualOrderAddonPrice =null;
				    if(Config.LayoutType.equalsIgnoreCase("Layout2")||Config.LayoutType.equalsIgnoreCase("Classic")){
				     ActualOrderAddonPrice = d.findElement(Property.VOGPOPUPAddOnPricel).getText();
				    }else{
				    	ActualOrderAddonPrice = d.findElement(Property.VOGPOPUPAddOnPrice1).getText();	
				    }
				    if(ActualOrderAddonPrice.equals(ExpectedOrderAddonPrice))
				    {
				    	//!System.out.println("Both VOGPOPUP order total Addon prices are same ");
				    }
				    else
				    {
				    	String PriceType = "Addon price";
				    	ErrorNumber = ErrorNumber+1;
				    	captureScreenshot();
				    	System.out.println("<-------- Both VOGPOPUP order total Addon prices are different --------->"+ErrorNumber);
				    	System.out.println("Expected User View Order Grid Popup Addon price : "+ExpectedOrderAddonPrice);
				    	System.out.println("Actual Users  View Order Grid Popup Addon price : "+ActualOrderAddonPrice);
				    	RW_File.WriteResult(ExpectedOrderAddonPrice, ActualOrderAddonPrice, PageName, PriceType);
				    }
				    String ExpectedOrderDiscount = null;
				    String ExpectedOrderLandingProdDiscount = null;
				    
				    if(LandingPageOption.equalsIgnoreCase("yes")){
				    	if(DiscountPercentage.equals("N"))
					    {
				    		double  DiscountCalculationFromSubTotal1= Double.valueOf(DiscountCalculationFromSubTotal).doubleValue();
							double  LDiscuntCalulationFromSubtotal1= Double.valueOf(LDiscuntCalulationFromSubtotal).doubleValue();
							double   Discount2=DiscountCalculationFromSubTotal1+LDiscuntCalulationFromSubtotal1;
							String   Discount3=""+Discount2;
							String Discount4 = Decimalsetting(Discount3, DecimalValue);
					    	ExpectedOrderDiscount= "-"+Config.Currency+Discount4;
					    	System.out.println("ExpectedOrderDiscount1");

					    }
					    else if(Discount.equals("0")||Discount.equals("0.0")||Discount.equals("0.00")||
					    		Discount.equals("0.000")||Discount.equals("0.0000"))
					    {
					    	double  DiscountCalculationFromSubTotal1= Double.valueOf(DiscountCalculationFromSubTotal).doubleValue();
							double  LDiscuntCalulationFromSubtotal1= Double.valueOf(LDiscuntCalulationFromSubtotal).doubleValue();
							double   Discount2=DiscountCalculationFromSubTotal1+LDiscuntCalulationFromSubtotal1;
							String   Discount3=""+Discount2;
							String Discount4 = Decimalsetting(Discount3, DecimalValue);
					    	ExpectedOrderDiscount= "-"+Config.Currency+Discount4;

					    }
					    else
					    {
					    	double  DiscountCalculationFromSubTotal1= Double.valueOf(DiscountCalculationFromSubTotal).doubleValue();
							double  LDiscuntCalulationFromSubtotal1= Double.valueOf(LDiscuntCalulationFromSubtotal).doubleValue();
							double   Discount2=DiscountCalculationFromSubTotal1+LDiscuntCalulationFromSubtotal1;
							String   Discount3=""+Discount2;
							String Discount4 = Decimalsetting(Discount3, DecimalValue);
					    	ExpectedOrderDiscount= "-"+Config.Currency+Discount4;

					    }
				    }else{
				    	if(DiscountPercentage.equals("N"))
					    {
					    	
					    	ExpectedOrderDiscount = "-"+Config.Currency+Discount;
					    	System.out.println("ExpectedOrderDiscount4");
					    }
					    else if(Discount.equals("0")||Discount.equals("0.0")||Discount.equals("0.00")||
					    		Discount.equals("0.000")||Discount.equals("0.0000"))
					    {
					    	ExpectedOrderDiscount = "-"+Config.Currency+DiscountCalculationFromSubTotal;
					    	System.out.println("ExpectedOrderDiscount5");
					    }
					    else
					    {
					    	ExpectedOrderDiscount = "-"+Config.Currency+DiscountCalculationFromSubTotal;	
					    	System.out.println("ExpectedOrderDiscount6");
					    	}
				    }
				    
				    String ExpectedOrderPromotonDiscount = null;
				    if(PromotionDiscountPercentage.equals("N"))
				    {
				    	ExpectedOrderPromotonDiscount = "-"+Config.Currency+PromotionCoupon;
				    }
				    else
				    {
				    	ExpectedOrderPromotonDiscount = "-"+Config.Currency+PromotionDiscountAfterSubtractingFromSubTotal;
				    }
				    if(EnablePromotionsORDiscounts.equals("ON"))
				    {  String ActualOrderDiscount=null;
				    	  if(Config.LayoutType.equalsIgnoreCase("Layout2")||Config.LayoutType.equalsIgnoreCase("Classic")){	
				     ActualOrderDiscount = d.findElement(Property.VOGPOPUPDiscountl).getText();
				    	  }else{
				     ActualOrderDiscount = d.findElement(Property.VOGPOPUPDiscount1).getText();  
				    	  }
				    if(ActualOrderDiscount.equals(ExpectedOrderDiscount))
				    {
				    	//!System.out.println("Both VOGPOPUP order Discount prices are same");
				    }
				    else
				    {
				    	String PriceType = "Discount";
				    	ErrorNumber = ErrorNumber+1;
				    	captureScreenshot();
				    	System.out.println("<-------- Both VOGPOPUP order  Discount prices are different --------->"+ErrorNumber);
				    	System.out.println("Expected User VOGPOPUP order  Discount price : "+ExpectedOrderDiscount);
				    	System.out.println("Actual Users VOGPOPUP order  Discount price : "+ActualOrderDiscount);
				    	RW_File.WriteResult(ExpectedOrderDiscount, ActualOrderDiscount, PageName, PriceType);
				    }
				    
				    String ActualOrderPromotonDiscount = null;
				    if(PromotionCoupon.equals("0") || PromotionCoupon.equals("0.00") || PromotionCoupon.equals("0.000") || PromotionCoupon.equals("0.0000"))
				    {
				    	
				    }
				    else
				    {
				    	if(EnablePromotionsORDiscounts.equals("ON"))
							if(SubTotal.equals("0") || SubTotal.equals("0.00") || SubTotal.equals("0.000") || SubTotal.equals("0.0000")){
								System.out.println("if subtotal is  zero promotion is not shown in  application in UserViewOrdersPage");
							}else{
								  if(Config.LayoutType.equalsIgnoreCase("Layout2")||Config.LayoutType.equalsIgnoreCase("Classic")){
							ActualOrderPromotonDiscount = d.findElement(Property.VOGPOPUPPromotionDiscountl).getText();
								  }else{
										ActualOrderPromotonDiscount = d.findElement(Property.VOGPOPUPPromotionDiscount).getText();
								  }
				    }}
				    
				    if(PromotionCoupon.equals("0") || PromotionCoupon.equals("0.00") || PromotionCoupon.equals("0.000") || PromotionCoupon.equals("0.0000"))
				    {/*  if(Config.LayoutType.equalsIgnoreCase("Layout2")){
						ActualOrderPromotonDiscount = d.findElement(Property.VOGPOPUPPromotionDiscountl).getText();
				    }else{
						ActualOrderPromotonDiscount = d.findElement(Property.VOGPOPUPPromotionDiscount).getText();

				    }
				    */}
				    else
				    {
				    if(SubTotal.equals("0") || SubTotal.equals("0.00") || SubTotal.equals("0.000") || SubTotal.equals("0.0000"))
				    {
					   System.out.println("if subtotal is  zero promotion value should be zero and not visible in View Orders ");
					}
				    else{
				    if(ActualOrderPromotonDiscount.equals(ExpectedOrderPromotonDiscount))
				    {
				    	//!System.out.println(" Both Overall promotion discount values are same");
				    }
				    else
				    {
				    	String PriceType = "Promotion Disocunt";
				    	ErrorNumber = ErrorNumber+1;
				    	captureScreenshot();
				    	System.out.println("<-------- Both VOGPOPUP order  Promotion Discount prices are different --------->"+ErrorNumber);
				    	System.out.println("Expected User VOGPOPUP order  Promotion Discount price : "+ExpectedOrderPromotonDiscount);
				    	System.out.println("Actual Users VOGPOPUP order  Promotion Discount price : "+ActualOrderPromotonDiscount);
				    	RW_File.WriteResult(ExpectedOrderPromotonDiscount, ActualOrderPromotonDiscount, PageName, PriceType);
				    }
				    }
				    }
				    }
				    String ActualOrderShippingPrice=null;
				    
					if((Orderelements.equalsIgnoreCase("YES")&& OrderelementsShippingAddress.equalsIgnoreCase("NO"))||(Orderelements.equalsIgnoreCase("YES")&& OrderelementsShippingMethod.equalsIgnoreCase("NO"))){}else{

				    ActualOrderShippingPrice= d.findElement(Property.VOGPOPUPShipping1).getText();
					   // String VOupdatedshippingprice = null;
					if(OrderType.equals("DynDownloadShipping")||OrderType.equals("DynShipTOMultipleLocations")||OrderType.equals("ShipToMyAddress")
								||OrderType.equals("StaticDownloadShipping")||OrderType.equals("StaticShipTOMultipleLocations")||
								OrderType.equals("StaticInventoryShipTOMultipleLocations")||OrderType.equals("DynDropShipment with List"))
					 {
					    if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
					    {
					    	VOupdatedshippingprice = d.findElement(Property.VOGPOPUPShipping1).getText();
					    	if(VOupdatedshippingprice.equals(ActualOrderShippingPrice))
					    	{
					    		System.out.println("Both VOGPOPUP order shipping price values are same");
					    	}
					    	else
							{
								ErrorNumber = ErrorNumber+1;
								captureScreenshot();
					        	String PriceType = "Shipping price";
						    	System.out.println("<-------- Both VOGPOPUP order Shipping prices are different --------->"+ErrorNumber);
						        System.out.println("Actual Shipping price value is: "+VOupdatedshippingprice);
					         	System.out.println("Expected Shipping price value is: "+ExpectedShippingHandilingPrice);
						        RW_File.WriteResult(ExpectedShippingHandilingPrice, VOupdatedshippingprice, PageName, PriceType);
							}
					    }
					    else if(OrderBase.equals("Item"))
						 {
					    	if((Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00")||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000")
							||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000") || Priceafterapplyingfulfillmentshippingmarkupfee.equals("0")))
					    	{
							
					    		if(ActualOrderShippingPrice.equals(ExpectedVOGPOPUPShippingPrice))
						        {
					    			System.out.println("Both VOGPOPUP order Shipping prices are same ");
						        }
					    		else
					    		{
								ErrorNumber = ErrorNumber+1;
								captureScreenshot();
					        	String PriceType = "Shipping price";
						    	System.out.println("<-------- Both VOGPOPUP order  Promotion Shipping prices are different 1--------->"+ErrorNumber);
						        System.out.println("Actual Shipping price value is: "+ActualOrderShippingPrice);
					         	System.out.println("Expected Shipping price value is: "+ExpectedVOGPOPUPShippingPrice);
						        RW_File.WriteResult(ExpectedVOGPOPUPShippingPrice, ActualOrderShippingPrice, PageName, PriceType);
					    		}
					    	}
						 }
					    else if(!(Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00")||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000")
							||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000") || Priceafterapplyingfulfillmentshippingmarkupfee.equals("0")))
					    {
					    	String Actualupdatedshippinghandilingfee = d.findElement(Property.VOGPOPUPShipping1).getText();
							if(ExpectedShippingHandilingPrice.equals(Actualupdatedshippinghandilingfee))
							{
								System.out.println("upadetd shipping price is updated");
							}
					        else
					        {
					    	String PriceType = "Shipping";
					    	ErrorNumber = ErrorNumber+1;
					    	captureScreenshot();
					    	System.out.println("<-------- Both VOGPOPUP order  Shipping prices are different2 --------->"+ErrorNumber);
					    	System.out.println("Expected User View Orders Shipping price : "+ExpectedShippingHandilingPrice);
					    	System.out.println("Actual Users view orders Shipping price : "+Actualupdatedshippinghandilingfee);
					    	RW_File.WriteResult(ExpectedShippingHandilingPrice, Actualupdatedshippinghandilingfee, PageName, PriceType);
					    	
					       }
					    }
					 } 
		}
					 String ExpectedorderPostage = null;
					    if(Postage.equals("0") || Postage.equals("0.00") || Postage.equals("0.000") || Postage.equals("0.0000"))
					    {
					    	ExpectedorderPostage = Config.Currency+Postage;
					    }
					    else
					    {
					    	ExpectedorderPostage = Config.Currency+Postage;
					    }
					    
					    String ActualorderPostage = null;
					    if(Postage.equals("0") || Postage.equals("0.00") || Postage.equals("0.000") || Postage.equals("0.0000"))
					    {
					    	 if(Config.LayoutType.equalsIgnoreCase("Layout2")||Config.LayoutType.equalsIgnoreCase("Classic")){
							    	ActualorderPostage = d.findElement(Property.VOGPOPUPPostagel).getText();
 
					    	 }else{
					    	ActualorderPostage = d.findElement(Property.VOGPOPUPPostage1).getText();
					    	 }
					    }
					    else
					    {
					    	if(Config.LayoutType.equalsIgnoreCase("Layout2")||Config.LayoutType.equalsIgnoreCase("Classic")){
						    	ActualorderPostage = d.findElement(Property.VOGPOPUPPostagel).getText();

				    	 }else{
				    	ActualorderPostage = d.findElement(Property.VOGPOPUPPostage1).getText();
				    	 }					    }
					   /* if(Postage.equals("0") || Postage.equals("0.00") || Postage.equals("0.000") || Postage.equals("0.0000"))
					    {
					    	
					    }
					    else
					    {*/
					    
					    if(ActualorderPostage.equals(ExpectedorderPostage))
					    {
					    	//!System.out.println("Both Postage prices are same");
					    }
					    else
					    {
					    	String PriceType = "Postage";
					    	ErrorNumber = ErrorNumber+1;
					    	captureScreenshot();
					    	System.out.println("<-------- Both VOGPOPUP order Postage prices are different --------->"+ErrorNumber);
					    	System.out.println("Expected User View Orders Postage price : "+ExpectedorderPostage);
					    	System.out.println("Actual Users view orders Postage price : "+ActualorderPostage);
					    	RW_File.WriteResult(ExpectedorderPostage, ActualorderPostage, PageName, PriceType);
					    }
					    String ExpectedOrderTaxValue = Config.Currency+PriceAfterCalculatingTax;
					    String ExpectedOrderTotalPrice = Config.Currency+Total;
					    String[] CalculateTaxConditions = CalculateTaxCondition.split("_");
						String   ActualOrderTaxValue=null;
						if(!CalculateTaxConditions[0].equals("---Select---") && CalculateTaxConditions[1].equals("ON"))
						{
							if(CalculateTaxConditions[0].equals("Vertex"))
							{
								ActualOrderTaxValue = d.findElement(Property.VOGPOPUPTaxValue).getText();
					   // System.out.println(PageName+" Tax Value : "+ActualOverAllTaxValue);
					    if(ActualOrderTaxValue.matches("\\$\\d{1,3}\\.\\d{2,4}"))
					    {
					    	//!System.out.println("Both VOGPOPUP order Tax values are same");
					    }
					    else
					    {
					    	String PriceType = "Tax";
					    	ErrorNumber = ErrorNumber+1;
					    	captureScreenshot();
					    	System.out.println("<------- Both VOGPOPUP order Tax prices are different0 --------->"+ErrorNumber);
					    	System.out.println("Expected User View Orders Tax price : "+ExpectedOrderTaxValue);
					    	System.out.println("Actual Users view orders Tax price : "+ActualOrderTaxValue);
					    	RW_File.WriteResult(ExpectedOrderTaxValue, ActualOrderTaxValue, PageName, PriceType);
					    }
							}
							else
							{
								 ActualOrderTaxValue = d.findElement(Property.VOGPOPUPTaxValue).getText();
								String VOUpdatedtax = null;
								if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
								{
									VOUpdatedtax = d.findElement(Property.VOGPOPUPTaxValue).getText();
								}
							    if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
							    {
								if(VOUpdatedtax.equals(ActualOrderTaxValue))
							    {
									//!System.out.println("Both VOGPOPUP order  Tax values are same");
							    }
							    else
							    {
							    	String PriceType = "Tax";
							    	ErrorNumber = ErrorNumber+1;
							    	captureScreenshot();
							    	System.out.println("<------ Both VOGPOPUP order  Tax prices are different1 --------->"+ErrorNumber);
							    	System.out.println("Expected User View Orders Tax price : "+ExpectedOrderTaxValue);
							    	System.out.println("Actual Users view orders Tax price : "+ActualOrderTaxValue);
							    	RW_File.WriteResult(ExpectedOrderTaxValue, ActualOrderTaxValue, PageName, PriceType);
							    }
							    }
							    else if(OrderBase.equals("Order")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
							    {
							    	
							    	String OrderbaseThirdPartyShippingPrice = d.findElement(Property.VOGPOPUPShipping1).getText();

							    	VOUpdatedtax = TaxValueWithThirdPartyShippingIsTaxable(OrderbaseThirdPartyShippingPrice, TotalPrice, PriceAfterApplyingCoupon,
							    								Tax, OrderAmountValue);

								if(VOUpdatedtax.equals(ActualOrderTaxValue))
							    {
									//!System.out.println("Both VOGPOPUP order Tax values are same");
							    }
							    else
							    {
							    	String PriceType = "Tax";
							    	ErrorNumber = ErrorNumber+1;
							    	captureScreenshot();
							    	System.out.println("<------ Both VOGPOPUP order Tax prices are different2 --------->"+ErrorNumber);
							    	System.out.println("Expected User View Orders Tax price : "+VOUpdatedtax);
							    	System.out.println("Actual Users view orders Tax price : "+ActualOrderTaxValue);
							    	RW_File.WriteResult(VOUpdatedtax, ActualOrderTaxValue, PageName, PriceType);
							    }
							    }
							    else
							    {
							    	if(ActualOrderTaxValue.equals(ExpectedOrderTaxValue))
								    {
							    		//!System.out.println("Both VOGPOPUP order Tax values are same");
								    }
								    else
								    {
								    	String PriceType = "Tax";
								    	ErrorNumber = ErrorNumber+1;
								    	captureScreenshot();
								    	System.out.println("<-------- Both VOGPOPUP order  Tax prices are different3 -------->"+ErrorNumber);
								    	System.out.println("Expected User View Orders Tax price : "+ExpectedOrderTaxValue);
								    	System.out.println("Actual Users view orders Tax price : "+ActualOverAllTaxValue);
								    	RW_File.WriteResult(ExpectedOrderTaxValue, ActualOrderTaxValue, PageName, PriceType);
								    }
							    }
							}
						}
						
						if(CalculateTaxConditions[0].equals("Vertex"))
						{
							if(CalculateTaxConditions[1].equals("ON"))
							{
								String ExpectedOrderTotalPrice1 =VertexTotalUserViewOrders(ExpectedOrderTotalPrice);	
								String ActualOrderTotalPrice = d.findElement(Property.VOGPOPUPTotal).getText();
								if(ActualOrderTotalPrice.equals(ExpectedOrderTotalPrice1))
								{
									//!System.out.println("Both Total prices are same");
								}
								else
								{
									String PriceType = "Total";
									ErrorNumber = ErrorNumber+1;
									captureScreenshot();
									System.out.println("<-------- Both VOGPOPUP order  Total prices are different -------->"+ErrorNumber);
									System.out.println("Expected User View Orders Total price : "+ExpectedOrderTotalPrice1);
									System.out.println("Actual Users view orders Total price : "+ActualOrderTotalPrice);
									RW_File.WriteResult(ExpectedOrderTotalPrice1, ActualOrderTotalPrice, PageName, PriceType);
								}
							}
							else
							{
								String ExpectedOrderTotalPrice1 =ExpectedOrderTotalPrice;	
								String ActualOrderTotalPrice = d.findElement(Property.VOGPOPUPTotal).getText();
								if(ActualOrderTotalPrice.equals(ExpectedOrderTotalPrice1))
								{
									//!System.out.println("Both Total prices are same");
								}
								else
								{
									String PriceType = "Total";
									ErrorNumber = ErrorNumber+1;
									captureScreenshot();
									System.out.println("<-------- Both VOGPOPUP order Total prices are different 0------->"+ErrorNumber);
									System.out.println("Expected User View Orders Total price : "+ExpectedOrderTotalPrice1);
									System.out.println("Actual Users view orders Total price : "+ActualOrderTotalPrice);
									RW_File.WriteResult(ExpectedOrderTotalPrice1, ActualOrderTotalPrice, PageName, PriceType);
								}
							}
						}
						else
						{
							String ActualOrderTotalPrice = d.findElement(Property.VOGPOPUPTotal).getText();
							String VOupdatedgrandtotal = null;
							String VOUpdateghandlinggrandtotal = null;
							if(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
						    		  userordershippingorhandlingfee.equals("0.0000"))
							{
							  if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
							  {
							   String OP3rdpartytax = d.findElement(Property.VOGPOPUPTotal).getText();
							   String OP3rdpartytax1 = OP3rdpartytax.substring(1,OP3rdpartytax.length());
							   String OP3rdpartyshippingprice =  d.findElement(Property.VOGPOPUPShipping1).getText();
							   String OP3rdpartyshippingprice1 = OP3rdpartyshippingprice.substring(1,OP3rdpartyshippingprice.length());
								if(IsShippingTaxable.equals("Yes"))
								{
									String UpdatedTax  = UpdatedtaxOCWithOutMarkup(SubTotal,PromotionCoupon,OrderAmountValue,Addons,
											DiscountCalculationFromSubTotal,OP3rdpartyshippingprice1,Tax); 
									if(OP3rdpartytax.equals(UpdatedTax))
									 {
										System.out.println("both upadted VOGPOPUP order  tax and displayed correctly.");
									 }
									else
									{
										ErrorNumber = ErrorNumber+1;
										System.out.println("tax is not correct");
										System.out.println("OP3rdpartytax :"+OP3rdpartytax);
										System.out.println("UpdatedTax :"+UpdatedTax);
									}
								}
							   VOupdatedgrandtotal  = OCgrandtotal(SubTotal,PriceAfterApplyingCoupon,OP3rdpartytax1,OP3rdpartyshippingprice1,
											Addons,DiscountCalculationFromSubTotal,OrderAmountValue, Total, IsShippingTaxable, Tax);
									
							   }
							}
							else
							{
								if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
								{
								String OP3rdpartytax = d.findElement(Property.VOGPOPUPTotal).getText();
								String OP3rdpartytax1 = OP3rdpartytax.substring(1,OP3rdpartytax.length());
								String OP3rdpartyshippingprice =  d.findElement(Property.VOGPOPUPShipping1).getText();
								String OP3rdpartyshippingprice1 = OP3rdpartyshippingprice.substring(1,OP3rdpartyshippingprice.length());
								String OPmarkupfee = d.findElement(Property.OPhandilingfee).getText();
								String OPmarkupfee1 = OPmarkupfee.substring(1,OPmarkupfee.length());
								if(IsShippingTaxable.equals("Yes"))
								{
									String UpdatedTax  = UpdatedtaxOCMarkup(SubTotal,PromotionCoupon,OrderAmountValue,Addons,
											DiscountCalculationFromSubTotal,OP3rdpartyshippingprice1,Tax,userordershippingorhandlingfee); 
									if(OP3rdpartytax.equals(UpdatedTax))
									 {
										System.out.println("both upadted VOGPOPUP order  tax and displayed correctly.1");
									 }
									else
									{
										ErrorNumber = ErrorNumber+1;
										System.out.println("tax is not correct");
										System.out.println("OP3rdpartytax :"+OP3rdpartytax);
										System.out.println("UpdatedTax :"+UpdatedTax);
									}
								}

								VOUpdateghandlinggrandtotal = Ocupdatedmarkupgrandtotal(SubTotal,PromotionCoupon,OPmarkupfee1,OP3rdpartytax1,OrderAmountValue,
										Addons,DiscountCalculationFromSubTotal,OP3rdpartyshippingprice1);
							     }
							}
							if(Weighttype.equals("--Select--")&&(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
						    		  userordershippingorhandlingfee.equals("0.0000")))
							{
						       if(ActualOrderTotalPrice.equals(ExpectedOrderTotalPrice))
						        {
						    	   //!System.out.println("Both VOGPOPUP order VOGPOPUP order  Total prices are same in user view orders1");
						        }
						       else
							    {
							    	String PriceType = "Total";
							    	ErrorNumber = ErrorNumber+1;
							    	captureScreenshot();
							    	System.out.println("<-------- Both VOGPOPUP order  Total prices are different 1--------->"+ErrorNumber);
							    	System.out.println("Expected User View Orders Total price : "+ExpectedOrderTotalPrice);
							    	System.out.println("Actual Users view orders Total price : "+ActualOrderTotalPrice);
							    	RW_File.WriteResult(ExpectedOrderTotalPrice, ActualOrderTotalPrice, PageName, PriceType);
							    }
							}
							else if(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
						    		  userordershippingorhandlingfee.equals("0.0000"))
							{
								if(OrderBase.equals("Item")&&(Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
								{
			                       if(VOupdatedgrandtotal.equals(ActualOrderTotalPrice))
						            {
			                    	   //!System.out.println("Both VOGPOPUP order  Total prices are same");
						            }
			                       else
			   				        {
			   				    	String PriceType = "Total";
			   				    	ErrorNumber = ErrorNumber+1;
			   				    	captureScreenshot();
			   				    	System.out.println("<---------- Both Promotion Total prices are different2 --------->"+ErrorNumber);
			   				    	System.out.println("Expected User View Orders Total price : "+VOupdatedgrandtotal);
			   				    	System.out.println("Actual Users view orders Total price : "+ActualOrderTotalPrice);
			   				    	RW_File.WriteResult(VOupdatedgrandtotal, ActualOrderTotalPrice, PageName, PriceType);
			   				       }
								}
							}
							else if(!(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
						    		  userordershippingorhandlingfee.equals("0.0000")))
							{
								if(OrderBase.equals("Item")&&(Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
								{
			                       if (VOUpdateghandlinggrandtotal.equals(ActualOrderTotalPrice))
						            {
			                    	   //!System.out.println("Both VOGPOPUP order Total prices are same user view orders3");
						            }
			                       else
			  				        {
			  				    	String PriceType = "Total";
			  				    	ErrorNumber = ErrorNumber+1;
			  				    	captureScreenshot();
			  				    	System.out.println("<------- Both VOGPOPUP order Total prices are different3 --------->"+ErrorNumber);
			  				    	System.out.println("Expected User View Orders Total price : "+VOupdatedgrandtotal);
			  				    	System.out.println("Actual Users view orders Total price : "+ActualOrderTotalPrice);
			  				    	RW_File.WriteResult(VOupdatedgrandtotal, ActualOrderTotalPrice, PageName, PriceType);
			  				       }
								}
								else
								{
								   if(ActualOrderTotalPrice.equals(ExpectedOrderTotalPrice))
								    {
									   //!System.out.println("Both VOGPOPUP order Total prices are same in user view orders1");
								    }
								   else
									{
									    	String PriceType = "Total";
									    	ErrorNumber = ErrorNumber+1;
									    	captureScreenshot();
									    	System.out.println("<------ Both PVOGPOPUP order  Total prices are different4 --------->"+ErrorNumber);
									    	System.out.println("Expected User View Orders Total price : "+ExpectedOrderTotalPrice);
									    	System.out.println("Actual Users view orders Total price : "+ActualOrderTotalPrice);
									    	RW_File.WriteResult(ExpectedOrderTotalPrice, ActualOrderTotalPrice, PageName, PriceType);
								   }
								}
							}
						}
						if (LandingPageOption.equalsIgnoreCase("Yes")) {
							 String ActualLandingPageProdItemPrice = d.findElement(Property.VOGPOPUPLandingProdItemPrice).getText();
							    if(ActualLandingPageProdItemPrice.equals(ExpectedLandingPageProductPP))
							    {
							    	System.out.println("View Order Grid Pop-Up Landing Item prices are same");
							    }
							    else
							    {
							    	String PriceType = "Item Price";
							    	ErrorNumber = ErrorNumber+1;
							    	captureScreenshot();
							    	System.out.println("<-------- Both View Order Grid Pop-Up Landing Item Prices are different --------->"+ErrorNumber);
							    	System.out.println("Expected View Order Grid Landing  Item Price  : "+ExpectedProdItemPrice);
							    	System.out.println("Actual Users View Order Grid Landing Item Price : "+ActualLandingPageProdItemPrice);
							    	RW_File.WriteResult(ExpectedLandingPageProductPP, ActualLandingPageProdItemPrice, PageName, PriceType);
							    }
								
								//String SubTotal4 = Decimalsetting(LandingPageSubtotal,DecimalValue);
							String ExpectedVOGPOPItemSubTotalLanding =Config.Currency + LandingPageSubtotal;
							System.out.println("ExpectedVOGPOPItemSubTotalLanding"+ExpectedVOGPOPItemSubTotalLanding);
						    String ActualSubtotalLanding=d.findElement(Property.VOGPOPUPSubTotalLanding).getText();
						    System.out.println("ExpectedVOGPOPItemSubTotalLanding"+ActualSubtotalLanding);		
						/*    int ActualSubtotalLanding2 = Double.valueOf(ActualSubtotalLanding).intValue();
							int ExpectedVOGPOPItemSubTotalLanding2 = Double.valueOf(ExpectedVOGPOPItemSubTotalLanding).intValue();*/
						    if(ActualSubtotalLanding.equalsIgnoreCase(ExpectedVOGPOPItemSubTotalLanding))
							{
								System.out.println("Both Landing item Subtotals in view order grid popup values are same");
							}
							else
							{
					    	String PriceType = "SubTotal";
					    	ErrorNumber = ErrorNumber+1;
					    	captureScreenshot();
					    	System.out.println("<-------- Both Landing Subtotals in view order grid popup values are different --------->"+ErrorNumber);
					    	System.out.println("Expected View Order Grid  Landing Subtotal price : "+ExpectedVOGPOPItemSubTotalLanding);
					    	System.out.println("Actual View Order Grid  Landing Subtotal price : "+ActualSubtotalLanding);
					    	RW_File.WriteResult(ExpectedVOGPOPItemSubTotalLanding, ActualSubtotalLanding, PageName, PriceType);
							}
						}
//						if (LandingPageOption.equalsIgnoreCase("Yes")) {
//							d.findElement(Property.VOGPOPUPClose).click();	
//						}
						 if(Config.LayoutType.equalsIgnoreCase("Layout2")||Config.LayoutType.equalsIgnoreCase("Classic")){
					d.findElement(Property.VOGPOPUPClosel).click();
				    }else{
						d.findElement(Property.VOGPOPUPClose).click();
				    }
		}
		catch (Exception e){
				ErrorNumber = ErrorNumber +1;
				captureScreenshot();
				e.printStackTrace();
	      }		
	}
	
	public static void UserViewOrdersPagePriceverification(String SubTotal, String Addons, String Discount, String PromotionCoupon,
			String ShippingPrice, String Postage, String PriceAfterCalculatingTax, String Total, String Quantity, String ItemPerPrice,
			String FlatRate, String DownloadPrice, String OrderType, String TestCase, String TestStep, 
			String Parameters, String PromotionDiscountPercentage, String PromotionDiscountAfterSubtractingFromSubTotal, 
			String DiscountPercentage, String DiscountCalculationFromSubTotal, String OrderBase, String OrderBaseShipping,
			String CalculateTaxCondition, String AppPageName, String EnablePromotionsORDiscounts,String Weighttype,
			String DiscountcalculationfromSubTotal,String Subtotal, String OrderAmountValue,
			String userordershippingorhandlingfee,String Priceafterapplyingfulfillmentshippingmarkupfee,String IsShippingTaxable,
			String Tax, String TotalPrice, String PriceAfterApplyingCoupon, String EnableZeroAmountOrder,
			String ShipAddSameAsBillAdd, String ShipAddSameAsBillAddSub,String IfShippingaddressIseditble,String LandingPageOption,String LandingPageProduct,
			String LandingPageProductPP,String LandingPageDiscount,String LandingPageDiscountValue,String SubtotalAfterPurlPrice,
			String LandingPageSubtotal,String DecimalValue,String DiscuntCalulationFromSubtotal,String Orderelements,String OrderelementsAddOns,String OrderelementsBillingAddress,String OrderelementsInventoryLocation,String OrderelementsInventoryNumber,
			String OrderelementsPaymentDetail,String OrderelementsShippingAddress,String OrderelementsShippingMethod,String OrderelementsSpecialInstructions,String OrderelementsCheckout,
			String OrderelementsJobTicket,String OrderelementsPrintPopup,String IsTaxExempt)
					throws InterruptedException {
		try{
			tec.AC40.Common.Wait.wait2Second();
			String PageName = AppPageName;
		    String ExpectedOverAllsubtotal=null;
			if(LandingPageOption.equalsIgnoreCase("yes")){
				double  Subtotal1= Double.valueOf(Subtotal).doubleValue();
				double  LandingPageSubtotal1= Double.valueOf(LandingPageSubtotal).doubleValue();
				double   SubTotal2=Subtotal1+LandingPageSubtotal1;
				String   SubTotal3=""+SubTotal2;
				String SubTotal4 = Decimalsetting(SubTotal3, DecimalValue);
				
		      ExpectedOverAllsubtotal = Config.Currency+SubTotal4;
			}else{
			      ExpectedOverAllsubtotal = Config.Currency+SubTotal;
			}
		    String ExpectedOverAllAddonPrice = Config.Currency+Addons;
		    String ExpectedShippingHandilingPrice = Config.Currency+Priceafterapplyingfulfillmentshippingmarkupfee;
		    
		    if(OrderelementsCheckout.equalsIgnoreCase("NO")||OrderelementsBillingAddress.equalsIgnoreCase("NO")){}else{
		    //Shipping Address same as Billing address data verification code
		 			if(OrderBase.equalsIgnoreCase("Order") && (OrderType.equals("DynDropShipment with List") ||
		 					OrderType.equals("DynDownloadShipping") || OrderType.equals("StaticDownloadShipping")
		 					|| OrderType.equals("ShipToMyAddress"))&& IfShippingaddressIseditble.equalsIgnoreCase("NO"))
		 		  	{
		 		  		
		 		  		//Verify Both Shipping and Billing address are same or not first Time
		 			String BillingAddressValue = d.findElement(Property.UserViewOrdersBillingAddress).getText();
		 			System.out.println(BillingAddressValue);
		 			String[] BillingAddressValue1 = BillingAddressValue.split("Show more details");
		 		//	String[] BillingAddressValue2=BillingAddressValue1[0].split("Show more details");	Billing Information	 			
		 			d.findElement(Property.UserViewOrdersShippingDetailsLink).click();
		 			tec.AC40.Common.Wait.wait5Second();
		 			String ShippingAddressValue = null;
		 			if(Config.LayoutType.equals("Layout1"))
		 			{
		 				ShippingAddressValue = d.findElement(Property.UserViewOrdersShippinAddressL1).getText();
		 			}
		 			else
		 			{
		 				ShippingAddressValue = d.findElement(Property.UserViewOrdersShippinAddress).getText();
		 			}
		 			String[] ShippingAddressValue1 = ShippingAddressValue.split("Shipping Address");
		 			String[] ShippingAddressValue2 = ShippingAddressValue1[1].split("Shipping Provider Name");
		 			//System.out.println("ShippingAddressValue :"+ShippingAddressValue);
		 			d.findElement(Property.UserViewOrdersShippingDetailsPopUpOK).click();
		 			tec.AC40.Common.Wait.wait5Second();
		 			
		 			ShipAddSameAsBillAddViewOrders(BillingAddressValue1[0],ShippingAddressValue2[0],PageName,
		 					ShipAddSameAsBillAdd, ShipAddSameAsBillAddSub);
		
		 		  	}
		    
		 			//Shipping Address same as Billing address data verification code
		 			if(OrderBase.equalsIgnoreCase("Order") && (OrderType.equals("DynDropShipment with List") ||
		 					OrderType.equals("DynDownloadShipping") || OrderType.equals("StaticDownloadShipping")
		 					|| OrderType.equals("ShipToMyAddress"))&& IfShippingaddressIseditble.equalsIgnoreCase("YES"))
		 		  	{
		 		  		
		 		  		//Verify Both Shipping and Billing address are same or not first Time
		 			String BillingAddressValue = d.findElement(Property.UserViewOrdersBillingAddress).getText();
		 			String[] BillingAddressValue1 = BillingAddressValue.split("Show more details");
		 			//System.out.println("BillingAddressValue :"+BillingAddressValue1[0]);
		 			
		 			d.findElement(Property.UserViewOrdersShippingDetailsLink).click();
		 			tec.AC40.Common.Wait.wait5Second();
		 			String ShippingAddressValue = null;
		 			if(Config.LayoutType.equals("Layout1"))
		 			{
		 				ShippingAddressValue = d.findElement(Property.UserViewOrdersShippinAddressL1).getText();
		 			}
		 			else
		 			{
		 				ShippingAddressValue = d.findElement(Property.UserViewOrdersShippinAddress).getText();
		 			}
		 			String[] ShippingAddressValue1 = ShippingAddressValue.split("Shipping Address");
		 			String[] ShippingAddressValue2 = ShippingAddressValue1[1].split("Shipping Provider Name");
		 			//System.out.println("ShippingAddressValue :"+ShippingAddressValue);
		 			d.findElement(Property.UserViewOrdersShippingDetailsPopUpOK).click();
		 			tec.AC40.Common.Wait.wait5Second();
		 			
		 			ShipAddNotSameAsBillAddViewOrders(BillingAddressValue1[0],ShippingAddressValue2[0],PageName,
		 					ShipAddSameAsBillAdd, ShipAddSameAsBillAddSub);
		
		 		  	}
		    }
		    
		    String BillingAddValue = null;
			 if(OrderelementsCheckout.equalsIgnoreCase("NO")||OrderelementsBillingAddress.equalsIgnoreCase("NO")){}else{

		    if(Config.LayoutType.equals("Layout1") || Config.LayoutType.equals("Layout2"))
		    {
		    	BillingAddValue = d.findElement(By.id("orderDetailsSection")).getText();
		    }
		    else
		    {
		    	BillingAddValue = d.findElement(By.xpath("//section[@id='orderDetailsSection']/article")).getText();
		    }
			//System.out.println(PageName+" BillingAddValue :"+BillingAddValue);
			BillingAddressVerificationOrderSummary(EnableZeroAmountOrder, BillingAddValue, PageName);
		    
			 }
		    String ExpectedOverAllDiscount = null;
		    if(LandingPageOption.equalsIgnoreCase("YES")){
		    	double  Discount1= Double.valueOf(Discount).doubleValue();
				double  LandingPageDiscountValue1= Double.valueOf(DiscuntCalulationFromSubtotal).doubleValue();
				double   Discount2=Discount1+LandingPageDiscountValue1;
				String discount3 = ""+Discount2;
				String discount4 = Decimalsetting(discount3, DecimalValue);
				   if(DiscountPercentage.equals("N"))
				    {
				    	ExpectedOverAllDiscount ="-"+Config.Currency+discount4;
				    }
				    else
				    {
				    	double  Discount11= Double.valueOf(DiscountCalculationFromSubTotal).doubleValue();
						double  LandingPageDiscountValue11= Double.valueOf(DiscuntCalulationFromSubtotal).doubleValue();
						double   Discount12=Discount11+LandingPageDiscountValue11;
						String discount13 = ""+Discount12;
						String discount14 = Decimalsetting(discount13, DecimalValue);
				    	ExpectedOverAllDiscount ="-"+Config.Currency+discount14;
				    }
		    }else{
		    if(DiscountPercentage.equals("N"))
		    {
		    	ExpectedOverAllDiscount ="-"+Config.Currency+Discount;
		    }
		    else
		    {
		    	ExpectedOverAllDiscount ="-"+Config.Currency+DiscountCalculationFromSubTotal;
		    }
		    }
		    String ExpectedOverAllPromotonDiscount = null;
		    if(PromotionDiscountPercentage.equals("N"))
		    {
		    	ExpectedOverAllPromotonDiscount = "-"+Config.Currency+PromotionCoupon;
		    }
		    else
		    {
		    	 ExpectedOverAllPromotonDiscount = "-"+Config.Currency+PromotionDiscountAfterSubtractingFromSubTotal;
		    }
		    String ExpectedOverAllShippingPrice = null;
		    if(OrderBase.equals("Item"))
		    {
		    	ExpectedOverAllShippingPrice = Config.Currency+ShippingPrice;
		    }
		    else
		    {
		    	ExpectedOverAllShippingPrice = Config.Currency+OrderBaseShipping;
		    }
		   
		    String ExpectedOverAllTaxValue = Config.Currency+PriceAfterCalculatingTax;
		    String ExpectedOverAllTotalPrice = Config.Currency+Total;
		   
		    String ExpectedIndProdQuantity = Quantity;
		    String ExpectedIndProdItemPrice = null;
		    String ExpectedLandingPageProductPP = null;
		    if(OrderType.equals("DynDownload") || OrderType.equals("StaticDownload"))
			{
		    	ExpectedIndProdItemPrice = Config.Currency+DownloadPrice;
		    	ExpectedLandingPageProductPP = Config.Currency+DownloadPrice;    
			}
			else if(OrderType.equals("DynShipTOMultipleLocations") || OrderType.equals("StaticShipTOMultipleLocations")
					|| OrderType.equals("StaticInventoryShipTOMultipleLocations"))
			{
				ExpectedIndProdItemPrice ="-";
				ExpectedLandingPageProductPP="-";
			}
			else
			{
				ExpectedIndProdItemPrice = Config.Currency+ItemPerPrice;
				ExpectedLandingPageProductPP = Config.Currency+LandingPageProductPP;
				
			}
		    // Item Total Verification
		      String ExpectedIndProdItemTotal=null;
		      String ActualIndProdItemTotal="";
		    if(OrderType.equals("DynShipTOMultipleLocations") || OrderType.equals("StaticShipTOMultipleLocations")
		    	     || OrderType.equals("StaticInventoryShipTOMultipleLocations"))
		    	   {
		    	    ExpectedIndProdItemTotal=Config.Currency+SubTotal;
		    	    ActualIndProdItemTotal = d.findElement(Property.VOIndProdItemTotal).getText();
		    	    if(ActualIndProdItemTotal.equals(ExpectedIndProdItemTotal))
		    	       {
		    	        //!System.out.println("Both Item total are same");
		    	       }
		    	       else
		    	       {
		    	        String PriceType = "Item total";
		    	        ErrorNumber = ErrorNumber+1;
		    	        captureScreenshot();
		    	        System.out.println("<-------- Both Promotion Item total are different --------->"+ErrorNumber);
		    	        System.out.println("Expected User View Orders Item total price : "+ExpectedIndProdItemTotal);
		    	        System.out.println("Actual Users view orders Item total price : "+ActualIndProdItemTotal);
		    	        RW_File.WriteResult(ExpectedIndProdItemTotal, ActualIndProdItemTotal, PageName, PriceType);
		    	       }

		    	   }
		    	   else
		    	   {
		    	    //System.out.println("enterd into else block and checking ActualIndProdItemTotal");
		    	    if(d.getPageSource().contains("Item Total")){
		    	         String PriceType = "Item total";
		    	         ErrorNumber = ErrorNumber+1;
		    	         captureScreenshot();
		    	         System.out.println("********Other Than Ship TO MultipleLocations Item Total available**");
		    	         RW_File.WriteResult(ExpectedIndProdItemTotal, ActualIndProdItemTotal, PageName, PriceType);
		    	        }
		    	    else{
		    	       //! System.out.println("item total not present");
		    	    }
		    	    
		    	   }
		    
		    String ExpectedIndProdAddonPrice = Config.Currency+Addons;
		    String ExpectedIndProdDiscount = null;
		    String ExpectedLandingProdDiscount = null;

		    if(DiscountPercentage.equals("N") && LandingPageDiscount.equals("N"))
			{
		    	 ExpectedIndProdDiscount = Config.Currency+Discount;
		    	 ExpectedLandingProdDiscount= Config.Currency+LandingPageDiscountValue;
		    }
		    else if(DiscountPercentage.equals("N") && LandingPageDiscount.equals("Y"))
			{
		    	ExpectedIndProdDiscount = Config.Currency+Discount;
		    	ExpectedLandingProdDiscount= LandingPageDiscountValue+Config.PercentageSymbol;
			}
			else if(DiscountPercentage.equals("Y") && LandingPageDiscount.equals("N"))
			{
				ExpectedIndProdDiscount = Discount+Config.PercentageSymbol;
		    	ExpectedLandingProdDiscount= Config.Currency+LandingPageDiscountValue;
			}
			else if(Discount.equals("0") || Discount.equals("0.0") || Discount.equals("0.00") ||
					Discount.equals("0.000") || Discount.equals("0.0000")||LandingPageDiscount.equals("0") ||
					LandingPageDiscount.equals("0.0") || LandingPageDiscount.equals("0.00") ||
					LandingPageDiscount.equals("0.000") || LandingPageDiscount.equals("0.0000"))
			{
		    	ExpectedIndProdDiscount = Config.Currency+Discount;
		   	    ExpectedLandingProdDiscount= Config.Currency+LandingPageDiscountValue;
		    }
		    else
		    {
		    	 ExpectedIndProdDiscount = Discount+Config.PercentageSymbol;
		    	 ExpectedLandingProdDiscount= LandingPageDiscountValue+Config.PercentageSymbol;
		    }
		    
		    
		    String ActualOverAllsubtotal = d.findElement(Property.VOoverallSubTotal).getText();
		    String UPdatedprintpagesubtotal = null;
			if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
			{
				//String OP3rdpartytax = d.findElement(Property.PrintTaxvalue).getText();
				//String OP3rdpartytax1 = OP3rdpartytax.substring(1,OP3rdpartytax.length());
				String OP3rdpartyshippingprice =  d.findElement(Property.VOoverallShppingPrice).getText();
			    String OP3rdpartyshippingprice1 = OP3rdpartyshippingprice.substring(1,OP3rdpartyshippingprice.length());
				UPdatedprintpagesubtotal  = viewsummaryshippingtotal(Subtotal,OP3rdpartyshippingprice1,Addons,DiscountcalculationfromSubTotal,OrderAmountValue,DecimalValue,Postage);
					
			}
			/*if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
			{
				if(UPdatedprintpagesubtotal.equals(ActualOverAllsubtotal))
				{
		    	//System.out.println("Both Subtotals are same");
				}
				else
				{
		    	String PriceType = "SubTotal";
		    	ErrorNumber = ErrorNumber+1;
		    	System.out.println("<-------- Both subtotal prices are different --------->"+ErrorNumber);
		    	System.out.println("ExpectedUserViewOrders Subtotal price : "+UPdatedprintpagesubtotal);
		    	System.out.println("Actual Users view orders Subtotal price : "+ActualOverAllsubtotal);
		    	RW_File.WriteResult(UPdatedprintpagesubtotal, ActualOverAllsubtotal, PageName, PriceType);
				}
			}*/
			
				if(ActualOverAllsubtotal.equals(ExpectedOverAllsubtotal))
				{
					//!System.out.println("Both Subtotals are same");
				}
				else
				{
		    	String PriceType = "SubTotal";
		    	ErrorNumber = ErrorNumber+1;
		    	captureScreenshot();
		    	System.out.println("<-------- Both subtotal prices are different --------->"+ErrorNumber);
		    	System.out.println("ExpectedUserViewOrders Subtotal price : "+ExpectedOverAllsubtotal);
		    	System.out.println("Actual Users view orders Subtotal price : "+ActualOverAllsubtotal);
		    	RW_File.WriteResult(ExpectedOverAllsubtotal, ActualOverAllsubtotal, PageName, PriceType);
				}
			
		    String ActualOverAllAddonPrice = d.findElement(Property.VOoverallAddOnPrice).getText();
		    if(ActualOverAllAddonPrice.equals(ExpectedOverAllAddonPrice))
		    {
		    	//!System.out.println("Both Addon prices are same ");
		    }
		    else
		    {
		    	String PriceType = "Addon price";
		    	ErrorNumber = ErrorNumber+1;
		    	captureScreenshot();
		    	System.out.println("<-------- Both Addon prices are different --------->"+ErrorNumber);
		    	System.out.println("Expected User View Orders Addon price : "+ExpectedOverAllAddonPrice);
		    	System.out.println("Actual Users view orders Addon price : "+ActualOverAllAddonPrice);
		    	RW_File.WriteResult(ExpectedOverAllAddonPrice, ActualOverAllAddonPrice, PageName, PriceType);
		    }
		    
		    if(EnablePromotionsORDiscounts.equals("ON"))
		    {
		    String ActualOverAllDiscount = d.findElement(Property.VOoverallDiscount).getText();
		    if(ActualOverAllDiscount.equals(ExpectedOverAllDiscount))
		    {
		    	//!System.out.println("Both Discount prices are same");
		    }
		    else
		    {
		    	String PriceType = "Discount";
		    	ErrorNumber = ErrorNumber+1;
		    	captureScreenshot();
		    	System.out.println("<-------- Both Discount prices are different --------->"+ErrorNumber);
		    	System.out.println("Expected User View Orders Discount price : "+ExpectedOverAllDiscount);
		    	System.out.println("Actual Users view orders Discount price : "+ActualOverAllDiscount);
		    	RW_File.WriteResult(ExpectedOverAllDiscount, ActualOverAllDiscount, PageName, PriceType);
		    }
		    
		    String ActualOverAllPromotonDiscount = null;
		    if(PromotionCoupon.equals("0") || PromotionCoupon.equals("0.00") || PromotionCoupon.equals("0.000") || PromotionCoupon.equals("0.0000"))
		    {
		    	
		    }
		    else
		    {
		    	if(EnablePromotionsORDiscounts.equals("ON"))
					if(Subtotal.equals("0") || Subtotal.equals("0.00") || Subtotal.equals("0.000") || Subtotal.equals("0.0000")){
						System.out.println("if subtotal is  zero promotion is not shown in  application in UserViewOrdersPage");
					}else{
		    	ActualOverAllPromotonDiscount = d.findElement(Property.VOoverallPromotionDiscount).getText();
		    }}
		    
		    if(PromotionCoupon.equals("0") || PromotionCoupon.equals("0.00") || PromotionCoupon.equals("0.000") || PromotionCoupon.equals("0.0000"))
		    {
		    	
		    }
		    else
		    {
		    if(Subtotal.equals("0") || Subtotal.equals("0.00") || Subtotal.equals("0.000") || Subtotal.equals("0.0000"))
		    {
			   System.out.println("if subtotal is  zero promotion value should be zero and not visible in View Orders ");
			}
		    else{
		    if(ActualOverAllPromotonDiscount.equals(ExpectedOverAllPromotonDiscount))
		    {
		    	//!System.out.println(" Both Overall promotion discount values are same");
		    }
		    else
		    {
		    	String PriceType = "Promotion Disocunt";
		    	ErrorNumber = ErrorNumber+1;
		    	captureScreenshot();
		    	System.out.println("<-------- Both Promotion Discount prices are different --------->"+ErrorNumber);
		    	System.out.println("Expected User View Orders Promotion Discount price : "+ExpectedOverAllPromotonDiscount);
		    	System.out.println("Actual Users view orders Promotion Discount price : "+ActualOverAllPromotonDiscount);
		    	RW_File.WriteResult(ExpectedOverAllPromotonDiscount, ActualOverAllPromotonDiscount, PageName, PriceType);
		    }}
		    }
		    }
		   /* else
		    {
		    	 String VOData1 = d.findElement(Property.VOData1).getText();
				   // System.out.println("VOData1 value "+VOData1);
				    if(VOData1.contains("Discount"))
				    {
				    	ErrorNumber = ErrorNumber+1;
				    	System.out.println("Both Actual and expected values are different");
				    }
				   
				    if(VOData1.contains("Promotion Discount"))
				    {
				    	System.out.println("Both Actual and expected values are different");
				    }
		    }*/
			if((Orderelements.equalsIgnoreCase("YES")&& OrderelementsShippingAddress.equalsIgnoreCase("NO"))||(Orderelements.equalsIgnoreCase("YES")&& OrderelementsShippingMethod.equalsIgnoreCase("NO"))){}else{

		    String ActualOverAllShippingPrice = d.findElement(Property.VOoverallShppingPrice).getText();
		   // String VOupdatedshippingprice = null;
		if(OrderType.equals("DynDownloadShipping")||OrderType.equals("DynShipTOMultipleLocations")||OrderType.equals("ShipToMyAddress")
					||OrderType.equals("StaticDownloadShipping")||OrderType.equals("StaticShipTOMultipleLocations")||
					OrderType.equals("StaticInventoryShipTOMultipleLocations")||OrderType.equals("DynDropShipment with List"))
		 {
		    if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
		    {
		    	VOupdatedshippingprice = d.findElement(Property.VOoverallShppingPrice).getText();
		    	if(VOupdatedshippingprice.equals(ActualOverAllShippingPrice))
		    	{
		    		//!System.out.println("Both Overall shipping price values are same");
		    	}
		    	else
				{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
		        	String PriceType = "Shipping price";
			    	System.out.println("<-------- Both Promotion Shipping prices are different --------->"+ErrorNumber);
			        System.out.println("Actual Shipping price value is: "+VOupdatedshippingprice);
		         	System.out.println("Expected Shipping price value is: "+ExpectedShippingHandilingPrice);
			        RW_File.WriteResult(ExpectedShippingHandilingPrice, VOupdatedshippingprice, PageName, PriceType);
				}
		    }
		    else if(OrderBase.equals("Item"))
			 {
		    	if((Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00")||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000")
				||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000") || Priceafterapplyingfulfillmentshippingmarkupfee.equals("0")))
		    	{
				
		    		if(ActualOverAllShippingPrice.equals(ExpectedOverAllShippingPrice))
			        {
		    			//!System.out.println("Both Shipping prices are same ");
			        }
		    		else
		    		{
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
		        	String PriceType = "Shipping price";
			    	System.out.println("<-------- Both Promotion Shipping prices are different --------->"+ErrorNumber);
			        System.out.println("Actual Shipping price value is: "+ActualOverAllShippingPrice);
		         	System.out.println("Expected Shipping price value is: "+ExpectedOverAllShippingPrice);
			        RW_File.WriteResult(ExpectedOverAllShippingPrice, ActualOverAllShippingPrice, PageName, PriceType);
		    		}
		    	}
			 }
		    else if(!(Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.00")||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.000")
				||Priceafterapplyingfulfillmentshippingmarkupfee.equals("0.0000") || Priceafterapplyingfulfillmentshippingmarkupfee.equals("0")))
		    {
		    	String Actualupdatedshippinghandilingfee = d.findElement(Property.VOoverallShppingPrice).getText();
				if(ExpectedShippingHandilingPrice.equals(Actualupdatedshippinghandilingfee))
				{
					//!System.out.println("upadetd shipping price is updated");
				}
		        else
		        {
		    	String PriceType = "Shipping";
		    	ErrorNumber = ErrorNumber+1;
		    	captureScreenshot();
		    	System.out.println("<-------- Both Promotion Shipping prices are different --------->"+ErrorNumber);
		    	System.out.println("Expected User View Orders Shipping price : "+ExpectedShippingHandilingPrice);
		    	System.out.println("Actual Users view orders Shipping price : "+Actualupdatedshippinghandilingfee);
		    	RW_File.WriteResult(ExpectedShippingHandilingPrice, Actualupdatedshippinghandilingfee, PageName, PriceType);
		    	
		       }
		    }
		 }}
		    String ExpectedOverAllPostage = null;
		    if(Postage.equals("0") || Postage.equals("0.00") || Postage.equals("0.000") || Postage.equals("0.0000"))
		    {
		    	
		    }
		    else
		    {
		    	ExpectedOverAllPostage = Config.Currency+Postage;
		    }
		    
		    String ActualOverAllPostage = null;
		    if(Postage.equals("0") || Postage.equals("0.00") || Postage.equals("0.000") || Postage.equals("0.0000"))
		    {
		    	
		    }
		    else
		    {
		    	ActualOverAllPostage = d.findElement(Property.VOoverallPostage).getText();
		    }
		    if(Postage.equals("0") || Postage.equals("0.00") || Postage.equals("0.000") || Postage.equals("0.0000"))
		    {
		    	
		    }
		    else
		    {
		    
		    if(ActualOverAllPostage.equals(ExpectedOverAllPostage))
		    {
		    	//!System.out.println("Both Postage prices are same");
		    }
		    else
		    {
		    	String PriceType = "Postage";
		    	ErrorNumber = ErrorNumber+1;
		    	captureScreenshot();
		    	System.out.println("<-------- Both Promotion Postage prices are different --------->"+ErrorNumber);
		    	System.out.println("Expected User View Orders Postage price : "+ExpectedOverAllPostage);
		    	System.out.println("Actual Users view orders Postage price : "+ActualOverAllPostage);
		    	RW_File.WriteResult(ExpectedOverAllPostage, ActualOverAllPostage, PageName, PriceType);
		    }
		    }
		    
		    String[] CalculateTaxConditions = CalculateTaxCondition.split("_");
		    if(IsTaxExempt.equalsIgnoreCase("yes")){}else{
			 if(OrderelementsCheckout.equalsIgnoreCase("NO")||OrderelementsBillingAddress.equalsIgnoreCase("NO")){
				 
			 }else{

			if(!CalculateTaxConditions[0].equals("---Select---") && CalculateTaxConditions[1].equals("ON"))
			{
				if(CalculateTaxConditions[0].equals("Vertex"))
				{
		    ActualOverAllTaxValue = d.findElement(Property.VOoverallTaxValue).getText();
		   // System.out.println(PageName+" Tax Value : "+ActualOverAllTaxValue);
		    if(ActualOverAllTaxValue.matches("\\$\\d{1,3}\\.\\d{2,4}"))
		    {
		    	//!System.out.println("Both Tax values are same");
		    }
		    else
		    {
		    	String PriceType = "Tax";
		    	ErrorNumber = ErrorNumber+1;
		    	captureScreenshot();
		    	System.out.println("<------- Both Promotion Tax prices are different --------->"+ErrorNumber);
		    	System.out.println("Expected User View Orders Tax price : "+ExpectedOverAllTaxValue);
		    	System.out.println("Actual Users view orders Tax price : "+ActualOverAllTaxValue);
		    	RW_File.WriteResult(ExpectedOverAllTaxValue, ActualOverAllTaxValue, PageName, PriceType);
		    }
				}
				else
				{
					String ActualOverAllTaxValue = d.findElement(Property.VOoverallTaxValue).getText();
					String VOUpdatedtax = null;
					if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
					{
						VOUpdatedtax = d.findElement(Property.VOoverallTaxValue).getText();
					}
				    if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
				    {
					if(VOUpdatedtax.equals(ActualOverAllTaxValue))
				    {
						//!System.out.println("Both Tax values are same");
				    }
				    else
				    {
				    	String PriceType = "Tax";
				    	ErrorNumber = ErrorNumber+1;
				    	captureScreenshot();
				    	System.out.println("<------ Both Promotion Tax prices are different --------->"+ErrorNumber);
				    	System.out.println("Expected User View Orders Tax price : "+ExpectedOverAllTaxValue);
				    	System.out.println("Actual Users view orders Tax price : "+ActualOverAllTaxValue);
				    	RW_File.WriteResult(ExpectedOverAllTaxValue, ActualOverAllTaxValue, PageName, PriceType);
				    }
				    }
				    else if(OrderBase.equals("Order")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
				    {
				    	
				    	String OrderbaseThirdPartyShippingPrice = d.findElement(Property.OCTotalShippingPrice).getText();

				    	VOUpdatedtax = TaxValueWithThirdPartyShippingIsTaxable(OrderbaseThirdPartyShippingPrice, TotalPrice, PriceAfterApplyingCoupon,
				    								Tax, OrderAmountValue);

					if(VOUpdatedtax.equals(ActualOverAllTaxValue))
				    {
						//!System.out.println("Both Tax values are same");
				    }
				    else
				    {
				    	String PriceType = "Tax";
				    	ErrorNumber = ErrorNumber+1;
				    	captureScreenshot();
				    	System.out.println("<------ Both Promotion Tax prices are different --------->"+ErrorNumber);
				    	System.out.println("Expected User View Orders Tax price : "+VOUpdatedtax);
				    	System.out.println("Actual Users view orders Tax price : "+ActualOverAllTaxValue);
				    	RW_File.WriteResult(VOUpdatedtax, ActualOverAllTaxValue, PageName, PriceType);
				    }
				    }
				    else
				    {
				    	if(ActualOverAllTaxValue.equals(ExpectedOverAllTaxValue))
					    {
				    		//!System.out.println("Both Tax values are same");
					    }
					    else
					    {
					    	String PriceType = "Tax";
					    	ErrorNumber = ErrorNumber+1;
					    	captureScreenshot();
					    	System.out.println("<-------- Both Promotion Tax prices are different -------->"+ErrorNumber);
					    	System.out.println("Expected User View Orders Tax price : "+ExpectedOverAllTaxValue);
					    	System.out.println("Actual Users view orders Tax price : "+ActualOverAllTaxValue);
					    	RW_File.WriteResult(ExpectedOverAllTaxValue, ActualOverAllTaxValue, PageName, PriceType);
					    }
				    }
				}
			}
			}}
			if(CalculateTaxConditions[0].equals("Vertex"))
			{
				if(CalculateTaxConditions[1].equals("ON"))
				{
					String ExpectedOverAllTotalPrice1 =VertexTotalUserViewOrders(ExpectedOverAllTotalPrice);	
					String ActualOverAllTotalPrice = d.findElement(Property.VOoverallTotalPrice).getText();
					if(ActualOverAllTotalPrice.equals(ExpectedOverAllTotalPrice1))
					{
						//!System.out.println("Both Total prices are same");
					}
					else
					{
						String PriceType = "Total";
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						System.out.println("<-------- Both Promotion Total prices are different -------->"+ErrorNumber);
						System.out.println("Expected User View Orders Total price : "+ExpectedOverAllTotalPrice);
						System.out.println("Actual Users view orders Total price : "+ActualOverAllTotalPrice);
						RW_File.WriteResult(ExpectedOverAllTotalPrice, ActualOverAllTotalPrice, PageName, PriceType);
					}
				}
				else
				{
					String ExpectedOverAllTotalPrice1 =ExpectedOverAllTotalPrice;	
					String ActualOverAllTotalPrice = d.findElement(Property.VOoverallTotalPrice).getText();
					if(ActualOverAllTotalPrice.equals(ExpectedOverAllTotalPrice1))
					{
						//!System.out.println("Both Total prices are same");
					}
					else
					{
						String PriceType = "Total";
						ErrorNumber = ErrorNumber+1;
						captureScreenshot();
						System.out.println("<-------- Both Promotion Total prices are different ------->"+ErrorNumber);
						System.out.println("Expected User View Orders Total price : "+ExpectedOverAllTotalPrice);
						System.out.println("Actual Users view orders Total price : "+ActualOverAllTotalPrice);
						RW_File.WriteResult(ExpectedOverAllTotalPrice, ActualOverAllTotalPrice, PageName, PriceType);
					}
				}
			}
			else
			{
				String ActualOverAllTotalPrice = d.findElement(Property.VOoverallTotalPrice).getText();
				String VOupdatedgrandtotal = null;
				String VOUpdateghandlinggrandtotal = null;
				if(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
			    		  userordershippingorhandlingfee.equals("0.0000"))
				{
				  if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
				  {
				   String OP3rdpartytax = d.findElement(Property.VOoverallTaxValue).getText();
				   String OP3rdpartytax1 = OP3rdpartytax.substring(1,OP3rdpartytax.length());
				   String OP3rdpartyshippingprice =  d.findElement(Property.VOoverallShppingPrice).getText();
				   String OP3rdpartyshippingprice1 = OP3rdpartyshippingprice.substring(1,OP3rdpartyshippingprice.length());
					if(IsShippingTaxable.equals("Yes"))
					{
						String UpdatedTax  = UpdatedtaxOCWithOutMarkup(Subtotal,PromotionCoupon,OrderAmountValue,Addons,
								DiscountcalculationfromSubTotal,OP3rdpartyshippingprice1,Tax); 
						if(OP3rdpartytax.equals(UpdatedTax))
						 {
							System.out.println("both upadted tax and displayed correctly.");
						 }
						else
						{
							ErrorNumber = ErrorNumber+1;
							System.out.println("tax is not correct");
							System.out.println("OP3rdpartytax :"+OP3rdpartytax);
							System.out.println("UpdatedTax :"+UpdatedTax);
						}
					}
				   VOupdatedgrandtotal  = OCgrandtotal(Subtotal,PriceAfterApplyingCoupon,OP3rdpartytax1,OP3rdpartyshippingprice1,
								Addons,DiscountcalculationfromSubTotal,OrderAmountValue, Total, IsShippingTaxable, Tax);
						
				   }
				}
				else
				{
					if(OrderBase.equals("Item")&& Weighttype.equals("KGS")|| Weighttype.equals("LBS"))
					{
					String OP3rdpartytax = d.findElement(Property.VOoverallTaxValue).getText();
					String OP3rdpartytax1 = OP3rdpartytax.substring(1,OP3rdpartytax.length());
					String OP3rdpartyshippingprice =  d.findElement(Property.VOoverallShppingPrice).getText();
					String OP3rdpartyshippingprice1 = OP3rdpartyshippingprice.substring(1,OP3rdpartyshippingprice.length());
					String OPmarkupfee = d.findElement(Property.OPhandilingfee).getText();
					String OPmarkupfee1 = OPmarkupfee.substring(1,OPmarkupfee.length());
					if(IsShippingTaxable.equals("Yes"))
					{
						String UpdatedTax  = UpdatedtaxOCMarkup(Subtotal,PromotionCoupon,OrderAmountValue,Addons,
								DiscountcalculationfromSubTotal,OP3rdpartyshippingprice1,Tax,userordershippingorhandlingfee); 
						if(OP3rdpartytax.equals(UpdatedTax))
						 {
							System.out.println("both upadted tax and displayed correctly.");
						 }
						else
						{
							ErrorNumber = ErrorNumber+1;
							System.out.println("tax is not correct");
							System.out.println("OP3rdpartytax :"+OP3rdpartytax);
							System.out.println("UpdatedTax :"+UpdatedTax);
						}
					}

					VOUpdateghandlinggrandtotal = Ocupdatedmarkupgrandtotal(Subtotal,PromotionCoupon,OPmarkupfee1,OP3rdpartytax1,OrderAmountValue,
							Addons,DiscountcalculationfromSubTotal,OP3rdpartyshippingprice1);
				     }
				}
				if(Weighttype.equals("--Select--")&&(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
			    		  userordershippingorhandlingfee.equals("0.0000")))
				{
			       if(ActualOverAllTotalPrice.equals(ExpectedOverAllTotalPrice))
			        {
			    	   //!System.out.println("Both Total prices are same in user view orders1");
			        }
			       else
				    {
				    	String PriceType = "Total";
				    	ErrorNumber = ErrorNumber+1;
				    	captureScreenshot();
				    	System.out.println("<-------- Both Promotion Total prices are different --------->"+ErrorNumber);
				    	System.out.println("Expected User View Orders Total price : "+ExpectedOverAllTotalPrice);
				    	System.out.println("Actual Users view orders Total price : "+ActualOverAllTotalPrice);
				    	RW_File.WriteResult(ExpectedOverAllTotalPrice, ActualOverAllTotalPrice, PageName, PriceType);
				    }
				}
				else if(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
			    		  userordershippingorhandlingfee.equals("0.0000"))
				{
					if(OrderBase.equals("Item")&&(Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
					{
                       if(VOupdatedgrandtotal.equals(ActualOverAllTotalPrice))
			            {
                    	   //!System.out.println("Both Total prices are same");
			            }
                       else
   				        {
   				    	String PriceType = "Total";
   				    	ErrorNumber = ErrorNumber+1;
   				    	captureScreenshot();
   				    	System.out.println("<---------- Both Promotion Total prices are different --------->"+ErrorNumber);
   				    	System.out.println("Expected User View Orders Total price : "+VOupdatedgrandtotal);
   				    	System.out.println("Actual Users view orders Total price : "+ActualOverAllTotalPrice);
   				    	RW_File.WriteResult(VOupdatedgrandtotal, ActualOverAllTotalPrice, PageName, PriceType);
   				       }
					}
				}
				else if(!(userordershippingorhandlingfee.equals("0.00")||userordershippingorhandlingfee.equals("0.000")||
			    		  userordershippingorhandlingfee.equals("0.0000")))
				{
					if(OrderBase.equals("Item")&&(Weighttype.equals("KGS")|| Weighttype.equals("LBS")))
					{
                       if (VOUpdateghandlinggrandtotal.equals(ActualOverAllTotalPrice))
			            {
                    	   //!System.out.println("Both Total prices are same user view orders3");
			            }
                       else
  				        {
  				    	String PriceType = "Total";
  				    	ErrorNumber = ErrorNumber+1;
  				    	captureScreenshot();
  				    	System.out.println("<------- Both Promotion Total prices are different --------->"+ErrorNumber);
  				    	System.out.println("Expected User View Orders Total price : "+VOupdatedgrandtotal);
  				    	System.out.println("Actual Users view orders Total price : "+ActualOverAllTotalPrice);
  				    	RW_File.WriteResult(VOupdatedgrandtotal, ActualOverAllTotalPrice, PageName, PriceType);
  				       }
					}
					else
					{
					   if(ActualOverAllTotalPrice.equals(ExpectedOverAllTotalPrice))
					    {
						   //!System.out.println("Both Total prices are same in user view orders1");
					    }
					   else
						{
						    	String PriceType = "Total";
						    	ErrorNumber = ErrorNumber+1;
						    	captureScreenshot();
						    	System.out.println("<------ Both Promotion Total prices are different --------->"+ErrorNumber);
						    	System.out.println("Expected User View Orders Total price : "+ExpectedOverAllTotalPrice);
						    	System.out.println("Actual Users view orders Total price : "+ActualOverAllTotalPrice);
						    	RW_File.WriteResult(ExpectedOverAllTotalPrice, ActualOverAllTotalPrice, PageName, PriceType);
					   }
					}
				}
			}
		    
		    String ActualIndProdQuantity1 = d.findElement(Property.VOIndProdQuantity).getText();
		    int ActualIndProdQuantity = Double.valueOf(ActualIndProdQuantity1).intValue();
		    int ExpectedIndProdQuantity1 = Double.valueOf(ExpectedIndProdQuantity).intValue();
		    if(ActualIndProdQuantity == ExpectedIndProdQuantity1)
		    {
		    	//!System.out.println("Both View orders page Quantitys are same");
		    }
		    else
		    {
		    	String PriceType = "Quantity";
		    	ErrorNumber = ErrorNumber+1;
		    	captureScreenshot();
		    	System.out.println("<-------- Both Promotion Quantity are different --------->"+ErrorNumber);
		    	System.out.println("Expected User View Orders Quantity price : "+ExpectedIndProdQuantity);
		    	System.out.println("Actual Users view orders Quantity price : "+ActualIndProdQuantity);
		    	RW_File.WriteResult(ExpectedIndProdQuantity, ActualIndProdQuantity1, PageName, PriceType);
		    }
		    
		    String ActualIndProdItemPrice = d.findElement(Property.VOIndProdItemPrice).getText();
		    if(ActualIndProdItemPrice.equals(ExpectedIndProdItemPrice))
		    {
		    	//!System.out.println("Both Item prices are same");
		    }
		    else
		    {
		    	String PriceType = "Item Price";
		    	ErrorNumber = ErrorNumber+1;
		    	captureScreenshot();
		    	System.out.println("<-------- Both Promotion Item Prices are different --------->"+ErrorNumber);
		    	System.out.println("Expected User View Orders Item Prices price : "+ExpectedLandingPageProductPP);
		    	System.out.println("Actual Users view orders Item Prices price : "+ActualIndProdItemPrice);
		    	RW_File.WriteResult(ExpectedLandingPageProductPP, ActualIndProdItemPrice, PageName, PriceType);
		    }
		
		    if (Addons.equals("0") || Addons.equals("0.00") || Addons.equals("0.000") || Addons.equals("0.0000")) {

		    }
		    else
		    {
		    String ActualIndProdAddonPrice = d.findElement(Property.VOIndProdAddonPrice).getText();
		    if(ActualIndProdAddonPrice.equals(ExpectedIndProdAddonPrice))
		    {
		    	//!System.out.println("Both Addon prices are same");
		    }
		    else
		    {
		    	String PriceType = "Addon Price";
		    	ErrorNumber = ErrorNumber+1;
		    	captureScreenshot();
		    	System.out.println("<-------- Both Promotion Addon Prices are different --------->"+ErrorNumber);
		    	System.out.println("Expected User View Orders Addon Prices price : "+ExpectedIndProdAddonPrice);
		    	System.out.println("Actual Users view orders Addon Prices price : "+ActualIndProdAddonPrice);
		    	RW_File.WriteResult(ExpectedIndProdAddonPrice, ActualIndProdAddonPrice, PageName, PriceType);
		    }
		    }
		    if(EnablePromotionsORDiscounts.equals("ON"))
		    {
		    String ActualIndProdDiscount = d.findElement(Property.VOIndProdDiscount).getText();
		    if(ActualIndProdDiscount.equals(ExpectedIndProdDiscount))
		    {
		    	//!System.out.println("Both Product Discount prices are same");
		    }
		    else
		    {
		    	String PriceType = "Product Disocunt Price";
		    	ErrorNumber = ErrorNumber+1;
		    	captureScreenshot();
		    	System.out.println("<-------- Both Promotion Product Disocunt Prices are different --------->"+ErrorNumber);
		    	System.out.println("Expected User View Orders Product Disocunt Prices price : "+ExpectedIndProdDiscount);
		    	System.out.println("Actual Users view orders Product Disocunt Prices price : "+ActualIndProdDiscount);
		    	RW_File.WriteResult(ExpectedIndProdDiscount, ActualIndProdDiscount,	PageName, PriceType);
		    }
		    }
		    /*else
		    {
		    	String VOData2 = d.findElement(Property.VOData2).getText();
		    	//System.out.println("VOData2 value "+VOData2);
		    	if(VOData2.contains("Discount"))
		    	{
		    		System.out.println("Both actual and expected values are different");
		    	}
		    }*/
		    
		    String ExpectedIndProdPostage = null;
		    if(Postage.equals("0") || Postage.equals("0.00") || Postage.equals("0.000") || Postage.equals("0.0000"))
		    {
		    	
		    }
		    else
		    {
		    	ExpectedIndProdPostage = Config.Currency+Postage;
		    }
		    String ActualIndProdPostage = null;
		    if(Postage.equals("0") || Postage.equals("0.00") || Postage.equals("0.000") || Postage.equals("0.0000"))
		    {
		    	
		    }
		    else
		    { 
		    ActualIndProdPostage = d.findElement(Property.VOIndProdlblPostagePrice).getText();
		    }
		    
		    if(Postage.equals("0") || Postage.equals("0.00") || Postage.equals("0.000") || Postage.equals("0.0000"))
		    {
		    	
		    }
		    else
		    {
		    
		    if(ActualIndProdPostage.equals(ExpectedIndProdPostage))
		    {
		    	//!System.out.println("Both postage prices are same");
		    }
		    else
		    {
		    	String PriceType = "Postage Price";
		    	ErrorNumber = ErrorNumber+1;
		    	captureScreenshot();
		    	System.out.println("<-------- Both Promotion Postage Prices are different --------->"+ErrorNumber);
		    	System.out.println("Expected User View Orders Postage Prices price : "+ExpectedIndProdPostage);
		    	System.out.println("Actual Users view orders Postage Prices price : "+ActualIndProdPostage);
		    	RW_File.WriteResult(ExpectedIndProdPostage, ActualIndProdPostage,  PageName, PriceType);
		    }
		    
		    }
		    if(LandingPageOption.equalsIgnoreCase("Yes")){
			    String ActuallandingItemPrice = d.findElement(Property.VOIndProdlandingItemPrice).getText();
			    if(ActuallandingItemPrice.equals(ExpectedLandingPageProductPP))
			    {
			    	System.out.println("Both Landing Item prices are same");
			    }
			    else
			    {
			    	String PriceType = "ItemPrice";
			    	ErrorNumber = ErrorNumber+1;
			    	captureScreenshot();
			    	System.out.println("<-------- Both Promotion Landing Item Prices are different --------->"+ErrorNumber);
			    	System.out.println("Expected User View Orders Landing Item Prices price : "+ExpectedLandingPageProductPP);
			    	System.out.println("Actual Users view orders Landing Item Prices price : "+ActuallandingItemPrice);
			    	RW_File.WriteResult(ExpectedLandingPageProductPP, ActuallandingItemPrice, PageName, PriceType);
			    }
			    if(EnablePromotionsORDiscounts.equals("ON"))
			    {
			    String ActualLandingProdDiscount = d.findElement(Property.VOIndProdlandingDiscount).getText();
			    if(ActualLandingProdDiscount.equals(ExpectedLandingProdDiscount))
			    {
			    System.out.println("Both Landing Product Discount prices are same");
			    }
			    else
			    {
			    	String PriceType = "Product Disocunt Price";
			    	ErrorNumber = ErrorNumber+1;
			    	captureScreenshot();
			    	System.out.println("<-------- Both Promotion Landing  Product Disocunt Prices are different --------->"+ErrorNumber);
			    	System.out.println("Expected User View Orders Landing Product Disocunt Prices price : "+ExpectedLandingProdDiscount);
			    	System.out.println("Actual Users view orders Landing Product Disocunt Prices price : "+ActualLandingProdDiscount);
			    	RW_File.WriteResult(ExpectedLandingProdDiscount, ActualLandingProdDiscount,	PageName, PriceType);
			    }
			    }
			    }
			tec.AC40.Common.Wait.wait2Second();
				}catch (Exception e){
					ErrorNumber = ErrorNumber+1;
					captureScreenshot();
					e.printStackTrace();
	      }
	}
	
	public static void OrderElementsSettings(String Orderelements,String OrderelementsAddOns,String OrderelementsBillingAddress,String OrderelementsInventoryLocation,String OrderelementsInventoryNumber,
			String OrderelementsPaymentDetail,String OrderelementsShippingAddress,String OrderelementsShippingMethod,String OrderelementsSpecialInstructions,String OrderelementsCheckout,
			String OrderelementsJobTicket,String OrderelementsPrintPopup) throws InterruptedException{
           try{
		 WebDriverWait wait = new WebDriverWait(d, Config.ElementWaitTime);
		 MouseAdjFunction();
		 FluentWait<WebDriver> waitfl = new FluentWait<WebDriver>(d);
		 waitfl.withTimeout(Config.ElementWaitTime, TimeUnit.SECONDS);
		 waitfl.pollingEvery(1, TimeUnit.SECONDS);
		 waitfl.ignoring(NoSuchElementException.class);
		 waitfl.ignoring(StaleElementReferenceException.class);
		 
		if(Config.LayoutType.equals("Classic"))
		{
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.MIscellaneousLink));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.MIscellaneousLink));
			//d.findElement(Property.CompanyLink).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.MIscellaneousLink);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();	
		d.findElement(Property.MIscellaneousLink).click();
		waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Orderelementsc));
		waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Orderelementsc));
		//d.findElement(Property.PricingIcon).isDisplayed();
		
		waitfl.until(new Function<WebDriver, WebElement>() 
				 {
				   public WebElement apply(WebDriver driver) {
				   return driver.findElement(Property.Orderelementsc);
				 }
				 });
		
		tec.AC40.Common.Wait.wait2Second();	
		d.findElement(Property.Orderelementsc).click();
		}
		else if(Config.LayoutType.equals("Layout1"))
		{
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.MIscellaneousLink1));
			//waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
			//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
			//d.findElement(Property.CompanyIconL1).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.MIscellaneousLink1);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();	
			d.findElement(Property.MIscellaneousLink1).click();
			//waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.PricingIconL1));
			//d.findElement(Property.PricingIconL1).isDisplayed();
			tec.AC40.Common.Wait.wait2Second();	
			d.findElement(Property.Orderelements1).click();
		}
		else if(Config.LayoutType.equals("Layout2"))
		{
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.MIscellaneousLinkl2));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.MIscellaneousLinkl2));
			//d.findElement(Property.CompanyIconL2).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.MIscellaneousLinkl2);
					 }
					 });
			
			tec.AC40.Common.Wait.wait2Second();	
			d.findElement(Property.MIscellaneousLinkl2).click();
			waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.Orderelementsl2));
			waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.Orderelementsl2));
			//d.findElement(Property.PricingIconL2).isDisplayed();
	  		
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.Orderelementsl2);
					 }
					 });
			tec.AC40.Common.Wait.wait2Second();	
			d.findElement(Property.Orderelementsl2).click();
		}
	     if(Orderelements.equalsIgnoreCase("YES")){
	    	 if(OrderelementsCheckout.equalsIgnoreCase("YES"))
	    	 {
	    		 Thread.sleep(4000);
	    		 System.out.println("Order element check box 1");
	    		 boolean addons=d.findElement(Property.OrderelementsAddOns).isSelected();
	    		 System.out.println("Order element check box 2");
	    		 boolean BillingAddress=d.findElement(Property.OrderelementBillingAddress).isSelected(); 
	    		 boolean InventoryLocation=d.findElement(Property.OrderelementsInventoryLocation).isSelected();
	    		 boolean InventoryNumber=d.findElement(Property.OrderelementsInventoryNumber).isSelected();
	    		 boolean PaymentDetail=d.findElement(Property.OrderelementsPaymentDetail).isSelected();
	    		 boolean ShippingAddress=d.findElement(Property.OrderelementsShippingAddress).isSelected();
	    		 boolean ShippingMethod=d.findElement(Property.OrderelementsShippingMethod).isSelected();
	    		 boolean SpecialInstructions=d.findElement(Property.OrderelementsSpecialInstructions).isSelected();
	    		 if(OrderelementsAddOns.equalsIgnoreCase("Yes")){
	    			if(OrderelementsAddOns.equalsIgnoreCase("YES")){
	    				if(addons==true){
	    					//System.out.println("******do nothing********");
	    				}else{
	    					d.findElement(Property.OrderelementsAddOns).click();
	    				}
	    	        }
	    			}else{
	    				 if(addons==false){
		    					//System.out.println("******do nothing********");
	  				}else{
 	 					d.findElement(Property.OrderelementsAddOns).click();
	  				} 
	    			}
	    		 if(OrderelementsBillingAddress.equalsIgnoreCase("Yes")){
		    			if(OrderelementsBillingAddress.equalsIgnoreCase("YES")){
		    				if(BillingAddress==true){
		    					//System.out.println("******do nothing********");
		    				}else{
		    					d.findElement(Property.OrderelementBillingAddress).click();
		    				}
		    	        }
		    			}else{
		    				 if(BillingAddress==false){
			    					//System.out.println("******do nothing********");
		  				}else{
	 	 					d.findElement(Property.OrderelementBillingAddress).click();
		  				} 
		    			}
	    		 if(OrderelementsInventoryLocation.equalsIgnoreCase("Yes")){
		    			if(OrderelementsInventoryLocation.equalsIgnoreCase("YES")){
		    				if(InventoryLocation==true){
		    					//System.out.println("******do nothing********");
		    				}else{
		    					d.findElement(Property.OrderelementsInventoryLocation).click();
		    				}
		    	        }
		    			}else{
		    				 if(InventoryLocation==false){
			    					//System.out.println("******do nothing********");
		  				}else{
	 	 					d.findElement(Property.OrderelementsInventoryLocation).click();
		  				} 
		    			}
	    		 if(OrderelementsInventoryNumber.equalsIgnoreCase("Yes")){
		    			if(OrderelementsInventoryNumber.equalsIgnoreCase("YES")){
		    				if(InventoryNumber==true){
		    					//System.out.println("******do nothing********");
		    				}else{
		    					d.findElement(Property.OrderelementsInventoryNumber).click();
		    				}
		    	        }
		    			}else{
		    				 if(InventoryNumber==false){
			    					//System.out.println("******do nothing********");
		  				}else{
	 	 					d.findElement(Property.OrderelementsInventoryNumber).click();
		  				} 
		    			}
	    		 if(OrderelementsPaymentDetail.equalsIgnoreCase("Yes")){
		    			if(OrderelementsPaymentDetail.equalsIgnoreCase("YES")){
		    				if(PaymentDetail==true){
		    					//System.out.println("******do nothing********");
		    				}else{
		    					d.findElement(Property.OrderelementsPaymentDetail).click();
		    				}
		    	        }
		    			}else{
		    				 if(PaymentDetail==false){
			    					//System.out.println("******do nothing********");
		  				}else{
	 	 					d.findElement(Property.OrderelementsPaymentDetail).click();
		  				} 
		    			}
	    		 if(OrderelementsShippingAddress.equalsIgnoreCase("Yes")){
		    			if(OrderelementsShippingAddress.equalsIgnoreCase("YES")){
		    				if(ShippingAddress==true){
		    					//System.out.println("******do nothing********");
		    				}else{
		    					d.findElement(Property.OrderelementsShippingAddress).click();
		    				}
		    	        }
		    			}else{
		    				 if(ShippingAddress==false){
			    					//System.out.println("******do nothing********");
		  				}else{
	 	 					d.findElement(Property.OrderelementsShippingAddress).click();
		  				} 
		    			}
	    		 if(OrderelementsShippingMethod.equalsIgnoreCase("Yes")){
		    			if(OrderelementsShippingMethod.equalsIgnoreCase("YES")){
		    				if(ShippingMethod==true){
		    					//System.out.println("******do nothing********");
		    				}else{
		    					d.findElement(Property.OrderelementsShippingMethod).click();
		    				}
		    	        }
		    			}else{
		    				 if(ShippingMethod==false){
			    					//System.out.println("******do nothing********");
		  				}else{
	 	 					d.findElement(Property.OrderelementsShippingMethod).click();
		  				} 
		    			}
	    		 if(OrderelementsSpecialInstructions.equalsIgnoreCase("Yes")){
		    			if(OrderelementsSpecialInstructions.equalsIgnoreCase("YES")){
		    				if(SpecialInstructions==true){
		    					//System.out.println("******do nothing********");
		    				}else{
		    					d.findElement(Property.OrderelementsSpecialInstructions).click();
		    				}
		    	        }
		    			}else{
		    				 if(SpecialInstructions==false){
			    					//System.out.println("******do nothing********");
		  				}else{
	 	 					d.findElement(Property.OrderelementsSpecialInstructions).click();
		  				} 
		    			}
	    	 }else{
	    		
	    	 }
             if(OrderelementsJobTicket.equalsIgnoreCase("YES")){

	    		 boolean addons=d.findElement(Property.OrderelementsAddOnsj).isSelected();
	    		 boolean BillingAddress=d.findElement(Property.OrderelementBillingAddressj).isSelected(); 
	    		 boolean InventoryLocation=d.findElement(Property.OrderelementsInventoryLocationj).isSelected();
	    		 boolean InventoryNumber=d.findElement(Property.OrderelementsInventoryNumberj).isSelected();
	    		 boolean PaymentDetail=d.findElement(Property.OrderelementsPaymentDetailj).isSelected();
	    		 boolean ShippingAddress=d.findElement(Property.OrderelementsShippingAddressj).isSelected();
	    		 boolean ShippingMethod=d.findElement(Property.OrderelementsShippingMethodj).isSelected();
	    		 boolean SpecialInstructions=d.findElement(Property.OrderelementsSpecialInstructionsj).isSelected();
	    		 if(OrderelementsAddOns.equalsIgnoreCase("Yes")){
	    			if(OrderelementsAddOns.equalsIgnoreCase("YES")){
	    				if(addons==true){
	    					//System.out.println("******do nothing********");
	    				}else{
	    					d.findElement(Property.OrderelementsAddOnsj).click();
	    				}
	    	        }
	    			}else{
	    				 if(addons==false){
		    					//System.out.println("******do nothing********");
	  				}else{
 	 					d.findElement(Property.OrderelementsAddOnsj).click();
	  				} 
	    			}
	    		 if(OrderelementsBillingAddress.equalsIgnoreCase("Yes")){
		    			if(OrderelementsBillingAddress.equalsIgnoreCase("YES")){
		    				if(BillingAddress==true){
		    					//System.out.println("******do nothing********");
		    				}else{
		    					d.findElement(Property.OrderelementBillingAddressj).click();
		    				}
		    	        }
		    			}else{
		    				 if(BillingAddress==false){
			    					//System.out.println("******do nothing********");
		  				}else{
	 	 					d.findElement(Property.OrderelementBillingAddressj).click();
		  				} 
		    			}
	    		 if(OrderelementsInventoryLocation.equalsIgnoreCase("Yes")){
		    			if(OrderelementsInventoryLocation.equalsIgnoreCase("YES")){
		    				if(InventoryLocation==true){
		    					//System.out.println("******do nothing********");
		    				}else{
		    					d.findElement(Property.OrderelementsInventoryLocationj).click();
		    				}
		    	        }
		    			}else{
		    				 if(InventoryLocation==false){
			    					//System.out.println("******do nothing********");
		  				}else{
	 	 					d.findElement(Property.OrderelementsInventoryLocationj).click();
		  				} 
		    			}
	    		 if(OrderelementsInventoryNumber.equalsIgnoreCase("Yes")){
		    			if(OrderelementsInventoryNumber.equalsIgnoreCase("YES")){
		    				if(InventoryNumber==true){
		    					//System.out.println("******do nothing********");
		    				}else{
		    					d.findElement(Property.OrderelementsInventoryNumberj).click();
		    				}
		    	        }
		    			}else{
		    				 if(InventoryNumber==false){
			    					//System.out.println("******do nothing********");
		  				}else{
	 	 					d.findElement(Property.OrderelementsInventoryNumberj).click();
		  				} 
		    			}
	    		 if(OrderelementsPaymentDetail.equalsIgnoreCase("Yes")){
		    			if(OrderelementsPaymentDetail.equalsIgnoreCase("YES")){
		    				if(PaymentDetail==true){
		    					//System.out.println("******do nothing********");
		    				}else{
		    					d.findElement(Property.OrderelementsPaymentDetailj).click();
		    				}
		    	        }
		    			}else{
		    				 if(PaymentDetail==false){
			    					//System.out.println("******do nothing********");
		  				}else{
	 	 					d.findElement(Property.OrderelementsPaymentDetailj).click();
		  				} 
		    			}
	    		 if(OrderelementsShippingAddress.equalsIgnoreCase("Yes")){
		    			if(OrderelementsShippingAddress.equalsIgnoreCase("YES")){
		    				if(ShippingAddress==true){
		    					//System.out.println("******do nothing********");
		    				}else{
		    					d.findElement(Property.OrderelementsShippingAddressj).click();
		    				}
		    	        }
		    			}else{
		    				 if(ShippingAddress==false){
			    					//System.out.println("******do nothing********");
		  				}else{
	 	 					d.findElement(Property.OrderelementsShippingAddressj).click();
		  				} 
		    			}
	    		 if(OrderelementsShippingMethod.equalsIgnoreCase("Yes")){
		    			if(OrderelementsShippingMethod.equalsIgnoreCase("YES")){
		    				if(ShippingMethod==true){
		    					//System.out.println("******do nothing********");
		    				}else{
		    					d.findElement(Property.OrderelementsShippingMethodj).click();
		    				}
		    	        }
		    			}else{
		    				 if(ShippingMethod==false){
			    					//System.out.println("******do nothing********");
		  				}else{
	 	 					d.findElement(Property.OrderelementsShippingMethodj).click();
		  				} 
		    			}
	    		 if(OrderelementsSpecialInstructions.equalsIgnoreCase("Yes")){
		    			if(OrderelementsSpecialInstructions.equalsIgnoreCase("YES")){
		    				if(SpecialInstructions==true){
		    					//System.out.println("******do nothing********");
		    				}else{
		    					d.findElement(Property.OrderelementsSpecialInstructionsj).click();
		    				}
		    	        }
		    			}else{
		    				 if(SpecialInstructions==false){
			    					//System.out.println("******do nothing********");
		  				}else{
	 	 					d.findElement(Property.OrderelementsSpecialInstructionsj).click();
		  				} 
		    			}
	    	 	 
	    	 }else{
	    		 
	    	 }
             if(OrderelementsPrintPopup.equalsIgnoreCase("YES")){

	    		 boolean addons=d.findElement(Property.OrderelementsAddOnsp).isSelected();
	    		 boolean BillingAddress=d.findElement(Property.OrderelementBillingAddressp).isSelected(); 
	    		 boolean InventoryLocation=d.findElement(Property.OrderelementsInventoryLocationp).isSelected();
	    		 boolean InventoryNumber=d.findElement(Property.OrderelementsInventoryNumberp).isSelected();
	    		 boolean PaymentDetail=d.findElement(Property.OrderelementsPaymentDetailp).isSelected();
	    		 boolean ShippingAddress=d.findElement(Property.OrderelementsShippingAddressp).isSelected();
	    		 boolean ShippingMethod=d.findElement(Property.OrderelementsShippingMethodp).isSelected();
	    		 boolean SpecialInstructions=d.findElement(Property.OrderelementsSpecialInstructionsp).isSelected();
	    		 if(OrderelementsAddOns.equalsIgnoreCase("Yes")){
	    			if(OrderelementsAddOns.equalsIgnoreCase("YES")){
	    				if(addons==true){
	    					//System.out.println("******do nothing********");
	    				}else{
	    					d.findElement(Property.OrderelementsAddOnsp).click();
	    				}
	    	        }
	    			}else{
	    				 if(addons==false){
		    					//System.out.println("******do nothing********");
	  				}else{
 	 					d.findElement(Property.OrderelementsAddOnsp).click();
	  				} 
	    			}
	    		 if(OrderelementsBillingAddress.equalsIgnoreCase("Yes")){
		    			if(OrderelementsBillingAddress.equalsIgnoreCase("YES")){
		    				if(BillingAddress==true){
		    					//System.out.println("******do nothing********");
		    				}else{
		    					d.findElement(Property.OrderelementBillingAddressp).click();
		    				}
		    	        }
		    			}else{
		    				 if(BillingAddress==false){
			    					//System.out.println("******do nothing********");
		  				}else{
	 	 					d.findElement(Property.OrderelementBillingAddressp).click();
		  				} 
		    			}
	    		 if(OrderelementsInventoryLocation.equalsIgnoreCase("Yes")){
		    			if(OrderelementsInventoryLocation.equalsIgnoreCase("YES")){
		    				if(InventoryLocation==true){
		    					//System.out.println("******do nothing********");
		    				}else{
		    					d.findElement(Property.OrderelementsInventoryLocationp).click();
		    				}
		    	        }
		    			}else{
		    				 if(InventoryLocation==false){
			    					//System.out.println("******do nothing********");
		  				}else{
	 	 					d.findElement(Property.OrderelementsInventoryLocationp).click();
		  				} 
		    			}
	    		 if(OrderelementsInventoryNumber.equalsIgnoreCase("Yes")){
		    			if(OrderelementsInventoryNumber.equalsIgnoreCase("YES")){
		    				if(InventoryNumber==true){
		    					//System.out.println("******do nothing********");
		    				}else{
		    					d.findElement(Property.OrderelementsInventoryNumberp).click();
		    				}
		    	        }
		    			}else{
		    				 if(InventoryNumber==false){
			    					//System.out.println("******do nothing********");
		  				}else{
	 	 					d.findElement(Property.OrderelementsInventoryNumberp).click();
		  				} 
		    			}
	    		 if(OrderelementsPaymentDetail.equalsIgnoreCase("Yes")){
		    			if(OrderelementsPaymentDetail.equalsIgnoreCase("YES")){
		    				if(PaymentDetail==true){
		    					//System.out.println("******do nothing********");
		    				}else{
		    					d.findElement(Property.OrderelementsPaymentDetailp).click();
		    				}
		    	        }
		    			}else{
		    				 if(PaymentDetail==false){
			    					//System.out.println("******do nothing********");
		  				}else{
	 	 					d.findElement(Property.OrderelementsPaymentDetailp).click();
		  				} 
		    			}
	    		 if(OrderelementsShippingAddress.equalsIgnoreCase("Yes")){
		    			if(OrderelementsShippingAddress.equalsIgnoreCase("YES")){
		    				if(ShippingAddress==true){
		    					//System.out.println("******do nothing********");
		    				}else{
		    					d.findElement(Property.OrderelementsShippingAddressp).click();
		    				}
		    	        }
		    			}else{
		    				 if(ShippingAddress==false){
			    					//System.out.println("******do nothing********");
		  				}else{
	 	 					d.findElement(Property.OrderelementsShippingAddressp).click();
		  				} 
		    			}
	    		 if(OrderelementsShippingMethod.equalsIgnoreCase("Yes")){
		    			if(OrderelementsShippingMethod.equalsIgnoreCase("YES")){
		    				if(ShippingMethod==true){
		    					//System.out.println("******do nothing********");
		    				}else{
		    					d.findElement(Property.OrderelementsShippingMethodp).click();
		    				}
		    	        }
		    			}else{
		    				 if(ShippingMethod==false){
			    					//System.out.println("******do nothing********");
		  				}else{
	 	 					d.findElement(Property.OrderelementsShippingMethodp).click();
		  				} 
		    			}
	    		 if(OrderelementsSpecialInstructions.equalsIgnoreCase("Yes")){
		    			if(OrderelementsSpecialInstructions.equalsIgnoreCase("YES")){
		    				if(SpecialInstructions==true){
		    					//System.out.println("******do nothing********");
		    				}else{
		    					d.findElement(Property.OrderelementsSpecialInstructionsp).click();
		    				}
		    	        }
		    			}else{
		    				 if(SpecialInstructions==false){
			    					//System.out.println("******do nothing********");
		  				}else{
	 	 					d.findElement(Property.OrderelementsSpecialInstructionsp).click();
		  				} 
		    			}
	    	  
	    	 }else{
	    		 
	    	 }
             
             tec.AC40.Common.Wait.wait2Second();
             d.findElement(Property.OrderelementsSaveChangs).click();
             waitfl.until(ExpectedConditions.elementToBeClickable(Property.OrderelementsSuccessMsg));
      	     
    	     waitfl.until(new Function<WebDriver, WebElement>() 
    				 {
    				   public WebElement apply(WebDriver driver) {
    				   return driver.findElement(Property.OrderelementsSuccessMsg);
    				 }
    				 });

    	     
    	     tec.AC40.Common.Wait.wait2Second();
	     }
		
		if(Config.LayoutType.equals("Classic"))
		{
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.OrderelementsBacktohome));
			//d.findElement(Property.CompanyLink).isDisplayed();
		
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.OrderelementsBacktohome);
					 }
					 });
			  d.findElement(Property.OrderelementsBacktohome).click();
			  tec.AC40.Common.Wait.wait2Second();	
		}
		else if(Config.LayoutType.equals("Layout1"))
		{
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.OrderelementsBacktohome));
			//waitfl.until(ExpectedConditions.presenceOfElementLocated(Property.CompanyIconL1));
			//waitfl.until(ExpectedConditions.visibilityOfElementLocated(Property.CompanyIconL1));
			//d.findElement(Property.CompanyIconL1).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.OrderelementsBacktohome);
					 }
					 });
			
			d.findElement(Property.OrderelementsBacktohome).click();
			  tec.AC40.Common.Wait.wait2Second();	
		}
		else if(Config.LayoutType.equals("Layout2"))
		{
			waitfl.until(ExpectedConditions.elementToBeClickable(Property.OrderelementsBacktohome));
			//d.findElement(Property.CompanyIconL2).isDisplayed();
			
			waitfl.until(new Function<WebDriver, WebElement>() 
					 {
					   public WebElement apply(WebDriver driver) {
					   return driver.findElement(Property.OrderelementsBacktohome);
					 }
					 });
			d.findElement(Property.OrderelementsBacktohome).click();
			  tec.AC40.Common.Wait.wait2Second();	
			
		}
}catch (Exception e){
	ErrorNumber = ErrorNumber+1;
	captureScreenshot();
	e.printStackTrace();
}
		
	}
	public boolean isElementPresent(By by) {
		try {
			d.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static void takeScreenShots(String fileName) throws IOException {

		File scrFile = ((TakesScreenshot) d).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")
				+ "\\screenshots\\" + fileName + ".png"));
	}

	public int noOfLinks(By link) {
		List<WebElement> alllinks = d.findElements(link);
		int links = alllinks.size();
		return links;

	}
	public void allLinks_Working() {
		List<WebElement> alllinks = d.findElements(Property.Links_Home);
		// List<WebElement> alllinks=d.findElements(By.tagName("a"));
		System.out.println("No of links :" + alllinks.size());
		String linkText[] = new String[alllinks.size()];
		int i = 0;
		for (WebElement link : alllinks) {
			linkText[i] = link.getText();
			System.out.println(linkText[i]);
			i++;
		}
		for (String t : linkText) {
			d.findElement(By.linkText(t)).click();
			String title = d.getTitle();
			if (title.equals("Error 404"))

			{
				System.out.println(" " + t + " Link: is not Working ");
				d.navigate().back();
			} else {
				System.out.println(" " + t + " Link: is Working fine");
				d.navigate().back();
			}
		}
	}
}