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
	private Employee currentUser = null;


	/**
	 * @return the list of project currently registered in the system.
	 */
	public List<Project> getProjects() {
		return Collections.unmodifiableList(currentProjects);
	}

	// Returns current user and null if none
	public Employee getcurrentUser() {
		return currentUser;
	}

	// Sets current user to employee
	public void setCurrentUser(Employee employee) {
		currentUser = employee;
	}


	/**
	 * @return whether or not the administrator is logged in
	 */
	public boolean adminLoggedIn() {
		return adminLoggedIn;
	}

	/**
	 * @return the list of employees currently registered in the system.
	 */
	public List<Employee> getEmployees() {
		return Collections.unmodifiableList(currentEmployees);
	}

	/**
	 * Logs in administrator if correct password is given
	 * @param password
	 * @return true if correct password, false if not
	 */
	public boolean adminLogin(String password) {
		adminLoggedIn = password.equals("admin1234");
		return adminLoggedIn;
	}

	/**
	 * Logs in administrator out
	 */
	public void adminLogOut() {
		adminLoggedIn = false;
	}



	// Creates a project provided preconditions are 
	// @param project to be created
	// @pre project != null
	// @pre admin logged in
	// @pre project name is unique
	// @post currentProject.size() == currentProject@pre.size().plus(project)
	public void createProject(Project project) throws OperationNotAllowedException {
		List<Project> projectsAtPre = new ArrayList<>(getProjects()); // To be used for postcondition

		checkAdministratorLoggedIn();

		checkNameIsAvailable(project);

		assert project != null; // Assert statements are put after defensive programming checks
		assert adminLoggedIn()==true;

		currentProjects.add(project);

		projectsAtPre.add(project);
		assert projectsAtPre.equals(currentProjects);
	}

	// Throws exception if administrator is not logged in, otherwise no change of state
	private void checkAdministratorLoggedIn() throws OperationNotAllowedException {
		if (!adminLoggedIn()) {
			throw new OperationNotAllowedException("Administrator login required");
		} 
	}

	// Throws exception if the name of the project is already in use, otherwise no change of state
	private void checkNameIsAvailable(Project project) throws OperationNotAllowedException {
		List<String> projectNames = new ArrayList<>();
		for (Project p : currentProjects) {
			projectNames.add(p.getName());
		}

		if (projectNames.contains(project.getName())) {
			throw new OperationNotAllowedException("Name for project is already used");
		}
	} 

	// Removes project
	public void removeProject(Project existingProject) throws OperationNotAllowedException {
		checkAdministratorLoggedIn();
		checkProjectExists(existingProject);

		removeActivitiesFromEmployees(existingProject);
		removeAllEmployeesFromProject(existingProject);

		currentProjects.remove(existingProject);

	}

	// Removes activities the employees' lists of current activities they are assigned to 
	private void removeActivitiesFromEmployees(Project existingProject) {
		for (Activity a: existingProject.getActivities()) {
			for (Employee e: a.getAssignedEmployees()) {
				e.unassignActivity(a);
			}
		}
	}

	// Removes all employees for all acitivites in a project
	private void removeAllEmployeesFromProject(Project project) throws OperationNotAllowedException {
		if (project.getActivities() != null) {
			for (Activity a: project.getActivities()) {
				a.removeAllEmployees();
			}
		}
	}

	// Throw exception is @param project is not in the list of current projects
	private void checkProjectExists(Project project) throws OperationNotAllowedException {
		if (!currentProjects.contains(project)) {
			throw new OperationNotAllowedException("Project does not exist");			
		}
	}

	// Register an employee if the administrator is logged in
	// @param employee to be registered
	public void registerEmployee(Employee employee) throws OperationNotAllowedException {
		checkAdministratorLoggedIn();

		checkEmployeeNotRegistered(employee);

		checkIdAvailable(employee);

		currentEmployees.add(employee);
	}	

	// Throws exception if employee is already registered
	private void checkEmployeeNotRegistered(Employee employee) throws OperationNotAllowedException {
		if (currentEmployees.contains(employee)) {
			throw new OperationNotAllowedException("Employee is already registered");
		}
	}

	// Throws exception if id of employee is already used
	private void checkIdAvailable(Employee employee) throws OperationNotAllowedException {
		if (searchEmployeeID(employee.getID()) != null) {
			throw new OperationNotAllowedException("Employee not registered - ID already used");
		}
	}

	// Search through list of current employees to find one with matching id
	// @param id to match
	// @return registered employee with corresonding id
	public Employee searchEmployeeID(String searchID) {
		for (Employee employee : currentEmployees ) {
			if (employee.match(searchID)) {
				return employee;
			}
		}
		return null;
	}

	public void removeEmployee(Employee employee) throws OperationNotAllowedException {
		checkAdministratorLoggedIn();

		checkEmployeeExists(employee);

		assert employee != null;

		currentEmployees.remove(employee);
	}

	// Throws exception employee is not registered
	private void checkEmployeeExists(Employee employee) throws OperationNotAllowedException {
		if (searchEmployeeID(employee.getID()) == null) {
			throw new OperationNotAllowedException("Employee does not exists in list of employees");
		}
	}

	public static Calendar yearWeekParser(String str) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-ww");
		Date date = format.parse(str);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) { // This only gets called if computer sets sunday as first weekday
			calendar.add(Calendar.DATE, 1);
		}

		return calendar;
	}

	public static String yearWeekFormat(Calendar cal) {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-ww");
		String formatted = format1.format(cal.getTime());
		return formatted;
	}

	// Gets list of available employees not working in specific weeks
	// @param start and end dates 
	// @return list of employees available between start- and enddate
	public List<Employee> getAvailableEmployeesInWeek(Calendar startweek, Calendar endweek) {
		List<Employee> availableEmployees = new ArrayList<>();

		for (Employee employee : currentEmployees) {
			if (employee.isEmployeeAvailable(startweek, endweek)) availableEmployees.add(employee);
		}

		return availableEmployees;
	}

	// Logs in user if user exist
	public void userLogin(String employeeId) {
		// checkEmployeeExist(employeeId);
		if (searchEmployeeID(employeeId) != null) {
			setCurrentUser(searchEmployeeID(employeeId));
		}
	}

	// Logs out current user
	public void logOut() {
		currentUser = null;		
	}

	// Returns true if the current user is teamleader, otherwise false
	public boolean isUserTeamleader() {
		for (Project project : currentProjects ) {
			if (project.getTeamleader() != null && project.getTeamleader().equals(currentUser)){
				return true;
			}
		}
		return false;
	}

	// Returns an existing project that matches given projectnumber. If none exist, then return null
	public Project getExistingProjectByProjectNumber(String projectNumber) {
		for (Project p: currentProjects) {
			if (p.getProjectNumber().equals(projectNumber)) {
				return p;
			}
		}
		return null;
	}

}
