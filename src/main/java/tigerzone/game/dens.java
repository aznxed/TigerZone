package tigerzone.game;

import static tigerzone.AI.Utils.addDistinctList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class dens
{
	// Check if the tile completes an existing den
		public  boolean doesCompleteDen(Tile tile) {

			List<Tile> neighbors = getDenNeighbors(tile.getRow(), tile.getCol(), tile.getBoard());
			for (Tile t : neighbors) {
				if (t.isDen()) {
					if (isDenComplete(t)) {
						return true;
					}
				}
			}

			return false;
		}

		public static boolean isDenComplete(Tile den) {

			/*
			 * If the Tile has not been assigned to a board, then its not in-play
			 */
			if (den.getBoard() == null) {
				return false;
			}

			/* Get this Den tile's four neighbors */
			List<Tile> neighbors = den.getBoard().getNeighbors(den.getRow(),
					den.getCol());

			/* A Den must have neighbors on all four sides */
			if (neighbors.size() != 4) {
				return false;
			}

			/*
			 * Now make sure that the top and bottom neighbors have neighbors to
			 * their left and right. Note that there is no need to check that this
			 * Den tile's left and right neighbors have top and bottom tiles!
			 */
			for (Tile n : neighbors) {
				if (n.getRow() == den.getRow() + 1
						|| n.getRow() == den.getRow() - 1) {
					// this is either my top or bottom neighbor, so make sure it has
					// neighbors to the left and right
					if (den.getBoard().getTile(n.getRow(), n.getCol() + 1) != null) {
						if (den.getBoard().getTile(n.getRow(), n.getCol() - 1) != null) {
							continue;
						} else {
							return false;
						}
					}
					return false;
				}
			}
			return true;
		}
		/**
		 * Returns the 8 possible neighbors that surround the tile at the given
		 * coordinates. Note that not all 8 may be returned.
		 *
		 * @param x
		 * @param y
		 * @return
		 */
		public List<Tile> getDenNeighbors(int x, int y, Board board)
		{

			// List of all possible 8 neighbors
			List<Tile> denNeighbors = new ArrayList<Tile>();

			//System.out.println("getDenNeighbors: called with coords " + x + " " + y);

			// return an empty list if either of the coordinates are not valid
			if (x < 0 || x >= board.MAX_ROWS || y < 0 || y >= board.MAX_COLS) {
				return denNeighbors;
			}

			// return an empty list if ther is no Tile at the given coordinates
			if (board.getBoard()[x][y] == null) {
				return denNeighbors;
			}

			//System.out.println("getDenNeighbors: collecting neighbors");
			// iterate the three possible rows and their columns. we skip the tile
			// whose coordinates are passed in
			for (int i = x - 1; i <= x + 1; i++) {
				// skip this row if it is out of bounds
				if (i < 0 || i >= board.MAX_ROWS) {
					continue;
				}
				// iterate the three possible columns for this row
				for (int j = y - 1; j <= y + 1; j++) {
					// skip this column if it is out of bounds
					if (j < 0 || j >= board.MAX_COLS) {
						continue;
					}
					// skip this cell if its coordinates are those of x and y
					if (i == x && j == y) {
						continue;
					}
					if (board.getBoard()[i][j] != null) {
						denNeighbors.add(board.getBoard()[i][j]);
					}
				}
			}

			return denNeighbors;
		}
		
		public int getDenScore(Tile tile) //move to den.java
		{
			Board board = tile.getBoard();
			int score = 0;

			// If this is not a den tile, just return
			if (!tile.isDen()) {
				return score;
			}

			// Else get all its possible 8 neighbors
			List<Tile> neighbors = getDenNeighbors(tile.getRow(), tile.getCol(), board);

			// Return the size of that list plus one to include itself
			return neighbors.size() + 1;
		}
		/**
		 * This method finds all neigboring Dens and collects their scores
		 * 
		 * @return
		 */
		public int collectDenScores(Tile tile) //move to dens
		{
			int score = 0;

			if (tile.getBoard() == null) {
				return score;
			}

			//System.out.println("collectDenScores: isDen returns " + this.isDen());
			
			// get my score if I'm a Den
			if (tile.isDen()) {
				score += this.getDenScore(tile);
			}

			// Look for neighbors that are Dens and get their score
			List<Tile> allDenNeighbors = getDenNeighbors(tile.getRow(),tile.getCol(), tile.getBoard());

			//System.out.println("collectDenScores: allDenNeighbors size = " + allDenNeighbors.size());

			// return if there are no Den neighbors
			if (allDenNeighbors.isEmpty()) {
				return score;
			}
			
			for (Tile n : allDenNeighbors) {
				if (!n.isDen()) {
					continue;
				}
				score += getDenScore(n);
			}
			
			return score;
		}

}