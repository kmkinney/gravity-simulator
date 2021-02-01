/*Kevin Kinney
 *Mrs. Gallatin
 *3/23/18
 */
import java.util.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class GravityComp extends JComponent 
{
	public static final double G = 1E5;
	public static final String PLANETS = "Planets", TWIN_SUNS = "Twin Suns", CUSTOM="Custom", 
			FOUR_STAR_DESIGN = "Four Star Design", FOR_G="For G";
	public static final String[] OPTIONS = {CUSTOM, PLANETS, TWIN_SUNS, 
			FOUR_STAR_DESIGN, FOR_G};
	
	private GravitySim sim;
	
	private ArrayList<Body> bodies;
	private ArrayList<Point2D> trails;
	private ArrayList<Thread> threads;
	private int numBodies;
	private boolean fixedSun, trailsOn, showVel, showAcc;
	private long speed;
	private String scenario;
	
	/**
	 * Creates a GravityComponent that controls the simulation.
	 */
	public GravityComp(GravitySim s)
	{
		sim = s;
		bodies = new ArrayList<Body>();
		threads = new ArrayList<>();
		trails = new ArrayList<>();
		numBodies = 4;
		fixedSun = true;
		trailsOn = true;
		showVel = true;
		showAcc = true;
		speed = 22;
		setScenario(PLANETS);
	}
	
/***********************SCENARIOS**********************************************/
	public void planets()
	{
		bodies.clear();
		trails.clear();
		sim.showCustom(false);
		Body bodyOne = new Body(GravitySim.BASE_WIDTH/2, GravitySim.BASE_HEIGHT/2, 50, 1000, Color.yellow);				
		bodies.add(bodyOne);
		if(numBodies>=2) {
			Body bodyTwo = new Body(GravitySim.BASE_WIDTH/4, GravitySim.BASE_HEIGHT/2, 15, .1, Color.green);
			bodies.add(bodyTwo);
			setUCMOrbit(bodyOne, bodyTwo);
		}
		if(numBodies>=3) {
			Body bodyThree = new Body(GravitySim.BASE_WIDTH/3, GravitySim.BASE_HEIGHT/2, 10, .1, Color.blue);
			bodies.add(bodyThree);
			setUCMOrbit(bodyOne, bodyThree);
		}
		if(numBodies>=4) {
			Body bodyFour = new Body(GravitySim.BASE_WIDTH/6, GravitySim.BASE_HEIGHT/2, 5, .05, Color.orange);
			bodies.add(bodyFour);
			setUCMOrbit(bodyOne, bodyFour);
		}
		for(Body b:bodies)
		{
			b.showAccVector(showAcc);
			b.showVelVector(showVel);
		}
		repaint();
	}
	public void twinSuns()
	{
		bodies.clear();
		trails.clear();
		sim.showCustom(false);
		Body bodyOne = new Body(GravitySim.BASE_WIDTH/3, GravitySim.BASE_HEIGHT/2, 50, 100, Color.YELLOW);
		Body bodyTwo = new Body(GravitySim.BASE_WIDTH/3*2, GravitySim.BASE_HEIGHT/2, 50, 100, Color.red);
      	bodyOne.setInitialVelocity(0, 100);
      	bodyTwo.setInitialVelocity(0, -100);
      
		bodies.add(bodyOne);
		bodies.add(bodyTwo);
		for(Body b:bodies)
		{
			b.showAccVector(showAcc);
			b.showVelVector(showVel);
		}
		setFixedSun(false);
		numBodies=2;
		repaint();
	}
	public void fourStar()
	{
		sim.showCustom(false);
		bodies.clear();
		trails.clear();
		setFixedSun(false);
		Body bOne = new Body(GravitySim.BASE_WIDTH/3, GravitySim.BASE_HEIGHT/3, 5, 30, Color.YELLOW);
		Body bTwo = new Body(GravitySim.BASE_WIDTH/3+300, GravitySim.BASE_HEIGHT/3, 5, 30, Color.RED);
		Body bThree = new Body(GravitySim.BASE_WIDTH/3, GravitySim.BASE_HEIGHT/3+300, 5, 30, Color.BLUE);
		Body bFour = new Body(GravitySim.BASE_WIDTH/3+300, GravitySim.BASE_HEIGHT/3+300, 5, 30, Color.GREEN);
		bodies.add(bOne);
		bodies.add(bTwo);
		bodies.add(bThree);
		bodies.add(bFour);
		
		bOne.setInitialVelocity(100, 0);
		bTwo.setInitialVelocity(0, 100);
		bThree.setInitialVelocity(0, -100);
		bFour.setInitialVelocity(-100, 0);
		numBodies=4;
		
		repaint();
	}
	public void forG()
	{
		sim.showCustom(false);
		trails.clear();
		bodies.clear();
		setFixedSun(false);
		numBodies = 14;
		
		int h = GravitySim.BASE_HEIGHT;
		int w = GravitySim.BASE_WIDTH;
		//P
		Body bOne = new Body(0, 10, 10, 5, Color.pink);
		Body bTwo = new Body(w/3, 200, 10, 20, Color.pink);
		Body bThree = new Body(w/5, h, 10, .001, Color.pink);
		bTwo.setFixed(true);
		bOne.setInitialVelocity(110, 50);
		bThree.setInitialVelocity(0,-500);
		bThree.setGroup(0);
		
		//O
		Body bFour = new Body(w/3*2-150, h/5*4, 1, 10, Color.pink);
		Body bFive = new Body(w/3*2 - 200, h/5*4, 5, 20, Color.pink);
		bFour.setGroup(2);
		bFive.setGroup(2);
		bFour.setFixed(true);
		setUCMOrbit(bFour, bFive);
		
		//r
		Body bSix = new Body(w/3, h-50, 5, 1, Color.pink);
		Body bSeven = new Body(w/3+50, h-200, 5, 100, Color.pink);
		bSix.setGroup(3);
		bSeven.setGroup(3);
		bSix.setInitialVelocity(0, -100);
		bSeven.setFixed(true);
		
		//m
		Body bEight = new Body(w/6*5-200, h, 5, 1, Color.pink);
		Body bNine = new Body(w/8*7-150, h, 5, 1, Color.pink);
		Body bTen = new Body(w/6*5-150, h-250, 10, 20, Color.pink);
		Body bEleven = new Body(w/8*7-100, h-250, 10, 20, Color.pink);
		bEight.setGroup(4);
		bNine.setGroup(5);
		bTen.setGroup(4);
		bEleven.setGroup(5);
		
		bTen.setFixed(true);
		bEleven.setFixed(true);
		bEight.setInitialVelocity(0, -100);
		bNine.setInitialVelocity(0, -100);
		
		//?
		Body bTwelve = new Body(w-150, h/4+50, 10, 1, Color.pink);
		Body bThirteen = new Body(w-100, h/3+100, 1, 10, Color.pink);
		Body bFourteen = new Body(w-100, h-50, 10, 200, Color.pink);
		
		bTwelve.setGroup(6);
		bThirteen.setGroup(6);
		bFourteen.setGroup(6);
		
		bThirteen.setFixed(true);
		bFourteen.setFixed(true);
		setUCMOrbit(bThirteen, bTwelve);
		
		bodies.add(bOne);
		bodies.add(bTwo);
		bodies.add(bThree);
		bodies.add(bFour);
		bodies.add(bFive);
		bodies.add(bSix);
		bodies.add(bSeven);
		bodies.add(bEight);
		bodies.add(bNine);
		bodies.add(bTen);
		bodies.add(bEleven);
		bodies.add(bTwelve);
		bodies.add(bThirteen);
		bodies.add(bFourteen);
		
		
		repaint();
	}
	/**
	 * Called when scenario set to custom;
	 */
	public void custom()
	{
		planets();
		sim.showCustom(true);
	}
	/**
	 * Applies custom changes
	 * @param list list of changes stored as bodies.
	 */
	public void applyCustom(ArrayList<Body> list)
	{
		bodies=list;
		reset();
		repaint();
	}
	/**
	 * Returns an ArrayList of bodies.
	 * @return an ArrayList of bodies.
	 */
	public ArrayList<Body> getBodies()
	{
		return bodies;
	}
	/**
	 * Paints the window.
	 * @param g the Graphics object.
	 */
	public void paintComponent(Graphics g)
	{
		int w = (int)g.getClipBounds().getWidth();
		int h = (int)g.getClipBounds().getHeight();
		g.setColor(Color.black);
		g.fillRect(0,0,w,h);
		
		g.setColor(Color.red);
		for(Point2D pt:trails) {
			if(trailsOn) {
				g.fillOval((int)pt.getX()-2, (int)pt.getY()-2, 5, 5);
			}
		}
		for(Body b:bodies) {
			b.draw(g);
			if(trailsOn){
				trails.add(b.getCenter());
			}
		}
	}
	
	/**
	 * Starts animation
	 */
	public void start()
	{
		for(Thread t:threads)
			t.interrupt();
		threads.clear();
		if(!trailsOn)
			trails.clear();
		for(Body b:bodies) {
			if(!b.isFixed() && (!fixedSun || b!=bodies.get(0))) {
				Thread t = new Thread(new BodyRunner(b, this));
				t.start();
				threads.add(t);
			}
		}
	}
	/**
	 * Stops animation
	 */
	public void stop()
	{
		for(Thread t:threads){
			t.interrupt();
		}
		threads.clear();
	}
	/**
	 * Resets each body to its initial state and stops the animation.
	 */
	public void reset()
	{
		stop();
		trails.clear();
		threads.clear();
		for(Body b:bodies)
			b.reset();
		repaint();
	}
	/**
	 * Shows a SettingsWindow to allow user manipulation of the simulation. Also stops animation.
	 */
	public void settings()
	{
		stop();
		new SettingsWindow(this);
	}
	/**
	 * Shows a ConfigureWindow to allow user manipulation of the simulation. Also stops animation.
	 */
	public void configure()
	{
		stop();
		new ConfigureWindow(this, sim);
	}
	
/******************************************GETTERS AND SETTERS**************************************/
	/**
	 * Sets the number of bodies in the simulation to the given integer 1-4.
	 * @param n the number of bodies.
	 */
	public void setNumBodies(int n)
	{
		numBodies = n;
		setScenario(scenario);
	}
	/**
	 * Returns number of bodies.
	 * @return number of bodies.
	 */
	public int getNumBodies()
	{
		return numBodies;
	}
	/**
	 * Sets the speed to the given value
	 * @param d the new speed.
	 */
	public void setSpeed(int d)
	{
		speed = (long)d;
	}
	/**
	 * Returns speed.
	 * @return speed.
	 */
	public long getSpeed()
	{
		return speed;
	}
	/**
	 * Sets whether to show trails to the given boolean value.
	 * @param showTrails whether or not to show trails.
	 */
	public void setTrailsOn(boolean showTrails)
	{
		trailsOn = showTrails;
		repaint();
	}
	/**
	 * Returns whether trails are on.
	 * @return whether trails are on.
	 */
	public boolean getTrailsOn()
	{
		return trailsOn;
	}
	/**
	 * Sets whether to fix sun in place to the given boolean value.
	 * @param fixSun whether or not to fix sun in place.
	 */
	public void setFixedSun(boolean fixSun)
	{
		fixedSun = fixSun;
	}
	/**
	 * Returns whether sun is fixed.
	 * @return whether sun is fixed.
	 */
	public boolean getFixedSun()
	{
		return fixedSun;
	}
	/**
	 * Sets whether to show velocity vector to the given boolean value.
	 * @param vel whether or not to show velocity vector.
	 */
	public void setShowVel(boolean vel)
	{
		showVel = vel;
		for(Body b:bodies)
			b.showVelVector(vel);
		repaint();
	}
	/**
	 * Returns whether velocity is shown.
	 * @return whether velocity is shown.
	 */
	public boolean getShowVel()
	{
		return showVel;
	}
	/**
	 * Sets whether to show acceleration vector to the given boolean value.
	 * @param acc whether or not to show acceleration vector.
	 */
	public void setShowAcc(boolean acc) 
	{
		showAcc = acc;
		for(Body b:bodies)
			b.showAccVector(acc);
		repaint();
	}
	/**
	 * Returns whether acceleration is shown.
	 * @return whether acceleration is shown.
	 */
	public boolean getShowAcc()
	{
		return showAcc;
	}
	/**
	 * Sets the scenario to the given string (use class constants).
	 * @param s the new scenario.
	 */
	public void setScenario(String s)
	{
		scenario=s;
		if(sim.isCustomScenario(s)) {
			ArrayList<Body> dat = sim.getCustomScenarioData(s); 
			numBodies = dat.size();
			applyCustom(dat);
			sim.showCustom(true);
		}
		switch(s) {
			case PLANETS:
				planets();
				break;
			case TWIN_SUNS:
				twinSuns();
				break;
			case CUSTOM:
				custom();
				break;
			case FOUR_STAR_DESIGN:
				fourStar();
				break;
			case FOR_G:
				forG();
				break;
		}
	}
	/**
	 * Returns the current scenario.
	 * @return the current scenario.
	 */
	public String getScenario()
	{
		return scenario;
	}
/**********************Physics Util Methods***************************/
	
	/**
	 * Sets required velocity for planet to orbit sun in circular path
	 * @param sun the Body to be orbited about.
	 * @param planet the Body that will orbit.
	 */
	public void setUCMOrbit(Body sun, Body planet)
	{
		Point2D one = sun.getCenter();
		Point2D two = planet.getCenter();
		
		double r = distance(sun, planet);
		double dx = two.getX() - one.getX();
		double dy = two.getY() - one.getY();
		double theta = Math.atan(Math.abs(dy)/Math.abs(dx));
		double v = Math.sqrt(G*sun.getMass()/r);
		double vx = Math.sin(theta)*v;
		
		if(dx>0)
			vx*=-1;
		double vy = Math.cos(theta)*v;
		if(dy<0)
			vy*=-1;
			
		planet.setInitialVelocity(vx, vy);
	}
	/**
	 * Returns the net force on the given Body by all other bodies as a vector component
	 * @param b the Body being acted on.
	 * @return the net force vector component as a double array with index 0 being x annd index 1 being y.
	 */
	public double[] netForce(Body b)
	{
		double[] nF = new double[2];
		for(Body bod:bodies)
		{
			if(bod!=null && bod!=b && b.getGroup()==bod.getGroup()){
				double[] f = forceComponent(b, bod);
				nF[0]+=f[0];
				nF[1]+=f[1];
			}
		}
		return nF;
	}
	/**
	 * Returns the gravitational force on bOne by bTwo.
	 * @param bOne the Body acted upon
	 * @param bTwo the Body acting on the other.
	 * @return the force from bTwo on bOne as a vector component.
	 */
	public double[] forceComponent(Body bOne, Body bTwo)
	{
		Point2D one = bOne.getCenter();
		Point2D two = bTwo.getCenter();
		
		double r = distance(bOne, bTwo);
		double dx = two.getX() - one.getX();
		double dy = two.getY() - one.getY();
		double theta = Math.atan(Math.abs(dy)/Math.abs(dx));
		double fG = G*(bOne.getMass()*bTwo.getMass())/(r*r);
		double[] fGComp = new double[2];
		
		//x comp
		fGComp[0] = Math.cos(theta)*fG;
		if(dx<0)
			fGComp[0]*=-1;
		//y comp
		fGComp[1] = Math.sin(theta)*fG;
		if(dy<0)
			fGComp[1]*=-1;
		
		return fGComp;
	}
	/**
	 * Returns the distance between the centers of the two bodies.
	 * @param bOne the first Body
	 * @param bTwo the second Body
	 * @return the distance between the centers of the two bodies.
	 */
	public double distance(Body bOne, Body bTwo)
	{
		return bOne.getCenter().distance(bTwo.getCenter());
	}
	public static void main(String[] args)
	{
		new GravitySim();
	}
}