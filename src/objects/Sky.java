package objects;

import com.jogamp.opengl.GL2;

import main.Environment;
import main.Vector;
import main.Environment.Time;
import shapes.Polygon;

public class Sky extends Particle{

	public Sky() {
		super(0, 0);
	}

	@Override
	public void draw(GL2 gl) {
		if (Environment.getTime() == Time.Day)
		{	
			Vector[] verticies = { new Vector(-1, 1), new Vector(1, 1), new Vector(1, -1), new Vector(-1, -1)};
			double[][] colours = {{0.8, 0.8, 0.8, 1}, {1, 0.9, 1, 1}, {0.8, 0.8, 0.8, 1}, {0.8, 0.8, 0.8, 1}};
			
			Polygon.drawFill(gl, verticies, colours);
		}
		else if (Environment.getTime() == Time.Night)
		{
			Vector[] verticies = { new Vector(-1, 1), new Vector(1, 1), new Vector(1, -1), new Vector(-1, -1)};
			double[][] colours = {{0.8, 0.8, 0.8, 1}, {1, 0.9, 1, 1}, {0.8, 0.8, 0.8, 1}, {0.8, 0.8, 0.8, 1}};
			
			Polygon.drawFill(gl, verticies, colours);
		}
	}
	
}
