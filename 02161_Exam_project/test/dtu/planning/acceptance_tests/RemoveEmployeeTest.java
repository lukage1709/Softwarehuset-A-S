package dtu.planning.acceptance_tests;

import org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dtu.planning.app.Employee;
import dtu.planning.app.OperationNotAllowedException;
import dtu.planning.app.PlanningApp;

public class RemoveEmployeeTest {
	PlanningApp planningApp = new PlanningApp();
	Employee employee, employee2;

	@Test (expected = OperationNotAllowedException.class)
	public void testInputDataSetA() throws OperationNotAllowedException {
		planningApp.adminLogin("admin1234");
		employee = new Employee("Anje", "Anders Jensen");
		planningApp.registerEmployee(employee);
		planningApp.adminLogOut();
		
		planningApp.removeEmployee(employee);
	}
	
	@Test (expected = OperationNotAllowedException.class)
	public void testInputDataSetB() throws OperationNotAllowedException {
		planningApp.adminLogin("admin1234");
		employee = new Employee("Anje", "Anders Jensen");
		planningApp.registerEmployee(employee);
		
		employee2 = new Employee("Xxxx", "Xxxxxx Xxxxxx");
		planningApp.removeEmployee(employee2);
	}
	
	@Test
	public void testInputDataSetC() throws OperationNotAllowedException {
		planningApp.adminLogin("admin1234");
		employee = new Employee("Anje", "Anders Jensen");
		planningApp.registerEmployee(employee);
		
		planningApp.removeEmployee(employee);
		assertThat(planningApp.getEmployees().contains(this.employee), is(false));
	}

}
