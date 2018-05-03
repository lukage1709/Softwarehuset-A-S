package dtu.planning.app;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.plaf.synth.SynthSpinnerUI;


public class PlanningApp {
	
	private boolean adminLoggedIn = false;
	private List<Project> currentProjects = new ArrayList<>();
	private List<Employee> currentEmployees = new ArrayList<>();
	private List<Employee> availableEmployees = new ArrayList<>();
	private Employee currentUser = null;


	/**
	 * @return the list of project currently registered in the system.
	 */
	public List<Project> getProjects() {
		return Collections.unmodifiableList(currentProjects);
	}
	
	
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
		
		checkNameIsAvailable(project);
		
		currentProjects.add(project);
	}
	
	private void checkAdministratorLoggedIn() throws OperationNotAllowedException {
		if (!adminLoggedIn()) {
			throw new OperationNotAllowedException("Administrator login required");
		} 
	}
	
	private void checkNameIsAvailable(Project project) throws OperationNotAllowedException {

		List<String> projectNames = new ArrayList<>();
		for (Project p : currentProjects) {
			projectNames.add(p.getName());
		}
		
		if (projectNames.contains(project.getName())) {
			throw new OperationNotAllowedException("Name for project is already used");
		}
		
	} 
	
	/**
	 * Removes project
	 */
	public void removeProject(Project existingProject) throws OperationNotAllowedException {
		checkAdministratorLoggedIn();
		checkProjectExists(existingProject);
		
		removeActivitiesFromEmployees(existingProject);
		removeAllEmployeesFromProject(existingProject);
				
		currentProjects.remove(existingProject);
		
	}
 
	private void removeActivitiesFromEmployees(Project existingProject) {
		for (Activity a: existingProject.getActivities()) {
			for (Employee e: a.getAssignedEmployees()) {
				e.unassignActivity(a);
			}
		}
		
	}


	private void removeAllEmployeesFromProject(Project project) throws OperationNotAllowedException {
		if (project.getActivities() != null) {
			for (Activity a: project.getActivities()) {
				a.removeAllEmployees();
			}
		}
	}
	
	private void checkProjectExists(Project project) throws OperationNotAllowedException {
		if (!currentProjects.contains(project)) {
			throw new OperationNotAllowedException("Project does not exist");			
		}
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
	

	public void removeEmployee(Employee employee) throws Exception {
		if (adminLoggedIn) {
			if (searchEmployeeID(employee.getID()) == null) {
				throw new OperationNotAllowedException("Employee does not exists in list of employees");

			} else {
				currentEmployees.remove(employee);
			}

		} else {
			throw new OperationNotAllowedException("Administrator login required to remove employee from system");

		}

	}
	
	public Calendar yearWeekParser(String str) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-ww");
		Date date = format.parse(str);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			calendar.add(Calendar.DATE, 1);
		}
		
		return calendar;
	}

	
	/**
	 * Gets list of available employees not working in specific weeks
	 *  
	 * @param start and end dates 
	 * @return 
	 * @throws Exception 
	 * 
	 */
	public void getAvailableEmployeesInWeek(Calendar startweek, Calendar endweek) throws Exception {
			for (Employee employee : currentEmployees) {
				if (!employee.isEmployeeAvailable(startweek, endweek)) availableEmployees.add(employee);
			}
			getAvailableEmployees();

	}
	
	

	public List<Employee> getAvailableEmployees() {
		return Collections.unmodifiableList(availableEmployees);
	}
	

	
	public Employee getcurrentUser() {
	    if (currentUser == null) {
	        return null;
	    }
	    return currentUser;
	}


	public void userLogin(String employeeID) {
	    if ( searchEmployeeID(employeeID)!=null) {
	        setCurrentUser( searchEmployeeID(employeeID));
	    }
	    
	}

	public void logOut() {
	    currentUser = null;		
	}



	public void setCurrentUser(Employee employee) {
	    currentUser = employee;
	 
	}
	
}
