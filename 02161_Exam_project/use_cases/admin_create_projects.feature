Feature: Create project
	Description: Administrator creates new project
	Actors: Administrator

Scenario: Create project 
	Given that the admin is logged in
	And the firm is going to create project with name "First project", startweek "2018-2"
	When when the administrator creates the project
    Then the project exists in the planning system
    
Scenario: Create project without administrator logged in
	Given that the admin is not logged in
	And the firm is going to create project with name "First project", startweek "2018-2"
	When when the administrator creates the project
    Then I get the error message "Administrator login required"	

Scenario: Project name already used
	Given there is project with name "Second project", startweek "2018-2"
	And that the admin is logged in
	And the firm is going to create project with the same name, startweek "2019-5"
	When when the administrator creates the project with a name already in use
    Then I get the error message "Name for project is already used"	
