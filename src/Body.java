/*Kevin Kinney
 *Mrs. Gallatin
 *3/23/18
 */
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import java.io.Serializable;

public class Body implements Serializable
{
	
	private double x, y, radius, mass;
	private Color color;
	private double ax, ay, vx, vy;
	private double vx0, vy0, x0, y0;
	private boolean aVector, vVector, fixed;
	private int group;
	
	public Body(double xCenter, double yCenter, double r, double m, Color c)
	{
		group = 1;
		x = xCenter;
		x0 = xCenter;
		y = yCenter;
		y0 = yCenter;
		mass = m;
		radius = r;
		color = c;
		ax = 0;
		ay = 0;
		vx0 = 0;
		vx = 0;
		vy0 = 0;
		vy = 0;
		aVector = true;
		vVector = true;
		fixed = false;
	}
	public void move(double dx, double dy)
	{
		x+=dx;
		y+=dy;
	}
	public void draw(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(color);
		g2.fill(new Ellipse2D.Double(x-radius, y-radius, radius*2, radius*2));
		if(aVector)
			g2.draw(new Line2D.Double(x, y, x+ax/10, y+ay/10));
		if(vVector)
			g2.draw(new Line2D.Double(x, y, x+vx/10, y+vy/10));
		
	}
	public void setA(double fx, double fy)
	{
		ax = fx/mass;
		ay = fy/mass;
	}
	public void setInitialVelocity(double velX, double velY)
	{
		vx0=vx=velX;
		vy0=vy=velY;
	}
	public void setVel(double velX, double velY)
	{
		vx = velX;
		vy = velY;
	}
	public void showAccVector(boolean show)
	{
		aVector = show;
	}
	public void showVelVector(boolean show)
	{
		vVector = show;
	}
	public void setInitPos(double xCenter, double yCenter)
	{
		x0 = xCenter;
		y0 = yCenter;
	} 
	public Point2D getCenter()
	{
		return new Point2D.Double(x, y);
	}
	public void setFixed(boolean flag)
	{
		fixed = flag;
	}
	public boolean isFixed()
	{
		return fixed;
	}
	public void setGroup(int g)
	{
		group = g;
	}
	public int getGroup()
	{
		return group;
	}
	public void reset()
	{
		x = x0;
		y = y0;
		vx = vx0;
		vy = vy0;
		ax = 0;
		ay = 0;
	}
	public void copy(Body other)
	{
		x = other.x;
		y = other.y;
		x0 = other.x0;
		y0 = other.y0;
		radius = other.radius;
		color = other.color;
		mass = other.mass;
		vx = other.vx;
		vy = other.vy;
		vx0 = other.vx0;
		vy0 = other.vy0;
	}
	
	public double getX(){return x;}
	public double getY(){return y;}
	public double getRadius(){return radius;}
	public double getVX(){return vx;}
	public double getVY(){return vy;}
	public double getMass(){return mass;}
	public Color getColor(){return color;}
	public void setX(double xPos){x = xPos;}
	public void setY(double yPos){y = yPos;}
	public void setRadius(double r){radius = r;}
	public void setMass(double m){mass = m;}
	public void setColor(Color c){color = c;}
}