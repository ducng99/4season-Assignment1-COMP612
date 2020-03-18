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
	private double transparency = Utils.genRand(0.5, 0.85);
	
	public Snow(int x, int y) {
		super(x, y);
		this.radius = Utils.genRand(3, 5);
		this.fallSpeed = Utils.genRand(20, 70);
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
	
	@Override
	public void checkAvailability()
	{
		Dimension d = Main.frame.getSize();
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
	
	public int getFallSpeed() {
		return fallSpeed;
	}
	
	public static int countAvailableSnow()
	{
		int count = 0;
		for (Snow s : snowParticles)
		{	
			if (!s.isDead)
				count++;
		}
		
		return count;
	}
}
