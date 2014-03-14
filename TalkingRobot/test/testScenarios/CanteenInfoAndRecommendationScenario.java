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
 * @version 1.5
 */
public class CanteenInfoAndRecommendationScenario  extends basicTestCases.BasicTest {
	
	@Test
	public void CIandCRTest() {
		userInput.add("hi");
		userInput.add("xizhe");
		userInput.add("what is at line one today");
		userInput.add("well i do not like these meals is there any suggestion");
		userInput.add("i want to have something vegetarian");
		userInput.add("where is line six");
		
		runMainActivityWithTestInput(userInput);
		
		
		ArrayList<String> mealsAtLineOne = new ArrayList<String>();
		
		
		Canteen curCanteen = new Canteen(new CanteenData(CanteenNames.ADENAUERRING, 0));// 0 for "today"
		
		for( MealData m : curCanteen.getCanteenData().getLines().get(0).getTodayMeals()) {
			mealsAtLineOne.add(m.getMealName());
		}
		
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
				&& nlgResults.get(2).contains(mealsAtLineOne.get(1)));
		
		assertTrue( dmResults.get(4).contains("veg"));
		
		assertTrue(nlgResults.get(5).contains("line six") && nlgResults.get(5).contains("right"));
		

	}
}