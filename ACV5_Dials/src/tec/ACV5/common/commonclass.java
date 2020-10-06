package tec.ACV5.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import tec.ACV5.config.config;
import tec.ACV5.suite.orderFlow;

public class commonclass {
	public static WebDriver driver ;
	public static File myObj;
	public static int error=0;
	protected static DateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd_h-mm-ss_a");
	protected static Date date = new Date();
	public static String foldername;
	public static void fileCreate()
	{
		 
	try { 
	       myObj = new File("D:\\AccuSelenium\\Ram\\ACV5_Dials\\Errorlog"+dateFormat.format(date)+".txt"); 
	      if (myObj.createNewFile()) { 
	        System.out.println("File created: " + myObj.getName()); 
	      } else { 
	        System.out.println("File already exists."); 
	      } 
	    } catch (IOException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace(); 
	    } 
	    write();
	  } 
	  
 
	  public static void fileWriter()
	  {
		  try{
			  FileWriter writer=new FileWriter("D:\\varun\\filename.txt");
			  writer.write("Files in Java might be tricky, but it is fun enough!");
		      writer.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		  }
		 
	  }
	  public static void takeScreenShots(String fileName) throws IOException {

			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")
					+ "\\screenshots\\" + orderFlow.ImageNumber + ".png"));
		}
	  public static void captureScreenshot() throws InterruptedException {
			try{
				orderFlow.ImageNumber = orderFlow.ImageNumber +1;
				if(config.TakeScreenShot.equalsIgnoreCase("Yes"))
				System.out.println("entered to the method capturescreenshot");
				{
					// capture screen shot
					//!System.out.println("Enter in to screen shot method");
					File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				    FileUtils.copyFile(scrFile, new File("D:\\AccuSelenium\\Ram\\ACV5_Dials\\screenshots\\varun.png"));
				}
				
			}catch (Exception e){
				// take screen shots
		    	  e.printStackTrace();
		      }		
		}
	  public static void write()
	  {
	  try {
	      //create a buffered reader that connects to the console, we use it so we can read lines
	      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	      //read a line from the console
	      String lineFromInput = in.readLine();

	      //create an print writer for writing to a file
	      PrintWriter out = new PrintWriter(new FileWriter(myObj.getName()));

	      //output to the file a line
	      out.println(lineFromInput);

	      //close the file (VERY IMPORTANT!)
	      out.close();
	   }
	      catch(IOException e1) {
	        System.out.println("Error during reading/writing");
	   }
	  }
	  public static void screenshot()
	  {
		  error++;
		  File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    	try {
	    	 // now copy the  screenshot to desired location using copyFile //method
	    	FileUtils.copyFile(src, new File("D:/AccuSelenium/Ram/ACV5_Dials/screenshots/error"+error+".png"));
	    	}
	    	 
	    	catch (IOException e1)
	    	 {
	    	  System.out.println(e1.getMessage());
	    	 }
	  }
	  
	  public static void createDirectory()
	  {
		  
			    File dir = new File("D:/AccuSelenium/Ram/ACV5_Dials/screenshots/"+dateFormat.format(date)+"");
			    
			    foldername = dir.getName();
			   // System.out.println(foldername);
			    // attempt to create the directory here
			    boolean successful = dir.mkdir();
			    if (successful)
			    {
			      // creating the directory succeeded
			      System.out.println("directory was created successfully");
			    }
			    else
			    {
			      // creating the directory failed
			      System.out.println("failed trying to create the directory");
			    }
			  
	  }
		
	  public static void parentDail() throws InterruptedException
		{
		
		driver.findElement(By.xpath("//tr[6]/td[1]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//label[contains(text(),'Dial UI Control')]/following::span[1]")).click();
		Thread.sleep(3000);
		//driver.findElement(By.xpath("//label[contains(text(),'Dial UI Control')]/following::span[1]")).sendKeys(parent_dial);
		Keyboard kb = ((RemoteWebDriver) driver).getKeyboard();
		kb.sendKeys(orderFlow.parent_dial1);
		kb.pressKey(Keys.ENTER);
		driver.findElement(By.xpath("//*[@id='displayLabel_UCField']")).clear();
		driver.findElement(By.xpath("//*[@id='displayLabel_UCField']")).sendKeys(orderFlow.display_label1);
		Thread.sleep(3000);
		if(orderFlow.parent_dial1.equalsIgnoreCase("OptionRadioList")||orderFlow.parent_dial1.equalsIgnoreCase("OptionCheckBoxList"))
		{
			driver.findElement(By.id("hlnkValueList")).click();
			Wait.wait5Second();
			driver.findElement(By.xpath("//*[@id='txtDisplayText']")).sendKeys("1"); 
			Wait.wait2Second();
			driver.findElement(By.xpath("//*[@id='txtDisplayValue']")).sendKeys("1"); 
			Wait.wait2Second();
			driver.findElement(By.xpath("//*[@id='btnAddValueList']")).click();
			
		}
		orderFlow.Parent_dialName=driver.findElement(By.xpath("//tr[6]/td[1]/following::td[1]")).getText();
		driver.findElement(By.xpath("//*[@id='btnSaveDialInfo']")).click();
		 
		 System.out.println(orderFlow.Parent_dialName);
		 Thread.sleep(5000);
		// driver.findElement(By.xpath("//*[@id='ProductDialGird']/div[2]/table/tbody/tr[5]/td[1]/a")).click();
      }	
	  
  	public static void parentDail2() throws InterruptedException
		{
		Wait.Fluentwait(By.xpath("//tr[7]/td[1]"));
		Wait.wait10Second();
		driver.findElement(By.xpath("//tr[7]/td[1]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//label[contains(text(),'Dial UI Control')]/following::span[1]")).click();
		Thread.sleep(3000);
		//driver.findElement(By.xpath("//label[contains(text(),'Dial UI Control')]/following::span[1]")).sendKeys(parent_dial);
		Keyboard kb = ((RemoteWebDriver) driver).getKeyboard();
		kb.sendKeys(orderFlow.parent_dial2);
		kb.pressKey(Keys.ENTER);
		driver.findElement(By.xpath("//*[@id='displayLabel_UCField']")).clear();
		driver.findElement(By.xpath("//*[@id='displayLabel_UCField']")).sendKeys(orderFlow.display_label2);
		Thread.sleep(3000);
		orderFlow.Parent_dialName2=driver.findElement(By.xpath("//tr[7]/td[1]/following::td[1]")).getText();
		driver.findElement(By.xpath("//*[@id='btnSaveDialInfo']")).click();
		 System.out.println(orderFlow.Parent_dialName2);
		 Thread.sleep(5000);
	//	 driver.findElement(By.xpath("//*[@id='ProductDialGird']/div[2]/table/tbody/tr[7]/td[1]/a")).click();
      }	
  		
  	public static void childDail() throws InterruptedException
	{
		if(orderFlow.child_dial1.equalsIgnoreCase("Map Selector")||orderFlow.child_dial1.equalsIgnoreCase("Image Selector"))
		{
			Wait.wait5Second();
			Wait.Fluentwait(By.xpath("//tr[4]/td[1]"));
			driver.findElement(By.xpath("//tr[4]/td[1]")).click();
			Wait.wait5Second();
			driver.findElement(By.xpath("//label[contains(text(),'Dial UI Control')]/following::span[1]")).click();
			Wait.wait5Second();
			//driver.findElement(By.xpath("//label[contains(text(),'Dial UI Control')]/following::span[1]")).sendKeys(parent_dial);
			Keyboard kb = ((RemoteWebDriver) driver).getKeyboard();
			kb.sendKeys(orderFlow.child_dial1);
			kb.pressKey(Keys.ENTER);
			
			driver.findElement(By.xpath("//*[@id='displayLabel_UCField']")).clear();
			driver.findElement(By.xpath("//*[@id='displayLabel_UCField']")).sendKeys(orderFlow.child_dial1);
			Wait.wait5Second();
			driver.findElement(By.xpath("(//label[contains(text(),'Visible Criteria Product Field')]/following::span[1])[1]")).click();
			kb.sendKeys(orderFlow.Parent_dialName);
			kb.pressKey(Keys.ENTER);
			Wait.wait5Second();
			driver.findElement(By.xpath("(//label[contains(text(),'Visible Criteria Text')]/following::input[1])[1]")).click();
			driver.findElement(By.xpath("(//label[contains(text(),'Visible Criteria Text')]/following::input[1])[1]")).clear();
			kb.sendKeys(orderFlow.visible_criteria_text);
			Wait.wait5Second();
			driver.findElement(By.xpath("(//*[@id='trVisibleCriteriaOperaration']/span/span/span/span[1])[1]")).click();
			kb.sendKeys(orderFlow.visible_criteria_operation);
			kb.pressKey(Keys.ENTER);
			Wait.wait5Second();
			driver.findElement(By.xpath("(//label[contains(text(),'Visible Criteria Combo Operator')]/following::span[1])[1]")).click();
			kb.sendKeys(orderFlow.visible_criteria_operation);
			kb.pressKey(Keys.ENTER);
			Wait.wait5Second();
			driver.findElement(By.xpath("(//label[contains(text(),'Visible Criteria Product Field1')]/following::span[1])[1]")).click();
			kb.sendKeys(orderFlow.Parent_dialName2);
			kb.pressKey(Keys.ENTER);
			Wait.wait5Second();
			driver.findElement(By.xpath("(//label[contains(text(),'Visible Criteria Text1')]/following::input[1])[1]")).click();
			driver.findElement(By.xpath("(//label[contains(text(),'Visible Criteria Text1')]/following::input[1])[1]")).clear();
			kb.sendKeys(orderFlow.visible_criteria_text2);
			Wait.wait5Second();
			driver.findElement(By.xpath("//label[contains(text(),'Visible Criteria Operation1')]/following::span[1]")).click();
			kb.sendKeys(orderFlow.visible_criteria_operation2);
			kb.pressKey(Keys.ENTER);
			Wait.wait5Second();
			driver.findElement(By.xpath("//*[@id='btnSaveDialInfo']")).click();
			orderFlow.child_dialName=driver.findElement(By.xpath("//tr[4]/td[1]/following::td[1]")).getText();
			Wait.wait5Second();
			   			
		}
		else
		{
	Wait.wait5Second();
	Wait.Fluentwait(By.xpath("//tr[8]/td[1]"));
	Wait.wait25Second();
	driver.findElement(By.xpath("//tr[8]/td[1]")).click();
	Wait.wait5Second();
	driver.findElement(By.xpath("//label[contains(text(),'Dial UI Control')]/following::span[1]")).click();
	Wait.wait5Second();
	//driver.findElement(By.xpath("//label[contains(text(),'Dial UI Control')]/following::span[1]")).sendKeys(parent_dial);
	Keyboard kb = ((RemoteWebDriver) driver).getKeyboard();
	kb.sendKeys(orderFlow.child_dial1);
	kb.pressKey(Keys.ENTER);
	if(orderFlow.child_dial1.equalsIgnoreCase("OptionRadioList")||orderFlow.child_dial1.equalsIgnoreCase("OptionCheckBoxList"))
	{
		driver.findElement(By.id("hlnkValueList")).click();
		Wait.wait5Second();
		driver.findElement(By.xpath("//*[@id='txtDisplayText']")).clear();
		driver.findElement(By.xpath("//*[@id='txtDisplayText']")).sendKeys("1"); 
		Wait.wait2Second();
		driver.findElement(By.xpath("//*[@id='txtDisplayValue']")).clear();
		driver.findElement(By.xpath("//*[@id='txtDisplayValue']")).sendKeys("1"); 
		Wait.wait2Second();
		driver.findElement(By.xpath("//*[@id='btnAddValueList']")).click();
		Wait.wait2Second();
		driver.findElement(By.xpath("//*[@id='btnCloseValueList']")).click();
		
	}
	driver.findElement(By.xpath("//*[@id='displayLabel_UCField']")).clear();
	driver.findElement(By.xpath("//*[@id='displayLabel_UCField']")).sendKeys(orderFlow.child_dial1);
	Thread.sleep(5000);
	driver.findElement(By.xpath("(//label[contains(text(),'Visible Criteria Product Field')]/following::span[1])[1]")).click();
	kb.sendKeys(orderFlow.Parent_dialName);
	kb.pressKey(Keys.ENTER);
	Wait.wait5Second();
	driver.findElement(By.xpath("(//label[contains(text(),'Visible Criteria Text')]/following::input[1])[1]")).click();
	driver.findElement(By.xpath("(//label[contains(text(),'Visible Criteria Text')]/following::input[1])[1]")).clear();
	kb.sendKeys(orderFlow.visible_criteria_text);
	Wait.wait5Second();
	driver.findElement(By.xpath("(//*[@id='trVisibleCriteriaOperaration']/span/span/span/span[1])[1]")).click();
	kb.sendKeys(orderFlow.visible_criteria_operation);
	kb.pressKey(Keys.ENTER);
	Wait.wait5Second();
	driver.findElement(By.xpath("(//label[contains(text(),'Visible Criteria Combo Operator')]/following::span[1])[1]")).click();
	kb.sendKeys(orderFlow.visible_criteria_operation);
	kb.pressKey(Keys.ENTER);
	Wait.wait5Second();
	driver.findElement(By.xpath("(//label[contains(text(),'Visible Criteria Product Field1')]/following::span[1])[1]")).click();
	kb.sendKeys(orderFlow.Parent_dialName2);
	kb.pressKey(Keys.ENTER);
	Wait.wait5Second();
	driver.findElement(By.xpath("(//label[contains(text(),'Visible Criteria Text1')]/following::input[1])[1]")).click();
	driver.findElement(By.xpath("(//label[contains(text(),'Visible Criteria Text1')]/following::input[1])[1]")).clear();
	kb.sendKeys(orderFlow.visible_criteria_text2);
	Wait.wait5Second();
	driver.findElement(By.xpath("//label[contains(text(),'Visible Criteria Operation1')]/following::span[1]")).click();
	kb.sendKeys(orderFlow.visible_criteria_operation2);
	kb.pressKey(Keys.ENTER);
	Wait.wait5Second();
	driver.findElement(By.xpath("//*[@id='btnSaveDialInfo']")).click();
	orderFlow.child_dialName=driver.findElement(By.xpath("//tr[8]/td[1]/following::td[1]")).getText();
	Wait.wait5Second();

  } 
}
	 
  	public static void personalizeWizard() throws InterruptedException
  	{
  		Wait.Fluentwait(By.xpath("//a[@id='lnkPersonalizeWizard']"));
  		Wait.wait5Second();
  		driver.findElement(By.xpath("//a[@id='lnkPersonalizeWizard']")).click();
  		Wait.Fluentwait(By.xpath("//tr[1]/td[1]/input"));
  		Wait.wait10Second();
  		try {
			for(int i=1;i<=10;i++)
			{
				boolean chkbox=driver.findElement(By.xpath("//tr["+i+"]/td[1]/input")).isSelected();
				if(chkbox==true)
				{
                driver.findElement(By.xpath("//tr["+i+"]/td[1]/input")).click(); 	
                driver.findElement(By.xpath("//tr["+i+"]/td[3]/input")).clear();
				}
				
			}
		} catch (Exception  e) {
		
		}
  		
  		for(int i=1;i<=10;i++)
		{
			try {
				String displayName=driver.findElement(By.xpath("//tr["+i+"]/td[5]")).getText();
				if(displayName.equalsIgnoreCase(orderFlow.Parent_dialName) || displayName.equalsIgnoreCase(orderFlow.Parent_dialName2) || displayName.equalsIgnoreCase(orderFlow.child_dialName))
				{
				driver.findElement(By.xpath("//tr["+i+"]/td[1]/input")).click(); 	
				driver.findElement(By.xpath("//tr["+i+"]/td[3]/input")).sendKeys(""+i);
				}
			} catch (Exception e) {
				
			}
			
		}
  		Wait.Fluentwait(By.xpath("//input[@id='btnSaveWizard']"));
  	driver.findElement(By.xpath("//input[@id='btnSaveWizard']")).click();
  	Wait.wait10Second();
  	}
	
}
