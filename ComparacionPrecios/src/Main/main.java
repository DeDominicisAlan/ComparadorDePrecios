package Main;

import Clases.Steam;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Steam steam = new Steam("Counter-Strike 2");
		
		//System.out.println(steam.getGameJSON());
		System.out.println(steam.getGameString());
		
		Steam steam1 = new Steam("Portal");
		
		Steam steam2 = new Steam("Portal Knights");
		
		//System.out.println(steam1.getGameJSON());
		System.out.println(steam1.getGameString());
		
		//System.out.println(steam2.getGameJSON());
		System.out.println(steam2.getGameString());
	
		Steam steam3 = new Steam("Dungeons & Degenerate Gamblers");
		
		System.out.println(steam3.getGameString());
	}

}
