package clueGame;

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
		// TODO Auto-generated method stub
		return row;
	}

	public int getCol() {
		// TODO Auto-generated method stub
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

}
