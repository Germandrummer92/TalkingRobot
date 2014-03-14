package coverageTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import data.CanteenData;
import data.CanteenNames;
import data.LineData;
import data.MealData;
import dm.Canteen;

/**
 * Test ask meals in specific lines function
 * @author Xizhe Lian
 * @version 1.0
 */
public class CILinesInfoTest extends basicTestCases.BasicTest {
	
	/**
	 * Ask meals information about each lines 
	 */
	@Test
   public void AdenAskMeals() {
		userInput.add("hi");
		userInput.add("xizhe");
		userInput.add("what's at line one today");
		//userInput.add("what's at line two today"); // line two is closed during semester break
		userInput.add("what's at line three today");
		userInput.add("what's at line four today");
		userInput.add("what's at line six today");
		userInput.add("what's at cafeteria today");
		//userInput.add("what's at curry queen today"); // curry queen never changes menu, so, not need to test
		//userInput.add("what's at schnitzelbar today");// closed
		
		runMainActivityWithTestInput(userInput);
		
		ArrayList<String> lineOneMeals = new ArrayList<String>();
		ArrayList<LineData> lines = new ArrayList<LineData>();
		//ArrayList<MealData> mls = new ArrayList<MealData>();
		Canteen curCanteen = new Canteen(new CanteenData(CanteenNames.ADENAUERRING, 0)); // 0 is for "today"
		
		/* test line one */
		for( MealData m : curCanteen.getCanteenData().getLines().get(0).getTodayMeals()) {
			lineOneMeals.add(m.getMealName());
		}
		boolean matched = false;
		for(String str : lineOneMeals) {
			if(nlgResults.get(2).contains(str)){
				matched = true;
				}else matched = false;
			}
		
		assertTrue(matched);
		
		/* test line two */
		/*// because line two in currently closed
		ArrayList<String> lineTwoMeals = new ArrayList<String>();
		for( MealData m : curCanteen.getCanteenData().getLines().get(1).getTodayMeals()) {
			lineTwoMeals.add(m.getMealName());
		}
		matched = false;
		for(String str : lineTwoMeals) {
			if(nlgResults.get(3).contains(str)){
				matched = true;
				}else matched = false;
			}
		assertTrue(matched);*/
		
		/* test line three */
		ArrayList<String> lineThreeMeals = new ArrayList<String>();
		for( MealData m : curCanteen.getCanteenData().getLines().get(2).getTodayMeals()) {
			lineThreeMeals.add(m.getMealName());
		}
		matched = false;
		for(String str : lineThreeMeals) {
			if(nlgResults.get(3).contains(str)){
				matched = true;
				}else matched = false;
			}
		assertTrue(matched);
		
		/* test line four */
		ArrayList<String> lineFourMeals = new ArrayList<String>();
		for( MealData m : curCanteen.getCanteenData().getLines().get(3).getTodayMeals()) {
			lineFourMeals.add(m.getMealName());
		}
		matched = false;
		for(String str : lineFourMeals) {
			if(nlgResults.get(4).contains(str)){
				matched = true;
				}else matched = false;
			}
		assertTrue(matched);
		
		/* test line six */
		ArrayList<String> lineSixMeals = new ArrayList<String>();
		for( MealData m : curCanteen.getCanteenData().getLines().get(8).getTodayMeals()) {
			lineSixMeals.add(m.getMealName());
		}
		matched = false;
		for(String str : lineSixMeals) {
			if(nlgResults.get(5).contains(str)){
				matched = true;
				}else matched = false;
			}
		assertTrue(matched);
		
		/* test line cafeteria */
		ArrayList<String> cafeMeals = new ArrayList<String>();
		for( MealData m : curCanteen.getCanteenData().getLines().get(6).getTodayMeals()) {
			cafeMeals.add(m.getMealName());
		}
		matched = false;
		for(String str : cafeMeals) {
			if(nlgResults.get(6).contains(str)){
				matched = true;
				}else matched = false;
			}
		assertTrue(matched);
		
		
		}
	
	    
	
	
	
	
	
	
   } 
	

