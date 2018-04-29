Feature: User login/logout
	Description: The user logs in and out of the system
	Actor: User
#keep
Scenario: User logs in
	Given that a user is not already logged in
	And the user logins with userID "Anje"
	And "Anje" is a registered user
	Then the user with id "Anje" logs in successfully
	
Scenario: User types unknown username
	Given that a user is not already logged in
	When the user logs in with username "Unknown"
	And "Unknown" is not a registered user.
	Then the user is not logged in

Scenario: User logs out
	Given that a user is logged in
	When the user logs out
	Then the user is not logged in