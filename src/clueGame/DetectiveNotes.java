package clueGame;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JDialog;
import javax.swing.JPanel;

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
		return null;
		
	}
	private JPanel addPersonGuess(){
		return null;
	}
	private JPanel addRooms(){
		return null;
	}
	private JPanel addRoomGuess(){
		return null;
	}
	private JPanel addWeapons(){
		return null;
	}
	private JPanel addWeaponGuess(){
		return null;
	}
}
