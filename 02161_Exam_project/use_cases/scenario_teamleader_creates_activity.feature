Feature: Teamleader creates activity
	Description: Teamleader creates activity for project
	Actors: Teamleader

Scenario: Teamleader succesfully creates activity
Given the teamleader is logged in
When teamleader names activity "Blue Rose"
Then activity name is "Blue Rose"

Scenario: Non-teamleader attempts to create activity
Given the teamleader is not logged in
When user/developer tries to name activity "Blue Rose"
Then user recieves "Error Message"

