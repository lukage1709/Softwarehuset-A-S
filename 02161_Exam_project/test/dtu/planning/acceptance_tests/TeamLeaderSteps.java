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
	private Project existingProject;
	private Employee employee, employee2, availableEmployee, assignedEmployee, teamleader;
	private ErrorMessageHolder errorMessage;
	public EmployeeHelper helper;
	private List<Employee> employeeID;
	private Activity newActivity, existingActivity;
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

	/* @Given("^there is a project with ID \"([^\"]*)\"$") // TO DO: DELETE THIS, PROJECTID USED WRONG!!!
	public void thereIsAProjectWithID(String projectID) throws Exception {
		// create dummy project
		planningApp.adminLogin("admin1234");
		project = new Project(projectID, planningApp.yearWeekParser("2018-5"));
		planningApp.createProject(project);
		project = planningApp.searchProjectByID(projectID);
		assertThat(project,is(notNullValue()));
		
		planningApp.adminLogOut();
	} */

  	@Given("^the employee is team leader on that project$")
	public void theEmployeeIsTeamLeaderOnThatProject() throws Exception {
		employee = helper.getEmployee();
	    
	    planningApp.userLogin(employee.getID());
	    employeeLoggedIn = planningApp.getcurrentUser();
	    assertTrue(employeeLoggedIn.equals(employee));
  		
		// and assign team leader
		planningApp.adminLogin("admin1234");
		System.out.println(employeeLoggedIn.getID());
		System.out.println(project.getProjectNumber());
		project.assignTeamleader(employeeLoggedIn);
		planningApp.adminLogOut();
		
		assertTrue(project.getTeamLeader().equals(employeeLoggedIn));
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
		assertTrue(project.getActivities().contains(activity));

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
		project.assignTeamleader(employee2);
	planningApp.adminLogOut();
		
	// assertFalse(project.getTeamLeader().equals(mockEmployeeLoggedIn));
	}
	

	
	/****************************************************************************************/
  	
  	// Linnea
  	/****************************************************************************************/
	@Given("^there is a project with ID \"([^\"]*)\"$")
	public void thereIsAProjectWithID(String projectId) throws Exception {
		project = new Project("Project with employees",planningApp.yearWeekParser("2018-02"));

		planningApp.adminLogin("admin1234");
		planningApp.createProject(project);
		planningApp.adminLogOut();

		assertEquals(project.getProjectNumber(),projectId);
	} 
	
	@Given("^a project with id \"([^\"]*)\" exists$")
	public void aProjectWithIdExists(String projectId) throws Exception {
		existingProject = new Project("projectToDelete",planningApp.yearWeekParser("2018-02"));

		planningApp.adminLogin("admin1234");
		planningApp.createProject(existingProject);
		planningApp.adminLogOut();

		assertEquals(existingProject.getProjectNumber(),projectId);
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
		
	    // assertTrue(planningApp.getEmployees().contains(planningApp.searchEmployeeID(employeeId))); WHY DOES THIS NOT WORK?
	}

	@When("^teamleader assigns employee with ID \"([^\"]*)\" to activity \"([^\"]*)\"$")
	public void teamleaderAssignsEmployeeWithIDToActivity(String employeeID, String activityName) throws Exception {
		/* employee = planningApp.searchEmployeeID(employeeID);
	    activity.assignEmployee(employee);
	    */
		activity.assignEmployee(availableEmployee);
		assertTrue(activity.getAssignedEmployees().contains(availableEmployee));
	}

	@Then("^the employee is assigned to activity in project with ID$")
	public void theEmployeeIsAssignedToActivityInProjectWithID() throws Exception {
		assertTrue(activity.getAssignedEmployees().contains(availableEmployee));
		
	}
	
	@Then("^the employee with ID \"([^\"]*)\" is still at the list \"([^\"]*)\"$")
	public void theEmployeeWithIDIsStillAtTheList(String employeeID, String arg2) throws Exception {
	    // assertTrue(planningApp.getEmployees().contains(planningApp.searchEmployeeID(employeeID)));
		assertTrue(planningApp.getEmployees().contains(availableEmployee));
	}
	
	// 2
	@Given("^there is an employee with ID \"([^\"]*)\" assigned to the activity in the project$")
	public void thereIsAnEmployeeWithIDAssignedToTheActivityInTheProject(String employeeId) throws Exception {
		// employee = planningApp.searchEmployeeID(employeeId);
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