package objects;

import com.jogamp.opengl.GL2;

import main.Environment;
import main.Environment.Season;
import main.Environment.Time;
import main.Utils;
import main.Vector;
import shapes.Circle;

public class Sun extends Particle {
	private int displayList = -1;
	private long prevTick = 0;

	public Sun(int x, int y) {
		super(x, y);
	}

	@Override
	public void draw(GL2 gl) {
		if (displayList == -1)
		{
			displayList = gl.glGenLists(1);
		}
		
		if (System.currentTimeMillis() - prevTick > 80)
		{
			gl.glNewList(displayList, GL2.GL_COMPILE);
		
			if (Environment.getSeason() == Season.Autumn && Environment.getTime() == Time.Day)
			{
				gl.glBegin(GL2.GL_LINES);
	
				Vector origPos = Utils.ScreenToWorldLoc(Position);
				Vector sunRadius = Utils.ScreenToWorldDist(new Vector(50, 50));
				Vector coronaRadius = Utils.ScreenToWorldDist(new Vector(60, 60));
				
				for (double deg = 0.0; deg <= 360.0; deg += 360.0 / 250)
				{
					gl.glColor3d(0.99, 0.78, 0.074);
					gl.glVertex2d(origPos.x + Utils.genRand(-0.01, 0.01), origPos.y + Utils.genRand(-0.01, 0.01));
					double rad = Math.toRadians(deg);
					double tmpX = origPos.x + Math.cos(rad) * coronaRadius.x + Utils.genRand(-0.02, 0.02);
				    double tmpY = origPos.y + Math.sin(rad) * coronaRadius.y + Utils.genRand(-0.02, 0.02);
					gl.glColor4d(0.99, 0.78, 0.074, 0.2);
				    gl.glVertex2d(tmpX, tmpY);
				}
				
				gl.glEnd();
				
				Circle.drawFill(gl, origPos, sunRadius, 20, new double[] {0.99, 0.72, 0.074, 1});
			}
			
			gl.glEndList();
			
			prevTick = System.currentTimeMillis();
		}
		
		gl.glCallList(displayList);
	}

}
