/**
 * 
 */
package data;

import java.lang.reflect.Type;

import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import dm.CanteenInfo;
import dm.CanteenRecom;
import dm.KitchenAssistance;
import dm.RecipeAssistance;
import dm.RecipeLearning;
import dm.Start;

/**
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class EnumDeserializer implements JsonDeserializer<Enum<?>> {

	/* (non-Javadoc)
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public Enum<?> deserialize(JsonElement arg0, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		if (arg1.equals(dm.Start.class)) {
			Start[] start = Start.values();
			for (Start s : start)
			{	
				if (s.equals(arg0.get))
					return s;
			}
		}
		if (arg1.equals(dm.CanteenInfo.class)) {
		
			CanteenInfo[] e = CanteenInfo.values();
			for (CanteenInfo c : e)
			{	
				if (c.equals(arg0.getAsString()))
					return c;
			}
		}
		if (arg1.equals(dm.CanteenRecom.class)) {
			CanteenRecom[] e1 = CanteenRecom.values();
			for (CanteenRecom c : e1)
			{	
				if (c.equals(arg0.getAsString()))
					return c;
			}
		}
		if (arg1.equals(dm.KitchenAssistance.class)) {
			KitchenAssistance[] e11 = KitchenAssistance.values();
			for (KitchenAssistance c : e11)
			{	
				if (c.equals(arg0.getAsString()))
					return c;
			}
		}
		if (arg1.equals(dm.RecipeAssistance.class)) {
			RecipeAssistance[] e111 = RecipeAssistance.values();
			for (RecipeAssistance c : e111)
			{	
				if (c.equals(arg0.getAsString()))
					return c;
			}
		}
		if (arg1.equals(dm.RecipeLearning.class)) {
			RecipeLearning[] e1111 = RecipeLearning.values();
			for (RecipeLearning c : e1111)
			{	
				if (c.equals(arg0.getAsString()))
					return c;
			}
		}
		return null;
	}

}
