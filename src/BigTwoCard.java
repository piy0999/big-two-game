
//import java.util.*;
/**
 * @author piyushjha
 * This class is used to implement and model a Big Two Card. 
 * It is the subclass of Card class.
 */
public class BigTwoCard extends Card {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9021875950801767304L;

	/**
	 * Constructor for the BigTwoCard class as the default constructor in Card class is not defined.
	 * @param suit
	 * The integer value of the suit of a card from 0-3 for diamond-spade
	 * @param rank
	 * The integer value of the rank of a card from 0-12 for Ace-King.
	 */
	public BigTwoCard(int suit, int rank)
	{
		super(suit,rank);
	}
	

	/**
	 * Compares this card with the specified card for order and overrides the logic for Big Two Card game.
	 * 
	 * @param card
	 *            the card to be compared
	 * @return a negative integer, zero, or a positive integer as this Big Two card is
	 *         less than, equal to, or greater than the specified card
	 */
	public int compareTo(Card card)
	{	
		// Compares if the rank is same or not
		if(this.rank == card.rank)
		{	
			//compares the suit next
			if( this.suit < card.suit )
			{
				return -1;
			}
			else if(this.suit > card.suit)
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}
		else
		{	//handles the cases for the '2' and 'A' cards
			if(this.rank == 1)
			{
				return 1;
			}
			else if(this.rank == 0)
			{
				if(card.rank == 1)
				{
					return -1;
				}
				else
				{
					return 1;
				}
			}
			else if(card.rank == 1)
			{
				return -1;
			}
			else if(card.rank == 0)
			{
				if(this.rank == 1)
				{
					return 1;
				}
				else
				{
					return -1;
				}
			}
			else if(this.rank > card.rank)
			{
				return 1;
			}
			else
			{
				return -1;
			}
		}
			
	}
	
}
