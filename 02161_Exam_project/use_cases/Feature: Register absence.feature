Feature: Register absence
Description: The employee register absence
Actors: Employee

Scenario: Employee register absence into the system 
Given the employee "Abcd" is logged into the system 
When the employee register absence into the system 
Then the absence is registered into the system

