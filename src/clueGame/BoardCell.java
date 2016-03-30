package clueGame;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JPanel;

import com.sun.prism.Graphics;
import com.sun.prism.paint.Color;

public class BoardCell extends JPanel {
	
	private int row, col;
	private static Dimension size = new Dimension(40,40);
	private char initial;
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
		g.
		g.drawRect((float)(row*size.getHeight()), (float)(col*size.getWidth()), (float)size.getHeight(), (float)size.getWidth());
	}
}
