package clueGame;

public class HumanPlayer extends Player {
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
	
	public void makeMove(Board board, int steps){
		board.calcTargets(row, column, steps);
		board.highlightTargets();
		
		
		
		//TODO: make suggestion
	}
	
	
}
