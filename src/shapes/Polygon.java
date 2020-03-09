package shapes;

import com.jogamp.opengl.GL2;

import main.Vector;

public class Polygon {

	public static void drawFill(GL2 gl, Vector[] verticies, double[] colour) {
		gl.glBegin(GL2.GL_POLYGON);
		gl.glColor4d(colour[0], colour[1], colour[2], colour[3]);
		
		for (Vector v : verticies)
		{
			gl.glVertex2d(v.x, v.y);
		}
		
		gl.glEnd();
	}
	
	public static void drawFill(GL2 gl, Vector[] verticies, double[][] colour) {
		gl.glBegin(GL2.GL_POLYGON);
		
		for (int i = 0; i < verticies.length; i++)
		{
			gl.glColor4d(colour[i][0], colour[i][1], colour[i][2], colour[i][3]);
			gl.glVertex2d(verticies[i].x, verticies[i].y);
		}
		
		gl.glEnd();
	}
}
