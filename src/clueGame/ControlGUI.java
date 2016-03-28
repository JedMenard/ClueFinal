package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlGUI extends JPanel {
	
	private JTextField name;
	
	public ControlGUI(){
		JPanel panel = createTurnControlPanel();
		add(panel);
		
		panel = createDiePanel();
		add(panel);
		
		panel = createGuessPanel();
		add(panel);
		
		panel = createResponsePanel();
		add(panel);
	}
	
	private JPanel createTurnControlPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 3));
		
		JLabel label = new JLabel("Whose turn?");
		JTextField whoseTurn = new JTextField();
		JButton nextPlayer = new JButton("Next player");
		nextPlayer.setPreferredSize(new Dimension(100, 50));
		JButton makeAccusation = new JButton("Make an accusation");
		
		panel.add(label);
		panel.add(whoseTurn);
		panel.add(nextPlayer);
		panel.add(makeAccusation);
		
		return panel;
	}
	
	private JPanel createDiePanel(){
		JPanel panel = new JPanel();
		
		JTextField roll = new JTextField("Roll");
		roll.setEditable(false);
		
		panel.add(roll);

		panel.setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		return panel;
	}
	
	private JPanel createGuessPanel(){
		JPanel panel = new JPanel();
		
		JTextField guess = new JTextField("Guess");
		
		panel.add(guess);
		
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		return panel;
	}
	
	private JPanel createResponsePanel(){
		JPanel panel = new JPanel();
		
		JTextField response = new JTextField("Response");
		response.setEditable(false);
		
		panel.add(response);

		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		return panel;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Control");
		frame.setSize(500,160);
		
		ControlGUI gui = new ControlGUI();
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
	}

}
