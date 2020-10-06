package com.test.suite;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.test.excel.Excel_Read;
import com.test.hooks.Config;
import com.test.hooks.Hooks;
import com.test.hooks.Wait;
import com.test.pages.Login_Page;


public class Login_Test extends Hooks
{
	
	@BeforeTest
	public void launchApplication() throws Exception
	{
		System.out.println("TEST STARTED");
		Hooks.InitiateBrowser();
	}
	
	
	@Test
	public void Login() throws Exception
	{
		/**
		 * @@ Writing for loop to read multiple sets of 
		 * input data from Excel Sheet
		 */
		for(int i=Config.Start_Row_Number; i<=Config.End_Row_Number;i++)
		{			
			try {
		
			Excel_Read.load_Excel_data(i);
			System.out.println("Excel Data loaded successfully");
			Hooks.navigate_to_URL();
			
			//Call Login_Page methods
			Login_Page lp=new Login_Page(driver);
			lp.EnterUsernameAndPassword();
			lp.sign_in_and_validate_login();
			Wait.wait3Second();
			lp.logout_user();
			Wait.wait4Second();
			Pass_Count=Pass_Count+1;
			Hooks.EndTest();

			}catch (Exception e) 
			{
				Fail_Count = Fail_Count+1;
				System.out.println("Exception is : " + e.toString());
			}
		}
		
		System.out.println("Pass Count ->" + Pass_Count);
		System.out.println("Fail Count ->" + Fail_Count);
	}
		
	@AfterTest
	public void CloseTest() throws Exception
	{
		Hooks.EndTest();
		Hooks.closeReports();
		System.out.println("TEST COMPLETED");
	}
	
}
