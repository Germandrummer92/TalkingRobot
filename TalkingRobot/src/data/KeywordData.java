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
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(f));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		  	KeywordData read = null;
		  		try {
					read = loader.fromJson(br.readLine(), KeywordData.class);
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
		File f = new File("resources/files/KeywordData/");
		return f.listFiles().length;
	}
	
	/*public static void main(String[] args) {
		File keywords = new File("resources/files/keywords.txt");
		BufferedReader br = null;
		DialogState d = new DialogState();
		try {
			br = new BufferedReader(new FileReader(keywords));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line;
		try {
			while ((line = br.readLine()) != null) {
			   new KeywordData(line, d, 0, null).writeFile();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

}