package clueGame;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawPanel extends JPanel {
	
	public void paintComponent(Graphics g)
	{
	  super.paintComponent(g);
	  g.setColor(Color.BLUE);
	  g.drawRect(100, 100, 20, 20);
	}


}
