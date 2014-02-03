/**
 * 
 */
package dm;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import data.DataDeserializer;
import data.EnumDeserializer;
import data.KeywordData;
/* *********************************************
 * NOTE: DON'T RUN THIS TILL ALL KEYWORDDATA HAS BEEN FIXED, OR EXCEPTIONS WILL FLY!
 ***********************************************/
/**
 * @author Daniel Draper
 * @version 1.0
 * Tests the different DialogClasses, for different combinations of keywords.
 */
public class DialogTest {

	static Dialog currentDialog;
	static Dictionary dictionary;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		dictionary = new Dictionary();
		
	}
	
		
		

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Loads and returns the keyword specified by the ID passed as a parameter
	 * @param KeywordID
	 * @return
	 */
	private Keyword loadKeyword(int keywordID) {
		File load = new File("resources/files/KeywordData/" + keywordID + ".json");
		//Needed for Generic Enum Deserialization
		EnumDeserializer des = new EnumDeserializer();
		DataDeserializer d = new DataDeserializer();
		Gson loader = new GsonBuilder().registerTypeAdapter(java.lang.Enum.class, des).registerTypeAdapter(data.Data.class, d).create();
		/*BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(load));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/
	  	KeywordData read = null;
	  		try {
				read = loader.fromJson(new FileReader(load), KeywordData.class);
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	  	/*try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	  	return new Keyword(read);	
	  	
	}
	
	/**
	 * Tests to see if keywords are loaded correctly, or errors persist.
	 */
	@Test
	public void loadTest() {
		Keyword kw = null;
		for (int i = 0; i < 185; i++) {
			try {
				kw = loadKeyword(i);
			}
			catch (Exception e) {
				fail("Failed at number:" + i + "\n");
			}
			try {
				System.out.println(kw.getWord() + i);
			}
			catch (Exception e) {
				fail("Failed at printing number:" + i + "\n");
			}
		}
	}

}
