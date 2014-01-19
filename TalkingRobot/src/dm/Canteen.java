package dm;
import java.util.List;
import data.CanteenData;
import data.MealCategoryData;

/**
 * This class represents a canteen in the system
 * @author Aleksandar Andonov
 * @version 1.0
 */
public class Canteen {

  private List<MealCategoryData> mealCategoryData;

  private CanteenData canteenData;
  
  /**
   * Create a Canteen object with the given canteenData
   * @param canteenData
   */
  public Canteen(CanteenData canteenData) {
	  //TODO Maybe parse the whole canteen object from the json provided by the canteen
	  //Still need description of file
	  this.canteenData = canteenData;
	  mealCategoryData = MealCategoryData.loadData();
  } //load the canteen Data automatically?

public List<MealCategoryData> getMealCategoryData() {
	return mealCategoryData;
}

public void setMealCategoryData(List<MealCategoryData> mealCategoryData) {
	this.mealCategoryData = mealCategoryData;
}

public CanteenData getCanteenData() {
	return canteenData;
}

public void setCanteenData(CanteenData canteenData) {
	this.canteenData = canteenData;
}

  

}