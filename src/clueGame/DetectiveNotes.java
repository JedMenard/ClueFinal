package clueGame;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JDialog {


	public DetectiveNotes(){
		setLayout(new GridLayout(3, 2));

		add(addPeople());
		add(addPersonGuess());
		add(addRooms());
		add(addRoomGuess());
		add(addWeapons());
		add(addWeaponGuess());

	}

	private JPanel addPeople(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		for (Player p : Board.players){
			JCheckBox b = new JCheckBox(p.getPlayerName());
			panel.add(b);
		}

		panel.setBorder(new TitledBorder(new EtchedBorder(), "People"));

		return panel;
	}
	private JPanel addPersonGuess(){
		JPanel panel = new JPanel();

		JComboBox<String> people = new JComboBox<String>();

		for (Player p : Board.players){
			people.addItem(p.getPlayerName());
		}

		panel.setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		panel.add(people);
		return panel;
	}

	private JPanel addRooms(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		for (char c : Board.roomNames.keySet()){
			if (c != 'W' && c != 'X'){
				JCheckBox b = new JCheckBox(Board.roomNames.get(c));
				panel.add(b);
			}
		}
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		return panel;
	}
	private JPanel addRoomGuess(){
		JPanel panel = new JPanel();

		JComboBox<String> rooms = new JComboBox<String>();

		for (char c : Board.roomNames.keySet()){
			rooms.addItem(Board.roomNames.get(c));
		}

		panel.setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		panel.add(rooms);
		return panel;
	}

	private JPanel addWeapons(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		for (Card c : Board.weaponCards){
			JCheckBox b = new JCheckBox(c.getName());
			panel.add(b);
		}
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		return panel;
	}
	private JPanel addWeaponGuess(){
		JPanel panel = new JPanel();

		JComboBox<String> rooms = new JComboBox<String>();

		for (Card c : Board.weaponCards){
			rooms.addItem(c.getName());
		}

		panel.setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));
		panel.add(rooms);
		return panel;
	}
}
