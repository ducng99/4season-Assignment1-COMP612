package main;

import objects.Land;
import objects.Sky;

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
	private static Land land;
	private static Sky sky;
	
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

	protected static void setTime(Time time) {
		Environment.time = time;
	}

	public static Season getSeason() {
		return season;
	}

	protected static void setSeason(Season season) {
		Environment.season = season;
	}

	public static Land getLand() {
		return land;
	}

	protected static void setLand(Land land) {
		Environment.land = land;
	}

	public static Sky getSky() {
		return sky;
	}

	protected static void setSky(Sky sky) {
		Environment.sky = sky;
	}
}
