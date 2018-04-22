Feature: Teamleader assigns employee to activity
	Description: Teamleaders ability to assign a developer to an activity related to the project.
	Actors: Teamleader
#Keep	
Scenario: Teamleader assigns employee to activity
	Given an employee is logged in
	And there is a project with ID "030901"
	And the employee is team leader on that project
	And there is an activity with name "Activity 1" in the activities list of that project
	And employee with ID "Anje0001" is available
	When teamleader assigns employee with ID "Anje0001" to activity "Activity 1"
	Then the employee is assigned to activity "Activity 1" in project with ID "030901"
	And the employee with ID "Anje0001" is still at the list "Available employees"	

Scenario: Teamleader unassigns employee to activity
	Given an employee is logged in
	And there is a project with ID "030901"
	And the employee is team leader on that project
	And there is an activity with name "Activity 1" in the activities list of that project
	And there is an employee with ID "Anje0001" assigned to "Activity 1" in project with ID "030901"
	When teamleader unassigns employee with ID "Anje0001" from activity "Activity 1" in project with ID "030901"
	Then the employee is unassigned from activity "Activity 1" in project with ID "030901"
	
    

