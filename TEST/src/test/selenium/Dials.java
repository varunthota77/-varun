package test.selenium;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class Dials   {
	
	public  String AdminName="testadmintv";
	public  String AdminPwd="welcome";
	public  static String temp="testadmintv";
	public static int startRow=0;
	@Test(dataProvider="dails")
	public void dails(String parentdial, String displaylabel,String childdial1, String visiblecriteriatext,String visisblecriteriaoperator, String visibledialcombooperator,String childdial2) throws InterruptedException {
	
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver",
    			System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");
    			 
    		WebDriver driver = new ChromeDriver();
    		
    		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    		
    		driver.get("http://192.168.1.121/acv4.1.0.0_responsive/");
    		
    		driver.manage().window().maximize();
    		Thread.sleep(5000);
    		driver.findElement(By.id("username")).sendKeys("testadmina123");
    		
    		driver.findElement(By.id("password")).sendKeys("welcome",Keys.ENTER);
    		Thread.sleep(15000);
    		driver.findElement(By.xpath("//img[contains(@src,'Content/themes/Layout1/images/Products_Icon.png')]")).click();
    		Thread.sleep(5000);
    		
    		driver.findElement(By.xpath("//b[contains(text(),'Manage Products')]")).click();
    		Thread.sleep(5000);
       		driver.findElement(By.xpath("//*[@id='btnSearch']")).click();
    		driver.findElement(By.xpath("//*[@id='btnSearch']")).sendKeys("dynamic");
    		Thread.sleep(5000);
    		driver.findElement(By.linkText("Dials")).click();
    		Thread.sleep(10000);
    		driver.findElement(By.xpath("//*[@id='ProductDialGird']/div[2]/table/tbody/tr[1]/td[1]")).click();
    		driver.findElement(By.xpath("//tr[7]/td[1]")).click();
    		Thread.sleep(3000);
    		driver.findElement(By.xpath("//label[contains(text(),'Dial UI Control')]/following::span[1]")).sendKeys(parentdial);
    		
    		
	}
	@DataProvider(name="dails")
	public Object[][] loginData() {
		Object[][] arrayObject = getExcelData("D:\\AccuSelenium\\Ram\\TEST\\TestData\\admin.xls","Sheet1");
		return arrayObject;
	}

	/**
	 * @param File Name
	 * @param Sheet Name
	 * @return
	 */
	public String[][] getExcelData(String fileName, String sheetName) {
		String[][] arrayExcelData = null;
		try {
			FileInputStream fs = new FileInputStream(fileName);
			Workbook wb = Workbook.getWorkbook(fs);
			Sheet sh = wb.getSheet(sheetName);

			int totalNoOfCols = sh.getColumns();
			int totalNoOfRows = sh.getRows();
			
			arrayExcelData = new String[totalNoOfRows-1][totalNoOfCols];
			
			for (int i= 1 ; i < totalNoOfRows; i++) {

				for (int j=0; j < totalNoOfCols; j++) {
					arrayExcelData[i-1][j] = sh.getCell(j, i).getContents();
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		}
		return arrayExcelData;
	}
}
