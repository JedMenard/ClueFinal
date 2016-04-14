package clueGame;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.*;

import com.sun.glass.events.MouseEvent;

public class ClueGame extends JFrame {
	public static Board board;
	private int currentPlayer = 0;


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
			
		}
	}
	

//	// Unused Functions
//	@Override
//	public void mouseReleased(java.awt.event.MouseEvent arg0) {}
//	@Override
//	public void mouseEntered(java.awt.event.MouseEvent arg0) {}
//	@Override
//	public void mouseExited(java.awt.event.MouseEvent arg0) {}
//	@Override
//	public void mousePressed(java.awt.event.MouseEvent arg0) {}
//
//
//	@Override
//	public void mouseClicked(java.awt.event.MouseEvent arg0) {
//		
//
//	}





	public static void main(String[] args) {
		ClueGame ourGame = new ClueGame();


		ourGame.setSize((board.getNumRows()*40>1000) ? board.getNumRows()*40 : 1000, board.getNumColumns()*40);

		ControlGUI gui = new ControlGUI(ourGame);
		ourGame.add(gui, BorderLayout.SOUTH);


		JOptionPane splash = new JOptionPane();
		splash.showMessageDialog(ourGame, "You are Professor Plum, press Next Player to begin play.", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);

		HumanCards myCards = new HumanCards();
		ourGame.add(myCards, BorderLayout.EAST);

		ourGame.setVisible(true);


	}
}
