       package dtu.planning.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dtu.planning.app.Employee;
import dtu.planning.app.OperationNotAllowedException;
import dtu.planning.app.PlanningApp;

public class RegisterEmployeeTest {
	
	PlanningApp planningApp = new PlanningApp();
	Employee employee, employee2;
	private List<Employee> currentEmployees = new ArrayList<>();
	
	
	//method tested:
	
	/* public void registerEmployee(Employee employee) throws OperationNotAllowedException {
		checkAdministratorLoggedIn();
		if (currentEmployees.contains(employee)) {
			throw new OperationNotAllowedException("Employee is already registered");

		}
		if (searchEmployeeID(employee.getID()) != null) {
			throw new OperationNotAllowedException("Employee not registered - ID already used");

		}else {
			currentEmployees.add(employee);
		}

	}	
	*/
	
	

	@Test (expected = OperationNotAllowedException.class)
	public void testInputDataSetA() 
		throws OperationNotAllowedException {
		// input-data: checkAdministratorLoggedIn() = false
		employee = new Employee("Anje", "Anders Jensen");
		planningApp.registerEmployee(employee);
	}
	
	@Test (expected = OperationNotAllowedException.class)
	public void testInputDataSetB() 
		throws OperationNotAllowedException {
		// input-data: AdminLoggedIn() = true, currentEmployes.contains(employee)
		employee = new Employee("Anje", "Anders Jensen");
		planningApp.adminLogin("admin1234");
		planningApp.registerEmployee(employee);
		planningApp.registerEmployee(employee);
		planningApp.adminLogOut();
		
	}
	
	@Test (expected = OperationNotAllowedException.class)
	public void testInputDataSetC() 
		throws OperationNotAllowedException {
		// input-data: AdminLoggedIn() = true, (!currentEmployes.contains(employee)),  searchEmployeeID(employee.getID()) != null
		planningApp.adminLogin("admin1234");
		employee = new Employee("Anje", "Anders Jensen");
		employee2 = new Employee("Anje", "Anders Jensen");
		planningApp.registerEmployee(employee2);
		planningApp.registerEmployee(employee);
		planningApp.adminLogOut();
		
	
	}
	
	@Test 
	public void testInputDataSetD() 
		throws OperationNotAllowedException {
		// input-data: AdminLoggedIn() = true, (!currentEmployes.contains(employee)),  searchEmployeeID(employee.getID()) == null
		employee = new Employee("Anje", "Anders Jensen");
		planningApp.adminLogin("admin1234");
		planningApp.registerEmployee(employee);
		assert (planningApp.searchEmployeeID("Anje") != null);
		
	}

}
