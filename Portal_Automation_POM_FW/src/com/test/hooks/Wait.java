package com.test.hooks;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wait extends Hooks 
{
	
	public static void wait_max_10_Seconds()
	{
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
	
	public static void wait_until_element_is_visible(WebElement locator)
	{
		ww=new WebDriverWait(driver, 25);
		ww.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	public static void wait2Second() throws InterruptedException {
		Thread.sleep(2000);
	}
	public static void wait_a_second() throws InterruptedException {
		Thread.sleep(1000);
	}
	
	public static void wait3Second() throws InterruptedException {
		Thread.sleep(3000);
	}
	public static void wait10Second() throws InterruptedException {
		Thread.sleep(10000);
	}
	public static void wait15Second() throws InterruptedException 
	{
		Thread.sleep(15000);
	}

	public static void wait4Second() throws InterruptedException {
		Thread.sleep(4000);
	}
	
	public static void wait5Second() throws InterruptedException {
		Thread.sleep(5000);
	}
	
	public static void wait8Second() throws InterruptedException {
		Thread.sleep(8000);
	}

	public static void wait7Second() throws InterruptedException 
	{
		Thread.sleep(7000);	
	}

	
	
}
