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
		
		JSONArray array = body.getItems();

		JSONObject aux;
		
		int i = getPosition(array);
		
		if(i != -1) {
			
			aux = array.getJSONObject(i);
			
		}else {
			
			throw new RuntimeException("El juego no se encontró");		
		}
			
		
		
		return aux;
	}

	private int getPosition(JSONArray array) {
		int pos = -1;
		
		
		
		for (int i = 0; i < array.length(); i++){
			
			String nombre = array.getJSONObject(i).getString("name").toLowerCase().replaceAll("[;:,.\\*|®_-°]", "");
			
			if(nombre.equals(this.gameName.toLowerCase().replaceAll("[;:,.\\*|_-°]®", ""))) { 
				pos = i;
			}
			
			if (pos == -1 && nombre.contains(this.gameName.toLowerCase().replaceAll("[;:,.\\*|°_-®]", "")))
				pos = i;
			} 

		return pos;
	}
	
	public JSONArray getGames() {

		JSONArray array = body.getItems();

		int p = this.getPosition(array);

		if (p == -1) {
			throw new RuntimeException("El juego no se encontró");
		}
		
		return array;
	}
	
	public String getGamesInString() {

		JSONArray array = body.getItems();
		
		String aux = "";
		
		for(int i = 0; i < array.length(); i++) {
			
			aux = devolverJuego(i, array, aux) + "\n";
			
		}

		
		return aux;
	}

	private String devolverJuego(int p, JSONArray array, String aux) {
		
		JSONObject first = array.getJSONObject(p);

		aux += ("Nombre: " + first.get("name") + "\n");

		if (first.has("price")) {
			JSONObject price = first.getJSONObject("price");

			Dolar dolar = new Dolar();

			Double impuesto = dolar.getPrecio() * 0.64;

			Double precioArg = dolar.getPrecio() + impuesto;
			
			 DecimalFormat df = new DecimalFormat("######.##");
			 
			 ;

			aux += ("Precio: " + df.format(price.getDouble("initial") / 100.0) + "\n");
			aux += ("Precio ARS: " + df.format((price.getDouble("initial") / 100.0) * precioArg) + "\n");

			if (price.getDouble("final")/100.0 < price.getDouble("initial") / 100.0) {
				aux+= "El videojuego esta en oferta!\n";
				aux += ("Precio en oferta actual: " + df.format(price.getDouble("final") / 100.0) + "\n");
				aux += ("Precio ARS en oferta actual: " + df.format((price.getDouble("final") / 100.0) * precioArg) + "\n");
			}

		} else {
			aux += ("Precio: gratis" + "\n");
		}
	
		return aux;
	}
	
	public String getGameString() {

		String aux = "";

		JSONArray array = body.getItems();

		int p = this.getPosition(array);

		if (p == -1) {
			throw new RuntimeException("El juego no se encontró");
		}

		aux = devolverJuego(p,array,aux);
		

		return aux;
	}

}
