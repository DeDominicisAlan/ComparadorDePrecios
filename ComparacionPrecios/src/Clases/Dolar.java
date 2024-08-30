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
			precio = (Integer) body.getJsonObject().get("venta") * 1.0;
			
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

	/**
	 * Calcula cuanto cuesta "X" cantidad de dolares a peso argentino
	 * @param Cantidad de dolares a calcular en pesos
	 * @return La cantidad de dolares en pesos
	 */
	public Double getCompra(double cant) {
		return cant * this.precio;
	}
	
	/**
	 * Calcula, por ejemplo, una compra con impuestos con tarjeta, que son un el precio final mas 60% de impuestos
	 * @param La cantidad de dolares a calcular
	 * @return El precio final con impuestos en pesos
	 */
	public Double getCompraConImpuestosPesos(double cant) {
		return getCompra(cant) + (getCompra(cant) * 0.64);
	}
	
}
