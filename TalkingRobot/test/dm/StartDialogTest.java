/**
 * 
 */
package dm;

/* *********************************************
 * NOTE: DON'T RUN THIS TILL ALL KEYWORDDATA HAS BEEN FIXED, OR EXCEPTIONS WILL FLY!
 ***********************************************/
 
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import data.DataDeserializer;
import data.EnumDeserializer;
import data.KeywordData;
import data.UserData;

/**
 * @author Daniel Draper
 * @version 1.0
 *	This class tests the StartDialogTest for different Keyword Combinations.
 */
public class StartDialogTest {

	private static ArrayList<Keyword> kwList;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DialogTest.currentDialog = new StartDialog(new Session(new User(), Robot.loadRobots().get(0)));
		kwList = new ArrayList<Keyword>();
	}

	/**
	 * Tests, if the StartDialog jumps to S_USER_FOUND if passed a keyword set up as a User, and if it does the correct Datahandling.
	 */
	@Test
	public void UserTest() {
		Keyword load = loadKeyword(2);
		kwList.add(load);
		try {
			DialogTest.currentDialog.updateState(kwList, null, null);
		} catch (WrongStateClassException e) {
			assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(Start.S_USER_FOUND) && DialogTest.currentDialog.getCurrentSession().getCurrentUser().getUserData().getUserID() == 0);
		}
		assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(Start.S_USER_FOUND) && DialogTest.currentDialog.getCurrentSession().getCurrentUser().getUserData().getUserID() == 0);
	}
	
	/**
	 * Tests, if the StartDialog jumps to S_USER_SAVED if passed keywords and terms to save a new User.
	 */
	@Test
	public void newUserTest() {
		DialogTest.currentDialog.getCurrentDialogState().setCurrentState(Start.S_WAITING_FOR_USERNAME);
		ArrayList<String> terms =  new ArrayList<String>();
		ArrayList<String> approval = new ArrayList<String>();
		approval.add("yes");
		terms.add("Benjamin");
		try {
			DialogTest.currentDialog.updateState(null, terms, null);
		} catch (WrongStateClassException e) {
			//not reached
			assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(Start.S_USER_NOT_FOUND) && DialogTest.currentDialog.getCurrentSession().getCurrentUser().getUserData().getUserName().equals("Benjamin"));
		}
		assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(Start.S_USER_NOT_FOUND) && DialogTest.currentDialog.getCurrentSession().getCurrentUser().getUserData().getUserName().equals("Benjamin"));
		try {
			DialogTest.currentDialog.updateState(null, null, approval);
		} catch (WrongStateClassException e) {
			//not reached
			assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(Start.S_WAITING_FOR_EMPLOYEE_STATUS) && DialogTest.currentDialog.getCurrentSession().getCurrentUser().getUserData().getUserName().equals("Benjamin"));
		}
		assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(Start.S_WAITING_FOR_EMPLOYEE_STATUS) && DialogTest.currentDialog.getCurrentSession().getCurrentUser().getUserData().getUserName().equals("Benjamin"));
		try {
			DialogTest.currentDialog.updateState(null, null, approval);
		} catch (WrongStateClassException e) {
			//not reached
			assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(Start.S_USER_SAVED) && DialogTest.currentDialog.getCurrentSession().getCurrentUser().getUserData().getUserName().equals("Benjamin") 
					&& DialogTest.currentDialog.getCurrentSession().getCurrentUser().getUserData().isStudent() == true);
		}
		assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(Start.S_USER_SAVED) && DialogTest.currentDialog.getCurrentSession().getCurrentUser().getUserData().getUserName().equals("Benjamin")
				&& DialogTest.currentDialog.getCurrentSession().getCurrentUser().getUserData().isStudent() == true);
		deleteKeyword("benjamin");
	}
	/**
	 * Tests, if the StartDialog jumps correctly, if passed "Yes" While waiting for the employee status of the user.
	 */
	@Test
	public void EmployeeTest() {
		DialogTest.currentDialog.getCurrentDialogState().setCurrentState(Start.S_WAITING_FOR_EMPLOYEE_STATUS);
		DialogTest.currentDialog.getCurrentSession().setCurrentUser(new User(new UserData("AB", true)));
		ArrayList<String> approval = new ArrayList<String>();
		approval.add("no");
		try {
			DialogTest.currentDialog.updateState(null, null, approval);
		} catch (WrongStateClassException e) {
		assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(Start.S_USER_SAVED) && DialogTest.currentDialog.getCurrentSession().getCurrentUser().getUserData().isStudent() == false);
		}
		assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(Start.S_USER_SAVED) && DialogTest.currentDialog.getCurrentSession().getCurrentUser().getUserData().isStudent() == false);
	}
	/**
	 * Tests, if a list of inputs follows the right states and handles data the right way.
	 */
	@Test
	public void completeTestUserKnown() {
		ArrayList<Keyword> kw1 = new ArrayList<Keyword>();
		ArrayList <Keyword> kw2 = new ArrayList<Keyword>();
		ArrayList <Keyword> kw3 = new ArrayList<Keyword>();
		kw1.add(loadKeyword(0)); //"Hello"
		kw2.add(loadKeyword(2)); //"Daniel"
		kw3.add(loadKeyword(11)); //"Cafeteria"
		User daniel =  new User((UserData)loadKeyword(2).getKeywordData().getDataReference().get(0));
		try {
			DialogTest.currentDialog.updateState(kw1, null, null);
		} catch (WrongStateClassException e) {
			//Not reached		}
		}
		assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(Start.S_WAITING_FOR_USERNAME) && DialogTest.currentDialog.getCurrentSession().getCurrentUser() != null);
		try {
			DialogTest.currentDialog.updateState(kw2, null, null);
		} catch (WrongStateClassException e) {
			//Not reached		}
		}
		assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(Start.S_USER_FOUND) && DialogTest.currentDialog.getCurrentSession().getCurrentUser().equals(daniel));
		try {
			DialogTest.currentDialog.updateState(kw3, null, null);
		} catch (WrongStateClassException e) {
			assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(CanteenInfo.CI_ADEN_CAFE_DISH) && DialogTest.currentDialog.getCurrentSession().getCurrentUser().equals(daniel));	
			}
		//Not Reachable due to WrongStateClassException as state is switched
		assertTrue(DialogTest.currentDialog.getCurrentDialogState().getCurrentState().equals(CanteenInfo.CI_ADEN_CAFE_DISH) && DialogTest.currentDialog.getCurrentSession().getCurrentUser().equals(daniel));
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
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(load));
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
	  	try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	  	return new Keyword(read);	
	  	
	}
	
	/**
	 * Tears down the changed Dialog after each test.
	 */
	@After
	public void tearDown() {
		DialogTest.currentDialog = new StartDialog(new Session(new User(), Robot.loadRobots().get(0)));
	}
	
	private void deleteKeyword(String keyword) {
		Dictionary dic = new Dictionary();
		dic.removeKeyword(keyword);
	}

}
