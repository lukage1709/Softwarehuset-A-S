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
	private int workedHours;

	public Activity(String activityId, String name, int estHours, Calendar startWeek, Calendar endWeek) {
		this.activityId = activityId;
		this.activityName = name;
		this.estimatedHours = estHours;
		this.startWeek = startWeek;
		this.endWeek = endWeek;
		this.workedHours = 0;
	}

	public String getActivityName() {
		return activityName;
	}

	public Calendar getStartWeek() {
		return startWeek;
	}

	public Calendar getEndWeek() {
		return endWeek;
	}

	public void assignEmployee(Employee employee) throws OperationNotAllowedException {
		employee.addToAssignedActivities(this);
		assignedEmployees.add(employee);
		
	}

	public List<Employee> getAssignedEmployees() {
		return Collections.unmodifiableList(assignedEmployees);
	}

	public void unassignEmployee(Employee employee) throws OperationNotAllowedException {
		employee.removeFromAssignedActivities(this);
		assignedEmployees.remove(employee);
		
		
	}
	
	public boolean endWeekIsBeforeStartWeek() {
		return getEndWeek().before(getStartWeek());
	}


	boolean isAvailableInPeriod(Calendar newActivityStart, Calendar newActivityEnd) {
		return ((getStartWeek().before(newActivityStart) && getEndWeek().after(newActivityEnd)) 
				 || (getStartWeek().after(newActivityStart) && getStartWeek().before(newActivityEnd))
				 || (getEndWeek().after(newActivityStart) && getEndWeek().before(newActivityEnd))
				 || (getStartWeek().equals(newActivityStart) && getEndWeek().equals(newActivityEnd)) );
  }

	public int getWorkedHours() {
		return workedHours;
	}

	public void registerWorkedHours(int hours) {
		workedHours += hours;
	}
		
	/**
	 * Removes all employees by making assignedEmployees an empty list
	 */
	public void removeAllEmployees() {
		assignedEmployees = new ArrayList<>();


	}

	public int getEstimatedHours() {
		return estimatedHours;
	}
	
}
