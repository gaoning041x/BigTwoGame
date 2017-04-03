
/**
 * @author Gao Ning
 * @since 16-10-11
 * 
 * in particular this is a class define the behaviour of a bigTwo game deck
 */
public class BigTwoDeck extends Deck{
	private static final long serialVersionUID = -3886066435694112173L;
	/**
	 * initialize the deck
	 */
	public void initialize()
	{
		  this.removeAllCards();
			for (int i = 0; i < 4; i++)
			{
				for (int j = 0; j < 13; j++) 
				{
					BigTwoCard card = new BigTwoCard(i, j);
					addCard(card);
				}
			}
	}
}
