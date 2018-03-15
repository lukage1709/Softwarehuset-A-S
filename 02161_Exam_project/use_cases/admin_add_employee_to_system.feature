Feature: Admin add employee to system
	Description: The administrator adds a new employee
	Actor: Admin
	
Scenario: Add an employee
	Given that the admin is logged in 
	And there is an employee with ID "anje" and name "Anders Jensen"
	When the administrator adds the employee 
	Then the employee is added to the list of employees
	
Scenario: Add an employee when not logged in as administrator
	Given that the admin is not logged in 
	And there is a employee with ID "anje" and name "Anders Jensen"
	When the user adds the employee 
	Then the employee is not added to the list of employees
	And I get an error message "Administrator login required to add an employee: Admin not logged in"
	
Scenario: Add an employee that is already added
	Given that the admin is logged in 
	And an employee is in the list of employees
	When the administrator adds the employee again
	Then the employee is not added to the list
	And I get an error message "Employee is already on list of employees"
	
	
	
