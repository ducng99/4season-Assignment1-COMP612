package main;

import java.awt.Dimension;
import java.util.Random;

/**
 * Custom utilities created for ease of access
 * @author Duc Nguyen
 *
 */
public class Utils {
	private static Random rand = new Random();
	
	/**
	 * Convert from screen pixels (starts from top-left corner) to world location (-1 -> 1)
	 * @param loc : a {@link Vector} contains screen pixels location
	 * @return
	 */
	public static Vector ScreenToWorld(Vector loc)
	{
		Dimension d = Main.frame.getSize();
		Vector worldP = new Vector();
		
		if (loc.x < d.width / 2)
		{
			worldP.x = (1 - (loc.x / (d.width / 2))) * -1;
		}
		else if (loc.x > d.width / 2)
		{
			worldP.x = (loc.x / (d.width / 2)) - 1;
		}
		else
		{
			worldP.x = 0;
		}

		if (loc.y < d.height / 2)
		{
			worldP.y = 1 - (loc.y / (d.height / 2));
		}
		else if (loc.y > d.height / 2)
		{
			worldP.y = ((loc.y / (d.height / 2)) * -1) + 1;
		}
		else
		{
			worldP.y = 0;
		}
		
		return worldP;
	}
	
	public static int genRand(int min, int max)
	{
		return rand.nextInt(max - min + 1) + min;
	}
}
