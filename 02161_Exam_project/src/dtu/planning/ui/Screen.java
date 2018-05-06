package dtu.planning.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

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
}
