package objects.trees;

import com.jogamp.opengl.GL2;

import main.Environment;
import main.Environment.Season;
import main.Utils;
import main.Vector;
import shapes.Circle;
import shapes.Polygon;

public class NormalTree extends Tree {
	private Vector foliageLoc;
	private Vector foliageRad;
	
	public NormalTree(int x, int y, int height) {
		super(x, y, height);
		foliageLoc = Utils.ScreenToWorldLoc(Position.Offset(0, -(height / 4 * 3) + 10 + Utils.genRand(-10, 10)));
		foliageRad = Utils.ScreenToWorldDist(new Vector(height / 2.0 + Utils.genRand(-10, 5), -10 + height / 2.0 + Utils.genRand(-10, 8)));
	}

	@Override
	public void draw(GL2 gl)
	{
		Vector[] verticies;
		
		//Shadow
		Circle.drawFill(gl, Utils.ScreenToWorldLoc(Position), Utils.ScreenToWorldDist(new Vector(15 + height / 10.0, 5 + height / 10.0)), 30, new double[] {0, 0, 0, 0.5}, new double[] {0, 0, 0, 0});
		
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
			Circle.drawFill(gl, foliageLoc, foliageRad, 40, new double[] {0.3137, 0.482, 0, 0.95});
			Circle.drawLines(gl, foliageLoc, foliageRad, 40, new double[] {0, 0, 0, 0.3});
		}
	}
}
