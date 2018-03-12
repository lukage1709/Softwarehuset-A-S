Feature: Register time
Description: The employee register worked time 
Actors: Employee

Scenario: Employee register work hours 
Given the activity ".." is assignt to a project 
And the employee "Abcd" is logged into the system 
When the employee register time worked at an activity
Then the time worked on the project is registered at the activity 

Scenario: Employee register work hours at non-started activity
Given the employee "Abcd" is logged into the system 
And the activity ".." is not assigned to a project
When the employee register work hours at an activity
Then the work hours is not registered at the activity 
And the error message is "Can't registor time, activity is not created"

Scenario: Employee register work hours at activity not assigned to
Given the employee "Abcd" is logged into the system 
When the employee register work hours at an activity
And the employee "Abcd" is not assigned to the activity "..."
Then the work hours is still registered at the activity