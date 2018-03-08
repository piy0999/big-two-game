
/**
 * @author piyushjha
 * Hand class is used to model a hand of cards from the given list of cards for a given player.
 * It is a subclass of CardList class.
 */
public abstract class Hand extends CardList {
	
	
	private static final long serialVersionUID = -3244172656591331634L;
	private CardGamePlayer player;
	
	/**
	 * Hand class contructor which assigns the enter player to the private player variable and adds the cards to cardList for the given Hand from the given cards.
	 * @param player
	 * CardGamePlayer object containing a player.
	 * @param cards
	 * CardList object containing the list of cards from which the Hand would be composed.
	 */
	public Hand(CardGamePlayer player, CardList cards)
	{	
		this.player = player;
			
		for(int i = 0; i < cards.size() ; i++)
		{
			addCard(cards.getCard(i));
		}
		
		
	}
	
	/**
	 * Returns the player for the given Hand.
	 * @return
	 * A CardGamePlayer object.
	 */
	public CardGamePlayer getPlayer()
	{
		return this.player;
	}

	
	/**
	 * Returns the Top Card of the given Hand. It is overridden in every subClass of the Hand.
	 * @return
	 * A Card Object with the Top card of the given Hand.
	 */
	public Card getTopCard()
	{
		//overrided in every subclass
		
		Card c1 = getCard(0);
		
		return c1;
	}
	
	/**
	 * Checks if the given Hand beats the Hand provided as an argument in the method.
	 * @param hand
	 * Hand object containing another Hand that can beat the given Hand.
	 * @return
	 * Returns True or False.
	 */
	public boolean beats(Hand hand)
	{	
		//check if the size of the Hands are same else the can't be compared.
		if(this.size() == hand.size())
		{	
			//similar implementation for Single/Pair/Triple.
			if(this.getType() == "Single" || this.getType() == "Pair" || this.getType() == "Triple")
			{
				Card c = this.getTopCard();
				int s1 = c.getSuit();
				int r1 = c.getRank();
				BigTwoCard c1 = new BigTwoCard(s1,r1);
				
				Card c2 = hand.getTopCard();
				
				if(c1.compareTo(c2) > 0)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else if(this.getType() == "Straight")
			{ //implementation for Straight Hand
				if(hand.getType() == "Straight")
				{
					Card c = this.getTopCard();
					int s1 = c.getSuit();
					int r1 = c.getRank();
					BigTwoCard c1 = new BigTwoCard(s1,r1);
					
					Card c2 = hand.getTopCard();
					
					if(c1.compareTo(c2) > 0)
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
			else if(this.getType() == "Flush")
			{
				if(hand.getType() == "Straight")
				{
					return true;
				}
				else if(hand.getType() == "Flush")
				{
					if(this.getTopCard().suit > hand.getTopCard().suit)
					{
						return true;
					}
					else if(this.getTopCard().suit < hand.getTopCard().suit)
					{
						return false;
					}
					else
					{
						Card c = this.getTopCard();
						int s1 = c.getSuit();
						int r1 = c.getRank();
						BigTwoCard c1 = new BigTwoCard(s1,r1);
						
						Card c2 = hand.getTopCard();
						
						if(c1.compareTo(c2) > 0)
						{
							return true;
						}
						else
						{
							return false;
						}
					}
				}
				else
				{
					return false;
				}
			}
			else if(this.getType() == "FullHouse")
			{
				if(hand.getType() == "Straight" || hand.getType() == "Flush")
				{
					return true;
				}
				else if(hand.getType() == "FullHouse")
				{
					Card c = this.getTopCard();
					int s1 = c.getSuit();
					int r1 = c.getRank();
					BigTwoCard c1 = new BigTwoCard(s1,r1);
					
					Card c2 = hand.getTopCard();
					
					if(c1.compareTo(c2) > 0)
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
			else if(this.getType() == "Quad")
			{
				if(hand.getType() == "Straight" || hand.getType() == "Flush" || hand.getType() == "FullHouse")
				{
					return true;
				}
				else if(hand.getType() == "Quad")
				{
					Card c = this.getTopCard();
					int s1 = c.getSuit();
					int r1 = c.getRank();
					BigTwoCard c1 = new BigTwoCard(s1,r1);
					
					Card c2 = hand.getTopCard();
					
					if(c1.compareTo(c2) > 0)
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
			else
			{	//implementation for StraightFlush hand.
				
				if(hand.getType() == "Straight" || hand.getType() == "Flush" || hand.getType() == "FullHouse" || hand.getType() == "Quad")
				{
					return true;
				}
				else if(hand.getType() == "StraightFlush")
				{	
					
					if(this.getTopCard().rank != hand.getTopCard().rank)
					{	
						
						Card c = this.getTopCard();
						int s1 = c.getSuit();
						int r1 = c.getRank();
						BigTwoCard c1 = new BigTwoCard(s1,r1);
						
						Card c2 = hand.getTopCard();
						
						if(c1.compareTo(c2) > 0)
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
						if(this.getTopCard().suit > hand.getTopCard().suit)
						{
							return true;
						}
						else 
						{
							return false;
						}
					}
					
				}
				else
				{
					return false;
				}
			}
		}
		else
		{
			return false;
		}
			
			
			
			
	}
	
	/**
	 * Abstract method overridden in every subclass of Hand to check if the given Hand is a legal hand or not.
	 * @return
	 * Returns True or False based on the validity.
	 */
	public abstract boolean isValid();
	/**
	 * Abstract method overridden in every subclass of Hand to return what is the type of the given Hand.
	 * @return
	 * Returns a String stating the name of the given Hand.
	 */
	public abstract String getType();
	
}
