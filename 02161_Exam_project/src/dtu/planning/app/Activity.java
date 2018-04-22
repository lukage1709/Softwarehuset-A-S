package dtu.planning.app;

import java.util.ArrayList;
import java.util.List;

public class Activity {

	private String activityName;
	private int estimatedHours;
	private String startWeek;
	private String endWeek;
	private List<Employee> assignedEmployees = new ArrayList<>();

	public Activity(String name, int estHours, String startWeek, String endWeek) {
		this.activityName = name;
		this.estimatedHours = estHours;
		this.startWeek = startWeek;
		this.endWeek = endWeek;
		// skal evt tilføjes i konstruktøren??
		//this.assignedEmployees = assignedEmployees;
	}

	public String getActivityName() {
		return activityName;
	}

	public int getEstimatedHours() {
		return estimatedHours;
	}

	public String getStartWeek() {
		return startWeek;
	}

	public String getEndWeek() {
		return endWeek;
	}

	public void assignEmployee(Employee employee) {
		assignedEmployees.add(employee);
	}

	public List<Employee> getAssignedEmployees() {
		return assignedEmployees;
	}

	public void unassignEmployee(Employee employee) {
		assignedEmployees.remove(employee);
		
	}
	
}
