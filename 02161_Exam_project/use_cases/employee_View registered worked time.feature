Feature: Employee view registered worked time
Description: The employee views registered work hours for employee
Actors: Employee

Scenario: Employee views registered work hours 
Given the employee "Abcd" is logged into the system 
And the activity "Test" is assigned to a project
When the employee views registered work hours at an activity
Then the employees views the work hours registered at the activity 

Scenario: Employee views nonregistered work hours 
Given the employee "Abcd" is logged into the system 
And the activity "Test" is assigned to a project
And there are no work hours registered 
When the employee views registered work hours
Then the work hours are not viewed
And the error message is "Can't view work hours, no work hours registored"

Scenario: Employee views registered work hours in not registered activity
Given the employee "Abcd" is logged into the system 
And there is no activity assigned to a project
When the employee views registered work hours
Then the work hours are not viewed
And the error message is "Can't view work hours, no activity created"
