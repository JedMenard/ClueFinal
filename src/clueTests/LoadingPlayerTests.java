package clueTests;

import static org.junit.Assert.*;

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
		ArrayList<Player> players = Player.loadPlayersFromFile("People.txt");
		
		// Check if the first loaded player is correct
		Player p1 = players.get(0);
		assertEquals(p1.getPlayerName(), "Mrs. Peacock");
		assertEquals(p1.getColumn(), 0);
		assertEquals(p1.getRow(), 0);
		assertEquals(p1.getColor(), "red");
		
		// Check if the third loaded player is correct
		Player p2 = players.get(2);
		assertEquals(p2.getPlayerName(), "Professor Plum");
		assertEquals(p2.getColumn(), 0);
		assertEquals(p2.getRow(), 0);
		assertEquals(p2.getColor(), "red");
		
		// Check if human player is loaded correctly
		HumanPlayer hp = new HumanPlayer();
		assertEquals(p2.getPlayerName(), "Human");
		assertEquals(p2.getColumn(), 0);
		assertEquals(p2.getRow(), 0);
		assertEquals(p2.getColor(), "red");
	}
}
