package dtu.planning.app;

import java.util.ArrayList;
import java.util.List;

public class PlanningApp {
	
	private boolean adminLoggedIn = false;
	private List<Project> currentProjects = new ArrayList<>();
	private List<Employee> currentEmployees = new ArrayList<>();

	public boolean adminLoggedIn() {
		return adminLoggedIn;
	}

	public boolean adminLogin(String password) {
		adminLoggedIn = password.equals("admin1234");
		return adminLoggedIn;
	}

	public void adminLogOut() {
		adminLoggedIn = false;
		
	}

	
	/**
	 * Creates a project provided the administrator is logged in
	 * 
	 * @param project name, start- and enddates
	 * 
	 */
	public void createProject(String projectName, String startDate, String endDate) {
		if (adminLoggedIn()) {
			Project project = new Project(projectName, startDate, endDate);
				currentProjects.add(project);
			
		}
		
	}
	
	/**
	 * @return the list of project currently registered in the system.
	 */
	public List<Project> getProjects() {
		return currentProjects;
	}

	public void registerEmployee(Employee employee) throws Exception {
		if (adminLoggedIn) {
			if (currentEmployees.contains(employee)) {
				throw new Exception("Employee is already registered");
			
			}
			if (searchEmployeeID(employee.getID()) != null) {
				throw new Exception("Employee not registered - ID already used");
			
			}else {
			currentEmployees.add(employee);
			}
		}
	
	}	

	public Employee searchEmployeeID(String searchID) {
		 for (Employee employee : currentEmployees ) {
			 if (employee.match(searchID)) {
			 return employee;
			 }
		 }
		 return null;
	}

	public List<Employee> getEmployees() {
		// TODO Auto-generated method stub
		return currentEmployees;
	}
	

}
