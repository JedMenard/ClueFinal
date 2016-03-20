package clueTests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

public class SelectTargetLocationTests {
	Board gameBoard;
	ComputerPlayer cPlayer = new ComputerPlayer();
	
	@BeforeClass
	public void load() {
		gameBoard = new Board();
	}
	
	@Test
	public void functionDesignTest() {
		gameBoard.calcTargets(5, 5, 0);
		
		// test 100 times that the player doesnt pick the room when it was last in the room
		for(int i = 0; i < 100; i ++) {
			BoardCell target = cPlayer.pickLocation(gameBoard.getTargets());

			assertEquals(true, target != cPlayer.getLastCell());
		}
	}
	
	@Test
	public void roomPreferenceTest() {
		Set<BoardCell> sampleCells = new HashSet<BoardCell>();
		BoardCell roomCell = new BoardCell();
		
		// pick which cell will be the room when the cells are created
		Random rand = new Random();
		int roomNum = rand.nextInt(100);
		
		// create a set of targets with cells, one of them being a room cell
		for(int i = 0; i < 100; i ++) {
			BoardCell cell = new BoardCell();
			if(i == roomNum) {
				cell.setDoorDirection('R');
				roomCell = cell;
			}
		}
		
		// make sure that the computer picks the room every time
		for(int i = 0; i < 1000; i ++) {
			assertEquals(true, cPlayer.pickLocation(sampleCells) == roomCell);
		}
	}
	
	public void randomChoiceTest() {
		gameBoard.calcTargets(3, 0, 2);
		boolean loc_4_2 = false;
		boolean loc_3_4 = false;
		boolean loc_4_5 = false;
		
		// run the test 100 times
		for (int i=0; i<100; i++) {
			BoardCell selected = cPlayer.pickLocation(gameBoard.getTargets());
			if (selected == gameBoard.getCellAt(4, 2))
				loc_4_2 = true;
			else if (selected == gameBoard.getCellAt(3, 4))
				loc_3_4 = true;
			else if (selected == gameBoard.getCellAt(4, 5))
				loc_4_5 = true;
			else
				fail("Invalid target selected");
		}
		// ensure each target was selected at least once
		assertTrue(loc_4_2);
		assertTrue(loc_3_4);
		assertTrue(loc_4_5);	
	}
}
