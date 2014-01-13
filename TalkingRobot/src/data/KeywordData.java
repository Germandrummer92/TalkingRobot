package data;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import dm.DialogState;

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

  	private DialogState dialogState;

  	private int priority;

  	private Data dataReference;
  	
  	public KeywordData(String word, DialogState state, int priority, Data dataRef) {
  		wordID = nextID();
  		this.word = word;
  		this.dialogState = state;
  		this.priority = priority;
  		this.dataReference = dataRef;
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
		Gson creator = new Gson();
		return creator.toJson(this);
	}
	
	@Override
	public void createFromJSONText(String jsonString) {
		Gson creator;
		creator =  new Gson();
		KeywordData newData = creator.fromJson(jsonString, this.getClass());
		this.wordID = newData.getWordID();
		this.word = newData.getWord();
		this.dialogState = newData.getDialogState();
		this.priority = newData.getPriority();
		this.dataReference = newData.getDataReference();
		
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
	public DialogState getDialogState() {
		return dialogState;
	}
	
	/**
	 * 
	 * @param dialogState the dialog state reference to be set.
	 */
	public void setDialogState(DialogState dialogState) {
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
	public Data getDataReference() {
		return dataReference;
	}

	/**
	 * @param dataReference the Data Reference to be set.
	 */
	public void setDataReference(Data dataReference) {
		this.dataReference = dataReference;
	}
	
	/**
	 * @return the next unique ID
	 */
	private int nextID() {
		File f = new File("resources/files/KeywordData/");
		return f.listFiles().length;
	}

}