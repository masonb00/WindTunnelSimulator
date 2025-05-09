package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Line;

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
 * To define all logic regarding streamlines in the simulation
 */
public class Streamline
{
    /////Feilds/////
    private final Geometry lineGeom; //Streamline HAS-A Geometry
    
    /////Constructors/////
    /**
     * Purpose: To create a Streamline with a start and end position
     * @param assetManager, the asset manager of the program
     * @param startPos, the start position of the object
     * @param endPos , the end position of the object
     */
    public Streamline(AssetManager assetManager, Vector3f startPos, Vector3f endPos)
    {
        Line streamline = new Line(startPos, endPos); //Streamline IS-A line
        lineGeom = new Geometry("Streamline", streamline);
                
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Cyan);
        lineGeom.setMaterial(mat);
    }
    
    /////Methods/////
    
    /**
     * Purpose: To get the geometry of the Streamline
     * @return lineGeom , the geometry of the streamline
     */
    public Geometry getGeometry()
    {
        return lineGeom;
    }
    
    /**
     * Purpose: To create a Streamline
     */
    public static void buildStreamlines(AssetManager assetManager, Node streamlineNode,int gridY, int gridZ, float spacing)
    {
        for (int y = -gridY / 2; y < gridY / 2; y++)
        {
            for (int z = -gridZ / 2; z < gridZ / 2; z++)
            {
                Vector3f start = new Vector3f(-5f, y * spacing, z * spacing);
                Vector3f end = new Vector3f(5f, y * spacing, z * spacing);
                
                Streamline streamline = new Streamline(assetManager, start, end);
                
                streamlineNode.attachChild(streamline.getGeometry());
            }
        }
    }
}
