package dtu.planning.app;

public class Project {
	private String name;
	private String startDate;
	private String endDate;
	private Employee teamLeader;
	private Employee noTeamLeader;
	
	
	// TODO: Løbenummer til projekter!
	// TODO: Fix datoer!
	public Project(String name, String startDate, String endDate) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * @return name of project.
	 */
	public String getName() {
		return name;
	}

	public Object getStartDate() {
		return startDate;
	}

	public Object getEndDate() {
		return endDate;
	}
	
	public void assignTeamLeader(Employee e ) {
		this.teamLeader = e;
		
	}
	public Employee getTeamLeader() {
		return this.teamLeader;
	}
	public boolean getProjectByID(String projectID) {
		return name.contains(projectID);
	}
	
	public void unassignTeamLeader(Employee e) {
		this.teamLeader = null;
	}
	
}
