Feature: Remove employee
	Description: The administrator removes an employee
	Actor: Admin
	
Scenario: Remove an employee
	Given that the admin is logged in 
	And there is a employee with ID "anje"
	When the administrator removes the employee 
	Then the employee is not in the employees list
	
Scenario: Remove an employee when not logged in as administrator
	Given that the admin is not logged in 
	And there is a employee with ID "anje"
	When the administrator removes the employee 
	Then I get an error message "Administrator login required"
	
Scenario: Remove an employee that does not exists
	Given that the admin is logged in 
	And there is an employee with ID "xxxx"
	When the administrator removes the employee
	Then I get an error message "Employee does not exists"