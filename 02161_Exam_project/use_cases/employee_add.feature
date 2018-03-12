Feature: Add employee
	Description: The administrator adds a new employee
	Actor: Admin
	
Scenario: Add an employee
	Given that the admin is logged in 
	And there is a employee with ID "anje" and name "Anders Jensen"
	When the administrator adds the employee 
	Then the employee is a employee in the employees list
	
Scenario: Add an employee when not logged in as administrator
	Given that the admin is not logged in 
	And there is a employee with ID "anje" and name "Anders Jensen"
	When the administrator adds the employee 
	Then I get an error message "Administrator login required"
	
Scenario: Add an employee that is already added
	Given that the admin is logged in 
	And an employee is added to the company
	When the administrator adds the employee again
	Then I get an error message "Employee is already added"