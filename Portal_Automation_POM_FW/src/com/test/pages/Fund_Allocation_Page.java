package com.test.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.test.excel.Excel_Read;
import com.test.hooks.Config;
import com.test.hooks.Hooks;
import com.test.hooks.Wait;

public class Fund_Allocation_Page 
{
	
	public RemoteWebDriver driver=null;
	public Actions a;
	public Fund_Allocation_Page(RemoteWebDriver driver)
	{
		this.driver=driver;	
		PageFactory.initElements( driver,this);
	}
	
	
	@FindBy(linkText="Administration")
	WebElement Administrator_Tab;
	
	
	@FindBy(xpath="//span[text()='Fund Allocation']")
	WebElement Fund_Allocation_Tab;
	
	
	public void navigate_to_fund_allocation_tab() throws  Exception
	{
		Wait.wait_until_element_is_visible(Administrator_Tab);
		Administrator_Tab.click();
		Wait.wait_a_second();
		Wait.wait4Second();
		Fund_Allocation_Tab.click();
	}
	
	
	@FindBy(xpath="//button[contains(text(),'Add Activity')]")
	WebElement Add_activity_btn;
	
	
	@FindBy(xpath="//input[@placeholder='Customer/GEO']")
	WebElement Customer_dropDown;

	@FindBy(xpath="//label[@ng-class='item.FundNameClass']/following::span[1]")
	WebElement Fund_Name_dropDown;

	@FindBy(xpath="//label[@ng-class='item.AccrualTypeClass']/following::span[1]")
	WebElement Transactio_Type_dropDown;
	
	@FindBy(xpath="//label[@ng-class='item.AmountClass']/following::span[1]/span/input[1]")
	WebElement Enter_amount;
	
	@FindBy(xpath="//label[@ng-class='item.FundPeriodClass']/following::span[1]")
	WebElement FundPeriod_dropDown;
	
	@FindBy(id="DealerComments1")
	WebElement Comments_box;
	
	@FindBy(xpath="//button[text()='Save Activity']")
	WebElement Save_activity_button;
	
	@FindBy(xpath="//label[contains(text(),'Fund Allocations Successfully Saved')]")
	WebElement SuccessMsg;
	
	
	public void add_Activity_And_details() throws Exception
	{
		Wait.wait_until_element_is_visible(Add_activity_btn);	
		Wait.wait3Second();
		Add_activity_btn.click();
		Wait.wait3Second();
		Customer_dropDown.click();
		Wait.wait_a_second();
		a=new Actions(driver);
		a.sendKeys("Automation").perform();Wait.wait_a_second();
		a.sendKeys(Keys.DOWN,Keys.ENTER).build().perform();
		Wait.wait4Second();

		Fund_Name_dropDown.click();
		Wait.wait2Second();
		Fund_Name_dropDown.sendKeys(Excel_Read.FundName + Keys.ENTER);
		Wait.wait2Second();
		

		Transactio_Type_dropDown.click();
		Wait.wait2Second();
		a.sendKeys("De").build().perform();
		Wait.wait2Second();
		a.sendKeys(Keys.ENTER).build().perform();
		Wait.wait2Second();
		
		//fund period
		FundPeriod_dropDown.click();
		Wait.wait_a_second();
		a.sendKeys(Keys.DOWN,Keys.ENTER).build().perform();
		Wait.wait_a_second();
		Comments_box.sendKeys("Test Comments here");
		Wait.wait2Second();
		
		Thread.sleep(3000);
		Enter_amount.sendKeys("500");// + Keys.ENTER);
		Thread.sleep(1000);
		
		Save_activity_button.click();
		Wait.wait4Second();
		
		try {
			if(driver.findElement(By.xpath("//label[text()='Please complete all data']")).isDisplayed())
			{
				Wait.wait4Second();
				Transactio_Type_dropDown.click();
				Wait.wait_a_second();
				a.sendKeys("D").build().perform();
				Wait.wait2Second();
				a.sendKeys(Keys.ENTER).build().perform();
				Wait.wait2Second();

				Thread.sleep(3000);
				Enter_amount.sendKeys("1000" + Keys.ENTER);
				Thread.sleep(1000);
				Save_activity_button.click();
				Wait.wait5Second();
				
				
			}
		}catch (Exception e) {
			System.out.println("Error try ignored at fund allocation");
		}
		
		Wait.wait_until_element_is_visible(SuccessMsg);
		
		
		Hooks.VerifyByElement(SuccessMsg, "Fund is successfully allocated", "Fund Allocated verification is failed");
		
		
	}
	
	
}
