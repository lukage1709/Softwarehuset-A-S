package dtu.planning.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import dtu.planning.app.Employee;
import dtu.planning.app.Project;

public class AdminScreen extends Screen {

	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println("-- Administrator Menu --");
		out.println("0: Log out");
		out.println("1: Add employee");
		out.println("2: Remove employee");
		out.println("3: Add project");
		out.println("4: Remove project");
		out.println("5: Assign team leader");
		out.println("6: Unassign team leader");
		out.println("\nSelect option: ");
	}

	@Override
	public void processInput(String input, PrintWriter out) throws IOException {
		if (input.equals("0")) {
			planningUI.getPlanningApp().adminLogOut();
			out.println("\nLogged out");
			planningUI.setScreen(new WelcomeScreen());
		} else if (input.equals("1")) {
			planningUI.setScreen(new AdminRegisterEmployeeScreen());
		} else if (input.equals("2")) {
			planningUI.setScreen(new AdminRemoveEmployeeScreen());
		} else if (input.equals("3")) {
			planningUI.setScreen(new AdminAddProjectScreen());
		} else if (input.equals("4")) {
			planningUI.setScreen(new AdminRemoveProjectScreen());
		} else if (input.equals("5")) {
			planningUI.setScreen(new AdminAssignTeamleaderScreen());
		} else if (input.equals("6")) {
			planningUI.setScreen(new AdminUnassignTeamleaderScreen());
		} else {
			planningUI.setScreen(new AdminScreen());
		}
	}

}

class AdminRegisterEmployeeScreen extends Screen {
	private String employeeID;
	private String employeeName;

	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println("-- Add Employee --");
		out.println("Enter ID (max. 4 characters) (leave blank to cancel):");
	}

	@Override
	public void processInput(String input, PrintWriter out) throws IOException {
		if (inputIsEmpty(input)) {
			planningUI.setScreen(new AdminScreen());
		} else if (idIsTooLong(input)) {
			out.println("\nID to long. Use max. 4 characters. Try again.");
			planningUI.setScreen(new AdminRegisterEmployeeScreen());
		} else {
			employeeID = input;
			employeeName = promptForEmployeeName(out);

			Employee employee = new Employee(employeeID, employeeName);
			try {
				planningUI.getPlanningApp().registerEmployee(employee);
				out.println("\nEmployee \"" + employeeName + "\" (ID " + employeeID + ") added");
				planningUI.setScreen(new AdminScreen());
			}  catch (Exception e) {
				out.println("\n" + e.getMessage());
				planningUI.setScreen(new AdminRegisterEmployeeScreen());
			}
			
		}
	}

	public boolean idIsTooLong(String input) {
		return input.length() > 4;
	}

	public String promptForEmployeeName(PrintWriter out) {
		Scanner console = new Scanner(System.in);
		String employeeName = "";
		do {
			out.println("Enter name:");
			employeeName = console.nextLine();
		} while (employeeName.equals(""));
		
		return employeeName;
	}
}

class AdminRemoveEmployeeScreen extends Screen {	
	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println("-- Remove Employee --");
		out.println("Choose employe");

		List<Employee> employees = planningUI.getPlanningApp().getEmployees();
		printEmployeeList(out, employees);
		
		out.println("\nEnter employee ID (leave blank to cancel):");

	}

	@Override
	public void processInput(String input, PrintWriter out) throws IOException {
		if (inputIsEmpty(input)) {
			planningUI.setScreen(new AdminScreen());
		} else if (employeeDoesNotExist(input)) {
			System.out.println("\nEmployee with ID \"" + input + "\" does not exists");
			planningUI.setScreen(new AdminRemoveEmployeeScreen());
		} else {
			if (userAcceptsOperation(out)) {
				Employee employee = planningUI.getPlanningApp().searchEmployeeID(input);
				try {
					planningUI.getPlanningApp().removeEmployee(employee);
					System.out.println("\nEmployee with ID \"" + input + "\" is removed");
					planningUI.setScreen(new AdminScreen());
				} catch (Exception e) {
					System.out.println("\n" + e.getMessage());
					planningUI.setScreen(new AdminRemoveEmployeeScreen());
				}
			} else {
				planningUI.setScreen(new AdminRemoveEmployeeScreen());
			}
		}
	}
}

class AdminAddProjectScreen extends Screen {
	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println("-- Add Project --");
		out.println("Enter project name (leave blank to cancel):");
	}

	@Override
	public void processInput(String input, PrintWriter out) throws IOException {
		if (inputIsEmpty(input)) {
			planningUI.setScreen(new AdminScreen());
		} else {
			String projectName = input;
			Scanner console = new Scanner(System.in);
			out.println("Enter project start date (YYYY-WW):");
			String projectDate = promptForDate(out, console);

			Project project;
			try {
				project = new Project(projectName, planningUI.getPlanningApp().yearWeekParser(projectDate));
				try {
					planningUI.getPlanningApp().createProject(project);
					out.println("\nProject \"" + projectName + "\" (starts " + projectDate + ") is added");
					planningUI.setScreen(new AdminScreen());
				}  catch (Exception e) {
					out.println("\n" + e.getMessage());
					planningUI.setScreen(new AdminAddProjectScreen());
				}
			} catch (ParseException e1) {
				out.println("\n" + e1.getMessage());
				planningUI.setScreen(new AdminAddProjectScreen());
			}
		}
	}
}

class AdminRemoveProjectScreen extends Screen {	
	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println("-- Remove Project --");
		out.println("Choose project");

		List<Project> projects = planningUI.getPlanningApp().getProjects();
		printProjectList(out, projects);
		out.println("\nEnter project ID (leave blank to cancel):");
	}

	@Override
	public void processInput(String input, PrintWriter out) throws IOException {
		if (inputIsEmpty(input)) {
			planningUI.setScreen(new AdminScreen());
		} else if (projectDoesNotExist(input)) {
			out.println("\nProject with ID \"" + input + "\" does not exists");
			planningUI.setScreen(new AdminRemoveProjectScreen());
		} else {
			if (userAcceptsOperation(out)) {
				Project project = planningUI.getPlanningApp().getExistingProjectByProjectNumber(input);
				try {
					planningUI.getPlanningApp().removeProject(project);
					out.println("\nProject with ID \"" + input + "\" is removed");
					planningUI.setScreen(new AdminScreen());
				} catch (Exception e) {
					out.println("\n" + e.getMessage());
					planningUI.setScreen(new AdminRemoveProjectScreen());
				}
			} else {
				planningUI.setScreen(new AdminRemoveProjectScreen());
			}
		}
	}

	public void printProjectList(PrintWriter out, List<Project> projects) {
		for (Project project : projects) {
			out.println(project.getProjectNumber() + "\t" + project.getName());
		}
	}
}

class AdminAssignTeamleaderScreen extends Screen {
	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println("-- Assign Team Leader --");
		out.println("Choose project");

		List<Project> projects = planningUI.getPlanningApp().getProjects();
		printProjectListWithTeamLeader(out, projects);
		out.println("\nEnter project ID (leave blank to cancel):");
	}

	@Override
	public void processInput(String input, PrintWriter out) throws IOException {
		if (inputIsEmpty(input)) {
			planningUI.setScreen(new AdminScreen());
		} else if (projectDoesNotExist(input)) {
			out.println("\nProject with ID \"" + input + "\" does not exists");
			planningUI.setScreen(new AdminAssignTeamleaderScreen());
		} else {
			out.println("\nChoose employee:");

			List<Employee> employees = planningUI.getPlanningApp().getEmployees();
			printEmployeeList(out, employees);
			
			String employeeID = promptForEmployeeID(out);
			
			planningUI.getPlanningApp().getExistingProjectByProjectNumber(input).assignTeamleader(planningUI.getPlanningApp().searchEmployeeID(employeeID));
			out.println("\nEmployee \"" + employeeID + " is now team leader on project \"" + input + "\"");
			planningUI.setScreen(new AdminScreen());
		}
	}

	public void printProjectListWithTeamLeader(PrintWriter out, List<Project> projects) {
		for (Project project : projects) {
			if (project.getTeamleader() == null) {
				out.println(project.getProjectNumber() + "\t" + project.getName());
			} else {
				out.println(project.getProjectNumber() + "\t" + project.getName() + "\t(Team leader: " + project.getTeamleader().getID() + ")");
			}
		}
	}
}


class AdminUnassignTeamleaderScreen extends Screen {
	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println("-- Unassign Team Leader --");
		out.println("Choose project");

		List<Project> projects = planningUI.getPlanningApp().getProjects();

		printProjectsWithATeamLeader(out, projects);
		out.println("\nEnter project ID (leave blank to cancel):");
	}

	@Override
	public void processInput(String input, PrintWriter out) throws IOException {
		if (inputIsEmpty(input)) {
			planningUI.setScreen(new AdminScreen());
		} else if (projectDoesNotExist(input)) {
			out.println("\nProject with ID \"" + input + "\" does not exists");
			planningUI.setScreen(new AdminUnassignTeamleaderScreen());
		} else if (projectDoesNotHaveATeamLeader(input)) {
			out.println("\nProject with ID \"" + input + "\" does not have a team leader");
			planningUI.setScreen(new AdminUnassignTeamleaderScreen());
		} else {
			planningUI.getPlanningApp().getExistingProjectByProjectNumber(input).unassignTeamleader();
			out.println("\nUnassigned team leader for project with ID \"" + input + "\"");
			planningUI.setScreen(new AdminScreen());
		}
	}

	public boolean projectDoesNotHaveATeamLeader(String input) {
		return planningUI.getPlanningApp().getExistingProjectByProjectNumber(input).getTeamleader() == null;
	}
	
	public void printProjectsWithATeamLeader(PrintWriter out, List<Project> projects) {
		for (Project project : projects) {
			if (project.getTeamleader() != null) {
				out.println(project.getProjectNumber() + "\t" + project.getName() + "\t(Team leader: " + project.getTeamleader().getID() + ")");
			}
		}
	}
}

