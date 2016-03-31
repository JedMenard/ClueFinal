package clueGame;

import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.JPanel;
import javax.swing.text.html.HTMLDocument.Iterator;

import java.awt.Color;

import java.awt.Graphics;


public class BoardCell extends JPanel {

	private int row, col;
	private static Dimension size = new Dimension(35,35);
	private static HashMap<Character,Color> colorMap = new HashMap<Character,Color>();	
	private char initial;
	public boolean isName = false;
	public String name;
	private boolean isDoor;
	private DoorDirection dir;

	public BoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}

	public void setInitial(char initial) {
		this.initial = initial;
	}

	public BoardCell() {
		super();
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", col=" + col + "]";
	}

	public boolean isDoorway() {
		return isDoor;
	}

	public boolean isRoom() {
		if (initial != 'W') return true;
		return false;
	}

	public DoorDirection getDoorDirection() {
		if(isDoor)
			return dir;
		else return null;
	}

	public char getInitial() {
		return initial;
	}

	public void setDoorDirection(char d) {

		switch(d){
		case 'R':
			dir = DoorDirection.RIGHT;
			break;
		case 'L':
			dir = DoorDirection.LEFT;
			break;
		case 'U':
			dir = DoorDirection.UP;
			break;
		case 'D':
			dir = DoorDirection.DOWN;
			break;
		}
		isDoor=true;
	}

	public String getName() {
		try {
			FileReader f = new FileReader("Legend.txt");
			BufferedReader reader = new BufferedReader(f);
			String line;

			while((line = reader.readLine()) != null) {
				String[] split = line.split(", ");

				if(split[0].toCharArray()[0] == (this.initial)) {
					reader.close();
					f.close();
					return split[1];
				}
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("No");
		} catch (IOException e){
			System.out.println("Even more no");
		}
		return "!";
	}

	public void draw(Graphics g){
		g.setColor(mapColor());
		g.fillRect((int)(col*size.getWidth()), (int)(row*size.getHeight()), (int)size.getHeight(), (int)size.getWidth());
		if (initial == 'W') {
			g.setColor(Color.WHITE);
			g.drawRect((int)(col*size.getWidth()), (int)(row*size.getHeight()), (int)size.getHeight(), (int)size.getWidth());
		}
		if (isName) {
			
			
			g.setColor(Color.BLACK);
			//g.setClip((int)(col*size.getWidth()), (int)(row*size.getHeight()), 400, 400);
			g.drawString(name, (int)(col*size.getWidth()), (int)(row*size.getHeight()));
			//g.setFont(new Font("TimesRoman", Font.PLAIN, 36));
			
		}
	}

	public Color mapColor() {
		switch (initial) {
		case 'E': return Color.GREEN;
		case 'S': return Color.decode("#DEB887"); //Burlywood!
		case 'V': return Color.orange;
		case 'N': return Color.blue;
		case 'M': return Color.gray;
		case 'J': return new Color(255,255,125);
		case 'A': return Color.red;
		case 'P': return Color.magenta;
		case 'U': return Color.CYAN;
		case 'W': return Color.black;
		case 'X': return Color.yellow;
		default:
			Random ran = new Random();
			if (!colorMap.containsKey(initial)) colorMap.put(initial, new Color(ran.nextInt(255),ran.nextInt(255),ran.nextInt(255)));
			return colorMap.get(initial);
			//return new Color(ran.nextInt(255),ran.nextInt(255),ran.nextInt(255));
		}
	}
}
