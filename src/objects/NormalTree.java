package objects;

import com.jogamp.opengl.GL2;

import main.Environment;
import main.Environment.Season;
import main.Utils;
import main.Vector;
import shapes.Circle;
import shapes.Polygon;

public class NormalTree extends Tree {
	private Vector[] foliageLoc = new Vector[2];
	private Vector[] foliageRad = new Vector[2];
	
	public NormalTree(int x, int y, int height) {
		super(x, y, height);
		foliageLoc[0] = Utils.ScreenToWorldLoc(Position.Offset(-13, -(height / 4 * 3) + 10 + Utils.genRand(-10, 10)));
		foliageRad[0] = Utils.ScreenToWorldDist(new Vector(height / 2.0 + Utils.genRand(-10, 5), -10 + height / 2.0 + Utils.genRand(-10, 8)));
		foliageLoc[1] = Utils.ScreenToWorldLoc(Position.Offset(13, -(height / 4 * 3) + 10 + Utils.genRand(-10, 10)));
		foliageRad[1] = Utils.ScreenToWorldDist(new Vector(height / 2.0 + Utils.genRand(-10, 5), -10 + height / 2.0 + Utils.genRand(-10, 8)));
	}

	@Override
	public void draw(GL2 gl)
	{
		Vector[] verticies;
		
		//Shadow
		Circle.drawFill(gl, Utils.ScreenToWorldLoc(Position), Utils.ScreenToWorldDist(new Vector(15 + height / 8.0, 5 + height / 8.0)), 30, new double[] {0, 0, 0, 0.2});
		
		// Body
		verticies = new Vector[]
		{
			Utils.ScreenToWorldLoc(Position.Offset(-2 - height / 20.0, 0)),
			Utils.ScreenToWorldLoc(Position.Offset(2 + height / 20.0, 0)),
			Utils.ScreenToWorldLoc(Position.Offset(8 + height / 20.0, -(height / 4 * 3))),
			Utils.ScreenToWorldLoc(Position.Offset(0, -(height / 3))),
			Utils.ScreenToWorldLoc(Position.Offset(-8, -(height / 4 * 3)))
		};
		
		Polygon.drawFill(gl, verticies, new double[] {0.2, 0.1, 0, 1});
		
		if (Environment.getSeason() == Season.Autumn)
		{
			// Foliage
			Circle.drawFill(gl, foliageLoc[0], foliageRad[0], 30, new double[] {0.3137, 0.482, 0, 1});
			Circle.drawLines(gl, foliageLoc[0], foliageRad[0], 30, new double[] {0, 0, 0, 0.3});

			Circle.drawFill(gl, foliageLoc[1], foliageRad[1], 30, new double[] {0.3137, 0.482, 0, 1});
			Circle.drawLines(gl, foliageLoc[1], foliageRad[1], 30, new double[] {0, 0, 0, 0.3});
		}
	}
}
