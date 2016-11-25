package tigerzone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import tigerzone.TerrainType;

enum TerrainType {
	GAMETRAIL, JUNGLE, LAKE, BUFFALO, CROCODILE, DEER, BOAR, DEN, END
}

public class Tile {

	// Each tile has the corrds
	// of where its at on the board

	private int row;
	private int col;

	// string code
	private String code;

	private int type;

	private boolean visited;

	private Board board;

	private int degrees;

	private TerrainType[] tilePortionType = new TerrainType[9];

	private int trailEdgeCount;

	private int[] clusterIndices = new int[9];

	public Tile() {

	}

	public Tile(TerrainType[] tilePortionType, int type) {
		this.type = type;
		this.tilePortionType = tilePortionType;

		// count number of gametrail edges this tile has
		for (int i = 0; i < tilePortionType.length; i++) {
			if (i % 2 != 0) {
				if (tilePortionType[i] == TerrainType.GAMETRAIL) {
					trailEdgeCount++;
				}
			}
		}

	}

	public Tile(TerrainType[] tilePortionType, int type, int row, int col) {
		this(tilePortionType, type);
		this.row = row;
		this.col = col;
	}

	public Tile(TerrainType[] tilePortionType, int type, int degrees, int row, int col) {
		this(tilePortionType, type, row, col);
		this.degrees = degrees;

	}

	// If number of trail edges is 2 then it's a continuation road tile
	public boolean isContinuation() {
		return trailEdgeCount == 2;
	}

	// determine if it's a t-junction, 4 way cross road, or one way road end
	public boolean isTrailEnd() {
		return (trailEdgeCount > 2 || trailEdgeCount == 1);
	}

	/**
	 * Add a list to a list of lists, but only if it does not already exist in
	 * the list of lists
	 * 
	 * @param lists
	 * @param list
	 */
	public void addDistinctList(List<List<Tile>> lists, List<Tile> list) {

		// Don't want to add empty lists
		if (list.size() == 1) {
			return;
		}

		// If there are no existing lists, then add the list
		if (lists.isEmpty()) {
			lists.add(list);
			return;
		}

		// Create a set from the passed in list
		Set<Tile> inSet = new HashSet<Tile>(list);

		// Go through each list in the list of lists
		for (List<Tile> lList : lists) {
			// If the two lists are of different size, we know they
			// are two different lists, continue
			if (lList.size() != list.size()) {
				continue;
			}

			// Else they're the same size and could possibly be the
			// same list (loop)
			// Create a new set from the lList elements
			Set<Tile> tSet = new HashSet<Tile>(lList);
			// If the elements in both lists are the same, they are the
			// same list, so return
			if (tSet.equals(inSet)) {
				return;
			}
		}

		// They are all different lists, so it's safe to add it to the list
		lists.add(list);

	}

	public int getTrailScore() {

		int score = 0;

		// get the number of trails emanating from this tile
		int trailCount = getTrailEdgeCount();

		// no trails emanating from this tile, so return a
		// score of 0
		if (trailCount == 0) {
			return score;
		}

		// A list of trails emanating from this tile
		List<List<Tile>> trails = new ArrayList<List<Tile>>();

		List<Tile> trail;
		if (this.getTopEdge() == TerrainType.GAMETRAIL) {
			this.getBoard().clearVisited();
			this.setVisited(true);
			trail = new ArrayList<Tile>();
			trail.add(this);
			isRoadComplete(trail, getBoard().getTile(row + 1, col));
			trails.add(trail);
		}
		if (this.getBottomEdge() == TerrainType.GAMETRAIL) {
			this.getBoard().clearVisited();
			this.setVisited(true);
			trail = new ArrayList<Tile>();
			trail.add(this);
			isRoadComplete(trail, getBoard().getTile(row - 1, col));
			trails.add(trail);
		}
		if (this.getRightEdge() == TerrainType.GAMETRAIL) {
			this.getBoard().clearVisited();
			this.setVisited(true);
			trail = new ArrayList<Tile>();
			trail.add(this);
			isRoadComplete(trail, getBoard().getTile(row, col + 1));
			trails.add(trail);
		}
		if (this.getLeftEdge() == TerrainType.GAMETRAIL) {
			this.getBoard().clearVisited();
			this.setVisited(true);
			trail = new ArrayList<Tile>();
			trail.add(this);
			isRoadComplete(trail, getBoard().getTile(row, col - 1));
			trails.add(trail);
		}

		// Filtered lsit of trails based on the original trails list
		// We'll be using addTrail() to filter out any duplicates and
		// non-existent trails
		// (trails of only size 1)
		List<List<Tile>> uniqueTrails = new ArrayList<List<Tile>>();

		for (List<Tile> originalTrail : trails) {
			addDistinctList(uniqueTrails, originalTrail);
		}

		// set score to trailCount - the number of trails emanating from tile.
		// Each of the trails will be scored individually
		score = trailCount - uniqueTrails.size();

		// Set used to determine if multiple trails share the same end tile
		Set<Tile> endTiles = new HashSet<Tile>();

		// Iterate through all the "unique" trails that emanate from this tile
		for (List<Tile> cTrail : uniqueTrails) {
			Tile frontTile = cTrail.get(0);
			Tile endTile = cTrail.get(cTrail.size() - 1);
			endTiles.add(endTile);
			score += cTrail.size();
			// if the trail has a proper ending, then see if there is a tiger in
			// the trail
			if (endTile.isTrailEnd()) {
				// Tiger
			}
			// if the trail forms a loop, compensate for the fact that the start
			// tile is found more than once in the trail
			if (frontTile == endTile) {
				score--;
			}
		}

		// compensate for those trails that shared the same end tile, thus
		// it was counted multiple times
		if (!(uniqueTrails.size() > 1 && endTiles.size() == 1)) {
			score -= (uniqueTrails.size() - endTiles.size());
		}

		// compensate for the fact that all the trails share the same
		// starting tile, thus its counted multiple times.
		if (uniqueTrails.size() > 0) {
			score -= (uniqueTrails.size() - 1);
		}
		return score;
	}

	// Get the score if this is a den tile
	// Return 0 if it's not a den
	public int getDenScore() {
		int score = 0;

		// If this is not a den tile, just return
		if (!this.isDen()) {
			return score;
		}

		// Else get all its possible 8 neighbors
		List<Tile> neighbors = this.getBoard().getDenNeighbors(this.getRow(), this.getCol());
		// Return the size of that list plus one to include itself
		return neighbors.size() + 1;
	}

	// Check if the tile completes an existing den
	public static boolean doesCompleteDen(Tile tile) {

		List<Tile> neighbors = tile.getBoard().getDenNeighbors(tile.getRow(), tile.getCol());
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
		List<Tile> neighbors = den.getBoard().getNeighbors(den.getRow(), den.getCol());

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
			if (n.getRow() == den.getRow() + 1 || n.getRow() == den.getRow() - 1) {
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
	 * This recursive method follows a trail and determines if the trail
	 * completes or not. As the method follows the trail, it adds to the path
	 * followed;i.e., the end result is the full trail/path followed.
	 */
	public static boolean isRoadComplete(List<Tile> path, Tile current) {

		// do nothing if there is no current tile
		if (current == null) {
			return false;
		}

		// record from where we came
		Tile previous = path.get(path.size() - 1);

		// add this current tile to the path
		path.add(current);

		// if this current tile was previously visited, then the trail has
		// looped and its complete
		if (current.isVisited()) {
			return true;
		}

		// mark current tile as being visited
		current.setVisited(true);

		// return true if we've reached a trail end; e.g., a Den
		if (current.isTrailEnd()) {
			return true;
		}

		// get the neighbors for this current tile
		List<Tile> neighbors = current.getBoard().getNeighbors(current.getRow(), current.getCol());

		boolean completed;

		// Iterate through the neighbors that allow the road to continue; i.e.,
		// find those neighboring tiles that share a trail edge
		for (Tile neighbor : neighbors) {

			// can't go back from where we came
			if (neighbor == previous) {
				continue;
			}

			// Check if neighbor is in same row
			if (neighbor.getRow() == current.getRow()) {
				if (neighbor.getCol() > current.getCol()) {
					// This is right neighbor - is there a trail
					// leading to that neighbor?
					if (current.getRightEdge() == TerrainType.GAMETRAIL) {
						// yes, so follow that trail
						completed = isRoadComplete(path, neighbor);
						// return true if the trail completed
						if (completed == true) {
							return true;
						}
						// move on to next neighbor
					}
				} else {
					// This is left neighbor- is there a trail
					// leading to that neighbor?
					if (current.getLeftEdge() == TerrainType.GAMETRAIL) {
						completed = isRoadComplete(path, neighbor);
						if (completed == true) {
							return true;
						}
					}
				}
			}

			// If not in the same row, it must be in the same column
			if (neighbor.getCol() == current.getCol()) {
				if (neighbor.getRow() > current.getRow()) {
					// This is bottom neighbor - is there a trail
					// leading to that neighbor
					if (current.getBottomEdge() == TerrainType.GAMETRAIL) {
						completed = isRoadComplete(path, neighbor);
						if (completed == true) {
							return true;
						}
					}

				} else {
					if (current.getTopEdge() == TerrainType.GAMETRAIL) {
						// This is top neighbor - is there a trail
						// leading to that neighbor
						completed = isRoadComplete(path, neighbor);
						if (completed == true) {
							return true;
						}
					}
				}
			}
		}

		// the trail never completed.
		return false;
	}

	public int getLakeScore() {

		int score = 0;

		if (!isLake()) {
			return score;
		}

		List<List<Tile>> lakes = new ArrayList<List<Tile>>();
		List<Tile> lake;

		if (this.getTopEdge() == TerrainType.LAKE) {
			this.getBoard().clearVisited();
			this.setVisited(true);
			lake = new ArrayList<Tile>();
			lake.add(this);
			isLakeComplete(lake, getBoard().getTile(row + 1, col));
			lakes.add(lake);
		}
		if (this.getBottomEdge() == TerrainType.LAKE) {
			this.getBoard().clearVisited();
			this.setVisited(true);
			lake = new ArrayList<Tile>();
			lake.add(this);
			isRoadComplete(lake, getBoard().getTile(row - 1, col));
			lakes.add(lake);
		}
		if (this.getRightEdge() == TerrainType.LAKE) {
			this.getBoard().clearVisited();
			this.setVisited(true);
			lake = new ArrayList<Tile>();
			lake.add(this);
			isRoadComplete(lake, getBoard().getTile(row, col + 1));
			lakes.add(lake);
		}
		if (this.getLeftEdge() == TerrainType.LAKE) {
			this.getBoard().clearVisited();
			this.setVisited(true);
			lake = new ArrayList<Tile>();
			lake.add(this);
			isRoadComplete(lake, getBoard().getTile(row, col - 1));
			lakes.add(lake);
		}

		// Filtered list of lakes based on the original lakes list
		// We'll be using addDistinctList() to filter out any duplicates and
		// non-existent lakes (lakes of only size 1)
		List<List<Tile>> uniqueLakes = new ArrayList<List<Tile>>();

		for (List<Tile> originalLake : lakes) {
			addDistinctList(uniqueLakes, originalLake);
		}

		boolean lakeComplete = true;
		if (this.isLakeCenter()) {
			for (List<Tile> l : uniqueLakes) {
				Tile startTile = l.get(0);
				Tile endTile = l.get(l.size() - 1);
				if (!endTile.isLakeCenter()) {
					lakeComplete = false;
				}
				if (startTile != endTile) {
					score += l.size();
				} else {
					score += l.size() - 1;
				}
			}
			if (lakeComplete) {
				score *= 2;
			}
			return (score == 0) ? 1 : score;			
		}

		for (List<Tile> l : uniqueLakes) {
			lakeComplete = true;
			Tile startTile = l.get(0);
			Tile endTile = l.get(l.size() - 1);
			if (!endTile.isLakeCenter()) {
				lakeComplete = false;
			}
			if (startTile != endTile) {
				score += l.size();
			} else {
				score += l.size() - 1;
			}
			if (lakeComplete) {
				score *= 2;
			}
		}
		return score;

	}

	public static boolean isLakeComplete(List<Tile> lake, Tile current) {

		// do nothing if there is no current tile
		if (current == null) {
			return false;
		}

		// record from where we came
		Tile previous = lake.get(lake.size() - 1);

		// add this current tile to the lake
		lake.add(current);

		// if there is no lake in the middle, then the lake cannot continiue and
		// is complete
		if (!current.isLakeCenter()) {
			return true;
		}

		// since there is a lake in the center, we should be able to advance the
		// lake

		// if this current tile was previously visited, then the lake has
		// looped and its complete
		if (current.isVisited()) {
			return true;
		}

		// mark current tile as being visited
		current.setVisited(true);

		// get the neighbors for this current tile
		List<Tile> neighbors = current.getBoard().getNeighbors(current.getRow(), current.getCol());

		boolean completed;

		// Iterate through the neighbors that allow the lake to continue; i.e.,
		// find those neighboring tiles that share a lake edge
		for (Tile neighbor : neighbors) {

			// can't go back from where we came
			if (neighbor == previous) {
				continue;
			}

			// Check if neighbor is in same row
			if (neighbor.getRow() == current.getRow()) {
				if (neighbor.getCol() > current.getCol()) {
					// This is right neighbor - is there a lake
					// leading to that neighbor?
					if (current.getRightEdge() == TerrainType.LAKE) {
						// yes, so follow that lake
						completed = isLakeComplete(lake, neighbor);
						// return true if the lake completed
						if (completed == false) {
							return false;
						}
						// move on to next neighbor
					}
				} else {
					// This is left neighbor- is there a trail
					// leading to that neighbor?
					if (current.getLeftEdge() == TerrainType.LAKE) {
						completed = isLakeComplete(lake, neighbor);
						if (completed == false) {
							return false;
						}
					}
				}
			}

			// If not in the same row, it must be in the same column
			if (neighbor.getCol() == current.getCol()) {
				if (neighbor.getRow() > current.getRow()) {
					// This is bottom neighbor - is there a lake
					// leading to that neighbor
					if (current.getBottomEdge() == TerrainType.LAKE) {
						completed = isLakeComplete(lake, neighbor);
						if (completed == false) {
							return false;
						}
					}

				} else {
					if (current.getTopEdge() == TerrainType.LAKE) {
						// This is top neighbor - is there a lake
						// leading to that neighbor
						completed = isLakeComplete(lake, neighbor);
						if (completed == false) {
							return false;
						}
					}
				}
			}
		}

		// the lake never completed.
		return true;
	}

	// Checks if tile contains a lake
	public boolean isLake() {
		if (this.isLakeCenter()) {
			return true;
		}

		if (this.getLeftEdge() == TerrainType.LAKE || this.getRightEdge() == TerrainType.LAKE
				|| this.getTopEdge() == TerrainType.LAKE || this.getBottomEdge() == TerrainType.LAKE) {
			return true;
		}

		return false;
	}

	public TerrainType[] getTilePortionType() {
		return tilePortionType;
	}

	public void setTilePortionType(TerrainType[] tilePortionType) {
		this.tilePortionType = tilePortionType;
	}

	public int[] getClusterIndices() {
		return clusterIndices;
	}

	public void setClusterIndices(int[] clusterIndices) {
		this.clusterIndices = clusterIndices;
	}

	public int getType() {
		return type;
	}

	public int getDegrees() {
		return degrees;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public TerrainType getTopEdge() {
		return tilePortionType[1];
	}

	public TerrainType getBottomEdge() {
		return tilePortionType[7];
	}

	public TerrainType getLeftEdge() {
		return tilePortionType[3];
	}

	public TerrainType getRightEdge() {
		return tilePortionType[5];
	}

	public boolean isDen() {
		return tilePortionType[4] == TerrainType.DEN;
	}

	public boolean isLakeCenter() {
		return tilePortionType[4] == TerrainType.LAKE;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getTrailEdgeCount() {
		return trailEdgeCount;
	}

	public void setTrailEdgeCount(int trailEdgeCount) {
		this.trailEdgeCount = trailEdgeCount;
	}

	public static void main(String[] args) {
		Tile t1 = new Tile();
		Tile t2 = new Tile();
		Tile t3 = t1;

		System.out.println(t1.equals(t3));
		System.out.println(t1 == t3);
		System.out.println(t1 == t2);

	}
}
