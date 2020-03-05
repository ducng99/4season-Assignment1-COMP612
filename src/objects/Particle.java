package objects;

import com.jogamp.opengl.GL2;

import main.Vector;

public abstract class Particle {
	protected Vector Position;

	public Particle(int x, int y)
	{
		this.Position = new Vector(x, y);
	}
	
	public Particle(Vector pos)
	{
		this.Position = pos;
	}
	
	public abstract void draw(GL2 gl);
}
