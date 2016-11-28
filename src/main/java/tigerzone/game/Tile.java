package tigerzone.game;


import tigerzone.game.TerrainType;

enum TerrainType {
	GAMETRAIL, JUNGLE, LAKE, BUFFALO, CROCODILE, DEER, BOAR, DEN, END
}

public class Tile {

	// Each tile has the corrds
	// of where its at on the board

	private int row;
	private int col;

	// string code
	private String terrainTypeString;
	private boolean visited;

	private Board board;

	private int degrees;

	private TerrainType[] tilePortionType = new TerrainType[9];

	private int trailEdgeCount;
	private int lakeEdgeCount;

	private int[] clusterIndices = new int[9];

	public Tile() {

	}
	
	public Tile(String terrainTypeString) {
		//this.type = type; //has no apparent use, will be removed
		this.terrainTypeString = terrainTypeString;
		tilePortionType = returnTileTerrain(terrainTypeString);

		// count number of gametrail edges for this tile
		// every odd element of the tilePortionType array
		// represents the center of an edge
		for (int i = 0; i < tilePortionType.length; i++) {
			if (i % 2 != 0) {
				if (tilePortionType[i] == TerrainType.GAMETRAIL) {
					trailEdgeCount++;
				}
			}
		}

		// count number of lake edges for this tile
		// if there is a lake center, then its all one continuous lake else
		// they're separate lakes represents the center of an edge
		//if (isLakeCenter()) {
		if (false) {
			lakeEdgeCount = 1;
		} else {
			for (int i = 0; i < tilePortionType.length; i++) {
				if (i % 2 != 0) {
					if (tilePortionType[i] == TerrainType.LAKE) {
						lakeEdgeCount++;
					}
				}
			}
		}

	}

	public Tile(String terrainTypeString, int row, int col) {
		this(terrainTypeString);
		this.row = row;
		this.col = col;
	}

	public Tile(String terrainTypeString, int degrees, int row, int col) {
		this(terrainTypeString, row, col);
		this.degrees = degrees;

	}
	//returns an array of its surroundings based on its opcode
	public TerrainType[] returnTileTerrain(String terrainType)
	{
		switch(terrainType)
		{
			case"JJJJ-":	
				TerrainType[] tileA = { TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.JUNGLE };
				return tileA;
			case"JJJJX":	
				TerrainType[] tileB = { TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.DEN,
					TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.JUNGLE }; 
				return tileB;
			case"JJTJX":	
				TerrainType[] tileC = { TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.DEN,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE };
				return tileC;
			case"TTTT-":	
				TerrainType[] tileD = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.END,
						TerrainType.GAMETRAIL, TerrainType.JUNGLE,
						TerrainType.GAMETRAIL, TerrainType.JUNGLE }; 
				return tileD;
			case"TJTJ-":	
				TerrainType[] tileE = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE }; 
				return tileE;
			case"TJJT-":	
				TerrainType[] tileF = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.GAMETRAIL, TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.JUNGLE, TerrainType.JUNGLE }; 
				return tileF;
			case"TJTT-":	
				TerrainType[] tileG = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.END,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE };
				return tileG;
			case"LLLL-":	
				TerrainType[] tileH = { TerrainType.LAKE, TerrainType.LAKE,
						TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE,
						TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE,
						TerrainType.LAKE }; 
				return tileH;	
			case"JLLL-":	
				TerrainType[] tileI = { TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.JUNGLE, TerrainType.LAKE, TerrainType.LAKE,
						TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE,
						TerrainType.LAKE };
				return tileI;	
			case"LLJJ-":	
				TerrainType[] tileJ = { TerrainType.JUNGLE, TerrainType.LAKE,
						TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.LAKE,
						TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.JUNGLE }; 
				return tileJ;	
			case"JLJL-":	
				TerrainType[] tileK = { TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.JUNGLE, TerrainType.LAKE, TerrainType.LAKE,
						TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.JUNGLE };
				return tileK;
			case"LJLJ-":	
				TerrainType[] tileL = { TerrainType.JUNGLE, TerrainType.LAKE,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.LAKE,
						TerrainType.JUNGLE }; 
				return tileL;
			case"LJJJ-":	
				TerrainType[] tileM = { TerrainType.JUNGLE, TerrainType.LAKE,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.JUNGLE }; 
				return tileM;
			case"JLLJ-":	
				TerrainType[] tileN = { TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.LAKE,
						TerrainType.JUNGLE };
				return tileN;
			case"TLJT-":	
				TerrainType[] tileO = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.GAMETRAIL, TerrainType.LAKE, TerrainType.JUNGLE,
						TerrainType.JUNGLE, TerrainType.JUNGLE };
				return tileO;
			case"TLJTP":	
				TerrainType[] tileP = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.GAMETRAIL, TerrainType.LAKE, TerrainType.JUNGLE,
						TerrainType.JUNGLE, TerrainType.JUNGLE };
				return tileP;
			case"JLTT-":	
				TerrainType[] tileQ = { TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.GAMETRAIL, TerrainType.LAKE, TerrainType.JUNGLE,
						TerrainType.GAMETRAIL, TerrainType.JUNGLE };
				return tileQ;
			case"JLTTB":	
				TerrainType[] tileR = { TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.GAMETRAIL, TerrainType.LAKE, TerrainType.JUNGLE,
						TerrainType.GAMETRAIL, TerrainType.JUNGLE }; 
				return tileR;
			case"TLTJ-":	
				TerrainType[] tileS = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE };
				return tileS;
			case"TLTJD":	
				TerrainType[] tileT = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE };
				return tileT;
			case"TLLL-":	
				TerrainType[] tileU = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.LAKE, TerrainType.END,
						TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE,
						TerrainType.LAKE }; 
				return tileU;
			case"TLTT-":	
				TerrainType[] tileV = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.END,
						TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE }; 
				return tileV;
			case"TLTTP":	
				TerrainType[] tileW = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.END,
						TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE }; 
				return tileW;
			case"TLLT-":	
				TerrainType[] tileX = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.LAKE,
						TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.LAKE,
						TerrainType.LAKE }; 
				return tileX;
			case"TLLTB-":	
				TerrainType[] tileY = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.LAKE,
						TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.LAKE,
						TerrainType.LAKE }; 
				return tileY;
			case"LJTJ-":	
				TerrainType[] tileZ = { TerrainType.JUNGLE, TerrainType.LAKE,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE }; 
				return tileZ;
			case"LJTJD":	
				TerrainType[] tileZZ = { TerrainType.JUNGLE, TerrainType.LAKE,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.END,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE };
				return tileZZ;
			case"TLLLC":	
				TerrainType[] tileZZZ = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.LAKE, TerrainType.LAKE,
						TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE,
						TerrainType.LAKE };
				return tileZZZ;
				}
		TerrainType[] empty = {}; 
		return empty;
	}
	
public Tile rotateTile(Tile tile, int degrees) {
		Tile rotateTile = new Tile(tile.getTerrainTypeString(), degrees, tile.getRow(), tile.getCol());

		TerrainType[] rotateArr = new TerrainType[9];
		if (degrees == 90) {
			rotateArr[0] = tile.getTilePortionType()[2];
			rotateArr[1] = tile.getTilePortionType()[5];
			rotateArr[2] = tile.getTilePortionType()[8];
			rotateArr[3] = tile.getTilePortionType()[1];
			rotateArr[5] = tile.getTilePortionType()[7];
			rotateArr[6] = tile.getTilePortionType()[0];
			rotateArr[7] = tile.getTilePortionType()[3];
			rotateArr[8] = tile.getTilePortionType()[6];
		}
		if (degrees == 180) {
			for (int i = 0; i < 9; i++) {
				rotateArr[i] = tile.getTilePortionType()[8 - i];
			}
		}
		if (degrees == 270) {
			rotateArr[0] = tile.getTilePortionType()[6];
			rotateArr[1] = tile.getTilePortionType()[3];
			rotateArr[2] = tile.getTilePortionType()[0];
			rotateArr[3] = tile.getTilePortionType()[7];
			rotateArr[5] = tile.getTilePortionType()[1];
			rotateArr[6] = tile.getTilePortionType()[8];
			rotateArr[7] = tile.getTilePortionType()[5];
			rotateArr[8] = tile.getTilePortionType()[2];
		}
		rotateTile.setTilePortionType(rotateArr);
		return rotateTile;
	}
	// returns true if this Tile includes a trail continuation
	public boolean isContinuation() {
		return trailEdgeCount == 2;
	}

	// returns true if this Tile is a t-junction, 4 way cross road, or one way
	// road end
	public boolean isTrailEnd() {
		return (trailEdgeCount > 2 || trailEdgeCount == 1);
	}


	public TerrainType[] getTilePortionType() {
		return tilePortionType;
	}

	public void setTilePortionType(TerrainType[] tilePortionType) {
		this.tilePortionType = tilePortionType;
	}

	public int[] getClusterIndices() {
		return clusterIndices;
	}

	public void setClusterIndices(int[] clusterIndices) {
		this.clusterIndices = clusterIndices;
	}


	public int getDegrees() {
		return degrees;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public TerrainType getTopEdge() {
		return tilePortionType[1];
	}

	public TerrainType getBottomEdge() {
		return tilePortionType[7];
	}

	public TerrainType getLeftEdge() {
		return tilePortionType[3];
	}

	public TerrainType getRightEdge() {
		return tilePortionType[5];
	}

	public boolean isDen() {
		return tilePortionType[4] == TerrainType.DEN;
	}

	public boolean isLakeCenter() {
		return tilePortionType[4] == TerrainType.LAKE;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getTrailEdgeCount() {
		return trailEdgeCount;
	}

	public void setTrailEdgeCount(int trailEdgeCount) {
		this.trailEdgeCount = trailEdgeCount;
	}
	public String getTerrainTypeString()
	{
		return terrainTypeString;
	}

	/**
	 * @return the lakeEdgeCount
	 */
	public int getLakeEdgeCount() {
		return lakeEdgeCount;
	}
	/**
	 * Returns true if the Tile contains a lake
	 * 
	 * @return
	 */
	public boolean isLake() //move to lakes.java
	{
		if (this.isLakeCenter()) {
			return true;
		}

		if (this.getLeftEdge() == TerrainType.LAKE
				|| this.getRightEdge() == TerrainType.LAKE
				|| this.getTopEdge() == TerrainType.LAKE
				|| this.getBottomEdge() == TerrainType.LAKE) {
			return true;
		}

		return false;
	}
	/**
	 * @param lakeEdgeCount
	 *            the lakeEdgeCount to set
	 */
	public void setLakeEdgeCount(int lakeEdgeCount) {
		this.lakeEdgeCount = lakeEdgeCount;
	}
}