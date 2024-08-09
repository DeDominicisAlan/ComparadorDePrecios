package Clases;

import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class JsonResponseParser {
	private JsonNode jsonResponse;

    public JsonResponseParser(JsonNode jsonResponse) {
    	this.jsonResponse = jsonResponse;
    }
    
    public JSONObject getJsonObject() {
    	return jsonResponse.getObject();
    }
    
    public JSONArray getItems() {
    	if(getJsonObject().has("items"))
    		return getJsonObject().getJSONArray("items");
    	
    	return null;
    }
    
    public JSONObject getJSONObjectFirst() {
    	if(this.getItems() != null)
    		return this.getItems().getJSONObject(0);
    	
    	return null;
    }
    
}
