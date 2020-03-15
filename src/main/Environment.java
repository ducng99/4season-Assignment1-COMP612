package main;

public class Environment {
	private static int windSpeed = 0;
	
	protected static void setWindSpeed(int speed)
	{
		windSpeed = speed;
	}
	
	public static int getWindSpeed()
	{
		return windSpeed;
	}
}
