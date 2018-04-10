package dtu.planning.app;

import java.util.ArrayList;
import java.util.List;

public class PlanningApp {
	
	private boolean adminLoggedIn = false;
	private List<Project> currentProjects = new ArrayList<>();

	public boolean adminLoggedIn() {
		return adminLoggedIn;
	}

	public boolean adminLogin(String password) {
		adminLoggedIn = password.equals("admin1234");
		return adminLoggedIn;
	}

	public void adminLogOut() {
		adminLoggedIn = false;
		
	}

	
	/**
	 * Creates a project provided the administrator is logged in
	 * 
	 * @param project name, start- and enddates
	 * 
	 */
	public void createProject(String projectName, String startDate, String endDate) {
		if (adminLoggedIn()) {
			Project project = new Project(projectName, startDate, endDate);
				currentProjects.add(project);
			
		}
		
	}
	
	/**
	 * @return the list of project currently registered in the system.
	 */
	public List<Project> getProjects() {
		return currentProjects;
	}

}
