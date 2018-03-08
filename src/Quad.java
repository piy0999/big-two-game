import java.util.ArrayList;

/**
 * @author piyushjha
 * This Hand is used to model and implement a Hand of Quad in a Big Two Card game.
 * It is a subclass of abstract class Hand.
 */
public class Quad extends Hand {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6513678899946621301L;
	/**
	 * The constructor to add the cardList of cards provided by the given player in the Hand of Quad.
	 * @param player
	 * Player for the given Hand
	 * @param cards
	 * CardList of the cards supplied by the given player for the given Quad.
	 */
	public Quad(CardGamePlayer player, CardList cards) {
		super(player, cards);
		
	}
	
	/**
	 * Returns the Top Card of the given Hand of Quad.
	 * @return
	 * A Card Object with the Top Card of the given Hand of Quad.
	 */
	public Card getTopCard()
	{
		
		
		ArrayList<Integer> ranks = new ArrayList<Integer>(); //ArrayList to find the rank with four cards to search the card with highest in the quadruple.
		
		for(int i = 0; i < size() ; i++)
		{
			ranks.add(getCard(i).rank);
		}
		
		int r1 = ranks.get(0);
		int r2 = 13;
		for(int i = 0; i < ranks.size() ; i++)
		{
			
			if(ranks.get(i) != r1)
			{
				r2 = ranks.get(i);
			}
		}
		
		int c1 = 0;
		for(int i = 0; i < ranks.size() ; i++)
		{
			if(ranks.get(i) == r1)
			{
				c1++;
			}
		}
		
		int index = 0;
		int largest = -1;
		if(c1 == 4)
		{
			for(int i = 0; i < ranks.size() ; i++)
			{
				if(ranks.get(i) == r1)
				{
					if(getCard(i).suit > largest)
					{
						largest = getCard(i).suit;
						index = i;
					}
				}
			}
		}
		else
		{
			for(int i = 0; i < ranks.size() ; i++)
			{
				if(ranks.get(i) == r2)
				{
					if(getCard(i).suit > largest)
					{
						largest = getCard(i).suit;
						index = i;
					}
				}
			}
		}
		
		return getCard(index);
		
	}


	/**
	 * Method to check if the given Hand of Quad is a legal hand or not.
	 * @return
	 * Returns True or False based on the validity.
	 */
	public boolean isValid() {
		
		// start with checking the size
		if(size() == 5)
		{
			int [] counter = new int[5]; // a counter array to check the number of cards with same rank
			for(int i = 0; i < size() ; i++)
			{	int count = 0;
				for(int j = 0 ; j < size() ; j++)
				{	
					
						if(getCard(i).rank == getCard(j).rank)
						{
							counter[i] = count++;
						}
					
					
				}
			}
			
			int threes = 0;
			int zeros = 0;
			for(int i = 0; i < 5 ; i++)
			{
				if(counter[i] == 3)
				{
					threes++;
				}
				if(counter[i] == 0)
				{
					zeros++;
				}
			}
			
			if(threes == 4 && zeros == 1)
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
	 * Method to return what is the type of the given Hand of Quad.
	 * @return
	 * Returns a String stating the name of the given Hand.
	 */
	public String getType() {
		
		return "Quad";
	}
	

	
}
