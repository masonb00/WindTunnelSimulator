package mygame;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
 * To handle all file IO for the program
 */
public class IO
{
    private static final String TEST_SAVE_FILE = "testStats.txt"; //file to save test data
    
    public static void save(String shapeType, String materialName, double windSpeed, double dragForce)
    {
        try (FileWriter writer = new FileWriter(TEST_SAVE_FILE, true)) //IO HAS-A FileWriter
        {
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss")); //string to hold the time of the test
            String saveData = String.format("[%s] Shape: %s, Material: %s, Wind Speed: %.2f m/s, Drag Force: %.2f N%n", timeStamp, shapeType, materialName, windSpeed, dragForce); //data being written to file
            writer.write(saveData); //write the data
        }
        catch (IOException e) //catch IO exceptions
        {
            System.err.println("Error while writing to save file: " + e.getMessage()); //print error message
        }
    }
}
