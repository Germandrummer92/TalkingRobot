package dm;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import data.Data;
import data.IngredientData;
import data.KeywordData;
import data.KeywordType;
import data.RecipeData;
import data.ToolData;
import data.UserData;


/**
 * The Dictionary of all the current keywords we have.
 * @author Daniel Draper
 * @version 1.3
 *
 */
public class Dictionary {

  private List<Keyword> keywordList;

  /**
   * Creates a new Dictionary and loads all the available keywords.
   */
  public Dictionary() {
	  loadKeywords();
  }
  /**
   * Loads the List of the keywords.
   */
  private void loadKeywords() {
	  keywordList = new ArrayList<Keyword>();
	  for (KeywordData d : KeywordData.loadData()) {
		  keywordList.add(new Keyword(d));
	  }
  }

  /**
   * Find possible Keywords matching the UserInput
   * @param kwInput
   * @return the List of keywords found in the Input.
   */
  public List<Keyword> findKeywords(List<String> kwInput) {
	  ArrayList<Keyword> res = new ArrayList<Keyword>();
	  for (String s: kwInput) {
		  for (Keyword kw: keywordList) {
			  if (kw.getWord().equals(s)) {
				  res.add(kw);
			  }
		  }
	  }
	  return res;
  }
  
  /**
   * Getter-Method for the list of keywords.
   * @return keywordList, a list of keywords
   */
  public List<Keyword> getKeywordList(){
	  return keywordList;
  }

  /**
   * Creates a new Keyword, adds it to the Dictionary and saves it in the corresponding Keyword Grammar file.
   * @param keyword the actual keyword
   * @param priority the keyword's priority
   * @param dialogState the DialogState the keyword will point to
   * @param ref
   */
  
  public void addKeyword(String keyword, int priority, ArrayList<DialogState> dialogState, ArrayList<Data> ref, KeywordType type) {
	  Keyword kw = new Keyword(new KeywordData(keyword, dialogState, priority, ref, type));
	  kw.getKeywordData().writeFile();
	  keywordList.add(kw);
	  String file;
	  switch (type) {
	  case USER : file = "user"; break;
	  case TOOL : file = "tool"; break;
	  case INGREDIENT : file = "ingredient";break;
	  case RECIPE : file = "recipe"; break;
	  case COUNTRY : file = "country"; break;
	  default : return;
	  }
	  FileWriter pw = null;
	  try {
		  pw = new FileWriter(new File("resources/nlu/Phoenix/TalkingRobot/Keyword/" + file), true);
	} catch (IOException e) {
		e.printStackTrace();
	}
	 if (pw != null) {
		 try {
			 pw.append("\n");
			pw.append("\t\t(" + keyword + ")");
		} catch (IOException e) {
			e.printStackTrace();
		}
	 try {
		pw.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
	 }
	  
  }
  
  /**
   * Removes a Recipe Keyword from the Keyword List, its KeywordData file and removes it from the Recipe grammar.
   */
  
  public void removeKeyword(String keyword) {
	  Keyword kwToDelete = null;
	  for (Keyword kw : keywordList) {
		  if (kw.getWord().equals(keyword)) {
			  kwToDelete = kw;
		  }
	  }
	  //Delete the KeywordData
	  kwToDelete.getKeywordData().deleteFile();
	  String file;
	  switch (kwToDelete.getKeywordData().getType()) {
	  case USER : file = "user"; break;
	  case TOOL : file = "tool"; break;
	  case INGREDIENT : file = "ingredient";break;
	  case RECIPE : file = "recipe"; break;
	  case COUNTRY : file = "country"; break;
	  default : return;
	  }
	  //Delete From the corresponding grammar
	  File f = new File("resources/nlu/Phoenix/TalkingRobot/Keyword/" + file);
	  File temp;
	  BufferedReader reader;
	  PrintWriter writer;
	try {
		temp = File.createTempFile("file", ".txt", f.getParentFile());
	  String charset = "UTF-8";
	  String delete = kwToDelete.getWord();
	  
	  reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
	
	  writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp), charset));
	
	  for (String line; (line = reader.readLine()) != null;) {
		  line = line.replace(delete, "");
		  if (!line.equals("")) {
			  writer.println(line);
		  }
		}
	  reader.close();
	  writer.close();
	  f.delete();
	  temp.renameTo(f);
  	} catch (IOException e) {
  		e.printStackTrace();
  	}
	//Remove from the list
	keywordList.remove(kwToDelete);
  }
  
 /* Test: public static void main(String[] args) {
	  Dictionary d = new Dictionary();
	  for (User u : DialogManager.giveDialogManager().getUserList())
	  d.addKeyword(u.getUserData().getUserName(), 100, new StartState(Start.S_USER_FOUND), u.getUserData());
  }*/
}