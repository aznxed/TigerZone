package tigerzone;

import java.util.ArrayDeque;
import java.util.Queue;

public class Deck {
	public Queue<Tile> init() {
		Queue<Tile> tileDeck = new ArrayDeque<>();
		return tileDeck;
	}
	public void addTile(Queue<Tile> tileDecks, Tile tile) {
		//System.out.println("Add Tile to deck");
		//tileDecks.offer(tile);
		//System.out.println("Tile added");
	}
	public Tile get(Queue<Tile> tileDecks) {
		return tileDecks.remove();
	}
	public Tile transTile( String tile ) {
		//WHAT IS TILE!?
		//Placeholder code
		Tile tempTile = new Tile();
		return tempTile;
	}
}