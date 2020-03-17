package shapes;

import com.jogamp.opengl.GL2;

import main.Vector;

public class Circle {
	public static void drawFill(GL2 gl, Vector pos, Vector radius, int steps, double[] rgba)
	{
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		
		gl.glColor4d(rgba[0], rgba[1], rgba[2], rgba[3]);
		
		gl.glVertex2d(pos.x, pos.y);

		for (double deg = 0.0; deg <= 360.0; deg += 360.0 / steps)
		{
			double rad = Math.toRadians(deg);
			double tmpX = pos.x + Math.cos(rad) * radius.x;
		    double tmpY = pos.y + Math.sin(rad) * radius.y;
		    gl.glVertex2d(tmpX, tmpY);
		}
		
		gl.glEnd();
	}

	public static void drawLines(GL2 gl, Vector pos, Vector radius, int steps, double[] rgba)
	{
		gl.glBegin(GL2.GL_LINE_LOOP);
		
		gl.glColor4d(rgba[0], rgba[1], rgba[2], rgba[3]);

		for (double deg = 0.0; deg <= 360.0; deg += 360.0 / steps)
		{
			double rad = Math.toRadians(deg);
			double tmpX = pos.x + Math.cos(rad) * radius.x;
		    double tmpY = pos.y + Math.sin(rad) * radius.y;
		    gl.glVertex2d(tmpX, tmpY);
		}
		
		gl.glEnd();
	}
}
