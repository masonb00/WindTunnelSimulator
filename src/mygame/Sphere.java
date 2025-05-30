package mygame;

import com.jme3.scene.Geometry;
import com.jme3.asset.AssetManager;
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
 * To define all logic regarding spheres as a subclass of shape
 */
//A Sphere IS-A Shape
public class Sphere extends Shape 
{
	/////Fields/////
	
	
	/////Constructors/////
	/**
	 * Purpose: To create a Sphere
	 * @param newXArea, the cross sectional area of the sphere
	 * @param newMaterial, the material property of the sphere
         * @param assetManager, the asset manager for the sphere
	 */
	public Sphere (double newXArea, MaterialProperty newMaterial, AssetManager assetManager)
	{
            super("Sphere", newXArea, newMaterial);
            
            //Build the 3-D Shape
            com.jme3.scene.shape.Sphere mesh = new com.jme3.scene.shape.Sphere(32, 32, 1f);
            this.setGeometry(new Geometry("SphereObject", mesh));
            
            this.getGeometry().setMaterial(this.getMaterial().createJmeMaterial(assetManager));
            this.getGeometry().setLocalTranslation(0, 0, 0);
	}
	
	
	/**
	 * Purpose: To define a toString method for type Sphere
	 */
        @Override
	public String toString()
	{
		return getShapeType() + " with " + getMaterial().getName();
	}
	
	
	
}
