package dtu.planning.tests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.Test;

import dtu.planning.app.Activity;
import dtu.planning.app.Employee;
import dtu.planning.app.OperationNotAllowedException;
import dtu.planning.app.PlanningApp;
import dtu.planning.app.Project;

public class MethodsOnlyUsedInUI {
	private PlanningApp planningApp;

	public MethodsOnlyUsedInUI() {
		this.planningApp = new PlanningApp();
	}

	@Test
	public void testYearWeekFormat() throws ParseException {
		String date = "2018-04";
		Calendar cal = planningApp.yearWeekParser(date);
		assertThat(planningApp.yearWeekFormat(cal), is(equalTo(date)));
	}
	
	@Test
	public void testIsUserTeamleader() throws OperationNotAllowedException, ParseException {
		planningApp.adminLogin("admin1234");
		Employee employee1 = new Employee("anje", "Anders Jensen");
		planningApp.registerEmployee(employee1);
		Project project1 = new Project("Test 1", planningApp.yearWeekParser("2018-03"));
		planningApp.createProject(project1);
		planningApp.adminLogOut();
		planningApp.userLogin("anje");
		assertFalse(planningApp.isUserTeamleader());
		
		planningApp.adminLogin("admin1234");
		project1.assignTeamleader(employee1);
		planningApp.adminLogOut();
		assertTrue(planningApp.isUserTeamleader());
	}
	
	@Test
	public void testGetExistingProjectByProjectNumber() throws OperationNotAllowedException, ParseException {
		assertThat(planningApp.getExistingProjectByProjectNumber("2018-000001"), is(equalTo(null)));
		
		planningApp.adminLogin("admin1234");
		Project project1 = new Project("Test 1", planningApp.yearWeekParser("2018-03"));
		planningApp.createProject(project1);
		planningApp.adminLogOut();
		
		assertThat(planningApp.getExistingProjectByProjectNumber(project1.getProjectNumber()), is(equalTo(project1)));
	}
	
	@Test
	public void testGetActivityById() throws Exception {
		planningApp.adminLogin("admin1234");
		Employee employee1 = new Employee("anje", "Anders Jensen");
		planningApp.registerEmployee(employee1);
		Project project1 = new Project("Test 1", planningApp.yearWeekParser("2018-03"));
		planningApp.createProject(project1);
		project1.assignTeamleader(employee1);
		planningApp.adminLogOut();
		
		planningApp.userLogin("anje");
		Activity activity = new Activity(project1.getActivityIdCounter(), "Activity 1", 0, planningApp.yearWeekParser("2018-03"), planningApp.yearWeekParser("2018-05"));
		project1.addActivity(activity);
		
		
		assertThat(project1.getActivityById(activity.getActivityId()), is(equalTo(activity)));
	}
	
}
