package clueTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Solution;

public class AccusationTests {

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
	public void checkAccusation() {
		// Makes and sets a solution as the correct solution
		Solution solution = new Solution("test1", "test2", "test3");
		board.setTheAnswer(solution);
		
		// Tests the correct solution
		assertTrue(board.checkAccustaion(solution));
		
		// Makes and tests with an incorrect person
		Solution falseSolution = new Solution("false", "test2", "test3");
		assertFalse(board.checkAccustaion(falseSolution));
		
		// Makes and tests with an incorrect room
		falseSolution = new Solution("test1", "false", "test3");
		assertFalse(board.checkAccustaion(falseSolution));
		
		// Makes and tests with an incorrect weapon
		falseSolution = new Solution("test1", "test2", "false");
		assertFalse(board.checkAccustaion(falseSolution));
		
		// Makes and tests with all three incorrect
		falseSolution = new Solution("false", "false", "false");
		assertFalse(board.checkAccustaion(falseSolution));
	}

}
