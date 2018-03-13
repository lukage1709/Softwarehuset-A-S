Feature: Assign teamleader
	Desciption: Admin assigning teamleader to a project
	Actors: Administrator
	#Make realistic id
	
Scenario: Assign a teamleader
	Given that the administrator is logged in
	And a project with id "030901" exists 
	And there is an employee with id "Cjep"
	And the employee is available
	When the administrator assigns the employee as teamleader
	Then the employee is set to teamleader for the project

Scenario: Assign unavailable employee as teamleader to project
	Given that the administrator is logged in
	And a project with id "030901" exists
	And there is an employee with id "Cjep"
	And the employee is not available
	When the administrator assigns the employee as teamleader
	Then I get the error message "Employee not available"	 
