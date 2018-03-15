Feature: Unassign teamleader
	Description: The administrator unassigns a teamleader from a project
	Actors: Administrator

Scenario: Unassign teamleader
	Given that the administrator is logged in
	And there is a project with id "030901"
	And the project has a teamleader with id "Cjep"
	When the administrator unassigns the teamleader
	Then the project has no teamleader
	
Scenario: Unassign teamleader when not the administrator
	Given that the administrator is not logged in
	And there is a project with id "030901"
	And the project has a teamleader with id "Cjep"
	When the user unassigns the teamleader
	Then I get the error mesagge "Administrator login required to unassign teamleader"


#Senere
