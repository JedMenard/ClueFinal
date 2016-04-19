package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ClueGame extends JFrame {
	private static final long serialVersionUID = 1L;
	public static Board board;
	private int currentPlayer = 0;
	public static boolean gameOver = false;
	private boolean firstTime = true;
	public ControlGUI gui;
	protected Solution suggestion;
	

	public void addGUI(ControlGUI g) {
		gui = g;
	}

	public ClueGame(){
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue");

		board = new Board(this);
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
		if (gameOver){
			JOptionPane.showMessageDialog(null, "Game over!!!");
		}
		else if (!board.humanTurnOver){
			JOptionPane.showMessageDialog(new JFrame(), "Your turn is not over", "Error", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		else {
			if (!firstTime)currentPlayer++;
			else firstTime = false;
			if (currentPlayer == Board.players.size()) currentPlayer = 0;
			ClueGame.board.unhighlight();
			int newRoll = ControlGUI.roll();
			Board.players.get(currentPlayer).makeMove(board, newRoll, this);
			
			
			
			//System.out.println(currentPlayer);
			
			
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

	JTextField roomName;
	JComboBox<String> personBox;
	JComboBox<String> weaponBox;
	JComboBox<String> roomBox;
	public void displaySuggestionPrompt() {
		Player thisPlayer = Board.players.get(currentPlayer);
		
		JDialog suggestionWindow = new JDialog(this);
		suggestionWindow.setTitle("Make a suggestion");
		suggestionWindow.setLayout(new GridLayout(0,2));
		
		JTextPane roomText = new JTextPane();
		roomText.setText("Your room:");
		roomText.setEditable(false);
		suggestionWindow.add(roomText);
		
		roomName = new JTextField();
		roomName.setText(board.getCellAt(thisPlayer.row, thisPlayer.column).getName());
		roomName.setEditable(false);
		suggestionWindow.add(roomName);
		
		JTextPane personText = new JTextPane();
		personText.setText("Person:");
		personText.setEditable(false);
		suggestionWindow.add(personText);
		
		personBox = new JComboBox<String>();
		for (Player p : Board.players) personBox.addItem(p.getPlayerName());
		personBox.addActionListener(new ComboBoxListenerSuggestion());
		suggestionWindow.add(personBox);
		
		JTextPane weaponText = new JTextPane();
		weaponText.setText("Weapon:");
		weaponText.setEditable(false);
		suggestionWindow.add(weaponText);
		
		weaponBox = new JComboBox<String>();
		for (Card c : Board.weaponCards) weaponBox.addItem(c.getName());
		weaponBox.addActionListener(new ComboBoxListenerSuggestion());
		suggestionWindow.add(weaponBox);
		
		JButton submit = new JButton("Submit");
		submit.addActionListener(new SubmitSuggestionListener(suggestionWindow));
		suggestionWindow.add(submit);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new CancelListener(suggestionWindow));
		suggestionWindow.add(cancel);
		
		suggestion = new Solution(personBox.getSelectedItem().toString(), roomName.getText(), weaponBox.getSelectedItem().toString());
		
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
		
		roomBox = new JComboBox<String>();
		for (char c : Board.roomNames.keySet()) roomBox.addItem(Board.roomNames.get(c));
		roomBox.addActionListener(new ComboBoxListenerAccusation());
		accusationWindow.add(roomBox);
		
		JTextPane personText = new JTextPane();
		personText.setText("Person:");
		accusationWindow.add(personText);
		
		personBox = new JComboBox<String>();
		for (Player p : Board.players) personBox.addItem(p.getPlayerName());
		personBox.addActionListener(new ComboBoxListenerAccusation());
		accusationWindow.add(personBox);
		
		JTextPane weaponText = new JTextPane();
		weaponText.setText("Weapon:");
		accusationWindow.add(weaponText);
		
		weaponBox = new JComboBox<String>();
		for (Card c : Board.weaponCards) weaponBox.addItem(c.getName());
		weaponBox.addActionListener(new ComboBoxListenerAccusation());
		accusationWindow.add(weaponBox);
		
		JButton submit = new JButton("Submit");
		submit.addActionListener(new SubmitAccusationListener(accusationWindow));
		accusationWindow.add(submit);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new CancelListener(accusationWindow));
		accusationWindow.add(cancel);
		
		suggestion = new Solution(personBox.getSelectedItem().toString(), roomBox.getSelectedItem().toString(), weaponBox.getSelectedItem().toString());
		
		accusationWindow.pack();
		accusationWindow.setVisible(true);
	}
	
	class SubmitAccusationListener implements ActionListener{
		JDialog window;
		SubmitAccusationListener(JDialog window){
			this.window = window;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(ClueGame.board.checkAccustaion(suggestion)){
				gameOver = true;
				JOptionPane.showMessageDialog(null, Board.players.get(0).getPlayerName() + " won the game with a guess of " + suggestion.person + " on " + suggestion.room + " with the " + suggestion.weapon);
			}
			else{
				JOptionPane.showMessageDialog(new JFrame(), Board.players.get(0).getPlayerName() + " was wrong with a guess of " + suggestion.person + " on " + suggestion.room + " with the " + suggestion.weapon);
			}
			ClueGame.board.unhighlight();
			repaint();
			ClueGame.board.humanTurnOver = true;
			
			//TODO: Update the control panel with the most recent accusation
			
			window.setVisible(false);
			board.humanTurnOver = true;
			board.unhighlight();
			repaint();
		}
		
		
	}
	
	class SubmitSuggestionListener implements ActionListener{
		JDialog window;
		
		SubmitSuggestionListener(JDialog window){
			this.window = window;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Card newCard = ClueGame.board.handleSuggestion(suggestion, Board.players.get(currentPlayer), new BoardCell());
			gui.updateGuess(suggestion.person + " on " + suggestion.room + " with the " + suggestion.weapon);
			gui.updateResponse(newCard);
			for (Player p : Board.players){
				if (p.getPlayerName().equals(suggestion.person)) 
					p.setLocation(Board.players.get(currentPlayer).row, Board.players.get(currentPlayer).column);
			}
			window.setVisible(false);
			board.humanTurnOver = true;
			board.unhighlight();
			repaint();
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
	
	public class ComboBoxListenerSuggestion implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {			
			suggestion = new Solution(personBox.getSelectedItem().toString(), roomName.getText(), weaponBox.getSelectedItem().toString());
		}
		
	}
	
	public class ComboBoxListenerAccusation implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {			
			suggestion = new Solution(personBox.getSelectedItem().toString(), roomBox.getSelectedItem().toString(), weaponBox.getSelectedItem().toString());
		}
		
	}
}
