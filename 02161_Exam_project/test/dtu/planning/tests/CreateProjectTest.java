package dtu.planning.tests;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dtu.planning.app.OperationNotAllowedException;
import dtu.planning.app.PlanningApp;
import dtu.planning.app.Project;

public class CreateProjectTest {

	public PlanningApp testInputData(boolean logInFirst, List<Project> projectsToAdd)  throws ParseException,  OperationNotAllowedException
	{
		PlanningApp test = new PlanningApp();
		if (logInFirst)
			test.adminLogin("admin1234");

		for (Project p : projectsToAdd) {
			test.createProject(p);
		}
		return test;
	}

	@Test
	public void testInputDataSetA() throws ParseException, OperationNotAllowedException {
		// Input-data: login==false, 1 project.
		List<Project> projectList = new ArrayList<Project>();
		projectList.add(new Project("TestProjectA", PlanningApp.yearWeekParser("2018-03")));

		PlanningApp pa;
		boolean correctException = false;
		String errorMessage = null;
		
		try {
			pa = testInputData(false, projectList);
		}
		catch (OperationNotAllowedException e) {
			correctException = true;
			errorMessage = e.getMessage();
			
		}

		assertTrue(correctException);
		assertEquals(errorMessage, "Administrator login required");
	}
	
	@Test
	public void testInputDataSetB() throws ParseException,  OperationNotAllowedException {
		// Input-data: login==true, two projects with identical names.
		List<Project> projectList = new ArrayList<Project>();

		projectList.add(new Project("TestProjectB", PlanningApp.yearWeekParser("2018-03")));
		projectList.add(new Project("TestProjectB", PlanningApp.yearWeekParser("2018-01")));

		PlanningApp pa;
		boolean correctException = false;
		String errorMessage = null;
		
		try {
			pa = testInputData(true, projectList);
		}
		catch (OperationNotAllowedException e) {
			correctException = true;
			errorMessage = e.getMessage();
			
		}

		assertTrue(correctException);
		assertEquals(errorMessage, "Name for project is already used");
	}

	@Test
	public void testInputDataSetC() throws ParseException, OperationNotAllowedException {
		// Input-data: login==true, two identical projects.
		List<Project> projectList = new ArrayList<Project>();

		Project p1 = new Project("TestProjectC1", PlanningApp.yearWeekParser("2018-03"));
		Project p2 = new Project("TestProjectC2", PlanningApp.yearWeekParser("2018-01"));
		projectList.add(p1);
		projectList.add(p2);

		PlanningApp pa = new PlanningApp();
		String errorMessage = null;
		
		try {
			pa = testInputData(true, projectList);
		}
		catch (OperationNotAllowedException e) {
			errorMessage = e.getMessage();	
		}

		assertTrue(pa.getProjects().contains(p1));
		assertTrue(pa.getProjects().contains(p2));
		assertNull(errorMessage);
	}
	
	
	@Test(expected = OperationNotAllowedException.class)
	public void testInputDataSetB_FirstTry() throws ParseException,  OperationNotAllowedException {
		PlanningApp test = new PlanningApp();
		test.adminLogin("admin1234");
		assertTrue(test.adminLoggedIn());

		Project p = new Project("TestProjectB", test.yearWeekParser("2018-03"));
		Project p1 = new Project("TestProjectB", test.yearWeekParser("2018-01"));
		assertEquals(p.getName(),p1.getName());
		test.createProject(p1);

		test.createProject(p);

	} 
	
}
