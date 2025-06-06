package mygame;

import com.jme3.scene.Geometry;

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
 * To define logic regarding shapes in the simulation
 */
public abstract class Shape 
{
	/////Fields/////
	private final double xArea; //The cross sectional area of the shape
	//A Shape HAS-A MaterialProperty
	private MaterialProperty material; //The material property of the shape
        //Shape HAS-A Geometry
        private Geometry geometry; //The geometry of the shape
        
        private String shapeType; //The type of shape
	
	/////Constructors/////
	/**
	 * Purpose: To define the abstract constructor for shape 
	 * @param newXArea. the cross sectional area of the shape
	 * @param newMaterial, the MaterialProperty of the shape
         * @param newShapeType, the type of shape being created
	 */
	public Shape (String newShapeType, double newXArea, MaterialProperty newMaterial)
	{
		this.xArea = newXArea;
		this.material = newMaterial;
                this.shapeType = newShapeType;
	}
	
	/////Methods/////
	/**
	 * Purpose: To compute the drag force of the shape
	 * @param windSpeed, the windSpeed of the tunnel
	 * @param airDensity, the airDensity in the tunnel
	 * @return double, the drag force of the shape
	 */
	public double computeDragForce(double windSpeed, double airDensity)
	{
		return 0.5 * this.material.getDragCoeff() * airDensity * windSpeed * xArea;
	}
        
        /**
         * Purpose: To get the geometry of the shape for 3-d rendering purposes
         * @return the geometry of the shape
         */
        public Geometry getGeometry()
        {
            return this.geometry;
        }
	
	/**
	 * Purpose: To get the material of the shape
	 * @return MaterialProperty, the MaterialProperty of the Shape
	 */
	public MaterialProperty getMaterial()
	{
		return this.material;
	}
        
        /**
         * Purpose: To create an abstract method to get the shapeType of subclasses
         */
        public String getShapeType()
        {
            return shapeType;
        }
        
        /**
         * Purpose: To set the type of shape
         */
        public void setShapeType(String newShapeType)
        {
            this.shapeType = newShapeType;
        }
        
        /**
         * Purpose" To set the geometry of the shape
         */
        public void setGeometry(Geometry geo)
        {
            this.geometry = geo;
        }
        
        /**
         * Purpose: To set the material of the object
         */
        public void setMaterial(MaterialProperty mat)
        {
            this.material = mat;
        }
}
