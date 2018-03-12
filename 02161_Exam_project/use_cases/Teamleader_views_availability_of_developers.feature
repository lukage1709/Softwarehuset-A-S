Feature: View availability 
	Description: teamleaders needs to be able to see which developers are available
	Actors: Teamleader
	
Scenario: Teamleader views developers available for activities
Given Teamleader is logged in
When Teamleader requests "availability"
Then Teamleader receives list of "availability"

 	