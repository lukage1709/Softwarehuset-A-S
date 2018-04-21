Feature: Teamleader creates activity
	Description: Teamleader creates activity in project
	Actors: Teamleader
#keep
Scenario: Teamleader creates activity
	Given an employee is logged in
	And there is a project with ID "030901"
	And the employee is team leader on that project
	When teamleader creates a new activity named "Activity 1", estimatedhours 20, startweek "2018-02" and endweek "2018-05"
	And and adds the activity to the project
	Then the activity is in the activities list of that project

Scenario: Teamleader uses already existing name for activity
    Given an employee is logged in
	And there is a project with ID "030901"
	And the employee is team leader on that project
	And there is an activity with name "Activity 1" in the project
	When the teamleader creates the activity again
	Then I get an error message "Activity name already used in this project"

