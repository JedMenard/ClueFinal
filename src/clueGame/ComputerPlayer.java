package clueGame;

import java.util.*;

public class ComputerPlayer extends Player {
	private static final long serialVersionUID = 1L;

	public ComputerPlayer(String n){
		super();
		super.setPlayerName(n);
	}

	public ComputerPlayer() {
		super();
	}

	public ComputerPlayer(String name, String color, String row, String col) {
		super(name, color, row, col);
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		ArrayList<BoardCell> doors = new ArrayList<BoardCell>();
		ArrayList<BoardCell> notDoors = new ArrayList<BoardCell>();
		
		BoardCell chosenCell;
		
		Random r = new Random();
		
		for(BoardCell t : targets) {
			if(t == super.lastCell) {
				// Added the below line of code
				notDoors.add(t);
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
		Random r = new Random();
		ArrayList<Card> weapons = new ArrayList<Card>();
		ArrayList<Card> people = new ArrayList<Card>();
		for (Card c: Deck.allCards) {
			if (!seenCard(c)){
				if (c.getType() == CardType.WEAPON) weapons.add(c);
				else if (c.getType() == CardType.PERSON) people.add(c);
			}
		}
		
		String person = people.get(r.nextInt(people.size())).getName();
		String weapon = weapons.get(r.nextInt(weapons.size())).getName();
		String room = location.getName();
		
		return new Solution(person, room, weapon);
	}
	
	public void makeMove(Board board, int steps) {
		board.calcTargets(row, column, steps);
		BoardCell target = pickLocation(board.getTargets()); 
		
		row = target.getRow();
		column = target.getCol();
		
		repaint();
		
		if (board.getCellAt(row, column).isDoorway()) makeSuggestion(board, board.getCellAt(row, column));
		// TODO: Handle accusations
	}
	
	private boolean seenCard(Card card) {
		for(Card c : seenCards) {
			if(c.equals(card)) {
				return true;
			}
		} 
		return false;
	}
}
