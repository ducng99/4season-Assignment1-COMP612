package objects;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import main.Environment;
import main.Main;
import main.Utils;
import main.Vector;
import shapes.Triangle;

public class Leaf extends Particle {
	public static ArrayList<Leaf> leafParticles = new ArrayList<>();
	
	private int size = 0;
	private int fallSpeed = 0;
	private double[][] rgbaArray;
	private Vector[] posOffsets = {new Vector(Utils.genRand(-10, 10), Utils.genRand(-10, 10)), new Vector(Utils.genRand(-10, 10), Utils.genRand(-10, 10)), new Vector(Utils.genRand(-10, 10), Utils.genRand(-10, 10))};

	public Leaf(int x, int y) {
		super(x, y);
		fallSpeed = Utils.genRand(20, 70);
		this.size = Utils.genRand(3, 7);
		rgbaArray = new double[][] {{Utils.genRand(0.43, 0.51), Utils.genRand(0.33, 0.42), 0, Utils.genRand(0.6, 1)}, {Utils.genRand(0.43, 0.51), Utils.genRand(0.33, 0.42), 0, Utils.genRand(0.6, 1)}, {Utils.genRand(0.43, 0.51), Utils.genRand(0.33, 0.42), 0, Utils.genRand(0.6, 1)}};
	}

	@Override
	public void draw(GL2 gl) {
		Vector[] verticies = new Vector[]
		{
			Utils.ScreenToWorldLoc(Position.Offset(posOffsets[0].x + size, posOffsets[0].y + size)),
			Utils.ScreenToWorldLoc(Position.Offset(posOffsets[1].x + size, posOffsets[1].y + size)),
			Utils.ScreenToWorldLoc(Position.Offset(posOffsets[2].x + size, posOffsets[2].y + size))
		};
		
		Triangle.drawFill(gl, verticies, rgbaArray);
		
		// Consistent speed for any FPS + leaf moving
		UpdatePos(Position.Offset((Environment.getWindSpeed() + Utils.genRand(0, 10)) / Main.getFPS() + Utils.genRand(-0.5, 0.5), fallSpeed / Main.getFPS()));
	}

	@Override
	public void UpdatePos(Vector pos)
	{
		Position = pos;
	}
	
	public static void DrawAllLeaf(GL2 gl)
	{
		for (Leaf l : leafParticles)
		{
			l.draw(gl);
		}
	}
	
	public static int countAllLeaf()
	{
		int count = 0;
		for (Leaf l : leafParticles)
		{
			if (!l.isDead)
				count++;
		}
		
		return count;
	}
	
	public int getFallSpeed()
	{
		return fallSpeed;
	}
}
