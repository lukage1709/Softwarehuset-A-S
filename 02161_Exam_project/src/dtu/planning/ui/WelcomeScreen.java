package dtu.planning.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;

import dtu.planning.app.Employee;
import dtu.planning.app.OperationNotAllowedException;
import dtu.planning.app.Project;

public class WelcomeScreen extends Screen {

	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println("-- Planning App --");
		out.println("0: Exit");
		out.println("1: Employee login");
		out.println("2: Administrator login");
		out.println("\nSelect option: ");
	}
	
	@Override
	public void processInput(String input, PrintWriter out) throws IOException {
		if (input.equals("0")) {
			out.println("\nGoodbye!");
			System.exit(0);
		} else if (input.equals("1")) {
			planningUI.setScreen(new EmployeeLoginScreen());
		} else if (input.equals("2")) {
			planningUI.setScreen(new AdminLoginScreen());
		} else {
			planningUI.setScreen(new WelcomeScreen());
		}
	}
}
