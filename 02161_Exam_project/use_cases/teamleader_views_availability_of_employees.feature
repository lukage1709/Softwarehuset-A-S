Feature: View availability 
	Description: teamleaders needs to be able to see which employees are available
	Actors: Teamleader
	
Scenario: Teamleader succesfully views employees available for activities
	Given an employee is logged in
	And there is a project with ID "2018-000001"
	And the employee is team leader on that project
	And there is an activity with name "Activity 1" startdate "2018-02" and enddate "2018-05" in the activities list
	When Teamleader requests available employees in the same time as the activity
	Then Teamleader receives list of availability employees not working in week two, tree and four

Scenario: Teamleader succesfully views employees available for activities
	Given an employee is logged in
	And there is a project with ID "2018-000001"
	And the employee is team leader on that project
	And there is an activity with name "Activity 2" startdate "2018-01" and enddate "2018-03" in the activities list
	When Teamleader requests available employees in the same time as the activity
	Then Teamleader receives list of availability employees not working in week one, and two
	
Scenario: Teamleader succesfully views employees available for activities
	Given an employee is logged in
	And there is a project with ID "2018-000001"
	And the employee is team leader on that project
	And there is an activity with name "Activity 2" startdate "2018-04" and enddate "2018-06" in the activities list
	When Teamleader requests available employees in the same time as the activity
	Then Teamleader receives list of availability employees not working in week four and five
	
Scenario: Teamleader succesfully views employees available for activities
	Given an employee is logged in
	And there is a project with ID "2018-000001"
	And the employee is team leader on that project
	And there is an activity with name "Activity 3" startdate "2018-06" and enddate "2018-07" in the activities list
	When Teamleader requests available employees in the same time as the activity
	Then Teamleader receives list of availability employees not working in week six


#Scenario: teamleader not logged in attempts to view available employees for activities
#	Given an employee is logged in
#	And there is a project with ID "2018-000001"
#	And there is an activity with name "Activity 1" startdate "2018-02" and enddate "2018-05" in the activities list
#	And the employee is not team leader on that project
#	When Teamleader requests available employees in the same time as the activity
#	Then user receives error message "cannot show available employees. Requires teamleader login"
# 	
#TEGN, vent
