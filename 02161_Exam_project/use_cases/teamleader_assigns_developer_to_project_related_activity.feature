Feature: Teamleader assigns developer to activity
	Description: Teamleaders ability to assign a developer to a specific activity related to the project - as opposed to a non-project related activity, such as Ultimate frisbee
	Actors: Teamleader

Scenario: Teamleader succesfully assigning employee to activity
Given the teamleader is logged in
When teamleader assigns employee to activity "Debug"
Then the employee is assigned to activity "Debug"

Scenario: Teamleader assigning unavailable employee
Given the teamleader is logged in
And the employee is not available
When teamleader assigns employee to activity "Debug"
Then teamleader receives error message "employee not available"

Scenario: Teamleader assigns employee already involved in activity to same activity
Given the teamleader is logged in
When the employee is working on project "Debug"
And the teamleader assigns the employee to "Debug"
Then teamleader receives error message "employee already assigned to activity"

Scenario: Teamleader assigns employee to nonexistent activity
Given the teamleader is logged in
When the teamleader assigns employee to activity "Debug"
And the activity "Debug" does not exist
Then teamleader receives error message " Cannot assign employee to activity: Activity does not exist"


