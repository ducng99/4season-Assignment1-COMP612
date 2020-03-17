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
	private static int numSnowAvailable = 0;
	
	private double radius = 0.0;
	private double fallSpeed;
	private double transparency = Utils.genRand(50, 85) / 100.0;
	
	public Snow(int x, int y, double radius, double fallSpeed) {
		super(x, y);
		this.radius = radius;
		this.fallSpeed = fallSpeed;
	}

	@Override
	public void draw(GL2 gl) {
		if (!isDead)
		{
			Circle.drawFill(gl, Utils.ScreenToWorldLoc(Position), Utils.ScreenToWorldDist(new Vector(radius, radius)), 20, new double[] {1, 1, 1, transparency});
		
			// Consistent speed for any FPS + snow shake
			UpdatePos(Position.Offset((Environment.getWindSpeed() + Utils.genRand(0, 10)) / Main.getFPS() + Utils.genRand(-0.5, 0.5), fallSpeed / Main.getFPS()));
		}
	}
	
	@Override
	public void UpdatePos(Vector pos)
	{
		Position = pos;
		checkAvailability();
	}
	
	public static void DrawAllSnow(GL2 gl)
	{
		for (Snow s : snowParticles)
		{
			s.draw(gl);
		}
	}
	
	public double getFallSpeed() {
		return fallSpeed;
	}
	
	public static int getAvailableSnow()
	{
		numSnowAvailable = 0;
		Snow.snowParticles.forEach(snow -> {
			if (!snow.isDead)
				numSnowAvailable++;
		});
		
		return numSnowAvailable;
	}
}
