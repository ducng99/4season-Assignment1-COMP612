package objects.trees;

import java.awt.Dimension;
import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import main.Environment;
import main.Main;
import main.Utils;
import main.Vector;
import objects.Particle;
import shapes.Triangle;

public class Leaf extends Particle {
	public static ArrayList<Leaf> leafParticles = new ArrayList<>();
	
	private int size = 0;
	private int fallSpeed = 0;
	private double[][] rgbaArray;
	private double stopHeight;
	private Vector[] posOffsets = {new Vector(Utils.genRand(-10, 10), Utils.genRand(-10, 10)), new Vector(Utils.genRand(-10, 10), Utils.genRand(-10, 10)), new Vector(Utils.genRand(-10, 10), Utils.genRand(-10, 10))};

	public Leaf(int x, int y) {
		super(x, y);
		fallSpeed = Utils.genRand(20, 70);
		this.size = Utils.genRand(3, 7);
		rgbaArray = new double[][] {{Utils.genRand(0.4, 0.6), Utils.genRand(0.3, 0.5), 0, Utils.genRand(0.6, 1)}, {Utils.genRand(0.4, 0.6), Utils.genRand(0.3, 0.5), 0, Utils.genRand(0.6, 1)}, {Utils.genRand(0.4, 0.6), Utils.genRand(0.3, 0.5), 0, Utils.genRand(0.6, 1)}};
		stopHeight = Utils.genRand(Environment.getLand().getPosition().y + (Main.dimension.getHeight() - Environment.getLand().getPosition().y) / 2, Main.dimension.getHeight());
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
		
		if (Position.y < stopHeight)
		{
			// Consistent speed for any FPS + leaf moving
			UpdatePos(Position.Offset((Environment.getWindSpeed() + Utils.genRand(0, 10)) / Main.getFPS() + Utils.genRand(-0.5, 0.5), fallSpeed / Main.getFPS()));
		}
	}

	@Override
	public void UpdatePos(Vector pos)
	{
		Position = pos;
	}
	
	@Override
	public void checkAvailability()
	{
		Dimension d = Main.dimension;
		if (Position.y > d.height + size || (Environment.getWindSpeed() > 0 ? Position.x > d.width + size : Position.x < -size))
		{
			isDead = true;
		}
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
	
	/**
	 * Generate a given number of leaves, reusing dead leaves
	 * @param noLeafToAdd
	 */
	public static void GenerateLeaf(int noLeafToAdd)
	{
		if (Tree.trees.size() > 0)
		{
			int highestTreeHeight = Main.dimension.height;
			
			for (Tree t : Tree.trees)
			{
				int tmp = (int)t.getPosition().y - t.height;
				if (tmp < highestTreeHeight)
				{
					highestTreeHeight = tmp;
				}
			}
			
			int addedLeaf = 0;
			
			for (Leaf l : Leaf.leafParticles)
			{
				if (l.isDead && addedLeaf < noLeafToAdd)
				{
					int fallSpeed = l.getFallSpeed();
					double windWidthCompensate = (Main.dimension.height - highestTreeHeight) / fallSpeed * Environment.getWindSpeed();
					int startWidthPoint = windWidthCompensate >= 0 ? Utils.genRand((int)-windWidthCompensate, Main.dimension.width) : Utils.genRand(0, (int)(Main.dimension.width - windWidthCompensate));
					l.UpdatePos(new Vector(startWidthPoint, Utils.genRand(highestTreeHeight, highestTreeHeight + 70)));
					addedLeaf++;
				}
			}
			
			for (; addedLeaf < noLeafToAdd; addedLeaf++)
			{
				Leaf l = new Leaf(0, 0);
				int fallSpeed = l.getFallSpeed();
				double windWidthCompensate = (Main.dimension.height - highestTreeHeight) / fallSpeed * Environment.getWindSpeed();
				int startWidthPoint = windWidthCompensate >= 0 ? Utils.genRand((int)-windWidthCompensate, Main.dimension.width) : Utils.genRand(0, (int)(Main.dimension.width - windWidthCompensate));
				l.UpdatePos(new Vector(startWidthPoint, Utils.genRand(highestTreeHeight, highestTreeHeight + 70)));
				Leaf.leafParticles.add(l);
			}
		}
	}
}
