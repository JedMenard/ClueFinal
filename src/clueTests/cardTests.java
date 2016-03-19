package clueTests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

public class cardTests {
	private static Board board;

	@BeforeClass
	public static void setUp() {
		// Create a new Board using the valid files. Note that
		// the default filenames must be attributes of the Board class. 
		board = new Board();
		// Initialize will load BOTH config files 
		board.initialize();
	}

	///////PART 1, LOADING THE CARDS///////////////////////
	@Test
	//Makes sure the deck is loaded to the appropriate amount
	//	i.e. 9 rooms, 6 characters, 6 weapons as mentioned in the rules, totaling to 21.
	public void testLoad() {
		assertEquals(board.deck.size(), 21);		
	}

	@Test
	//Makes sure the correct amount of each type of card are present in the deck
	public void testQuantity() {
		int rooms = 0, people = 0, weapons = 0;
		for (Card c: board.deck) {
			if (c.getType() == CardType.ROOM) rooms++;
			if (c.getType() == CardType.PERSON) people++;
			if (c.getType() == CardType.WEAPON) weapons++;
		}
		assertEquals(rooms,9);
		assertEquals(people,6);
		assertEquals(weapons,6);
	}

	@Test
	//Test specific cards are present in the deck.
	public void testSpecificCards() {
		Card card1 = new Card("Mercury", CardType.ROOM);
		Card card2 = new Card("The Cook", CardType.PERSON);
		Card card3 = new Card("Rope", CardType.WEAPON);
		int truths = 0;		
		for (Card c: board.deck) {
			if (c.equals(card1)) truths++;
			if (c.equals(card2)) truths++;
			if (c.equals(card3)) truths++;
		}
		assertEquals(truths,3);				
	}

	////////////PART 2, DEALING THE CARDS////////////////////////////////

	@Test
	//All cards are dealt
	public void testAllCardsDealt() {
		int truths = 0;
		for (Player p: board.players) {
			for (Card c: p.GetMyCards()) {
				if (board.deck.contains(c)) truths++;
			}
		}
		assertEquals(board.deck.size(),truths);
	}

	@Test
	//Makes sure all players have a roughly even distribution of total cards
	public void testSameCardAmount() {
		int truths = 0;		
		for (int i = 1; i < board.players.size(); i++) {
			if (Math.abs(board.players.get(0).GetMyCards().size() - board.players.get(i).GetMyCards().size()) <= 1) truths++;
		}		
		assertEquals(truths, 5);
	}
	
	@Test
	//No players have duplicate cards
	public void testUniqueHand() {
		int duplicates = 0;
		for (int i=0; i < board.players.size(); i++) {
			for (int k = i+1; k < board.players.size(); k++) {
				for (Card c: board.players.get(i).GetMyCards()) {
					for (Card c2: board.players.get(k).GetMyCards()) {
						if (c.equals(c2)) duplicates++;						
					}
				}
			}
		}
		assertEquals(duplicates, 0);
	}
}
