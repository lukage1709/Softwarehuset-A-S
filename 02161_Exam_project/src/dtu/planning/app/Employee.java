package dtu.planning.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Employee {

	private String employeeID;
	private String employeeName;
	private List<Activity> assignedActivities = new ArrayList<>();

	public Employee(String employeeID, String employeeName) {
		this.employeeID = employeeID;
		this.employeeName = employeeName;
	}

	public String getID() {
		return employeeID;
	}

	public String getName() {
		return employeeName;
	}
	@Override
	public String toString() {
		return getName() + " " + getID() + " " + hashCode();
	}

	public boolean match(String searchID) {
		return employeeID.equals(searchID);	
	}
	
	public void unassignActivity(Activity activity) {
		assignedActivities.remove(activity);
	}

	public List<Activity> getAssignedActivities() {
		return Collections.unmodifiableList(assignedActivities);
	}
	
	public void addToAssignedActivities(Activity activity) throws OperationNotAllowedException {
		if (!activity.getAssignedEmployees().contains(this)) { // Because of the bidirectional relationship between employee and activity
			activity.assignEmployee(this);
		}
		
		if (assignedActivities.contains(activity)) {
			throw new OperationNotAllowedException("Cannot assign employee twice");
		}
		assignedActivities.add(activity);
	}

	public void removeFromAssignedActivities(Activity activity) throws OperationNotAllowedException {
		if (!assignedActivities.contains(activity)) {
			throw new OperationNotAllowedException("Employee not assigned to this activity!");
		}
		
		assignedActivities.remove(activity);
		
	}

	public boolean isEmployeeAvailable(Calendar newActivityStart, Calendar newActivityEnd) {
		for (Activity activity : assignedActivities) {
			return (!activity.isAvailableInPeriod(newActivityStart, newActivityEnd));
			
		 }
		return true;
	}


}
