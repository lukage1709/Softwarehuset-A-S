package dtu.planning.app;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import dtu.planning.app.PlanningApp;

public class Project {
	private String name;
	private Calendar startDate;
	private String projectNumber;
	private Employee teamLeader;
	private List<Activity> activities = new ArrayList<>();
	private int idCounter = 1; 
	private int activityIdCounter = 1;
	
	
	public Project(String name, int startYear, int startMonth, int startDay) {
		this.name = name;
		this.startDate = new GregorianCalendar(startYear, startMonth, startDay);
		this.projectNumber = generateProjectNumber(startYear);

	}
	


	/**
	 * @return name of project.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return startDate
	 * Til refactoring: Denne metode kan måske erstatte de tre næste
	 */
	public Calendar getStartDate() {
		return startDate;
	}
	
	/**
	 * @return start year of project.
	 */
	public int getStartYear() {
		return startDate.get(Calendar.YEAR);
	}
	
	/**
	 * @return start month of project.
	 */
	public int getStartMonth() {
		return startDate.get(Calendar.MONTH);
	}
	
	/**
	 * @return name of project.
	 */
	public int getStartDay() {
		return startDate.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * @return project number.
	 */	
	public String getProjectNumber() {
		String id = projectNumber;
		return id;
	}
	
	/**
	 * Calculates a project number
	 * @return projectnumber
	 */
	private String generateProjectNumber(int startYear) {
		String id = calculateId();
		String number = startYear + "-" + id;
		return number;
	}
	
	/**
	 * @return a new sequence number with exactly 6 digits.
	 * It is assumed that the number of projects will not be greater than 999999
	 */
	private String calculateId() {
		String idNumber = String.format("%06d", idCounter); 
		idCounter++;
		
		return idNumber;
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
		if (activityNameAlreadyExistsInProject(activity)) {
			throw new Exception("Activity name already used in this project");
			
		}
		if (activity.endWeekIsBeforeStartWeek()) {
			throw new Exception("The activity cannot end before it starts");
		}
		activities.add(activity);
		
	}



	public boolean activityNameAlreadyExistsInProject(Activity activity) {
		return getActivityByName(activity.getActivityName()) != null;
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

	public String getActivityIdCounter() {
		String idNumber = String.format("%04d", activityIdCounter); 
		idCounter++;
		
		return idNumber;
	}


	/**
	 * Removes all activities by making activities an empty list
	 */
	public void removeAllActivities() {
		activities = new ArrayList<>();		
	}

	
}
