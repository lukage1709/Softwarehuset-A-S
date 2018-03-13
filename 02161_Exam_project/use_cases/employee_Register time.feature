Feature: emplyoee registers time
Description: The employee registers time worked on activities 
Actors: Employee

Scenario: Employee registers work hours 
Given the activity "Penetration Test" is assigned to a project 
And the employee "Abcd" is logged into the system 
When the employee register time worked on activity
Then the time worked on the project is registered at the activity 

Scenario: Employee registers work hours on  non-started activity
Given the employee "Abcd" is logged into the system 
And the activity "Penetration Test" is not assigned to a project
When the employee register work hours on activity
Then the work hours is not registered at the activity 
And the error message is "Can't registor time used on activity, activity does not exist in system"

Scenario: Employee registers work hours at activity the employee is not assigned to
Given the employee "Abcd" is logged into the system 
When the employee register work hours at an activity
And the employee "Abcd" is not assigned to the activity "..."
Then the employee's work hours are still registered at the activity
