Feature: View registered worked time
Description: The employee view registered work hours
Actors: Employee

Scenario: Employee view registered work hours 
Given the employee "Abcd" is logged into the system 
And the activity "Test" is assigned to a project
When the employee view registered work hours at an activity
Then the employees view the work hours registered at the activity 

Scenario: Employee view non registered work hours 
Given the employee "Abcd" is logged into the system 
And the activity "Test" is assigned to a project
And there are no work hours registered 
When the employee view registered work hours
Then the work hours is not viewed
And the error message is "Can't view work hours, no work hours registored"

Scenario: Employee view registered work hours in not registored activity
Given the employee "Abcd" is logged into the system 
And there are no activity assigned to a project
When the employee view registered work hours
Then the work hours are not viewed
And the error message is "Can't view work hours, no activity created"
