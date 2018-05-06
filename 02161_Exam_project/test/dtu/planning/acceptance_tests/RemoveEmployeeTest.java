//package dtu.planning.acceptance_tests;
//
//import org.junit.Assert.*;
//
//import org.junit.Test;
//
//import dtu.planning.app.Employee;
//import dtu.planning.app.PlanningApp;
//
//
//public class RemoveEmployeeTest {
//	PlanningApp planningApp = new PlanningApp();
//	Employee employee, employee2;
//
//	@Test
//	public void testInputDataSetA() throws Exception {
//		planningApp.adminLoggedIn();
//		employee = new Employee("Anje", "Anders Jensen");
//		planningApp.registerEmployee(employee);
//		planningApp.adminLogOut();
//		
//		planningApp.removeEmployee(employee);
//	}
//	
//	@Test
//	public void testInputDataSetB() throws Exception {
//		planningApp.adminLoggedIn();
//		employee = new Employee("Anje", "Anders Jensen");
//		planningApp.registerEmployee(employee);
//		
//		employee2 = new Employee("Xxxx", "Xxxxxx Xxxxxx");
//		planningApp.removeEmployee(employee2);
//	}
//	
//	@Test
//	public void testInputDataSetC() throws Exception {
//		planningApp.adminLoggedIn();
//		employee = new Employee("Anje", "Anders Jensen");
//		planningApp.registerEmployee(employee);
//		
//		planningApp.removeEmployee(employee);
//		assertThat(planningApp.getAvailableEmployees().contains(employee), is(false));
//	}
//
//}
