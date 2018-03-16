Feature: employee registers time
    Description: The employee registers time worked on activities 
    Actors: Employee

Scenario: Employee registers work hours 
    Given the employee "Abcd1234" is logged into the system 
    And a project with id "030901" exists 
    And the activity "Activity 1" is assigned to project with ID "030901"
    And the employee is assign to the activity "Activity 1"
    When the employee registers work hours to activity
    Then the employee's work hours is registered to the activity "Activity 1" in project "030901"
