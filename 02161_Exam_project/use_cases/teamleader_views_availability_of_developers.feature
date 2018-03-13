Feature: View availability 
	Description: teamleaders needs to be able to see which developers are available
	Actors: Teamleader
	
Scenario: Teamleader succesfully views developers available for activities
Given Teamleader is logged in
When Teamleader requests "availability"
Then Teamleader receives list of "availability"


Scenario: User not logged in as teamleader attempts to view available developers for activities
Given teamleader is not logged in
When user requests "availability"
Then user receives error message "cannot show list of developers. Not logged in as teamleader"

 	
