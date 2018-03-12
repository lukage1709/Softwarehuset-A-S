Feature: Create project
	Description: 
	Actors: Administrator

Scenario: Create project
	Given that the administrator is logged in
	When when the administrator creates a project with name "First project", startdate "xx-xxxx" og enddate "qq-qqqq"
	Then there is a project with name "First Project", startdate "xx-xxxx" og enddate "qq-qqqq"
	
Scenario: Create project when not the administrator
	Given that the administrator is not logged in
	When when the administrator creates a project with name "First project", startdate "xx-xxxx" og enddate "qq-qqqq"
	Then I get the errormessage "Administrator login required"
	
Scenario: Project name already used
	Given that the administrator is logged in
	And there is a project with name "First Project"
	When when the administrator creates a project with name "First project", startdate "xx-xxxx" og enddate "qq-qqqq"
	Then I get the errormessage "Name is already used"	