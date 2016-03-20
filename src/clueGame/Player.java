package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Player {
	private String playerName;
	private int row, column;	
	private Color color;
	private Set<Card> myCards;
	private static Set<Card> seenCards;
		
	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}
	
	public Player() {
		setPlayerName("The Cook");
		setRow(0); setColumn(0);
		setColor(Color.BLUE);
		myCards = new HashSet<Card>();
		seenCards = new HashSet<Card>();
	}


	public void AddCard(Card c) {
		myCards.add(c);
	}
	
	//for test
	public Set<Card> GetMyCards() {
		return myCards;
	}
	
	public void SeeCard(Card c){
		seenCards.add(c);
	}
	
	public void setLocation(int r, int c){
		setRow(r);
		setColumn(c);
	}

	public static ArrayList<Player> loadPlayersFromFile(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
