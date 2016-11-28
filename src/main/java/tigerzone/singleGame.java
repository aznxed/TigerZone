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
		Tile temp = new Tile(tile,toXArray(x),toYArray(y),rot);
		myDeck.addTile(temp);
		gameBoard.addTile(temp);
		//TODO: make function for first tile in board // of find one 
	}
	public void StartAI(String tile)
	{
		Tile temp = new Tile(tile); 
		gameAI.setUp(gameBoard, myDeck, temp);
	}
	public move getMove()
	{
		Tile temp =  gameAI.makeMove();
		move currMove = new move(0,0,0,"",0); // will be filled in with move data
		currMove.xPos = toXCartesian(temp.getRow());
		currMove.yPos = toYCartesian(temp.getCol());
		currMove.rot = temp.getDegrees();
		return currMove;
	}
	public void runAI()
	{
		gameAI.runMainAI();
	}
	
	public void placeTile(String tile, int x,int y, int rot)
	{
		Tile temp = new Tile(tile,toXArray(x),toYArray(y),rot);
		myDeck.addTile(temp);
		gameBoard.addTile(temp);
		
	}
	public void placeTiger()
	{
		//TODO: ADD this
	}
	
	public int toXArray(int x)
	{
		return x + 77;
	}
	public int toYArray(int y)
	{
		return y - 77;
	}
	public int toXCartesian(int x)
	{
		return x - 77;
	}
	public int toYCartesian(int y)
	{
		return y + 77;
	}
	
}
