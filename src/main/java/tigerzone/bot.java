package tigerzone;



public class bot {
	
	//Initialize the deck of tiles
	public static void initDeck(){
		return;
	}
	//Add a tile to the Deck
	public static void addDeck(String tile){
		System.out.println("Added " + tile + " to deck");
		return;
	}
	//Initialize the board
	public static void initBoards(){
		return;
	}
	//Place first tile
	public static void firstTile(String tile, int x, int y, int rot){
		return;
	}
	//Play a tile
	public static move makeMove(String game, int time, String tile){
		//TODO: Return something that is not a string
		//Return something that includes int xPos, int yPos, int rot, String meep, int meepPos
		move moveMade = new move(999, 999, 360, "", -1);
		return moveMade;
	}
	//Place a tile
	public static void placeTile(String game, String tile, int x, int y, int rot, String meep, int meepPos){
		return;
	}
	//Extra Processing Time
	public static void botProcess(int time){
		return;
	}
	
}