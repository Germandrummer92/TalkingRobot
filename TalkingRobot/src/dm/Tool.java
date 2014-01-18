package dm;
import java.util.ArrayList;

import data.ToolData;
import data.RecipeData;

/**
 * Represents a tool in the system with the data loaded/saved in a json file.
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class Tool {

  private ToolData toolData;

  /**
   * Creates a new Tool object with a name and location and saves it to a file.
   * @param toolName name of the new tool
   * @param location location in the kitchen as a string
   */
  public Tool(String toolName, String location) {
	  toolData = new ToolData(toolName, location, new ArrayList<RecipeData>());
	  toolData.writeFile();
  }
  /**
   * Creates a new Tool object from a loaded ToolData object.
 * @param d the toolData to be loaded
 */
  private Tool(ToolData d) {
	toolData = d;
  }

/**
 * 
 * @return the toolData of this tool
 */
public ToolData getToolData() {
  	return toolData;
  }

  
  /**
   * Loads the List of current Tools known to the system from files. 
   * @return the List of current Tools known to the system.
   */
  public static ArrayList<Tool> loadTools() {
	  ArrayList<Tool> res = new ArrayList<Tool>();
	  for (ToolData d : ToolData.loadData()) {
		  res.add(new Tool(d));
	  }
	  return res;
  }
}