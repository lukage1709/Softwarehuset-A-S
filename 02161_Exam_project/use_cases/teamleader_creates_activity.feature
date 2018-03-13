Feature: Teamleader creates activity
	Description: Teamleader creates activity for project
	Actors: Teamleader

Scenario: Teamleader succesfully creates activity
Given the teamleader is logged in
When teamleader names activity "Blue Rose"
Then activity is created 
And the activity name is set to "Blue Rose"

Scenario: Teamleader attempts to make activity for nonexistent project
Given the teamleader is logged in
When the teamleader names activity "Blue Rose"
And the project does not exist
Then i get error message "cannot add activity: Project does not exist"

Scenario: Non-teamleader attempts to create activity
Given the teamleader is not logged in
When user names activity "Blue Rose"
Then activity is not created
And I get error message "Activity not created. Requires teamleader login"

Scenario: teamleader attempts to create activity in project in which the same activity already exists
Given the teamleader is logged in
When teamleader names activity "Blue Rose"
And activity "Blue Rose" already exists
Then i get error message "Activity already exists in project"



