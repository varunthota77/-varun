package test.selenium;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class CrossBrowser {
static WebDriver driver=null;
static String browser="chrome";
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	
	if(browser.equalsIgnoreCase("firefox")){
		//create firefox instance
		DesiredCapabilities capability = DesiredCapabilities.firefox();
		capability.setBrowserName("firefox");
		capability.setPlatform(Platform.WIN10);
		System.setProperty("webdriver.gecko.driver",
				System.getProperty("user.dir")+"\\Drivers\\geckodriver.exe");	
			driver = new FirefoxDriver();
		}
		//Check if parameter passed as 'chrome'
		else if(browser.equalsIgnoreCase("chrome")){
			//set path to chromedriver.exe
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");	
			//create chrome instance
			driver = new ChromeDriver();
		}
		//Check if parameter passed as 'Edge'
				else if(browser.equalsIgnoreCase("IE")){
					//set path to Edge.exe
					System.setProperty("webdriver.ie.driver","D:\\AccuSelenium\\Ram\\TEST\\Drivers\\IEDriverServer.exe");
					//create Edge instance
					driver = new InternetExplorerDriver();
				}
	driver.get("//192.168.1.121/acv4.1.0.0_responsive");
}
}