Feature: View availability 
	Description: teamleaders needs to be able to see which employees are available
	Actors: Teamleader
#	
Scenario: Teamleader succesfully views employees available for activities
	Given that there is a project with the name "Test Project"
	And this project has a teamleader with id "Anje"
	And the teamleader is logged in
	And there is an activity with name "Activity 1" startdate "2018-02" and enddate "2018-05" in the activities list
	When Teamleader requests available employees in the same time as the activity
	Then Teamleader receives list of availability employees not working in week two, tree and four
#
Scenario: Teamleader succesfully views employees available for activities
	Given that there is a project with the name "Test Project"
	And this project has a teamleader with id "Anje"
	And the teamleader is logged in
	And there is an activity with name "Activity 2" startdate "2018-01" and enddate "2018-03" in the activities list
	When Teamleader requests available employees in the same time as the activity
	Then Teamleader receives list of availability employees not working in week one, and two
#	
Scenario: Teamleader succesfully views employees available for activities
	Given that there is a project with the name "Test Project"
	And this project has a teamleader with id "Anje"
	And the teamleader is logged in
	And there is an activity with name "Activity 2" startdate "2018-04" and enddate "2018-06" in the activities list
	When Teamleader requests available employees in the same time as the activity
	Then Teamleader receives list of availability employees not working in week four and five
	
Scenario: Teamleader succesfully views employees available for activities
	Given that there is a project with the name "Test Project"
	And this project has a teamleader with id "Anje"
	And the teamleader is logged in
	And there is an activity with name "Activity 3" startdate "2018-06" and enddate "2018-07" in the activities list
	When Teamleader requests available employees in the same time as the activity
	Then Teamleader receives list of availability employees not working in week six

 	
#TEGN, vent
