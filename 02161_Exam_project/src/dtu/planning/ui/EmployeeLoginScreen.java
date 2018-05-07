package dtu.planning.ui;

import java.io.IOException;
import java.io.PrintWriter;

public class EmployeeLoginScreen extends Screen {

	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println("-- Employee login --");
		out.println("Enter employee ID (leave blank to cancel):");
	}

	@Override
	public void processInput(String input, PrintWriter out) throws IOException {
		if (inputIsEmpty(input)) {
			planningUI.setScreen(new WelcomeScreen());
		} else if (employeeDoesNotExist(input)) {
			out.println("\nEmployee does not exist");
			planningUI.setScreen(new EmployeeLoginScreen());
		} else {
			planningUI.getPlanningApp().userLogin(input);
			out.println("\nLogged in");
			planningUI.setScreen(new EmployeeScreen());
		}
	}
}
