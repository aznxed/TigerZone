package tigerzone;

import java.util.ArrayDeque;
import java.util.Queue;

public class Deck {
	public static void init() {
		
	}
	public static void addTile(Queue<Tile> tileDeck, Tile tile) {
		tileDeck.add(tile);
	}
	public static Tile get(Queue<Tile> tileDeck) {
		return tileDeck.pop();
	}
}