package clueGame;

import java.util.*;

import javax.swing.JOptionPane;

public class ComputerPlayer extends Player {
	private static final long serialVersionUID = 1L;
	public static boolean timeToAccuse = false;
	public static Solution lastGuess;

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
	
	public void makeAccusation(Board board) {
		if(board.checkAccustaion(lastGuess)) {
			ClueGame.gameOver = true;
			JOptionPane.showMessageDialog(null, getPlayerName() + " won the game with a guess of " + lastGuess.person + " on " + lastGuess.room + " with the " + lastGuess.weapon);
		}
		else {
			timeToAccuse = false;
			JOptionPane.showMessageDialog(null, getPlayerName() + " was not correct with a guess of " + lastGuess.person + " on " + lastGuess.room + " with the " + lastGuess.weapon);
		}
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
	
	public void makeMove(Board board, int steps, ClueGame game) {
		Solution solution;
		board.calcTargets(row, column, steps);
		BoardCell target = pickLocation(board.getTargets()); 
		
		if(timeToAccuse){
			makeAccusation(board);
			return;
		}
		
		row = target.getRow();
		column = target.getCol();
		
		repaint();
		
		if (board.getCellAt(row, column).isDoorway()) {
			solution = makeSuggestion(board, board.getCellAt(row, column));
			for (Player p : Board.players){
				if (p.getPlayerName().equals(solution.person)) 
					p.setLocation(this.row, this.column);
			}
			game.gui.updateGuess(solution.person + " on " + solution.room + " with the " + solution.weapon);
			lastGuess = solution;
			Card newCard = board.handleSuggestion(solution, this, null);
			if (newCard != null){
				seenCards.add(newCard);
				game.gui.updateResponse(newCard);
			}
			else{
				timeToAccuse = true;
			}
			
		}
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
