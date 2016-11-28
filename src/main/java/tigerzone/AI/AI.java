package tigerzone.AI;
import tigerzone.game.Board;
import tigerzone.game.Deck;
import tigerzone.game.Tile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;
public class AI {
	 
    
	class option{
		private int score; //calculated score
		private int ref;//refers to the tile position in possible moves
		int getRef()
		{
			return ref;
		}
	}
	
	private Board board;
	private Deck deck;
	private Tile tile;

	private ArrayList<option> scores = new ArrayList<option>(); //  
	public AI (){	
		
	}
	public void setUp(Board board, Deck deck, Tile tile)
	{
		this.board = board;
		this.deck = deck;
		this.tile = tile;
		List<Tile> possiblePlaces = board.getPossibleMoves(tile);
	}
	public void runMainAI()
	{
		
	}
	
	/*public void runMoves()
	{
		
		class sortByScore implements Comparator<option> {
			  @Override
			  public int compare(option x, option y) {
			    int startComparison = compare(x.score, y.score);
			    return startComparison;
			  }

			  // I don't know why this isn't in Long...
			  private static int compare(long a, long b) {
			    return a < b ? -1
			         : a > b ? 1
			         : 0;
			  }
			}
		//Collections.sort(scores);
	}*/
	public Tile makeMove()
	{
		return tile;
		//scores.get(0).getRef();//takes best move from the array list, returns the refrence to tile placement from possible Places
	}
	
}

//********PSEUDOCODE FOR AI V 0.0.1 ***********/
//---------INPUT---------//
//possiblePlaces []
	//**given an array of possible places(including rotations)
//clusters[]
	//**give an arrayList of clusters
//tilesLeft
	//**given an array, of remaining tiles 

//----GLOBAL TRACKERS----//
//tigerArray []
	//**stores the best tiger placement in each tile
//scoreQueue
	//**priority queue that is sorted by point value, and a refrence to 
	//**original array tile 
//clustersPoint
	//**keep track of the clusters points
//int tigerCost
	//**arbitrary value to determine if it is worth it to place a tiger

//--------------------------MAIN-------------------------------//
 //if(isOurTurn)
	//run clock
		//if clock triggered
			//pick best in priority queue
			//if tiger should be placed
				//pick this for tiger placement
			//
		//else
			//updateCluster(theirLastMove);	
			//loop through scoreQueue
				//run function from best move(ie cluster(i))
				//update scoreQueue
			//
			//** repeat this for all possibilities.
		//
	//
//else
	//updateCluster(OurlastMove);
//		


////------------------CALCULATE BEST MOVE---------------------//


//---------CLUSTER---------//
//give it points for completing if it can complete a cluster
//subtract points for completing opposing teams cluster

//--------PROXIMITY--------//
//**used to figure out how much space there is for future completion
//Run through each adjacent tile and check for tiles
	//add number 
//

//-----TIGER PLACEMENT-----//
//run through priority queue
	//run through possible tiger placements 
		//calculate point value based on best option
		//if point value gained > opportunuty cost of tiger placement
			//add it to points of queue
			//store position in tigerArray 
			//decrease tigerCost
		//
	//
//

////---------------CALCULATE CLUSTER POINTS------------------//
//**used to calulate the number of points a competed cluster will give

//---------UPDATE----------//
//update all affected cluster values 
	//run function for calculating cluster points (ie lake(i))
	//update this value in the priority queue
//
//repeat above for each calculation.

//----------LAKE-----------//
//check if it is easy to complete 
	//find number of uncompleted sides in chain
//check its size 
//calculate the prey
//calculate nearby farmers
//find if it can be easily finished with the stacks(to be done later)
//--------DEN--------//
//calculate how many are already surrounding it
//see based on remaining tiles how easily it will be completed(to be done later)
//-------GAME TRAIL ------//
//calculate the length  
//calculate the prey


