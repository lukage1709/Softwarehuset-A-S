package dtu.planning.ui;

import java.io.IOException;
import java.io.PrintWriter;

public class AdminLoginScreen extends Screen {

	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println("-- Administrator login --");
		out.println("Enter password (leave blank to cancel):");
	}

	@Override
	public void processInput(String input, PrintWriter out) throws IOException {
		if (inputIsEmpty(input)) {
			planningUI.setScreen(new WelcomeScreen());
		} else if (passwordIsCorrect(input)) {
			out.println("\nLogged in as administrator");
			planningUI.setScreen(new AdminScreen());
		} else {
			out.println("\nWrong password");
			planningUI.setScreen(new AdminLoginScreen());
		}
	}

	public boolean passwordIsCorrect(String input) {
		return planningUI.getPlanningApp().adminLogin(input);
	}

	

}
