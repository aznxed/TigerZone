package tigerzone;

import org.junit.Test;
import tigerzone.game.*;
import tigerzone.game.Deck;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Junit {
	// to be implemented with tests for each part
	
	//Declare variables, classes, etc, that all tests will need up here
	
	//bot botA = new bot();
	
	Board boardA = new Board();
	Deck deck = new Deck();
	Tile temp = new Tile("TLTJ-");
	
	
	@Test 
	public void testGetTop(){
	  deck.addTile(temp);
	  assertEquals(temp, deck.getTop());
	 }
	
	
	
	 @Test 
	 public void testAddTile(){
		 assertTrue(boardA.addXTile(7, 77, temp));
		 
	 }
	  
	 @Test 
	 public void testAddDeck(){
	  
	 }
	
	 @Test 
	 public void testMakeMove(){
	 
	 }
	 
	 @Test 
	 public void testPlaceTilek(){
	  
	 }
	  
	 @Test 
	 public void testPlaceMeep(){
	  
	 }
	  
	 @Test 
	 public void testRemoveMeep(){
	  
	 }
	
	//Tile tile = new Tile();
	
	/**
	 * @Test 
	 * public void returnTileTerrain(){
	 * 
	 * }
	 * @Test 
	 * public void testRotateTile(){
	 * 
	 * }
	 * @Test 
	 * public void testGetTilePortionType(){
	 * 
	 * }
	 * @Test 
	 * public void testIsDen(){
	 * 
	 * }
	 * @Test 
	 * public void testIsLakeCenter(){
	 * 
	 * }
	 * @Test 
	 * public void testRemoveMeep(){
	 * 
	 * }
	 * 
	 * 
	 */
	

	
	
	//Board boardB = new Board();
	
	
	/**
	 * @Test 
	 * public void testaddXTile(){
	 * 
	 * }
	 * @Test 
	 * public void testGetVaildOrients(){
	 * 
	 * }
	 * @Test 
	 * public void testGetPossibleMoves(){
	 * 
	 * }
	 */
	
	//AI ai = new AI();
	
	/**
	 * @Test 
	 * public void testAddTile(){
	 * 
	 * }
	 * @Test 
	 * public void testRemoveTile(){
	 * 
	 * }
	 */
	
	//trails trails = new trails()
	/**
	 * @Test 
	 * public void testIsTrailsComplete(){
	 * 
	 * }
	 * @Test 
	 * public void testGetTrailScore(){
	 * 
	 * }
	 */
	
	//dens den = new dens()
	/**
	 * @Test 
	 * public void testDoesCompleteDen(){
	 * 
	 * }
	 * @Test 
	 * public void testIsDenComplete(){
	 * 
	 * }
	 * @Test 
	 * public void testGetDenScore(){
	 * 
	 * }
	 * @Test 
	 * public void testCollectDenScores(){
	 * 
	 * }
	 */
	
	//lakes lake = new lakes()
	/**
	 * @Test 
	 * public void testIsLakeComplete(){
	 * 
	 * }
	 * @Test 
	 * public void testGetLakeScore(){
	 * 
	 * }
	 */
}
//
/*
//<------test tile placements
		for (int i = gameBoard.getTopBound(); i <= gameBoard.getBottomBound(); i++) {
			for (int j = gameBoard.getLeftBound(); j <= gameBoard
					.getRightBound(); j++) {
				if (gameBoard.board[i][j] == null) {
					System.out.print("0 ");
				} else {
					System.out.print(gameBoard.board[i][j].getType() + " ");
				}
			}
			System.out.print("\n");
		}
		
		for (int i = gameBoard.getTopBound(); i <= gameBoard.getBottomBound(); i++) {
			for (int j = gameBoard.getLeftBound(); j <= gameBoard
					.getRightBound(); j++) {
				if (gameBoard.board[i][j] == null) {
					System.out.print("1 ");
				} else {
					System.out.print(gameBoard.board[i][j].getDegrees() + " ");
				}
			}
			System.out.print("\n");
		}
		//------
*/