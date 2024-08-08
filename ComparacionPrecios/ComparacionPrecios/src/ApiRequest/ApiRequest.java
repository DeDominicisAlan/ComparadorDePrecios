package ApiRequest;


import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.Scanner;

import kong.unirest.HttpResponse;


public class ApiRequest {
	public static void main(String args[]) {
		
		
		System.out.println("Ingresa el nombre de un videojuego:");
		try (Scanner s = new Scanner (System.in)){
			
			String nombre = s.nextLine();
			
			while(!nombre.equals("x")) {
			
			String gameName =  nombre;
			
			String steamApiUrl = "https://store.steampowered.com/api/storesearch/?term=" + gameName + "&l=english&cc=US";
			
			HttpResponse<JsonNode> response = Unirest.get(steamApiUrl).asJson();
			
		
			
			if(response.isSuccess()) {
				JsonNode body = response.getBody();
				
				
				try {
				
	 
	                     
	                JSONObject jsonObject = body.getObject();
	                

	                
	                if(jsonObject.has("items")) {
	                	
	                	JSONArray items = jsonObject.getJSONArray("items");
	                	
	           
	                	
	                	if(items.length() > 0) {
	                		
	                		JSONObject first = items.getJSONObject(0);
	                		System.out.println("Nombre: " + first.get("name"));

	                		if(first.has("price")) {
	                			JSONObject price = first.getJSONObject("price");
	                			
	                			System.out.println("Precio: " + price.getDouble("initial"));
	                			
	                			if(price.getDouble("final") != price.getDouble("initial")) {
	                				System.out.println("Precio en oferta actual: " + price.getDouble("final"));
	                			}
	                			
	                		}else {
	                			System.out.println("Precio: gratis");
	                		}
	                		
	                	}else {
	                		System.out.println("No se encontraron videojuegos con ese nombre");
	                	}
	                	
	                }else {
	                	System.out.println("No se encontro el campo 'items'");
	                }
	                
				}catch(Exception e) {
                    System.out.println("Error al procesar la respuesta JSON: " + e.getMessage());
				}
				
			}
			System.out.println("Ingresa el nombre de un videojuego:");
			
			nombre = s.nextLine();

			}
		}catch(Exception e) {
			System.out.println("Error al leer la entrada de dato del ususario: " + e.getMessage());
		}
		
	
		
		
		
	}
}
