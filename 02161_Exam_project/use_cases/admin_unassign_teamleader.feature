#Feature: Unassign teamleader
#	Description: The administrator unassigns a teamleader from a project
#	Actors: Administrator
#
#Scenario: Unassign teamleader
#	Given that a project with the name "Test Project" exists
#	And the project has a teamleader with id "Anje"
#	And that the admin is logged in
#	When the administrator unassigns the teamleader
#	Then the project has no teamleader
#	
#Scenario: Unassign teamleader when not the administrator
#	Given that the administrator is not logged in
#	And there is a project with id "2018-000001"
#	And the project has a teamleader with id "Anje"
#	When the user unassigns the teamleader
#	Then I get the error mesagge "Administrator login required to unassign teamleader"
#
##VENT
##Senere
