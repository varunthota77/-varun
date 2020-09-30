package test.selenium;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
//import jxl.Sheet;
//import jxl.Workbook;
import jxl.read.biff.BiffException;
public class DialsUsingCollections   {	
	public static  int startRow=1;
	public static  int EndRow=6;
	public  String AdminName="testadmintv";
	public  String AdminPwd="welcome";
	public static String temp="testadmintv";		
	public static String Parent_dialName,Parent_dialName2,child_dialName;
	public static String parent_dial1,display_label1, parent_dial2,display_label2,child_dial1,visible_criteria_text,visible_criteria_text_value,visible_criteria_operation,Visible_Criteria_Combo_Operator,visible_criteria_text2,visible_criteria_text_value2,visible_criteria_operation2;
	public static HashMap<String, String> map=new HashMap<String,String>(); 
	public static WebDriver driver = null;
	  public static DateFormat format =new java.text.SimpleDateFormat("_yyyy-MMM-dd_hh"); //_yyyy-MMM-dd_h-mm
	    public static Date date=new Date();
	    public static String Execution_Time =format.format(date);
	public static void main(String...strings) throws IOException, InterruptedException{
       File file =    new File(System.getProperty("user.dir")+"/TestData/"+"\\"+"loginData.xls");
	   FileInputStream inputStream = new FileInputStream(file);
	   Workbook Workbook = null;
	    Workbook = new HSSFWorkbook(inputStream);   
	    Sheet Sheet = Workbook.getSheet("Sheet1");
	    int rowCount = Sheet.getLastRowNum()-Sheet.getFirstRowNum();	 
	    for (int i = startRow; i <= EndRow; i++) {
	        Row row = Sheet.getRow(i);
	        for (int j = 0; j < row.getLastCellNum(); j++) {
	        	map.put(Sheet.getRow(0).getCell(j).getStringCellValue(), row.getCell(j).getStringCellValue());
	      //    System.out.print(row.getCell(j).getStringCellValue()+"|| ");        	
	        }
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
    		Thread.sleep(5000);
    		driver.findElement(By.id("username")).sendKeys("testadmina123");   		
    		driver.findElement(By.id("password")).sendKeys("welcome",Keys.ENTER);
    		Thread.sleep(15000);
    		System.out.println("Admin Login done");
    		driver.findElement(By.xpath("//img[contains(@src,'Content/themes/Layout1/images/Products_Icon.png')]")).click();
    		Thread.sleep(5000);    		
    		driver.findElement(By.xpath("//b[contains(text(),'Manage Products')]")).click();
    		Thread.sleep(5000);
       		driver.findElement(By.xpath("//*[@id='btnSearch']")).click();
    		driver.findElement(By.xpath("//*[@id='btnSearch']")).sendKeys("dynamic");
    		Thread.sleep(5000);
    		driver.findElement(By.linkText("Dials")).click();
    		Thread.sleep(45000);
    		driver.findElement(By.xpath("//a[contains(text(),'Product Info')]")).click();
    		String s=driver.findElement(By.xpath("//span[contains(text(),'ACTIVE')]")).getText();
    		//System.out.println(s);
    		if(s.equalsIgnoreCase("active"))
    		{
    			driver.findElement(By.cssSelector("span.km-switch-handle")).click();
    			Wait.wait2Second();
    			driver.findElement(By.id("popup_ok")).click();
    			Wait.wait5Second();
    			
    		}
    		driver.findElement(By.xpath("//a[contains(text(),'Dials')]")).click();
    		Wait.wait5Second();
    		driver.findElement(By.xpath("//*[@id='ProductDialGird']/div[2]/table/tbody/tr[1]/td[1]")).click();
    		parentDail();
    		parentDail2();
    		childDail();
    		Thread.sleep(7000);
    		driver.findElement(By.xpath("//a[contains(text(),'Product Info')]")).click();
    		Wait.wait5Second();
    		String str=driver.findElement(By.xpath("//span[contains(text(),'ACTIVE')]")).getText();
    		//System.out.println(s);
    		if(str.equalsIgnoreCase("inactive"))
    		{
    			driver.findElement(By.cssSelector("span.km-switch-handle")).click();
    			Wait.wait2Second();
    			driver.findElement(By.id("popup_ok")).click();
    			Wait.wait5Second();
    			
    		}
    		driver.findElement(By.id("Logout")).click();
    		System.out.println("Admin Logout done");
    		Wait.wait5Second();
    		driver.close();   /*Admin end*/
    		driver = new ChromeDriver();
    		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    		driver.get("http://192.168.1.121/acv4.1.0.0_responsive/");
            driver.manage().window().maximize();
    		Thread.sleep(5000);
    		driver.findElement(By.id("username")).sendKeys("testusera123");   		
    		driver.findElement(By.id("password")).sendKeys("welcome",Keys.ENTER);
    		Wait.wait10Second();
    		Wait.wait10Second();
    		driver.findElement(By.xpath("//a[contains(text(),'Dynamic product comp')]")).click();
    		Wait.wait10Second();
    		driver.findElement(By.xpath("//*[@id='txtOrderName']")).sendKeys("Dials Testing");
    		driver.findElement(By.xpath("//*[@id='txtOrderDescription']")).sendKeys("Dials Testing");
    		System.out.println(parent_dial1);
    		Thread.sleep(5000);
    		String pd=	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])[1]")).getText();
    	boolean dial1=	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])[1]")).isDisplayed();
    	  if(dial1==true)
  	    {
  	    	System.out.println( "parent dial is displayed "+pd);
  	    }
  	    else
  	    {
  	    	System.out.println( "parent dial is not displayed "+pd);
  	    }
    	  String pd2=	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]")).getText();
      	boolean dial2=	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]")).isDisplayed();
      	  if(dial2==true)
    	    {
    	    	System.out.println( "parent dial 2 is displayed "+pd2);
    	    }
    	    else
    	    {
    	    	System.out.println( "parent dial 2 is not displayed "+pd);
    	    }

if(pd.equalsIgnoreCase("6RTFEditor"))
{
	
}
if(pd.equalsIgnoreCase("CallTracking"))
{
  //	
}
if(pd.equalsIgnoreCase("CurrencyTextBox"))
{                                    
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])/following::input[1]")).click();
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])/following::input[1]")).sendKeys(visible_criteria_text_value);
    driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])/following::input[1]")).click();
}
if(pd.equalsIgnoreCase("Date Picker"))
{
	//
}
if(pd.equalsIgnoreCase("Date Selector"))
{
	//
}
if(pd.equalsIgnoreCase("Date Time Picker"))
{
	//
}
if(pd.equalsIgnoreCase("Dependent Dropdown"))
{
	//
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
	driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial1+"')]/following::span[1]")).click();
	driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial1+"')]/following::span[1]")).sendKeys(visible_criteria_text_value);
    driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])[1]")).click();
}
if(pd.equalsIgnoreCase("String Long TextBox"))
{
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])[1]/following::textarea[1]")).click();
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])[1]/following::textarea[1]")).sendKeys(visible_criteria_text_value);
    driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])[1]")).click();
}
if(pd.equalsIgnoreCase("String TextBox"))
{
	 driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])[1]/following::input[1]")).click();
	 driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])[1]/following::input[1]")).sendKeys(visible_criteria_text_value);
	 driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])[1]")).click();
}
if(pd.equalsIgnoreCase("Table Data"))
{
	
}
if(pd.equalsIgnoreCase("Time Picker"))
{
	//
}
if(pd.equalsIgnoreCase("WebSiteStringTextBox"))
{
	driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial1+"')]/following::input[1]")).click();
	 driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial1+"')]/following::input[1]")).sendKeys(visible_criteria_text_value);
	 driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial1+"')])[1]")).click();
}
if(pd.equalsIgnoreCase("Weight Picker"))
{
	//
}

//////


if(pd2.equalsIgnoreCase("6RTFEditor"))
{
	
}
if(pd2.equalsIgnoreCase("CallTracking"))
{
	
}
if(pd2.equalsIgnoreCase("CurrencyTextBox"))
{                                    
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])/following::input[1]")).click();
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])/following::input[1]")).sendKeys(visible_criteria_text_value2);
    driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])/following::input[1]")).click();
}
if(pd2.equalsIgnoreCase("Date Picker"))
{
	//
}
if(pd2.equalsIgnoreCase("Date Selector"))
{
	//
}
if(pd2.equalsIgnoreCase("Date Time Picker"))
{
	//
}
if(pd2.equalsIgnoreCase("Dependent Dropdown"))
{
	//
}
if(pd2.equalsIgnoreCase("DropDownList"))
{
	
}
if(pd2.equalsIgnoreCase("MultLine TextBox"))
{
	driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial2+"')]/following::textarea[1]")).click();
	driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial2+"')]/following::textarea[1]")).sendKeys(visible_criteria_text_value2);
    driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]")).click();	
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
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]/following::textarea[1]")).click();
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]/following::textarea[1]")).sendKeys(visible_criteria_text_value2);
    driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]")).click();
}
if(pd2.equalsIgnoreCase("SpellChecker String TextBox"))
{
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]/following::textarea[1]")).click();
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]/following::textarea[1]")).sendKeys(visible_criteria_text_value2);
    driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]")).click();
}
if(pd2.equalsIgnoreCase("States Dropdown"))
{

	driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial2+"')]/following::span[1]")).click();
	driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial2+"')]/following::span[1]")).sendKeys(visible_criteria_text_value2);
    driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]")).click();
}
if(pd2.equalsIgnoreCase("String Long TextBox"))
{
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]/following::textarea[1]")).click();
	driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]/following::textarea[1]")).sendKeys(visible_criteria_text_value2);
    driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]")).click();
}
if(pd2.equalsIgnoreCase("String TextBox"))
{
	 driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]/following::input[1]")).click();
	 driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]/following::input[1]")).sendKeys(visible_criteria_text_value2);
	 driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]")).click();
}
if(pd2.equalsIgnoreCase("Table Data"))
{
	
}
if(pd2.equalsIgnoreCase("Time Picker"))
{
	//
}
if(pd2.equalsIgnoreCase("WebSiteStringTextBox"))
{
	driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial2+"')]/following::input[1]")).click();
	 driver.findElement(By.xpath("//label[contains(text(),'"+parent_dial2+"')]/following::input[1]")).sendKeys(visible_criteria_text_value2);
	 driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial2+"')])[1]")).click();
}
if(pd2.equalsIgnoreCase("Weight Picker"))
{
	//
}
	  


	  //  System.out.println(visible_criteria_text_value);
	  //  driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial+"')])[1]/following::input[1]")).click();
	//    driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial+"')])[1]/following::input[1]")).sendKeys(visible_criteria_text_value);
	 //   driver.findElement(By.xpath("(//label[contains(text(),'"+parent_dial+"')])[1]")).click();
       Wait.wait5Second();
       driver.findElement(By.xpath("//span[contains(text(),'Dynamic product comp')]")).click();
	    String cd=	driver.findElement(By.xpath("(//label[contains(text(),'"+child_dial1+"')])[1]")).getText();
	    Wait.wait5Second();
		boolean cdial= driver.findElement(By.xpath("(//label[contains(text(),'"+child_dial1+"')])[1]")).isDisplayed();
		 if(cdial==true)
		    {
		    	System.out.println( "child dial is displayed "+ cd);
		    }
		    else
		    {
		    	if(driver.getPageSource().contains(child_dial1)){
		    		System.out.println("child dial is present");
		    		}else{
		    		System.out.println("child dial is not present");
		    		}
		    }
	    }
	    
	}
    	public static void parentDail() throws InterruptedException
    		{
    		
    		driver.findElement(By.xpath("//tr[7]/td[1]")).click();
    		Thread.sleep(3000);
    		driver.findElement(By.xpath("//label[contains(text(),'Dial UI Control')]/following::span[1]")).click();
    		Thread.sleep(3000);
    		//driver.findElement(By.xpath("//label[contains(text(),'Dial UI Control')]/following::span[1]")).sendKeys(parent_dial);
    		Keyboard kb = ((RemoteWebDriver) driver).getKeyboard();
    		kb.sendKeys(parent_dial1);
    		kb.pressKey(Keys.ENTER);
    		driver.findElement(By.xpath("//*[@id='displayLabel_UCField']")).clear();
    		driver.findElement(By.xpath("//*[@id='displayLabel_UCField']")).sendKeys(display_label1);
    		Thread.sleep(3000);
    		driver.findElement(By.xpath("//*[@id='btnSaveDialInfo']")).click();
    		 Parent_dialName=driver.findElement(By.xpath("//tr[7]/td[1]/following::td[1]")).getText();
    		 System.out.println(Parent_dialName);
    		 Thread.sleep(5000);
    		 driver.findElement(By.xpath("//*[@id='ProductDialGird']/div[2]/table/tbody/tr[6]/td[1]/a")).click();
	        }	
    	
    	public static void parentDail2() throws InterruptedException
		{
		
		driver.findElement(By.xpath("//tr[9]/td[1]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//label[contains(text(),'Dial UI Control')]/following::span[1]")).click();
		Thread.sleep(3000);
		//driver.findElement(By.xpath("//label[contains(text(),'Dial UI Control')]/following::span[1]")).sendKeys(parent_dial);
		Keyboard kb = ((RemoteWebDriver) driver).getKeyboard();
		kb.sendKeys(parent_dial2);
		kb.pressKey(Keys.ENTER);
		driver.findElement(By.xpath("//*[@id='displayLabel_UCField']")).clear();
		driver.findElement(By.xpath("//*[@id='displayLabel_UCField']")).sendKeys(display_label2);
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id='btnSaveDialInfo']")).click();
		 Parent_dialName2=driver.findElement(By.xpath("//tr[9]/td[1]/following::td[1]")).getText();
		 System.out.println(Parent_dialName2);
		 Thread.sleep(5000);
		 driver.findElement(By.xpath("//*[@id='ProductDialGird']/div[2]/table/tbody/tr[6]/td[1]/a")).click();
        }	
    	
    	
    	public static void childDail() throws InterruptedException
		{
    	Thread.sleep(5000);
		driver.findElement(By.xpath("//tr[11]/td[1]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//label[contains(text(),'Dial UI Control')]/following::span[1]")).click();
		Thread.sleep(3000);
		//driver.findElement(By.xpath("//label[contains(text(),'Dial UI Control')]/following::span[1]")).sendKeys(parent_dial);
		Keyboard kb = ((RemoteWebDriver) driver).getKeyboard();
		kb.sendKeys(child_dial1);
		kb.pressKey(Keys.ENTER);
		driver.findElement(By.xpath("//*[@id='displayLabel_UCField']")).clear();
		driver.findElement(By.xpath("//*[@id='displayLabel_UCField']")).sendKeys(child_dial1);
		Thread.sleep(5000);
		driver.findElement(By.xpath("(//label[contains(text(),'Visible Criteria Product Field')]/following::span[1])[1]")).click();
		kb.sendKeys(Parent_dialName);
		kb.pressKey(Keys.ENTER);
		Thread.sleep(5000);
		driver.findElement(By.xpath("(//label[contains(text(),'Visible Criteria Text')]/following::input[1])[1]")).click();
		driver.findElement(By.xpath("(//label[contains(text(),'Visible Criteria Text')]/following::input[1])[1]")).clear();
		kb.sendKeys(visible_criteria_text);
		Thread.sleep(5000);
		driver.findElement(By.xpath("(//*[@id='trVisibleCriteriaOperaration']/span/span/span/span[1])[1]")).click();
		kb.sendKeys(visible_criteria_operation);
		kb.pressKey(Keys.ENTER);
		Thread.sleep(5000);
		driver.findElement(By.xpath("(//label[contains(text(),'Visible Criteria Combo Operator')]/following::span[1])[1]")).click();
		kb.sendKeys(visible_criteria_operation);
		kb.pressKey(Keys.ENTER);
		Thread.sleep(5000);
		driver.findElement(By.xpath("(//label[contains(text(),'Visible Criteria Product Field1')]/following::span[1])[1]")).click();
		kb.sendKeys(Parent_dialName2);
		kb.pressKey(Keys.ENTER);
		Thread.sleep(5000);
		driver.findElement(By.xpath("(//label[contains(text(),'Visible Criteria Text1')]/following::input[1])[1]")).click();
		driver.findElement(By.xpath("(//label[contains(text(),'Visible Criteria Text1')]/following::input[1])[1]")).clear();
		kb.sendKeys(visible_criteria_text2);
		Thread.sleep(5000);
		driver.findElement(By.xpath("//label[contains(text(),'Visible Criteria Operation1')]/following::span[1]")).click();
		kb.sendKeys(visible_criteria_operation2);
		kb.pressKey(Keys.ENTER);
		Thread.sleep(5000);
		driver.findElement(By.xpath("//*[@id='btnSaveDialInfo']")).click();
		child_dialName=driver.findElement(By.xpath("//tr[7]/td[1]/following::td[1]")).getText();
        }    	
}

