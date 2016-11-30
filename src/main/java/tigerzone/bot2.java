package tigerzone;

import tigerzone.game.Board;
import tigerzone.game.Tile;
import tigerzone.game.UI;

import java.util.Random;

public class bot2 {
	//Initialize the deck of tiles
		private Board boardA;
		private Board boardB;
		
		private String firstGame = "";
	
		public void initDeck(){
			return;
		}
		//Add a tile to the Deck
		public void addDeck(String tile){
			System.out.println("Add " + tile + " to deck");
			boardA.addDeck(tile);
			boardB.addDeck(tile);
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
			move reportMove = boardA.placeBoard(tile, 77 - y, 77 + x, rot);
			boardB.makeMoveBoard(newTile, 77 - y, 77 + x, rot);
			/*//Initialize board bounds
			boardA.setTopBound(77 + x - 1);
			boardA.setBottomBound(77 + x + 1);
			boardA.setLeftBound(77 - y - 1);
			boardA.setRightBound(77 - y + 1);
			boardB.setTopBound(77 + x - 1);
			boardB.setBottomBound(77 + x + 1);
			boardB.setLeftBound(77 - y - 1);
			boardB.setRightBound(77 - y + 1);*/
			System.out.println("Report Move: " + reportMove.xPos + " " + reportMove.yPos + " " + reportMove.xPos);
			return;
		}
		//Return a move for the given tile 
		public move makeMove(String game, int time, String tile){
			
			if (firstGame.equals("")){
				firstGame = game;
			}
			
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
			if (game.equals(firstGame)){
				tempMove2 = boardA.makeMoveBoard(newTile);
			}
			else {
				tempMove2 = boardB.makeMoveBoard(newTile);
			}
			//Translate x and y coordinates and rotation
			move currMove = new move(tempMove2.yPos - 77, 77 - tempMove2.xPos , tempMove2.rot, tempMove2.meep, tempMove2.meepPos);
			System.out.println("Got a move in: " + (System.currentTimeMillis() - startTime));
			return currMove;
		}
		//Place a tile ALWAYS ENEMY TILE
		public void placeTile(String game, String tile, int x, int y, int rot, String meep, int meepPos){
			Tile newTile = new Tile(tile);
			if (game.equals(firstGame)) {
				boardA.placeBoard(tile, 77 - y, 77 + x, rot);
			}
			else {
				boardB.placeBoard(tile, 77 - y , 77 + x, rot);
			}
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
			boardA.setTopBound(77 - 1);
			boardA.setBottomBound(77 + 1);
			boardA.setLeftBound(77 - 1);
			boardA.setRightBound(77 + 1);


			boardA.initTiles(boardA.deck);//change to deck

			
			boardB.setTopBound(77 - 1);
			boardB.setBottomBound(77 + 1);
			boardB.setLeftBound(77 - 1);
			boardB.setRightBound(77 + 1);


			boardB.initTiles(boardB.deck);//change to deck
			return;
		}
		public void printBoard() {
			UI window = new UI();
			window.createUIBoard(boardA);
			//UI test = new UI();
			//test.createUIBoard(gameBoard);
		}
}