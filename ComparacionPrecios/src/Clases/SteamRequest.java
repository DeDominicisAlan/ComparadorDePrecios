package Clases;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class SteamRequest {
	private HttpResponse<JsonNode> response;

	public SteamRequest(String steamApiUrl) {
		this.response = Unirest.get(steamApiUrl).asJson();
	}
	
	public boolean isSuccess() {
		return response.isSuccess();
	}
	
	public JsonNode getJsonResponse() {
		return response.getBody();
	}
	
	public JSONObject getJsonObject() {
		return this.getJsonResponse().getObject();
	}
	
	public JSONArray getJSONArray(){
    	return this.getJsonObject().getJSONArray("items");
	}
	
	
}
