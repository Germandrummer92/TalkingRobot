/**
 * 
 */
package data;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * @author Daniel Draper
 * @version 1.0
 * Class needed to deserialize interface Data.
 */
public class DataDeserializer implements JsonDeserializer<Data> {

	/**
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public Data deserialize(JsonElement arg0, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
			Gson loader = new Gson();
		if (arg0.getAsJsonObject().has("userID")) {
			return loader.fromJson(arg0, UserData.class);
		}
		if (arg0.getAsJsonObject().has("canteenID")) {		
			return loader.fromJson(arg0, CanteenData.class);
		}
		if (arg0.getAsJsonObject().has("ingredientID")) {
			return loader.fromJson(arg0, IngredientData.class);
		}
		if (arg0.getAsJsonObject().has("wordID")) {
			return loader.fromJson(arg0, KeywordData.class);
		}
		if (arg0.getAsJsonObject().has("lineID")) {
			return loader.fromJson(arg0, LineData.class);
		}
		if (arg0.getAsJsonObject().has("mealCategoryID")) {
			return loader.fromJson(arg0, MealCategoryData.class);
		}
		if (arg0.getAsJsonObject().has("mealID")) {
			return loader.fromJson(arg0, MealData.class);
		}
		if (arg0.getAsJsonObject().has("recipeID")) {
			return loader.fromJson(arg0, RecipeData.class);
		}
		if (arg0.getAsJsonObject().has("robotID")) {
			return loader.fromJson(arg0, RobotData.class);
		}
		return null;
	}
}