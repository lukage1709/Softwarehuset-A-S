package dtu.planning.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Scanner;

import dtu.planning.app.Employee;
import dtu.planning.app.OperationNotAllowedException;
import dtu.planning.app.PlanningApp;
import dtu.planning.app.Project;

public class PlanningUI {
	private PlanningApp planningApp = new PlanningApp();
	private Screen screen;
	private Project projectToManage;
	
	public PlanningUI() {
		setScreen(new WelcomeScreen());
	}
	
	public static void main(String[] args) throws IOException {
		Scanner consoleIn = new Scanner(System.in);
		PrintWriter consoleOut = new PrintWriter(System.out, true);
		
		PlanningUI ui = new PlanningUI();
		ui.menuLoop(consoleIn, consoleOut);
	}
	
	public void menuLoop(Scanner in, PrintWriter out) throws IOException {
		String input;
		do {
			printMenu(out);
			input = in.nextLine();
			processInput(input.trim(), out);
		} while(true);
		
	}
	
	public void processInput(String input, PrintWriter out) throws IOException {
		getScreen().processInput(input, out);
		out.println();
	}

	public void printMenu(PrintWriter out) throws IOException {
		getScreen().printMenu(out);
	}
	
	public void setScreen(Screen screen) {
		this.screen = screen;
		this.screen.setPlanningUI(this);
	}
	
	public Screen getScreen() {
		return screen;
	}
	
	public PlanningApp getPlanningApp() {
		return planningApp;
	}
	
	public void setProjectToManage(Project project) {
		this.projectToManage = project;
	}
	
	public Project getProjectToManage() {
		return projectToManage;
	}
	
}
