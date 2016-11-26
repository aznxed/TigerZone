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
	
	
	static TerrainType[] tileLake1 = { TerrainType.JUNGLE, TerrainType.LAKE,  TerrainType.LAKE, 
		                              TerrainType.JUNGLE, TerrainType.LAKE,  TerrainType.LAKE, 
		                              TerrainType.JUNGLE, TerrainType.JUNGLE,TerrainType.JUNGLE };
	
	
	static TerrainType[] tileLake2 = { TerrainType.JUNGLE, TerrainType.LAKE,  TerrainType.JUNGLE, 
                                       TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.LAKE, 
                                       TerrainType.JUNGLE, TerrainType.JUNGLE,TerrainType.JUNGLE };
	
	static TerrainType[] tileLake2b = { TerrainType.JUNGLE, TerrainType.LAKE,   TerrainType.JUNGLE, 
                                        TerrainType.LAKE,   TerrainType.JUNGLE, TerrainType.JUNGLE, 
                                        TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE };
	
	static TerrainType[] tileLake3 = { TerrainType.JUNGLE, TerrainType.JUNGLE,  TerrainType.JUNGLE, 
        							   TerrainType.LAKE, TerrainType.LAKE,  TerrainType.JUNGLE, 
        							   TerrainType.LAKE, TerrainType.LAKE,TerrainType.JUNGLE };
	
	static TerrainType[] tileLake4 = { TerrainType.JUNGLE, TerrainType.JUNGLE,  TerrainType.JUNGLE, 
		                               TerrainType.JUNGLE, TerrainType.LAKE,  TerrainType.LAKE, 
		                               TerrainType.JUNGLE, TerrainType.LAKE,TerrainType.LAKE };
	
	static TerrainType[] tileLake5 = { TerrainType.LAKE, TerrainType.LAKE,  TerrainType.JUNGLE, 
                                       TerrainType.LAKE, TerrainType.LAKE,  TerrainType.JUNGLE, 
                                       TerrainType.JUNGLE, TerrainType.JUNGLE,TerrainType.JUNGLE };
	
	
	static TerrainType[] tileLake6 = { TerrainType.JUNGLE, TerrainType.LAKE,  TerrainType.JUNGLE, 
        							   TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE, 
        							   TerrainType.JUNGLE, TerrainType.JUNGLE,TerrainType.JUNGLE };
	
	static TerrainType[] tileLake7 = { TerrainType.JUNGLE, TerrainType.LAKE,  TerrainType.JUNGLE, 
		                               TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE, 
		                               TerrainType.JUNGLE, TerrainType.LAKE,TerrainType.JUNGLE };
	
	static TerrainType[] tileLake7b = { TerrainType.JUNGLE, TerrainType.JUNGLE,  TerrainType.JUNGLE, 
                                        TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.LAKE, 
                                        TerrainType.JUNGLE, TerrainType.JUNGLE,TerrainType.JUNGLE };
	
	static TerrainType[] tileLake8 = { TerrainType.JUNGLE, TerrainType.JUNGLE,  TerrainType.JUNGLE, 
		                               TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE, 
		                               TerrainType.JUNGLE, TerrainType.LAKE,TerrainType.JUNGLE };
	
	static TerrainType[] tileLakeHG = { TerrainType.JUNGLE, TerrainType.LAKE,  TerrainType.JUNGLE, 
                                        TerrainType.JUNGLE, TerrainType.LAKE, TerrainType.JUNGLE, 
                                        TerrainType.JUNGLE, TerrainType.LAKE,TerrainType.JUNGLE };
	
	static TerrainType[] tileDen =    { TerrainType.JUNGLE, TerrainType.JUNGLE,  TerrainType.JUNGLE, 
                                        TerrainType.JUNGLE, TerrainType.DEN, TerrainType.JUNGLE, 
                                        TerrainType.JUNGLE, TerrainType.JUNGLE,TerrainType.JUNGLE };
	static TerrainType[] tileDen1 =    { TerrainType.JUNGLE, TerrainType.JUNGLE,  TerrainType.JUNGLE, 
                                         TerrainType.JUNGLE, TerrainType.DEN, TerrainType.JUNGLE, 
                                         TerrainType.JUNGLE, TerrainType.JUNGLE,TerrainType.JUNGLE };
	
	static TerrainType[] tileJungle =    { TerrainType.JUNGLE, TerrainType.JUNGLE,  TerrainType.JUNGLE, 
        								TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE, 
        								TerrainType.JUNGLE, TerrainType.JUNGLE,TerrainType.JUNGLE };
	
	
	static TerrainType[] tileCanal = { TerrainType.JUNGLE, TerrainType.JUNGLE,  TerrainType.JUNGLE, 
        							   TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE, 
        							   TerrainType.JUNGLE, TerrainType.JUNGLE,TerrainType.JUNGLE };

	

	public static void main(String[] args) {

		Board board = new Board();

		Tile tl = new Tile(tileTL, 1);
		
		Tile lake1 = new Tile(tileLake1, 2);
		Tile lake4 = new Tile(tileLake4, 3);
		Tile lake3 = new Tile(tileLake3, 4);
		Tile lake5 = new Tile(tileLake5, 5);
		Tile lakeCanal = new Tile(tileCanal, 6);
		Tile lake7b = new Tile(tileLake7b, 7);
			
		board = new Board();
			
		
		/*
		System.out.println(board.addXTile(0, 0, den));	
		System.out.println(board.addXTile(0, 1, lake4));
		System.out.println(board.addXTile(1, 0, den1));	
		System.out.println(board.addXTile(1, 1, lake1));
		System.out.println(lake4.collectDenScores());
		System.out.println(lake1.collectDenScores());
		System.out.println(den.collectDenScores());
		System.out.println(den1.collectDenScores());
		System.out.println();
		System.out.println(den.getDenScore());
		System.out.println(den1.getDenScore());
		*/
		//System.out.println(board.addXTile(1, 1, lake5));
		//System.out.println(lake5.collectDenScores());		
		//System.out.println(den.getDenScore());
		//System.out.println(den1.getDenScore());
		
		System.out.println(board.addXTile(0, 0, lake4));
		System.out.println(board.addXTile(0, 1, lakeCanal));
		System.out.println(board.addXTile(0, 2, lake3));
		System.out.println(board.addXTile(1, 0, lake1));
		System.out.println(board.addXTile(1, 1, lake7b));
		System.out.println(board.addXTile(1, 2, lake5));
		System.out.println(lake5.getLakeScore());
				
		
	}
}