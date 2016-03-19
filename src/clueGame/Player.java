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
	private Set<Card> seenCards;
		
	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}
	
	public Player() {
		playerName = "The Cook";
		row = 0; column = 0;
		color = Color.BLUE;
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
}
