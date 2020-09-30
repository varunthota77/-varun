package test.selenium;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TableData {

	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");
		WebDriver d=new ChromeDriver();
		d.get("http://192.168.1.121/acv4.1.0.0_responsive/#/");
		
		
Thread.sleep(10000);
		
		d.findElement(By.xpath("//input[@id='username']")).sendKeys("testadmina123");
		d.findElement(By.xpath("//input[@id='password']")).sendKeys("welcome");
		
		Thread.sleep(1000);
		d.findElement(By.xpath("//input[@name='btnLogin']")).click();
		
		Thread.sleep(16000);
		
		d.findElement(By.xpath("//img[contains(@src,'Reports.png')][1]")).click();
		
		
		Thread.sleep(3000);
		d.findElement(By.xpath("//img[contains(@src,'vieworders.png')]")).click();
		Thread.sleep(12000);
		WebElement tabledata=d.findElement(By.xpath("//div[@id='divGrid']"));
		List<WebElement> rowtable=tabledata.findElements(By.tagName("tr"));
		int trsize=rowtable.size();
		System.out.println(trsize);
		Thread.sleep(2000);
		for(int i=1;i<=trsize;i++)
		{
			List<WebElement> coltable=rowtable.get(i).findElements(By.tagName("td"));
			int tdsize=coltable.size();
			System.out.println(tdsize);
			for(int j=1;j<tdsize;j++)
			{
			
				String el=d.findElement(By.xpath("//tbody//tr["+i+"]//td["+j+"]")).getText();
				System.out.print("   ");
				System.out.print(el);
			}System.out.println(" ");
		}
		
	}

}