package shapes;

import com.jogamp.opengl.GL2;

import main.Vector;

public class Triangle {
	public static void drawFill(GL2 gl, Vector[] verticies, double[][] rgbaArray)
	{
		gl.glBegin(GL2.GL_TRIANGLES);
		
		for (int i = 0; i < verticies.length; i++)
		{
			gl.glColor4d(rgbaArray[i][0], rgbaArray[i][1], rgbaArray[i][2], rgbaArray[i][3]);
			gl.glVertex2d(verticies[i].x, verticies[i].y);
		}
		
		gl.glEnd();
	}
	
	public static void drawFill(GL2 gl, Vector[] verticies, double[] rgba)
	{
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glColor4d(rgba[0], rgba[1], rgba[2], rgba[3]);
		
		for (Vector v : verticies)
		{
			gl.glVertex2d(v.x, v.y);
		}
		
		gl.glEnd();
	}
}
