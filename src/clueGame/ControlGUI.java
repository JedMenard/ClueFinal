package clueGame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private static int roll;
	
	
	public ControlGUI(ClueGame game){
		JPanel panel = createTurnControlPanel(game);
		add(panel);
		
		panel = createDiePanel();
		add(panel);
		
		panel = createGuessPanel();
		add(panel);
		
		panel = createResponsePanel();
		add(panel);

	}
	
	private JPanel createTurnControlPanel(ClueGame game){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 3));
		
		JLabel label = new JLabel("Whose turn?");
		JTextField whoseTurn = new JTextField();
		whoseTurn.setEditable(false);
		JButton nextPlayer = new JButton("Next player");
		nextPlayer.setPreferredSize(new Dimension(100, 50));
		nextPlayer.addActionListener(new ButtonListener(game));
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

	public class ButtonListener implements ActionListener{
		ClueGame game;
		
		ButtonListener(ClueGame game){
			this.game = game;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			switch (e.getActionCommand()) {
			case "Next player":
				game.handleRounds();
				game.repaint();
				break;

			case "Make an accusation":
				//TODO: Make an accusation
				break;
				
			default:
				break;
			}			
		}
	}
	
	public static int roll(){
		Random rand = new Random();
		roll = rand.nextInt(6) + 1;
		
		return roll;
	}
	
	public void updatePlayer(Player p){
		
	}
	
	public void updateRoll(int r){
		
	}
	
	public void ourRepaint(){
		repaint();
	}
}
