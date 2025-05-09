package mygame;

import com.jme3.asset.AssetManager;
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
 * To define all logic regarding cube as a subclass of shape
 */
//A Cube IS-A Shape
public class Cube extends Shape
{
    /////Fields/////
	private final String shapeType = "Box"; //The type of shape
	
	/////Constructors/////
	/**
	 * Purpose: To create a Box
	 * @param newXArea, the cross sectional area of the Box
	 * @param newMaterial, the material property of the Box
         * @param assetManager, the asset manager for the scene
	 */
	public Cube (double newXArea, MaterialProperty newMaterial, AssetManager assetManager)
	{
            super(newXArea, newMaterial);
            
            //Build the 3-D Shape
            com.jme3.scene.shape.Box mesh = new com.jme3.scene.shape.Box(1f, 1f, 1f);
            this.geometry = new Geometry("CubeObject", mesh);
            
            this.geometry.setMaterial(material.createJmeMaterial(assetManager));
            this.geometry.setLocalTranslation(0, 0, 0);
	}
	
	/////Methods/////
	/**
	 * Purpose: To get the type of shape
	 * @return String, the type of shape
	 */
	public String getShapeType()
	{
		return shapeType;
	}
	
	/**
	 * Purpose: To define a toString method for type cube
	 */
        @Override
	public String toString()
	{
		return shapeType + " with " + getMaterial().getName();
	}
}
