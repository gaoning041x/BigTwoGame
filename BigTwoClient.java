import java.io.*;
import java.util.ArrayList;
import java.net.*;
public class BigTwoClient implements CardGame,NetworkGame {
	private int numOfPlayers;
	private Deck deck;
	private ArrayList<CardGamePlayer> playerList;
	private ArrayList<Hand> handsOnTable;
	private int playerID;
	private String playerName;
	private String serverIP;
	private int serverPort;
	private Socket sock;
	private ObjectOutputStream oos;
	private int currentIdx;
	private BigTwoTable table;
	private ObjectInputStream ois;
	/**
	 * this method set the socket oos ois to be null
	 * 
	 */
	public void disconnect()
	{
		/*try {
			if(ois!=null)
			{
				ois.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(oos!=null)
			{
				oos.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(sock!=null)
			{
				sock.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		sock=null;
		ois=null;
		oos=null;
	}
	/**
	 * this is the constructor
	 */
	public BigTwoClient()
	{
		numOfPlayers=0;
		playerName=null;
		playerList=new ArrayList<CardGamePlayer>();
		handsOnTable =new ArrayList<Hand>();
		for (int i=0; i<4; ++i)
		{
			playerList.add(new CardGamePlayer());
			playerList.get(i).setName(null);
		}
		table =new BigTwoTable(this);
		table.releaseGUI();
		table.disable();
		String inputName=null;
		while(inputName==null)
		{
			table.printMsg("Pleas input a name, no cancel is allowed.\n");
			inputName=table.getUserName();
		}
		setPlayerName(inputName);
		/*serverIP="127.0.0.1";
		serverPort=2396;*/
		serverIP=table.getServerIPAddress();
		serverPort=table.getServerTCP();
		if(serverIP!=null&&serverPort!=-1&&serverIP!="")
		{
			makeConnection();
		}
		else
		{
			table.printMsg("Invalid IP or Port.\n");
		}
	}
	/**
	 * this method returns the playerID
	 */
	@Override
	public int getPlayerID() {
		// TODO Auto-generated method stub
		return this.playerID;
	}

	/**
	 * this method set the playerID
	 */
	@Override
	public void setPlayerID(int playerID) {
		// TODO Auto-generated method stub
		this.playerID=playerID;
	}

	/**
	 * this method returns the name of player
	 */
	@Override
	public String getPlayerName() {
		// TODO Auto-generated method stub
		return this.playerName;
	}

	/**
	 * this method sets the name of player
	 */
	@Override
	public void setPlayerName(String playerName) {
		// TODO Auto-generated method stub
		this.playerName=playerName;
	}

	/**
	 * this method returns the ip address of the server
	 */
	@Override
	public String getServerIP() {
		// TODO Auto-generated method stub
		return this.serverIP;
	}

	/**
	 * this method set the ip address of the server for this client
	 */
	@Override
	public void setServerIP(String serverIP) {
		// TODO Auto-generated method stub
		this.serverIP=serverIP;
	}

	/**
	 * this method returns the TCP port of the server
	 */
	@Override
	public int getServerPort() {
		// TODO Auto-generated method stub
		return this.serverPort;
	}

	/**
	 * this method sets the server TCP port
	 */
	@Override
	public void setServerPort(int serverPort) {
		// TODO Auto-generated method stub
		this.serverPort=serverPort;
	}

	/** 
	 * this method bulids the connection with the server
	 */
	@Override
	public void makeConnection() {
		// TODO Auto-generated method stub
		try
		{
			sock=new Socket(serverIP, serverPort);
			oos=new ObjectOutputStream(sock.getOutputStream());
			//start a thread to receive the message from the server
			Thread rThread =new Thread (new ServerHandler());
			rThread.start();
			if(sock!=null)
			{
				sendMessage(new CardGameMessage(CardGameMessage.JOIN, -1, this.getPlayerName()));
				sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * the method parses the Message
	 */
	@Override
	public void parseMessage(GameMessage message) {
		// TODO Auto-generated method stub
		int typeOfMsg = message.getType();
		//PLAYER_LIST
		if(typeOfMsg==CardGameMessage.PLAYER_LIST)
		{
			table.connectDisable();
			int counter=0;
			this.setPlayerID(message.getPlayerID());
			table.setActivePlayer(this.playerID);
			for(int i=0; i<((String[])message.getData()).length; ++i)
			{
				if(((String[])message.getData())!=null)
				{
					this.playerList.get(i).setName(((String[])message.getData())[i]);
					++counter;
				}
			}
			numOfPlayers=counter;
		}
		//JOIN
		else if(typeOfMsg==CardGameMessage.JOIN)
		{
			this.playerList.get(message.getPlayerID()).setName((String)message.getData());
			table.repaint();
		}
		//FULL
		else if(typeOfMsg==CardGameMessage.FULL)
		{
			serverIP=null;
			serverPort=-1;
			this.disconnect();
			table.printMsg("The server is full, cannot join the game.\n");
		}
		//QUIT
		else if (typeOfMsg==CardGameMessage.QUIT)
		{
			this.playerList.get(message.getPlayerID()).setName("");
			table.repaint();
			table.printMsg("Player "+(String)message.getData()+" has left the game.\n");
			handsOnTable.clear();
			removeAllCards();
			table.repaint();
			sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
		}
		// READY
		else if(typeOfMsg==CardGameMessage.READY)
		{
			table.printMsg(playerList.get(message.getPlayerID()).getName()+" is ready!\n");
			table.repaint();
		}
		//START
		else if(typeOfMsg==CardGameMessage.START)
		{
			this.start((Deck)message.getData());
		}
		//MOVE
		else if(typeOfMsg==CardGameMessage.MOVE)
		{
			this.checkMove(message.getPlayerID(), (int[])message.getData());
		}
		//MSG
		else if (typeOfMsg==CardGameMessage.MSG)
		{
			//this should be displayed in the chat area
			table.printChatMsg((String)message.getData()+"\n");
		}
	}

	/**
	 * this method send the Message to the server
	 */
	@Override
	public void sendMessage(GameMessage message) {
		// TODO Auto-generated method stub
		try {
			if(sock!=null)
			{
				oos.writeObject(message);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** this method returns the number of players
	 */
	@Override
	public int getNumOfPlayers() {
		// TODO Auto-generated method stub
		return this.numOfPlayers;
	}

	/**
	 * this method returns the deck
	 */
	@Override
	public Deck getDeck() {
		// TODO Auto-generated method stub
		return this.deck;
	}

	/**
	 * this method returns the list of players
	 */
	@Override
	public ArrayList<CardGamePlayer> getPlayerList() {
		// TODO Auto-generated method stub
		return this.playerList;
	}

	/**
	 * this method returns the hands on table
	 */
	@Override
	public ArrayList<Hand> getHandsOnTable() {
		// TODO Auto-generated method stub
		return this.handsOnTable;
	}

	/**
	 * this method returns the current index 
	 */
	@Override
	public int getCurrentIdx() {
		// TODO Auto-generated method stub
		return this.currentIdx;
	}

	/**
	 * this method start the game
	 */
	@Override
	public void start(Deck deck) {
		// TODO Auto-generated method stub
		handsOnTable.clear();
		for (int i=0; i<4; ++i)
		{
			playerList.get(i).removeAllCards();
		}
		for (int i=0; i<=48; i+=4)
		{
			for (int j=0; j<4; ++j)
			{
				playerList.get(j).addCard(deck.getCard(i+j));
				if (deck.getCard(i+j).getRank()==2)
				{
					if(deck.getCard(i+j).getSuit()==0)
					{
						currentIdx=j;
					}
				}
			}
		}
		playerList.get(0).getCardsInHand().sort();
		playerList.get(1).getCardsInHand().sort();
		playerList.get(2).getCardsInHand().sort();
		playerList.get(3).getCardsInHand().sort();
		table.reset();
		if(this.playerID==currentIdx)
		{
			table.enable();
		}
		else
		{
			table.disable();
		}
		table.repaint();
		table.printMsg(this.getPlayerList().get(currentIdx).getName()+" please make move\n");
	}

	/**
	 * this method makes a move
	 */
	@Override
	public void makeMove(int playerID, int[] cardIdx) {
		// TODO Auto-generated method stub
		CardGameMessage move =new CardGameMessage(CardGameMessage.MOVE, -1, cardIdx);
		sendMessage(move);
		
	}

	/**
	 * this method checks the move
	 */
	@Override
	public void checkMove(int playerID, int[] cardIdx) {
		// TODO Auto-generated method stub
		int numOfHandsPlayed=handsOnTable.size();
		if(cardIdx==null)
		{
			if(numOfHandsPlayed==0)
			{
				table.printMsg("not a legal move!!!\n");
			}
			else if(handsOnTable.get(numOfHandsPlayed-1).getPlayer().getName()==playerList.get(currentIdx).getName())
			{
				table.printMsg("not a legal move!!!\n");
			}
			else
			{
				table.printMsg(playerList.get(currentIdx).getName()+": "+"{pass}\n");
				if (currentIdx!=3)
				{
					++currentIdx;
				}
				else
				{
					currentIdx=0;
				}
				table.printMsg(this.getPlayerList().get(currentIdx).getName()+" please make a move.\n");
			}
		}
		else 
		{
			if(numOfHandsPlayed==0)
			{
				CardList playerSelectedCards =new CardList();
				Hand playerHand;
				for(int i=0; i<cardIdx.length; ++i)
				{
					playerSelectedCards.addCard(playerList.get(currentIdx).getCardsInHand().getCard(cardIdx[i]));	
				}
				playerHand=composeHand(playerList.get(currentIdx), playerSelectedCards);
				if(playerHand==null)
				{
					table.printMsg("Not a legal move!!!\n");
				}
				else
				{
					playerHand.sort();
					if(playerHand.getCard(0).getRank()!=2||playerHand.getCard(0).getSuit()!=0)
					{
						table.printMsg("Not a legal move!!!\n");
					}
					else
					{
						table.printMsg(playerList.get(currentIdx).getName()+": "+"{"+playerHand.getType()+"}");
						for (int j=0; j<playerHand.size(); ++j)
						{
							table.printMsg(" ["+playerHand.getCard(j).toString()+"]");
						}
						table.printMsg("\n");
						playerList.get(currentIdx).removeCards(playerHand);
						if (currentIdx!=3)
						{
							++currentIdx;
						}
						else
						{
							currentIdx=0;
						}
						handsOnTable.add(playerHand);
						table.printMsg(this.getPlayerList().get(currentIdx).getName()+" please make a move.\n");
					}
				}
			}
			else
			{
				CardList playerSelectedCards =new CardList();
				Hand playerHand;
				for(int i=0; i<cardIdx.length; ++i)
				{
					playerSelectedCards.addCard(playerList.get(currentIdx).getCardsInHand().getCard(cardIdx[i]));
					
				}
				playerHand=composeHand(playerList.get(currentIdx), playerSelectedCards);
				if(handsOnTable.get(numOfHandsPlayed-1).getPlayer().getName()==playerList.get(currentIdx).getName())
				{
					if (playerHand==null)
					{
						table.printMsg("Not a legal move!!!\n");
					}
					else
					{
						playerHand.sort();
						table.printMsg(playerList.get(currentIdx).getName()+": "+"{"+playerHand.getType()+"}");
						for (int j=0; j<playerHand.size(); ++j)
						{
							table.printMsg(" ["+playerHand.getCard(j).toString()+"]");
						}
						table.printMsg("\n");
						playerList.get(currentIdx).removeCards(playerHand);
						if (currentIdx!=3)
						{
							++currentIdx;
						}
						else
						{
							currentIdx=0;
						}
						handsOnTable.add(playerHand);
						table.printMsg(this.getPlayerList().get(currentIdx).getName()+" please make a move.\n");
					}
				}
				else
				{
					if(playerHand!=null)
					{
						if (playerHand.size()==handsOnTable.get(handsOnTable.size()-1).size())//
						{
							if (handsOnTable.get(handsOnTable.size()-1).beats(playerHand)==true)//this means that the selected hand is beaten
							{
								table.printMsg("Not a legal move!!!\n");
							}
							else if(playerHand!=null)
							{
								playerHand.sort();
								table.printMsg(playerList.get(currentIdx).getName()+": "+"{"+playerHand.getType()+"}");
								for (int j=0; j<playerHand.size(); ++j)
								{
									table.printMsg(" ["+playerHand.getCard(j).toString()+"]");
								}
								table.printMsg("\n");
								playerList.get(currentIdx).removeCards(playerHand);
								if (currentIdx!=3)
								{
									++currentIdx;
								}
								else
								{
									currentIdx=0;
								}
								handsOnTable.add(playerHand);
								table.printMsg(this.getPlayerList().get(currentIdx).getName()+" please make a move.\n");
							}
						}
						else
						{
							table.printMsg("Not a legal move!!!\n");
						}//
					}
					else
					{
						table.printMsg("Not a legal move!!!\n");
					}//
				}
			}
		} 
		if(!endOfGame())
		{
			playerList.get(playerID).getCardsInHand().sort();
			table.resetSelected();
			if(this.playerID==currentIdx)
			{
				table.enable();
			}
			else
			{
				table.disable();
			}
			table.repaint();
		}
		/*still need to deal with the situation that this is the end of the game*/
		else
		{
			//end of game logic
			table.repaint();
			table.printEndGameMsg();
			handsOnTable.clear();
			for (int i=0; i<4; ++i)
			{
				playerList.get(i).removeAllCards();
			}
			
			sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
		} 
	}

	/**
	 * @param player
	 * @param cards
	 * @return valid hand if no null
	 */
	public static Hand composeHand(CardGamePlayer player, CardList cards)
	{
		if (cards.size()==1)
		{
			Single single =new Single(player, cards);
			return single;
		}
		else if (cards.size()==2)
		{
			Pair pair =new Pair(player, cards);
			if (pair.isValid()==true)
			{
				return pair;
			}
			else 
			{
				return null;
			}
		}
		else if (cards.size()==3)
		{
			Triple triple=new Triple (player, cards);
			if (triple.isValid()==true)
			{
				return triple;
			}
			else
			{
				return null;
			}
		}
		else if (cards.size()!=5)
		{
			return null;
		}
		else 
		{
			Straight straight=new Straight(player, cards);
			Flush flush =new Flush (player, cards);
			FullHouse fullhouse = new FullHouse (player, cards);
			Quad quad = new Quad (player, cards);
			StraightFlush straightflush =new StraightFlush(player, cards);
			if (straightflush.isValid()==true)
			{
				return straightflush;
			}
			else if(quad.isValid()==true)
			{
				return quad;
			}
			else if(fullhouse.isValid()==true)
			{
				return fullhouse;
			}
			else if (flush.isValid()==true)
			{
				return flush;
			}
			else if (straight.isValid()==true)
			{
				return straight;
			}
			else
			{
				return null;
			}
		}
	}// return a valid hand from the specified list of cards of the player. Returns null if no hand to be composed   
	/**
	 * check if the game ends
	 */
	@Override
	public boolean endOfGame() {
		// TODO Auto-generated method stub
		for (int i=0; i<playerList.size(); ++i)
		{
			if (playerList.get(i).getNumOfCards()==0)
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @author Gao Ning
	 * the class that start a new thread to handle the incoming message
	 */
	private class ServerHandler implements Runnable 
	{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			CardGameMessage incomingMsg;
			try
			{
				if(sock!=null)
				{
					ois =new ObjectInputStream(sock.getInputStream());
					while (sock!=null&&ois!=null&&(incomingMsg =(CardGameMessage)ois.readObject())!=null)
					{
						parseMessage(incomingMsg);
					}
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			
		}
		
	}
	
	/**
	 * the method that removes all cards
	 */
	public void removeAllCards ()
	{
		for (int i=0; i<playerList.size(); ++i)
		{
			playerList.get(i).removeAllCards();
		}
	}
	/**
	 * main method
	 * @param args
	 * 
	 */
	public static void main(String[] args)
	{
		BigTwoClient bigTwoClient =new BigTwoClient();
	}
}
