Feature: Assign teamleader
	Desciption: Admin assigning teamleaders to a project
	Actors: Administrator
#keep	
Scenario: Assign a teamleader
	Given that the admin is logged in
	And a project with id "030901" exists 
	And there is an employee with id "Abcd1234"
	#And the employee is available
	When the administrator assigns the employee as teamleader
	Then the employee with id "Abcd1234" is set to teamleader of the project
	
Scenario: Unassign a teamleader
	Given that the admin is logged in
	And a project with id "030901" exists 
	And there is an employee with id "Abcd1234" assigned as teamleader to project "030901"
	When the administrator unassigns the employee with id "Abcd1234" as teamleader for project "030901"
	Then the employee with id "Abcd1234" is no longer teamleader of the project with id "030901" 
