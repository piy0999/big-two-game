/**
 * @author piyushjha
 * This Hand is used to model and implement a Hand of Pair in a Big Two Card game.
 * It is a subclass of abstract class Hand.
 */
public class Pair extends Hand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6656399162808425037L;
	/**
	 * The constructor to add the cardList of cards provided by the given player in the Hand of Pair.
	 * @param player
	 * Player for the given Hand
	 * @param cards
	 * CardList of the cards supplied by the given player for the given Pair
	 */
	public Pair(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	/**
	 * Returns the Top Card of the given Hand of Pair.
	 * @return
	 * A Card Object with the Top Card of the given Hand of Pair.
	 */
	public Card getTopCard()
	{
		//returns the card with higher suit.
		Card c1 = getCard(0);
		Card c2 = getCard(1);
		if(c1.suit > c2.suit)
		{
			return c1;
		}
		else
		{
			return c2;
		}
	}
	

	/**
	 * Method to check if the given Hand of Pair is a legal hand or not.
	 * @return
	 * Returns True or False based on the validity.
	 */
	public boolean isValid() {
		
		// starts by checking the size
		if(size() == 2)
		{
			Card c1 = getCard(0);
			Card c2 = getCard(1);
			if(c1.rank == c2.rank)
			{
				return true;
			}
			else
			{
				return false;
			}	
		}
		else
		{
			return false;
		}
		
	}

	/**
	 * Method to return what is the type of the given Hand of Pair.
	 * @return
	 * Returns a String stating the name of the given Hand.
	 */
	public String getType() {
		
		return "Pair";
	}
	
	
	
}
