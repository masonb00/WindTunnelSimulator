package mygame;
import com.jme3.app.SimpleApplication;
import com.jme3.input.MouseInput;
import com.jme3.input.RawInputListener;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.event.*;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;
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
 * Buba, et al. “Rotation around Object.” jMonkeyEngine Hub, 9 Apr. 2009, hub.jmonkeyengine.org/t/rotation-around-object/9875. 
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
    private Label errorLabel; //Main HAS-A errorLabel label
    private Shape testShape; //Main HAS-A testShape Shape
    private final float xStart = -5f; //global var
    private enum ViewMode { STREAMLINES, PARTICLES} //used to switch between particle and streamlined view
    private ViewMode currentMode = ViewMode.STREAMLINES; //sets default view to streamlines
    private final Node streamlineNode = new Node("Streamlines"); //node to hold all streamlines
    private final HashMap<Geometry, Vector3f> originalOffsets = new HashMap<>(); //Main HAS-A HashMap
    private final ArrayList<Geometry> particles = new ArrayList<>(); //Main HAS-A ArrayList
    private final Node particleNode = new Node("Particles"); //node to hold all particles
    private final int gridY = 10; //y length of the grid
    private final int gridZ = 10; //z length of the grid
    private final float spacing = 0.3f; //spacing of the grid
    private VersionedReference<Double> sliderRef; //Main HAS-A VersionedReference for wind speed slider
    //boolean to determine if slider has been changed since last tick
    //Versioned reference sliderRef is a bool that indicates whether or not the slider has been changed since last checked
    //saving resources from checking slider when there was no change
    private MaterialProperty aluminum = new MaterialProperty("Aluminum", 0.47, ColorRGBA.Blue); //Main HAS-A MaterialProperty
    private MaterialProperty wood = new MaterialProperty("Wood", 0.82, ColorRGBA.Brown); //Main HAS-A MaterialProperty
    private MaterialProperty mesh = new MaterialProperty("Mesh", 1.6, ColorRGBA.Gray); //Main HAS-A MaterialProperty
    private MaterialProperty foam = new MaterialProperty("Foam", 1.2, ColorRGBA.Orange); //Main HAS-A MaterialProperty
    private MaterialProperty carbonFiber = new MaterialProperty("Carbon Fiber", 1.28, ColorRGBA.DarkGray); //Main HAS-A MaterialProperty
    private Node cameraPivot; //Node to hold camera pivot
    private CameraNode camNode; //Main HAS-A CameraNode
    private boolean rightDragging = false; //Boolean to tell if R mouse buttong is being clicked
    private int previousX; //previous x coord
    private int previousY; //previous y coord
    
    
            
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
        
        tunnel = new WindTunnel(10, testShape); //Create WindTunnel with speed 10 m/s
        double drag = tunnel.computeDrag(testShape);
        
        //Build streamlines
        Streamline.buildStreamlines(assetManager, streamlineNode, gridY, gridZ, spacing);
        
        //Build Particles
        Particle.buildParticles(assetManager, particleNode, particles, originalOffsets, xStart, gridY, gridZ, spacing);
        
        
        //Add the GUI
        GuiGlobals.initialize(this); //initialize the gui in the current application
        BaseStyles.loadGlassStyle(); //style the GUI
        stateManager.attach(new MouseAppState(this)); //enable mouse support for GUI compnents
        GuiGlobals.getInstance().getFocusManagerState().setEnabled(true); //sets the GUI focus to true to enable interaction
        
        ControlPanelGUI gui = new ControlPanelGUI(guiNode, cam, this); //create the GUI
        sliderRef = gui.getSliderRef(); //holds sliderRef value
        windSpeedLabel = gui.getWindSpeedLabel(); //Sets label equal to windSpeed
        dragForceLabel = gui.getdragForceLabel(); //sets label equal to dragForce
              
        //Add lighting
        DirectionalLight sun = new DirectionalLight(); //Add in diectional lighting for stage
        sun.setDirection(new Vector3f(-1, -2, -3).normalizeLocal()); //sets the direction of the directional lighting
        sun.setColor(ColorRGBA.White); //sets the color of the light
        rootNode.addLight(sun); //Attaches the light to the scene
        
        //Input designations
        inputManager.addMapping("ToggleView", new com.jme3.input.controls.KeyTrigger(com.jme3.input.KeyInput.KEY_SPACE)); //Sets the space bar as the ViewMode toggle switch
        inputManager.addListener(toggleListener, "ToggleView"); //Adds the listener to the new mapping
            
        viewPort.setBackgroundColor(ColorRGBA.DarkGray); //Sets the background color to dark gray      
        
        //Camera Controls
        flyCam.setEnabled(false); //Disbale the default flycam
        initCameraOrbit(); //Set cam to orbit mode
        initControls(); //set cam controls
        
        //IO implementation
    }
    
    //Create the toggleListener for ViewMode enum
    private final ActionListener toggleListener = new com.jme3.input.controls.ActionListener() 
    {
        @Override
        public void onAction(String name, boolean isPressed, float timePerFrame)
        {
            if (name.equals("ToggleView") && isPressed)
            {
                if (currentMode == ViewMode.STREAMLINES) //if viewmode is stream lines
                {
                    currentMode = ViewMode.PARTICLES; //sets viewmode to particles
                    streamlineNode.setCullHint(Spatial.CullHint.Always); //hides the streamlines
                    particleNode.setCullHint(Spatial.CullHint.Inherit); //shows the particles
                } else //if viewmode is particles
                {
                    currentMode = ViewMode.STREAMLINES; //sets viewmode to streamlines
                    particleNode.setCullHint(Spatial.CullHint.Always); //hides the particles
                    streamlineNode.setCullHint(Spatial.CullHint.Inherit); //shows the streamlines
                }
            }
        }
    };
        
    @Override
    public void simpleUpdate(float timePerFrame)
    {
        Vector3f objectCenter = new Vector3f(0, 0, 0); //sets the center of the object
        float objectRadius = 1f; //sets the radius of the object
        
        for (Geometry p : particles)
        {
            Vector3f pos = p.getLocalTranslation(); //get particle location
            pos.x += timePerFrame * 2f; //set speed of particle translation
            
            if(pos.distance(objectCenter) < objectRadius + 0.5f) //if particle hits object
            {
                pos.y += timePerFrame * 1.5f; //bounce particle up
            }
            
            //Reset particle after passing object
            if (pos.x > 5f) 
            {
                Vector3f original = originalOffsets.get(p);
                pos.x = xStart;
                pos.y = original.y;
                pos.z = original.z;  
            }  
        p.setLocalTranslation(pos); //resets particle position
        }
        
        try
        {
            //Update for slider
            if (sliderRef != null && sliderRef.update())
            {
                double value = sliderRef.get(); //get the versionedReference from the slider
                double drag = tunnel.computeDrag(testShape); //compute the drag of the shape
                dragForceLabel.setText(String.format("Drag Force: %.2f Newtons", drag)); //Set the drag force label to the drag force

                if (!Float.isNaN((float) value)) //check if slider value is a number
                {
                    tunnel.setWindSpeed(value); //set the windspeed to slider value
                    IO.save(testShape.getShapeType(), testShape.getMaterial().getName(), value, drag); //log test data to save file
                    windSpeedLabel.setText(String.format("Wind Speed: %.1f m/s", value)); //set the windspeed label to windspeed value
                }
            }
        }
        catch (NullPointerException e) //catch null pointer exception and print error message
        {
            System.err.println("Slider or shape is not initialized: " + e.getMessage());
        }
        catch (IllegalArgumentException e) //catch illegal argument exception and print error message
        {
            System.err.println("Invalid input in drag calculation: " + e.getMessage());
        }
        catch (Exception e) //catch all general exceptions and print error message
        {
            System.err.println("Unexpected error during update: " + e.getMessage());
        }
    }
    
    /**
     * Purpose: To switch the testShape based on GUI inputs
     * @param shapeType the type of shape
     */
    public void setShape(String shapeType)
    {
        try
        {
            if (testShape != null) 
            {
                rootNode.detachChild(testShape.getGeometry()); //remove current shape from scene
            }

            switch (shapeType) //switch block to set new shape
            {
                case "Sphere" -> testShape = new Sphere(0.1, aluminum, assetManager);
                case "Cube" -> testShape = new Cube(0.1, aluminum, assetManager);
                case "FlatPlate" -> testShape = new FlatPlate(0.1, aluminum, assetManager);
                default -> throw new IllegalArgumentException("Invalid Shape");
            }

            rootNode.attachChild(testShape.getGeometry()); //attach new shape to the scene

            double drag = tunnel.computeDrag(testShape); //recompute drag force
            dragForceLabel.setText(String.format("Drag Force: %.2f Newtons", drag)); //set label to new drag force
            errorLabel.setText("");
        }
        catch (Exception e) //catch general exceptions
        {
            String message = "Error: " + e.getMessage();
            errorLabel.setText(message);
            System.err.println(message);
        }
    }
    
    /**
     * Purpose: To initialize the orbital camera
     */
    private void initCameraOrbit()
    {
        cameraPivot = new Node("CameraPivot"); //define cameraPivot
        rootNode.attachChild(cameraPivot); //attach it to the scene
        
        camNode = new CameraNode("CamNode", cam); //define camNode
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera); //Set the cam fixed to the node
        camNode.setLocalTranslation(0, 0, 100); //Set camera distance to center
        camNode.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y); //points cam at origin of scene
        
        cameraPivot.attachChild(camNode); //attaches camNOde to the camera pivut
    }
    
    /**
     * Purpose: To add controls and listeners to key mapping
     */
    public void initControls()
    {
        inputManager.addMapping("RightClick", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT)); //add mapping for right click
        inputManager.addMapping("ZoomIn", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false)); //add mapping for mouse wheel up
        inputManager.addMapping("ZoomOut", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true)); //add mapping for mouse wheel down
        
        inputManager.addListener(actionListener, "RightClick"); //add listener to right click
        inputManager.addListener(analogListener, "ZoomIn", "ZoomOut"); //add listener to mouse wheel
        inputManager.addRawInputListener(mouseListener); //add mouse listener to calculate delta
    }
    
    /**
     * Purpose: To create an ActionListener for use in the camera Controls
     */
    private final ActionListener actionListener = new ActionListener()
    {
        public void onAction(String name, boolean isPressed, float timePerFrame)
        {
            if ("RightClick".equals(name)) //Check if r mouse button is clicked
            {
                rightDragging = isPressed; //update rightDragging to match current state
                if (isPressed)
                {
                    Vector2f cursorPos = inputManager.getCursorPosition(); //get the current cursor position
                    previousX = (int) cursorPos.x; //store cursor x coord
                    previousY = (int) cursorPos.y; //store cursor y coord
                }
            }
        }
    };
    
    /**
     * Purpose: To create the analog listener for camera zoom feature
     */
    private final AnalogListener analogListener = new AnalogListener()
    {
        public void onAnalog(String name, float value, float timePerFrame)
        {
            Vector3f current = camNode.getLocalTranslation(); //get current position of cam
            float zoomSpeed = 50f * timePerFrame; //set cam zoom speed per tick
            
            if("ZoomIn".equals(name))
            {
                camNode.setLocalTranslation(current.add(cam.getDirection().mult(zoomSpeed))); //set position of cam to zoom in
            }
            else if ("ZoomOut".equals(name))
                {
                    camNode.setLocalTranslation(current.subtract(cam.getDirection().mult(zoomSpeed))); //set position of cam to zoom out
                }
        }
    };
    
    /**
     * Purpose: To create a raw input listener to get the mouse delta for camera movement
     */
    private final RawInputListener mouseListener = new RawInputListener()
    {
        @Override
        public void onMouseMotionEvent(MouseMotionEvent event)
        {
            if (rightDragging) //if cursor is being dragged
            {
                int currentX = event.getX(); //get current cursor x pos
                int currentY = event.getY(); //get current cursor y pos
                
                float deltaX = currentX - previousX; //get drag distance x
                float deltaY = currentY - previousY; //get drag distance y
                
                cameraPivot.rotate(0, -deltaX * 0.01f, 0); //Orbit horizontal
                cameraPivot.rotate(-deltaY * 0.01f, 0, 0); //Orbit vertical
                
                previousX = currentX; //set new previous position x
                previousY = currentY; //set new previous position y
            }
        }
        //All methods if rawInputListener must be overriden, left blank and unused in our case
        @Override public void beginInput() {}
        @Override public void endInput() {}
        @Override public void onJoyAxisEvent(JoyAxisEvent event) {}
        @Override public void onJoyButtonEvent(JoyButtonEvent event) {}
        @Override public void onMouseButtonEvent(MouseButtonEvent event) {}
        @Override public void onKeyEvent(KeyInputEvent event) {}
        @Override public void onTouchEvent(com.jme3.input.event.TouchEvent event) {}
    };
    
    /**
     * Purpose: To get the wind tunnel object
     */
    public WindTunnel getTunnel()
    {
        return tunnel;
    }
}
