package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	
	public Card() {
		this.cardName = "Colonel Mustard";
		this.type = CardType.PERSON;
	}
	
	
	public Card(String cardName, CardType type) {
		this.cardName = cardName;
		this.type = type;
	}

	public String getName() {
		return cardName;
	}
	
	public CardType getType() {
		return type;
	}
	
	public boolean equals(Card c) {
		if (this.cardName.equals(c.getName()) && this.type.equals(c.getType())) return true;
		return false;
	}
}
