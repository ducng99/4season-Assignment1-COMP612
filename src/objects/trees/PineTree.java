package objects.trees;

import com.jogamp.opengl.GL2;

import main.Environment;
import main.Environment.Season;
import main.Utils;
import main.Vector;
import shapes.Circle;
import shapes.Polygon;
import shapes.Triangle;

public class PineTree extends Tree {

	public PineTree(int x, int y, int height) {
		super(x, y, height);
	}

	@Override
	public void draw(GL2 gl) {
		Vector[] verticies;
		
		//Shadow
		Circle.drawFill(gl, Utils.ScreenToWorldLoc(Position), Utils.ScreenToWorldDist(new Vector(20 + height / 10.0, 10 + height / 10.0)), 30, new double[] {0, 0, 0, 0.2});
		
		//base
		verticies = new Vector[] {
			Utils.ScreenToWorldLoc(Position.Offset(-5 - height / 20.0, -(height / 4.0))),
			Utils.ScreenToWorldLoc(Position.Offset(5 + height / 20.0, -(height / 4.0))),
			Utils.ScreenToWorldLoc(Position.Offset(5 + height / 20.0, 0)),
			Utils.ScreenToWorldLoc(Position.Offset(-5 - height / 20.0, 0))
		};
		
		Polygon.drawFill(gl, verticies, new double[] {0.05, 0.3, 0.1, 1});

		gl.glBegin(GL2.GL_TRIANGLES);

		//Bottom
		verticies = new Vector[] {
			Utils.ScreenToWorldLoc(Position.Offset(0, -(height / 2.0))),
			Utils.ScreenToWorldLoc(Position.Offset(-25 - height / 20.0, -(height / 4.0))),
			Utils.ScreenToWorldLoc(Position.Offset(25 + height / 20.0, -(height / 4.0)))
		};
		
		Triangle.drawFill(gl, verticies, new double[] {0, 0.4, 0, 1});
		
		//Middle
		verticies = new Vector[] {
			Utils.ScreenToWorldLoc(Position.Offset(0, -(height / 4.0 * 3))),
			Utils.ScreenToWorldLoc(Position.Offset(-20 - height / 20.0, -(height / 2.0) + 5)),
			Utils.ScreenToWorldLoc(Position.Offset(20 + height / 20.0, -(height / 2.0) + 5))
		};
		
		Triangle.drawFill(gl, verticies, new double[] {0, 0.4, 0, 1});
		
		//Top
		verticies = new Vector[] {
			Utils.ScreenToWorldLoc(Position.Offset(0, -height)),
			Utils.ScreenToWorldLoc(Position.Offset(-15 - height / 20.0, -(height / 4.0 * 3) + 5)),
			Utils.ScreenToWorldLoc(Position.Offset(15 + height / 20.0, -(height / 4.0 * 3) + 5))
		};
		
		if (Environment.getSeason() == Season.Winter)
			Triangle.drawFill(gl, verticies, new double[][] {{0.99, 0.99, 0.99, 1}, {0, 0.4, 0, 1}, {0, 0.4, 0, 1}});
		else
			Triangle.drawFill(gl, verticies, new double[][] {{0, 0.4, 0, 1}, {0, 0.4, 0, 1}, {0, 0.4, 0, 1}});

		gl.glEnd();
	}
	
}
