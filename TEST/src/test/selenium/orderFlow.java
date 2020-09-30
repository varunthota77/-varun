package test.selenium;

import org.apache.bcel.generic.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.WebConsole.Logger;
public class orderFlow extends CommonMethods
{ 
	
	public static void main(String[] args) throws Exception 
 {
		
		// TODO Auto-generated method stub
adminLogin();
Wait.wait10Second(); 
d.findElement(By.xpath("//*[@id='ParentMenu_40']/div/label")).click();
d.findElement(By.xpath("//img[contains(@src,'vieworders.png')]")).click();
Wait.wait5Second();
d.findElement(By.xpath("//tr[1]/td[6]")).click();

//d.switchTo().alert().getText();

	
	WebElement ImageFile = d.findElement(By.xpath("//img[contains(@id,'Test Image')]"));
        
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
 }
