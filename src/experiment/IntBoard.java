package experiment;
import java.util.*;

import clueGame.BoardCell;

public class IntBoard {
	
	private int width, height;
	private BoardCell[][] board;
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;

	public IntBoard(int w, int h) {
		width = w;
		height = h;
		board = new BoardCell[width][height];
		adjMtx = new HashMap<BoardCell,LinkedList<BoardCell>>();
		
		for(int r = 0 ; r < height; r++){
			for (int c = 0; c < width; c++){
				board[r][c] = new BoardCell(r,c);
			}
		}
		
		for(int r = 0 ; r < height; r++){
			for (int c = 0; c < width; c++){
				calcAdjacencies(board[r][c]);
			}
		}
		
		
	}
	
	public void calcAdjacencies(BoardCell b){
		
		LinkedList<BoardCell> l = new LinkedList<BoardCell>();
		
		if(b.getCol() - 1 >= 0){
			l.add(board[b.getRow()][b.getCol() - 1]);
			//System.out.println("adj test case 1");
		}
		if(b.getCol() + 1 < width){
			l.add(board[b.getRow()][b.getCol() + 1]);
			//System.out.println("adj test case 2");
		}
		if(b.getRow() - 1 >= 0){
			l.add(board[b.getRow() - 1][b.getCol()]);
			//System.out.println("adj test case 3");
		}
		if(b.getRow() + 1 < height){
			l.add(board[b.getRow() + 1][b.getCol()]);
			//System.out.println("adj test case 4");
		}
		
		adjMtx.put(b, l);
	}
	
	public Set<BoardCell> getTargets(){
		return targets;
	}
	
	public LinkedList<BoardCell> getAdjList(BoardCell b){
		return adjMtx.get(b);
	}
	
	public BoardCell getCell(int r, int c){
		return board[r][c];
		
	}
	
	public void calcTargets(BoardCell cell, int s){
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		getAllTargets(cell, s);
		if(targets.contains(cell)) targets.remove(cell);
	}
	
	public void getAllTargets(BoardCell cell, int s) {
		if(s==0){
			if (!targets.contains(cell)) 
				targets.add(cell);
			//visited.add(cell);
		}
		else{
			visited.add(cell);
			getAdjList(cell);
			for(BoardCell c : adjMtx.get(cell)){
				if(!visited.contains(c)){
					getAllTargets(c, s-1);
				}
			}
			visited.clear();
		}
	}

}
