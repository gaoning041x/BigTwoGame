
/**
 * @author Gao Ning
 * @since 16-10-11
 * @version 1.0
 * this is the Quad class
 *
 */
public class Quad extends Hand{
	private final int  typeIndex=6;
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
	 *  this the constructor
	 */
	public Quad (CardGamePlayer player, CardList cards)
	{
		super(player, cards);
	}
	/**
	 * return a boolean value indicating the validness of this hand
	 */
	public boolean isValid()
	{
		if (this.size()!=5)
		{
			return false;
		}
		else
		{
			int rank0=this.getCard(0).getRank();
			int counter=0;
			for (int i=1; i<5; ++i)
			{
				if (rank0!=this.getCard(i).getRank())
				{
					++counter;
				}
			}
			if (counter==1)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	/** 
	 * return the top card of this hand
	 */
	public Card getTopCard()
	{
		int same0=0;
		int rank0=this.getCard(0).getRank();
		for (int i=1; i<5; ++i)
		{
			if (rank0!=this.getCard(i).getRank())
			{
				++same0;
			}
		}
		if (same0==0)
		{
			int top=1;
			for (int i=2; i<5 ;++i)
			{
				if (this.getCard(i).getSuit()>this.getCard(top).getSuit())
				{
					top=i;
				}
			}
			return this.getCard(top);
		}
		else 
		{
			int top=0;
			for (int i=1; i<5; ++i)
			{
				if (this.getCard(i).getSuit()>this.getCard(top).getSuit())
				{
					if (this.getCard(i).getRank()==this.getCard(top).getRank())
					{
						top=i;
					}
				}
			}
			return this.getCard(top);
		}
	}
	
	/**
	 * return the type of this hand
	 */
	public String getType()
	{
		return this.LEGALCOMB[typeIndex];
	}
}
