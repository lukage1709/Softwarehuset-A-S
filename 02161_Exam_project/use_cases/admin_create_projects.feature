Feature: Create project
	Description: Administrator creates new project
	Actors: Administrator
#keep
Scenario: Create project 
	Given that the admin is logged in
	And the firm is going to create project with name "First project", start year "2018", start month "05" and start day "01"
	When when the administrator creates the project
    Then the project exists in the planning system

Scenario: Project name already used
	Given that the admin is logged in
    And there is project with name "Second project", start year "2018", start month "05" and start day "01"
	And the firm is going to create project with name "Second project", start year "2019", start month "02" and start day "01"
	When when the administrator creates the project with a name already in use
    Then I get the error message "Name for project is already used"	
