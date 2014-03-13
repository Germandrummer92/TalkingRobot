package testScenarios;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.joda.time.LocalDate;
import org.junit.Test;

import data.CanteenData;
import data.CanteenNames;
import data.LineData;
import data.MealData;
import dm.Canteen;

/**
 * Test scenario based on functional specification 9.1.C, mainly about CanteenInformation and Recommandation
 * @author Xizhe Lian
 * @version 1.0
 */
public class CanteenInfoAndRecommendationScenario  extends basicTestCases.BasicTest {
	
	@Test
	public void CIandCRTest() {
		userInput.add("hi");
		userInput.add("xixi");
		userInput.add("what is at line one today");
		userInput.add("well i do not like these meals is there any suggestion");
		userInput.add("i want to have something vegetarian");
		userInput.add("yes i will take that");
		userInput.add("where is line three");
		
		runMainActivityWithTestInput(userInput);
		
		String dmResultMeals = dmResults.get(2);
		String[] meals = ((String) dmResultMeals.subSequence(1, dmResultMeals.length() - 1)).split(";");
		
		ArrayList<String> mealsAtLineOne = new ArrayList<String>();
		for(int i = 0; i < meals.length; i++) {
			if(meals[i].contains("one")) {
				String[] mealAtLineOne = meals[i].split(" at ");
				mealsAtLineOne.add(mealAtLineOne[0]);
			}
		}
		
		LocalDate date = LocalDate.now();
		Canteen curCanteen = new Canteen(new CanteenData(CanteenNames.ADENAUERRING, 
				date.getDayOfWeek()));
		
		System.out.println(meals[0]);
		
		
		String suggestion = nlgResults.get(4);
		ArrayList<LineData> ld = new ArrayList<LineData>();
		ld = curCanteen.getCanteenData().getLines();
		String category = null;
		for( LineData l : ld) {
			for( MealData m : l.getTodayMeals()) {
				if(m.getMealName().equals(suggestion)){
					category = m.getMealCategory().toString();
				}
			}
		}
		
		assertTrue(nlgResults.get(2).contains(mealsAtLineOne.get(0))
				&& nlgResults.get(2).contains(mealsAtLineOne.get(mealsAtLineOne.size() - 1)));
		
		assertTrue(dmResults.get(4) == null || dmResults.get(4).contains("vegetarian"));
		
		assertTrue(nlgResults.get(6).contains("line three") && nlgResults.get(6).contains("turn right"));
		

	}
}