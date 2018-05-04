Feature: Teamleader assigns employee to activity
	Description: Teamleaders ability to assign a developer to an activity related to the project.
	Actors: Teamleader
	

Scenario: Teamleader assigns employee to activity
	Given that there is a project with the name "Test Project"
	And this project has a teamleader with id "Anje"
	And there is an activity with name "Activity 1" in the activities list of that project
	And employee with ID "Cane" is available
	And the teamleader is logged in
	When teamleader assigns employee with ID "Cane" to activity "Activity 1"
	Then the employee is assigned to activity in project with ID
	And the activity is written on the employees list of activities
	And the employee with ID "Cane" is still at the list "Available employees"	

Scenario: Teamleader unassigns employee to activity
	Given that there is a project with the name "Test Project"
	And this project has a teamleader with id "Anje"
	And there is an activity with name "Activity 1" in the activities list of that project
	And there is an employee with ID "Cane" assigned to the activity in the project
	And the teamleader is logged in
	When teamleader unassigns employee with ID "Cane" from the activity in the project
	Then the employee is unassigned from activity "Activity 1" in the project
	And the activity is no longer written on the employees list of activities
	
    

