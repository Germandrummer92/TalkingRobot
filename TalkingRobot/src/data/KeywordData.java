package data;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import dm.CanteenInfo;
import dm.CanteenInformationState;
import dm.DialogState;
import dm.Start;
import dm.StartState;

/**
 * 
 * @author Luiz Henrique Soares Silva
 * @version 1.0
 * 
 * This class describes and implements a Keyword Data.
 *
 */
public class KeywordData implements Data {

	private int wordID;

  	private String word;

  	private ArrayList<DialogState> dialogState;

  	private int priority;

  	private ArrayList<Data> dataReference;
  	
  	private KeywordType type;
  	/**
  	 * Creates a new KeywordData type with the parameters specified
  	 * @param word
  	 * @param state dialogstates
  	 * @param priority
  	 * @param dataRef dataReferences
  	 * @param type
  	 */
  	public KeywordData(String word, ArrayList<DialogState> state, int priority, ArrayList<Data> dataRef, KeywordType type) {
  		wordID = nextID();
  		this.word = word;
  		this.dialogState = state;
  		this.priority = priority;
  		this.dataReference = dataRef;
  		this.type = type;
  	}
  	
  	private KeywordData(int id,String word, ArrayList<DialogState> state, int priority, ArrayList<Data> dataRef, KeywordType type) {
  		this.wordID = id;
  		this.word = word;
  		this.dialogState = state;
  		this.priority = priority;
  		this.dataReference = dataRef;
  		this.type = type;
  	}
  	
  	/**
  	 *  Create the object directly from a jsonString
  	 * @param jsonString the json String
  	 */
  	public KeywordData(String jsonString) {
  		this.createFromJSONText(jsonString);
  	}

	@Override
	public String generateJSON() {
		Gson creator = new GsonBuilder().
			    registerTypeAdapter(java.lang.Enum.class, new EnumSerializer()).create();

		//switch (dialogState.getCurrentState().getDeclaringClass().toString()) {
	//	case "dm.Start" : dialogState.setCurrentState();
		//}
		
		return creator.toJson(this);
	}
	
	@Override
	public void createFromJSONText(String jsonString) {
		//Needed for Generic Enum Deserialization
				EnumDeserializer des = new EnumDeserializer();
				DataDeserializer d = new DataDeserializer();
				Gson creator = new GsonBuilder().registerTypeAdapter(java.lang.Enum.class, des).registerTypeAdapter(Data.class, d).create();
		KeywordData newData = creator.fromJson(jsonString, this.getClass());
		this.wordID = newData.getWordID();
		this.word = newData.getWord();
		this.dialogState = newData.getDialogState();
		this.priority = newData.getPriority();
		this.dataReference = newData.getDataReference();
		this.type = newData.getType();
		
	}
	
	@Override
	public void writeFile() {
		String pathname = "resources/files/KeywordData/" + wordID + ".json";
	    PrintWriter writer;
	    try {
	            writer = new PrintWriter(pathname, "UTF-8");
	            writer.println(this.generateJSON());
	            writer.close();
	    } catch (FileNotFoundException | UnsupportedEncodingException e) {
	            e.printStackTrace();
	    }
	}
	
	@Override
	public JSONObject getJson() {
		JSONObject object = null;
	    try {
	            object = new JSONObject(this.generateJSON());
	    } catch (JSONException e) {
	            e.printStackTrace();
	    }
	    return object;
	}

	/**
	 * 
	 * @return the keyword's ID.
	 */
	public Integer getWordID() {
		return wordID;
	}

	/**
	 * 
	 * @param wordID the keyword's ID to be set.
	 */
	public void setWordID(Integer wordID) {
		this.wordID = wordID;
	}
	
	/**
	 * 
	 * @return the keyword (as a String).
	 */
	public String getWord() {
		return word;
	}
	
	/**
	 * 
	 * @param word the keyword (as a String) to be set.
	 */
	public void setWord(String word) {
		this.word = word;
	}
	
	/**
	 * 
	 * @return the keyword's dialog state.
	 */
	public ArrayList<DialogState> getDialogState() {
		return dialogState;
	}
	
	/**
	 * 
	 * @param dialogState the dialog state reference to be set.
	 */
	public void setDialogState(ArrayList<DialogState> dialogState) {
		this.dialogState = dialogState;
	}
	
	/**
	 * 
	 * @return the keyword's priority.
	 */
	public Integer getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority of this keyword to be set.
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	/**
	 * @return the current Data Reference.
	 */
	public ArrayList<Data> getDataReference() {
		return dataReference;
	}

	/**
	 * @param dataReference the Data Reference to be set.
	 */
	public void setDataReference(ArrayList<Data> dataReference) {
		this.dataReference = dataReference;
	}
	
	
	/**
	 * This static method returns a List of all existing KeywordData files.
	 * @return a list of all existing KeywordData files
	 */
	public static ArrayList <KeywordData> loadData() {
		File load = new File("resources/files/KeywordData/");
		//Needed for Generic Enum Deserialization
		EnumDeserializer des = new EnumDeserializer();
		DataDeserializer d = new DataDeserializer();
		Gson loader = new GsonBuilder().registerTypeAdapter(java.lang.Enum.class, des).registerTypeAdapter(data.Data.class, d).create();
		ArrayList <KeywordData> res = new ArrayList <KeywordData>();
		for (File f : load.listFiles()) {
			/*BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(f));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}*/
		  	KeywordData read = null;
		  		try {
					read = loader.fromJson(new FileReader(f), KeywordData.class);
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		  	res.add(read);
		}
		return res;
	}
	
	/**
	 * @return the next unique ID
	 */
	private int nextID() {
		int maxID = 0;
		for (KeywordData d: loadData()) {
			if (d.getWordID() > maxID) {
				maxID = d.getWordID();
			}
		}
		return maxID + 1;
	}
	
	/**
	 * @return the type
	 */
	public KeywordType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(KeywordType type) {
		this.type = type;
	}

	public void deleteFile() {
		String dirPath = "resources/files/KeywordData/" + wordID + ".json";
		File currFile = new File(dirPath + wordID + ".json");
		currFile.delete();
		
	}

	/*public static void main(String[] args) {
		for (KeywordData d : loadData()) {
			System.out.println(d.word + " " + d.wordID);
		}*/
	//}
}