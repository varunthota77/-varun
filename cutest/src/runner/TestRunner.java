package runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;

import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="D:/AccuSelenium/Ram/cutest/FeatureFiles/Login.feature",
		glue="cutest")
		



public class TestRunner 
{

}

