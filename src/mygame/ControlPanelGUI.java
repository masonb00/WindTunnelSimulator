package mygame;

import com.jme3.scene.Node;
import com.jme3.renderer.Camera;
import com.simsilica.lemur.*;
import com.simsilica.lemur.Slider;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.core.VersionedReference;


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
 * “Nifty 1.3 Controls Example/Demonstration.” Vimeo, void, 6 May 2025, vimeo.com/25637085. 
 * “W3schools.Com.” W3Schools Online Web Tutorials, www.w3schools.com/java/java_lambda.asp. Accessed 9 May 2025. 
 * 
 * Version/date: V1
 * 
 * Responsibilities of class:
 * To create the GUI responsible for all changes to the application and other user interaction
 */
public class ControlPanelGUI
{
    private final Container panel; //ControlPanelGUI HAS-A Container
    private final Label windSpeedLabel; //ControlPanelGUI HAS-A Label
    private final Label dragForceLabel; //ControlPanelGUI HAS-A Label
    private final Slider windSpeedSlider; //ControlPanelGUI HAS-A Slider
    private final VersionedReference<Double> sliderRef; //ControlPanel HAS-A VersionedReference
    private final DefaultRangedValueModel model = new DefaultRangedValueModel(0, 100, 10); //ControlPanelGUI HAS-A DefaultRangedValueModel
    
    
    public ControlPanelGUI(Node guiNode, Camera cam, Main mainApp)
    {
        //Define the GUI
        panel = new Container(); //create the panel
        guiNode.attachChild(panel); //attach panel to GUI
        panel.setLocalTranslation(50, cam.getHeight() - 50, 0); //set GUI location
        panel.setInsets(new Insets3f(10, 10, 10, 10)); //set inset values in panel
        
        //Add to layout manager
        Container shapeSection = new Container(new SpringGridLayout(Axis.Y, Axis.X, FillMode.Last, FillMode.Even)); //create layout
        shapeSection.setInsets(new Insets3f(5, 5, 5, 5)); //sets padding size of components in layout
        shapeSection.addChild(new Label("Choose Shape: ")); //add shape button label to layout manager
   
        //Create the Shape buttons
        Button sphereButton = new Button("Sphere"); //create sphere button
        Button cubeButton = new Button("Cube"); //create cube button
        Button flatPlateButton = new Button("Flat Plate"); //create flatplate button
        Label errorLabel = new Label(""); //create error label
        
        //Add Shape buttons to layout manager
        shapeSection.addChild(sphereButton); //add sphere button to layout manager
        shapeSection.addChild(cubeButton); //add cube button to layout manager
        shapeSection.addChild(flatPlateButton); //add flatplate button to layout manager
        shapeSection.addChild(errorLabel); //add the error label to the layout manager
            
        //Shape Button Action Logic
        sphereButton.addClickCommands(source -> mainApp.setShape("Sphere")); //sets button logic (changes the shape of the object in the scene)
        cubeButton.addClickCommands(source -> mainApp.setShape("Cube")); //sets button logic
        flatPlateButton.addClickCommands(source -> mainApp.setShape("FlatPlate")); //sets button logic
        
        //Add layout manager to the panel
        panel.addChild(shapeSection);
        
        //Create Wind Speed Slider Section
        Container windSpeedSection = new Container(); //create the Wind speed section container
        windSpeedSection.setInsets(new Insets3f(5, 5, 5, 5)); //set inset padding
        windSpeedLabel = new Label("Wind Speed: 10 m/s"); //set new label to initial wind speed
        windSpeedSection.addChild(windSpeedLabel);  //add label to windspeedsection container
        
        //Create Wind Speed Slider
        windSpeedSlider = new Slider(); //create new slider
        windSpeedSlider.setDelta(1f); //set the step to 1
        windSpeedSlider.setPreferredSize(new com.jme3.math.Vector3f(200, 20, 0)); //set the size of the slider
        windSpeedSlider.setModel(model); //set the range of the slider to the ranged model field
        
        //Create VersionedReference for the slider
        sliderRef = windSpeedSlider.getModel().createReference();
        //Versioned reference sliderRef is a bool that indicates whether or not the slider has been changed since last checked
        //saving resources from checking slider when there was no change
        
        windSpeedSection.addChild(windSpeedSlider); //add slider to windSpeedSection container
        panel.addChild(windSpeedSection); //add WindSpeedSection to panel
        
        //Create label for Drag Force
        dragForceLabel = new Label("Drag Force: 0 Newtons");
        windSpeedSection.addChild(dragForceLabel); //add dragForceLabel to the windSpeedSection container
        
        //Create save button
        Button saveButton = new Button ("Save"); //create the save button
        windSpeedSection.addChild(saveButton); //add save button to the container
        WindTunnel tunnel = mainApp.getTunnel();
        saveButton.addClickCommands(source -> IO.save(tunnel.getShape().getShapeType(), tunnel.getShape().getMaterial().toString(), tunnel.getWindSpeed(), tunnel.computeDrag(tunnel.getShape())));
    }
    public Container getPanel()
    {
        return panel;
    }
    
    public VersionedReference<Double> getSliderRef()
    {
        return sliderRef;
    }
    
    public Label getWindSpeedLabel()
    {
        return windSpeedLabel;
    }
    
    public Label getdragForceLabel()
    {
        return dragForceLabel;
    }
}
