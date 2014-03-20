package basicTestCases;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Test;

import data.MealData;
import data.CanteenData;
import data.CanteenNames;
import dm.Canteen;

/**
 * 
 * @author Bettina Weller
 * @version 1.0
 * A test class for canteen information.
 */
public class CanteenInformationTest extends BasicTest{
	
	/**
	 * tests for meals at line one in canteen adenauerring.
	 * everything in one sentence mentioned.
	 */
	@Test
	public void lineOneTodayAdenExpl() {
		LinkedList<String> meals = new LinkedList<String>();
		LinkedList<String> price = new LinkedList<String>();
		Canteen curCanteen = new Canteen(new CanteenData(CanteenNames.ADENAUERRING, 0));// 0 for "today"
		for (MealData m : curCanteen.getCanteenData().getLines().get(0).getTodayMeals()) {
			meals.add(m.getMealName().toLowerCase());
			price.add(m.getS_price().toString());
		}
		
		userInput.add("hello");
		userInput.add("i am bettina");
		userInput.add("what can i eat at line one in canteen adenauerring");
		userInput.add("how much is " + meals.get(0));
		
		this.runMainActivityWithTestInput(userInput);
		
		for (int i = 0; i < meals.size(); i++) {
			assertTrue(nlgResults.get(2).toLowerCase().contains(meals.get(i)));
		}
		if (meals.size() > 0) {
			assertTrue(nlgResults.get(3).contains(price.get(0)));
		}
	}
	
	/**
	 * tests for meals at line one in canteen moltke.
	 * everything in one sentence mentioned.
	 */
	@Test
	public void lineOneTodayMoltkeExpl() {
		LinkedList<String> meals = new LinkedList<String>();
		LinkedList<String> price = new LinkedList<String>();
		Canteen curCanteen = new Canteen(new CanteenData(CanteenNames.MOLTKE, 0));// 0 for "today"
		for (MealData m : curCanteen.getCanteenData().getLines().get(0).getTodayMeals()) {
			meals.add(m.getMealName().toLowerCase());
			price.add(m.getS_price().toString());
		}
		
		userInput.add("hello");
		userInput.add("i am bettina");
		userInput.add("what can i eat at line one in canteen Moltke");
		userInput.add("what's the price of " + meals.get(0));
		
		this.runMainActivityWithTestInput(userInput);
		
		for (int i = 0; i < meals.size(); i++) {
			assertTrue(nlgResults.get(2).toLowerCase().contains(meals.get(i)));
		}
		if (meals.size() > 0) {
			assertTrue(nlgResults.get(3).contains(price.get(0)));
		}
	}
	
	/**
	 * tests for meals at line one in canteen adenauerring tomorrow
	 * everything in one sentence mentioned.
	 */
	@Test
	public void lineOneTomorrowAdenExpl() {
		LinkedList<String> meals = new LinkedList<String>();
		LinkedList<String> price = new LinkedList<String>();
		Canteen curCanteen = new Canteen(new CanteenData(CanteenNames.ADENAUERRING, 1));// 1 for "tomorrow"
		for (MealData m : curCanteen.getCanteenData().getLines().get(0).getTodayMeals()) {
			meals.add(m.getMealName().toLowerCase());
			price.add(m.getS_price().toString());
		}
		
		userInput.add("hello");
		userInput.add("i am bettina");
		userInput.add("what can i eat at line one in canteen adenauerring tomorrow");
		userInput.add("whats the price of " + meals.get(0));

		this.runMainActivityWithTestInput(userInput);
		
		for (int i = 0; i < meals.size(); i++) {
			assertTrue(nlgResults.get(2).toLowerCase().contains(meals.get(i)));
		}
		if (meals.size() > 0) {
			assertTrue(nlgResults.get(3).contains(price.get(0)));
		}
	}
	
	/**
	 * tests for meals at line one in canteen moltke tomorrow.
	 * everything in one sentence mentioned.
	 */
	@Test
	public void lineOneTomorrowMoltkeExpl() {
		LinkedList<String> meals = new LinkedList<String>();
		LinkedList<String> price = new LinkedList<String>();
		Canteen curCanteen = new Canteen(new CanteenData(CanteenNames.MOLTKE, 1));// 1 for "tomorrow"
		for (MealData m : curCanteen.getCanteenData().getLines().get(0).getTodayMeals()) {
			meals.add(m.getMealName().toLowerCase());
			price.add(m.getS_price().toString());
		}
		
		userInput.add("hello");
		userInput.add("i am bettina");
		userInput.add("what can i eat at line one in canteen moltke tomorrow");
		userInput.add("how much is " + meals.get(0));
		
		this.runMainActivityWithTestInput(userInput);
		
		for (int i = 0; i < meals.size(); i++) {
			assertTrue(nlgResults.get(2).toLowerCase().contains(meals.get(i)));
		}
		if (meals.size() > 0) {
			assertTrue(nlgResults.get(3).contains(price.get(0)));
		}
	}
	
	/**
	 * ask for the location of line one in canteen adenauerring.
	 */
	@Test
	public void whereLineOneAdenExpl() {
		userInput.add("hello");
		userInput.add("i am bettina");
		userInput.add("where is line six");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue(nlgResults.get(2).contains("the entrance on right hand side from main entrance")
				&& nlgResults.get(2).contains("stairs there to go up"));
	}

}
