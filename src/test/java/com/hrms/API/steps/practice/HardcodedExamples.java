package com.hrms.API.steps.practice;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class HardcodedExamples {

	/*
	 * REST Assured - Java library specifically developed to automate REST endpoints
	 * 
	 * Given - Preparing your request When - What action will you perform, what type
	 * of call are you making? Then - Verification
	 * 
	 */

	/** Concatenates with endpoint during run time */
	String baseURI = RestAssured.baseURI = "http://18.232.148.34/syntaxapi/api";
	String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MDM2MzU3MjEsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTYwMzY3ODkyMSwidXNlcklkIjoiNjQ1In0.BDJultMrrfjFi-hYjoHtwnIhbg4GvBIghTrRK-MI-Rg";
	static String employeeID;

	// @Test
	public void sampleTest() {

		/** BaseURI for all endpoints */
		// RestAssured.baseURI = "http://18.232.148.34/syntaxapi/api";

		/** JWT */

		/** Preparing request for /getOneEmployee.php */
		/** Using .log.all to print out everything being sent with the request */
		RequestSpecification preparingGetOneEmployeeRequest = given().header("Authorization", token)
				.header("Content-Type", "application/json").queryParam("employee_id", "3695").log().all();

		/** Making call to /getOneEmployee.php */
		Response getOneEmployeeResponse = preparingGetOneEmployeeRequest.when().get("/getOneEmployee.php");

		/** One way to print response object */
		// System.out.println(getOneEmployeeResponse.asString());

		/** Second way using rest assured prettyPrint() */
		getOneEmployeeResponse.prettyPrint();

		/** Using assertThat() to verify status code */
		getOneEmployeeResponse.then().assertThat().statusCode(200);
	}

	@Test
	public void aPOSTcreateEmployee() {

		/** Preparing request for creating an employee */
		RequestSpecification createEmployeeRequest = given().header("Authorization", token)
				.header("Content-Type", "application/json")
				.body("{\n" + "  \"emp_firstname\": \"syntaxFirstName\",\n"
						+ "  \"emp_lastname\": \"syntaxLastName\",\n" + "  \"emp_middle_name\": \"syntaxMiddleName\",\n"
						+ "  \"emp_gender\": \"F\",\n" + "  \"emp_birthday\": \"2000-07-11\",\n"
						+ "  \"emp_status\": \"Employee\",\n" + "  \"emp_job_title\": \"Cloud Architect\"\n" + "}");
		// .log().all();

		/** making call to /createEmployee.php */
		Response createEmployeeResponse = createEmployeeRequest.when().post("/createEmployee.php");

		/** Printing response */
		// createEmployeeResponse.prettyPrint();

		/**
		 * Using jsonPath() to view the response body which lets us get the employee ID
		 * We are storing the employeeID as a static global variable to be able to use
		 * with other calls
		 */
		employeeID = createEmployeeResponse.jsonPath().getString("Employee[0].employee_id");

		/** Optional: Printing employeeID */
		// System.out.println(employeeID);

		/** Verifying status code is 201 */
		createEmployeeResponse.then().assertThat().statusCode(201);

		/**
		 * Verifying response body "Message" is paired with "Entry Created"; equalTo()
		 * method comes from static Hamcrest package - NEED TO IMPORT MANUALLY import
		 * static org.hamcrest.Matchers.*;
		 */
		createEmployeeResponse.then().assertThat().body("Message", equalTo("Entry Created"));

		/** Verifying created employee first name */
		createEmployeeResponse.then().assertThat().body("Employee[0].emp_firstname", equalTo("syntaxFirstName"));

		/** Verifying server Apache/2.4.39 (Win64) PHP/7.2.18 */
		createEmployeeResponse.then().header("Server", "Apache/2.4.39 (Win64) PHP/7.2.18");
	}

	@Test
	public void bGETcreatedEmployee() {

		/** Preparing request to get created employee */
		RequestSpecification getCreatedEmployeeRequest = given().header("Content-Type", "application/json")
				.header("Authorization", token).queryParam("employee_id", employeeID);

		/** Storing response for retrieving created employee */
		Response getCreatedEmployeeResponse = getCreatedEmployeeRequest.when().get("/getOneEmployee.php");

		/** Printing response */
		// getCreatedEmployeeResponse.prettyPrint();

		/**
		 * Storing response employee ID into empID to compare with static global
		 * employee ID
		 */
		String empID = getCreatedEmployeeResponse.body().jsonPath().getString("employee[0].employee_id");

		/** Comparing empID with stored employeeID from created employee call */
		boolean verifyingEmployeeID = empID.contentEquals(employeeID);

		/** Asserting to verify the above condition is true */
		Assert.assertTrue(verifyingEmployeeID);

		/** Verifying status code is 200 */
		getCreatedEmployeeResponse.then().assertThat().statusCode(200);

		/**
		 * Storing full response as a string so that we are able to pass it as an
		 * argument with JsonPath
		 */
		String response = getCreatedEmployeeResponse.asString();

		/** Created object of JsonPath */
		JsonPath js = new JsonPath(response);

		/** Grabbing employee ID using 'js' */
		String employeeId = js.getString("employee[0].employee_id");
		String firstName = js.getString("employee[0].emp_firstname");
		String middleName = js.getString("employee[0].emp_middle_name");
		String lastName = js.getString("employee[0].emp_lastname");
		String birthday = js.getString("employee[0].emp_birthday");
		String gender = js.getString("employee[0].emp_gender");
		String jobTitle = js.getString("employee[0].emp_job_title");
		String status = js.getString("employee[0].emp_status");

		/** Asserting response employee ID matches stored employee ID */
		Assert.assertTrue(employeeId.contentEquals(employeeID));

		/** Asserting the rest of the values match expected data */
		Assert.assertEquals(firstName, "syntaxFirstName");
		Assert.assertEquals(middleName, "syntaxMiddleName");
		Assert.assertEquals(lastName, "syntaxLastName");
		Assert.assertEquals(birthday, "2000-07-11");
		Assert.assertEquals(gender, "Female");
		Assert.assertEquals(jobTitle, "Cloud Architect");
		Assert.assertEquals(status, "Employee");
	}

	@Test
	public void cGETallEmployees() {

		/** Preparing request to get all employees */
		RequestSpecification getAllEmployeesRequest = given().header("Content-Type", "application/json")
				.header("Authorization", token);

		/** Storing response into getAllEmployeesResponse */
		Response getAllEmployeesResponse = getAllEmployeesRequest.when().get("/getAllEmployees.php");

		/** Printing response */
		// getAllEmployeesResponse.prettyPrint();

		/** Storing response as a String */
		String response = getAllEmployeesResponse.asString();

		/**
		 * Creating object of JsonPath and passing response as a String as an argument
		 */
		JsonPath js = new JsonPath(response);

		/** Retrieving the size of the array (the number of object in the array) */
		int count = js.getInt("Employees.size()");
		// System.out.println(count);

//		for (int i = 0; i < count; i++) {
//
//			String allEmployeeIDs = js.getString("Employees[" + i + "].employee_id");
//
//			// System.out.println(allEmployeeIDs);
//
//			if (allEmployeeIDs.contentEquals(employeeID)) {
//
//				System.out.println("Employee ID: " + employeeID + " is present in the body");
//				String firstNameOfEmpID = js.getString("Employees[" + i + "].emp_firstname");
//				System.out.println(firstNameOfEmpID);
//				break;
//			}
//
//		}

		/** for loop to print out first names of all employees */

//		for(int i = 0; i < count; i++) {		
//			String allFirstNames = js.getString("Employees[" + i +"].emp_firstname");
//			System.out.println(allFirstNames);
//		}

	}

	@Test
	public void dPUTupdateCreatedEmployee() {
		/** Task completed */
		given().body("{\n" + "  \"employee_id\": \"" + employeeID + "\",\n"
				+ "  \"emp_firstname\": \"syntaxUpdatedFirstName\",\n"
				+ "  \"emp_lastname\": \"syntaxUpdatedLastName\",\n"
				+ "  \"emp_middle_name\": \"syntaxUpdatedMiddleName\",\n" + "  \"emp_gender\": \"F\",\n"
				+ "  \"emp_birthday\": \"2000-07-11\",\n" + "  \"emp_status\": \"Employee\",\n"
				+ "  \"emp_job_title\": \"Cloud Consultant\"\n" + "}");
	}

}
