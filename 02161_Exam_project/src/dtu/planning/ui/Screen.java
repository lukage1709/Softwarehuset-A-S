package dtu.planning.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import dtu.planning.app.Activity;
import dtu.planning.app.Employee;

public abstract class Screen {
	PlanningUI planningUI;
	
	abstract public void printMenu(PrintWriter out) throws IOException;
	
	abstract public void processInput(String input, PrintWriter out) throws IOException;
	
	public void setPlanningUI(PlanningUI ui) {
		this.planningUI = ui;
	}
	
	public boolean inputIsEmpty(String input) {
		return input == null || input.equals("");
	}
	
	public boolean userAcceptsOperation(PrintWriter out) {
		Scanner console = new Scanner(System.in);
		String inp = "";
		do {
			out.println("\nAre you sure? (y/n):");
			inp = console.nextLine();
			inp = inp.trim().toLowerCase();
		} while (!inp.equals("y") && !inp.equals("n"));

		if (inp.equals("n")) {
			return false;
		} else if (inp.equals("y")) {
			return true;
		}
		return false;
	}

	public boolean projectDoesNotExist(String input) {
		return planningUI.getPlanningApp().getExistingProjectByProjectNumber(input) == null;
	}

	public void printEmployeeList(PrintWriter out, List<Employee> employees) {
		for (Employee employee : employees) {
			out.println(employee.getID() + "\t" + employee.getName());
		}
	}

	public boolean employeeDoesNotExist(String input) {
		return planningUI.getPlanningApp().searchEmployeeID(input) == null;
	}

	public String promptForDate(PrintWriter out, Scanner console) {
		String yearWeek;
		yearWeek = console.next();
		while (!yearWeek.matches("\\d{4}-\\d{1,2}")) {
			out.println("\nWrong date format. Use YYYY-WW:");
			yearWeek = console.next();
		}
		return yearWeek;
	}

	public String promptForEmployeeID(PrintWriter out) {
		String employeeID = "";
		Scanner console = new Scanner(System.in);
		do {
			out.println("\nEnter employee ID (leave blank to cancel):");
			employeeID = console.nextLine();
			if (employeeID.isEmpty()) {
				break;
			}
		} while (employeeDoesNotExist(employeeID));
		return employeeID;
	}

	public void printActivityList(PrintWriter out, List<Activity> activities) {
		for (Activity activity : activities) {
			out.println(activity.getActivityId() + "\t" + activity.getActivityName());
		}
	}

	public boolean activityDoesNotExist(String input) {
		return planningUI.getProjectToManage().getActivityById(input) == null;
	}
}
