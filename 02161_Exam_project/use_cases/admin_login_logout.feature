Feature: Admin login/logout
	Description: The administrator logs in and out of the system
	Actor: Admin
	
Scenario: Admin can login
	Given that the admin is not already logged in
	And the password is "adminpass"
	Then the admin login successfully
	And the admin is logged in
	
Scenario: Administrator types the wrong
	Given that the admin is not already logged in
	And the password is "wrongpassword"
	Then the admin login fails
	And the admin is not logged in

Scenario: Admin logs out
	Given that the admin is logged in
	When the admin logs out
	Then the admin is not logged in