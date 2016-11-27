package tigerzone;

import java.util.ArrayDeque;
import java.util.Queue;

import tigerzone.game.Board;
import tigerzone.game.Tile;

public class bot {
	
	//Initialize the deck of tiles
	public static void initDeck(){
		Queue<Tile> tileDeck = new ArrayDeque<>();
		return;
	}
	//Add a tile to the Deck
	public static void addDeck(String tile){
		//tile in form jttp-
		//needs to be translated before adding to deck
		System.out.println("Added " + tile + " to deck");
		return;
	}
	//Initialize the board
	public static void initBoards(){
		Board boardA = new Board();
		Board boardB = new Board();
		return;
	}
	//Place first tile
	public static void firstTile(String tile, int x, int y, int rot){
		return;
	}
	//Play a tile
	public static move makeMove(String game, int time, String tile){
		//Move is in form (int xPos, int yPos, int rot, String meep, int meepPos)
		//To Pass Set meep equal to "PASS" and rot to -1
		//To Retrieve Meep Set meep equal to "RETRIEVE" and rot to -1. Use x and y for coor
		//To ADD Meep Set meep equal to "ADD" and rot to -1. Use x and y for coor
		
		//get starttime in millisecond
		//current move = "";
		//while (time * 0.8 > (currenttime - starttime)){
		// 		Check for next valid move
		// }
		//while (currentMove = "") { check valid tile placements }
		
		move moveMade = new move(999, 999, 360, "", -1);
		return moveMade;
	}
	//Place a tile ALWAYS ENEMY TILE
	public static void placeTile(String game, String tile, int x, int y, int rot, String meep, int meepPos){
		return;
	}
	//Tile couldn't be placed
	//Place Tiger at x y
	public static void placeMeep(int x, int y) {
		return;
	}
	//Remove Tiger at x y
	public static void removeMeep(int x, int y) {
		return;
	}
	//Extra Processing Time Do Whatever
	public static void botProcess(int time){
		return;
	}
	
}
