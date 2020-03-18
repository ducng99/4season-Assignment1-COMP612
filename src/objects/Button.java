package objects;

import java.util.ArrayList;
import java.util.HashMap;

import com.jogamp.opengl.GL2;

import main.Main;
import main.Utils;
import main.Vector;
import shapes.Polygon;

public class Button extends Particle {
	public static ArrayList<Button> buttons = new ArrayList<>();
	public static HashMap<Runnable, Button> linkedButton = new HashMap<>();
	
	private int width = 0;
	private int height = 0;
	private double[] rgba;
	private String text;
	private final Runnable action;

	public Button(int x, int y, int width, int height, String text, double[] rgba, Runnable action) {
		super(x, y);
		setWidth(width);
		setHeight(height);
		this.text = text;
		this.rgba = rgba;
		buttons.add(this);
		this.action = action;
		linkedButton.put(action, this);
	}

	@Override
	public void draw(GL2 gl) {
		Vector[] verticies = new Vector[]
		{
			Utils.ScreenToWorldLoc(Position),
			Utils.ScreenToWorldLoc(Position.Offset(getWidth())),
			Utils.ScreenToWorldLoc(Position.Offset(getWidth(), getHeight())),
			Utils.ScreenToWorldLoc(Position.Offset(0, getHeight()))
		};
		
		Polygon.drawFill(gl, verticies, rgba);
		
		gl.glColor3d(1, 1, 1);
		Vector textLoc = Utils.ScreenToWorldLoc(Position.Offset(10, 20));
		gl.glRasterPos2d(textLoc.x, textLoc.y);
		Main.glut.glutBitmapString(Main.font, text);
	}
	
	public void DoAction()
	{
		action.run();
	}
	
	public void SetText(String text)
	{
		this.text = text;
	}
	
	public void SetColour(double[] colour)
	{
		this.rgba = colour;
	}
	
	public boolean isInButton(Vector pos)
	{
		if (pos.x >= Position.x && pos.x <= Position.x + getWidth() && pos.y >= Position.y && pos.y <= Position.y + getHeight())
		{
			return true;
		}
		
		return false;
	}
	
	public static void DrawAllButton(GL2 gl)
	{
		for (Button b : buttons)
		{
			b.draw(gl);
		}
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		if (width > 0)
			this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		if (height > 0)
			this.height = height;
	}

}
