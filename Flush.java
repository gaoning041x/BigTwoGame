

/**
 * @author Gao Ning
 * @since 16-10-11
 * @version 1.0
 * this is the Flush class
 *
 */
public class Flush extends Hand{
	private final int  typeIndex=4;
	/**
	 * @return
	 * this method returns the index for type for code simplification
	 */
	public int getTypeIndex()
	{
		return this.typeIndex;
	}
	/**
	 * @param player
	 * @param cards
	 * this is the constructor
	 */
	public Flush (CardGamePlayer player, CardList cards)
	{
		super(player, cards);
	}
	
	/**
	 * return a boolean value indicating the validness of this hand
	 */
	public boolean isValid()
	{
		int suit0=this.getCard(0).getSuit();
		if (this.size()!=5)
		{
			return false;
		}
		else
		{
			for (int i=0; i<5; ++i)
			{
				if (this.getCard(i).getSuit()!=suit0)
				{
					return false;
				}
			}
			return true;
		}
	}
	
	/**
	 * return the top card of this hand
	 */
	public Card getTopCard()
	{
		int top=0;
		for (int i=1; i<5; ++i)
		{
			if (this.getCard(i).compareTo(this.getCard(top))==1)
			{
				top=i;
			}
		}
		return this.getCard(top);
	}
	/**
	 * return the type of this hand
	 */
	public String getType()
	{
		return this.LEGALCOMB[typeIndex];
	}
	/**
	 * overridden comparing method for Flush
	 */
	public boolean beats (Hand hand)
	{
		if (this.size()!=hand.size())
		{
			return false;
		}
		else if (this.getTypeIndex()<hand.getTypeIndex())
		{
			return false;
		}
		else if (this.getTypeIndex()==hand.getTypeIndex())
		{ 
			if (this.getTopCard().getSuit()>hand.getTopCard().getSuit())
			{
				return true;
			}
			else if(this.getTopCard().getSuit()==hand.getTopCard().getSuit())
			{
				if(this.getTopCard().getRank()>hand.getTopCard().getRank())
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
		{
			return true;
		}
	}
}