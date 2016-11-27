package tigerzone;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static tigerzone.Utils.*;
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
	private int lakeEdgeCount;

	private int[] clusterIndices = new int[9];

	public Tile() {
		//TODO: MAKE SURE TO DELETE THIS
		this.type = 0;
	}

	public Tile(TerrainType[] tilePortionType, int type) {
		this.type = type;
		this.tilePortionType = tilePortionType;

		// count number of gametrail edges for this tile
		// every odd element of the tilePortionType array
		// represents the center of an edge
		for (int i = 0; i < tilePortionType.length; i++) {
			if (i % 2 != 0) {
				if (tilePortionType[i] == TerrainType.GAMETRAIL) {
					trailEdgeCount++;
				}
			}
		}

		// count number of lake edges for this tile
		// if there is a lake center, then its all one continuous lake else
		// they're separate lakes represents the center of an edge
		if (isLakeCenter()) {
			lakeEdgeCount = 1;
		} else {
			for (int i = 0; i < tilePortionType.length; i++) {
				if (i % 2 != 0) {
					if (tilePortionType[i] == TerrainType.LAKE) {
						lakeEdgeCount++;
					}
				}
			}
		}

	}

	public Tile(TerrainType[] tilePortionType, int type, int row, int col) {
		this(tilePortionType, type);
		this.row = row;
		this.col = col;
	}

	public Tile(TerrainType[] tilePortionType, int type, int degrees, int row,
			int col) {
		this(tilePortionType, type, row, col);
		this.degrees = degrees;

	}

	// returns true if this Tile includes a trail continuation
	public boolean isContinuation() {
		return trailEdgeCount == 2;
	}

	// returns true if this Tile is a t-junction, 4 way cross road, or one way
	// road end
	public boolean isTrailEnd() {
		return (trailEdgeCount > 2 || trailEdgeCount == 1);
	}

	/**
	 * Returns the score for all trails emanating from this tile.
	 * 
	 * @return
	 */
	public int getTrailScore() {

		int score = 0;

		// make sure Tile pertains to board
		if (this.getBoard() == null) {
			return 0;
		}

		// get the number of trails emanating from this tile
		int trailCount = getTrailEdgeCount();

		// System.out.println("trailCount1 = " + trailCount);

		if (trailCount == 2) {
			--trailCount;
		}

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
			isTrailComplete(trail, getBoard().getTile(row - 1, col));
			trails.add(trail);
		}
		if (this.getBottomEdge() == TerrainType.GAMETRAIL) {
			this.getBoard().clearVisited();
			this.setVisited(true);
			trail = new ArrayList<Tile>();
			trail.add(this);
			isTrailComplete(trail, getBoard().getTile(row + 1, col));
			trails.add(trail);
		}
		if (this.getRightEdge() == TerrainType.GAMETRAIL) {
			this.getBoard().clearVisited();
			this.setVisited(true);
			trail = new ArrayList<Tile>();
			trail.add(this);
			isTrailComplete(trail, getBoard().getTile(row, col + 1));
			trails.add(trail);
		}
		if (this.getLeftEdge() == TerrainType.GAMETRAIL) {
			this.getBoard().clearVisited();
			this.setVisited(true);
			trail = new ArrayList<Tile>();
			trail.add(this);
			isTrailComplete(trail, getBoard().getTile(row, col - 1));
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

		// System.out.println("uniqueTrails size = " + uniqueTrails.size());
		// System.out.println("trailCount = " + trailCount);

		// set score to trailCount - the number of trails emanating from tile.
		// Each of the trails will be scored individually
		score = trailCount - uniqueTrails.size();

		// System.out.println("score = " + score);

		// Set used to determine if multiple trails share the same end tile
		Set<Tile> endTiles = new HashSet<Tile>();

		// Iterate through all the "unique" trails that emanate from this tile
		for (List<Tile> cTrail : uniqueTrails) {
			Tile frontTile = cTrail.get(0);
			Tile endTile = cTrail.get(cTrail.size() - 1);
			endTiles.add(endTile);

			// System.out.println("cTrail.size = " + cTrail.size());
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
				// if it is a junction and there is a loop, then two of the
				// junction trails heads bridge one trail, so we must decrement
				// one more time
				if (trailCount > 2) {
					score--;
				}
			}
			// System.out.println("score2 = " + score);

		}

		// compensate for those trails that shared the same end tile, thus
		// it was counted multiple times
		if (!(uniqueTrails.size() > 1 && endTiles.size() == 1)) {
			score -= (uniqueTrails.size() - endTiles.size());
		}

		// compensate for the fact that all the trails share the same
		// starting tile, thus its counted multiple times.
		// if (uniqueTrails.size() > 0) {
		// score -= (uniqueTrails.size() - 1);
		// }
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
		List<Tile> neighbors = this.getBoard().getDenNeighbors(this.getRow(),
				this.getCol());

		// Return the size of that list plus one to include itself
		return neighbors.size() + 1;
	}

	/**
	 * Returns the score for lakes emanating from this tile
	 * 
	 * @return
	 */
	public int getLakeScore() {

		int score = 0;

		// make sure Tile pertains to board
		if (this.getBoard() == null) {
			return 0;
		}

		// return 0 if this is not a lake tile
		if (!isLake()) {
			return score;
		}

		// System.out.println("lake score1 = " + score);

		// Create a list of possible lakes emanating from this tile
		List<List<Tile>> lakes = new ArrayList<List<Tile>>();
		List<Tile> lake;

		if (this.getTopEdge() == TerrainType.LAKE) {
			this.getBoard().clearVisited();
			this.setVisited(true);
			lake = new ArrayList<Tile>();
			lake.add(this);
			isLakeComplete(lake, getBoard().getTile(row - 1, col));
			lakes.add(lake);
		}
		if (this.getBottomEdge() == TerrainType.LAKE) {
			this.getBoard().clearVisited();
			this.setVisited(true);
			lake = new ArrayList<Tile>();
			lake.add(this);
			isLakeComplete(lake, getBoard().getTile(row + 1, col));
			lakes.add(lake);
		}
		if (this.getRightEdge() == TerrainType.LAKE) {
			this.getBoard().clearVisited();
			this.setVisited(true);
			lake = new ArrayList<Tile>();
			lake.add(this);
			isLakeComplete(lake, getBoard().getTile(row, col + 1));
			lakes.add(lake);
		}
		if (this.getLeftEdge() == TerrainType.LAKE) {
			this.getBoard().clearVisited();
			this.setVisited(true);
			lake = new ArrayList<Tile>();
			lake.add(this);
			isLakeComplete(lake, getBoard().getTile(row, col - 1));
			lakes.add(lake);
		}

		// System.out.println("lakes size = " + lakes.size());

		// Filtered list of lakes based on the original lakes list
		// We'll be using addDistinctList() to filter out any duplicates and
		// non-existent lakes (lakes of only size 1)
		List<List<Tile>> uniqueLakes = new ArrayList<List<Tile>>();

		// Begin the filtering process
		for (List<Tile> originalLake : lakes) {
			addDistinctList(uniqueLakes, originalLake);
		}

		// if this tile is a lake center, then do not touch its base score,
		// which should be 1
		if (!isLakeCenter()) {
			score = getLakeEdgeCount() - uniqueLakes.size();
		} else {
			score = getLakeEdgeCount();
		}

		// System.out.println("unique lakes size = " + uniqueLakes.size());
		// System.out.println("getLakeEdgeCount = " + getLakeEdgeCount());
		// System.out.println("lake score2 = " + score);
		// System.out.println("lake center = " + isLakeCenter());
		// System.out.println();

		boolean lakeComplete = true;
		// if this tile contains a lake center, then all lakes emanating from
		// this tile should represent one lake
		if (isLakeCenter()) {

			// Set used to determine if multiple lakes share the same end tile
			Set<Tile> endTiles = new HashSet<Tile>();

			// iterate through all the lake channels and see if they all
			// complete. we must iterate through all the channel regardless of
			// complete or not because they must all be scored
			for (List<Tile> l : uniqueLakes) {

				// System.out.println("unique lake size = " + l.size());

				// get the first and last tile in the channel to see if the
				// channel makes a complete loop
				Tile startTile = l.get(0);
				Tile endTile = l.get(l.size() - 1);
				endTiles.add(endTile);

				// if the channel does not complete in a lake center, then its
				// not a complete lake, so the channels emanating from this tile
				// do not represent one contiguous lake
				if (endTile.isLakeCenter()) {
					lakeComplete = false;
				}
				// does this one channel form a loop
				if (startTile != endTile) {
					score += l.size() - 1;
				} else {
					score += l.size() - 2;
					// we must do this because looping around to the same tile
					// also completes a lake!!
					lakeComplete = true;
				}
			}

			// System.out.println("endTiles size = " + endTiles.size());

			// compensate for those lakes that shared the same end tile, thus
			// it was counted multiple times
			score -= (uniqueLakes.size() - endTiles.size());

			// if all the channels collectively form one lake, then update the
			// score accordingly. Note that if there are no surrounding lake
			// tiles, then no need to check for completeness
			if (uniqueLakes.size() > 0 && lakeComplete) {
				score *= 2;
			}

			return (score == 0) ? 1 : score;
		}

		// System.out.println("not lake center");

		// Base score must be 0 when working with separate lakes.
		score = 0;

		// tile does not contain a lake center, therefore all lake channel
		// emanating from this tile must be treated as separate lakes/channels.
		for (List<Tile> l : uniqueLakes) {

			int lakeScore = 0;

			// System.out.println("unique lake size = " + l.size());
			lakeComplete = true;

			Tile startTile = l.get(0);
			Tile endTile = l.get(l.size() - 1);

			if (endTile.isLakeCenter()) {
				lakeComplete = false;
			}
			// System.out.println("lake complete = " + lakeComplete);

			if (startTile != endTile) {
				lakeScore += l.size();
			} else {
				lakeScore += l.size() - 1;
			}

			if (lakeComplete) {
				lakeScore *= 2;
			}

			// update the base score with this separate lake's score
			score += lakeScore;
		}

		return score;
	}

	/**
	 * Returns true if the Tile contains a lake
	 * 
	 * @return
	 */
	public boolean isLake() {
		if (this.isLakeCenter()) {
			return true;
		}

		if (this.getLeftEdge() == TerrainType.LAKE
				|| this.getRightEdge() == TerrainType.LAKE
				|| this.getTopEdge() == TerrainType.LAKE
				|| this.getBottomEdge() == TerrainType.LAKE) {
			return true;
		}

		return false;
	}

	/**
	 * This method finds all neigboring Dens and collects their scores
	 * 
	 * @return
	 */
	public int collectDenScores() {
		int score = 0;

		if (this.getBoard() == null) {
			return score;
		}

		//System.out.println("collectDenScores: isDen returns " + this.isDen());
		
		// get my score if I'm a Den
		if (this.isDen()) {
			score += this.getDenScore();
		}

		// Look for neighbors that are Dens and get their score
		List<Tile> allDenNeighbors = getBoard().getDenNeighbors(getRow(),
				getCol());

		//System.out.println("collectDenScores: allDenNeighbors size = " + allDenNeighbors.size());

		// return if there are no Den neighbors
		if (allDenNeighbors.isEmpty()) {
			return score;
		}

		for (Tile n : allDenNeighbors) {
			if (!n.isDen()) {
				continue;
			}
			score += n.getDenScore();
		}
		
		return score;
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

	/**
	 * @return the lakeEdgeCount
	 */
	public int getLakeEdgeCount() {
		return lakeEdgeCount;
	}

	/**
	 * @param lakeEdgeCount
	 *            the lakeEdgeCount to set
	 */
	public void setLakeEdgeCount(int lakeEdgeCount) {
		this.lakeEdgeCount = lakeEdgeCount;
	}
}