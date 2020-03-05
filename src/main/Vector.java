package main;

/**
 * Custom Vector class contains x, y, z variables for easy access
 * @author Duc Nguyen
 *
 */
public class Vector {
	public double x;
	public double y;
	public double z;
	
	public Vector()
	{
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public Vector(double x, double y)
	{
		this.x = x;
		this.y = y;
		this.z = 0;
	}
	
	public Vector(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector Offset(Vector a)
	{
		return new Vector(this.x + a.x, this.y + a.y, this.z + a.z);
	}
	
	public Vector Offset(double a)
	{
		return new Vector(this.x + a, this.y, this.z);
	}
	
	public Vector Offset(double a, double b)
	{
		return new Vector(this.x + a, this.y + b, this.z);
	}
	
	public Vector Offset(double a, double b, double c)
	{
		return new Vector(this.x + a, this.y + b, this.z + c);
	}
}
