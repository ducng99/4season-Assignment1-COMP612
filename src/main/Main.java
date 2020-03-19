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
import objects.trees.Leaf;
import objects.trees.Tree;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JFrame;

public class Main implements GLEventListener, MouseListener {
	private static FPSAnimator animator;
	public static Dimension dimension = new Dimension();
	private static final LinkedBlockingQueue<MouseEvent> mouseEventsQ = new LinkedBlockingQueue<>();
	
	private int displayList;
	private long prevTick = System.currentTimeMillis();

	public static final GLUT glut = new GLUT();
	public static final int font = GLUT.BITMAP_HELVETICA_18;

	@Override
	public void display(GLAutoDrawable gld) {
		final GL2 gl = gld.getGL().getGL2();
		
		while (mouseEventsQ.size() > 0)
		{
			handleMouseEvents();
		}
		
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		
		gl.glCallList(displayList);
		Environment.getSun().draw(gl);	// Update sun every 50ms
		
		// Every 50ms
		if (System.currentTimeMillis() - prevTick > 50)
		{			
			if (Environment.getSeason() == Season.Winter)
				Snow.GenerateSnow(400, 5);	// 400 max snow, generate 2 per 50 ticks -> faster & more snow for snowman
			else
				Leaf.GenerateLeaf(2);	//2 leaves per 50 ticks
			prevTick = System.currentTimeMillis();
		}
		
		Environment.getSnowman().draw(gl);
		
		if (Environment.getSeason() == Season.Winter)
			Snow.DrawAllSnow(gl);
		else
			Leaf.DrawAllLeaf(gl);
		
		Button.DrawAllButton(gl);
		
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
		
		Sun sun = new Sun(dimension.width - 100, 100);
		Environment.setSun(sun);

		Land land = new Land();
		Environment.setLand(land);
		
		// Initialize buttons
		new Button(10, dimension.height - 40, 150, 30, "Season: Winter", new double[] {0, 0, 0.4, 0.85}, new Runnable() {
			@Override
			public void run() {
				if (Environment.getSeason() != Season.Winter)
				{
					Environment.setSeason(Season.Winter);
					Button.linkedButton.get(this).isDead = true;
					Button.linkedButton.get(this).SetText("Season: Winter");
					Button.linkedButton.get(this).SetColour(new double[] {0, 0, 0.4, 0.85});
					System.out.println("Changed season: Winter");
					DrawStaticParticles(gl);
				}
				else
				{
					Environment.setSeason(Season.Autumn);
					Button.linkedButton.get(this).isDead = false;
					Button.linkedButton.get(this).SetText("Season: Autumn");
					Button.linkedButton.get(this).SetColour(new double[] {0, 0.2, 0.6, 0.85});
					System.out.println("Changed season: Autumn");
					DrawStaticParticles(gl);
				}
			}});
		
		new Button(170, dimension.height - 40, 150, 30, "Time: Night", new double[] {0, 0, 0.4, 0.85}, new Runnable() {
			@Override
			public void run() {
				if (Environment.getTime() == Time.Day)
				{
					Environment.setTime(Time.Night);
					Button.linkedButton.get(this).SetText("Time: Night");
					Button.linkedButton.get(this).SetColour(new double[] {0, 0, 0.4, 0.85});
					System.out.println("Changed time: Night");
					DrawStaticParticles(gl);
				}
				else
				{
					Environment.setTime(Time.Day);
					Button.linkedButton.get(this).SetText("Time: Day");
					Button.linkedButton.get(this).SetColour(new double[] {0, 0.2, 0.6, 0.85});
					System.out.println("Changed time: Day");
					DrawStaticParticles(gl);
				}
			}
		});
		
		new Button(330, dimension.height - 40, 150, 30, "Wind Speed -", new double[] {0, 0, 0.4, 0.85}, new Runnable() {
			@Override
			public void run() {
				Environment.setWindSpeed(Environment.getWindSpeed() - 5);
				System.out.println("Wind speed - 5 = " + Environment.getWindSpeed());
			}
		});
		
		new Button(490, dimension.height - 40, 150, 30, "Wind Speed +", new double[] {0, 0, 0.4, 0.85}, new Runnable() {
			@Override
			public void run() {
				Environment.setWindSpeed(Environment.getWindSpeed() + 5);
				System.out.println("Wind speed + 5 = " + Environment.getWindSpeed());
			}
		});
		
		Snowman sm = new Snowman((int)(dimension.width / 2.0), (int)(Environment.getLand().getPosition().y + (dimension.height - Environment.getLand().getPosition().y) / 2.0));
		Environment.setSnowman(sm);
		
		Tree.GenerateTrees(20);
		
		// Initialize list of static objects
		displayList = gl.glGenLists(1);
		DrawStaticParticles(gl);
	}

	@Override
	public void reshape(GLAutoDrawable gld, int x, int y, int width, int height) {
		final GL2 gl = gld.getGL().getGL2();
		
		dimension.setSize(width, height);
		
		DrawStaticParticles(gl);
	}
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("4 seasons-ish");
		frame.setSize(1200, 800);
		
		GLProfile glProfile = GLProfile.get(GLProfile.GL2);
		GLCapabilities glCapabilities = new GLCapabilities(glProfile);
		
		GLCanvas canvas = new GLCanvas(glCapabilities);
		Main main = new Main();
		canvas.addGLEventListener(main);
		canvas.addMouseListener(main);
		frame.add(canvas);
		
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
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		canvas.requestFocusInWindow();
		dimension.setSize(canvas.getSize());
		
		animator.start();
	}
	
	private void DrawStaticParticles(GL2 gl)
	{
		gl.glNewList(displayList, GL2.GL_COMPILE);
		
		Environment.getSky().draw(gl);
		Environment.getLand().draw(gl);
		
		Tree.DrawAllTrees(gl);
		
		gl.glEndList();
	}
	
	private void DrawDebugText(GL2 gl)
	{
		gl.glColor4d(1, 1, 1, 0.8);
		Vector glutPoint = new Vector(5, 20);
		Vector glutPointW = Utils.ScreenToWorldLoc(glutPoint);
		gl.glRasterPos2d(glutPointW.x, glutPointW.y);
		glut.glutBitmapString(font, "FPS: " + Math.round(getFPS()));
		
		glutPoint = glutPoint.Offset(0, 25);
		glutPointW = Utils.ScreenToWorldLoc(glutPoint);
		gl.glRasterPos2d(glutPointW.x, glutPointW.y);
		glut.glutBitmapString(font, "Wind speed: " + Environment.getWindSpeed() + " pixels/second");

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
				if (b.isInButton(mousePos))
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
		try {
			mouseEventsQ.put(arg0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
