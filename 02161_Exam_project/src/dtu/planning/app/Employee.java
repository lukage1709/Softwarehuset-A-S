package dtu.planning.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Employee {

	private String employeeID;
	private String employeeName;
	private List<Activity> assignedActivities = new ArrayList<>();

	public Employee(String employeeID, String employeeName) {
		this.employeeID = employeeID;
		this.employeeName = employeeName;
	}
	
	public Employee(String employeeID) {
		this.employeeID = employeeID;
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

	public boolean isEmployeeAvailable(Calendar startweek, Calendar endweek) {
		for (Activity activity : assignedActivities) {
			
			System.out.println("\nopr.aktivitet: \n" + activity.getStartWeek().getTime() + "\n" + activity.getEndWeek().getTime());
			
			Calendar newActivityStart = startweek;
			Calendar newActivityEnd = endweek;
			System.out.println("ny aktivitet:\n" + newActivityStart.getTime() + "\n" + newActivityEnd.getTime());
			
			return (activity.isAvailableInPeriod(newActivityStart, newActivityEnd));
			
		 }
		return false;
	}


}
