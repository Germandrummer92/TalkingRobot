/**
 * 
 */
package data;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * @author Daniel Draper
 * @version 1.0
 *
 */
public class EnumSerializer implements JsonSerializer<Enum<?>> {

	/** 
	 * @see com.google.gson.JsonSerializer#serialize(java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
	 */
	@Override
	public JsonElement serialize(Enum<?> arg0, Type arg1,
			JsonSerializationContext arg2) {
		JsonObject j = new JsonObject();
		j.addProperty("name", arg0.toString());
		return j;
	}

}
