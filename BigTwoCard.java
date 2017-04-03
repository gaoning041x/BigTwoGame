
/**
 * @author Gao Ning
 * in this class, compareTo method is overridden
 * in particular, the comparing logic is amended
 * A is only smaller than 2, while 2 is the largest
 * A=0, 2=1.........
 */
public class BigTwoCard extends Card {
	/**
	 * @param suit
	 * @param rank
	 * this is a constructor
	 */
	public BigTwoCard (int suit, int rank)
	{
		super(suit, rank);
	}
	/**
	 * compare two cards, in particular, follow the bigTwo game rulw
	 */
	public int compareTo(Card card)
	{
		if (this.rank > card.rank) 
		{
			if (card.rank==0)
			{
				if (this.rank==1)
				{
					return 1;
				}
				else 
				{
					return -1;
				}
			}
			else if (card.rank==1)
			{
				return -1;
			}
			else 
			{
				return 1;
			}
		} 
		else if (this.rank < card.rank)
		{
			if (this.rank==0)
			{
				if (card.rank==1)
				{
					return -1;
				}
				else 
				{
					return 1;
				}
			}
			else if (this.rank==1)
			{
				return 1;
			}
			else 
			{
				return -1;
			}
		} 
		else if (this.suit > card.suit)
		{
			return 1;
		} 
		else if (this.suit < card.suit) 
		{
			return -1;
		} 
		else 
		{
			return 0;
		}
	}
}
