Feature: Teamleader assigns employee to activity
	Description: Teamleaders ability to assign a developer to an activity related to the project.
	Actors: Teamleader
#Keep	

Scenario: Teamleader assigns employee to activity
	Given an employee is logged in
	And there is a project with ID "030901"
	And the employee is team leader on that project
	And there is an activity with name "Activity 1" in the activities list of that project
	And employee with ID "Anje" is available
	When teamleader assigns employee with ID "Anje" to activity "Activity 1"
	Then the employee is assigned to activity in project with ID
	And the employee with ID "Anje" is still at the list "Available employees"	

Scenario: Teamleader unassigns employee to activity
	Given an employee is logged in
	And there is a project with ID "030901"
	And the employee is team leader on that project
	And there is an activity with name "Activity 1" in the activities list of that project
	And there is an employee with ID "Anje" assigned to the activity in the project
	When teamleader unassigns employee with ID "Anje" from the activity in the project
	Then the employee is unassigned from activity "Activity 1" in the project
	
    

