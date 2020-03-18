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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JFrame;

public class Main implements GLEventListener, MouseListener {
	public static final JFrame frame = new JFrame("4 seasons-ish");
	private static FPSAnimator animator;
	private static final LinkedBlockingQueue<MouseEvent> mouseEventsQ = new LinkedBlockingQueue<>();
	
	private int displayList;
	private long prevTick = System.currentTimeMillis();

	private GLUT glut = new GLUT();
	private int font = GLUT.BITMAP_HELVETICA_18;

	@Override
	public void display(GLAutoDrawable gld) {
		final GL2 gl = gld.getGL().getGL2();
		
		while (mouseEventsQ.size() > 0)
		{
			handleMouseEvents();
		}
		
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		
		gl.glCallList(displayList);
		
		// Every 50ms
		if (System.currentTimeMillis() - prevTick > 50)
		{
			if (Environment.getSeason() == Season.Winter)
				GenerateSnow(200, 1);	// 200 max snow, generate 1 per 50 tick
			else
				GenerateLeaf(2);	//10 per 50 ticks
			prevTick = System.currentTimeMillis();
		}
		
		if (Environment.getSeason() == Season.Winter)
			Snow.DrawAllSnow(gl);
		else
			Leaf.DrawAllLeaf(gl);
		
		DrawDebugText(gl);
		
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
		gl.glEnable(GL2.GL_LINE_SMOOTH);
		gl.glShadeModel(GL2.GL_SMOOTH);
		
		Sky sky = new Sky();
		Environment.setSky(sky);

		Land land = new Land();
		Environment.setLand(land);
		
		GenerateTrees(20);
		
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
		
		GLCanvas canvas = new GLCanvas(glCapabilities);
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
				new Thread(new Runnable() {
					public void run() {
						animator.stop();
						System.exit(0);
					}
				}).start();
			}
		});
		
		//Setup environment
		Environment.setWindSpeed(30);
		Environment.setTime(Time.Night);
		Environment.setSeason(Season.Winter);
		
		frame.setVisible(true);
		
		animator.start();
	}
	
	/**
	 * Generate normal trees and pine trees randomly. Start height is always lower than the {@link Land} object from {@link Environment}
	 * @param numTrees
	 */
	public static void GenerateTrees(int numTrees)
	{		
		for (int i = 0; i < numTrees; i++)
		{
			int treeType = Utils.genRand(1, 2);
			if (treeType == 1)
				Tree.trees.add(new PineTree(Utils.genRand(10, frame.getSize().width - 10), (int)Environment.getLand().getPosition().y + 30 + i * (int)Math.round(100.0 / numTrees), 50 + i * 8));
			else
				Tree.trees.add(new NormalTree(Utils.genRand(10, frame.getSize().width - 10), (int)Environment.getLand().getPosition().y + 30 + i * (int)Math.round(100.0 / numTrees), 50 + i * 8));
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
				Snow s = new Snow(0, 0);
				double fallSpeed = s.getFallSpeed();
				double windWidthCompensate = frame.getSize().height / fallSpeed * Environment.getWindSpeed();
				int startWidthPoint = windWidthCompensate >= 0 ? Utils.genRand((int)-windWidthCompensate, frame.getSize().width) : Utils.genRand(0, (int)(frame.getSize().width - windWidthCompensate));
				s.UpdatePos(new Vector(startWidthPoint, 0));
				Snow.snowParticles.add(s);
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
					int fallSpeed = s.getFallSpeed();
					double windWidthCompensate = frame.getSize().height / (double)fallSpeed * Environment.getWindSpeed();
					int startWidthPoint = windWidthCompensate >= 0 ? Utils.genRand((int)-windWidthCompensate, frame.getSize().width) : Utils.genRand(0, (int)(frame.getSize().width - windWidthCompensate));
					s.UpdatePos(new Vector(startWidthPoint, 0));
					s.isDead = false;
					i++;
				}
			}
		}
	}
	
	private static void GenerateLeaf(int noLeafToAdd)
	{
		if (Tree.trees.size() > 0)
		{
			int highestTreeHeight = frame.getSize().height;
			
			for (int i = 0; i < Tree.trees.size(); i++)
			{
				int tmp = (int)Tree.trees.get(i).getPosition().y - Tree.trees.get(i).height;
				if (tmp < highestTreeHeight)
				{
					highestTreeHeight = tmp;
				}
			}
			
			for (int i = 0; i < noLeafToAdd; i++)
			{
				Leaf l = new Leaf(0, 0);
				int fallSpeed = l.getFallSpeed();
				double windWidthCompensate = (frame.getSize().height - highestTreeHeight) / fallSpeed * Environment.getWindSpeed();
				int startWidthPoint = windWidthCompensate >= 0 ? Utils.genRand((int)-windWidthCompensate, frame.getSize().width) : Utils.genRand(0, (int)(frame.getSize().width - windWidthCompensate));
				l.UpdatePos(new Vector(startWidthPoint, Utils.genRand(highestTreeHeight, highestTreeHeight + 70)));
				Leaf.leafParticles.add(l);
			}
		}
	}
	
	private void DrawDebugText(GL2 gl)
	{
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
		if (Environment.getSeason() == Season.Winter)
			glut.glutBitmapString(font, "Snows available: " + Snow.countAvailableSnow());
		else
			glut.glutBitmapString(font, "Leaves available: " + Leaf.countAllLeaf());

		glutPoint = glutPoint.Offset(0, 25);
		glutPointW = Utils.ScreenToWorldLoc(glutPoint);
		gl.glRasterPos2d(glutPointW.x, glutPointW.y);
		glut.glutBitmapString(font, "Time: " + Environment.getTime());

		glutPoint = glutPoint.Offset(0, 25);
		glutPointW = Utils.ScreenToWorldLoc(glutPoint);
		gl.glRasterPos2d(glutPointW.x, glutPointW.y);
		glut.glutBitmapString(font, "Season: " + Environment.getSeason());
	}
	
	private static void handleMouseEvents()
	{
		try {
			MouseEvent e = mouseEventsQ.take();
			Vector mousePos = new Vector(e.getX(), e.getY());
			
			for (Button b : Button.buttons)
			{
				if (!b.isDead)
				{
					b.DoAction();
				}
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static double getFPS()
	{
		if (animator.getLastFPS() == 0.0f)
			return animator.getFPS();
		else
			return animator.getLastFPS();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		try {
			mouseEventsQ.put(arg0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
