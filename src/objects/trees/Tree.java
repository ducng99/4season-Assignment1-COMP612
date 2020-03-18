package objects.trees;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import main.Environment;
import main.Main;
import main.Utils;
import objects.Land;
import objects.Particle;

public abstract class Tree extends Particle {
	public static ArrayList<Tree> trees = new ArrayList<>();
	
	public int height;
	
	public Tree(int x, int y, int height) {
		super(x, y);
		this.height = height;
	}

	@Override
	public void draw(GL2 gl) {
		throw new NullPointerException("Draw function not defined");
	}

	public static void DrawAllTrees(GL2 gl)
	{
		for (Tree t : trees)
		{
			t.draw(gl);
		}
	}
	
	/**
	 * Generate normal trees and pine trees randomly. Start height is always lower than the {@link Land} object from {@link Environment}
	 * @param numTrees
	 */
	public static void GenerateTrees(int numTrees)
	{		
		for (int i = 0; i < numTrees; i++)
		{
			int treeType = Utils.genRand(1, 2);
			if (treeType == 1)
				Tree.trees.add(new PineTree(Utils.genRand(10, Main.dimension.width - 10), (int)Environment.getLand().getPosition().y + 30 + i * (int)Math.round((Main.dimension.getHeight() - Environment.getLand().getPosition().y) / 2 / numTrees), 50 + i * 6));
			else
				Tree.trees.add(new NormalTree(Utils.genRand(10, Main.dimension.width - 10), (int)Environment.getLand().getPosition().y + 30 + i * (int)Math.round((Main.dimension.getHeight() - Environment.getLand().getPosition().y) / 2 / numTrees), 40 + i * 12));
		}
	}
}
