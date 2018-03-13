Feature: Teamleader creates activity
	Description: Teamleader creates activity for project
	Actors: Teamleader

Scenario: Teamleader succesfully creates activity
Given the teamleader is logged in
When teamleader names activity "Blue Rose"
Then activity is created 
And the activity name is set to "Blue Rose"

Scenario: Non-teamleader attempts to create activity
Given the teamleader is not logged in
When user tries to name activity "Blue Rose"
Then activity is not created
And I get error message "Activity not created. Requires teamleader login"

