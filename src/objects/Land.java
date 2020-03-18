package objects;

import java.awt.Dimension;

import com.jogamp.opengl.GL2;
import shapes.Polygon;
import main.Environment;
import main.Environment.Season;
import main.Environment.Time;
import main.Main;
import main.Utils;
import main.Vector;

public class Land extends Particle {
	public Land()
	{
		super(0, Main.dimension.height - 200);
	}

	@Override
	public void draw(GL2 gl) {
		Dimension d = Main.dimension;
		
		Vector[] verticies;
		
		verticies = new Vector[] {
			Utils.ScreenToWorldLoc(new Vector(0, Position.y + 20)),
			Utils.ScreenToWorldLoc(new Vector(d.getWidth() / 4, Position.y)),
			Utils.ScreenToWorldLoc(new Vector(d.getWidth() / 4 * 3, Position.y)),
			Utils.ScreenToWorldLoc(new Vector(d.getWidth(), Position.y + 30)),
			Utils.ScreenToWorldLoc(new Vector(d.getWidth(), d.getHeight())),
			Utils.ScreenToWorldLoc(new Vector(0, d.getHeight()))
		};
		double[][] colour = null;
		if (Environment.getTime() == Time.Night)
		{		
			if (Environment.getSeason() == Season.Autumn)
				colour = new double[][] {{0, 0.1, 0, 1}, {0, 0.1, 0, 1}, {0, 0.1, 0, 1}, {0, 0.2, 0, 1}, {0, 0.1, 0, 1}, {0, 0.1, 0, 1}};
			else
				colour = new double[][] {{0.3, 0.3, 0.3, 1}, {0.3, 0.3, 0.3, 1}, {0.3, 0.3, 0.3, 1}, {0.4, 0.4, 0.4, 1}, {0.3, 0.3, 0.3, 1}, {0.3, 0.3, 0.3, 1}};
				
		}
		else
		{
			if (Environment.getSeason() == Season.Autumn)
				colour = new double[][] {{0, 0.1, 0, 1}, {0, 0.1, 0, 1}, {0, 0.1, 0, 1}, {0, 0.2, 0, 1}, {0, 0.1, 0, 1}, {0, 0.1, 0, 1}};
			else
				colour = new double[][] {{0.75, 0.75, 0.75, 1}, {0.7, 0.7, 0.7, 1}, {0.7, 0.7, 0.7, 1}, {0.8, 0.8, 0.8, 1}, {0.7, 0.75, 0.75, 1}, {0.75, 0.75, 0.75, 1}};
		}
		Polygon.drawFill(gl, verticies, colour);
	}
}
