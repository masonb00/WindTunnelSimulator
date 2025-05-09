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
    private final VersionedReference<Double> sliderRef;
    private final DefaultRangedValueModel model = new DefaultRangedValueModel(0, 100, 10);
    
    public ControlPanelGUI(Node guiNode, Camera cam, Main mainApp)
    {
        //Define the GUI
        panel = new Container();
        guiNode.attachChild(panel);
        panel.setLocalTranslation(50, cam.getHeight() - 50, 0);
        panel.setInsets(new Insets3f(10, 10, 10, 10));
        
        //Add to layout manager
        Container shapeSection = new Container(new SpringGridLayout(Axis.Y, Axis.X, FillMode.Last, FillMode.Even));
        shapeSection.setInsets(new Insets3f(5, 5, 5, 5));
        shapeSection.addChild(new Label("Choose Shape: "));
   
        //Create the Shape buttons
        Button sphereButton = new Button("Sphere");
        Button cubeButton = new Button("Cube");
        Button flatPlateButton = new Button("Flat Plate");
        
        //Add Shape buttons to layout manager
        shapeSection.addChild(sphereButton);
        shapeSection.addChild(cubeButton);
        shapeSection.addChild(flatPlateButton);
            
        //Shape Button Action Logic
        sphereButton.addClickCommands(source -> mainApp.setShape("Sphere"));
        cubeButton.addClickCommands(source -> mainApp.setShape("Cube"));
        flatPlateButton.addClickCommands(source -> mainApp.setShape("FlatPlate"));
        
        //Add layout manager to the panel
        panel.addChild(shapeSection);
        
        //Create Wind Speed Slider Section
        Container windSpeedSection = new Container();
        windSpeedSection.setInsets(new Insets3f(5, 5, 5, 5));
        windSpeedLabel = new Label("Wind Speed: 10 m/s");
        windSpeedSection.addChild(windSpeedLabel);
        
        //Create Wind Speed Slider
        windSpeedSlider = new Slider();
        windSpeedSlider.setDelta(1f);
        windSpeedSlider.setPreferredSize(new com.jme3.math.Vector3f(200, 20, 0));
        windSpeedSlider.setModel(model);
        
        //Create VersionedReference for the slider
        sliderRef = windSpeedSlider.getModel().createReference();
        
        windSpeedSection.addChild(windSpeedSlider);
        panel.addChild(windSpeedSection);
        
        //Create label for Drag Force
        dragForceLabel = new Label("Drag Force: 0 Newtons");
        windSpeedSection.addChild(dragForceLabel);
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
