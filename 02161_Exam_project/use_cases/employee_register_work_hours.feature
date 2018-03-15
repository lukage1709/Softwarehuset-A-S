Feature: employee registers time
  Description: The employee registers time worked on activities 
  Actors: Employee


Scenario: Employee registers work hours 
Given the employee "Abcd" is logged into the system 
And the activity "Test" is assigned to a project 
When the employee registers time worked on activity
Then the employee's time worked on the project is registered to the activity 

#Samme som 1
Scenario: Employee registers work hours on non-started activity
Given the employee "Abcd" is logged into the system 
And the activity "Test" is not assigned to a project
When the employee register work hours on activity
Then the work hours is not registered to the activity 
And I get the error message "Can't register time used on activity: Activity does not exist in system"
#



#Måske ikke denne! hvis første scenario tager højde for nedenstående

Scenario: Employee registers work hours on activity which the employee has not been assigned to
Given the employee "Abcd" is logged into the system 
When the employee registers work hours at an activity
And the employee "Abcd" is not assigned to the activity "UX changes"
Then the employee's work hours are not registered to the activity


#BÅDE PROJEKT OG IKKE PROJEKT RELATERET AKTIVITET OG TIMETAL
