package mygame;
import java.util.ArrayList;

/**
 * Lead Author(s):
 * @author Mason Boelter
 * @author 
 * <<add additional lead authors here, with a full first and last name>>
 * 
 * Other contributors:
 * <<add additional contributors (mentors, tutors, friends) here, with contact information>>
 * 
 * References:
 * “JMonkeyEngine Docs.” jMonkeyEngine Docs, wiki.jmonkeyengine.org/docs/3.4/tutorials/beginner/hello_simpleapplication.html. Accessed 11 Apr. 2025.
 * “W3schools.Com.” W3Schools Online Web Tutorials, www.w3schools.com/java/java_lambda.asp. Accessed 9 May 2025.
 *  
 * Version/date: V1
 * 
 * Responsibilities of class:
 * To define all fields and methods needed for WindTunnel related logic
 */
public class WindTunnel 
{
	/////Fields/////
	private double windSpeed; //The windspeed of the environment in m/s
	private double airDensity = 1.225; //The air density of the environment in kg/m^3
	//A WindTunnel HAS-A Shape
	private Shape shape; //The shape in the WindTunnel
	//A WindTunnel HAS-MANY Particles
	private final ArrayList<Particle> particles; //The particles in the WindTunnel
	
	/////Constructors/////
	/**
	 * Purpose: To create a default constructor with airDensity set to sea level
	 * @param newWindSpeed, the windSpeed in the tunnel
	 */
	public WindTunnel (double newWindSpeed, Shape testShape)
	{
		this.windSpeed = newWindSpeed;
		this.particles = new ArrayList<>();
                this.shape = testShape;
	}
	
	/**
	 * Purpose: To create the WindTunnel with a custom windSpeed and airDensity
	 * @param newWindSpeed, the windSpeed in the tunnel
	 * @param newAirDensity, the airDensity in the tunnel
	 */
	public WindTunnel (double newWindSpeed, double newAirDensity)
	{
		this.windSpeed = newWindSpeed;
		this.airDensity = newAirDensity;
		this.particles = new ArrayList<>();
	}
	
	/////Methods/////
	/**
	 * Purpose: To update the position of all particles in the tunnel
	 * @param timeStep, the timeStep of the simulation
	 */
	public void updateParticles(double timeStep)
	{
		for (Particle p : particles) //Iterate through particles
		{
                    p.updateParticleX(windSpeed, timeStep); //Update the x position of the particles based on windSpeed and timeStep
		}
	}
	
	/**
	 * Purpose: To have call Shape.computeDragForce using WindTunnel parameters
	 * @param shape, the shape to compute the drag force
         * @return dragForce, the drag force of the object
	 */
	public double computeDrag(Shape shape)
	{
            return shape.computeDragForce(windSpeed, airDensity);
	}
	
	
	/**
	 * Purpose: To set wind speed
         * @param double new wind speed
	 */
	public void setWindSpeed(double newWindSpeed)
        {
            this.windSpeed = newWindSpeed;
        }
        
        /**
         * Purpose: To get the wind Speed in the tunnel
         */
        public double getWindSpeed()
        {
            return windSpeed;
        }
        
        /**
         * Purpose: To get the shape in the wind tunnel
         */
        public Shape getShape()
        {
            return shape;
        }
	
}
