package test.selenium;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;  
public class AccuPromotion {
public WebElement element;
	public static void main(String[] args) throws InterruptedException {
    // TODO Auto-generated method stub
    System.setProperty("webdriver.chrome.driver", "D:\\AccuSelenium\\Ram\\TEST\\Drivers\\chromedriver.exe");
    WebDriver driver = new ChromeDriver();
WebDriverWait wb =new WebDriverWait(driver,6000);
   driver.get("http://192.168.1.121/acv4.1.0.0_responsive/#/");
    driver.manage().window().maximize();
    Wait.wait5Second();
     driver.findElement(By.id("username")).sendKeys("testar");
   driver.findElement(By.id("password")).sendKeys("welcome");
   Wait.wait5Second();
    driver.findElement(By.id("btnLogin")).click();
    System.out.println("Login");
       Wait.wait5Second();
       driver.findElement(By.xpath("//*[@id='expanderSign1']/div/label")).click();
       driver.findElement(By.id("txtemulatesearch")).sendKeys("testur");
       Wait.wait5Second();
       driver.findElement(By.xpath("//*[@id='imgEmulatesearch']")).click();
       Wait.wait5Second();
       driver.findElement(By.xpath("//*//*[@id='lblCategories']/label")).click();
       Wait.wait5Second();
       driver.findElement(By.xpath("//*[@id='productCountClick_5871779']")).click();
       Wait.wait5Second();
       driver.findElement(By.xpath("//*[@id='lnkimg-5871797-0-false-0']")).click();
       Wait.wait5Second();
       driver.findElement(By.xpath("//*[@id='lnkSelectListContinue']"));
       Wait.wait5Second();
    }
    }


