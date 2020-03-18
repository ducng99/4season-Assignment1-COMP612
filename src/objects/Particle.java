package objects;

import com.jogamp.opengl.GL2;

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
	
	/**
	 * Method used to define whether the particle {@link #isDead} or not. This is defined differently for each particle, some particles might not need this method, therefore the method is not abstract
	 */
	public void checkAvailability()
	{
		throw new NullPointerException("method not defined");
	}
	
	/**
	 * Draw the particle with provided {@link GL2} variable
	 * @param gl
	 */
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
