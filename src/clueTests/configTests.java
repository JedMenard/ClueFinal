package clueTests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;


public class configTests {

	// Constants that I will use to test whether the file was loaded correctly
		public static final int NUM_ROOMS = 11;
		public static final int NUM_ROWS = 22;
		public static final int NUM_COLUMNS = 23;

		// NOTE: I made Board static because I only want to set it up one 
		// time (using @BeforeClass), no need to do setup before each test.
		// The methods that test invalid config files will have a local 
		// Board variable, so will not use this
		private static Board board;
		
		@BeforeClass
		public static void setUp() {
			// Create a new Board using the valid files. Note that
			// the default filenames must be attributes of the Board class. 
			board = new Board();
			// Initialize will load BOTH config files 
			board.initialize();
		}
		@Test
		public void testRooms() {
			// rooms is static, see discussion in lab writeup
			Map<Character, String> rooms = Board.getRooms();
			// Ensure we read the correct number of rooms
			assertEquals(NUM_ROOMS, rooms.size());
			// To ensure data is correctly loaded, test retrieving a few rooms 
			// from the hash, including the first and last in the file and a few others
			assertEquals("Earth", rooms.get('E'));
			assertEquals("Sun", rooms.get('X'));
			assertEquals("Darkness", rooms.get('W'));
			assertEquals("Pluto", rooms.get('P'));
			assertEquals("Venus", rooms.get('V'));
		}
		
		@Test
		public void testBoardDimensions() {
			// Ensure we have the proper number of rows and columns
			assertEquals(NUM_ROWS, board.getNumRows());
			assertEquals(NUM_COLUMNS, board.getNumColumns());		
		}
		
		// Test a doorway in each direction, plus two cells that are not
		// a doorway.
		// These cells are white on the planning spreadsheet
		@Test
		public void FourDoorDirections() {
			// Test one each RIGHT/LEFT/UP/DOWN
			BoardCell room = board.getCellAt(1, 2);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
			room = board.getCellAt(3, 1);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.DOWN, room.getDoorDirection());
			room = board.getCellAt(2, 5);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.LEFT, room.getDoorDirection());
			room = board.getCellAt(15, 2);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.UP, room.getDoorDirection());
			// Test that room pieces that aren't doors know it
			room = board.getCellAt(14, 21);
			assertFalse(room.isDoorway());	
			// Test that walkways are not doors
			BoardCell cell = board.getCellAt(14, 13);
			assertFalse(cell.isDoorway());		

		}
		
		// Test that we have the correct number of doors
		@Test
		public void testNumberOfDoorways() 
		{
			int numDoors = 0;
			int totalCells = board.getNumColumns() * board.getNumRows();
			Assert.assertEquals(506, totalCells);
			for (int row=0; row<board.getNumRows(); row++)
				for (int col=0; col<board.getNumColumns(); col++) {
					BoardCell cell = board.getCellAt(row, col);
					if (cell.isDoorway())
						numDoors++;
				}
			//System.out.println(numDoors);
			Assert.assertEquals(16, numDoors);
		}

		// Test a few room cells to ensure the room initial is correct.
		@Test
		public void testRoomInitials() {
			assertEquals('E', board.getCellAt(0, 0).getInitial());
			assertEquals('S', board.getCellAt(4, 8).getInitial());
			assertEquals('V', board.getCellAt(9, 0).getInitial());
			assertEquals('U', board.getCellAt(21, 22).getInitial());
			assertEquals('J', board.getCellAt(21, 0).getInitial());
		}
		
		// Test that an exception is thrown for a bad config file
		@Test (expected = BadConfigFormatException.class)
		public void testBadColumns() throws BadConfigFormatException, FileNotFoundException {
			// overloaded Game ctor takes config file names. See discussion of 
			// testing exceptions in lab writeup. 
			// Note that we are using a LOCAL Board variable, not the static one 
			// set up by @BeforeClass
			Board board = new Board("LayoutBadColumns.csv", "Legend.txt");
			// Instead of initialize, we call the two load functions directly
			board.loadRoomConfig();
			// This one should throw an exception
			board.loadBoardConfig();
		}
		// Test that an exception is thrown for a bad config file
		@Test (expected = BadConfigFormatException.class)
		public void testBadRoom() throws BadConfigFormatException, FileNotFoundException {
			Board board = new Board("LayoutBadRoom.csv", "Legend.txt");
			board.loadRoomConfig();
			board.loadBoardConfig();
		}
		// Test that an exception is thrown for a bad room config file
		@Test (expected = BadConfigFormatException.class)
		public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
			Board board = new Board("Layout.csv", "LegendBadFormat.txt");
			board.loadRoomConfig();
		}
}
