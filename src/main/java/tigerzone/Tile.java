package tigerzone;

import tigerzone.EdgeType;

enum EdgeType {
	GAMETRAIL, JUNGLE, LAKE
}


public class Tile {

	// Each tile has the corrds
	// of where its at on the board
	
	private int[][] arr= new int[3][3]; //constructs the points we need
	
	private int row;
	private int col;
	
	private int type;
	
	private int degrees;
	
	private EdgeType topEdge;
	private EdgeType bottomEdge;
	private EdgeType leftEdge;
	private EdgeType rightEdge;
	
	private int topIndex;
	private int bottomIndex;
	private int leftIndex;
	private int rightIndex;
	
	private boolean roadEnd;
	
	
	public Tile(int type, EdgeType topEdge, EdgeType bottomEdge, EdgeType leftEdge, EdgeType rightEdge)
	{
		this.type = type;
		this.topEdge = topEdge;
		this.bottomEdge = bottomEdge;
		this.leftEdge = leftEdge;
		this.rightEdge = rightEdge;
		this.roadEnd = false;
	}
	
	public Tile(int type, int row, int col, EdgeType topEdge, EdgeType bottomEdge, EdgeType leftEdge, EdgeType rightEdge)
	{
		this.type = type;
		this.row = row;
		this.col = col;
		this.topEdge = topEdge;
		this.bottomEdge = bottomEdge;
		this.leftEdge = leftEdge;
		this.rightEdge = rightEdge;
		this.roadEnd = false;
	}
	
	public Tile(int type, int degrees, int row, int col, EdgeType topEdge, EdgeType bottomEdge, EdgeType leftEdge, EdgeType rightEdge)
	{
		this.type = type;
		this.degrees = degrees;
		this.row = row;
		this.col = col;
		this.topEdge = topEdge;
		this.bottomEdge = bottomEdge;
		this.leftEdge = leftEdge;
		this.rightEdge = rightEdge;
		this.roadEnd = false;
	}
	
	public int getType()
	{
		return type;
	}
	
	public int getDegrees()
	{
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

	public EdgeType getTopEdge() {
		return topEdge;
	}

	public void setTopEdge(EdgeType topEdge) {
		this.topEdge = topEdge;
	}

	public EdgeType getBottomEdge() {
		return bottomEdge;
	}

	public void setBottomEdge(EdgeType bottomEdge) {
		this.bottomEdge = bottomEdge;
	}

	public EdgeType getLeftEdge() {
		return leftEdge;
	}

	public void setLeftEdge(EdgeType leftEdge) {
		this.leftEdge = leftEdge;
	}

	public EdgeType getRightEdge() {
		return rightEdge;
	}

	public void setRightEdge(EdgeType rightEdge) {
		this.rightEdge = rightEdge;
	}
	
	public int getTopIndex()
	{
		return topIndex;
	}
	
	public int getBottomIndex()
	{
		return bottomIndex;
	}
	
	public int getLeftIndex()
	{
		return leftIndex;
	}
	
	public int getRightIndex()
	{
		return rightIndex;
	}
	
	public void setTopIndex(int topIndex)
	{
		this.topIndex = topIndex;
	}
	
	public void setBottomIndex(int bottomIndex)
	{
		this.bottomIndex = bottomIndex;
	}
	
	public void setLeftIndex(int leftIndex)
	{
		this.leftIndex = leftIndex;
	}
	
	public void setRightIndex(int rightIndex)
	{
		this.rightIndex = rightIndex;
	}
	
	public boolean getRoadEnd()
	{
		return roadEnd;
	}
	
	public void setRoadEnd()
	{
		this.roadEnd = true;
	}
}
