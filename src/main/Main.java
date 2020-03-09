package main;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;

import objects.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Main implements GLEventListener {
	public static final JFrame frame = new JFrame("Hello world");
	private static FPSAnimator animator = null;
	
	private static ArrayList<Particle> particles = new ArrayList<>();
	
	private int displayList;

	@Override
	public void display(GLAutoDrawable gld) {
		final GL2 gl = gld.getGL().getGL2();
		
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		
		gl.glCallList(displayList);
		gl.glFlush();
	}

	@Override
	public void dispose(GLAutoDrawable gld) {
	}

	@Override
	public void init(GLAutoDrawable gld) {
		final GL2 gl = gld.getGL().getGL2();
		
		displayList = gl.glGenLists(1);
		
		gl.glNewList(displayList, GL2.GL_COMPILE);
		
		//Background - Sky
		Vector[] verticies = { new Vector(-1, 1)};
		
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3d(0.8, 0.8, 0.8);
		gl.glVertex2d(-1, 1);
		gl.glColor3d(1, 0.9, 1);
		gl.glVertex2d(1, 1);
		gl.glColor3d(0.8, 0.8, 0.8);
		gl.glVertex2d(1, -1);
		gl.glVertex2d(-1, -1);
		gl.glEnd();

		Land land = new Land();
		particles.add(land);
		
		GenerateTrees(20);
		
		for (Particle p : particles)
		{
			p.draw(gl);
		}
		
		gl.glEndList();
	}

	@Override
	public void reshape(GLAutoDrawable gld, int x, int y, int width, int height) {	}

	
	public static void main(String[] args)
	{		
		GLProfile glProfile = GLProfile.get(GLProfile.GL2);
		GLCapabilities glCapabilities = new GLCapabilities(glProfile);
		
		GLCanvas canvas = new GLCanvas();
		canvas.addGLEventListener(new Main());
		
		frame.add(canvas);
		frame.setSize(800, 600);
		frame.setResizable(false);
		
		animator = new FPSAnimator(60);
		animator.add(canvas);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				animator.stop();
				System.exit(0);
			}
		});
		
		frame.setVisible(true);
		
		animator.start();
	}
	
	public static void GenerateTrees(int numTrees)
	{		
		for (int i = 0; i < numTrees; i++)
		{
			particles.add(new Tree(Utils.genRand(10, frame.getSize().width), 370 + i * (int)Math.round(80.0 / (double)numTrees), 50 + i * 2));
		}
	}
}
