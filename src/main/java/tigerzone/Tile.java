package tigerzone;

import tigerzone.EdgeType;

enum EdgeType {
	ROAD, FIELD, CITY
}


public class Tile {

	// Each tile has the corrds
	// of where its at on the board
	private int row;
	private int col;
	
	private int type;
	
	private int degrees;
	
	private EdgeType topEdge;
	private EdgeType bottomEdge;
	private EdgeType leftEdge;
	private EdgeType rightEdge;
	
	
	public Tile(int type, EdgeType topEdge, EdgeType bottomEdge, EdgeType leftEdge, EdgeType rightEdge)
	{
		this.type = type;
		this.topEdge = topEdge;
		this.bottomEdge = bottomEdge;
		this.leftEdge = leftEdge;
		this.rightEdge = rightEdge;
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
}
