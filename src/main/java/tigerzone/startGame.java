package tigerzone;


public class startGame {
	public String playerName = "Tigger";
	public String serverPassword = "PersiaRocks!";
	public int number1 = 1;
	
	public static void main(String[] args) {
		
		//Used for how many boards and AI that need to be made. 
		int gameNum = 2;
		
		//Initialize deck
		Tile tile1 = new Tile();
		Tile tile2 = new Tile();
		
		tile1.setTopEdge(EdgeType.FIELD);
		tile2.setTopEdge(EdgeType.FIELD);
		
		System.out.println(tile1.getTopEdge() == tile2.getTopEdge());
		
		//Initialize boards
		Board board1 = new Board();
		Board board2 = new Board();
		
		System.out.println(Board.CENTER_TILE);
		
		//Initialize Sockets
		tcpClient tcpClient = new tcpClient();
		
	}
}