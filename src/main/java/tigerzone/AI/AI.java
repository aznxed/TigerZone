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
		public void setRef(int temp)
		{
			ref = temp;
		}
		public void setScore(int temp)
		{
			score = temp;
		}
	}
	private Board board;
	private Deck deck;
	private Tile tile;
	private List <Tile> possiblePlaces;
	private ArrayList<option> scores = new ArrayList<option>(); //keeps an array list of scores
	boolean[] done = new boolean[2];//array of false
	
	
	public AI (){	
		
	}
	public void setUp(Board board, Deck deck, Tile tile)
	{
		this.board = board;
		this.deck = deck;
		this.tile = tile;
		this.possiblePlaces = board.getPossibleMoves(tile);
	}
	
	public void runMainAI()
	{
		if(!done[0])
		{
			possiblePlaces();//instantiates new possible places
			done[0] = true;
		}
		if(!done[1])
		{
			
			//run function to get score and add to score queue
			sortList(scores);
			done[1] = true;
			
		}
	}
	public Tile makeMove()
	{
		return possiblePlaces.get(scores.get(0).getRef());//takes best move from the array list, returns the refrence to tile placement from possible Places
	}
	
	public void possiblePlaces()
	{
		for(int i =0; i < possiblePlaces.size(); i++)
		{
			option temp = new option();
			temp.setRef(i);
			temp.setScore(0);
			scores.add(temp);
		}
	}
	
	public void sortList(List <option> list) 
	//sorts list based on option score
	{
		Collections.sort(list, new Comparator<option>() {
		      @Override
		      public int compare(final option object1, final option object2)
		      {
		          return object1.score < object2.score ? -1
					         : object1.score > object2.score ? 1
					         : 0;
		      }
		  });
	}
	
	
}




