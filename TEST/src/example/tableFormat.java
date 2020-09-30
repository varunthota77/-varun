package example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class tableFormat {
	public static WebDriver d;
	public static String url="http://192.168.1.121/acv4.1.0.0_responsive/#";
	public static String AdminName="testadminch1";
	public static String AdminPwd="welcome";
	public static String ApproverName="testapptv";
	public static String ApproverPwd="welcome@123";
	public static String shippingBasis="item";
	//public static WebDriverWait wait = new WebDriverWait(d,50);
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");		 
	    d = new ChromeDriver();
	  //
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		d.get(url);
		d.manage().window().maximize();
		Thread.sleep(10000);
		
		d.findElement(By.id("username")).sendKeys(AdminName);
		
		d.findElement(By.id("password")).sendKeys(AdminPwd,Keys.ENTER);
		Thread.sleep(20000);

		d.findElement(By.xpath("//*[@id='ParentMenu_40']/div/label")).click();
	
		d.findElement(By.xpath("//img[contains(@src,'Content/themes/Layout1/Images/vieworders.png')]")).click();
		Thread.sleep(10000);
	//	List<String> al=new ArrayList<String>();  
		
		List<WebElement> rows = d.findElements(By.xpath("//*[@id='divGrid']/div[2]/table/tbody/tr"));
		int count = rows.size();
		System.out.println("ROW COUNT : "+count);
	}

}
