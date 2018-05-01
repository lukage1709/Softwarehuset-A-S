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
		/* for (int i = 0; i < currentProjects.size(); i++) {
			if (currentProjects.get(i).getName().equals(project.getName())) {
				throw new OperationNotAllowedException("Name for project is already used");
			}
		} */
		
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
