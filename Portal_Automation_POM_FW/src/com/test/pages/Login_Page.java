package com.test.pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.test.excel.Excel_Read;
import com.test.hooks.Hooks;
import com.test.hooks.Wait;

public class Login_Page extends Hooks
{
	public RemoteWebDriver driver=null;

	public Login_Page(RemoteWebDriver driver)
	{
		this.driver=driver;	
		PageFactory.initElements( driver,this);
	}
	
	@FindBy(id="loginName")
	WebElement Username;
	
	@FindBy(id="loginPassword")
	WebElement Password;
	
	@FindBy(id="LoginAuthenticate")
	WebElement SignInButton;
	
	@FindBy(linkText="Administration")
	WebElement Administrator_Tab;
	
	
	@FindBy(linkText="Logout")
	WebElement logout;

	public void EnterUsernameAndPassword() throws InterruptedException
	{
		Username.sendKeys(Excel_Read.Username);
		Password.sendKeys(Excel_Read.Password);
	}
	
	public void sign_in_and_validate_login() throws InterruptedException
	{
		SignInButton.click();
		Wait.wait7Second();
		Wait.wait_until_element_is_visible(Administrator_Tab);
		Wait.wait7Second();
		String SuccessMsg="Test Passed: Login is verified";
		String FailedMsg ="Test Failed at Login Verification";
		
		Hooks.VerifyByElement(Administrator_Tab, SuccessMsg, FailedMsg);
		System.out.println("Sign in is Done");
	
	}
	
	public void logout_user() throws Exception
	{
		logout.click();
		Wait.wait5Second();
	}
	
}
