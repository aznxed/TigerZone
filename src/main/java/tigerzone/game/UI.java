package tigerzone.game;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UI 
{ 
	public int getType(String terrainType){
		switch(terrainType)
		{
			case"JJJJ-":	
				return 1;
			case"JJJJX":	
				return 2;
			case"JJTJX":	
				return 3;
			case"TTTT-":	
				return 4;
			case"TJTJ-":	
				return 5;
			case"TJJT-":	
				return 6;
			case"TJTT-":	
				return 7;
			case"LLLL-":	
				return 8;	
			case"JLLL-":	
				return 9;	
			case"LLJJ-":	
				return 10;	
			case"JLJL-":	
				return 11;
			case"LJLJ-":	
				return 12;
			case"LJJJ-":	
				return 13;
			case"JLLJ-":	
				return 14;
			case"TLJT-":	
				return 15;
			case"TLJTP":	
				return 16;
			case"JLTT-":	
				return 17;
			case"JLTTB":	
				return 18;
			case"TLTJ-":	
				return 19;
			case"TLTJD":	
				return 20;
			case"TLLL-":	
				return 21;
			case"TLTT-":	
				return 22;
			case"TLTTP":	
				return 23;
			case"TLLT-":	
				return 24;
			case"TLLTB-":	
				return 25;
			case"LJTJ-":	
				return 26;
			case"LJTJD":	
				return 27;
			case"TLLLC":	
				return 28;
				}
		
		return 0;
		
	}
	
	public void createUIBoard(Board gameBoard)
	{
	String imagePath = "src/main/CarcassonneTiles/";
	JFrame frame = new JFrame();
	frame.setPreferredSize(new Dimension(800, 1000));

	JPanel jp = new JPanel(new GridLayout(gameBoard.getBottomBound()
			- gameBoard.getTopBound() + 1, gameBoard.getRightBound()
			- gameBoard.getLeftBound() + 1, 0, 0));

	frame.pack();
	for (int i = gameBoard.getTopBound(); i <= gameBoard.getBottomBound(); i++) {
		for (int j = gameBoard.getLeftBound(); j <= gameBoard
				.getRightBound(); j++) {
			if (gameBoard.getBoard()[i][j] == null) {
				JLabel j29 = new JLabel();
				j29.setIcon(new ImageIcon(imagePath + "Tile29.png"));
				jp.add(j29);
			} else {
				JLabel j1 = new JLabel();
				ImageIcon II = new ImageIcon(imagePath + "Tile"
								+ Integer.toString(getType(gameBoard.getBoard()[i][j].getTerrainTypeString()))
								+ "."
								+ Integer.toString(gameBoard.getBoard()[i][j]
										.getDegrees()) + ".png");
				Image image = II.getImage(); // transform it
				int tileHeight = gameBoard.getBottomBound() - gameBoard.getTopBound();
				int tileWidth = gameBoard.getRightBound() - gameBoard.getLeftBound();
				//System.out.println("Height: " + tileHeight);
				//System.out.println("Width: " + tileWidth);
				Image newimg = image.getScaledInstance(frame.getContentPane().getWidth() / (tileWidth * (13 / 8)),
						frame.getContentPane().getHeight() / (tileHeight * (13 / 8)) ,
						java.awt.Image.SCALE_SMOOTH); // scale
														// it
														// the
														// smooth
														// way
				II = new ImageIcon(newimg);
				j1.setIcon(II);
				jp.add(j1);
			}
		}
	}
	
	frame.add(jp);
	frame.setVisible(true);
}
}