package dtu.planning.acceptance_tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dtu.planning.app.Employee;
import dtu.planning.app.OperationNotAllowedException;
import dtu.planning.app.PlanningApp;

public class LoginLogoutSteps {
	
	private PlanningApp planningApp;
	private String password;
	private EmployeeHelper helper;
	private Employee employee;
	private ErrorMessageHolder errorMessage;
	
	public LoginLogoutSteps(PlanningApp planningApp, EmployeeHelper helper) {
		this.planningApp = planningApp;
		this.helper = helper;
	}
	
	/* Thomas */
	/****************************************************************************************/

	@Given("^that the admin is not already logged in$")
	public void thatTheAdminIsNotAlreadyLoggedIn() throws Exception {
	    assertFalse(planningApp.adminLoggedIn());
	}

	@Given("^the admin logins with password \"([^\"]*)\"$")
	public void theAdminLoginsWithPassword(String password) throws Exception {
	    this.password = password;
	}

	@Then("^the administrator login succeds$")
	public void theAdministratorLoginSucceds() throws Exception {
	    assertTrue(planningApp.adminLogin(password));
	}

	@Then("^the administrator is logged in$")
	public void theAdministratorIsLoggedIn() throws Exception {
	    assertTrue(planningApp.adminLoggedIn());
	}
	
	@Then("^the administrator login fails$")
	public void theAdministratorLoginFails() throws Exception {
		assertFalse(planningApp.adminLogin(password));
	}

	@Then("^the administrator is not logged in$")
	public void theAdministratorIsNotLoggedIn() throws Exception {
		assertFalse(planningApp.adminLoggedIn());
	}
	
	@Given("^that the admin is logged in$")
	public void thatTheAdminIsLoggedIn() throws Exception {
		planningApp.adminLogin("admin1234");
	    assertTrue(planningApp.adminLoggedIn());
	}

	@When("^the admin logs out$")
	public void theAdminLogsOut() throws Exception {
	    planningApp.adminLogOut();
	}

	@Then("^the admin is not logged in$")
	public void theAdminIsNotLoggedIn() throws Exception {
	    assertFalse(planningApp.adminLoggedIn());
	}
	
	/****************************************************************************************/
	
	// Lukas, correct login
	
	@Given("^that a user is not already logged in$")
	public void thatAUserIsNotAlreadyLoggedIn() throws Exception {
	     assertTrue(planningApp.getcurrentUser() == null);

	}
	
	@Given("^\"([^\"]*)\" is a registered user$")
	public void isARegisteredUser(String id) throws Exception {
	    employee = helper.getEmployee();
	    planningApp.adminLogin("admin1234");
	    planningApp.registerEmployee(employee);
	    
	    assertTrue(planningApp.searchEmployeeID(id) != null);
	    
	}
	
	@When("^the user logs in with id \"([^\"]*)\"$")
	public void theUserLogsInWithId(String id) {
		planningApp.userLogin(id);
	}

	@Then("^the user with id \"([^\"]*)\" logs in successfully$")
	public void theUserWithIdLogsInSuccessfully(String id) throws Exception {
	   assertTrue(planningApp.getcurrentUser() == planningApp.searchEmployeeID(id));
	   
	}

	// failed login-attempt due to uknown userID (lukas)


	@Given("^\"([^\"]*)\" is not a registered user\\.$")
	public void isNotARegisteredUser(String id) throws Exception {
	       assertNull(planningApp.searchEmployeeID(id));
	  
	}
	
	

	@Then("^the user is not logged in$")
	public void theUserIsNotLoggedIn() throws Exception {
	    assertTrue(planningApp.getcurrentUser() == null);

	}


	// user logs out (lukas)

	@Given("^that a user is logged in$")
	public void thatAUserIsLoggedIn() throws Exception {
	    employee = helper.getEmployee();
	    planningApp.adminLogin("admin1234");
	    planningApp.registerEmployee(employee);
	    planningApp.userLogin("Anje");
	    assertFalse(planningApp.getcurrentUser() == null);
	}

	@When("^the user logs out$")
	public void theUserLogsOut() throws Exception {
	    planningApp.logOut();
	   
	}
	
}
