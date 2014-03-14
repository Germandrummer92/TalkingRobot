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
 * Test ask meals in specific lines function
 * @author Xizhe Lian
 * @version 1.0
 */
public class CILinesInfoTest extends basicTestCases.BasicTest {
	
	/**
	 * Ask meals information about each lines 
	 */
	@Test
   public void adenAskMeals() {
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
	
	/**
	 * test ask student price function of each line
	 */
	@Test
	public void adenAskStudentPriceTest(){
		LocalDate date = LocalDate.now();
		int dayOfWeek = date.getDayOfWeek();
		boolean weekend = false;
		
		Canteen c3 = new Canteen(new CanteenData(CanteenNames.ADENAUERRING, 0));
		int dayShift = (dayOfWeek + 2) % 7;
		if(dayOfWeek == 0 || (dayOfWeek == 6)) { // weekends
			c3 = new Canteen(new CanteenData(CanteenNames.ADENAUERRING, dayShift)); 
			weekend = true;
		}
		
		userInput.add("hi");
		userInput.add("xizhe");
		
		if( !weekend ){
			userInput.add("what's in canteen today");
		}else {
			if(dayShift == 1) {
				userInput.add("what's in canteen next monday");
			}else userInput.add("what's in canteen next tuesday");
		}
		
		
		String meal = c3.getCanteenData().getLines().get(0).getTodayMeals().get(0).getMealName(); // line one
		userInput.add("how much is " + meal);
		// line two is closed now
		meal = c3.getCanteenData().getLines().get(2).getTodayMeals().get(0).getMealName();// line three
		userInput.add("how much costs " + meal);
		meal = c3.getCanteenData().getLines().get(3).getTodayMeals().get(0).getMealName();// line four
		userInput.add("what's the price of " + meal);
		meal = c3.getCanteenData().getLines().get(8).getTodayMeals().get(0).getMealName();// line six
		userInput.add("what is the price of " + meal);
		ArrayList<MealData> cafeMeals = c3.getCanteenData().getLines().get(9).getTodayMeals(); // cafeteria
		for( MealData m : cafeMeals ){
			meal = m.getMealName();
			userInput.add("how much is " + meal);
		}
		/* side dish test */
		userInput.add("how much is blattSalat");
		userInput.add("how much costs currywurst");
		userInput.add("how much is belgische pommes");
		userInput.add("how much is country potatoes");
		
		runMainActivityWithTestInput(userInput);
		
		String strAmount = String.valueOf(2.50);
		assertTrue(nlgResults.get(3).contains(strAmount));// line one price
		
		float price = c3.getCanteenData().getLines().get(2).getTodayMeals().get(0).getS_price();
		strAmount = String.valueOf(price);
		assertTrue( nlgResults.get(4).contains(strAmount) );
		
		price = c3.getCanteenData().getLines().get(3).getTodayMeals().get(0).getS_price();
		strAmount = String.valueOf(price);
		assertTrue(nlgResults.get(5).contains(strAmount));
		
		
		price = c3.getCanteenData().getLines().get(8).getTodayMeals().get(0).getS_price();
		strAmount = String.valueOf(price);
		assertTrue(nlgResults.get(6).contains(strAmount));
		
		boolean correct = false;
		
		for(int i = 1; i <= cafeMeals.size(); i++) {
			price = cafeMeals.get(i - 1).getS_price();
			strAmount = String.valueOf(price);
			if(nlgResults.get(i + 6).contains(strAmount)){
				correct = true;
			}else correct = false;
		}
		assertTrue(correct);
		
		strAmount = String.valueOf(0.75);
		//System.out.println(nlgResults.get(7 + cafeMeals.size()));
		assertTrue(nlgResults.get(7 + cafeMeals.size()).contains(strAmount)); // blatsalat is always 0.75
		
		strAmount = String.valueOf(1.7);
		assertTrue(nlgResults.get(8 + cafeMeals.size()).contains(strAmount));
		
		strAmount = String.valueOf(1.15);
		assertTrue(nlgResults.get(9 + cafeMeals.size()).contains(strAmount));
		
		strAmount = String.valueOf(0.9);
		assertTrue(nlgResults.get(10 + cafeMeals.size()).contains(strAmount));

	}
	
	/**
	 * test ask employee price function of each line
	 */
	@Test
	public void adenAskEmployeePriceTest(){
		LocalDate date = LocalDate.now();
		int dayOfWeek = date.getDayOfWeek();
		boolean weekend = false;
		
		Canteen c2 = new Canteen(new CanteenData(CanteenNames.ADENAUERRING, 0));
		int dayShift = (dayOfWeek + 2) % 7;
		if(dayOfWeek == 0 || (dayOfWeek == 6)) { // weekends
			c2 = new Canteen(new CanteenData(CanteenNames.ADENAUERRING, dayShift)); 
			weekend = true;
		}
		
		userInput.add("hi");
		userInput.add("random");
		userInput.add("yes");
		userInput.add("no");
		
		if( !weekend ){
			userInput.add("what's in canteen today");
		}else {
			if(dayShift == 1) {
				userInput.add("what's in canteen next monday");
			}else userInput.add("what's in canteen next tuesday");
		}
		
		
		String meal = c2.getCanteenData().getLines().get(0).getTodayMeals().get(0).getMealName(); // line one
		userInput.add("how much is " + meal);
		// line two is closed now
		meal = c2.getCanteenData().getLines().get(2).getTodayMeals().get(0).getMealName();// line three
		userInput.add("how much costs " + meal);
		meal = c2.getCanteenData().getLines().get(3).getTodayMeals().get(0).getMealName();// line four
		userInput.add("what's the price of " + meal);
		meal = c2.getCanteenData().getLines().get(8).getTodayMeals().get(0).getMealName();// line six
		userInput.add("what is the price of " + meal);
		
		/*ArrayList<MealData> cafeMeals = c2.getCanteenData().getLines().get(6).getTodayMeals(); // cafeteria
		for( MealData m : cafeMeals ){
			meal = m.getMealName();
			userInput.add("how much is " + meal);
		}*/
		/* side dish test */
		userInput.add("how much is blattSalat");
		//userInput.add("how much costs currywurst");
		//userInput.add("what's the price of belgische pommes");
		userInput.add("what is the price of country potatoes");
		
		runMainActivityWithTestInput(userInput);
		
		String strAmount = String.valueOf(3.65);
		assertTrue(nlgResults.get(5).contains(strAmount));// line one price
		
		float price = c2.getCanteenData().getLines().get(2).getTodayMeals().get(0).getE_price();
		strAmount = String.valueOf(price);
		assertTrue( nlgResults.get(6).contains(strAmount) );
		
		price = c2.getCanteenData().getLines().get(3).getTodayMeals().get(0).getE_price();
		strAmount = String.valueOf(price);
		assertTrue(nlgResults.get(7).contains(strAmount));
		
		
		price = c2.getCanteenData().getLines().get(8).getTodayMeals().get(0).getE_price();
		strAmount = String.valueOf(price);
		assertTrue(nlgResults.get(8).contains(strAmount));
		
		/*boolean correct = false;
		
		for(int i = 1; i <= cafeMeals.size(); i++) {
			price = cafeMeals.get(i - 1).getE_price();
			strAmount = String.valueOf(price);
			if(nlgResults.get(i + 8).contains(strAmount)){
				correct = true;
			}else correct = false;
		}
		assertTrue(correct);
		*/
		strAmount = String.valueOf(0.95); 
		//System.out.println(nlgResults.get(7 + cafeMeals.size()));
		assertTrue(nlgResults.get(9).contains(strAmount)); // blattsalat is always 0.75
		
		/*// no record for curry wurst employee price
		strAmount = String.valueOf(1.7);
		assertTrue(nlgResults.get(10 + cafeMeals.size()).contains(strAmount));
		
		strAmount = String.valueOf(1.15);// belgische pommes
		assertTrue(nlgResults.get(10 + cafeMeals.size()).contains(strAmount));*/
		
		strAmount = String.valueOf(1.1);// country potatoes
		assertTrue(nlgResults.get(10).contains(strAmount));

	}
	
	/**
	 * test ask line location function
	 */
	@Test 
	public void lineLocationTest() {
		LocalDate date = LocalDate.now();
		int dayOfWeek = date.getDayOfWeek();
		boolean weekend = false;
		
		Canteen c1 = new Canteen(new CanteenData(CanteenNames.ADENAUERRING, 0));
		int dayShift = (dayOfWeek + 2) % 7;
		if(dayOfWeek == 0 || (dayOfWeek == 6)) { // weekends
			c1 = new Canteen(new CanteenData(CanteenNames.ADENAUERRING, dayShift)); 
			weekend = true;
		}
		
		userInput.add("hi");
		userInput.add("xizhe");
		
		if( !weekend ){
			userInput.add("what's in canteen today");
		}else {
			if(dayShift == 1) {
				userInput.add("what's in canteen next monday");
			}else userInput.add("what's in canteen next tuesday");
		}
		
		userInput.add("where is line one");
		userInput.add("where is line two");
		userInput.add("where is line three");
		userInput.add("where is line four");
		userInput.add("where is line five");
		userInput.add("where is line six");
		userInput.add("where is schnitzelbar");
		userInput.add("where is cafeteria");
		userInput.add("where is curry queen");
		
		runMainActivityWithTestInput(userInput);
		
		assertTrue(nlgResults.get(3).contains("line one") && nlgResults.get(3).contains("left"));
		assertTrue(nlgResults.get(4).contains("line two") && nlgResults.get(4).contains("left"));
		assertTrue(nlgResults.get(5).contains("line three") && nlgResults.get(5).contains("right"));
		assertTrue(nlgResults.get(6).contains("line four") && nlgResults.get(6).contains("right"));
		assertTrue(nlgResults.get(7).contains("line five") && nlgResults.get(7).contains("right"));
		assertTrue(nlgResults.get(8).contains("line six") && nlgResults.get(8).contains("right"));
		assertTrue(nlgResults.get(9).contains("schnitzelbar") && nlgResults.get(9).contains("dinning hall"));
		assertTrue(nlgResults.get(10).contains("cafeteria") && nlgResults.get(10).contains("pass by"));
		assertTrue(nlgResults.get(11).contains("curry queen") && nlgResults.get(11).contains("slope"));
		
	}
	
	
	
   } 
	

