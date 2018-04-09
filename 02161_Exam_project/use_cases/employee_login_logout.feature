#Feature: User login/logout
#	Description: The user logs in and out of the system
#	Actor: User
##keep
#Scenario: User logs in
#	Given that the user is not already logged in
#	And the user logins with username "Abcd1234"
#	And "Abcd1234" is a registered user
#	Then the user logs in successfully
#	
#Scenario: User types unknown username
#	Given that the user is not already logged in
#	When the user logs in with username "Unknown"
#	And "Unknown" is not a registered user.
#	Then the user is not logged in
#
#Scenario: User logs out
#	Given that the user is logged in
#	When the user logs out
#	Then the user is not logged in
