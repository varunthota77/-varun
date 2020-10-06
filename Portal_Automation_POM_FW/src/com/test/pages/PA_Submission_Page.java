package com.test.pages;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.exec.ExecuteException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.DownAction;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.excel.Excel_Read;
import com.test.hooks.Hooks;
import com.test.hooks.Wait;

public class PA_Submission_Page 
{

	public static Wait wait;


	public RemoteWebDriver driver=null;
	

	public PA_Submission_Page(RemoteWebDriver driver)
	{
	this.driver=driver;	
	PageFactory.initElements( driver,this);
	}
	
	
	
	
	@FindBy(linkText="Administration")
	WebElement Administrator_Tab;
	
	
	@FindBy(xpath="//span[text()='Fund Allocation']")
	WebElement Fund_Allocation_Tab1;

	@FindBy(linkText="Admin Configs")
	WebElement Admin_Configs_Tab;
	
	
	@FindBy(linkText="PA")
	WebElement PA_Tab;
	
	
	@FindBy(linkText="PM-IQense")
    WebElement PMIQense_Tab;
	
	@FindBy(xpath="(//input[@type='checkbox'])[19]")
	WebElement checkBox;
	
	@FindBy(xpath="(//button[@class='skyblue_button pull-right buttonwidth_inherit media440_floatleft ng-binding'])[2]")
	WebElement newSave_Tab;
	
	@FindBy(xpath ="//img[@class='client_logo_image']")
	WebElement homeButton;
	
	/**
	 * 
	 * APPROVER FLOW
	 * 
	 */
	
	public void verify_check_box_assign_fund_key() throws  Exception
	{
		Wait.wait_until_element_is_visible(Administrator_Tab);
		Administrator_Tab.click();
		Wait.wait2Second();
		Admin_Configs_Tab.click();
		Wait.wait2Second();
		PA_Tab.click();
		Wait.wait2Second();
		Wait.wait_until_element_is_visible(PMIQense_Tab);
		PMIQense_Tab.click();

		Wait.wait2Second();
		try {if(checkBox.isEnabled())
		{
			checkBox.click();
			System.out.println("Checkbox setting is Off");
		}
			
		}catch (Exception e) {
		System.out.println("Check box is default disabled");
		}
		

		Wait.wait_until_element_is_visible(newSave_Tab);
		newSave_Tab.click();

		Wait.wait5Second();
		//back to home
		homeButton.click();
		Wait.wait8Second();
	}
	
	
		
	//Locators
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
		Wait.wait8Second();
		
	}
	
	
	@FindBy(linkText="My Funding")
	WebElement My_Funding_tab;
	
	@FindBy(xpath="//span[text()='Submit a Pre-Approval']")
	WebElement Pre_Approval_Tab;
	
	public void navigate_to_pre_approval_tab() throws Exception
	{
		Wait.wait_until_element_is_visible(My_Funding_tab);
		Wait.wait10Second();
		My_Funding_tab.click();
		Wait.wait_a_second();
		Pre_Approval_Tab.click();
		Wait.wait10Second();
	}
	
	
	
	@FindBy(xpath = "//div[@ng-class='item.selectFundclass']/span")
	WebElement selectFund;

	@FindBy(xpath="//input[@id='PeriodStartDate0_0']")
	WebElement Start_Date;

	@FindBy(xpath="//input[@id='PeriodEndDate0_0']")
	WebElement Period_EndDate;

	@FindBy(xpath="//label[@ng-class='item.mediaClass']/following::span[1]/span/span[1]")
	WebElement Activity_Tab;
	
	
	
	@FindBy(xpath="//label[@ng-class='item.submediaClass']/following::span[1]")
	WebElement SubActivity_Tab;
	
	

	@FindBy(xpath="//input[@ng-model='item.PublicationName']")
	WebElement Publication_Tab;

	@FindBy(xpath="//button[@class='grayButton display_Block width100 ng-binding']")
	WebElement SelectProduct_Tab;
	
	@FindBy(xpath="//label[@for='ChkCheckbox_0_1']")
	WebElement Products_Tab;
	
	
	@FindBy(xpath="//button[@ng-hide='isReadOnly']")
	WebElement save_Tab;
	
	
	
	@FindBy(xpath="//input[@class='k-formatted-value text-right ng-scope k-input']")
	WebElement Total_investment_Tab;

	@FindBy(xpath="(//span[@class='k-input ng-scope'])[4]")
	WebElement Campaign_Tab;

	//@FindBy(xpath="//input[@name='email.EmailId']")
	//WebElement EmailAddress_Tab;

	@FindBy(xpath="//textarea[@ng-readonly='isPASaved']")
	WebElement PA_description_Tab;
	
	
	@FindBy(xpath="(//input[@class='grayButton'])[2]")
	WebElement Upload_Tab;
	

	@FindBy(xpath="//input[@id=\"uploadfile\"]")
	WebElement Select_file_btn;
	
	
	@FindBy(xpath="//button[@ng-click='uploadfiles()']")
	WebElement Upload_Tab1;
	
	@FindBy(xpath="(//button[@type='button'])[7]")
	WebElement close_Tab;
	
	@FindBy(xpath="(//button[@class='blueButton pull-right submit_button_72 ng-binding'])[1]")
	WebElement submit_Tab;
	
	
	String  Execution_Date= null;
	
	
	public void Start_Date() throws InterruptedException
	{
		 Wait.wait10Second();
		 Wait.wait_until_element_is_visible(selectFund);
		 selectFund.sendKeys(Excel_Read.FundName +Keys.ENTER);
		 wait.wait2Second();
		 DateFormat format =new java.text.SimpleDateFormat("MM/dd/YYYY");
	 	 Date date =new Date();
		 Execution_Date =format.format(date);
		
		 System.out.println("Today Date IS : " + Execution_Date );
		 
		 Start_Date.sendKeys(Execution_Date);
			Wait.wait_a_second();

			
	}

	
	public void Period_EndDate() throws Exception
	{
		Wait.wait2Second();
		Period_EndDate.click();
		Period_EndDate.sendKeys(Execution_Date);
	    wait.wait2Second();

	}
	

	
	public void Activity_tab() throws Exception
	{
		Wait.wait4Second();
		Activity_Tab.click();
		a=new Actions(driver);
		a.sendKeys(Keys.DOWN).build().perform();
		Wait.wait_a_second();
		a.sendKeys(Keys.ENTER).build().perform();
		
		Wait.wait2Second();
	}
	
	
	
	public void Sub_Activity_tab() throws Exception
	{
		Wait.wait7Second();
		SubActivity_Tab.click();
		Wait.wait2Second();
		a=new Actions(driver);
		a.sendKeys(Keys.DOWN).build().perform();
		Wait.wait_a_second();
		a.sendKeys(Keys.ENTER).build().perform();
		
		Wait.wait2Second();
	}
	public void Publication_Tab() throws Exception
	{
		Wait.wait2Second();
		Wait.wait_until_element_is_visible(Publication_Tab);
		Publication_Tab.sendKeys("Text");
		Wait.wait2Second();
	}
	
	
	public void selectproducts() throws Exception
	{
		try {
		//click on product tab
		Wait.wait2Second();
		SelectProduct_Tab.click();
		Wait.wait3Second();
		a.moveToElement(driver.findElement(By.xpath("//span[@class='break_word line_height_15 checkBoxGreeen']//label "))).click().build().perform();
		Wait.wait2Second();
		Wait.wait_until_element_is_visible(save_Tab);
		save_Tab.click();
		Wait.wait2Second();
		}catch (Exception e) 
		{
			System.out.println("Ignore");
		}
	}
	
	
	public void Total_investment_Tab() throws Exception
	{
		Wait.wait_until_element_is_visible(Total_investment_Tab);
	
		Total_investment_Tab.sendKeys("100",Keys.ENTER);
		Wait.wait5Second();

}
	public void  Campaign_Tab() throws Exception
	{
		Wait.wait4Second();
		Campaign_Tab.click();
		a=new Actions(driver);
		a.sendKeys(Keys.DOWN).build().perform();
		Wait.wait_a_second();
		a.sendKeys(Keys.ENTER).build().perform();
		Wait.wait2Second();
	}
	/*public void  EmailAddress_Tab() throws Exception
	{
		Wait.wait_until_element_is_visible( EmailAddress_Tab);
		 EmailAddress_Tab.sendKeys();
		 EmailAddress_Tab.sendKeys("lavan.sridhara95@gmail.com",Keys.ENTER);
		Wait.wait5Second();
	}*/	

	public void PA_description_Tab() throws Exception
	{
		Wait.wait4Second();

		Wait.wait_until_element_is_visible(PA_description_Tab);
		PA_description_Tab.sendKeys("Text");
		Wait.wait2Second();
	}
	
	public void Upload_file() throws Exception
	{
		Wait.wait5Second();
		Upload_Tab.click();
		Wait.wait3Second();
		Select_file_btn.click();
		Wait.wait7Second();
	
		
		//Upload file
		Hooks.upload_File();
		
		//click on upload
		Wait.wait5Second();
		Upload_Tab1.click();
		Wait.wait8Second();
		Wait.wait_until_element_is_visible(close_Tab);
		close_Tab.click();
		Wait.wait3Second();
	}
public static String PA_number;

	@FindBy(xpath="//input[@id='dpdatepicker']")
	WebElement Date_picker_Tab;
	@FindBy(xpath="//input[@class='k-formatted-value k-input']")
	WebElement Mass_vendor_Tab;
	
	@FindBy(xpath="//input[@id='txtHyperlink']")
	WebElement Location_Tab;
	
	
	@FindBy(xpath="//textarea[@ng-change='ValidateTextArea(item)']")
	WebElement Textarea_Tab;

	public void Default_Questions() throws Exception
	{

		Start_Date.sendKeys(Execution_Date);
		Wait.wait_a_second();
		
		Mass_vendor_Tab.sendKeys("20");
		Wait.wait8Second();
		
		Location_Tab.sendKeys("USA");
		Wait.wait8Second();
		
		Textarea_Tab.sendKeys("Text");
		Wait.wait8Second();

	}

	
		public void submit_Tab() throws Exception
		{
			
			Wait.wait_until_element_is_visible(submit_Tab);
			submit_Tab.click();
			Wait.wait8Second();			
		
		}
	
		
		@FindBy(xpath="//b[@class='ng-binding ng-scope']")
		WebElement approvalid_Tab;
		
		
		
		public void approvalid_Tab() throws Exception
		{
			Wait.wait2Second();
			Wait.wait_until_element_is_visible(approvalid_Tab);
			Hooks.PA_Number =  approvalid_Tab.getText();
			System.out.println("The PA Number is: " + Hooks.PA_Number) ;
			Wait.wait5Second();
		}
		

		@FindBy(id="txtEmulateUserSearch")
		WebElement Emulate_Search;
		
		@FindBy(id="ClearEmulateUserimg")
		WebElement Clear_Emulate;
		
	
		public void Emulateuser() throws Exception
		{
			Wait.wait4Second();
			Emulate_Search.click();
			Emulate_Search.sendKeys("automation user");
			Wait.wait_a_second();
			a=new Actions(driver);
			a.sendKeys(Keys.DOWN).build().perform();
			Wait.wait_a_second();
			a.sendKeys(Keys.ENTER).build().perform();
			Wait.wait10Second();
		}
		
	@FindBy(linkText="Administration")
	WebElement Administrator_Tab1;
	
	
	@FindBy(xpath="//span[text()='Approve Pre-Approval Requests']")
	WebElement Pre_Approval_Requests_Tab;

	public void navigate_to__Administrator_Tab1() throws  Exception
	{
		Wait.wait5Second();
		driver.navigate().refresh();
		Wait.wait10Second();
		Wait.wait_until_element_is_visible(Administrator_Tab1);
		Administrator_Tab1.click();
		Wait.wait2Second();
		//Admin_Configs_Tab.click();
	}

	/*@FindBy(xpath="//button[text()='Submit']")
	WebElement SubmitButton;*/
	
	@FindBy(xpath="//label[text()='Successfully Updated']")
	WebElement SuccessMessage;
	
	@FindBy(xpath="//button[text()='Yes']")
	WebElement SuccessMessage_PopUp_Yes;
	
	@FindBy(xpath="//button[text()='Ok']")
	WebElement SuccessMessage_PopUp_Ok;
	
	public void navigate_to__Pre_Approval_Requests_Tab() throws  Exception
	{
		Wait.wait_until_element_is_visible(Pre_Approval_Requests_Tab);
		Pre_Approval_Requests_Tab.click();
		Wait.wait5Second();
		
		//click on approve checkbox
		String x = "Pre-Approval #:  "+Hooks.PA_Number+"";
		try {
			driver.navigate().refresh();
			Wait.wait15Second();
			driver.navigate().refresh();
			Wait.wait8Second();
			try {
				driver.findElement(By.xpath("//a[text()='Next']/following::span[1]/span")).click();
				a.sendKeys("3",Keys.ENTER).build().perform();
				Wait.wait5Second();
				}catch (Exception e1) {
					System.out.println("No pagination found");
				}
			WebElement Approve_checkBox = driver.findElement(By.xpath("(//span[text()='"+x+"']/following::div[2]/div/div[2]/div[2]/div/input)[1]"));
			Approve_checkBox.click();
			Wait.wait4Second();
			
		}catch (Exception e) 
		{
			Wait.wait5Second();
			driver.navigate().refresh();
			Wait.wait15Second();
			
			driver.navigate().refresh();
			Wait.wait15Second();
			driver.navigate().refresh();
			Wait.wait15Second();
			try {
			driver.findElement(By.xpath("//a[text()='Next']/following::span[1]/span")).click();
			a.sendKeys("3",Keys.ENTER).build().perform();
			Wait.wait5Second();
			}catch (Exception e1) {
				System.out.println("No pagination found");
			}
			WebElement Approve_checkBox = driver.findElement(By.xpath("(//span[text()='"+x+"']/following::div[2]/div/div[2]/div[2]/div/input)[1]"));
			Approve_checkBox.click();
			Wait.wait4Second();
		}

		    WebElement SubmitButton = driver.findElement(By.xpath("//span[text()='"+x+"']/following::div[2]/div/div[10]/button"));
			SubmitButton.click();
			Wait.wait8Second();
			try {
			Hooks.VerifyByElement(SuccessMessage, "Approved PA Number", "Verification Failed at Approved PA Number");
			SuccessMessage_PopUp_Ok.click();
			}catch (Exception e) {
				Wait.wait7Second();
				SuccessMessage_PopUp_Ok.click();
			}
			Wait.wait7Second();
			System.out.println("PA Submission is Done");
			
			
			//clear emulation
			Wait.wait2Second();
			Clear_Emulate.click();
			Wait.wait8Second();
	}
	
}
