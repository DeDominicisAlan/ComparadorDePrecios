package Clases;

import java.text.DecimalFormat;

import javax.management.RuntimeErrorException;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class Steam {

	private String gameName;
	private String steamApiUrl;
	private JsonResponseParser body;
	private HttpResponseValidator httpResponseValidator;

	public Steam(String nombre) {
		this.gameName = nombre;
		this.steamApiUrl = "https://store.steampowered.com/api/storesearch/?term=" + gameName + "&l=spanish&cc=AR";
		fetchGameData();
	}

	private void fetchGameData() {
		try {
			HttpResponse<JsonNode> response = Unirest.get(steamApiUrl).asJson();
			httpResponseValidator = new HttpResponseValidator(response);

			if (httpResponseValidator.isSuccess()) {
				body = new JsonResponseParser(httpResponseValidator.getResponse().getBody());
			} else {
				throw new RuntimeException("Error en la solicitud HTTP: " + response.getStatus());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public JSONObject getGameJSON() {
		JSONObject aux = body.getJSONObjectFirst();
		return aux;
	}

	private int getPosition(JSONArray array) {

		for (int i = 0; i < array.length(); i++)
			if (array.getJSONObject(i).get("name").equals(this.gameName))
				return i;

		return -1;
	}

	public String getGameString() {

		String aux = "";

		JSONArray array = body.getItems();

		int p = this.getPosition(array);

		if (p == -1) {
			throw new RuntimeException("El juego no se encontró");
		}

		JSONObject first = array.getJSONObject(p);

		aux += ("Nombre: " + first.get("name") + "\n");

		if (first.has("price")) {
			JSONObject price = first.getJSONObject("price");

			Dolar dolar = new Dolar();

			Double impuesto = dolar.getPrecio() * 0.64;

			Double precioArg = dolar.getPrecio() + impuesto;
			
			 DecimalFormat df = new DecimalFormat("######.##");
			 
			 ;

			aux += ("	Precio: " + df.format(price.getDouble("initial") / 100.0) + "\n");
			aux += ("	Precio ARS: " + df.format((price.getDouble("initial") / 100.0) * precioArg) + "\n");

			if (price.getDouble("final") != price.getDouble("initial") / 100.0) {
				aux+= "\n";
				aux += ("	Precio en oferta actual: " + df.format(price.getDouble("final") / 100.0) + "\n");
				aux += ("	Precio ARS en oferta actual: " + df.format((price.getDouble("final") / 100.0) * precioArg) + "\n");
			}

		} else {
			aux += ("Precio: gratis" + "\n");
		}

		return aux;
	}

}
