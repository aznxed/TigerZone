package tigerzone;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board {

	public static int MAX_ROWS = 153;
	public static int MAX_COLS = 153;

	public static int CENTER_CELL = MAX_ROWS / 2 + 1;

	private int topBound;
	private int bottomBound;
	private int leftBound;
	private int rightBound;

	private Tile[][] board = new Tile[MAX_ROWS][MAX_COLS];

	// Keep a list of all the tiles placed on the board
	private List<Tile> placedTiles = new ArrayList<Tile>();

	public int getTopBound() {
		return this.topBound;
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
	 * Returns the 8 possible neighbors that surround the tile at the given
	 * coordinates. Note that not all 8 may be returned.
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public List<Tile> getDenNeighbors(int x, int y) {

		// List of all possible 8 neighbors
		List<Tile> denNeighbors = new ArrayList<Tile>();

		//System.out.println("getDenNeighbors: called with coords " + x + " " + y);

		// return an empty list if either of the coordinates are not valid
		if (x < 0 || x >= MAX_ROWS || y < 0 || y >= MAX_COLS) {
			return denNeighbors;
		}

		// return an empty list if ther is no Tile at the given coordinates
		if (board[x][y] == null) {
			return denNeighbors;
		}

		//System.out.println("getDenNeighbors: collecting neighbors");
		// iterate the three possible rows and their columns. we skip the tile
		// whose coordinates are passed in
		for (int i = x - 1; i <= x + 1; i++) {
			// skip this row if it is out of bounds
			if (i < 0 || i >= MAX_ROWS) {
				continue;
			}
			// iterate the three possible columns for this row
			for (int j = y - 1; j <= y + 1; j++) {
				// skip this column if it is out of bounds
				if (j < 0 || j >= MAX_COLS) {
					continue;
				}
				// skip this cell if its coordinates are those of x and y
				if (i == x && j == y) {
					continue;
				}
				if (board[i][j] != null) {
					denNeighbors.add(board[i][j]);
				}
			}
		}

		return denNeighbors;
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
	}

	/**
	 * Add a Tile to the board. It will first validate that the Tile can be
	 * added to that position.
	 *
	 * @param x
	 * @param y
	 * @param tile
	 * @return
	 */
	public boolean addXTile(int x, int y, Tile tile) {
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

	public Tile rotateTile(Tile tile, int degrees) {
		Tile rotateTile = new Tile(tile.getTilePortionType(), tile.getType(),
				degrees, tile.getRow(), tile.getCol());

		TerrainType[] rotateArr = new TerrainType[9];
		if (degrees == 90) {
			rotateArr[0] = tile.getTilePortionType()[2];
			rotateArr[1] = tile.getTilePortionType()[5];
			rotateArr[2] = tile.getTilePortionType()[8];
			rotateArr[3] = tile.getTilePortionType()[1];
			rotateArr[5] = tile.getTilePortionType()[7];
			rotateArr[6] = tile.getTilePortionType()[0];
			rotateArr[7] = tile.getTilePortionType()[3];
			rotateArr[8] = tile.getTilePortionType()[6];
		}
		if (degrees == 180) {
			for (int i = 0; i < 9; i++) {
				rotateArr[i] = tile.getTilePortionType()[8 - i];
			}
		}
		if (degrees == 270) {
			rotateArr[0] = tile.getTilePortionType()[6];
			rotateArr[1] = tile.getTilePortionType()[3];
			rotateArr[2] = tile.getTilePortionType()[0];
			rotateArr[3] = tile.getTilePortionType()[7];
			rotateArr[5] = tile.getTilePortionType()[1];
			rotateArr[6] = tile.getTilePortionType()[8];
			rotateArr[7] = tile.getTilePortionType()[5];
			rotateArr[8] = tile.getTilePortionType()[2];
		}
		rotateTile.setTilePortionType(rotateArr);
		return rotateTile;
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
						possibleMoves
								.add(rotateTile(tile, validOrients.get(k)));

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

	public void addTile(Tile tile) {
		// For now just take the first possible tile

		// I don't get this! Comments please!
		if (!(getPossibleMoves(tile).isEmpty())) {

			// Keep a list of all the tiles placed on the board
			placedTiles.add(tile);
			tile.setBoard(this);

			Random rand = new Random();
			// This doesn't make sense to me. Shouldn't we be adding the tile
			// passed in and not the tile you're getting from the possible moves
			// list?
			Tile addTile = getPossibleMoves(tile).get(
					rand.nextInt(getPossibleMoves(tile).size()));
			int x = addTile.getRow();
			int y = addTile.getCol();
			board[x][y] = addTile;

			/*
			 * List<Tile> nbors = getNeighbors(tile.getRow(), tile.getCol());
			 *
			 * //First tile placed on board if(nbors.size() == 0){
			 *
			 * //Start clusters //Call switch statement
			 *
			 * } else{ for(int i = 0; i < nbors.size(); i++){
			 *
			 * List<TerrainType> t = possibleClusters(nbors.get(i).getCode());
			 *
			 * //based on what switch statement gives us //jungleClusters //Add
			 * tile to Cluster cluster = new Cluster(TerrainType.JUNGLE);
			 * jungleClusters.add(cluster);
			 *
			 *
			 * }
			 *
			 *
			 *
			 * }
			 */

			if (true) {

			}

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
	}

	/**
	 * Removes the Tile at the given coordinates
	 *
	 * @param x
	 * @param y
	 */
	public void removeTile(int x, int y) {
		board[x][y] = null;
	}

	// Pass in tile and number of jungle clusters to start
	public void addJungleCluster(Tile tile, int x) {

	}

	public void addRoadCluster(Tile tile) {

	}

	public void addLakeCluster(Tile tile) {

	}

	public void initTiles(List<Tile> tiles) {

		TerrainType[] tileA = { TerrainType.JUNGLE, TerrainType.JUNGLE,
				TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
				TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
				TerrainType.JUNGLE };// 1:no:empty
										// field
		TerrainType[] tileB = { TerrainType.JUNGLE, TerrainType.JUNGLE,
				TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.DEN,
				TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
				TerrainType.JUNGLE }; // 4:
										// no:
										// monistary
		TerrainType[] tileC = { TerrainType.JUNGLE, TerrainType.JUNGLE,
				TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.DEN,
				TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE }; // 2:
										// no
										// :
										// monistary
										// with
										// 1
										// road
										// leaving.
		TerrainType[] tileD = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.END,
				TerrainType.GAMETRAIL, TerrainType.JUNGLE,
				TerrainType.GAMETRAIL, TerrainType.JUNGLE }; // 4:
																// no:
																// crossrouads
																// 4
																// roads
																// 4
																// fields.
		TerrainType[] tileE = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE }; // 8: no: a straight road with 2 fields on
										// the side 19
		TerrainType[] tileF = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.GAMETRAIL, TerrainType.JUNGLE, TerrainType.JUNGLE,
				TerrainType.JUNGLE, TerrainType.JUNGLE }; // 9:no:
															// a
															// curved
															// road
															// has
															// 2
															// total
															// fields
		TerrainType[] tileG = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.END,
				TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE }; // 4:
										// no:
										// three
										// way
										// crossroads
										// 3
										// total
										// fields
		TerrainType[] tileH = { TerrainType.LAKE, TerrainType.LAKE,
				TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE,
				TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE,
				TerrainType.LAKE }; // 1:no:
									// giant
									// city
									// tile
									// on
									// each
									// side
		TerrainType[] tileI = { TerrainType.JUNGLE, TerrainType.JUNGLE,
				TerrainType.JUNGLE, TerrainType.LAKE, TerrainType.LAKE,
				TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE,
				TerrainType.LAKE }; // 4:
									// no:
									// city
									// on
									// 3
									// sides
									// field
									// on
									// top
									// side
		TerrainType[] tileJ = { TerrainType.JUNGLE, TerrainType.LAKE,
				TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.LAKE,
				TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.JUNGLE,
				TerrainType.JUNGLE }; // 5:no:
										// diagonal
										// tile
										// bottom
										// left
										// field
										// top
										// right
										// city
										// 23
		TerrainType[] tileK = { TerrainType.JUNGLE, TerrainType.JUNGLE,
				TerrainType.JUNGLE, TerrainType.LAKE, TerrainType.LAKE,
				TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.JUNGLE,
				TerrainType.JUNGLE }; // 3:no"
										// one
										// city
										// from
										// left
										// to
										// right
										// going
										// thru
										// center
										// with
										// 2
										// fields
		TerrainType[] tileL = { TerrainType.JUNGLE, TerrainType.LAKE,
				TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
				TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.LAKE,
				TerrainType.JUNGLE }; // 3:
										// no:
										// inverse
										// of
										// above
										// 2
										// citys
										// 1
										// field
		TerrainType[] tileM = { TerrainType.JUNGLE, TerrainType.LAKE,
				TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
				TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
				TerrainType.JUNGLE }; // 5:no:
										// city
										// on
										// top
										// one
										// field
		TerrainType[] tileN = { TerrainType.JUNGLE, TerrainType.JUNGLE,
				TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
				TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.LAKE,
				TerrainType.JUNGLE }; // 2:no:
										// two
										// cities
										// adjacent
		TerrainType[] tileO = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.GAMETRAIL, TerrainType.LAKE, TerrainType.JUNGLE,
				TerrainType.JUNGLE, TerrainType.JUNGLE }; // 1:no:
															// road
															// in
															// top
															// left
															// city
															// on
															// right
		TerrainType[] tileP = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.GAMETRAIL, TerrainType.LAKE, TerrainType.JUNGLE,
				TerrainType.JUNGLE, TerrainType.JUNGLE }; // 2:yes:same
															// as
															// above
															// but
															// w
															// boar
		TerrainType[] tileQ = { TerrainType.JUNGLE, TerrainType.JUNGLE,
				TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.GAMETRAIL, TerrainType.LAKE, TerrainType.JUNGLE,
				TerrainType.GAMETRAIL, TerrainType.JUNGLE }; // 1: no: same as o
																// but roads go
																// from left
																// to down
		TerrainType[] tileR = { TerrainType.JUNGLE, TerrainType.JUNGLE,
				TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.GAMETRAIL, TerrainType.LAKE, TerrainType.JUNGLE,
				TerrainType.GAMETRAIL, TerrainType.JUNGLE }; // 2:yes: same as
																// above but has
																// animal 19
		TerrainType[] tileS = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE }; // no idea:no feild L road M city
										// Rightside
		TerrainType[] tileT = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE }; // 2: Yes: same as above w deer
		TerrainType[] tileU = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE, TerrainType.LAKE, TerrainType.END,
				TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE,
				TerrainType.LAKE }; // 3:no:road
									// Top
									// city
									// L
									// R
									// B
		TerrainType[] tileV = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.END,
				TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE }; // 1:no:3
										// progned
										// road
										// facing
										// left
										// city
										// on
										// right
		TerrainType[] tileW = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.END,
				TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE }; // 2:yes:
										// same
										// as
										// above
										// but
										// with
										// boar
		TerrainType[] tileX = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.LAKE,
				TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.LAKE,
				TerrainType.LAKE }; // 3:no:
									// road
									// goues
									// left
									// to
									// up
									// city
									// R
									// &
									// Botom
		TerrainType[] tileY = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.LAKE,
				TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.LAKE,
				TerrainType.LAKE }; // 2:yes:
									// same
									// as
									// above
									// but
									// has
									// goat
		TerrainType[] tileZ = { TerrainType.JUNGLE, TerrainType.LAKE,
				TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE }; // 1:no city Top road B field L field R
		TerrainType[] tileZZ = { TerrainType.JUNGLE, TerrainType.LAKE,
				TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.END,
				TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE };// 2:yes:see
		// above

		// CROCODILE TILE, I need middle to be LAKE
		TerrainType[] tileZZZ = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
				TerrainType.JUNGLE, TerrainType.LAKE, TerrainType.LAKE,
				TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE,
				TerrainType.LAKE };

		Tile startTile = new Tile(tileS, 19, 0, CENTER_CELL, CENTER_CELL);
		board[CENTER_CELL][CENTER_CELL] = startTile;

		// Adding tiles to deck
		Tile tile = new Tile(tileA, 1);
		tiles.add(tile);

		tile = new Tile(tileB, 2);
		for (int i = 0; i < 4; i++) {
			tiles.add(tile);
		}

		tile = new Tile(tileC, 3);
		tiles.add(tile);
		tiles.add(tile);

		tile = new Tile(tileD, 4);
		tiles.add(tile);

		tile = new Tile(tileE, 5);
		for (int i = 0; i < 8; i++) {
			tiles.add(tile);
		}

		tile = new Tile(tileF, 6);
		for (int i = 0; i < 9; i++) {
			tiles.add(tile);
		}

		tile = new Tile(tileG, 7);
		for (int i = 0; i < 4; i++) {
			tiles.add(tile);
		}

		tile = new Tile(tileH, 8);
		tiles.add(tile);

		tile = new Tile(tileI, 9);
		for (int i = 0; i < 4; i++) {
			tiles.add(tile);
		}

		tile = new Tile(tileJ, 10);
		for (int i = 0; i < 5; i++) {
			tiles.add(tile);
		}

		tile = new Tile(tileK, 11);
		for (int i = 0; i < 3; i++) {
			tiles.add(tile);
		}

		tile = new Tile(tileL, 12);
		for (int i = 0; i < 3; i++) {
			tiles.add(tile);
		}

		tile = new Tile(tileM, 13);
		for (int i = 0; i < 5; i++) {
			tiles.add(tile);
		}

		tile = new Tile(tileN, 14);
		tiles.add(tile);
		tiles.add(tile);

		tile = new Tile(tileO, 15);
		tiles.add(tile);

		tile = new Tile(tileP, 16);
		tiles.add(tile);
		tiles.add(tile);

		tile = new Tile(tileQ, 17);
		tiles.add(tile);

		tile = new Tile(tileR, 18);
		tiles.add(tile);
		tiles.add(tile);

		tile = new Tile(tileS, 19);
		tiles.add(tile);
		tiles.add(tile);

		tile = new Tile(tileT, 20);
		tiles.add(tile);
		tiles.add(tile);

		tile = new Tile(tileU, 21);
		tiles.add(tile);

		tile = new Tile(tileV, 22);
		tiles.add(tile);

		tile = new Tile(tileW, 23);
		tiles.add(tile);
		tiles.add(tile);

		tile = new Tile(tileX, 24);
		for (int i = 0; i < 3; i++) {
			tiles.add(tile);
		}

		tile = new Tile(tileY, 25);
		tiles.add(tile);
		tiles.add(tile);

		tile = new Tile(tileZ, 26);
		tiles.add(tile);

		tile = new Tile(tileZZ, 27);
		tiles.add(tile);
		tiles.add(tile);

		tile = new Tile(tileZZZ, 28);
		tiles.add(tile);
		tiles.add(tile);

		Collections.shuffle(tiles);
	}

	public static void main(String[] args) {
		Board gameBoard = new Board();
		List<Tile> tiles = new ArrayList<Tile>();

		gameBoard.setTopBound(CENTER_CELL - 1);
		gameBoard.setBottomBound(CENTER_CELL + 1);
		gameBoard.setLeftBound(CENTER_CELL - 1);
		gameBoard.setRightBound(CENTER_CELL + 1);

		String imagePath = "src/main/CarcassonneTiles/";

		gameBoard.initTiles(tiles);

		for (int i = 0; i < tiles.size(); i++) {
			gameBoard.addTile(tiles.get(i));
		}

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
		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(800, 1000));

		JPanel jp = new JPanel(new GridLayout(gameBoard.getBottomBound()
				- gameBoard.getTopBound() + 1, gameBoard.getRightBound()
				- gameBoard.getLeftBound() + 1, 0, 0));

		frame.pack();
		for (int i = gameBoard.getTopBound(); i <= gameBoard.getBottomBound(); i++) {
			for (int j = gameBoard.getLeftBound(); j <= gameBoard
					.getRightBound(); j++) {
				if (gameBoard.board[i][j] == null) {
					JLabel j29 = new JLabel();
					j29.setIcon(new ImageIcon(imagePath + "Tile29.png"));
					jp.add(j29);
				} else {
					JLabel j1 = new JLabel();
					ImageIcon II = new ImageIcon(imagePath + "Tile"
									+ Integer.toString(gameBoard.board[i][j]
											.getType())
									+ "."
									+ Integer.toString(gameBoard.board[i][j]
											.getDegrees()) + ".png");
					Image image = II.getImage(); // transform it
					int tileHeight = gameBoard.getBottomBound() - gameBoard.getTopBound();
					int tileWidth = gameBoard.getRightBound() - gameBoard.getLeftBound();
					//System.out.println("Height: " + tileHeight);
					//System.out.println("Width: " + tileWidth);
					Image newimg = image.getScaledInstance(frame.getContentPane().getWidth() / (tileWidth * (13 / 8)),
							frame.getContentPane().getHeight() / (tileHeight * (13 / 8)) ,
							java.awt.Image.SCALE_SMOOTH); // scale
															// it
															// the
															// smooth
															// way
					II = new ImageIcon(newimg);
					j1.setIcon(II);
					jp.add(j1);
				}
			}
		}

		frame.add(jp);
		frame.setVisible(true);
	}

}