package dtu.planning.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Activity {

	private String activityId;
	private String activityName;
	private int estimatedHours;
	private Calendar startWeek;
	private Calendar endWeek;
	private List<Employee> assignedEmployees = new ArrayList<>();

	public Activity(String activityId, String name, int estHours, Calendar startWeek, Calendar endWeek) {
		this.activityId = activityId;
		this.activityName = name;
		this.estimatedHours = estHours;
		this.startWeek = startWeek;
		this.endWeek = endWeek;
		
	}

	public String getActivityName() {
		return activityName;
	}

	public int getEstimatedHours() {
		return estimatedHours;
	}

	public Calendar getStartWeek() {
		return startWeek;
	}

	public Calendar getEndWeek() {
		return endWeek;
	}

	public void assignEmployee(Employee employee) {
		assignedEmployees.add(employee);
	}

	public List<Employee> getAssignedEmployees() {
		return Collections.unmodifiableList(assignedEmployees);
	}

	public void unassignEmployee(Employee employee) {
		assignedEmployees.remove(employee);
		
	}

	public String getActivityId() {
		return activityId;
	}
	
	public boolean endWeekIsBeforeStartWeek() {
		return getEndWeek().before(getStartWeek());
	}

	boolean isAvailableInPeriod(Calendar newActivityStart, Calendar newActivityEnd) {
		return (!((getStartWeek().before(newActivityStart) && getEndWeek().after(newActivityEnd)) 
				 || (getStartWeek().after(newActivityStart) && getStartWeek().before(newActivityEnd))
				 || (getEndWeek().after(newActivityStart) && getEndWeek().before(newActivityEnd))
				 || (getStartWeek().equals(newActivityStart) && getEndWeek().equals(newActivityEnd)) ));
		
	}
	
}
