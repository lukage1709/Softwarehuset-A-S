Feature: User login/logout
	Description: The user logs in and out of the system
	Actor: User
#keep
Scenario: User logs in
	Given that a user is not already logged in
	And "Anje" is a registered user
	When the user logs in with id "Anje"
	Then the user with id "Anje" logs in successfully
	
Scenario: User types unknown username
	Given that a user is not already logged in
	And "Unknown" is not a registered user.
	When the user logs in with id "Unknown"
	Then the user is not logged in

Scenario: User logs out
	Given that a user is logged in
	When the user logs out
	Then the user is not logged in