package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;

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
 * “W3schools.Com.” W3Schools Online Web Tutorials, www.w3schools.com/java/java_lambda.asp. Accessed 9 May 2025. 
 * “JMonkeyEngine Docs.” jMonkeyEngine Docs, wiki.jmonkeyengine.org/docs/3.4/tutorials/beginner/hello_simpleapplication.html. Accessed 11 Apr. 2025.
 *  
 * Version/date: V1
 * 
 * Responsibilities of class:
 * To define all fields and methods related to the material of the different shapes
 */
public class MaterialProperty 
{
    /////Fields/////
	private final double dragCoeff; //The drag coefficient of the material
	private final String name; //The name of the material
        //A MaterialProperty HAS-A ColorRGBA
        private final ColorRGBA color; //The color of the material
	
	/////Constructors/////
	
	/**
	 * Purpose: To create a new material with its associated properties
         * @param newName, the name of the material
         * @param newDragCoeff, the drag coefficient of the material
         * @param newColor, the color of the material
	 */
	public MaterialProperty (String newName, double newDragCoeff, ColorRGBA newColor)
	{
		this.name = newName;
		this.dragCoeff = newDragCoeff;
                this.color = newColor;
	}
	
        /////Methods/////
        
        /**
         * Purpose: To build the jME Material
         * @param assetManager, the jME asset manager for the material
         * @return mat, the jME material of the MaterialProperty
         */
        public Material createJmeMaterial(AssetManager assetManager)
        {
            //This file is in the jME3-core-3.7.0-stable.jar in the /lib folder of the project and is used to render simple objects in the jMonkeyEngine
            Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md"); 
            mat.setColor("Diffuse", ColorRGBA.Blue);
            mat.setColor("Ambient", ColorRGBA.Blue);
            
            return mat;
        }
        
	/**
	 * Purpose: To get the dragCoeff of the material
	 * @return The drag coefficient of the object
	 */
	public double getDragCoeff()
	{
		return this.dragCoeff;
	}
	
	/**
	 * Purpose: To get the name of the material
	 * @return name of the material
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Purpose: To override the toString method for class MaterialProperty
	 * @return MaterialProperty summary in String format
	 */
	@Override
	public String toString()
	{
		return this.name + "has a drag coefficient of " + this.dragCoeff;
	}
	
}
