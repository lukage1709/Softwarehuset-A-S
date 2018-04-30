package dtu.planning.acceptance_tests;

import java.util.List;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import dtu.planning.app.Activity;
import dtu.planning.app.Employee;
import dtu.planning.app.PlanningApp;
import dtu.planning.app.Project;


public class TeamLeaderSteps {
	private PlanningApp planningApp;
	private Project project; 
	private Employee employee, employee2;
	private ErrorMessageHolder errorMessage;
	public EmployeeHelper helper;
	private List<Employee> employeeID;
	private Employee employeeLoggedIn;
	private Activity activity;

	public TeamLeaderSteps(PlanningApp planningApp, ErrorMessageHolder errorMessage, EmployeeHelper helper) {
		this.planningApp = planningApp;
		this.errorMessage = errorMessage;
		this.helper = helper;
		
		this.employeeLoggedIn = null;
	}

	/* Thomas */
	/****************************************************************************************/
	
	@Given("^an employee is logged in$")
	public void anEmployeeIsLoggedIn() throws Exception {
	    // create dummy employee
		employee = helper.getEmployee();
	    planningApp.adminLogin("admin1234");
	    planningApp.registerEmployee(employee);
	    assertTrue(planningApp.getEmployees().contains(employee));
	    planningApp.adminLogOut();
	    
	    planningApp.userLogin(employee.getID());
	    employeeLoggedIn = planningApp.getcurrentUser();
	    assertTrue(employeeLoggedIn.equals(employee));
		
	}

	@Given("^there is a project with ID \"([^\"]*)\"$")
	public void thereIsAProjectWithID(String projectID) throws Exception {
		// create dummy project
		planningApp.adminLogin("admin1234");
		project = new Project(projectID, planningApp.yearWeekParser("2018-5"));
		planningApp.createProject(project);
		project = planningApp.searchProjectByID(projectID);
		assertThat(project,is(notNullValue()));
		
		planningApp.adminLogOut();
	}

	@Given("^the employee is team leader on that project$")
	public void theEmployeeIsTeamLeaderOnThatProject() throws Exception {
		// and assign team leader
		planningApp.adminLogin("admin1234");
		project.assignTeamLeader(employeeLoggedIn);
		planningApp.adminLogOut();
		
		assertTrue(project.getTeamLeader().equals(employeeLoggedIn));
	}
	
	@When("^teamleader creates a new activity named \"([^\"]*)\", estimatedhours (\\d+), startweek \"([^\"]*)\" and endweek \"([^\"]*)\"$")
	public void teamleaderCreatesANewActivityNamedEstimatedhoursStartweekAndEndweek(String name, int estHours, String startWeek, String endWeek) throws Exception {
	    activity = new Activity(project.getActivityIdCounter(), name, estHours, planningApp.yearWeekParser(startWeek), planningApp.yearWeekParser(endWeek));
	    assertThat(activity.getActivityName(),is(equalTo(name)));
		assertThat(activity.getEstimatedHours(),is(equalTo(estHours)));
		assertThat(activity.getStartWeek(),is(equalTo(planningApp.yearWeekParser(startWeek))));
		assertThat(activity.getEndWeek(),is(equalTo(planningApp.yearWeekParser(endWeek))));
	}

	@When("^and adds the activity to the project$")
	public void andAddsTheActivityToTheProject() throws Exception {
		project.addActivity(activity);
	}

	@Then("^the activity is in the activities list of that project$")
	public void theActivityIsInTheActivitiesListOfThatProject() throws Exception {
		assertTrue(project.getActivities().contains(activity));
	}
	
	@Given("^there is an activity with name \"([^\"]*)\" in the project$")
	public void thereIsAnActivityWithNameInTheProject(String activityName) throws Exception {
		activity = new Activity(project.getActivityIdCounter(), activityName, 0, planningApp.yearWeekParser("2018-1"), planningApp.yearWeekParser("2018-3"));
		project.addActivity(activity);
		
	    assertThat(project.getActivityByName(activityName), is(notNullValue()));
	}

	@When("^the teamleader creates the activity again$")
	public void theTeamleaderCreatesTheActivityAgain() throws Exception {
	    try {
	    	project.addActivity(activity);
	    } catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}
	
	@When("^teamleader worngly adds activity named \"([^\"]*)\", estimatedhours (\\d+), startweek \"([^\"]*)\" and endweek \"([^\"]*)\" to the project$")
	public void teamleaderWornglyAddsActivityNamedEstimatedhoursStartweekAndEndweekToTheProject(String name, int estHours, String startWeek, String endWeek) throws Exception {
		activity = new Activity(project.getActivityIdCounter(), name, estHours, planningApp.yearWeekParser(startWeek), planningApp.yearWeekParser(endWeek));
	    assertThat(activity.getActivityName(),is(equalTo(name)));
		assertThat(activity.getEstimatedHours(),is(equalTo(estHours)));
		assertThat(activity.getStartWeek(),is(equalTo(planningApp.yearWeekParser(startWeek))));
		assertThat(activity.getEndWeek(),is(equalTo(planningApp.yearWeekParser(endWeek))));
		
		try {
	    	project.addActivity(activity);
	    } catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}
	
	
	/****************************************************************************************/
	
	
	
	// Christina 
	/****************************************************************************************/
	
	// teamleader assigns employee to activity
	
	// 1
	@Given("^there is an activity with name \"([^\"]*)\" in the activities list of that project$")
	public void thereIsAnActivityWithNameInTheActivitiesListOfThatProject(String activityName) throws Exception {
		activity = new Activity(project.getActivityIdCounter(), activityName, 0, planningApp.yearWeekParser("2018-1"), planningApp.yearWeekParser("2018-3"));
		project.addActivity(activity);
		assertThat(project.getActivityByName(activityName), is(notNullValue()));
	}

	@Given("^employee with ID \"([^\"]*)\" is available$")
	public void employeeWithIDIsAvailable(String employeeID) throws Exception {
	    assertTrue(planningApp.getEmployees().contains(planningApp.searchEmployeeID(employeeID)));
	}

	@When("^teamleader assigns employee with ID \"([^\"]*)\" to activity \"([^\"]*)\"$")
	public void teamleaderAssignsEmployeeWithIDToActivity(String employeeID, String activityName) throws Exception {
		employee = planningApp.searchEmployeeID(employeeID);
	    activity.assignEmployee(employee);
	    assertTrue(activity.getAssignedEmployees().contains(employee));
	}

	@Then("^the employee is assigned to activity in project with ID$")
	public void theEmployeeIsAssignedToActivityInProjectWithID() throws Exception {
		assertThat(activity.getAssignedEmployees().contains(employee), is(true));
		
	}
	
	// her skulle der måske kaldes den der beregning??
	@Then("^the employee with ID \"([^\"]*)\" is still at the list \"([^\"]*)\"$")
	public void theEmployeeWithIDIsStillAtTheList(String employeeID, String arg2) throws Exception {
	    assertTrue(planningApp.getEmployees().contains(planningApp.searchEmployeeID(employeeID)));
	}
	
	// 2
	@Given("^there is an employee with ID \"([^\"]*)\" assigned to the activity in the project$")
	public void thereIsAnEmployeeWithIDAssignedToTheActivityInTheProject(String employeeID) throws Exception {
		employee = planningApp.searchEmployeeID(employeeID);
	    activity.assignEmployee(employee);
	    assertThat(activity.getAssignedEmployees().contains(employee), is(true));
		
	}

	@When("^teamleader unassigns employee with ID \"([^\"]*)\" from the activity in the project$")
	public void teamleaderUnassignsEmployeeWithIDFromTheActivityInTheProject(String employeeID) throws Exception {
	    employee = planningApp.searchEmployeeID(employeeID);
	    activity.unassignEmployee(employee);
	}

	@Then("^the employee is unassigned from activity \"([^\"]*)\" in the project$")
	public void theEmployeeIsUnassignedFromActivityInTheProject(String arg1) throws Exception {
		assertThat(activity.getAssignedEmployees().contains(employee), is(false));
	}
	 
	
	/****************************************************************************************/
	
	
}