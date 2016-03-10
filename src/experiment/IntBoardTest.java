package experiment;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import clueGame.BoardCell;

public class IntBoardTest {
	
	IntBoard ib;
	
	@Before
	public void setup(){
		ib = new IntBoard(4,4);
	}

	@Test //top left
	public void testAdjacency0() {
		BoardCell cell = ib.getCell(0,0);
		LinkedList<BoardCell> testList = ib.getAdjList(cell);
		assertTrue(testList.contains(ib.getCell(1, 0)));
		assertTrue(testList.contains(ib.getCell(0, 1)));
		assertEquals(2, testList.size());
	}
	
	@Test //bottom right
	public void testAdjacency1() {
		BoardCell cell = ib.getCell(3,3);
		LinkedList<BoardCell> testList = ib.getAdjList(cell);
		assertTrue(testList.contains(ib.getCell(3, 2)));
		assertTrue(testList.contains(ib.getCell(2, 3)));
		assertEquals(2, testList.size());
	}
	
	@Test //left edge
	public void testAdjacency2() {
		BoardCell cell = ib.getCell(2,0);
		LinkedList<BoardCell> testList = ib.getAdjList(cell);
		assertTrue(testList.contains(ib.getCell(1, 0)));
		assertTrue(testList.contains(ib.getCell(2, 1)));
		assertTrue(testList.contains(ib.getCell(3, 0)));
		assertEquals(3, testList.size());
	}

	@Test //right edge
	public void testAdjacency3() {
		BoardCell cell = ib.getCell(1,3);
		LinkedList<BoardCell> testList = ib.getAdjList(cell);
		assertTrue(testList.contains(ib.getCell(0, 3)));
		assertTrue(testList.contains(ib.getCell(1, 2)));
		assertTrue(testList.contains(ib.getCell(2, 3)));
		assertEquals(3, testList.size());
	}
	
	@Test //[1][1]
	public void testAdjacency4() {
		BoardCell cell = ib.getCell(1,1);
		LinkedList<BoardCell> testList = ib.getAdjList(cell);
		assertTrue(testList.contains(ib.getCell(1, 0)));
		assertTrue(testList.contains(ib.getCell(0, 1)));
		assertTrue(testList.contains(ib.getCell(1, 2)));
		assertTrue(testList.contains(ib.getCell(2, 1)));
		assertEquals(4, testList.size());
	}
	@Test //[2][2]
	public void testAdjacency5() {
		BoardCell cell = ib.getCell(2,2);
		LinkedList<BoardCell> testList = ib.getAdjList(cell);
		assertTrue(testList.contains(ib.getCell(2, 1)));
		assertTrue(testList.contains(ib.getCell(1, 2)));
		assertTrue(testList.contains(ib.getCell(2, 3)));
		assertTrue(testList.contains(ib.getCell(3, 2)));
		assertEquals(4, testList.size());
	}
	
	@Test
	public void testTargets0_3()
	{
		BoardCell cell = ib.getCell(0, 0);
		ib.calcTargets(cell, 3);
		Set<BoardCell> targets = ib.getTargets();
		System.out.println("4 - " + targets.size());
		for(BoardCell c : targets){
			System.out.println(c);
		}
		assertEquals(6, targets.size());
		assertTrue(targets.contains(ib.getCell(3, 0)));
		assertTrue(targets.contains(ib.getCell(2, 1)));
		assertTrue(targets.contains(ib.getCell(0, 1)));
		assertTrue(targets.contains(ib.getCell(1, 2)));
		assertTrue(targets.contains(ib.getCell(0, 3)));
		assertTrue(targets.contains(ib.getCell(1, 0)));
	}
	
	@Test
	public void testTargets0_2()
	{
		BoardCell cell = ib.getCell(0, 0);
		ib.calcTargets(cell, 2);
		Set<BoardCell> targets = ib.getTargets();
		System.out.println("1 - " + targets.size());
		for(BoardCell c : targets){
			System.out.println(c);
		}
		assertEquals(3, targets.size());
		assertTrue(targets.contains(ib.getCell(2, 0)));
		assertTrue(targets.contains(ib.getCell(1, 1)));
		assertTrue(targets.contains(ib.getCell(0, 2)));
	}
	
	@Test
	public void testTargets0_1()
	{
		BoardCell cell = ib.getCell(0, 0);
		ib.calcTargets(cell, 1);
		Set targets = ib.getTargets();
		System.out.println("2 - " + targets.size());
		assertEquals(2, targets.size());
		assertTrue(targets.contains(ib.getCell(1, 0)));
		assertTrue(targets.contains(ib.getCell(0, 1)));
	}
	
	@Test
	public void testTargets1_4()
	{
		BoardCell cell = ib.getCell(1, 1);
		ib.calcTargets(cell, 4);
		Set<BoardCell> targets = ib.getTargets();
		System.out.println("3 - " + targets.size());
		for(BoardCell c : targets){
			System.out.println(c);
		}
		assertEquals(7, targets.size());
		assertTrue(targets.contains(ib.getCell(0, 0)));
		assertTrue(targets.contains(ib.getCell(2, 0)));
		assertTrue(targets.contains(ib.getCell(0, 2)));
		assertTrue(targets.contains(ib.getCell(2, 2)));
		assertTrue(targets.contains(ib.getCell(1, 3)));
		assertTrue(targets.contains(ib.getCell(3, 3)));
		assertTrue(targets.contains(ib.getCell(3, 1)));
	}
	@Test
	
	public void testTargets1_1()
	{
		BoardCell cell = ib.getCell(1, 1);
		ib.calcTargets(cell, 1);
		Set targets = ib.getTargets();
		System.out.println("5 - " + targets.size());
		assertEquals(4, targets.size());
		assertTrue(targets.contains(ib.getCell(1, 0)));
		assertTrue(targets.contains(ib.getCell(0, 1)));
		assertTrue(targets.contains(ib.getCell(1, 2)));
		assertTrue(targets.contains(ib.getCell(2, 1)));
	}
	
	@Test
	public void testTargets2_0()
	{
		BoardCell cell = ib.getCell(1, 3);
		ib.calcTargets(cell, 1);
		Set targets = ib.getTargets();
		System.out.println("6 - " + targets.size());
		assertEquals(3, targets.size());
		assertTrue(targets.contains(ib.getCell(0, 3)));
		assertTrue(targets.contains(ib.getCell(1, 2)));
		assertTrue(targets.contains(ib.getCell(2, 3)));
	}
	
	
}
