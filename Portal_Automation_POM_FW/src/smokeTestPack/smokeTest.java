package smokeTestPack;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.test.database.DB_Test;
import com.test.excel.Excel_Read;
import com.test.hooks.Config;
import com.test.hooks.Hooks;
import com.test.hooks.Wait;
import com.test.pages.Approval_WorkFlow_Page;
import com.test.pages.Claim_submission_Page;
import com.test.pages.Fund_Allocation_Page;
import com.test.pages.Fund_Submit_Page;
import com.test.pages.L1_and_L2_Page;
import com.test.pages.Login_Page;
import com.test.pages.PA_Submission_Page;

public class smokeTest extends Hooks
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
			try 
			{
				Hooks.InitiateBrowser();
				Login_Page lp=new Login_Page(driver);
	
				Excel_Read.load_Excel_data(i);
				System.out.println("Excel Data loaded successfully");
				Hooks.navigate_to_URL();
				
				//Call Login_Page methods
				lp.EnterUsernameAndPassword();
				lp.sign_in_and_validate_login();
				Wait.wait5Second();

		        //Call Fund submit page methods
				Fund_Submit_Page fp=new Fund_Submit_Page(driver);
				fp.navigate_to_fundSetup_page();
				fp.enter_fundName_and_Description();
				fp.choose_fund_Setup_details();    
				fp.add_period();
				fp.save_And_validate_fund_is_Created();
			
			
				 //Fund Approve
				 Approval_WorkFlow_Page ap=new Approval_WorkFlow_Page(driver);
				 ap.navigate_to_ApprovalWorkFlow_tab();
				 ap.select_fund_and_publish();  
				 ap.emulate_user();
				 ap.navigate_to_fund_Approve_Tab();
				
			
				 //Fund Allocation
				 Fund_Allocation_Page  fap=new Fund_Allocation_Page(driver);
				 fap.navigate_to_fund_allocation_tab();
				 fap.add_Activity_And_details();
	
				
				//PA Submission Page methods
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
				try {
				fsp.Campaign_Tab();
				}catch (Exception e) 
				{
					System.out.println("Ignore campaign method");
				}
				
				fsp.Upload_file();
				fsp.PA_description_Tab();
				fsp.submit_Tab();
				Wait.wait7Second();
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
					
				
					//Claim Submission & Processing methods
					Wait.wait5Second();
					Claim_submission_Page  csp =new Claim_submission_Page(driver);
		
				   //Claim Submission 
				    csp.emulate_customer();
					csp.navigate_to_My_Funding_tab();
					csp.CheckBox_tab();
					csp.Select_Tab();
					csp.Claim_Description_Tab();
					csp.upload_Tab();
					csp.close_Tab();
					csp.Submit_Tab();
					csp.claimid_Tab(); 
				
				  
					//Claim Processing
					csp. user_emulation();
					csp.Administrator_Tab();
					csp.Claim_Tab();
					csp.Approved_Amount_Tab();
					csp.Held_Amount_Tab();
					csp.Denied_Amount_Tab();
					csp.Address1_Tab();
					csp.Address2_Tab();
					csp.Address_Tab();
					csp.DenialCodes();
					csp.save_Tab();
			
					System.out.println("The Final PA Number is: "+ Hooks.PA_Number);  
					
					/**
					 * EXECUTE INSERT QUERY FOR CLAIM APPROVAL IN L1 AND L2
					 */
				try 
					{
						Wait.wait2Second();
						csp.connectToDB();
						csp.Insert_ClaimNumber_in_Database();
						Wait.wait2Second();
						System.out.println("Insert claim into L1 and L2 Database Query is executed successfully");
					}catch (Exception e) {
						System.out.println("Insert query execution failed: "+ e);
					}
				
			
					/**
					 * @Verify L1 AND L2 ROLES
					 */
				
					
					L1_and_L2_Page l1_l2_p=new L1_and_L2_Page(driver);
					/**
					 * L1 USER
					 */
			
					l1_l2_p.emulate_L1_user();

					l1_l2_p.navigate_to_review_payment_tab_L1_User();
					l1_l2_p.choose_pre_payment_dropDown_and_Search();
					//approve l1 record based on claim number
					
					csp.approvedCheckBox();
					l1_l2_p.validate_L1_User_Approve_flow();  
					Wait.wait2Second();
					
					/**
					 * L2 USER
					 */
					
					l1_l2_p.emulate_L2_user();
					l1_l2_p.navigate_to_review_payment_tab_L2_User();
					l1_l2_p.choose_pre_payment_dropDown_and_Search();
					//approve l2 record based on claim number
					csp.approvedCheckBox_l2();
					l1_l2_p.validate_L2_User_Approve_flow();
					Wait.wait2Second();
				
				//Logout
				lp.logout_user();
				Pass_Count=Pass_Count+1;
				Hooks.EndTest();

			}catch (Exception e) 
			{
				captureScreenshot();
				Fail_Count = Fail_Count+1;
				e.printStackTrace();
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
