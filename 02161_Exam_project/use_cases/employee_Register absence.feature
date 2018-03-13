Feature: Register absence
Description: The employee registers absence
Actors: Employee

Scenario: Employee registers absence into the system 
Given the employee "Abcd" is logged into the system 
When the employee registers absence into the system 
Then the employee's absence is registered into the system

