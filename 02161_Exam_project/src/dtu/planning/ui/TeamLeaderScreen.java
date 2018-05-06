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

		for (Project project : projects) {
			if (project.getTeamleader() != null && project.getTeamleader().equals(planningUI.getPlanningApp().getcurrentUser())) {
				out.println(project.getProjectNumber() + "\t" + project.getName());
			}
		}
		out.println("\nEnter project ID (leave blank to cancel):");

	}

	@Override
	public void processInput(String input, PrintWriter out) throws IOException {
		if (inputIsEmpty(input)) {
			planningUI.setScreen(new EmployeeScreen());
		} else if (planningUI.getPlanningApp().getExistingProjectByProjectNumber(input) == null) {
			out.println("\nProject with ID \"" + input + "\" does not exists");
			planningUI.setScreen(new TeamLeaderChooseProjectScreen());
		} else if (planningUI.getPlanningApp().getExistingProjectByProjectNumber(input).getTeamleader() != planningUI.getPlanningApp().getcurrentUser()) {
			out.println("\nYou are not team leader on project \"" + input + "\"");
			planningUI.setScreen(new TeamLeaderChooseProjectScreen());
		} else {
			planningUI.setProjectToManage(planningUI.getPlanningApp().getExistingProjectByProjectNumber(input));
			out.println("\nYou have chosen project \"" + input + "\"");
			planningUI.setScreen(new TeamLeaderScreen());
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
			int estimatedHours = 0;
			String startWeek;
			String endWeek;

			Scanner console = new Scanner(System.in);
			// get estimated hours
			out.println("Enter estimated hours (integer larger than 0):");
			while (!console.hasNextInt()) {
				out.println("\nWrong input. Enter estimated hours (integer larger than 0):");
				console.next();
			}
			estimatedHours = console.nextInt();
			if (estimatedHours <= 0) {
				out.println("\nEstimated hours must be larger than 0");
				planningUI.setScreen(new TeamLeaderAddActivityScreen());
			} else {
				// get start date/week
				out.println("Enter activity start date (YYYY-WW):");
				startWeek = console.next();
				while (!startWeek.matches("\\d{4}-\\d{1,2}")) {
					out.println("\nWrong date format. Use YYYY-WW:");
					startWeek = console.next();
				}

				// get end date/week
				out.println("Enter activity end date (YYYY-WW):");
				endWeek = console.next();
				while (!endWeek.matches("\\d{4}-\\d{1,2}")) {
					out.println("\nWrong date format. Use YYYY-WW:");
					endWeek = console.next();
				}

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
}

class TeamLeaderAssignEmployeeScreen extends Screen {
	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println("-- Assign employee to activity --");
		out.println("Choose activity");

		List<Activity> activities = planningUI.getProjectToManage().getActivities();

		for (Activity activity : activities) {
			out.println(activity.getActivityId() + "\t" + activity.getActivityName());
		}
		out.println("\nEnter activity ID (leave blank to cancel):");

	}

	@Override
	public void processInput(String input, PrintWriter out) throws IOException {	
		if (inputIsEmpty(input)) {
			planningUI.setScreen(new TeamLeaderScreen());
		} else if (planningUI.getProjectToManage().getActivityById(input) == null) {
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
					for (Employee employee : availableEmployees) {
						out.println(employee.getID() + "\t" + employee.getName());
					}

					Scanner console = new Scanner(System.in);
					String employeeID = "";
					do {
						out.println("\nEnter employee ID (leave blank to cancel):");
						employeeID = console.nextLine();
						if (employeeID.isEmpty()) {
							break;
						}
					} while (planningUI.getPlanningApp().searchEmployeeID(employeeID) == null);

					if (employeeID.isEmpty()) {
						planningUI.setScreen(new TeamLeaderScreen());
					} else {
						if (!availableEmployees.contains(planningUI.getPlanningApp().searchEmployeeID(employeeID))) {
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
}

class TeamLeaderUnassignEmployeeScreen extends Screen {
	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println("-- Unassign employee from activity --");
		out.println("Choose activity");

		List<Activity> activities = planningUI.getProjectToManage().getActivities();

		for (Activity activity : activities) {
			out.println(activity.getActivityId() + "\t" + activity.getActivityName());
		}
		out.println("\nEnter activity ID (leave blank to cancel):");

	}

	@Override
	public void processInput(String input, PrintWriter out) throws IOException {	
		if (inputIsEmpty(input)) {
			planningUI.setScreen(new TeamLeaderScreen());
		} else if (planningUI.getProjectToManage().getActivityById(input) == null) {
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
					for (Employee employee : assignedEmployees) {
						out.println(employee.getID() + "\t" + employee.getName());
					}

					Scanner console = new Scanner(System.in);
					String employeeID = "";
					do {
						out.println("\nEnter employee ID (leave blank to cancel):");
						employeeID = console.nextLine();
						if (employeeID.isEmpty()) {
							break;
						}
					} while (planningUI.getPlanningApp().searchEmployeeID(employeeID) == null);

					if (employeeID.isEmpty()) {
						planningUI.setScreen(new TeamLeaderScreen());
					} else {
						if (!assignedEmployees.contains(planningUI.getPlanningApp().searchEmployeeID(employeeID))) {
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
}
