package com.hrms.runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "@target/FailedTests.txt", 
				 glue = "com/hrms/stepDefinitions", // we need to glue our step definitions - implementation 
				 monochrome = true, // when set as true, will format the console outcome
				 plugin = {"pretty", // will print the steps inside the console
						 "html:target/cucumber-default-reports",
						 "rerun:target/FailedTests.txt"} 
					)
public class FailedRunner { // break till 11:30am

}
