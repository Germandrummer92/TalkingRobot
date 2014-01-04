package data;
import org.json.*;

public interface Data {

  public String generateJSON();

  public void createFromJSONText(String jsonString);

  public void writeFile();

  public JSONObject getJson();

}