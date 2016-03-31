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
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JPanel;

import java.awt.Graphics;

public class Board extends JPanel {

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
	private Map<Character, String> roomNames = new HashMap<Character, String>(); 

	// 3/18
	//public ArrayList<Card> deck;
	public Deck deck;
	public ArrayList<Player> players;	

	// 3/10
	private Solution theAnswer;

	public void loadConfigFiles(){}
	public void selectAnswer(){}
	public Card handleSuggestion(Solution suggestion, Player accusingPlayer, BoardCell clicked) {
		int i;
		Card result = null;
		for (i=0; i<players.size(); i++) {
			if (players.get(i).equals(accusingPlayer)) break;
		}
		for (int j = 1; j<players.size(); j++) {
			result = players.get((i+j)%players.size()).disproveSuggestion(suggestion);
			if (result != null) break;
		}
		return result;
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
		players = Player.loadPlayersFromFile("Players.txt");
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

		createPlayers();
		nameCells();		
	}

	private void createPlayers() {
		players = Player.loadPlayersFromFile("Players.txt"); 

		for (int i = 0; !deck.empty(); i++)  {
			players.get(i%PLAYER_AMOUNT).AddCard(deck.draw());
		}		
	}

	//This is the function which tries to load the deck of cards from cards.txt and legend.txt
	public void loadCards() throws FileNotFoundException {
		//initialize deck, and the reader for the files
		//I used separate files because the nextline() was being difficult to work with when looking
		//	for multiple different types of text structures.
		deck = new Deck();
		Random rand = new Random();
		FileReader legend = new FileReader(leg);		
		Scanner in = new Scanner(legend);
		String s = null;

		//Loads the room cards
		while (in.hasNext()) {
			String s2=null;
			if (in.hasNext()) s2 = in.next();			
			if (in.hasNext()) s = in.next();			
			if (s.endsWith(",")) s = s.substring(0, s.lastIndexOf(','));
			else { s += " " + in.next(); s = s.substring(0, s.lastIndexOf(',')); }
			roomNames.put(s2.charAt(0), s);
			Card card = new Card(s, CardType.ROOM);
			if (in.hasNext()) s = in.next();			
			if (s.contains("Card")) deck.add(card);
		}
		
		String solRoom = deck.draw().getName();

		in.close();

		//Loads the people cards		
		for (Player p : players){
			deck.add(new Card(p.getPlayerName(), CardType.PERSON));
		}
		
		Card c = new Card();
		do {
			c = deck.getRand();
		}while (c.getType() != CardType.PERSON);
		String solPerson = c.getName();
		deck.del(c);

		FileReader weapons = new FileReader("Weapons.txt");
		in = new Scanner(weapons);

		//Loads the weapon cards
		while (in.hasNextLine()) {
			s = in.nextLine();			
			Card card = new Card(s, CardType.WEAPON);
			deck.add(card);
		}		
		
		while (c.getType() != CardType.WEAPON) {
			c = deck.getRand();
		}
		String solWeapon = c.getName();
		deck.del(c);
		in.close();
		
		theAnswer = new Solution(solPerson, solRoom, solWeapon);
	
	}


	public static Map<Character, String> getRooms() {
		return rooms;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numCols;
	}

	public BoardCell getCellAt(int i, int j) {
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
				if(cell.length()==2 && cell.charAt(1) != 'N'){
					tempBoard[row][col].setDoorDirection(cell.charAt(1));
				}
				else if (cell.length()==2 && cell.charAt(1) == 'N') {
					tempBoard[row][col].isName = true;
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
		return adjMtx.get(board[i][j]);
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public void calcTargets(int row, int col, int steps) {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(board[row][col]);

		for(BoardCell c : adjMtx.get(board[row][col])){
			getAllTargets(board[c.getRow()][c.getCol()], steps-1, visited);
		}

		if(targets.contains(board[row][col])) targets.remove(board[row][col]);
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
	
	private void nameCells(){
		for (int row = 0; row < numRows; row++){
			for (int col = 0; col < numCols; col++){
				board[row][col].name = roomNames.get(board[row][col].getInitial());
				System.out.println(board[row][col].name);
			}
		}
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int col = 0; col < numCols; col++){
			for (int row = 0; row < numRows; row++){
				board[row][col].draw(g);
			}
		}
		for (Player p: players) {
			p.draw(g);
		}
	}
	
	// Added 3-19. For use in testing only.
	public Solution getTheAnswer() {
		return theAnswer;
	}
	public void setTheAnswer(Solution theAnswer) {
		this.theAnswer = theAnswer;
	}

	// Added 3-24. For use in testing only.
	public void resetPlayers(){
		players = new ArrayList<Player>();
	}
}
