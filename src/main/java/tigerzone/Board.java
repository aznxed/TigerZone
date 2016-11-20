package tigerzone;

import java.util.ArrayList;
import java.util.List;

public class Board {

	//Changes
	public static int TILE_NUM = 77;
	public static int MAX_ROWS = 143;
	public static int MAX_COLS = 143;

	public static int CENTER_CELL = MAX_ROWS / 2 + 1;

	private Tile[][] board = new Tile[MAX_ROWS][MAX_COLS];

	public boolean isValid(int x, int y, Tile tile) {
		if (getCenterTile() == null) {
			board[CENTER_CELL][CENTER_CELL] = tile;
			return true;
		} 
		
		//Tile already placed there
		if(board[x][y] != null){
			return false;
		}
		
		List<Tile> nbors = getNeighbors(x, y);
		
		if(nbors.isEmpty()){
			return false;
		}
		
		for(int i = 0; i < nbors.size(); i++){
			Tile nTile = nbors.get(i);
			
			//Check if its in same row
			if(nTile.getRow() == x){
				if(nTile.getCol() > y){
					//This is right neighbor
					continue;
				}
				else{
					//This is left neighbor
					continue; 
				}
			}
			
			if(nTile.getCol() == y){
				if(nTile.getRow() > x){
					//This is bottom neighbor
					continue;
				}
				else{
					//This is top neighbor
					continue; 
				}
			}
			
			
			
			
		}
		
		
		
		return true;
	}

	public Tile getCenterTile() {
		return board[CENTER_CELL][CENTER_CELL];
	}

	public void clearBoard() {

		for (int x = 0; x < MAX_ROWS; x++) {
			for (int y = 0; y < MAX_COLS; y++) {
				board[x][y] = null;
			}
		}

	}
	
	public List<Tile> getNeighbors(int x, int y){
		List<Tile> n = new ArrayList<Tile>();
		
		if (y > 0) {
			if (board[x][y - 1] != null) {
				n.add(board[x][y-1]);
			}
		}

		if (y < MAX_COLS) {
			if (board[x][y + 1] != null) {
				n.add(board[x][y + 1]);
			}
		}

		if (x > 0) {
			if (board[x - 1][y] != null) {
				n.add(board[x - 1][y]);
			}
		}

		if (x < MAX_ROWS) {
			if (board[x + 1][y] != null) {
				n.add(board[x + 1][y]);
			}
		}
		
		
		return n;
	}
	
	public boolean addTile(int x, int y, Tile tile){
		if(isValid(x, y, tile)){
			//add tile to board
			//give tile coords
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println(MAX_ROWS / 2 + 1);
		
		Tile tile1 = new Tile();
		Tile tile2 = new Tile();
		
		tile1.setTopEdge(EdgeType.FIELD);
		tile2.setTopEdge(EdgeType.FIELD);
		
		System.out.println(tile1.getTopEdge() == tile2.getTopEdge());
		
	}

}
