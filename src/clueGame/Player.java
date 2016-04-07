package clueGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JPanel;

public class Player extends JPanel {
	private String playerName;
	private int row, column;	
	private Color color;
	private Set<Card> myCards;
	protected Set<Card> seenCards;
	protected BoardCell lastCell;
	private static Dimension size = new Dimension(35,35);	

	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> cards = new ArrayList<Card>();
		for(Card c : myCards) {
			if(c.getName().equals(suggestion.person) || c.getName().equals(suggestion.room) || c.getName().equals(suggestion.weapon)) {
				cards.add(c);
			}			
		}
		if (cards.size()>0) {
			Random r = new Random();
			int ran = r.nextInt(cards.size());
			return cards.get(ran);
		}
		return null;
	}

	public Player() {
		setPlayerName("The Cook");		
		setRow(0); setColumn(0);		
		myCards = new HashSet<Card>();
		seenCards = new HashSet<Card>();		
	}

	public Player(String n, String col, String r, String c){
		playerName = n;		
		row = Integer.parseInt(r);
		column = Integer.parseInt(c);
		//color = Color.getColor(col.toLowerCase());		
		myCards = new HashSet<Card>();
		seenCards = new HashSet<Card>();
		try {
		    Field field = Class.forName("java.awt.Color").getField(col.toLowerCase());
		    color = (Color)field.get(null);
		} catch (Exception e) {
		    color = null; // Not defined
		}		
	}

	public Player(String playerName2, Color color2, int row2, int column2) {
		playerName = playerName2;
		color = color2;
		row = row2;
		column = column2;
		myCards = new HashSet<Card>();
		seenCards = new HashSet<Card>();
	}

	public void AddCard(Card c) {
		myCards.add(c);
	}

	public void SeeCard(Card c){
		seenCards.add(c);
	}
	
	public static ArrayList<Player> loadPlayersFromFile(String string) {
		ArrayList<Player> players = new ArrayList<Player>();

		try {
			FileReader fr;
			fr = new FileReader(string);
			Scanner line = new Scanner(fr);		
			boolean madePlayer = false;
			
			while(line.hasNext()){
				Scanner in = new Scanner(line.nextLine());
				Player temp;
				
				in.useDelimiter(",");
								
				String name = in.next();
				String color = in.next();
				String row = in.next();
				String col = in.next();
				
				if(!madePlayer){
					temp = new HumanPlayer(name, color, row, col);
					madePlayer = true;
				}
				else{
					temp = new ComputerPlayer(name, color, row, col);
				}
				players.add(temp);
				
				in.close();
			}
			line.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return players;
	}
	
	public void draw(Graphics g){
		if (color == null) {
			Random ran = new Random();
			color = new Color(ran.nextInt(255),ran.nextInt(255),ran.nextInt(255));
		}
		g.setColor(color);
		g.fillOval((int)(column*size.getWidth()), (int)(row*size.getHeight()), (int)size.getHeight(), (int)size.getWidth());
		g.setColor(Color.WHITE);
		g.drawOval((int)(column*size.getWidth()), (int)(row*size.getHeight()), (int)size.getHeight(), (int)size.getWidth());		
	}	
	
	public Set<Card> GetMyCards() {
		return myCards;
	}
	
	public void setLocation(int r, int c){
		setRow(r);
		setColumn(c);
	}
	
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public Color getColor() {
		return color;
	}


}
