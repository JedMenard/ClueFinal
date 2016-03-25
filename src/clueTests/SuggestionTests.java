package clueTests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Solution;

public class SuggestionTests {

	private static Board board;
	
	@BeforeClass
	public static void setUp(){
		// Create a new Board using the valid files. Note that
		// the default filenames must be attributes of the Board class. 
		board = new Board();
		// Initialize will load BOTH config files 
		board.initialize();
	}



	@Test
	public void makeSuggestionTest(){
		// Setting up the computer player's position and what cards have been seen
		ComputerPlayer comp = new ComputerPlayer();
		comp.SeeCard(new Card("Colonel Mustard", CardType.PERSON));
		comp.SeeCard(new Card("Revolver", CardType.WEAPON));
		comp.setLocation(9, 5);
		
		// Testing 100 times to account for random factor
		for(int i = 0; i < 100; i++){
			// Making the suggestion
			Solution suggestion = comp.makeSuggestion(board, board.getCellAt(9, 5));
			
			// Asserting that the room should be Venus, and that
			// the weapon should not be the gun and 
			// the person should not be Colonel Mustard
			assertNotEquals("Colonel Mustard", suggestion.person);
			assertEquals("Venus", suggestion.room);
			assertNotEquals("Revolver", suggestion.weapon);
		}
		
	}
	
}
