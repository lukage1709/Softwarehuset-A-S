package dtu.planning.acceptance_tests;

import java.util.ArrayList;
import java.util.List;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import dtu.planning.app.Activity;
import dtu.planning.app.Employee;
import dtu.planning.app.OperationNotAllowedException;
import dtu.planning.app.PlanningApp;
import dtu.planning.app.Project;


public class AdminSteps {
	private PlanningApp planningApp;
	private Project newProject; 
	private Project existingProject;
	private Activity activity;
	private Employee employee;
	private List<Activity> activitiesToExistingProject;
	private List<Employee> employeesOnExistingProject;
	private ErrorMessageHolder errorMessage;
	public EmployeeHelper helper;

	public AdminSteps(PlanningApp planningApp, ErrorMessageHolder errorMessage, EmployeeHelper helper) {
		this.planningApp = planningApp;
		this.errorMessage = errorMessage;
		this.helper = helper;
	}

	/* Linnea */
	/****************************************************************************************/

	@Given("^the firm is going to create project with name \"([^\"]*)\", startweek \"([^\"]*)\"$")
	public void theFirmIsGoingToCreateProjectWithNameStartweek(String name, String startDate) throws Exception {
		newProject = new Project(name, planningApp.yearWeekParser(startDate));

		assertThat(newProject.getName(),is(equalTo(name)));
		assertThat(newProject.getStartDate(),is(equalTo(planningApp.yearWeekParser(startDate))));
	}

	@When("^when the administrator creates the project(?: with a name already in use|)$")
	public void whenTheAdministratorCreatesTheProject() throws OperationNotAllowedException {
		try {
			planningApp.createProject(newProject);
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	@Then("^the project exists in the planning system$")
	public void theProjectExistsInThePlanningSystem() throws Exception {
		List<Project> projects = planningApp.getProjects();
		assertThat(projects.size(),is(1));
		Project p = projects.get(0);
		assertThat(p.getName(), is(newProject.getName()));
		assertThat(p.getStartDate(), is(newProject.getStartDate()));
	}

	@Given("^there is project with name \"([^\"]*)\", startweek \"([^\"]*)\"$")
	public void thereIsProjectWithNameStartweek(String name, String startDate) throws Exception {
		existingProject = new Project(name,planningApp.yearWeekParser(startDate));

		planningApp.adminLogin("admin1234");
		planningApp.createProject(existingProject);
		planningApp.adminLogOut();

		assertTrue(planningApp.getProjects().contains(existingProject));

	}

	@Given("^the firm is going to create project with the same name, startweek \"([^\"]*)\"$")
	public void theFirmIsGoingToCreateProjectWithTheSameNameStartweek(String startDate) throws Exception {
		newProject = new Project(existingProject.getName(),planningApp.yearWeekParser(startDate));

		assertThat(newProject.getName(),is(equalTo(existingProject.getName())));
		assertThat(newProject.getStartDate(),is(equalTo(planningApp.yearWeekParser(startDate))));
	}

	@Then("^I get the error message \"([^\"]*)\"$")
	public void iGetTheErrorMessage(String errorMessage) throws Exception {
		assertEquals(errorMessage, this.errorMessage.getErrorMessage());
	}


	@Given("^that a project with the name \"([^\"]*)\" exists$")
	public void thatAProjectWithTheNameExists(String name) throws Exception {
		existingProject = new Project(name,planningApp.yearWeekParser("2018-02"));

		planningApp.adminLogin("admin1234");
		planningApp.createProject(existingProject);
		planningApp.adminLogOut();

		assertEquals(existingProject.getName(),name);
	}


	@When("^the administrator removes the project(?: again|)$")
	public void theAdministratorRemovesTheProject() throws OperationNotAllowedException {
		try {
			planningApp.removeProject(existingProject);
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	@Then("^the projects activities are no longer registered as current activities for the employees$")
	public void theProjectsActivitiesAreNoLongerRegisteredAsCurrentActivitiesForTheEmployees() throws Exception {
		for (Activity a: activitiesToExistingProject) {
			for (Employee e: employeesOnExistingProject) {
				assertFalse(e.getAssignedActivities().contains(a));
			}
		}
	}

	@Then("^there are no longer any employees assigned to the activities$")
	public void thereAreNoLongerAnyEmployeesAssignedToTheActivities() throws Exception {
		for (Activity a: existingProject.getActivities()) {
			assertThat(a.getAssignedEmployees().size(),is(equalTo(0)));
		}
	}	    


	@Then("^the project is no longer registered under current projects$")
	public void theProjectIsNoLongerRegisteredUnderCurrentProjects() throws Exception {
		assertFalse(planningApp.getProjects().contains(existingProject));
	}


	@Given("^the project has at least one activity assigned to it$")
	public void theProjectHasAtLeastOneActivityAssignedToIt() throws Exception {
		activity = new Activity(existingProject.getActivityIdCounter(), "Activity 1", 20, planningApp.yearWeekParser("2018-2"), planningApp.yearWeekParser("2018-5"));
		existingProject.addActivity(activity);

		activitiesToExistingProject = new ArrayList<>();
		activitiesToExistingProject = existingProject.getActivities();
		assertThat(activitiesToExistingProject.size(),is(1));
	}


	@Given("^the activity has at least one registered employee assigned to it$")
	public void theActivityHasAtLeastOneRegisteredEmployeeAssignedToIt() throws Exception {
		assertThat(existingProject.getActivities().size(),is(equalTo(1)));
		activity = existingProject.getActivities().get(0);

		employee = new Employee("anje", "Anders Jensen");
		planningApp.adminLogin("admin1234");
		planningApp.registerEmployee(employee);
		planningApp.adminLogOut();
		assertTrue(planningApp.getEmployees().contains(employee));

		try {
			activity.assignEmployee(employee);
			assertTrue(activity.getAssignedEmployees().contains(employee));
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}

		employeesOnExistingProject = new ArrayList<>();
		for (Activity a: activitiesToExistingProject) {
			employeesOnExistingProject.addAll(a.getAssignedEmployees());
		}

		assertThat(employeesOnExistingProject.size(),is(1));
	}

	
	@Given("^that a project with the name \"([^\"]*)\" has already been removed$")
	public void thatAProjectWithTheNameHasAlreadyBeenRemoved(String name) throws Exception {
	    existingProject= new Project(name, planningApp.yearWeekParser("2018-2")); // This name is misleading, but required for the removing check. 
	    
	    assertFalse(planningApp.getProjects().contains(existingProject));
	}



	//



	/****************************************************************************************/



	//Christina 
	/****************************************************************************************/

	//Admin add employee to system 
	//1
	@Given("^there is an employee with \"([^\"]*)\" and name \"([^\"]*)\"$")
	public void thereIsAnEmployeeWithAndName(String employeeID, String employeeName) throws Exception {
		employee = new Employee(employeeID, employeeName);
		assertThat(employee.getID(),is(equalTo(employeeID)));
		assertThat(employee.getName(),is(equalTo(employeeName)));

	}

	@When("^the administrator registers the employee$")
	public void theAdministratorRegistersTheEmployee() throws Exception {
		planningApp.registerEmployee(employee);
	}

	@Then("^the employee with ID \"([^\"]*)\"  is a registered employee$")
	public void theEmployeeWithIDIsARegisteredEmployee(String arg1) throws Exception {
		List<Employee> employees = planningApp.getEmployees();
		assertThat(employees.size(),is(1));
		Employee emp = employees.get(0);
		assertThat(emp.getID(), is(employee.getID()));
		assertThat(emp.getName(), is(employee.getName()));

	}


	//2.
	@Given("^an employee with ID \"([^\"]*)\" and name \"([^\"]*)\" is registered$")
	public void anEmployeeWithIDAndNameIsRegistered(String employeeID, String employeeName) throws Exception {
		employee = new Employee(employeeID, employeeName);
		assertThat(employee.getID(), is(equalTo(employeeID)));
		assertThat(employee.getName(), is(equalTo(employeeName)));

	}

	@When("^the administrator registers the employee again$")
	public void theAdministratorRegistersTheEmployeeAgain() throws Exception {
		try {
			planningApp.registerEmployee(helper.getEmployee());
			planningApp.registerEmployee(helper.getEmployee());
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}

	}

	@Then("^I get an error message \"([^\"]*)\"$")
	public void iGetAnErrorMessage(String errormessage) throws Exception {
		assertEquals(errormessage, errorMessage.getErrorMessage());
		
	}


	//3
	@When("^the administrator registers the employee with name \"([^\"]*)\" and ID \"([^\"]*)\"$")
	public void theAdministratorRegistersTheEmployeeWithNameAndID(String employeeName, String employeeID) throws Exception {
		planningApp.registerEmployee(helper.getEmployee());

		employee = new Employee(employeeID, employeeName);
		assertThat(employee.getID(), is(equalTo(employeeID)));
		assertThat(employee.getName(), is(equalTo(employeeName)));
		try {
			planningApp.registerEmployee(employee);
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	@Then("^the employee is not registered$")
	public void theEmployeeIsNotRegistered() throws Exception {
		assertThat(planningApp.getEmployees().contains(this.employee), is(false));
	}


	// Admin removes employee from system
	//1.
	@Given("^there is a employee with ID \"([^\"]*)\"$")
	public void thereIsAEmployeeWithID(String employeeID) throws Exception {
		planningApp.adminLogin("admin1234");
		planningApp.registerEmployee(helper.getEmployee());

	}

	@When("^the administrator removes the employee$")
	public void theAdministratorRemovesTheEmployee() throws Exception {
		planningApp.removeEmployee(helper.getEmployee());
	}

	@Then("^the employee is removed from the list of employees$")
	public void theEmployeeIsRemovedFromTheListOfEmployees() throws Exception {
		assertThat(planningApp.getEmployees().contains(this.employee), is(false));
	}

	// 2
	@Given("^that there is a employee with ID \"([^\"]*)\"$")
	public void thatThereIsAEmployeeWithID(String employeeID) throws Exception {
		employee = helper.getEmployee();
		planningApp.adminLogin("admin1234");
		planningApp.registerEmployee(employee);

	}

	@Given("^that the admin is not logged in$")
	public void thatTheAdminIsNotLoggedIn() throws Exception {
		planningApp.adminLogOut();

	}
	@When("^the administrator tries to remove the employee$")
	public void theAdministratorTriesToRemoveTheEmployee() throws Exception {
		try {
			planningApp.removeEmployee(helper.getEmployee());
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());

		}
	}

	@Then("^the employee is not removed from the list of employees$")
	public void theEmployeeIsNotRemovedFromTheListOfEmployees() throws Exception {
		assertThat(planningApp.getEmployees().contains(employee), is(true));
	}

	// 3
	@Given("^an employee is not in the list of employees$")
	public void anEmployeeIsNotInTheListOfEmployees() throws Exception {
		planningApp.registerEmployee(helper.getEmployee());
		planningApp.removeEmployee(helper.getEmployee());
	}

	@When("^the administrator tries to remove the employee from list$")
	public void theAdministratorTriesToRemoveTheEmployeeFromList() throws Exception {
		try {
			planningApp.removeEmployee(helper.getEmployee());
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	/****************************************************************************************/

	//Lukas 
	//Admin assigns employee as teamleader of a project 
	/****************************************************************************************/

	@Given("^there is an employee with id \"([^\"]*)\"$")
	public void thereIsAnEmployeeWithId(String arg1) throws Exception {
		employee = helper.getEmployee();
		planningApp.adminLogin("admin1234");
		planningApp.registerEmployee(employee);
		planningApp.adminLogOut();
		assertTrue(planningApp.searchEmployeeID(arg1) != null);
	}

	@When("^the administrator assigns the employee  as teamleader for project$")
	public void theAdministratorAssignsTheEmployeeAsTeamleaderForProject() throws Exception {
		existingProject.assignTeamleader(employee);
	}



	@Then("^the employee is set to teamleader of the project$")
	public void theEmployeeIsSetToTeamleaderOfTheProject() throws Exception {
		assertTrue(existingProject.getTeamleader().equals(employee));
	} 

	// 

	@Given("^the project has a teamleader with id \"([^\"]*)\"$")
	public void theProjectHasATeamleaderWithId(String employeeId) throws Exception {
		employee = new Employee("Anders Jensen", employeeId);

		planningApp.adminLogin("admin1234");
		planningApp.registerEmployee(employee);
		assertTrue(planningApp.getEmployees().contains(employee));
		planningApp.adminLogOut();

		existingProject.assignTeamleader(employee);
		assertTrue(existingProject.getTeamleader().equals(employee));
	}

	@When("^the administrator unassigns the employee as teamleader for project$")
	public void theAdministratorUnassignsTheEmployeeAsTeamleaderForProject() throws Exception {
		existingProject.unassignTeamleader();
	}


	@Then("^the project no longer has the employee as teamleader$")
	public void theProjectNoLongerHasTheEmployeeAsTeamleader() throws Exception {
		assertNull(existingProject.getTeamleader());
	}


	/****************************************************************************************/









}