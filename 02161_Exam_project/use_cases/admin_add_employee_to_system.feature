Feature: Administrator registers employee in system
	Description: The administrator adds a new employee
	Actor: Administrator
	
Scenario: Register new employee 
	Given that the admin is logged in 
	And no employee with ID "Anje0001" is registered 
	And there is an employee with  and name "Anders Jensen" 
	When the administrator registers the employee with name name "Anders Jensen" and "Anje0001"
	Then the employee with ID "Anje0001"  is a registered employee

#Scenario: Register an employee that is already added
#	Given that the admin is logged in 
#	And an employee with ID "Anje0001" and name "Anders Jensen" is registered
#	When the administrator registers the employee again
#	Then the employee is not registered
#	And I get an error message "Employee is already registered"
#	
#Scenario: Register with used ID
#	Given that the admin is logged in 
#	And an employee with ID "Anje0001" and name "Anders Jensen" is registered
#	When the administrator registers the employee with name "Andreas Jessen" and ID "Anje0001"
#	Then the employee is not registered
#	And I get an error message "Employee not registered - ID already used"
#	
#	
#	
