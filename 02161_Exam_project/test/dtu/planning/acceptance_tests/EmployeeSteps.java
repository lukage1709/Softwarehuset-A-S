package dtu.planning.acceptance_tests;


import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import dtu.planning.app.Activity;
import dtu.planning.app.Employee;
import dtu.planning.app.PlanningApp;
import dtu.planning.app.Project;


public class EmployeeSteps {
	private PlanningApp planningApp;
	private EmployeeHelper helper;
	private Employee employee;
	private Project project;
	private Activity activity;
	private int workedHours;
	private int registeredWorkHours;
	
	public EmployeeSteps(PlanningApp planningApp, EmployeeHelper helper) {
		this.planningApp = planningApp;
		this.helper = helper;
	}

	/* Thomas */
	/****************************************************************************************/
	
	@Given("^the employee \"([^\"]*)\" is logged into the system$")
	public void theEmployeeIsLoggedIntoTheSystem(String employeeID) throws Exception {
		employee = helper.getEmployee();
	    planningApp.adminLogin("admin1234");
	    planningApp.registerEmployee(employee);
	    planningApp.adminLogOut();
	    
	    planningApp.userLogin(employeeID);
	    assertTrue(planningApp.getcurrentUser().equals(employee));
	}

	@Given("^the activity \"([^\"]*)\" exists within the project with ID \"([^\"]*)\"$")
	public void theActivityExistsWithinTheProjectWithID(String activityName, String projectID) throws Exception {
		planningApp.adminLogin("admin1234");
		project = new Project(projectID, planningApp.yearWeekParser("2018-5"));;
		planningApp.createProject(project);
		activity = new Activity(project.getActivityIdCounter(), activityName, 0, planningApp.yearWeekParser("2018-1"), planningApp.yearWeekParser("2018-3"));
		project.addActivity(activity);
		planningApp.adminLogOut();
		
		assertTrue(planningApp.getProjects().contains(project));
		assertTrue(project.getActivities().contains(activity));
	}

	@Given("^the employee is assigned to the activity$")
	public void theEmployeeIsAssignedToTheActivity() throws Exception {
		activity.assignEmployee(employee);
	    assertTrue(activity.getAssignedEmployees().contains(employee));
	}

	@When("^the employee registers (\\d+) hours to the activity$")
	public void theEmployeeRegistersHoursToTheActivity(int hours) throws Exception {
		registeredWorkHours = activity.getWorkedHours();
	    workedHours = hours;
	    activity.registerWorkedHours(hours);
	}

	@Then("^the hours are registered on the activity$")
	public void theHoursAreRegisteredOnTheActivity() throws Exception {
	    assertThat(activity.getWorkedHours(), is(equalTo(registeredWorkHours + workedHours)));
	}
	
	
	/****************************************************************************************/
	
	
	
	
	
	
}