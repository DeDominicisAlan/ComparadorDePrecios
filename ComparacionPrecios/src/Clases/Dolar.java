package Clases;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

//Cotizacion del precio sacada de DolarApi.com

public class Dolar {

	private Double precio;
	
	public Dolar() {
		setReponse();
	}
	
	private void setReponse() {
		
		HttpResponse<JsonNode> response = Unirest.get("https://dolarapi.com/v1/dolares/oficial").asJson();
		HttpResponseValidator httpResponseValidator = new HttpResponseValidator(response);
				
		if(httpResponseValidator.isSuccess()) {
			JsonResponseParser body = new JsonResponseParser(httpResponseValidator.getResponse().getBody());
			precio = (Double) body.getJsonObject().get("venta");
		}else {
			throw new RuntimeException("Error en la solicitud HTTP: " + response.getStatus());
		}
		
	
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	
	
	/*public HttpResponse<JsonNode> getResponse() {
		if(http.isSuccess()){
			http.getResponse();
		}
		return null;
	}*/
	
	
	
}
