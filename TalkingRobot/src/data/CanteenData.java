package data;

import java.util.ArrayList;

import org.json.JSONObject;

public class CanteenData implements Data {

  private Integer canteenID;

  private String canteenName;

  private String address;

  private ArrayList<LineData> lines;

  private JSONObject canteenJSON;

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