package tigerzone;

enum EdgeType {
	ROAD, FIELD, CITY
}

public class Tile {

	// Each tile has the cords
	// of where its at on the board
	private int row;
	private int col;
	
	private EdgeType topEdge;
	private EdgeType bottomEdge;
	private EdgeType leftEdge;
	private EdgeType rightEdge;

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
