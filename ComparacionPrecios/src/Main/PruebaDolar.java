package Main;

import java.io.IOException;

import Clases.Dolar;

public class PruebaDolar {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		Dolar dolar = new Dolar();
		
		System.out.println("Precio del dolar: " + dolar.getPrecio());
		
		double compra = 600;
		
		System.out.println("Precio de una compra de " + compra + ": " + dolar.getCompra(compra)); //5 dolares a peso argentino
		
		System.out.println("Precio de una compra de " + compra + " con impuestos: " +dolar.getCompraConImpuestosPesos(compra));
	}

}
