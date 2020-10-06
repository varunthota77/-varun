package test;

import cucumber.api.java.en.Given;

public class StepClass 
{
	
	@Given("^user is on home page$")
	public void method1()

	{
		System.out.println("Working fine");
	}
}
