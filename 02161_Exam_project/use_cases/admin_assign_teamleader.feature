Feature: Assign teamleader
	Desciption: Admin assigning teamleaders to a project
	Actors: Administrator
#keep	
Scenario: Assign a teamleader
	Given that a project with id "2018-000001" exists
	And that the admin is logged in
	And there is an employee with id "Anje"
#	And the employee is available
	When the administrator assigns the employee  as teamleader for project 
	Then the employee is set to teamleader of the project
	
Scenario: Unassign a teamleader
	Given that a project with id "2018-000001" exists
	And the project has a teamleader with id "Anje"
	And that the admin is logged in
	When the administrator unassigns the employee as teamleader for project 
	Then the project no longer has the employee as teamleader
