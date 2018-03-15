Feature: Teamleader assigns developer to activity
	Description: Teamleaders ability to assign a developer to an activity related to the project.
	Actors: Teamleader

Scenario: Teamleader succesfully assigns employee to activity
Given the teamleader is logged in
When teamleader assigns employee to activity "Debug"
Then the employee is assigned to activity "Debug"

Scenario: Teamleader assigns unavailable employee to activity
Given the teamleader is logged in
And the employee is not available
When teamleader assigns employee to activity "Debug"
Then the employee is not registered to the activity
And I get the error message "employee not available at this time"

Scenario: Teamleader assigns employee already involved in an activity to same activity
Given the teamleader is logged in
When the employee is working on project "Debug"
And the teamleader assigns the employee to "Debug"
Then the employee is not added to the activity again
And I get the error message "employee already assigned to activity"

Scenario: Teamleader assigns employee to nonexistent activity
Given the teamleader is logged in
When the teamleader assigns employee to activity "Debug"
And the activity "Debug" does not exist
Then the employee is not assigned to the activity
And i get the error message "Cannot assign employee to activity: Activity does not exist"


