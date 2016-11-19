package tigerzone;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board {

	public static int MAX_ROWS = 153;
	public static int MAX_COLS = 153;

	public static int CENTER_CELL = MAX_ROWS / 2 + 1;
	
	private int topBound;
	private int bottomBound;
	private int leftBound;
	private int rightBound;

	private Tile[][] board = new Tile[MAX_ROWS][MAX_COLS];
	
	private List<List<Tile>> roadList = new ArrayList<List<Tile>>();
	
	
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
		Tile rotateTile = new Tile(tile.getArr(), tile.getType(), degrees, tile.getRow(), tile.getCol(), tile.getTopEdge(), tile.getBottomEdge(), tile.getLeftEdge(), tile.getRightEdge());
		for(int i = 0; i < degrees/90; i++)
		{
			EdgeType temp = rotateTile.getTopEdge();
			rotateTile.setTopEdge(rotateTile.getLeftEdge());
			rotateTile.setLeftEdge(rotateTile.getBottomEdge());
			rotateTile.setBottomEdge(rotateTile.getRightEdge());
			rotateTile.setRightEdge(temp);
		}
		
		int[][] rotateArr;
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
			
			if(addTile.getRoadEnd())
			{
				if(addTile.getTopEdge() == EdgeType.GAMETRAIL)
				{
					
				}
			}
			
			if(x == getTopBound() && x > 0)
			{
				setTopBound(x - 1);
			}
			if(x == getBottomBound() && x < MAX_ROWS)
			{
				setBottomBound(x + 1);
			}
			if(y == getLeftBound() && y > 0)
			{
				setLeftBound(y - 1);
			}
			if(y == getRightBound() && y < MAX_COLS)
			{
				setRightBound(y + 1);
			}
		}
	}
	
	public void initTiles(List<Tile> tiles)
	{
		
		int[][] tileA= {{-1,9,-1},{9,-1,9},{-1,9,-1}};// 1:no:empty field
		int[][] tileB= {{-1,9,-1},{9,3,9},{-1,9,-1}}; // 4: no: monistary
		int[][] tileC= {{-1,9,-1},{9,3,9},{-1,5,-1}}; // 2: no : monistary with 1 road leaving.
		int[][] tileD= {{9,5,10},{6,4,7},{11,8,12}}; // 4: no: crossrouads 4 roads 4 fields.
		int[][] tileE= {{9,5,10},{9,-1,10},{9,5,10}}; //8: no: a straight road with 2 fields on the side  19
		int[][] tileF= {{9,5,10},{5,-1,10},{10,10,-1}}; // 9:no: a curved road has 2 total fields
		int[][] tileG= {{9,5,10},{6,4,10},{11,7,10}}; // 4: no: three way crossroads 3 total fields
		int[][] tileH= {{-1,1,-1},{1,-1,1},{-1,1,-1}}; //1:no: giant city tile on each side
		int[][] tileI= {{-1,9,-1},{1,-1,1},{-1,1,-1}}; //4: no: city on 3 sides field on top side
		int[][] tileJ= {{-1,1,-1},{9,-1,1},{-1,9,-1}}; //5:no: diagonal tile bottom left field top right city 23
		int[][] tileK= {{-1,9,-1},{1,-1,1},{-1,10,-1}}; //3:no" one city from left to right going thru center with 2 fields
		int[][] tileL= {{-1,1,-1},{9,-1,9},{-1,2,-1}}; // 3: no: inverse of above 2 citys 1 field
		int[][] tileM= {{-1,1,-1},{9,-1,9},{-1,9,-1}}; // 5:no: city on top one field
		int[][] tileN= {{-1,9,-1},{9,-1,1},{-1,2,-1}}; // 2:no: two cities adjacent 
		int[][] tileO= {{9,5,10},{5,-1,1},{10,10,-1}}; //1:no: road in top left city on right
		int[][] tileP= {{9,5,10},{5,-1,1},{10,10,-1}}; //2:yes:same as above but w boar
		int[][] tileQ= {{9,9,-1},{5,-1,1},{10,5,9}}; // 1: no: same as o but roads go from left to down
		int[][] tileR= {{9,9,-1},{5,-1,1},{10,5,9}}; // 2:yes: same as above but has animal 19
		int[][] tileS= {{9,5,10},{9,-1,1},{9,5,10}}; // no idea:no feild L road M city Rightside
		int[][] tileT= {{9,5,10},{9,-1,1},{9,5,10}}; // 2: Yes: same as above w deer
		int[][] tileU= {{9,5,10},{1,4,1},{-1,1,-1}}; // 3:no:road Top city L R B
		int[][] tileV= {{9,5,10},{6,4,1},{11,7,10}}; // 1:no:3 progned road facing left city on right
		int[][] tileW= {{9,5,10},{6,4,1},{11,7,10}}; //2:yes: same as above but with boar
		int[][] tileX= {{9,5,10},{5,-1,1},{10,1,-1}}; //3:no: road goues left to up city R & Botom
		int[][] tileY= {{9,5,10},{5,-1,1},{10,1,-1}}; //2:yes: same as above but has goat
		int[][] tileZ= {{-1,1,-1},{9,-1,10},{9,5,10}}; //1:no city Top road B field L field R
		int[][] tileZZ= {{-1,1,-1},{9,-1,10},{9,5,10}};//2:yes:see above
		
		
		Tile startTile = new Tile(tileS, 19, 270, CENTER_CELL, CENTER_CELL, EdgeType.LAKE, EdgeType.JUNGLE, EdgeType.GAMETRAIL, EdgeType.GAMETRAIL);
		board[CENTER_CELL][CENTER_CELL] = startTile;
		
		
		Tile tile = new Tile(tileA, 1, EdgeType.JUNGLE, EdgeType.JUNGLE, EdgeType.JUNGLE, EdgeType.JUNGLE);
		tiles.add(tile);
		
		tile = new Tile(tileB, 2, EdgeType.JUNGLE, EdgeType.JUNGLE, EdgeType.JUNGLE, EdgeType.JUNGLE);
		for(int i = 0; i < 4; i++)
		{
			tiles.add(tile);
		}
		
		tile = new Tile(tileC, 3, EdgeType.JUNGLE, EdgeType.GAMETRAIL, EdgeType.JUNGLE, EdgeType.JUNGLE);
		tile.setRoadEnd();
		tiles.add(tile);
		tiles.add(tile);
		
		tile = new Tile(tileD, 4, EdgeType.GAMETRAIL, EdgeType.GAMETRAIL, EdgeType.GAMETRAIL, EdgeType.GAMETRAIL);
		tile.setRoadEnd();
		tiles.add(tile);
		
		tile = new Tile(tileE, 5, EdgeType.GAMETRAIL, EdgeType.GAMETRAIL, EdgeType.JUNGLE, EdgeType.JUNGLE);
		for(int i = 0; i < 8; i++)
		{
			tiles.add(tile);
		}
		
		tile = new Tile(tileF, 6, EdgeType.GAMETRAIL, EdgeType.JUNGLE, EdgeType.GAMETRAIL, EdgeType.JUNGLE);
		for(int i = 0; i < 9; i++)
		{
			tiles.add(tile);
		}
		
		tile = new Tile(tileG, 7, EdgeType.GAMETRAIL, EdgeType.GAMETRAIL, EdgeType.GAMETRAIL, EdgeType.JUNGLE);
		tile.setRoadEnd();
		for(int i = 0; i < 4; i++)
		{
			tiles.add(tile);
		}
		
		tile = new Tile(tileH, 8, EdgeType.LAKE, EdgeType.LAKE, EdgeType.LAKE, EdgeType.LAKE);
		tiles.add(tile);
		
		tile = new Tile(tileI, 9, EdgeType.JUNGLE, EdgeType.LAKE, EdgeType.LAKE, EdgeType.LAKE);
		for(int i = 0; i < 4; i++)
		{
			tiles.add(tile);
		}
		
		tile = new Tile(tileJ, 10, EdgeType.LAKE, EdgeType.JUNGLE, EdgeType.JUNGLE, EdgeType.LAKE);
		for(int i = 0; i < 5; i++)
		{
			tiles.add(tile);
		}
		
		tile = new Tile(tileK, 11, EdgeType.JUNGLE, EdgeType.JUNGLE, EdgeType.LAKE, EdgeType.LAKE);
		for(int i = 0; i < 3; i++)
		{
			tiles.add(tile);
		}
		
		tile = new Tile(tileL, 12, EdgeType.LAKE, EdgeType.LAKE, EdgeType.JUNGLE, EdgeType.JUNGLE);
		for(int i = 0; i < 3; i++)
		{
			tiles.add(tile);
		}
		
		tile = new Tile(tileM, 13, EdgeType.LAKE, EdgeType.JUNGLE, EdgeType.JUNGLE, EdgeType.JUNGLE);
		for(int i = 0; i < 5; i++)
		{
			tiles.add(tile);
		}
		
		tile = new Tile(tileN, 14, EdgeType.JUNGLE, EdgeType.LAKE, EdgeType.JUNGLE, EdgeType.LAKE);
		tiles.add(tile);
		tiles.add(tile);
		
		tile = new Tile(tileO, 15, EdgeType.GAMETRAIL, EdgeType.JUNGLE, EdgeType.GAMETRAIL, EdgeType.LAKE);
		tiles.add(tile);
		
		tile = new Tile(tileP, 16, EdgeType.GAMETRAIL, EdgeType.JUNGLE, EdgeType.GAMETRAIL, EdgeType.LAKE);
		tiles.add(tile);
		tiles.add(tile);
		
		tile = new Tile(tileQ, 17, EdgeType.JUNGLE, EdgeType.GAMETRAIL, EdgeType.GAMETRAIL, EdgeType.LAKE);
		tiles.add(tile);
		
		tile = new Tile(tileR, 18, EdgeType.JUNGLE, EdgeType.GAMETRAIL, EdgeType.GAMETRAIL, EdgeType.LAKE);
		tiles.add(tile);
		tiles.add(tile);
		
		tile = new Tile(tileS, 19, EdgeType.GAMETRAIL, EdgeType.GAMETRAIL, EdgeType.JUNGLE, EdgeType.LAKE);
		tiles.add(tile);
		tiles.add(tile);
		
		tile = new Tile(tileT, 20, EdgeType.GAMETRAIL, EdgeType.GAMETRAIL, EdgeType.JUNGLE, EdgeType.LAKE);
		tiles.add(tile);
		tiles.add(tile);
		
		tile = new Tile(tileU, 21, EdgeType.GAMETRAIL, EdgeType.LAKE, EdgeType.LAKE, EdgeType.LAKE);
		tile.setRoadEnd();
		for(int i = 0; i < 3; i++)
		{
			tiles.add(tile);
		}
		
		tile = new Tile(tileV, 22, EdgeType.GAMETRAIL, EdgeType.GAMETRAIL, EdgeType.GAMETRAIL, EdgeType.LAKE);
		tile.setRoadEnd();
		tiles.add(tile);
		
		tile = new Tile(tileW, 23, EdgeType.GAMETRAIL, EdgeType.GAMETRAIL, EdgeType.GAMETRAIL, EdgeType.LAKE);
		tile.setRoadEnd();
		tiles.add(tile);
		tiles.add(tile);
		
		tile = new Tile(tileX, 24, EdgeType.GAMETRAIL, EdgeType.LAKE, EdgeType.GAMETRAIL, EdgeType.LAKE);
		for(int i = 0; i < 3; i++)
		{
			tiles.add(tile);
		}
		
		tile = new Tile(tileY, 25, EdgeType.GAMETRAIL, EdgeType.LAKE, EdgeType.GAMETRAIL, EdgeType.LAKE);
		tiles.add(tile);
		tiles.add(tile);
		
		tile = new Tile(tileZ, 26, EdgeType.LAKE, EdgeType.GAMETRAIL, EdgeType.JUNGLE, EdgeType.JUNGLE);
		tile.setRoadEnd();
		tiles.add(tile);
		
		tile = new Tile(tileZZ, 27, EdgeType.LAKE, EdgeType.GAMETRAIL, EdgeType.JUNGLE, EdgeType.JUNGLE);
		tile.setRoadEnd();
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
		frame.setPreferredSize(new Dimension(850,1100));
		
		JPanel jp = new JPanel(new GridLayout(gameBoard.getBottomBound() - gameBoard.getTopBound()+1, gameBoard.getRightBound() - gameBoard.getLeftBound()+1, 0, 0));
		
		for(int i = gameBoard.getTopBound(); i <= gameBoard.getBottomBound(); i++)
		{
			for(int j = gameBoard.getLeftBound(); j <= gameBoard.getRightBound(); j++)
			{
				if(gameBoard.board[i][j] == null)
				{
					JLabel j28 = new JLabel();
					j28.setIcon(new ImageIcon("C:/Users/Skyler/Pictures/Screenshots/CarcassonneTiles/Tile28.png"));
					jp.add(j28);
				}
				else
				{
					JLabel j1 = new JLabel();
					ImageIcon II = new ImageIcon("C:/Users/Skyler/Pictures/Screenshots/CarcassonneTiles/Tile"+Integer.toString(gameBoard.board[i][j].getType()) + "." + Integer.toString(gameBoard.board[i][j].getDegrees()) + ".png");
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
