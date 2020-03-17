package main;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

import main.Environment.Season;
import main.Environment.Time;
import objects.*;
import shapes.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Main implements GLEventListener {
	public static final JFrame frame = new JFrame("Hello world");
	private static FPSAnimator animator = null;
	
	private static ArrayList<Particle> staticParticles = new ArrayList<>();
	
	private int displayList;
	private double prevTick = System.currentTimeMillis();
	
	private int font = GLUT.BITMAP_HELVETICA_18;

	@Override
	public void display(GLAutoDrawable gld) {
		final GL2 gl = gld.getGL().getGL2();
		
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		
		gl.glCallList(displayList);
		
		if (System.currentTimeMillis() - prevTick > 50)
		{
			GenerateSnow(200, 1);	// 200 max snow, generate 1 per 50 tick
			prevTick = System.currentTimeMillis();
		}
		
		if (Environment.getSeason() == Season.Winter)
			Snow.DrawAllSnow(gl);
		
		GLUT glut = new GLUT();
		Vector glutPoint = new Vector(5, 20);
		Vector glutPointW = Utils.ScreenToWorldLoc(glutPoint);
		gl.glRasterPos2d(glutPointW.x, glutPointW.y);
		glut.glutBitmapString(font, "FPS: " + Math.round(getFPS()));
		
		glutPoint = glutPoint.Offset(0, 25);
		glutPointW = Utils.ScreenToWorldLoc(glutPoint);
		gl.glRasterPos2d(glutPointW.x, glutPointW.y);
		glut.glutBitmapString(font, "Wind speed: " + Environment.getWindSpeed());

		glutPoint = glutPoint.Offset(0, 25);
		glutPointW = Utils.ScreenToWorldLoc(glutPoint);
		gl.glRasterPos2d(glutPointW.x, glutPointW.y);
		glut.glutBitmapString(font, "Snows available: " + Snow.getAvailableSnow());

		glutPoint = glutPoint.Offset(0, 25);
		glutPointW = Utils.ScreenToWorldLoc(glutPoint);
		gl.glRasterPos2d(glutPointW.x, glutPointW.y);
		glut.glutBitmapString(font, "Time: " + Environment.getTime());
		
		gl.glFlush();
	}

	@Override
	public void dispose(GLAutoDrawable gld) {
	}

	@Override
	public void init(GLAutoDrawable gld) {
		final GL2 gl = gld.getGL().getGL2();
		
		gl.setSwapInterval(0);

		// Enable transparent
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL2.GL_BLEND);
		gl.glShadeModel(GL2.GL_SMOOTH);
		
		Sky sky = new Sky();
		Environment.setSky(sky);

		Land land = new Land();
		Environment.setLand(land);
		
		GenerateTrees(10);
		
		// Initialize list of static objects
		displayList = gl.glGenLists(1);

		gl.glNewList(displayList, GL2.GL_COMPILE);
		
		Environment.getSky().draw(gl);
		Environment.getLand().draw(gl);
		
		Tree.DrawAllTrees(gl);
		
		gl.glEndList();
	}

	@Override
	public void reshape(GLAutoDrawable gld, int x, int y, int width, int height) {
		final GL2 gl = gld.getGL().getGL2();
		
		gl.glNewList(displayList, GL2.GL_COMPILE);
		
		Environment.getSky().draw(gl);
		Environment.getLand().draw(gl);
		
		Tree.DrawAllTrees(gl);
		
		gl.glEndList();
	}
	
	public static void main(String[] args)
	{		
		GLProfile glProfile = GLProfile.get(GLProfile.GL2);
		GLCapabilities glCapabilities = new GLCapabilities(glProfile);
		
		GLCanvas canvas = new GLCanvas();
		canvas.addGLEventListener(new Main());
		
		frame.add(canvas);
		frame.setSize(1200, 800);
		//frame.setResizable(false);
		
		animator = new FPSAnimator(144);
		animator.setUpdateFPSFrames(10, null);
		animator.add(canvas);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				animator.stop();
				System.exit(0);
			}
		});
		
		//Setup environment
		Environment.setWindSpeed(30);
		
		frame.setVisible(true);
		
		animator.start();
	}
	
	public static void GenerateTrees(int numTrees)
	{		
		for (int i = 0; i < numTrees; i++)
		{
			int treeType = Utils.genRand(1, 2);
			if (treeType == 1)
				Tree.trees.add(new PineTree(Utils.genRand(10, frame.getSize().width - 10), (int)Environment.getLand().getPosition().y + 20 + i * (int)Math.round(80.0 / numTrees), 50 + i * 10));
			else
				Tree.trees.add(new NormalTree(Utils.genRand(10, frame.getSize().width - 10), (int)Environment.getLand().getPosition().y + 20 + i * (int)Math.round(80.0 / numTrees), 50 + i * 10));
		}
	}
	
	/**
	 * Generate snow with maximum allowed number of snow. Snow will be reseted if dead and reused when maximum number of snow has been reached.
	 * @param maxNumSnow maximum number of snow allowed
	 * @param noSnowToAdd how many snow should be generated
	 */
	private static void GenerateSnow(int maxNumSnow, int noSnowToAdd)
	{
		int currentNoSnow = Snow.snowParticles.size();
		if (currentNoSnow < maxNumSnow)
		{
			// Adding snow particles
			for (int i = 0; i < maxNumSnow - currentNoSnow && i < noSnowToAdd; i++)
			{
				// Compensate for wind speed by padding start point on x-axis of the snow
				double fallSpeed = Utils.genRand(20, 70);
				double windWidthCompensate = frame.getSize().height / fallSpeed * Environment.getWindSpeed();
				int startWidthPoint = windWidthCompensate >= 0 ? Utils.genRand((int)-windWidthCompensate, frame.getSize().width) : Utils.genRand(0, (int)(frame.getSize().width - windWidthCompensate));
				Snow.snowParticles.add(new Snow(startWidthPoint, Utils.genRand(-frame.getSize().height / 4, 0), Utils.genRand(3, 5), fallSpeed));
			}
		}
		else
		{
			int i = 0;
			for (Snow s : Snow.snowParticles)
			{
				// reusing dead snow particles
				if (s.isDead && i < noSnowToAdd)
				{
					// Compensate for wind speed by padding start point on x-axis of the snow
					double fallSpeed = s.getFallSpeed();
					double windWidthCompensate = frame.getSize().height / fallSpeed * Environment.getWindSpeed();
					int startWidthPoint = windWidthCompensate >= 0 ? Utils.genRand((int)-windWidthCompensate, frame.getSize().width) : Utils.genRand(0, (int)(frame.getSize().width - windWidthCompensate));
					s.UpdatePos(new Vector(startWidthPoint, Utils.genRand(-frame.getSize().height / 4, 0)));
					s.isDead = false;
					i++;
				}
			}
		}
	}
	
	public static double getFPS()
	{
		if (animator.getLastFPS() == 0.0f)
			return animator.getFPS();
		else
			return animator.getLastFPS();
	}
}
