Feature: View availability 
	Description: teamleaders needs to be able to see which employees are available
	Actors: Teamleader
	
Scenario: Teamleader succesfully views employees available for activities
Given Teamleader is logged in
When Teamleader requests "available employees"
Then Teamleader receives list of "availability employees"

Scenario: teamleader not logged in attempts to view available employees for activities
Given teamleader is not logged in
When user requests "available employees"
Then user receives error message "cannot show available employees. Requires teamleader login"

 	
