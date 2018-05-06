package dtu.planning.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import dtu.planning.app.Activity;
import dtu.planning.app.Employee;
import dtu.planning.app.Project;

public class TeamLeaderScreen extends Screen {

	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println("-- Team Leader Menu --");
		if (planningUI.getProjectToManage() != null) {
			out.println("Managing project: \"" + planningUI.getProjectToManage().getName() + "\"");
		}
		out.println("0: Back to main menu");
		out.println("1: Add activity");
		out.println("2: Assign employee to activity");
		out.println("3: Unassign employee from activity");
		out.println("\nSelect option: ");
	}

	@Override
	public void processInput(String input, PrintWriter out) throws IOException {
		if (input.equals("0")) {
			planningUI.setScreen(new EmployeeScreen());
		} else if (input.equals("1")) {
			planningUI.setScreen(new TeamLeaderAddActivityScreen());
		} else if (input.equals("2")) {
			planningUI.setScreen(new TeamLeaderAssignEmployeeScreen());
		} else if (input.equals("3")) {
			planningUI.setScreen(new TeamLeaderUnassignEmployeeScreen());
		} else {
			planningUI.setScreen(new TeamLeaderScreen());
		}
	}
}

class TeamLeaderChooseProjectScreen extends Screen {
	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println("-- Manage project --");
		out.println("Choose project that you want to manage");

		List<Project> projects = planningUI.getPlanningApp().getProjects();
		printProjectListWithCurrentUserAsTeamLeader(out, projects);
		out.println("\nEnter project ID (leave blank to cancel):");
	}

	@Override
	public void processInput(String input, PrintWriter out) throws IOException {
		if (inputIsEmpty(input)) {
			planningUI.setScreen(new EmployeeScreen());
		} else if (projectDoesNotExist(input)) {
			out.println("\nProject with ID \"" + input + "\" does not exists");
			planningUI.setScreen(new TeamLeaderChooseProjectScreen());
		} else if (currentUserIsNotTeamLeaderOnProject(input)) {
			out.println("\nYou are not team leader on project \"" + input + "\"");
			planningUI.setScreen(new TeamLeaderChooseProjectScreen());
		} else {
			planningUI.setProjectToManage(planningUI.getPlanningApp().getExistingProjectByProjectNumber(input));
			out.println("\nYou have chosen project \"" + input + "\"");
			planningUI.setScreen(new TeamLeaderScreen());
		}
	}

	public boolean currentUserIsNotTeamLeaderOnProject(String input) {
		return planningUI.getPlanningApp().getExistingProjectByProjectNumber(input).getTeamleader() != planningUI.getPlanningApp().getcurrentUser();
	}
	
	public void printProjectListWithCurrentUserAsTeamLeader(PrintWriter out, List<Project> projects) {
		for (Project project : projects) {
			if (project.getTeamleader() != null && project.getTeamleader().equals(planningUI.getPlanningApp().getcurrentUser())) {
				out.println(project.getProjectNumber() + "\t" + project.getName());
			}
		}
	}
}

class TeamLeaderAddActivityScreen extends Screen {
	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println("-- Add Activity --");
		out.println("Project \"" + planningUI.getProjectToManage().getName() + "\" starts " + planningUI.getPlanningApp().yearWeekFormat(planningUI.getProjectToManage().getStartDate()));
		out.println("Enter activity name (leave blank to cancel):");
	}

	@Override
	public void processInput(String input, PrintWriter out) throws IOException {	
		if (inputIsEmpty(input)) {
			planningUI.setScreen(new TeamLeaderScreen());
		} else {
			String activityName = input;
			Scanner console = new Scanner(System.in);
			// get estimated hours
			int estimatedHours = promptForEstimatedHours(out, console);
			if (estimatedHours <= 0) {
				out.println("\nEstimated hours must be larger than 0");
				planningUI.setScreen(new TeamLeaderAddActivityScreen());
			} else {
				// get start date/week
				out.println("Enter activity start date (YYYY-WW):");
				String startWeek = promptForDate(out, console);

				// get end date/week
				out.println("Enter activity end date (YYYY-WW):");
				String endWeek = promptForDate(out, console);
				try {
					Activity activity = new Activity(planningUI.getProjectToManage().getActivityIdCounter(), activityName, estimatedHours, planningUI.getPlanningApp().yearWeekParser(startWeek), planningUI.getPlanningApp().yearWeekParser(endWeek));
					planningUI.getProjectToManage().addActivity(activity);
					out.println("\nActivity \"" + activity.getActivityName() + "\" is added to project \"" + planningUI.getProjectToManage().getName() + "\"");
					planningUI.setScreen(new TeamLeaderScreen());
				} catch (ParseException e) {
					out.println("\n" + e.getMessage());
					planningUI.setScreen(new TeamLeaderAddActivityScreen());
				} catch (Exception e) {
					out.println("\n" + e.getMessage());
					planningUI.setScreen(new TeamLeaderAddActivityScreen());
				}
			}
		}
	}

	public int promptForEstimatedHours(PrintWriter out, Scanner console) {
		int estimatedHours = 0;
		out.println("Enter estimated hours (integer larger than 0):");
		while (!console.hasNextInt()) {
			out.println("\nWrong input. Enter estimated hours (integer larger than 0):");
			console.next();
		}
		estimatedHours = console.nextInt();
		return estimatedHours;
	}
}

class TeamLeaderAssignEmployeeScreen extends Screen {
	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println("-- Assign employee to activity --");
		out.println("Choose activity");
		List<Activity> activities = planningUI.getProjectToManage().getActivities();
		printActivityList(out, activities);
		out.println("\nEnter activity ID (leave blank to cancel):");

	}

	@Override
	public void processInput(String input, PrintWriter out) throws IOException {	
		if (inputIsEmpty(input)) {
			planningUI.setScreen(new TeamLeaderScreen());
		} else if (activityDoesNotExist(input)) {
			out.println("\nActivity with ID \"" + input + "\" does not exists");
			planningUI.setScreen(new TeamLeaderAssignEmployeeScreen());
		} else {
			Activity activity = planningUI.getProjectToManage().getActivityById(input);
			try {
				out.println("\nChoose available employee:");
				List<Employee> availableEmployees = planningUI.getPlanningApp().getAvailableEmployeesInWeek(activity.getStartWeek(), activity.getEndWeek());

				if (availableEmployees.isEmpty()) {
					out.println("\nThere are no available employees");
					planningUI.setScreen(new TeamLeaderScreen());
				} else {
					printEmployeeList(out, availableEmployees);
					String employeeID = promptForEmployeeID(out);
					if (inputIsEmpty(employeeID)) {
						planningUI.setScreen(new TeamLeaderScreen());
					} else {
						if (employeeIsNotAvailable(availableEmployees, employeeID)) {
							out.println("\nEmployee with ID \"" + employeeID + "\" is not available");
							planningUI.setScreen(new TeamLeaderAssignEmployeeScreen());
						} else {
							activity.assignEmployee(planningUI.getPlanningApp().searchEmployeeID(employeeID));
							out.println("\nEmployee \"" + planningUI.getPlanningApp().searchEmployeeID(employeeID).getName() + "\" is assigned to activity \"" + activity.getActivityName() + "\"");
							planningUI.setScreen(new TeamLeaderScreen());
						}
					}
				}
			} catch (Exception e) {
				out.println("\n" + e.getMessage());
				planningUI.setScreen(new TeamLeaderAssignEmployeeScreen());
			}
		}
	}

	public boolean employeeIsNotAvailable(List<Employee> availableEmployees, String employeeID) {
		return !availableEmployees.contains(planningUI.getPlanningApp().searchEmployeeID(employeeID));
	}
}

class TeamLeaderUnassignEmployeeScreen extends Screen {
	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println("-- Unassign employee from activity --");
		out.println("Choose activity");
		List<Activity> activities = planningUI.getProjectToManage().getActivities();
		printActivityList(out, activities);
		out.println("\nEnter activity ID (leave blank to cancel):");
	}

	@Override
	public void processInput(String input, PrintWriter out) throws IOException {	
		if (inputIsEmpty(input)) {
			planningUI.setScreen(new TeamLeaderScreen());
		} else if (activityDoesNotExist(input)) {
			out.println("\nActivity with ID \"" + input + "\" does not exists");
			planningUI.setScreen(new TeamLeaderUnassignEmployeeScreen());
		} else {
			Activity activity = planningUI.getProjectToManage().getActivityById(input);
			try {
				out.println("\nChoose assigned employee:");
				List<Employee> assignedEmployees = planningUI.getProjectToManage().getActivityById(input).getAssignedEmployees();
				if (assignedEmployees.isEmpty()) {
					out.println("\nThere are no assigned employees");
					planningUI.setScreen(new TeamLeaderScreen());
				} else {
					printEmployeeList(out, assignedEmployees);
					String employeeID = promptForEmployeeID(out);
					if (inputIsEmpty(employeeID)) {
						planningUI.setScreen(new TeamLeaderScreen());
					} else {
						if (employeeIsNotAssignedToActivity(assignedEmployees, employeeID)) {
							out.println("\nEmployee with ID \"" + employeeID + "\" is not assigned to this activity");
							planningUI.setScreen(new TeamLeaderUnassignEmployeeScreen());
						} else {
							activity.unassignEmployee(planningUI.getPlanningApp().searchEmployeeID(employeeID));
							out.println("\nEmployee \"" + planningUI.getPlanningApp().searchEmployeeID(employeeID).getName() + "\" is unassigned from activity \"" + activity.getActivityName() + "\"");
							planningUI.setScreen(new TeamLeaderScreen());
						}
					}
				}
			} catch (Exception e) {
				out.println("\n" + e.getMessage());
				planningUI.setScreen(new TeamLeaderUnassignEmployeeScreen());
			}
		}
	}

	public boolean employeeIsNotAssignedToActivity(List<Employee> assignedEmployees, String employeeID) {
		return !assignedEmployees.contains(planningUI.getPlanningApp().searchEmployeeID(employeeID));
	}
}
