package tigerzone;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Contains a set of static utils used by the TigerZone package
 * 
 */
public class Utils {

	// Check if the tile completes an existing den
	public static boolean doesCompleteDen(Tile tile) {

		List<Tile> neighbors = tile.getBoard().getDenNeighbors(tile.getRow(),
				tile.getCol());
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
	 * This recursive method is used to follow a lake.
	 * 
	 * @param lake
	 *            - the lake being built
	 * @param current
	 *            - the tile that is currently on
	 * @return
	 */
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
	 * Add a list to a list of lists, but only if it does not already exist in
	 * the list of lists
	 * 
	 * @param lists
	 * @param list
	 */
	public static void addDistinctList(List<List<Tile>> lists, List<Tile> list) {

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

}