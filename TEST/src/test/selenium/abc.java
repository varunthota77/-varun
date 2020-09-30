package test.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class abc {
public static WebDriver driver=null;
public static void main(String[] args) {

// Launching browser and load html file
	System.setProperty("webdriver.chrome.driver",
			System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");
driver= new ChromeDriver();
driver.get("file:///C:/Users/Admin/Desktop/H.html");

// Assert tag name of bold text
String tagName= driver.findElement(By.id("bold1")).getTagName();
Assert.assertEquals(tagName, "b");

// Assert tag name of bold text
String tagName1= driver.findElement(By.id("bold2")).getTagName();
Assert.assertEquals(tagName1, "strong");

// Get value of font-weight and assert if it is bold
String fontWeight= driver.findElement(By.id("bold3")).getCssValue("font-weight");
System.out.println(fontWeight);
Assert.assertTrue(Integer.parseInt(fontWeight)>700);

driver.quit();


}
}


	
