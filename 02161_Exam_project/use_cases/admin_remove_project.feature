Feature: Administrator removes project
	Description: Admin deletes project
	Actor: Administrator

Scenario: Administrator removes project with activities succesfully
Given that a project with the name "Test Project" exists
And the project has at least one activity assigned to it
And the activity has at least one registered employee assigned to it
And that the admin is logged in
When the administrator removes the project
Then the projects activities are no longer registered as current activities for the employees
And there are no longer any employees assigned to the activities
And the project is no longer registered under current projects


Scenario: Removing a project when not the administrator
Given that a project with the name "Test Project" exists
And the admin is not logged in
When the administrator removes the project
Then I get the error message "Administrator login required"

Scenario: Removing project again
Given that a project with the name "Test Project" has already been removed
And that the admin is logged in
When the administrator removes the project again
Then I get the error message "Project does not exist"

