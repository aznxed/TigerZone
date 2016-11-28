package tigerzone;

import tigerzone.game.Board;
import tigerzone.game.Tile;
import java.util.Random;

public class bot2 {
	//Initialize the deck of tiles
		private Board boardA;
		private Board boardB;
	
		public void initDeck(){
			return;
		}
		//Add a tile to the Deck
		public void addDeck(String tile){
			return;
		}
		//Initialize the board
		public void initBoards(){
			boardA = new Board();
			boardB = new Board();
			return;
		}
		//Place first tile
		public void firstTile(String tile, int x, int y, int rot){
			Tile newTile = new Tile(tile);
			boardA.makeMoveBoard(newTile);
			boardB.makeMoveBoard(newTile);
			return;
		}
		//Play a tile
		public move makeMove(String game, int time, String tile){
			
			//TerrainType[] transTile = new TerrainType[];
			//TerrainType[] transTile = Tile.returnTileTerrain(tile);
			
			//Random rand = new Random();
			//Tile addTile = boardA.getPossibleMoves(newTile).get(rand.nextInt(boardA.getPossibleMoves(newTile).size()));
			
			//Move is in form (int xPos, int yPos, int rot, String meep, int meepPos)
			//To Pass Set meep equal to "PASS" and meepPos to -1
			//To Retrieve Meep Set meep equal to "RETRIEVE" and meepPos to -1. Use x and y for coor
			//To ADD Meep Set meep equal to "ADD" and meepPos to -1. Use x and y for coor
			long startTime = System.currentTimeMillis();
			
			Tile newTile = new Tile(tile);
			move tempMove2;
			if (game.equals("A")){
				tempMove2 = boardA.makeMoveBoard(newTile);
			}
			else {
				tempMove2 = boardB.makeMoveBoard(newTile);
			}
			//Translate x and y coordinates and rotation
			move currMove = new move(tempMove2.yPos - 77, 77 - tempMove2.xPos, (tempMove2.rot + 270) % 360, tempMove2.meep, tempMove2.meepPos);
			//Next tile
			//Tile bar = myDeck.get(tileDeck);
			//Tile tempTile = myDeck.transTile(tile);
			/*
			long timeLeft = (long) ((double) time * 0.8 * 1000);
			while (timeLeft > (System.currentTimeMillis() - startTime)) {
				//Check for next best move
			}
			while (currMove.meepPos == -2){
				//Check for any valid move
				currMove = new move(0, 0, 0, "", -1);
				//makeMove  Return (X, Y, rot, meep, meepPos);
			}*/
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