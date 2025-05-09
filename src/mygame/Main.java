package mygame;
import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.event.MouseAppState;
import java.util.ArrayList;
import java.util.HashMap;
import com.simsilica.lemur.style.BaseStyles;

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
 * To create and govern the main method and application build
 */
public class Main extends SimpleApplication
{
    private WindTunnel tunnel; //Main HAS-A WindTunnel
    private Label windSpeedLabel; //Main HAS-A windSpeedLabel Label
    private Label dragForceLabel; //Main HAS-A dragForceLabel Label
    private Shape testShape; //Main HAS-A testShape Shape
    private final float xStart = -5f; //global var
    private enum ViewMode { STREAMLINES, PARTICLES}
    private ViewMode currentMode = ViewMode.STREAMLINES;
    private final Node streamlineNode = new Node("Streamlines");
    private final HashMap<Geometry, Vector3f> originalOffsets = new HashMap<>();
    private final ArrayList<Geometry> particles = new ArrayList<>();
    private final Node particleNode = new Node("Particles");
    private final int gridY = 10;
    private final int gridZ = 10;
    private final float spacing = 0.3f;
    private VersionedReference<Double> sliderRef;
    private MaterialProperty aluminum = new MaterialProperty("Aluminum", 0.47, ColorRGBA.Blue); //Main HAS-A MaterialProperty
    private MaterialProperty wood = new MaterialProperty("Wood", 0.82, ColorRGBA.Brown); //Main HAS-A MaterialProperty
    private MaterialProperty mesh = new MaterialProperty("Mesh", 1.6, ColorRGBA.Gray); //Main HAS-A MaterialProperty
    private MaterialProperty foam = new MaterialProperty("Foam", 1.2, ColorRGBA.Orange); //Main HAS-A MaterialProperty
    private MaterialProperty carbonFiber = new MaterialProperty("Carbon Fiber", 1.28, ColorRGBA.DarkGray); //Main HAS-A MaterialProperty
    
            
    public static void main(String[] args)
    {
        Main app = new Main();
        app.start();
    }
    
    @Override
    public void simpleInitApp()
    {       
        //Create the 3D sphere
        testShape = new Sphere(0.1, aluminum, assetManager);
        
        //Attach the sphere to the scene
        rootNode.attachChild(testShape.getGeometry());
        
        //Attach the mode Nodes to the scene
        rootNode.attachChild(streamlineNode);
        rootNode.attachChild(particleNode);
        
        tunnel = new WindTunnel(10); //Create WindTunnel with speed 10 m/s
        double drag = tunnel.computeDrag(testShape);
        
        //Build streamlines
        Streamline.buildStreamlines(assetManager, streamlineNode, gridY, gridZ, spacing);
        
        //Build Particles
        Particle.buildParticles(assetManager, particleNode, particles, originalOffsets, xStart, gridY, gridZ, spacing);
        
        //Add the GUI
        GuiGlobals.initialize(this);
        BaseStyles.loadGlassStyle();
        stateManager.attach(new MouseAppState(this));
        GuiGlobals.getInstance().getFocusManagerState().setEnabled(true);
        
        ControlPanelGUI gui = new ControlPanelGUI(guiNode, cam, this);
        sliderRef = gui.getSliderRef();
        windSpeedLabel = gui.getWindSpeedLabel();
        dragForceLabel = gui.getdragForceLabel();
              
        //Add lighting
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-1, -2, -3).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
        
        //Input designations
        inputManager.addMapping("ToggleView", new com.jme3.input.controls.KeyTrigger(com.jme3.input.KeyInput.KEY_SPACE));
        inputManager.addListener(toggleListener, "ToggleView");
            
        viewPort.setBackgroundColor(ColorRGBA.DarkGray);       
    }
    
    //Create the toggleListener for ViewMode enum
    private final com.jme3.input.controls.ActionListener toggleListener = new com.jme3.input.controls.ActionListener() 
    {
        @Override
        public void onAction(String name, boolean isPressed, float tpf)
        {
            if (name.equals("ToggleView") && isPressed)
            {
                if (currentMode == ViewMode.STREAMLINES)
                {
                    currentMode = ViewMode.PARTICLES;
                    streamlineNode.setCullHint(Spatial.CullHint.Always);
                    particleNode.setCullHint(Spatial.CullHint.Inherit);
                } else 
                {
                    currentMode = ViewMode.STREAMLINES;
                    particleNode.setCullHint(Spatial.CullHint.Always);
                    streamlineNode.setCullHint(Spatial.CullHint.Inherit);
                }
            }
        }
    };
        
    @Override
    public void simpleUpdate(float timePerFrame)
    {
        Vector3f objectCenter = new Vector3f(0, 0, 0);
        float objectRadius = 1f;
        
        for (Geometry p : particles)
        {
            Vector3f pos = p.getLocalTranslation();
            pos.x += timePerFrame * 2f; 
            
            if(pos.distance(objectCenter) < objectRadius + 0.5f)
            {
                pos.y += timePerFrame * 1.5f;
            }
            
            //Reset particle after passing object
            if (pos.x > 5f) 
            {
                Vector3f original = originalOffsets.get(p);
                pos.x = xStart;
                pos.y = original.y;
                pos.z = original.z;  
            }  
        p.setLocalTranslation(pos);
        }
        
        //Update for slider
        if (sliderRef != null && sliderRef.update())
        {
            double value = sliderRef.get();
            double drag = tunnel.computeDrag(testShape);
            dragForceLabel.setText(String.format("Drag Force: %.2f Newtons", drag));
            
            if (!Float.isNaN((float) value))
            {
                tunnel.setWindSpeed(value);
                windSpeedLabel.setText(String.format("Wind Speed: %.1f m/s", value));
            }
        }
            
    }
    
    /**
     * Purpose: To switch the testShape based on GUI inputs
     * @param shapeType the type of shape
     */
    public void setShape(String shapeType)
    {
        if (testShape != null)
        {
            rootNode.detachChild(testShape.getGeometry());
        }
        
        switch (shapeType)
        {
            case "Sphere" -> testShape = new Sphere(0.1, aluminum, assetManager);
            case "Cube" -> testShape = new Cube(0.1, aluminum, assetManager);
            case "FlatPlate" -> testShape = new FlatPlate(0.1, aluminum, assetManager);
            default -> System.out.println("Invalid Input");     
        }
        
        rootNode.attachChild(testShape.getGeometry());
        
        double drag = tunnel.computeDrag(testShape);
        dragForceLabel.setText(String.format("Drag Force: %.2f Newtons", drag));
    }

}
