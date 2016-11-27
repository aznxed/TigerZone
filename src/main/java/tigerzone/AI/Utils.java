package tigerzone.AI;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tigerzone.game.Tile;

/**
 * Contains a set of static utils used by the TigerZone package
 * 
 */
public class Utils {


	/**
	 * Add a list to a list of lists, but only if it does not already exist in
	 * the list of lists
	 * 
	 * @param lists
	 * @param list
	 */
	public static void addDistinctList(List<List<Tile>> lists, List<Tile> list) {
		
		//System.out.println("addDistinctList: lists size = " + lists.size());
		//System.out.println("addDistinctList: list size = " + list.size());

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