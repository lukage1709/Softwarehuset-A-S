Feature: Teamleader assigns employee to non-project related activity
	Description: Teamleaders can assign developers to non-project related activities (such as vacation)
	Actors: Teamleader
	
Scenario: Teamleader succesfully assigns employee to non-project related activity
Given teamleader is logged in
And employee is available
When teamleader assigns employee to "vacation"
Then employee is assigned to the activity "vacation"

Scenario: Teamleader assigns unavailable employee to non-project related activity
Given teamleader is logged in
And employee is unavailable
When teamleader assigns employee to "vacation"
Then the employee is not assigned to the activity 
And the teamleader receives an erorr message "Cannot assign unavailabe employee to activity"

#VENT
