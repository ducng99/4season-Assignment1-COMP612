package objects;

import java.awt.Dimension;

import com.jogamp.opengl.GL2;

import main.Main;
import main.Utils;
import main.Vector;

public class Land extends Particle {
	public Land()
	{
		super(0, 0);
	}

	@Override
	public void draw(GL2 gl) {
		Vector tmp = new Vector();
		Dimension d = Main.frame.getSize();
		
		gl.glBegin(GL2.GL_POLYGON);
		
		gl.glColor3d(0.1, 0, 0);
		
		tmp = Utils.ScreenToWorld(new Vector(0, d.getHeight() / 2));
		gl.glVertex2d(tmp.x, tmp.y);
		tmp = Utils.ScreenToWorld(new Vector(d.getWidth() / 4, d.getHeight() / 2 - 20));
		gl.glVertex2d(tmp.x, tmp.y);
		tmp = Utils.ScreenToWorld(new Vector(d.getWidth() / 2, d.getHeight() / 2 + 20));
		gl.glVertex2d(tmp.x, tmp.y);
		tmp = Utils.ScreenToWorld(new Vector(d.getWidth() / 4 * 3, d.getHeight() / 2 - 10));
		gl.glVertex2d(tmp.x, tmp.y);

		gl.glColor3d(0.2, 0.02, 0.02);
		tmp = Utils.ScreenToWorld(new Vector(d.getWidth(), d.getHeight() / 2 + 10));
		gl.glVertex2d(tmp.x, tmp.y);

		gl.glColor3d(0.1, 0, 0);
		tmp = Utils.ScreenToWorld(new Vector(d.getWidth(), d.getHeight()));
		gl.glVertex2d(tmp.x, tmp.y);
		tmp = Utils.ScreenToWorld(new Vector(0, d.getHeight()));
		gl.glVertex2d(tmp.x, tmp.y);
		
		gl.glEnd();
	}
}
