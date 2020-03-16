package main;

public class Environment {
	public enum Time
	{
		Day, Night
	}
	
	public enum Season
	{
		Winter, Autumn
	}
	
	private static int windSpeed = 0;
	private static Time time = Time.Day;
	private static Season season = Season.Winter;
	
	protected static void setWindSpeed(int speed)
	{
		windSpeed = speed;
	}
	
	public static int getWindSpeed()
	{
		return windSpeed;
	}

	public static Time getTime() {
		return time;
	}

	public static void setTime(Time time) {
		Environment.time = time;
	}

	public static Season getSeason() {
		return season;
	}

	public static void setSeason(Season season) {
		Environment.season = season;
	}
}
