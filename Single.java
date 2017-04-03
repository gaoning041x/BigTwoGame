
/**
 * @author Gao Ning
 * particularly, Single class inherits the getTop method, as it only has one card
 */
public class Single extends Hand{
	private final int  typeIndex=0;
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
	 * this is the constructor of Single class
	 */
	public Single (CardGamePlayer player, CardList cards)
	{
		super (player, cards);
	}	
	/**
	 * return the type of this hand
	 */
	public String getType()
	{
		return this.LEGALCOMB[typeIndex];
	}
	/**
	 * return a boolean value indicating the validness of this hand
	 */
	public boolean isValid()
	{
		if (this.size()>=1)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}