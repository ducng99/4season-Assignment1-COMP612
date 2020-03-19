package objects;

import java.awt.Dimension;
import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import main.Environment;
import main.Main;
import main.Utils;
import main.Vector;
import shapes.Circle;

public class Snow extends Particle {
	public static ArrayList<Snow> snowParticles = new ArrayList<>();
	
	private double radius = 0.0;
	private int fallSpeed;
	private double transparency = Utils.genRand(0.6, 0.85);
	private boolean inSnowman = false;
	
	public Snow(int x, int y) {
		super(x, y);
		this.radius = Utils.genRand(3, 5);
		this.fallSpeed = Utils.genRand(30, 100);
	}

	@Override
	public void draw(GL2 gl) {
		if (!isDead)
		{
			Circle.drawFill(gl, Utils.ScreenToWorldLoc(Position), Utils.ScreenToWorldDist(new Vector(radius, radius)), 20, new double[] {1, 1, 1, transparency}, new double[] {0.9, 0.9, 0.9, transparency});
		
			// Consistent speed for any FPS + snow shake
			UpdatePos(Position.Offset((Environment.getWindSpeed() + Utils.genRand(0, 10)) / Main.getFPS() + Utils.genRand(-0.5, 0.5), fallSpeed / Main.getFPS()));
		}
	}
	
	@Override
	public void UpdatePos(Vector pos)
	{
		// check whether the snow touch the snowman
		if (!inSnowman && !Environment.getSnowman().IsInSnowman(this))
			Position = pos;
		else
			inSnowman = true;
		checkAvailability();
	}
	
	@Override
	public void checkAvailability()
	{
		Dimension d = Main.dimension;
		if (Position.y > d.height + radius || (Environment.getWindSpeed() > 0 ? Position.x > d.width + radius : Position.x < -radius))
		{
			isDead = true;
		}
	}
	
	public static void DrawAllSnow(GL2 gl)
	{
		for (Snow s : snowParticles)
		{
			s.draw(gl);
		}
	}
	
	public double getRadius()
	{
		return radius;
	}
	
	public int getFallSpeed() {
		return fallSpeed;
	}
	
	public static int countAvailableSnow()
	{
		int count = 0;
		for (Snow s : snowParticles)
		{	
			if (!s.isDead && !s.inSnowman)
				count++;
		}
		
		return count;
	}
	
	/**
	 * Generate snow with maximum allowed number of snow. Snow will be reseted if dead and reused when maximum number of snow has been reached.
	 * @param maxNumSnow maximum number of snow allowed
	 * @param noSnowToAdd how many snow should be generated
	 */
	public static void GenerateSnow(int maxNumSnow, int noSnowToAdd)
	{
		int currentNoSnow = Snow.snowParticles.size() - Environment.getSnowman().getNoSnowInSnowman();
		if (currentNoSnow < maxNumSnow)
		{
			// Adding snow particles
			for (int i = 0; i < maxNumSnow - currentNoSnow && i < noSnowToAdd; i++)
			{
				// Compensate for wind speed by padding start point on x-axis of the snow
				Snow s = new Snow(0, 0);
				double fallSpeed = s.getFallSpeed();
				double windWidthCompensate = Main.dimension.height / fallSpeed * Environment.getWindSpeed();
				int startWidthPoint = windWidthCompensate >= 0 ? Utils.genRand((int)-windWidthCompensate, Main.dimension.width) : Utils.genRand(0, (int)(Main.dimension.width - windWidthCompensate));
				s.UpdatePos(new Vector(startWidthPoint, 0));
				Snow.snowParticles.add(s);
			}
		}
		else
		{
			int i = 0;
			for (Snow s : Snow.snowParticles)
			{
				// reusing dead snow particles
				if (s.isDead && i < noSnowToAdd)
				{
					// Compensate for wind speed by padding start point on x-axis of the snow
					int fallSpeed = s.getFallSpeed();
					double windWidthCompensate = Main.dimension.height / (double)fallSpeed * Environment.getWindSpeed();
					int startWidthPoint = windWidthCompensate >= 0 ? Utils.genRand((int)-windWidthCompensate, Main.dimension.width) : Utils.genRand(0, (int)(Main.dimension.width - windWidthCompensate));
					s.UpdatePos(new Vector(startWidthPoint, 0));
					s.isDead = false;
					i++;
				}
			}
		}
	}
}
