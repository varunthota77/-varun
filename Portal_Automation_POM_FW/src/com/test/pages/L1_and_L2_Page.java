package com.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.LogStatus;
import com.test.hooks.Config;
import com.test.hooks.Hooks;
import com.test.hooks.Wait;

public class L1_and_L2_Page 
{
	
	public RemoteWebDriver driver;
	public Actions a;
	public L1_and_L2_Page(RemoteWebDriver driver)
	{
		this.driver=driver;	
		PageFactory.initElements( driver,this);
	}
	
	
	@FindBy(id="txtEmulateUserSearch")
	WebElement Emulate_Search;
	
	@FindBy(id="ClearEmulateUserimg")
	WebElement Clear_Emulate;
	
	
	
	@FindBy(linkText="Administration")
	WebElement Administrator_Tab;
	
	
	@FindBy(xpath="//span[text()='Review Online Payment Requests (L1)']")
	WebElement Review_Online_Payment_tab_L1_user;
	
	@FindBy(xpath="//span[text()='Approval Final Payment Register (L2)']")
	WebElement Review_Online_Payment_tab_L2_user;
	
	@FindBy(xpath= "//*[@id=\"blockContent\"]/div[2]/div[2]/div/span[1]/span/span[1]")
	WebElement select_pre_payment_dropDown;
	
	@FindBy(xpath ="//button[text()='Search']")
	WebElement SearchButton;
	
	
	
	@FindBy(xpath="//button[text()='Submit']")
	WebElement SubmitButton;
	
	@FindBy(xpath="//button[text()='Submit For Final Approval']")
	WebElement SubmitButton_Final;
	
	
	@FindBy(xpath="//button[text()='Yes']")
	WebElement Please_confirm_Yes;
	
	@FindBy(xpath="//label[text()='Successfully Updated']")
	WebElement SuccessMessage;
	
	@FindBy(xpath="//button[text()='Yes']")
	WebElement SuccessMessage_PopUp_Yes;
	
	@FindBy(xpath="//button[text()='Ok']")
	WebElement SuccessMessage_PopUp_Ok;
	

	/**
	 * L1 USER
	 * 
	 */
	
	public void emulate_L1_user() throws Exception
	{	try {
		if(Clear_Emulate.isDisplayed())
		{
			Wait.wait2Second();
			Clear_Emulate.click();
			Wait.wait10Second();
		}}catch (Exception e)
		{
			System.out.println("");
		}
		
		Emulate_Search.sendKeys(Config.L1_user);
		Wait.wait_a_second();
		a=new Actions(driver);
		a.sendKeys(Keys.DOWN,Keys.ENTER).build().perform();
		Wait.wait10Second();
		driver.navigate().refresh();
		Wait.wait10Second();
	}
	
	public void navigate_to_review_payment_tab_L1_User() throws  Exception
	{
		Wait.wait_until_element_is_visible(Administrator_Tab);
		Administrator_Tab.click();
		Wait.wait2Second();
		Review_Online_Payment_tab_L1_user.click();
	}
	

	public void choose_pre_payment_dropDown_and_Search() throws Exception
	{
		Wait.wait2Second();
		Wait.wait_until_element_is_visible(select_pre_payment_dropDown);
		select_pre_payment_dropDown.click();
		Hooks.Pick_Value("US");
		Wait.wait_a_second();
		SearchButton.click();
		Wait.wait5Second();
	}
	
	
	/*public void approvedCheckBox() throws Exception
	{
		Claim_submission_Page cp=new Claim_submission_Page(driver);
		String cl1 = cp.claim_Number();
		System.out.println("Actual Claim Numberfrom L1 AND L2: "+ cl1);
		WebElement Approved_Except_For_CheckBox = driver.findElement(By.xpath("//span[text()='"+cl1+"']/following::td[8]/input"));

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
	}*/
	
	public void validate_L1_User_Approve_flow() throws Exception
	{
		Wait.wait2Second();
		SubmitButton.click();
		Wait.wait_a_second();
		Please_confirm_Yes.click();
		Wait.wait2Second();
		Hooks.VerifyByElement(SuccessMessage, "L1 User is Approved", "Verification Failed at L1 User Approve");
		SuccessMessage_PopUp_Yes.click();
		Wait.wait2Second();
	}
	
	/**
	 * L2 USER
	 * 
	 */
	
	public void emulate_L2_user() throws Exception
	{
		try {
		Wait.wait5Second();
		Clear_Emulate.click();
		}catch (Exception e) 
		{
			//
		}
		Wait.wait7Second();
		Emulate_Search.sendKeys(Config.L2_user);
		Wait.wait_a_second();
		a=new Actions(driver);
		a.sendKeys(Keys.DOWN,Keys.ENTER).build().perform();
		Wait.wait10Second();
		driver.navigate().refresh();
		Wait.wait10Second();
	}
	
	
	
	public void navigate_to_review_payment_tab_L2_User() throws  Exception
	{
		Wait.wait_until_element_is_visible(Administrator_Tab);
		Administrator_Tab.click();
		Wait.wait_a_second();
		Wait.wait4Second();
		Review_Online_Payment_tab_L2_user.click();
	}
	

	
	/*public void approvedCheckBox_l2() throws Exception
	{
		Claim_submission_Page cp=new Claim_submission_Page(driver);
		Wait.wait4Second();
		String cl1 = cp.claim_Number();
		WebElement Approved_Except_For_CheckBox = driver.findElement(By.xpath("//span[text()='"+cl1+"']/following::td[8]/input"));

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
	*/
	public void validate_L2_User_Approve_flow() throws Exception
	{
		Wait.wait2Second();
		SubmitButton_Final.click();
		Wait.wait_a_second();
		Please_confirm_Yes.click();
		Wait.wait3Second();
		Hooks.VerifyByElement(SuccessMessage, "L2 User is Approved", "Verification Failed at L2 User Approve");
		Wait.wait3Second();
		try 
		{
		   SuccessMessage_PopUp_Yes.click();
		}catch (Exception e) 
		{
			SuccessMessage_PopUp_Ok.click();
		}
		Wait.wait2Second();
	}
	
	
	

}
