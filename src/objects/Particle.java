package objects;

import java.awt.Dimension;

import com.jogamp.opengl.GL2;

import main.Main;
import main.Vector;

public abstract class Particle {
	protected Vector Position;
	public boolean isDead;

	public Particle(int x, int y)
	{
		this.Position = new Vector(x, y);
		isDead = false;
	}
	
	public Particle(Vector pos)
	{
		this.Position = pos;
		isDead = true;
	}
	
	public void checkAvailability()
	{
		Dimension d = Main.frame.getSize();
		if (Position.y > d.height)
		{
			isDead = true;
		}
	}
	
	public abstract void draw(GL2 gl);
	
	/**
	 * Update particle's position
	 * @param pos
	 */
	public void UpdatePos(Vector pos)
	{
		// Throw error when calling method before overriding
		throw new NullPointerException("method is not overrided");
	}
}
