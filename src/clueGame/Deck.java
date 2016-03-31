package clueGame;

import java.util.ArrayList;
import java.util.Random;

public class Deck {

	//This keeps track of what cards haven't been dealt
	public static ArrayList<Card> deck = new ArrayList<Card>();
	
	//This deck holds all possible cards in the game 
	public static ArrayList<Card> allCards = new ArrayList<Card>();
	
	public Deck() {
		ArrayList<Card> deck = new ArrayList<Card>();
	}
	
	public Deck(ArrayList<Card> deck) {
		 this.deck = deck;
		 allCards = deck;
	}

	//Adds card to both lists
	public void add(Card c) { allCards.add(c); deck.add(c); }
	
	//Return card at index from all possible cards.
	public Card get(int i) { return allCards.get(i); }
	
	//Return a random card from all possible cards.
	public Card getRand() { 
		Random r = new Random();
		if (allCards.size()>0) return allCards.get(r.nextInt(allCards.size()));
		else return null;
	}
	
	//Return a random card and then remove it from the deck
	public Card draw() { 
		Random r = new Random();
		if (deck.size()>0) {
			int x = r.nextInt(deck.size());
			Card temp = deck.get(x);
			deck.remove(x);
			return temp;
		}
		else return null;
	}
	
	//Checks if deck is empty
	public boolean empty() {
		if (deck.size() > 0) return false;
		return true;
	}
	
	//Removes specified card from the deck
	public void del(Card c) {
		for (int i=0; i<deck.size(); i++) {
			if (deck.get(i).getName() == c.getName()) {
				deck.remove(i);
				break;
			}			
		}
	}

	public boolean contains(Card c) {
		for (Card k: allCards) {
			if (k.getName() == c.getName()) return true;			
		}
		return false;
	}
	
}
