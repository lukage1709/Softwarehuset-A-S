Feature: Teamleader creates activity
	Description: Teamleader creates activity in project
	Actors: Teamleader
#keep
Scenario: Teamleader creates activity
    	Given the teamleader is logged in
    	And there is a project with name "First Project"
   	When teamleader names activity "Activity 1"
    	Then activity is created 
    	And the activity name is set to "Activity 1"

Scenario: Teamleader uses already existing name for activity
    	Given the teamleader is logged in
    	And there is a project with name "First Project"
    	And there is an activity with name "Activity 1" in the project "First Project"
    	When teamleader creates activity with the name "Activity 1"
    	Then the activity is not created
    	And I get error message "Activity name already used in this project"

