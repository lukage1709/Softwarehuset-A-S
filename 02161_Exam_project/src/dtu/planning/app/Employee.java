package dtu.planning.app;

public class Employee {

	private String employeeID;
	private String employeeName;

	public Employee(String employeeID) {
		this.employeeID = employeeID;
	}

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

	public boolean match(String searchID) {
		return employeeID.contains(searchID);
	}

	public void setName(String employeeName2) {
		this.employeeName = employeeName2;		
	}

}
