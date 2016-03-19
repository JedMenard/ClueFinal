package clueGame;

public class Solution {
	public Solution(String p, String r, String w){
		person = p;
		room = r;
		weapon = w;
	}
	public Solution(Card p, Card r, Card w){
		person = p.getName();
		room = r.getName();
		weapon = w.getName();
	}
	public String person;
	public String room;
	public String weapon;
}
