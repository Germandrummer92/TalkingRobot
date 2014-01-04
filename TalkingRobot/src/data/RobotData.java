package data;

import org.json.JSONObject;

public class RobotData implements Data {

  private Integer robotID;

  private String robotName;

  private Boolean isInKitchen;

  private JSONObject robotJSON;

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