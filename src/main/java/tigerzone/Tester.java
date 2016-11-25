package tigerzone;

public class Tester {

	static TerrainType[] tileTL = { TerrainType.JUNGLE, TerrainType.JUNGLE,	   TerrainType.JUNGLE, 
		                            TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.GAMETRAIL, 
		                            TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE };

	static TerrainType[] tileTR = { TerrainType.JUNGLE, TerrainType.JUNGLE,
			TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.GAMETRAIL,
			TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
			TerrainType.JUNGLE };

	static TerrainType[] tileBL = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
			TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
			TerrainType.GAMETRAIL, TerrainType.JUNGLE, TerrainType.JUNGLE,
			TerrainType.JUNGLE };

	static TerrainType[] tileBR = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
			TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.GAMETRAIL,
			TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
			TerrainType.JUNGLE };
	
	static TerrainType[] tileMRTEE = { TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE, 
		                               TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.GAMETRAIL, 
		                               TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE };
	
	static TerrainType[] tileBMRTEE = { TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE, 
        TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE, 
        TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE };
	
	static TerrainType[] tileMRJ = { TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE, 
        TerrainType.GAMETRAIL, TerrainType.GAMETRAIL, TerrainType.GAMETRAIL, 
        TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE };
	

	static TerrainType[] tileF = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
			TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.GAMETRAIL,
			TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
			TerrainType.JUNGLE };

	static TerrainType[] tileG = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
			TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
			TerrainType.GAMETRAIL, TerrainType.JUNGLE, TerrainType.JUNGLE,
			TerrainType.JUNGLE };

	public static void main(String[] args) {

		Board board = new Board();

		// Adding tiles to deck
		/*
		Tile tile1 = new Tile(tileF, 1);
		Tile tile2 = new Tile(tileG, 1);

		System.out.println(tile1.getTrailEdgeCount());
		System.out.println(tile1.isContinuation());

		System.out.println(board.addXTile(0, 0, tile2));
		System.out.println(tile2.getRow() + " " + tile2.getCol());
		System.out.println(tile2.getTrailScore());

		System.out.println();
		System.out.println(board.addXTile(0, 1, tile1));
		System.out.println(tile1.getTrailScore());
		*/
		
		Tile tl = new Tile(tileTL, 1);
		Tile tl2 = new Tile(tileTL, 1);
		Tile tr = new Tile(tileTR, 1);
		Tile tr2 = new Tile(tileTR, 1);
		Tile bl = new Tile(tileBL, 1);
		Tile bl2 = new Tile(tileBL, 1);
		Tile br = new Tile(tileBR, 1);		
		Tile br2 = new Tile(tileBR, 1);		
		
		
		
		Tile mrtee = new Tile(tileMRTEE, 1);
		Tile bmrtee = new Tile(tileBMRTEE, 1);
		Tile mrj = new Tile(tileMRJ, 1);

		board = new Board();
		//System.out.println(board.addXTile(0, 0, tl));
		
		System.out.println(board.addXTile(0, 1, tl));
		System.out.println(board.addXTile(0, 2, tr));
		
		System.out.println(board.addXTile(1, 2, br));
		System.out.println(board.addXTile(1, 1, mrj));
		
		System.out.println(board.addXTile(1, 0, tl2));
				
		System.out.println(board.addXTile(2, 0, bl));
		System.out.println(board.addXTile(2, 1, br2));
		
		
		
		//System.out.println(board.addXTile(1, 0, bl));
		//System.out.println(board.addXTile(1, 0, mrtee));
		//System.out.println(board.addXTile(1, 1, br));
		//System.out.println(board.addXTile(2, 0, bmrtee));	
		
		
		System.out.println(mrj.getTrailScore());
		//System.out.println(mrtee.getTrailScore());
		
		
		// System.out.println(board.addXTile(0, 1, tile2));

	}
}