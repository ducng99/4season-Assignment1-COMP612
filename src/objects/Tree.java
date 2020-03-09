package objects;

import com.jogamp.opengl.GL2;
import main.Utils;
import main.Vector;

public class Tree extends Particle {
	
	private int height = 0;

	public Tree(int x, int y, int height) {
		super(x, y);
		this.height = height;
	}

	@Override
	public void draw(GL2 gl) {
		Vector[] verticies;
		
		//i don't know what this is called. bottom bottom?		
		verticies = new Vector[] {
			Utils.ScreenToWorld(Position.Offset(-5, -(height / 4.0))),
			Utils.ScreenToWorld(Position.Offset(5, -(height / 4.0))),
			Utils.ScreenToWorld(Position.Offset(5, 0)),
			Utils.ScreenToWorld(Position.Offset(-5, 0))
		};
		
		shapes.Polygon.drawFill(gl, verticies, new double[] {0.05, 0.3, 0.1, 1});

		gl.glBegin(GL2.GL_TRIANGLES);

		//Bottom
		verticies = new Vector[] {
			Utils.ScreenToWorld(Position.Offset(0, -(height / 2.0))),
			Utils.ScreenToWorld(Position.Offset(-25, -(height / 4.0))),
			Utils.ScreenToWorld(Position.Offset(25, -(height / 4.0)))
		};
		
		shapes.Triangle.drawFill(gl, verticies, new double[] {0, 0.4, 0, 1});
		
		//Middle
		verticies = new Vector[] {
			Utils.ScreenToWorld(Position.Offset(0, -(height / 4.0 * 3))),
			Utils.ScreenToWorld(Position.Offset(-20, -(height / 2.0) + 5)),
			Utils.ScreenToWorld(Position.Offset(20, -(height / 2.0) + 5))
		};
		
		shapes.Triangle.drawFill(gl, verticies, new double[] {0, 0.4, 0, 1});
		
		//Top
		verticies = new Vector[] {
			Utils.ScreenToWorld(Position.Offset(0, -height)),
			Utils.ScreenToWorld(Position.Offset(-15, -(height / 4.0 * 3) + 5)),
			Utils.ScreenToWorld(Position.Offset(15, -(height / 4.0 * 3) + 5))
		};
		
		gl.glColor4f(0.99f, 0.99f, 0.99f, 0.1f);
		
		shapes.Triangle.drawFill(gl, verticies, new double[][] {{0.99, 0.99, 0.99, 1}, {0, 0.4, 0, 1}, {0, 0.4, 0, 1}});

		gl.glEnd();
	}
	
}
