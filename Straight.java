
/**
 * @author Gao Ning
 * @since 16-10-11
 * this class model the Straight
 *
 */
public class Straight extends Hand {
	private final int  typeIndex=3;
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
	 * this is the constructor for Straight class
	 */
	public Straight (CardGamePlayer player, CardList cards)
	{
		super(player, cards);
	}
	/**
	 * return the boolean value indicating the validness of this hand
	 */
	public boolean isValid()
	{
		if (this.size()!=5)
		{
			return false;
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
	/**
	 * return the type of this hand
	 */
	public String getType()
	{
		return this.LEGALCOMB[typeIndex];
	}
}
