
/**
 * @author ngao
 * @since 16-10-11
 * class triple 
 *
 */
public class Triple extends Hand{
	private final int  typeIndex=2;
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
	 * constructor for Triple
	 */
	public Triple (CardGamePlayer player, CardList cards)
	{
		super(player, cards);
	}
	/**
	 * return the type of this hand
	 */
	public String getType()
	{
		return LEGALCOMB[typeIndex];
	}
	/**
	 * return a boolean value to define whether the list is Triple or not
	 */
	public boolean isValid()
	{
		if (this.size()!=3)
		{
			return false;
		}
		else if (this.getCard(0).getRank()!=this.getCard(1).getRank())
		{
			return false;
		}
		else if (this.getCard(0).getRank()!=this.getCard(2).getRank())
		{
			return false;
		}
		else if (this.getCard(1).getRank()!=this.getCard(2).getRank())
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
}
