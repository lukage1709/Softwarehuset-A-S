Feature: Assign teamleader
	Desciption: 
	Actors: Administrator
	#Make realistic id
	
Scenario: Assign a teamleader
	Given that the administrator is logged in
	And a project with id "030901" exists 
	And there is an employee with id "Cjep"
	And the employee is available
	When the administrator assigns the employee as teamleader
	Then the employee are teamleader for the project

Scenario: Assign teamleader who are not available
	Given that the administrator is logged in
	And a project with id "030901" exists
	And there is an employee with id "Cjep"
	And the employee is not available
	When the administrator assigns the employee as teamleader
	Then I get the errormessage "Employee not available"	 
