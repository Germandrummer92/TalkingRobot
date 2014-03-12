package basicTestCases;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Test;

/**
 * 
 * @author Bettina Weller
 * @version 1.0
 * A test class for canteen recommendation.
 */
public class CanteenRecommendationTest extends BasicTest{
	
	private LinkedList<String> differentLines;
	
	/**
	 * recommend pork.
	 */
	@Test
	public void recommendPork() {
		userInput.add("hello");
		userInput.add("i am bettina");
		userInput.add("could you recommend me something to eat");
		userInput.add("i want pork");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue(mealNotFound(3) || containsLine(3));
		
	}
	
	/**
	 * recommend fish.
	 */
	@Test
	public void recommendFish() {
		userInput.add("hello");
		userInput.add("i am bettina");
		userInput.add("could you recommend me something to eat");
		userInput.add("i want fish");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue(mealNotFound(3) || containsLine(3));
	}
	
	/**
	 * recommend cow.
	 */
	@Test
	public void recommendCow() {
		userInput.add("hello");
		userInput.add("i am bettina");
		userInput.add("could you recommend me something to eat");
		userInput.add("i want cow");
		
		this.runMainActivityWithTestInput(userInput);
		
		assertTrue(mealNotFound(3) || containsLine(3));
	}
	
	private boolean mealNotFound(int position) {
		if(nlgResults.get(position).contains("no") || nlgResults.get(position).contains("not")
				|| nlgResults.get(position).contains("can't")) {
			return true;
		}
			return false;
	}
	
	/*
	 * Many tests use this as a reference. If there are new lines added to the system or old 
	 * lines are deleted this method needs to be changed accordingly.
	 */
	private void setDifferentLines() {
		differentLines = new LinkedList<String>();
		differentLines.add("cafeteria");
		differentLines.add("curry queen");
		differentLines.add("line one");
		differentLines.add("line two");
		differentLines.add("line three");
		differentLines.add("line four and five");
		differentLines.add("line six");
		differentLines.add("schnitzel bar");
		differentLines.add("action counter");
		differentLines.add("buffet");
		differentLines.add("choice one");
		differentLines.add("choice two");
		differentLines.add("good and cheap");
	}
	
	private boolean containsLine(int position) {
		for(int i = 0; i < differentLines.size(); i++) {
			if(nlgResults.get(position).contains(differentLines.get(i))) {
				return true;
			}
		}
		return false;
	}
}
