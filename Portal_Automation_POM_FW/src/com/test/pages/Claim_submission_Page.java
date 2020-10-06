package com.test.pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.LogStatus;
import com.test.database.DB_Test;
import com.test.hooks.Hooks;
import com.test.hooks.Wait;

public class Claim_submission_Page
{
	private static Wait wait;


	public RemoteWebDriver driver=null;
	
	
	public Claim_submission_Page(RemoteWebDriver driver)
	{
		this.driver=driver;	
		PageFactory.initElements( driver,this);
	}
	
	@FindBy(id="searchDealer")
	WebElement searchCustomer;
	Actions a;
	public void emulate_customer() throws Exception
	{
		Wait.wait4Second();
		searchCustomer.click();
		
		searchCustomer.sendKeys("Automation");
		Wait.wait_a_second();
		a=new Actions(driver);
		a.sendKeys(Keys.DOWN).build().perform();
		Wait.wait_a_second();
		a.sendKeys(Keys.ENTER).build().perform();
		
		Wait.wait5Second();
	}
	
	@FindBy(linkText="My Funding")
	WebElement My_Funding_tab;
	
	@FindBy(xpath="//span[text()='Submit Claim for Payment']")
	WebElement submit_claim_for_payment_Tab;
	
	@FindBy(xpath="//label[@id='label2472null']")
	WebElement Checkbox_Tab;

	@FindBy(xpath="//button[@class='skyblue_button ng-binding']")
	WebElement Select_Tab;
	
	
	@FindBy(xpath="//textarea[@name='Claim Description']")
	WebElement Claim_Description_Tab;

	@FindBy(xpath="(//input[@ng-click='selectfiles(parentIndex,$index)'])[2]")
	WebElement upload_Tab;

	@FindBy(xpath="//input[@id='uploadfile']")
	WebElement select_Tab;
	
	
	
	@FindBy(xpath="//button[@ng-click='uploadfiles()']")
	WebElement upload1_Tab;

	
	@FindBy(xpath="//button[@ng-click='CloseWindow()']")
	WebElement close_Tab;
	
	
	@FindBy(xpath="//button[@class='blueButton pull-right ng-binding']")
	WebElement Submit_Tab;
	
	@FindBy(xpath="//div[@class='ng-binding']")
	WebElement claimid_Tab;
	
	@FindBy(xpath="//input[@id='txtEmulateUserSearch']")
	WebElement user_emulation_Tab;
	
	@FindBy(linkText="Administration")
	WebElement Administrator_Tab;
	
	@FindBy(xpath="//span[text()='Claim Processing']")
	WebElement ClaimProcessing_Tab;
	
	
	
	@FindBy(xpath="(//input[@class='k-formatted-value k-input'])[3]")
	WebElement Approved_Amount_Tab;
	
	@FindBy(xpath="//*[@id=\"txtPromotionRemibursement\"]")
	WebElement Revised_Reimbursement_Amount;
	
	@FindBy(xpath="(//input[@class='k-formatted-value k-input'])[4]")
	WebElement Held_Amount_Tab;
	
	
	@FindBy(xpath="(//input[@style='display: inline-block;'])[10]")
	WebElement Denied_Amount_Tab;

	
	@FindBy(xpath="//input[@ng-model='ClaimHeader.ClaimPayToSendTos[1].Address1']")
	WebElement Address1_Tab;
	
	@FindBy(xpath="//input[@ng-model='ClaimHeader.ClaimPayToSendTos[1].Address2']")
	WebElement Address2_Tab;
	
	@FindBy(xpath="//input[@ng-model='ClaimHeader.ClaimPayToSendTos[0].Address1']")
	WebElement Address_Tab;
	
	@FindBy(xpath="//button[@ng-hide='isSubmittedClaim']")
	WebElement save_Tab;
	public void navigate_to_My_Funding_tab() throws Exception
	{
		Wait.wait_until_element_is_visible(My_Funding_tab);
		My_Funding_tab.click();
		submit_claim_for_payment_Tab.click();
		Wait.wait3Second();
	}
	
	@FindBy(name ="SearchPA")
	WebElement SearchPA_Number;
	
	
	@FindBy(xpath ="(//div[@id='Body']/div[2]/div/table/tbody/tr[1]/td[1]/span/label)[1]")
	WebElement firstRecordCheckBox;
	
	
	
	public void CheckBox_tab() throws Exception
	{
		//Search PA
		Wait.wait10Second();
		Wait.wait_until_element_is_visible(SearchPA_Number);
		SearchPA_Number.sendKeys(Hooks.PA_Number);//Hooks.PA_Number);
		Wait.wait_a_second();
		a.moveToElement(firstRecordCheckBox).click().build().perform();
		
	}
	public void Select_Tab() throws Exception
	{
		Wait.wait_until_element_is_visible( Select_Tab);
		Select_Tab.click();
		Wait.wait3Second();
	}

	public void Claim_Description_Tab() throws Exception
	{
		Wait.wait_until_element_is_visible(Claim_Description_Tab);
		Claim_Description_Tab.sendKeys();
		Claim_Description_Tab.sendKeys("Text");
		Wait.wait5Second();
	}

	


	public void upload_Tab() throws Exception
	{
		Wait.wait2Second();
		Wait.wait_until_element_is_visible(upload_Tab);
		upload_Tab.click();
		Wait.wait8Second();
		select_Tab.click();
		Wait.wait3Second();
		
		//Upload file
		Hooks.upload_File();
		
		//click on upload
		Wait.wait5Second();
		upload1_Tab.click();
		Wait.wait5Second();
		
	}
	public void close_Tab() throws Exception
	{
		Wait.wait_until_element_is_visible(close_Tab);
		close_Tab.click();
		Wait.wait2Second();
	}
	
	
	public void  Submit_Tab() throws Exception
	{
		Wait.wait_until_element_is_visible(Submit_Tab);
		Submit_Tab.click();
		Wait.wait5Second();
	}
	
	public String GetClaimNumber;
	public String[] convertedClaimNumber;
	public String Actual_ClaimNumber;
	
	public String claimid_Tab() throws Exception
	{
		Wait.wait10Second();
		try {
		Wait.wait_until_element_is_visible(claimid_Tab);
		Wait.wait10Second();
		GetClaimNumber =  claimid_Tab.getText();
		System.out.println("The Claim Number is: " + GetClaimNumber) ;
		convertedClaimNumber = GetClaimNumber.split(" ");
		}catch (Exception e) {
			System.out.println("Ignore");
		}
			
		Actual_ClaimNumber= convertedClaimNumber[1];
		//Actual_ClaimNumber= "38700";
		return Actual_ClaimNumber;
		
	}


	public void user_emulation() throws Exception
	{
		Wait.wait4Second();
		user_emulation_Tab.click();
		user_emulation_Tab.sendKeys("Automation");
		Wait.wait_a_second();
		a=new Actions(driver);
		a.sendKeys(Keys.DOWN).build().perform();
		Wait.wait_a_second();
		a.sendKeys(Keys.ENTER).build().perform();
		
		Wait.wait5Second();
	}
	
	public void Administrator_Tab() throws Exception
	{	Wait.wait3Second();
		Wait.wait_until_element_is_visible(Administrator_Tab);
		
		Administrator_Tab.click();
		Wait.wait_a_second();
		ClaimProcessing_Tab.click();
		Wait.wait3Second();
	}
	
	@FindBy(name ="searchClaim")
	WebElement searchClaim;
	
	@FindBy(xpath="//a[@ng-bind='item.ClaimNumber']")
	WebElement Claim_Tab;
	
	public void Claim_Tab() throws Exception
	{
		claimid_Tab();
		try {
		Wait.wait5Second();
		driver.navigate().refresh();
		Wait.wait15Second();
		Wait.wait5Second();
		driver.navigate().refresh();
		Wait.wait15Second();
		Wait.wait_until_element_is_visible(searchClaim);
		searchClaim.sendKeys(Actual_ClaimNumber);
		Wait.wait_a_second();
		Claim_Tab.click();
		Wait.wait5Second();
		}catch(Exception e)
		{
			Wait.wait10Second();
			driver.navigate().refresh();
			Wait.wait15Second();
			driver.navigate().refresh();
			Wait.wait8Second();
			Wait.wait_until_element_is_visible(searchClaim);
			searchClaim.sendKeys(Actual_ClaimNumber);
			Wait.wait_a_second();

			Claim_Tab.click();
			Wait.wait5Second();
		}
		System.out.println("Claim Processing is working fine");
	}

	
	
	public void Approved_Amount_Tab() throws Exception
	{
		Wait.wait4Second();
		Wait.wait_until_element_is_visible(Approved_Amount_Tab);
		String revisedAmount = Revised_Reimbursement_Amount.getAttribute("aria-valuenow");
		System.out.println("The revise total amount is: "+ revisedAmount);
		Wait.wait2Second();
		//Approved_Amount_Tab.clear();
		Wait.wait2Second();
		Approved_Amount_Tab.sendKeys(revisedAmount);
		Wait.wait2Second();
	}

	public void  Held_Amount_Tab() throws Exception
	{/*
		Wait.wait2Second();
		Wait.wait_until_element_is_visible(Held_Amount_Tab);
		Held_Amount_Tab.sendKeys("2");
		Wait.wait2Second();*/
	}

	public void  Denied_Amount_Tab() throws Exception
	{
		Wait.wait2Second();
		/*Wait.wait_until_element_is_visible(Denied_Amount_Tab);
		Denied_Amount_Tab.sendKeys("2");
		Wait.wait2Second();*/
	}

	
	public void Address1_Tab() throws Exception
	{
		Wait.wait2Second();
		Wait.wait_until_element_is_visible(Address1_Tab);
		Address1_Tab.sendKeys("usa");
		Wait.wait2Second();
	
}
	
	public void Address2_Tab() throws Exception
	{
		Wait.wait2Second();
		Wait.wait_until_element_is_visible(Address2_Tab);
		Address2_Tab.sendKeys("Newyork");
		Wait.wait2Second();
}


	public void Address_Tab() throws Exception
	{
		Wait.wait_until_element_is_visible(Address_Tab);
		Address_Tab.sendKeys();
		Address_Tab.sendKeys("Text",Keys.ENTER);
		Wait.wait5Second();	
	}


	@FindBy(xpath ="(//button[@class='grayButton width100 ng-binding'])[2]")
	WebElement DenialCodes;
	
	@FindBy(xpath ="//input[@ng-model='item.isSelected']")
	WebElement DenialCodes_select;
	
	@FindBy(xpath ="//button[@ng-click='saveClick()']")
	WebElement DenialCodes_save;
	
	public void DenialCodes() throws Exception
	{
		Wait.wait2Second();
		/*Wait.wait_until_element_is_visible(DenialCodes);
		DenialCodes.click();
		Wait.wait2Second();
		DenialCodes_select.click();
		Wait.wait3Second();
		DenialCodes_save.click();
		Wait.wait3Second();*/
		System.out.println("");
	}

	
	public void save_Tab() throws Exception
	{
		System.out.println("The Claim Number in save : "+ Actual_ClaimNumber);
		Wait.wait_until_element_is_visible(save_Tab);
		save_Tab.click();
		Wait.wait5Second();
	}
	
	/**
	 * Database query for Insert record into L1 & L2
	 */


	
	public Statement connectToDB() throws SQLException
	{
		
		System.out.println("The Claim NUmber in connectDb : "+ Actual_ClaimNumber);
		Connection con=DriverManager.getConnection("jdbc:sqlserver://"+DB_Test.host+":"+DB_Test.portNumber+";databasename="+DB_Test.DBName,DB_Test.uname,DB_Test.pass);
		Statement st=con.createStatement();
		return st;
	}

	public String UpdateClaimNumber; 
	public void Insert_ClaimNumber_in_Database() throws SQLException, InterruptedException 
	{
		 UpdateClaimNumber ="DECLARE @orgUnitId bigint=1, @objectId bigint="+Actual_ClaimNumber+",\r\n" + 
				"@objectType int=3,@eventType int=2,@workflowId bigint,\r\n" + 
				"@levelNumber int,@creationDate datetime=GETDATE(),@lastModifiedDate datetime,\r\n" + 
				"@isComplete bit=0,@UserCulture varchar(10)='en-US',@approvalprocessGroupId bigint\r\n" + 
				"INSERT INTO TObjectWorkflow \r\n" + 
				"([orgUnitId],[objectId],[objectType],[eventType],[workflowId],[levelNumber]\r\n" + 
				",[creationDate],[lastModifiedDate],[isComplete],[UserCulture],[approvalprocessGroupId])\r\n" + 
				"VALUES(@orgUnitId,@objectId,@objectType,@eventType,@workflowId,@levelNumber, \r\n" + 
				"@creationDate,@lastModifiedDate,@isComplete,@UserCulture,@approvalprocessGroupId)";
		Statement st= connectToDB();
		System.out.println("Database Script is Initiated");
		st.executeUpdate(UpdateClaimNumber);
		System.out.println("Claim Number is Inserted Database");
			
	}

	/*public  String  claim_Number() throws Exception
	{
		calimVerify();
		String app1 = actualClaim;
		System.out.println("Actual Claim Number from claim calimNumberMethod: "+ app1);
		return app1;
		
	}
	*/
	//L1 AND L2 APPROVE

	@FindBy(xpath= "//*[@id=\"blockContent\"]/div[2]/div[2]/div/span[1]/span/span[1]")
	WebElement select_pre_payment_dropDown;
	
	@FindBy(xpath ="//button[text()='Search']")
	WebElement SearchButton;
	
	public void approvedCheckBox() throws Exception
	{
		claimid_Tab();
		System.out.println("Actual Claim Numberfrom L1 : "+ Actual_ClaimNumber);
		
		try {
		
		WebElement Approved_Except_For_CheckBox = driver.findElement(By.xpath("//span[text()='"+Actual_ClaimNumber+"']/following::td[8]/input"));

		if(Approved_Except_For_CheckBox.isSelected())
		{
			System.out.println("L1 User Approved check box is already selected");
			Hooks.et.log(LogStatus.INFO,"L1 User Approved check box is already selected");
		}
		else
		{
			Wait.wait2Second();
			Approved_Except_For_CheckBox.click();
			System.out.println("L1 User Approved check box is selected");
			Hooks.et.log(LogStatus.INFO,"L1 User Approved check box is selected");
		}
		}catch (Exception e) {
			//refresh and try again
			Wait.wait5Second();
			driver.navigate().refresh();
			Wait.wait7Second();
			driver.navigate().refresh();
			Wait.wait7Second();
			Wait.wait_until_element_is_visible(select_pre_payment_dropDown);
			select_pre_payment_dropDown.click();
			Hooks.Pick_Value("US");
			Wait.wait_a_second();
			SearchButton.click();
			Wait.wait5Second();
		
			WebElement Approved_Except_For_CheckBox = driver.findElement(By.xpath("//span[text()='"+Actual_ClaimNumber+"']/following::td[8]/input"));

			if(Approved_Except_For_CheckBox.isSelected())
			{
				System.out.println("L1 User Approved check box is already selected");
				Hooks.et.log(LogStatus.INFO,"L1 User Approved check box is already selected");
			}
			else
			{
				Wait.wait2Second();
				Approved_Except_For_CheckBox.click();
				System.out.println("L1 User Approved check box is selected");
				Hooks.et.log(LogStatus.INFO,"L1 User Approved check box is selected");
			}
		}
	}
	
	public void approvedCheckBox_l2() throws Exception
	{
		claimid_Tab();
		try {
		Wait.wait4Second();
		WebElement Approved_Except_For_CheckBox = driver.findElement(By.xpath("//span[text()='"+Actual_ClaimNumber+"']/following::td[8]/input"));

		if(Approved_Except_For_CheckBox.isSelected())
		{
			System.out.println("L2 User Approved check box is already selected");
			Hooks.et.log(LogStatus.INFO,"L2 User Approved check box is already selected");
		}
		else
		{
			Wait.wait2Second();
			Approved_Except_For_CheckBox.click();
			System.out.println("L2 User Approved check box is selected");
			Hooks.et.log(LogStatus.INFO,"L2 User Approved check box is selected");
		}
		}catch (Exception e) {
			
		
			//refresh and try again
			Wait.wait5Second();
			driver.navigate().refresh();
			Wait.wait7Second();
			driver.navigate().refresh();
			Wait.wait7Second();
			Wait.wait_until_element_is_visible(select_pre_payment_dropDown);
			select_pre_payment_dropDown.click();
			Hooks.Pick_Value("US");
			Wait.wait_a_second();
			SearchButton.click();
			Wait.wait5Second();
			
			WebElement Approved_Except_For_CheckBox = driver.findElement(By.xpath("//span[text()='"+Actual_ClaimNumber+"']/following::td[8]/input"));
			if(Approved_Except_For_CheckBox.isSelected())
			{
				System.out.println("L2 User Approved check box is already selected");
				Hooks.et.log(LogStatus.INFO,"L2 User Approved check box is already selected");
			}
			else
			{
				Wait.wait2Second();
				Approved_Except_For_CheckBox.click();
				System.out.println("L2 User Approved check box is selected");
				Hooks.et.log(LogStatus.INFO,"L2 User Approved check box is selected");
			}
		}
	}
	
	
	
	
	
}