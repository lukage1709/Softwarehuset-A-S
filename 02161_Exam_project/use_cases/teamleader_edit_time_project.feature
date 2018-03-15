Feature: Edit project
	Description: Teamleader adds a starting time and an estimated end time for at project.
	Actors: Teamleader

Scenario: Edit project successfully
Given that the teamleader is logged in
When the teamleader adds a start time to the project which is: "01.08.2019" and an end time to the project which is "01.09.2019"
Then the start time for the project is set to "01.08.2019" and the end time of the project is set to "01.09.2019"

#
Scenario: Teamleader only adds start time to project
Given that the teamleader is logged in
When the teamleader changes the start time for the project to "01.08.2019"
Then the start time is changed to "01.08.2019"

Scenario: Team leader only adds estimated end time to project
Given that the teamleader is logged in
When the teamleader changes the end time of the project to "01.09.2019"
Then the end time of the project is set to "01.09.2019"
#



#TEGN
