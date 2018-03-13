Feature: Administrator removes project
	Description: Admin deletes project
	Actor: Administrator


Scenario: Administrator removes project succesfully
Given that the administrator is logged in
And a project with id "030901" exists
When the administrator removes the project
Then all it's activities are deleted 
And the project no longer exists


Scenario: Removing a project when not the administrator
Given that the administrator is not logged in
And a project with id "030901" exists
When the user removes a project with id "030901"
Then I get the error message "Administrator login required to delete project"

	 
