package Main;

import java.util.Scanner;

import Clases.Steam;

public class main {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		System.out.println("Ingresar nombre: ");
		
		String name = s.nextLine();
		
		Steam game = new Steam(name);
		
		System.out.println("Juego en STRING:");
		
		System.out.println(game.getGameString());
		
		System.out.println("Juego en JSON");
		
		System.out.println(game.getGameJSON());
		
		System.out.println("Busqueda Juegos");
		
		System.out.println(game.getGames());
		
		System.out.println("");

		System.out.println("Juegos en String");
		
		System.out.println(game.getGamesInString());
		
		
	}

}
