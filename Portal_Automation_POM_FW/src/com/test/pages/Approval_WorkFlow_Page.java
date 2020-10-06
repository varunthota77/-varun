package com.test.pages;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.server.handler.SendKeys;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.test.excel.Excel_Read;
import com.test.hooks.Config;
import com.test.hooks.Hooks;
import com.test.hooks.Wait;

public class Approval_WorkFlow_Page 
{
	

	public RemoteWebDriver driver=null;
	public Actions a;
	public Approval_WorkFlow_Page(RemoteWebDriver driver)
	{
		this.driver=driver;	
		PageFactory.initElements( driver,this);
	}
	
	
	@FindBy(linkText="Administration")
	WebElement Administrator_Tab;
	
	
	@FindBy(xpath="//span[text()='Approval Workflow']")
	WebElement ApprovalWorkFlow_Tab;
	
	@FindBy(id="SearchworkflowName")
	WebElement searchFiled;

	public void navigate_to_ApprovalWorkFlow_tab() throws Exception
	{
		Wait.wait_until_element_is_visible(Administrator_Tab);
		Administrator_Tab.click();
		ApprovalWorkFlow_Tab.click();
		Wait.wait_until_element_is_visible(searchFiled);
		Wait.wait3Second();
		
	}


	@FindBy(xpath="//div[text()='Fund']/following::td[2]/div/a")
	WebElement Fund_edit_button;
	
	
	@FindBy(xpath="(//*[@id=\"panel\"]/div[2]/div/div[2]/div[2]/div/div/div/input)[1]")
	WebElement Fund_selection;
	
	@FindBy(xpath="//button[text()='Publish']")
	WebElement Fund_Publish;
	
	@FindBy(xpath="//*[@id=\"Header\"]/div[2]/div[11]/label")
	WebElement SuccessMsg_Fund_Publish;
	
	
	public void select_fund_and_publish() throws Exception
	{
		Fund_edit_button.click();
		Wait.wait_until_element_is_visible(Fund_selection);
		Wait.wait2Second();
		Fund_selection.sendKeys(Excel_Read.FundName);
		Wait.wait_a_second();
		a=new Actions(driver);
		a.sendKeys(Keys.DOWN,Keys.ENTER).build().perform();;
		Wait.wait2Second();
		Fund_Publish.click();
		Wait.wait_until_element_is_visible(SuccessMsg_Fund_Publish);
		Wait.wait4Second();
		Hooks.VerifyByElement(SuccessMsg_Fund_Publish, "Fund published successfully", "Fund Creation validation failed");
		
		
	}
	
	
	
	@FindBy(xpath="//span[text()='Fund Approval']")
	WebElement FoundApprove_Tab;
	
	@FindBy(id="txtEmulateUserSearch")
	WebElement Emulate_Search;
	
	@FindBy(id="ClearEmulateUserimg")
	WebElement Clear_Emulate;
	

	@FindBy(xpath="(//button[text()='Submit'])[2]")
	WebElement Approved_Submit;
	
	@FindBy(xpath="//label[text()='Successfully Updated']")
	WebElement SuccessMsg_AfterApprove;
	
	@FindBy(xpath = "//button[@ng-click=\"close('Yes')\"]")
	WebElement popup_ok;
	
	
	
	public void emulate_user() throws Exception
	{
		Emulate_Search.sendKeys(Config.Emulate_App_1_user);
		Wait.wait_a_second();
		a=new Actions(driver);
		a.sendKeys(Keys.DOWN,Keys.ENTER).build().perform();
		Wait.wait7Second();
		driver.navigate().refresh();
		Wait.wait7Second();
	}
	
	
	
	public void navigate_to_fund_Approve_Tab() throws Exception
	{
	
		Wait.wait_until_element_is_visible(Administrator_Tab);
		Administrator_Tab.click();
		Wait.wait2Second();
		FoundApprove_Tab.click();
		Wait.wait5Second();
		driver.navigate().refresh();
		Wait.wait8Second();
		driver.navigate().refresh();
		Wait.wait8Second();
		String label = "Fund Name: "+Excel_Read.FundName+"";
		
		try {
			
		WebElement approve_check = driver.findElement
		(By.xpath("(//span[text()='"+label+"']/following::div[2]/div/div[2]/div[1]/div/input)[1]"));
		approve_check.click();
		Wait.wait_a_second();

		}catch (Exception e) 
		{
			
			driver.navigate().refresh();
			Wait.wait8Second();
			driver.navigate().refresh();
			Wait.wait8Second();
			WebElement approve_check = driver.findElement
			(By.xpath("(//span[text()='"+label+"']/following::div[2]/div/div[2]/div[1]/div/input)[1]"));
			approve_check.click();
			Wait.wait_a_second();
		}
		Approved_Submit.click();
		Wait.wait8Second();
		Wait.wait_until_element_is_visible(SuccessMsg_AfterApprove);
		Hooks.VerifyByElement(SuccessMsg_AfterApprove, "Fund Is Successfully Approved with First user ", "Validation Failed at Fund Approved stage for Second user");
		popup_ok.click();
		Wait.wait5Second();
		
		//clear emulate
		Clear_Emulate.click();
		Wait.wait7Second();
		
		
		Emulate_Search.sendKeys(Config.Emulate_App_2_user);
		Wait.wait_a_second();
		a=new Actions(driver);
		a.sendKeys(Keys.DOWN,Keys.ENTER).build().perform();
		Wait.wait7Second();
		driver.navigate().refresh();
		Wait.wait7Second();
		
		Wait.wait_until_element_is_visible(Administrator_Tab);
		Administrator_Tab.click();
		Wait.wait2Second();
		FoundApprove_Tab.click();
		Wait.wait5Second();

		String label1 = "Fund Name: "+Excel_Read.FundName+"";		
		WebElement approve_check2 = driver.findElement
		(By.xpath("(//span[text()='"+label1+"']/following::div[2]/div/div[2]/div[1]/div/input)[1]"));
		approve_check2.click();
		Wait.wait_a_second();
		Approved_Submit.click();
		Wait.wait8Second();
		Wait.wait_until_element_is_visible(SuccessMsg_AfterApprove);
		Hooks.VerifyByElement(SuccessMsg_AfterApprove, "Fund Is Successfully Approved with Second user", "Validation Failed at Fund Approved stage for Second user");
		popup_ok.click();
		Wait.wait5Second();
		
		
		//clear emulate
		Clear_Emulate.click();
		Wait.wait10Second();
		
	}
	
	
	
	
	
	
	
	
}
