package dtu.planning.acceptance_tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dtu.planning.app.PlanningApp;

public class LoginLogoutSteps {
	
	private PlanningApp planningApp;
	private String password;
	
	public LoginLogoutSteps(PlanningApp planningApp) {
		this.planningApp = planningApp;
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
}
