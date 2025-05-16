package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import java.util.ArrayList;
import java.util.HashMap;

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
 * To define all logic regarding particles in the simulation
 */
public class Particle
{
    /////Fields/////
    //Particle HAS-A Geometry
    private final Geometry geo; //The geometry of the Particle
	
    /////Constructor/////
    /**
     * Purpose: To create a particle with an x,y position and velocity
     * @param assetManager, the asset manager of the program
     * @param startPos, the start position of the object
     */
    public Particle(AssetManager assetManager, Vector3f startPos)
    {
        Sphere mesh = new Sphere(6, 6, 0.05f); //Particle IS-A Sphere
        geo = new Geometry("Particle", mesh);
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        geo.setMaterial(mat);
        geo.setLocalTranslation(startPos);
    }
	
    /////Methods/////
        
    /**
     * Purpose: To get the geometry of the Particle
     * @return Geometry, the geometry of the particle
     */
    public Geometry getGeometry()
    {
        return geo;
    }
        
    /**
     * Purpose: To get the vector of the Particle
     * @return Vector3f, the vector of the Particle
     */
    public Vector3f getPosition()
    {
        return geo.getLocalTranslation();
    }
      
    /**
     * Purpose: To set the vector of the Particle
     * @param pos, the vector to set the Particle with
     */
    public void setPosition(Vector3f pos)
    {
        geo.setLocalTranslation(pos);
    }
    
    /**
     * Purpose: To update the particles x position
     * @param windSpeed
     * @param timeStep
     */
    public void updateParticleX(double windSpeed, double timeStep)
    {
        Vector3f pos = geo.getLocalTranslation();
        pos.x += windSpeed * timeStep;
        geo.setLocalTranslation(pos);
    }
    
    /**
     * Purpose: To create the visuals for the particle ViewMode
     * @param assetManager
     * @param particleNode
     * @param particles
     * @param originalOffsets
     */
    public static void buildParticles(AssetManager assetManager, Node particleNode, ArrayList<Geometry> particles, HashMap<Geometry, Vector3f> originalOffsets, float xStart, int gridY, int gridZ, float spacing)
    {
        //Create the particle stream
        for (int y = -gridY / 2; y < gridY / 2; y++ )
        {
            for (int z = -gridZ / 2; z < gridZ / 2; z++)
            {
                Vector3f startPos = new Vector3f(xStart, y * spacing, z * spacing); //set starting position of particle
                Particle particle = new Particle(assetManager, startPos); //create new particle
                
                originalOffsets.put(particle.getGeometry(), startPos); //store particle in hash map
                particles.add(particle.getGeometry()); //add particle to array list
                particleNode.attachChild(particle.getGeometry()); //attach particle to the particle node
                particleNode.setCullHint(Spatial.CullHint.Always); //show the particle
            }
        }
    }
    
}
