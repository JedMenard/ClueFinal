package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import jdk.nashorn.internal.scripts.JD;

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
			//System.out.println(currentPlayer);
			
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


		JOptionPane.showMessageDialog(ourGame, "You are Mrs. Peacock, press Next Player to begin play.", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);

		HumanCards myCards = new HumanCards();
		ourGame.add(myCards, BorderLayout.EAST);

		ourGame.setVisible(true);


	}

	public void displaySuggestionPrompt() {
		Player thisPlayer = Board.players.get(currentPlayer);
		
		JDialog suggestionWindow = new JDialog(this);
		suggestionWindow.setTitle("Make a suggestion");
		suggestionWindow.setLayout(new GridLayout(0,2));
		
		JTextPane roomText = new JTextPane();
		roomText.setText("Your room:");
		suggestionWindow.add(roomText);
		
		JTextField roomName = new JTextField();
		roomName.setText(board.getCellAt(thisPlayer.row, thisPlayer.column).getName());
		roomName.setEditable(false);
		suggestionWindow.add(roomName);
		
		JTextPane personText = new JTextPane();
		personText.setText("Person:");
		suggestionWindow.add(personText);
		
		JComboBox<String> personBox = new JComboBox<String>();
		for (Player p : Board.players) personBox.addItem(p.getPlayerName());
		suggestionWindow.add(personBox);
		
		JTextPane weaponText = new JTextPane();
		weaponText.setText("Weapon:");
		suggestionWindow.add(weaponText);
		
		JComboBox<String> weaponBox = new JComboBox<String>();
		for (Card c : Board.weaponCards) weaponBox.addItem(c.getName());
		suggestionWindow.add(weaponBox);
		
		JButton submit = new JButton("Submit");
		Solution solution = new Solution(personBox.getItemAt(personBox.getSelectedIndex()),roomName.getText(),weaponBox.getItemAt(weaponBox.getSelectedIndex()));
		submit.addActionListener(new SubmitSuggestionListener(solution, suggestionWindow));
		suggestionWindow.add(submit);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new CancelListener(suggestionWindow));
		suggestionWindow.add(cancel);
		
		suggestionWindow.pack();
		suggestionWindow.setVisible(true);
	}
	
	public void displayAccusationPrompt() {
		JDialog accusationWindow = new JDialog(this);
		accusationWindow.setTitle("Make an accusation");
		accusationWindow.setLayout(new GridLayout(0,2));
		
		JTextPane roomText = new JTextPane();
		roomText.setText("Your room:");
		accusationWindow.add(roomText);
		
		JComboBox<String> roomBox = new JComboBox<String>();
		for (char c : Board.roomNames.keySet()) roomBox.addItem(Board.roomNames.get(c));
		accusationWindow.add(roomBox);
		
		JTextPane personText = new JTextPane();
		personText.setText("Person:");
		accusationWindow.add(personText);
		
		JComboBox<String> personBox = new JComboBox<String>();
		for (Player p : Board.players) personBox.addItem(p.getPlayerName());
		accusationWindow.add(personBox);
		
		JTextPane weaponText = new JTextPane();
		weaponText.setText("Weapon:");
		accusationWindow.add(weaponText);
		
		JComboBox<String> weaponBox = new JComboBox<String>();
		for (Card c : Board.weaponCards) weaponBox.addItem(c.getName());
		accusationWindow.add(weaponBox);
		
		JButton submit = new JButton("Submit");
		Solution solution = new Solution(personBox.getItemAt(personBox.getSelectedIndex()), roomBox.getItemAt(roomBox.getSelectedIndex()), weaponBox.getItemAt(weaponBox.getSelectedIndex()));
		submit.addActionListener(new SubmitAccusationListener(solution, accusationWindow));
		accusationWindow.add(submit);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new CancelListener(accusationWindow));
		accusationWindow.add(cancel);
		
		accusationWindow.pack();
		accusationWindow.setVisible(true);
	}
	
	class SubmitAccusationListener implements ActionListener{
		Solution solution;
		JDialog window;
		SubmitAccusationListener(Solution solution, JDialog window){
			this.solution = solution;
			this.window = window;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			ClueGame.board.checkAccustaion(solution);
			ClueGame.board.unhighlight();
			repaint();
			ClueGame.board.humanTurnOver = true;
			
			//TODO: Update the control panel with the most recent accusation
			
			window.setVisible(false);
		}
		
		
	}
	
	class SubmitSuggestionListener implements ActionListener{
		Solution solution;
		JDialog window;
		
		SubmitSuggestionListener(Solution solution, JDialog window){
			this.solution = solution;
			this.window = window;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ClueGame.board.handleSuggestion(solution, Board.players.get(currentPlayer), new BoardCell());
			
			//TODO: Update the control panel with the most recent accusation
			
			window.setVisible(false);	
		}
		
		
	}
	
	class CancelListener implements ActionListener{
		JDialog window;
		public CancelListener(JDialog window) {
			this.window = window;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			window.setVisible(false);
		}
		
		
	}
}
