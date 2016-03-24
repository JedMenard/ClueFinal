
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
	static Board gameBoard;
	ComputerPlayer cPlayer = new ComputerPlayer();
	
	@BeforeClass
	public static void load() {
		gameBoard = new Board();
		gameBoard.initialize();
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
			sampleCells.add(cell);
		}
				
		
		// make sure that the computer picks the room every time
		for(int i = 0; i < 10; i ++) {
			assertEquals(true, cPlayer.pickLocation(sampleCells) == roomCell);
		}
		System.out.println();
	}
	
	@Test
	public void randomChoiceTest() {
		gameBoard.calcTargets(5, 1, 1);
		boolean loc_4_1 = false;
		boolean loc_5_0 = false;
		boolean loc_5_2 = false;
		
		// run the test 100 times
		for (int i=0; i<100; i++) {
			BoardCell selected = cPlayer.pickLocation(gameBoard.getTargets());
			if (selected == gameBoard.getCellAt(4, 1))
				loc_4_1 = true;
			else if (selected == gameBoard.getCellAt(5, 0))
				loc_5_0 = true;
			else if (selected == gameBoard.getCellAt(5, 2))
				loc_5_2 = true;
			else
				fail("Invalid target selected");
		}
		// ensure each target was selected at least once
		assertTrue(loc_4_1);
		assertTrue(loc_5_0);
		assertTrue(loc_5_2);	
	}
}
