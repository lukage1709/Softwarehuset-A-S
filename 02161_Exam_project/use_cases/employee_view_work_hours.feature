Feature: Employee view registered worked time
Description: The employee views their registered work hours
Actors: Employee

Scenario: Employee views registered work hours 
Given the employee "Abcd" is logged into the system 
And the activity "Test" is assigned to a project
When the employee views registered work hours at an activity
Then the employees views the employee's registered work hours on the activity 


Scenario: Employee views registered work hours in not registered activity
Given the employee "Abcd" is logged into the system 
And there is no activity assigned to a project
When the employee views registered work hours
Then the work hours are not viewed
And I get the error message "Can't view work hours on activity, activity is not registered in system"

#VENT
