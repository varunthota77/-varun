package test.selenium;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ImageVerify  {
	public static WebDriver d;
	public static WebElement ImageFile;
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");		 
	    d = new ChromeDriver();
	  //
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		d.get("http://192.168.1.121/acv4.1.0.0_responsive/#");
		d.manage().window().maximize();
		//wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		d.findElement(By.id("username")).sendKeys("testadmina123");
		//wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password")));
	//	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
		d.findElement(By.id("password")).sendKeys("welcome",Keys.ENTER);
		Thread.sleep(5000);
		
		
		try
		   {
         ImageFile = d.findElement(By.xpath("//*[@id='Homeref']"));
        Boolean ImagePresent = (Boolean) ((JavascriptExecutor)d).executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", ImageFile);
        if (!ImagePresent)
        {
             System.out.println("Image not displayed.");
        }
        else
        {
            System.out.println("Image displayed.");
        }
		   }
        catch(Exception e)
		   {
			System.out.println("image not displayed");
			//e.printStackTrace();
		   }
		
	}

}
