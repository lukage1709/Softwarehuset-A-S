Feature: Teamleader assigning developer to activity
	Description: Teamleaders ability to assign a developer to a specific activity related to the project - as opposed to a non-project related activity, such as Ultimate frisbee
	Actors: Teamleader

Scenario: Teamleader succesfully assigning developer to activity
Given the teamleader is logged in
When teamleader assigns developer to activity "Debug"
Then developer is assigned to activity "Debug"

Scenario: Teamleader assigning unavailable developer
Given the teamleader is logged in
And the developer is not available
When teamleader assigns developer to activity "Debug"
Then teamleader receives error message "Developer not available"

Scenario: Teamleader assigns already involved developer to activity
Given the teamleader is logged in
When the developer is working on project "Debug"
And the teamleader tries to assign the developer to "Debug"
Then teamleader receives error message "Developre already assigned to activity"

Scenario: Teamleader assigns developer to nonexistent activity
Given the teamleader is logged in
When the teamleader assigns developer to activity "Debug"
And the activity "Debug" does not exist
Then teamleader receives error message " Cannot assign developer: Activity does not exist"


