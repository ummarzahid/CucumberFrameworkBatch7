package com.hrms.stepDefinitions;

import org.junit.Assert;

import com.hrms.utils.CommonMethods;
import com.hrms.utils.GlobalVariables;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class EmployeeSearchStepDefinitions extends CommonMethods{

	@When("navigate to employee list")
	public void navigate_to_employee_list() {
		click(dash.pimLinkBtn);
		click(viewEmp.EmployeeListBtn);
	}

	@When("enter a valid Employee id {string}")
	public void enter_a_valid_Employee_id(String employeeId) {
		sendText(viewEmp.idTextBox, employeeId);
		GlobalVariables.empId = employeeId;
	}

	@Then("click on search Button")
	public void click_on_search_Button() {
		click(viewEmp.searchBtn);
	}

	@Then("verify the table is displayed")
	public void verify_the_table_is_displayed() {
		Assert.assertTrue(viewEmp.isTableDisplayed());
	}

	@When("get first name from the table")
	public void get_first_name_from_the_table() {
	    System.out.println(viewEmp.getFirstNameFromTable());
	}

	@Then("validate first names from ui against db")
	public void validate_first_names_from_ui_against_db() {
	    Assert.assertEquals(DBStepDefinitions.dbData, viewEmp.getFirstNameFromTable());
	}
}
