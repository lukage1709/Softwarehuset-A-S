package dtu.planning.tests;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dtu.planning.app.OperationNotAllowedException;
import dtu.planning.app.PlanningApp;
import dtu.planning.app.Project;

import org.junit.Test;

import dtu.planning.app.Employee;
import dtu.planning.app.OperationNotAllowedException;
import dtu.planning.app.PlanningApp;
import dtu.planning.app.Project;
import dtu.planning.app.Activity;


public class AddActivityTest {
	PlanningApp planningApp = new PlanningApp();
	Activity activity1;
	Activity activity2;
	Project newProject;
	Employee employee;
	

	@Test (expected = OperationNotAllowedException.class)
	public void testInputDataSetA() throws  ParseException, OperationNotAllowedException {
		planningApp.adminLogin("admin1234");
		newProject = new Project("TestProjectA", PlanningApp.yearWeekParser("2018-03"));
		Employee employee = new Employee("employeeID", "employeeName");
			
		
		
		Activity activity1 = new Activity(newProject.getActivityIdCounter(), "name", 0, PlanningApp.yearWeekParser("2018-3"), PlanningApp.yearWeekParser("2018-3"));
		Activity activity2 = new Activity(newProject.getActivityIdCounter(), "name", 0, PlanningApp.yearWeekParser("2018-3"), PlanningApp.yearWeekParser("2018-4"));
		
		newProject.addActivity(activity2);
		newProject.addActivity(activity1);
		
		
		
	}
	
	
	@Test (expected = OperationNotAllowedException.class)
	public void testInputDataSetB() throws  ParseException, OperationNotAllowedException {
		newProject = new Project("TestProjectA", PlanningApp.yearWeekParser("2018-03"));
		
		Activity activity1 = new Activity(newProject.getActivityIdCounter(), "name1", 0, planningApp.yearWeekParser("2018-3"), planningApp.yearWeekParser("2018-1"));
		Activity activity2 = new Activity(newProject.getActivityIdCounter(), "name2", 0, planningApp.yearWeekParser("2018-3"), planningApp.yearWeekParser("2018-4"));
		newProject.addActivity(activity2);
		newProject.addActivity(activity1);
		
		
	}
	
	@Test (expected = OperationNotAllowedException.class)
	public void testInputDataSetC() throws  ParseException, OperationNotAllowedException {
		newProject = new Project("TestProjectA", PlanningApp.yearWeekParser("2018-15"));
		
		Activity activity1 = new Activity(newProject.getActivityIdCounter(), "name1", 0, planningApp.yearWeekParser("2018-10"), planningApp.yearWeekParser("2018-20"));
		Activity activity2 = new Activity(newProject.getActivityIdCounter(), "name2", 0, planningApp.yearWeekParser("2018-15"), planningApp.yearWeekParser("2018-20"));
		newProject.addActivity(activity2);
		newProject.addActivity(activity1);
		
		
	
}
	
	@Test 
	public void testInputDataSetD() throws  ParseException, OperationNotAllowedException {
		newProject = new Project("TestProjectA", PlanningApp.yearWeekParser("2018-1"));
		
		Activity activity1 = new Activity(newProject.getActivityIdCounter(), "name1", 0, planningApp.yearWeekParser("2018-1"), planningApp.yearWeekParser("2018-3"));
		Activity activity2 = new Activity(newProject.getActivityIdCounter(), "name2", 0, planningApp.yearWeekParser("2018-2"), planningApp.yearWeekParser("2018-3"));
		newProject.addActivity(activity1);
		newProject.addActivity(activity2);
		
		assertTrue(newProject.getActivities().contains(activity1));
		
	
}
}
