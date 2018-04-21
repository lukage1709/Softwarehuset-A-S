package dtu.planning.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Project {
	private String name;
	private String startDate;
	private String endDate;
	private Employee teamLeader;
	private Employee noTeamLeader;
	private List<Activity> activities = new ArrayList<>();
	
	
	// TODO: Løbenummer til projekter!
	// TODO: Fix datoer!
	public Project(String name, String startDate, String endDate) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * @return name of project.
	 */
	public String getName() {
		return name;
	}

	public Object getStartDate() {
		return startDate;
	}

	public Object getEndDate() {
		return endDate;
	}
	
	public void assignTeamLeader(Employee e ) {
		this.teamLeader = e;
		
	}
	public Employee getTeamLeader() {
		return this.teamLeader;
	}
	public boolean getProjectByID(String projectID) {
		return name.contains(projectID);
	}
	
	public void unassignTeamLeader(Employee e) {
		this.teamLeader = null;
	}

	public void addActivity(Activity activity) throws Exception {
		if (getActivityByName(activity.getActivityName()) != null) {
			throw new Exception("Activity name already used in this project");
			
		}
		activities.add(activity);
		
	}

	public List<Activity> getActivities() {
		return Collections.unmodifiableList(activities);
	}

	public Activity getActivityByName(String activityName) {
		for (Activity act : activities) {
			if (act.getActivityName().equals(activityName)) {
				return act;
			}
		}
		return null;
	}
	
}
