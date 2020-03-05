package objects;

import com.jogamp.opengl.GL2;

public class Land extends Particle {
	public Land(int x, int y)
	{
		super(x, y);
	}

	@Override
	public void draw(GL2 gl) {
		gl.glBegin(GL2.GL_POLYGON);
		
		gl.glColor3d(arg0, arg1, arg2);
	}
}
