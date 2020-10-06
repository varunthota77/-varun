package com.test.suite;

	
	import org.testng.annotations.AfterTest;
	import org.testng.annotations.BeforeTest;
	import org.testng.annotations.Test;

	import com.test.excel.Excel_Read;
	import com.test.hooks.Config;
	import com.test.hooks.Hooks;
	import com.test.hooks.Wait;
	import com.test.pages.Claim_submission_Page;
	import com.test.pages.Login_Page;

	public class ClaimSubmission_Test extends Hooks
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
				
			
				
				
				//call method
				 Claim_submission_Page  fsp =new Claim_submission_Page(driver);
				fsp.emulate_customer();
				fsp.navigate_to_My_Funding_tab();
				fsp.CheckBox_tab();
				fsp.Select_Tab();
				fsp.Claim_Description_Tab();
				fsp.upload_Tab();
				fsp.close_Tab();
				fsp.Submit_Tab();
				fsp.claimid_Tab();
				fsp. user_emulation();
				fsp.Administrator_Tab();
				fsp.Claim_Tab();
				fsp.Approved_Amount_Tab();
				fsp.Held_Amount_Tab();
				fsp.Denied_Amount_Tab();
				fsp.Address1_Tab();
				fsp.Address2_Tab();
				fsp.Address_Tab();
				fsp.DenialCodes();
				fsp.save_Tab();
				
				
				
				
				//logout
				lp.logout_user();
				Wait.wait4Second();
				Pass_Count=Pass_Count+1;
				//Hooks.EndTest();

				}catch (Exception e) 
				{
					Fail_Count = Fail_Count+1;
					e.printStackTrace();
				}
			}
			
			System.out.println("Pass Count ->" + Pass_Count);
			System.out.println("Fail Count ->" + Fail_Count);
		}
			
		@AfterTest
		public void CloseTest() throws Exception
		{
			//Hooks.EndTest();
			Hooks.closeReports();
			System.out.println("TEST COMPLETED");
		}


	}


