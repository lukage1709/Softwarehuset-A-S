package dtu.planning.acceptance_tests;

import java.util.List;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import dtu.planning.app.PlanningApp;
import dtu.planning.app.Project;

public class AdminSteps {
	private PlanningApp planningApp;
	private Project project; 

	public AdminSteps(PlanningApp planningApp) {
		this.planningApp = planningApp;
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

}