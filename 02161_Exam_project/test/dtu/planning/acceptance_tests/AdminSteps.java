package dtu.planning.acceptance_tests;

import java.util.List;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


import dtu.planning.app.Employee;
import dtu.planning.app.PlanningApp;
import dtu.planning.app.Project;


public class AdminSteps {
	private PlanningApp planningApp;
	private Project project; 
	private Employee employee;
	private ErrorMessageHolder errorMessage;
	public EmployeeHelper helper;
	private List<Employee> employeeID;
	private Employee teamLeader;
	private Project project2;
	private Employee employee2;
	private Employee employee3;
	private Project project3;

	public AdminSteps(PlanningApp planningApp, ErrorMessageHolder errorMessage, EmployeeHelper helper) {
		this.planningApp = planningApp;
		this.errorMessage = errorMessage;
		this.helper = helper;
	}

	/* Linnea */
	/****************************************************************************************/


	@When("^when the administrator creates a project with name \"([^\"]*)\", startdate \"([^\"]*)\" og enddate \"([^\"]*)\"$")
	public void whenTheAdministratorCreatesAProjectWithNameStartdateOgEnddate(String projectName, String startDate, String endDate) throws Exception {
		project = new Project(projectName,startDate, endDate);
		assertThat(project.getName(),is(equalTo(projectName)));
		assertThat(project.getStartDate(),is(equalTo(startDate)));
		assertThat(project.getEndDate(),is(equalTo(endDate)));
		
		planningApp.createProject(projectName, startDate, endDate);
	}
	
	@Then("^there is a new project with name \"([^\"]*)\", startdate \"([^\"]*)\" og enddate \"([^\"]*)\"$")
	public void thereIsANewProjectWithNameStartdateOgEnddate(String arg1, String arg2, String arg3) throws Exception {
	    List<Project> projects = planningApp.getProjects();
	    assertThat(projects.size(), is(1));
	    Project p = projects.get(0);
	    assertThat(p.getName(), is(project.getName()));
	    assertThat(p.getStartDate(), is(project.getStartDate()));
	    assertThat(p.getEndDate(), is(project.getEndDate()));
	    
	}
	
	
	/****************************************************************************************/

	
	
	//Christina 
	//Admin add employee to system 
	/****************************************************************************************/
	
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
	
	// admin removes employee from system
	
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
		assertThat(planningApp.getEmployees().contains(employee), is(false));

	}
	
	/****************************************************************************************/
	
	//Lukas 
		//Admin assigns employee as teamleader of a project 
		/****************************************************************************************/

	@Given("^a project with id \"([^\"]*)\" exists$")
	public void aProjectWithIdExists(String projectID) throws Exception {
		// create dummy project
		
		planningApp.createProject(projectID, "2018", "2018");
		
		project2 = planningApp.searchProjectByID(projectID);
		assertThat(project2,is(notNullValue()));
	    
	}

	@Given("^there is an employee with id \"([^\"]*)\"$")
	public void thereIsAnEmployeeWithId(String employeeID) throws Exception {
		employee2 = new Employee(employeeID); // du kommer til at have et problem her. skal ogs� bruge name for employee
		planningApp.registerEmployee(employee2);
		List<Employee> employee_with_id = planningApp.getEmployees();
	    assertTrue(employee_with_id.contains(employee2));
	   
	   
	}

	@When("^the administrator assigns the employee as teamleader$") 
	public void theAdministratorAssignsTheEmployeeAsTeamleader() throws Exception { 
		// teamLeader = employee2;   //planningApp.searchEmployeeID(employeeID);
		project2.assignTeamLeader(employee2);
	   
	   
	}

	@Then("^the employee with id \"([^\"]*)\" is set to teamleader of the project$")
	public void theEmployeeWithIdIsSetToTeamleaderOfTheProject(String employeeID) throws Exception {
		assertTrue(project2.getTeamLeader().equals(planningApp.searchEmployeeID(employeeID)));
	}
	
	//Admin unassigns employee from being the teamleader of a project
	
	@Given("^there is an employee with id \"([^\"]*)\" assigned as teamleader to project \"([^\"]*)\"$")
	public void thereIsAnEmployeeWithIdAssignedAsTeamleaderToProject(String employeeID, String projectID) throws Exception {
		project3 = planningApp.searchProjectByID(projectID);
		employee3 = new Employee(employeeID);
		planningApp.registerEmployee(employee3);
		project3.assignTeamLeader(employee3);
		   
		
		assertTrue(planningApp.searchProjectByID(projectID).getTeamLeader().equals(planningApp.searchEmployeeID(employeeID)));
	}


	@When("^the administrator unassigns the employee with id \"([^\"]*)\" as teamleader for project \"([^\"]*)\"$")
	public void theAdministratorUnassignsTheEmployeeWithIdAsTeamleaderForProject(String employeeID, String projectID) throws Exception {
		planningApp.searchProjectByID(projectID).unassignTeamLeader(planningApp.searchEmployeeID(employeeID));
	}
	
	@Then("^the employee with id \"([^\"]*)\" is no longer teamleader of the project with id \"([^\"]*)\"$")
	public void theEmployeeWithIdIsNoLongerTeamleaderOfTheProjectWithId(String employeeID, String projectID) throws Exception {
		assertThat(planningApp.searchProjectByID(projectID).getTeamLeader(),is(not(planningApp.searchEmployeeID(employeeID))));
	}
	
	/****************************************************************************************/



	

		
	  
	
}