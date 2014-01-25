package dm;
import data.CanteenData;
import data.MealCategoryData;
import java.util.ArrayList;
import data.LineData;
import data.MealData;

/**
 * This class represents a canteen in the system
 * @author Aleksandar Andonov
 * @version 1.1
 */
public class Canteen {

  private CanteenData canteenData;
  
  /**
   * Create a Canteen object with the given canteenData
   * @param canteenData
   */
  public Canteen(CanteenData canteenData) {
	  this.canteenData = canteenData;
  } 

public CanteenData getCanteenData() {
	return canteenData;
}

public void setCanteenData(CanteenData canteenData) {
	this.canteenData = canteenData;
}

/**
 * Returns a list of the lines from this canteen which offer meals from
 * the given category.
 * @param category The category which meals should be part of.
 * @return A list of lines serving meals from the given category.
 */
public ArrayList<LineData> linesForCategory(MealCategoryData category) {
	ArrayList<LineData> lines = new ArrayList<LineData>();
	for (LineData canteenLine : canteenData.getLines()) {
		for (MealData meal : canteenLine.getTodayMeals()) {
			if (meal.getMealCategory().equals(category)) {
				lines.add(canteenLine);
			}
		}
	}
	return lines;
}

/**
 * Returns a list of the meals from this canteen which are from
 * the given category.
 * @param category The category which meals should be part of.
 * @return A list of the meals from the given category.
 */
public ArrayList<MealData> mealsForCategory(MealCategoryData category) {
	ArrayList<MealData> meals = new ArrayList<MealData>();
	for (LineData canteenLine : canteenData.getLines()) {
		for (MealData meal : canteenLine.getTodayMeals()) {
			if (meal.getMealCategory().equals(category)) {
				meals.add(meal);
			}
		}
	}
	return meals;
}

  

}