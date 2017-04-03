import java.util.ArrayList;
/**
 * @author Gao Ning
 * @since 16 10 11
 * Pair class, contain the legal combination of Pair
 *
 */
public class Pair extends Hand {
		private final int  typeIndex=1;
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
		public Pair (CardGamePlayer player, CardList cards)
		{
			super(player, cards);
		}	
		/**
		 * this returns the top card of the hand
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
		 * return a boolean value to decide whether the list is a Pair
		 */
		public boolean isValid()
		{
			if (this.size()!=2)
			{
				return false;
			}
			else
			{
				if (this.getCard(0).getRank()==this.getCard(1).getRank())
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		public String getType()
		{
			return LEGALCOMB[typeIndex];
		}
}