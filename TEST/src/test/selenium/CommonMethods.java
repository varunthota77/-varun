package test.selenium;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
public class CommonMethods {
public static WebDriver d=null;
public static String url="http://192.168.1.121/acv4.1.0.0_responsive/#";
public static String AdminName="testadmintv";
public static String AdminPwd="welcome";
public static String ApproverName="testapptv";
public static String ApproverPwd="welcome@123";
public static String shippingBasis="item";
public static WebDriverWait wait = new WebDriverWait(d,50);
public static void adminLogin()
{
	System.setProperty("webdriver.chrome.driver",
			System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");		 
    d = new ChromeDriver();
  //
	d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	d.get(url);
	d.manage().window().maximize();
	wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
	d.findElement(By.id("username")).sendKeys(AdminName);
	wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password")));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
	d.findElement(By.id("password")).sendKeys(AdminPwd,Keys.ENTER);
}
public static void date()
{
	 DateFormat dateFormat = new SimpleDateFormat("_yyyy-MMM-dd_h-mm-ss_a");
		 Date date = new Date();
		 System.out.println("Time Stamp : "+dateFormat.format(date));
}
public static void logout()
{
	d.findElement(By.id("Logout")).click();
}
public static void approverLogin()
{

  //
	d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
	d.findElement(By.id("username")).sendKeys(ApproverName);
	wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password")));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
	d.findElement(By.id("password")).sendKeys(ApproverPwd,Keys.ENTER);
}
public static void getscreenshot() throws Exception 
{
        File scrFile = ((TakesScreenshot)d).getScreenshotAs(OutputType.FILE);
     //The below method will save the screen shot in d drive with name "screenshot.png"
        FileUtils.copyFile(scrFile, new File("D:\\screenshot.png"));
}
public static void changeShippingBasis() throws Exception
{
	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='ParentMenu_1']/div/label")));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='ParentMenu_1']/div/label")));
	d.findElement(By.xpath("//*[@id='ParentMenu_1']/div/label")).click();
	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[contains(@src,'ManageOrgUnits.png')]")));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[contains(@src,'ManageOrgUnits.png')]")));
	d.findElement(By.xpath("//img[contains(@src,'ManageOrgUnits.png')]")).click();
	wait.until(ExpectedConditions.presenceOfElementLocated(By.id("lnkShipping")));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lnkShipping")));
	d.findElement(By.id("lnkShipping")).click();
	Wait.wait10Second();
	wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("span.k-icon.k-i-arrow-s")));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.k-icon.k-i-arrow-s")));
	Wait.wait5Second();
	if(shippingBasis.equalsIgnoreCase("order")||shippingBasis.equalsIgnoreCase("split ship"))
	{
		d.findElement(By.cssSelector("span.k-icon.k-i-arrow-s")).click();
		 Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
		kb.sendKeys(shippingBasis);
		kb.pressKey(Keys.ENTER);
		d.findElement(By.xpath("//div[@id='divShippingSettings']/div/div[4]/fieldset/div/div[2]/span/span/span[2]/span")).click();
		kb.sendKeys("individual");
		kb.pressKey(Keys.ENTER);
		d.findElement(By.id("lnkSaveShippingConfig")).click();
		String errorMsg=d.findElement(By.id("spnShippingErrorMsg")).getText();
		if(errorMsg.equalsIgnoreCase("Change of Shipping basis is not allowed when there are any pending orders"))
		{
			CommonMethods.logout();
			approverLogin();
		}
	}
	else
	{
	d.findElement(By.cssSelector("span.k-icon.k-i-arrow-s")).click();
	Keyboard kb = ((RemoteWebDriver) d).getKeyboard();
	kb.sendKeys(shippingBasis);
	kb.pressKey(Keys.ENTER);
	d.findElement(By.id("lnkSaveShippingConfig")).click();
	wait.until(ExpectedConditions.presenceOfElementLocated(By.id("spnShippingErrorMsg")));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("spnShippingErrorMsg")));
	Wait.wait5Second();
	String errorMsg=d.findElement(By.id("spnShippingErrorMsg")).getText();
	   if(errorMsg.equalsIgnoreCase("Change of Shipping basis is not allowed when there are any pending orders"))
	     {
		   try
		   {
		logout();
	approverLogin();
	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[contains(@src,'Content/themes/Layout1/Images/Reports.png')]")));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[contains(@src,'Content/themes/Layout1/Images/Reports.png')]")));
	d.findElement(By.xpath("//img[contains(@src,'Content/themes/Layout1/Images/Reports.png')]")).click();
	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='k-checkbox header-checkbox']")));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='k-checkbox header-checkbox']")));
	Wait.wait5Second();
	d.findElement(By.xpath("//*[@class='k-checkbox header-checkbox']")).click();
		   }
		   catch(Exception e)
		   {
			   getscreenshot();
			  e.printStackTrace();
		   }
	  }
	}
  }
public void wait1(By element)
{
	wait.until(ExpectedConditions.presenceOfElementLocated(element));
	wait.until(ExpectedConditions.visibilityOfElementLocated(element));
}
}