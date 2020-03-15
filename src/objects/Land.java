package objects;

import java.awt.Dimension;

import com.jogamp.opengl.GL2;
import shapes.Polygon;

import main.Main;
import main.Utils;
import main.Vector;

public class Land extends Particle {
	public Land()
	{
		super(0, 0);
	}

	@Override
	public void draw(GL2 gl) {
		Vector tmp = new Vector();
		Dimension d = Main.frame.getSize();
		d.height = d.height + 100;
		
		Vector[] verticies;
		
		verticies = new Vector[] {
			Utils.ScreenToWorldLoc(new Vector(0, d.getHeight() / 2)),
			Utils.ScreenToWorldLoc(new Vector(d.getWidth() / 4, d.getHeight() / 2 - 20)),
			Utils.ScreenToWorldLoc(new Vector(d.getWidth() / 2, d.getHeight() / 2 + 20)),
			Utils.ScreenToWorldLoc(new Vector(d.getWidth() / 4 * 3, d.getHeight() / 2 - 10)),
			Utils.ScreenToWorldLoc(new Vector(d.getWidth(), d.getHeight() / 2 + 10)),
			Utils.ScreenToWorldLoc(new Vector(d.getWidth(), d.getHeight())),
			Utils.ScreenToWorldLoc(new Vector(0, d.getHeight()))
		};
		
		double[][] colour= {
			{0.1, 0, 0, 1}, {0.1, 0, 0, 1}, {0.1, 0, 0, 1}, {0.1, 0, 0, 1}, {0.2, 0.02, 0.02, 1}, {0.1, 0, 0, 1}, {0.1, 0, 0, 1}
		};
		
		Polygon.drawFill(gl, verticies, colour);
	}
}
