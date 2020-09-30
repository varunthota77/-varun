import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class sample {

public static void main(String[] args) {

	System.setProperty("webdriver.chrome.driver",
			System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");	
WebDriver driver=new ChromeDriver();
ChromeOptions options = new ChromeOptions();
options.addArguments("no-sandbox");
options.addArguments("disable-extensions");
driver = new ChromeDriver();
driver.get("https://dzone.com/articles/selenium-java-tutorial-how-to-test-login-process");

// TODO Auto-generated method stub

}

}