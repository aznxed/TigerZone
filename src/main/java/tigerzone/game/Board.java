package tigerzone.game;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import tigerzone.game.Deck;
import tigerzone.move;

public class Board {

	public static int MAX_ROWS = 153;
	public static int MAX_COLS = 153;
	public static int CENTER_CELL = MAX_ROWS / 2 + 1;

	private int topBound;
	private int bottomBound;
	private int leftBound;
	private int rightBound;
	
	private boolean startTile = true;

	private Tile[][] board = new Tile[MAX_ROWS][MAX_COLS];

	// Keep a list of all the tiles placed on the board
	private List<Tile> placedTiles = new ArrayList<Tile>();

	public int getTopBound() {
		return this.topBound;
	}
	public Tile[][] getBoard(){
		return this.board;
	}
	public int getBottomBound() {
		return this.bottomBound;
	}

	public int getLeftBound() {
		return this.leftBound;
	}

	public int getRightBound() {
		return this.rightBound;
	}

	public void setTopBound(int upperBound) {
		this.topBound = upperBound;
	}

	public void setBottomBound(int lowerBound) {
		this.bottomBound = lowerBound;
	}

	public void setLeftBound(int leftBound) {
		this.leftBound = leftBound;
	}

	public void setRightBound(int rightBound) {
		this.rightBound = rightBound;
	}

	public Tile getTile(int x, int y) {
		if (x >= 0 && x < MAX_ROWS && y >= 0 && y < MAX_COLS) {
			return board[x][y];
		}
		return null;

	}

	/**
	 * Return this tile's immediate neighbors
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public List<Tile> getNeighbors(int x, int y) {
		List<Tile> n = new ArrayList<Tile>();

		if (y > 0) {
			if (board[x][y - 1] != null) {
				n.add(board[x][y - 1]);
			}
		}

		if (y < MAX_COLS) {
			if (board[x][y + 1] != null) {
				n.add(board[x][y + 1]);
			}
		}

		if (x > 0) {
			if (board[x - 1][y] != null) {
				n.add(board[x - 1][y]);
			}
		}

		if (x < MAX_ROWS) {
			if (board[x + 1][y] != null) {
				n.add(board[x + 1][y]);
			}
		}

		return n;
	}

	

	/**
	 * Checks to see if the tile being placed at the x-y coordinate is valid
	 * with respect to its potential neighbors
	 *
	 * @param x
	 * @param y
	 * @param tile
	 * @return
	 */
	
	public boolean isValid(int x, int y, Tile tile) {
		// Space already has tile
		if (board[x][y] != null) {
			return false;
		}

		List<Tile> nbors = getNeighbors(x, y);

		// No neighbors
		if (nbors.isEmpty()) {
			return false;
		}

		return true;
	}
	
	/*
	public boolean isValid(int x, int y, Tile tile)
	{

		// Tile already exists in that position
		if (board[x][y] != null) {
			return false;
		}

		// return true if the board is empty
		if (placedTiles.isEmpty()) {
			return true;
		}

		// Get the neighbors of this position
		List<Tile> nbors = getNeighbors(x, y);

		// If there are no neighbors, then its not a valid
		// position in which to place a tile.
		if (nbors.isEmpty()) {
			return false;
		}

		// Ensure that all the neighbors are compatible.
		boolean valid = true;
		// Iterate through all its potential neighbors
		for (Tile neighbor : nbors) {

			// Check if neighbor is in same row
			if (neighbor.getRow() == x) {
				if (neighbor.getCol() > y) {
					// This is right neighbor
					if (neighbor.getLeftEdge() != tile.getRightEdge()) {
						valid = false;
						// no need to continue checking other
						// neighbors
						break;
					}
				} else {
					// This is left neighbor
					if (neighbor.getRightEdge() != tile.getLeftEdge()) {
						valid = false;
						break;
					}
				}
			}

			// If not in the same row, it must be in the same column
			if (neighbor.getCol() == y) {
				if (neighbor.getRow() > x) {
					// This is bottom neighbor
					if (neighbor.getTopEdge() != tile.getBottomEdge()) {
						valid = false;
						break;
					}

				} else {
					// This is top neighbor
					if (neighbor.getBottomEdge() != tile.getTopEdge()) {
						valid = false;
						break;
					}
				}
			}

		} // Iterate thru neighbors
		return valid;
	}*/

	/**
	 * Add a Tile to the board. It will first validate that the Tile can be
	 * added to that position.
	 *
	 * @param x
	 * @param y
	 * @param tile
	 * @return
	 */
	public boolean addXTile(int x, int y, Tile tile)
	{
		if (!isValid(x, y, tile)) {
			return false;
		}
		// add tile to board
		// give tile coords
		placedTiles.add(tile);
		board[x][y] = tile;
		tile.setCol(y);
		tile.setRow(x);
		tile.setBoard(this);
		return true;
	}

	public List<Integer> getValidOrients(int x, int y, Tile tile) {
		List<Integer> validOrients = new ArrayList<Integer>();

		List<Tile> nbors = getNeighbors(x, y);

		// Add possible orientation to list
		validOrients.add(0);
		validOrients.add(90);
		validOrients.add(180);
		validOrients.add(270);

		// For each neighboring tile, check if sides match for each orientation
		// If not, remove from validOrients
		for (int i = 0; i < nbors.size(); i++) {
			Tile nTile = nbors.get(i);

			if (validOrients.isEmpty()) {
				break;
			}
			// Check if its in same row
			if (nTile.getRow() == x) {
				if (nTile.getCol() > y) {
					// This is right neighbor
					if (nTile.getLeftEdge() != tile.getRightEdge()) {
						if (validOrients.contains(0)) {
							validOrients.remove(Integer.valueOf(0));
						}
					}
					if (nTile.getLeftEdge() != tile.getBottomEdge()) {
						if (validOrients.contains(90)) {
							validOrients.remove(Integer.valueOf(90));
						}
					}
					if (nTile.getLeftEdge() != tile.getLeftEdge()) {
						if (validOrients.contains(180)) {
							validOrients.remove(Integer.valueOf(180));
						}
					}
					if (nTile.getLeftEdge() != tile.getTopEdge()) {
						if (validOrients.contains(270)) {
							validOrients.remove(Integer.valueOf(270));
						}
					}
				} else {
					// This is left neighbor
					if (nTile.getRightEdge() != tile.getLeftEdge()) {
						if (validOrients.contains(0)) {
							validOrients.remove(Integer.valueOf(0));
						}
					}
					if (nTile.getRightEdge() != tile.getTopEdge()) {
						if (validOrients.contains(90)) {
							validOrients.remove(Integer.valueOf(90));
						}
					}
					if (nTile.getRightEdge() != tile.getRightEdge()) {
						if (validOrients.contains(180)) {
							validOrients.remove(Integer.valueOf(180));
						}
					}
					if (nTile.getRightEdge() != tile.getBottomEdge()) {
						if (validOrients.contains(270)) {
							validOrients.remove(Integer.valueOf(270));
						}
					}
				}
			}

			if (nTile.getCol() == y) {
				if (nTile.getRow() > x) {
					// This is bottom neighbor
					if (nTile.getTopEdge() != tile.getBottomEdge()) {
						if (validOrients.contains(0)) {
							validOrients.remove(Integer.valueOf(0));
						}
					}
					if (nTile.getTopEdge() != tile.getLeftEdge()) {
						if (validOrients.contains(90)) {
							validOrients.remove(Integer.valueOf(90));
						}
					}
					if (nTile.getTopEdge() != tile.getTopEdge()) {
						if (validOrients.contains(180)) {
							validOrients.remove(Integer.valueOf(180));
						}
					}
					if (nTile.getTopEdge() != tile.getRightEdge()) {
						if (validOrients.contains(270)) {
							validOrients.remove(Integer.valueOf(270));
						}
					}
				} else {
					// This is top neighbor
					if (nTile.getBottomEdge() != tile.getTopEdge()) {
						if (validOrients.contains(0)) {
							validOrients.remove(Integer.valueOf(0));
						}
					}
					if (nTile.getBottomEdge() != tile.getRightEdge()) {
						if (validOrients.contains(90)) {
							validOrients.remove(Integer.valueOf(90));
						}
					}
					if (nTile.getBottomEdge() != tile.getBottomEdge()) {
						if (validOrients.contains(180)) {
							validOrients.remove(Integer.valueOf(180));
						}
					}
					if (nTile.getBottomEdge() != tile.getLeftEdge()) {
						if (validOrients.contains(270)) {
							validOrients.remove(Integer.valueOf(270));
						}
					}
				}
			}
		}

		return validOrients;
	}

	public List<Tile> getPossibleMoves(Tile tile) {
		List<Tile> possibleMoves = new ArrayList<Tile>();
	    for (int i = getTopBound(); i <= getBottomBound(); i++) {
			for (int j = getLeftBound(); j <= getRightBound(); j++) {
				if (isValid(i, j, tile)) {
					tile.setRow(i);
					tile.setCol(j);
					List<Integer> validOrients = getValidOrients(i, j, tile);
					for (int k = 0; k < validOrients.size(); k++) {
						possibleMoves.add(tile.rotateTile(tile, validOrients.get(k)));
					}
				}
			}
		}

		return possibleMoves;
	}

	// Clear the visited flag for all tiles placed on the board
	public void clearVisited() {

		for (Tile t : placedTiles) {
			t.setVisited(false);
		}

	}
	public void addNewTile(Tile tile)
	{
		int x = tile.getRow();
		int y = tile.getCol();
		board[x][y] = tile;
	}
	//Places random Tile
	public boolean addTile(Tile tile)// moveto AI
	{
	
		if (!(getPossibleMoves(tile).isEmpty())) {

			// Keep a list of all the tiles placed on the board
			placedTiles.add(tile);
			tile.setBoard(this);

			Random rand = new Random();

			Tile addTile = getPossibleMoves(tile).get(
					rand.nextInt(getPossibleMoves(tile).size()));
			int x = addTile.getRow();
			int y = addTile.getCol();
			board[x][y] = addTile;

			if (x == getTopBound() && x > 0) {
				setTopBound(x - 1);
			}
			if (x == getBottomBound() && x < MAX_ROWS) {
				setBottomBound(x + 1);
			}
			if (y == getLeftBound() && y > 0) {
				setLeftBound(y - 1);
			}
			if (y == getRightBound() && y < MAX_COLS) {
				setRightBound(y + 1);
			}
			return true;
		}
		else {
			return false;
		}
		
	}
	
	//Places random Tile
	public move makeMoveBoard(Tile tile)// moveto AI
	{
		move tempMove;
		tempMove = new move(0, 0, 0, "", -1);
		if (startTile){
			startTile = false;
			board[CENTER_CELL][CENTER_CELL] = tile;
		}
		else {
		
			if (!(getPossibleMoves(tile).isEmpty())) {
				
				// Keep a list of all the tiles placed on the board
				placedTiles.add(tile);
				tile.setBoard(this);
	
				Random rand = new Random();
	
				Tile addTile = getPossibleMoves(tile).get(
						rand.nextInt(getPossibleMoves(tile).size()));
				int x = addTile.getRow();
				int y = addTile.getCol();
				board[x][y] = addTile;
				//tempMove = new move(x, y, addTile.getDegrees(), "", -1);
				tempMove.xPos = x;
				tempMove.yPos = y;
				tempMove.rot = addTile.getDegrees();
				tempMove.meepPos = 0;
				System.out.println("X: " + x + " Y: " + y + " Rot: " + tempMove.rot);
				
				if (x == getTopBound() && x > 0) {
					setTopBound(x - 1);
				}
				if (x == getBottomBound() && x < MAX_ROWS) {
					setBottomBound(x + 1);
				}
				if (y == getLeftBound() && y > 0) {
					setLeftBound(y - 1);
				}
				if (y == getRightBound() && y < MAX_COLS) {
					setRightBound(y + 1);
				}
			}
			else {
				System.out.println("WARNING NO POSSIBLE MOVES");
			}
			return tempMove;
		}
		return tempMove;
	}


	public void removeTile(int x, int y) {
		board[x][y] = null;
	}

	public void initTiles(Deck tiles) {
		//this will be moved to deck.java
		Tile startTile = new Tile("TLTJ-", 0, CENTER_CELL, CENTER_CELL);
		board[CENTER_CELL][CENTER_CELL] = startTile;
	}

	public void placeStartTile(String tile) {
		//this will be moved to deck.java
		Tile startTile = new Tile(tile, 0, CENTER_CELL, CENTER_CELL);
		board[CENTER_CELL][CENTER_CELL] = startTile;
	}
	

	public static void main(String[] args)
	{
		Board gameBoard = new Board();
		Deck deck = new Deck();
		
		Tile temp = new Tile("TLTJ-");//test code
		deck.addTile(temp);//test code
		temp = new Tile("JJJJ-");//test code
		deck.addTile(temp);//test code
		
		gameBoard.setTopBound(CENTER_CELL - 1);
		gameBoard.setBottomBound(CENTER_CELL + 1);
		gameBoard.setLeftBound(CENTER_CELL - 1);
		gameBoard.setRightBound(CENTER_CELL + 1);


		gameBoard.initTiles(deck);//change to deck

		for (int i = 0; i < deck.getSize()+1; i++) {
			gameBoard.addTile(deck.getTop());
		}
		UI test = new UI();
		test.createUIBoard(gameBoard);
	}
}
