package objects;

import com.jogamp.opengl.GL2;

import main.Utils;
import main.Vector;
import shapes.Circle;
import shapes.Polygon;

public class NormalTree extends Tree {
	public NormalTree(int x, int y, int height) {
		super(x, y, height);
	}

	@Override
	public void draw(GL2 gl)
	{
		Vector[] verticies;
		
		//Shadow
		Circle.drawFill(gl, Utils.ScreenToWorldLoc(Position), Utils.ScreenToWorldDist(new Vector(15 + height / 8.0, 5 + height / 8.0)), 30, new double[] {0, 0, 0, 0.2});
		
		verticies = new Vector[]
		{
			Utils.ScreenToWorldLoc(Position.Offset(-2 - height / 20.0, 0)),
			Utils.ScreenToWorldLoc(Position.Offset(2 + height / 20.0, 0)),
			Utils.ScreenToWorldLoc(Position.Offset(8 + height / 20.0, -(height / 4 * 3))),
			Utils.ScreenToWorldLoc(Position.Offset(0, -(height / 3))),
			Utils.ScreenToWorldLoc(Position.Offset(-8, -(height / 4 * 3)))
		};
		
		Polygon.drawFill(gl, verticies, new double[] {0 , 0.2, 0, 1});
		
		Circle.drawFill(gl, Utils.ScreenToWorldLoc(Position.Offset(0, -(height / 4 * 3) + 10)), Utils.ScreenToWorldDist(new Vector(height / 2.0, -10 + height / 2.0)), 30, new double[] {0, 0.3, 0, 1});
	}
}
