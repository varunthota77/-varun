package tec.ACV5.suite;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.server.handler.SendKeys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.gargoylesoftware.htmlunit.javascript.host.Map;
import tec.ACV5.common.Wait;
import tec.ACV5.common.commonclass;
import tec.ACV5.config.config;
import tec.ACV5.config.property;
//import jxl.Sheet;
//import jxl.Workbook;
//import jxl.read.biff.BiffException;
public class orderFlow extends commonclass{	
	public static int ImageNumber = 0;
	public static int errorNumber=0;
	public static int passcount=0;
	public static int failcount=0;
	public static String SheetNameErrorLog = null;
	public static String pd,pd2,cd;
	public String AdminPwd="welcome";
	public static String temp="testadmintv";
	public static String productName="Dynamic Dial";
	public static String Parent_dialName,Parent_dialName2,child_dialName;
	public static String parent_dial1,display_label1, parent_dial2,display_label2,child_dial1,visible_criteria_text,visible_criteria_text_value,visible_criteria_operation,Visible_Criteria_Combo_Operator,visible_criteria_text2,visible_criteria_text_value2,visible_criteria_operation2;	
	public static HashMap<String, String> map=new HashMap<String,String>(); 
	public static boolean dial1;
	public static DateFormat format =new java.text.SimpleDateFormat("_yyyy-MMM-dd_hh");//_yyyy-MMM-dd_h-mm
    
	//   public static Date date=new Date();
	 //   public static String Execution_Time =format.format(date);
	public static void main(String...strings) throws IOException, InterruptedException{
		createDirectory();
		SheetNameErrorLog=commonclass.dateFormat.format(date);
	    if(config.IsConsoleErrorSave.equalsIgnoreCase("Yes"))
      	 {
      	 System.setErr(new PrintStream(new FileOutputStream(System.getProperty("user.dir")+"\\ErrorLog\\"+SheetNameErrorLog+".txt", true), true));
      	 System.setOut(new PrintStream(new FileOutputStream(System.getProperty("user.dir")+"\\ErrorLog\\"+SheetNameErrorLog+".txt", true), true));
      	 }
       File file = new File(System.getProperty("user.dir")+"/TestData/"+"\\"+"Data.xls");
	   FileInputStream inputStream = new FileInputStream(file);
	   Workbook Workbook = null;
	    Workbook = new HSSFWorkbook(inputStream);   
	    Sheet Sheet = Workbook.getSheet("Sheet1");
	    int rowCount = Sheet.getLastRowNum()-Sheet.getFirstRowNum();	 
	   if(config.SelectedRow!=null)
	    {
	    	if(config.SelectedRow.equals("")||config.SelectedRow.equals(" ")){	
	    	}
	    	else
	    	{
	    		int a=Integer.parseInt(config.SelectedRow);
		    	config.ExecutionStartRow=a;
		    	config.ExecutionEndRow=a;	
	    	}	
	    }
	    	    
	    for (int i = config.ExecutionStartRow; i <= config.ExecutionEndRow; i++) {
	    	
	        Row row = Sheet.getRow(i);
	        for (int j = 0; j < row.getLastCellNum(); j++) {
	        	map.put(Sheet.getRow(0).getCell(j).getStringCellValue(), row.getCell(j).getStringCellValue());
	      //    System.out.print(row.getCell(j).getStringCellValue()+"|| ");        	
	        }
	        try
		    {
	        parent_dial1=map.get("parent_dial1");
	        display_label1=map.get("display_label1");
	        parent_dial2=map.get("parent_dial2");
	        display_label2=map.get("display_label2");
	        child_dial1=map.get("child_dial1");
	        visible_criteria_text=map.get("visible_criteria_text");
	        visible_criteria_text_value=map.get("visible_criteria_text_value");
	        visible_criteria_operation=map.get("visible_criteria_operation");
	        Visible_Criteria_Combo_Operator=map.get("Visible_Criteria_Combo_Operator");
	        visible_criteria_text2=map.get("visible_criteria_text2");
	        visible_criteria_text_value2=map.get("visible_criteria_text_value2");
	        visible_criteria_operation2=map.get("visible_criteria_operation2");       
	        System.setProperty("webdriver.chrome.driver",
    		System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");	
	        
	      
 /*Admin hide*/ 	        
	        driver = new ChromeDriver();
	        System.out.println("****** ****** Test Case Number --> "+i+"******* *******");
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd_h-mm-ss_a");
			Date date = new Date();
			System.out.println(dateFormat.format(date));
    		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    		driver.get("http://192.168.1.121/acv4.1.0.0_responsive/");
            driver.manage().window().maximize();
            Wait.Fluentwait(property.UserName);
    		Wait.wait5Second();
    		driver.findElement(property.UserName).sendKeys(config.AdminNamel1);   		
    		driver.findElement(property.Password).sendKeys(config.AdminPwdl1);
    		driver.findElement(By.xpath("//*[@id='btnLogin']")).click();
    		Wait.Fluentwait(property.productTab);
    		Wait.wait10Second();
    		System.out.println("Admin Login done");
    		driver.findElement(property.productTab).click();
    		Wait.wait2Second();  		
    		driver.findElement(property.Manageproducts).click();
    		Wait.Fluentwait(property.productsearchbutton);
    		Wait.wait5Second();
       		driver.findElement(property.productsearchbutton).click();
    		driver.findElement(property.productsearchbutton).sendKeys(productName);
    		Wait.Fluentwait(property.dialslink);
    		Wait.wait25Second();
    		driver.findElement(property.dialslink).click();
    		Wait.Fluentwait(property.productinfo);
    		Wait.wait10Second();
    		driver.findElement(property.productinfo).click();
    		Wait.Fluentwait(property.productstatus);
    		String s=driver.findElement(property.productstatus).getText();
    		//System.out.println(s);
    		if(s.equalsIgnoreCase("active"))
    		{
    			
    			driver.findElement(property.onlinelink).click();
    			Wait.wait2Second();
    			driver.findElement(property.okpopup).click();
    			Wait.wait5Second();
    			
    		}
    		Wait.Fluentwait(property.dialstab);
    		driver.findElement(property.dialstab).click();
    		Wait.Fluentwait(property.closedial);
    		Wait.wait5Second();
    		driver.findElement(property.closedial).click();
    		parentDail();
    		parentDail2();
    		childDail();
    		personalizeWizard();
    		System.out.println("Dial settings done successfully");
    		Wait.Fluentwait(property.productinfo);
    		Wait.wait10Second();
    		driver.findElement(property.productinfo).click();
    		Wait.Fluentwait(property.productstatus);
    		Wait.wait10Second();
    		String str=driver.findElement(property.productstatus).getText();
    		//System.out.println(s);
    		if(str.equalsIgnoreCase("inactive"))
    		{
    			driver.findElement(By.cssSelector("span.km-switch-handle")).click();
    			Wait.wait2Second();
    			driver.findElement(By.id("popup_ok")).click();
    			Wait.wait5Second();	
    		}
    		Wait.Fluentwait(property.logout);
    		Wait.wait5Second();
    		driver.findElement(property.logout).click();
    		System.out.println("Admin Logout done");
    		Wait.Fluentwait(property.UserName);
    		Wait.wait5Second();        /*Admin end*/
    		//driver.close();   
    		//driver = new ChromeDriver();
    		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    		//driver.get("http://192.168.1.121/acv4.1.0.0_responsive/");
           // driver.manage().window().maximize();
    		//Thread.sleep(5000);
    		driver.findElement(property.UserName).sendKeys(config.UserNamel1);   		
    		driver.findElement(property.Password).sendKeys(config.UserPwdl1);
    		driver.findElement(By.xpath("//*[@id='btnLogin']")).click();
    		Wait.Fluentwait(property.productname);
    		Wait.wait5Second();
    		driver.findElement(property.productname).click();
    		Wait.Fluentwait(property.ordername);
    		Wait.wait10Second();
    		driver.findElement(property.ordername).sendKeys("Dials ordername");
    		driver.findElement(property.orderdesc).sendKeys("dials order description");
    		System.out.println(parent_dial1);
    		Wait.wait5Second();
    		 pd=	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])[1]")).getText();
    		try
    		{
    	 dial1=	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])[1]")).isDisplayed();
    		}
    		catch(Exception e)
    		{
    			if(dial1==false)
    			{
    				 dial1=	driver.findElement(By.xpath("//input[@data-value='"+parent_dial1+"']")).isDisplayed();
    			}
    		}
    	  if(dial1==true)
  	    {
  	    	System.out.println( "parent dial 1 is displayed --->"+pd);
  	    }
  	    else
  	    {
  	    	System.out.println( "parent dial 1 is not displayed --->"+pd);
  	    }
    	   pd2=	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]")).getText();
      	boolean dial2=	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]")).isDisplayed();
      	  if(dial2==true)
    	    {
    	    	System.out.println( "parent dial 2 is displayed --->"+pd2);
    	    }
    	    else
    	    {
    	    	System.out.println( "parent dial 2 is not displayed --->"+pd);
    	    }

if(pd.equalsIgnoreCase("6RTFEditor"))
{
	}
if(pd.equalsIgnoreCase("CallTracking"))
{
	}
if(pd.equalsIgnoreCase("CurrencyTextBox"))
{                                    
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])/following::input[1]")).click();
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])/following::input[1]")).sendKeys(visible_criteria_text_value);
    driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])/following::input[1]")).click();
}
if(pd.equalsIgnoreCase("Date Picker"))
{
	}
if(pd.equalsIgnoreCase("Date Selector"))
{
	}
if(pd.equalsIgnoreCase("Date Time Picker"))
{
	}
if(pd.equalsIgnoreCase("Dependent Dropdown"))
{
	
}
if(pd.equalsIgnoreCase("DropDownList"))
{
	
}
if(pd.equalsIgnoreCase("MultLine TextBox"))
{
	driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial1+"')]/following::textarea[1]")).click();
	driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial1+"')]/following::textarea[1]")).sendKeys(visible_criteria_text_value);
    driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])[1]")).click();
}
if(pd.equalsIgnoreCase("OptionCheckBoxList"))
{
	
}
if(pd.equalsIgnoreCase("OptionRadioList"))
{
	
}
if(pd.equalsIgnoreCase("QRCode"))
{
	
}
if(pd.equalsIgnoreCase("SpellChecker Long TextBox"))
{
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])[1]/following::textarea[1]")).click();
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])[1]/following::textarea[1]")).sendKeys(visible_criteria_text_value);
    driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])[1]")).click();
}
if(pd.equalsIgnoreCase("SpellChecker String TextBox"))
{
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])[1]/following::textarea[1]")).click();
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])[1]/following::textarea[1]")).sendKeys(visible_criteria_text_value);
    driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])[1]")).click();
}
if(pd.equalsIgnoreCase("States Dropdown"))
{
	driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial1+"')]/following::div[1]/span/span")).click();
	Wait.wait2Second();
	driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial1+"')]/following::div[1]/span/span")).sendKeys(visible_criteria_text_value);
   // driver.findElement(By.xpath("//label[contains(text(),'States Dropdown')]/following::div[1]/span/span")).click();
}
if(pd.equalsIgnoreCase("String Long TextBox"))
{
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])[1]/following::textarea[1]")).click();
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])[1]/following::textarea[1]")).sendKeys(visible_criteria_text_value);
    driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])[1]")).click();
}
if(pd.equalsIgnoreCase("String TextBox"))
{
	 driver.findElement(By.xpath("//input[@data-value='"+parent_dial1+"']")).click();
	 driver.findElement(By.xpath("//input[@data-value='"+parent_dial1+"']")).sendKeys(visible_criteria_text_value);
	 driver.findElement(By.xpath("//input[@data-value='"+parent_dial1+"']")).click();
		
}
if(pd.equalsIgnoreCase("Table Data"))
{
	
}
if(pd.equalsIgnoreCase("Time Picker"))
{
	
}
if(pd.equalsIgnoreCase("WebSiteStringTextBox"))
{
	driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial1+"')]/following::input[1]")).click();
	driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial1+"')]/following::input[1]")).sendKeys(visible_criteria_text_value);
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])[1]")).click();
}
if(pd.equalsIgnoreCase("Weight Picker"))
{
	
}

////


if(pd2.equalsIgnoreCase("6RTFEditor"))
{
	
}
if(pd2.equalsIgnoreCase("CallTracking"))
{
	
}
   if(pd2.equalsIgnoreCase("CurrencyTextBox"))
    {     
	   if(pd.equalsIgnoreCase(pd2))
	    {
	driver.findElement(By.xpath("((//label[contains(text(),'"+parent_dial2+"')])/following::input[1])[2]")).click();
	driver.findElement(By.xpath("((//label[contains(text(),'"+parent_dial2+"')])/following::input[1])[2]")).sendKeys(visible_criteria_text_value2);
	//driver.findElement(By.xpath("((//label[contains(text(),'"+parent_dial2+"')])/following::input[1])[2]")).click();		
	    }
	else
	{
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])/following::input[1]")).click();
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])/following::input[1]")).sendKeys(visible_criteria_text_value2);
  //  driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])/following::input[1]")).click();
	}
}
if(pd2.equalsIgnoreCase("Date Picker"))
{
	
}
if(pd2.equalsIgnoreCase("Date Selector"))
{
	
}
if(pd2.equalsIgnoreCase("Date Time Picker"))
{
	
}
if(pd2.equalsIgnoreCase("Dependent Dropdown"))
{
	
}
if(pd2.equalsIgnoreCase("DropDownList"))
{
	
}
if(pd2.equalsIgnoreCase("MultLine TextBox"))
{
	if(pd.equalsIgnoreCase(pd2))
	{
		driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')]/following::textarea[1])[2]")).click();
		driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')]/following::textarea[1])[2]")).sendKeys(visible_criteria_text_value2);
	  //  driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]")).click();	
	}
	else
	{
	driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial2+"')]/following::textarea[1]")).click();
	driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial2+"')]/following::textarea[1]")).sendKeys(visible_criteria_text_value2);
   // driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]")).click();	
	}
}
if(pd2.equalsIgnoreCase("OptionCheckBoxList"))
{
	
}
if(pd2.equalsIgnoreCase("OptionRadioList"))
{
	
}
if(pd2.equalsIgnoreCase("QRCode"))
{
	
}
if(pd2.equalsIgnoreCase("SpellChecker Long TextBox"))
{
	if(pd.equalsIgnoreCase(pd2))
	{
		
	}
	else
	{
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]/following::textarea[1]")).click();
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]/following::textarea[1]")).sendKeys(visible_criteria_text_value2);
   // driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]")).click();
    }
}	
if(pd2.equalsIgnoreCase("SpellChecker String TextBox"))
{
	if(pd.equalsIgnoreCase(pd2))
	{
		
	}
	else
	{
	driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial2+"')]/following::input[1]")).click();
	driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial2+"')]/following::input[1]")).sendKeys(visible_criteria_text_value2);
   // driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial2+"')]/following::input[1]")).click();
    }
}
if(pd2.equalsIgnoreCase("States Dropdown"))
{
	if(pd.equalsIgnoreCase(pd2))
	{
		
	}
	else
	{
		driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial2+"')]/following::div[1]/span/span")).click();
		Wait.wait2Second();
		driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial2+"')]/following::div[1]/span/span")).sendKeys(visible_criteria_text_value);
   // driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]")).click();
    }
}
if(pd2.equalsIgnoreCase("String Long TextBox"))
{
	if(pd.equalsIgnoreCase(pd2))
	{
	driver.findElement(By.xpath("((//label[contains(text(),'"+parent_dial2+"')])/following::textarea[1])[2]")).click();
	driver.findElement(By.xpath("((//label[contains(text(),'"+parent_dial2+"')])/following::textarea[1])[2]")).sendKeys(visible_criteria_text_value2);
	}
	else
	{
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]/following::textarea[1]")).click();
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]/following::textarea[1]")).sendKeys(visible_criteria_text_value2);
   // driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]")).click();
	}
}
if(pd2.equalsIgnoreCase("String TextBox"))
{
	if(pd.equalsIgnoreCase(pd2))
	{
		driver.findElement(By.xpath("(//input[@data-value='"+parent_dial2+"'])[2]")).click();
		driver.findElement(By.xpath("(//input[@data-value='"+parent_dial2+"'])[2]")).sendKeys(visible_criteria_text_value2);
		 
		
	//driver.findElement(By.xpath("((//label[contains(text(),'"+parent_dial2+"')])/following::input[1])[2]")).click();
	//driver.findElement(By.xpath("((//label[contains(text(),'"+parent_dial2+"')])/following::input[1])[2]")).sendKeys(visible_criteria_text_value2);
	}
	else
	{
		driver.findElement(By.xpath("//input[@data-value='"+parent_dial2+"']")).click();
		driver.findElement(By.xpath("//input[@data-value='"+parent_dial2+"']")).sendKeys(visible_criteria_text_value2);
		
	//driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]/following::input[1]")).click();
	//driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]/following::input[1]")).sendKeys(visible_criteria_text_value2);
	//driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]")).click();
    }
}
if(pd2.equalsIgnoreCase("Table Data"))
{
	
}
if(pd2.equalsIgnoreCase("Time Picker"))
{
	
}
if(pd2.equalsIgnoreCase("WebSiteStringTextBox"))
{
	if(pd.equalsIgnoreCase(pd2))
	{
		
	}
	else
	{
	driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial2+"')]/following::input[1]")).click();
	driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial2+"')]/following::input[1]")).sendKeys(visible_criteria_text_value2);
	//driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]")).click();
    }
}
if(pd2.equalsIgnoreCase("Weight Picker"))
{
	
}
      //  System.out.println(visible_criteria_text_value);
	  //  driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial+"')])[1]/following::input[1]")).click();
	  //    driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial+"')])[1]/following::input[1]")).sendKeys(visible_criteria_text_value);
	  //   driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial+"')])[1]")).click();
       Wait.wait5Second();
       driver.findElement(By.xpath("//span[contains(text(),'"+productName+"')]")).click();     
       if(pd.equalsIgnoreCase(child_dial1) && pd2.equalsIgnoreCase(child_dial1))
       {
    	   cd=driver.findElement(By.xpath("(//*[text()='"+child_dial1+"'])[3]")).getText();
       }
       else if(pd.equalsIgnoreCase(child_dial1) || pd2.equalsIgnoreCase(child_dial1))
       {
    	   cd=driver.findElement(By.xpath("(//*[text()='"+child_dial1+"'])[2]")).getText();
       }
       else
       {
	     cd=driver.findElement(By.xpath("(//*[text()='"+child_dial1+"'])[1]")).getText();
       }
	    Wait.wait5Second();
		boolean cdial= driver.findElement(By.xpath("(//label[contains(text(),'"+child_dial1+"')])[1]")).isDisplayed();
		 if(cdial==true)
		    {
		    	System.out.println( "child dial is displayed --->"+ cd);
		    }
		    else
		    {
		    		System.out.println("child dial is not displayed --->"+ cd);
		    		errorNumber++;
		    		}
		 
		    }
		 catch(Exception e)
		    {
		    	errorNumber++;
		    	e.printStackTrace();
		    	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			    FileUtils.copyFile(scrFile, new File("D:\\AccuSelenium\\Ram\\ACV5_Dials\\screenshots\\"+commonclass.foldername+"\\TC_"+i+".png"));
		    }
		    if(errorNumber==0)
		    {
		    	System.out.println("Expected Dials are:{"+parent_dial1+","+parent_dial2+","+child_dial1+"}");
		        System.out.println("Actual Dials are:{"+pd+","+pd2+","+cd+"}");
		        if(parent_dial1.equalsIgnoreCase(pd) && parent_dial2.equalsIgnoreCase(pd2) && child_dial1.equalsIgnoreCase(cd))
		        {
		    	System.out.println("Expected and Actual results are same");
		    	System.out.println("*****PASS*****");
		    	passcount++;
		    	driver.close();	
		        }
		        else
		        {
		        	System.out.println("Expected and Actual results are different");
			    	System.out.println("*****FAIL*****");
		        }
		    	
		    }
		    else
		    {
		    	System.out.println("*****FAIL*****");
		    	failcount++;
		    }
		    errorNumber=0;
		    System.out.println("_____________________________________________________________________________");
	    } 
	
    	
	    	
	    int TCcount=config.ExecutionEndRow-config.ExecutionStartRow;
	    System.out.println("***TOTAL TC's Executed= "+(TCcount+1));
	    System.out.println("***TOTAL TC's PASS= "+passcount);
	    System.out.println("***TOTAL TC's FAIL= "+failcount);
	   // fileCreate();
	   }
	
}

