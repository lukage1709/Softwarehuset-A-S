Feature: Admin login/logout
	Description: The administrator logs in and out of the system
	Actor: Admin

#password fjernet fra scenariet
Scenario: Admin can login
Given that the admin is not already logged in
And the admin logins with "AdminAdmnin"
Then the admin logs in successfully
	
#password fjernet fra scenariet
Scenario: Administrator types wrong username
Given that the admin is not already logged in
And the admin username is "AdminAdmin"
When the user logs in with "Cjeps"
Then the admin is not logged in

Scenario: Admin logs out
Given that the admin is logged in
When the admin logs out
Then the admin is not logged in
