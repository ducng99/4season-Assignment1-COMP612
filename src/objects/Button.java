package objects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import main.Utils;
import main.Vector;
import shapes.Polygon;

public class Button extends Particle {
	public static ArrayList<Button> buttons = new ArrayList<>();
	
	private int width = 0;
	private int height = 0;
	private double[] rgba;
	private final Method action;

	public Button(int x, int y, int width, int height, double[] rgba, Method action) {
		super(x, y);
		setWidth(width);
		setHeight(height);
		this.rgba = rgba;
		buttons.add(this);
		this.action = action;
	}

	@Override
	public void draw(GL2 gl) {
		Vector[] verticies = new Vector[]
		{
			Utils.ScreenToWorldLoc(Position),
			Utils.ScreenToWorldLoc(Position.Offset(width)),
			Utils.ScreenToWorldLoc(Position.Offset(width, height)),
			Utils.ScreenToWorldLoc(Position.Offset(0, height))
		};
		
		Polygon.drawFill(gl, verticies, rgba);
	}
	
	public void DoAction()
	{
		try {
			action.invoke(this);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
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
