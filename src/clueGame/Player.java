package clueGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JPanel;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

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

	public Player(String n, String r, String c){
		playerName = n;		
		row = Integer.parseInt(r);
		column = Integer.parseInt(c);
	}

	public void AddCard(Card c) {
		myCards.add(c);
	}

	//for test
	public Set<Card> GetMyCards() {
		return myCards;
	}

	public void SeeCard(Card c){
		seenCards.add(c);
	}

	public void setLocation(int r, int c){
		setRow(r);
		setColumn(c);
	}

	public static ArrayList<Player> loadPlayersFromFile(String string) {
		ArrayList<Player> players = new ArrayList<Player>();

		try {
			Random rand = new Random();
			FileReader fr;
			fr = new FileReader(string);
			Scanner in = new Scanner(fr);

			while(in.hasNext()){
				String name = in.next();
				String color = in.next();
				String row = in.next();
				String col = in.next();

				name = name.substring(0, name.lastIndexOf(","));
				color = color.substring(0, color.lastIndexOf(","));
				row = row.substring(0, row.lastIndexOf(","));

				Player temp = new Player(name, row, col);
				players.add(temp);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		// TODO Auto-generated method stub
		return players;
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

	public void setColor(Color color) {
		this.color = color;
	}

	public BoardCell getLastCell() {
		return lastCell;
	}

	public void setLastCell(BoardCell lastCell) {
		this.lastCell = lastCell;
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

//	public Color mapColor() {
//			Random ran = new Random();
//			if (!colorMap.containsKey(playerName)) colorMap.put(playerName, new Color(ran.nextInt(255),ran.nextInt(255),ran.nextInt(255)));
//			return colorMap.get(playerName);					
//	}
	
}
