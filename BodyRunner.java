/*Kevin Kinney
 *Mrs. Gallatin
 *3/23/18
 */
/*************************BODY RUNNER*******************************/
import java.util.*;
/**
 * BodyRunner implements Runnable and controls Body animation.
 */	
public class BodyRunner implements Runnable
{
	public static final int MAX_SPEED = 25;
	private Body b;
	private GravityComp sim;
	private double t, dt, velX, velY;
	/**
	 * Constructs BodyRunner with given Body object
	 * @param body the Body object
	 */
	public BodyRunner(Body body, GravityComp comp)
	{
		b = body;
		velX = b.getVX();
		velY = b.getVY();
		t = 0;
		dt= .001;
		sim = comp;
	}
	/**
	 * Called by Thread and controls animation until Interrupted.
	 */ 
	public void run()
	{
		boolean stopped = false;
		while(!Thread.interrupted() && !stopped){
			double[] force = sim.netForce(b);
			double ax = force[0]/b.getMass();
			double ay = force[1]/b.getMass();
			
			velX+=ax*dt;
			velY+=ay*dt;
			b.move(velX*dt, velY*dt);
			b.setA(force[0], force[1]);
			b.setVel(velX, velY);
			t+=dt;
			sim.repaint();
			try{
				Thread.sleep(MAX_SPEED - sim.getSpeed());
			}catch(Exception e){
				stopped = true;
			}
		}
	}
}
