
public abstract class Hand extends CardList{
	/**
	 * this array contains all the legal cominations, this helps to simplify the code as 
	 * different combinations can be represented by integers
	 */
	protected final String[] LEGALCOMB ={"Single", "Pair", "Triple", "Straight", "Flush", "FullHouse", "Quad", "StraightFlush"};
	protected CardGamePlayer player;
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
	 * 		input player and the player of this hand will be set
	 * @param cards
	 * 		input cards and the cards of this hand will be se
	 */
	/**
	 * @param player
	 * @param cards
	 * this is a constructor
	 */
	public Hand (CardGamePlayer player, CardList cards)
	{
		this.player=player;
		for (int i=0; i<cards.size(); ++i)
		{
			this.addCard(cards.getCard(i));
		}
	}
	/**
	 * @return
	 * returns the player
	 */
	public CardGamePlayer getPlayer()
	{
		return this.player;
	}
	/**
	 * 		To be overridden, only give back the Top card among all.
	 */
	public Card getTopCard()
	{
		int top=0;
		int sizeOfCards=this.size();
		for (int i=0; i<sizeOfCards;++i)
		{
			if (this.getCard(i).compareTo(this.getCard(top))==1)
			{
				top=i;
			}
		}
		return this.getCard(top);
	}
	/**
	 * to be overriden 
	 * returns the type of this hand, here it is set to be Single	
	 */
	abstract public String getType();
	/**
	 * 
	 * returns a boolean value indicating the validness of a hand
	 */
	abstract public boolean isValid();
	/**
	 * 
	 * this return a boolean value indicating if the current hand beats the input hand
	 * in particular, it assume the current hand and input hand is valid
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
			if (this.getTopCard().compareTo(hand.getTopCard())==1)
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
			return true;
		}
	}
}
