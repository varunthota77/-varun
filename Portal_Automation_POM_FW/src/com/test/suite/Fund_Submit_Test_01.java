package com.test.suite;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.test.excel.Excel_Read;
import com.test.hooks.Config;
import com.test.hooks.Hooks;
import com.test.hooks.Wait;
import com.test.pages.Fund_Submit_Page;
import com.test.pages.Login_Page;

public class Fund_Submit_Test_01 extends Hooks
{


	@BeforeTest
	public void init_browser() throws Exception
	{
		System.out.println("TEST STARTED");
	
	}
	
	
	@Test
	public void fund_setup() throws Exception
	{
		/**
		 * @@ Writing for loop to read multiple sets of 
		 * input data from Excel Sheet
		 */
		for(int i=Config.Start_Row_Number; i<=Config.End_Row_Number;i++)
		{			
			try {
			
			Hooks.InitiateBrowser();
			Login_Page lp=new Login_Page(driver);
			Fund_Submit_Page fp=new Fund_Submit_Page(driver);

			Excel_Read.load_Excel_data(i);
			System.out.println("Excel Data loaded successfully");
			Hooks.navigate_to_URL();
			
			//Call Login_Page methods
			
			lp.EnterUsernameAndPassword();
			lp.sign_in_and_validate_login();
			Wait.wait5Second();
			
			//Call Fund submit page methods
			fp.navigate_to_fundSetup_page();
			fp.enter_fundName_and_Description();
			fp.choose_fund_Setup_details();    
			fp.add_period();
			fp.save_And_validate_fund_is_Created();
			lp.logout_user();
			
			Pass_Count=Pass_Count+1;
			Hooks.EndTest();

			}catch (Exception e) 
			{
				Fail_Count = Fail_Count+1;
				System.out.println("What is the error here: " + e.toString());
			}
			
			
		
		}
		
		//Hooks.EndTest();
		System.out.println("Pass Count ->" + Pass_Count);
		System.out.println("Fail Count ->" + Fail_Count);
	}
		
	@AfterTest
	public void end_test_fundSetup() throws Exception
	{
		
		closeReports();
		System.out.println("TEST COMPLETED");
	}


}
