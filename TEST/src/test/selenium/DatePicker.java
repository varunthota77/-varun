package test.selenium;


import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DatePicker {

	
	public static void main(String[] args) throws IOException, InterruptedException {
String month;
		
			Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
	  System.setProperty("webdriver.chrome.driver","D:\\Accu_Selenium\\AC4.0_POD_Pricing\\TestData\\Drivers\\chromedriver.exe");
	  WebDriver ir = new ChromeDriver();
	  
	  	  
	  ir.get("https://www.spicejet.com/"); 
	  ir.manage().window().maximize();
	  Thread.sleep(1000);
	  
	  ir.findElement(By.xpath("//*[@id='flightSearchContainer']/div[4]/button")).click();
	  
	  String date="9 September 2019";
	    String datesplit[]=date.split(" ");
	   
	  for(int i=datesplit.length-1;i>=0;i--)
	  {
		  System.out.println(datesplit[i]);
		  
		 
		  	}
	  
	  String year=ir.findElement(By.xpath("//*[@id='ui-datepicker-div']/div[2]/div/div/span[2]")).getText();
	  month=ir.findElement(By.xpath("//*[@id='ui-datepicker-div']/div[2]/div/div/span[1]")).getText();
	  
	//  System.out.println(year);
	  //System.out.println(month);
	  	  if(datesplit[2]==year)
	  	  {
	  		  	  		 
	  	  }
	  	  else
	  	  {
	  		  while(!datesplit[2].equalsIgnoreCase(year))
	  		ir.findElement(By.xpath("//*[@id='ui-datepicker-div']/div[2]/div/a/span")).click();
	  	  }
	  	    	    
	  		  	  	  
	  	 if(datesplit[1]==month)
	  	  {
	  		  	  		 
	  	  }
	  	  else
	  	  {
	  		  for(int j=1;j<=12;j++)
	  		  {
	  			 ir.findElement(By.xpath("//*[@id='ui-datepicker-div']/div[2]/div/a/span")).click();
	  			 month=ir.findElement(By.xpath("//*[@id='ui-datepicker-div']/div[2]/div/div/span[1]")).getText();
	  			System.out.println(month);
	  			 if(datesplit[1].equalsIgnoreCase(month))
	  		  		
	  			 {
	  				for( int x=1;x<=5;x++)
  					{
  						for(int y=1;y<=7;y++)
  						{
  							
  							{
  								
  								//Thread.sleep(3000);
  						String date1= ir.findElement(By.xpath("//*[@id='ui-datepicker-div']/div[2]/table/tbody/tr[ "+ x +"]/td["+y+"]/a")).getText();
  					
  						
  					//System.out.println(date1);//	ir.findElement(By.xpath("//*[@id='ui-datepicker-div']/div[2]/table/tbody/tr[tr]/td[td]/a")).click();
  						if(datesplit[0].equalsIgnoreCase(date1))
  						{
  							ir.findElement(By.xpath("//*[@id='ui-datepicker-div']/div[2]/table/tbody/tr[ "+ x +"]/td["+y+"]/a")).click();
  							 break;
  						}
  					}
  				}
  			}  				
	  	 }			 
	  } 				  							
   }
}
		
			

	  }
