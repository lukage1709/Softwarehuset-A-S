package dtu.planning.app;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
	public void createProject(Project project) {
		if (adminLoggedIn()) {
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
		return currentEmployees;
	}
	
	
	public Project searchProjectByID(String projectID) {
		 for (Project project : currentProjects ) {
			 if (project.getProjectByID(projectID)) {
			 return project;
			 }
		 }
		return null;

}

	public void removeEmployee(Employee employee) throws Exception {
		if (adminLoggedIn) {
			if (searchEmployeeID(employee.getID()) == null) {
				throw new Exception("Employee does not exists in list of employees");

			} else {
				currentEmployees.remove(employee);
			}

		} else {
			throw new Exception("Administrator login required to remove employee from system");

		}

	}
	
	public Calendar yearWeekParser(String str) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-ww");
		Date date = format.parse(str);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		return calendar;
	}
	
}
