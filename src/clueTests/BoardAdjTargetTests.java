package clueTests;

import java.util.LinkedList;
import java.util.Set;


//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private Board board;
	@Before
	public void setUp() {
		board = new Board("Layout.csv", "Legend.txt");
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		LinkedList<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(3, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(6, 0);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(15, 20);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(4, 20);
		assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(21, 22);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		LinkedList<BoardCell> testList = board.getAdjList(1, 2);
		//System.out.println(testList.size());
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(1, 3)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(2, 16);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(2, 15)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(3, 1);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(4, 1)));
		//TEST DOORWAY UP
		testList = board.getAdjList(15, 2);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(14, 2)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		LinkedList<BoardCell> testList = board.getAdjList(1, 3);
		assertTrue(testList.contains(board.getCellAt(1, 2)));
		assertTrue(testList.contains(board.getCellAt(1, 4)));
		assertTrue(testList.contains(board.getCellAt(0, 3)));
		assertTrue(testList.contains(board.getCellAt(2, 3)));
		assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(5, 10);
		assertTrue(testList.contains(board.getCellAt(5, 11)));
		assertTrue(testList.contains(board.getCellAt(5, 9)));
		assertTrue(testList.contains(board.getCellAt(4, 10)));
		assertTrue(testList.contains(board.getCellAt(6, 10)));
		assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(17, 15);
		assertTrue(testList.contains(board.getCellAt(16, 15)));
		assertTrue(testList.contains(board.getCellAt(18, 15)));
		assertTrue(testList.contains(board.getCellAt(17, 16)));
		assertEquals(3, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(14, 10);
		assertTrue(testList.contains(board.getCellAt(14, 11)));
		assertTrue(testList.contains(board.getCellAt(14, 9)));
		assertTrue(testList.contains(board.getCellAt(13, 10)));
		assertTrue(testList.contains(board.getCellAt(15, 10)));
		assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are PINK on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board
		LinkedList<BoardCell> testList = board.getAdjList(0, 4);
		assertTrue(testList.contains(board.getCellAt(0, 3)));
		assertTrue(testList.contains(board.getCellAt(1, 4)));
		assertEquals(2, testList.size());
		
		// Test on left edge of board, two walkway pieces
		testList = board.getAdjList(5, 0);
		assertTrue(testList.contains(board.getCellAt(4, 0)));
		assertTrue(testList.contains(board.getCellAt(5, 1)));
		assertEquals(2, testList.size());

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(9, 15);
		assertTrue(testList.contains(board.getCellAt(8, 15)));
		assertTrue(testList.contains(board.getCellAt(10, 15)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(13,7);
		assertTrue(testList.contains(board.getCellAt(12, 7)));
		assertTrue(testList.contains(board.getCellAt(14, 7)));
		assertTrue(testList.contains(board.getCellAt(13, 6)));
		assertTrue(testList.contains(board.getCellAt(13, 8)));
		assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(21, 15);
		assertTrue(testList.contains(board.getCellAt(20, 15)));
		assertEquals(1, testList.size());
		
		// Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(10, 22);
		assertTrue(testList.contains(board.getCellAt(10, 21)));
		assertEquals(1, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(11, 6);
		assertTrue(testList.contains(board.getCellAt(10, 6)));
		assertTrue(testList.contains(board.getCellAt(12, 6)));
		assertTrue(testList.contains(board.getCellAt(11, 7)));
		//System.out.println(testList.size());
		assertEquals(3, testList.size());
	}
	
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(21, 7, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 7)));
		assertTrue(targets.contains(board.getCellAt(21, 6)));	
		
		board.calcTargets(14, 0, 1);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 1)));
		assertTrue(targets.contains(board.getCellAt(13, 0)));			
	}
	
	// Tests of just walkways, 2 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(21, 7, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(19, 7)));
		assertTrue(targets.contains(board.getCellAt(20, 6)));
		
		board.calcTargets(14, 0, 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(13, 1)));
		assertTrue(targets.contains(board.getCellAt(14, 2)));
	}
	
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(21, 7, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 7)));
		assertTrue(targets.contains(board.getCellAt(19, 5)));
		assertTrue(targets.contains(board.getCellAt(19, 7)));
		assertTrue(targets.contains(board.getCellAt(18, 6)));
		assertTrue(targets.contains(board.getCellAt(20, 6)));
		
		board.calcTargets(14, 0, 4);
		targets= board.getTargets();
		assertTrue(targets.contains(board.getCellAt(14, 4)));
		assertTrue(targets.contains(board.getCellAt(13, 3)));	
		assertTrue(targets.contains(board.getCellAt(14, 2)));	
		assertTrue(targets.contains(board.getCellAt(13, 1)));
		assertTrue(targets.contains(board.getCellAt(15, 2)));
	}	
	
	// Tests of just walkways plus one door, 6 steps
	// These are LIGHT BLUE on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(14, 0, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 6)));
		assertTrue(targets.contains(board.getCellAt(13, 5)));	
		assertTrue(targets.contains(board.getCellAt(13, 3)));	
		assertTrue(targets.contains(board.getCellAt(14, 4)));	
		assertTrue(targets.contains(board.getCellAt(13, 1)));	
		assertTrue(targets.contains(board.getCellAt(14, 2)));	
		assertTrue(targets.contains(board.getCellAt(15, 2)));	
	}	
	
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		board.calcTargets(6, 20, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 21)));
		assertTrue(targets.contains(board.getCellAt(6, 19)));
		assertTrue(targets.contains(board.getCellAt(5, 20)));
		assertTrue(targets.contains(board.getCellAt(5, 22)));
		assertTrue(targets.contains(board.getCellAt(4, 19)));
		assertTrue(targets.contains(board.getCellAt(5, 18)));
		assertTrue(targets.contains(board.getCellAt(6, 17)));
	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(2, 16, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(2, 15)));
		
		// Take two steps
		board.calcTargets(2, 16, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(1, 15)));
		assertTrue(targets.contains(board.getCellAt(2, 14)));
		assertTrue(targets.contains(board.getCellAt(3, 15)));
	}

}
