package tigerzone.game;


import java.util.ArrayDeque;
import java.util.Queue;

public class Deck {
	private Queue<Tile> tileDeck = new ArrayDeque<>();

	public void addTile(Tile tile) {
		tileDeck.add(tile);
	}
	public Tile getTop() {
		return tileDeck.remove();
	}
	public int getSize()
	{
		return tileDeck.size();
	}

}