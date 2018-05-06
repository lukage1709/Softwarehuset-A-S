package dtu.planning.acceptance_tests;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import dtu.planning.app.Activity;
import dtu.planning.app.Employee;
import dtu.planning.app.PlanningApp;
import dtu.planning.app.Project;


public class TeamLeaderSteps {
	private PlanningApp planningApp;
	private Project existingProject;
	private Employee employee, employee2, availableEmployee, assignedEmployee, teamleader;
	private ErrorMessageHolder errorMessage;
	public EmployeeHelper helper;
	private Activity newActivity, existingActivity;
	private Employee employeeLoggedIn;
	private Activity activity;
	private List<Employee> availableEmployees = new ArrayList();

	public TeamLeaderSteps(PlanningApp planningApp, ErrorMessageHolder errorMessage, EmployeeHelper helper) {
		this.planningApp = planningApp;
		this.errorMessage = errorMessage;
		this.helper = helper;
		
		this.employeeLoggedIn = null;
	}

	/* Thomas */
	/****************************************************************************************/

	
	@When("^teamleader creates a new activity named \"([^\"]*)\", estimatedhours (\\d+), startweek \"([^\"]*)\" and endweek \"([^\"]*)\"$")
	public void teamleaderCreatesANewActivityNamedEstimatedhoursStartweekAndEndweek(String name, int estHours, String startWeek, String endWeek) throws Exception {
	    newActivity = new Activity(existingProject.getActivityIdCounter(), name, estHours, planningApp.yearWeekParser(startWeek), planningApp.yearWeekParser(endWeek));
	    assertThat(newActivity.getActivityName(),is(equalTo(name)));
		assertThat(newActivity.getEstimatedHours(),is(equalTo(estHours)));
		assertThat(newActivity.getStartWeek(),is(equalTo(planningApp.yearWeekParser(startWeek))));
		assertThat(newActivity.getEndWeek(),is(equalTo(planningApp.yearWeekParser(endWeek))));
	}

	@When("^and adds the activity to the project$")
	public void andAddsTheActivityToTheProject() throws Exception {
		existingProject.addActivity(newActivity);
	}
	
	@Then("^the activity is in the activities list of that project$")
	public void theActivityIsInTheActivitiesListOfThatProject() throws Exception {
		assertTrue(existingProject.getActivities().contains(newActivity));

	}
	
	@Given("^there is an activity with name \"([^\"]*)\" in the project$")
	public void thereIsAnActivityWithNameInTheProject(String activityName) throws Exception {
		newActivity = new Activity(existingProject.getActivityIdCounter(), activityName, 0, planningApp.yearWeekParser("2018-1"), planningApp.yearWeekParser("2018-3"));
		existingProject.addActivity(newActivity);
		
	    assertThat(existingProject.getActivityByName(activityName), is(notNullValue()));
	}

	@When("^the teamleader creates the activity again$")
	public void theTeamleaderCreatesTheActivityAgain() throws Exception {
		try {
			existingProject.addActivity(newActivity);
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}
	
	@When("^teamleader wrongly adds activity named \"([^\"]*)\", estimatedhours (\\d+), startweek \"([^\"]*)\" and endweek \"([^\"]*)\" to the project$")
	public void teamleaderWronglyAddsActivityNamedEstimatedhoursStartweekAndEndweekToTheProject(String name, int estHours, String startWeek, String endWeek) throws Exception {
		newActivity = new Activity(existingProject.getActivityIdCounter(), name, estHours, planningApp.yearWeekParser(startWeek), planningApp.yearWeekParser(endWeek));
	    assertThat(newActivity.getActivityName(),is(equalTo(name)));
		assertThat(newActivity.getEstimatedHours(),is(equalTo(estHours)));
		assertThat(newActivity.getStartWeek(),is(equalTo(planningApp.yearWeekParser(startWeek))));
		assertThat(newActivity.getEndWeek(),is(equalTo(planningApp.yearWeekParser(endWeek))));
		
		try {
			existingProject.addActivity(newActivity);
	    } catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}
  	
  	@Given("^that there is a project with the name \"([^\"]*)\" that starts \"([^\"]*)\"$")
  	public void thatThereIsAProjectWithTheNameThatStarts(String activityName, String startDate) throws Exception {
  		existingProject = new Project(activityName, planningApp.yearWeekParser(startDate));

		planningApp.adminLogin("admin1234");
		planningApp.createProject(existingProject);
		planningApp.adminLogOut();

		assertTrue(planningApp.getProjects().contains(existingProject));
  	}
	

	
	/****************************************************************************************/
  	
  	// Linnea
  	/****************************************************************************************/

	
	@Given("^that there is a project with the name \"([^\"]*)\"$")
	public void thatThereIsAProjectWithTheName(String name) throws Exception {
		existingProject = new Project(name,planningApp.yearWeekParser("2018-01"));

		planningApp.adminLogin("admin1234");
		planningApp.createProject(existingProject);
		planningApp.adminLogOut();

		assertTrue(planningApp.getProjects().contains(existingProject));
	}
	
	
	@Given("^this project has a teamleader with id \"([^\"]*)\"$")
	public void thisProjectHasATeamleaderWithId(String employeeId) throws Exception {
		teamleader = new Employee(employeeId, "Anders Jensen");
		
		planningApp.adminLogin("admin1234");
		planningApp.registerEmployee(teamleader);
		assertTrue(planningApp.getEmployees().contains(teamleader));
		planningApp.adminLogOut();

		existingProject.assignTeamleader(teamleader);
		assertTrue(existingProject.getTeamleader().equals(teamleader));
	}

	
	@Given("^the teamleader is logged in$")
	public void theTeamleaderIsLoggedIn() throws Exception {
		planningApp.userLogin(existingProject.getTeamLeader().getID());
	    assertEquals(planningApp.getcurrentUser(), existingProject.getTeamLeader());   
	}
	
	
	@Then("^the activity is written on the employees list of activities$")
	public void theActivityIsWrittenOnTheEmployeesListOfActivities() throws Exception {
	   assertTrue(availableEmployee.getAssignedActivities().contains(activity));
	}
	
	@Then("^the activity is no longer written on the employees list of activities$")
	public void theActivityIsNoLongerWrittenOnTheEmployeesListOfActivities() throws Exception {
		assertFalse(assignedEmployee.getAssignedActivities().contains(activity));
	}
	
	// Christina 
	/****************************************************************************************/
	
	// teamleader assigns employee to activity
	
	// 1
	@Given("^there is an activity with name \"([^\"]*)\" in the activities list of that project$")
	public void thereIsAnActivityWithNameInTheActivitiesListOfThatProject(String activityName) throws Exception {
		activity = new Activity(existingProject.getActivityIdCounter(), activityName, 0, planningApp.yearWeekParser("2018-1"), planningApp.yearWeekParser("2018-3"));
		existingProject.addActivity(activity);
		assertThat(existingProject.getActivityByName(activityName), is(notNullValue()));

	}

	@Given("^employee with ID \"([^\"]*)\" is available$")
	public void employeeWithIDIsAvailable(String employeeId) throws Exception {
		availableEmployee = new Employee(employeeId, "Carsten Nelson");
		planningApp.adminLogin("admin1234");
		planningApp.registerEmployee(availableEmployee);
		assertTrue(planningApp.getEmployees().contains(availableEmployee));
		planningApp.adminLogOut();
		
	}

	@When("^teamleader assigns employee with ID \"([^\"]*)\" to activity \"([^\"]*)\"$")
	public void teamleaderAssignsEmployeeWithIDToActivity(String employeeID, String activityName) throws Exception {
		activity.assignEmployee(availableEmployee);
		assertTrue(activity.getAssignedEmployees().contains(availableEmployee));
	}
	
	@Then("^the employee is assigned to activity in project with ID$")
	public void theEmployeeIsAssignedToActivityInProjectWithID() throws Exception {
		assertTrue(activity.getAssignedEmployees().contains(availableEmployee));
		
	}
	
	@Then("^the employee with ID \"([^\"]*)\" is still at the list \"([^\"]*)\"$")
	public void theEmployeeWithIDIsStillAtTheList(String employeeID, String arg2) throws Exception {
		assertTrue(planningApp.getEmployees().contains(availableEmployee));
	}
	
	// 2
	@Given("^there is an employee with ID \"([^\"]*)\" assigned to the activity in the project$")
	public void thereIsAnEmployeeWithIDAssignedToTheActivityInTheProject(String employeeId) throws Exception {
		assignedEmployee = new Employee(employeeId, "Carsten Nelson");
		
		planningApp.adminLogin("admin1234");
		planningApp.registerEmployee(assignedEmployee);
		assertTrue(planningApp.getEmployees().contains(assignedEmployee));
		planningApp.adminLogOut();
		
		activity.assignEmployee(assignedEmployee);
		assertThat(activity.getAssignedEmployees().contains(assignedEmployee), is(true));
		
	}

	@When("^teamleader unassigns employee with ID \"([^\"]*)\" from the activity in the project$")
	public void teamleaderUnassignsEmployeeWithIDFromTheActivityInTheProject(String employeeId) throws Exception {
	    assertEquals(assignedEmployee.getID(), employeeId);
	    activity.unassignEmployee(assignedEmployee);
    
	}

	@Then("^the employee is unassigned from activity \"([^\"]*)\" in the project$")
	public void theEmployeeIsUnassignedFromActivityInTheProject(String arg1) throws Exception {
    assertThat(activity.getAssignedEmployees().contains(assignedEmployee), is(false));
	}
	
	// teamleader requests list of available employees
	
	// 1
	@Given("^there is an activity with name \"([^\"]*)\" startdate \"([^\"]*)\" and enddate \"([^\"]*)\" in the activities list$")
	public void thereIsAnActivityWithNameStartdateAndEnddateInTheActivitiesList(String activityName, String startDate, String endDate) throws Exception {
		existingActivity = new Activity(existingProject.getActivityIdCounter(), "Existing Activity", 0, planningApp.yearWeekParser("2018-2"), planningApp.yearWeekParser("2018-5"));
		existingProject.addActivity(existingActivity);
		
		newActivity = new Activity(existingProject.getActivityIdCounter(), activityName, 0, planningApp.yearWeekParser(startDate), planningApp.yearWeekParser(endDate));
		existingProject.addActivity(newActivity);

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
		availableEmployees = planningApp.getAvailableEmployeesInWeek(newActivity.getStartWeek(), newActivity.getEndWeek());
	
	}

	@Then("^Teamleader receives list of availability employees not working in week two, tree and four$")
	public void teamleaderReceivesListOfAvailabilityEmployeesNotWorkingInWeekTwoTreeAndFour() throws Exception {
		assertThat(availableEmployees.contains(employee), is(false));
		assertThat(availableEmployees.contains(employee2), is(true));

	}
	// 2
	@Then("^Teamleader receives list of availability employees not working in week one, and two$")
	public void teamleaderReceivesListOfAvailabilityEmployeesNotWorkingInWeekOneAndTwo() throws Exception {
		assertThat(availableEmployees.contains(employee), is(false));
		assertThat(availableEmployees.contains(employee2), is(true));
		
	}
	// 3
	@Then("^Teamleader receives list of availability employees not working in week four and five$")
	public void teamleaderReceivesListOfAvailabilityEmployeesNotWorkingInWeekFourAndFive() throws Exception {
		assertThat(availableEmployees.contains(employee), is(false));
		assertThat(availableEmployees.contains(employee2), is(true));
	}
	// 4
	@Then("^Teamleader receives list of availability employees not working in week six$")
	public void teamleaderReceivesListOfAvailabilityEmployeesNotWorkingInWeekSix() throws Exception {
		assertThat(availableEmployees.contains(employee), is(true));
		assertThat(availableEmployees.contains(employee2), is(true));

	}
	
	/****************************************************************************************/
	
	
}