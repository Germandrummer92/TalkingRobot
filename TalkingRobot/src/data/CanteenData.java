package data;

import java.util.ArrayList;

import org.json.JSONObject;

/**
*
* @author Meng Meng Yan
* @version 1.0
* This Class represents the data for a canteen.
* @see Data
*/
public class CanteenData implements Data {

  	private Integer canteenID;
  	private String canteenName;
	private String address;
  	private ArrayList<LineData> lines;
	private JSONObject canteenJSON;
  
  
	/**
	* Creates a new CanteenData object.
	* @param canteenID ID of the new Canteen
	* @param canteenName Name of the new Canteen
	* @param address Adress of the new Canteen
	* @param lines Lines of the new Canteen
	*/
	public CanteenData(int canteenID, String canteenName, String address, ArrayList<LineData> lines) {
		this.canteenID = canteenID;
		this.canteenName = canteenName;
		this.address = adress;
		this.lines = lines;
	}


	@Override
	 /**
	* @see Data#generateJSON()
	*/
	public String generateJSON() {
	 	Gson creator;
         	creator = new Gson();
         	return creator.toJson(this);
	}

	@Override
	/**
         * @see Data#createFromJSONText(String jsonString)
         */
	public void createFromJSONText(String jsonString) {
		Gson creator;
	        creator = new Gson();
	        CanteenData newData = creator.fromJson(jsonString, this.getClass());
	        canteenID = newData.getCanteenID();
	        canteenName = newData.getCanteenName();
	        adress = newData.getAdress();
	        lines = newData.getLines();
	}

	@Override
	/**
         * @see Data#writeFile()
         */
	public void writeFile() {
		String pathname = "resources/files/CanteenData/" + canteenID + ".json";
	        PrintWriter writer;
	        try {
	                writer = new PrintWriter(pathname, "UTF-8");
	                writer.println(this.generateJSON());
	                writer.close();
	        } catch (FileNotFoundException | UnsupportedEncodingException e) {
	                e.printStackTrace();
	        }
	}

	@Override
	/**
         * @see Data#getJson()
         */
	public JSONObject getJson() {
		JSONObject object = null;
	        try {
	                object = new JSONObject(this.generateJSON());
	        } catch (JSONException e) {
	                e.printStackTrace();
	        }
	        return object;
	}

	/**
         * @return the canteenID
         */
        public Integer getCanteenID() {
                return canteenID;
        }
        
        /**
         * @return the canteenName
         */
        public String getCanteenName() {
                return canteenName;
        }
        
        /**
         * @return the adress
         */
        public String getAddress() {
        	return adress;
        }
  
  	/**
         * @return the lines
         */
	public ArrayList<LineData> getLines() {
		return lines;
	}
	
	/**
         * @param canteenID the canteenID to set
         */
	public void setCanteenID (Integer canteenID) {
		this.canteenID = canteenID;
	}
	
	/**
         * @param canteenName the canteenName to set
         */
	public void setCanteenName (String canteenName) {
		this.canteenName = canteenName;
	}
	
	/**
         * @param adress the adress to set
         */
	public void setAddress (String address) {
		this.adress = adress;
	}
	
	/**
         * @param lines the lines to set
         */
	public void setLines (ArrayList<LineData> lines) {
		this.lines = lines;
	}
	
	/**
         * @param canteenJSON the canteenJSON to set
         */
	public void setJSON (JSONObject canteenJSON) {
		this.canteenJSON = canteenJSON;
	}
}
