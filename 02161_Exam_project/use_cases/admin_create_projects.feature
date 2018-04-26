Feature: Create project
	Description: Administrator creates new project
	Actors: Administrator
#keep
Scenario: Create project 
	Given that the admin is logged in
    When when the administrator creates a project with name "First project", start year "2018", start month "05" and start day "01"
    Then the project exists in the planning system

Scenario: Project name already used
	Given that the admin is logged in
    And there is project with name "First project", start year "2018", start month "05" and start day "01"
    When when the administrator creates a project with name "First project", start year "2019", start month "02" and start day "01"
    Then I get the error message "Name for project is already used"	
