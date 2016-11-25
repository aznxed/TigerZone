package tigerzone;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
	private TerrainType feature;
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
	
	public void addTail(int[] tilePortion)
	{
		tilePortions.add(tilePortion);
	}
	
	public void addHead(int[] tilePortion)
	{
		tilePortions.add(tilePortion);
	}
	
}

