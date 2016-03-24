package clueGame;

import java.util.*;

public class ComputerPlayer extends Player {
	public BoardCell pickLocation(Set<BoardCell> targets) {
		ArrayList<BoardCell> doors = new ArrayList<BoardCell>();
		ArrayList<BoardCell> notDoors = new ArrayList<BoardCell>();
		
		BoardCell chosenCell;
		
		Random r = new Random();
		
		for(BoardCell t : targets) {
			if(t == super.lastCell) {
				continue;
			} else if(t.isDoorway()) {
				doors.add(t);
			} else {
				notDoors.add(t);
			}
		}
				
		if(doors.size() > 0) {
			int ran = r.nextInt(doors.size());
			chosenCell = doors.get(ran);
		} else {
			int ran = r.nextInt(notDoors.size());
			chosenCell = notDoors.get(ran);
		}
		
		return chosenCell;
	}
	
	public void makeAccustation(Board board) {
		
	}
	
	public Solution makeSuggestion(Board board, BoardCell location) {
		return null;
	}

}
