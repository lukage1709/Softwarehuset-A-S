package dtu.planning.acceptance_tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
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
	private Project nonExistingProject;
	private Activity activity;
	private Employee employee;
	private ErrorMessageHolder errorMessage;
	public EmployeeHelper helper;
	private List<Employee> employeeID;
	private Employee teamLeader;
	private Project project2;
	private Employee employee2;
	private Employee employee3;
	private Project project3;
	private Project project;

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


	@Given("^that a project with id \"([^\"]*)\" exists$")
	public void thatAProjectWithIdExists(String projectId) throws Exception {
		existingProject = new Project("projectToDelete",planningApp.yearWeekParser("2018-02"));

		planningApp.adminLogin("admin1234");
		planningApp.createProject(existingProject);
		planningApp.adminLogOut();

		assertEquals(existingProject.getProjectNumber(),projectId);
	}  


	@When("^the administrator removes the project(?: again|)$")
	public void theAdministratorRemovesTheProject() throws OperationNotAllowedException {
		try {
			planningApp.removeProject(existingProject);
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	@Then("^there are no longer any employees assigned to the activities$")
	public void thereAreNoLongerAnyEmployeesAssignedToTheActivities() throws Exception {
		if (existingProject.getActivities() != null) {
			for (Activity a: existingProject.getActivities()) {
				assertThat(a.getAssignedEmployees().size(),is(equalTo(0)));
			}
		}	    
	} 

	@Then("^the project is no longer registered under current projects$")
	public void theProjectIsNoLongerRegisteredUnderCurrentProjects() throws Exception {
		assertFalse(planningApp.getProjects().contains(existingProject));
	}


	/*@Then("^the employees are no longer assigned to the project$")
	public void theEmployeesAreNoLongerAssignedToTheProject() throws Exception {
		List<Employee> employeesToUnassign = new ArrayList<>();	

		for (Activity a: existingProject.getActivities()) {
			employeesToUnassign = a.getAssignedEmployees();

			for (Employee e: employeesToUnassign) {
				assertFalse(e.getAssignedActivities().contains(a));
			}
		}

	} FOR LATER

	 */

	@Given("^the project has at least one activity assigned to it$")
	public void theProjectHasAtLeastOneActivityAssignedToIt() throws Exception {
		activity = new Activity(existingProject.getActivityIdCounter(), "Activity 1", 20, planningApp.yearWeekParser("2018-2"), planningApp.yearWeekParser("2018-5"));
		existingProject.addActivity(activity);
		assertThat(existingProject.getActivities().size(),is(equalTo(1)));
	}

	@Given("^the activity has at least one employee assigned to it$")
	public void theActivityHasAtLeastOneEmployeeAssignedToIt() throws Exception {
		employee = planningApp.searchEmployeeID("anje");
		activity.assignEmployee(employee);
		assertTrue(activity.getAssignedEmployees().contains(employee));
	}


	@Given("^that a project with id \"([^\"]*)\" no longer exists$")
	public void thatAProjectWithIdNoLongerExists(String projectId) throws Exception {
		String startDateForProject = projectId.split("-")[0] + "-1";
		nonExistingProject = new Project("projectToDelete",planningApp.yearWeekParser(startDateForProject));

		assertEquals(nonExistingProject.getProjectNumber(),projectId);
		assertFalse(planningApp.getProjects().contains(nonExistingProject));
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
	public void iGetAnErrorMessage(String errorMessage) throws Exception {
		assertEquals(errorMessage, this.errorMessage.getErrorMessage());
	}


	//3
	@When("^the administrator registers the employee with name \"([^\"]*)\" and ID \"([^\"]*)\"$")
	public void theAdministratorRegistersTheEmployeeWithNameAndID(String employeeName, String employeeID) throws Exception {
		//employee = new Employee("Anje0001", "Anders Jensen");
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
		planningApp.registerEmployee(helper.getEmployee());
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


	/*@Given("^there is an employee with id \"([^\"]*)\"$")
	public void thereIsAnEmployeeWithId(String employeeId) throws Exception {
		employee = new Employee("Anders Jensen", employeeId);
		planningApp.adminLogin("admin1234");
		planningApp.registerEmployee(employee);
		assertTrue(planningApp.getEmployees().contains(employee));
		planningApp.adminLogOut();
	} */

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

	/*

	//Admin unassigns employee from being the teamleader of a project

	@Given("^there is an employee with id \"([^\"]*)\" assigned as teamleader to project \"([^\"]*)\"$")
	public void thereIsAnEmployeeWithIdAssignedAsTeamleaderToProject(String employeeID, String projectID) throws Exception {
		project3 = planningApp.searchProjectByID(projectID);
		employee3 = new Employee(employeeID);
		planningApp.registerEmployee(employee3);
		project3.assignTeamLeader(employee3);


		assertTrue(planningApp.searchProjectByID(projectID).getTeamLeader().equals(planningApp.searchEmployeeID(employeeID)));
	 */

	//unassign teamleader (lukas)

	/* @Given("^there is an employee with id \"([^\"]*)\" assigned as teamleader to project \"([^\"]*)\"$")
	public void thereIsAnEmployeeWithIdAssignedAsTeamleaderToProject(String arg1, String arg2) throws Exception {

	    assertTrue(project.getTeamleader() == employee);

	}

	@When("^the administrator unassigns the employee as teamleader for project$")
	public void theAdministratorUnassignsTheEmployeeAsTeamleaderForProject() throws Exception {
	    project.unassignTeamleader();
	    throw new PendingException();
	}
	 */
	/*

	@Then("^the employee with id \"([^\"]*)\" is no longer teamleader of the project with id \"([^\"]*)\"$")
	public void theEmployeeWithIdIsNoLongerTeamleaderOfTheProjectWithId(String employeeID, String projectID) throws Exception {
		assertThat(planningApp.searchProjectByID(projectID).getTeamLeader(),is(not(planningApp.searchEmployeeID(employeeID))));
	}
	 */
	/*

	@Then("^the employee with is no longer teamleader of the project$")
	public void theEmployeeWithIsNoLongerTeamleaderOfTheProject() throws Exception {
	    assertTrue(project.getTeamleader() == null);

	}

	 */
	/****************************************************************************************/









}