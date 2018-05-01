package dtu.planning.app;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class PlanningApp {
	
	private boolean adminLoggedIn = false;
	private List<Project> currentProjects = new ArrayList<>();
	private List<Employee> currentEmployees = new ArrayList<>();
	private List<Employee> availableEmployees = new ArrayList<>();

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
	 * @param project to be created
	 * 
	 */
	public void createProject(Project project) throws OperationNotAllowedException {
		checkAdministratorLoggedIn();

		for (Project existingProject: currentProjects) {
			if (existingProject.getName().equals(project.getName())) {
				throw new OperationNotAllowedException("Name for project is already used");
			}
		}
	currentProjects.add(project);
		
	}
	
	private void checkAdministratorLoggedIn() throws OperationNotAllowedException {
		if (!adminLoggedIn()) {
			throw new OperationNotAllowedException("Administrator login required");
		} 
	}
	
	/**
	 * @return the list of project currently registered in the system.
	 */
	public List<Project> getProjects() {
		return currentProjects;
	}

	
	
	/**
	 * Register an employee if the administrator is logged in
	 *  
	 * @param employee to be registered
	 * 
	 */
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
	

	/**
	 * @return the list of employees currently registered in the system.
	 */
	public List<Employee> getEmployees() {
		return Collections.unmodifiableList(currentEmployees);
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

	
	/**
	 * Gets list of available employees not working in specific weeks
	 *  
	 * @param start and end dates 
	 * @return 
	 * 
	 */
	public void getAvailableEmployeesInWeek(Calendar startweek, Calendar endweek) {
		 for (Employee employee : currentEmployees) {
			 if (employee.isEmployeeAvailable(startweek, endweek)) availableEmployees.add(employee);
		 }
		
	}

	public List<Employee> getAvailableEmployees() {
		return Collections.unmodifiableList(availableEmployees);
	}
	

	
}
