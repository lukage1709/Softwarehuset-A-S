package dtu.planning.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import dtu.planning.app.Activity;
import dtu.planning.app.Project;

public class EmployeeScreen extends Screen {

	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println("-- Main Menu --");
		out.println("0: Log out");
		out.println("1: Register time");
		if (planningUI.getPlanningApp().isUserTeamleader()) {
			out.println("2: Manage projects (for team leaders)");
		}
		out.println("\nSelect option: ");
	}

	@Override
	public void processInput(String input, PrintWriter out) throws IOException {
		if (input.equals("0")) {
			planningUI.getPlanningApp().logOut();
			out.println("Logged out");
			planningUI.setScreen(new WelcomeScreen());
		} else if (input.equals("1")) {
			planningUI.setScreen(new EmployeeRegisterTimeScreen());
		} else if (input.equals("2")) {
			if (planningUI.getPlanningApp().isUserTeamleader()) {
				planningUI.setScreen(new TeamLeaderChooseProjectScreen());
			} else {
				out.println("\nOnly allowed for team leaders");
				planningUI.setScreen(new EmployeeScreen());
			}
		} else {
			planningUI.setScreen(new EmployeeScreen());
		}
	}

}

class EmployeeRegisterTimeScreen extends Screen {

	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println("-- Register Time --");
		out.println("Choose project");
		printProjectsWithAssignedActivities(out);
		out.println("\nEnter project ID (leave blank to cancel):");
	}

	@Override
	public void processInput(String input, PrintWriter out) throws IOException {
		if (inputIsEmpty(input)) {
			planningUI.setScreen(new EmployeeScreen());
		} else if (projectDoesNotExist(input)) {
			out.println("\nProject with ID \"" + input + "\" does not exists");
			planningUI.setScreen(new EmployeeRegisterTimeScreen());
		} else {
			Project selectedProject = planningUI.getPlanningApp().getExistingProjectByProjectNumber(input);
			out.println("Choose activity");
			printAssginedActivities(out, selectedProject);
			
			String activityId = prompForActivityID(out, selectedProject);
			if (activityId.isEmpty()) {
				planningUI.setScreen(new EmployeeScreen());
			} else {
				Activity selectedActivity = selectedProject.getActivityById(activityId);
				int hours = promptForHours(out);
				selectedActivity.registerWorkedHours(hours);
				out.println("\nYou have registered " + hours + " hours on activity \"" + selectedActivity.getActivityName() + "\". There is now registered a total of " + selectedActivity.getWorkedHours() + " hours.");
				planningUI.setScreen(new EmployeeScreen());
			}

		}
	}
	
	public void printProjectsWithAssignedActivities(PrintWriter out) {
		for (Project p: planningUI.getPlanningApp().getProjects()) {
			for (Activity a : p.getActivities()) {
				if (!a.getAssignedEmployees().isEmpty() && a.getAssignedEmployees().contains(planningUI.getPlanningApp().getcurrentUser())) {
					out.println(p.getProjectNumber() + "\t" + p.getName());
				}
			}
		}
	}
	
	public int promptForHours(PrintWriter out) {
		int hours = 0;
		Scanner console = new Scanner(System.in);
		out.println("\nEnter worked hours you want to add (or negative integer to subtract):");
		while (!console.hasNextInt()) {
			out.println("\nWrong input. Enter worked hours you want to add (or negative integer to subtract):");
			console.next();
		}
		hours = console.nextInt();
		return hours;
	}
	
	private String prompForActivityID(PrintWriter out, Project selectedProject) {
		String activityId = "";
		Scanner console = new Scanner(System.in);
		do {
			out.println("\nEnter activity ID (leave blank to cancel):");
			activityId = console.nextLine();
			if (activityId.isEmpty()) {
				break;
			}
		} while (selectedProject.getActivityById(activityId) == null);
		
		return activityId;
	}

	public void printAssginedActivities(PrintWriter out, Project selectedProject) {
		for (Activity a : selectedProject.getActivities()) {
			if (!a.getAssignedEmployees().isEmpty() && a.getAssignedEmployees().contains(planningUI.getPlanningApp().getcurrentUser())) {
				out.println(a.getActivityId() + "\t" + a.getActivityName() + "\t(Reg: " + a.getWorkedHours() + ", Est: " + a.getEstimatedHours() + ")");
			}
		}
	}

}
