package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ClueGame extends JFrame {
	private static final long serialVersionUID = 1L;
	public static Board board;
	private int currentPlayer = 0;
	public static boolean gameOver = false;
	private ControlGUI gui;

	public void addGUI(ControlGUI g) {
		gui = g;
	}

	public ClueGame(){
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue");

		board = new Board();
		board.initialize();
		add(board, BorderLayout.CENTER);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());		

	}

	private JMenu createFileMenu()
	{
		JMenu menu = new JMenu("File"); 
		menu.add(createDetectiveNotes());
		menu.add(createFileExitItem());		
		return menu;
	}

	private JMenuItem createFileExitItem()
	{
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());

		return item;
	}

	private JMenuItem createDetectiveNotes()
	{
		JMenuItem item = new JMenuItem("Detective Notes");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e)
			{
				DetectiveNotes notes = new DetectiveNotes();
				notes.setSize(500,500);
				notes.setVisible(true);
			}
		}
		item.addActionListener(new MenuItemListener());

		return item;
	}

	public void handleRounds(){
		if (!board.humanTurnOver){
			JOptionPane.showMessageDialog(new JFrame(), "Your turn is not over", "Error", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		else {
			ClueGame.board.unhighlight();
			int newRoll = ControlGUI.roll();
			Board.players.get(currentPlayer).makeMove(board, newRoll);
			
			
			currentPlayer++;
			System.out.println(currentPlayer);
			
			if (currentPlayer == Board.players.size()) currentPlayer = 0;
		}
	}
	
	public String getCurrentPlayerName(){
		return Board.players.get(currentPlayer).getPlayerName();
	}

	public static void main(String[] args) {
		ClueGame ourGame = new ClueGame();


		ourGame.setSize((board.getNumRows()*40>1000) ? board.getNumRows()*40 : 1000, board.getNumColumns()*40);

		ControlGUI gui = new ControlGUI(ourGame);
		ourGame.add(gui, BorderLayout.SOUTH);
		ourGame.addGUI(gui);


		JOptionPane.showMessageDialog(ourGame, "You are Professor Plum, press Next Player to begin play.", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);

		HumanCards myCards = new HumanCards();
		ourGame.add(myCards, BorderLayout.EAST);

		ourGame.setVisible(true);


	}
}
