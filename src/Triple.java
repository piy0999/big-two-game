/**
 * @author piyushjha
 * This Hand is used to model and implement a Hand of Triple in a Big Two Card game.
 * It is a subclass of abstract class Hand.
 */
public class Triple extends Hand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3085937390798849734L;
	/**
	 * The constructor to add the cardList of cards provided by the given player in the Hand of Triple.
	 * @param player
	 * Player for the given Hand
	 * @param cards
	 * CardList of the cards supplied by the given player for the given Triple.
	 */
	public Triple(CardGamePlayer player, CardList cards) {
		super(player, cards);
		
	}
	/**
	 * Returns the Top Card of the given Hand of Triple.
	 * @return
	 * A Card Object with the Top Card of the given Hand of Triple.
	 */
	public Card getTopCard()
	{
		//returns the card with the largest suit
		Card c1 = getCard(0);
		Card c2 = getCard(1);
		Card c3 = getCard(2);
		if(c1.suit > c2.suit && c1.suit > c3.suit)
		{
			return c1;
		}
		else if(c2.suit > c3.suit && c2.suit > c1.suit)
		{
			return c2;
		}
		else
		{
			return c3;
		}
	}
		
	
	/**
	 * Method to check if the given Hand of Triple is a legal hand or not.
	 * @return
	 * Returns True or False based on the validity.
	 */
	public boolean isValid() {
		
		//starts by checking the size of the hand
		if(size() == 3)
		{
			Card c1 = getCard(0);
			Card c2 = getCard(1);
			Card c3 = getCard(2);
			if(c1.rank == c2.rank && c2.rank == c3.rank && c3.rank == c1.rank)
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
	 * Method to return what is the type of the given Hand of Triple.
	 * @return
	 * Returns a String stating the name of the given Hand.
	 */
	public String getType() {
		
		return "Triple";
	}
	

}
