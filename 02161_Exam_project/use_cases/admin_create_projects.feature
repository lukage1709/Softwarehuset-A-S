Feature: Create project
	Description: Admin creates new project
	Actors: Administrator

Scenario: Create project
Given that the administrator is logged in
When when the administrator creates a project with name "First project", startdate "xx-xxxx" og enddate "qq-qqqq"
Then there is a new project with name "First Project", startdate "xx-xxxx" og enddate "qq-qqqq"


#
Scenario: Create project when not the administrator
Given that the administrator is not logged in
When the user creates a project with name "First project", startdate "xx-xxxx" og enddate "qq-qqqq"
Then I get the error message "Administrator login required to create project"

#
	
Scenario: Project name already used
Given that the administrator is logged in
And there is a project with name "First Project"
When when the administrator creates a project with name "First project", startdate "xx-xxxx" og enddate "qq-qqqq"
Then I get the error message "Name for project is already used"	



#BRUG
