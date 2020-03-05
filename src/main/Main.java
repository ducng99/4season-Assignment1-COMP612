package main;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

import objects.Particle;
import objects.Tree;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Main implements GLEventListener {
	public static final JFrame frame = new JFrame("Hello world");
	private static Animator animator = null;
	
	private static ArrayList<Particle> particles = new ArrayList<>();

	@Override
	public void display(GLAutoDrawable gld) {
		final GL2 gl = gld.getGL().getGL2();
		
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		
		//Background - Sky
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3d(0.99, 0.99, 0.99);
		gl.glVertex2d(-1, 1);
		gl.glVertex2d(1, 1);
		gl.glVertex2d(1, -1);
		gl.glVertex2d(-1, -1);
		gl.glEnd();
		
		for (Particle p : particles)
		{
			p.draw(gl);
		}
		
		gl.glFlush();
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {

	}

	@Override
	public void init(GLAutoDrawable arg0) {

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
		
		animator = new Animator(canvas);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				animator.stop();
				System.exit(0);
			}
		});
		
		Tree tree = new Tree(200, 400, 50);
		Tree tree1 = new Tree(400, 450, 50);
		Tree tree2 = new Tree(550, 350, 50);
		particles.add(tree);
		particles.add(tree1);
		particles.add(tree2);
		
		frame.setVisible(true);
		
		animator.start();
	}
}
