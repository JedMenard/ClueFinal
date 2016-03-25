package clueTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class DisprovingSuggestionTest {
	static Card scarlet, mustard, plum;
	static Card knife, revolver, leadpipe;
	static Card venus, mercury, jupiter;
	static Board board;
	@BeforeClass
	public static void setUp(){

		
		// Initializing the people cards
		scarlet = new Card("Miss Scarlet", CardType.PERSON);
		mustard = new Card("Colonel Mustard", CardType.PERSON);
		plum = new Card("Professor Plum", CardType.PERSON);
		
		// Initializing the weapon cards
		knife = new Card("Knife", CardType.WEAPON);
		revolver = new Card("Revolver", CardType.WEAPON);
		leadpipe = new Card("Leadpipe", CardType.WEAPON);

		// Initializing the room cards
		venus = new Card("Venus", CardType.ROOM);
		mercury = new Card("Mercury", CardType.ROOM);
		jupiter = new Card("Jupiter", CardType.ROOM);
		
		//Initializing the board
		board = new Board();
		board.initialize();
		
	}

	@Test
	public void testOnePlayerOneMatch() {
		Player p1 = new Player();
		p1.AddCard(scarlet);
		p1.AddCard(mustard);
		p1.AddCard(revolver);
		p1.AddCard(knife);
		p1.AddCard(venus);
		p1.AddCard(mercury);
		
		// Tests that the player returns the person
		Solution suggestion = new Solution(scarlet, jupiter, leadpipe);
		assertEquals(scarlet, p1.disproveSuggestion(suggestion));
		
		// Tests that the player returns the room
		suggestion = new Solution(plum, venus, leadpipe);
		assertEquals(venus, p1.disproveSuggestion(suggestion));
		
		// Tests that the player returns the weapon
		suggestion = new Solution(plum, jupiter, knife);
		assertEquals(knife, p1.disproveSuggestion(suggestion));
		
		// Tests that the player returns null
		suggestion = new Solution(plum, jupiter, leadpipe);
		assertEquals(null, p1.disproveSuggestion(suggestion));
	}
	
	@Test
	public void testOnePlayerMultipleMatches(){
		// Setting up the player and the solution
		Player p1 = new Player();
		p1.AddCard(plum);
		p1.AddCard(leadpipe);
		p1.AddCard(jupiter);
		Solution suggestion = new Solution(plum, jupiter, leadpipe);
		
		// Booleans to keep track of which cards have been found
		boolean pFound, lFound, jFound;
		pFound = lFound = jFound = false;
		
		// Entering a loop to account for the random factor
		for (int i = 0; i < 10000; i++){
			Card returned = p1.disproveSuggestion(suggestion);
			
			// Making sure the returned card is not a card the player doesn't have
			assertTrue(returned == plum || returned == leadpipe || returned == jupiter);
			
			// Keeping track of which cards have been found
			if(returned == plum) pFound = true;
			else if(returned == leadpipe) lFound = true;
			else if(returned == jupiter) jFound = true;
			
			// Exiting once all three have been found
			// No sense in doing all 10,000 loops if not necessary
			if (pFound == true && lFound == true && jFound == true) break;
		}
		
		// Asserting all three solutions have been found
		assertTrue(pFound);
		assertTrue(jFound);
		assertTrue(lFound);
	}

	
	@Test
	public void testAllQueried(){
		// Initializing the players
		ComputerPlayer cpu1 = new ComputerPlayer("cpu1");
		ComputerPlayer cpu2 = new ComputerPlayer("cpu2");
		ComputerPlayer cpu3 = new ComputerPlayer("cpu3");
		HumanPlayer p1 = new HumanPlayer("p1");
		
		// Giving them cards
		cpu1.AddCard(scarlet);
		cpu2.AddCard(mercury);
		cpu3.AddCard(revolver);
		p1.AddCard(plum);
		
		// Putting the players in the game
		board.resetPlayers();
		board.players.add(cpu1);
		board.players.add(cpu2);
		board.players.add(cpu3);
		board.players.add(p1);
		
		// Testing that no players can disprove the suggestion
		Solution suggestion = new Solution(mustard, venus, leadpipe);
		assertEquals(null, board.handleSuggestion(suggestion, cpu1, board.getCellAt(0, 0)));
		
		// Testing that the human is the one who can disprove
		suggestion = new Solution(plum, venus, leadpipe);
		assertEquals(plum, board.handleSuggestion(suggestion, cpu1, board.getCellAt(0, 0)));
		
		// Testing that the human suggesting does not disprove
		assertEquals(null, board.handleSuggestion(suggestion, p1, board.getCellAt(0, 0)));
		
		// Testing that the cpu suggesting does not disprove
		suggestion = new Solution(scarlet, venus, leadpipe);
		assertEquals(null, board.handleSuggestion(suggestion, cpu1, board.getCellAt(0, 0)));
		
		// Testing that the first person who can disprove does
		suggestion = new Solution(mustard, mercury, revolver);
		assertEquals(mercury, board.handleSuggestion(suggestion, cpu1, board.getCellAt(0, 0)));
		
		// Testing that the farthest person can disprove
		suggestion = new Solution(mustard, venus, revolver);
		assertEquals(revolver, board.handleSuggestion(suggestion, p1, board.getCellAt(0, 0)));

	}
}
