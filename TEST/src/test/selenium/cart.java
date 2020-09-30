package test.selenium;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.common.base.Function;
public class cart {
public static WebDriver d;
public static WebDriverWait wait;
public static int ElementWaitTime = 300;
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");		 
	    d = new ChromeDriver();
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		d.get("http://192.168.1.121/acv40ltuser/#/");
		d.manage().window().maximize();
	for(int i=301;i<=399;i++)	
	{
		Thread.sleep(5000);
		Fluentwait(By.id("username"));
		d.findElement(By.id("username")).sendKeys("luser"+i);		
		d.findElement(By.id("password")).sendKeys("welcome",Keys.ENTER);
		Thread.sleep(10000);
		Fluentwait(By.xpath("(//span/span)[1]"));
		String cart=d.findElement(By.xpath("(//span/span)[1]")).getText();
if(cart.equalsIgnoreCase("0"))
{
	System.out.println("No items in the cart for luser"+i);
	d.findElement(By.id("Logout")).click();             
	Fluentwait(By.id("username"));
}
else
{
	Fluentwait(By.xpath("//*[@id='lnkShoppingcartList']/span/img"));
	d.findElement(By.xpath("//*[@id='lnkShoppingcartList']/span/img")).click();
	Fluentwait(By.xpath("//*[@id='lnkEmptyCart_2']"));
	Thread.sleep(5000);
	d.findElement(By.xpath("//*[@id='lnkEmptyCart_2']")).click();
	Thread.sleep(3000);
	Fluentwait(By.xpath("//*[@id='btnShoppingcartItemDeleteOk']"));
	d.findElement(By.xpath("//*[@id='btnShoppingcartItemDeleteOk']")).click();
System.out.println("cart item deleted for luser"+i);
Thread.sleep(3000);
d.findElement(By.id("Logout"));
d.findElement(By.id("Logout")).click();
Thread.sleep(3000);
}
  }		
	}
	public static void  Fluentwait(By Element){
		FluentWait	 waitfl = new FluentWait<WebDriver>(d);
		waitfl.withTimeout(ElementWaitTime, TimeUnit.SECONDS);
		 waitfl.pollingEvery(1, TimeUnit.SECONDS);
		 waitfl.ignoring(NoSuchElementException.class);
		 waitfl.ignoring(StaleElementReferenceException.class);
		 waitfl.until(ExpectedConditions.elementToBeClickable(Element));
		 waitfl.until(ExpectedConditions.presenceOfElementLocated(Element));
		 waitfl.until(ExpectedConditions.visibilityOfElementLocated(Element));
		 waitfl.until(new Function<WebDriver, WebElement>() 
		 {
			public WebElement apply(WebDriver driver) {
			return driver.findElement(Element);
			}
		 });
		 }
}
