package dtu.planning.acceptance_tests;

import dtu.planning.app.Employee;

public class EmployeeHelper {
	private Employee employee;
	
	public Employee getEmployee() {
		if (employee == null) {
			employee = exampleEmployee();
		}
		return employee;
	}
	
	private Employee exampleEmployee() {
		Employee employee = new Employee("Anje0001", "Anders Jensen");
		return employee;
	}
}
