package objects;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

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
}
