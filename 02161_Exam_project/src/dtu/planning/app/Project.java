package dtu.planning.app;

public class Project {
	private String name;
	private String startDate;
	private String endDate;
	
	
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
}
