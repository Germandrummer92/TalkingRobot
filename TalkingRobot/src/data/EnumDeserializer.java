/**
 * 
 */
package data;

import java.lang.reflect.Type;

import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import dm.CanteenInfo;
import dm.CanteenRecom;
import dm.KitchenAssistance;
import dm.RecipeAssistance;
import dm.RecipeLearning;
import dm.Start;

/**
 * @author Daniel Draper
 * @version 1.0
 *	This class deserializes the generic Enum that's part of the DialogState class that is part of the JSON-serializable KeywordData.
 */
public class EnumDeserializer implements JsonDeserializer<Enum<?>> {

	/** 
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public Enum<?> deserialize(JsonElement arg0, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		if (((JsonObject)arg0).get("name").toString().contains("S_")) {
			Start[] start = Start.values();
			for (Start s : start)
			{	
				if (s.name().equals(((JsonObject)arg0).get("name").toString().subSequence(1, ((JsonObject)arg0).get("name").toString().length()-1))) {
					
					return s;
				}
			}
		}
		if (((JsonPrimitive)arg0).toString().contains("CI_")) {
		
			CanteenInfo[] e = CanteenInfo.values();
			for (CanteenInfo c : e)
			{	
				if (c.name().equals(((JsonPrimitive)arg0).toString().substring(1, ((JsonPrimitive)arg0).toString().length()-1))) {
					return c;
				}
			}
		}
		if (((JsonPrimitive)arg0).toString().contains("CR_")) {
			CanteenRecom[] e1 = CanteenRecom.values();
			for (CanteenRecom c : e1)
			{	
				if (c.name().equals(((JsonPrimitive)arg0).toString().substring(1, ((JsonPrimitive)arg0).toString().length()-1))) {
					return c;
				}
			}
		}
		if (((JsonPrimitive)arg0).toString().contains("KA_")) {
			KitchenAssistance[] e11 = KitchenAssistance.values();
			for (KitchenAssistance c : e11)
			{	
				if (c.name().equals(((JsonPrimitive)arg0).toString().substring(1, ((JsonPrimitive)arg0).toString().length()-1))) {
					return c;
				}
			}
		}
		if (((JsonPrimitive)arg0).toString().contains("RA_")) {
			RecipeAssistance[] e111 = RecipeAssistance.values();
			for (RecipeAssistance c : e111)
			{	
				if (c.name().equals(((JsonPrimitive)arg0).toString().substring(1, ((JsonPrimitive)arg0).toString().length()-1))) {
					return c;
				}
			}
		}
		if (((JsonPrimitive)arg0).toString().contains("RL_")) {
			RecipeLearning[] e1111 = RecipeLearning.values();
			for (RecipeLearning c : e1111)
			{	
				if (c.name().equals(((JsonPrimitive)arg0).toString().substring(1, ((JsonPrimitive)arg0).toString().length()-1))) {
					return c;
				}
			}
		}
		throw new JsonParseException("Wrong kind of Enum");
	}

}
