package objects;

import java.awt.Dimension;

import com.jogamp.opengl.GL2;

import main.Main;
import main.Vector;

/**
 * Particle abstract class, contains position and draw function and availability of the particle
 * @author Duc Nguyen
 *
 */
public abstract class Particle {
	protected Vector Position;
	public boolean isDead;

	public Particle(int x, int y)
	{
		this.Position = new Vector(x, y);
		isDead = false;
	}
	
	public Vector getPosition()
	{
		return Position;
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
