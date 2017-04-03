
/**
 * @author Gao Ning
 * @since 16-10-11
 * @version 1.0
 * this is ths StraightFlush class
 *
 */
public class StraightFlush extends Hand{
	private final int  typeIndex=7;
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
	public StraightFlush (CardGamePlayer player, CardList cards)
	{
		super(player, cards);
	}
	
	/**
	 * return the type of the hand
	 */
	public String getType()
	{ 
		return this.LEGALCOMB[typeIndex];
	}
	
	/**
	 * return the boolean value indicating the validness of this hand
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
			for (int i=1; i<5; ++i)
			{
				for (int j=i-1; j>=0; --j)
				{
					if (this.getCard(i).getRank()==this.getCard(j).getRank())
					{
						return false;
					}
				}
			}
			int counter=0;
			for (int i=0; i<5; ++i)
			{
				if (this.getCard(i).getRank()==this.getTopCard().getRank())
				{
					if (this.getCard(i).getSuit()!=this.getTopCard().getSuit())
					{
						return false;
					}
				}
				else 
				{
					if(this.getTopCard().getRank()==0)
					{
						counter+=(13-this.getCard(i).getRank());
					}
					else if(this.getTopCard().getRank()==1)
					{
						if (this.getCard(i).getRank()!=0)
						{
							counter+=(14-this.getCard(i).getRank());
						}
						else
						{
							counter+=1;
						}
					}
					else
					{
						counter+=(this.getTopCard().getRank()-this.getCard(i).getRank());
					}
				}
			}
			if (counter==10)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
}
