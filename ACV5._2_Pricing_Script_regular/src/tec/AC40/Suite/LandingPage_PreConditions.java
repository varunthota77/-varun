package tec.AC40.Suite;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import tec.AC40.Common.Commonclass;
import tec.AC40.Config.Config;

public class LandingPage_PreConditions extends Commonclass {

	@Test
	public void AccuLandingPrice() throws InterruptedException{
		try{
		    MouseAdjFunction();
		    WebDriver d = null;
			   String os = System.getProperty("os.name");
			   if (os.contains("Windows")) 
			   {
					if (Config.browser.equals("GC")) {
						Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
					} else if (Config.browser.equals("IE")) {
						Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
					} else if (Config.browser.equals("FF")) {
						Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
					}
					// Below code kill's the chrome driver .exe file from
					// back end
					Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
				}
		        tec.AC40.Common.Wait.wait5Second();
		        tec.AC40.Common.Wait.wait5Second();
		        StartBrowser();
	        tec.AC40.Common.Wait.wait5Second();
		    adminLogin();
	        tec.AC40.Common.Wait.wait5Second();
	      // adminsettings();
	        creatlandingPage();
	        tec.AC40.Common.Wait.wait5Second();
		 
		}catch(Exception e){
			e.printStackTrace();
		}
	}



}
