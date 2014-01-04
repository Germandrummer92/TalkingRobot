package data;
import org.json.*;

/**
 * 
 * @author Daniel Draper
 * @version 1.0
 * The Data Interface outlines the methods implemented by every Data class regarding loading/saving of data onto the hard drive.
 *
 */
public interface Data {

  /**
  * Generates the text for the JSON file, that will represent the current object.
  * @return the text representing the object
  */
  public String generateJSON();

  /**
   * Creates a JavaObject from the text loaded from the JSON File.
   * @param jsonString the string responsible for the 
   */
  public void createFromJSONText(String jsonString);

  /**
   * Saves the created JSONtext on the hard drive
   */
  public void writeFile();

  /**
   * Returns a JSONObject representation of this object.
   * @return the JSONObject represenation of this object.
   */
  public JSONObject getJson();

}