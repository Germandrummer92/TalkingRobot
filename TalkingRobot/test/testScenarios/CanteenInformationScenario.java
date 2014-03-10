package testScenarios;


import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.joda.time.LocalDate;
import org.junit.Test;

import data.CanteenData;
import data.CanteenNames;
import dm.Canteen;

/**
 * 
 * @author Meng Meng Yan
 * @version 1.0
 */
public class CanteenInformationScenario extends basicTestCases.BasicTest {
	
	@Test
	public void CITest () {
		userInput.add("hi");
		userInput.add("meng meng");
		userInput.add("can you inform me about the canteen");
		userInput.add("i want to eat at canteen adenauerring");
		userInput.add("what is at line four");
		
		runMainActivityWithoutConsoleOutput(userInput);
		
		userInput.add("i want to eat in the canteen tomorrow");
		
		String dmResultMeals = dmResults.get(3);
		String[] meals = ((String) dmResultMeals.subSequence(1, dmResultMeals.length() - 1)).split(";");
		int randomMeal = this.getRandomNum(meals.length);
		String[] chosenMeal = meals[randomMeal].split(" at ");
		
		ArrayList<String> mealsAtLineFour = new ArrayList<String>();
		for(int i = 0; i < meals.length; i++) {
			if(meals[i].contains("four")) {
				String[] mealAtLineFour = meals[i].split(" at ");
				mealsAtLineFour.add(mealAtLineFour[0]);
			}
		}
		
		userInput.add("at which line can i eat " + chosenMeal[0]);
		
		runMainActivityWithTestInput(userInput);
		
		LocalDate date = LocalDate.now();
		Canteen curCanteen = new Canteen(new CanteenData(CanteenNames.ADENAUERRING, 
				date.getDayOfWeek()));
		String tomorrowMeal1 = 
				curCanteen.getCanteenData().getLines().get(0).getTodayMeals().get(0).getMealName();
		String tomorrowMeal2 = 
				curCanteen.getCanteenData().getLines().get(3).getTodayMeals().get(0).getMealName();
		
		System.out.println(meals[0]);
		assertTrue(nlgResults.get(3).contains(meals[0])
				&& nlgResults.get(3).contains(meals[meals.length - 1]));
		assertTrue(nlgResults.get(4).contains(mealsAtLineFour.get(0))
				&& nlgResults.get(4).contains(mealsAtLineFour.get(mealsAtLineFour.size() - 1)));
		assertTrue(nlgResults.get(5).contains(tomorrowMeal1)
				&& nlgResults.get(5).contains(tomorrowMeal2));
	}
}
