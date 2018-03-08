import java.util.ArrayList;

/**
 * @author piyushjha
 * This Hand is used to model and implement a Hand of Flush in a Big Two Card game.
 * It is a subclass of abstract class Hand.
 */
public class Flush extends Hand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2348369156615550005L;

	/**
	 * The constructor to add the cardList of cards provided by the given player in the Hand of Flush.
	 * @param player
	 * Player for the given Hand
	 * @param cards
	 * CardList of the cards supplied by the given player for the given Flush
	 */
	public Flush(CardGamePlayer player, CardList cards) {
		super(player, cards);
		
	}
	
	/**
	 * Returns the Top Card of the given Hand of Flush.
	 * @return
	 * A Card Object with the Top Card of the given Hand of Flush.
	 */
	public Card getTopCard()
	{
		
		
		ArrayList<Integer> ranks = new ArrayList<Integer>(); // New ArrayList for handling the ranks of exceptional cases of '2' and Ace.
		for(int i = 0; i < size() ; i++)
		{
			if(getCard(i).rank == 1)
			{
				ranks.add(14);
			}
			else if(getCard(i).rank == 0)
			{
				ranks.add(13);
			}
			else
			{
				ranks.add(getCard(i).rank);
			}
		}
		
		int index = 0;
		int max = 0;
		for(int i = 0 ; i < size() ; i++)
		{
			if(ranks.get(i) > max)
			{
				max = ranks.get(i);
				index  = i;
			}
		}
		
		return getCard(index);
		
	}

	/**
	 * Method to check if the given Hand of Flush is a legal hand or not.
	 * @return
	 * Returns True or False based on the validity.
	 */
	public boolean isValid() {
		
		// starts by checking the size of the hand
		if(size() == 5)
		{
			
			for(int i = 0; i < size() - 1 ; i++)
			{
				if(getCard(i).suit != getCard(i+1).suit) //comparison for same suit
				{
					return false;
				}
		
			}
			
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Method to return what is the type of the given Hand of Flush.
	 * @return
	 * Returns a String stating the name of the given Hand.
	 */
	public String getType() {
		
		return "Flush";
	}
	
}
