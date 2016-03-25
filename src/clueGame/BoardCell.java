package clueGame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class BoardCell {
	
	private int row, col;
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
		// TODO Auto-generated constructor stub
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
		// TODO Auto-generated method stub
		return isDoor;
	}

	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		if(isDoor)
			return dir;
		else return null;
	}

	public char getInitial() {
		// TODO Auto-generated method stub
		return initial;
	}

	public void setDoorDirection(char d) {
		
		// TODO Auto-generated method stub
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
}
