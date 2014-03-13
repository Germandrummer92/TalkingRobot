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
		
		this.runMainActivityWithTestInput(userInput);;

		assertTrue(dmResults.get(3) == null || dmResults.get(3).contains("pork"));
		
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
		
		assertTrue(dmResults.get(3) == null || dmResults.get(3).contains("fish"));
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
		
		assertTrue(dmResults.get(3) == null || dmResults.get(3).contains("cow"));
	}
	
}
