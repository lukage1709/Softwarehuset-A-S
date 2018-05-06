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
		out.println("3: Load test data");
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
		} else if (input.equals("3")) {
			loadTestData();
		} else {
			planningUI.setScreen(new WelcomeScreen());
		}
	}
	
	public void loadTestData() {
		planningUI.getPlanningApp().adminLogin("admin1234");
		try {
			Employee employee1 = new Employee("anje", "Anders Jensen"); System.out.println("Added employee \"anje\"");
			Employee employee2 = new Employee("heha", "Henning Hansen"); System.out.println("Added employee \"heha\"");
			planningUI.getPlanningApp().registerEmployee(employee1);
			planningUI.getPlanningApp().registerEmployee(employee2);
			Project project1 = new Project("Test 1", planningUI.getPlanningApp().yearWeekParser("2018-03")); System.out.println("Added project \"Test 1\"");
			Project project2 = new Project("Test 2", planningUI.getPlanningApp().yearWeekParser("2018-04"));System.out.println("Added project \"Test 2\"");
			planningUI.getPlanningApp().createProject(project1);
			planningUI.getPlanningApp().createProject(project2);
			
			project1.assignTeamleader(employee1); System.out.println("Assigned \"anje\" as team leader on project \"Test 1\"");
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (OperationNotAllowedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		planningUI.getPlanningApp().adminLogOut();
		
	}
}
