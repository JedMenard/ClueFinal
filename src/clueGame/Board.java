package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {

	public final int BOARD_SIZE = 50;
	public final int PLAYER_AMOUNT = 6;
	private static HashMap<Character, String> rooms;
	private BoardCell[][] board;
	private BoardCell[][] tempBoard;
	private int numRows;
	private int numCols;
	private String leg;
	private String lay;

	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;

	// 3/18
	public ArrayList<Card> deck;	
	public ArrayList<Player> players;	

	// 3/10
	private Solution theAnswer;

	public void loadConfigFiles(){}
	public void selectAnswer(){}
	public Card handleSuggestion(Solution suggestion, Player accusingPlayer, BoardCell clicked) {
		return null;
	}
	public boolean checkAccustaion(Solution accusation){
		if (accusation == theAnswer) return true;
		else return false;
	}

	public Board() {
		leg = "Legend.txt";
		lay = "Layout.csv";		
	}

	public Board(String string, String string2) {
		lay = string;
		leg = string2;		
	}

	public void initialize(){
		rooms = new HashMap<Character, String>();
		adjMtx = new HashMap<BoardCell,LinkedList<BoardCell>>();

		try {
			loadRoomConfig();
			loadBoardConfig();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (BadConfigFormatException r) {
			try {
				FileWriter fout = new FileWriter("BadConfigLog.txt");
				fout.write("Error in Configuration File.");
				fout.flush();
				fout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		for(int r = 0 ; r < numRows; r++){
			for (int c = 0; c < numCols; c++){
				calcAdjacencies(board[r][c]);
			}
		}

		// 3/18////////////////////////////////////////////////////

		//This is where the board tries to create the deck
		try { loadCards(); }
		catch (FileNotFoundException e) {
			System.out.println("Can't find card file");
		}

		//Creates 1 human player and 5 computer players and distributes cards.
		players = new ArrayList<Player>(); 
		HumanPlayer human = new HumanPlayer();
		players.add(human);
		while (players.size() < PLAYER_AMOUNT) {
			ComputerPlayer cp = new ComputerPlayer();
			players.add(cp);
		}
		for (int i = 0; i < deck.size(); i++) {
			players.get(i%PLAYER_AMOUNT).AddCard(deck.get(i));
		}		
	}


	//This is the function which tries to load the deck of cards from cards.txt and legend.txt
	public void loadCards() throws FileNotFoundException {
		//initialize deck, and the reader for the files
		//I used separate files because the nextline() was being difficult to work with when looking
		//	for multiple different types of text structures.
		deck = new ArrayList<Card>();
		FileReader legend = new FileReader(leg);		
		Scanner in = new Scanner(legend);
		String s = null;

		//Loads the room cards
		while (in.hasNext()) {
			if (in.hasNext()) in.next();
			if (in.hasNext()) s = in.next();
			if (s.endsWith(",")) s = s.substring(0, s.lastIndexOf(','));
			else { s += " " + in.next(); s = s.substring(0, s.lastIndexOf(',')); }			
			Card card = new Card(s, CardType.ROOM);
			if (in.hasNext()) s = in.next();			
			if (s.contains("Card")) deck.add(card);
		}
		
		int ran = (int) (Math.random()*deck.size());
		String solRoom = deck.get(ran).getName();

		in.close();
		FileReader people = new FileReader("People.txt");
		in = new Scanner(people);

		//Loads the people cards
		while (in.hasNextLine()) {
			s = in.nextLine();			
			Card card = new Card(s, CardType.PERSON);
			deck.add(card);
		}
		
		Card c = new Card();
		while (c.getType() != CardType.PERSON) {
			c = deck.get((int)Math.random()*deck.size());
		}
		String solPerson = c.getName();

		in.close();
		FileReader weapons = new FileReader("Weapons.txt");
		in = new Scanner(weapons);

		//Loads the weapon cards
		while (in.hasNextLine()) {
			s = in.nextLine();			
			Card card = new Card(s, CardType.WEAPON);
			deck.add(card);
		}		
		
		while (c.getType() != CardType.WEAPON) {
			c = deck.get((int)Math.random()*deck.size());
		}
		String solWeapon = c.getName();
		in.close();
		
		theAnswer = new Solution(solPerson, solRoom, solWeapon);
		
		System.out.println(theAnswer.room);
		System.out.println(theAnswer.person);
		System.out.println(theAnswer.weapon);
	}


	public static Map<Character, String> getRooms() {
		// TODO Auto-generated method stub
		return rooms;
	}

	public int getNumRows() {
		// TODO Auto-generated method stub
		return numRows;
	}

	public int getNumColumns() {
		// TODO Auto-generated method stub
		return numCols;
	}

	public BoardCell getCellAt(int i, int j) {
		// TODO Auto-generated method stub
		return board[i][j];
	}

	public void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException {
		rooms = new HashMap<Character, String>();
		FileReader legend = new FileReader(leg);
		Scanner in = new Scanner(legend);
		String str;
		while(in.hasNextLine()){
			str = in.nextLine();
			if(str.lastIndexOf(',') == str.indexOf(','))
				throw new BadConfigFormatException();
			rooms.put(str.charAt(0), str.substring(3, str.lastIndexOf(',')));
		}
		in.close();
	}

	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException{
		tempBoard = new BoardCell[BOARD_SIZE][BOARD_SIZE];
		FileReader layout = new FileReader(lay);
		Scanner in = new Scanner(layout);
		String str;
		int row = 0;
		int col = 0;
		int prevCol = 0;
		while(in.hasNextLine()){

			str = in.nextLine();
			col = 0;
			while(str.contains(",")){
				String cell = str.substring(0, str.indexOf(','));
				/*System.out.println(cell);
					System.out.println(cell.length());*/
				if (!rooms.containsKey(cell.charAt(0)))
					throw new BadConfigFormatException();
				tempBoard[row][col] = new BoardCell(row, col);
				tempBoard[row][col].setInitial(cell.charAt(0));
				// TAKE OUT SECOND CONDITION IN FOLLLOWING IF STATEMENT ONCE DEALING WITH 'N' AS SECOND CHAR
				// WE DON'T KNOW WHAT IT IS, SORRY
				if(cell.length()==2 && cell.charAt(1) != 'N'){
					tempBoard[row][col].setDoorDirection(cell.charAt(1));
				}
				col++;
				str = str.substring(str.indexOf(',')+1);
			}
			//repeat while loop once more to get last cell
			String cell = str;
			tempBoard[row][col] = new BoardCell(row, col);
			tempBoard[row][col].setInitial(cell.charAt(0));
			if(cell.length()==2){
				tempBoard[row][col].setDoorDirection(cell.charAt(1));
			}
			col++;
			if(row != 0 && col != prevCol)
				throw new BadConfigFormatException();
			prevCol = col;
			row++;
		}
		numRows = row;
		numCols = col;


		board = new BoardCell[numRows][numCols];
		for(int r=0; r<numRows; r++){
			for(int c=0; c<numCols; c++){
				board[r][c] = tempBoard[r][c];
			}
		}
		in.close();
	}

	public LinkedList<BoardCell> getAdjList(int i, int j) {
		// TODO Auto-generated method stub
		return adjMtx.get(board[i][j]);
	}

	public Set<BoardCell> getTargets() {
		// TODO Auto-generated method stub
		return targets;
	}

	public void calcTargets(int i, int j, int k) {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(board[i][j]);

		for(BoardCell c : adjMtx.get(board[i][j])){
			getAllTargets(board[c.getRow()][c.getCol()], k-1, visited);
		}

		if(targets.contains(board[i][j])) targets.remove(board[i][j]);
	}

	private void getAllTargets(BoardCell thisCell, int k, Set<BoardCell> visited) {

		Set<BoardCell> myVisited = new HashSet<BoardCell>(visited);
		myVisited.add(thisCell);

		if(k==0 || thisCell.isDoorway()){
			targets.add(thisCell);
			return;
		}

		for(BoardCell c : adjMtx.get(thisCell)){
			if(!myVisited.contains(c))
				getAllTargets(board[c.getRow()][c.getCol()], k-1, myVisited);
		}

	}

	public void calcAdjacencies(BoardCell b){

		LinkedList<BoardCell> l = new LinkedList<BoardCell>();
		if (b.isDoorway()){
			switch(b.getDoorDirection()){
			case RIGHT:
				l.add(board[b.getRow()][b.getCol() + 1]);
				break;
			case LEFT:
				l.add(board[b.getRow()][b.getCol() - 1]);
				break;
			case UP:
				l.add(board[b.getRow() - 1][b.getCol()]);
				break;
			case DOWN:
				l.add(board[b.getRow() + 1][b.getCol()]);
				break;
			default:
				break;
			}
		}
		else if(b.getInitial() == 'W'){
			if(b.getCol() - 1 >= 0 && (board[b.getRow()][b.getCol() - 1].getInitial() == 'W' 
					|| (board[b.getRow()][b.getCol() - 1].isDoorway() && board[b.getRow()][b.getCol() - 1].getDoorDirection() == DoorDirection.RIGHT))){
				l.add(board[b.getRow()][b.getCol() - 1]);
				//System.out.println("adj test case 1");
			}
			if(b.getCol() + 1 < numCols && (board[b.getRow()][b.getCol() + 1].getInitial() == 'W' 
					|| (board[b.getRow()][b.getCol() + 1].isDoorway() && board[b.getRow()][b.getCol() + 1].getDoorDirection() == DoorDirection.LEFT))){
				l.add(board[b.getRow()][b.getCol() + 1]);
				//System.out.println("adj test case 2");
			}
			if(b.getRow() - 1 >= 0 && (board[b.getRow() - 1][b.getCol()].getInitial() == 'W' 
					|| (board[b.getRow() - 1][b.getCol()].isDoorway() && board[b.getRow() - 1][b.getCol()].getDoorDirection() == DoorDirection.DOWN))){
				l.add(board[b.getRow() - 1][b.getCol()]);
				//System.out.println("adj test case 3");
			}
			if(b.getRow() + 1 < numRows && (board[b.getRow() + 1][b.getCol()].getInitial() == 'W' 
					|| (board[b.getRow() + 1][b.getCol()].isDoorway() && board[b.getRow() + 1][b.getCol()].getDoorDirection() == DoorDirection.UP))){
				l.add(board[b.getRow() + 1][b.getCol()]);
				//System.out.println("adj test case 4");
			}
		}
		adjMtx.put(b, l);
	}

	// Added 3-19. For use in testing only.
	public Solution getTheAnswer() {
		return theAnswer;
	}
	public void setTheAnswer(Solution theAnswer) {
		this.theAnswer = theAnswer;
	}

}
