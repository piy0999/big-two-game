import java.util.ArrayList;
/**
 * @author piyushjha
 * This Hand is used to model and implement a Hand of StraightFlush in a Big Two Card game.
 * It is a subclass of abstract class Hand.
 */
public class StraightFlush extends Hand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6944500492777769254L;
	/**
	 * The constructor to add the cardList of cards provided by the given player in the Hand of StraightFlush.
	 * @param player
	 * Player for the given Hand
	 * @param cards
	 * CardList of the cards supplied by the given player for the given StraightFlush.
	 */
	public StraightFlush(CardGamePlayer player, CardList cards) {
		super(player, cards);
		
	}
	/**
	 * Returns the Top Card of the given Hand of StraightFlush.
	 * @return
	 * A Card Object with the Top Card of the given Hand of StraightFlush.
	 */
	public Card getTopCard()
	{
		
		ArrayList<Integer> ranks = new ArrayList<Integer>(); // new ArrayList to handle the ranks of exception cases with cards of Ace and '2'
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
	 * Method to check if the given Hand of StraightFlush is a legal hand or not.
	 * @return
	 * Returns True or False based on the validity.
	 */
	public boolean isValid() {
		//start by checking the size
		if(size() == 5)
		{	
			boolean consecutive = true; //to check if the ranks of cards are consecutive or not
			boolean sameSuit = true; //to check if they belong to the same suit
			ArrayList<Integer> ranks = new ArrayList<Integer>();
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
					consecutive = false;
				}
			}
			
			for(int i = 0; i < size() - 1 ; i++)
			{
				if(getCard(i).suit != getCard(i+1).suit)
				{
					sameSuit = false;
				}
		
			}
			
			if(consecutive && sameSuit)
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
	 * Method to return what is the type of the given Hand of StraightFlush.
	 * @return
	 * Returns a String stating the name of the given Hand.
	 */
	public String getType() {
		
		return "StraightFlush";
	}
	
	
}
