Feature: Remove employee
	Description: The administrator removes an employee
	Actor: Admin
	
Scenario: Remove an employee
	Given that the admin is logged in 
	And there is a employee with ID "anje"
	When the administrator removes the employee 
	Then the employee is removed from the list of employees
	
Scenario: Remove an employee when not logged in as administrator
	Given that the admin is not logged in 
	And there is a employee with ID "anje"
	When the administrator removes the employee 
	Then the employee is not removed from the list of employees
	And I get an error message "Administrator login required to remove employee from system"
	
Scenario: Remove an employee that does not exists
	Given that the admin is logged in 
	And an employee with ID "xxxx" is not in the list of employees
	When the administrator removes the employee
	Then I get an error message "Employee does not exists in list of employees"
