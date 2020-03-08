package objects;

import main.Utils;
import main.Vector;

import com.jogamp.opengl.GL2;

public class Tree extends Particle {
	
	private int height = 0;

	public Tree(int x, int y, int height) {
		super(x, y);
		this.height = height;
	}

	@Override
	public void draw(GL2 gl) {
		Vector worldPos;
		
		//i don't know what this is called. bottom bottom?
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3d(0.2, 0.1, 0.1);
		
		worldPos = Utils.ScreenToWorld(Position.Offset(-5, -20));
		gl.glVertex2d(worldPos.x, worldPos.y);
		worldPos = Utils.ScreenToWorld(Position.Offset(5, -20));
		gl.glVertex2d(worldPos.x, worldPos.y);
		worldPos = Utils.ScreenToWorld(Position.Offset(5, 0));
		gl.glVertex2d(worldPos.x, worldPos.y);
		worldPos = Utils.ScreenToWorld(Position.Offset(-5, 0));
		gl.glVertex2d(worldPos.x, worldPos.y);
		
		gl.glEnd();

		gl.glBegin(GL2.GL_TRIANGLES);

		gl.glColor3d(0, 0.4, 0);
		//Bottom
		worldPos = Utils.ScreenToWorld(Position.Offset(0, -40));
		gl.glVertex2d(worldPos.x, worldPos.y);
		worldPos = Utils.ScreenToWorld(Position.Offset(-25, -20));
		gl.glVertex2d(worldPos.x, worldPos.y);
		worldPos = Utils.ScreenToWorld(Position.Offset(25, -20));
		gl.glVertex2d(worldPos.x, worldPos.y);
		
		//Middle
		worldPos = Utils.ScreenToWorld(Position.Offset(0, -55));
		gl.glVertex2d(worldPos.x, worldPos.y);
		worldPos = Utils.ScreenToWorld(Position.Offset(-20, -35));
		gl.glVertex2d(worldPos.x, worldPos.y);
		worldPos = Utils.ScreenToWorld(Position.Offset(20, -35));
		gl.glVertex2d(worldPos.x, worldPos.y);
		
		//Top
		gl.glColor4f(0.99f, 0.99f, 0.99f, 0.1f);
		
		worldPos = Utils.ScreenToWorld(Position.Offset(0, -70));
		gl.glVertex2d(worldPos.x, worldPos.y);
		
		gl.glColor3d(0, 0.4, 0);
		worldPos = Utils.ScreenToWorld(Position.Offset(-15, -50));
		gl.glVertex2d(worldPos.x, worldPos.y);
		worldPos = Utils.ScreenToWorld(Position.Offset(15, -50));
		gl.glVertex2d(worldPos.x, worldPos.y);

		gl.glEnd();
	}
	
}
