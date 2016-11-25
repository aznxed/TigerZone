package tigerzone;

import java.util.ArrayList;
import java.util.List;

public class Cluster extends Board{
	private TerrainType feature;
	private int size;
	private List<int[]> tilePortions = new ArrayList<int[]>();
	
	
	public Cluster(TerrainType feature)
	{
		this.feature = feature;
	}
	
	public TerrainType getFeature()
	{
		return feature;
	}
	
	public List<int[]> getTilePortions()
	{
		return tilePortions;
	}
	
	public int getSize(){
		return size;
	}
	
	public void addTail(int[] tilePortion)
	{
		
		tilePortions.add(tilePortion);
	}
	
	public void addHead(int[] tilePortion)
	{
		tilePortions.add(tilePortion);
	}
	
	//Gets adjacent clusters to see if you add or or create new
	public int[] getAdjacentClusters(int x, int y){
		List<Tile> neighbors = new ArrayList<Tile>();
		neighbors = getNeighbors(x, y);
		if(neighbors.size() == 0){
			
		}
		
		else{
			
		}
		return null;
	}
	
	
	
	
	
}
