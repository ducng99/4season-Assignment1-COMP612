package main;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

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

	@Override
	public void display(GLAutoDrawable gld) {
		final GL2 gl = gld.getGL().getGL2();
		
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		
		gl.glCallList(displayList);
		
		if (System.currentTimeMillis() - prevTick > 50)
		{
			GenerateSnow(200, 1);
			prevTick = System.currentTimeMillis();
		}
		
		Snow.DrawAllSnow(gl);
		
		gl.glFlush();
	}

	@Override
	public void dispose(GLAutoDrawable gld) {
	}

	@Override
	public void init(GLAutoDrawable gld) {
		final GL2 gl = gld.getGL().getGL2();

		// Enable transparent
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL2.GL_BLEND);
		
		// Initialize list of static objects
		displayList = gl.glGenLists(1);

		gl.glNewList(displayList, GL2.GL_COMPILE);
		
		//Background - Sky
		Vector[] verticies = { new Vector(-1, 1), new Vector(1, 1), new Vector(1, -1), new Vector(-1, -1)};
		double[][] colours = {{0.8, 0.8, 0.8, 1}, {1, 0.9, 1, 1}, {0.8, 0.8, 0.8, 1}, {0.8, 0.8, 0.8, 1}};
		
		Polygon.drawFill(gl, verticies, colours);

		Land land = new Land();
		staticParticles.add(land);
		
		GenerateTrees(10);
		
		for (Particle p : staticParticles)
		{
			p.draw(gl);
		}
		
		gl.glEndList();
	}

	@Override
	public void reshape(GLAutoDrawable gld, int x, int y, int width, int height) {
		display(gld);
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
		
		animator = new FPSAnimator(144);	// How do I turn off v-sync?
		animator.setUpdateFPSFrames(3, null);
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
			staticParticles.add(new Tree(Utils.genRand(10, frame.getSize().width), frame.getSize().height / 5 * 3 + i * (int)Math.round(80.0 / (double)numTrees), 50 + i * 2));
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
			for (int i = 0; i < maxNumSnow - currentNoSnow && i < noSnowToAdd; i++)
			{
				double fallSpeed = Utils.genRand(20, 70);
				Snow.snowParticles.add(new Snow(Utils.genRand(0, frame.getSize().width), Utils.genRand(-frame.getSize().height / 4, 0), Utils.genRand(3, 5), fallSpeed));
			}
		}
		else
		{
			int i = 0;
			for (Snow s : Snow.snowParticles)
			{
				if (s.isDead && i < noSnowToAdd)
				{
					s.UpdatePos(new Vector(Utils.genRand(0, frame.getSize().width), Utils.genRand(-frame.getSize().height / 4, 0)));
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
