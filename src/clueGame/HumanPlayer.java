package clueGame;

import javax.swing.JOptionPane;

public class HumanPlayer extends Player {
	private static final long serialVersionUID = 1L;
	boolean wantToMakeSuggestion = false;

	public HumanPlayer(){
		super();
	}
	
	public HumanPlayer(String n){
		super();
		super.setPlayerName(n);
	}

	public HumanPlayer(Player player) {
		super(player.getPlayerName(), player.getColor(), player.getRow(), player.getColumn());
	}

	public HumanPlayer(String name, String color, String row, String col) {
		super(name, color, row, col);
	}
	
	@Override
	public void makeMove(Board board, int steps, ClueGame game){
		board.humanTurnOver = false;
		board.calcTargets(row, column, steps);
		board.highlightTargets();
		if (board.getCellAt(row, column).isDoorway()) makeSuggestion(board.getCellAt(row, column),game);
		repaint();
	}
	
	public Solution makeSuggestion(BoardCell cell, ClueGame game){
		int confirm = JOptionPane.showConfirmDialog(null, "Would you like to make a suggestion?");
		
		if (confirm == JOptionPane.YES_OPTION){
			game.displaySuggestionPrompt();
		}
		return null;
	}
}
