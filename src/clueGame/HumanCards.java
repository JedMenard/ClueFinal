package clueGame;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class HumanCards extends JPanel{
	private static final long serialVersionUID = 1L;

	HumanCards(){
		this.setLayout(new GridLayout(0, 1));
		setBorder(new TitledBorder(new EtchedBorder(), "My Cards"));
		
		add(createPeople());
		add(createRooms());
		add(createWeapons());

	}

	private Component createWeapons() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		panel.setLayout(new GridLayout(0, 1));

		for (Card c : Board.players.get(0).GetMyCards()){
			if (c.getType() == CardType.WEAPON){
				JTextField card = new JTextField();
				card.setEditable(false);
				card.setText(c.getName());
				panel.add(card);
			}
		}
		return panel;
	}

	private Component createRooms() {		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		panel.setLayout(new GridLayout(0, 1));

		for (Card c : Board.players.get(0).GetMyCards()){
			if (c.getType() == CardType.ROOM){
				JTextField card = new JTextField();
				card.setEditable(false);
				card.setText(c.getName());
				panel.add(card);
			}
		}
		return panel;
	}

	private JPanel createPeople(){
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		panel.setLayout(new GridLayout(0, 1));
		
		for (Card c : Board.players.get(0).GetMyCards()){
			if (c.getType() == CardType.PERSON){
				JTextField card = new JTextField();
				card.setEditable(false);
				card.setText(c.getName());
				panel.add(card);
			}
		}
		return panel;
	}
}
