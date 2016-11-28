package tigerzone;

import tigerzone.game.Board;
import tigerzone.game.Deck;
import tigerzone.game.Tile;
import tigerzone.AI.AI;

public class singleGame {
	private Deck myDeck = new Deck();
	private Board gameBoard = new Board();
	private AI gameAI = new AI();
	
	public void placeFirstTile(String tile, int x,int y, int rot)
	{
		Tile temp = new Tile(tile,x,y,rot);
		myDeck.addTile(temp);
		gameBoard.addTile(temp);
		//TODO: make function for first tile in board // of find one 
	}
	public void StartAI(String tile)
	{
		Tile temp = new Tile(tile); //remove the 1
		gameAI.setUp(gameBoard, myDeck, temp);
		gameAI.runMainAI();
	}
	public move getMove()
	{
		move currMove = new move(0,0,0,"string",0); // will be filled in with move data
		gameAI.makeMove();
		return currMove;
	}
	public void placeTile(String tile, int x,int y, int rot)
	{
		Tile temp = new Tile(tile,x,y,rot);
		myDeck.addTile(temp);
		gameBoard.addTile(temp);
		
	}
	public void placeTiger()
	{
		//TODO: ADD this
	}
	//TODO: write function to take input of cartesian to array
	
	
}
