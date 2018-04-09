#Feature: Teamleader assigns employee to activity
#	Description: Teamleaders ability to assign a developer to an activity related to the project.
#	Actors: Teamleader
##Keep	
#Scenario: Teamleader assigns employee to activity
#	Given the teamleader is logged in
#	And there is a project with name "First Project"
#	And there is an activity with name "Activity 1"
#	And employee with ID "Anje0001" is available
#	When teamleader assigns employee to activity "Activity 1"
#	Then the employee is assigned to activity "Activity 1" in "First Project"
#	And the employee with ID "Anje0001" is still at the list "Available employees"	
#
#Scenario: Teamleader unassigns employee to activity
#	Given the teamleader is logged
#	And there is a project with name "First Project"
#	And there is an activity with name "Activity 1"
#	And there is an employee with ID "Abcd" assigned to "Activity 1" in "First project"
#	When teamleader unassigns employee from activity "Activity 1"
#	Then the employee is unassigned from activity "Activity 1" in "First Project"
#	
#    
#
