package com.test.suite;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.test.database.DB_Test;
import com.test.excel.Excel_Read;
import com.test.hooks.Config;
import com.test.hooks.Hooks;
import com.test.hooks.Wait;
import com.test.pages.Claim_submission_Page;
import com.test.pages.L1_and_L2_Page;
import com.test.pages.Login_Page;
import com.test.pages.PA_Submission_Page;

public class PA_Submission_Test_04 extends Hooks
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
			PA_Submission_Page fsp =new PA_Submission_Page(driver);
			fsp.verify_check_box_assign_fund_key();
			fsp.emulate_customer();
			fsp.navigate_to_pre_approval_tab();
			fsp.Start_Date();
			fsp.Period_EndDate();
		    fsp.Activity_tab();
			fsp.Sub_Activity_tab();
			
			fsp.Publication_Tab();
		    fsp.selectproducts();
			
			fsp.Total_investment_Tab();
			fsp.Campaign_Tab();
			fsp.Upload_file();
		
			fsp.PA_description_Tab();
			
			/*try {
				fsp.Default_Questions();
				}catch (Exception e) 
				{
					System.out.println("Ignore campaign method");
				}*/
			
			fsp.submit_Tab();
			fsp.approvalid_Tab();
			fsp.Emulateuser();
			fsp.navigate_to__Administrator_Tab1();
			fsp.navigate_to__Pre_Approval_Requests_Tab();
			
			/**
			 * Database script
			 */
			
			//Update activity date using Database
			DB_Test.Update_Activity_Date_in_Database();
			System.out.println("Update Activity Date Database Query is executed successfully");
			
		/*	
			//call method
			Claim_submission_Page  csp =new Claim_submission_Page(driver);
			csp.emulate_customer();
			csp.navigate_to_My_Funding_tab();
			csp.CheckBox_tab();
			csp.Select_Tab();
			csp.Claim_Description_Tab();
			csp.upload_Tab();
			csp.close_Tab();
			csp.Submit_Tab();
			csp.claimid_Tab();
			csp. user_emulation();
			csp.Administrator_Tab();
			csp.Claim_Tab();
			csp.Approved_Amount_Tab();
			csp.Held_Amount_Tab();
			csp.Denied_Amount_Tab();
			csp.Address1_Tab();
			csp.Address2_Tab();
			csp.Address_Tab();
			csp.save_Tab();
			
		
			System.out.println("The Final PA Number is: "+ Hooks.PA_Number);
			
			*//**
			 * EXECUTE INSERT QUERY FOR CLAIM APPROVAL IN L1 AND L2
			 *//*
			
			DB_Test.Insert_ClaimNumber_in_Database();
			System.out.println("Insert claim into L1 and L2 Database Query is executed successfully");
			
			*//**
			 * @Verify L1 AND L2 ROLES
			 *//*
			L1_and_L2_Page l1_l2_p=new L1_and_L2_Page(driver);
			*//**
			 * L1 USER
			 *//*
	
			l1_l2_p.emulate_L1_user();
			l1_l2_p.navigate_to_review_payment_tab_L1_User();
			l1_l2_p.choose_pre_payment_dropDown_and_Search();
			csp.approvedCheckBox();
			l1_l2_p.validate_L1_User_Approve_flow();  
			Wait.wait2Second();
			
			*//**
			 * L2 USER
			 *//*
			l1_l2_p.emulate_L2_user();
			l1_l2_p.navigate_to_review_payment_tab_L2_User();
			l1_l2_p.choose_pre_payment_dropDown_and_Search();
			l1_l2_p.approvedCheckBox_l2();
			l1_l2_p.validate_L2_User_Approve_flow();
			Wait.wait2Second();
			*/
			
			//logout
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
