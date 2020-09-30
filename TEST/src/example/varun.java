package example;
	import java.io.File;

	import java.io.FileInputStream;

	import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

	import org.apache.poi.ss.usermodel.Row;

	import org.apache.poi.ss.usermodel.Sheet;

	import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.gargoylesoftware.htmlunit.javascript.host.Map;

import test.selenium.ReadExcel;
	public class varun 
	{
		public static String username,password;
		public static HashMap<String, String> map=new HashMap<String,String>(); 
		public static void main(String...strings) throws IOException, InterruptedException{
       File file =    new File(System.getProperty("user.dir")+"/TestData/"+"\\"+"LoginData.xls");
	   FileInputStream inputStream = new FileInputStream(file);
	   Workbook Workbook = null;
       

	        Workbook = new HSSFWorkbook(inputStream);
	    
	    Sheet Sheet = Workbook.getSheet("Sheet1");
	    int rowCount = Sheet.getLastRowNum()-Sheet.getFirstRowNum();	 
	    for (int i = 1; i < rowCount+1; i++) {
	        Row row = Sheet.getRow(i);
	        for (int j = 0; j < row.getLastCellNum(); j++) {
	        	map.put(Sheet.getRow(0).getCell(j).getStringCellValue(), row.getCell(j).getStringCellValue());
	        //   System.out.print(row.getCell(j).getStringCellValue()+"|| ");        	
	        }
	        
	       // System.out.println(map.get("username"));
	        username=map.get("username");
	        password=map.get("password");
	        System.setProperty("webdriver.chrome.driver",
	    			System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");
	    			 
	    		WebDriver driver = new ChromeDriver();
	    		
	    		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    		
	    		driver.get("http://192.168.1.121/acv4.1.0.0_responsive/");
	    		
	    		driver.manage().window().maximize();
	    		Thread.sleep(5000);
	    		driver.findElement(By.id("username")).sendKeys(username);
	    		
	    		driver.findElement(By.id("password")).sendKeys(password,Keys.ENTER);
	    		Thread.sleep(5000);
	    		driver.findElement(By.id("Logout")).click();
	    }
    
	 }
    
	    
	    }
	

