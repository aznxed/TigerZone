package tigerzone.game;

import static tigerzone.AI.Utils.addDistinctList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class trails{
	/**
	 * This recursive method follows a trail and determines if the trail
	 * completes or not. As the method follows the trail, it adds to the path
	 * followed;i.e., the end result is the full trail/path followed.
	 */
	public static boolean isTrailComplete(List<Tile> path, Tile current) {

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
		List<Tile> neighbors = current.getBoard().getNeighbors(
				current.getRow(), current.getCol());

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
						completed = isTrailComplete(path, neighbor);
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
						completed = isTrailComplete(path, neighbor);
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
						completed = isTrailComplete(path, neighbor);
						if (completed == true) {
							return true;
						}
					}

				} else {
					if (current.getTopEdge() == TerrainType.GAMETRAIL) {
						// This is top neighbor - is there a trail
						// leading to that neighbor
						completed = isTrailComplete(path, neighbor);
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
	/**
	 * Returns the score for all trails emanating from this tile.
	 * 
	 * @return
	 */
	public int getTrailScore(Tile tile) //move to trails.java
	{

		int score = 0;

		// make sure Tile pertains to board
		if (tile.getBoard() == null) {
			return 0;
		}

		// get the number of trails emanating from this tile
		int trailCount = tile.getTrailEdgeCount();

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
		if (tile.getTopEdge() == TerrainType.GAMETRAIL) {
			tile.getBoard().clearVisited();
			tile.setVisited(true);
			trail = new ArrayList<Tile>();
			trail.add(tile);
			isTrailComplete(trail, tile.getBoard().getTile(tile.getRow() - 1, tile.getCol()));
			trails.add(trail);
		}
		if (tile.getBottomEdge() == TerrainType.GAMETRAIL) {
			tile.getBoard().clearVisited();
			tile.setVisited(true);
			trail = new ArrayList<Tile>();
			trail.add(tile);
			isTrailComplete(trail, tile.getBoard().getTile(tile.getRow() + 1, tile.getCol()));
			trails.add(trail);
		}
		if (tile.getRightEdge() == TerrainType.GAMETRAIL) {
			tile.getBoard().clearVisited();
			tile.setVisited(true);
			trail = new ArrayList<Tile>();
			trail.add(tile);
			isTrailComplete(trail, tile.getBoard().getTile(tile.getRow(), tile.getCol()+1));
			trails.add(trail);
		}
		if (tile.getLeftEdge() == TerrainType.GAMETRAIL) {
			tile.getBoard().clearVisited();
			tile.setVisited(true);
			trail = new ArrayList<Tile>();
			trail.add(tile);
			isTrailComplete(trail, tile.getBoard().getTile(tile.getRow(), tile.getCol()-1));
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
}