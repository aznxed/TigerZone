package tigerzone;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board {

	public static int MAX_ROWS = 155;
	public static int MAX_COLS = 155;

	public static int CENTER_CELL = MAX_ROWS / 2 + 1;
	
	private int topBound;
	private int bottomBound;
	private int leftBound;
	private int rightBound;

	private Tile[][] board = new Tile[MAX_ROWS][MAX_COLS];
	
	public int getTopBound()
	{
		return this.topBound;
	}
	public int getBottomBound()
	{
		return this.bottomBound;
	}
	public int getLeftBound()
	{
		return this.leftBound;
	}
	public int getRightBound()
	{
		return this.rightBound;
	}
	
	public void setTopBound(int upperBound)
	{
		this.topBound = upperBound;
	}
	public void setBottomBound(int lowerBound)
	{
		this.bottomBound = lowerBound;
	}
	public void setLeftBound(int leftBound)
	{
		this.leftBound = leftBound;
	}
	public void setRightBound(int rightBound)
	{
		this.rightBound = rightBound;
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
	
	public boolean isValid(int x, int y, Tile tile)
	{
		//Space already has tile
		if(board[x][y] != null) {
			return false;
		}
		
		List<Tile> nbors = getNeighbors(x, y);
		
		//No neighbors
		if(nbors.isEmpty()){
			return false;
		}
		
		return true;
	}

	public List<Integer> getValidOrients(int x, int y, Tile tile) {
		List<Integer> validOrients = new ArrayList<Integer>();
		
		List<Tile> nbors = getNeighbors(x, y);
		
		/*for(int i = 0; i < nbors.size(); i++){
			System.out.println(nbors.get(i).getRow()+ " " + nbors.get(i).getCol());
		}
		System.out.println(x+ " " + y);*/
		
		//Add possible orientation to list
		validOrients.add(0);
		validOrients.add(90);
		validOrients.add(180);
		validOrients.add(270);
		
		//For each neighboring tile, check if sides match for each orientation 
		//If not, remove from validOrients
		for(int i = 0; i < nbors.size(); i++){
			Tile nTile = nbors.get(i);
			
			if(validOrients.isEmpty())
			{
				break;
			}
			//Check if its in same row
			if(nTile.getRow() == x){
				if(nTile.getCol() > y){
					//This is right neighbor
					if(nTile.getLeftEdge() != tile.getRightEdge())
					{
						if(validOrients.contains(0))
						{
							validOrients.remove(Integer.valueOf(0));
						}
					}
					if(nTile.getLeftEdge() != tile.getTopEdge())
					{
						if(validOrients.contains(90))
						{
							validOrients.remove(Integer.valueOf(90));
						}
					}
					if(nTile.getLeftEdge() != tile.getLeftEdge())
					{
						if(validOrients.contains(180))
						{
							validOrients.remove(Integer.valueOf(180));
						}
					}
					if(nTile.getLeftEdge() != tile.getBottomEdge())
					{
						if(validOrients.contains(270))
						{
							validOrients.remove(Integer.valueOf(270));
						}
					}
				}
				else{
					//This is left neighbor
					if(nTile.getRightEdge() != tile.getLeftEdge())
					{
						if(validOrients.contains(0))
						{
							validOrients.remove(Integer.valueOf(0));
						}
					}
					if(nTile.getRightEdge() != tile.getBottomEdge())
					{
						if(validOrients.contains(90))
						{
							validOrients.remove(Integer.valueOf(90));
						}
					}
					if(nTile.getRightEdge() != tile.getRightEdge())
					{
						if(validOrients.contains(180))
						{
							validOrients.remove(Integer.valueOf(180));
						}
					}
					if(nTile.getRightEdge() != tile.getTopEdge())
					{
						if(validOrients.contains(270))
						{
							validOrients.remove(Integer.valueOf(270));
						}
					}
				}
			}
			
			if(nTile.getCol() == y){
				if(nTile.getRow() > x){
					//This is bottom neighbor
					if(nTile.getTopEdge() != tile.getBottomEdge())
					{
						if(validOrients.contains(0))
						{
							validOrients.remove(Integer.valueOf(0));
						}
					}
					if(nTile.getTopEdge() != tile.getRightEdge())
					{
						if(validOrients.contains(90))
						{
							validOrients.remove(Integer.valueOf(90));
						}
					}
					if(nTile.getTopEdge() != tile.getTopEdge())
					{
						if(validOrients.contains(180))
						{
							validOrients.remove(Integer.valueOf(180));
						}
					}
					if(nTile.getTopEdge() != tile.getLeftEdge())
					{
						if(validOrients.contains(270))
						{
							validOrients.remove(Integer.valueOf(270));
						}
					}
				}
				else{
					//This is top neighbor
					if(nTile.getBottomEdge() != tile.getTopEdge())
					{
						if(validOrients.contains(0))
						{
							validOrients.remove(Integer.valueOf(0));
						}
					}
					if(nTile.getBottomEdge() != tile.getLeftEdge())
					{
						if(validOrients.contains(90))
						{
							validOrients.remove(Integer.valueOf(90));
						}
					}
					if(nTile.getBottomEdge() != tile.getBottomEdge())
					{
						if(validOrients.contains(180))
						{
							validOrients.remove(Integer.valueOf(180));
						}
					}
					if(nTile.getBottomEdge() != tile.getRightEdge())
					{
						if(validOrients.contains(270))
						{
							validOrients.remove(Integer.valueOf(270));
						}
					}
				}
			}
		}
		
		return validOrients;
	}
	
	public Tile rotateTile(Tile tile, int degrees)
	{
		Tile rotateTile = new Tile(tile.getType(), degrees, tile.getRow(), tile.getCol(), tile.getTopEdge(), tile.getBottomEdge(), tile.getLeftEdge(), tile.getRightEdge());
		for(int i = 0; i < degrees/90; i++)
		{
			EdgeType temp = rotateTile.getTopEdge();
			rotateTile.setTopEdge(rotateTile.getLeftEdge());
			rotateTile.setLeftEdge(rotateTile.getBottomEdge());
			rotateTile.setBottomEdge(rotateTile.getRightEdge());
			rotateTile.setRightEdge(temp);
		}
		return rotateTile;
	}
	
	
	public List<Tile> getPossibleMoves(Tile tile)
	{
		List<Tile> possibleMoves = new ArrayList<Tile>();
		for(int i = getTopBound(); i <= getBottomBound(); i++)
		{
			for(int j = getLeftBound(); j <= getRightBound(); j++)
			{
				if(isValid(i, j, tile))
				{
					tile.setRow(i);
					tile.setCol(j);
					List<Integer> validOrients = getValidOrients(i, j, tile);
					for(int k = 0; k < validOrients.size(); k++)
					{
						possibleMoves.add(rotateTile(tile, validOrients.get(k)));
						
					}
					//System.out.println(i + " " + j+" ");
				}
			}
		}
	
		return possibleMoves;
	}
	
	public void addTile(Tile tile){
		//For now just take the first possible tile
		if(!(getPossibleMoves(tile).isEmpty()))
		{
			Random rand = new Random();
			Tile addTile = getPossibleMoves(tile).get(rand.nextInt(getPossibleMoves(tile).size()));
			int x = addTile.getRow();
			int y = addTile.getCol();
			board[x][y] = addTile;
			//System.out.println(x+" "+y);
			if(x == getTopBound() && x > 0)
			{
				setTopBound(x - 1);
				//System.out.println("T" + getTopBound());
			}
			if(x == getBottomBound() && x < MAX_ROWS)
			{
				setBottomBound(x + 1);
				//System.out.println("B" + getBottomBound());
			}
			if(y == getLeftBound() && y > 0)
			{
				setLeftBound(y - 1);
				//System.out.println("L" + getLeftBound());
			}
			if(y == getRightBound() && y < MAX_COLS)
			{
				setRightBound(y + 1);
				//System.out.println("R" + getRightBound());
			}
		}
	}
	
	public void initTiles(List<Tile> tiles)
	{
		Tile startTile = new Tile(19, 270, CENTER_CELL, CENTER_CELL, EdgeType.CITY, EdgeType.FIELD, EdgeType.ROAD, EdgeType.ROAD);
		board[CENTER_CELL][CENTER_CELL] = startTile;
		
		Tile tile = new Tile(1, EdgeType.FIELD, EdgeType.FIELD, EdgeType.FIELD, EdgeType.FIELD);
		tiles.add(tile);
		
		tile = new Tile(2, EdgeType.FIELD, EdgeType.FIELD, EdgeType.FIELD, EdgeType.FIELD);
		for(int i = 0; i < 4; i++)
		{
			tiles.add(tile);
		}
		
		tile = new Tile(3, EdgeType.FIELD, EdgeType.ROAD, EdgeType.FIELD, EdgeType.FIELD);
		tiles.add(tile);
		tiles.add(tile);
		
		tile = new Tile(4, EdgeType.ROAD, EdgeType.ROAD, EdgeType.ROAD, EdgeType.ROAD);
		tiles.add(tile);
		
		tile = new Tile(5, EdgeType.ROAD, EdgeType.ROAD, EdgeType.FIELD, EdgeType.FIELD);
		for(int i = 0; i < 8; i++)
		{
			tiles.add(tile);
		}
		
		tile = new Tile(6, EdgeType.ROAD, EdgeType.FIELD, EdgeType.ROAD, EdgeType.FIELD);
		for(int i = 0; i < 9; i++)
		{
			tiles.add(tile);
		}
		
		tile = new Tile(7, EdgeType.ROAD, EdgeType.ROAD, EdgeType.ROAD, EdgeType.FIELD);
		for(int i = 0; i < 4; i++)
		{
			tiles.add(tile);
		}
		
		tile = new Tile(8, EdgeType.CITY, EdgeType.CITY, EdgeType.CITY, EdgeType.CITY);
		tiles.add(tile);
		
		tile = new Tile(9, EdgeType.FIELD, EdgeType.CITY, EdgeType.CITY, EdgeType.CITY);
		for(int i = 0; i < 4; i++)
		{
			tiles.add(tile);
		}
		
		tile = new Tile(10, EdgeType.CITY, EdgeType.FIELD, EdgeType.FIELD, EdgeType.CITY);
		for(int i = 0; i < 5; i++)
		{
			tiles.add(tile);
		}
		
		tile = new Tile(11, EdgeType.FIELD, EdgeType.FIELD, EdgeType.CITY, EdgeType.CITY);
		for(int i = 0; i < 3; i++)
		{
			tiles.add(tile);
		}
		
		tile = new Tile(12, EdgeType.CITY, EdgeType.CITY, EdgeType.FIELD, EdgeType.FIELD);
		for(int i = 0; i < 3; i++)
		{
			tiles.add(tile);
		}
		
		tile = new Tile(13, EdgeType.CITY, EdgeType.FIELD, EdgeType.FIELD, EdgeType.FIELD);
		for(int i = 0; i < 5; i++)
		{
			tiles.add(tile);
		}
		
		tile = new Tile(14, EdgeType.FIELD, EdgeType.CITY, EdgeType.FIELD, EdgeType.CITY);
		tiles.add(tile);
		tiles.add(tile);
		
		tile = new Tile(15, EdgeType.ROAD, EdgeType.FIELD, EdgeType.ROAD, EdgeType.CITY);
		tiles.add(tile);
		
		tile = new Tile(16, EdgeType.ROAD, EdgeType.FIELD, EdgeType.ROAD, EdgeType.CITY);
		tiles.add(tile);
		tiles.add(tile);
		
		tile = new Tile(17, EdgeType.FIELD, EdgeType.ROAD, EdgeType.ROAD, EdgeType.CITY);
		tiles.add(tile);
		
		tile = new Tile(18, EdgeType.FIELD, EdgeType.ROAD, EdgeType.ROAD, EdgeType.CITY);
		tiles.add(tile);
		
		tile = new Tile(19, EdgeType.ROAD, EdgeType.ROAD, EdgeType.FIELD, EdgeType.CITY);
		tiles.add(tile);
		tiles.add(tile);
		
		tile = new Tile(20, EdgeType.ROAD, EdgeType.ROAD, EdgeType.FIELD, EdgeType.CITY);
		tiles.add(tile);
		tiles.add(tile);
		
		tile = new Tile(21, EdgeType.ROAD, EdgeType.CITY, EdgeType.CITY, EdgeType.CITY);
		for(int i = 0; i < 3; i++)
		{
			tiles.add(tile);
		}
		
		tile = new Tile(22, EdgeType.ROAD, EdgeType.ROAD, EdgeType.ROAD, EdgeType.CITY);
		tiles.add(tile);
		
		tile = new Tile(23, EdgeType.ROAD, EdgeType.ROAD, EdgeType.ROAD, EdgeType.CITY);
		tiles.add(tile);
		tiles.add(tile);
		
		tile = new Tile(24, EdgeType.ROAD, EdgeType.CITY, EdgeType.ROAD, EdgeType.CITY);
		for(int i = 0; i < 3; i++)
		{
			tiles.add(tile);
		}
		
		tile = new Tile(25, EdgeType.ROAD, EdgeType.CITY, EdgeType.ROAD, EdgeType.CITY);
		tiles.add(tile);
		tiles.add(tile);
		
		tile = new Tile(26, EdgeType.CITY, EdgeType.ROAD, EdgeType.FIELD, EdgeType.FIELD);
		tiles.add(tile);
		
		tile = new Tile(26, EdgeType.CITY, EdgeType.ROAD, EdgeType.FIELD, EdgeType.FIELD);
		tiles.add(tile);
		tiles.add(tile);
		
		Collections.shuffle(tiles);
	}

	public static void main(String[] args) {
		Board gameBoard = new Board();
		List<Tile> tiles = new ArrayList<Tile>();
		
		gameBoard.setTopBound(CENTER_CELL - 1);
		gameBoard.setBottomBound(CENTER_CELL + 1);
		gameBoard.setLeftBound(CENTER_CELL - 1);
		gameBoard.setRightBound(CENTER_CELL + 1);
		
		
		gameBoard.initTiles(tiles);
		
		//Tile tile = new Tile(22, EdgeType.ROAD, EdgeType.ROAD, EdgeType.ROAD, EdgeType.CITY);
		//gameBoard.addTile(tile);
		
		for(int i = 0; i < tiles.size(); i++)
		{
			gameBoard.addTile(tiles.get(i));
		}
		
		for(int i = gameBoard.getTopBound(); i <= gameBoard.getBottomBound(); i++)
		{
			for(int j = gameBoard.getLeftBound(); j <= gameBoard.getRightBound(); j++)
			{
				if(gameBoard.board[i][j] == null)
				{
					System.out.print("0 ");
				}
				else
				{
					System.out.print(gameBoard.board[i][j].getType() + " ");
				}
			}
			System.out.print("\n");
		}
		
		for(int i = gameBoard.getTopBound(); i <= gameBoard.getBottomBound(); i++)
		{
			for(int j = gameBoard.getLeftBound(); j <= gameBoard.getRightBound(); j++)
			{
				if(gameBoard.board[i][j] == null)
				{
					System.out.print("1 ");
				}
				else
				{
					System.out.print(gameBoard.board[i][j].getDegrees() + " ");
				}
			}
			System.out.print("\n");
		}
		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(825,1000));
		
		JPanel jp = new JPanel(new GridLayout(gameBoard.getBottomBound() - gameBoard.getTopBound()+1, gameBoard.getRightBound() - gameBoard.getLeftBound()+1));
		
		for(int i = gameBoard.getTopBound(); i <= gameBoard.getBottomBound(); i++)
		{
			for(int j = gameBoard.getLeftBound(); j <= gameBoard.getRightBound(); j++)
			{
				if(gameBoard.board[i][j] == null)
				{
					JLabel j28 = new JLabel();
					j28.setIcon(new ImageIcon("C:/Users/Skyler/Pictures/Screenshots/Tile28.png"));
					jp.add(j28);
				}
				else
				{
					JLabel j1 = new JLabel();
					ImageIcon II = new ImageIcon("C:/Users/Skyler/Pictures/Screenshots/Tile"+Integer.toString(gameBoard.board[i][j].getType()) + "." + Integer.toString(gameBoard.board[i][j].getDegrees()) + ".png");
					Image image = II.getImage(); // transform it 
					Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
					II = new ImageIcon(newimg);
					j1.setIcon(II);
					jp.add(j1);
				}
			}
		}
		
		frame.add(jp);
		frame.pack();
		frame.setVisible(true);
	}

}
