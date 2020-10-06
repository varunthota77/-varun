package test;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;

import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="D:/AccuSelenium/Ram/maven/FeatureFiles/Login.feature",
		glue={"D:/AccuSelenium/Ram/maven/src/test/java/test"}
		/*monochrome = true*/)



public class TestRunner 
{

}

