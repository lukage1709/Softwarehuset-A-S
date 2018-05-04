#Feature: Assign teamleader
#	Desciption: Admin assigning teamleaders to a project
#	Actors: Administrator
#	
#Scenario: Assign a teamleader
#	Given that a project with the name "Test Project" exists
#	And there is an employee with id "Anje"
#	And the employee is available
#	And that the admin is logged in
#	When the administrator assigns the employee  as teamleader for project 
#	Then the employee is set to teamleader of the project
#	
#Scenario: Unassign a teamleader
#	Given that a project with the name "Test Project" exists
#	And the project has a teamleader with id "Anje"
#	And that the admin is logged in
#	When the administrator unassigns the employee as teamleader for project 
#	Then the project no longer has the employee as teamleader
#