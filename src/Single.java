/**
 * @author piyushjha
 * This Hand is used to model and implement a Hand of Single in a Big Two Card game.
 * It is a subclass of abstract class Hand.
 */
public class Single extends Hand {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8507941208671911631L;
	/**
	 * The constructor to add the cardList of cards provided by the given player in the Hand of Single.
	 * @param player
	 * Player for the given Hand
	 * @param cards
	 * CardList of the cards supplied by the given player for the given Single
	 */
	public Single(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	/**
	 * Returns the Top Card of the given Hand of Single.
	 * @return
	 * A Card Object with the Top Card of the given Hand of Single.
	 */
	public Card getTopCard()
	{
		// returns the only card
		Card c1 = getCard(0);
		
		return c1;
	}
			
	/**
	 * Method to check if the given Hand of Single is a legal hand or not.
	 * @return
	 * Returns True or False based on the validity.
	 */
	public boolean isValid() {
		
		//starts by checking the size
		if(size() == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Method to return what is the type of the given Hand of Single.
	 * @return
	 * Returns a String stating the name of the given Hand.
	 */
	public String getType() {
		
		return "Single";
	}
		
	
}
