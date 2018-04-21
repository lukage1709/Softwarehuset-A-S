package dtu.planning.app;

public class Activity {

	private String activityName;
	private int estimatedHours;
	private String startWeek;
	private String endWeek;

	public Activity(String name, int estHours, String startWeek, String endWeek) {
		this.activityName = name;
		this.estimatedHours = estHours;
		this.startWeek = startWeek;
		this.endWeek = endWeek;
	}

	public String getActivityName() {
		return activityName;
	}

	public int getEstimatedHours() {
		return estimatedHours;
	}

	public String getStartWeek() {
		return startWeek;
	}

	public String getEndWeek() {
		return endWeek;
	}
	
}
