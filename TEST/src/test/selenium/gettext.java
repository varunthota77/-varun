package test.selenium;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class gettext {

	
	public static void main(String[] args) throws InterruptedException, AWTException {
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");	
	    WebDriver driver=new ChromeDriver();
	    WebDriverWait wb=new WebDriverWait(driver,500);
	    
	    driver.get("http://192.168.1.121/acv4.1.0.0_responsive/#/");
	    driver.manage().window().maximize();
	 
	//wb.until(ExpectedConditions.visibilityOfElementLocated(s));
	 
	    Thread.sleep(11000);
	    driver.findElement(By.xpath("//input[@id='username']")).sendKeys("testusertv");
	    driver.findElement(By.id("password")).sendKeys("welcome");
	    driver.findElement(By.xpath("//input[@value='Login']")).click();
	    Thread.sleep(2000);
	    wb.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='productCountClick_5371703']")));
	    wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='productCountClick_5371703']")));
	    driver.findElement(By.xpath("//*[@id='productCountClick_5371703']")).click();
	    wb.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='lnkname-5702710-0-false-false']")));
	    wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='lnkname-5702710-0-false-false']")));
	    driver.findElement(By.xpath("//*[@id='lnkname-5702710-0-false-false']")).click();
	    wb.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='txtOrderName']")));
	    wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='txtOrderName']")));
	    driver.findElement(By.xpath("//*[@id='txtOrderName']")).sendKeys("test");
	    driver.findElement(By.xpath("//*[@id='btnNext']/span[2]")).click();
	    Thread.sleep(15000);
	    wb.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@aria-owns='ddlShipping_listbox']/span/span")));
	    wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@aria-owns='ddlShipping_listbox']/span/span"))); 
	 driver.findElement(By.xpath("//span[@aria-owns='ddlShipping_listbox']/span/span")).click();
	 wb.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ul[@id='ddlShipping_listbox']/li[2]")));
	 wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='ddlShipping_listbox']/li[2]")));
	 driver.findElement(By.xpath("//ul[@id='ddlShipping_listbox']/li[2]")).click();
	 
	 wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@name,'spnContinue1')]")));
	 driver.findElement(By.xpath("//span[contains(@name,'spnContinue1')]")).click();
	 Thread.sleep(2000);
	 wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@name,'CheckAllbox')]")));
	 driver.findElement(By.xpath("//input[contains(@name,'CheckAllbox')]")).click();
	 wb.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@name,'chkbox_6385315')]")));
	 driver.findElement(By.xpath("//input[contains(@name,'chkbox_6385315')]")).click();
	 driver.findElement(By.xpath("//button[contains(@id,'btnCheckout_2')]")).click();
	 
	
	}

}