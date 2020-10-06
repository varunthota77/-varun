package cutest;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class steps
{
	
	@Given("^user is on home page$")
	public void method1()

	{
		System.out.println("Working fine");
	}
	@When("^Enter username and password$")
	public void method2()

	{
		System.out.println("Working fine");
	}
	@Then("^verify logout link and close the browser$")
	public void method3()

	{
		System.out.println("Working fine");
	}
}
