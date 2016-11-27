package tigerzone;

import java.util.ArrayDeque;
import java.util.Queue;
import java.lang.System;

public class bot {
	Deck myDeck = new Deck();
	Queue<Tile> tileDeck;
	
	//Initialize the deck of tiles
	public void initDeck(){
		tileDeck = myDeck.init();
		return;
	}
	//Add a tile to the Deck
	public void addDeck(String tile){
		//tile in form jttp-
		//needs to be translated before adding to deck
		Tile tempTile = myDeck.transTile(tile);
		myDeck.addTile(tileDeck, tempTile);
		System.out.println("Added " + tile + " to deck");
		return;
	}
	//Initialize the board
	public void initBoards(){
		Board boardA = new Board();
		Board boardB = new Board();
		return;
	}
	//Place first tile
	public void firstTile(String tile, int x, int y, int rot){
		return;
	}
	//Play a tile
	public move makeMove(String game, int time, String tile){
		//Move is in form (int xPos, int yPos, int rot, String meep, int meepPos)
		//To Pass Set meep equal to "PASS" and rot to -1
		//To Retrieve Meep Set meep equal to "RETRIEVE" and rot to -1. Use x and y for coor
		//To ADD Meep Set meep equal to "ADD" and rot to -1. Use x and y for coor
		long startTime = System.currentTimeMillis();
		//Next tile
		//Tile bar = myDeck.get(tileDeck);
		//Tile tempTile = myDeck.transTile(tile);
		
		move currMove = new move(0, 0, 0, "", -2);
		long timeLeft = (long) ((double) time * 0.8 * 1000);
		while (timeLeft > (System.currentTimeMillis() - startTime)) {
			//Check for next best move
		}
		while (currMove.meepPos == -2){
			//Check for any valid move
			currMove = new move(0, 0, 0, "", -1);
		}
		
		System.out.println("Got a move in: " + (System.currentTimeMillis() - startTime));
		return currMove;
	}
	//Place a tile ALWAYS ENEMY TILE
	public void placeTile(String game, String tile, int x, int y, int rot, String meep, int meepPos){
		return;
	}
	//Tile couldn't be placed
	//Place Tiger at x y
	public void placeMeep(int x, int y) {
		return;
	}
	//Remove Tiger at x y
	public void removeMeep(int x, int y) {
		return;
	}
	//Extra Processing Time Do Whatever
	public void botProcess(int time){
		return;
	}
	
}