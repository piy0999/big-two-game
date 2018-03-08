
/**
 * @author piyushjha
 * This class is used to implement and model a Big Two Deck for a Big Two Card Game.
 * It is a subclass of Deck class.s
 */
public class BigTwoDeck extends Deck {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8254481884293570270L;

	/**
	 * Initialize the deck of Big Two Cards.
	 * Overrides the method in Deck class.
	 */
	public void initialize()
	{
		super.removeAllCards();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				BigTwoCard card = new BigTwoCard(i, j); //created a Big Two Card
				super.addCard(card); //adds the Big Two card to the CardList
			}
		}
	}
}
