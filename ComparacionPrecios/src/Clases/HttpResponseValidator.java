package Clases;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;

public class HttpResponseValidator {
	private HttpResponse<JsonNode> response;

	public HttpResponseValidator(HttpResponse<JsonNode> response) {
		this.response = response;
	}
	
	public boolean isSuccess() {
		return response.isSuccess();
	}
	
	public HttpResponse<JsonNode> getResponse(){
		return response;
	}
	
	public void setResponse(HttpResponse<JsonNode> response) {
		this.response = response;
	}
	
}
