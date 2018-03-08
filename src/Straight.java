import java.util.*;
/**
 * @author piyushjha
 * This Hand is used to model and implement a Hand of Straight in a Big Two Card game.
 * It is a subclass of abstract class Hand.
 */
public class Straight extends Hand {
/**
	 * 
	 */
	private static final long serialVersionUID = -4215347239575696845L;

	/**
	 * The constructor to add the cardList of cards provided by the given player in the Hand of Straight.
	 * @param player
	 * Player for the given Hand
	 * @param cards
	 * CardList of the cards supplied by the given player for the given Straight.
	 */
	public Straight(CardGamePlayer player, CardList cards) {
		super(player, cards);
		
	}
	/**
	 * Returns the Top Card of the given Hand of Straight.
	 * @return
	 * A Card Object with the Top Card of the given Hand of Straight.
	 */
	public Card getTopCard()
	{
		
		
		ArrayList<Integer> ranks = new ArrayList<Integer>(); // new ArrayList for storing the higher ranks for checking the exception cases of Ace and '2' cards
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
	 * Method to check if the given Hand of Straight is a legal hand or not.
	 * @return
	 * Returns True or False based on the validity.
	 */
	public boolean isValid() {
		// starts by checking the size
		if(size() == 5)
		{
			ArrayList<Integer> ranks = new ArrayList<Integer>(); // new ArrayList for storing the higher ranks for checking the exception cases of Ace and '2' cards
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
			
			ranks.sort(null);
			
			for(int i = 0; i < ranks.size() - 1 ; i++)
			{	
				
				if(ranks.get(i+1) - ranks.get(i) != 1)
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
	 * Method to return what is the type of the given Hand of Straight.
	 * @return
	 * Returns a String stating the name of the given Hand.
	 */
	public String getType() {
		
		return "Straight";
	}
	
	
}
