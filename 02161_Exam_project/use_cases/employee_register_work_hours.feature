Feature: Rmployee registers time
    Description: The employee registers time worked on activities 
    Actors: Employee
#keep
Scenario: Employee registers work hours 
    Given the employee "Anje" is logged into the system 
    And the activity "Activity 1" exists within the project with ID "030901"
    And the employee is assigned to the activity
    When the employee registers 10 hours to the activity
    Then the hours are registered on the activity

    Scenario: Employee registers minus work hours and the sum is less than 0
    Given the employee "Anje" is logged into the system 
    And the activity "Activity 1" exists within the project with ID "030901"
    And the employee is assigned to the activity
    And the activity has 10 hours registered
    When the employee registers a negative amount of "-20" hours to the activity
    Then the hours registered on the activity is 0