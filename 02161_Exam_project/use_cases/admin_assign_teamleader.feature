Feature: Assign teamleader
	Desciption: Admin assigning teamleaders to a project
	Actors: Administrator
#keep	
Scenario: Assign a teamleader
	Given that the admin is logged in
	And a project with id "2018-000001" exists 
#	And there is an employee with id "Anje"
#	#And the employee is available
#	When the administrator assigns the employee as teamleader
#	Then the employee with id "Anje" is set to teamleader of the project
	
Scenario: Unassign a teamleader
	Given that the admin is logged in
	And a project with id "2018-000001" exists 
#	And there is an employee with id "Anje" assigned as teamleader to project "2018-000001"
#	When the administrator unassigns the employee with id "Abcd" as teamleader for project "2018-000001"
#	Then the employee with id "Anje" is no longer teamleader of the project with id "2018-000001" 
