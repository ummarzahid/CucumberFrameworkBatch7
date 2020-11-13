@addEmployee
Feature: Add Employee

  Background: 
    When login with valid credentials
    Then navigate to add employee page

  Scenario: Add employee without login details but without middle name
    And enter first and last name
    Then click on save Button
    And verify the employee is added successfully

  Scenario: Add employee with login details
    And enter first and last name
    When check login details checkbox
    Then enter login details
    Then click on save Button
    And verify the employee is added successfully

  @parameter
  Scenario: Add employee without login details but with middle name
    When enter first name as "Ahmet" middle name as "Can" and last name as "Bicer"
    Then click on save Button
    And verify that "Ahmet Can Bicer" is added successfully

  @examples
  Scenario Outline: Adding multiple employees without login details
    When enter employee "<First Name>", "<Middle Name>" and "<Last Name>"
    Then click on save Button
    And verify that "<First Name>", "<Middle Name>" and "<Last Name>" is successfully added

    Examples: 
      | First Name | Middle Name | Last Name |
      | Mark       | J           | Smith     |
      | Hunter     | ABC         | Musoev    |
      | John       | M           | Wick      |
      | John       | F           | Kennedy   |

  @dtWithHeader
  Scenario: Adding multiple employees at one execution
    When add multiple employees and verify they are added
      | First Name | Middle Name | Last Name | Employee ID     |
      | Mark       | J           | Smith     | adasdfadfasdfa  |
      | Hunter     | ABC         | Musoev    | adfasdfadsfadsf |
      | John       | M           | Wick      | adfasdfaadaf    |
      | John       | F           | Kennedy   | adfasdfaff      |
      
      @excelTask
Scenario: Adding multiple employees from excel file 
	When add multiple employees from "AddEmployee" verify they are added successfully      
      
      
      
