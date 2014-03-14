package coverageTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.joda.time.LocalDate;
import org.junit.Test;

import data.CanteenData;
import data.CanteenNames;
import data.LineData;
import data.MealData;
import dm.Canteen;

/**
 * cover more states in CI
 * @author Xizhe Lian
 * @version 1.0
 */
public class CICoverageTest extends basicTestCases.BasicTest {
	
	/**
	 * test ouput when user ask for generall informations about canteen
	 */
	@Test
	public void adenGeneralMealsInfoTest() {
		userInput.add("hi");
		userInput.add("xizhe");
		userInput.add("what's in canteen adennauerring today");
		userInput.add("what's in canteen adennauerring tomorrow");
		userInput.add("what's in canteen adennauerring the day after tomorrow");
		
		runMainActivityWithTestInput(userInput);
		
		ArrayList<String> meals = new ArrayList<String>();
		Canteen curCanteen = new Canteen(new CanteenData(CanteenNames.ADENAUERRING, 0)); // 0 is for "today"
		Canteen tmrCanteen = new Canteen(new CanteenData(CanteenNames.ADENAUERRING, 1)); // 1 for "tomorrow"
		Canteen datCanteen = new Canteen(new CanteenData(CanteenNames.ADENAUERRING, 2)); // 2 for "the day after tomorrow"
		
		ArrayList<LineData> lines = new ArrayList<LineData>();
		ArrayList<MealData> mls = new ArrayList<MealData>();
		LocalDate date = LocalDate.now();
		int dayOfWeek = date.getDayOfWeek();
		if(dayOfWeek == 6 || dayOfWeek == 0) {// it's weekend
			assertTrue(nlgResults.get(2).contains("closed") 
					||nlgResults.get(2).contains("weekend"));
		}else { // normal working day
			for( LineData l : curCanteen.getCanteenData().getLines()) {
				lines.add(l);
			}
			
			for( LineData l : lines) {
				for( MealData m : l.getTodayMeals()) {
					mls.add(m);
				} 
			}
			
			for(MealData m : mls) {
				meals.add(m.getMealName());
				
			}
			
			boolean mealMatched = false;
			for(String str : meals) {
				if(nlgResults.get(2).contains(str)){
					mealMatched = true;
				}else mealMatched = false;
			}
			assertTrue(mealMatched);
			
		}
		
		if((dayOfWeek + 1) % 7 == 6 
				|| (dayOfWeek + 1) % 7 == 0) { // "tomorrow" is weekend
			assertTrue(nlgResults.get(3).contains("closed") 
					||nlgResults.get(3).contains("weekend"));
		}else { // normal working day
			for( LineData l : tmrCanteen.getCanteenData().getLines()) {
				lines.add(l);
			}
			
			for( LineData l : lines) {
				for( MealData m : l.getTodayMeals()) {
					mls.add(m);
				} 
			}
			
			for(MealData m : mls) {
				meals.add(m.getMealName());
				
			}
			
			boolean mealMatched = false;
			for(String str : meals) {
				if(nlgResults.get(3).contains(str)){
					mealMatched = true;
				}else mealMatched = false;
			}
			assertTrue(mealMatched);
		}
		
		if((dayOfWeek + 2) % 7 == 6 
				|| (dayOfWeek + 2) % 7 == 0) { // "the day after tomorrow" is weekend
			assertTrue(nlgResults.get(4).contains("closed") 
					||nlgResults.get(4).contains("weekend"));
		}else { // normal working day
			for( LineData l : datCanteen.getCanteenData().getLines()) {
				lines.add(l);
			}
			
			for( LineData l : lines) {
				for( MealData m : l.getTodayMeals()) {
					mls.add(m);
				} 
			}
			
			for(MealData m : mls) {
				meals.add(m.getMealName());
				
			}
			
			boolean mealMatched = false;
			for(String str : meals) {
				if(nlgResults.get(4).contains(str)){
					mealMatched = true;
				}else mealMatched = false;
			}
			assertTrue(mealMatched);
		}
	}
	
	/**
	 * test output when user asks info about meals in next week
	 */
	@Test
	public void adenGeneralMealsForNextWeekInfoTest() {
		userInput.add("hi");
		userInput.add("xizhe");
		userInput.add("what's in canteen adennauerring next monday");
		userInput.add("what's in canteen adennauerring next tuesday");
		userInput.add("what's in canteen adennauerring next wednesday");
		userInput.add("what's in canteen adennauerring next thursday");
		userInput.add("what's in canteen adennauerring next friday");
		
		runMainActivityWithTestInput(userInput);
		
		ArrayList<String> meals = new ArrayList<String>();
		
		ArrayList<LineData> lines = new ArrayList<LineData>();
		ArrayList<MealData> mls = new ArrayList<MealData>();
		LocalDate date = LocalDate.now();
		int dayOfWeek = date.getDayOfWeek();
		int dayShift = (1 - dayOfWeek) + 7; // this is dayshift for "next monday"
		
		Canteen monCanteen = new Canteen(new CanteenData(CanteenNames.ADENAUERRING, dayShift));
		Canteen tueCanteen = new Canteen(new CanteenData(CanteenNames.ADENAUERRING, dayShift + 1));
		Canteen wedCanteen = new Canteen(new CanteenData(CanteenNames.ADENAUERRING, dayShift + 2));
		Canteen thuCanteen = new Canteen(new CanteenData(CanteenNames.ADENAUERRING, dayShift + 3));
		Canteen friCanteen = new Canteen(new CanteenData(CanteenNames.ADENAUERRING, dayShift + 4));
		
		/* test next Monday*/
		for( LineData l : monCanteen.getCanteenData().getLines()) {
			lines.add(l);
		}
		
		for( LineData l : lines) {
			for( MealData m : l.getTodayMeals()) {
				mls.add(m);
			} 
		}
		
		for(MealData m : mls) {
			meals.add(m.getMealName());
			
		}
	
		boolean mealMatched = false;
		for(String str : meals) {
			if(nlgResults.get(2).contains(str)){
				mealMatched = true;
			}else mealMatched = false;
		}
		assertTrue(mealMatched);	
		
		/* test for next Tuesday */
		for( LineData l : tueCanteen.getCanteenData().getLines()) {
			lines.add(l);
		}
		
		for( LineData l : lines) {
			for( MealData m : l.getTodayMeals()) {
				mls.add(m);
			} 
		}
		
		for(MealData m : mls) {
			meals.add(m.getMealName());
			
		}

		mealMatched = false;
		for(String str : meals) {
			if(nlgResults.get(3).contains(str)){
				mealMatched = true;
			}else mealMatched = false;
		}
		assertTrue(mealMatched);	
	
		/* test next Wednesday */
		for( LineData l : wedCanteen.getCanteenData().getLines()) {
			lines.add(l);
		}
		
		for( LineData l : lines) {
			for( MealData m : l.getTodayMeals()) {
				mls.add(m);
			} 
		}
		
		for(MealData m : mls) {
			meals.add(m.getMealName());
			
		}

		mealMatched = false;
		for(String str : meals) {
			if(nlgResults.get(4).contains(str)){
				mealMatched = true;
			}else mealMatched = false;
		}
		assertTrue(mealMatched);	

		/* test next Thursday */
		for( LineData l : thuCanteen.getCanteenData().getLines()) {
			lines.add(l);
		}
		
		for( LineData l : lines) {
			for( MealData m : l.getTodayMeals()) {
				mls.add(m);
			} 
		}
		
		for(MealData m : mls) {
			meals.add(m.getMealName());
			
		}

		mealMatched = false;
		for(String str : meals) {
			if(nlgResults.get(5).contains(str)){
				mealMatched = true;
			}else mealMatched = false;
		}
		assertTrue(mealMatched);	
		
		/* test next friday */
		for( LineData l : friCanteen.getCanteenData().getLines()) {
			lines.add(l);
		}
		
		for( LineData l : lines) {
			for( MealData m : l.getTodayMeals()) {
				mls.add(m);
			} 
		}
		
		for(MealData m : mls) {
			meals.add(m.getMealName());
			
		}

		mealMatched = false;
		for(String str : meals) {
			if(nlgResults.get(6).contains(str)){
				mealMatched = true;
			}else mealMatched = false;
		}
		assertTrue(mealMatched);	


	}
	
	/**
	 * general informations in canteen moltke street
	 */
	@Test
	public void molteGeneralMealsInfoTest() {
		userInput.add("hi");
		userInput.add("xizhe");
		userInput.add("what's in canteen moltke today");
		userInput.add("what's in canteen moltke tomorrow");
		userInput.add("what's in canteen moltke the day after tomorrow");
		
		runMainActivityWithTestInput(userInput);
		
		ArrayList<String> meals = new ArrayList<String>();
		Canteen curCanteen = new Canteen(new CanteenData(CanteenNames.MOLTKE, 0)); // 0 is for "today"
		Canteen tmrCanteen = new Canteen(new CanteenData(CanteenNames.MOLTKE, 1)); // 1 for "tomorrow"
		Canteen datCanteen = new Canteen(new CanteenData(CanteenNames.MOLTKE, 2)); // 2 for "the day after tomorrow"
		
		ArrayList<LineData> lines = new ArrayList<LineData>();
		ArrayList<MealData> mls = new ArrayList<MealData>();
		LocalDate date = LocalDate.now();
		int dayOfWeek = date.getDayOfWeek();
		if(dayOfWeek == 6 || dayOfWeek == 0) {// it's weekend
			assertTrue(nlgResults.get(2).contains("closed") 
					||nlgResults.get(2).contains("weekend"));
		}else { // normal working day
			for( LineData l : curCanteen.getCanteenData().getLines()) {
				lines.add(l);
			}
			
			for( LineData l : lines) {
				for( MealData m : l.getTodayMeals()) {
					mls.add(m);
				} 
			}
			
			for(MealData m : mls) {
				meals.add(m.getMealName());
				
			}
			
			boolean mealMatched = false;
			for(String str : meals) {
				if(nlgResults.get(2).contains(str)){
					mealMatched = true;
				}else mealMatched = false;
			}
			assertTrue(mealMatched);
			
		}
		
		if((dayOfWeek + 1) % 7 == 6 
				|| (dayOfWeek + 1) % 7 == 0) { // "tomorrow" is weekend
			assertTrue(nlgResults.get(3).contains("closed") 
					||nlgResults.get(3).contains("weekend"));
		}else { // normal working day
			for( LineData l : tmrCanteen.getCanteenData().getLines()) {
				lines.add(l);
			}
			
			for( LineData l : lines) {
				for( MealData m : l.getTodayMeals()) {
					mls.add(m);
				} 
			}
			
			for(MealData m : mls) {
				meals.add(m.getMealName());
				
			}
			
			boolean mealMatched = false;
			for(String str : meals) {
				if(nlgResults.get(3).contains(str)){
					mealMatched = true;
				}else mealMatched = false;
			}
			assertTrue(mealMatched);
		}
		
		if((dayOfWeek + 2) % 7 == 6 
				|| (dayOfWeek + 2) % 7 == 0) { // "the day after tomorrow" is weekend
			assertTrue(nlgResults.get(4).contains("closed") 
					||nlgResults.get(4).contains("weekend"));
		}else { // normal working day
			for( LineData l : datCanteen.getCanteenData().getLines()) {
				lines.add(l);
			}
			
			for( LineData l : lines) {
				for( MealData m : l.getTodayMeals()) {
					mls.add(m);
				} 
			}
			
			for(MealData m : mls) {
				meals.add(m.getMealName());
				
			}
			
			boolean mealMatched = false;
			for(String str : meals) {
				if(nlgResults.get(4).contains(str)){
					mealMatched = true;
				}else mealMatched = false;
			}
			assertTrue(mealMatched);
		}
	}
		

}
