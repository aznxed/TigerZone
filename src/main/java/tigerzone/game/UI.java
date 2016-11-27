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
								+ Integer.toString(gameBoard.getBoard()[i][j]
										.getType())
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