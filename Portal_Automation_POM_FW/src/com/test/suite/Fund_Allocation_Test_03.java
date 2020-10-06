package com.test.suite;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.test.excel.Excel_Read;
import com.test.hooks.Config;
import com.test.hooks.Hooks;
import com.test.hooks.Wait;
import com.test.pages.Approval_WorkFlow_Page;
import com.test.pages.Fund_Allocation_Page;
import com.test.pages.Login_Page;

public class Fund_Allocation_Test_03 extends Hooks
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
				Fund_Allocation_Page  fp=new Fund_Allocation_Page(driver);
	
				Excel_Read.load_Excel_data(i);
				System.out.println("Excel Data loaded successfully");
				Hooks.navigate_to_URL();
				
				//Call Login_Page methods
				
				lp.EnterUsernameAndPassword();
				lp.sign_in_and_validate_login();
				Wait.wait5Second();
				
				//Call Fund Allocation page methods
				fp.navigate_to_fund_allocation_tab();
				fp.add_Activity_And_details();
				
				
				//Logout
				lp.logout_user();
				Pass_Count=Pass_Count+1;
				Hooks.EndTest();

			}catch (Exception e) 
			{
				captureScreenshot();
				Fail_Count = Fail_Count+1;
				System.out.println("What is the error here: " + e.toString());
			}
	
		
		}
		System.out.println("Pass Count ->" + Pass_Count);
		System.out.println("Fail Count ->" + Fail_Count);
	}
		
	@AfterTest
	public void end_test_fundSetup() throws Exception
	{
		Hooks.closeReports();
		System.out.println("TEST COMPLETED");
	}


}
