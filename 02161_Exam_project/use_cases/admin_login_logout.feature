Feature: Admin login/logout
	Description: The administrator logs in and out of the system
	Actor: Administrator
#keep
Scenario: Admin logs in with correct password
	Given that the admin is not already logged in
	And the admin logins with password "admin1234"
	Then the administrator login succeds
	And the administrator is logged in
	
Scenario: Admin types the wrong password
	Given that the admin is not already logged in
	And the admin logins with password "wrongpassword"
	Then the administrator login fails
	And the administrator is not logged in

Scenario: Admin logs out
	Given that the admin is logged in
	When the admin logs out
	Then the admin is not logged in
