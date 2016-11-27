package tigerzone.game;

import static tigerzone.AI.Utils.addDistinctList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This recursive method is used to follow a lake.
 * 
 * @param lake
 *            - the lake being built
 * @param current
 *            - the tile that is currently on
 * @return
 */
public class lakes {
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
		List<Tile> neighbors = current.getBoard().getNeighbors(
				current.getRow(), current.getCol());
	
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
	/**
	 * Returns the score for lakes emanating from this tile
	 * 
	 * @return
	 */
	public int getLakeScore(Tile tile) //move to lakes.java
	{

		int score = 0;

		// make sure Tile pertains to board
		if (tile.getBoard() == null) {
			return 0;
		}

		// return 0 if this is not a lake tile
		if (!tile.isLake()) {
			return score;
		}

		// System.out.println("lake score1 = " + score);

		// Create a list of possible lakes emanating from this tile
		List<List<Tile>> lakes = new ArrayList<List<Tile>>();
		List<Tile> lake;

		if (tile.getTopEdge() == TerrainType.LAKE) {
			tile.getBoard().clearVisited();
			tile.setVisited(true);
			lake = new ArrayList<Tile>();
			lake.add(tile);
			isLakeComplete(lake, tile.getBoard().getTile(tile.getRow() - 1, tile.getCol()));
			lakes.add(lake);
		}
		if (tile.getBottomEdge() == TerrainType.LAKE) {
			tile.getBoard().clearVisited();
			tile.setVisited(true);
			lake = new ArrayList<Tile>();
			lake.add(tile);
			isLakeComplete(lake, tile.getBoard().getTile(tile.getRow() + 1, tile.getCol()));
			lakes.add(lake);
		}
		if (tile.getRightEdge() == TerrainType.LAKE) {
			tile.getBoard().clearVisited();
			tile.setVisited(true);
			lake = new ArrayList<Tile>();
			lake.add(tile);
			isLakeComplete(lake, tile.getBoard().getTile(tile.getRow(), tile.getCol()+1));
			lakes.add(lake);
		}
		if (tile.getLeftEdge() == TerrainType.LAKE) {
			tile.getBoard().clearVisited();
			tile.setVisited(true);
			lake = new ArrayList<Tile>();
			lake.add(tile);
			isLakeComplete(lake, tile.getBoard().getTile(tile.getRow(), tile.getCol()-1));
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
		if (!tile.isLakeCenter()) {
			score = tile.getLakeEdgeCount() - uniqueLakes.size();
		} else {
			score = tile.getLakeEdgeCount();
		}

		// System.out.println("unique lakes size = " + uniqueLakes.size());
		// System.out.println("getLakeEdgeCount = " + getLakeEdgeCount());
		// System.out.println("lake score2 = " + score);
		// System.out.println("lake center = " + isLakeCenter());
		// System.out.println();

		boolean lakeComplete = true;
		// if this tile contains a lake center, then all lakes emanating from
		// this tile should represent one lake
		if (tile.isLakeCenter()) {

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
	
}