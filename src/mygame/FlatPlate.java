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
 * To define all logic regarding FlatPlates as a subclass of shape
 */
//A FlatPlate IS-A Shape
public class FlatPlate extends Shape
{
    /////Fields/////
	private final String shapeType = "FlatPlate"; //The type of shape
	
	/////Constructors/////
	/**
	 * Purpose: To create a flat plate
	 * @param newXArea, the cross sectional area of the flat plate
	 * @param newMaterial, the material property of the flat plate
         * @param assetManager, the asset manager for the flat plate
	 */
	public FlatPlate (double newXArea, MaterialProperty newMaterial, AssetManager assetManager)
	{
            super(newXArea, newMaterial);
            
            //Build the 3-D Shape
            com.jme3.scene.shape.Box mesh = new com.jme3.scene.shape.Box(1f, 0.05f, 1f);
            this.setGeometry(new Geometry("FlatPlateObject", mesh));
            
            this.getGeometry().setMaterial(this.getMaterial().createJmeMaterial(assetManager));
            this.getGeometry().setLocalTranslation(0, 0, 0);
	}
	
	/////Methods/////
	/**
	 * Purpose: To get the type of shape
	 * @return String, the type of shape
	 */
        @Override
	public String getShapeType()
	{
		return shapeType;
	}
	
	/**
	 * Purpose: To define a toString method for type falt plate
	 */
        @Override
	public String toString()
	{
		return shapeType + " with " + getMaterial().getName();
	}
}
