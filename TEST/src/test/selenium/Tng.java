package test.selenium;

import org.testng.annotations.*;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class Tng {

	/**First of all, beforeSuite() method is executed only once.

	Lastly, the afterSuite() method executes only once.

	Even the methods beforeTest(), beforeClass(), afterClass(), and afterTest() methods are executed only once.

	beforeMethod() method executes for each test case but before executing the test case.

	afterMethod() method executes for each test case but after executing the test case.

	In between beforeMethod() and afterMethod(), each test case executes.**/
	     
	 @Test
	   public void testCase1() {
	      System.out.println("in test case 1");
	   }

	   // test case 2
	   @Test
	   public void testCase2() {
	      System.out.println("in test case 2");
	   }

	   @BeforeMethod
	   public void beforeMethod() {
	      System.out.println("in beforeMethod");
	   }

	   @AfterMethod
	   public void afterMethod() {
	      System.out.println("in afterMethod");
	   }

	   @BeforeClass
	   public void beforeClass() {
	      System.out.println("in beforeClass");
	   }

	   @AfterClass
	   public void afterClass() {
	      System.out.println("in afterClass");
	   }

	   @BeforeTest
	   public void beforeTest() {
	      System.out.println("in beforeTest");
	   }

	   @AfterTest
	   public void afterTest() {
	      System.out.println("in afterTest");
	   }

	   @BeforeSuite
	   public void beforeSuite() {
	      System.out.println("in beforeSuite");
	   }

	   @AfterSuite
	   public void afterSuite() {
	      System.out.println("in afterSuite");
	   }
	   @Test(priority=0)
	   public void test1()
	   {
		   System.out.println("test priority 1");
	   }
	   
	  @Test(enabled=false)
	  public void NE()
	  {
		  System.out.println("false");
	  }
       @Test(dependsOnMethods ={"testCase2"})
	public void dependentMethod()
	{
    	   System.out.println("dependent method");
	}
}
