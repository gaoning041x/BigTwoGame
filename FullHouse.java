
/**
 * @author Gao Ning
 * @since 16-10-11
 * @version 1.0
 * this is the FullHouse class
 *
 */
public class FullHouse extends Hand {
	private final int  typeIndex=5;
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
	 * this is the constructor of Flush class
	 */
	public FullHouse (CardGamePlayer player, CardList cards)
	{
		super(player, cards);
	}
	/**
	 * return the top card of this hand
	 */
	public Card getTopCard()
	{
		int same_rank_for0=0;
		int top0=0;
		int top1=0;
		int rank0=this.getCard(0).getRank();
		for (int i=1; i<5; ++i)
		{
			if (this.getCard(i).getRank()==rank0)
			{
				++same_rank_for0;
			}
			else
			{
				top1=i;
			}
		}
		if(same_rank_for0==2)
		{
			for (int i=1; i<5; ++i)
			{
				if (this.getCard(top0).getSuit()<this.getCard(i).getSuit())
				{
					if (this.getCard(top0).getRank()==this.getCard(i).getRank())
					{
						top0=i;
					}
				}
			}
			return this.getCard(top0);
		}
		else
		{
			for (int j=1; j<5; ++j)
			{
				if (this.getCard(top1).getSuit()<this.getCard(j).getSuit())
				{
					if (this.getCard(top1).getRank()==this.getCard(j).getRank())
					{
						top1=j;
					}
				}
			}
			return this.getCard(top1);
		}
	}
	/**
	 * return a boolean value indicating the validness of hand
	 */
	public boolean isValid()
	{
		int same0=0;
		int same1=0;
		int rank0=this.getCard(0).getRank();
		int rank1=0;
		for (int i=1; i<5; ++i)
		{
			if (rank0!=this.getCard(i).getRank())
			{
				rank1=this.getCard(i).getRank();
			}
		}
		for (int i=0; i<5; ++i)
		{
			if (this.getCard(i).getRank()==rank0)
			{
				same0+=1;
			}
			if (this.getCard(i).getRank()==rank1)
			{
				same1+=1;
			}
		}
		if ((same1+same0)==5)
		{
			if (same1==2)
			{
				return true;
			}
			else if(same1==3)
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
	 * return the type of this hand
	 */
	public String getType()
	{
		return this.LEGALCOMB[typeIndex];
	}
}
