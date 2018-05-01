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
	private Employee mockEmployeeLoggedIn;
	private Activity newActivity, existingActivity;

	public TeamLeaderSteps(PlanningApp planningApp, ErrorMessageHolder errorMessage, EmployeeHelper helper) {
		this.planningApp = planningApp;
		this.errorMessage = errorMessage;
		this.helper = helper;
		
		this.mockEmployeeLoggedIn = null;
	}

	/* Thomas */
	/****************************************************************************************/
	
	@Given("^an employee is logged in$")
	public void anEmployeeIsLoggedIn() throws Exception {
	    // create dummy employee
		planningApp.adminLogin("admin1234");
		Employee employee = new Employee("Anje", "Anders Jensen");
		planningApp.registerEmployee(employee);
		assertTrue(planningApp.getEmployees().contains(employee));
		planningApp.adminLogOut();
		
		mockEmployeeLoggedIn = employee;
		assertThat(mockEmployeeLoggedIn, is(equalTo(employee)));
		
	}

	@Given("^there is a project with ID \"([^\"]*)\"$")
	public void thereIsAProjectWithID(String projectID) throws Exception {
		// create dummy project
		planningApp.adminLogin("admin1234");
		project = new Project(projectID, 2018, 5, 1);
		planningApp.createProject(project);
		project = planningApp.searchProjectByID(projectID);
		assertThat(project,is(notNullValue()));
		
		planningApp.adminLogOut();
	}

	@Given("^the employee is team leader on that project$")
	public void theEmployeeIsTeamLeaderOnThatProject() throws Exception {
		// and assign team leader
		planningApp.adminLogin("admin1234");
		project.assignTeamLeader(mockEmployeeLoggedIn);
		planningApp.adminLogOut();
		
		assertTrue(project.getTeamLeader().equals(mockEmployeeLoggedIn));
	}
	
	@When("^teamleader creates a new activity named \"([^\"]*)\", estimatedhours (\\d+), startweek \"([^\"]*)\" and endweek \"([^\"]*)\"$")
	public void teamleaderCreatesANewActivityNamedEstimatedhoursStartweekAndEndweek(String name, int estHours, String startWeek, String endWeek) throws Exception {
	    newActivity = new Activity(project.getActivityIdCounter(), name, estHours, planningApp.yearWeekParser(startWeek), planningApp.yearWeekParser(endWeek));
	    assertThat(newActivity.getActivityName(),is(equalTo(name)));
		assertThat(newActivity.getEstimatedHours(),is(equalTo(estHours)));
		assertThat(newActivity.getStartWeek(),is(equalTo(planningApp.yearWeekParser(startWeek))));
		assertThat(newActivity.getEndWeek(),is(equalTo(planningApp.yearWeekParser(endWeek))));
	}

	@When("^and adds the activity to the project$")
	public void andAddsTheActivityToTheProject() throws Exception {
		project.addActivity(newActivity);
	}

	@Then("^the activity is in the activities list of that project$")
	public void theActivityIsInTheActivitiesListOfThatProject() throws Exception {
		System.out.println(newActivity.getActivityId());
		assertTrue(project.getActivities().contains(newActivity));
	}
	
	@Given("^there is an activity with name \"([^\"]*)\" in the project$")
	public void thereIsAnActivityWithNameInTheProject(String activityName) throws Exception {
		newActivity = new Activity(project.getActivityIdCounter(), activityName, 0, planningApp.yearWeekParser("2018-1"), planningApp.yearWeekParser("2018-3"));
		project.addActivity(newActivity);
		
	    assertThat(project.getActivityByName(activityName), is(notNullValue()));
	}

	@When("^the teamleader creates the activity again$")
	public void theTeamleaderCreatesTheActivityAgain() throws Exception {
	    try {
	    	project.addActivity(newActivity);
	    } catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}
	
	@When("^teamleader worngly adds activity named \"([^\"]*)\", estimatedhours (\\d+), startweek \"([^\"]*)\" and endweek \"([^\"]*)\" to the project$")
	public void teamleaderWornglyAddsActivityNamedEstimatedhoursStartweekAndEndweekToTheProject(String name, int estHours, String startWeek, String endWeek) throws Exception {
		newActivity = new Activity(project.getActivityIdCounter(), name, estHours, planningApp.yearWeekParser(startWeek), planningApp.yearWeekParser(endWeek));
	    assertThat(newActivity.getActivityName(),is(equalTo(name)));
		assertThat(newActivity.getEstimatedHours(),is(equalTo(estHours)));
		assertThat(newActivity.getStartWeek(),is(equalTo(planningApp.yearWeekParser(startWeek))));
		assertThat(newActivity.getEndWeek(),is(equalTo(planningApp.yearWeekParser(endWeek))));
		
		try {
	    	project.addActivity(newActivity);
	    } catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}
	
	@Given("^the employee is not team leader on that project$")
	public void theEmployeeIsNotTeamLeaderOnThatProject() throws Exception {
		planningApp.adminLogin("admin1234");
		Employee employee2 = new Employee("Heha", "Henning Hansen");
		planningApp.registerEmployee(employee2);
		project.assignTeamLeader(employee2);
		planningApp.adminLogOut();
		
		assertFalse(project.getTeamLeader().equals(mockEmployeeLoggedIn));
	}
	
	
	/****************************************************************************************/
	
	
	
	// Christina 
	/****************************************************************************************/
	
	// teamleader assigns employee to activity
	
	// 1
	@Given("^there is an activity with name \"([^\"]*)\" in the activities list of that project$")
	public void thereIsAnActivityWithNameInTheActivitiesListOfThatProject(String activityName) throws Exception {
		newActivity = new Activity(project.getActivityIdCounter(), activityName, 0, planningApp.yearWeekParser("2018-1"), planningApp.yearWeekParser("2018-3"));
		project.addActivity(newActivity);
		assertThat(project.getActivityByName(activityName), is(notNullValue()));
	}

	@Given("^employee with ID \"([^\"]*)\" is available$")
	public void employeeWithIDIsAvailable(String employeeID) throws Exception {
	    assertTrue(planningApp.getEmployees().contains(planningApp.searchEmployeeID(employeeID)));
	}

	@When("^teamleader assigns employee with ID \"([^\"]*)\" to activity \"([^\"]*)\"$")
	public void teamleaderAssignsEmployeeWithIDToActivity(String employeeID, String activityName) throws Exception {
		employee = planningApp.searchEmployeeID(employeeID);
	    newActivity.assignEmployee(employee);
	    assertTrue(newActivity.getAssignedEmployees().contains(employee));
	}

	@Then("^the employee is assigned to activity in project with ID$")
	public void theEmployeeIsAssignedToActivityInProjectWithID() throws Exception {
		assertThat(newActivity.getAssignedEmployees().contains(employee), is(true));
		
	}
	
	// her skulle der måske kaldes den der beregning??
	// eller noget med at se om employee er ledig uden for tidspunktet??
	@Then("^the employee with ID \"([^\"]*)\" is still at the list \"([^\"]*)\"$")
	public void theEmployeeWithIDIsStillAtTheList(String employeeID, String arg2) throws Exception {
	    assertTrue(planningApp.getEmployees().contains(planningApp.searchEmployeeID(employeeID)));
	}
	
	// 2
	@Given("^there is an employee with ID \"([^\"]*)\" assigned to the activity in the project$")
	public void thereIsAnEmployeeWithIDAssignedToTheActivityInTheProject(String employeeID) throws Exception {
		employee = planningApp.searchEmployeeID(employeeID);
	    newActivity.assignEmployee(employee);
	    employee.assignActivity(newActivity);
	    assertThat(newActivity.getAssignedEmployees().contains(employee), is(true));
	    assertThat(employee.getAssignedActivities().contains(newActivity), is(true));
		
	}

	@When("^teamleader unassigns employee with ID \"([^\"]*)\" from the activity in the project$")
	public void teamleaderUnassignsEmployeeWithIDFromTheActivityInTheProject(String employeeID) throws Exception {
	    employee = planningApp.searchEmployeeID(employeeID);
	    newActivity.unassignEmployee(employee);
	    employee.unassignActivity(newActivity);
	    
	}

	@Then("^the employee is unassigned from activity \"([^\"]*)\" in the project$")
	public void theEmployeeIsUnassignedFromActivityInTheProject(String arg1) throws Exception {
		assertThat(newActivity.getAssignedEmployees().contains(employee), is(false));
	}
	
	// teamleader requests list of available employees
	
	// 1
	@Given("^there is an activity with name \"([^\"]*)\" startdate \"([^\"]*)\" and enddate \"([^\"]*)\" in the activities list$")
	public void thereIsAnActivityWithNameStartdateAndEnddateInTheActivitiesList(String activityName, String startDate, String endDate) throws Exception {
		existingActivity = new Activity(project.getActivityIdCounter(), "Existing Activity", 0, planningApp.yearWeekParser("2018-2"), planningApp.yearWeekParser("2018-5"));
		project.addActivity(existingActivity);
		
		newActivity = new Activity(project.getActivityIdCounter(), activityName, 0, planningApp.yearWeekParser(startDate), planningApp.yearWeekParser(endDate));
		project.addActivity(newActivity);

		// dummie employee
		planningApp.adminLogin("admin1234");
		employee = planningApp.getEmployees().get(0);
		employee2 = new Employee("Abcd", "Abcd AbcdAbcd");
	    planningApp.registerEmployee(employee2);
	    planningApp.adminLogOut();
	    
	    existingActivity.assignEmployee(employee);
	    
	}
	
	@When("^Teamleader requests available employees in the same time as the activity$")
	public void teamleaderRequestsAvailableEmployeesInTheSameTimeAsTheActivity() throws Exception {
		planningApp.searchEmployeeID(employee.getID()).assignActivity(existingActivity); 
		planningApp.getAvailableEmployeesInWeek(newActivity.getStartWeek(), newActivity.getEndWeek());

	}

	@Then("^Teamleader receives list of availability employees not working in week two, tree and four$")
	public void teamleaderReceivesListOfAvailabilityEmployeesNotWorkingInWeekTwoTreeAndFour() throws Exception {
		assertThat(planningApp.getAvailableEmployees().contains(employee), is(false));
		assertThat(planningApp.getAvailableEmployees().contains(employee2), is(true));

	}
	// 2
	@Then("^Teamleader receives list of availability employees not working in week one, and two$")
	public void teamleaderReceivesListOfAvailabilityEmployeesNotWorkingInWeekOneAndTwo() throws Exception {
		assertThat(planningApp.getAvailableEmployees().contains(employee), is(false));
		assertThat(planningApp.getAvailableEmployees().contains(employee2), is(true));
		
	}
	// 3
	@Then("^Teamleader receives list of availability employees not working in week four and five$")
	public void teamleaderReceivesListOfAvailabilityEmployeesNotWorkingInWeekFourAndFive() throws Exception {
		assertThat(planningApp.getAvailableEmployees().contains(employee), is(false));
		assertThat(planningApp.getAvailableEmployees().contains(employee2), is(true));
	}
	// 4
	@Then("^Teamleader receives list of availability employees not working in week six$")
	public void teamleaderReceivesListOfAvailabilityEmployeesNotWorkingInWeekSix() throws Exception {
		assertThat(planningApp.getAvailableEmployees().contains(employee), is(true));
		assertThat(planningApp.getAvailableEmployees().contains(employee2), is(true));
	}
	
	/****************************************************************************************/
	
	
}