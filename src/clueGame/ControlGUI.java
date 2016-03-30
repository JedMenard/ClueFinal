package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComponent;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlGUI extends JPanel {
	
	private JTextField name;
	DrawPanel drawPanel;
	
	public ControlGUI(){
		JPanel panel = createTurnControlPanel();
		add(panel);
		
		panel = createDiePanel();
		add(panel);
		
		panel = createGuessPanel();
		add(panel);
		
		panel = createResponsePanel();
		add(panel);
		
		createDrawPanel();
		add(drawPanel,BorderLayout.CENTER);
	}
	
	private JPanel createTurnControlPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 3));
		
		JLabel label = new JLabel("Whose turn?");
		JTextField whoseTurn = new JTextField();
		whoseTurn.setEditable(false);
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
		
		JTextField roll = new JTextField();
		roll.setEditable(false);		
		roll.setPreferredSize(new Dimension(100,30));
		
		panel.add(roll);

		panel.setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		return panel;
	}
	
	private JPanel createGuessPanel(){
		JPanel panel = new JPanel();
		
		JTextField guess = new JTextField();
		guess.setEditable(false);
		guess.setPreferredSize(new Dimension(100,30));
		
		panel.add(guess);
		
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		return panel;
	}
	
	private JPanel createResponsePanel(){
		JPanel panel = new JPanel();
		
		JTextField response = new JTextField();
		response.setEditable(false);
		response.setPreferredSize(new Dimension(100,30));
		
		panel.add(response);

		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		return panel;
	}
	
	private void createDrawPanel() {
		drawPanel = new DrawPanel();
		drawPanel.setSize(100,100);
		drawPanel.setVisible(true);
		
	}

}
