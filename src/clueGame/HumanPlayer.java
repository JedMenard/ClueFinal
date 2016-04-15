package clueGame;

public class HumanPlayer extends Player {
	private static final long serialVersionUID = 1L;

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
	public void makeMove(ClueGame game, Board board, int steps){
		board.humanTurnOver = false;
		board.calcTargets(row, column, steps);
		board.highlightTargets();
		game.repaint();
		
		
		//TODO: make suggestion
	}
	
	
}
