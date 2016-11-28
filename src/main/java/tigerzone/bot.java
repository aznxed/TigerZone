package tigerzone;
import java.lang.System;

public class bot {
	singleGame gameA = new singleGame();
	singleGame gameB = new singleGame();
	
	
	public void placeFirstTile(String tile, int x, int y, int rot){
		gameA.placeFirstTile(tile, x, y, rot);
		gameB.placeFirstTile(tile, x, y, rot);
	}
	
	//Play a tile
	public move makeMove(String game, int time, String tile){
		//TODO: ADD if to switch games.
		gameA.StartAI(tile);
		
		//Move is in form (int xPos, int yPos, int rot, String meep, int meepPos)
		//To Pass Set meep equal to "PASS" and rot to -1
		//To Retrieve Meep Set meep equal to "RETRIEVE" and rot to -1. Use x and y for coor
		//To ADD Meep Set meep equal to "ADD" and rot to -1. Use x and y for coor
		
		move currMove = new move(0,0,0,"placeHolder",0);
		
		long startTime = System.currentTimeMillis();
		long timeLeft = (long) ((double) time * 0.8 * 1000);
		while (true) {
			if(timeLeft == (System.currentTimeMillis() - startTime))
			{
				currMove = gameA.getMove();//returns tile and meeple in move notation 
				break;
			}
		}
		makeMove(game, tile, 0,0,0,"placeHolder",0);
		System.out.println("Got a move in: " + (System.currentTimeMillis() - startTime));
		return currMove;
	}
	//Place a tile ALWAYS ENEMY TILE
	public void makeMove(String game, String tile, int x, int y, int rot, String meep, int meepPos){
		//TODO: ADD if to switch games.
		gameA.placeTile(tile, x, y, rot);
		gameA.placeTiger();
	}
	
	

}
