package com.test.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.test.excel.Excel_Read;
import com.test.hooks.Hooks;
import com.test.hooks.Wait;

public class Fund_Submit_Page 
{
	
	public RemoteWebDriver driver=null;

	public Fund_Submit_Page(RemoteWebDriver driver)
	{
		this.driver=driver;	
		PageFactory.initElements( driver,this);
	}
	
	
	@FindBy(linkText="Administration")
	WebElement Administrator_Tab;
	
	
	@FindBy(linkText="Fund Set-up")
	WebElement FundSetup_Tab;
	
	public void navigate_to_fundSetup_page() throws Exception
	{
		Wait.wait_until_element_is_visible(Administrator_Tab);
		Administrator_Tab.click();
		Wait.wait_a_second();
		FundSetup_Tab.click();
		Wait.wait4Second();
	}
	
	@FindBy(id="txtFundName")
	WebElement FundName;
	
	
	@FindBy(id="txtfunddescription")
	WebElement FundDescription;
	
	public void enter_fundName_and_Description() throws Exception
	{
		FundName.sendKeys(Excel_Read.FundName);
		FundDescription.sendKeys(Excel_Read.FundDesc);
		Wait.wait2Second();
	}
	
	
	@FindBy(xpath="//span[@aria-owns='FundingModel_listbox']/span/span[1]")
	WebElement FundingModel_dropDown;
	
	
	@FindBy(xpath="//label[@ng-class='ReserveBalanceAtClass']/following::span[1]/span/span[1]")
	WebElement ReserveBalance_dropDown;
	
	@FindBy(xpath="//label[@ng-class='ReimbursementByClass']/following::span[1]/span/span[1]")
	WebElement Reimbursement_dropDown;
	
	
	@FindBy(xpath="//label[@ng-class='PromotionByClass']/following::span[1]/span/span[1]")
	WebElement Promotion_dropDown;
	
	@FindBy(xpath="//label[@ng-class='FundTypeClass']/following::span[1]/span/span[1]")
	WebElement FundType_dropDown;
	
	@FindBy(xpath="//label[@ng-class='CountryCodeClass']/following::span[1]/span/span[1]")
	WebElement Country_dropDown;
	
	@FindBy(xpath="//label[@ng-class='CurrencyCodeClass']/following::span[1]/span/span[1]")
	WebElement Currency_dropDown;
	
	@FindBy(xpath="//label[@ng-class='ReimbursementMethodClass']/following::span[1]/span/span[1]")
	WebElement ReimbursmentMethod_dropDown;
	
	@FindBy(xpath="//label[@ng-class='FundSetupAmountClass']/following::span[1]/span/input[1]")
	WebElement Fund_Budget_Amount;
	
	@FindBy(xpath="//label[@ng-class='PaymentPercentageClass']/following::span[1]/span/input[1]")
	WebElement Payment_Percentage;
	
	@FindBy(xpath="//label[@ng-class='FundPeriodAssignmentClass']/following::span[1]/span/span[1]")
	WebElement FundPeriod_dropDown;
	
	@FindBy(xpath="//label[@ng-class='ControlMaximumActivityEndDatebyClass']/following::span[1]/span/span[1]")
	WebElement Control_Max_dropDown;
	
	
	
	@FindBy(id="fundStartDate")
	WebElement fundStartDate;
	
	@FindBy(xpath="//input[@ng-model='EndDate']")
	WebElement fundEndDate;

	@FindBy(id="avilablemediaDetails")
	WebElement Select_Media;
	
	@FindBy(xpath ="//button[@ng-click='addMediastoSelectedList()']")
	WebElement Add_media_Icon;
	
	@FindBy(id="avilableProductDetails")
	WebElement Select_Product;
	
	@FindBy(xpath ="//button[@ng-click='addProducttoSelectedList()']")
	WebElement Add_Product_Icon;
	
	@FindBy(name="ViewonWeb")
	WebElement Radio_View_on_Web_Yes;
	
	@FindBy(name="ViewonReport")
	WebElement Radio_View_on_Report_Yes;
	
	@FindBy(name="ViewonSnapshot")
	WebElement Radio_View_on_Sanpshot_Yes;
	
	@FindBy(xpath="//*[text()='Period Measure']/following::span[1]/span/span[1]")
	WebElement Choose_Period_Measure;
	
	public void choose_fund_Setup_details() throws Exception
	{
		//fund model
		FundingModel_dropDown.click();
		Wait.wait_a_second();
		Hooks.Pick_Value(Excel_Read.Funding_Model);
		Wait.wait_a_second();
		
		//Reserve model
		//Hooks.select_value_from_DropDown(ReserveBalance_dropDown,Excel_Read.Reserve_Balance);
		ReserveBalance_dropDown.click();
		Wait.wait_a_second();
		Hooks.Pick_Value(Excel_Read.Reserve_Balance);
		Wait.wait_a_second();

		//Reimbursement drop down
		//Hooks.select_value_from_DropDown(Reimbursement_dropDown,Excel_Read.Reimbursement);
		Reimbursement_dropDown.click();
		Wait.wait_a_second();
		Hooks.Pick_Value(Excel_Read.Reimbursement);
		Wait.wait_a_second();

		//Promotion
		//Hooks.select_value_from_DropDown(Promotion_dropDown,Excel_Read.Promotion);
		Promotion_dropDown.click();
		Wait.wait_a_second();
		Hooks.Pick_Value(Excel_Read.Promotion);
		Wait.wait_a_second();


		//Fund Type
		//Hooks.select_value_from_DropDown(FundType_dropDown,Excel_Read.Fund_Type);
		Wait.wait2Second();
		FundType_dropDown.click();
		Wait.wait_a_second();
		Hooks.Pick_Value(Excel_Read.Fund_Type);
		Wait.wait_a_second();

		//Country 
		//Hooks.select_value_from_DropDown(Country_dropDown,Excel_Read.Country);
		Country_dropDown.click();
		Wait.wait_a_second();
		Hooks.Pick_Value(Excel_Read.Country);
		Wait.wait4Second();

		//Currency
		//Hooks.select_value_from_DropDown(Currency_dropDown,Excel_Read.Currency);
		Currency_dropDown.click();
		Wait.wait_a_second();
		Hooks.Pick_Value(Excel_Read.Currency);
		Wait.wait_a_second();
		
		//Reimbursement method
		//Hooks.select_value_from_DropDown(ReimbursmentMethod_dropDown,Excel_Read.Reimbursement_method);
		ReimbursmentMethod_dropDown.click();
		Wait.wait_a_second();
		Hooks.Pick_Value(Excel_Read.Reimbursement_method);
		Wait.wait_a_second();

		//Fund period assignment
		//Hooks.select_value_from_DropDown(FundPeriod_dropDown,Excel_Read.FundPeriod_Assignment);
		FundPeriod_dropDown.click();
		Wait.wait_a_second();
		Hooks.Pick_Value(Excel_Read.FundPeriod_Assignment);
		Wait.wait_a_second();
		
		
/*		//Fund period assignment
		//Hooks.select_value_from_DropDown(FundPeriod_dropDown,Excel_Read.FundPeriod_Assignment);
		Control_Max_dropDown.click();
		Wait.wait_a_second();
		Hooks.Pick_Value("F");
		Wait.wait_a_second();
				*/
		
		//Start and End date
		Wait.wait2Second();
		fundStartDate.sendKeys(Excel_Read.Start_Date);
		fundEndDate.sendKeys(Excel_Read.End_date);
		
		//Enter budget
		Wait.wait2Second();
		Fund_Budget_Amount.click();
		Wait.wait_a_second();
		Hooks.a = new Actions(driver);
		Hooks.a.sendKeys(Excel_Read.Fund_Budget_Amount).build().perform();
		Wait.wait_a_second();
		try {
		Payment_Percentage.clear();
		Payment_Percentage.sendKeys(Excel_Read.Payment);
		}catch (Exception e) {
			System.out.println("Payment not displayed");
		}
		//Hooks.select_value_from_DropDown(Select_Media, "Advertising");
		driver.findElement(By.xpath("//*[@id=\"avilablemediaDetails\"]/option[1]")).click();
		Actions a1=new Actions(driver);
		a1.sendKeys(Keys.CONTROL, "a").build().perform();
		Add_media_Icon.click();
		Wait.wait_a_second();
		//Hooks.select_value_from_DropDown(Select_Product, "All - General Marketing");
		driver.findElement(By.xpath("//*[@id=\"avilableProductDetails\"]/option[1]")).click();
		//driver.findElement(By.xpath("//*[@id=\"avilableProductDetails\"]/option[2]")).click();

		a1.sendKeys(Keys.CONTROL, "A").build().perform();
		Add_Product_Icon.click();
		//Select radio buttons
		Wait.wait2Second();
		Radio_View_on_Web_Yes.click();
		Radio_View_on_Report_Yes.click();
		Radio_View_on_Sanpshot_Yes.click();
		Wait.wait_a_second();
		
		//select period 
		Choose_Period_Measure.click();
		Wait.wait_a_second();
		Hooks.Pick_Value(Excel_Read.Period_Measure);
		Wait.wait2Second();
		
	}
	
	
	@FindBy(xpath="//*[text()='Save Fund']")
	WebElement Save_Fund;
	
	
	@FindBy(xpath = "//button[contains(text(),'Add Period')]")
	WebElement add_period;
	
	
	@FindBy(xpath = "//input[@ng-model='item.PeriodEndDate']")
	WebElement period_end_Date;
	
	@FindBy(xpath = "//input[@ng-model='item.ClaimSubmissionEndDate']")
	WebElement Period_Claim_Submission_Deadline;
	
	
	
	
	@FindBy(xpath = "//*[@ng-model='item.PeriodDescription']")
	WebElement period_Desc;
	
	public void add_period() throws Exception
	{
		add_period.click();
		Wait.wait2Second();
		period_end_Date.sendKeys(Excel_Read.End_date);
		Period_Claim_Submission_Deadline.sendKeys(Excel_Read.End_date);
		period_Desc.sendKeys("Sample period description");
		Wait.wait_a_second();
	}
	
	
	@FindBy(xpath ="//tbody/tr[1]/td[2]")
	WebElement createdFundRecord;
	
	public void save_And_validate_fund_is_Created() throws Exception
	{
		Save_Fund.click();
		Wait.wait5Second();
		try {
		Wait.wait_until_element_is_visible(createdFundRecord);
		Hooks.VerifyByElement(createdFundRecord, "Fund Is Successfully Create and Verified", "Fund Creation Test Is Interrupted");
		}catch (Exception e) {
			System.out.println("Exception occured");
		}
		Wait.wait3Second();
		System.out.println("Fund Setup Automation is Completed");
	}
	
	
	

}
