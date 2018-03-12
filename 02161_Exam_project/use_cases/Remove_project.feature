Feature: Remove project
	Description:
	Actor: Administrator

Scenario: Remove project
	Given that the administrator is logged in
	And a project with id "030901" exists
	When the administrator removes the project
	Then all it's activities are deleted 
	And the project no longer exists
	
Scenario: Removing a project when not the administrator
	Given that the administrator is not logged in
	And a project with id "030901" exists
	When when the administrator removes a project with id "030901"
	Then I get the errormessage "Administrator login required"

	 