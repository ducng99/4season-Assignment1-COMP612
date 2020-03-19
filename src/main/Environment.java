package main;

import objects.Land;
import objects.Sky;
import objects.Snowman;
import objects.Sun;

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
	private static Sun sun;
	private static Snowman snowman;
	
	public static void setWindSpeed(int speed)
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

	public static Land getLand() {
		return land;
	}

	public static void setLand(Land land) {
		Environment.land = land;
	}

	public static Sky getSky() {
		return sky;
	}

	public static void setSky(Sky sky) {
		Environment.sky = sky;
	}

	public static Sun getSun() {
		return sun;
	}

	public static void setSun(Sun sun) {
		Environment.sun = sun;
	}

	public static Snowman getSnowman() {
		return snowman;
	}

	public static void setSnowman(Snowman snowman) {
		Environment.snowman = snowman;
	}
}
