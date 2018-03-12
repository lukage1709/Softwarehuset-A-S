Feature: Teamleader assigns developer to non-project related activity
	Description: Teamleaders can assign developers to non-project related activities such as vacation
	Actors: Teamleader
	
Scenario: Teamleader succesfully assigns developer to non-project related activity
Given Teamleader is logged in
And developer is available
When teamleader assigns developer to "vacation"
Then developer is assigned to "vacation"

Scenario: Teamleader assigns unavailable developer to non-project related activity
Given Teamleader is logged in
And developer is unavailable
When teamleader assigns developer to "vacation"
Then teamleader receives an erorr message "Cannot assign unavailabe developer"

