Feature: Create project
	Description: Administrator creates new project
	Actors: Administrator
#keep
Scenario: Create project 
	Given that the admin is logged in
    When when the administrator creates a project with name "First project", start year "2018", start month "05" and start day "01"
    Then there is a new project with name name "First project", start year "2018", start month "05" and start day "01"

#Scenario: Project name already used
#	Given that the admin is logged in
#    And there is a project with name "First Project"
#    When the administrator creates a project with name "First project", startdate "05" "2018" og enddate "25" "2018"
#    Then I get the error message "Name for project is already used"	
