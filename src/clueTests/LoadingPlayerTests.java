package clueTests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.HumanPlayer;
import clueGame.Player;

public class LoadingPlayerTests {

	@Test
	public void testLoadingPlayers() {
		// Load the players from the file
		ArrayList<Player> players = Player.loadPlayersFromFile("Players.txt");
		
		// Check if the first loaded player is correct
		Player p1 = players.get(0);
		assertEquals(p1.getPlayerName(), "Mrs. Peacock");
		assertEquals(p1.getColumn(), 3);
		assertEquals(p1.getRow(), 0);
		assertEquals(p1.getColor(), Color.getColor("Blue"));
		
		// Check if the third loaded player is correct
		Player p2 = players.get(2);
		assertEquals(p2.getPlayerName(), "Professor Plum");
		assertEquals(p2.getColumn(), 6);
		assertEquals(p2.getRow(), 21);
		assertEquals(p2.getColor(), Color.getColor("Purple"));
		
		// Check if the last player is loaded correctly
		Player p4 = players.get(4);
		assertEquals(p4.getPlayerName(), "Colonel Mustard");
		assertEquals(p4.getColumn(), 22);
		assertEquals(p4.getRow(), 10);
		assertEquals(p4.getColor(), Color.getColor("Yellow"));
	}
}
