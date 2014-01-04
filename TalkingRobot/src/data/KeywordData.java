package data;
import org.json.JSONObject;

import dm.DialogState;

public class KeywordData implements Data {

  private Integer wordID;

  private String word;

  private DialogState dialogState;

  private JSONObject keywordJSON;

  public Integer priority;

  public Data dataReference;

@Override
public String generateJSON() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void createFromJSONText(String jsonString) {
	// TODO Auto-generated method stub
	
}

@Override
public void writeFile() {
	// TODO Auto-generated method stub
	
}

@Override
public JSONObject getJson() {
	// TODO Auto-generated method stub
	return null;
}

}